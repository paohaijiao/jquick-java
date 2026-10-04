package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 18（生产级样板）：供应链金融供应商准入规则集
 * <p>
 * 五维评分（企业资质 / 履约记录 / 合作深度 / 财务状况 / 注册年限），
 * 再映射评级、保理额度比例、担保要求与账期；失信与重大违约由 Service 一票否决。
 * 规则脚本见 {@code src/test/resources/demo/supplier-admission.xml}。
 */
public interface SupplierAdmissionRules {

    /**
     * 企业资质评分（0-20）：资质等级 + 注册资本
     */
    int scoreQualification(@Param("qualificationLevel") String qualificationLevel,
                           @Param("registeredCapitalWan") int registeredCapitalWan);

    /**
     * 履约记录评分（0-30）：准时交付率、质量合格率、合同履约率各 10 分
     */
    int scoreFulfillment(@Param("onTimeDeliveryRate") double onTimeDeliveryRate,
                         @Param("qualityPassRate") double qualityPassRate,
                         @Param("contractFulfillRate") double contractFulfillRate);

    /**
     * 合作深度评分（0-15）：合作月数 + 年度交易额
     */
    int scoreCooperation(@Param("cooperationMonths") int cooperationMonths,
                         @Param("annualOrderAmount") double annualOrderAmount);

    /**
     * 财务状况评分（0-20）
     */
    int scoreFinance(@Param("financeStatus") String financeStatus);

    /**
     * 注册年限评分（0-15）
     */
    int scoreRegisteredYears(@Param("registeredYears") int registeredYears);

    /**
     * 供应商评级：A / B / C / D
     */
    String grade(@Param("totalScore") int totalScore);

    /**
     * 保理额度占年交易额比例
     */
    double factoringRatio(@Param("grade") String grade);

    /**
     * 建议结算账期（天）
     */
    int settlementDays(@Param("grade") String grade);
}
