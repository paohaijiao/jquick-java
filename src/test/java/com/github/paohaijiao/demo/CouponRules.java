package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 10：优惠券发放规则
 * <p>
 * 基础满减按会员等级与订单金额分档，叠加新客/忠诚/品类奖励后确定券类型、门槛与风控处置。
 */
public interface CouponRules {

    double baseDiscount(@Param("memberLevel") String memberLevel,
                        @Param("orderAmount") double orderAmount);

    double newUserBonus(@Param("newUser") boolean newUser,
                        @Param("joinDays") int joinDays);

    double loyaltyBonus(@Param("memberLevel") String memberLevel,
                        @Param("orderCount") int orderCount);

    double categoryBonus(@Param("category") String category,
                         @Param("campaignActive") boolean campaignActive);

    String couponType(@Param("memberLevel") String memberLevel,
                      @Param("orderAmount") double orderAmount,
                      @Param("newUser") boolean newUser,
                      @Param("joinDays") int joinDays,
                      @Param("orderCount") int orderCount,
                      @Param("category") String category,
                      @Param("campaignActive") boolean campaignActive);

    String couponName(@Param("couponType") String couponType,
                      @Param("discountAmount") double discountAmount);

    double threshold(@Param("discountAmount") double discountAmount);

    int validDays(@Param("couponType") String couponType);

    boolean needManualReview(@Param("riskFlag") boolean riskFlag,
                             @Param("discountAmount") double discountAmount);

    boolean stackable(@Param("couponType") String couponType,
                      @Param("hasUsedCoupon") boolean hasUsedCoupon);

    String riskLevel(@Param("riskFlag") boolean riskFlag,
                     @Param("orderCount") int orderCount,
                     @Param("joinDays") int joinDays);
}
