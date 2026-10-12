package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.MemberLoyaltyDecision;
import com.github.paohaijiao.demo.model.MemberProfile;
import com.github.paohaijiao.demo.service.MemberLoyaltyService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 25：会员成长值与等级升降级
 * <p>
 * 覆盖 6 组会员形态：临界升级黑金、银卡升金卡、白金未保级降级、
 * 金卡保级（高退款折减成长值）、违规冻结权益、临期保级提醒。
 */
public class MemberLoyaltyServiceTest {

    private MemberLoyaltyService service;

    @Before
    public void setUp() {
        MemberLoyaltyRules rules = JQuickJava.create()
                .createApi(MemberLoyaltyRules.class, "demo/member-loyalty.xml");
        this.service = new MemberLoyaltyService(rules);
    }

    /**
     * 成长值恰好 50000 → 白金升黑金，85 折
     */
    @Test
    public void shouldUpgradeToBlackGoldAtThreshold() {
        MemberProfile member = base("M001", "S4", 47000);
        member.setPeriodSpendYuan(3000);
        member.setPeriodOrders(0);

        MemberLoyaltyDecision d = service.evaluate(member);

        Assert.assertEquals(50000, d.getNewGrowthValue());
        Assert.assertEquals("S5", d.getTargetLevel());
        Assert.assertEquals("UPGRADE", d.getChangeType());
        Assert.assertEquals(0.85, d.getDiscount(), 0.0001);
        Assert.assertTrue(d.getBenefits().contains("黑金权益：全场 85 折、专属客户经理、全场免运费"));
    }

    /**
     * 银卡会员大额消费 → 升级金卡，94 折
     */
    @Test
    public void shouldUpgradeSilverToGold() {
        MemberProfile member = base("M002", "S2", 6000);
        member.setPeriodSpendYuan(2500);
        member.setPeriodOrders(5);

        MemberLoyaltyDecision d = service.evaluate(member);

        Assert.assertEquals(2600, d.getPeriodEarned());
        Assert.assertEquals(8600, d.getNewGrowthValue());
        Assert.assertEquals("S3", d.getTargetLevel());
        Assert.assertEquals("UPGRADE", d.getChangeType());
        Assert.assertEquals(0.94, d.getDiscount(), 0.0001);
    }

    /**
     * 白金会员本期零消费且高退款率 → 成长值 15000 降至金卡
     */
    @Test
    public void shouldDowngradePlatinumWithoutSpending() {
        MemberProfile member = base("M003", "S4", 15000);
        member.setPeriodSpendYuan(0);
        member.setPeriodOrders(0);
        member.setRefundRate(40);

        MemberLoyaltyDecision d = service.evaluate(member);

        Assert.assertEquals(0, d.getPeriodEarned());
        Assert.assertEquals("S3", d.getTargetLevel());
        Assert.assertEquals("DOWNGRADE", d.getChangeType());
        Assert.assertTrue(d.getBenefits().contains(
                "成长值未达当前等级保级线，等级由 S4 调整为 S3"));
    }

    /**
     * 金卡会员保级，退款率 20% 档成长值按 80% 计入
     */
    @Test
    public void shouldMaintainGoldWithRefundRateDeduction() {
        MemberProfile member = base("M004", "S3", 8000);
        member.setPeriodSpendYuan(1000);
        member.setPeriodOrders(5);
        member.setRefundRate(20);

        MemberLoyaltyDecision d = service.evaluate(member);

        Assert.assertEquals(880, d.getPeriodEarned());
        Assert.assertEquals(8880, d.getNewGrowthValue());
        Assert.assertEquals("S3", d.getTargetLevel());
        Assert.assertEquals("MAINTAIN", d.getChangeType());
        Assert.assertEquals(0.94, d.getDiscount(), 0.0001);
    }

    /**
     * 两期违规套取权益 → 本期成长值清零，折扣冻结，升降级冻结
     */
    @Test
    public void shouldFreezeBenefitsForRepeatedViolation() {
        MemberProfile member = base("M005", "S3", 19000);
        member.setPeriodSpendYuan(5000);
        member.setPeriodOrders(20);
        member.setViolationCount(2);

        MemberLoyaltyDecision d = service.evaluate(member);

        Assert.assertEquals(0, d.getPeriodEarned());
        Assert.assertEquals(19000, d.getNewGrowthValue());
        Assert.assertEquals("S3", d.getTargetLevel());
        Assert.assertEquals("MAINTAIN", d.getChangeType());
        Assert.assertTrue(d.isBenefitFrozen());
        Assert.assertEquals(1.0, d.getDiscount(), 0.0001);
        Assert.assertTrue(d.getBenefits().get(0).contains("违规套取权益行为，新增成长值清零"));
    }

    /**
     * 等级 30 天内到期触发保级提醒，远期不提醒
     */
    @Test
    public void shouldRemindRenewalWithinThirtyDays() {
        MemberProfile expiring = base("M006", "S2", 1900);
        expiring.setPeriodSpendYuan(100);
        expiring.setDaysToExpire(15);
        MemberLoyaltyDecision d1 = service.evaluate(expiring);
        Assert.assertTrue(d1.isRenewalReminder());
        Assert.assertTrue(d1.getBenefits().contains("等级距到期仅剩 15 天，保持消费即可保级，敬请关注续级提醒"));

        MemberProfile safe = base("M007", "S2", 1900);
        safe.setPeriodSpendYuan(100);
        safe.setDaysToExpire(200);
        MemberLoyaltyDecision d2 = service.evaluate(safe);
        Assert.assertFalse(d2.isRenewalReminder());
    }

    private MemberProfile base(String id, String level, int growth) {
        MemberProfile m = new MemberProfile();
        m.setMemberId(id);
        m.setMemberName("测试会员");
        m.setCurrentLevel(level);
        m.setGrowthValue(growth);
        m.setPeriodSpendYuan(0);
        m.setPeriodOrders(0);
        m.setRefundRate(0);
        m.setDaysToExpire(100);
        m.setViolationCount(0);
        return m;
    }
}
