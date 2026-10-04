package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 人才补贴审核决策结果
 */
@Data
public class SubsidyDecision {

    /**
     * 申请单号
     */
    private String applyNo;

    /**
     * 资格评分（0-110）
     */
    private int qualificationScore;

    /**
     * 审核结论：QUALIFIED-通过 / MANUAL_REVIEW-人工复核 / REJECTED-驳回
     */
    private String stage;

    /**
     * 核定补贴金额（仅审核通过时大于 0）
     */
    private double subsidyAmount;

    /**
     * 学历补贴
     */
    private double educationAmount;

    /**
     * 人才认定奖励
     */
    private double talentAmount;

    /**
     * 创业带动就业奖励
     */
    private double startupAmount;

    /**
     * 审核依据与说明
     */
    private List<String> reasons = new ArrayList<String>();

    public void addReason(String reason) {
        this.reasons.add(reason);
    }
}
