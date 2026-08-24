package com.github.paohaijiao.xml;

import com.github.paohaijiao.xml.param.Param;

public interface RulesMapper {
    /**
     * 报送时限计分
     * @param eventType 事件类型
     * @param reportMinutes 报送时限（分钟）
     * @return
     */
    int scoreReportTime(@Param("eventType") String eventType, @Param("reportMinutes") int reportMinutes);

    /**
     * 协同回复计分
     * @param eventType 事件类型
     * @param replyMinutes 协同回复时限（分钟）
     * @return
     */
    double scoreCoordination(@Param("eventType") String eventType, @Param("replyMinutes") int replyMinutes);

    /**
     * 办结时限计分
     * @param closureDays 办结时限（天）
     * @return
     */
    int scoreClosure(@Param("closureDays") int closureDays);

    // 4. 调度响应计分 (由人工打分，此函数可暂不实现)
    int scoreDispatch(@Param("speed") int speed, @Param("proficiency") int proficiency, @Param("familiarity") int familiarity);

    /**
     * 综合计算单事件总分
     * @param eventType 事件类型
     * @param reportMinutes
     * @param replyMinutes
     * @param closureDays
     * @param dispatchScore
     * @param qualityScore
     * @return
     */
    double calculateSingleEventScore(@Param("eventType") String eventType, @Param("reportMinutes") int reportMinutes, @Param("replyMinutes") int replyMinutes, @Param("closureDays") int closureDays, @Param("dispatchScore") int dispatchScore, @Param("qualityScore") int qualityScore);
}
