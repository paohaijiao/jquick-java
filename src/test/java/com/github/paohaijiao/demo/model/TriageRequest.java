package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 急诊预检分诊要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TriageRequest {

    /**
     * 患者号
     */
    private String patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 年龄
     */
    private int age;

    /**
     * 主诉代码：CHEST_PAIN 胸痛 / STROKE_SIGN 卒中征象 / FEVER 发热 /
     * TRAUMA 外伤 / ABDOMINAL 腹痛 / COLD 普通感冒 / RASH 皮疹 / FOLLOW_UP 复诊开药
     */
    private String chiefComplaint;

    /**
     * 血压/心率等其他生命体征是否已报危急值
     */
    private boolean vitalSignsAbnormal;

    /**
     * 血氧饱和度（%）
     */
    private int oxygenSaturation;

    /**
     * 意识状态：ALERT 清醒 / DROWSY 嗜睡 / UNRESPONSIVE 无反应
     */
    private String consciousnessLevel;

    /**
     * 是否活动性出血
     */
    private boolean bleedingActive;

    /**
     * 是否呼吸困难
     */
    private boolean breathingDistress;

    /**
     * 体温（℃）
     */
    private double temperature;

    /**
     * 症状发作时长（以半小时为单位，如 2 小时传 4）
     */
    private int onsetHalfHours;

    /**
     * 是否 3 岁以下婴幼儿
     */
    private boolean infant;

    /**
     * 是否孕产妇
     */
    private boolean pregnant;
}
