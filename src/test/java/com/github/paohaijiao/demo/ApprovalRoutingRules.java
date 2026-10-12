package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 36：请假/加班/出差审批分级路由规则
 */
public interface ApprovalRoutingRules {

    /**
     * 请假分级审批人：
     * 年假 &gt;80h VP / &gt;24h 部门经理，其余主管；
     * 病假 &gt;40h VP / &gt;24h 部门经理，其余主管；
     * 事假 &gt;40h VP / &gt;8h 部门经理，其余主管
     */
    String leaveApprover(@Param("leaveType") String leaveType,
                         @Param("hours") double hours);

    /**
     * 出差预算分级审批人：≥3 万 CEO / ≥1 万 CFO / ≥3000 VP / 其余部门经理
     */
    String tripApprover(@Param("amount") double amount);

    /**
     * 加班审批人：跨期补录或超 16 小时 → 部门经理，其余主管
     */
    String overtimeApprover(@Param("hours") double hours,
                            @Param("retroactive") boolean retroactive);

    /**
     * 病假超 3 天（24 小时）须医院证明
     */
    boolean sickCertificateRequired(@Param("hours") double hours);

    /**
     * 按终审角色返回审批时限（小时）
     */
    int slaHours(@Param("role") String role);
}
