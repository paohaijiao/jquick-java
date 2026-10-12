package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工月度薪资补贴核算要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayrollInput {

    /**
     * 工号
     */
    private String employeeId;

    /**
     * 姓名
     */
    private String employeeName;

    /**
     * 基本工资
     */
    private double baseSalary;

    /**
     * 本单位工龄（年）
     */
    private int yearsOfService;

    /**
     * 当月夜班次数
     */
    private int nightShifts;

    /**
     * 工资所属月份（1-12）
     */
    private int payrollMonth;

    /**
     * 高温作业天数
     */
    private int highTempDays;

    /**
     * 是否户外高温岗位
     */
    private boolean highTempOutdoor;

    /**
     * 加班时长（小时）
     */
    private double overtimeHours;

    /**
     * 加班小时基数（元/小时）
     */
    private double overtimeHourlyBase;

    /**
     * 加班类型：WORKDAY 工作日 / WEEKEND 休息日 / HOLIDAY 法定节假日
     */
    private String overtimeType;

    /**
     * 出勤餐补天数
     */
    private int mealAllowanceDays;

    /**
     * 是否边远地区岗位
     */
    private boolean remoteArea;

    /**
     * 是否全勤
     */
    private boolean attendancePerfect;

    /**
     * 月度绩效分（0-100）
     */
    private int performanceScore;
}
