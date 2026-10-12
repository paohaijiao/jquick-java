package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 31：来料检验缺陷率分档与单据/外观尺寸评分
 */
public interface IncomingMaterialRules {

    /**
     * 严重缺陷率得分（0-30）：0 个满分；比率与阈值交叉相乘
     */
    int scoreMajor(@Param("major") int major,
                   @Param("sample") int sample);

    /**
     * 轻微缺陷率得分（0-20）
     */
    int scoreMinor(@Param("minor") int minor,
                   @Param("sample") int sample);

    /**
     * 质保书/COA 得分（0-15）
     */
    int scoreCertificate(@Param("provided") boolean provided);

    /**
     * 外观得分（0-15）
     */
    int scoreAppearance(@Param("pass") boolean pass);

    /**
     * 尺寸得分（0-10）
     */
    int scoreDimension(@Param("pass") boolean pass);

    /**
     * 环保合规得分（0-10）
     */
    int scoreEnvironmental(@Param("compliant") boolean compliant);

    /**
     * 按综合质量分给出初判
     */
    String inspectionLevel(@Param("totalScore") int totalScore);
}
