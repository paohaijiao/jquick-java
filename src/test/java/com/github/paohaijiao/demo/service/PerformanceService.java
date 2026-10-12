package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.PerformanceRules;
import com.github.paohaijiao.demo.model.BonusDecision;
import com.github.paohaijiao.demo.model.PerformanceRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 场景 6：绩效等级与奖金核算服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：四维加权汇总、系数聚合与下限保护、晋升与改进判定、评语组装</li>
 *     <li><b>规则侧</b>：权重、等级阈值、基准系数、各类加成与扣减，全部外置在 XML 中（可随年度政策调整）</li>
 * </ul>
 */
public class PerformanceService {

    /**
     * 晋升资格总分线
     */
    private static final double PROMOTION_SCORE_LINE = 85.0;

    private final PerformanceRules rules;

    public PerformanceService(PerformanceRules rules) {
        this.rules = rules;
    }

    /**
     * 核算绩效与奖金
     *
     * @param record 考核快照
     * @return 绩效奖金决策
     */
    public BonusDecision evaluate(PerformanceRecord record) {
        BonusDecision decision = new BonusDecision();
        decision.setEmployeeId(record.getEmployeeId());

        double totalScore = scale2(rules.weightedScore(record.getKpiScore(), record.getProjectScore(),
                record.getTeamScore(), record.getAttendanceScore()));
        String grade = rules.grade(totalScore);

        double coefficient = rules.baseCoefficient(grade)
                + rules.managerBonus(record.isManager())
                + rules.salesBonus(record.getSalesAmount(), grade)
                + rules.violationPenalty(record.isHasViolation());
        if (coefficient < 0.0) {
            coefficient = 0.0;
        }
        coefficient = scale2(coefficient);

        decision.setTotalScore(totalScore);
        decision.setGrade(grade);
        decision.setBonusCoefficient(coefficient);
        decision.setBonusAmount(scale2(rules.bonusAmount(record.getBaseSalary(), coefficient)));
        decision.setAttendanceLevel(rules.attendanceLevel(record.getAttendanceScore()));
        decision.setPromotionEligible(rules.promotionEligible(totalScore, record.isHasViolation()));
        decision.setNeedPip(rules.needPip(totalScore, record.isHasViolation()));

        buildRemarks(record, decision);
        return decision;
    }

    private void buildRemarks(PerformanceRecord record, BonusDecision decision) {
        if ("S".equals(decision.getGrade())) {
            decision.addRemark("绩效等级 S，优先纳入年度评优与晋升池");
        }
        if (decision.isPromotionEligible()) {
            decision.addRemark("总分不低于 " + (int) PROMOTION_SCORE_LINE + " 且无违规，具备晋升资格");
        }
        if (record.isHasViolation()) {
            decision.addRemark("存在违规记录，奖金系数已扣减 0.3");
        }
        if (decision.isNeedPip()) {
            decision.addRemark("绩效不达标，需进入绩效改进计划（PIP）");
        }
        if (decision.getAttendanceLevel() == 0) {
            decision.addRemark("考勤得分低于 80，请关注出勤情况");
        }
        if (record.getSalesAmount() >= 500000.0 && ("S".equals(decision.getGrade()) || "A".equals(decision.getGrade()))) {
            decision.addRemark("销售额达标且绩效优秀，享受销售激励加成");
        }
    }

    private double scale2(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
