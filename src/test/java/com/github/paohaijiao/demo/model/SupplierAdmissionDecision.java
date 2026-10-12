package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 供应商准入与保理授信决策结果
 */
@Data
public class SupplierAdmissionDecision {

    /**
     * 供应商编号
     */
    private String supplierId;

    /**
     * 准入结论：APPROVE-准入 / CONDITIONAL-附条件准入 / REJECT-淘汰
     */
    private String decision;

    /**
     * 供应商评级：A-战略 / B-合格 / C-限制 / D-淘汰
     */
    private String grade;

    /**
     * 综合评分（0-100）
     */
    private int totalScore;

    /**
     * 核定保理额度（元）
     */
    private double factoringLimit;

    /**
     * 是否要求追加担保/保证金
     */
    private boolean requireGuarantee;

    /**
     * 建议结算账期（天）
     */
    private int settlementDays;

    /**
     * 评分明细
     */
    private List<String> scoreDetails = new ArrayList<String>();

    /**
     * 拒绝原因
     */
    private List<String> rejectReasons = new ArrayList<String>();

    /**
     * 风险提示与附加条件
     */
    private List<String> conditions = new ArrayList<String>();

    public void addScoreDetail(String detail) {
        this.scoreDetails.add(detail);
    }

    public void addRejectReason(String reason) {
        this.rejectReasons.add(reason);
    }

    public void addCondition(String condition) {
        this.conditions.add(condition);
    }
}
