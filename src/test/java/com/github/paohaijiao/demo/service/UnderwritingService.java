package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.InsuranceUnderwritingRules;
import com.github.paohaijiao.demo.model.UnderwritingApplication;
import com.github.paohaijiao.demo.model.UnderwritingDecision;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 场景 17：保单核保服务
 * <p>
 * 规则侧（XML）：五维健康评分、核保结论分档、加费/折扣比例、职业年龄保额上限。
 * Java 侧：未如实告知/恶性肿瘤史/超龄/高危职业硬拒保、降额承保、除外责任与最终保费组装。
 */
public class UnderwritingService {

    /**
     * 自动核保年龄上限
     */
    private static final int MAX_AGE = 65;

    /**
     * 高危职业类别
     */
    private static final int HIGH_RISK_OCCUPATION = 6;

    private final InsuranceUnderwritingRules rules;

    public UnderwritingService(InsuranceUnderwritingRules rules) {
        this.rules = rules;
    }

    public UnderwritingDecision underwrite(UnderwritingApplication app) {
        UnderwritingDecision decision = new UnderwritingDecision();
        decision.setPolicyNo(app.getPolicyNo());

        if (!passHardRules(app, decision)) {
            return decision;
        }

        int ageScore = rules.scoreAge(app.getAge());
        int occupationScore = rules.scoreOccupation(app.getOccupationClass());
        int bmiScore = rules.scoreBmi(app.getBmi());
        int smokerScore = rules.scoreSmoker(app.isSmoker());
        int chronicScore = rules.scoreChronic(app.getChronicDisease());
        int totalScore = ageScore + occupationScore + bmiScore + smokerScore + chronicScore;

        decision.addScoreDetail("年龄风险评分: " + ageScore + " / 25");
        decision.addScoreDetail("职业类别评分: " + occupationScore + " / 25");
        decision.addScoreDetail("BMI 评分: " + bmiScore + " / 20");
        decision.addScoreDetail("吸烟评分: " + smokerScore + " / 10");
        decision.addScoreDetail("慢性病史评分: " + chronicScore + " / 20");
        decision.addScoreDetail("汇总评分: " + totalScore);
        decision.setTotalScore(totalScore);

        String result = rules.underwritingResult(totalScore, app.getChronicDisease(), app.getProductType());
        decision.setResult(result);

        applySumInsuredCap(app, decision);
        applyPremium(app, decision, result);

        if ("EXCLUSION".equals(result)) {
            buildExclusions(app, decision);
        } else if ("POSTPONE".equals(result)) {
            decision.addNote("健康评分低于 55，延期 6-12 个月观察并要求提供体检/复查报告后再申请");
        } else if ("LOADED".equals(result)) {
            decision.addNote("健康风险高于标准体，按加费 " + decision.getExtraPremiumRate() + "% 承保");
        }
        return decision;
    }

    /**
     * 硬否决规则：未如实告知、恶性肿瘤史投保重疾/医疗、超龄、高危职业
     */
    private boolean passHardRules(UnderwritingApplication app, UnderwritingDecision decision) {
        if (!app.isHealthDeclarationPass()) {
            decline(decision, "健康告知未如实填写，依据保险法第十六条解除承保并拒赔");
            return false;
        }
        if ("CANCER_HISTORY".equals(app.getChronicDisease())
                && ("CRITICAL_ILLNESS".equals(app.getProductType())
                || "MEDICAL".equals(app.getProductType())
                || "LIFE".equals(app.getProductType()))) {
            decline(decision, "存在恶性肿瘤既往史，" + productName(app.getProductType()) + "不予承保");
            return false;
        }
        if (app.getAge() > MAX_AGE) {
            decline(decision, "年龄 " + app.getAge() + " 周岁超过自动核保上限 " + MAX_AGE + " 周岁");
            return false;
        }
        if (app.getOccupationClass() >= HIGH_RISK_OCCUPATION) {
            decline(decision, "职业类别为第 " + app.getOccupationClass()
                    + " 类高危职业，普通险种不予承保，建议转投专属高危职业意外险");
            return false;
        }
        return true;
    }

    private void decline(UnderwritingDecision decision, String reason) {
        decision.setResult("DECLINE");
        decision.addDeclineReason(reason);
    }

    /**
     * 保额超过职业/年龄上限时降额承保
     */
    private void applySumInsuredCap(UnderwritingApplication app, UnderwritingDecision decision) {
        int maxWan = rules.maxSumInsuredWan(app.getOccupationClass(), app.getAge());
        double maxAmount = maxWan * 10000.0;
        if (app.getSumInsured() > maxAmount) {
            decision.setApprovedSumInsured(round(maxAmount));
            decision.setSumInsuredReduced(true);
            decision.addNote("投保保额 " + round(app.getSumInsured()) + " 元超出第 "
                    + app.getOccupationClass() + " 类职业/" + app.getAge()
                    + " 周岁自动核保上限 " + maxWan + " 万元，降额至 " + maxWan + " 万元承保");
        } else {
            decision.setApprovedSumInsured(round(app.getSumInsured()));
        }
    }

    /**
     * 保费调整：加费件上浮、优选体打折、其余按标准保费
     */
    private void applyPremium(UnderwritingApplication app, UnderwritingDecision decision, String result) {
        int extraRate = rules.extraPremiumRate(result);
        int discountRate = rules.premiumDiscountRate(result);
        decision.setExtraPremiumRate(extraRate);
        decision.setPremiumDiscountRate(discountRate);

        double extra = app.getPremiumAmount() * extraRate / 100.0;
        double discount = app.getPremiumAmount() * discountRate / 100.0;
        decision.setExtraPremium(round(extra));
        double finalPremium = app.getPremiumAmount() + extra - discount;
        decision.setFinalPremium(round(finalPremium));

        if (discountRate > 0) {
            decision.addNote("优选体承保，标准保费享 " + discountRate + "% 折扣，省 " + round(discount) + " 元");
        }
    }

    private void buildExclusions(UnderwritingApplication app, UnderwritingDecision decision) {
        if ("DIABETES".equals(app.getChronicDisease())) {
            decision.addExclusionItem("糖尿病及其并发症（含血糖异常相关治疗）");
            decision.addExclusionItem("糖尿病继发心脑血管、眼底、肾脏病变");
        }
        if ("HYPERTENSION".equals(app.getChronicDisease())) {
            decision.addExclusionItem("高血压及其并发症（含心脑血管、肾脏疾病）");
        }
        decision.addNote("已就既往症做责任除外，其余保险责任正常承保，保费不加费");
    }

    private String productName(String productType) {
        if ("LIFE".equals(productType)) {
            return "寿险";
        }
        if ("CRITICAL_ILLNESS".equals(productType)) {
            return "重疾险";
        }
        if ("MEDICAL".equals(productType)) {
            return "医疗险";
        }
        return "意外险";
    }

    private double round(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
