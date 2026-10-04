package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 科技项目申报要素（立项初审所需的项目画像）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechProjectApplication {

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目类别：KEY_CORE-关键核心技术 / HIGH_TECH-高新技术 / SOCIAL_DEV-社会发展
     */
    private String projectCategory;

    /**
     * 技术鉴定结论：INTL_LEADING-国际领先 / INTL_ADVANCED-国际先进
     * / DOM_LEADING-国内领先 / DOM_ADVANCED-国内先进 / GENERAL-一般
     */
    private String techLevel;

    /**
     * 已授权发明专利数量
     */
    private int inventionPatents;

    /**
     * 产业化阶段：MASS-量产 / PILOT-中试 / LAB-实验室
     */
    private String pilotStage;

    /**
     * 上年度营业收入（万元）
     */
    private int annualRevenueWan;

    /**
     * 团队带头人称号：ACADEMICIAN-院士级 / EXPERT-行业专家 / NORMAL-普通
     */
    private String leaderTitle;

    /**
     * 核心团队博士人数
     */
    private int phdCount;

    /**
     * 研发投入占营业收入比例（整数百分比）
     */
    private int rdRatio;

    /**
     * 项目自筹资金占总投资比例（整数百分比）
     */
    private int selfFundedRatio;

    /**
     * 项目达产后新增就业人数
     */
    private int employmentIncrease;

    /**
     * 年节能折标煤量（吨）
     */
    private int energySavingTon;

    /**
     * 近三年是否存在重大违法违规记录
     */
    private boolean majorViolation;

    /**
     * 申报材料是否齐全
     */
    private boolean materialsComplete;
}
