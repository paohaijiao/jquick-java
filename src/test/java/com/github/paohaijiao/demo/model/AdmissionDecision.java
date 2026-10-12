package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 授信审批决策结果
 */
@Data
public class AdmissionDecision {

    /**
     * 申请编号
     */
    private String applicationId;

    /**
     * 准入结论：PASS-通过 / MANUAL_REVIEW-人工复核 / REJECT-拒绝
     */
    private String admit;

    /**
     * 风控总分（0-100）
     */
    private int totalScore;

    /**
     * 风险等级：A / B / C / D
     */
    private String riskLevel;

    /**
     * 建议授信额度（元）
     */
    private double suggestedLimit;

    /**
     * 执行利率（年化）
     */
    private double interestRate;

    /**
     * 各维度评分明细，便于人工复核与模型回溯
     */
    private List<String> scoreDetails = new ArrayList<String>();

    /**
     * 拒绝原因
     */
    private List<String> rejectReasons = new ArrayList<String>();

    /**
     * 风险提示
     */
    private List<String> riskWarnings = new ArrayList<String>();

    public void addRejectReason(String reason) {
        this.rejectReasons.add(reason);
    }

    public void addRiskWarning(String warning) {
        this.riskWarnings.add(warning);
    }

    public void addScoreDetail(String detail) {
        this.scoreDetails.add(detail);
    }
}
