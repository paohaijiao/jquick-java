package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客服工单要素（SLA 分级与升级所需的工单快照）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    /**
     * 工单号
     */
    private String ticketNo;

    /**
     * 工单标题
     */
    private String title;

    /**
     * 优先级：P0-最高 / P1 / P2 / P3
     */
    private String priority;

    /**
     * 工单类型：INCIDENT-故障 / REQUEST-请求 / PROBLEM-问题 / CHANGE-变更
     */
    private String category;

    /**
     * 客户等级：VIP / GOLD / NORMAL
     */
    private String customerLevel;

    /**
     * 来源渠道：APP / WEB / PHONE
     */
    private String createChannel;

    /**
     * 是否安全类工单
     */
    private boolean securityRelated;

    /**
     * 重开次数
     */
    private int reopenedTimes;

    /**
     * 处理人当前在办工单数
     */
    private int assigneeLoad;

    /**
     * 是否历史发生过 SLA 违约
     */
    private boolean hasSlaBreachHistory;

    /**
     * 已耗时（分钟）
     */
    private int elapsedMinutes;
}
