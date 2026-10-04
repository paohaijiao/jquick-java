package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 16（生产级样板）：企业经营贷授信测算规则集
 * <p>
 * 六维评分（经营年限 / 营收规模 / 纳税信用 / 流水核验 / 负债率 / 征信记录），
 * 再由总分映射信用等级、审批结论、纳税倍数与利率；行业准入与失信否决由 Service 前置。
 * 规则脚本见 {@code src/test/resources/demo/corporate-credit.xml}。
 */
public interface CorporateLoanRules {

    /**
     * 持续经营年限评分（0-15）
     */
    int scoreOperatingYears(@Param("operatingYears") int operatingYears);

    /**
     * 营收规模评分（0-20）
     */
    int scoreRevenue(@Param("annualRevenue") double annualRevenue);

    /**
     * 纳税信用评分（0-20）：纳税信用等级 + 实缴税额
     */
    int scoreTaxCredit(@Param("taxCreditLevel") String taxCreditLevel,
                       @Param("annualTaxAmount") double annualTaxAmount);

    /**
     * 流水核验评分（0-15）：有效流水与营收的匹配度，交叉相乘规避除法精度问题
     */
    int scoreBankFlow(@Param("bankFlowAnnual") double bankFlowAnnual,
                      @Param("annualRevenue") double annualRevenue);

    /**
     * 资产负债率评分（0-15）
     */
    int scoreDebtRatio(@Param("debtRatio") int debtRatio);

    /**
     * 征信记录评分（0-15）：逾期次数 + 失信记录
     */
    int scoreCreditRecord(@Param("overdueCount") int overdueCount,
                          @Param("hasJudicialRecord") boolean hasJudicialRecord);

    /**
     * 信用等级：A / B / C / D
     */
    String creditRating(@Param("totalScore") int totalScore);

    /**
     * 审批结论：APPROVE / MANUAL_REVIEW / REJECT
     */
    String approveDecision(@Param("totalScore") int totalScore);

    /**
     * 纳税额授信倍数：随信用等级与综合分浮动
     */
    double taxMultiplier(@Param("taxCreditLevel") String taxCreditLevel,
                         @Param("totalScore") int totalScore);

    /**
     * 执行年利率：等级越低利率越高
     */
    double interestRate(@Param("creditRating") String creditRating);
}
