package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.InvestmentComplianceDecision;
import com.github.paohaijiao.demo.model.SoeInvestmentApplication;
import com.github.paohaijiao.demo.service.SoeInvestmentService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 22：国资项目立项投资额度合规校验
 * <p>
 * 覆盖 6 组投资形态：主业大额合规通过、多项条件性通过、房地产负面清单、
 * 非主业大额截断、自有资金红线、授权层级不足上提审批。
 */
public class SoeInvestmentServiceTest {

    private SoeInvestmentService service;

    @Before
    public void setUp() {
        SoeInvestmentRules rules = JQuickJava.create()
                .createApi(SoeInvestmentRules.class, "demo/soe-investment.xml");
        this.service = new SoeInvestmentService(rules);
    }

    /**
     * 主业 8000 万、自有 45%、负债 65%、材料齐全、授权充分、收益 8% → 合规通过
     */
    @Test
    public void shouldPassCompliantMainBusinessInvestment() {
        SoeInvestmentApplication app = baseApplication("SOE2026001", "MAIN", 8000);
        app.setSelfOwnedRatio(45);
        app.setDebtRatio(65);
        app.setBoardApprovalAmountWan(8000);
        app.setExpectedReturnRate(8);

        InvestmentComplianceDecision decision = service.review(app);

        Assert.assertEquals("COMPLIANT", decision.getResult());
        Assert.assertEquals("GROUP", decision.getRequiredLevel());
        Assert.assertEquals("GROUP", decision.getGrantedLevel());
        Assert.assertEquals(8000, decision.getCappedAmountWan());
        Assert.assertEquals(0, decision.getFindings().size());
    }

    /**
     * 主业 600 万但自有资金偏低、负债偏高、缺风评 → 附条件通过，三项整改
     */
    @Test
    public void shouldPassConditionallyWithThreeFindings() {
        SoeInvestmentApplication app = baseApplication("SOE2026002", "MAIN", 600);
        app.setSelfOwnedRatio(35);
        app.setDebtRatio(72);
        app.setBoardApprovalAmountWan(600);
        app.setHasRiskAssessment(false);
        app.setExpectedReturnRate(5);

        InvestmentComplianceDecision decision = service.review(app);

        Assert.assertEquals("CONDITIONAL", decision.getResult());
        Assert.assertEquals("EXECUTIVE", decision.getRequiredLevel());
        Assert.assertEquals(600, decision.getCappedAmountWan());
        Assert.assertTrue(decision.getFindings().contains(
                "自有资金比例偏低（低于 40%），须进一步落实资金来源"));
        Assert.assertTrue(decision.getFindings().contains(
                "资产负债率偏高（70%-80%），须同步报送负债压降方案"));
        Assert.assertTrue(decision.getFindings().contains("缺少风险评估报告，须补正后再上会"));
    }

    /**
     * 房地产类投资 5000 万 → 负面清单否决，核定出资 0
     */
    @Test
    public void shouldRejectRealEstateNegativeList() {
        SoeInvestmentApplication app = baseApplication("SOE2026003", "REAL_ESTATE", 5000);
        app.setSelfOwnedRatio(50);
        app.setDebtRatio(50);
        app.setBoardApprovalAmountWan(5000);
        app.setExpectedReturnRate(8);

        InvestmentComplianceDecision decision = service.review(app);

        Assert.assertEquals("NON_COMPLIANT", decision.getResult());
        Assert.assertEquals("GROUP", decision.getRequiredLevel());
        Assert.assertEquals(0, decision.getCappedAmountWan());
        Assert.assertTrue(decision.getFindings().contains(
                "投向金融类、房地产类负面清单领域，不符合国有资本投资方向"));
    }

    /**
     * 非主业 6000 万 → 超专项报批红线否决，出资额截断至 3000 万
     */
    @Test
    public void shouldRejectLargeNonMainInvestmentAndCapAt3000() {
        SoeInvestmentApplication app = baseApplication("SOE2026004", "NON_MAIN", 6000);
        app.setSelfOwnedRatio(50);
        app.setDebtRatio(50);
        app.setBoardApprovalAmountWan(6000);
        app.setExpectedReturnRate(6);

        InvestmentComplianceDecision decision = service.review(app);

        Assert.assertEquals("NON_COMPLIANT", decision.getResult());
        Assert.assertEquals(3000, decision.getCappedAmountWan());
        Assert.assertTrue(decision.getFindings().contains(
                "非主业单笔投资 5000 万元以上须专项报批，本次不予通过"));
    }

    /**
     * 主业 2000 万但自有资金仅 25% → 触碰 30% 红线不予通过
     */
    @Test
    public void shouldRejectForSelfOwnedRatioBelowRedLine() {
        SoeInvestmentApplication app = baseApplication("SOE2026005", "MAIN", 2000);
        app.setSelfOwnedRatio(25);
        app.setDebtRatio(50);
        app.setBoardApprovalAmountWan(2000);
        app.setExpectedReturnRate(6);

        InvestmentComplianceDecision decision = service.review(app);

        Assert.assertEquals("NON_COMPLIANT", decision.getResult());
        Assert.assertEquals("BOARD", decision.getRequiredLevel());
        Assert.assertEquals(2000, decision.getCappedAmountWan());
        Assert.assertTrue(decision.getFindings().contains(
                "自有资金比例 25% 低于 30% 监管红线"));
    }

    /**
     * 申报 4000 万仅获经营层 500 万授权 → 须报公司董事会，核定出资截断至 500 万
     */
    @Test
    public void shouldRequireHigherApprovalLevel() {
        SoeInvestmentApplication app = baseApplication("SOE2026006", "MAIN", 4000);
        app.setSelfOwnedRatio(50);
        app.setDebtRatio(60);
        app.setBoardApprovalAmountWan(500);
        app.setExpectedReturnRate(6);

        InvestmentComplianceDecision decision = service.review(app);

        Assert.assertEquals("CONDITIONAL", decision.getResult());
        Assert.assertEquals("BOARD", decision.getRequiredLevel());
        Assert.assertEquals("EXECUTIVE", decision.getGrantedLevel());
        Assert.assertEquals(500, decision.getCappedAmountWan());
        Assert.assertTrue(decision.getFindings().contains(
                "现有授权层级（经营层）不足，须报公司董事会履行审批程序"));
        Assert.assertTrue(decision.getFindings().contains(
                "出资额受授权额度与投向管控约束，核定出资 500 万元"));
    }

    private SoeInvestmentApplication baseApplication(String id, String type, int amount) {
        SoeInvestmentApplication app = new SoeInvestmentApplication();
        app.setProjectId(id);
        app.setProjectName("测试投资项目");
        app.setProjectType(type);
        app.setTotalInvestmentWan(amount);
        app.setSelfOwnedRatio(50);
        app.setDebtRatio(60);
        app.setBoardApprovalAmountWan(amount);
        app.setHasFeasibilityReport(true);
        app.setHasRiskAssessment(true);
        app.setExpectedReturnRate(6);
        return app;
    }
}
