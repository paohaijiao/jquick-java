package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户近 90 天行为与价值要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBehavior {

    /**
     * 用户号
     */
    private String userId;

    /**
     * 最近一次成交距今天数
     */
    private int lastOrderDays;

    /**
     * 90 天成交订单数
     */
    private int orderCount90d;

    /**
     * 90 天成交金额（元）
     */
    private double gmv90d;

    /**
     * 历史平均客单价（元）
     */
    private double avgOrderAmount;

    /**
     * 近 30 天有浏览行为的天数
     */
    private int browseDays30d;

    /**
     * 优惠券订单占比（整数百分比）
     */
    private int couponSensitivity;

    /**
     * 退货率（整数百分比）
     */
    private int returnRate;

    /**
     * 会员等级序位（S1=1 ... S5=5）
     */
    private int memberRank;
}
