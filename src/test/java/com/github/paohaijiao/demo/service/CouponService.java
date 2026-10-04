package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.CouponRules;
import com.github.paohaijiao.demo.model.CouponDecision;
import com.github.paohaijiao.demo.model.CouponRequest;

/**
 * 场景 10：优惠券发放服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：优惠金额汇总、券类型与约束装配、发放依据组装</li>
 *     <li><b>规则侧</b>：满减分档、新客/忠诚/品类奖励、券类型优先级、门槛与风控阈值，全部外置在 XML 中</li>
 * </ul>
 */
public class CouponService {

    private final CouponRules rules;

    public CouponService(CouponRules rules) {
        this.rules = rules;
    }

    /**
     * 计算发券决策
     *
     * @param request 用户与订单快照
     * @return 发券决策
     */
    public CouponDecision issue(CouponRequest request) {
        CouponDecision decision = new CouponDecision();
        decision.setUserId(request.getUserId());

        double base = rules.baseDiscount(request.getMemberLevel(), request.getOrderAmount());
        double newUserBonus = rules.newUserBonus(request.isNewUser(), request.getJoinDays());
        double loyaltyBonus = rules.loyaltyBonus(request.getMemberLevel(), request.getOrderCount());
        double categoryBonus = rules.categoryBonus(request.getCategory(), request.isCampaignActive());
        double discountAmount = base + newUserBonus + loyaltyBonus + categoryBonus;

        String couponType = rules.couponType(request.getMemberLevel(), request.getOrderAmount(),
                request.isNewUser(), request.getJoinDays(), request.getOrderCount(),
                request.getCategory(), request.isCampaignActive());

        if ("NONE".equals(couponType)) {
            discountAmount = 0.0;
        }

        decision.setCouponType(couponType);
        decision.setDiscountAmount(discountAmount);
        decision.setCouponName(rules.couponName(couponType, discountAmount));
        decision.setThreshold(rules.threshold(discountAmount));
        decision.setValidDays(rules.validDays(couponType));
        decision.setNeedManualReview(rules.needManualReview(request.isRiskFlag(), discountAmount));
        decision.setStackable(rules.stackable(couponType, request.isHasUsedCoupon()));
        decision.setRiskLevel(rules.riskLevel(request.isRiskFlag(), request.getOrderCount(), request.getJoinDays()));

        buildReasons(request, decision, base, newUserBonus, loyaltyBonus, categoryBonus);
        return decision;
    }

    private void buildReasons(CouponRequest request, CouponDecision decision, double base,
                              double newUserBonus, double loyaltyBonus, double categoryBonus) {
        if ("NONE".equals(decision.getCouponType())) {
            decision.addReason("订单金额与用户画像未命中任何发券策略");
            return;
        }
        decision.addReason("会员等级 " + request.getMemberLevel() + " 命中基础满减 " + base + " 元");
        if (newUserBonus > 0.0) {
            decision.addReason("新客注册 " + request.getJoinDays() + " 天，叠加新客奖励 " + newUserBonus + " 元");
        }
        if (loyaltyBonus > 0.0) {
            decision.addReason("历史下单 " + request.getOrderCount() + " 单，叠加忠诚奖励 " + loyaltyBonus + " 元");
        }
        if (categoryBonus > 0.0) {
            decision.addReason("活动期 " + request.getCategory() + " 品类，叠加品类奖励 " + categoryBonus + " 元");
        }
        if (request.isRiskFlag()) {
            decision.addReason("命中风控标记，需人工复核后再发放");
        } else if (decision.isNeedManualReview()) {
            decision.addReason("优惠金额达到 200 元复核线，转人工复核");
        }
        if (!decision.isStackable()) {
            decision.addReason("该券不可与其他优惠叠加");
        }
    }
}
