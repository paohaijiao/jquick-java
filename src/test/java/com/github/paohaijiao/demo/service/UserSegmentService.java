package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.UserSegmentRules;
import com.github.paohaijiao.demo.model.UserBehavior;
import com.github.paohaijiao.demo.model.UserSegmentDecision;

/**
 * 场景 28：用户画像动态分层与营销策略装配
 */
public class UserSegmentService {

    private final UserSegmentRules rules;

    public UserSegmentService(UserSegmentRules rules) {
        this.rules = rules;
    }

    public UserSegmentDecision segment(UserBehavior user) {
        UserSegmentDecision d = new UserSegmentDecision();
        d.setUserId(user.getUserId());

        String activity = rules.activitySegment(user.getBrowseDays30d(), user.getLastOrderDays());
        d.setActivitySegment(activity);

        int valueScore = rules.scoreGmv(user.getGmv90d())
                + rules.scoreOrders(user.getOrderCount90d())
                + rules.scoreMember(user.getMemberRank())
                + rules.scoreAvgPrice(user.getAvgOrderAmount());
        d.setValueScore(valueScore);

        String value;
        if (valueScore >= 55) {
            value = "HIGH_VALUE";
        } else if (valueScore >= 35) {
            value = "MID_VALUE";
        } else if (valueScore >= 15) {
            value = "LOW_VALUE";
        } else {
            value = "NO_VALUE";
        }
        d.setValueSegment(value);

        collectTags(d, user, activity, value);
        d.setMarketingStrategy(buildStrategy(activity, value, d.getTags()));
        return d;
    }

    private void collectTags(UserSegmentDecision d, UserBehavior user,
                             String activity, String value) {
        if (user.getCouponSensitivity() >= 60) {
            d.addTag("PRICE_SENSITIVE");
        }
        if (user.getReturnRate() >= 30) {
            d.addTag("HIGH_RETURN_RISK");
        }
        if ("ACTIVE".equals(activity) && "HIGH_VALUE".equals(value)) {
            d.addTag("VIP_CULTIVATION");
        }
        if ("DORMANT".equals(activity) && user.getMemberRank() >= 4) {
            d.addTag("WINBACK");
        }
    }

    private String buildStrategy(String activity, String value, java.util.List<String> tags) {
        if (tags.contains("HIGH_RETURN_RISK")) {
            return "审慎营销：仅做内容种草，收紧退货险与运费险权益";
        }
        if ("HIGH_VALUE".equals(value) && "ACTIVE".equals(activity)) {
            return "专属客户经理一对一维护，发放 VIP 专享权益";
        }
        if ("HIGH_VALUE".equals(value)) {
            return "EDM/短信推送大额召回券，安排客服一对一唤醒";
        }
        if ("MID_VALUE".equals(value)
                && ("ACTIVE".equals(activity) || "WARM".equals(activity))) {
            return "推送满减加购券与凑单活动，提升客单价";
        }
        if ("MID_VALUE".equals(value)) {
            return "EDM 推送品牌内容与爆品清单，保持品牌触达";
        }
        if ("LOW_VALUE".equals(value)
                && ("ACTIVE".equals(activity) || "WARM".equals(activity))) {
            return "签到领积分与低价爆品推送，促进首单与复购";
        }
        return "进入静默池，仅大促节点低频触达，不做主动打扰";
    }
}
