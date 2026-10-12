package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 7（生产级样板）：零售库存补货规则集
 * <p>
 * 规则按「需求测算 → 缺口计算 → 下单约束 → 紧急度」拆分：
 * <ul>
 *     <li>测算类：提前期需求、动态安全库存、目标库存</li>
 *     <li>约束类：MOQ、包装规格取整（规则内演示 while 循环）、单次上限</li>
 *     <li>决策类：可售天数、紧急度、是否加急</li>
 * </ul>
 * 规则脚本见 {@code src/test/resources/demo/inventory.xml}。
 */
public interface InventoryRules {

    /**
     * 补货提前期需求 = 日均销量 × 提前期
     */
    int demandDuringLeadTime(@Param("avgDailySales") int avgDailySales,
                             @Param("leadTimeDays") int leadTimeDays);

    /**
     * 动态安全库存：促销期与低履约率供应商需要更高安全库存
     */
    int safetyStockLevel(@Param("safetyStock") int safetyStock,
                         @Param("avgDailySales") int avgDailySales,
                         @Param("supplierReliability") double supplierReliability,
                         @Param("promotion") boolean promotion);

    /**
     * 目标库存：安全库存 + 提前期需求，并按季节系数修正
     */
    int targetStock(@Param("safetyStock") int safetyStock,
                    @Param("demandDuringLeadTime") int demandDuringLeadTime,
                    @Param("seasonFactor") String seasonFactor,
                    @Param("avgDailySales") int avgDailySales);

    /**
     * 库存缺口
     */
    int shortage(@Param("targetStock") int targetStock,
                 @Param("currentStock") int currentStock);

    /**
     * 最小起订量约束
     */
    int adjustToMoq(@Param("qty") int qty,
                    @Param("moq") int moq);

    /**
     * 包装规格向上取整（while 循环示例）
     */
    int roundToPack(@Param("qty") int qty,
                    @Param("packSize") int packSize);

    /**
     * 可售天数
     */
    int stockDays(@Param("currentStock") int currentStock,
                  @Param("avgDailySales") int avgDailySales);

    /**
     * 紧急度：CRITICAL / HIGH / MEDIUM / LOW
     */
    String urgency(@Param("stockDays") int stockDays,
                   @Param("leadTimeDays") int leadTimeDays);

    /**
     * 是否建议加急补货
     */
    boolean suggestExpress(@Param("stockDays") int stockDays,
                           @Param("leadTimeDays") int leadTimeDays,
                           @Param("seasonFactor") String seasonFactor);

    /**
     * 单次下单量上限
     */
    int cappedQty(@Param("qty") int qty,
                  @Param("maxOrder") int maxOrder);
}
