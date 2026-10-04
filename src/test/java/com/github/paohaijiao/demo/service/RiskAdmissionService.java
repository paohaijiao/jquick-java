package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.RiskAdmissionRules;
import com.github.paohaijiao.demo.model.AdmissionDecision;
import com.github.paohaijiao.demo.model.CreditApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 场景 1：信贷风控准入服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：进件校验、维度汇总、额度封顶、决策组装、拒绝原因与风险提示</li>
 *     <li><b>规则侧</b>：评分口径、阈值、等级映射、定价，全部外置在 XML 中，可热更新</li>
 * </ul>
 * 规则方法只接受标量参数（int / double / boolean / String），复杂对象由本层拆解后传入。
 */
public class RiskAdmissionService {

    /**
     * 单笔授信额度上限（元）
     */
    private static final double LIMIT_CAP = 500000.0;

    /**
     * 准入政策年龄下限
     */
    private static final int MIN_AGE = 22;

    /**
     * 准入政策年龄上限
     */
    private static final int MAX_AGE = 60;

    private final RiskAdmissionRules rules;

    public RiskAdmissionService(RiskAdmissionRules rules) {
        this.rules = rules;
    }

    /**
     * 执行授信审批
     *
     * @param application 进件要素
     * @return 审批决策结果
     */
    public AdmissionDecision evaluate(CreditApplication application) {
        AdmissionDecision decision = new AdmissionDecision();
        decision.setApplicationId(application.getApplicationId());

        if (!passPreCheck(application, decision)) {
            return decision;
        }

        int debtScore = rules.scoreDebtRatio(application.getMonthlyIncome(), application.getMonthlyDebt());
        int overdueScore = rules.scoreOverdue(application.getOverdueCount(), application.getMaxOverdueDays());
        int employmentScore = rules.scoreEmployment(application.getEmployedMonths(), application.getCompanyType());
        int queryScore = rules.scoreCreditQuery(application.getCreditQueryCount3M());
        int collateralScore = rules.scoreCollateral(application.getCollateralType(),
                application.getCollateralValue(), application.getLoanAmount());

        int totalScore = debtScore + overdueScore + employmentScore + queryScore + collateralScore;
        if (totalScore < 0) {
            totalScore = 0;
        } else if (totalScore > 100) {
            totalScore = 100;
        }

        decision.addScoreDetail("负债收入比评分: " + debtScore + " / 25");
        decision.addScoreDetail("历史逾期评分: " + overdueScore + " / 30");
        decision.addScoreDetail("职业稳定性评分: " + employmentScore + " / 15");
        decision.addScoreDetail("征信查询评分: " + queryScore + " / 10");
        decision.addScoreDetail("抵押物评分: " + collateralScore + " / 20");
        decision.addScoreDetail("汇总评分: " + totalScore);

        String riskLevel = rules.riskLevel(totalScore);
        String admit = rules.admit(totalScore, application.isHasJudicialRecord());

        if ("PASS".equals(admit) && rules.needManualReview(totalScore, application.getCreditQueryCount3M())) {
            admit = "MANUAL_REVIEW";
            decision.addRiskWarning("征信查询次数超出自动审批阈值，转人工复核");
        }

        decision.setTotalScore(totalScore);
        decision.setRiskLevel(riskLevel);
        decision.setAdmit(admit);

        if ("REJECT".equals(admit)) {
            buildRejectReasons(application, decision, totalScore, debtScore, overdueScore);
            return decision;
        }

        double limit = rules.suggestLimit(application.getMonthlyIncome(), totalScore);
        decision.setSuggestedLimit(capLimit(limit, application.getLoanAmount()));
        decision.setInterestRate(rules.interestRate(riskLevel));
        buildRiskWarnings(application, decision);
        return decision;
    }

    /**
     * 前置硬规则校验：不满足则直接拒绝，不进入评分模型
     */
    private boolean passPreCheck(CreditApplication application, AdmissionDecision decision) {
        if (application.getAge() < MIN_AGE || application.getAge() > MAX_AGE) {
            reject(decision, "年龄不符合准入政策（" + MIN_AGE + "-" + MAX_AGE + " 周岁）");
            return false;
        }
        if (application.getMonthlyIncome() <= 0.0) {
            reject(decision, "收入信息缺失或无效，无法完成授信评估");
            return false;
        }
        if (application.getLoanAmount() <= 0.0) {
            reject(decision, "申请金额无效");
            return false;
        }
        return true;
    }

    private void reject(AdmissionDecision decision, String reason) {
        decision.setAdmit("REJECT");
        decision.setRiskLevel("D");
        decision.setTotalScore(0);
        decision.setSuggestedLimit(0.0);
        decision.setInterestRate(0.0);
        decision.addRejectReason(reason);
    }

    private void buildRejectReasons(CreditApplication application, AdmissionDecision decision,
                                    int totalScore, int debtScore, int overdueScore) {
        if (application.isHasJudicialRecord()) {
            decision.addRejectReason("存在涉诉/被执行记录，触发一票否决");
        }
        if (debtScore == 0) {
            decision.addRejectReason("负债收入比过高，超出可承受范围");
        }
        if (overdueScore <= 5) {
            decision.addRejectReason("历史逾期严重，最长逾期天数 " + application.getMaxOverdueDays() + " 天");
        }
        if (decision.getRejectReasons().isEmpty()) {
            decision.addRejectReason("综合评分不足，总分 " + totalScore + " 低于准入线 55");
        }
    }

    private void buildRiskWarnings(CreditApplication application, AdmissionDecision decision) {
        BigDecimal income = BigDecimal.valueOf(application.getMonthlyIncome());
        BigDecimal debt = BigDecimal.valueOf(application.getMonthlyDebt());
        double debtRatio = debt.divide(income, 4, RoundingMode.HALF_UP).doubleValue();
        if (debtRatio > 0.5) {
            decision.addRiskWarning("负债收入比 " + String.format("%.2f", debtRatio) + " 超过 50% 预警线");
        }
        if (application.getCreditQueryCount3M() > 5) {
            decision.addRiskWarning("近 3 个月征信查询 " + application.getCreditQueryCount3M() + " 次，存在多头借贷迹象");
        }
        if (application.getSocialSecurityMonths() < 6) {
            decision.addRiskWarning("社保连续缴纳不足 6 个月，收入稳定性待核实");
        }
        if ("NONE".equals(application.getCollateralType())) {
            decision.addRiskWarning("无抵押物，按纯信用方式授信");
        }
        if ("TIER_3".equals(application.getCityTier())) {
            decision.addRiskWarning("三线及以下城市进件，区域集中度需关注");
        }
    }

    private double capLimit(double limit, double loanAmount) {
        double result = limit;
        if (result > LIMIT_CAP) {
            result = LIMIT_CAP;
        }
        if (result > loanAmount) {
            result = loanAmount;
        }
        return BigDecimal.valueOf(result).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
