package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.LicenseReviewApplication;
import com.github.paohaijiao.demo.model.LicenseReviewDecision;
import com.github.paohaijiao.demo.service.LicenseAnnualReviewService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 24：食品经营许可证年审合规校验
 * <p>
 * 覆盖 6 组主体形态：标杆门店满分通过、轻微问题附条件、问题门店不予通过、
 * 重大违规红线、证照失效红线、85 分临界通过。
 */
public class LicenseAnnualReviewServiceTest {

    private LicenseAnnualReviewService service;

    @Before
    public void setUp() {
        LicenseAnnualReviewRules rules = JQuickJava.create()
                .createApi(LicenseAnnualReviewRules.class, "demo/license-annual-review.xml");
        this.service = new LicenseAnnualReviewService(rules);
    }

    /**
     * 各项全合规标杆门店 → 100 分通过
     */
    @Test
    public void shouldPassModelStoreWithFullScore() {
        LicenseReviewApplication app = baseApplication("LIC2026001");
        app.setHealthCertificateExpired(0);
        app.setHealthCertificateTotal(10);
        app.setTrainingRate(100);
        app.setFoodSafetyOfficer(true);
        app.setDefectCount(0);
        app.setRectificationCompleted(true);
        app.setComplaintCount(0);
        app.setEnvironmentalRating("A");
        app.setLiabilityInsurance(true);

        LicenseReviewDecision decision = service.review(app);

        Assert.assertEquals(100, decision.getTotalScore());
        Assert.assertEquals("PASS", decision.getResult());
        Assert.assertEquals(0, decision.getNonConformities().size());
        Assert.assertTrue(decision.getScoreDetails().contains("健康证管理: 20 / 20"));
    }

    /**
     * 个别健康证过期、2 项缺陷、2 件投诉、B 级环境、未投保 → 71 分附条件通过
     */
    @Test
    public void shouldPassConditionallyWithMinorIssues() {
        LicenseReviewApplication app = baseApplication("LIC2026002");
        app.setHealthCertificateExpired(1);
        app.setHealthCertificateTotal(20);
        app.setTrainingRate(85);
        app.setFoodSafetyOfficer(true);
        app.setDefectCount(2);
        app.setRectificationCompleted(true);
        app.setComplaintCount(2);
        app.setEnvironmentalRating("B");
        app.setLiabilityInsurance(false);

        LicenseReviewDecision decision = service.review(app);

        Assert.assertEquals(71, decision.getTotalScore());
        Assert.assertEquals("CONDITIONAL", decision.getResult());
        Assert.assertTrue(decision.getScoreDetails().contains("健康证管理: 12 / 20"));
        Assert.assertTrue(decision.getNonConformities().contains(
                "日常监督检查发现 2 项一般不符合项，须逐项整改"));
        Assert.assertTrue(decision.getNonConformities().contains(
                "建议投保食品安全责任保险，完善风险分担机制"));
    }

    /**
     * 健康证大面积过期、无食安员、缺陷多、未整改、投诉多、C 级环境 → 7 分不予通过
     */
    @Test
    public void shouldFailStoreWithSeriousNonConformities() {
        LicenseReviewApplication app = baseApplication("LIC2026003");
        app.setHealthCertificateExpired(8);
        app.setHealthCertificateTotal(20);
        app.setTrainingRate(50);
        app.setFoodSafetyOfficer(false);
        app.setDefectCount(7);
        app.setRectificationCompleted(false);
        app.setComplaintCount(12);
        app.setEnvironmentalRating("C");
        app.setLiabilityInsurance(false);

        LicenseReviewDecision decision = service.review(app);

        Assert.assertEquals(7, decision.getTotalScore());
        Assert.assertEquals("FAIL", decision.getResult());
        Assert.assertTrue(decision.getNonConformities().contains(
                "环境卫生量化等级为 C 级，须停业整顿后复查"));
        Assert.assertTrue(decision.getNonConformities().contains(
                "未按规定配备食品安全总监或食品安全员"));
        Assert.assertTrue(decision.getScoreDetails().contains("食安培训: 2 / 15"));
    }

    /**
     * 其余全合规但年度内发生重大违规 → 红线直接驳回
     */
    @Test
    public void shouldRejectForMajorViolation() {
        LicenseReviewApplication app = baseApplication("LIC2026004");
        app.setHealthCertificateExpired(0);
        app.setHealthCertificateTotal(8);
        app.setTrainingRate(100);
        app.setFoodSafetyOfficer(true);
        app.setDefectCount(0);
        app.setRectificationCompleted(true);
        app.setComplaintCount(0);
        app.setEnvironmentalRating("A");
        app.setLiabilityInsurance(true);
        app.setMajorViolationCount(1);

        LicenseReviewDecision decision = service.review(app);

        Assert.assertEquals("REJECT", decision.getResult());
        Assert.assertTrue(decision.getNonConformities().contains(
                "年度内存在 1 次重大食品安全违法违规记录，不予年审"));
    }

    /**
     * 许可证过期、营业执照失效分别驳回
     */
    @Test
    public void shouldRejectForInvalidLicenseAndBusinessLicense() {
        LicenseReviewApplication expired = baseApplication("LIC2026005");
        expired.setValidWithinPeriod(false);
        LicenseReviewDecision d1 = service.review(expired);
        Assert.assertEquals("REJECT", d1.getResult());
        Assert.assertTrue(d1.getNonConformities().contains("许可证已超过有效期，须重新申领后再经营"));

        LicenseReviewApplication revoked = baseApplication("LIC2026006");
        revoked.setBusinessLicenseValid(false);
        LicenseReviewDecision d2 = service.review(revoked);
        Assert.assertEquals("REJECT", d2.getResult());
        Assert.assertTrue(d2.getNonConformities().contains("营业执照失效或被吊销，不予年审"));
    }

    /**
     * 恰好 85 分通过线：投诉 5 件、B 级环境但其他项满分 → PASS，不再列不合规项
     */
    @Test
    public void shouldPassAtExactScoreThreshold() {
        LicenseReviewApplication app = baseApplication("LIC2026007");
        app.setHealthCertificateExpired(0);
        app.setHealthCertificateTotal(12);
        app.setTrainingRate(80);
        app.setFoodSafetyOfficer(true);
        app.setDefectCount(0);
        app.setRectificationCompleted(true);
        app.setComplaintCount(5);
        app.setEnvironmentalRating("B");
        app.setLiabilityInsurance(true);

        LicenseReviewDecision decision = service.review(app);

        Assert.assertEquals(85, decision.getTotalScore());
        Assert.assertEquals("PASS", decision.getResult());
        Assert.assertEquals(0, decision.getNonConformities().size());
        Assert.assertTrue(decision.getScoreDetails().contains("投诉处置: 2 / 10"));
    }

    private LicenseReviewApplication baseApplication(String licenseId) {
        LicenseReviewApplication app = new LicenseReviewApplication();
        app.setLicenseId(licenseId);
        app.setEnterpriseName("测试餐饮单位");
        app.setValidWithinPeriod(true);
        app.setBusinessLicenseValid(true);
        app.setHealthCertificateExpired(0);
        app.setHealthCertificateTotal(10);
        app.setTrainingRate(100);
        app.setFoodSafetyOfficer(true);
        app.setDefectCount(0);
        app.setRectificationCompleted(true);
        app.setComplaintCount(0);
        app.setEnvironmentalRating("A");
        app.setLiabilityInsurance(true);
        app.setMajorViolationCount(0);
        return app;
    }
}
