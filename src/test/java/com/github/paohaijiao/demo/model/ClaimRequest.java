package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 理赔申请单（报案要素）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRequest {

    /**
     * 理赔单号
     */
    private String claimNo;

    /**
     * 保单号
     */
    private String policyNo;

    /**
     * 被保险人
     */
    private String insuredName;

    /**
     * 产品类型：MEDICAL-医疗险 / CRITICAL_ILLNESS-重疾险 / ACCIDENT-意外险 / AUTO-车险
     */
    private String productType;

    /**
     * 就诊医院等级：THIRD_A-三甲 / SECOND_A-二甲 / COMMUNITY-社区医院
     */
    private String hospitalLevel;

    /**
     * 申请赔付金额（元）
     */
    private double claimAmount;

    /**
     * 发票/费用清单金额（元）
     */
    private double invoiceAmount;

    /**
     * 保单约定免赔额（元）
     */
    private double deductible;

    /**
     * 已投保天数
     */
    private int insuredDays;

    /**
     * 产品等待期天数
     */
    private int waitingPeriodDays;

    /**
     * 近 1 年历史理赔次数
     */
    private int priorClaimCount;

    /**
     * 风控标记次数（黑名单、重复报案、影像异常等）
     */
    private int fraudFlagCount;

    /**
     * 是否意外出险
     */
    private boolean isAccident;

    /**
     * 理赔材料是否齐全
     */
    private boolean documentComplete;
}
