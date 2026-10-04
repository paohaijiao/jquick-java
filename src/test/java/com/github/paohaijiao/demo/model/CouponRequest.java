package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 优惠券发放请求（发券决策所需的用户与订单快照）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequest {

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 会员等级：NORMAL / SILVER / GOLD / VIP
     */
    private String memberLevel;

    /**
     * 本次订单金额
     */
    private double orderAmount;

    /**
     * 历史订单数
     */
    private int orderCount;

    /**
     * 商品品类：DIGITAL / FRESH / APPAREL / GENERAL
     */
    private String category;

    /**
     * 注册天数
     */
    private int joinDays;

    /**
     * 距上次下单天数
     */
    private int lastOrderDays;

    /**
     * 历史客单价
     */
    private double avgOrderAmount;

    /**
     * 是否新客
     */
    private boolean newUser;

    /**
     * 是否已领取过本活动券
     */
    private boolean hasUsedCoupon;

    /**
     * 活动是否在有效期
     */
    private boolean campaignActive;

    /**
     * 券库存余量
     */
    private int stockLeft;

    /**
     * 领取渠道：APP / WEB / MINI
     */
    private String channel;

    /**
     * 风控标记
     */
    private boolean riskFlag;
}
