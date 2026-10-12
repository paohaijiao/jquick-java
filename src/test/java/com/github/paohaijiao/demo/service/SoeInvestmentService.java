package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.SoeInvestmentRules;
import com.github.paohaijiao.demo.model.InvestmentComplianceDecision;
import com.github.paohaijiao.demo.model.SoeInvestmentApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 场景 22：国资项目立项投资额度合规校验服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：负面清单、资金红线、材料完备性、审批层级高低等合规项核验</li>
 *     <li><b>规则侧</b>：审批层级的额度阈值、出资额分段截断口径，外置在 XML 中</li>
 * </ul>
 */
public class SoeInvestmentService {

    private final SoeInvestmentRules rules;

    public SoeInvestmentService(SoeInvestmentRules rules) {
        this.rules = rules;
    }

    public InvestmentComplianceDecision review(SoeInvestmentApplication app) {
        InvestmentComplianceDecision decision = new InvestmentComplianceDecision();
        decision.setProjectId(app.getProjectId());

        String requiredLevel = rules.approvalLevel(app.getTotalInvestmentWan());
        String grantedLevel = rules.approvalLevel(app.getBoardApprovalAmountWan());
        decision.setRequiredLevel(requiredLevel);
        decision.setGrantedLevel(grantedLevel);

        int capped = rules.cappedApprovedWan(app.getTotalInvestmentWan(),
                app.getBoardApprovalAmountWan(), app.getProjectType());
        decision.setCappedAmountWan(capped);

        List<String> blocking = new ArrayList<String>();
        List<String> conditional = new ArrayList<String>();

        if ("FINANCIAL".equals(app.getProjectType()) || "REAL_ESTATE".equals(app.getProjectType())) {
            blocking.add("投向金融类、房地产类负面清单领域，不符合国有资本投资方向");
        }
        if ("NON_MAIN".equals(app.getProjectType()) && app.getTotalInvestmentWan() >= 5000) {
            blocking.add("非主业单笔投资 5000 万元以上须专项报批，本次不予通过");
        }
        if (app.getSelfOwnedRatio() < 30) {
            blocking.add("自有资金比例 " + app.getSelfOwnedRatio() + "% 低于 30% 监管红线");
        }
        if (app.getDebtRatio() > 80) {
            blocking.add("资产负债率 " + app.getDebtRatio() + "% 超过 80% 管控红线");
        }

        if ("NON_MAIN".equals(app.getProjectType()) && app.getTotalInvestmentWan() < 5000) {
            conditional.add("非主业投资须出具专项论证报告并说明协同效应");
        }
        if (app.getSelfOwnedRatio() >= 30 && app.getSelfOwnedRatio() < 40) {
            conditional.add("自有资金比例偏低（低于 40%），须进一步落实资金来源");
        }
        if (app.getDebtRatio() > 70 && app.getDebtRatio() <= 80) {
            conditional.add("资产负债率偏高（70%-80%），须同步报送负债压降方案");
        }
        if (!app.isHasFeasibilityReport()) {
            conditional.add("缺少可行性研究报告，须补正后再上会");
        }
        if (!app.isHasRiskAssessment()) {
            conditional.add("缺少风险评估报告，须补正后再上会");
        }
        if (app.getExpectedReturnRate() < 3) {
            conditional.add("预期收益率 " + app.getExpectedReturnRate()
                    + "% 低于 3% 资金成本基准，须重新测算");
        }
        if (levelRank(grantedLevel) < levelRank(requiredLevel)) {
            conditional.add("现有授权层级（" + levelName(grantedLevel) + "）不足，须报"
                    + levelName(requiredLevel) + "履行审批程序");
        }
        if (blocking.isEmpty() && capped < app.getTotalInvestmentWan()) {
            conditional.add("出资额受授权额度与投向管控约束，核定出资 " + capped + " 万元");
        }

        for (String finding : blocking) {
            decision.addFinding(finding);
        }
        for (String finding : conditional) {
            decision.addFinding(finding);
        }

        if (!blocking.isEmpty()) {
            decision.setResult("NON_COMPLIANT");
        } else if (!conditional.isEmpty()) {
            decision.setResult("CONDITIONAL");
        } else {
            decision.setResult("COMPLIANT");
        }
        return decision;
    }

    private int levelRank(String level) {
        if ("SASAC".equals(level)) {
            return 4;
        }
        if ("GROUP".equals(level)) {
            return 3;
        }
        if ("BOARD".equals(level)) {
            return 2;
        }
        if ("EXECUTIVE".equals(level)) {
            return 1;
        }
        return 0;
    }

    private String levelName(String level) {
        if ("SASAC".equals(level)) {
            return "国资监管机构";
        }
        if ("GROUP".equals(level)) {
            return "集团董事会";
        }
        if ("BOARD".equals(level)) {
            return "公司董事会";
        }
        if ("EXECUTIVE".equals(level)) {
            return "经营层";
        }
        return "内部决策机构";
    }
}
