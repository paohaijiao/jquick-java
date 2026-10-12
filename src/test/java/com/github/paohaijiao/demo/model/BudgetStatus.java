package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门预算执行状态与单笔支出申请
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetStatus {

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 年度预算
     */
    private double annualBudget;

    /**
     * 实际已支出
     */
    private double actualSpent;

    /**
     * 已承诺金额（已签合同/在途采购单）
     */
    private double committedAmount;

    /**
     * 当前月份（1-12）
     */
    private int month;

    /**
     * 年末预测支出
     */
    private double forecastYearEnd;

    /**
     * 本次单笔申请支出金额
     */
    private double singleExpenseAmount;

    /**
     * 支出类别：CAPEX 资本性 / OPEX 运营 / ADMIN 行政 / MARKETING 市场
     */
    private String expenseCategory;

    /**
     * 是否应急支出
     */
    private boolean emergency;

    /**
     * CAPEX 是否已完成立项审批
     */
    private boolean capexApproved;
}
