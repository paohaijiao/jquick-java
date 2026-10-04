package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.CouponDecision;
import com.github.paohaijiao.demo.model.CouponRequest;
import com.github.paohaijiao.demo.service.CouponService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 10 生产级样板：优惠券发放规则
 * <p>
 * 覆盖 6 组发券形态：新客首单、VIP 大额、大额满减、忠诚回馈、
 * 风控拦截、未命中策略。
 */
public class CouponServiceTest {

    private CouponService service;

    @Before
    public void setUp() {
        CouponRules rules = JQuickJava.create()
                .createApi(CouponRules.class, "demo/coupon.xml");
        this.service = new CouponService(rules);
    }

    /**
     * 新客首单：注册 2 天、订单 88 元 → 新客专享券 30 元，不可叠加，风控 MEDIUM
     */
    @Test
    public void shouldIssueNewUserCoupon() {
        CouponRequest request = new CouponRequest();
        request.setUserId("U-1001");
        request.setMemberLevel("NORMAL");
        request.setOrderAmount(88.0);
        request.setOrderCount(0);
        request.setCategory("APPAREL");
        request.setJoinDays(2);
        request.setLastOrderDays(0);
        request.setAvgOrderAmount(0.0);
        request.setNewUser(true);
        request.setHasUsedCoupon(false);
        request.setCampaignActive(false);
        request.setStockLeft(1000);
        request.setChannel("APP");
        request.setRiskFlag(false);

        CouponDecision decision = service.issue(request);

        Assert.assertEquals("NEW_USER", decision.getCouponType());
        Assert.assertEquals("新客专享券", decision.getCouponName());
        Assert.assertEquals(30.0, decision.getDiscountAmount(), 0.001);
        Assert.assertEquals(200.0, decision.getThreshold(), 0.001);
        Assert.assertEquals(7, decision.getValidDays());
        Assert.assertFalse(decision.isStackable());
        Assert.assertFalse(decision.isNeedManualReview());
        Assert.assertEquals("MEDIUM", decision.getRiskLevel());
        Assert.assertTrue(decision.getReasons().contains("新客注册 2 天，叠加新客奖励 30.0 元"));
    }

    /**
     * VIP 大额：订单 2500 元 → VIP 专享券 200 元，达复核线转人工
     */
    @Test
    public void shouldIssueVipExclusiveCoupon() {
        CouponRequest request = new CouponRequest();
        request.setUserId("U-1002");
        request.setMemberLevel("VIP");
        request.setOrderAmount(2500.0);
        request.setOrderCount(15);
        request.setCategory("DIGITAL");
        request.setJoinDays(900);
        request.setLastOrderDays(10);
        request.setAvgOrderAmount(800.0);
        request.setNewUser(false);
        request.setHasUsedCoupon(false);
        request.setCampaignActive(false);
        request.setStockLeft(500);
        request.setChannel("APP");
        request.setRiskFlag(false);

        CouponDecision decision = service.issue(request);

        Assert.assertEquals("VIP_EXCLUSIVE", decision.getCouponType());
        Assert.assertEquals("VIP专享券", decision.getCouponName());
        Assert.assertEquals(200.0, decision.getDiscountAmount(), 0.001);
        Assert.assertEquals(2000.0, decision.getThreshold(), 0.001);
        Assert.assertEquals(30, decision.getValidDays());
        Assert.assertTrue(decision.isStackable());
        Assert.assertTrue(decision.isNeedManualReview());
        Assert.assertEquals("LOW", decision.getRiskLevel());
        Assert.assertTrue(decision.getReasons().contains("优惠金额达到 200 元复核线，转人工复核"));
    }

    /**
     * 大额满减：SILVER、订单 1200 元 → 大额满减券 100 元
     */
    @Test
    public void shouldIssueLargeOrderCoupon() {
        CouponRequest request = new CouponRequest();
        request.setUserId("U-1003");
        request.setMemberLevel("SILVER");
        request.setOrderAmount(1200.0);
        request.setOrderCount(8);
        request.setCategory("GENERAL");
        request.setJoinDays(200);
        request.setLastOrderDays(20);
        request.setAvgOrderAmount(400.0);
        request.setNewUser(false);
        request.setHasUsedCoupon(false);
        request.setCampaignActive(false);
        request.setStockLeft(800);
        request.setChannel("WEB");
        request.setRiskFlag(false);

        CouponDecision decision = service.issue(request);

        Assert.assertEquals("LARGE_ORDER", decision.getCouponType());
        Assert.assertEquals("大额满减券", decision.getCouponName());
        Assert.assertEquals(100.0, decision.getDiscountAmount(), 0.001);
        Assert.assertEquals(1000.0, decision.getThreshold(), 0.001);
        Assert.assertEquals(15, decision.getValidDays());
        Assert.assertFalse(decision.isNeedManualReview());
        Assert.assertEquals("LOW", decision.getRiskLevel());
    }

