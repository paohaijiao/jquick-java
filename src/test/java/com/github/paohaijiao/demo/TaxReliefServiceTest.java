package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.TaxReliefApplication;
import com.github.paohaijiao.demo.model.TaxReliefDecision;
import com.github.paohaijiao.demo.service.TaxReliefService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 23：小微企业税费减免 / 稳岗补贴资格判定
 * <p>
 * 覆盖 6 组企业形态：小规模低档税率、一般纳税人跨档税率、三项标准超标、
 * 临界值小微企业、限制行业禁入、裁员率与欠税影响稳岗返还。
 */
public class TaxReliefServiceTest {

    private TaxReliefService service;

    @Before
    public void setUp() {
        TaxReliefRules rules = JQuickJava.create()
                .createApi(TaxReliefRules.class, "demo/tax-relief.xml");
        this.service = new TaxReliefService(rules);
    }

    /**
     * 小规模纳税人，80 人、所得额 80 万、稳岗条件齐全 → 所得税 5% 缴 4 万，补贴 2 万
     */
    @Test
    public void shouldApplyFivePercentTaxAndStabilizationSubsidy() {
        TaxReliefApplication app = baseApplication("TR2026001", "SMALL_SCALE");
        app.setEmployeeCount(80);
        app.setTotalAssetsWan(1000.0);
        app.setAnnualTaxableIncomeWan(80.0);
        app.setSocialInsuranceMonths(24);
        app.setLayoffRate(2);
        app.setPaidUnemploymentInsuranceWan(4.0);

        TaxReliefDecision decision = service.evaluate(app);

        Assert.assertEquals("APPROVED", decision.getResult());
        Assert.assertTrue(decision.isSmallQualified());
        Assert.assertEquals(20.0, decision.getStatutoryTaxWan(), 0.001);
        Assert.assertEquals(4.0, decision.getIncomeTaxWan(), 0.001);
        Assert.assertEquals(16.0, decision.getTaxSavingWan(), 0.001);
        Assert.assertEquals(0.01, decision.getVatLevyRate(), 0.00001);
        Assert.assertTrue(decision.isStabilizationEligible());
        Assert.assertEquals(2.0, decision.getStabilizationSubsidyWan(), 0.001);
    }

    /**
     * 一般纳税人，200 人、所得额 250 万跨档 → 5 + 150×10% = 20 万，社保不足无稳岗
     */
    @Test
    public void applySegmentedTaxForIncomeOverOneMillion() {
        TaxReliefApplication app = baseApplication("TR2026002", "GENERAL");
        app.setEmployeeCount(200);
        app.setTotalAssetsWan(4000.0);
        app.setAnnualTaxableIncomeWan(250.0);
        app.setSocialInsuranceMonths(6);
        app.setLayoffRate(1);
        app.setPaidUnemploymentInsuranceWan(3.0);

        TaxReliefDecision decision = service.evaluate(app);

        Assert.assertTrue(decision.isSmallQualified());
        Assert.assertEquals(62.5, decision.getStatutoryTaxWan(), 0.001);
        Assert.assertEquals(20.0, decision.getIncomeTaxWan(), 0.001);
        Assert.assertEquals(42.5, decision.getTaxSavingWan(), 0.001);
        Assert.assertEquals(0.0, decision.getVatLevyRate(), 0.00001);
        Assert.assertFalse(decision.isStabilizationEligible());
        Assert.assertEquals(0.0, decision.getStabilizationSubsidyWan(), 0.001);
    }

    /**
     * 400 人、资产 6000 万、所得额 400 万，三项标准全超 → 按 25% 完税，逐条告知
     */
    @Test
    public void shouldDenySmallTaxBenefitWhenAllLimitsExceeded() {
        TaxReliefApplication app = baseApplication("TR2026003", "GENERAL");
        app.setEmployeeCount(400);
        app.setTotalAssetsWan(6000.0);
        app.setAnnualTaxableIncomeWan(400.0);
        app.setSocialInsuranceMonths(0);
        app.setLayoffRate(0);

        TaxReliefDecision decision = service.evaluate(app);

        Assert.assertFalse(decision.isSmallQualified());
        Assert.assertEquals(100.0, decision.getIncomeTaxWan(), 0.001);
        Assert.assertEquals(0.0, decision.getTaxSavingWan(), 0.001);
        Assert.assertTrue(decision.getReasons().contains("从业人数 400 人超过 300 人标准"));
        Assert.assertTrue(decision.getReasons().contains("资产总额 6000.00 万元超过 5000 万元标准"));
        Assert.assertTrue(decision.getReasons().contains("年应纳税所得额超过 300 万元标准"));
    }

