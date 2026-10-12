package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.MedicalClaim;
import com.github.paohaijiao.demo.model.ReimbursementDecision;
import com.github.paohaijiao.demo.service.MedicalReimbursementService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 13 生产级样板：医保报销项目与比例合规校验
 * <p>
 * 覆盖 6 组结算形态：职工三甲甲类、居民社区乙类、丙类全自费、
 * 年度封顶截断、急诊政策倾斜、异地未备案人工审核。
 */
public class MedicalReimbursementServiceTest {

    private MedicalReimbursementService service;

    @Before
    public void setUp() {
        MedicalReimbursementRules rules = JQuickJava.create()
                .createApi(MedicalReimbursementRules.class, "demo/medical-reimbursement.xml");
        this.service = new MedicalReimbursementService(rules);
    }

    /**
     * 职工三甲甲类药，费用 5000 元 → 起付线 800，比例 90%，支付 3780
     */
    @Test
    public void shouldReimburseEmployeeThirdGradeClassA() {
        MedicalClaim claim = claim("MC-1301", "EMPLOYEE", "THIRD_A", "DRUG_A", 5000.0,
                false, false, false, 0.0);

        ReimbursementDecision decision = service.settle(claim);

        Assert.assertTrue(decision.isWithinCatalog());
        Assert.assertEquals("APPROVE", decision.getResult());
        Assert.assertEquals(800.0, decision.getDeductibleAmount(), 0.001);
        Assert.assertEquals(0.9, decision.getReimburseRatio(), 0.0001);
        Assert.assertEquals(0.0, decision.getPrepaidSelfPay(), 0.001);
        Assert.assertEquals(3780.0, decision.getPayableAmount(), 0.001);
        Assert.assertEquals(1220.0, decision.getSelfPayAmount(), 0.001);
        Assert.assertFalse(decision.isCapped());
        Assert.assertFalse(decision.isNeedManualReview());
    }

    /**
     * 居民社区乙类药，费用 1000 元 → 先行自付 100、起付线 100、比例 60%，支付 480
     */
    @Test
    public void shouldReimburseResidentCommunityClassB() {
        MedicalClaim claim = claim("MC-1302", "RESIDENT", "COMMUNITY", "DRUG_B", 1000.0,
                false, false, false, 0.0);

        ReimbursementDecision decision = service.settle(claim);

        Assert.assertEquals("APPROVE", decision.getResult());
        Assert.assertEquals(100.0, decision.getDeductibleAmount(), 0.001);
        Assert.assertEquals(100.0, decision.getPrepaidSelfPay(), 0.001);
        Assert.assertEquals(0.6, decision.getReimburseRatio(), 0.0001);
        Assert.assertEquals(480.0, decision.getPayableAmount(), 0.001);
        Assert.assertEquals(520.0, decision.getSelfPayAmount(), 0.001);
        Assert.assertTrue(decision.getNotes().contains("乙类药品先行自付 10%：100.0 元"));
    }

    /**
     * 丙类药目录外 → 全部自费 800 元
     */
    @Test
    public void shouldDenyOutOfCatalogClassCDrug() {
        MedicalClaim claim = claim("MC-1303", "EMPLOYEE", "THIRD_A", "DRUG_C", 800.0,
                false, false, false, 0.0);

        ReimbursementDecision decision = service.settle(claim);

        Assert.assertFalse(decision.isWithinCatalog());
        Assert.assertEquals("SELF_PAY", decision.getResult());
        Assert.assertEquals(0.0, decision.getPayableAmount(), 0.001);
        Assert.assertEquals(800.0, decision.getSelfPayAmount(), 0.001);
        Assert.assertEquals(0.0, decision.getReimburseRatio(), 0.0001);
        Assert.assertTrue(decision.getNotes().contains("DRUG_C 属目录外项目，全部自费"));
    }

    /**
     * 大额费用触达年度封顶线：剩余额度仅 2 万 → 支付 20000
     */
    @Test
    public void shouldTruncateAtAnnualCap() {
        MedicalClaim claim = claim("MC-1304", "EMPLOYEE", "THIRD_A", "DRUG_A", 600000.0,
                false, false, false, 280000.0);

        ReimbursementDecision decision = service.settle(claim);

        Assert.assertEquals(300000.0, decision.getAnnualCap(), 0.001);
        Assert.assertEquals(20000.0, decision.getPayableAmount(), 0.001);
        Assert.assertEquals(580000.0, decision.getSelfPayAmount(), 0.001);
        Assert.assertTrue(decision.isCapped());
        Assert.assertTrue(decision.getNotes().contains(
                "统筹支付触达年度封顶线，理论支付 539280.0 元，按剩余额度 20000.0 元结算"));
    }

    /**
     * 职工二级医院急诊，费用 3000 元 → 起付线 250、比例 90%，支付 2475
     */
    @Test
    public void shouldApplyEmergencyPreferentialPolicy() {
        MedicalClaim claim = claim("MC-1305", "EMPLOYEE", "SECOND_A", "DRUG_A", 3000.0,
                true, false, false, 0.0);

        ReimbursementDecision decision = service.settle(claim);

        Assert.assertEquals(250.0, decision.getDeductibleAmount(), 0.001);
        Assert.assertEquals(0.9, decision.getReimburseRatio(), 0.0001);
        Assert.assertEquals(2475.0, decision.getPayableAmount(), 0.001);
        Assert.assertTrue(decision.getNotes().contains("急诊就诊，起付线减半、报销比例上浮 5 个百分点"));
    }

    /**
     * 异地就医未备案，费用 6000 元理论支付 4680 → 挂账转人工审核
     */
    @Test
    public void shouldHoldOffSiteClaimWithoutFiling() {
        MedicalClaim claim = claim("MC-1306", "EMPLOYEE", "THIRD_A", "DRUG_A", 6000.0,
                false, true, false, 0.0);

        ReimbursementDecision decision = service.settle(claim);

        Assert.assertEquals(4680.0, decision.getPayableAmount(), 0.001);
        Assert.assertEquals("MANUAL_REVIEW", decision.getResult());
        Assert.assertTrue(decision.isNeedManualReview());
        Assert.assertTrue(decision.getNotes().contains("异地就医未备案，转人工审核后再行结算"));
    }

    private MedicalClaim claim(String claimNo, String insuranceType, String hospitalLevel,
                               String category, double totalExpense, boolean emergency,
                               boolean offSite, boolean filed, double annualAccumulated) {
        MedicalClaim claim = new MedicalClaim();
        claim.setClaimNo(claimNo);
        claim.setPatientName("患者");
        claim.setInsuranceType(insuranceType);
        claim.setHospitalLevel(hospitalLevel);
        claim.setMedicalCategory(category);
        claim.setTotalExpense(totalExpense);
        claim.setEmergency(emergency);
        claim.setOffSite(offSite);
        claim.setFiled(filed);
        claim.setAnnualAccumulated(annualAccumulated);
        return claim;
    }
}
