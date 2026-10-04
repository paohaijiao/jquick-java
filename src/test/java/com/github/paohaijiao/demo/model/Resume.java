package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 候选人简历要素（初筛所需的硬性条件与画像）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resume {

    /**
     * 候选人姓名
     */
    private String candidateName;

    /**
     * 应聘岗位
     */
    private String positionName;

    /**
     * 候选人学历：PHD / MASTER / BACHELOR / COLLEGE
     */
    private String educationLevel;

    /**
     * 岗位要求学历
     */
    private String requiredEducation;

    /**
     * 毕业院校层次：985 / 211 / ORDINARY
     */
    private String schoolTier;

    /**
     * 相关工作年限
     */
    private int yearsExperience;

    /**
     * 岗位要求年限
     */
    private int requiredYears;

    /**
     * 技能匹配数
     */
    private int skillMatchCount;

    /**
     * 岗位要求技能数
     */
    private int requiredSkillCount;

    /**
     * 期望薪资
     */
    private double expectedSalary;

    /**
     * 薪资带宽下限
     */
    private double salaryMin;

    /**
     * 薪资带宽上限
     */
    private double salaryMax;

    /**
     * 所在城市
     */
    private String currentCity;

    /**
     * 工作城市
     */
    private String jobCity;

    /**
     * 是否接受异地调动
     */
    private boolean willingToRelocate;

    /**
     * 近 5 年跳槽次数
     */
    private int jobHoppingCount;

    /**
     * 职业空窗月数
     */
    private int gapMonths;

    /**
     * 是否持有岗位相关资格证
     */
    private boolean certified;

    /**
     * 英语水平：CET6 / CET4 / NONE
     */
    private String englishLevel;

    /**
     * 招聘渠道：REFERRAL / HEADHUNTER / ONLINE
     */
    private String channel;
}
