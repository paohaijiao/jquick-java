package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 33：体检各指标医学分级规则（NORMAL/ATTENTION/HIGH/CRITICAL）
 */
public interface HealthScreeningRules {

    /**
     * 收缩压分级
     */
    String systolicLevel(@Param("systolic") int systolic);

    /**
     * 舒张压分级
     */
    String diastolicLevel(@Param("diastolic") int diastolic);

    /**
     * 空腹血糖分级
     */
    String glucoseLevel(@Param("glucose") double glucose);

    /**
     * 总胆固醇分级
     */
    String cholesterolLevel(@Param("cholesterol") double cholesterol);

    /**
     * BMI 分级（含消瘦提示）
     */
    String bmiLevel(@Param("bmi") double bmi);

    /**
     * 静息心率分级
     */
    String heartRateLevel(@Param("heartRate") int heartRate);

    /**
     * 谷丙转氨酶分级
     */
    int altLevelRank(@Param("alt") int alt);

    /**
     * 血肌酐分级（男女阈值不同）
     */
    String creatinineLevel(@Param("creatinine") int creatinine,
                           @Param("gender") String gender);
}
