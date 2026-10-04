package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 17（生产级样板）：人身险核保规则集
 * <p>
 * 五维健康评分（年龄 / 职业 / BMI / 吸烟 / 慢病），再映射核保结论、加费比例、
 * 保额上限与优选体折扣；拒保硬否决（未如实告知、恶性肿瘤史、超龄、高危职业）由 Service 前置。
 * 规则脚本见 {@code src/test/resources/demo/insurance-underwriting.xml}。
 */
public interface InsuranceUnderwritingRules {

    /**
     * 年龄风险评分（0-25，越年轻越高）
     */
    int scoreAge(@Param("age") int age);

    /**
     * 职业类别评分（0-25）
     */
    int scoreOccupation(@Param("occupationClass") int occupationClass);

    /**
     * BMI 评分（0-20）
     */
    int scoreBmi(@Param("bmi") double bmi);

    /**
     * 吸烟评分（0-10）
     */
    int scoreSmoker(@Param("smoker") boolean smoker);

    /**
     * 慢性病史评分（0-20）
     */
    int scoreChronic(@Param("chronicDisease") String chronicDisease);

    /**
     * 核保结论：慢病与险种匹配优先于评分（糖尿病/高血压 → 除外责任）
     */
    String underwritingResult(@Param("totalScore") int totalScore,
                              @Param("chronicDisease") String chronicDisease,
                              @Param("productType") String productType);

    /**
     * 加费比例（%）：仅加费承保件有效
     */
    int extraPremiumRate(@Param("result") String result);

    /**
     * 优选体保费折扣比例（%）
     */
    int premiumDiscountRate(@Param("result") String result);

    /**
     * 职业与年龄对应的免体检/自动核保保额上限（万元）
     */
    int maxSumInsuredWan(@Param("occupationClass") int occupationClass,
                         @Param("age") int age);
}
