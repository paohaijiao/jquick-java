package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 38：硬资格门控、学业/排名/科研分项与奖助等级规则
 */
public interface ScholarshipRules {

    /**
     * 参评硬资格：无不及格学分且无纪律处分
     */
    boolean eligibilityOk(@Param("failedCredits") int failedCredits,
                          @Param("violation") boolean violation);

    /**
     * 成绩分（60 分制）：≥95 60 / ≥90 54 / ≥85 46 / ≥80 38 / ≥75 28 / 其余 0
     */
    int academicScore(@Param("average") int average);

    /**
     * 排名分（25 分制）：前 10% 25 / 前 20% 20 / 前 30% 13 / 前 50% 7 / 其余 0
     */
    int rankScore(@Param("rank") int rank,
                  @Param("total") int total);

    /**
     * 科研竞赛分（15 分制）：国家级竞赛或论文 15 / 省级 10 / 校级 5
     */
    int researchScore(@Param("competition") String competition,
                      @Param("paper") boolean paper);

    /**
     * 奖助等级：
     * 国奖类 ≥85 国家奖学金、≥70 国家励志（困难生由 Service 二次校验）；
     * 学业类 ≥85 一等、≥65 二等、≥50 三等；
     * 助学类 HARDSHIP 特困档、DIFFICULT 困难档；
     * 单项类国家竞赛/论文创新 1500、省级竞赛 800、志愿之星 500
     */
    String awardLevel(@Param("type") String type,
                      @Param("score") int score,
                      @Param("economic") String economic,
                      @Param("competition") String competition,
                      @Param("paper") boolean paper,
                      @Param("volunteer") boolean volunteer);

    /**
     * 各等级对应金额
     */
    int amountOf(@Param("level") String level);
}
