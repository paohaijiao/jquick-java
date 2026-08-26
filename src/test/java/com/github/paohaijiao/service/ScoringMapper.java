package com.github.paohaijiao.service;

import com.github.paohaijiao.xml.param.Param;

import java.util.Date;

public interface ScoringMapper {
    /**
     * 快速报送-事件上报的情报端
     * @param eventType 事件类型 (A/B/C/D)
     * @param t 报送时间与事发时间的差值（分钟）
     * @return 得分（保留一位小数）
     */
    double quickSubmissionReport(@Param("eventType") String eventType, @Param("t") int t);

    /**
     * 计算区县管理端触发时效得分
     * @param eventType 事件类型 (A/B/C/D)
     * @param t 触发时间与事发时间的差值（分钟）
     * @return 得分（保留一位小数）
     */
    double quickSubmissionTrigger(@Param("eventType") String eventType, @Param("t") int t);
    /**
     * 快速办结 - 市级事件考核事件归属区县
     *
     * @param hasOfficialFollowUp 是否有正式续报
     * @param lastFollowUpTime 最后一次正式续报时间
     * @param settleTime 办结时间
     * @param reportTime 上报事件时间
     * @return 得分（double类型，保留一位小数）
     */
    double quickSettleOwnToZone(@Param("hasOfficialFollowUp") boolean hasOfficialFollowUp, @Param("lastFollowUpTime") Date lastFollowUpTime, @Param("settleTime") Date settleTime, @Param("reportTime") Date reportTime);


    /**
     * 快速办结 - 区级事件考核事件归属街镇
     *
     * @param hasOfficialFollowUp 是否有正式续报
     * @param lastFollowUpTime 最后一次正式续报时间
     * @param settleTime 办结时间
     * @param reportTime 上报事件时间
     * @return 得分（double类型，保留一位小数）
     */
    double quickSettleOwnToStreet(@Param("hasOfficialFollowUp") boolean hasOfficialFollowUp, @Param("lastFollowUpTime") Date lastFollowUpTime, @Param("settleTime") Date settleTime, @Param("reportTime") Date reportTime);

    /**
     * 快速协同回复 - 考核协同目标单位回复时效性
     *
     * @param eventType 事件类型 (A/B/C/D)
     * @param t 第一次协同回复时间 - 协同发起时间（分钟）
     * @return 得分（double类型，保留一位小数）
     */
    double quickReplyTargetUnit(@Param("eventType") String eventType, @Param("t") double t);

    /**
     * 快速协同回复 - 考核批示下达交办目标单位回复时效性
     *
     * @param eventType 事件类型 (A/B/C/D)
     * @param t 第一次下达回复时间 - 批示下达时间（分钟）
     * @return 得分（double类型，保留一位小数）
     */
    double quickReplyTargetTimeUnit(@Param("eventType") String eventType, @Param("t") double t);

}
