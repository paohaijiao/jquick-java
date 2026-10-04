package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 工单 SLA 决策结果
 */
@Data
public class SlaDecision {

    /**
     * 工单号
     */
    private String ticketNo;

    /**
     * 服务水平等级：VIP / GOLD / STANDARD
     */
    private String slaLevel;

    /**
     * 首响时限（分钟）
     */
    private int responseMinutes;

    /**
     * 解决时限（分钟）
     */
    private int resolveMinutes;

    /**
     * 升级时限（分钟）
     */
    private int escalateMinutes;

    /**
     * 综合风险分
     */
    private int riskScore;

    /**
     * 违约风险：HIGH / MEDIUM / LOW
     */
    private String breachRisk;

    /**
     * 是否触发升级
     */
    private boolean needEscalate;

    /**
     * 是否需要经理介入
     */
    private boolean needManager;

    /**
     * 处理动作建议
     */
    private List<String> actions = new ArrayList<String>();

    public void addAction(String action) {
        this.actions.add(action);
    }
}
