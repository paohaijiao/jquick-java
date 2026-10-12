package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 投资者适当性匹配决策结果
 */
@Data
public class SuitabilityDecision {

    /**
     * 客户号
     */
    private String investorId;

    /**
     * 匹配结论：ALLOW-可购买 / CONFIRM-确认后购买（双录+特别风险揭示） / BLOCK-禁止购买
     */
    private String decision;

    /**
     * 投资者成熟度评分（0-100）
     */
    private int maturityScore;

    /**
     * 风险承受等级（1-5）
     */
    private int riskToleranceRank;

    /**
     * 产品风险等级（1-5）
     */
    private int productRiskRank;

    /**
     * 风险等级差（产品等级 - 承受等级，正数表示产品超出承受能力）
     */
    private int rankGap;

    /**
     * 该客户允许购买的最高产品风险等级
     */
    private int maxAllowedRank;

    /**
     * 是否必须双录（录音录像）并手抄风险提示
     */
    private boolean doubleRecordingRequired;

    /**
     * 阻断/限制原因
     */
    private List<String> reasons = new ArrayList<String>();

    /**
     * 评分明细
     */
    private List<String> scoreDetails = new ArrayList<String>();

    public void addReason(String reason) {
        this.reasons.add(reason);
    }

    public void addScoreDetail(String detail) {
        this.scoreDetails.add(detail);
    }
}
