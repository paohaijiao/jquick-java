package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 37：预算占用分级、执行节奏、预测超支、单笔处置与风险分
 */
public interface BudgetAlertRules {

    /**
     * 预算占用率分级（实际+承诺）：≥100% RED / ≥90% ORANGE / ≥70% YELLOW / 其余 GREEN
     */
    String utilizationLevel(@Param("spent") double spent,
                            @Param("committed") double committed,
                            @Param("budget") double budget);

    /**
     * 支出进度与时间进度偏差超 ±15% 为超前/滞后
     */
    String burnStatus(@Param("spent") double spent,
                      @Param("budget") double budget,
                      @Param("month") int month);

    /**
     * 年末预测超支：超预算 10% SEVERE，超预算 MILD，其余 OK
     */
    String forecastStatus(@Param("forecast") double forecast,
                          @Param("budget") double budget);

    /**
     * 单笔支出处置：
     * CAPEX 未立项 BLOCK；RED 级应急 ESCALATE、非应急 BLOCK；
     * ORANGE 超年度预算 5% ESCALATE、否则警示放行；YELLOW 警示放行；GREEN 放行
     */
    String singleExpenseAction(@Param("level") String level,
                               @Param("category") String category,
                               @Param("emergency") boolean emergency,
                               @Param("capexApproved") boolean capexApproved,
                               @Param("overFivePercent") boolean overFivePercent);

    /**
     * 风险分：RED100/ORANGE70/YELLOW40/GREEN10，超前消耗与预测严重超支各加 10
     */
    int riskScore(@Param("level") String level,
                  @Param("burn") String burn,
                  @Param("forecast") String forecast);
}
