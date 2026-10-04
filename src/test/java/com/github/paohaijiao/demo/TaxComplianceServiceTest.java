package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.TaxProfile;
import com.github.paohaijiao.demo.model.TaxRiskDecision;
import com.github.paohaijiao.demo.service.TaxComplianceService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 8 生产级样板：税务合规风险打分
 * <p>
 * 覆盖 6 组企业形态：高风险异常企业、合规良好企业、中风险临界、
 * 异常发票触发稽查、进项异常、新设企业。
 */
public class TaxComplianceServiceTest {

    private TaxComplianceService service;

    @Before
    public void setUp() {
        TaxComplianceRules rules = JQuickJava.create()
                .createApi(TaxComplianceRules.class, "demo/tax-compliance.xml");
        this.service = new TaxComplianceService(rules);
    }

    /**
     * 高风险异常企业：逾期 5 次、缺票 3 次、税负率极低、进项异常、异常发票 → 100 分、稽查
     */
    @Test
    public void shouldFlagHighRiskEnterprise() {
        TaxProfile profile = new TaxProfile();
        profile.setCompanyId("91310000MA1K0001X");
        profile.setCompanyName("某贸易有限公司");
        profile.setTaxpayerType("GENERAL");
        profile.setTaxPeriod("2026Q2");
        profile.setLateCount(5);
        profile.setMissingInvoice(3);
        profile.setTaxBurdenRate(0.01);
        profile.setIndustryAvgBurden(0.03);
        profile.setRevenue(8000000.0);
        profile.setInputTaxRatio(0.97);
        profile.setHasAbnormalInvoice(true);
        profile.setYearsSinceRegister(1);
        profile.setCreditRating("C");

        TaxRiskDecision decision = service.evaluate(profile);

        Assert.assertEquals(100, decision.getRiskScore());
        Assert.assertEquals("HIGH", decision.getRiskLevel());
        Assert.assertEquals("TAX_AUDIT", decision.getAuditLevel());
        Assert.assertTrue(decision.isNeedWarning());
        Assert.assertTrue(decision.isNeedInvestigation());
        Assert.assertEquals(7, decision.getIndicators().size());
    }

    /**
     * 合规良好企业：各项指标正常 → 0 分、自查
     */
    @Test
    public void shouldPassCompliantEnterprise() {
        TaxProfile profile = new TaxProfile();
        profile.setCompanyId("91310000MA1K0002X");
        profile.setCompanyName("某科技股份有限公司");
        profile.setTaxpayerType("GENERAL");
        profile.setTaxPeriod("2026Q2");
        profile.setLateCount(0);
        profile.setMissingInvoice(0);
        profile.setTaxBurdenRate(0.05);
        profile.setIndustryAvgBurden(0.04);
        profile.setRevenue(20000000.0);
        profile.setInputTaxRatio(0.70);
        profile.setHasAbnormalInvoice(false);
        profile.setYearsSinceRegister(5);
        profile.setCreditRating("A");

        TaxRiskDecision decision = service.evaluate(profile);

        Assert.assertEquals(0, decision.getRiskScore());
        Assert.assertEquals("LOW", decision.getRiskLevel());
        Assert.assertEquals("SELF_CHECK", decision.getAuditLevel());
        Assert.assertFalse(decision.isNeedWarning());
        Assert.assertFalse(decision.isNeedInvestigation());
        Assert.assertTrue(decision.getNotes().contains("风险较低，纳入常规自查"));
    }

    /**
     * 中风险临界：逾期 3 次、缺票 1 次、税负率偏低、进项偏高 → 48 分、实地核查
     */
    @Test
    public void shouldAssessMediumRiskEnterprise() {
        TaxProfile profile = new TaxProfile();
        profile.setCompanyId("91310000MA1K0003X");
        profile.setCompanyName("某制造有限公司");
        profile.setTaxpayerType("GENERAL");
        profile.setTaxPeriod("2026Q2");
        profile.setLateCount(3);
        profile.setMissingInvoice(1);
        profile.setTaxBurdenRate(0.03);
        profile.setIndustryAvgBurden(0.04);
        profile.setRevenue(5000000.0);
        profile.setInputTaxRatio(0.90);
        profile.setHasAbnormalInvoice(false);
        profile.setYearsSinceRegister(2);
        profile.setCreditRating("B");

        TaxRiskDecision decision = service.evaluate(profile);

        Assert.assertEquals(48, decision.getRiskScore());
        Assert.assertEquals("MEDIUM", decision.getRiskLevel());
        Assert.assertEquals("FIELD_VISIT", decision.getAuditLevel());
        Assert.assertTrue(decision.isNeedWarning());
        Assert.assertFalse(decision.isNeedInvestigation());
    }

