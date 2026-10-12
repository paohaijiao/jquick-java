package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.CampaignFraudDecision;
import com.github.paohaijiao.demo.model.CampaignOrder;
import com.github.paohaijiao.demo.service.CampaignFraudService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 26：营销活动订单防刷单
 * <p>
 * 覆盖 6 组订单形态：正常老客放行、新号高频临界 40 分复核、黑产满分拦截、
 * 秒杀脚本硬规则拦截、疑似刷券 54 分复核、70 分纯评分拦截边界。
 */
public class CampaignFraudServiceTest {

    private CampaignFraudService service;

    @Before
    public void setUp() {
        CampaignFraudRules rules = JQuickJava.create()
                .createApi(CampaignFraudRules.class, "demo/campaign-fraud.xml");
        this.service = new CampaignFraudService(rules);
    }

    /**
     * 资深实名老客、行为平稳 → 0 分放行
     */
    @Test
    public void shouldPassNormalLoyalCustomer() {
        CampaignOrder o = base("ORD001");
        o.setAccountAgeDays(400);
        o.setOrderCount24h(1);

        CampaignFraudDecision d = service.check(o);

        Assert.assertEquals(0, d.getRiskScore());
        Assert.assertEquals("PASS", d.getAction());
        Assert.assertFalse(d.isHardRuleHit());
    }

    /**
     * 注册 3 天新号、4 单/小时、10 单/日、设备 2 账户、3 张券 → 恰好 40 分复核
     */
    @Test
    public void shouldReviewNewAccountAtScoreForty() {
        CampaignOrder o = base("ORD002");
        o.setAccountAgeDays(3);
        o.setOrderCount1h(4);
        o.setOrderCount24h(10);
        o.setDeviceAccountCount24h(2);
        o.setIpAccountCount24h(4);
        o.setCouponOrderCount7d(5);
        o.setAmountDeviationPercent(50);

        CampaignFraudDecision d = service.check(o);

        Assert.assertEquals(40, d.getRiskScore());
        Assert.assertEquals("REVIEW", d.getAction());
        Assert.assertFalse(d.isHardRuleHit());
        Assert.assertTrue(d.getScoreDetails().contains("账户龄: 14 / 20"));
        Assert.assertTrue(d.getRiskTags().contains("注册未满 7 天"));
    }

    /**
     * 黑产设备池全套特征 → 100 分并命中硬规则拦截
     */
    @Test
    public void shouldRejectFullFraudProfile() {
        CampaignOrder o = base("ORD003");
        o.setAccountAgeDays(0);
        o.setOrderCount1h(8);
        o.setOrderCount24h(20);
        o.setDeviceAccountCount24h(6);
        o.setIpAccountCount24h(12);
        o.setCouponOrderCount7d(12);
        o.setEmptyPackageRate(60);
        o.setAmountDeviationPercent(500);
        o.setRealName(false);
        o.setPaymentConsistent(false);
        o.setVirtualAddress(true);

        CampaignFraudDecision d = service.check(o);

        Assert.assertEquals(100, d.getRiskScore());
        Assert.assertEquals("REJECT", d.getAction());
        Assert.assertTrue(d.isHardRuleHit());
        Assert.assertTrue(d.getRiskTags().contains("黑产设备池：未实名账户聚集同一设备"));
        Assert.assertTrue(d.getRiskTags().contains("同设备关联多账户"));
        Assert.assertTrue(d.getRiskTags().contains("历史空包率高"));
    }

    /**
     * 其余特征干净，但 1 小时 6 单且客单偏离 450% → 秒杀脚本硬规则拦截
     */
    @Test
    public void shouldRejectScriptPatternByHardRule() {
        CampaignOrder o = base("ORD004");
        o.setAccountAgeDays(500);
        o.setOrderCount1h(6);
        o.setOrderCount24h(6);
        o.setAmountDeviationPercent(450);

        CampaignFraudDecision d = service.check(o);

        Assert.assertEquals(30, d.getRiskScore());
        Assert.assertEquals("REJECT", d.getAction());
        Assert.assertTrue(d.isHardRuleHit());
        Assert.assertTrue(d.getRiskTags().contains("秒杀脚本特征：1 小时高频下单且客单价严重偏离"));
    }

    /**
     * 注册 5 天、设备 3 账户 IP 5 个、5 张券空包率 25%、偏离 220% → 54 分复核
     */
    @Test
    public void shouldReviewCouponAbuseAtScoreFiftyFour() {
        CampaignOrder o = base("ORD005");
        o.setAccountAgeDays(5);
        o.setOrderCount1h(3);
        o.setOrderCount24h(9);
        o.setDeviceAccountCount24h(3);
        o.setIpAccountCount24h(5);
        o.setCouponOrderCount7d(5);
        o.setEmptyPackageRate(25);
        o.setAmountDeviationPercent(220);

        CampaignFraudDecision d = service.check(o);

        Assert.assertEquals(54, d.getRiskScore());
        Assert.assertEquals("REVIEW", d.getAction());
        Assert.assertTrue(d.getScoreDetails().contains("设备与IP聚集: 12 / 20"));
        Assert.assertTrue(d.getScoreDetails().contains("券单与空包: 6 / 10"));
    }

    /**
     * 恰好 70 分：未命中任何硬规则，纯评分线拦截
     */
    @Test
    public void shouldRejectAtExactScoreSeventy() {
        CampaignOrder o = base("ORD006");
        o.setAccountAgeDays(5);
        o.setOrderCount1h(6);
        o.setOrderCount24h(20);
        o.setDeviceAccountCount24h(2);
        o.setIpAccountCount24h(4);
        o.setCouponOrderCount7d(5);
        o.setEmptyPackageRate(0);
        o.setAmountDeviationPercent(220);
        o.setRealName(false);
        o.setPaymentConsistent(false);
        o.setVirtualAddress(true);

        CampaignFraudDecision d = service.check(o);

        Assert.assertEquals(70, d.getRiskScore());
        Assert.assertEquals("REJECT", d.getAction());
        Assert.assertFalse(d.isHardRuleHit());
        Assert.assertTrue(d.getRiskTags().contains("付款人与下单人不一致"));
        Assert.assertTrue(d.getRiskTags().contains("虚拟/集中代收收货地址"));
    }

    private CampaignOrder base(String orderId) {
        CampaignOrder o = new CampaignOrder();
        o.setOrderId(orderId);
        o.setUserId("U10001");
        o.setCampaignId("CAMP2026");
        o.setAccountAgeDays(365);
        o.setOrderCount1h(0);
        o.setOrderCount24h(0);
        o.setDeviceAccountCount24h(1);
        o.setIpAccountCount24h(1);
        o.setCouponOrderCount7d(0);
        o.setEmptyPackageRate(0);
        o.setAmountDeviationPercent(0);
        o.setRealName(true);
        o.setPaymentConsistent(true);
        o.setVirtualAddress(false);
        return o;
    }
}
