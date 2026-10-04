package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.WorkshopEnergyRules;
import com.github.paohaijiao.demo.model.WorkshopEnergyAlert;
import com.github.paohaijiao.demo.model.WorkshopEnergyRecord;

/**
 * 场景 32：车间能耗多信号聚合预警
 */
public class WorkshopEnergyAlertService {

    private static final int LEVEL_NORMAL = 0;
    private static final int LEVEL_INFO = 1;
    private static final int LEVEL_WARNING = 2;
    private static final int LEVEL_CRITICAL = 3;

    private final WorkshopEnergyRules rules;

    public WorkshopEnergyAlertService(WorkshopEnergyRules rules) {
        this.rules = rules;
    }

    public WorkshopEnergyAlert evaluate(WorkshopEnergyRecord r) {
        WorkshopEnergyAlert alert = new WorkshopEnergyAlert();
        alert.setWorkshopId(r.getWorkshopId());
        alert.setEfficiencyLevel("NORMAL");

        if (!r.isMeterOnline()) {
            alert.setAlertLevel("CRITICAL");
            alert.addSignal("能耗计量表离线，数据采集中断，须立即检修计量回路");
            return alert;
        }

        int level = LEVEL_NORMAL;

        if ("STOPPED".equals(r.getProductionLineStatus())
                && r.getTotalKwh() > r.getBaselineKwh() * 0.1) {
            level = upgrade(level, LEVEL_CRITICAL);
            alert.addSignal("产线已停线但仍有 " + r.getTotalKwh()
                    + "kWh 用电（超基准 10%），疑似设备空转或偷漏电");
        }

        int powerDev = rules.powerDeviationPercent(r.getTotalKwh(), r.getBaselineKwh());
        alert.setPowerDeviationPercent(powerDev);
        if (powerDev >= 30) {
            level = upgrade(level, LEVEL_CRITICAL);
            alert.addSignal("用电同比偏高 " + powerDev + "%，超过 30% 紧急线，须立即排查高耗能设备");
        } else if (powerDev >= 15) {
            level = upgrade(level, LEVEL_WARNING);
            alert.addSignal("用电同比偏高 " + powerDev + "%，关注排产与设备能效");
        }

        if ("RUNNING".equals(r.getProductionLineStatus())) {
            String efficiency = rules.efficiencyLevel(r.getTotalKwh(),
                    r.getOutputQty(), r.getStandardKwhPerUnit());
            alert.setEfficiencyLevel(efficiency);
            if ("WASTE".equals(efficiency)) {
                if (r.getOutputQty() == 0) {
                    level = upgrade(level, LEVEL_CRITICAL);
                    alert.addSignal("本班有能耗但产量为 0，能源完全空耗");
                } else {
                    level = upgrade(level, LEVEL_WARNING);
                    alert.addSignal("单位产品电耗超标准 20%，能效评级 WASTE");
                }
            } else if ("ATTENTION".equals(efficiency)) {
                level = upgrade(level, LEVEL_INFO);
                alert.addSignal("单位产品电耗超标准 5%，能效评级 ATTENTION");
            }
        }

        int demandExcess = rules.demandExcessPercent(r.getMaxDemandKw(), r.getDemandContractKw());
        alert.setDemandExcessPercent(demandExcess);
        if (demandExcess >= 10) {
            level = upgrade(level, LEVEL_CRITICAL);
            alert.addSignal("最大需量超合同 " + demandExcess + "%，面临需量电费加价，立即错峰");
        } else if (demandExcess > 0) {
            level = upgrade(level, LEVEL_WARNING);
            alert.addSignal("最大需量超合同 " + demandExcess + "%，接近加价线");
        }

        int waterDev = rules.waterDeviationPercent(r.getWaterUsageTon(), r.getWaterBaselineTon());
        alert.setWaterDeviationPercent(waterDev);
        if (waterDev >= 100) {
            level = upgrade(level, LEVEL_CRITICAL);
            alert.addSignal("用水同比偏高 " + waterDev + "%，疑似供水管网泄漏，立即巡线");
        } else if (waterDev >= 50) {
            level = upgrade(level, LEVEL_WARNING);
            alert.addSignal("用水同比偏高 " + waterDev + "%，核查冷却水与清洗用水");
        }

        if (!r.isGasPressureOk()) {
            level = upgrade(level, LEVEL_WARNING);
            alert.addSignal("燃气压力异常，可能影响加热炉工艺稳定性");
        }

        if (rules.temperatureAbnormal(r.isTempControlled(), r.getTemperature())) {
            level = upgrade(level, LEVEL_WARNING);
            alert.addSignal("恒温车间实测 " + r.getTemperature()
                    + "℃，超出 18-26℃ 工艺区间，检查空调系统");
        }

        alert.setAlertLevel(levelName(level));
        return alert;
    }

    private int upgrade(int current, int candidate) {
        return candidate > current ? candidate : current;
    }

    private String levelName(int level) {
        if (level >= LEVEL_CRITICAL) {
            return "CRITICAL";
        }
        if (level >= LEVEL_WARNING) {
            return "WARNING";
        }
        if (level >= LEVEL_INFO) {
            return "INFO";
        }
        return "NORMAL";
    }
}