    /**
     * 忠诚回馈：GOLD、35 单 + 活动期生鲜 → 基础 80 + 忠诚 20 + 品类 15 = 115 元
     */
    @Test
    public void shouldRewardLoyalUser() {
        CouponRequest request = new CouponRequest();
        request.setUserId("U-1004");
        request.setMemberLevel("GOLD");
        request.setOrderAmount(600.0);
        request.setOrderCount(35);
        request.setCategory("FRESH");
        request.setJoinDays(1500);
        request.setLastOrderDays(3);
        request.setAvgOrderAmount(300.0);
        request.setNewUser(false);
        request.setHasUsedCoupon(false);
        request.setCampaignActive(true);
        request.setStockLeft(300);
        request.setChannel("MINI");
        request.setRiskFlag(false);

        CouponDecision decision = service.issue(request);

        Assert.assertEquals("LOYALTY", decision.getCouponType());
        Assert.assertEquals("忠诚回馈券", decision.getCouponName());
        Assert.assertEquals(115.0, decision.getDiscountAmount(), 0.001);
        Assert.assertEquals(1000.0, decision.getThreshold(), 0.001);
        Assert.assertEquals(10, decision.getValidDays());
        Assert.assertTrue(decision.isStackable());
        Assert.assertTrue(decision.getReasons().contains("历史下单 35 单，叠加忠诚奖励 20.0 元"));
        Assert.assertTrue(decision.getReasons().contains("活动期 FRESH 品类，叠加品类奖励 15.0 元"));
    }

    /**
     * 风控拦截：命中风控标记 + 已领券 → 需人工复核、不可叠加、风险 HIGH
     */
    @Test
    public void shouldHoldCouponForRiskReview() {
        CouponRequest request = new CouponRequest();
        request.setUserId("U-1005");
        request.setMemberLevel("VIP");
        request.setOrderAmount(3000.0);
        request.setOrderCount(1);
        request.setCategory("DIGITAL");
        request.setJoinDays(1);
        request.setLastOrderDays(0);
        request.setAvgOrderAmount(0.0);
        request.setNewUser(true);
        request.setHasUsedCoupon(true);
        request.setCampaignActive(false);
        request.setStockLeft(10);
        request.setChannel("APP");
        request.setRiskFlag(true);

        CouponDecision decision = service.issue(request);

        Assert.assertEquals("NEW_USER", decision.getCouponType());
        Assert.assertEquals(230.0, decision.getDiscountAmount(), 0.001);
        Assert.assertEquals(2000.0, decision.getThreshold(), 0.001);
        Assert.assertTrue(decision.isNeedManualReview());
        Assert.assertFalse(decision.isStackable());
        Assert.assertEquals("HIGH", decision.getRiskLevel());
        Assert.assertTrue(decision.getReasons().contains("命中风控标记，需人工复核后再发放"));
    }

    /**
     * 未命中策略：NORMAL、订单 120 元、非新客 → 无可用券
     */
    @Test
    public void shouldReturnNoneWhenNoStrategyMatched() {
        CouponRequest request = new CouponRequest();
        request.setUserId("U-1006");
        request.setMemberLevel("NORMAL");
        request.setOrderAmount(120.0);
        request.setOrderCount(3);
        request.setCategory("GENERAL");
        request.setJoinDays(100);
        request.setLastOrderDays(30);
        request.setAvgOrderAmount(100.0);
        request.setNewUser(false);
        request.setHasUsedCoupon(false);
        request.setCampaignActive(false);
        request.setStockLeft(1000);
        request.setChannel("WEB");
        request.setRiskFlag(false);

        CouponDecision decision = service.issue(request);

        Assert.assertEquals("NONE", decision.getCouponType());
        Assert.assertEquals("无可用券", decision.getCouponName());
        Assert.assertEquals(0.0, decision.getDiscountAmount(), 0.001);
        Assert.assertEquals(0.0, decision.getThreshold(), 0.001);
        Assert.assertFalse(decision.isNeedManualReview());
        Assert.assertEquals("LOW", decision.getRiskLevel());
        Assert.assertTrue(decision.getReasons().contains("订单金额与用户画像未命中任何发券策略"));
    }
}
