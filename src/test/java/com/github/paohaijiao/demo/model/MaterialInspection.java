package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 来料检验（IQC）抽检结果要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialInspection {

    /**
     * 来料批次号
     */
    private String batchNo;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料类别：ELECTRONIC 电子件 / METAL 金属件 / CHEMICAL 化工 / PLASTIC 塑胶
     */
    private String materialCategory;

    /**
     * 抽检样本数
     */
    private int sampleSize;

    /**
     * 致命缺陷数（安全/功能零容忍）
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
     * 是否随附质保书/COA
     */
    private boolean certificateProvided;

    /**
     * 外观检验是否合格
     */
    private boolean appearancePass;

    /**
     * 关键尺寸检验是否合格
     */
    private boolean dimensionPass;

    /**
     * RoHS/环保合规是否通过
     */
    private boolean environmentalCompliant;

    /**
     * 供应商评级：A / B / C
     */
    private String supplierGrade;
}