    /**
     * 异常发票触发稽查：总分仅 20（LOW）但存在异常发票 → 仍移交稽查
     */
    @Test
    public void shouldInvestigateDueToAbnormalInvoice() {
        TaxProfile profile = new TaxProfile();
        profile.setCompanyId("91310000MA1K0004X");
        profile.setCompanyName("某服务有限公司");
        profile.setTaxpayerType("SMALL_MICRO");
        profile.setTaxPeriod("2026Q2");
        profile.setLateCount(0);
        profile.setMissingInvoice(0);
        profile.setTaxBurdenRate(0.05);
        profile.setIndustryAvgBurden(0.04);
        profile.setRevenue(1000000.0);
        profile.setInputTaxRatio(0.70);
        profile.setHasAbnormalInvoice(true);
        profile.setYearsSinceRegister(5);
        profile.setCreditRating("B");

        TaxRiskDecision decision = service.evaluate(profile);

        Assert.assertEquals(20, decision.getRiskScore());
        Assert.assertEquals("LOW", decision.getRiskLevel());
        Assert.assertEquals("TAX_AUDIT", decision.getAuditLevel());
        Assert.assertFalse(decision.isNeedWarning());
        Assert.assertTrue(decision.isNeedInvestigation());
        Assert.assertTrue(decision.getNotes().contains("存在异常发票，直接移交税务稽查"));
    }

    /**
     * 进项异常：逾期 1 次、税负率偏低、进项占比 96% → 32 分、自查
     */
    @Test
    public void shouldWatchInputTaxRatio() {
        TaxProfile profile = new TaxProfile();
        profile.setCompanyId("91310000MA1K0005X");
        profile.setCompanyName("某供应链有限公司");
        profile.setTaxpayerType("GENERAL");
        profile.setTaxPeriod("2026Q2");
        profile.setLateCount(1);
        profile.setMissingInvoice(0);
        profile.setTaxBurdenRate(0.02);
        profile.setIndustryAvgBurden(0.04);
        profile.setRevenue(6000000.0);
        profile.setInputTaxRatio(0.96);
        profile.setHasAbnormalInvoice(false);
        profile.setYearsSinceRegister(4);
        profile.setCreditRating("B");

        TaxRiskDecision decision = service.evaluate(profile);

        Assert.assertEquals(32, decision.getRiskScore());
        Assert.assertEquals("LOW", decision.getRiskLevel());
        Assert.assertEquals("SELF_CHECK", decision.getAuditLevel());
        Assert.assertFalse(decision.isNeedWarning());
        Assert.assertFalse(decision.isNeedInvestigation());
        Assert.assertTrue(decision.getNotes().contains("进项占比过高，关注虚增进项风险"));
    }

    /**
     * 新设企业：逾期 2 次、缺票 4 次、税负率极低、进项偏高、成立不足 1 年 → 72 分、稽查
     */
    @Test
    public void shouldMonitorNewlyRegisteredEnterprise() {
        TaxProfile profile = new TaxProfile();
        profile.setCompanyId("91310000MA1K0006X");
        profile.setCompanyName("某新设电商有限公司");
        profile.setTaxpayerType("SMALL_MICRO");
        profile.setTaxPeriod("2026Q2");
        profile.setLateCount(2);
        profile.setMissingInvoice(4);
        profile.setTaxBurdenRate(0.02);
        profile.setIndustryAvgBurden(0.05);
        profile.setRevenue(3000000.0);
        profile.setInputTaxRatio(0.88);
        profile.setHasAbnormalInvoice(false);
        profile.setYearsSinceRegister(0);
        profile.setCreditRating("M");

        TaxRiskDecision decision = service.evaluate(profile);

        Assert.assertEquals(72, decision.getRiskScore());
        Assert.assertEquals("HIGH", decision.getRiskLevel());
        Assert.assertEquals("TAX_AUDIT", decision.getAuditLevel());
        Assert.assertTrue(decision.isNeedWarning());
        Assert.assertTrue(decision.isNeedInvestigation());
        Assert.assertTrue(decision.getNotes().contains("新设企业，处于重点监控期"));
    }
}
