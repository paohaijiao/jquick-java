package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.InventoryItem;
import com.github.paohaijiao.demo.model.PricingDecision;
import com.github.paohaijiao.demo.service.DynamicPricingService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 4 生产级样板：库存滞销动态定价
 * <p>
 * 覆盖 6 组商品形态：严重滞销、畅销旺季、临期外包仓、触发毛利保护、
 * 极限清仓、库龄临界待观察。
 */
public class DynamicPricingServiceTest {

    private DynamicPricingService service;

    @Before
    public void setUp() {
        DynamicPricingRules rules = JQuickJava.create()
                .createApi(DynamicPricingRules.class, "demo/dynamic-pricing.xml");
        this.service = new DynamicPricingService(rules);
    }

    /**
     * 严重滞销：库龄 200 天、近 30 天仅售出 5 件 → 深度清仓，打包促销
     */
    @Test
    public void shouldDeepClearSeverelyAgedStock() {
        InventoryItem item = new InventoryItem();
        item.setSku("SKU-2001");
        item.setSkuName("冬季羽绒服");
        item.setCategory("服饰");
        item.setStockAgeDays(200);
        item.setStockQty(100);
        item.setCostPrice(60.0);
        item.setCurrentPrice(200.0);
        item.setRecentSalesQty30d(5);
        item.setSeasonFactor("NORMAL");
        item.setPerishable(false);
        item.setWarehouseType("MAIN");
        item.setMarginFloor(0.10);

        PricingDecision decision = service.price(item);

        Assert.assertEquals(4, decision.getAgeLevel());
        Assert.assertEquals(0.5, decision.getBaseDiscount(), 0.0001);
        Assert.assertEquals(0.35, decision.getFinalDiscount(), 0.0001);
        Assert.assertEquals(70.0, decision.getSuggestedPrice(), 0.001);
        Assert.assertEquals("DEEP", decision.getClearanceLevel());
        Assert.assertEquals("BUNDLE", decision.getPromotionType());
        Assert.assertTrue(decision.isNeedClearance());
        Assert.assertEquals(0.1429, decision.getGrossMarginRate(), 0.0001);
    }

    /**
     * 畅销旺季：库龄 10 天、周转快、旺季 → 不打折，无促销
     */
    @Test
    public void shouldKeepFastSellingItemAtFullPrice() {
        InventoryItem item = new InventoryItem();
        item.setSku("SKU-2002");
        item.setSkuName("当季连衣裙");
        item.setCategory("女装");
        item.setStockAgeDays(10);
        item.setStockQty(100);
        item.setCostPrice(50.0);
        item.setCurrentPrice(100.0);
        item.setRecentSalesQty30d(80);
        item.setSeasonFactor("PEAK");
        item.setPerishable(false);
        item.setWarehouseType("MAIN");
        item.setMarginFloor(0.10);

        PricingDecision decision = service.price(item);

        Assert.assertEquals(0, decision.getAgeLevel());
        Assert.assertEquals(1.0, decision.getFinalDiscount(), 0.0001);
        Assert.assertEquals(100.0, decision.getSuggestedPrice(), 0.001);
        Assert.assertEquals("NONE", decision.getClearanceLevel());
        Assert.assertEquals("NONE", decision.getPromotionType());
        Assert.assertFalse(decision.isNeedClearance());
        Assert.assertEquals(0.5, decision.getGrossMarginRate(), 0.0001);
        Assert.assertTrue(decision.getNotes().isEmpty());
    }

    /**
     * 临期外包仓：库龄 75 天、淡季、易腐、外包仓 → 深度清仓走闪购
     */
    @Test
    public void shouldFlashSalePerishableAgedItem() {
        InventoryItem item = new InventoryItem();
        item.setSku("SKU-2003");
        item.setSkuName("进口冷链牛排");
        item.setCategory("生鲜");
        item.setStockAgeDays(75);
        item.setStockQty(200);
        item.setCostPrice(20.0);
        item.setCurrentPrice(50.0);
        item.setRecentSalesQty30d(40);
        item.setSeasonFactor("OFF");
        item.setPerishable(true);
        item.setWarehouseType("OUTSOURCE");
        item.setMarginFloor(0.10);

        PricingDecision decision = service.price(item);

        Assert.assertEquals(0.54, decision.getFinalDiscount(), 0.001);
        Assert.assertEquals(27.0, decision.getSuggestedPrice(), 0.001);
        Assert.assertEquals("DEEP", decision.getClearanceLevel());
        Assert.assertEquals("FLASH_SALE", decision.getPromotionType());
        Assert.assertTrue(decision.isNeedClearance());
        Assert.assertEquals(0.2593, decision.getGrossMarginRate(), 0.0001);
    }

