package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 多级分润决策
 */
@Data
public class DistributionCommissionDecision {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 可佣基数（活动让利后）
     */
    private double baseAmount;

    /**
     * 自购/直销佣金
     */
    private double selfCommission;

    /**
     * 直推上级奖励
     */
    private double directCommission;

    /**
     * 间推上级奖励
     */
    private double indirectCommission;

    /**
     * 团长团队服务费
     */
    private double groupBonus;

    /**
     * 分润合计（受 30% 封顶约束）
     */
    private double totalCommission;

    /**
     * 是否触发 30% 分润上限熔断
     */
    private boolean capped;

    /**
     * 状态：SETTLED 可结算 / PENDING 待人工审核 / FORFEITED 退款没收
     */
    private String status;

    /**
     * 分润明细与说明
     */
    private List<String> breakdown = new ArrayList<String>();

    public void addBreakdown(String line) {
        this.breakdown.add(line);
    }
}
