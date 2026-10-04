package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.ScholarshipApplication;
import com.github.paohaijiao.demo.model.ScholarshipDecision;
import com.github.paohaijiao.demo.service.ScholarshipService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 38：校园奖学金/助学金评定
 * <p>
 * 6 组形态：国家奖学金标杆、国家励志（困难生限定）、学业一二三档、
 * 助学金困难分级与一般家庭驳回、单项奖学金、挂科/处分资格门控。
 */
public class ScholarshipServiceTest {

    private ScholarshipService service;

    @Before
    public void setUp() {
        ScholarshipRules rules = JQuickJava.create()
                .createApi(ScholarshipRules.class, "demo/scholarship.xml");
        this.service = new ScholarshipService(rules);
    }

    /**
     * 均分 96、年级前 1.5%、国赛获奖加论文 → 国家奖学金 8000
     */
    @Test
    public void shouldAwardNationalSpecialScholarship() {
        ScholarshipApplication a = base("S001", "NATIONAL", "NORMAL");
        a.setAverageScore(96);
        a.setRankInGrade(3);
        a.setGradeTotal(200);
        a.setCompetitionLevel("NATIONAL");
        a.setPublishedPaper(true);

        ScholarshipDecision d = service.evaluate(a);

        Assert.assertTrue(d.isEligible());
        Assert.assertEquals(100, d.getEvaluationScore());
        Assert.assertEquals("NATIONAL_SPECIAL", d.getScholarshipLevel());
        Assert.assertEquals(8000, d.getRewardAmount());
        Assert.assertTrue(d.getHonors().contains("科研创新标兵"));
    }

    /**
     * 88 分前 12.5% 校赛 → 综合 71 分，困难生获国家励志奖学金
     */
    @Test
    public void shouldAwardNationalEndeavorToNeedyStudent() {
        ScholarshipApplication a = base("S002", "NATIONAL", "DIFFICULT");
        a.setAverageScore(88);
        a.setRankInGrade(25);
        a.setGradeTotal(200);
        a.setCompetitionLevel("SCHOOL");

        ScholarshipDecision d = service.evaluate(a);

        Assert.assertEquals(71, d.getEvaluationScore());
        Assert.assertEquals("NATIONAL_ENDEAVOR", d.getScholarshipLevel());
        Assert.assertEquals(5000, d.getRewardAmount());
    }

    /**
     * 学业奖学金：95 分前 5% 一等、85 分前 15% 二等、80 分前 25% 三等
     */
    @Test
    public void shouldAwardAcademicScholarshipByThreeTiers() {
        ScholarshipApplication first = base("S003", "ACADEMIC", "NORMAL");
        first.setAverageScore(95);
        first.setRankInGrade(10);
        first.setGradeTotal(200);
        ScholarshipDecision d1 = service.evaluate(first);
        Assert.assertEquals(85, d1.getEvaluationScore());
        Assert.assertEquals("FIRST", d1.getScholarshipLevel());
        Assert.assertEquals(3000, d1.getRewardAmount());

        ScholarshipApplication second = base("S004", "ACADEMIC", "NORMAL");
        second.setAverageScore(85);
        second.setRankInGrade(30);
        second.setGradeTotal(200);
        ScholarshipDecision d2 = service.evaluate(second);
        Assert.assertEquals(66, d2.getEvaluationScore());
        Assert.assertEquals("SECOND", d2.getScholarshipLevel());
        Assert.assertEquals(2000, d2.getRewardAmount());

        ScholarshipApplication third = base("S005", "ACADEMIC", "NORMAL");
        third.setAverageScore(80);
        third.setRankInGrade(50);
        third.setGradeTotal(200);
        ScholarshipDecision d3 = service.evaluate(third);
        Assert.assertEquals(51, d3.getEvaluationScore());
        Assert.assertEquals("THIRD", d3.getScholarshipLevel());
        Assert.assertEquals(1000, d3.getRewardAmount());
    }