    /**
     * 触发毛利保护：库龄 120 天、周转极慢，折扣价击穿毛利线 → 按保护价执行
     */
    @Test
    public void shouldProtectGrossMarginFloor() {
        InventoryItem item = new InventoryItem();
        item.setSku("SKU-2004");
        item.setSkuName("滞销小家电");
        item.setCategory("家电");
        item.setStockAgeDays(120);
        item.setStockQty(100);
        item.setCostPrice(60.0);
        item.setCurrentPrice(100.0);
        item.setRecentSalesQty30d(10);
        item.setSeasonFactor("NORMAL");
        item.setPerishable(false);
        item.setWarehouseType("MAIN");
        item.setMarginFloor(0.20);

        PricingDecision decision = service.price(item);

        Assert.assertEquals(0.50, decision.getFinalDiscount(), 0.001);
        Assert.assertEquals(72.0, decision.getSuggestedPrice(), 0.001);
        Assert.assertEquals("DEEP", decision.getClearanceLevel());
        Assert.assertEquals("BUNDLE", decision.getPromotionType());
        Assert.assertEquals(0.1667, decision.getGrossMarginRate(), 0.0001);
        Assert.assertTrue(decision.getNotes().contains("清仓折扣触及最低毛利保护线，按保护价执行"));
    }

    /**
     * 极限清仓：库龄 200 天、零销量、临期、外包仓、淡季 → 折扣触底 30%
     */
    @Test
    public void shouldApplyFloorDiscountOnDeadStock() {
        InventoryItem item = new InventoryItem();
        item.setSku("SKU-2005");
        item.setSkuName("临期礼盒");
        item.setCategory("食品");
        item.setStockAgeDays(200);
        item.setStockQty(300);
        item.setCostPrice(25.0);
        item.setCurrentPrice(100.0);
        item.setRecentSalesQty30d(0);
        item.setSeasonFactor("OFF");
        item.setPerishable(true);
        item.setWarehouseType("OUTSOURCE");
        item.setMarginFloor(0.05);

        PricingDecision decision = service.price(item);

        Assert.assertEquals(0.30, decision.getFinalDiscount(), 0.001);
        Assert.assertEquals(30.0, decision.getSuggestedPrice(), 0.001);
        Assert.assertEquals("DEEP", decision.getClearanceLevel());
        Assert.assertEquals("FLASH_SALE", decision.getPromotionType());
        Assert.assertTrue(decision.isNeedClearance());
        Assert.assertEquals(0.1667, decision.getGrossMarginRate(), 0.0001);
    }

    /**
     * 库龄临界：库龄 65 天、周转中等 → 轻度清仓，会员价促销，暂不强制清仓
     */
    @Test
    public void shouldApplyLightClearanceForMidAgedItem() {
        InventoryItem item = new InventoryItem();
        item.setSku("SKU-2006");
        item.setSkuName("常规运动鞋");
        item.setCategory("鞋靴");
        item.setStockAgeDays(65);
        item.setStockQty(100);
        item.setCostPrice(50.0);
        item.setCurrentPrice(80.0);
        item.setRecentSalesQty30d(45);
        item.setSeasonFactor("NORMAL");
        item.setPerishable(false);
        item.setWarehouseType("MAIN");
        item.setMarginFloor(0.10);

        PricingDecision decision = service.price(item);

        Assert.assertEquals(0.8, decision.getFinalDiscount(), 0.0001);
        Assert.assertEquals(64.0, decision.getSuggestedPrice(), 0.001);
        Assert.assertEquals("LIGHT", decision.getClearanceLevel());
        Assert.assertEquals("MEMBER_PRICE", decision.getPromotionType());
        Assert.assertFalse(decision.isNeedClearance());
        Assert.assertEquals(0.2188, decision.getGrossMarginRate(), 0.0001);
    }
}
