package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.AfterSalesDecision;
import com.github.paohaijiao.demo.model.AfterSalesRequest;
import com.github.paohaijiao.demo.service.AfterSalesService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 27：售后退款/退货/换货资格判定
 * <p>
 * 覆盖 6 组售后形态：七天无理由退货、质量退货、尺码换货、
 * 无理由条件不符双驳回、生鲜类目质量/无理由分叉、超期无凭证与商家认责分叉。
 */
public class AfterSalesServiceTest {

    private AfterSalesService service;

    @Before
    public void setUp() {
        AfterSalesRules rules = JQuickJava.create()
                .createApi(AfterSalesRules.class, "demo/after-sales.xml");
        this.service = new AfterSalesService(rules);
    }

    /**
     * 普通商品签收 3 天、未使用、密封完好 → 七天无理由退货退款，买家承担运费
     */
    @Test
    public void shouldApproveSevenDayReturn() {
        AfterSalesRequest req = base("AS001", "STANDARD", "SEVEN_DAY");
        req.setDaysSinceReceived(3);
        req.setProductCondition("UNUSED");
        req.setSealedIntact(true);
        req.setImpactResale(false);

        AfterSalesDecision d = service.review(req);

        Assert.assertEquals("RETURN_REFUND", d.getAction());
        Assert.assertEquals(1.0, d.getRefundRatio(), 0.0001);
        Assert.assertEquals("BUYER", d.getFreightPayer());
    }

    /**
     * 到货破损 10 天、凭证齐全 → 质量退货，商家承担运费
     */
    @Test
    public void shouldApproveQualityReturnWithProof() {
        AfterSalesRequest req = base("AS002", "STANDARD", "QUALITY");
        req.setDaysSinceReceived(10);
        req.setProductCondition("DAMAGED");
        req.setQualityProof(true);

        AfterSalesDecision d = service.review(req);

        Assert.assertEquals("RETURN_REFUND", d.getAction());
        Assert.assertEquals(1.0, d.getRefundRatio(), 0.0001);
        Assert.assertEquals("MERCHANT", d.getFreightPayer());
        Assert.assertTrue(d.getReasons().contains(
                "质量问题凭证核验通过，办理全额退货退款，退货运费由商家承担"));
    }

    /**
     * 已穿用但 8 天内尺码不符 → 换货，买家承担换货运费
     */
    @Test
    public void shouldApproveSizeExchange() {
        AfterSalesRequest req = base("AS003", "STANDARD", "SIZE");
        req.setDaysSinceReceived(8);
        req.setProductCondition("USED");

        AfterSalesDecision d = service.review(req);

        Assert.assertEquals("EXCHANGE", d.getAction());
        Assert.assertEquals(0.0, d.getRefundRatio(), 0.0001);
        Assert.assertEquals("BUYER", d.getFreightPayer());
        Assert.assertTrue(d.getReasons().contains("尺码不符 15 天内支持换货，换货运费由买家承担"));
    }

    /**
     * 同样 5 天无理由：有使用痕迹或密封破损 → 均驳回
     */
    @Test
    public void shouldRejectSevenDayWhenConditionNotMet() {
        AfterSalesRequest used = base("AS004", "STANDARD", "SEVEN_DAY");
        used.setDaysSinceReceived(5);
        used.setProductCondition("UNUSED");
        used.setSealedIntact(true);
        used.setImpactResale(true);
        AfterSalesDecision d1 = service.review(used);
        Assert.assertEquals("REJECT", d1.getAction());
        Assert.assertTrue(d1.getReasons().get(0).contains("不符合七天无理由退货条件"));

        AfterSalesRequest unsealed = base("AS005", "STANDARD", "SEVEN_DAY");
        unsealed.setDaysSinceReceived(5);
        unsealed.setProductCondition("UNUSED");
        unsealed.setSealedIntact(false);
        unsealed.setImpactResale(false);
        AfterSalesDecision d2 = service.review(unsealed);
        Assert.assertEquals("REJECT", d2.getAction());
    }

    /**
     * 生鲜：质量问题可退；无理由拒退
     */
    @Test
    public void shouldSplitFreshQualityAndSevenDay() {
        AfterSalesRequest quality = base("AS006", "FRESH", "QUALITY");
        quality.setDaysSinceReceived(2);
        quality.setProductCondition("DAMAGED");
        quality.setQualityProof(true);
        AfterSalesDecision d1 = service.review(quality);
        Assert.assertEquals("RETURN_REFUND", d1.getAction());
        Assert.assertEquals("MERCHANT", d1.getFreightPayer());

        AfterSalesRequest sevenDay = base("AS007", "FRESH", "SEVEN_DAY");
        sevenDay.setDaysSinceReceived(2);
        sevenDay.setProductCondition("UNUSED");
        sevenDay.setSealedIntact(true);
        AfterSalesDecision d2 = service.review(sevenDay);
        Assert.assertEquals("REJECT", d2.getAction());
        Assert.assertTrue(d2.getReasons().contains("生鲜易腐商品不支持七天无理由退货（质量问题除外）"));
    }

    /**
     * 超期无凭证驳回；异议期内商家认责即便无凭证也全退
     */
    @Test
    public void shouldHandleOverdueProofMissingAndShopResponsible() {
        AfterSalesRequest overdue = base("AS008", "STANDARD", "QUALITY");
        overdue.setDaysSinceReceived(20);
        overdue.setProductCondition("DAMAGED");
        overdue.setQualityProof(false);
        overdue.setShopResponsible(false);
        AfterSalesDecision d1 = service.review(overdue);
        Assert.assertEquals("REJECT", d1.getAction());
        Assert.assertEquals("NONE", d1.getFreightPayer());

        AfterSalesRequest shopFault = base("AS009", "STANDARD", "QUALITY");
        shopFault.setDaysSinceReceived(10);
        shopFault.setProductCondition("DAMAGED");
        shopFault.setQualityProof(false);
        shopFault.setShopResponsible(true);
        AfterSalesDecision d2 = service.review(shopFault);
        Assert.assertEquals("RETURN_REFUND", d2.getAction());
        Assert.assertEquals("MERCHANT", d2.getFreightPayer());
        Assert.assertTrue(d2.getReasons().contains(
                "商家责任认定成立，办理全额退货退款，退货运费由商家承担"));
    }

    private AfterSalesRequest base(String orderId, String category, String reason) {
        AfterSalesRequest r = new AfterSalesRequest();
        r.setOrderId(orderId);
        r.setSku("SKU100");
        r.setProductCategory(category);
        r.setDaysSinceReceived(0);
        r.setProductCondition("UNUSED");
        r.setReasonType(reason);
        r.setQualityProof(false);
        r.setShopResponsible(false);
        r.setSealedIntact(true);
        r.setImpactResale(false);
        return r;
    }
}
