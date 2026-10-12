package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 市民资格申请要素（落户 / 购房 / 购车三类政策入口共用）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityQualificationApplication {

    /**
     * 申请单号
     */
    private String applicationId;

    /**
     * 申请人姓名
     */
    private String applicantName;

    /**
     * 申请事项：SETTLE-落户 / HOUSE_PURCHASE-购房 / CAR_PURCHASE-购车
     */
    private String benefit;

    /**
     * 年龄
     */
    private int age;

    /**
     * 学历：DOCTOR / MASTER / BACHELOR / COLLEGE / OTHER
     */
    private String education;

    /**
     * 本市社保连续缴纳月数
     */
    private int socialSecurityMonths;

    /**
     * 本市个税连续缴纳月数
     */
    private int taxPaymentMonths;

    /**
     * 在本市是否已拥有住房
     */
    private boolean hasPropertyInCity;

    /**
     * 名下是否已有本市小客车
     */
    private boolean hasCarInCity;

    /**
     * 落户通道：EMPLOYEE-就业 / INVESTMENT-投资 / TALENT-人才引进
     */
    private String channel;

    /**
     * 创办企业年限（投资落户）
     */
    private int companyYears;

    /**
     * 创办企业上年纳税额（万元，投资落户）
     */
    private double enterpriseTaxWan;

    /**
     * 人才称号：TOP_TALENT-高层次人才 / ELITE-紧缺人才 / NONE-无
     */
    private String talentTitle;

    /**
     * 是否存在严重失信记录
     */
    private boolean badCreditRecord;
}
