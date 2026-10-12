package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.TaxComplianceRules;
import com.github.paohaijiao.demo.model.TaxProfile;
import com.github.paohaijiao.demo.model.TaxRiskDecision;

/**
 * 场景 8：税务合规风险打分服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：六维指标汇总、封顶、处置建议组装</li>
 *     <li><b>规则侧</b>：各维度扣分口径、风险分级阈值、处置路由，全部外置在 XML 中</li>
 * </ul>
 */
public class TaxComplianceService {

    private final TaxComplianceRules rules;

    public TaxComplianceService(TaxComplianceRules rules) {
        this.rules = rules;
    }

    /**
     * 评估税务合规风险
     *
     * @param profile 企业税务画像
     * @return 风险决策
     */
    public TaxRiskDecision evaluate(TaxProfile profile) {
        TaxRiskDecision decision = new TaxRiskDecision();
        decision.setCompanyId(profile.getCompanyId());

        int lateScore = rules.scoreLate(profile.getLateCount());
        int missingScore = rules.scoreMissingInvoice(profile.getMissingInvoice());
        int burdenScore = rules.scoreBurden(profile.getTaxBurdenRate(), profile.getIndustryAvgBurden());
        int inputScore = rules.scoreInputRatio(profile.getInputTaxRatio());
        int abnormalScore = rules.scoreAbnormal(profile.isHasAbnormalInvoice());
        int ageScore = rules.scoreAge(profile.getYearsSinceRegister());

        int total = lateScore + missingScore + burdenScore + inputScore + abnormalScore + ageScore;
        if (total > 100) {
            total = 100;
        } else if (total < 0) {
            total = 0;
        }

        decision.setRiskScore(total);
        decision.setRiskLevel(rules.riskLevel(total));
        decision.setAuditLevel(rules.auditLevel(total, profile.isHasAbnormalInvoice()));
        decision.setNeedWarning(rules.needWarning(total));
        decision.setNeedInvestigation(rules.needInvestigation(total, profile.isHasAbnormalInvoice()));

        decision.addIndicator("逾期申报扣分: " + lateScore);
        decision.addIndicator("缺失发票扣分: " + missingScore);
        decision.addIndicator("税负率异常扣分: " + burdenScore);
        decision.addIndicator("进项异常扣分: " + inputScore);
        decision.addIndicator("异常发票扣分: " + abnormalScore);
        decision.addIndicator("成立年限扣分: " + ageScore);
        decision.addIndicator("风险总分: " + total);

        buildNotes(profile, decision);
        return decision;
    }

    private void buildNotes(TaxProfile profile, TaxRiskDecision decision) {
        if (profile.isHasAbnormalInvoice()) {
            decision.addNote("存在异常发票，直接移交税务稽查");
        }
        if (profile.getLateCount() >= 3) {
            decision.addNote("逾期申报 " + profile.getLateCount() + " 次，需督促按期申报");
        }
        if (profile.getTaxBurdenRate() < profile.getIndustryAvgBurden() * 0.5) {
            decision.addNote("税负率显著低于行业均值，关注收入完整性");
        }
        if (profile.getInputTaxRatio() > 0.95) {
            decision.addNote("进项占比过高，关注虚增进项风险");
        }
        if (profile.getYearsSinceRegister() < 1) {
            decision.addNote("新设企业，处于重点监控期");
        }
        if ("D".equals(profile.getCreditRating())) {
            decision.addNote("纳税信用等级 D，按最高风险级别管理");
        }
        if (decision.getRiskScore() < 40) {
            decision.addNote("风险较低，纳入常规自查");
        }
    }
}
