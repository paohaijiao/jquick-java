package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 补货决策要素（零售补货所需的商品、库存与供应参数）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplenishmentItem {

    /**
     * 商品编码
     */
    private String sku;

    /**
     * 商品名称
     */
    private String skuName;

    /**
     * 仓库
     */
    private String warehouse;

    /**
     * 当前库存
     */
    private int currentStock;

    /**
     * 基础安全库存
     */
    private int safetyStock;

    /**
     * 日均销量
     */
    private int avgDailySales;

    /**
     * 补货提前期（天）
     */
    private int leadTimeDays;

    /**
     * 最小起订量
     */
    private int moq;

    /**
     * 包装规格（整箱数量）
     */
    private int packSize;

    /**
     * 供应商履约率（0-1）
     */
    private double supplierReliability;

    /**
     * 是否促销期
     */
    private boolean promotion;

    /**
     * 季节系数：PEAK-旺季 / NORMAL-平季 / OFF-淡季
     */
    private String seasonFactor;

    /**
     * 单次最大下单量
     */
    private int maxOrder;
}
