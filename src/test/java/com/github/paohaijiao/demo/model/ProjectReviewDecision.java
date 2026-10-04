package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 科技项目立项初审决策结果
 */
@Data
public class ProjectReviewDecision {

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 评审总分（0-100）
     */
    private int totalScore;

    /**
     * 评审结论：APPROVE-拟立项 / REVIEW-答辩复评 / REJECT-不予立项 / MATERIAL_PENDING-补正
     */
    private String result;

    /**
     * 核定财政资助额度（万元，仅拟立项时大于 0）
     */
    private int grantAmountWan;

    /**
     * 各维度评分明细
     */
    private List<String> scoreDetails = new ArrayList<String>();

    /**
     * 审核依据与说明
     */
    private List<String> reasons = new ArrayList<String>();

    public void addScoreDetail(String detail) {
        this.scoreDetails.add(detail);
    }

    public void addReason(String reason) {
        this.reasons.add(reason);
    }
}
