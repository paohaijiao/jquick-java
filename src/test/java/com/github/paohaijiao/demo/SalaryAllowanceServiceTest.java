package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.PayrollDecision;
import com.github.paohaijiao.demo.model.PayrollInput;
import com.github.paohaijiao.demo.service.SalaryAllowanceService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 35：薪资补贴自动核算
 * <p>
 * 6 组形态：全补贴标杆、新人基础补贴、法定节假日三倍加班、
 * 非高温季防错发、加班超 36 小时核减、绩效不达标与 60 分临界。
 */
public class SalaryAllowanceServiceTest {

    private SalaryAllowanceService service;

    @Before
    public void setUp() {
        SalaryAllowanceRules rules = JQuickJava.create()
                .createApi(SalaryAllowanceRules.class, "demo/salary-allowance.xml");
        this.service = new SalaryAllowanceService(rules);
    }

    /**
     * 12 年工龄、8 夜班、12 户外高温、10 小时平日加班、边远、全勤、90 分绩效
     */
    @Test
    public void shouldCalculateAllAllowanceItems() {
        PayrollInput i = base("E001", 10000, 7);
        i.setYearsOfService(12);
        i.setNightShifts(8);
        i.setHighTempDays(12);
        i.setHighTempOutdoor(true);
        i.setOvertimeHours(10);
        i.setOvertimeHourlyBase(60);
        i.setOvertimeType("WORKDAY");
        i.setMealAllowanceDays(22);
        i.setRemoteArea(true);
        i.setAttendancePerfect(true);
        i.setPerformanceScore(90);

        PayrollDecision d = service.calculate(i);

        Assert.assertEquals(500.0, d.getSeniorityAllowance(), 0.0001);
        Assert.assertEquals(400.0, d.getNightShiftAllowance(), 0.0001);
        Assert.assertEquals(360.0, d.getHighTempAllowance(), 0.0001);
        Assert.assertEquals(900.0, d.getOvertimePay(), 0.0001);
        Assert.assertEquals(660.0, d.getMealAllowance(), 0.0001);
        Assert.assertEquals(1000.0, d.getRemoteAreaAllowance(), 0.0001);
        Assert.assertEquals(300.0, d.getPerfectAttendanceBonus(), 0.0001);
        Assert.assertEquals(2000.0, d.getPerformanceBonus(), 0.0001);
        Assert.assertEquals(6120.0, d.getTotalAllowance(), 0.0001);
        Assert.assertEquals(16120.0, d.getGrossPay(), 0.0001);
    }

    /**
     * 新人无工龄/夜班/加班/全勤，仅餐补与 72 分绩效
     */
    @Test
    public void shouldCalculateFreshmanBasicAllowance() {
        PayrollInput i = base("E002", 6000, 6);
        i.setYearsOfService(0);
        i.setMealAllowanceDays(20);
        i.setPerformanceScore(72);

        PayrollDecision d = service.calculate(i);

        Assert.assertEquals(0.0, d.getSeniorityAllowance(), 0.0001);
        Assert.assertEquals(600.0, d.getMealAllowance(), 0.0001);
        Assert.assertEquals(600.0, d.getPerformanceBonus(), 0.0001);
        Assert.assertEquals(1200.0, d.getTotalAllowance(), 0.0001);
        Assert.assertEquals(7200.0, d.getGrossPay(), 0.0001);
    }

    /**
     * 6 年工龄 + 4 夜班 + 法定节假日 8 小时三倍加班 + 85 分绩效
     */
    @Test
    public void shouldPayTripleOvertimeOnHoliday() {
        PayrollInput i = base("E003", 8000, 10);
        i.setYearsOfService(6);
        i.setNightShifts(4);
        i.setHighTempDays(0);
        i.setOvertimeHours(8);
        i.setOvertimeHourlyBase(50);
        i.setOvertimeType("HOLIDAY");
        i.setMealAllowanceDays(0);
        i.setAttendancePerfect(true);
        i.setPerformanceScore(85);

        PayrollDecision d = service.calculate(i);

        Assert.assertEquals(240.0, d.getSeniorityAllowance(), 0.0001);
        Assert.assertEquals(200.0, d.getNightShiftAllowance(), 0.0001);
        Assert.assertEquals(1200.0, d.getOvertimePay(), 0.0001);
        Assert.assertEquals(300.0, d.getPerfectAttendanceBonus(), 0.0001);
        Assert.assertEquals(1600.0, d.getPerformanceBonus(), 0.0001);
        Assert.assertEquals(3540.0, d.getTotalAllowance(), 0.0001);
    }

