package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 29：分销多级分润的类目费率与身份系数
 */
public interface DistributionCommissionRules {

    /**
     * 类目基础佣金率
     */
    double categoryRate(@Param("category") String category);

    /**
     * 分销商等级系数
     */
    double levelMultiplier(@Param("level") String level);

    /**
     * 直推奖励率（一级上线）
     */
    double directReferRate(@Param("uplineLevel") String uplineLevel);

    /**
     * 间推奖励率（二级上线）
     */
    double indirectReferRate(@Param("rootLevel") String rootLevel);

    /**
     * 活动订单可佣基数系数（让利折减）
     */
    double baseFactor(@Param("promotionCostRate") int promotionCostRate);

    /**
     * 团长服务费费率
     */
    double groupBonusRate(@Param("groupLeader") boolean groupLeader,
                          @Param("groupCount") int groupCount);
}
