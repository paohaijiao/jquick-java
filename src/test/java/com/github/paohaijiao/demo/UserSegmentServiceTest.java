package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.UserBehavior;
import com.github.paohaijiao.demo.model.UserSegmentDecision;
import com.github.paohaijiao.demo.service.UserSegmentService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 28：用户画像标签动态分层
 * <p>
 * 覆盖 6 组用户形态：高价值活跃 VIP、高价值温客召回、中价值价格敏感、
 * 无价值沉睡静默、活跃高退货审慎营销、高价值冷客 EDM 唤醒（55 分边界）。
 */
public class UserSegmentServiceTest {

    private UserSegmentService service;

    @Before
    public void setUp() {
        UserSegmentRules rules = JQuickJava.create()
                .createApi(UserSegmentRules.class, "demo/user-segment.xml");
        this.service = new UserSegmentService(rules);
    }

    /**
     * 全维度头部用户 → ACTIVE / HIGH_VALUE(75) / VIP 培育
     */
    @Test
    public void shouldSegmentActiveHighValueVip() {
        UserBehavior u = base("U001");
        u.setBrowseDays30d(22);
        u.setLastOrderDays(3);
        u.setOrderCount90d(15);
        u.setGmv90d(6000);
        u.setAvgOrderAmount(1200);
        u.setMemberRank(5);
        u.setCouponSensitivity(30);
        u.setReturnRate(5);

        UserSegmentDecision d = service.segment(u);

        Assert.assertEquals("ACTIVE", d.getActivitySegment());
        Assert.assertEquals(75, d.getValueScore());
        Assert.assertEquals("HIGH_VALUE", d.getValueSegment());
        Assert.assertTrue(d.getTags().contains("VIP_CULTIVATION"));
        Assert.assertTrue(d.getMarketingStrategy().contains("专属客户经理"));
    }

    /**
     * 高价值但浏览活跃转温 → 大额召回券防流失
     */
    @Test
    public void shouldRecallHighValueWarmUser() {
        UserBehavior u = base("U002");
        u.setBrowseDays30d(10);
        u.setLastOrderDays(25);
        u.setOrderCount90d(8);
        u.setGmv90d(5200);
        u.setAvgOrderAmount(1100);
        u.setMemberRank(4);

        UserSegmentDecision d = service.segment(u);

        Assert.assertEquals("WARM", d.getActivitySegment());
        Assert.assertEquals(67, d.getValueScore());
        Assert.assertEquals("HIGH_VALUE", d.getValueSegment());
        Assert.assertTrue(d.getMarketingStrategy().contains("大额召回券"));
    }

    /**
     * 价值分恰好 35 的温客且券敏感 → 中价值，满减加购券
     */
    @Test
    public void shouldSegmentPriceSensitiveMidValue() {
        UserBehavior u = base("U003");
        u.setBrowseDays30d(12);
        u.setLastOrderDays(5);
        u.setOrderCount90d(4);
        u.setGmv90d(900);
        u.setAvgOrderAmount(300);
        u.setMemberRank(3);
        u.setCouponSensitivity(70);

        UserSegmentDecision d = service.segment(u);

        Assert.assertEquals("WARM", d.getActivitySegment());
        Assert.assertEquals(35, d.getValueScore());
        Assert.assertEquals("MID_VALUE", d.getValueSegment());
        Assert.assertTrue(d.getTags().contains("PRICE_SENSITIVE"));
        Assert.assertTrue(d.getMarketingStrategy().contains("满减加购券"));
    }

    /**
     * 长期无行为低等级用户 → DORMANT / NO_VALUE，静默池
     */
    @Test
    public void shouldSilenceDormantNoValueUser() {
        UserBehavior u = base("U004");
        u.setBrowseDays30d(1);
        u.setLastOrderDays(150);
        u.setOrderCount90d(0);
        u.setGmv90d(0);
        u.setAvgOrderAmount(0);
        u.setMemberRank(1);

        UserSegmentDecision d = service.segment(u);

        Assert.assertEquals("DORMANT", d.getActivitySegment());
        Assert.assertEquals(0, d.getValueScore());
        Assert.assertEquals("NO_VALUE", d.getValueSegment());
        Assert.assertEquals(0, d.getTags().size());
        Assert.assertTrue(d.getMarketingStrategy().contains("静默池"));
    }

    /**
     * 活跃中价值但退货率 35% → 高退货风险，审慎营销优先于活跃策略
     */
    @Test
    public void shouldApplyCautiousStrategyForHighReturnRisk() {
        UserBehavior u = base("U005");
        u.setBrowseDays30d(20);
        u.setLastOrderDays(2);
        u.setOrderCount90d(10);
        u.setGmv90d(2200);
        u.setAvgOrderAmount(400);
        u.setMemberRank(3);
        u.setReturnRate(35);

        UserSegmentDecision d = service.segment(u);

        Assert.assertEquals("ACTIVE", d.getActivitySegment());
        Assert.assertEquals(49, d.getValueScore());
        Assert.assertEquals("MID_VALUE", d.getValueSegment());
        Assert.assertTrue(d.getTags().contains("HIGH_RETURN_RISK"));
        Assert.assertTrue(d.getMarketingStrategy().contains("审慎营销"));
    }

    /**
     * 价值分恰好 55 的冷客（高等级）→ HIGH_VALUE + EDM 唤醒，不打 VIP 标签
     */
    @Test
    public void shouldWakeHighValueColdUserAtThreshold() {
        UserBehavior u = base("U006");
        u.setBrowseDays30d(4);
        u.setLastOrderDays(60);
        u.setOrderCount90d(3);
        u.setGmv90d(5000);
        u.setAvgOrderAmount(200);
        u.setMemberRank(4);

        UserSegmentDecision d = service.segment(u);

        Assert.assertEquals("COLD", d.getActivitySegment());
        Assert.assertEquals(55, d.getValueScore());
        Assert.assertEquals("HIGH_VALUE", d.getValueSegment());
        Assert.assertFalse(d.getTags().contains("VIP_CULTIVATION"));
        Assert.assertFalse(d.getTags().contains("WINBACK"));
        Assert.assertTrue(d.getMarketingStrategy().contains("大额召回券"));
    }

    private UserBehavior base(String id) {
        UserBehavior u = new UserBehavior();
        u.setUserId(id);
        u.setLastOrderDays(999);
        u.setOrderCount90d(0);
        u.setGmv90d(0);
        u.setAvgOrderAmount(0);
        u.setBrowseDays30d(0);
        u.setCouponSensitivity(0);
        u.setReturnRate(0);
        u.setMemberRank(1);
        return u;
    }
}
