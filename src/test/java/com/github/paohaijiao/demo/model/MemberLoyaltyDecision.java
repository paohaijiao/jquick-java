package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员等级评定与权益决策结果
 */
@Data
public class MemberLoyaltyDecision {

    /**
     * 会员号
     */
    private String memberId;

    /**
     * 期末累计成长值
     */
    private int newGrowthValue;

    /**
     * 本期新增成长值
     */
    private int periodEarned;

    /**
     * 期初等级
     */
    private String currentLevel;

    /**
     * 评定后等级
     */
    private String targetLevel;

    /**
     * 等级变动：UPGRADE 升级 / DOWNGRADE 降级 / MAINTAIN 保级
     */
    private String changeType;

    /**
     * 评定等级对应折扣（权益冻结时为 1.0）
     */
    private double discount;

    /**
     * 是否触发临期保级提醒
     */
    private boolean renewalReminder;

    /**
     * 权益是否因违规被冻结
     */
    private boolean benefitFrozen;

    /**
     * 等级权益与评定说明
     */
    private List<String> benefits = new ArrayList<String>();

    public void addBenefit(String benefit) {
        this.benefits.add(benefit);
    }
}
