package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 简历初筛决策结果
 */
@Data
public class ScreeningDecision {

    /**
     * 候选人姓名
     */
    private String candidateName;

    /**
     * 综合评分（0-100）
     */
    private int totalScore;

    /**
     * 硬性条件是否全部满足
     */
    private boolean hardCheckPass;

    /**
     * 初筛结论：INTERVIEW-推荐面试 / RESERVE-人才储备 / REJECT-不通过
     */
    private String result;

    /**
     * 推荐等级：STRONG / NORMAL / RESERVE / NONE
     */
    private String recommendLevel;

    /**
     * 命中与不满足条件说明
     */
    private List<String> reasons = new ArrayList<String>();

    public void addReason(String reason) {
        this.reasons.add(reason);
    }
}
