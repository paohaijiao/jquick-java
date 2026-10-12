package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.DeviceAlarmRules;
import com.github.paohaijiao.demo.model.AlarmDecision;
import com.github.paohaijiao.demo.model.DeviceAlarm;

/**
 * 场景 9：IoT 设备告警分级服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：六维评分汇总与封顶、告警类型与等级装配、指标与动作组装</li>
 *     <li><b>规则侧</b>：各维度打分口径、类型归类优先级、等级/派单/通知阈值，全部外置在 XML 中</li>
 * </ul>
 */
public class DeviceAlarmService {

    private static final int SCORE_FLOOR = 0;
    private static final int SCORE_CEIL = 100;

    private final DeviceAlarmRules rules;

    public DeviceAlarmService(DeviceAlarmRules rules) {
        this.rules = rules;
    }

    /**
     * 计算设备告警决策
     *
     * @param device 设备运行快照
     * @return 告警决策
     */
    public AlarmDecision evaluate(DeviceAlarm device) {
        AlarmDecision decision = new AlarmDecision();
        decision.setDeviceId(device.getDeviceId());

        int rawScore = rules.scoreTemperature(device.getTemperature())
                + rules.scoreError(device.getErrorCount())
                + rules.scoreResource(device.getCpuUsage(), device.getMemoryUsage())
                + rules.scoreBattery(device.getBatteryLevel())
                + rules.scoreConnection(device.isConnectionLost(), device.getLastHeartbeatSeconds())
                + rules.scoreVibration(device.getVibrationLevel());
        int alarmScore = clamp(rawScore);

        String alarmLevel = rules.alarmLevel(alarmScore);
        String alarmType = rules.alarmType(device.isConnectionLost(), device.getLastHeartbeatSeconds(),
                device.getTemperature(), device.getCpuUsage(), device.getMemoryUsage(),
                device.getBatteryLevel(), device.getVibrationLevel());

        decision.setAlarmScore(alarmScore);
        decision.setAlarmLevel(alarmLevel);
        decision.setAlarmType(alarmType);
        decision.setNeedDispatch(rules.needDispatch(alarmScore, device.getCriticality()));
        decision.setDispatchPriority(rules.dispatchPriority(alarmScore, device.getCriticality()));
        decision.setNotifyChannel(rules.notifyChannel(alarmLevel, device.getCriticality()));
        decision.setAutoRemediation(rules.autoRemediation(alarmType, alarmScore));
        decision.setEscalateMinutes(rules.escalateMinutes(alarmLevel));

        buildIndicators(device, decision);
        buildActions(device, decision);
        return decision;
    }

    private int clamp(int score) {
        if (score < SCORE_FLOOR) {
            return SCORE_FLOOR;
        }
        if (score > SCORE_CEIL) {
            return SCORE_CEIL;
        }
        return score;
    }

    private void buildIndicators(DeviceAlarm device, AlarmDecision decision) {
        if (device.getTemperature() >= 60.0) {
            decision.addIndicator("温度 " + device.getTemperature() + "℃");
        }
        if (device.getCpuUsage() >= 75) {
            decision.addIndicator("CPU 使用率 " + device.getCpuUsage() + "%");
        }
        if (device.getMemoryUsage() >= 75) {
            decision.addIndicator("内存使用率 " + device.getMemoryUsage() + "%");
        }
        if (device.getErrorCount() >= 1) {
            decision.addIndicator("错误次数 " + device.getErrorCount());
        }
        if (device.isConnectionLost()) {
            decision.addIndicator("设备断连");
        } else if (device.getLastHeartbeatSeconds() >= 120) {
            decision.addIndicator("心跳间隔 " + device.getLastHeartbeatSeconds() + " 秒");
        }
        if (device.getBatteryLevel() <= 20) {
            decision.addIndicator("电量 " + device.getBatteryLevel() + "%");
        }
        if (device.getVibrationLevel() >= 3.0) {
            decision.addIndicator("振动强度 " + device.getVibrationLevel());
        }
    }

    private void buildActions(DeviceAlarm device, AlarmDecision decision) {
        if (decision.isNeedDispatch()) {
            decision.addAction("按 " + decision.getDispatchPriority() + " 优先级派发运维工单");
        } else {
            decision.addAction("暂不派单，持续观察指标趋势");
        }
        decision.addAction("通过 " + decision.getNotifyChannel() + " 通知责任人");
        if (!"NONE".equals(decision.getAutoRemediation())) {
            decision.addAction("执行自动处置：" + decision.getAutoRemediation());
        }
        if ("HIGH".equals(device.getCriticality()) && decision.getAlarmScore() >= 20) {
            decision.addAction("关键设备告警，同步通知设备负责人");
        }
        decision.addAction(decision.getEscalateMinutes() + " 分钟内未恢复则升级");
    }
}
