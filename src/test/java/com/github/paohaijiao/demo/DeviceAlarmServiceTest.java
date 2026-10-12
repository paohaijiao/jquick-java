package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.AlarmDecision;
import com.github.paohaijiao.demo.model.DeviceAlarm;
import com.github.paohaijiao.demo.service.DeviceAlarmService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 9 生产级样板：IoT 设备告警分级
 * <p>
 * 覆盖 6 组告警形态：严重过热、网络断连、性能资源耗尽、低电量、
 * 设备正常、振动异常。
 */
public class DeviceAlarmServiceTest {

    private DeviceAlarmService service;

    @Before
    public void setUp() {
        DeviceAlarmRules rules = JQuickJava.create()
                .createApi(DeviceAlarmRules.class, "demo/device-alarm.xml");
        this.service = new DeviceAlarmService(rules);
    }

    /**
     * 严重过热：温度 92℃ + 高频错误 22 次 + CPU 96% → 80 分 CRITICAL
     */
    @Test
    public void shouldEscalateOverheatDeviceToCritical() {
        DeviceAlarm device = new DeviceAlarm();
        device.setDeviceId("DEV-9001");
        device.setDeviceName("炼钢炉温控传感器");
        device.setDeviceType("SENSOR");
        device.setCriticality("HIGH");
        device.setTemperature(92.0);
        device.setCpuUsage(96);
        device.setMemoryUsage(70);
        device.setErrorCount(22);
        device.setConnectionLost(false);
        device.setLastHeartbeatSeconds(60);
        device.setBatteryLevel(80);
        device.setVibrationLevel(1.0);

        AlarmDecision decision = service.evaluate(device);

        Assert.assertEquals(80, decision.getAlarmScore());
        Assert.assertEquals("CRITICAL", decision.getAlarmLevel());
        Assert.assertEquals("TEMPERATURE", decision.getAlarmType());
        Assert.assertTrue(decision.isNeedDispatch());
        Assert.assertEquals("P0", decision.getDispatchPriority());
        Assert.assertEquals("PHONE_CALL", decision.getNotifyChannel());
        Assert.assertEquals("ISOLATE", decision.getAutoRemediation());
        Assert.assertEquals(5, decision.getEscalateMinutes());
        Assert.assertEquals(3, decision.getIndicators().size());
        Assert.assertTrue(decision.getIndicators().contains("温度 92.0℃"));
        Assert.assertTrue(decision.getIndicators().contains("CPU 使用率 96%"));
        Assert.assertTrue(decision.getIndicators().contains("错误次数 22"));
        Assert.assertTrue(decision.getActions().contains("按 P0 优先级派发运维工单"));
        Assert.assertTrue(decision.getActions().contains("执行自动处置：ISOLATE"));
    }

    /**
     * 网络断连：断连 + 错误 10 次 + 低电量 15% → 51 分 MAJOR
     */
    @Test
    public void shouldClassifyNetworkOutage() {
        DeviceAlarm device = new DeviceAlarm();
        device.setDeviceId("DEV-9002");
        device.setDeviceName("园区闸机网关");
        device.setDeviceType("GATEWAY");
        device.setCriticality("HIGH");
        device.setTemperature(40.0);
        device.setCpuUsage(50);
        device.setMemoryUsage(50);
        device.setErrorCount(10);
        device.setConnectionLost(true);
        device.setLastHeartbeatSeconds(600);
        device.setBatteryLevel(15);
        device.setVibrationLevel(0.5);

        AlarmDecision decision = service.evaluate(device);

        Assert.assertEquals(51, decision.getAlarmScore());
        Assert.assertEquals("MAJOR", decision.getAlarmLevel());
        Assert.assertEquals("NETWORK", decision.getAlarmType());
        Assert.assertTrue(decision.isNeedDispatch());
        Assert.assertEquals("P1", decision.getDispatchPriority());
        Assert.assertEquals("SMS", decision.getNotifyChannel());
        Assert.assertEquals("ISOLATE", decision.getAutoRemediation());
        Assert.assertEquals(15, decision.getEscalateMinutes());
        Assert.assertTrue(decision.getIndicators().contains("设备断连"));
        Assert.assertTrue(decision.getIndicators().contains("电量 15%"));
    }

    /**
     * 性能资源耗尽：内存 92% + 错误 6 次，但设备重要性 MEDIUM → 22 分 MINOR 且不派单
     */
    @Test
    public void shouldFlagPerformanceIssueWithoutDispatch() {
        DeviceAlarm device = new DeviceAlarm();
        device.setDeviceId("DEV-9003");
        device.setDeviceName("图像识别节点");
        device.setDeviceType("CAMERA");
        device.setCriticality("MEDIUM");
        device.setTemperature(45.0);
        device.setCpuUsage(88);
        device.setMemoryUsage(92);
        device.setErrorCount(6);
        device.setConnectionLost(false);
        device.setLastHeartbeatSeconds(30);
        device.setBatteryLevel(60);
        device.setVibrationLevel(1.0);

        AlarmDecision decision = service.evaluate(device);

        Assert.assertEquals(22, decision.getAlarmScore());
        Assert.assertEquals("MINOR", decision.getAlarmLevel());
        Assert.assertEquals("PERFORMANCE", decision.getAlarmType());
        Assert.assertFalse(decision.isNeedDispatch());
        Assert.assertEquals("P3", decision.getDispatchPriority());
        Assert.assertEquals("IM", decision.getNotifyChannel());
        Assert.assertEquals("RATE_LIMIT", decision.getAutoRemediation());
        Assert.assertEquals(60, decision.getEscalateMinutes());
        Assert.assertTrue(decision.getIndicators().contains("内存使用率 92%"));
    }

