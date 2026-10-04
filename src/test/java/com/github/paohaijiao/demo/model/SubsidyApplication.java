package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 人才补贴申请要素（资格审核所需的申请人画像）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubsidyApplication {

    /**
     * 申请单号
     */
    private String applyNo;

    /**
     * 申请人姓名
     */
    private String applicantName;

    /**
     * 补贴类型：SETTLEMENT-安家补贴 / STARTUP-创业补贴 / RENT-租房补贴
     */
    private String subsidyType;

    /**
     * 学历：PHD / MASTER / BACHELOR / COLLEGE
     */
    private String education;

    /**
     * 年龄
     */
    private int age;

    /**
     * 本地社保连续缴纳月数
     */
    private int localSocialSecurityMonths;

    /**
     * 高层次人才认定类别：A / B / C / NONE
     */
    private String talentCategory;

    /**
     * 是否在本市创办企业
     */
    private boolean startupRegistered;

    /**
     * 创办企业带动就业人数
     */
    private int startupEmployees;

    /**
     * 是否有失信被执行记录
     */
    private boolean creditViolation;

    /**
     * 是否有刑事犯罪记录
     */
    private boolean criminalRecord;

    /**
     * 是否已享受过同类补贴
     */
    private boolean receivedSimilarSubsidy;

    /**
     * 申报材料是否齐全
     */
    private boolean documentsComplete;
}
