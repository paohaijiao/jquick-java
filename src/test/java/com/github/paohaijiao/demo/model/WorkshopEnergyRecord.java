package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 车间班次能耗与工况要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkshopEnergyRecord {

    /**
     * 车间编号
     */
    private String workshopId;

    /**
     * 车间名称
     */
    private String workshopName;

    /**
     * 班次：DAY 白班 / MIDDLE 中班 / NIGHT 夜班
     */
    private String shift;

    /**
     * 本班总用电（kWh）
     */
    private int totalKwh;

    /**
     * 历史同期基准用电（kWh）
     */
    private int baselineKwh;

    /**
     * 本班产量
     */
    private int outputQty;

    /**
     * 单位产品标准电耗（kWh/件）
     */
    private int standardKwhPerUnit;

    /**
     * 本班用水量（吨）
     */
    private int waterUsageTon;

    /**
     * 用水基准（吨）
     */
    private int waterBaselineTon;

    /**
     * 本班最大需量（kW）
     */
    private int maxDemandKw;

    /**
     * 合同约定需量（kW）
     */
    private int demandContractKw;

    /**
     * 是否恒温车间
     */
    private boolean tempControlled;

    /**
     * 车间温度（℃）
     */
    private int temperature;

    /**
     * 燃气压力是否正常
     */
    private boolean gasPressureOk;

    /**
     * 能耗计量表是否在线
     */
    private boolean meterOnline;

    /**
     * 产线状态：RUNNING 运行 / STOPPED 停线
     */
    private String productionLineStatus;
}
