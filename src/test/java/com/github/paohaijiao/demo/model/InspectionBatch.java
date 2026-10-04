package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 产线批次质检记录（抽检指标与实测数据快照）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionBatch {

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 车间
     */
    private String workshop;

    /**
     * 抽检数量
     */
    private int sampleSize;

    /**
     * 致命缺陷数（安全/功能丧失）
     */
    private int criticalDefects;

    /**
     * 严重缺陷数
     */
    private int majorDefects;

    /**
     * 轻微缺陷数
     */
    private int minorDefects;

    /**
     * 关键尺寸实测值
     */
    private double measuredSize;

    /**
     * 关键尺寸标称值
     */
    private double targetSize;

    /**
     * 允许偏差（绝对值）
     */
    private double tolerance;

    /**
     * 外观评分（0-100）
     */
    private int surfaceScore;

    /**
     * 设备状态：NORMAL / WARNING
     */
    private String equipmentStatus;

    /**
     * 是否首件检验
     */
    private boolean firstArticle;
}
