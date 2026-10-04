package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 12：产线质检多指标判定规则集
 * <p>
 * 规则按「缺陷扣分 + 加扣项修正 → 等级判定 → 处置与 CAPA」拆分：
 * <ul>
 *     <li>扣分类：致命/严重/轻微缺陷、外观、关键尺寸、设备状态</li>
 *     <li>决策类：质量等级、处置去向、纠正措施、放行许可</li>
 * </ul>
 * 规则脚本见 {@code src/test/resources/demo/quality-inspection.xml}。
 */
public interface QualityInspectionRules {

    int defectRatePermil(@Param("totalDefects") int totalDefects,
                         @Param("sampleSize") int sampleSize);

    boolean dimensionPass(@Param("measuredSize") double measuredSize,
                          @Param("targetSize") double targetSize,
                          @Param("tolerance") double tolerance);

    int scoreDefects(@Param("criticalDefects") int criticalDefects,
                     @Param("majorDefects") int majorDefects,
                     @Param("minorDefects") int minorDefects);

    int scoreAppearance(@Param("surfaceScore") int surfaceScore);

    int scoreDimension(@Param("dimensionPass") boolean dimensionPass);

    int scoreEquipment(@Param("equipmentStatus") String equipmentStatus);

    String qualityLevel(@Param("qualityScore") int qualityScore,
                        @Param("criticalDefects") int criticalDefects,
                        @Param("defectRatePermil") int defectRatePermil);

    String disposition(@Param("qualityLevel") String qualityLevel);

    boolean needCapa(@Param("criticalDefects") int criticalDefects,
                     @Param("majorDefects") int majorDefects,
                     @Param("equipmentWarning") boolean equipmentWarning);

    boolean releaseAllowed(@Param("qualityLevel") String qualityLevel,
                           @Param("dimensionPass") boolean dimensionPass);
}
