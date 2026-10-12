package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.Resume;
import com.github.paohaijiao.demo.model.ScreeningDecision;
import com.github.paohaijiao.demo.service.ResumeScreeningService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 14 生产级样板：招聘简历智能初筛
 * <p>
 * 覆盖 6 组候选人形态：理想候选人强烈推荐、学历硬门槛不达标、
 * 薪资超预算、门槛达标评分储备、高频跳槽否决、异地调动达标。
 */
public class ResumeScreeningServiceTest {

    private ResumeScreeningService service;

    @Before
    public void setUp() {
        ResumeScreeningRules rules = JQuickJava.create()
                .createApi(ResumeScreeningRules.class, "demo/resume-screening.xml");
        this.service = new ResumeScreeningService(rules);
    }

    /**
     * 理想候选人：硕士 985、8 年经验、技能全匹配、零跳槽 → 90 分，强烈推荐面试
     */
    @Test
    public void shouldRecommendStrongCandidateForInterview() {
        Resume resume = baseResume("张伟", "MASTER", "MASTER", "985", 8, 3, 5, 5);
        resume.setExpectedSalary(18000.0);
        resume.setSalaryMin(15000.0);
        resume.setSalaryMax(25000.0);
        resume.setCurrentCity("上海");
        resume.setJobCity("上海");
        resume.setWillingToRelocate(false);
        resume.setJobHoppingCount(0);
        resume.setGapMonths(2);
        resume.setCertified(true);
        resume.setEnglishLevel("CET6");

        ScreeningDecision decision = service.screen(resume);

        Assert.assertTrue(decision.isHardCheckPass());
        Assert.assertEquals(90, decision.getTotalScore());
        Assert.assertEquals("INTERVIEW", decision.getResult());
        Assert.assertEquals("STRONG", decision.getRecommendLevel());
        Assert.assertTrue(decision.getReasons().contains("硬性条件全部满足，综合评分 90，推荐等级 STRONG"));
    }

    /**
     * 本科学历应聘硕士要求岗位 → 学历门槛不通过直接淘汰
     */
    @Test
    public void shouldRejectWhenEducationBelowRequirement() {
        Resume resume = baseResume("李强", "BACHELOR", "MASTER", "211", 5, 3, 4, 5);
        resume.setExpectedSalary(16000.0);
        resume.setSalaryMin(15000.0);
        resume.setSalaryMax(25000.0);
        resume.setCurrentCity("上海");
        resume.setJobCity("上海");
        resume.setWillingToRelocate(false);
        resume.setJobHoppingCount(1);
        resume.setGapMonths(5);
        resume.setCertified(true);
        resume.setEnglishLevel("CET6");

        ScreeningDecision decision = service.screen(resume);

        Assert.assertFalse(decision.isHardCheckPass());
        Assert.assertEquals("REJECT", decision.getResult());
        Assert.assertEquals("NONE", decision.getRecommendLevel());
        Assert.assertTrue(decision.getReasons().contains("学历 BACHELOR 未达岗位要求 MASTER"));
    }

    /**
     * 期望薪资超出带宽上限 → 薪资门槛不通过
     */
    @Test
    public void shouldRejectWhenSalaryOutOfRange() {
        Resume resume = baseResume("王芳", "MASTER", "BACHELOR", "211", 6, 3, 5, 5);
        resume.setExpectedSalary(30000.0);
        resume.setSalaryMin(20000.0);
        resume.setSalaryMax(25000.0);
        resume.setCurrentCity("北京");
        resume.setJobCity("北京");
        resume.setWillingToRelocate(false);
        resume.setJobHoppingCount(1);
        resume.setGapMonths(1);
        resume.setCertified(true);
        resume.setEnglishLevel("CET6");

        ScreeningDecision decision = service.screen(resume);

        Assert.assertFalse(decision.isHardCheckPass());
        Assert.assertEquals("REJECT", decision.getResult());
        Assert.assertTrue(decision.getReasons().contains(
                "期望薪资 30000.0 超出岗位薪资带宽或显著低于带宽下限"));
    }

