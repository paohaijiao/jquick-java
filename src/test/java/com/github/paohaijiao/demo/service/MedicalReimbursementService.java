package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.MedicalReimbursementRules;
import com.github.paohaijiao.demo.model.MedicalClaim;
import com.github.paohaijiao.demo.model.ReimbursementDecision;

/**
 * 场景 13：医保报销合规校验服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：报销链路串联（目录 → 先行自付 → 起付线 → 比例 → 封顶）、结算说明组装</li>
 *     <li><b>规则侧</b>：目录范围、起付线与比例矩阵、乙类先行自付、封顶线、备案口径，全部外置在 XML 中</li>
 * </ul>
 */
public class MedicalReimbursementService {

    private final MedicalReimbursementRules rules;

    public MedicalReimbursementService(MedicalReimbursementRules rules) {
        this.rules = rules;
    }

    /**
     * 执行医保报销结算
     *
     * @param claim 就诊费用要素
     * @return 报销决策
     */
    public ReimbursementDecision settle(MedicalClaim claim) {
        ReimbursementDecision decision = new ReimbursementDecision();
        decision.setClaimNo(claim.getClaimNo());

        boolean withinCatalog = rules.withinCatalog(claim.getMedicalCategory());
        double annualCap = rules.annualCap(claim.getInsuranceType());
        decision.setWithinCatalog(withinCatalog);
        decision.setAnnualCap(annualCap);

        if (!withinCatalog) {
            decision.setResult("SELF_PAY");
            decision.setPayableAmount(0.0);
            decision.setSelfPayAmount(claim.getTotalExpense());
            decision.addNote(claim.getMedicalCategory() + " 属目录外项目，全部自费");
            return decision;
        }

        double deductible = rules.deductible(claim.getInsuranceType(),
                claim.getHospitalLevel(), claim.isEmergency());
        double ratio = rules.reimburseRatio(claim.getInsuranceType(),
                claim.getHospitalLevel(), claim.isEmergency());
        double prepaid = rules.prepaidSelfPay(claim.getMedicalCategory(), claim.getTotalExpense());

        double baseAfterPrepaid = claim.getTotalExpense() - prepaid;
        double baseAfterDeductible = baseAfterPrepaid - deductible;
        if (baseAfterDeductible < 0.0) {
            baseAfterDeductible = 0.0;
        }
        double rawPayable = baseAfterDeductible * ratio;
        double remainingCap = annualCap - claim.getAnnualAccumulated();
        double payable = rules.applyCap(rawPayable, remainingCap);

        decision.setDeductibleAmount(deductible);
        decision.setPrepaidSelfPay(prepaid);
        decision.setReimburseRatio(ratio);
        decision.setPayableAmount(payable);
        decision.setSelfPayAmount(claim.getTotalExpense() - payable);
        decision.setCapped(payable < rawPayable);
        decision.setResult(rules.complianceResult(true, claim.isOffSite(), claim.isFiled()));
        decision.setNeedManualReview(rules.needManualReview(decision.getResult(), payable));

        buildNotes(claim, decision, rawPayable);
        return decision;
    }

    private void buildNotes(MedicalClaim claim, ReimbursementDecision decision, double rawPayable) {
        if (claim.isEmergency()) {
            decision.addNote("急诊就诊，起付线减半、报销比例上浮 5 个百分点");
        }
        if (decision.getPrepaidSelfPay() > 0.0) {
            decision.addNote("乙类药品先行自付 10%：" + decision.getPrepaidSelfPay() + " 元");
        }
        if (decision.isCapped()) {
            decision.addNote("统筹支付触达年度封顶线，理论支付 " + rawPayable + " 元，按剩余额度 "
                    + decision.getPayableAmount() + " 元结算");
        }
        if (claim.isOffSite() && !claim.isFiled()) {
            decision.addNote("异地就医未备案，转人工审核后再行结算");
        }
    }
}