    /**
     * 低电量：电量 3% + 错误 6 次，关键设备 → 25 分 MINOR 且按 P2 派单
     */
    @Test
    public void shouldAlertLowBatteryOnCriticalDevice() {
        DeviceAlarm device = new DeviceAlarm();
        device.setDeviceId("DEV-9004");
        device.setDeviceName("冷库温湿度记录仪");
        device.setDeviceType("SENSOR");
        device.setCriticality("HIGH");
        device.setTemperature(50.0);
        device.setCpuUsage(40);
        device.setMemoryUsage(40);
        device.setErrorCount(6);
        device.setConnectionLost(false);
        device.setLastHeartbeatSeconds(50);
        device.setBatteryLevel(3);
        device.setVibrationLevel(0.2);

        AlarmDecision decision = service.evaluate(device);

        Assert.assertEquals(25, decision.getAlarmScore());
        Assert.assertEquals("MINOR", decision.getAlarmLevel());
        Assert.assertEquals("POWER", decision.getAlarmType());
        Assert.assertTrue(decision.isNeedDispatch());
        Assert.assertEquals("P2", decision.getDispatchPriority());
        Assert.assertEquals("IM", decision.getNotifyChannel());
        Assert.assertEquals("NONE", decision.getAutoRemediation());
        Assert.assertTrue(decision.getIndicators().contains("电量 3%"));
    }

    /**
     * 设备正常：各项指标健康 → 0 分 NORMAL
     */
    @Test
    public void shouldKeepHealthyDeviceNormal() {
        DeviceAlarm device = new DeviceAlarm();
        device.setDeviceId("DEV-9005");
        device.setDeviceName("办公区环境传感器");
        device.setDeviceType("SENSOR");
        device.setCriticality("MEDIUM");
        device.setTemperature(42.0);
        device.setCpuUsage(30);
        device.setMemoryUsage(40);
        device.setErrorCount(0);
        device.setConnectionLost(false);
        device.setLastHeartbeatSeconds(30);
        device.setBatteryLevel(85);
        device.setVibrationLevel(0.5);

        AlarmDecision decision = service.evaluate(device);

        Assert.assertEquals(0, decision.getAlarmScore());
        Assert.assertEquals("NORMAL", decision.getAlarmLevel());
        Assert.assertEquals("NONE", decision.getAlarmType());
        Assert.assertFalse(decision.isNeedDispatch());
        Assert.assertEquals("P3", decision.getDispatchPriority());
        Assert.assertEquals("EMAIL", decision.getNotifyChannel());
        Assert.assertEquals("NONE", decision.getAutoRemediation());
        Assert.assertEquals(240, decision.getEscalateMinutes());
        Assert.assertTrue(decision.getIndicators().isEmpty());
        Assert.assertTrue(decision.getActions().contains("暂不派单，持续观察指标趋势"));
    }

    /**
     * 振动异常：振动强度 9.5 + 错误 10 次 → 38 分 MINOR，自动限流
     */
    @Test
    public void shouldDetectAbnormalVibration() {
        DeviceAlarm device = new DeviceAlarm();
        device.setDeviceId("DEV-9006");
        device.setDeviceName("传送带电机");
        device.setDeviceType("ROBOT");
        device.setCriticality("MEDIUM");
        device.setTemperature(55.0);
        device.setCpuUsage(70);
        device.setMemoryUsage(65);
        device.setErrorCount(10);
        device.setConnectionLost(false);
        device.setLastHeartbeatSeconds(40);
        device.setBatteryLevel(70);
        device.setVibrationLevel(9.5);

        AlarmDecision decision = service.evaluate(device);

        Assert.assertEquals(38, decision.getAlarmScore());
        Assert.assertEquals("MINOR", decision.getAlarmLevel());
        Assert.assertEquals("VIBRATION", decision.getAlarmType());
        Assert.assertFalse(decision.isNeedDispatch());
        Assert.assertEquals("P3", decision.getDispatchPriority());
        Assert.assertEquals("IM", decision.getNotifyChannel());
        Assert.assertEquals("THROTTLE", decision.getAutoRemediation());
        Assert.assertTrue(decision.getIndicators().contains("振动强度 9.5"));
        Assert.assertTrue(decision.getActions().contains("执行自动处置：THROTTLE"));
    }
}
