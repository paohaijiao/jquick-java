package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IoT 设备运行快照（告警分级所需的实时指标）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceAlarm {

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型：SENSOR / GATEWAY / CAMERA / ROBOT
     */
    private String deviceType;

    /**
     * 设备重要性：HIGH / MEDIUM / LOW
     */
    private String criticality;

    /**
     * 温度（摄氏度）
     */
    private double temperature;

    /**
     * CPU 使用率（0-100）
     */
    private int cpuUsage;

    /**
     * 内存使用率（0-100）
     */
    private int memoryUsage;

    /**
     * 窗口期内错误次数
     */
    private int errorCount;

    /**
     * 是否断连
     */
    private boolean connectionLost;

    /**
     * 最近心跳间隔（秒）
     */
    private int lastHeartbeatSeconds;

    /**
     * 电池电量（0-100）
     */
    private int batteryLevel;

    /**
     * 振动强度
     */
    private double vibrationLevel;
}
