package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分销订单与各级分销商身份要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionOrder {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 商品编码
     */
    private String sku;

    /**
     * 实付金额（元）
     */
    private double orderAmount;

    /**
     * 商品类目：BEAUTY 美妆 / STANDARD 标品 / FRESH 生鲜 / APPLIANCE 家电
     */
    private String productCategory;

    /**
     * 成交分销商等级：L1 初级 / L2 中级 / L3 高级 / PARTNER 合伙人
     */
    private String distributorLevel;

    /**
     * 直推上级（一级上线）等级
     */
    private String uplineLevel;

    /**
     * 间推上级（二级上线）等级
     */
    private String rootLevel;

    /**
     * 是否团长
     */
    private boolean groupLeader;

    /**
     * 近 30 天成团数
     */
    private int groupCount30d;

    /**
     * 活动让利占比（整数百分比）
     */
    private int promotionCostRate;

    /**
     * 订单后续是否发生退款
     */
    private boolean refunded;
}
