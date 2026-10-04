package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.SalaryAllowanceRules;
import com.github.paohaijiao.demo.model.PayrollDecision;
import com.github.paohaijiao.demo.model.PayrollInput;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 场景 35：薪资补贴自动核算（工龄/夜班/高温/加班/餐补/边远/全勤/绩效）
 */
public class SalaryAllowanceService {

    /**
     * 法定月度加班时长上限（小时）
     */
    private static final double MAX_LEGAL_OVERTIME_HOURS = 36.0;

    private final SalaryAllowanceRules rules;

    public SalaryAllowanceService(SalaryAllowanceRules rules) {
        this.rules = rules;
    }

    public PayrollDecision calculate(PayrollInput input) {
        PayrollDecision d = new PayrollDecision();
        d.setEmployeeId(input.getEmployeeId());

        d.setSeniorityAllowance(money(rules.seniorityAllowance(
                input.getBaseSalary(), input.getYearsOfService())));
        d.setNightShiftAllowance(rules.nightShiftAllowance(input.getNightShifts()));

        if (input.getHighTempDays() > 0 && (input.getPayrollMonth() < 6 || input.getPayrollMonth() > 9)) {
            d.setHighTempAllowance(0.0);
            d.addNote("申报高温作业 " + input.getHighTempDays()
                    + " 天，但 " + input.getPayrollMonth()
                    + " 月不在高温津贴发放期（6-9 月），该补贴不予核发");
        } else {
            d.setHighTempAllowance(rules.highTempAllowance(
                    input.getHighTempDays(), input.isHighTempOutdoor()));
        }

        double payableOvertimeHours = input.getOvertimeHours();
        if (payableOvertimeHours > MAX_LEGAL_OVERTIME_HOURS) {
            d.addNote("申报加班 " + input.getOvertimeHours()
                    + " 小时超出法定上限 36 小时，超出部分按 "
                    + (payableOvertimeHours - MAX_LEGAL_OVERTIME_HOURS) + " 小时核减");
            payableOvertimeHours = MAX_LEGAL_OVERTIME_HOURS;
        }
        d.setOvertimePay(money(rules.overtimePay(input.getOvertimeHourlyBase(),
                payableOvertimeHours, input.getOvertimeType())));
        d.setMealAllowance(rules.mealAllowance(input.getMealAllowanceDays()));
        d.setRemoteAreaAllowance(money(rules.remoteAreaAllowance(
                input.getBaseSalary(), input.isRemoteArea())));
        d.setPerfectAttendanceBonus(rules.perfectAttendanceBonus(input.isAttendancePerfect()));
        d.setPerformanceBonus(money(rules.performanceBonus(
                input.getBaseSalary(), input.getPerformanceScore())));

        double total = money(d.getSeniorityAllowance() + d.getNightShiftAllowance()
                + d.getHighTempAllowance() + d.getOvertimePay() + d.getMealAllowance()
                + d.getRemoteAreaAllowance() + d.getPerfectAttendanceBonus()
                + d.getPerformanceBonus());
        d.setTotalAllowance(total);
        d.setGrossPay(money(input.getBaseSalary() + total));
        return d;
    }

    private double money(double v) {
        return BigDecimal.valueOf(v).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
