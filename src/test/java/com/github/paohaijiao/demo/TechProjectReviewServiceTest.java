package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.ProjectReviewDecision;
import com.github.paohaijiao.demo.model.TechProjectApplication;
import com.github.paohaijiao.demo.service.TechProjectReviewService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 20：科技项目申报材料打分初审
 * <p>
 * 覆盖 6 组项目形态：关键核心满分立项、国内先进中试答辩、实验室弱项目驳回、
 * 重大违规否决、材料不齐补正、社会发展类 80 分临界立项。
 */
public class TechProjectReviewServiceTest {

    private TechProjectReviewService service;

    @Before
    public void setUp() {
        TechProjectReviewRules rules = JQuickJava.create()
                .createApi(TechProjectReviewRules.class, "demo/tech-project-review.xml");
        this.service = new TechProjectReviewService(rules);
    }

    /**
     * 国际领先、已量产、院士团队的关键核心技术项目 → 100 分，资助 500 万
     */
    @Test
    public void shouldApproveKeyCoreProjectWithFullScore() {
        TechProjectApplication app = baseApplication("TP2026001", "KEY_CORE");
        app.setTechLevel("INTL_LEADING");
        app.setInventionPatents(6);
        app.setPilotStage("MASS");
        app.setAnnualRevenueWan(8000);
        app.setLeaderTitle("ACADEMICIAN");
        app.setPhdCount(4);
        app.setRdRatio(18);
        app.setSelfFundedRatio(70);
        app.setEmploymentIncrease(80);
        app.setEnergySavingTon(1500);

        ProjectReviewDecision decision = service.review(app);

        Assert.assertEquals(100, decision.getTotalScore());
        Assert.assertEquals("APPROVE", decision.getResult());
        Assert.assertEquals(500, decision.getGrantAmountWan());
        Assert.assertTrue(decision.getScoreDetails().contains("技术先进性: 35 / 35"));
        Assert.assertTrue(decision.getReasons().contains(
                "综合评分 100 分达到立项线，拟立项支持，核定资助额度 500 万元"));
    }

    /**
     * 国内领先、中试阶段、专家团队的高新技术项目 → 68 分，答辩复评不定额
     */
    @Test
    public void shouldRouteDomesticProjectToReview() {
        TechProjectApplication app = baseApplication("TP2026002", "HIGH_TECH");
        app.setTechLevel("DOM_LEADING");
        app.setInventionPatents(0);
        app.setPilotStage("PILOT");
        app.setAnnualRevenueWan(1200);
        app.setLeaderTitle("EXPERT");
        app.setPhdCount(1);
        app.setRdRatio(10);
        app.setSelfFundedRatio(50);
        app.setEmploymentIncrease(30);
        app.setEnergySavingTon(500);

        ProjectReviewDecision decision = service.review(app);

        Assert.assertEquals(68, decision.getTotalScore());
        Assert.assertEquals("REVIEW", decision.getResult());
        Assert.assertEquals(0, decision.getGrantAmountWan());
        Assert.assertTrue(decision.getScoreDetails().contains("团队实力: 11 / 15"));
        Assert.assertTrue(decision.getReasons().contains(
                "综合评分处于答辩复评区间（65-79 分），须组织专家答辩"));
    }

    /**
     * 技术一般、实验室阶段、普通团队 → 22 分，不予立项
     */
    @Test
    public void shouldRejectWeakLabProject() {
        TechProjectApplication app = baseApplication("TP2026003", "HIGH_TECH");
        app.setTechLevel("GENERAL");
        app.setInventionPatents(0);
        app.setPilotStage("LAB");
        app.setAnnualRevenueWan(0);
        app.setLeaderTitle("NORMAL");
        app.setPhdCount(0);
        app.setRdRatio(2);
        app.setSelfFundedRatio(15);
        app.setEmploymentIncrease(2);
        app.setEnergySavingTon(50);

        ProjectReviewDecision decision = service.review(app);

        Assert.assertEquals(22, decision.getTotalScore());
        Assert.assertEquals("REJECT", decision.getResult());
        Assert.assertEquals(0, decision.getGrantAmountWan());
        Assert.assertTrue(decision.getReasons().contains("综合评分未达立项标准（65 分），不予立项"));
    }

