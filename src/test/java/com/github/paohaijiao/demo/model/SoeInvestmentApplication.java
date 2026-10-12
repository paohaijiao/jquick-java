package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 国有企业投资项目立项要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoeInvestmentApplication {

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目类型：MAIN-主业 / NON_MAIN-非主业 / FINANCIAL-金融类 / REAL_ESTATE-房地产类
     */
    private String projectType;

    /**
     * 申报总投资额（万元）
     */
    private int totalInvestmentWan;

    /**
     * 自有资金占总投资比例（整数百分比）
     */
    private int selfOwnedRatio;

    /**
     * 资产负债率（整数百分比）
     */
    private int debtRatio;

    /**
     * 董事会已授权出资额度（万元）
     */
    private int boardApprovalAmountWan;

    /**
     * 是否出具可行性研究报告
     */
    private boolean hasFeasibilityReport;

    /**
     * 是否出具风险评估报告
     */
    private boolean hasRiskAssessment;

    /**
     * 预期年收益率（整数百分比）
     */
    private int expectedReturnRate;
}
