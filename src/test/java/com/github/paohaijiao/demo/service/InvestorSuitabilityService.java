package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.InvestorSuitabilityRules;
import com.github.paohaijiao.demo.model.InvestorAssessment;
import com.github.paohaijiao.demo.model.SuitabilityDecision;

/**
 * 场景 19：投资者适当性匹配服务
 * <p>
 * 规则侧（XML）：成熟度评分口径、等级差与确认/禁购阈值。
 * Java 侧：年龄与签署前置校验、阻断原因组装、双录要求与可购等级回填。
 */
public class InvestorSuitabilityService {

    /**
     * 高龄投资者门槛
     */
    private static final int ELDERLY_AGE = 65;

    private final InvestorSuitabilityRules rules;

    public InvestorSuitabilityService(InvestorSuitabilityRules rules) {
        this.rules = rules;
    }

    public SuitabilityDecision evaluate(InvestorAssessment assessment) {
        SuitabilityDecision decision = new SuitabilityDecision();
        decision.setInvestorId(assessment.getInvestorId());
        decision.setRiskToleranceRank(assessment.getRiskToleranceRank());
        decision.setProductRiskRank(assessment.getProductRiskRank());
        decision.setMaxAllowedRank(assessment.getRiskToleranceRank());
        decision.setRankGap(rules.rankGap(
                assessment.getRiskToleranceRank(), assessment.getProductRiskRank()));

        if (assessment.getAge() < 18) {
            block(decision, "客户未满 18 周岁，不具备金融产品购买民事行为能力");
            return decision;
        }
        if (!assessment.isSignedRiskNotice()) {
            block(decision, "未签署《" + assessment.getProductName()
                    + "》产品风险揭示书，须先完成签署与风险测评");
            return decision;
        }

        int ageScore = rules.scoreAge(assessment.getAge());
        int incomeScore = rules.scoreIncome(assessment.getAnnualIncome());
        int assetsScore = rules.scoreAssets(assessment.getNetAssets());
        int experienceScore = rules.scoreExperience(assessment.getInvestExperienceYears());
        int toleranceScore = rules.scoreTolerance(assessment.getRiskToleranceRank());
        int totalScore = ageScore + incomeScore + assetsScore + experienceScore + toleranceScore;
        if (totalScore > 100) {
            totalScore = 100;
        }

        decision.addScoreDetail("年龄成熟度评分: " + ageScore + " / 15");
        decision.addScoreDetail("收入成熟度评分: " + incomeScore + " / 15");
        decision.addScoreDetail("资产成熟度评分: " + assetsScore + " / 20");
        decision.addScoreDetail("投资经验评分: " + experienceScore + " / 20");
        decision.addScoreDetail("风险承受问卷评分: " + toleranceScore + " / 30");
        decision.addScoreDetail("汇总评分: " + totalScore);
        decision.setMaturityScore(totalScore);

        // 产品风险高于承受等级：适当性硬阻断（Java 侧硬政策，不随评分脚本调整）
        if (assessment.getProductRiskRank() > assessment.getRiskToleranceRank()) {
            decision.setDecision("BLOCK");
            decision.addReason("产品风险等级 R" + assessment.getProductRiskRank()
                    + " 高于客户风险承受等级 C" + assessment.getRiskToleranceRank()
                    + "，依据《证券期货投资者适当性管理办法》禁止向其销售");
            return decision;
        }

        boolean elderly = assessment.getAge() >= ELDERLY_AGE;
        if (rules.needConfirm(totalScore, assessment.getProductRiskRank(), elderly)) {
            decision.setDecision("CONFIRM");
            decision.setDoubleRecordingRequired(true);
            if (elderly) {
                decision.addReason("客户年满 " + ELDERLY_AGE
                        + " 周岁购买 R" + assessment.getProductRiskRank()
                        + " 中高风险产品，须在网点双录并由成年直系亲属陪同确认");
            } else {
                decision.addReason("客户投资成熟度评分 " + totalScore
                        + " 低于 50，购买 R" + assessment.getProductRiskRank()
                        + " 产品须双录并手抄特别风险提示，冷静期 24 小时后回访确认");
            }
            return decision;
        }

        decision.setDecision("ALLOW");
        return decision;
    }

    private void block(SuitabilityDecision decision, String reason) {
        decision.setDecision("BLOCK");
        decision.setMaturityScore(0);
        decision.addReason(reason);
    }
}
