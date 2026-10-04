package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 预售尾款资格与履约决策
 */
@Data
public class PresaleFinalPaymentDecision {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 处置动作：PAYABLE 可付尾款 / WAITING 待窗口或待库存 / CANCELLED 已取消 /
     * FORFEIT 定金没收 / HOLD 待实名 / BLOCKED 商家失格关单
     */
    private String action;

    /**
     * 本次应支付尾款
     */
    private double payableAmount;

    /**
     * 定金处理：DEDUCT 担保抵扣 / REFUNDABLE 可退 / FORFEIT 没收 / HOLD 暂挂
     */
    private String depositTreatment;

    /**
     * 扣减锁定库存后的可售现货
     */
    private int availableStock;

    /**
     * 商家是否具备预售履约资格
     */
    private boolean merchantEligible;

    /**
     * 审核说明
     */
    private List<String> reasons = new ArrayList<String>();

    public void addReason(String reason) {
        this.reasons.add(reason);
    }
}
