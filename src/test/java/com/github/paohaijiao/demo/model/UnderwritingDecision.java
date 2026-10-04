package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 核保决策结果
 */
@Data
public class UnderwritingDecision {

    /**
     * 投保单号
     */
    private String policyNo;

    /**
     * 核保结论：
     * PREFERRED-优选体 / STANDARD-标准体 / LOADED-加费承保 /
     * EXCLUSION-除外责任承保 / POSTPONE-延期观察 / DECLINE-拒保
     */
    private String result;

    /**
     * 健康风险评分（0-100，分数越高越健康）
     */
    private int totalScore;

    /**
     * 核定承保保额（元），超职业/年龄上限时自动降额
     */
    private double approvedSumInsured;

    /**
     * 是否发生降额承保
     */
    private boolean sumInsuredReduced;

    /**
     * 加费比例（百分比整数，如 25 表示加费 25%）
     */
    private int extraPremiumRate;

    /**
     * 加费金额（元）
     */
    private double extraPremium;

    /**
     * 优选体保费折扣比例（百分比整数）
     */
    private int premiumDiscountRate;

    /**
     * 最终保费（元）
     */
    private double finalPremium;

    /**
     * 除外责任清单
     */
    private List<String> exclusionItems = new ArrayList<String>();

    /**
     * 拒保原因
     */
    private List<String> declineReasons = new ArrayList<String>();

    /**
     * 核保说明与提示
     */
    private List<String> notes = new ArrayList<String>();

    /**
     * 评分明细
     */
    private List<String> scoreDetails = new ArrayList<String>();

    public void addExclusionItem(String item) {
        this.exclusionItems.add(item);
    }

    public void addDeclineReason(String reason) {
        this.declineReasons.add(reason);
    }

    public void addNote(String note) {
        this.notes.add(note);
    }

    public void addScoreDetail(String detail) {
        this.scoreDetails.add(detail);
    }
}
