package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.SlaDecision;
import com.github.paohaijiao.demo.model.Ticket;
import com.github.paohaijiao.demo.service.TicketSlaService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 5 生产级样板：工单 SLA 分级与升级
 * <p>
 * 覆盖 6 组工单形态：P0 安全故障、P3 低优请求、VIP 客户重开工单、
 * 中等负载重开工单、历史违约复现、安全类普通工单。
 */
public class TicketSlaServiceTest {

    private TicketSlaService service;

    @Before
    public void setUp() {
        TicketSlaRules rules = JQuickJava.create()
                .createApi(TicketSlaRules.class, "demo/ticket-sla.xml");
        this.service = new TicketSlaService(rules);
    }

    /**
     * P0 安全故障：VIP 客户、安全类 → 5 分钟首响、10 分钟升级、经理介入
     */
    @Test
    public void shouldHandleP0SecurityIncident() {
        Ticket ticket = new Ticket();
        ticket.setTicketNo("TK20260826001");
        ticket.setTitle("支付网关不可用");
        ticket.setPriority("P0");
        ticket.setCategory("INCIDENT");
        ticket.setCustomerLevel("VIP");
        ticket.setCreateChannel("PHONE");
        ticket.setSecurityRelated(true);
        ticket.setReopenedTimes(0);
        ticket.setAssigneeLoad(8);
        ticket.setHasSlaBreachHistory(false);
        ticket.setElapsedMinutes(20);

        SlaDecision decision = service.evaluate(ticket);

        Assert.assertEquals("VIP", decision.getSlaLevel());
        Assert.assertEquals(5, decision.getResponseMinutes());
        Assert.assertEquals(120, decision.getResolveMinutes());
        Assert.assertEquals(10, decision.getEscalateMinutes());
        Assert.assertEquals(46, decision.getRiskScore());
        Assert.assertEquals("MEDIUM", decision.getBreachRisk());
        Assert.assertTrue(decision.isNeedEscalate());
        Assert.assertTrue(decision.isNeedManager());
        Assert.assertTrue(decision.getActions().contains("安全类工单，同步通知安全值班与应急群"));
    }

    /**
     * P3 低优请求：无负载、无重开 → 标准水平，无升级
     */
    @Test
    public void shouldKeepLowPriorityRequestStandard() {
        Ticket ticket = new Ticket();
        ticket.setTicketNo("TK20260826002");
        ticket.setTitle("修改头像失败");
        ticket.setPriority("P3");
        ticket.setCategory("REQUEST");
        ticket.setCustomerLevel("NORMAL");
        ticket.setCreateChannel("APP");
        ticket.setSecurityRelated(false);
        ticket.setReopenedTimes(0);
        ticket.setAssigneeLoad(3);
        ticket.setHasSlaBreachHistory(false);
        ticket.setElapsedMinutes(100);

        SlaDecision decision = service.evaluate(ticket);

        Assert.assertEquals("STANDARD", decision.getSlaLevel());
        Assert.assertEquals(240, decision.getResponseMinutes());
        Assert.assertEquals(7200, decision.getResolveMinutes());
        Assert.assertEquals(1440, decision.getEscalateMinutes());
        Assert.assertEquals(5, decision.getRiskScore());
        Assert.assertEquals("LOW", decision.getBreachRisk());
        Assert.assertFalse(decision.isNeedEscalate());
        Assert.assertFalse(decision.isNeedManager());
    }

    /**
     * VIP 重开工单：P1、重开 2 次、处理人负载 15 → 高风险、自动升级、经理介入
     */
    @Test
    public void shouldEscalateReopenedVipTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketNo("TK20260826003");
        ticket.setTitle("订单状态不同步");
        ticket.setPriority("P1");
        ticket.setCategory("PROBLEM");
        ticket.setCustomerLevel("VIP");
        ticket.setCreateChannel("WEB");
        ticket.setSecurityRelated(false);
        ticket.setReopenedTimes(2);
        ticket.setAssigneeLoad(15);
        ticket.setHasSlaBreachHistory(false);
        ticket.setElapsedMinutes(45);

        SlaDecision decision = service.evaluate(ticket);

