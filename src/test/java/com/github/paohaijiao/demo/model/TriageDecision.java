package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 急诊分诊决策
 */
@Data
public class TriageDecision {

    /**
     * 患者号
     */
    private String patientId;

    /**
     * 分诊级别：I 濒危 / II 危重 / III 急症 / IV 非急症
     */
    private String triageLevel;

    /**
     * 安置区域：RESCUE 抢救室 / CRITICAL_BAY 危重处置区/绿道 / OBS 留观区 / WAITING 候诊区
     */
    private String zone;

    /**
     * 医生目标响应时间（分钟）
     */
    private int responseMinutes;

    /**
     * 生命威胁信号分（≥4 直接 I 级）
     */
    private int lifeThreatScore;

    /**
     * 绿色通道与处置标签
     */
    private List<String> channels = new ArrayList<String>();

    public void addChannel(String channel) {
        this.channels.add(channel);
    }
}
