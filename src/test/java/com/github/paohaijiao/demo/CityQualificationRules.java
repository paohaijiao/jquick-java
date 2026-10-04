package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 21：落户 / 购房 / 购车资格多政策判定规则集
 * <p>
 * 落户按学历、社保、年龄、人才称号、投资纳税五维积分（80 核准 / 60 复核）；
 * 购房购车按社保或个税连续缴纳月数设置门槛，人才通道缩短年限。
 * 规则脚本见 {@code src/test/resources/demo/city-qualification.xml}。
 */
public interface CityQualificationRules {

    /** 学历积分：博士 40 / 硕士 30 / 本科 20 / 大专 10 / 其他 0 */
    int educationScore(@Param("education") String education);

    /** 社保积分：每满 12 个月 10 分，48 个月封顶 40 分 */
    int securityScore(@Param("months") int months);

    /** 年龄积分：35 岁以下 15 / 45 岁以下 10 / 55 岁以下 5 */
    int ageSettleScore(@Param("age") int age);

    /** 人才称号积分：高层次 30 / 紧缺 15 / 无 0 */
    int titleScore(@Param("talentTitle") String talentTitle);

    /** 投资纳税积分：年纳税 100/50/10 万对应 20/10/5 分 */
    int investTaxScore(@Param("enterpriseTaxWan") double enterpriseTaxWan);

    /** 落户结论：80 分以上核准，60 分以上复核，其余不通过 */
    String settleResult(@Param("points") int points);

    /** 购房资格：社保或个税满 36 个月；高层次/紧缺人才放宽至 3 个月 */
    boolean houseEligible(@Param("ssMonths") int ssMonths,
                          @Param("taxMonths") int taxMonths,
                          @Param("talentTitle") String talentTitle);

    /** 购车（新能源指标）资格：普通满 24 个月、人才满 6 个月，且名下无本市小客车 */
    boolean carEligible(@Param("ssMonths") int ssMonths,
                        @Param("taxMonths") int taxMonths,
                        @Param("noCarInCity") boolean noCarInCity,
                        @Param("talentTitle") String talentTitle);
}
