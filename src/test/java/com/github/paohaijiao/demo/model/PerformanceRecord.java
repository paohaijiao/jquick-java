package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 绩效考核记录（绩效与奖金核算所需的考核快照）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceRecord {

    /**
     * 工号
     */
    private String employeeId;

    /**
     * 姓名
     */
    private String employeeName;

    /**
     * 部门
     */
    private String dept;

    /**
     * KPI 得分（0-100）
     */
    private double kpiScore;

    /**
     * 项目得分（0-100）
     */
    private double projectScore;

    /**
     * 团队协作风得分（0-100）
     */
    private double teamScore;

    /**
     * 考勤得分（0-100）
     */
    private double attendanceScore;

    /**
     * 是否管理者
     */
    private boolean manager;

    /**
     * 是否发生违规
     */
    private boolean hasViolation;

    /**
     * 年度销售额（元）
     */
    private double salesAmount;

    /**
     * 月度基本工资（元）
     */
    private double baseSalary;
}
