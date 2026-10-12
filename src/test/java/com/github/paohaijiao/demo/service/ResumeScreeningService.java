package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.ResumeScreeningRules;
import com.github.paohaijiao.demo.model.Resume;
import com.github.paohaijiao.demo.model.ScreeningDecision;

/**
 * 场景 14：招聘简历智能初筛服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：硬条件逐项判定与否决、五维评分汇总、淘汰原因与推荐说明组装</li>
 *     <li><b>规则侧</b>：门槛口径、院校/经验/技能/稳定性评分、结论与推荐阈值，全部外置在 XML 中</li>
 * </ul>
 * 采用「硬性门槛 → 多维评分 → 结论路由」两段式：门槛不满足时评分不改变淘汰结论。
 */
public class ResumeScreeningService {

    private final ResumeScreeningRules rules;

    public ResumeScreeningService(ResumeScreeningRules rules) {
        this.rules = rules;
    }

    /**
     * 执行简历初筛
     *
     * @param resume 简历要素
     * @return 初筛决策
     */
    public ScreeningDecision screen(Resume resume) {
        ScreeningDecision decision = new ScreeningDecision();
        decision.setCandidateName(resume.getCandidateName());

        int actualRank = rules.eduRank(resume.getEducationLevel());
        int requiredRank = rules.eduRank(resume.getRequiredEducation());

        boolean educationPass = rules.educationPass(actualRank, requiredRank);
        boolean experiencePass = rules.experiencePass(resume.getYearsExperience(), resume.getRequiredYears());
        boolean skillPass = rules.skillPass(resume.getSkillMatchCount(), resume.getRequiredSkillCount());
        boolean salaryPass = rules.salaryPass(resume.getExpectedSalary(),
                resume.getSalaryMin(), resume.getSalaryMax());
        boolean locationPass = rules.locationPass(resume.getCurrentCity(),
                resume.getJobCity(), resume.isWillingToRelocate());
        boolean hardCheckPass = educationPass && experiencePass && skillPass && salaryPass && locationPass;
        boolean hardReject = rules.hardReject(resume.getJobHoppingCount(), resume.getGapMonths());

        int score = rules.scoreEducation(actualRank, requiredRank, resume.getSchoolTier())
                + rules.scoreExperience(resume.getYearsExperience(), resume.getRequiredYears())
                + rules.scoreSkills(resume.getSkillMatchCount(), resume.getRequiredSkillCount())
                + rules.scoreStability(resume.getJobHoppingCount(), resume.getGapMonths())
                + rules.scoreBonus(resume.isCertified(), resume.getEnglishLevel());

        decision.setHardCheckPass(hardCheckPass);
        decision.setTotalScore(score);
        String result = rules.screeningResult(score, hardCheckPass, hardReject);
        decision.setResult(result);
        // 推荐等级仅对进入面试或储备池的候选人生效，淘汰候选人均为 NONE
        decision.setRecommendLevel("REJECT".equals(result) ? "NONE" : rules.recommendLevel(score));

        buildReasons(resume, decision, educationPass, experiencePass, skillPass, salaryPass,
                locationPass, hardReject);
        return decision;
    }

    private void buildReasons(Resume resume, ScreeningDecision decision, boolean educationPass,
                              boolean experiencePass, boolean skillPass, boolean salaryPass,
                              boolean locationPass, boolean hardReject) {
        if (hardReject) {
            decision.addReason("近 5 年跳槽 " + resume.getJobHoppingCount()
                    + " 次或空窗 " + resume.getGapMonths() + " 个月，命中稳定性否决项");
        }
        if (!educationPass) {
            decision.addReason("学历 " + resume.getEducationLevel() + " 未达岗位要求 "
                    + resume.getRequiredEducation());
        }
        if (!experiencePass) {
            decision.addReason("相关工作年限 " + resume.getYearsExperience() + " 年未达要求 "
                    + resume.getRequiredYears() + " 年");
        }
        if (!skillPass) {
            decision.addReason("技能匹配 " + resume.getSkillMatchCount() + "/"
                    + resume.getRequiredSkillCount() + "，覆盖率不足 80%");
        }
        if (!salaryPass) {
            decision.addReason("期望薪资 " + resume.getExpectedSalary()
                    + " 超出岗位薪资带宽或显著低于带宽下限");
        }
        if (!locationPass) {
            decision.addReason("候选人不在工作城市且不接受异地调动");
        }
        if ("INTERVIEW".equals(decision.getResult())) {
            decision.addReason("硬性条件全部满足，综合评分 " + decision.getTotalScore()
                    + "，推荐等级 " + decision.getRecommendLevel());
        } else if ("RESERVE".equals(decision.getResult())) {
            decision.addReason("硬性条件满足但评分 " + decision.getTotalScore()
                    + "，纳入人才储备池");
        }
    }
}
