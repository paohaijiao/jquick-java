package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券发放决策结果
 */
@Data
public class CouponDecision {

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 券类型：NEW_USER / VIP_EXCLUSIVE / LARGE_ORDER / LOYALTY / CATEGORY / ORDER / NONE
     */
    private String couponType;

    /**
     * 券名称
     */
    private String couponName;

    /**
     * 优惠金额
     */
    private double discountAmount;

    /**
     * 使用门槛（满 X 可用）
     */
    private double threshold;

    /**
     * 有效期（天）
     */
    private int validDays;

    /**
     * 是否可叠加
     */
    private boolean stackable;

    /**
     * 是否需要人工复核
     */
    private boolean needManualReview;

    /**
     * 风控等级：HIGH / MEDIUM / LOW
     */
    private String riskLevel;

    /**
     * 发放依据
     */
    private List<String> reasons = new ArrayList<String>();

    public void addReason(String reason) {
        this.reasons.add(reason);
    }
}
