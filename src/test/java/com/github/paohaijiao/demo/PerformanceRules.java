package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 6（生产级样板）：绩效等级与奖金系数规则集
 * <p>
 * 规则按「加权评分 → 等级映射 → 系数修正 → 奖金核算」拆分：
 * <ul>
 *     <li>评分类：KPI / 项目 / 协作 / 考勤四维加权</li>
 *     <li>修正类：管理者加成、销售激励、违规扣减</li>
 *     <li>决策类：晋升资格、绩效改进、奖金金额</li>
 * </ul>
 * 规则脚本见 {@code src/test/resources/demo/performance.xml}。
 */
public interface PerformanceRules {

    /**
     * 加权总分：KPI 50% + 项目 20% + 协作 15% + 考勤 15%
     */
    double weightedScore(@Param("kpiScore") double kpiScore,
                         @Param("projectScore") double projectScore,
                         @Param("teamScore") double teamScore,
                         @Param("attendanceScore") double attendanceScore);

    /**
     * 绩效等级：S / A / B / C / D
     */
    String grade(@Param("totalScore") double totalScore);

    /**
     * 等级基准奖金系数
     */
    double baseCoefficient(@Param("grade") String grade);

    /**
     * 管理者加成
     */
    double managerBonus(@Param("manager") boolean manager);

    /**
     * 销售激励：高等级高销售额额外加成
     */
    double salesBonus(@Param("salesAmount") double salesAmount,
                      @Param("grade") String grade);

    /**
     * 违规扣减
     */
    double violationPenalty(@Param("hasViolation") boolean hasViolation);

    /**
     * 是否具备晋升资格
     */
    boolean promotionEligible(@Param("totalScore") double totalScore,
                              @Param("hasViolation") boolean hasViolation);

    /**
     * 是否进入绩效改进计划
     */
    boolean needPip(@Param("totalScore") double totalScore,
                    @Param("hasViolation") boolean hasViolation);

    /**
     * 考勤等级：>=95 为 3，>=90 为 2，>=80 为 1，否则 0
     */
    int attendanceLevel(@Param("attendanceScore") double attendanceScore);

    /**
     * 奖金金额 = 基本工资 × 最终系数
     */
    double bonusAmount(@Param("baseSalary") double baseSalary,
                       @Param("bonusCoefficient") double bonusCoefficient);
}
