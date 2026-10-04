package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 9：IoT 设备告警分级
 * <p>
 * 各维度独立打分（温度/错误/资源/电量/连接/振动），再由综合分决定等级、派单、通知与自动处置。
 */
public interface DeviceAlarmRules {

    int scoreTemperature(@Param("temperature") double temperature);

    int scoreError(@Param("errorCount") int errorCount);

    int scoreResource(@Param("cpuUsage") int cpuUsage,
                      @Param("memoryUsage") int memoryUsage);

    int scoreBattery(@Param("batteryLevel") int batteryLevel);

    int scoreConnection(@Param("connectionLost") boolean connectionLost,
                        @Param("lastHeartbeatSeconds") int lastHeartbeatSeconds);

    int scoreVibration(@Param("vibrationLevel") double vibrationLevel);

    String alarmLevel(@Param("alarmScore") int alarmScore);

    String alarmType(@Param("connectionLost") boolean connectionLost,
                     @Param("lastHeartbeatSeconds") int lastHeartbeatSeconds,
                     @Param("temperature") double temperature,
                     @Param("cpuUsage") int cpuUsage,
                     @Param("memoryUsage") int memoryUsage,
                     @Param("batteryLevel") int batteryLevel,
                     @Param("vibrationLevel") double vibrationLevel);

    boolean needDispatch(@Param("alarmScore") int alarmScore,
                         @Param("criticality") String criticality);

    String dispatchPriority(@Param("alarmScore") int alarmScore,
                            @Param("criticality") String criticality);

    String notifyChannel(@Param("alarmLevel") String alarmLevel,
                         @Param("criticality") String criticality);

    String autoRemediation(@Param("alarmType") String alarmType,
                           @Param("alarmScore") int alarmScore);

    int escalateMinutes(@Param("alarmLevel") String alarmLevel);
}
