package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 核心企业上游供应商档案（供应链金融保理准入要素）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierProfile {

    /**
     * 供应商编号
     */
    private String supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 工商注册年限
     */
    private int registeredYears;

    /**
     * 注册资本（万元）
     */
    private int registeredCapitalWan;

    /**
     * 资质等级：S / A / B / C / NONE（未取得体系认证）
     */
    private String qualificationLevel;

    /**
     * 近 12 个月与核心企业交易额（元）
     */
    private double annualOrderAmount;

    /**
     * 准时交付率（百分比，可含一位小数，如 97.5）
     */
    private double onTimeDeliveryRate;

    /**
     * 来料/交付质量合格率（百分比）
     */
    private double qualityPassRate;

    /**
     * 合同履约率（百分比）
     */
    private double contractFulfillRate;

    /**
     * 与核心企业合作月数
     */
    private int cooperationMonths;

    /**
     * 财务健康状况：HEALTHY / STABLE / WEAK / RISKY
     */
    private String financeStatus;

    /**
     * 是否失信被执行人
     */
    private boolean dishonest;

    /**
     * 近 2 年是否发生重大合同违约
     */
    private boolean majorBreach;
}
