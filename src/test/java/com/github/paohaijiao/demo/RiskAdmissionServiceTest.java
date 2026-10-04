package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.AdmissionDecision;
import com.github.paohaijiao.demo.model.CreditApplication;
import com.github.paohaijiao.demo.service.RiskAdmissionService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 1 生产级样板：信贷风控准入
 * <p>
 * 覆盖 6 组真实业务形态：优质客户、高风险多头借贷、边界客户、高分多头查询、
 * 涉诉一票否决、年龄不符硬拒绝。
 */
public class RiskAdmissionServiceTest {

    private RiskAdmissionService service;

    @Before
    public void setUp() {
        RiskAdmissionRules rules = JQuickJava.create()
                .createApi(RiskAdmissionRules.class, "demo/risk-admission.xml");
        this.service = new RiskAdmissionService(rules);
    }

    /**
     * 优质客户：国企、收入稳定、无逾期、房产足值抵押 → 自动通过、A 级、低利率
     */
    @Test
    public void shouldPassPrimeCustomerWithTopScore() {
        CreditApplication application = new CreditApplication();
        application.setApplicationId("APP20260826001");
        application.setApplicantName("张伟");
        application.setAge(35);
        application.setCompanyType("SOE");
        application.setEmployedMonths(84);
        application.setSocialSecurityMonths(60);
        application.setMonthlyIncome(30000.0);
        application.setMonthlyDebt(4500.0);
        application.setOverdueCount(0);
        application.setMaxOverdueDays(0);
        application.setCreditQueryCount3M(2);
        application.setLoanAmount(300000.0);
        application.setLoanTermMonths(36);
        application.setCollateralType("HOUSE");
        application.setCollateralValue(600000.0);
        application.setHasJudicialRecord(false);
        application.setCityTier("TIER_1");

        AdmissionDecision decision = service.evaluate(application);

        Assert.assertEquals("PASS", decision.getAdmit());
        Assert.assertEquals(100, decision.getTotalScore());
        Assert.assertEquals("A", decision.getRiskLevel());
        Assert.assertEquals(300000.0, decision.getSuggestedLimit(), 0.001);
        Assert.assertEquals(0.0385, decision.getInterestRate(), 0.0001);
        Assert.assertTrue(decision.getRejectReasons().isEmpty());
        Assert.assertTrue(decision.getRiskWarnings().isEmpty());
        Assert.assertEquals(6, decision.getScoreDetails().size());
    }

    /**
     * 高风险客户：自雇、无社保、负债率 75%、6 次逾期且最长 120 天、近 3 月查询 12 次 → 拒绝
     */
    @Test
    public void shouldRejectHighRiskCustomer() {
        CreditApplication application = new CreditApplication();
        application.setApplicationId("APP20260826002");
        application.setApplicantName("李强");
        application.setAge(31);
        application.setCompanyType("SELF_EMPLOYED");
        application.setEmployedMonths(8);
        application.setSocialSecurityMonths(0);
        application.setMonthlyIncome(12000.0);
        application.setMonthlyDebt(9000.0);
        application.setOverdueCount(6);
        application.setMaxOverdueDays(120);
        application.setCreditQueryCount3M(12);
        application.setLoanAmount(200000.0);
        application.setLoanTermMonths(24);
        application.setCollateralType("NONE");
        application.setCollateralValue(0.0);
        application.setHasJudicialRecord(false);
        application.setCityTier("TIER_3");

        AdmissionDecision decision = service.evaluate(application);

        Assert.assertEquals("REJECT", decision.getAdmit());
        Assert.assertEquals(0, decision.getTotalScore());
        Assert.assertEquals("D", decision.getRiskLevel());
        Assert.assertEquals(0.0, decision.getSuggestedLimit(), 0.001);
        Assert.assertEquals(0.0, decision.getInterestRate(), 0.0001);
        Assert.assertEquals(2, decision.getRejectReasons().size());
        Assert.assertTrue(decision.getRejectReasons().contains("负债收入比过高，超出可承受范围"));
        Assert.assertTrue(decision.getRejectReasons().contains("历史逾期严重，最长逾期天数 120 天"));
    }

    /**
     * 边界客户：民营在职 20 个月、负债率 46%、1 次短期逾期、车抵 → 人工复核、C 级
     */
    @Test
    public void shouldRouteBorderlineCustomerToManualReview() {
        CreditApplication application = new CreditApplication();
        application.setApplicationId("APP20260826003");
        application.setApplicantName("王芳");
        application.setAge(28);
        application.setCompanyType("PRIVATE");
        application.setEmployedMonths(20);
        application.setSocialSecurityMonths(12);
        application.setMonthlyIncome(15000.0);
        application.setMonthlyDebt(6900.0);
        application.setOverdueCount(1);
        application.setMaxOverdueDays(20);
        application.setCreditQueryCount3M(4);
        application.setLoanAmount(100000.0);
        application.setLoanTermMonths(24);
        application.setCollateralType("VEHICLE");
        application.setCollateralValue(130000.0);
        application.setHasJudicialRecord(false);
        application.setCityTier("TIER_2");

        AdmissionDecision decision = service.evaluate(application);

        Assert.assertEquals("MANUAL_REVIEW", decision.getAdmit());
        Assert.assertEquals(57, decision.getTotalScore());
        Assert.assertEquals("C", decision.getRiskLevel());
        Assert.assertEquals(100000.0, decision.getSuggestedLimit(), 0.001);
        Assert.assertEquals(0.0680, decision.getInterestRate(), 0.0001);
    }

