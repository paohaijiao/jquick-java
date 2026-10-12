package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.UnderwritingApplication;
import com.github.paohaijiao.demo.model.UnderwritingDecision;
import com.github.paohaijiao.demo.service.UnderwritingService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 17 生产级样板：人身险保单核保风险判定
 * <p>
 * 覆盖 6 组投保形态：优选体折扣、标准体、吸烟慢病加费、糖尿病责任除外（含降额）、
 * 未如实告知拒保、恶性肿瘤史拒保。
 */
public class UnderwritingServiceTest {

    private UnderwritingService service;

    @Before
    public void setUp() {
        InsuranceUnderwritingRules rules = JQuickJava.create()
                .createApi(InsuranceUnderwritingRules.class, "demo/insurance-underwriting.xml");
        this.service = new UnderwritingService(rules);
    }

    /**
     * 优选体：28 岁 1 类职业不吸烟无慢病，100 分，寿险保费 9 折
     */
    @Test
    public void shouldIssuePreferredWithDiscount() {
        UnderwritingApplication app = new UnderwritingApplication(
                "UW20260001", "林悦", 28, 1, "LIFE",
                1000000.0, 5000.0, 22.0, false, "NONE", true);

        UnderwritingDecision decision = service.underwrite(app);

        Assert.assertEquals(100, decision.getTotalScore());
        Assert.assertEquals("PREFERRED", decision.getResult());
        Assert.assertEquals(1000000.0, decision.getApprovedSumInsured(), 0.001);
        Assert.assertFalse(decision.isSumInsuredReduced());
        Assert.assertEquals(10, decision.getPremiumDiscountRate());
        Assert.assertEquals(4500.0, decision.getFinalPremium(), 0.001);
        Assert.assertTrue(decision.getNotes().contains("优选体承保，标准保费享 10% 折扣，省 500.0 元"));
    }

    /**
     * 标准体：42 岁 3 类职业 BMI 偏高，78 分，重疾险标准保费承保
     */
    @Test
    public void shouldIssueStandardWithoutAdjustment() {
        UnderwritingApplication app = new UnderwritingApplication(
                "UW20260002", "赵磊", 42, 3, "CRITICAL_ILLNESS",
                500000.0, 8000.0, 26.0, false, "NONE", true);

        UnderwritingDecision decision = service.underwrite(app);

        Assert.assertEquals(78, decision.getTotalScore());
        Assert.assertEquals("STANDARD", decision.getResult());
        Assert.assertEquals(0, decision.getExtraPremiumRate());
        Assert.assertEquals(8000.0, decision.getFinalPremium(), 0.001);
        Assert.assertTrue(decision.getExclusionItems().isEmpty());
    }

    /**
     * 加费承保：48 岁吸烟高血压投保人买寿险，62 分加费 25%
     */
    @Test
    public void shouldIssueLoadedWithExtraPremium() {
        UnderwritingApplication app = new UnderwritingApplication(
                "UW20260003", "孙建国", 48, 3, "LIFE",
                800000.0, 6000.0, 27.0, true, "HYPERTENSION", true);

        UnderwritingDecision decision = service.underwrite(app);

        Assert.assertEquals(62, decision.getTotalScore());
        Assert.assertEquals("LOADED", decision.getResult());
        Assert.assertEquals(25, decision.getExtraPremiumRate());
        Assert.assertEquals(1500.0, decision.getExtraPremium(), 0.001);
        Assert.assertEquals(7500.0, decision.getFinalPremium(), 0.001);
        Assert.assertTrue(decision.getNotes().contains("健康风险高于标准体，按加费 25% 承保"));
    }

    /**
     * 除外责任 + 降额：糖尿病投保医疗险，300 万保额降至 200 万，糖尿病相关责任除外
     */
    @Test
    public void shouldIssueWithExclusionAndReducedSumInsured() {
        UnderwritingApplication app = new UnderwritingApplication(
                "UW20260004", "周敏", 38, 2, "MEDICAL",
                3000000.0, 4000.0, 25.0, false, "DIABETES", true);

        UnderwritingDecision decision = service.underwrite(app);

        Assert.assertEquals(74, decision.getTotalScore());
        Assert.assertEquals("EXCLUSION", decision.getResult());
        Assert.assertTrue(decision.isSumInsuredReduced());
        Assert.assertEquals(2000000.0, decision.getApprovedSumInsured(), 0.001);
        Assert.assertEquals(0, decision.getExtraPremiumRate());
        Assert.assertEquals(4000.0, decision.getFinalPremium(), 0.001);
        Assert.assertTrue(decision.getExclusionItems().contains("糖尿病及其并发症（含血糖异常相关治疗）"));
        Assert.assertTrue(decision.getNotes().contains(
                "投保保额 3000000.0 元超出第 2 类职业/38 周岁自动核保上限 200 万元，降额至 200 万元承保"));
    }

    /**
     * 未如实健康告知：直接拒保，不进入评分
     */
    @Test
    public void shouldDeclineFalseHealthDeclaration() {
        UnderwritingApplication app = new UnderwritingApplication(
                "UW20260005", "吴海", 35, 2, "CRITICAL_ILLNESS",
                800000.0, 7000.0, 23.0, false, "DIABETES", false);

        UnderwritingDecision decision = service.underwrite(app);

        Assert.assertEquals("DECLINE", decision.getResult());
        Assert.assertEquals(0, decision.getTotalScore());
        Assert.assertTrue(decision.getDeclineReasons().contains(
                "健康告知未如实填写，依据保险法第十六条解除承保并拒赔"));
    }

    /**
     * 恶性肿瘤既往史投保重疾险：直接拒保
     */
    @Test
    public void shouldDeclineCancerHistoryForCriticalIllness() {
        UnderwritingApplication app = new UnderwritingApplication(
                "UW20260006", "郑芳", 45, 2, "CRITICAL_ILLNESS",
                600000.0, 9000.0, 24.5, false, "CANCER_HISTORY", true);

        UnderwritingDecision decision = service.underwrite(app);

        Assert.assertEquals("DECLINE", decision.getResult());
        Assert.assertTrue(decision.getDeclineReasons().contains(
                "存在恶性肿瘤既往史，重疾险不予承保"));
        Assert.assertEquals(0.0, decision.getFinalPremium(), 0.001);
    }
}
