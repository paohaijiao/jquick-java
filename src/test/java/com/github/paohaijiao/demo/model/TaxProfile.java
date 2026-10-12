package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 企业税务画像（税务风险打分所需的申报与经营快照）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxProfile {

    /**
     * 企业税号
     */
    private String companyId;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 纳税人类型：GENERAL-一般纳税人 / SMALL_MICRO-小规模
     */
    private String taxpayerType;

    /**
     * 申报所属期
     */
    private String taxPeriod;

    /**
     * 逾期申报次数
     */
    private int lateCount;

    /**
     * 缺失发票次数
     */
    private int missingInvoice;

    /**
     * 实际税负率
     */
    private double taxBurdenRate;

    /**
     * 行业平均税负率
     */
    private double industryAvgBurden;

    /**
     * 本期营业收入（元）
     */
    private double revenue;

    /**
     * 进项占比
     */
    private double inputTaxRatio;

    /**
     * 是否存在异常发票
     */
    private boolean hasAbnormalInvoice;

    /**
     * 注册年限
     */
    private int yearsSinceRegister;

    /**
     * 纳税信用等级：A / B / M / C / D
     */
    private String creditRating;
}