    /**
     * 10 月申报 10 天户外高温 → 非发放期防错发清零
     */
    @Test
    public void shouldRejectHighTempAllowanceOutsideSummer() {
        PayrollInput i = base("E004", 7000, 10);
        i.setYearsOfService(2);
        i.setHighTempDays(10);
        i.setHighTempOutdoor(true);
        i.setMealAllowanceDays(10);
        i.setPerformanceScore(60);

        PayrollDecision d = service.calculate(i);

        Assert.assertEquals(0.0, d.getHighTempAllowance(), 0.0001);
        Assert.assertEquals(70.0, d.getSeniorityAllowance(), 0.0001);
        Assert.assertEquals(300.0, d.getMealAllowance(), 0.0001);
        Assert.assertEquals(350.0, d.getPerformanceBonus(), 0.0001);
        Assert.assertEquals(720.0, d.getTotalAllowance(), 0.0001);
        Assert.assertTrue(d.getNotes().get(0).contains("不在高温津贴发放期（6-9 月）"));
    }

    /**
     * 申报 40 小时工作日加班 → 仅核发 36 小时
     */
    @Test
    public void shouldCapOvertimeAtLegalLimit() {
        PayrollInput i = base("E005", 6000, 8);
        i.setYearsOfService(0);
        i.setOvertimeHours(40);
        i.setOvertimeHourlyBase(50);
        i.setOvertimeType("WORKDAY");
        i.setMealAllowanceDays(0);
        i.setPerformanceScore(0);

        PayrollDecision d = service.calculate(i);

        Assert.assertEquals(2700.0, d.getOvertimePay(), 0.0001);
        Assert.assertEquals(2700.0, d.getTotalAllowance(), 0.0001);
        Assert.assertTrue(d.getNotes().get(0).contains("超出法定上限 36 小时，超出部分按 4.0 小时核减"));
    }

    /**
     * 绩效 59 分无奖金；同条件 60 分临界拿 5%
     */
    @Test
    public void shouldHandlePerformanceThresholdAtSixty() {
        PayrollInput low = base("E006", 9000, 9);
        low.setYearsOfService(10);
        low.setNightShifts(10);
        low.setMealAllowanceDays(21);
        low.setPerformanceScore(59);
        PayrollDecision d1 = service.calculate(low);
        Assert.assertEquals(450.0, d1.getSeniorityAllowance(), 0.0001);
        Assert.assertEquals(500.0, d1.getNightShiftAllowance(), 0.0001);
        Assert.assertEquals(630.0, d1.getMealAllowance(), 0.0001);
        Assert.assertEquals(0.0, d1.getPerformanceBonus(), 0.0001);
        Assert.assertEquals(1580.0, d1.getTotalAllowance(), 0.0001);

        PayrollInput edge = base("E007", 9000, 9);
        edge.setYearsOfService(10);
        edge.setNightShifts(10);
        edge.setMealAllowanceDays(21);
        edge.setPerformanceScore(60);
        PayrollDecision d2 = service.calculate(edge);
        Assert.assertEquals(450.0, d2.getPerformanceBonus(), 0.0001);
        Assert.assertEquals(2030.0, d2.getTotalAllowance(), 0.0001);
    }

    private PayrollInput base(String id, double baseSalary, int month) {
        PayrollInput i = new PayrollInput();
        i.setEmployeeId(id);
        i.setEmployeeName("测试员工");
        i.setBaseSalary(baseSalary);
        i.setYearsOfService(0);
        i.setNightShifts(0);
        i.setPayrollMonth(month);
        i.setHighTempDays(0);
        i.setHighTempOutdoor(false);
        i.setOvertimeHours(0);
        i.setOvertimeHourlyBase(0);
        i.setOvertimeType("WORKDAY");
        i.setMealAllowanceDays(0);
        i.setRemoteArea(false);
        i.setAttendancePerfect(false);
        i.setPerformanceScore(0);
        return i;
    }
}
