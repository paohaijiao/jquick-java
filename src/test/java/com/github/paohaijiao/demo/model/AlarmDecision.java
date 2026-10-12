package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * IoT 设备告警决策结果
 */
@Data
public class AlarmDecision {

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 告警分（0-100）
     */
    private int alarmScore;

    /**
     * 告警等级：CRITICAL / MAJOR / MINOR / NORMAL
     */
    private String alarmLevel;

    /**
     * 告警类型：TEMPERATURE / PERFORMANCE / NETWORK / POWER / VIBRATION / NONE
     */
    private String alarmType;

    /**
     * 是否派发工单
     */
    private boolean needDispatch;

    /**
     * 派单优先级：P0 / P1 / P2 / P3
     */
    private String dispatchPriority;

    /**
     * 通知渠道：PHONE_CALL / SMS / IM / EMAIL
     */
    private String notifyChannel;

    /**
     * 自动处置动作：ISOLATE / RATE_LIMIT / THROTTLE / NONE
     */
    private String autoRemediation;

    /**
     * 升级时限（分钟）
     */
    private int escalateMinutes;

    /**
     * 命中指标明细
     */
    private List<String> indicators = new ArrayList<String>();

    /**
     * 处置动作建议
     */
    private List<String> actions = new ArrayList<String>();

    public void addIndicator(String indicator) {
        this.indicators.add(indicator);
    }

    public void addAction(String action) {
        this.actions.add(action);
    }
}
