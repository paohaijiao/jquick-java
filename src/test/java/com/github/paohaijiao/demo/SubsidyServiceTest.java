package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.SubsidyApplication;
import com.github.paohaijiao.demo.model.SubsidyDecision;
import com.github.paohaijiao.demo.service.SubsidyService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 11 生产级样板：政务人才补贴资格自动审核
 * <p>
 * 覆盖 6 组申报形态：高层次人才创业、硕士就业达标线、本科临界复核、
 * 失信一票否决、材料不齐补正、低学历创业不达标。
 */
public class SubsidyServiceTest {

    private SubsidyService service;

    @Before
    public void setUp() {
        SubsidyRules rules = JQuickJava.create()
                .createApi(SubsidyRules.class, "demo/subsidy-eligibility.xml");
        this.service = new SubsidyService(rules);
    }

    /**
     * 博士 A 类人才创业，带动就业 12 人 → 110 分，补贴 40 万
     */
    @Test
    public void shouldApproveTopTalentStartup() {
        SubsidyApplication app = baseApplication("SUB-1101");
        app.setEducation("PHD");
        app.setAge(28);
        app.setLocalSocialSecurityMonths(48);
        app.setTalentCategory("A");
        app.setStartupRegistered(true);
        app.setStartupEmployees(12);

        SubsidyDecision decision = service.review(app);

        Assert.assertEquals(110, decision.getQualificationScore());
        Assert.assertEquals("QUALIFIED", decision.getStage());
        Assert.assertEquals(400000.0, decision.getSubsidyAmount(), 0.001);
        Assert.assertEquals(100000.0, decision.getEducationAmount(), 0.001);
        Assert.assertEquals(200000.0, decision.getTalentAmount(), 0.001);
        Assert.assertEquals(100000.0, decision.getStartupAmount(), 0.001);
        Assert.assertTrue(decision.getReasons().contains("审核通过，核定补贴 400000.0 元"));
    }

    /**
     * 硕士 B 类人才稳定就业，评分恰为 75 达标线 → 通过，补贴 16 万
     */
    @Test
    public void shouldApproveAtQualificationLine() {
        SubsidyApplication app = baseApplication("SUB-1102");
        app.setEducation("MASTER");
        app.setAge(28);
        app.setLocalSocialSecurityMonths(36);
        app.setTalentCategory("B");
        app.setStartupRegistered(false);
        app.setStartupEmployees(0);

        SubsidyDecision decision = service.review(app);

        Assert.assertEquals(75, decision.getQualificationScore());
        Assert.assertEquals("QUALIFIED", decision.getStage());
        Assert.assertEquals(160000.0, decision.getSubsidyAmount(), 0.001);
        Assert.assertEquals(60000.0, decision.getEducationAmount(), 0.001);
        Assert.assertEquals(100000.0, decision.getTalentAmount(), 0.001);
        Assert.assertEquals(0.0, decision.getStartupAmount(), 0.001);
    }

    /**
     * 本科 C 类人才，评分 55 处于复核区间 → 人工复核，暂不定额
     */
    @Test
    public void shouldRouteBorderlineApplicationToManualReview() {
        SubsidyApplication app = baseApplication("SUB-1103");
        app.setEducation("BACHELOR");
        app.setAge(29);
        app.setLocalSocialSecurityMonths(24);
        app.setTalentCategory("C");
        app.setStartupRegistered(false);
        app.setStartupEmployees(0);

        SubsidyDecision decision = service.review(app);

        Assert.assertEquals(55, decision.getQualificationScore());
        Assert.assertEquals("MANUAL_REVIEW", decision.getStage());
        Assert.assertEquals(0.0, decision.getSubsidyAmount(), 0.001);
        Assert.assertTrue(decision.getReasons().contains("评分处于复核区间，转人工审核，暂不定额"));
    }

    /**
     * 硕士条件优秀但存在失信记录 → 一票否决直接驳回
     */
    @Test
    public void shouldRejectCreditViolationRegardlessOfScore() {
        SubsidyApplication app = baseApplication("SUB-1104");
        app.setEducation("MASTER");
        app.setAge(30);
        app.setLocalSocialSecurityMonths(40);
        app.setTalentCategory("B");
        app.setStartupRegistered(false);
        app.setStartupEmployees(0);
        app.setCreditViolation(true);

        SubsidyDecision decision = service.review(app);

        Assert.assertTrue(decision.getQualificationScore() >= 70);
        Assert.assertEquals("REJECTED", decision.getStage());
        Assert.assertEquals(0.0, decision.getSubsidyAmount(), 0.001);
        Assert.assertTrue(decision.getReasons().contains("存在失信被执行记录，触发一票否决"));
    }

    /**
     * 博士评分 65 但材料不齐 → 退回补正并人工复核
     */
    @Test
    public void shouldHoldApplicationForMissingDocuments() {
        SubsidyApplication app = baseApplication("SUB-1105");
        app.setEducation("PHD");
        app.setAge(30);
        app.setLocalSocialSecurityMonths(36);
        app.setTalentCategory("NONE");
        app.setStartupRegistered(false);
        app.setStartupEmployees(0);
        app.setDocumentsComplete(false);

        SubsidyDecision decision = service.review(app);

        Assert.assertEquals(65, decision.getQualificationScore());
        Assert.assertEquals("MANUAL_REVIEW", decision.getStage());
        Assert.assertEquals(0.0, decision.getSubsidyAmount(), 0.001);
        Assert.assertTrue(decision.getReasons().contains("申报材料不齐全，退回补正后转人工复核"));
    }

    /**
     * 大专学历创业带动 6 人就业，评分 36 未达标 → 驳回
     */
    @Test
    public void shouldRejectBelowStandardApplication() {
        SubsidyApplication app = baseApplication("SUB-1106");
        app.setEducation("COLLEGE");
        app.setAge(42);
        app.setLocalSocialSecurityMonths(12);
        app.setTalentCategory("NONE");
        app.setStartupRegistered(true);
        app.setStartupEmployees(6);

        SubsidyDecision decision = service.review(app);

        Assert.assertEquals(36, decision.getQualificationScore());
        Assert.assertEquals("REJECTED", decision.getStage());
        Assert.assertEquals(0.0, decision.getSubsidyAmount(), 0.001);
        Assert.assertTrue(decision.getReasons().contains("资格评分未达补贴标准，驳回申请"));
    }

    private SubsidyApplication baseApplication(String applyNo) {
        SubsidyApplication app = new SubsidyApplication();
        app.setApplyNo(applyNo);
        app.setApplicantName("申请人");
        app.setSubsidyType("SETTLEMENT");
        app.setCreditViolation(false);
        app.setCriminalRecord(false);
        app.setReceivedSimilarSubsidy(false);
        app.setDocumentsComplete(true);
        return app;
    }
}
