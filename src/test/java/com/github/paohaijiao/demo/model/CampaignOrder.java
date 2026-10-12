package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 营销活动订单的刷单风险要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignOrder {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 下单用户
     */
    private String userId;

    /**
     * 活动编号
     */
    private String campaignId;

    /**
     * 账户注册天数
     */
    private int accountAgeDays;

    /**
     * 近 1 小时下单数
     */
    private int orderCount1h;

    /**
     * 近 24 小时下单数
     */
    private int orderCount24h;

    /**
     * 24 小时内同设备关联账户数
     */
    private int deviceAccountCount24h;

    /**
     * 24 小时内同 IP 关联账户数
     */
    private int ipAccountCount24h;

    /**
     * 7 天内使用优惠券的订单数
     */
    private int couponOrderCount7d;

    /**
     * 历史空包率（整数百分比）
     */
    private int emptyPackageRate;

    /**
     * 本单客单价相对历史均单的偏离率（整数百分比，如 400 表示高出 4 倍）
     */
    private int amountDeviationPercent;

    /**
     * 是否已实名认证
     */
    private boolean realName;

    /**
     * 付款人与下单人实名是否一致
     */
    private boolean paymentConsistent;

    /**
     * 收货地址是否为虚拟/集中代收点
     */
    private boolean virtualAddress;
}
