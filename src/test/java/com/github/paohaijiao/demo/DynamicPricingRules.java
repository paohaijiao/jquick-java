package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 4（生产级样板）：库存滞销动态定价规则集
 * <p>
 * 规则按「库龄基准折扣 + 多维修正 + 促销路由」拆分：
 * <ul>
 *     <li>折扣类：库龄基准、周转、季节、临期、仓储五类修正</li>
 *     <li>决策类：清仓等级、促销方式、是否清仓、最低毛利保护</li>
 * </ul>
 * 规则脚本见 {@code src/test/resources/demo/dynamic-pricing.xml}。
 */
public interface DynamicPricingRules {

    /**
     * 库龄分级：0-4（<30 / 30-59 / 60-89 / 90-179 / >=180）
     */
    int ageLevel(@Param("stockAgeDays") int stockAgeDays);

    /**
     * 库龄基准折扣
     */
    double baseDiscount(@Param("stockAgeDays") int stockAgeDays);

    /**
     * 周转修正：近 30 天销量 / 库存，越滞销折扣越低
     */
    double slowMovingFactor(@Param("recentSalesQty30d") int recentSalesQty30d,
                            @Param("stockQty") int stockQty);

    /**
     * 季节修正：淡季降价、旺季抬价
     */
    double seasonFactor(@Param("seasonFactor") String seasonFactor);

    /**
     * 临期修正：易腐商品额外降价
     */
    double perishableFactor(@Param("perishable") boolean perishable,
                            @Param("stockAgeDays") int stockAgeDays);

    /**
     * 仓储修正：外包仓额外降价
     */
    double warehouseFactor(@Param("warehouseType") String warehouseType);

    /**
     * 最低毛利保护：售价不得低于 成本价 ×（1 + marginFloor）
     */
    double applyFloor(@Param("price") double price,
                      @Param("costPrice") double costPrice,
                      @Param("marginFloor") double marginFloor);

    /**
     * 清仓等级：DEEP / MEDIUM / LIGHT / NONE
     */
    String clearanceLevel(@Param("finalDiscount") double finalDiscount);

    /**
     * 促销方式路由
     */
    String promotionType(@Param("clearanceLevel") String clearanceLevel,
                         @Param("perishable") boolean perishable);

    /**
     * 是否建议清仓：库龄 ≥ 90 天，或库龄 ≥ 60 天且可售月数 > 4
     */
    boolean needClearance(@Param("stockAgeDays") int stockAgeDays,
                          @Param("stockQty") int stockQty,
                          @Param("recentSalesQty30d") int recentSalesQty30d);
}
