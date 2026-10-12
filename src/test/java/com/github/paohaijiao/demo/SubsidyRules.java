package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 11：政务人才补贴资格审核规则集
 * <p>
 * 规则按「硬性否决前置 → 五维资格评分 → 分级结论 → 分项定额」拆分：
 * <ul>
 *     <li>评分类：学历、年龄、本地社保、人才认定、创业带动就业</li>
 *     <li>决策类：一票否决、审核结论（通过/复核/驳回）、三项补贴金额</li>
 * </ul>
 * 规则脚本见 {@code src/test/resources/demo/subsidy-eligibility.xml}。
 */
public interface SubsidyRules {

    int scoreEducation(@Param("education") String education);

    int scoreAge(@Param("age") int age);

    int scoreSocialSecurity(@Param("months") int months);

    int scoreTalent(@Param("talentCategory") String talentCategory);

    int scoreStartup(@Param("startupRegistered") boolean startupRegistered,
                     @Param("startupEmployees") int startupEmployees);

    boolean hardReject(@Param("creditViolation") boolean creditViolation,
                       @Param("criminalRecord") boolean criminalRecord,
                       @Param("receivedSimilarSubsidy") boolean receivedSimilarSubsidy);

    String eligibilityStage(@Param("qualificationScore") int qualificationScore,
                            @Param("hardReject") boolean hardReject,
                            @Param("documentsComplete") boolean documentsComplete);

    double educationAmount(@Param("education") String education);

    double talentAmount(@Param("talentCategory") String talentCategory);

    double startupAmount(@Param("startupRegistered") boolean startupRegistered,
                         @Param("startupEmployees") int startupEmployees);
}
