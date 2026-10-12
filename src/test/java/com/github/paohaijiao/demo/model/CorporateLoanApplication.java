package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 企业经营贷申请件（对公进件要素）
 * <p>
 * 字段取自企业授信调查表与税务/银行数据授权回传，仅保留额度测算与评级所需要素。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateLoanApplication {

    /**
     * 申请编号
     */
    private String applicationId;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 持续经营年限
     */
    private int operatingYears;

    /**
     * 近 12 个月营业收入（元）
     */
    private double annualRevenue;

    /**
     * 近 12 个月实缴纳税总额（元）
     */
    private double annualTaxAmount;

    /**
     * 近 12 个月对公账户有效流水（元）
     */
    private double bankFlowAnnual;

    /**
     * 资产负债率（百分比整数，如 55 表示 55%）
     */
    private int debtRatio;

    /**
     * 纳税信用等级：A / B / M / C / D
     */
    private String taxCreditLevel;

    /**
     * 近 2 年对公贷款逾期次数
     */
    private int overdueCount;

    /**
     * 是否存在失信被执行 / 重大涉诉记录
     */
    private boolean hasJudicialRecord;

    /**
     * 行业类别：MANUFACTURING-制造 / TECH-科技 / TRADE-商贸 / RESTRICTED-受限类
     */
    private String industryCategory;

    /**
     * 申请授信额度（元）
     */
    private double applyAmount;
}
