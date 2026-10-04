package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 32：车间能耗偏离率、单耗评级与温度异常规则
 */
public interface WorkshopEnergyRules {

    /**
     * 用电同比偏离率（%）
     */
    int powerDeviationPercent(@Param("total") int total,
                              @Param("baseline") int baseline);

    /**
     * 单位产品电耗评级（交叉相乘：total*100 对 标准耗电*阈值）
     */
    String efficiencyLevel(@Param("total") int total,
                           @Param("output") int output,
                           @Param("standardPerUnit") int standardPerUnit);

    /**
     * 需量超合同率（%）
     */
    int demandExcessPercent(@Param("maxDemand") int maxDemand,
                            @Param("contract") int contract);

    /**
     * 用水同比偏离率（%）
     */
    int waterDeviationPercent(@Param("usage") int usage,
                              @Param("baseline") int baseline);

    /**
     * 恒温车间温度是否超出 18-26℃ 工艺区间
     */
    boolean temperatureAbnormal(@Param("controlled") boolean controlled,
                                @Param("temperature") int temperature);
}
