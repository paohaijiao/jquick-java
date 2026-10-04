package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 车间能耗预警决策
 */
@Data
public class WorkshopEnergyAlert {

    /**
     * 车间编号
     */
    private String workshopId;

    /**
     * 告警级别：CRITICAL 紧急 / WARNING 预警 / INFO 提示 / NORMAL 正常
     */
    private String alertLevel;

    /**
     * 用电同比偏离率（整数百分比）
     */
    private int powerDeviationPercent;

    /**
     * 最大需量超合同率（整数百分比）
     */
    private int demandExcessPercent;

    /**
     * 用水同比偏离率（整数百分比）
     */
    private int waterDeviationPercent;

    /**
     * 单耗评级：WASTE 浪费 / ATTENTION 关注 / NORMAL 正常
     */
    private String efficiencyLevel;

    /**
     * 异常信号与处置提示
     */
    private List<String> signals = new ArrayList<String>();

    public void addSignal(String signal) {
        this.signals.add(signal);
    }
}
