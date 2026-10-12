package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.InventoryRules;
import com.github.paohaijiao.demo.model.ReplenishDecision;
import com.github.paohaijiao.demo.model.ReplenishmentItem;

/**
 * 场景 7：零售库存补货建议服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：链路串联（需求 → 安全库存 → 目标 → 缺口 → 约束 → 上限）、建议组装</li>
 *     <li><b>规则侧</b>：销量测算口径、安全库存策略、季节系数、取整规则、紧急度阈值，全部外置在 XML 中</li>
 * </ul>
 */
public class ReplenishmentService {

    private final InventoryRules rules;

    public ReplenishmentService(InventoryRules rules) {
        this.rules = rules;
    }

    /**
     * 计算补货建议
     *
     * @param item 商品与库存快照
     * @return 补货决策
     */
    public ReplenishDecision replenish(ReplenishmentItem item) {
        ReplenishDecision decision = new ReplenishDecision();
        decision.setSku(item.getSku());

        int ddlt = rules.demandDuringLeadTime(item.getAvgDailySales(), item.getLeadTimeDays());
        int safety = rules.safetyStockLevel(item.getSafetyStock(), item.getAvgDailySales(),
                item.getSupplierReliability(), item.isPromotion());
        int target = rules.targetStock(safety, ddlt, item.getSeasonFactor(), item.getAvgDailySales());
        int shortage = rules.shortage(target, item.getCurrentStock());
        int roundedQty = rules.roundToPack(rules.adjustToMoq(shortage, item.getMoq()), item.getPackSize());
        int orderQty = rules.cappedQty(roundedQty, item.getMaxOrder());
        int stockDays = rules.stockDays(item.getCurrentStock(), item.getAvgDailySales());
        String urgency = rules.urgency(stockDays, item.getLeadTimeDays());

        decision.setDemandDuringLeadTime(ddlt);
        decision.setSafetyStockLevel(safety);
        decision.setTargetStock(target);
        decision.setShortage(shortage);
        decision.setOrderQty(orderQty);
        decision.setStockDays(stockDays);
        decision.setUrgency(urgency);
        decision.setSuggestExpress(rules.suggestExpress(stockDays, item.getLeadTimeDays(), item.getSeasonFactor()));

        buildNotes(item, decision, roundedQty, orderQty);
        return decision;
    }

    private void buildNotes(ReplenishmentItem item, ReplenishDecision decision, int roundedQty, int orderQty) {
        if (decision.getShortage() == 0) {
            decision.addNote("库存充足，本次无需补货");
            return;
        }
        if (roundedQty > decision.getShortage()) {
            decision.addNote("按 MOQ/整箱包装取整后下单量高于库存缺口，属正常约束");
        }
        if (orderQty < roundedQty) {
            decision.addNote("单次下单量受上限约束封顶为 " + orderQty + "，超出部分建议拆单");
        }
        if (decision.getShortage() < item.getMoq()) {
            decision.addNote("缺口低于最小起订量，按 MOQ 下单");
        }
        if (item.isPromotion()) {
            decision.addNote("促销期需求上浮，安全库存已自动抬升");
        }
        if (item.getSupplierReliability() < 0.9) {
            decision.addNote("供应商履约率 " + item.getSupplierReliability() + " 偏低，安全库存已上调");
        }
        if ("PEAK".equals(item.getSeasonFactor())) {
            decision.addNote("旺季备货，目标库存上调 7 天销量");
        }
        if (decision.isSuggestExpress()) {
            decision.addNote("可售天数不足提前期，建议加急补货");
        }
    }
}
