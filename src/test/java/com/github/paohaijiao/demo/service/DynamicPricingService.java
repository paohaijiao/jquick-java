package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.DynamicPricingRules;
import com.github.paohaijiao.demo.model.InventoryItem;
import com.github.paohaijiao.demo.model.PricingDecision;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 场景 4：库存滞销动态定价服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：折扣聚合、上下限封顶、毛利保护复核、促销说明组装、金额精度处理</li>
 *     <li><b>规则侧</b>：库龄基准折扣、各类修正系数、清仓等级与促销路由，全部外置在 XML 中</li>
 * </ul>
 */
public class DynamicPricingService {

    /**
     * 折扣下限，避免击穿成本
     */
    private static final double MIN_DISCOUNT = 0.30;

    /**
     * 折扣上限，不允许加价
     */
    private static final double MAX_DISCOUNT = 1.0;

    private final DynamicPricingRules rules;

    public DynamicPricingService(DynamicPricingRules rules) {
        this.rules = rules;
    }

    /**
     * 计算动态定价
     *
     * @param item 商品与库存快照
     * @return 定价决策
     */
    public PricingDecision price(InventoryItem item) {
        PricingDecision decision = new PricingDecision();
        decision.setSku(item.getSku());

        int ageLevel = rules.ageLevel(item.getStockAgeDays());
        double base = rules.baseDiscount(item.getStockAgeDays());
        double discount = base
                + rules.slowMovingFactor(item.getRecentSalesQty30d(), item.getStockQty())
                + rules.seasonFactor(item.getSeasonFactor())
                + rules.perishableFactor(item.isPerishable(), item.getStockAgeDays())
                + rules.warehouseFactor(item.getWarehouseType());
        if (discount < MIN_DISCOUNT) {
            discount = MIN_DISCOUNT;
        } else if (discount > MAX_DISCOUNT) {
            discount = MAX_DISCOUNT;
        }

        String clearance = rules.clearanceLevel(discount);
        double suggested = scale2(item.getCurrentPrice() * discount);
        double floorPrice = rules.applyFloor(suggested, item.getCostPrice(), item.getMarginFloor());
        boolean floorApplied = floorPrice > suggested;
        double finalPrice = scale2(floorPrice);

        decision.setAgeLevel(ageLevel);
        decision.setBaseDiscount(base);
        decision.setFinalDiscount(scale4(discount));
        decision.setSuggestedPrice(finalPrice);
        decision.setClearanceLevel(clearance);
        decision.setPromotionType(rules.promotionType(clearance, item.isPerishable()));
        decision.setNeedClearance(rules.needClearance(item.getStockAgeDays(), item.getStockQty(),
                item.getRecentSalesQty30d()));
        if (finalPrice > 0.0) {
            decision.setGrossMarginRate(scale4((finalPrice - item.getCostPrice()) / finalPrice));
        }

        buildNotes(item, decision, floorApplied);
        return decision;
    }

    private void buildNotes(InventoryItem item, PricingDecision decision, boolean floorApplied) {
        if (floorApplied) {
            decision.addNote("清仓折扣触及最低毛利保护线，按保护价执行");
        }
        if (decision.isNeedClearance()) {
            decision.addNote("建议纳入清仓池，当前库龄 " + item.getStockAgeDays() + " 天");
        }
        if (item.isPerishable() && item.getStockAgeDays() >= 7) {
            decision.addNote("临期商品，优先通过闪购与打包方式清货");
        }
        if ("OUTSOURCE".equals(item.getWarehouseType())) {
            decision.addNote("外包仓商品，优先清库以降低仓储成本");
        }
        if (decision.getFinalDiscount() <= MIN_DISCOUNT) {
            decision.addNote("折扣已触及下限 30%，请评估损失后处理");
        }
    }

    private double scale2(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private double scale4(double value) {
        return BigDecimal.valueOf(value).setScale(4, RoundingMode.HALF_UP).doubleValue();
    }
}
