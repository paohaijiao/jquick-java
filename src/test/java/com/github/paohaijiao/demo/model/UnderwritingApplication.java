package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 人身险投保单核保要素
 * <p>
 * 字段取自投保书、健康告知问卷与职业分类表，用于核保员风险评估与自动核保决策。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnderwritingApplication {

    /**
     * 投保单号
     */
    private String policyNo;

    /**
     * 投保人/被保人姓名
     */
    private String applicantName;

    /**
     * 年龄
     */
    private int age;

    /**
     * 职业类别（1-6 类，数字越大风险越高，6 类为高危拒保职业）
     */
    private int occupationClass;

    /**
     * 投保险种：LIFE-寿险 / CRITICAL_ILLNESS-重疾险 / MEDICAL-医疗险 / ACCIDENT-意外险
     */
    private String productType;

    /**
     * 投保保额（元）
     */
    private double sumInsured;

    /**
     * 标准保费（元）
     */
    private double premiumAmount;

    /**
     * 身体质量指数 BMI
     */
    private double bmi;

    /**
     * 是否吸烟
     */
    private boolean smoker;

    /**
     * 慢性病史：NONE-无 / HYPERTENSION-高血压 / DIABETES-糖尿病 / CANCER_HISTORY-恶性肿瘤史
     */
    private String chronicDisease;

    /**
     * 健康告知是否如实填写（false 视为未如实告知，直接拒保）
     */
    private boolean healthDeclarationPass;
}