    /**
     * 高分但多头查询：模型 85 分、A 级，但近 3 月查询 10 次 → 自动降级为人工复核
     */
    @Test
    public void shouldEscalateHighScoreCustomerWithFrequentQueries() {
        CreditApplication application = new CreditApplication();
        application.setApplicationId("APP20260826004");
        application.setApplicantName("赵敏");
        application.setAge(40);
        application.setCompanyType("LISTED");
        application.setEmployedMonths(60);
        application.setSocialSecurityMonths(48);
        application.setMonthlyIncome(25000.0);
        application.setMonthlyDebt(5000.0);
        application.setOverdueCount(0);
        application.setMaxOverdueDays(0);
        application.setCreditQueryCount3M(10);
        application.setLoanAmount(200000.0);
        application.setLoanTermMonths(36);
        application.setCollateralType("HOUSE");
        application.setCollateralValue(400000.0);
        application.setHasJudicialRecord(false);
        application.setCityTier("TIER_1");

        AdmissionDecision decision = service.evaluate(application);

        Assert.assertEquals("MANUAL_REVIEW", decision.getAdmit());
        Assert.assertEquals(85, decision.getTotalScore());
        Assert.assertEquals("A", decision.getRiskLevel());
        Assert.assertEquals(200000.0, decision.getSuggestedLimit(), 0.001);
        Assert.assertTrue(decision.getRiskWarnings().contains("征信查询次数超出自动审批阈值，转人工复核"));
        Assert.assertTrue(decision.getRiskWarnings().contains("近 3 个月征信查询 10 次，存在多头借贷迹象"));
    }

    /**
     * 涉诉客户：模型评分 92 分（A 级），但存在被执行记录 → 一票否决
     */
    @Test
    public void shouldRejectCustomerWithJudicialRecord() {
        CreditApplication application = new CreditApplication();
        application.setApplicationId("APP20260826005");
        application.setApplicantName("陈刚");
        application.setAge(38);
        application.setCompanyType("PRIVATE");
        application.setEmployedMonths(48);
        application.setSocialSecurityMonths(36);
        application.setMonthlyIncome(20000.0);
        application.setMonthlyDebt(3000.0);
        application.setOverdueCount(0);
        application.setMaxOverdueDays(0);
        application.setCreditQueryCount3M(3);
        application.setLoanAmount(150000.0);
        application.setLoanTermMonths(36);
        application.setCollateralType("HOUSE");
        application.setCollateralValue(300000.0);
        application.setHasJudicialRecord(true);
        application.setCityTier("TIER_2");

        AdmissionDecision decision = service.evaluate(application);

        Assert.assertEquals("REJECT", decision.getAdmit());
        Assert.assertEquals(92, decision.getTotalScore());
        Assert.assertEquals("A", decision.getRiskLevel());
        Assert.assertEquals(0.0, decision.getSuggestedLimit(), 0.001);
        Assert.assertEquals(0.0, decision.getInterestRate(), 0.0001);
        Assert.assertTrue(decision.getRejectReasons().contains("存在涉诉/被执行记录，触发一票否决"));
    }

    /**
     * 年龄不符：19 周岁 → 前置硬规则拒绝，不进入评分模型
     */
    @Test
    public void shouldRejectUnderageApplicantBeforeScoring() {
        CreditApplication application = new CreditApplication();
        application.setApplicationId("APP20260826006");
        application.setApplicantName("孙悦");
        application.setAge(19);
        application.setCompanyType("PRIVATE");
        application.setEmployedMonths(6);
        application.setSocialSecurityMonths(3);
        application.setMonthlyIncome(8000.0);
        application.setMonthlyDebt(1000.0);
        application.setOverdueCount(0);
        application.setMaxOverdueDays(0);
        application.setCreditQueryCount3M(1);
        application.setLoanAmount(50000.0);
        application.setLoanTermMonths(12);
        application.setCollateralType("NONE");
        application.setCollateralValue(0.0);
        application.setHasJudicialRecord(false);
        application.setCityTier("TIER_2");

        AdmissionDecision decision = service.evaluate(application);

        Assert.assertEquals("REJECT", decision.getAdmit());
        Assert.assertEquals(0, decision.getTotalScore());
        Assert.assertEquals(0.0, decision.getSuggestedLimit(), 0.001);
        Assert.assertTrue(decision.getRejectReasons().contains("年龄不符合准入政策（22-60 周岁）"));
        Assert.assertTrue(decision.getScoreDetails().isEmpty());
    }
}
