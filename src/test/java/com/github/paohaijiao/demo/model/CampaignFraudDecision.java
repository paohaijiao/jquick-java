package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 营销刷单风控决策
 */
@Data
public class CampaignFraudDecision {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 风险总分（0-100，越高越危险）
     */
    private int riskScore;

    /**
     * 处置动作：PASS 放行 / REVIEW 复核 / REJECT 拦截
     */
    private String action;

    /**
     * 是否命中硬规则
     */
    private boolean hardRuleHit;

    /**
     * 各维度得分明细
     */
    private List<String> scoreDetails = new ArrayList<String>();

    /**
     * 命中的风险标签
     */
    private List<String> riskTags = new ArrayList<String>();

    public void addDetail(String detail) {
        this.scoreDetails.add(detail);
    }

    public void addRiskTag(String tag) {
        this.riskTags.add(tag);
    }
}
