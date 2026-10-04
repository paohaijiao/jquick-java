package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.LoanAccount;
import com.github.paohaijiao.demo.model.WarningDecision;
import com.github.paohaijiao.demo.service.PostLoanWarningService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 2 生产级样板：贷后逾期预警与催收策略
 * <p>
 * 覆盖 6 组贷后形态：严重逾期大额失联、首次轻微逾期、中期足值抵押、失联车抵、
 * 账户正常、长期逾期触发诉讼。
 */
public class PostLoanWarningServiceTest {

    private PostLoanWarningService service;

    @Before
    public void setUp() {
        PostLoanWarningRules rules = JQuickJava.create()
                .createApi(PostLoanWarningRules.class, "demo/post-loan-warning.xml");
        this.service = new PostLoanWarningService(rules);
    }

    /**
     * 严重逾期：95 天、逾期 20 万、失联无抵押 → ORANGE，走诉讼渠道
     */
    @Test
    public void shouldEscalateSevereOverdueAccount() {
        LoanAccount account = new LoanAccount();
        account.setLoanNo("LN20260826001");
        account.setCustomerName("张强");
        account.setProductType("CONSUMER");
        account.setRemainingPrincipal(365000.0);
        account.setOverdueAmount(200000.0);
        account.setOverdueDays(95);
        account.setBaseRate(0.10);
        account.setCollateralType("NONE");
        account.setCollateralValue(0.0);
        account.setContactSuccess(false);
        account.setLastContactDays(30);
        account.setOverdueHistoryCount(0);
        account.setWarningCount(0);

        WarningDecision decision = service.evaluate(account);

        Assert.assertEquals("ORANGE", decision.getWarningLevel());
        Assert.assertEquals(60, decision.getWarningScore());
        Assert.assertEquals(4, decision.getOverdueStage());
        Assert.assertEquals("LITIGATION", decision.getCollectChannel());
        Assert.assertEquals(14250.0, decision.getPenaltyInterest(), 0.001);
        Assert.assertEquals(210000.0, decision.getMinRepayAmount(), 0.001);
        Assert.assertEquals(8, decision.getRiskMigration());
        Assert.assertTrue(decision.isNeedLitigation());
        Assert.assertTrue(decision.getActions().contains("多次联系未果，建议上门核实并更新联系方式"));
    }

    /**
     * 首次轻微逾期：5 天、逾期 1000 元、可联系 → BLUE，短信提醒
     */
    @Test
    public void shouldKeepMildOverdueAccountLowLevel() {
        LoanAccount account = new LoanAccount();
        account.setLoanNo("LN20260826002");
        account.setCustomerName("李娜");
        account.setProductType("CONSUMER");
        account.setRemainingPrincipal(365000.0);
        account.setOverdueAmount(1000.0);
        account.setOverdueDays(5);
        account.setBaseRate(0.06);
        account.setCollateralType("NONE");
        account.setCollateralValue(0.0);
        account.setContactSuccess(true);
        account.setLastContactDays(1);
        account.setOverdueHistoryCount(0);
        account.setWarningCount(0);

        WarningDecision decision = service.evaluate(account);

        Assert.assertEquals("BLUE", decision.getWarningLevel());
        Assert.assertEquals(27, decision.getWarningScore());
        Assert.assertEquals(1, decision.getOverdueStage());
        Assert.assertEquals("SMS", decision.getCollectChannel());
        Assert.assertEquals(450.0, decision.getPenaltyInterest(), 0.001);
        Assert.assertEquals(1010.0, decision.getMinRepayAmount(), 0.001);
        Assert.assertEquals(2, decision.getRiskMigration());
        Assert.assertFalse(decision.isNeedLitigation());
    }

    /**
     * 中期逾期：45 天、房抵覆盖 1.37 倍、可联系 → ORANGE，上门催收
     */
    @Test
    public void shouldRouteCollateralizedOverdueToHomeVisit() {
        LoanAccount account = new LoanAccount();
        account.setLoanNo("LN20260826003");
        account.setCustomerName("王磊");
        account.setProductType("MORTGAGE");
        account.setRemainingPrincipal(365000.0);
        account.setOverdueAmount(60000.0);
        account.setOverdueDays(45);
        account.setBaseRate(0.10);
        account.setCollateralType("HOUSE");
        account.setCollateralValue(500000.0);
        account.setContactSuccess(true);
        account.setLastContactDays(5);
        account.setOverdueHistoryCount(1);
        account.setWarningCount(1);

        WarningDecision decision = service.evaluate(account);

        Assert.assertEquals("ORANGE", decision.getWarningLevel());
        Assert.assertEquals(66, decision.getWarningScore());
        Assert.assertEquals(2, decision.getOverdueStage());
        Assert.assertEquals("HOME_VISIT", decision.getCollectChannel());
        Assert.assertEquals(6750.0, decision.getPenaltyInterest(), 0.001);
        Assert.assertEquals(61800.0, decision.getMinRepayAmount(), 0.001);
        Assert.assertEquals(5, decision.getRiskMigration());
        Assert.assertFalse(decision.isNeedLitigation());
    }

