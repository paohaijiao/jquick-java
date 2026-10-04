package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 税费减免与稳岗补贴核定结果
 */
@Data
public class TaxReliefDecision {

    /**
     * 企业编号
     */
    private String enterpriseId;

    /**
     * 办理结论：APPROVED-可享受优惠 / DENIED-不予享受
     */
    private String result;

    /**
     * 是否具备小型微利企业资格
     */
    private boolean smallQualified;

    /**
     * 法定应纳企业所得税（25% 口径，万元）
     */
    private double statutoryTaxWan;

    /**
     * 核定应纳企业所得税（万元）
     */
    private double incomeTaxWan;

    /**
     * 企业所得税减免额（万元）
     */
    private double taxSavingWan;

    /**
     * 增值税征收率（小规模优惠为 0.01，不适用为 0）
     */
    private double vatLevyRate;

    /**
     * 是否具备稳岗补贴资格
     */
    private boolean stabilizationEligible;

    /**
     * 核定稳岗补贴金额（万元）
     */
    private double stabilizationSubsidyWan;

    /**
     * 资格判定与政策依据
     */
    private List<String> reasons = new ArrayList<String>();

    public void addReason(String reason) {
        this.reasons.add(reason);
    }
}
