package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 27：售后退款/退货/换货资格规则
 */
public interface AfterSalesRules {

    /**
     * 七天无理由退货资格：7 天内、未使用、密封完好、不影响二次销售
     */
    boolean sevenDayEligible(@Param("days") int days,
                             @Param("unused") boolean unused,
                             @Param("sealed") boolean sealed,
                             @Param("impact") boolean impact);

    /**
     * 质量问题退货资格：15 天异议期内，且有凭证或商家认责
     */
    boolean qualityEligible(@Param("days") int days,
                            @Param("proof") boolean proof,
                            @Param("shopResponsible") boolean shopResponsible);

    /**
     * 换货资格：尺码问题 15 天内，描述不符（商家责任）30 天内
     */
    boolean exchangeEligible(@Param("reason") String reason,
                             @Param("days") int days);

    /**
     * 运费承担方判定
     */
    String freightPayer(@Param("reason") String reason);
}
