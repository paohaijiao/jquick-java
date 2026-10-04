package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.TaxReliefRules;
import com.github.paohaijiao.demo.model.TaxReliefApplication;
import com.github.paohaijiao.demo.model.TaxReliefDecision;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 场景 23：小微企业税费减免 / 稳岗补贴资格判定服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：小型微利企业三项条件（人数/资产/所得额）、行业禁入、
 *     欠税冻结、法定税额与减免额对比核算</li>
 *     <li><b>规则侧</b>：分段实际税负、小规模征收率、稳岗资格与返还比例，外置在 XML 中</li>
 * </ul>
 */
public class TaxReliefService {

    private static final int EMPLOYEE_LIMIT = 300;
    private static final double ASSETS_LIMIT_WAN = 5000.0;
    private static final double INCOME_LIMIT_WAN = 300.0;

    private final TaxReliefRules rules;

    public TaxReliefService(TaxReliefRules rules) {
        this.rules = rules;
    }

    public TaxReliefDecision evaluate(TaxReliefApplication app) {
        TaxReliefDecision decision = new TaxReliefDecision();
        decision.setEnterpriseId(app.getEnterpriseId());

        double income = app.getAnnualTaxableIncomeWan();
        double statutory = round2(income * 0.25);
        decision.setStatutoryTaxWan(statutory);

        if (app.isRestrictedIndustry()) {
            decision.setResult("DENIED");
            decision.setSmallQualified(false);
            decision.setIncomeTaxWan(statutory);
            decision.setTaxSavingWan(0.0);
            decision.setVatLevyRate(0.0);
            decision.setStabilizationEligible(false);
            decision.setStabilizationSubsidyWan(0.0);
            decision.addReason("企业从事限制或禁止行业，不得享受小型微利企业税收优惠及稳岗返还");
            return decision;
        }

        decision.setResult("APPROVED");

        boolean employeeOk = app.getEmployeeCount() <= EMPLOYEE_LIMIT;
        boolean assetsOk = app.getTotalAssetsWan() <= ASSETS_LIMIT_WAN;
        boolean incomeOk = income <= INCOME_LIMIT_WAN;
        boolean qualified = employeeOk && assetsOk && incomeOk;
        decision.setSmallQualified(qualified);

        double tax = round2(rules.incomeTaxWan(income, qualified));
        decision.setIncomeTaxWan(tax);
        decision.setTaxSavingWan(round2(statutory - tax));

        if (qualified) {
            decision.addReason("符合小型微利企业条件，年应纳企业所得税核定为 " + tax + " 万元，"
                    + "较法定税率减免 " + decision.getTaxSavingWan() + " 万元");
        } else {
            decision.addReason("不满足小型微利企业条件，按 25% 法定税率缴纳企业所得税 " + statutory + " 万元");
            if (!employeeOk) {
                decision.addReason("从业人数 " + app.getEmployeeCount() + " 人超过 300 人标准");
            }
            if (!assetsOk) {
                decision.addReason("资产总额 " + money(app.getTotalAssetsWan()) + " 万元超过 5000 万元标准");
            }
            if (!incomeOk) {
                decision.addReason("年应纳税所得额超过 300 万元标准");
            }
        }

        double vatRate = rules.vatLevyRate(app.getTaxpayerType());
        decision.setVatLevyRate(vatRate);
        if (vatRate > 0.0) {
            decision.addReason("小规模纳税人增值税减按 1% 征收率执行");
        }

        boolean stable = rules.stabilizationEligible(app.getSocialInsuranceMonths(),
                app.getLayoffRate(), !app.isSocialArrears());
        if (stable && app.isOverdueTax()) {
            stable = false;
            decision.addReason("企业存在欠税记录，稳岗返还暂缓拨付，清缴后另行申请");
        }
        decision.setStabilizationEligible(stable);
        double subsidy = round2(rules.subsidyAmountWan(app.getPaidUnemploymentInsuranceWan(), stable));
        decision.setStabilizationSubsidyWan(subsidy);
        if (stable) {
            decision.addReason("符合稳岗返还条件，按实缴失业保险费 50% 核定补贴 " + subsidy + " 万元");
        } else if (!app.isOverdueTax()) {
            decision.addReason("不满足稳岗返还条件（社保连续缴纳满 12 月、裁员率 5% 以下、无社保欠费）");
        }
        return decision;
    }

    private double round2(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /** 金额文本：固定两位小数且禁用科学计数法 */
    private String money(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