        Assert.assertEquals("VIP", decision.getSlaLevel());
        Assert.assertEquals(20, decision.getResponseMinutes());
        Assert.assertEquals(240, decision.getResolveMinutes());
        Assert.assertEquals(30, decision.getEscalateMinutes());
        Assert.assertEquals(66, decision.getRiskScore());
        Assert.assertEquals("HIGH", decision.getBreachRisk());
        Assert.assertTrue(decision.isNeedEscalate());
        Assert.assertTrue(decision.isNeedManager());
        Assert.assertTrue(decision.getActions().contains("工单重开 2 次，建议复盘根因"));
    }

    /**
     * 中等负载重开：P2、重开 3 次、负载 6 → 中风险、未到升级时限
     */
    @Test
    public void shouldAssessMediumRiskTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketNo("TK20260826004");
        ticket.setTitle("报表导出超时");
        ticket.setPriority("P2");
        ticket.setCategory("INCIDENT");
        ticket.setCustomerLevel("GOLD");
        ticket.setCreateChannel("WEB");
        ticket.setSecurityRelated(false);
        ticket.setReopenedTimes(3);
        ticket.setAssigneeLoad(6);
        ticket.setHasSlaBreachHistory(false);
        ticket.setElapsedMinutes(100);

        SlaDecision decision = service.evaluate(ticket);

        Assert.assertEquals("STANDARD", decision.getSlaLevel());
        Assert.assertEquals(60, decision.getResponseMinutes());
        Assert.assertEquals(2880, decision.getResolveMinutes());
        Assert.assertEquals(240, decision.getEscalateMinutes());
        Assert.assertEquals(57, decision.getRiskScore());
        Assert.assertEquals("MEDIUM", decision.getBreachRisk());
        Assert.assertFalse(decision.isNeedEscalate());
        Assert.assertFalse(decision.isNeedManager());
    }

    /**
     * 历史违约复现：P1、曾违约 → 风险直接判 HIGH，并触发升级
     */
    @Test
    public void shouldFlagTicketWithBreachHistory() {
        Ticket ticket = new Ticket();
        ticket.setTicketNo("TK20260826005");
        ticket.setTitle("账单对账差异");
        ticket.setPriority("P1");
        ticket.setCategory("PROBLEM");
        ticket.setCustomerLevel("NORMAL");
        ticket.setCreateChannel("PHONE");
        ticket.setSecurityRelated(false);
        ticket.setReopenedTimes(1);
        ticket.setAssigneeLoad(6);
        ticket.setHasSlaBreachHistory(true);
        ticket.setElapsedMinutes(70);

        SlaDecision decision = service.evaluate(ticket);

        Assert.assertEquals("GOLD", decision.getSlaLevel());
        Assert.assertEquals(480, decision.getResolveMinutes());
        Assert.assertEquals(60, decision.getEscalateMinutes());
        Assert.assertEquals(48, decision.getRiskScore());
        Assert.assertEquals("HIGH", decision.getBreachRisk());
        Assert.assertTrue(decision.isNeedEscalate());
        Assert.assertFalse(decision.isNeedManager());
    }

    /**
     * 安全类普通工单：P2、安全类 → 无需经理介入但安全类触发经理介入
     */
    @Test
    public void shouldHandleSecurityRelatedNormalTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketNo("TK20260826006");
        ticket.setTitle("异常登录告警");
        ticket.setPriority("P2");
        ticket.setCategory("INCIDENT");
        ticket.setCustomerLevel("NORMAL");
        ticket.setCreateChannel("APP");
        ticket.setSecurityRelated(true);
        ticket.setReopenedTimes(0);
        ticket.setAssigneeLoad(6);
        ticket.setHasSlaBreachHistory(false);
        ticket.setElapsedMinutes(250);

        SlaDecision decision = service.evaluate(ticket);

        Assert.assertEquals("STANDARD", decision.getSlaLevel());
        Assert.assertEquals(60, decision.getResponseMinutes());
        Assert.assertEquals(21, decision.getRiskScore());
        Assert.assertEquals("LOW", decision.getBreachRisk());
        Assert.assertTrue(decision.isNeedEscalate());
        Assert.assertTrue(decision.isNeedManager());
    }
}
