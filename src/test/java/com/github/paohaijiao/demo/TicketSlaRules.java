package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 5（生产级样板）：工单 SLA 分级与升级规则集
 * <p>
 * 规则按「服务水平分级 + 时限矩阵 + 违约风险」拆分：
 * <ul>
 *     <li>时限类：首响、解决、升级三档时限，按优先级与客户等级取值</li>
 *     <li>风险类：优先级权重、重开工单、处理人负载共同决定违约风险</li>
 * </ul>
 * 规则脚本见 {@code src/test/resources/demo/ticket-sla.xml}。
 */
public interface TicketSlaRules {

    /**
     * 服务水平等级：VIP / GOLD / STANDARD
     */
    String slaLevel(@Param("priority") String priority,
                    @Param("customerLevel") String customerLevel);

    /**
     * 首次响应时限（分钟）
     */
    int responseMinutes(@Param("priority") String priority,
                        @Param("securityRelated") boolean securityRelated);

    /**
     * 解决时限（分钟）
     */
    int resolveMinutes(@Param("priority") String priority,
                       @Param("slaLevel") String slaLevel);

    /**
     * 升级时限（分钟）
     */
    int escalateMinutes(@Param("priority") String priority,
                        @Param("slaLevel") String slaLevel);

    /**
     * 优先级权重
     */
    int priorityWeight(@Param("priority") String priority);

    /**
     * 重开工单风险分（每次 12 分，封顶 36）
     */
    int scoreReopen(@Param("reopenedTimes") int reopenedTimes);

    /**
     * 处理人负载风险分
     */
    int scoreLoad(@Param("assigneeLoad") int assigneeLoad);

    /**
     * 违约风险：HIGH / MEDIUM / LOW
     */
    String breachRisk(@Param("riskScore") int riskScore,
                      @Param("hasSlaBreachHistory") boolean hasSlaBreachHistory);

    /**
     * 是否触发升级
     */
    boolean needEscalate(@Param("elapsedMinutes") int elapsedMinutes,
                         @Param("escalateMinutes") int escalateMinutes);

    /**
     * 是否需要经理介入
     */
    boolean needManager(@Param("priority") String priority,
                        @Param("customerLevel") String customerLevel,
                        @Param("securityRelated") boolean securityRelated);
}