    /**
     * 人数 300、资产 5000 万、所得额 300 万恰在临界值 → 仍具备小微资格，税 25 万
     */
    @Test
    public void shouldQualifyAtExactBoundaryValues() {
        TaxReliefApplication app = baseApplication("TR2026004", "GENERAL");
        app.setEmployeeCount(300);
        app.setTotalAssetsWan(5000.0);
        app.setAnnualTaxableIncomeWan(300.0);
        app.setSocialInsuranceMonths(24);
        app.setLayoffRate(0);

        TaxReliefDecision decision = service.evaluate(app);

        Assert.assertTrue(decision.isSmallQualified());
        Assert.assertEquals(75.0, decision.getStatutoryTaxWan(), 0.001);
        Assert.assertEquals(25.0, decision.getIncomeTaxWan(), 0.001);
        Assert.assertEquals(50.0, decision.getTaxSavingWan(), 0.001);
    }

    /**
     * 限制行业企业即使规模符合 → 全面禁止享受，按法定税率完税
     */
    @Test
    public void shouldDenyAllBenefitsForRestrictedIndustry() {
        TaxReliefApplication app = baseApplication("TR2026005", "SMALL_SCALE");
        app.setEmployeeCount(50);
        app.setTotalAssetsWan(500.0);
        app.setAnnualTaxableIncomeWan(80.0);
        app.setSocialInsuranceMonths(36);
        app.setLayoffRate(0);
        app.setPaidUnemploymentInsuranceWan(2.0);
        app.setRestrictedIndustry(true);

        TaxReliefDecision decision = service.evaluate(app);

        Assert.assertEquals("DENIED", decision.getResult());
        Assert.assertFalse(decision.isSmallQualified());
        Assert.assertEquals(20.0, decision.getIncomeTaxWan(), 0.001);
        Assert.assertEquals(0.0, decision.getTaxSavingWan(), 0.001);
        Assert.assertEquals(0.0, decision.getVatLevyRate(), 0.00001);
        Assert.assertFalse(decision.isStabilizationEligible());
        Assert.assertTrue(decision.getReasons().contains(
                "企业从事限制或禁止行业，不得享受小型微利企业税收优惠及稳岗返还"));
    }

    /**
     * 裁员率 8% 不符稳岗条件；裁员率达标但欠税的，稳岗返还暂缓
     */
    @Test
    public void shouldBlockStabilizationSubsidyForHighLayoffAndTaxDue() {
        TaxReliefApplication highLayoff = baseApplication("TR2026006", "SMALL_SCALE");
        highLayoff.setEmployeeCount(100);
        highLayoff.setTotalAssetsWan(800.0);
        highLayoff.setAnnualTaxableIncomeWan(60.0);
        highLayoff.setSocialInsuranceMonths(24);
        highLayoff.setLayoffRate(8);
        highLayoff.setPaidUnemploymentInsuranceWan(4.0);
        TaxReliefDecision d1 = service.evaluate(highLayoff);
        Assert.assertTrue(d1.isSmallQualified());
        Assert.assertEquals(3.0, d1.getIncomeTaxWan(), 0.001);
        Assert.assertFalse(d1.isStabilizationEligible());
        Assert.assertEquals(0.0, d1.getStabilizationSubsidyWan(), 0.001);

        TaxReliefApplication taxDue = baseApplication("TR2026007", "SMALL_SCALE");
        taxDue.setEmployeeCount(100);
        taxDue.setTotalAssetsWan(800.0);
        taxDue.setAnnualTaxableIncomeWan(60.0);
        taxDue.setSocialInsuranceMonths(24);
        taxDue.setLayoffRate(2);
        taxDue.setPaidUnemploymentInsuranceWan(4.0);
        taxDue.setOverdueTax(true);
        TaxReliefDecision d2 = service.evaluate(taxDue);
        Assert.assertFalse(d2.isStabilizationEligible());
        Assert.assertTrue(d2.getReasons().contains("企业存在欠税记录，稳岗返还暂缓拨付，清缴后另行申请"));
    }

    private TaxReliefApplication baseApplication(String id, String taxpayerType) {
        TaxReliefApplication app = new TaxReliefApplication();
        app.setEnterpriseId(id);
        app.setEnterpriseName("测试小微企业");
        app.setTaxpayerType(taxpayerType);
        app.setEmployeeCount(100);
        app.setTotalAssetsWan(1000.0);
        app.setAnnualTaxableIncomeWan(100.0);
        app.setRestrictedIndustry(false);
        app.setSocialInsuranceMonths(12);
        app.setLayoffRate(0);
        app.setSocialArrears(false);
        app.setOverdueTax(false);
        app.setPaidUnemploymentInsuranceWan(0.0);
        return app;
    }
}
