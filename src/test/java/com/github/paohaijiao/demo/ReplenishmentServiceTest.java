package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.ReplenishDecision;
import com.github.paohaijiao.demo.model.ReplenishmentItem;
import com.github.paohaijiao.demo.service.ReplenishmentService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 7 生产级样板：零售库存补货建议
 * <p>
 * 覆盖 6 组补货形态：紧急缺货、库存充足、促销旺季、淡季整箱取整、
 * 中等紧急、单次上限封顶。
 */
public class ReplenishmentServiceTest {

    private ReplenishmentService service;

    @Before
    public void setUp() {
        InventoryRules rules = JQuickJava.create()
                .createApi(InventoryRules.class, "demo/inventory.xml");
        this.service = new ReplenishmentService(rules);
    }

    /**
     * 紧急缺货：库存 20、日均 30、供应商履约率 0.8 → 目标 290，紧急 CRITICAL
     */
    @Test
    public void shouldUrgentlyReplenishShortage() {
        ReplenishmentItem item = new ReplenishmentItem();
        item.setSku("SKU-7001");
        item.setSkuName("矿泉水 550ml");
        item.setWarehouse("华东仓");
        item.setCurrentStock(20);
        item.setSafetyStock(50);
        item.setAvgDailySales(30);
        item.setLeadTimeDays(5);
        item.setMoq(50);
        item.setPackSize(10);
        item.setSupplierReliability(0.8);
        item.setPromotion(false);
        item.setSeasonFactor("NORMAL");
        item.setMaxOrder(5000);

        ReplenishDecision decision = service.replenish(item);

        Assert.assertEquals(150, decision.getDemandDuringLeadTime());
        Assert.assertEquals(140, decision.getSafetyStockLevel());
        Assert.assertEquals(290, decision.getTargetStock());
        Assert.assertEquals(270, decision.getShortage());
        Assert.assertEquals(270, decision.getOrderQty());
        Assert.assertEquals(0, decision.getStockDays());
        Assert.assertEquals("CRITICAL", decision.getUrgency());
        Assert.assertTrue(decision.isSuggestExpress());
    }

    /**
     * 库存充足：库存 500、目标 200 → 无需补货，紧急度 LOW
     */
    @Test
    public void shouldSkipWhenStockSufficient() {
        ReplenishmentItem item = new ReplenishmentItem();
        item.setSku("SKU-7002");
        item.setSkuName("卷纸");
        item.setWarehouse("华北仓");
        item.setCurrentStock(500);
        item.setSafetyStock(50);
        item.setAvgDailySales(30);
        item.setLeadTimeDays(5);
        item.setMoq(50);
        item.setPackSize(10);
        item.setSupplierReliability(0.95);
        item.setPromotion(false);
        item.setSeasonFactor("NORMAL");
        item.setMaxOrder(5000);

        ReplenishDecision decision = service.replenish(item);

        Assert.assertEquals(150, decision.getDemandDuringLeadTime());
        Assert.assertEquals(50, decision.getSafetyStockLevel());
        Assert.assertEquals(200, decision.getTargetStock());
        Assert.assertEquals(0, decision.getShortage());
        Assert.assertEquals(0, decision.getOrderQty());
        Assert.assertEquals(16, decision.getStockDays());
        Assert.assertEquals("LOW", decision.getUrgency());
        Assert.assertFalse(decision.isSuggestExpress());
        Assert.assertTrue(decision.getNotes().contains("库存充足，本次无需补货"));
    }

    /**
     * 促销旺季：低履约 + 促销 + 旺季，缺口 390 但 MOQ 500 → 按 MOQ 下单
     */
    @Test
    public void shouldBoostSafetyStockInPromotionPeak() {
        ReplenishmentItem item = new ReplenishmentItem();
        item.setSku("SKU-7003");
        item.setSkuName("空调扇");
        item.setWarehouse("华南仓");
        item.setCurrentStock(100);
        item.setSafetyStock(50);
        item.setAvgDailySales(20);
        item.setLeadTimeDays(7);
        item.setMoq(500);
        item.setPackSize(100);
        item.setSupplierReliability(0.85);
        item.setPromotion(true);
        item.setSeasonFactor("PEAK");
        item.setMaxOrder(5000);

        ReplenishDecision decision = service.replenish(item);

        Assert.assertEquals(140, decision.getDemandDuringLeadTime());
        Assert.assertEquals(210, decision.getSafetyStockLevel());
        Assert.assertEquals(490, decision.getTargetStock());
        Assert.assertEquals(390, decision.getShortage());
        Assert.assertEquals(500, decision.getOrderQty());
        Assert.assertEquals(5, decision.getStockDays());
        Assert.assertEquals("CRITICAL", decision.getUrgency());
        Assert.assertTrue(decision.getNotes().contains("促销期需求上浮，安全库存已自动抬升"));
    }

