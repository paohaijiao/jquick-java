package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工审批申请要素（请假/加班/出差统一入口）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalRequest {

    /**
     * 申请单号
     */
    private String requestId;

    private String employeeId;
    private String employeeName;

    /**
     * 申请类型：LEAVE 请假 / OVERTIME 加班 / TRIP 出差
     */
    private String approvalType;

    /**
     * 请假类型：PERSONAL 事假 / SICK 病假 / ANNUAL 年假
     */
    private String leaveType;

    /**
     * 申请时长（小时，请假按每天 8 小时折算）
     */
    private double hours;

    /**
     * 出差预算金额（元）
     */
    private double tripAmount;

    /**
     * 申请人职级：REGULAR 普通员工 / TEAM_LEAD 主管 / DEPT_MANAGER 部门经理 / EXECUTIVE 高管
     */
    private String employeeLevel;

    /**
     * 加班是否为跨期补录（超过 3 个自然日提交）
     */
    private boolean retroactiveOvertime;

    /**
     * 病假是否附医院证明
     */
    private boolean sickCertificate;

    /**
     * 年假余额是否充足
     */
    private boolean annualLeaveEnough;

    /**
     * 是否境外出差
     */
    private boolean overseasTrip;
}