    /**
     * 大专达标、技能覆盖 80%、评分 57 → 纳入人才储备
     */
    @Test
    public void shouldReserveBorderlineCandidate() {
        Resume resume = baseResume("赵敏", "COLLEGE", "COLLEGE", "ORDINARY", 5, 3, 4, 5);
        resume.setExpectedSalary(9000.0);
        resume.setSalaryMin(8000.0);
        resume.setSalaryMax(12000.0);
        resume.setCurrentCity("成都");
        resume.setJobCity("成都");
        resume.setWillingToRelocate(false);
        resume.setJobHoppingCount(2);
        resume.setGapMonths(6);
        resume.setCertified(true);
        resume.setEnglishLevel("CET4");

        ScreeningDecision decision = service.screen(resume);

        Assert.assertTrue(decision.isHardCheckPass());
        Assert.assertEquals(57, decision.getTotalScore());
        Assert.assertEquals("RESERVE", decision.getResult());
        Assert.assertEquals("RESERVE", decision.getRecommendLevel());
        Assert.assertTrue(decision.getReasons().contains("硬性条件满足但评分 57，纳入人才储备池"));
    }

    /**
     * 近 5 年跳槽 5 次 → 命中稳定性否决项
     */
    @Test
    public void shouldRejectFrequentJobHopper() {
        Resume resume = baseResume("孙磊", "MASTER", "BACHELOR", "985", 7, 3, 5, 5);
        resume.setExpectedSalary(20000.0);
        resume.setSalaryMin(15000.0);
        resume.setSalaryMax(30000.0);
        resume.setCurrentCity("深圳");
        resume.setJobCity("深圳");
        resume.setWillingToRelocate(false);
        resume.setJobHoppingCount(5);
        resume.setGapMonths(1);
        resume.setCertified(true);
        resume.setEnglishLevel("CET6");

        ScreeningDecision decision = service.screen(resume);

        Assert.assertTrue(decision.isHardCheckPass());
        Assert.assertEquals("REJECT", decision.getResult());
        Assert.assertTrue(decision.getReasons().get(0).contains("命中稳定性否决项"));
    }

    /**
     * 异地候选人愿意调动，评分 78 → 正常推荐面试
     */
    @Test
    public void shouldInterviewRelocationWillingCandidate() {
        Resume resume = baseResume("周涛", "BACHELOR", "BACHELOR", "211", 7, 2, 3, 3);
        resume.setExpectedSalary(12000.0);
        resume.setSalaryMin(10000.0);
        resume.setSalaryMax(15000.0);
        resume.setCurrentCity("武汉");
        resume.setJobCity("上海");
        resume.setWillingToRelocate(true);
        resume.setJobHoppingCount(1);
        resume.setGapMonths(12);
        resume.setCertified(true);
        resume.setEnglishLevel("CET6");

        ScreeningDecision decision = service.screen(resume);

        Assert.assertTrue(decision.isHardCheckPass());
        Assert.assertEquals(78, decision.getTotalScore());
        Assert.assertEquals("INTERVIEW", decision.getResult());
        Assert.assertEquals("NORMAL", decision.getRecommendLevel());
    }

    private Resume baseResume(String name, String education, String requiredEducation, String schoolTier,
                              int years, int requiredYears, int matched, int required) {
        Resume resume = new Resume();
        resume.setCandidateName(name);
        resume.setPositionName("Java 开发工程师");
        resume.setEducationLevel(education);
        resume.setRequiredEducation(requiredEducation);
        resume.setSchoolTier(schoolTier);
        resume.setYearsExperience(years);
        resume.setRequiredYears(requiredYears);
        resume.setSkillMatchCount(matched);
        resume.setRequiredSkillCount(required);
        resume.setChannel("ONLINE");
        return resume;
    }
}
