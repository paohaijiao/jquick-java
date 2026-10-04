package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.ApprovalRequest;
import com.github.paohaijiao.demo.model.ApprovalRoutingDecision;
import com.github.paohaijiao.demo.service.ApprovalRoutingService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 36：请假/加班/出差审批流转
 * <p>
 * 6 组形态：短时事假主管直审、年假分级与余额退回、长病假证明门控与 HR 加签、
 * 加班分级与跨期补录、出差预算与境外升级、大额差旅与高管同级规避。
 */
public class ApprovalRoutingServiceTest {

    private ApprovalRoutingService service;

    @Before
    public void setUp() {
        ApprovalRoutingRules rules = JQuickJava.create()
                .createApi(ApprovalRoutingRules.class, "demo/approval-routing.xml");
        this.service = new ApprovalRoutingService(rules);
    }

    /**
     * 普通员工 4 小时事假 → 主管 8 小时内审结
     */
    @Test
    public void shouldRouteShortPersonalLeaveToTeamLead() {
        ApprovalRequest req = leaveReq("A001", "PERSONAL", 4.0, "REGULAR");

        ApprovalRoutingDecision d = service.route(req);

        Assert.assertEquals("ROUTED", d.getStatus());
        Assert.assertEquals("TEAM_LEAD", d.getFirstApproverRole());
        Assert.assertEquals("TEAM_LEAD", d.getFinalApproverRole());
        Assert.assertEquals(8, d.getSlaHours());
        Assert.assertEquals(0, d.getCountersignRoles().size());
    }

    /**
     * 6 天年假余额足 → 部门经理；余额不足 → 退回
     */
    @Test
    public void shouldRouteAnnualLeaveByDaysAndRejectInsufficientBalance() {
        ApprovalRequest enough = leaveReq("A002", "ANNUAL", 48.0, "REGULAR");
        enough.setAnnualLeaveEnough(true);
        ApprovalRoutingDecision d1 = service.route(enough);
        Assert.assertEquals("ROUTED", d1.getStatus());
        Assert.assertEquals("DEPT_MANAGER", d1.getFinalApproverRole());
        Assert.assertEquals(16, d1.getSlaHours());

        ApprovalRequest shortBalance = leaveReq("A003", "ANNUAL", 48.0, "REGULAR");
        shortBalance.setAnnualLeaveEnough(false);
        ApprovalRoutingDecision d2 = service.route(shortBalance);
        Assert.assertEquals("REJECTED", d2.getStatus());
        Assert.assertTrue(d2.getRejectReason().contains("年假余额不足"));
    }

    /**
     * 6 天病假有证明 → VP 审批并加签 HR；无证明 → 退回补证
     */
    @Test
    public void shouldRequireCertificateAndCountersignHrForLongSickLeave() {
        ApprovalRequest certified = leaveReq("A004", "SICK", 48.0, "REGULAR");
        certified.setSickCertificate(true);
        ApprovalRoutingDecision d1 = service.route(certified);
        Assert.assertEquals("VP", d1.getFinalApproverRole());
        Assert.assertEquals(24, d1.getSlaHours());
        Assert.assertTrue(d1.getCountersignRoles().contains("HR"));
        Assert.assertTrue(d1.getRouteNotes().get(0).contains("加签 HR 核发病假工资"));

        ApprovalRequest noProof = leaveReq("A005", "SICK", 48.0, "REGULAR");
        noProof.setSickCertificate(false);
        ApprovalRoutingDecision d2 = service.route(noProof);
        Assert.assertEquals("REJECTED", d2.getStatus());
        Assert.assertTrue(d2.getRejectReason().contains("医院诊断证明"));
    }

    /**
     * 20 小时常规加班 → 部门经理；8 小时跨期补录 → 部门经理加签 HR
     */
    @Test
    public void shouldRouteOvertimeByHoursAndRetroactiveFlag() {
        ApprovalRequest normal = base("A006", "OVERTIME", "REGULAR");
        normal.setHours(20);
        ApprovalRoutingDecision d1 = service.route(normal);
        Assert.assertEquals("DEPT_MANAGER", d1.getFinalApproverRole());
        Assert.assertEquals(0, d1.getCountersignRoles().size());

        ApprovalRequest retro = base("A007", "OVERTIME", "REGULAR");
        retro.setHours(8);
        retro.setRetroactiveOvertime(true);
        ApprovalRoutingDecision d2 = service.route(retro);
        Assert.assertEquals("DEPT_MANAGER", d2.getFinalApproverRole());
        Assert.assertTrue(d2.getCountersignRoles().contains("HR"));
        Assert.assertTrue(d2.getRouteNotes().get(0).contains("加签 HR 复核考勤原始记录"));
    }

    /**
     * 境内 8000 元出差 → VP；2000 元境外出差 → 升级 VP
     */
    @Test
    public void shouldRouteTripByBudgetAndEscalateOverseas() {
        ApprovalRequest domestic = base("A008", "TRIP", "REGULAR");
        domestic.setTripAmount(8000);
        ApprovalRoutingDecision d1 = service.route(domestic);
        Assert.assertEquals("VP", d1.getFinalApproverRole());
        Assert.assertEquals(24, d1.getSlaHours());

        ApprovalRequest overseas = base("A009", "TRIP", "REGULAR");
        overseas.setTripAmount(2000);
        overseas.setOverseasTrip(true);
        ApprovalRoutingDecision d2 = service.route(overseas);
        Assert.assertEquals("VP", d2.getFinalApproverRole());
        Assert.assertTrue(d2.getRouteNotes().get(0).contains("境外出差预算审批由部门经理升级至 VP"));
    }

    /**
     * 5 万差旅 → CEO；高管 1.2 万差旅（本应 CFO）→ 上提 CEO 规避自审
     */
    @Test
    public void shouldRouteLargeBudgetToCeoAndEscalateExecutive() {
        ApprovalRequest large = base("A010", "TRIP", "REGULAR");
        large.setTripAmount(50000);
        ApprovalRoutingDecision d1 = service.route(large);
        Assert.assertEquals("CEO", d1.getFinalApproverRole());
        Assert.assertEquals(48, d1.getSlaHours());

        ApprovalRequest exec = base("A011", "TRIP", "EXECUTIVE");
        exec.setTripAmount(12000);
        ApprovalRoutingDecision d2 = service.route(exec);
        Assert.assertEquals("CEO", d2.getFinalApproverRole());
        Assert.assertTrue(d2.getRouteNotes().get(0).contains("上提至 CEO"));
    }

    private ApprovalRequest leaveReq(String id, String leaveType, double hours, String level) {
        ApprovalRequest r = base(id, "LEAVE", level);
        r.setLeaveType(leaveType);
        r.setHours(hours);
        r.setSickCertificate(false);
        r.setAnnualLeaveEnough(false);
        return r;
    }

    private ApprovalRequest base(String id, String type, String level) {
        ApprovalRequest r = new ApprovalRequest();
        r.setRequestId(id);
        r.setEmployeeId("E-" + id);
        r.setEmployeeName("测试员工");
        r.setApprovalType(type);
        r.setLeaveType("PERSONAL");
        r.setHours(0);
        r.setTripAmount(0);
        r.setEmployeeLevel(level);
        r.setRetroactiveOvertime(false);
        r.setSickCertificate(false);
        r.setAnnualLeaveEnough(false);
        r.setOverseasTrip(false);
        return r;
    }
}
