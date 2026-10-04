package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.ScholarshipRules;
import com.github.paohaijiao.demo.model.ScholarshipApplication;
import com.github.paohaijiao.demo.model.ScholarshipDecision;

/**
 * 场景 38：校园奖学金/评优/助学金资格评定
 */
public class ScholarshipService {

    /**
     * 助学金最低综合测评分（成绩基本要求）
     */
    private static final int NEED_BASED_MIN_SCORE = 25;

    private final ScholarshipRules rules;

    public ScholarshipService(ScholarshipRules rules) {
        this.rules = rules;
    }

    public ScholarshipDecision evaluate(ScholarshipApplication a) {
        ScholarshipDecision d = new ScholarshipDecision();
        d.setStudentId(a.getStudentId());

        if (!rules.eligibilityOk(a.getFailedCredits(), a.isDisciplineViolation())) {
            d.setEligible(false);
            d.setScholarshipLevel("NONE");
            d.setRewardAmount(0);
            if (a.getFailedCredits() > 0) {
                d.setRejectReason("存在 " + a.getFailedCredits()
                        + " 学分不及格课程，不具备奖助参评资格");
            } else {
                d.setRejectReason("考核期内存在纪律处分，不具备奖助参评资格");
            }
            return d;
        }
        d.setEligible(true);

        int score = rules.academicScore(a.getAverageScore())
                + rules.rankScore(a.getRankInGrade(), a.getGradeTotal())
                + rules.researchScore(a.getCompetitionLevel(), a.isPublishedPaper());
        d.setEvaluationScore(score);

        String level = rules.awardLevel(a.getApplyType(), score,
                a.getFamilyEconomicLevel(), a.getCompetitionLevel(),
                a.isPublishedPaper(), a.isVolunteerStar());

        if ("NATIONAL_ENDEAVOR".equals(level) && "NORMAL".equals(a.getFamilyEconomicLevel())) {
            d.setScholarshipLevel("NONE");
            d.setRewardAmount(0);
            d.setRejectReason("综合测评达国家励志奖学金线，但该奖项仅面向家庭经济困难学生");
            return d;
        }
        if ("NEED_BASED".equals(a.getApplyType())) {
            if ("NORMAL".equals(a.getFamilyEconomicLevel())) {
                d.setScholarshipLevel("NONE");
                d.setRewardAmount(0);
                d.setRejectReason("家庭经济情况为一般，不符合助学金申请条件");
                return d;
            }
            if (score < NEED_BASED_MIN_SCORE) {
                d.setScholarshipLevel("NONE");
                d.setRewardAmount(0);
                d.setRejectReason("综合测评 " + score + " 分未达助学金成绩基本线 "
                        + NEED_BASED_MIN_SCORE + " 分");
                return d;
            }
        }
        if ("INDIVIDUAL".equals(a.getApplyType()) && "NONE".equals(level)) {
            d.setScholarshipLevel("NONE");
            d.setRewardAmount(0);
            d.setRejectReason("无国家级/省级竞赛、论文或志愿服务之星等单项成果");
            return d;
        }

        d.setScholarshipLevel(level);
        d.setRewardAmount(rules.amountOf(level));
        if (a.isPublishedPaper() && !"NONE".equals(level)) {
            d.addHonor("科研创新标兵");
        }
        if (a.isVolunteerStar()) {
            d.addHonor("志愿服务之星");
        }
        return d;
    }
}
