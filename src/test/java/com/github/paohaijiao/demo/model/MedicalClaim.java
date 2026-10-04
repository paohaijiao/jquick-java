package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 医保结算要素（报销合规校验所需的就诊与费用快照）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalClaim {

    /**
     * 结算单号
     */
    private String claimNo;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 参保类型：EMPLOYEE-职工医保 / RESIDENT-居民医保
     */
    private String insuranceType;

    /**
     * 医院等级：THIRD_A-三甲 / SECOND_A-二级 / COMMUNITY-社区
     */
    private String hospitalLevel;

    /**
     * 费用类别：DRUG_A-甲类药 / DRUG_B-乙类药 / DRUG_C-丙类自费 / TREATMENT-诊疗 / EXAMINATION-检查
     */
    private String medicalCategory;

    /**
     * 本次费用总额
     */
    private double totalExpense;

    /**
     * 是否急诊
     */
    private boolean emergency;

    /**
     * 是否异地就医
     */
    private boolean offSite;

    /**
     * 异地就医是否已备案
     */
    private boolean filed;

    /**
     * 年度统筹基金已累计支付额
     */
    private double annualAccumulated;
}
