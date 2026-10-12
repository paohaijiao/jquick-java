package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 35：薪资补贴各分项核算规则
 */
public interface SalaryAllowanceRules {

    /**
     * 工龄津贴：20 年 8%、10 年 5%、5 年 3%、满 1 年 1%
     */
    double seniorityAllowance(@Param("baseSalary") double baseSalary,
                             @Param("years") int years);

    /**
     * 夜班补贴：50 元/班
     */
    int nightShiftAllowance(@Param("shifts") int shifts);

    /**
     * 高温津贴：户外 30 元/天，室内 15 元/天
     */
    int highTempAllowance(@Param("days") int days,
                          @Param("outdoor") boolean outdoor);

    /**
     * 加班工资：工作日 1.5 倍、休息日 2 倍、法定节假日 3 倍
     */
    double overtimePay(@Param("hourlyBase") double hourlyBase,
                       @Param("hours") double hours,
                       @Param("type") String type);

    /**
     * 餐补：30 元/天
     */
    int mealAllowance(@Param("days") int days);

    /**
     * 边远地区津贴：基本工资 10%
     */
    double remoteAreaAllowance(@Param("baseSalary") double baseSalary,
                               @Param("remote") boolean remote);

    /**
     * 全勤奖：300 元
     */
    int perfectAttendanceBonus(@Param("perfect") boolean perfect);

    /**
     * 绩效奖金：≥95 30%、≥85 20%、≥70 10%、≥60 5%，其余为 0
     */
    double performanceBonus(@Param("baseSalary") double baseSalary,
                            @Param("score") int score);
}
