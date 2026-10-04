package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.PresaleFinalPaymentDecision;
import com.github.paohaijiao.demo.model.PresaleOrder;
import com.github.paohaijiao.demo.service.PresaleFinalPaymentService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 30：预售尾款资格与履约条件
 * <p>
 * 6 组形态：正常支付、窗口前取消退定金、逾期定金没收、库存不足排队与临界满足、
 * 商家失格三种情形关单、未实名暂挂与补全放行。
 */
public class PresaleFinalPaymentServiceTest {

    private PresaleFinalPaymentService service;

    @Before
    public void setUp() {
        PresaleFinalPaymentRules rules = JQuickJava.create()
                .createApi(PresaleFinalPaymentRules.class, "demo/presale-final-payment.xml");
        this.service = new PresaleFinalPaymentService(rules);
    }

    /**
     * 窗口进行中、实名、现货充足 → 支付 800 元尾款，定金担保
     */
    @Test
    public void shouldApproveFinalPayment() {
        PresaleOrder o = base("PRE001");
        o.setDepositAmount(200);
        o.setBalanceAmount(800);
        o.setWindowStatus(1);
        o.setDemandQty(2);
        o.setStockOnHand(100);
        o.setReservedStock(30);
        o.setIdentityVerified(true);

        PresaleFinalPaymentDecision d = service.process(o);

        Assert.assertEquals("PAYABLE", d.getAction());
        Assert.assertEquals(800.0, d.getPayableAmount(), 0.0001);
        Assert.assertEquals("DEDUCT", d.getDepositTreatment());
        Assert.assertEquals(70, d.getAvailableStock());
        Assert.assertTrue(d.isMerchantEligible());
    }

    /**
     * 窗口未开启即取消 → 定金可退
     */
    @Test
    public void shouldRefundDepositWhenCancelledBeforeWindow() {
        PresaleOrder o = base("PRE002");
        o.setDepositAmount(200);
        o.setWindowStatus(0);
        o.setCancelledBeforeWindow(true);

        PresaleFinalPaymentDecision d = service.process(o);

        Assert.assertEquals("CANCELLED", d.getAction());
        Assert.assertEquals(0.0, d.getPayableAmount(), 0.0001);
        Assert.assertEquals("REFUNDABLE", d.getDepositTreatment());
        Assert.assertTrue(d.getReasons().contains("尾款窗口开启前取消订单，定金全额退还"));
    }

    /**
     * 窗口结束仍未付尾款 → 定金没收
     */
    @Test
    public void shouldForfeitDepositAfterWindowClosed() {
        PresaleOrder o = base("PRE003");
        o.setDepositAmount(200);
        o.setWindowStatus(2);
        o.setIdentityVerified(true);

        PresaleFinalPaymentDecision d = service.process(o);

        Assert.assertEquals("FORFEIT", d.getAction());
        Assert.assertEquals("FORFEIT", d.getDepositTreatment());
        Assert.assertEquals(0.0, d.getPayableAmount(), 0.0001);
        Assert.assertTrue(d.getReasons().get(0).contains("定金按预售协议不予退还"));
    }

    /**
     * 现货仅 1 件不足 5 件 → 排队；补库后恰好 5 件临界 → 放行
     */
    @Test
    public void shouldWaitWhenStockShortThenApproveAtExactAvailable() {
        PresaleOrder o = base("PRE004");
        o.setBalanceAmount(800);
        o.setWindowStatus(1);
        o.setIdentityVerified(true);
        o.setDemandQty(5);
        o.setStockOnHand(50);
        o.setReservedStock(49);

        PresaleFinalPaymentDecision d1 = service.process(o);
        Assert.assertEquals("WAITING", d1.getAction());
        Assert.assertEquals("DEDUCT", d1.getDepositTreatment());
        Assert.assertTrue(d1.getReasons().get(0).contains("仅剩 1 件现货，不足本单 5 件需求"));

        o.setReservedStock(45);
        PresaleFinalPaymentDecision d2 = service.process(o);
        Assert.assertEquals("PAYABLE", d2.getAction());
        Assert.assertEquals(5, d2.getAvailableStock());
        Assert.assertEquals(800.0, d2.getPayableAmount(), 0.0001);
    }

    /**
     * 信用不足、历史违约、未缴保证金分别导致关单退定金
     */
    @Test
    public void shouldBlockWhenMerchantNotEligible() {
        PresaleOrder lowCredit = base("PRE005");
        lowCredit.setMerchantCreditScore(75);
        PresaleFinalPaymentDecision d1 = service.process(lowCredit);
        Assert.assertEquals("BLOCKED", d1.getAction());
        Assert.assertEquals("REFUNDABLE", d1.getDepositTreatment());
        Assert.assertTrue(d1.getReasons().contains("商家信用分 75 低于 80 分预售门槛"));

        PresaleOrder breached = base("PRE006");
        breached.setMerchantBreaches(1);
        PresaleFinalPaymentDecision d2 = service.process(breached);
        Assert.assertEquals("BLOCKED", d2.getAction());
        Assert.assertTrue(d2.getReasons().contains("商家存在 1 次历史预售违约记录"));

        PresaleOrder noBond = base("PRE007");
        noBond.setMerchantBondPaid(false);
        PresaleFinalPaymentDecision d3 = service.process(noBond);
        Assert.assertEquals("BLOCKED", d3.getAction());
        Assert.assertTrue(d3.getReasons().contains("商家未缴纳履约保证金"));
    }

    /**
     * 窗口内未实名 → 暂挂；完成实名后放行
     */
    @Test
    public void shouldHoldForIdentityThenApprove() {
        PresaleOrder o = base("PRE008");
        o.setBalanceAmount(800);
        o.setWindowStatus(1);
        o.setIdentityVerified(false);
        o.setDemandQty(1);
        o.setStockOnHand(10);
        o.setReservedStock(0);

        PresaleFinalPaymentDecision d1 = service.process(o);
        Assert.assertEquals("HOLD", d1.getAction());
        Assert.assertEquals("HOLD", d1.getDepositTreatment());
        Assert.assertTrue(d1.getReasons().get(0).contains("尚未完成实名认证"));

        o.setIdentityVerified(true);
        PresaleFinalPaymentDecision d2 = service.process(o);
        Assert.assertEquals("PAYABLE", d2.getAction());
        Assert.assertEquals(800.0, d2.getPayableAmount(), 0.0001);
    }

    private PresaleOrder base(String id) {
        PresaleOrder o = new PresaleOrder();
        o.setOrderId(id);
        o.setSku("SKU300");
        o.setDepositAmount(100);
        o.setBalanceAmount(900);
        o.setWindowStatus(1);
        o.setCancelledBeforeWindow(false);
        o.setDemandQty(1);
        o.setStockOnHand(100);
        o.setReservedStock(0);
        o.setIdentityVerified(true);
        o.setMerchantCreditScore(90);
        o.setMerchantBreaches(0);
        o.setMerchantBondPaid(true);
        return o;
    }
}
