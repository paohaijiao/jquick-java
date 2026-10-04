package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 理赔核赔结果
 */
@Data
public class ClaimDecision {

    /**
     * 理赔单号
     */
    private String claimNo;

    /**
     * 核赔结论：APPROVE-正常赔付 / MANUAL_AUDIT-转人工审核 / REJECT-拒赔
     */
    private String auditResult;

    /**
     * 赔付金额（元）
     */
    private double payableAmount;

    /**
     * 实际扣减的免赔额（元）
     */
    private double deductibleAmount;

    /**
     * 适用赔付比例
     */
    private double reimburseRatio;

    /**
     * 理赔风险分
     */
    private int riskScore;

    /**
     * 理赔风险等级：LOW / MEDIUM / HIGH
     */
    private String riskLevel;

    /**
     * 是否需要转调查岗
     */
    private boolean needInvestigation;

    /**
     * 是否走小额快赔通道
     */
    private boolean fastTrack;

    /**
     * 核赔说明，供理赔员与客户解释使用
     */
    private List<String> auditNotes = new ArrayList<String>();

    public void addAuditNote(String note) {
        this.auditNotes.add(note);
    }
}
