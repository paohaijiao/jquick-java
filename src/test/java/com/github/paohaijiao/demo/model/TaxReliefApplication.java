package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小微企业税费减免与稳岗补贴申请要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxReliefApplication {

    /**
     * 企业编号
     */
    private String enterpriseId;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 纳税人类型：GENERAL-一般纳税人 / SMALL_SCALE-小规模纳税人
     */
    private String taxpayerType;

    /**
     * 从业人数（含劳务派遣）
     */
    private int employeeCount;

    /**
     * 资产总额（万元）
     */
    private double totalAssetsWan;

    /**
     * 年应纳税所得额（万元）
     */
    private double annualTaxableIncomeWan;

    /**
     * 是否属于限制或禁止行业
     */
    private boolean restrictedIndustry;

    /**
     * 社保连续缴纳月数（稳岗补贴审核用）
     */
    private int socialInsuranceMonths;

    /**
     * 上年度裁员率（整数百分比）
     */
    private int layoffRate;

    /**
     * 是否存在社保欠费
     */
    private boolean socialArrears;

    /**
     * 是否存在欠税
     */
    private boolean overdueTax;

    /**
     * 上年度实际缴纳失业保险费（万元）
     */
    private double paidUnemploymentInsuranceWan;
}
