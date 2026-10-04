package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 信贷授信申请件（进件要素）
 * <p>
 * 字段来源于真实个人消费信贷进件表单与征信报告摘要，仅保留风控评分所需的关键要素。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplication {

    /**
     * 申请编号
     */
    private String applicationId;

    /**
     * 申请人姓名
     */
    private String applicantName;

    /**
     * 年龄
     */
    private int age;

    /**
     * 单位性质：SOE-国企 / LISTED-上市公司 / FOREIGN-外企 / PRIVATE-民营 / SELF_EMPLOYED-自雇
     */
    private String companyType;

    /**
     * 现单位在职月数
     */
    private int employedMonths;

    /**
     * 社保连续缴纳月数
     */
    private int socialSecurityMonths;

    /**
     * 月均收入（元）
     */
    private double monthlyIncome;

    /**
     * 月均负债还款额（元），含已有贷款与信用卡最低还款
     */
    private double monthlyDebt;

    /**
     * 近 2 年逾期次数
     */
    private int overdueCount;

    /**
     * 近 2 年最长逾期天数
     */
    private int maxOverdueDays;

    /**
     * 近 3 个月征信查询次数
     */
    private int creditQueryCount3M;

    /**
     * 申请金额（元）
     */
    private double loanAmount;

    /**
     * 申请期限（月）
     */
    private int loanTermMonths;

    /**
     * 抵押物类型：HOUSE-房产 / VEHICLE-车辆 / NONE-无抵押
     */
    private String collateralType;

    /**
     * 抵押物评估价值（元）
     */
    private double collateralValue;

    /**
     * 是否存在涉诉/被执行记录
     */
    private boolean hasJudicialRecord;

    /**
     * 所在城市层级：TIER_1-一线 / TIER_2-二线 / TIER_3-三线及以下
     */
    private String cityTier;
}
