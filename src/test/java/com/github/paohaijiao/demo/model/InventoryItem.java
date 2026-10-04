package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 库存商品要素（滞销定价所需的商品与库存快照）
 * <p>
 * 字段来源于 WMS/ERP 的商品主数据与库存周转报表。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {

    /**
     * 商品编码
     */
    private String sku;

    /**
     * 商品名称
     */
    private String skuName;

    /**
     * 品类
     */
    private String category;

    /**
     * 库龄（天）
     */
    private int stockAgeDays;

    /**
     * 当前库存数量
     */
    private int stockQty;

    /**
     * 成本价（元）
     */
    private double costPrice;

    /**
     * 当前售价（元）
     */
    private double currentPrice;

    /**
     * 近 30 天销量
     */
    private int recentSalesQty30d;

    /**
     * 季节系数：PEAK-旺季 / NORMAL-平季 / OFF-淡季
     */
    private String seasonFactor;

    /**
     * 是否临期/易腐商品
     */
    private boolean perishable;

    /**
     * 仓库类型：MAIN-自营仓 / OUTSOURCE-外包仓
     */
    private String warehouseType;

    /**
     * 最低毛利保护线（如 0.1 表示不低于 10% 毛利）
     */
    private double marginFloor;
}
