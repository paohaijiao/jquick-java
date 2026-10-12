package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 体检指标要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthScreeningReport {

    /**
     * 体检报告号
     */
    private String reportId;

    /**
     * 受检人
     */
    private String personName;

    /**
     * 性别：M 男 / F 女
     */
    private String gender;

    /**
     * 年龄
     */
    private int age;

    /**
     * 收缩压（mmHg）
     */
    private int systolicBp;

    /**
     * 舒张压（mmHg）
     */
    private int diastolicBp;

    /**
     * 空腹血糖（mmol/L）
     */
    private double fastingGlucose;

    /**
     * 总胆固醇（mmol/L）
     */
    private double totalCholesterol;

    /**
     * 体质指数
     */
    private double bmi;

    /**
     * 静息心率（次/分）
     */
    private int heartRate;

    /**
     * 谷丙转氨酶（U/L）
     */
    private int alt;

    /**
     * 血肌酐（μmol/L）
     */
    private int creatinine;

    /**
     * 是否有吸烟史
     */
    private boolean smokingHistory;

    /**
     * 是否有糖尿病/高血压家族史
     */
    private boolean familyHistory;
}
