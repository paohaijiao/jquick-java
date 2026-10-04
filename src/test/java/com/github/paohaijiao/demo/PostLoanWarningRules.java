package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 2（生产级样板）：贷后逾期预警规则集
 * <p>
 * 规则按「逾期评分卡 + 催收决策映射」拆分：
 * <ul>
 *     <li>评分类：逾期天数、金额占比、联系触达、抵押物覆盖度四个维度打分</li>
 *     <li>决策类：预警等级、催收渠道、罚息计提、最低还款额、风险迁徙</li>
 * </ul>
 * 规则脚本见 {@code src/test/resources/demo/post-loan-warning.xml}。
 */
public interface PostLoanWarningRules {

    /**
     * 逾期阶段：0-未逾期 / 1-M1 / 2-M2 / 3-M3 / 4-M3+
     */
    int overdueStage(@Param("overdueDays") int overdueDays);

    /**
     * 逾期天数与历史逾期评分（0-35）
     */
    int scoreOverdueDays(@Param("overdueDays") int overdueDays,
                         @Param("overdueHistoryCount") int overdueHistoryCount);

    /**
     * 逾期金额占剩余本金比例评分（0-25）
     */
    int scoreAmountRatio(@Param("overdueAmount") double overdueAmount,
                         @Param("remainingPrincipal") double remainingPrincipal);

    /**
     * 联系触达评分（0-15）：联系不上为 0
     */
    int scoreContact(@Param("contactSuccess") boolean contactSuccess,
                     @Param("lastContactDays") int lastContactDays);

    /**
     * 抵押物覆盖度评分（0-25）
     */
    int scoreCollateral(@Param("collateralType") String collateralType,
                        @Param("collateralValue") double collateralValue,
                        @Param("remainingPrincipal") double remainingPrincipal);

    /**
     * 预警等级：RED / ORANGE / YELLOW / BLUE
     */
    String warningLevel(@Param("warningScore") int warningScore);

    /**
     * 催收渠道路由：LITIGATION / HOME_VISIT / PHONE / SMS
     */
    String collectChannel(@Param("warningScore") int warningScore,
                          @Param("overdueAmount") double overdueAmount);

    /**
     * 罚息计提：剩余本金 × 合同利率 × 1.5 × 逾期天数 / 365
     */
    double penaltyInterest(@Param("remainingPrincipal") double remainingPrincipal,
                           @Param("baseRate") double baseRate,
                           @Param("overdueDays") int overdueDays);

    /**
     * 最低还款额：逾期金额 + 违约金（按逾期阶段阶梯）
     */
    double minRepayAmount(@Param("overdueAmount") double overdueAmount,
                          @Param("overdueDays") int overdueDays);

    /**
     * 风险迁徙度：逾期阶段 × 2 + 历史预警次数，封顶 9
     */
    int riskMigration(@Param("overdueStage") int overdueStage,
                      @Param("warningCount") int warningCount);
}
