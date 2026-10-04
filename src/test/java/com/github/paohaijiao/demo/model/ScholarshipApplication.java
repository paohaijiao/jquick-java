package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生奖学金/助学金申请要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScholarshipApplication {

    /**
     * 学号
     */
    private String studentId;

    private String studentName;

    /**
     * 申请类型：NATIONAL 国家级奖助 / ACADEMIC 学业奖学金 /
     * NEED_BASED 助学金 / INDIVIDUAL 单项奖学金
     */
    private String applyType;

    /**
     * 加权平均分（0-100）
     */
    private int averageScore;

    /**
     * 年级排名
     */
    private int rankInGrade;

    /**
     * 年级总人数
     */
    private int gradeTotal;

    /**
     * 不及格学分数
     */
    private int failedCredits;

    /**
     * 是否有纪律处分
     */
    private boolean disciplineViolation;

    /**
     * 竞赛级别：NATIONAL 国家级 / PROVINCIAL 省级 / SCHOOL 校级 / NONE 无
     */
    private String competitionLevel;

    /**
     * 是否发表学术论文
     */
    private boolean publishedPaper;

    /**
     * 是否志愿服务之星
     */
    private boolean volunteerStar;

    /**
     * 家庭经济情况：HARDSHIP 特困 / DIFFICULT 困难 / NORMAL 一般
     */
    private String familyEconomicLevel;
}
