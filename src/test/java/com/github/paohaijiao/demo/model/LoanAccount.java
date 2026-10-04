package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 贷后账户要素（逾期与催收所需的账户快照）
 * <p>
 * 字段来源于信贷核心系统的账户表与贷后催收台账，仅保留预警分级所需的关键要素。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanAccount {

    /**
     * 借据号
     */
    private String loanNo;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 产品类型：CONSUMER-消费贷 / MORTGAGE-房贷 / SME-小微经营贷
     */
    private String productType;

    /**
     * 剩余本金（元）
     */
    private double remainingPrincipal;

    /**
     * 当前逾期金额（元）
     */
    private double overdueAmount;

    /**
     * 当前逾期天数
     */
    private int overdueDays;

    /**
     * 合同年化利率
     */
    private double baseRate;

    /**
     * 抵押物类型：HOUSE-房产 / VEHICLE-车辆 / NONE-无抵押
     */
    private String collateralType;

    /**
     * 抵押物评估价值（元）
     */
    private double collateralValue;

    /**
     * 是否联系上本人
     */
    private boolean contactSuccess;

    /**
     * 距上次有效联系天数
     */
    private int lastContactDays;

    /**
     * 近 1 年逾期次数
     */
    private int overdueHistoryCount;

    /**
     * 历史已触发预警次数
     */
    private int warningCount;
}
