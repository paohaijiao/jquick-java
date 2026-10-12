package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 体检异常筛查与风险分层决策
 */
@Data
public class HealthScreeningDecision {

    /**
     * 报告号
     */
    private String reportId;

    /**
     * 整体风险级别：NORMAL / ATTENTION / HIGH / CRITICAL
     */
    private String highestLevel;

    /**
     * 异常指标数（ATTENTION 及以上）
     */
    private int abnormalCount;

    /**
     * CRITICAL 指标数
     */
    private int criticalCount;

    /**
     * HIGH 指标数
     */
    private int highCount;

    /**
     * 风险标签
     */
    private List<String> riskTags = new ArrayList<String>();

    /**
     * 逐项异常说明与复查建议
     */
    private List<String> advice = new ArrayList<String>();

    /**
     * 建议首诊科室
     */
    private String followUpDepartment;

    public void addRiskTag(String tag) {
        this.riskTags.add(tag);
    }

    public void addAdvice(String line) {
        this.advice.add(line);
    }
}
