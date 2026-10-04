package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业经营贷授信决策结果
 */
@Data
public class CorporateCreditDecision {

    /**
     * 申请编号
     */
    private String applicationId;

    /**
     * 审批结论：APPROVE-批准 / MANUAL_REVIEW-人工复核 / REJECT-拒绝
     */
    private String decision;

    /**
     * 综合评分（0-100）
     */
    private int totalScore;

    /**
     * 企业信用等级：A / B / C / D
     */
    private String creditRating;

    /**
     * 核定授信额度（元）
     */
    private double approvedLimit;

    /**
     * 授信额度测算理论值（元，封顶前）
     */
    private double theoreticalLimit;

    /**
     * 执行年利率
     */
    private double interestRate;

    /**
     * 额度触发的封顶项：TAX-纳税倍数 / REVENUE-营收占比 / CEILING-绝对上限 / APPLY-申请额
     */
    private String cappedBy;

    /**
     * 评分明细
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

    public void addScoreDetail(String detail) {
        this.scoreDetails.add(detail);
    }

    public void addRejectReason(String reason) {
        this.rejectReasons.add(reason);
    }

    public void addRiskWarning(String warning) {
        this.riskWarnings.add(warning);
    }
}
