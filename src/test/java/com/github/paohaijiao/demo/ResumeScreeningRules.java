package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 14：招聘简历智能初筛规则集
 * <p>
 * 规则按「硬性门槛 → 多维评分 → 结论路由」两段式拆分：
 * <ul>
 *     <li>门槛类：学历、年限、技能覆盖率、薪资带宽、工作地，任一不满足即淘汰</li>
 *     <li>评分类：学历院校、经验、技能、稳定性、资质英语五个维度</li>
 * </ul>
 * 规则脚本见 {@code src/test/resources/demo/resume-screening.xml}。
 */
public interface ResumeScreeningRules {

    int eduRank(@Param("education") String education);

    boolean educationPass(@Param("actualRank") int actualRank,
                          @Param("requiredRank") int requiredRank);

    boolean experiencePass(@Param("yearsExperience") int yearsExperience,
                           @Param("requiredYears") int requiredYears);

    boolean skillPass(@Param("skillMatchCount") int skillMatchCount,
                      @Param("requiredSkillCount") int requiredSkillCount);

    boolean salaryPass(@Param("expectedSalary") double expectedSalary,
                       @Param("salaryMin") double salaryMin,
                       @Param("salaryMax") double salaryMax);

    boolean locationPass(@Param("currentCity") String currentCity,
                         @Param("jobCity") String jobCity,
                         @Param("willingToRelocate") boolean willingToRelocate);

    boolean hardReject(@Param("jobHoppingCount") int jobHoppingCount,
                       @Param("gapMonths") int gapMonths);

    int scoreEducation(@Param("actualRank") int actualRank,
                       @Param("requiredRank") int requiredRank,
                       @Param("schoolTier") String schoolTier);

    int scoreExperience(@Param("yearsExperience") int yearsExperience,
                        @Param("requiredYears") int requiredYears);

    int scoreSkills(@Param("skillMatchCount") int skillMatchCount,
                    @Param("requiredSkillCount") int requiredSkillCount);

    int scoreStability(@Param("jobHoppingCount") int jobHoppingCount,
                       @Param("gapMonths") int gapMonths);

    int scoreBonus(@Param("certified") boolean certified,
                   @Param("englishLevel") String englishLevel);

    String screeningResult(@Param("totalScore") int totalScore,
                           @Param("hardCheckPass") boolean hardCheckPass,
                           @Param("hardReject") boolean hardReject);

    String recommendLevel(@Param("totalScore") int totalScore);
}
