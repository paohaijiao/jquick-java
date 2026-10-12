package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.InvestorAssessment;
import com.github.paohaijiao.demo.model.SuitabilityDecision;
import com.github.paohaijiao.demo.service.InvestorSuitabilityService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 19 生产级样板：理财客户风险等级适当性匹配
 * <p>
 * 覆盖 6 组客户形态：C5 高净值购 R4、C2 保守购 R1 存款、C2 越级购 R3 禁购、
 * 高龄客户购 R3 双录确认、未签揭示书阻断、低成熟度客户购 R4 冷静期确认。
 */
public class InvestorSuitabilityServiceTest {

    private InvestorSuitabilityService service;

    @Before
    public void setUp() {
        InvestorSuitabilityRules rules = JQuickJava.create()
                .createApi(InvestorSuitabilityRules.class, "demo/investor-suitability.xml");
        this.service = new InvestorSuitabilityService(rules);
    }

    /**
     * C5 高净值客户购买 R4 偏股型基金：100 分直接放行
     */
    @Test
    public void shouldAllowSophisticatedInvestor() {
        InvestorAssessment assessment = new InvestorAssessment(
                "INV001", "高净值客户韩冰", 45, 800000.0, 5000000.0, 12,
                5, 4, "沪深300指数增强基金", true);

        SuitabilityDecision decision = service.evaluate(assessment);

        Assert.assertEquals(100, decision.getMaturityScore());
        Assert.assertEquals("ALLOW", decision.getDecision());
        Assert.assertEquals(-1, decision.getRankGap());
        Assert.assertFalse(decision.isDoubleRecordingRequired());
    }

    /**
     * C2 保守型年轻客户购买 R1 大额存单：成熟度虽低但 R1 不触发确认，直接放行
     */
    @Test
    public void shouldAllowConservativeInvestorForDeposit() {
        InvestorAssessment assessment = new InvestorAssessment(
                "INV002", "职场新人许晴", 30, 120000.0, 150000.0, 1,
                2, 1, "三年期大额存单", true);

        SuitabilityDecision decision = service.evaluate(assessment);

        Assert.assertEquals(43, decision.getMaturityScore());
        Assert.assertEquals("ALLOW", decision.getDecision());
        Assert.assertEquals(2, decision.getMaxAllowedRank());
        Assert.assertTrue(decision.getReasons().isEmpty());
    }

    /**
     * C2 客户越级购买 R3 混合基金：适当性硬阻断
     */
    @Test
    public void shouldBlockMismatchedRiskProduct() {
        InvestorAssessment assessment = new InvestorAssessment(
                "INV003", "保守客户冯鑫", 35, 150000.0, 200000.0, 3,
                2, 3, "平衡型混合基金", true);

        SuitabilityDecision decision = service.evaluate(assessment);

        Assert.assertEquals("BLOCK", decision.getDecision());
        Assert.assertEquals(1, decision.getRankGap());
        Assert.assertTrue(decision.getReasons().contains(
                "产品风险等级 R3 高于客户风险承受等级 C2，依据《证券期货投资者适当性管理办法》禁止向其销售"));
    }

    /**
     * 68 岁 C3 客户购买 R3 债券基金：可购但必须网点双录、家属陪同
     */
    @Test
    public void shouldRequireDoubleRecordingForElderly() {
        InvestorAssessment assessment = new InvestorAssessment(
                "INV004", "老年客户沈奶奶", 68, 300000.0, 2000000.0, 8,
                3, 3, "中短债基金", true);

        SuitabilityDecision decision = service.evaluate(assessment);

        Assert.assertEquals(63, decision.getMaturityScore());
        Assert.assertEquals("CONFIRM", decision.getDecision());
        Assert.assertTrue(decision.isDoubleRecordingRequired());
        Assert.assertTrue(decision.getReasons().contains(
                "客户年满 65 周岁购买 R3 中高风险产品，须在网点双录并由成年直系亲属陪同确认"));
    }

    /**
     * 未签署风险揭示书：前置阻断
     */
    @Test
    public void shouldBlockWithoutSignedRiskNotice() {
        InvestorAssessment assessment = new InvestorAssessment(
                "INV005", "未签揭示书客户", 40, 600000.0, 3000000.0, 10,
                4, 4, "私募股权投资基金", false);

        SuitabilityDecision decision = service.evaluate(assessment);

        Assert.assertEquals("BLOCK", decision.getDecision());
        Assert.assertTrue(decision.getReasons().contains(
                "未签署《私募股权投资基金》产品风险揭示书，须先完成签署与风险测评"));
    }

    /**
     * 低成熟度 C4 年轻客户购买 R4 产品：评分 43，双录 + 手抄 + 24 小时冷静期
     */
    @Test
    public void shouldConfirmLowMaturityInvestor() {
        InvestorAssessment assessment = new InvestorAssessment(
                "INV006", "新手投资者蒋帆", 23, 80000.0, 80000.0, 1,
                4, 4, "行业主题股票基金", true);

        SuitabilityDecision decision = service.evaluate(assessment);

        Assert.assertEquals(43, decision.getMaturityScore());
        Assert.assertEquals("CONFIRM", decision.getDecision());
        Assert.assertTrue(decision.isDoubleRecordingRequired());
        Assert.assertTrue(decision.getReasons().contains(
                "客户投资成熟度评分 43 低于 50，购买 R4 产品须双录并手抄特别风险提示，冷静期 24 小时后回访确认"));
    }
}