    /**
     * 淡季整箱取整：目标 110、缺口 50、包装 20 → 取整为 60
     */
    @Test
    public void shouldRoundUpToPackSize() {
        ReplenishmentItem item = new ReplenishmentItem();
        item.setSku("SKU-7004");
        item.setSkuName("暖宝宝");
        item.setWarehouse("华北仓");
        item.setCurrentStock(60);
        item.setSafetyStock(40);
        item.setAvgDailySales(10);
        item.setLeadTimeDays(10);
        item.setMoq(20);
        item.setPackSize(20);
        item.setSupplierReliability(0.98);
        item.setPromotion(false);
        item.setSeasonFactor("OFF");
        item.setMaxOrder(5000);

        ReplenishDecision decision = service.replenish(item);

        Assert.assertEquals(100, decision.getDemandDuringLeadTime());
        Assert.assertEquals(110, decision.getTargetStock());
        Assert.assertEquals(50, decision.getShortage());
        Assert.assertEquals(60, decision.getOrderQty());
        Assert.assertEquals(6, decision.getStockDays());
        Assert.assertEquals("CRITICAL", decision.getUrgency());
    }

    /**
     * 中等紧急：可售 6 天、提前期 3 天 → HIGH（提前期 +3 天内）
     */
    @Test
    public void shouldMarkHighUrgency() {
        ReplenishmentItem item = new ReplenishmentItem();
        item.setSku("SKU-7005");
        item.setSkuName("咖啡豆");
        item.setWarehouse("华东仓");
        item.setCurrentStock(30);
        item.setSafetyStock(40);
        item.setAvgDailySales(5);
        item.setLeadTimeDays(3);
        item.setMoq(10);
        item.setPackSize(5);
        item.setSupplierReliability(0.95);
        item.setPromotion(false);
        item.setSeasonFactor("NORMAL");
        item.setMaxOrder(1000);

        ReplenishDecision decision = service.replenish(item);

        Assert.assertEquals(15, decision.getDemandDuringLeadTime());
        Assert.assertEquals(55, decision.getTargetStock());
        Assert.assertEquals(25, decision.getShortage());
        Assert.assertEquals(25, decision.getOrderQty());
        Assert.assertEquals(6, decision.getStockDays());
        Assert.assertEquals("HIGH", decision.getUrgency());
        Assert.assertFalse(decision.isSuggestExpress());
    }

    /**
     * 单次上限封顶：缺口 2085、整箱取整 2100，但单次上限 1000 → 下单 1000
     */
    @Test
    public void shouldCapOrderQuantity() {
        ReplenishmentItem item = new ReplenishmentItem();
        item.setSku("SKU-7006");
        item.setSkuName("爆款零食");
        item.setWarehouse("华中仓");
        item.setCurrentStock(15);
        item.setSafetyStock(100);
        item.setAvgDailySales(400);
        item.setLeadTimeDays(5);
        item.setMoq(100);
        item.setPackSize(100);
        item.setSupplierReliability(0.9);
        item.setPromotion(false);
        item.setSeasonFactor("NORMAL");
        item.setMaxOrder(1000);

        ReplenishDecision decision = service.replenish(item);

        Assert.assertEquals(2000, decision.getDemandDuringLeadTime());
        Assert.assertEquals(100, decision.getSafetyStockLevel());
        Assert.assertEquals(2100, decision.getTargetStock());
        Assert.assertEquals(2085, decision.getShortage());
        Assert.assertEquals(1000, decision.getOrderQty());
        Assert.assertEquals(0, decision.getStockDays());
        Assert.assertEquals("CRITICAL", decision.getUrgency());
        Assert.assertTrue(decision.getNotes().contains("按 MOQ/整箱包装取整后下单量高于库存缺口，属正常约束"));
    }
}
