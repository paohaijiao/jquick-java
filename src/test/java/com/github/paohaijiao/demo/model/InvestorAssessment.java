package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 投资者风险承受能力测评与拟购产品要素（投资者适当性管理）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestorAssessment {

    /**
     * 客户号
     */
    private String investorId;

    /**
     * 客户姓名
     */
    private String investorName;

    /**
     * 年龄
     */
    private int age;

    /**
     * 年收入（元）
     */
    private double annualIncome;

    /**
     * 家庭金融净资产（元）
     */
    private double netAssets;

    /**
     * 投资经验年限
     */
    private int investExperienceYears;

    /**
     * 风险承受能力等级（1-5，对应 C1 保守型至 C5 激进型）
     */
    private int riskToleranceRank;

    /**
     * 拟购产品风险等级（1-5，对应 R1 低风险至 R5 高风险）
     */
    private int productRiskRank;

    /**
     * 拟购产品名称
     */
    private String productName;

    /**
     * 是否已签署产品风险揭示书
     */
    private boolean signedRiskNotice;
}
