package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 1（生产级样板）：信贷风控准入规则集
 * <p>
 * 规则按「多维评分卡 + 决策映射」拆分，每个方法只做一件事：
 * <ul>
 *     <li>评分类：输出单维度得分，由 Service 汇总，便于模型回溯与单维度调参</li>
 *     <li>决策类：输入汇总分，输出准入结论、风险等级、额度、利率</li>
 * </ul>
 * 规则脚本见 {@code src/test/resources/demo/risk-admission.xml}。
 */
public interface RiskAdmissionRules {

    /**
     * 负债收入比评分（0-25）：月负债还款额 / 月收入
     */
    int scoreDebtRatio(@Param("monthlyIncome") double monthlyIncome,
                       @Param("monthlyDebt") double monthlyDebt);

    /**
     * 历史逾期评分（0-30）：结合逾期次数与最长逾期天数
     */
    int scoreOverdue(@Param("overdueCount") int overdueCount,
                     @Param("maxOverdueDays") int maxOverdueDays);

    /**
     * 职业稳定性评分（0-15）：在职月数 + 单位性质
     */
    int scoreEmployment(@Param("employedMonths") int employedMonths,
                        @Param("companyType") String companyType);

    /**
     * 征信查询评分（-5 ~ 10）：近 3 个月查询次数越多得分越低
     */
    int scoreCreditQuery(@Param("queryCount3M") int queryCount3M);

    /**
     * 抵押物评分（0-20）：抵押物类型与覆盖倍数
     */
    int scoreCollateral(@Param("collateralType") String collateralType,
                        @Param("collateralValue") double collateralValue,
                        @Param("loanAmount") double loanAmount);

    /**
     * 风险等级映射：A / B / C / D
     */
    String riskLevel(@Param("totalScore") int totalScore);

    /**
     * 准入结论：PASS / MANUAL_REVIEW / REJECT
     */
    String admit(@Param("totalScore") int totalScore,
                 @Param("hasJudicialRecord") boolean hasJudicialRecord);

    /**
     * 依据月收入与总分测算建议授信额度
     */
    double suggestLimit(@Param("monthlyIncome") double monthlyIncome,
                        @Param("totalScore") int totalScore);

    /**
     * 依据风险等级给出年化执行利率
     */
    double interestRate(@Param("riskLevel") String riskLevel);

    /**
     * 是否触发人工复核（中等分区间或多头借贷查询）
     */
    boolean needManualReview(@Param("totalScore") int totalScore,
                             @Param("queryCount3M") int queryCount3M);
}
