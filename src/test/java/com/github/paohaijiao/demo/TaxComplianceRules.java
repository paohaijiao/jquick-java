package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 8（生产级样板）：税务合规风险打分规则集
 * <p>
 * 规则按「多维风险指标 → 分级 → 处置路由」拆分，六个维度独立计分：
 * 逾期申报、缺失发票、税负率异常、进项异常、异常发票、成立年限。
 * 规则脚本见 {@code src/test/resources/demo/tax-compliance.xml}。
 */
public interface TaxComplianceRules {

    /**
     * 逾期申报扣分（每次 5 分，封顶 25）
     */
    int scoreLate(@Param("lateCount") int lateCount);

    /**
     * 缺失发票扣分（每次 8 分，封顶 24）
     */
    int scoreMissingInvoice(@Param("missingInvoice") int missingInvoice);

    /**
     * 税负率异常扣分：明显低于行业均值触发
     */
    int scoreBurden(@Param("taxBurdenRate") double taxBurdenRate,
                    @Param("industryAvgBurden") double industryAvgBurden);

    /**
     * 进项占比异常扣分
     */
    int scoreInputRatio(@Param("inputTaxRatio") double inputTaxRatio);

    /**
     * 异常发票扣分
     */
    int scoreAbnormal(@Param("hasAbnormalInvoice") boolean hasAbnormalInvoice);

    /**
     * 新设企业扣分（成立时间越短风险越高）
     */
    int scoreAge(@Param("yearsSinceRegister") int yearsSinceRegister);

    /**
     * 风险等级：HIGH / MEDIUM / LOW
     */
    String riskLevel(@Param("riskScore") int riskScore);

    /**
     * 处置方式：SELF_CHECK / FIELD_VISIT / TAX_AUDIT
     */
    String auditLevel(@Param("riskScore") int riskScore,
                      @Param("hasAbnormalInvoice") boolean hasAbnormalInvoice);

    /**
     * 是否推送风险提醒
     */
    boolean needWarning(@Param("riskScore") int riskScore);

    /**
     * 是否移交稽查
     */
    boolean needInvestigation(@Param("riskScore") int riskScore,
                              @Param("hasAbnormalInvoice") boolean hasAbnormalInvoice);
}
