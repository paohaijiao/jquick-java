package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.CorporateCreditDecision;
import com.github.paohaijiao.demo.model.CorporateLoanApplication;
import com.github.paohaijiao.demo.service.CorporateLoanService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 16 生产级样板：企业经营贷授信额度智能测算
 * <p>
 * 覆盖 6 组企业形态：优质科技（纳税倍数+营收封顶）、稳健制造（B 级批准）、
 * 临界复核（C 级人工）、失信被执行、超高负债、受限行业。
 */
public class CorporateLoanServiceTest {

    private CorporateLoanService service;

    @Before
    public void setUp() {
        CorporateLoanRules rules = JQuickJava.create()
                .createApi(CorporateLoanRules.class, "demo/corporate-credit.xml");
        this.service = new CorporateLoanService(rules);
    }

    /**
     * 优质科技企业：90 分 A 级，理论 2700 万先受营收占比压到 2400 万、再触单户 2000 万上限
     */
    @Test
    public void shouldApproveTopTechEnterpriseWithRevenueCap() {
        CorporateLoanApplication app = new CorporateLoanApplication();
        app.setApplicationId("CL20260001");
        app.setEnterpriseName("华芯半导体科技有限公司");
        app.setOperatingYears(9);
        app.setAnnualRevenue(80000000.0);
        app.setAnnualTaxAmount(3000000.0);
        app.setBankFlowAnnual(100000000.0);
        app.setDebtRatio(45);
        app.setTaxCreditLevel("A");
        app.setOverdueCount(0);
        app.setHasJudicialRecord(false);
        app.setIndustryCategory("TECH");
        app.setApplyAmount(25000000.0);

        CorporateCreditDecision decision = service.evaluate(app);

        Assert.assertEquals(90, decision.getTotalScore());
        Assert.assertEquals("A", decision.getCreditRating());
        Assert.assertEquals("APPROVE", decision.getDecision());
        Assert.assertEquals(27000000.0, decision.getTheoreticalLimit(), 0.001);
        Assert.assertEquals(20000000.0, decision.getApprovedLimit(), 0.001);
        Assert.assertEquals("CEILING", decision.getCappedBy());
        Assert.assertEquals(0.0345, decision.getInterestRate(), 0.00001);
        Assert.assertTrue(decision.getRiskWarnings().contains(
                "理论授信额度 27000000.00 元（纳税额 × 9.0）受单户 2000 万绝对上限约束，核定额度 20000000.00 元"));
    }

    /**
     * 稳健制造企业：78 分 B 级，纳税 350 万 × 4.5 倍理论 1575 万，按营收 30% 核 900 万
     */
    @Test
    public void shouldApproveStableManufacturer() {
        CorporateLoanApplication app = new CorporateLoanApplication();
        app.setApplicationId("CL20260002");
        app.setEnterpriseName("恒峰精密制造有限公司");
        app.setOperatingYears(6);
        app.setAnnualRevenue(30000000.0);
        app.setAnnualTaxAmount(3500000.0);
        app.setBankFlowAnnual(35000000.0);
        app.setDebtRatio(55);
        app.setTaxCreditLevel("B");
        app.setOverdueCount(0);
        app.setHasJudicialRecord(false);
        app.setIndustryCategory("MANUFACTURING");
        app.setApplyAmount(10000000.0);

        CorporateCreditDecision decision = service.evaluate(app);

        Assert.assertEquals(78, decision.getTotalScore());
        Assert.assertEquals("B", decision.getCreditRating());
        Assert.assertEquals("APPROVE", decision.getDecision());
        Assert.assertEquals(15750000.0, decision.getTheoreticalLimit(), 0.001);
        Assert.assertEquals(9000000.0, decision.getApprovedLimit(), 0.001);
        Assert.assertEquals("REVENUE", decision.getCappedBy());
        Assert.assertEquals(0.0385, decision.getInterestRate(), 0.00001);
    }