    /**
     * 项目本身满分但主体存在重大违法违规 → 一票否决
     */
    @Test
    public void shouldRejectMajorViolationRegardlessOfScore() {
        TechProjectApplication app = baseApplication("TP2026004", "KEY_CORE");
        app.setTechLevel("INTL_LEADING");
        app.setInventionPatents(8);
        app.setPilotStage("MASS");
        app.setAnnualRevenueWan(6000);
        app.setLeaderTitle("ACADEMICIAN");
        app.setPhdCount(5);
        app.setRdRatio(20);
        app.setSelfFundedRatio(80);
        app.setEmploymentIncrease(60);
        app.setEnergySavingTon(1200);
        app.setMajorViolation(true);

        ProjectReviewDecision decision = service.review(app);

        Assert.assertEquals("REJECT", decision.getResult());
        Assert.assertEquals(0, decision.getGrantAmountWan());
        Assert.assertTrue(decision.getReasons().contains(
                "申报主体近三年存在重大违法违规记录，不予立项"));
    }

    /**
     * 高分项目但申报材料不齐 → 补正暂存，不进入定额
     */
    @Test
    public void shouldHoldForIncompleteMaterials() {
        TechProjectApplication app = baseApplication("TP2026005", "KEY_CORE");
        app.setTechLevel("INTL_ADVANCED");
        app.setInventionPatents(3);
        app.setPilotStage("MASS");
        app.setAnnualRevenueWan(5500);
        app.setLeaderTitle("EXPERT");
        app.setPhdCount(3);
        app.setRdRatio(16);
        app.setSelfFundedRatio(65);
        app.setEmploymentIncrease(55);
        app.setEnergySavingTon(900);
        app.setMaterialsComplete(false);

        ProjectReviewDecision decision = service.review(app);

        Assert.assertEquals("MATERIAL_PENDING", decision.getResult());
        Assert.assertEquals(0, decision.getGrantAmountWan());
        Assert.assertTrue(decision.getReasons().contains(
                "申报材料不齐全，须补正后重新提交初审"));
    }

    /**
     * 社会发展类项目恰好 80 分立项线 → APPROVE，资助 50 万
     */
    @Test
    public void shouldApproveSocialDevelopmentAtExactThreshold() {
        TechProjectApplication app = baseApplication("TP2026006", "SOCIAL_DEV");
        app.setTechLevel("DOM_LEADING");
        app.setInventionPatents(2);
        app.setPilotStage("PILOT");
        app.setAnnualRevenueWan(6000);
        app.setLeaderTitle("EXPERT");
        app.setPhdCount(3);
        app.setRdRatio(16);
        app.setSelfFundedRatio(70);
        app.setEmploymentIncrease(60);
        app.setEnergySavingTon(400);

        ProjectReviewDecision decision = service.review(app);

        Assert.assertEquals(80, decision.getTotalScore());
        Assert.assertEquals("APPROVE", decision.getResult());
        Assert.assertEquals(50, decision.getGrantAmountWan());
        Assert.assertTrue(decision.getScoreDetails().contains("社会效益: 8 / 10"));
    }

    private TechProjectApplication baseApplication(String projectId, String category) {
        TechProjectApplication app = new TechProjectApplication();
        app.setProjectId(projectId);
        app.setProjectName("测试科技项目");
        app.setProjectCategory(category);
        app.setTechLevel("GENERAL");
        app.setInventionPatents(0);
        app.setPilotStage("LAB");
        app.setAnnualRevenueWan(0);
        app.setLeaderTitle("NORMAL");
        app.setPhdCount(0);
        app.setRdRatio(0);
        app.setSelfFundedRatio(0);
        app.setEmploymentIncrease(0);
        app.setEnergySavingTon(0);
        app.setMajorViolation(false);
        app.setMaterialsComplete(true);
        return app;
    }
}
