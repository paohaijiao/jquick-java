package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.CorporateLoanRules;
import com.github.paohaijiao.demo.model.CorporateCreditDecision;
import com.github.paohaijiao.demo.model.CorporateLoanApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 场景 16：企业经营贷授信测算服务
 * <p>
 * 规则侧（XML）：六维评分口径、等级与审批阈值、纳税倍数、风险定价。
 * Java 侧：行业/失信/负债硬否决、评分汇总、额度三重封顶（纳税倍数 → 营收占比 30% →
 * 绝对上限 2000 万 → 不超申请额）、决策组装。
 */
public class CorporateLoanService {

    /**
     * 单户授信绝对上限（元）：2000 万
     */
    private static final double LIMIT_CEILING = 20000000.0;

    /**
     * 授信额度占年营收比例上限：30%
     */
    private static final double REVENUE_RATIO_CAP = 0.30;

    /**
     * 资产负债率硬性上限（%）
     */
    private static final int DEBT_RATIO_HARD_LIMIT = 85;

    private final CorporateLoanRules rules;

    public CorporateLoanService(CorporateLoanRules rules) {
        this.rules = rules;
    }

    public CorporateCreditDecision evaluate(CorporateLoanApplication app) {
        CorporateCreditDecision decision = new CorporateCreditDecision();
        decision.setApplicationId(app.getApplicationId());

        if (!passPreCheck(app, decision)) {
            return decision;
        }

        int yearsScore = rules.scoreOperatingYears(app.getOperatingYears());
        int revenueScore = rules.scoreRevenue(app.getAnnualRevenue());
        int taxScore = rules.scoreTaxCredit(app.getTaxCreditLevel(), app.getAnnualTaxAmount());
        int flowScore = rules.scoreBankFlow(app.getBankFlowAnnual(), app.getAnnualRevenue());
        int debtScore = rules.scoreDebtRatio(app.getDebtRatio());
        int creditScore = rules.scoreCreditRecord(app.getOverdueCount(), app.isHasJudicialRecord());

        int totalScore = yearsScore + revenueScore + taxScore + flowScore + debtScore + creditScore;
        if (totalScore < 0) {
            totalScore = 0;
        } else if (totalScore > 100) {
            totalScore = 100;
        }

        decision.addScoreDetail("持续经营年限评分: " + yearsScore + " / 15");
        decision.addScoreDetail("营收规模评分: " + revenueScore + " / 20");
        decision.addScoreDetail("纳税信用评分: " + taxScore + " / 20");
        decision.addScoreDetail("流水核验评分: " + flowScore + " / 15");
        decision.addScoreDetail("资产负债率评分: " + debtScore + " / 15");
        decision.addScoreDetail("征信记录评分: " + creditScore + " / 15");
        decision.addScoreDetail("汇总评分: " + totalScore);

        String rating = rules.creditRating(totalScore);
        String result = rules.approveDecision(totalScore);

        decision.setTotalScore(totalScore);
        decision.setCreditRating(rating);
        decision.setDecision(result);

        if ("REJECT".equals(result)) {
            buildRejectReasons(app, decision, totalScore);
            return decision;
        }

        decision.setInterestRate(rules.interestRate(rating));
        double multiplier = rules.taxMultiplier(app.getTaxCreditLevel(), totalScore);
        double theoretical = app.getAnnualTaxAmount() * multiplier;
        decision.setTheoreticalLimit(round(theoretical));
        decision.setApprovedLimit(applyLimitCaps(app, theoretical, decision, multiplier));
        buildWarnings(app, decision);
        return decision;
    }