    /**
     * 助学金：特困 3000、困难 2000、一般家庭驳回
     */
    @Test
    public void shouldGrantNeedBasedAidByEconomicLevel() {
        ScholarshipApplication hardship = base("S006", "NEED_BASED", "HARDSHIP");
        hardship.setAverageScore(75);
        hardship.setRankInGrade(100);
        hardship.setGradeTotal(200);
        ScholarshipDecision d1 = service.evaluate(hardship);
        Assert.assertEquals(28, d1.getEvaluationScore());
        Assert.assertEquals("FIRST_GRANT", d1.getScholarshipLevel());
        Assert.assertEquals(3000, d1.getRewardAmount());

        ScholarshipApplication difficult = base("S007", "NEED_BASED", "DIFFICULT");
        difficult.setAverageScore(75);
        difficult.setRankInGrade(100);
        difficult.setGradeTotal(200);
        ScholarshipDecision d2 = service.evaluate(difficult);
        Assert.assertEquals("SECOND_GRANT", d2.getScholarshipLevel());
        Assert.assertEquals(2000, d2.getRewardAmount());

        ScholarshipApplication normal = base("S008", "NEED_BASED", "NORMAL");
        normal.setAverageScore(90);
        normal.setRankInGrade(5);
        normal.setGradeTotal(200);
        ScholarshipDecision d3 = service.evaluate(normal);
        Assert.assertEquals("NONE", d3.getScholarshipLevel());
        Assert.assertTrue(d3.getRejectReason().contains("不符合助学金申请条件"));
    }

    /**
     * 单项：国赛创新奖 1500；志愿服务之星 500
     */
    @Test
    public void shouldAwardIndividualScholarship() {
        ScholarshipApplication comp = base("S009", "INDIVIDUAL", "NORMAL");
        comp.setCompetitionLevel("NATIONAL");
        ScholarshipDecision d1 = service.evaluate(comp);
        Assert.assertEquals("INNOVATION", d1.getScholarshipLevel());
        Assert.assertEquals(1500, d1.getRewardAmount());

        ScholarshipApplication volunteer = base("S010", "INDIVIDUAL", "NORMAL");
        volunteer.setVolunteerStar(true);
        ScholarshipDecision d2 = service.evaluate(volunteer);
        Assert.assertEquals("VOLUNTEER", d2.getScholarshipLevel());
        Assert.assertEquals(500, d2.getRewardAmount());
        Assert.assertTrue(d2.getHonors().contains("志愿服务之星"));
    }

    /**
     * 挂科或处分 → 直接丧失参评资格
     */
    @Test
    public void shouldRejectFailedOrDisciplinedApplicant() {
        ScholarshipApplication failed = base("S011", "ACADEMIC", "NORMAL");
        failed.setAverageScore(96);
        failed.setFailedCredits(3);
        ScholarshipDecision d1 = service.evaluate(failed);
        Assert.assertFalse(d1.isEligible());
        Assert.assertEquals("NONE", d1.getScholarshipLevel());
        Assert.assertTrue(d1.getRejectReason().contains("3 学分不及格"));

        ScholarshipApplication violated = base("S012", "ACADEMIC", "NORMAL");
        violated.setAverageScore(96);
        violated.setDisciplineViolation(true);
        ScholarshipDecision d2 = service.evaluate(violated);
        Assert.assertFalse(d2.isEligible());
        Assert.assertTrue(d2.getRejectReason().contains("纪律处分"));
    }

    private ScholarshipApplication base(String id, String type, String economic) {
        ScholarshipApplication a = new ScholarshipApplication();
        a.setStudentId(id);
        a.setStudentName("测试学生");
        a.setApplyType(type);
        a.setAverageScore(70);
        a.setRankInGrade(100);
        a.setGradeTotal(200);
        a.setFailedCredits(0);
        a.setDisciplineViolation(false);
        a.setCompetitionLevel("NONE");
        a.setPublishedPaper(false);
        a.setVolunteerStar(false);
        a.setFamilyEconomicLevel(economic);
        return a;
    }
}
