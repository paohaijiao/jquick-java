package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.TicketSlaRules;
import com.github.paohaijiao.demo.model.SlaDecision;
import com.github.paohaijiao.demo.model.Ticket;

/**
 * 场景 5：工单 SLA 分级与升级服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：时限矩阵装配、风险分汇总、升级判定、处理动作组装</li>
 *     <li><b>规则侧</b>：服务水平分级、时限取值、权重与风险阈值，全部外置在 XML 中</li>
 * </ul>
 */
public class TicketSlaService {

    private final TicketSlaRules rules;

    public TicketSlaService(TicketSlaRules rules) {
        this.rules = rules;
    }

    /**
     * 计算工单 SLA 策略
     *
     * @param ticket 工单快照
     * @return SLA 决策
     */
    public SlaDecision evaluate(Ticket ticket) {
        SlaDecision decision = new SlaDecision();
        decision.setTicketNo(ticket.getTicketNo());

        String level = rules.slaLevel(ticket.getPriority(), ticket.getCustomerLevel());
        int escalate = rules.escalateMinutes(ticket.getPriority(), level);
        int riskScore = rules.priorityWeight(ticket.getPriority())
                + rules.scoreReopen(ticket.getReopenedTimes())
                + rules.scoreLoad(ticket.getAssigneeLoad());

        decision.setSlaLevel(level);
        decision.setResponseMinutes(rules.responseMinutes(ticket.getPriority(), ticket.isSecurityRelated()));
        decision.setResolveMinutes(rules.resolveMinutes(ticket.getPriority(), level));
        decision.setEscalateMinutes(escalate);
        decision.setRiskScore(riskScore);
        decision.setBreachRisk(rules.breachRisk(riskScore, ticket.isHasSlaBreachHistory()));
        decision.setNeedEscalate(rules.needEscalate(ticket.getElapsedMinutes(), escalate));
        decision.setNeedManager(rules.needManager(ticket.getPriority(), ticket.getCustomerLevel(),
                ticket.isSecurityRelated()));

        buildActions(ticket, decision);
        return decision;
    }

    private void buildActions(Ticket ticket, SlaDecision decision) {
        if (ticket.isSecurityRelated()) {
            decision.addAction("安全类工单，同步通知安全值班与应急群");
        }
        if ("P0".equals(ticket.getPriority())) {
            decision.addAction("P0 工单，立即拉群并通知值班经理");
        }
        if (decision.isNeedEscalate()) {
            decision.addAction("已耗时 " + ticket.getElapsedMinutes() + " 分钟，超过升级时限，自动升级");
        }
        if (ticket.getReopenedTimes() >= 2) {
            decision.addAction("工单重开 " + ticket.getReopenedTimes() + " 次，建议复盘根因");
        }
        if (ticket.getAssigneeLoad() >= 12) {
            decision.addAction("处理人在办工单 " + ticket.getAssigneeLoad() + " 件，建议改派或增援");
        }
        if (decision.isNeedManager()) {
            decision.addAction("按策略需经理介入关注");
        }
    }
}
