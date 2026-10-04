package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 预算预警与单笔支出处置决策
 */
@Data
public class BudgetAlertDecision {

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 预算占用率级别：GREEN / YELLOW / ORANGE / RED
     */
    private String utilizationLevel;

    /**
     * 预算占用率（百分比，含承诺金额）
     */
    private int utilizationPercent;

    /**
     * 执行节奏：NORMAL 正常 / OVER_BURN 超前消耗 / UNDER_BURN 执行滞后
     */
    private String burnStatus;

    /**
     * 年末预测：OK / MILD 轻度超支 / SEVERE 严重超支（≥10%）
     */
    private String forecastStatus;

    /**
     * 预测超支金额
     */
    private double overrunAmount;

    /**
     * 单笔支出处置：APPROVE 放行 / APPROVE_WITH_WARNING 警示放行 /
     * ESCALATE 升级审批 / BLOCK 拦截
     */
    private String singleExpenseAction;

    /**
     * 风险分（0-120）
     */
    private int riskScore;

    /**
     * 预警信息
     */
    private List<String> alerts = new ArrayList<String>();

    public void addAlert(String alert) {
        this.alerts.add(alert);
    }
}
