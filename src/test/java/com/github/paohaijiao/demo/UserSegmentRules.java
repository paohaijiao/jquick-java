package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 28：用户活跃度分层与价值评分规则
 */
public interface UserSegmentRules {

    /**
     * 活跃度分层：按浏览天数与最近购买日逐级覆盖
     */
    String activitySegment(@Param("browseDays") int browseDays,
                           @Param("lastOrderDays") int lastOrderDays);

    /**
     * 消费金额得分（0-30）
     */
    int scoreGmv(@Param("gmv") double gmv);

    /**
     * 订单数得分（0-20）
     */
    int scoreOrders(@Param("orders") int orders);

    /**
     * 会员等级得分（0-15）
     */
    int scoreMember(@Param("rank") int rank);

    /**
     * 客单价得分（0-10）
     */
    int scoreAvgPrice(@Param("avgAmount") double avgAmount);
}
