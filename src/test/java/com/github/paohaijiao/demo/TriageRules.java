package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 34：急诊生命威胁评分、主诉基础分级、发热与时间窗规则
 */
public interface TriageRules {

    /**
     * 生命威胁信号累加（≥4 视为濒危 I 级）：
     * 其他体征危急/血氧&lt;90/无反应各 4 分，嗜睡 2 分，活动出血与呼吸困难各 3 分
     */
    int lifeThreatScore(@Param("vitalAbnormal") int vitalAbnormal,
                        @Param("spo2") int spo2,
                        @Param("consciousCode") int consciousCode,
                        @Param("bleeding") int bleeding,
                        @Param("distress") int distress);

    /**
     * 主诉基础分级（CHEST_PAIN/STROKE_SIGN 为 II，TRAUMA/ABDOMINAL/FEVER 为 III，其余 IV）
     */
    String complaintBaseLevel(@Param("complaint") String complaint);

    /**
     * 发热分级，婴幼儿 39℃ 升至 II
     */
    String feverLevel(@Param("temperature") double temperature,
                      @Param("infant") boolean infant);

    /**
     * 是否处于卒中 4.5 小时（9 个半小时）救治时间窗
     */
    boolean withinStrokeWindow(@Param("onsetHalfHours") int onsetHalfHours);
}