    /**
     * 临界商贸企业：64 分 C 级转人工复核，额度按纳税 2.5 倍核 87.5 万
     */
    @Test
    public void shouldRouteBorderlineEnterpriseToManualReview() {
        CorporateLoanApplication app = new CorporateLoanApplication();
        app.setApplicationId("CL20260003");
        app.setEnterpriseName("瑞达商贸有限公司");
        app.setOperatingYears(5);
        app.setAnnualRevenue(21000000.0);
        app.setAnnualTaxAmount(350000.0);
        app.setBankFlowAnnual(18000000.0);
        app.setDebtRatio(58);
        app.setTaxCreditLevel("B");
        app.setOverdueCount(1);
        app.setHasJudicialRecord(false);
        app.setIndustryCategory("TRADE");
        app.setApplyAmount(1000000.0);

        CorporateCreditDecision decision = service.evaluate(app);

        Assert.assertEquals(64, decision.getTotalScore());
        Assert.assertEquals("C", decision.getCreditRating());
        Assert.assertEquals("MANUAL_REVIEW", decision.getDecision());
        Assert.assertEquals(875000.0, decision.getApprovedLimit(), 0.001);
        Assert.assertEquals("TAX", decision.getCappedBy());
        Assert.assertEquals(0.0435, decision.getInterestRate(), 0.00001);
        Assert.assertTrue(decision.getRiskWarnings().contains(
                "评分处于复核区间（60-74），需补充财报与实地尽调"));
        Assert.assertTrue(decision.getScoreDetails().contains("流水核验评分: 8 / 15"));
    }

    /**
     * 失信被执行：一票否决，不进入评分
     */
    @Test
    public void shouldRejectDishonestEnterprise() {
        CorporateLoanApplication app = new CorporateLoanApplication();
        app.setApplicationId("CL20260004");
        app.setEnterpriseName("失信黑名单企业");
        app.setOperatingYears(10);
        app.setAnnualRevenue(50000000.0);
        app.setAnnualTaxAmount(2000000.0);
        app.setBankFlowAnnual(60000000.0);
        app.setDebtRatio(50);
        app.setTaxCreditLevel("B");
        app.setOverdueCount(0);
        app.setHasJudicialRecord(true);
        app.setIndustryCategory("TRADE");
        app.setApplyAmount(5000000.0);

        CorporateCreditDecision decision = service.evaluate(app);

        Assert.assertEquals("REJECT", decision.getDecision());
        Assert.assertEquals(0, decision.getTotalScore());
        Assert.assertEquals(0.0, decision.getApprovedLimit(), 0.001);
        Assert.assertTrue(decision.getRejectReasons().contains(
                "企业存在失信被执行/重大涉诉记录，触发一票否决"));
    }

    /**
     * 资产负债率 88%：超过 85% 硬性上限直接拒绝
     */
    @Test
    public void shouldRejectOverLeveragedEnterprise() {
        CorporateLoanApplication app = new CorporateLoanApplication();
        app.setApplicationId("CL20260005");
        app.setEnterpriseName("高负债贸易企业");
        app.setOperatingYears(7);
        app.setAnnualRevenue(40000000.0);
        app.setAnnualTaxAmount(800000.0);
        app.setBankFlowAnnual(42000000.0);
        app.setDebtRatio(88);
        app.setTaxCreditLevel("B");
        app.setOverdueCount(1);
        app.setHasJudicialRecord(false);
        app.setIndustryCategory("TRADE");
        app.setApplyAmount(3000000.0);

        CorporateCreditDecision decision = service.evaluate(app);

        Assert.assertEquals("REJECT", decision.getDecision());
        Assert.assertTrue(decision.getRejectReasons().contains(
                "资产负债率 88% 超过硬性上限 85%，偿债能力不足"));
    }

    /**
     * 受限行业（类金融/房地产）：行业准入直接拒绝
     */
    @Test
    public void shouldRejectRestrictedIndustry() {
        CorporateLoanApplication app = new CorporateLoanApplication();
        app.setApplicationId("CL20260006");
        app.setEnterpriseName("某受限行业公司");
        app.setOperatingYears(12);
        app.setAnnualRevenue(120000000.0);
        app.setAnnualTaxAmount(5000000.0);
        app.setBankFlowAnnual(150000000.0);
        app.setDebtRatio(40);
        app.setTaxCreditLevel("A");
        app.setOverdueCount(0);
        app.setHasJudicialRecord(false);
        app.setIndustryCategory("RESTRICTED");
        app.setApplyAmount(20000000.0);

        CorporateCreditDecision decision = service.evaluate(app);

        Assert.assertEquals("REJECT", decision.getDecision());
        Assert.assertTrue(decision.getRejectReasons().contains("企业属于授信受限行业，不予准入"));
    }
}
