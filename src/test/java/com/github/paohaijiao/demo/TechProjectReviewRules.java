package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 20：科技项目申报材料打分初审规则集
 * <p>
 * 五维评分（满分 100）：技术先进性 35 / 产业化成熟度 25 / 团队实力 15
 * / 经费预算合理性 15 / 社会效益 10；结论分档：拟立项 ≥80、答辩复评 ≥65、其余驳回。
 * 规则脚本见 {@code src/test/resources/demo/tech-project-review.xml}。
 */
public interface TechProjectReviewRules {

    /** 技术先进性 0-35：鉴定结论为轴，发明专利加成，35 封顶 */
    int scoreTech(@Param("techLevel") String techLevel,
                  @Param("inventionPatents") int inventionPatents);

    /** 产业化成熟度 0-25：量产/中试/实验室为轴，营收规模加成 */
    int scoreIndustrialization(@Param("pilotStage") String pilotStage,
                               @Param("annualRevenueWan") int annualRevenueWan);

    /** 团队实力 0-15：带头人称号为轴，博士人数加成，分档封顶 */
    int scoreTeam(@Param("leaderTitle") String leaderTitle,
                  @Param("phdCount") int phdCount);

    /** 经费预算合理性 0-15：研发占比 0-8 + 自筹比例 0-7 */
    int scoreBudget(@Param("rdRatio") int rdRatio,
                    @Param("selfFundedRatio") int selfFundedRatio);

    /** 社会效益 0-10：新增就业 0-5 + 节能降耗 0-5 */
    int scoreSocial(@Param("employmentIncrease") int employmentIncrease,
                    @Param("energySavingTon") int energySavingTon);

    /** 评审结论：重大违规与材料不齐前置，80/65 为通过、复评线 */
    String reviewResult(@Param("totalScore") int totalScore,
                        @Param("majorViolation") boolean majorViolation,
                        @Param("materialsComplete") boolean materialsComplete);

    /** 立项资助定额（万元）：项目类别 × 评分档，未立项为 0 */
    int grantAmountWan(@Param("projectCategory") String projectCategory,
                       @Param("totalScore") int totalScore);
}
