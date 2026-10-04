package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.SupplierAdmissionRules;
import com.github.paohaijiao.demo.model.SupplierAdmissionDecision;
import com.github.paohaijiao.demo.model.SupplierProfile;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 场景 18：供应链金融供应商准入服务
 * <p>
 * 规则侧（XML）：五维评分口径、评级阈值、保理比例与账期。
 * Java 侧：失信/重大违约一票否决、评级到准入结论映射、保理额度封顶与附加条件组装。
 */
public class SupplierAdmissionService {

    /**
     * 单户保理额度绝对上限（元）：1000 万
     */
    private static final double FACTORING_CEILING = 10000000.0;

    /**
     * C 级（限制类）保理额度上限（元）：200 万
     */
    private static final double GRADE_C_CEILING = 2000000.0;

    private final SupplierAdmissionRules rules;

    public SupplierAdmissionService(SupplierAdmissionRules rules) {
        this.rules = rules;
    }

    public SupplierAdmissionDecision evaluate(SupplierProfile supplier) {
        SupplierAdmissionDecision decision = new SupplierAdmissionDecision();
        decision.setSupplierId(supplier.getSupplierId());

        if (supplier.isDishonest()) {
            reject(decision, "供应商为失信被执行人，禁止准入并终止合作洽谈");
            return decision;
        }
        if (supplier.isMajorBreach()) {
            reject(decision, "近 2 年发生重大合同违约（断供/质量事故），暂停准入 24 个月");
            return decision;
        }

        int qualificationScore = rules.scoreQualification(
                supplier.getQualificationLevel(), supplier.getRegisteredCapitalWan());
        int fulfillmentScore = rules.scoreFulfillment(
                supplier.getOnTimeDeliveryRate(), supplier.getQualityPassRate(),
                supplier.getContractFulfillRate());
        int cooperationScore = rules.scoreCooperation(
                supplier.getCooperationMonths(), supplier.getAnnualOrderAmount());
        int financeScore = rules.scoreFinance(supplier.getFinanceStatus());
        int yearsScore = rules.scoreRegisteredYears(supplier.getRegisteredYears());
        int totalScore = qualificationScore + fulfillmentScore + cooperationScore
                + financeScore + yearsScore;
        if (totalScore > 100) {
            totalScore = 100;
        }

        decision.addScoreDetail("企业资质评分: " + qualificationScore + " / 20");
        decision.addScoreDetail("履约记录评分: " + fulfillmentScore + " / 30");
        decision.addScoreDetail("合作深度评分: " + cooperationScore + " / 15");
        decision.addScoreDetail("财务状况评分: " + financeScore + " / 20");
        decision.addScoreDetail("注册年限评分: " + yearsScore + " / 15");
        decision.addScoreDetail("汇总评分: " + totalScore);
        decision.setTotalScore(totalScore);

        String grade = rules.grade(totalScore);
        decision.setGrade(grade);
        decision.setSettlementDays(rules.settlementDays(grade));

        if ("A".equals(grade) || "B".equals(grade)) {
            decision.setDecision("APPROVE");
        } else if ("C".equals(grade)) {
            decision.setDecision("CONDITIONAL");
        } else {
            decision.setDecision("REJECT");
            decision.addRejectReason("综合评分 " + totalScore + " 低于准入线 60，评为 D 级供应商");
            return decision;
        }

        double ratio = rules.factoringRatio(grade);
        double limit = supplier.getAnnualOrderAmount() * ratio;
        if ("C".equals(grade) && limit > GRADE_C_CEILING) {
            limit = GRADE_C_CEILING;
        }
        if (limit > FACTORING_CEILING) {
            limit = FACTORING_CEILING;
        }
        decision.setFactoringLimit(round(limit));

        if ("C".equals(grade)) {
            decision.setRequireGuarantee(true);
            decision.addCondition("附条件准入：需缴纳 10% 履约保证金或引入第三方担保");
            decision.addCondition("保理业务按单笔应收账款逐笔审核，不授循环额度");
            decision.addCondition("观察期 6 个月，履约指标连续达标后可申请升为 B 级");
        }
        if ("WEAK".equals(supplier.getFinanceStatus()) || "RISKY".equals(supplier.getFinanceStatus())) {
            decision.addCondition("财务状况偏弱，保理回款须进入核心企业监管专户封闭运行");
        }
        if (supplier.getCooperationMonths() < 6) {
            decision.addCondition("与核心企业合作不足 6 个月，首笔业务降额 50% 试单");
        }
        return decision;
    }

    private void reject(SupplierAdmissionDecision decision, String reason) {
        decision.setDecision("REJECT");
        decision.setGrade("D");
        decision.setFactoringLimit(0.0);
        decision.addRejectReason(reason);
    }

    private double round(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
