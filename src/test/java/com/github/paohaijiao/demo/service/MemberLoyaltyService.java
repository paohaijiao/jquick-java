package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.MemberLoyaltyRules;
import com.github.paohaijiao.demo.model.MemberLoyaltyDecision;
import com.github.paohaijiao.demo.model.MemberProfile;

/**
 * 场景 25：会员成长值累计、等级升降级与权益装配
 */
public class MemberLoyaltyService {

    private final MemberLoyaltyRules rules;

    public MemberLoyaltyService(MemberLoyaltyRules rules) {
        this.rules = rules;
    }

    public MemberLoyaltyDecision evaluate(MemberProfile member) {
        MemberLoyaltyDecision decision = new MemberLoyaltyDecision();
        decision.setMemberId(member.getMemberId());
        decision.setCurrentLevel(member.getCurrentLevel());

        int earned = rules.periodEarn(member.getPeriodSpendYuan(),
                member.getPeriodOrders(), member.getRefundRate());

        boolean frozen = member.getViolationCount() >= 2;
        if (frozen) {
            earned = 0;
        }
        decision.setPeriodEarned(earned);
        decision.setBenefitFrozen(frozen);

        int newGrowth = member.getGrowthValue() + earned;
        decision.setNewGrowthValue(newGrowth);

        String target = rules.targetLevel(newGrowth);
        decision.setTargetLevel(target);

        int currentRank = rules.levelRank(member.getCurrentLevel());
        int targetRank = rules.levelRank(target);

        double discount = rules.levelDiscount(target);
        if (frozen) {
            decision.setChangeType("MAINTAIN");
            decision.setDiscount(1.0);
        } else if (targetRank > currentRank) {
            decision.setChangeType("UPGRADE");
            decision.setDiscount(discount);
        } else if (targetRank < currentRank) {
            decision.setChangeType("DOWNGRADE");
            decision.setDiscount(discount);
        } else {
            decision.setChangeType("MAINTAIN");
            decision.setDiscount(discount);
        }

        assembleBenefits(decision, member, target, currentRank, targetRank, frozen);

        boolean reminder = rules.renewalReminder(member.getDaysToExpire(), member.getViolationCount());
        decision.setRenewalReminder(reminder);
        if (reminder) {
            decision.addBenefit("等级距到期仅剩 " + member.getDaysToExpire()
                    + " 天，保持消费即可保级，敬请关注续级提醒");
        }

        return decision;
    }

    private void assembleBenefits(MemberLoyaltyDecision decision, MemberProfile member,
                                  String target, int currentRank, int targetRank, boolean frozen) {
        if (frozen) {
            decision.addBenefit("本期存在 " + member.getViolationCount()
                    + " 次违规套取权益行为，新增成长值清零，升降级与折扣权益冻结一期");
            return;
        }
        if ("UPGRADE".equals(decision.getChangeType())) {
            decision.addBenefit("成长值达到 " + decision.getNewGrowthValue()
                    + " 分，等级由 " + member.getCurrentLevel() + " 升级为 " + target);
        } else if ("DOWNGRADE".equals(decision.getChangeType())) {
            decision.addBenefit("成长值未达当前等级保级线，等级由 "
                    + member.getCurrentLevel() + " 调整为 " + target);
        } else {
            decision.addBenefit("成长值 " + decision.getNewGrowthValue()
                    + " 分，维持 " + target + " 等级");
        }
        if ("S5".equals(target)) {
            decision.addBenefit("黑金权益：全场 85 折、专属客户经理、全场免运费");
        } else if ("S4".equals(target)) {
            decision.addBenefit("白金权益：全场 9 折、全场免运费");
        } else if ("S3".equals(target)) {
            decision.addBenefit("金卡权益：全场 94 折");
        } else if ("S2".equals(target)) {
            decision.addBenefit("银卡权益：全场 97 折");
        } else {
            decision.addBenefit("注册会员：暂无等级折扣，多消费可快速升级");
        }
    }
}