    /**
     * 失联车抵：35 天、逾期 10 万、车抵覆盖不足、联系不上 → YELLOW，电话催收
     */
    @Test
    public void shouldWarnLostContactAccount() {
        LoanAccount account = new LoanAccount();
        account.setLoanNo("LN20260826004");
        account.setCustomerName("赵敏");
        account.setProductType("SME");
        account.setRemainingPrincipal(365000.0);
        account.setOverdueAmount(100000.0);
        account.setOverdueDays(35);
        account.setBaseRate(0.12);
        account.setCollateralType("VEHICLE");
        account.setCollateralValue(300000.0);
        account.setContactSuccess(false);
        account.setLastContactDays(20);
        account.setOverdueHistoryCount(2);
        account.setWarningCount(0);

        WarningDecision decision = service.evaluate(account);

        Assert.assertEquals("YELLOW", decision.getWarningLevel());
        Assert.assertEquals(41, decision.getWarningScore());
        Assert.assertEquals("PHONE", decision.getCollectChannel());
        Assert.assertEquals(6300.0, decision.getPenaltyInterest(), 0.001);
        Assert.assertEquals(103000.0, decision.getMinRepayAmount(), 0.001);
        Assert.assertEquals(4, decision.getRiskMigration());
        Assert.assertFalse(decision.isNeedLitigation());
        Assert.assertTrue(decision.getActions().contains("多次联系未果，建议上门核实并更新联系方式"));
    }

    /**
     * 账户正常：未逾期 → 直接短路，不进入评分模型
     */
    @Test
    public void shouldShortCircuitWhenAccountNotOverdue() {
        LoanAccount account = new LoanAccount();
        account.setLoanNo("LN20260826005");
        account.setCustomerName("陈静");
        account.setProductType("CONSUMER");
        account.setRemainingPrincipal(50000.0);
        account.setOverdueAmount(0.0);
        account.setOverdueDays(0);
        account.setBaseRate(0.08);
        account.setCollateralType("NONE");
        account.setCollateralValue(0.0);
        account.setContactSuccess(true);
        account.setLastContactDays(0);
        account.setOverdueHistoryCount(0);
        account.setWarningCount(0);

        WarningDecision decision = service.evaluate(account);

        Assert.assertEquals("BLUE", decision.getWarningLevel());
        Assert.assertEquals(0, decision.getWarningScore());
        Assert.assertEquals(0, decision.getOverdueStage());
        Assert.assertEquals("SMS", decision.getCollectChannel());
        Assert.assertEquals(0.0, decision.getPenaltyInterest(), 0.001);
        Assert.assertEquals(0.0, decision.getMinRepayAmount(), 0.001);
        Assert.assertEquals(0, decision.getRiskMigration());
        Assert.assertFalse(decision.isNeedLitigation());
        Assert.assertTrue(decision.getActions().contains("账户状态正常，无需催收"));
    }

    /**
     * 长期逾期：120 天、逾期 40 万、房抵覆盖足值、频繁预警 → RED，启动诉讼准备
     */
    @Test
    public void shouldRaiseRedAlertAndPrepareLitigation() {
        LoanAccount account = new LoanAccount();
        account.setLoanNo("LN20260826006");
        account.setCustomerName("周涛");
        account.setProductType("SME");
        account.setRemainingPrincipal(365000.0);
        account.setOverdueAmount(400000.0);
        account.setOverdueDays(120);
        account.setBaseRate(0.15);
        account.setCollateralType("HOUSE");
        account.setCollateralValue(500000.0);
        account.setContactSuccess(true);
        account.setLastContactDays(10);
        account.setOverdueHistoryCount(0);
        account.setWarningCount(2);

        WarningDecision decision = service.evaluate(account);

        Assert.assertEquals("RED", decision.getWarningLevel());
        Assert.assertEquals(90, decision.getWarningScore());
        Assert.assertEquals(4, decision.getOverdueStage());
        Assert.assertEquals("LITIGATION", decision.getCollectChannel());
        Assert.assertEquals(27000.0, decision.getPenaltyInterest(), 0.001);
        Assert.assertEquals(420000.0, decision.getMinRepayAmount(), 0.001);
        Assert.assertEquals(9, decision.getRiskMigration());
        Assert.assertTrue(decision.isNeedLitigation());
        Assert.assertTrue(decision.getActions().contains("预警等级 RED，启动诉讼材料准备"));
    }
}
