package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 19（生产级样板）：投资者适当性匹配规则集
 * <p>
 * 五维成熟度评分（年龄 / 收入 / 资产 / 经验 / 风险承受问卷），再按产品与承受等级差、
 * 高龄、低成熟度决定可购/确认/禁购；风险揭示书签署校验由 Service 前置。
 * 规则脚本见 {@code src/test/resources/demo/investor-suitability.xml}。
 */
public interface InvestorSuitabilityRules {

    /**
     * 年龄成熟度评分（0-15）
     */
    int scoreAge(@Param("age") int age);

    /**
     * 收入成熟度评分（0-15）
     */
    int scoreIncome(@Param("annualIncome") double annualIncome);

    /**
     * 资产成熟度评分（0-20）
     */
    int scoreAssets(@Param("netAssets") double netAssets);

    /**
     * 投资经验评分（0-20）
     */
    int scoreExperience(@Param("investExperienceYears") int investExperienceYears);

    /**
     * 风险承受问卷评分（0-30）
     */
    int scoreTolerance(@Param("riskToleranceRank") int riskToleranceRank);

    /**
     * 是否需要确认（双录）：高龄客户或低成熟度客户购买 R3 及以上产品时触发
     */
    boolean needConfirm(@Param("maturityScore") int maturityScore,
                        @Param("productRiskRank") int productRiskRank,
                        @Param("elderly") boolean elderly);

    /**
     * 风险等级差（产品 - 承受）
     */
    int rankGap(@Param("riskToleranceRank") int riskToleranceRank,
                @Param("productRiskRank") int productRiskRank);
}
