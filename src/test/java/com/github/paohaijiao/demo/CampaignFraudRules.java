package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 26：营销活动订单防刷单六维风险评分
 */
public interface CampaignFraudRules {

    /**
     * 账户龄风险（0-20）
     */
    int scoreAccountAge(@Param("days") int days);

    /**
     * 下单频次风险（0-25）：1 小时档 + 24 小时档
     */
    int scoreFrequency(@Param("count1h") int count1h,
                       @Param("count24h") int count24h);

    /**
     * 设备/IP 聚集风险（0-20）
     */
    int scoreDevice(@Param("deviceAccounts") int deviceAccounts,
                    @Param("ipAccounts") int ipAccounts);

    /**
     * 身份与地址风险（0-15）
     */
    int scoreIdentity(@Param("realName") boolean realName,
                      @Param("paymentConsistent") boolean paymentConsistent,
                      @Param("virtualAddress") boolean virtualAddress);

    /**
     * 券单与空包风险（0-10）
     */
    int scoreCoupon(@Param("couponOrders") int couponOrders,
                    @Param("emptyRate") int emptyRate);

    /**
     * 客单价偏离风险（0-10）
     */
    int scoreDeviation(@Param("deviationPercent") int deviationPercent);
}