    /**
     * 前置硬规则：受限行业、失信被执行、超负债、基础数据缺失直接拒绝
     */
    private boolean passPreCheck(CorporateLoanApplication app, CorporateCreditDecision decision) {
        if ("RESTRICTED".equals(app.getIndustryCategory())) {
            reject(decision, "企业属于授信受限行业，不予准入");
            return false;
        }
        if (app.isHasJudicialRecord()) {
            reject(decision, "企业存在失信被执行/重大涉诉记录，触发一票否决");
            return false;
        }
        if (app.getDebtRatio() > DEBT_RATIO_HARD_LIMIT) {
            reject(decision, "资产负债率 " + app.getDebtRatio() + "% 超过硬性上限 "
                    + DEBT_RATIO_HARD_LIMIT + "%，偿债能力不足");
            return false;
        }
        if (app.getAnnualRevenue() <= 0.0 || app.getApplyAmount() <= 0.0) {
            reject(decision, "营收或申请额度数据缺失，无法完成授信测算");
            return false;
        }
        if ("D".equals(app.getTaxCreditLevel())) {
            reject(decision, "纳税信用等级为 D，属于重点监控企业，不予准入");
            return false;
        }
        return true;
    }

    private void reject(CorporateCreditDecision decision, String reason) {
        decision.setDecision("REJECT");
        decision.setCreditRating("D");
        decision.setTotalScore(0);
        decision.addRejectReason(reason);
    }

    /**
     * 额度三重封顶，记录实际起作用的封顶项
     */
    private double applyLimitCaps(CorporateLoanApplication app, double theoretical,
                                  CorporateCreditDecision decision, double multiplier) {
        double limit = theoretical;
        decision.setCappedBy("TAX");

        double revenueCap = app.getAnnualRevenue() * REVENUE_RATIO_CAP;
        if (revenueCap < limit) {
            limit = revenueCap;
            decision.setCappedBy("REVENUE");
        }
        if (LIMIT_CEILING < limit) {
            limit = LIMIT_CEILING;
            decision.setCappedBy("CEILING");
        }
        if (app.getApplyAmount() < limit) {
            limit = app.getApplyAmount();
            decision.setCappedBy("APPLY");
        }
        if (!"TAX".equals(decision.getCappedBy()) && theoretical > limit) {
            decision.addRiskWarning("理论授信额度 " + money(theoretical)
                    + " 元（纳税额 × " + multiplier + "）受" + capName(decision.getCappedBy())
                    + "约束，核定额度 " + money(limit) + " 元");
        }
        return round(limit);
    }

    private String capName(String code) {
        if ("REVENUE".equals(code)) {
            return "营收占比上限";
        }
        if ("CEILING".equals(code)) {
            return "单户 2000 万绝对上限";
        }
        if ("APPLY".equals(code)) {
            return "申请额";
        }
        return "纳税倍数";
    }

    private void buildRejectReasons(CorporateLoanApplication app,
                                    CorporateCreditDecision decision, int totalScore) {
        if (app.getOperatingYears() < 2) {
            decision.addRejectReason("持续经营年限不足 2 年，经营稳定性无法评估");
        }
        if (app.getDebtRatio() > 70) {
            decision.addRejectReason("资产负债率 " + app.getDebtRatio() + "% 偏高");
        }
        if (app.getOverdueCount() > 2) {
            decision.addRejectReason("近 2 年逾期 " + app.getOverdueCount() + " 次，信用记录较差");
        }
        if (decision.getRejectReasons().isEmpty()) {
            decision.addRejectReason("综合评分 " + totalScore + " 低于自动审批线 60");
        }
    }

    private void buildWarnings(CorporateLoanApplication app, CorporateCreditDecision decision) {
        if (app.getDebtRatio() > 70) {
            decision.addRiskWarning("资产负债率 " + app.getDebtRatio() + "%，高于 70% 关注线");
        }
        if (app.getBankFlowAnnual() < app.getAnnualRevenue()) {
            decision.addRiskWarning("对公流水低于营业收入，需核实收入真实性与回款周期");
        }
        if (app.getOverdueCount() > 0) {
            decision.addRiskWarning("近 2 年存在 " + app.getOverdueCount() + " 次逾期记录，贷后需重点监测");
        }
        if ("MANUAL_REVIEW".equals(decision.getDecision())) {
            decision.addRiskWarning("评分处于复核区间（60-74），需补充财报与实地尽调");
        }
    }

    private double round(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /** 金额文本：固定两位小数且禁用科学计数法（27000000.0 直接拼接会变成 2.7E7） */
    private String money(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
