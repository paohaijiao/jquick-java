package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 25：会员成长值累计与等级评定规则
 */
public interface MemberLoyaltyRules {

    /**
     * 本期可获成长值：每消费 1 元计 1 分，每单额外 20 分；高退款率折减
     */
    int periodEarn(@Param("spendYuan") int spendYuan,
                   @Param("orders") int orders,
                   @Param("refundRate") int refundRate);

    /**
     * 按累计成长值评定等级
     */
    String targetLevel(@Param("growthValue") int growthValue);

    /**
     * 等级序位（S1=1 ... S5=5）
     */
    int levelRank(@Param("level") String level);

    /**
     * 等级折扣系数
     */
    double levelDiscount(@Param("level") String level);

    /**
     * 临期 30 天内且无违规，推送保级提醒
     */
    boolean renewalReminder(@Param("daysToExpire") int daysToExpire,
                            @Param("violationCount") int violationCount);
}
