package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.ApprovalRoutingRules;
import com.github.paohaijiao.demo.model.ApprovalRequest;
import com.github.paohaijiao.demo.model.ApprovalRoutingDecision;

/**
 * 场景 36：请假/加班/出差审批智能流转
 */
public class ApprovalRoutingService {

    private final ApprovalRoutingRules rules;

    public ApprovalRoutingService(ApprovalRoutingRules rules) {
        this.rules = rules;
    }

    public ApprovalRoutingDecision route(ApprovalRequest req) {
        ApprovalRoutingDecision d = new ApprovalRoutingDecision();
        d.setRequestId(req.getRequestId());

        if ("LEAVE".equals(req.getApprovalType())) {
            if (!routeLeave(req, d)) {
                return d;
            }
        } else if ("OVERTIME".equals(req.getApprovalType())) {
            routeOvertime(req, d);
        } else {
            routeTrip(req, d);
        }

        applyExecutiveEscalation(req, d);
        d.setSlaHours(rules.slaHours(d.getFinalApproverRole()));
        d.setStatus("ROUTED");
        return d;
    }

    private boolean routeLeave(ApprovalRequest req, ApprovalRoutingDecision d) {
        if ("ANNUAL".equals(req.getLeaveType()) && !req.isAnnualLeaveEnough()) {
            d.setStatus("REJECTED");
            d.setRejectReason("年假余额不足，请核减假期或改走事假流程");
            return false;
        }
        if ("SICK".equals(req.getLeaveType())
                && rules.sickCertificateRequired(req.getHours())
                && !req.isSickCertificate()) {
            d.setStatus("REJECTED");
            d.setRejectReason("病假超过 3 个工作日（24 小时），须上传医院诊断证明后再提交");
            return false;
        }
        String approver = rules.leaveApprover(req.getLeaveType(), req.getHours());
        d.setFirstApproverRole(approver);
        d.setFinalApproverRole(approver);
        if ("SICK".equals(req.getLeaveType()) && rules.sickCertificateRequired(req.getHours())) {
            d.addCountersign("HR");
            d.addNote("长病假由 " + approver + " 审批并加签 HR 核发病假工资");
        }
        return true;
    }

    private void routeOvertime(ApprovalRequest req, ApprovalRoutingDecision d) {
        String approver = rules.overtimeApprover(req.getHours(), req.isRetroactiveOvertime());
        d.setFirstApproverRole(approver);
        d.setFinalApproverRole(approver);
        if (req.isRetroactiveOvertime()) {
            d.addCountersign("HR");
            d.addNote("跨期补录加班由 " + approver + " 审批并加签 HR 复核考勤原始记录");
        }
    }

    private void routeTrip(ApprovalRequest req, ApprovalRoutingDecision d) {
        String approver = rules.tripApprover(req.getTripAmount());
        if (req.isOverseasTrip() && "DEPT_MANAGER".equals(approver)) {
            approver = "VP";
            d.addNote("境外出差预算审批由部门经理升级至 VP");
        }
        d.setFirstApproverRole(approver);
        d.setFinalApproverRole(approver);
        if (req.isOverseasTrip() && !"DEPT_MANAGER".equals(approver)) {
            d.addNote("境外出差须同步完成外事/安全审批备案");
        }
    }

    private void applyExecutiveEscalation(ApprovalRequest req, ApprovalRoutingDecision d) {
        if ("EXECUTIVE".equals(req.getEmployeeLevel()) && !"CEO".equals(d.getFinalApproverRole())) {
            d.addNote("高管申请规避同级自审，终审由 " + d.getFinalApproverRole() + " 上提至 CEO");
            d.setFinalApproverRole("CEO");
        }
    }
}
