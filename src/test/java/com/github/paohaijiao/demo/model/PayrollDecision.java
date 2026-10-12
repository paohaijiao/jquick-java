package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 月度补贴分项与应发合计
 */
@Data
public class PayrollDecision {

    /**
     * 工号
     */
    private String employeeId;

    private double seniorityAllowance;
    private double nightShiftAllowance;
    private double highTempAllowance;
    private double overtimePay;
    private double mealAllowance;
    private double remoteAreaAllowance;
    private double perfectAttendanceBonus;
    private double performanceBonus;

    /**
     * 补贴合计
     */
    private double totalAllowance;

    /**
     * 应发工资（基本工资 + 补贴）
     */
    private double grossPay;

    /**
     * 核算说明（防错发、超限提示）
     */
    private List<String> notes = new ArrayList<String>();

    public void addNote(String note) {
        this.notes.add(note);
    }
}
