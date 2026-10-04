package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 审批路由决策
 */
@Data
public class ApprovalRoutingDecision {

    /**
     * 申请单号
     */
    private String requestId;

    /**
     * ROUTED 已路由待审批 / REJECTED 退回补正
     */
    private String status;

    /**
     * 首审角色：TEAM_LEAD / DEPT_MANAGER / VP / CFO / CEO
     */
    private String firstApproverRole;

    /**
     * 终审角色
     */
    private String finalApproverRole;

    /**
     * 加签角色列表（HR / VP / FINANCE 等）
     */
    private List<String> countersignRoles = new ArrayList<String>();

    /**
     * 审批时限（小时）
     */
    private int slaHours;

    /**
     * 路由说明
     */
    private List<String> routeNotes = new ArrayList<String>();

    /**
     * 退回原因
     */
    private String rejectReason;

    public void addCountersign(String role) {
        this.countersignRoles.add(role);
    }

    public void addNote(String note) {
        this.routeNotes.add(note);
    }
}
