package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.DistributionCommissionDecision;
import com.github.paohaijiao.demo.model.DistributionOrder;
import com.github.paohaijiao.demo.service.DistributionCommissionService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 29：分销多级返佣与分润熔断
 * <p>
 * 6 组形态：美妆高级分销团长触顶熔断、家电薄利两级分润、
 * 活动让利基数折减、退款全额没收、大额批发人工审核、生鲜团长成团临界。
 */
public class DistributionCommissionServiceTest {

    private DistributionCommissionService service;

    @Before
    public void setUp() {
        DistributionCommissionRules rules = JQuickJava.create()
                .createApi(DistributionCommissionRules.class, "demo/distribution-commission.xml");
        this.service = new DistributionCommissionService(rules);
    }

    /**
     * 美妆 L3 + 合伙人直推 + L3 间推 + 月成团 25 团长 → 345 元触 300 元上限
     */
    @Test
    public void shouldCapTotalCommissionAtThirtyPercent() {
        DistributionOrder o = base("DIS001", 1000, "BEAUTY", "L3");
        o.setUplineLevel("PARTNER");
        o.setRootLevel("L3");
        o.setGroupLeader(true);
        o.setGroupCount30d(25);

        DistributionCommissionDecision d = service.settle(o);

        Assert.assertEquals(1000.0, d.getBaseAmount(), 0.0001);
        Assert.assertEquals(260.0, d.getSelfCommission(), 0.0001);
        Assert.assertEquals(50.0, d.getDirectCommission(), 0.0001);
        Assert.assertEquals(15.0, d.getIndirectCommission(), 0.0001);
        Assert.assertEquals(20.0, d.getGroupBonus(), 0.0001);
        Assert.assertEquals(300.0, d.getTotalCommission(), 0.0001);
        Assert.assertTrue(d.isCapped());
        Assert.assertEquals("SETTLED", d.getStatus());
    }

    /**
     * 家电低毛利 L1 分销 + L2 直推 → 60 + 60，无间推与团长奖
     */
    @Test
    public void shouldSettleApplianceThinMargin() {
        DistributionOrder o = base("DIS002", 2000, "APPLIANCE", "L1");
        o.setUplineLevel("L2");
        o.setRootLevel("L1");

        DistributionCommissionDecision d = service.settle(o);

        Assert.assertEquals(60.0, d.getSelfCommission(), 0.0001);
        Assert.assertEquals(60.0, d.getDirectCommission(), 0.0001);
        Assert.assertEquals(0.0, d.getIndirectCommission(), 0.0001);
        Assert.assertEquals(0.0, d.getGroupBonus(), 0.0001);
        Assert.assertEquals(120.0, d.getTotalCommission(), 0.0001);
        Assert.assertFalse(d.isCapped());
        Assert.assertEquals("SETTLED", d.getStatus());
    }

    /**
     * 标品 L2 活动让利 35% → 基数 350，自购 38.5、直推 14、间推 7
     */
    @Test
    public void shouldDiscountCommissionBaseForPromotion() {
        DistributionOrder o = base("DIS003", 500, "STANDARD", "L2");
        o.setUplineLevel("L3");
        o.setRootLevel("PARTNER");
        o.setPromotionCostRate(35);

        DistributionCommissionDecision d = service.settle(o);

        Assert.assertEquals(350.0, d.getBaseAmount(), 0.0001);
        Assert.assertEquals(38.5, d.getSelfCommission(), 0.0001);
        Assert.assertEquals(14.0, d.getDirectCommission(), 0.0001);
        Assert.assertEquals(7.0, d.getIndirectCommission(), 0.0001);
        Assert.assertEquals(59.5, d.getTotalCommission(), 0.0001);
        Assert.assertEquals("SETTLED", d.getStatus());
    }

    /**
     * 订单退款 → 所有分润没收
     */
    @Test
    public void shouldForfeitAllWhenRefunded() {
        DistributionOrder o = base("DIS004", 1000, "BEAUTY", "L3");
        o.setUplineLevel("PARTNER");
        o.setRefunded(true);

        DistributionCommissionDecision d = service.settle(o);

        Assert.assertEquals("FORFEITED", d.getStatus());
        Assert.assertEquals(0.0, d.getTotalCommission(), 0.0001);
        Assert.assertEquals(0.0, d.getSelfCommission(), 0.0001);
        Assert.assertTrue(d.getBreakdown().contains("订单已退款，各级分润全部不予结算"));
    }

    /**
     * 单笔 6 万元批发单 → 佣金预估照算但冻结待人工审核
     */
    @Test
    public void shouldHoldLargeWholesaleOrderForReview() {
        DistributionOrder o = base("DIS005", 60000, "STANDARD", "L1");
        o.setUplineLevel("L1");
        o.setRootLevel("L1");

        DistributionCommissionDecision d = service.settle(o);

        Assert.assertEquals("PENDING", d.getStatus());
        Assert.assertEquals(60000.0, d.getBaseAmount(), 0.0001);
        Assert.assertEquals(6000.0, d.getSelfCommission(), 0.0001);
        Assert.assertEquals(6000.0, d.getTotalCommission(), 0.0001);
        Assert.assertTrue(d.getBreakdown().contains(
                "单笔金额超 5 万元，转人工核验交易真实性后再行结算"));
    }

    /**
     * 生鲜 L1 团长：成团 10 单拿 1% 服务费（21 元），9 单无服务费（18 元）
     */
    @Test
    public void shouldPayGroupBonusAtTenOrdersThreshold() {
        DistributionOrder o = base("DIS006", 300, "FRESH", "L1");
        o.setUplineLevel("L1");
        o.setRootLevel("L1");
        o.setGroupLeader(true);
        o.setGroupCount30d(10);

        DistributionCommissionDecision d1 = service.settle(o);
        Assert.assertEquals(18.0, d1.getSelfCommission(), 0.0001);
        Assert.assertEquals(3.0, d1.getGroupBonus(), 0.0001);
        Assert.assertEquals(21.0, d1.getTotalCommission(), 0.0001);
        Assert.assertEquals("SETTLED", d1.getStatus());

        o.setGroupCount30d(9);
        DistributionCommissionDecision d2 = service.settle(o);
        Assert.assertEquals(0.0, d2.getGroupBonus(), 0.0001);
        Assert.assertEquals(18.0, d2.getTotalCommission(), 0.0001);
    }

    private DistributionOrder base(String id, double amount, String category, String level) {
        DistributionOrder o = new DistributionOrder();
        o.setOrderId(id);
        o.setSku("SKU200");
        o.setOrderAmount(amount);
        o.setProductCategory(category);
        o.setDistributorLevel(level);
        o.setUplineLevel("L1");
        o.setRootLevel("L1");
        o.setGroupLeader(false);
        o.setGroupCount30d(0);
        o.setPromotionCostRate(0);
        o.setRefunded(false);
        return o;
    }
}
