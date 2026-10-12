package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 售后资格决策
 */
@Data
public class AfterSalesDecision {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 处置动作：RETURN_REFUND 退货退款 / EXCHANGE 换货 / REJECT 驳回
     */
    private String action;

    /**
     * 退款比例
     */
    private double refundRatio;

    /**
     * 运费承担方：MERCHANT 商家 / BUYER 买家 / NONE 不涉及
     */
    private String freightPayer;

    /**
     * 审核结论说明
     */
    private List<String> reasons = new ArrayList<String>();

    public void addReason(String reason) {
        this.reasons.add(reason);
    }
}
