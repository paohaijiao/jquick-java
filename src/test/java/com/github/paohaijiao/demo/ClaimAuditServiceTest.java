package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.ClaimDecision;
import com.github.paohaijiao.demo.model.ClaimRequest;
import com.github.paohaijiao.demo.service.ClaimAuditService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 3 生产级样板：保险理赔核赔
 * <p>
 * 覆盖 6 组核赔形态：门诊小额快赔、重疾大额封顶、等待期拒赔、风控标记转调查、
 * 材料不全转人工、未达免赔额拒赔。
 */
public class ClaimAuditServiceTest {

    private ClaimAuditService service;

    @Before
    public void setUp() {
        ClaimAuditRules rules = JQuickJava.create()
                .createApi(ClaimAuditRules.class, "demo/claim-audit.xml");
        this.service = new ClaimAuditService(rules);
    }

    /**
     * 门诊小额：三甲医院医疗险，赔款 2465 元，材料齐全且无历史理赔 → 正常赔付并走快赔通道
     */
    @Test
    public void shouldApproveOutpatientClaimWithFastTrack() {
        ClaimRequest request = new ClaimRequest();
        request.setClaimNo("CLM20260826001");
        request.setPolicyNo("POL20250101001");
        request.setInsuredName("刘洋");
        request.setProductType("MEDICAL");
        request.setHospitalLevel("THIRD_A");
        request.setClaimAmount(3200.0);
        request.setInvoiceAmount(3200.0);
        request.setDeductible(300.0);
        request.setInsuredDays(400);
        request.setWaitingPeriodDays(30);
        request.setPriorClaimCount(0);
        request.setFraudFlagCount(0);
        request.setAccident(false);
        request.setDocumentComplete(true);

        ClaimDecision decision = service.audit(request);

        Assert.assertEquals("APPROVE", decision.getAuditResult());
        Assert.assertEquals(2465.0, decision.getPayableAmount(), 0.001);
        Assert.assertEquals(300.0, decision.getDeductibleAmount(), 0.001);
        Assert.assertEquals(0.85, decision.getReimburseRatio(), 0.0001);
        Assert.assertEquals(0, decision.getRiskScore());
        Assert.assertEquals("LOW", decision.getRiskLevel());
        Assert.assertFalse(decision.isNeedInvestigation());
        Assert.assertTrue(decision.isFastTrack());
        Assert.assertTrue(decision.getAuditNotes().contains("赔付金额不超过 5000 元且材料齐全，进入小额快赔通道"));
    }

    /**
     * 重疾大额：赔款触达 50 万封顶线，风险分为 30（MEDIUM）
     */
    @Test
    public void shouldApplyCapForCriticalIllnessClaim() {
        ClaimRequest request = new ClaimRequest();
        request.setClaimNo("CLM20260826002");
        request.setPolicyNo("POL20240101002");
        request.setInsuredName("周静");
        request.setProductType("CRITICAL_ILLNESS");
        request.setHospitalLevel("THIRD_A");
        request.setClaimAmount(600000.0);
        request.setInvoiceAmount(580000.0);
        request.setDeductible(10000.0);
        request.setInsuredDays(800);
        request.setWaitingPeriodDays(90);
        request.setPriorClaimCount(1);
        request.setFraudFlagCount(0);
        request.setAccident(false);
        request.setDocumentComplete(true);

        ClaimDecision decision = service.audit(request);

        Assert.assertEquals("APPROVE", decision.getAuditResult());
        Assert.assertEquals(500000.0, decision.getPayableAmount(), 0.001);
        Assert.assertEquals(0.0, decision.getDeductibleAmount(), 0.001);
        Assert.assertEquals(1.0, decision.getReimburseRatio(), 0.0001);
        Assert.assertEquals(30, decision.getRiskScore());
        Assert.assertEquals("MEDIUM", decision.getRiskLevel());
        Assert.assertFalse(decision.isFastTrack());
        Assert.assertTrue(decision.getAuditNotes().contains("赔付金额触达产品封顶线，按封顶金额赔付"));
    }

    /**
     * 等待期内出险：已投保 20 天 < 等待期 30 天 → 拒赔且赔款为 0
     */
    @Test
    public void shouldRejectClaimWithinWaitingPeriod() {
        ClaimRequest request = new ClaimRequest();
        request.setClaimNo("CLM20260826003");
        request.setPolicyNo("POL20260801003");
        request.setInsuredName("吴磊");
        request.setProductType("MEDICAL");
        request.setHospitalLevel("THIRD_A");
        request.setClaimAmount(8000.0);
        request.setInvoiceAmount(8000.0);
        request.setDeductible(300.0);
        request.setInsuredDays(20);
        request.setWaitingPeriodDays(30);
        request.setPriorClaimCount(0);
        request.setFraudFlagCount(0);
        request.setAccident(false);
        request.setDocumentComplete(true);

        ClaimDecision decision = service.audit(request);

        Assert.assertEquals("REJECT", decision.getAuditResult());
        Assert.assertEquals(0.0, decision.getPayableAmount(), 0.001);
        Assert.assertFalse(decision.isFastTrack());
        Assert.assertTrue(decision.getAuditNotes().contains(
                "保单处于等待期（已投保 20 天 < 等待期 30 天），本次不承担赔付责任"));
    }

    /**
     * 风控标记：2 次标记 + 大额意外报案 → 转调查岗，风险分 80（HIGH）
     */
    @Test
    public void shouldRouteFlaggedClaimToInvestigation() {
        ClaimRequest request = new ClaimRequest();
        request.setClaimNo("CLM20260826004");
        request.setPolicyNo("POL20250101004");
        request.setInsuredName("郑凯");
        request.setProductType("ACCIDENT");
        request.setHospitalLevel("THIRD_A");
        request.setClaimAmount(80000.0);
        request.setInvoiceAmount(90000.0);
        request.setDeductible(500.0);
        request.setInsuredDays(300);
        request.setWaitingPeriodDays(30);
        request.setPriorClaimCount(2);
        request.setFraudFlagCount(2);
        request.setAccident(true);
        request.setDocumentComplete(true);

        ClaimDecision decision = service.audit(request);

        Assert.assertEquals("MANUAL_AUDIT", decision.getAuditResult());
        Assert.assertEquals(71775.0, decision.getPayableAmount(), 0.001);
        Assert.assertEquals(250.0, decision.getDeductibleAmount(), 0.001);
        Assert.assertEquals(0.9, decision.getReimburseRatio(), 0.0001);
        Assert.assertEquals(80, decision.getRiskScore());
        Assert.assertEquals("HIGH", decision.getRiskLevel());
        Assert.assertTrue(decision.isNeedInvestigation());
        Assert.assertFalse(decision.isFastTrack());
    }

    /**
     * 材料不全：车险意外小额，赔款 3600 元但材料缺失 → 转人工审核
     */
    @Test
    public void shouldRouteIncompleteDocumentClaimToManualAudit() {
        ClaimRequest request = new ClaimRequest();
        request.setClaimNo("CLM20260826005");
        request.setPolicyNo("POL20250201005");
        request.setInsuredName("何欣");
        request.setProductType("AUTO");
        request.setHospitalLevel("THIRD_A");
        request.setClaimAmount(4000.0);
        request.setInvoiceAmount(4000.0);
        request.setDeductible(0.0);
        request.setInsuredDays(500);
        request.setWaitingPeriodDays(30);
        request.setPriorClaimCount(0);
        request.setFraudFlagCount(0);
        request.setAccident(true);
        request.setDocumentComplete(false);

        ClaimDecision decision = service.audit(request);

        Assert.assertEquals("MANUAL_AUDIT", decision.getAuditResult());
        Assert.assertEquals(3600.0, decision.getPayableAmount(), 0.001);
        Assert.assertEquals(0.9, decision.getReimburseRatio(), 0.0001);
        Assert.assertFalse(decision.isFastTrack());
        Assert.assertTrue(decision.getAuditNotes().contains("理赔材料不齐全，转人工审核"));
    }

    /**
     * 未达免赔额：费用 500 元、免赔额 800 元 → 拒赔
     */
    @Test
    public void shouldRejectClaimBelowDeductible() {
        ClaimRequest request = new ClaimRequest();
        request.setClaimNo("CLM20260826006");
        request.setPolicyNo("POL20250401006");
        request.setInsuredName("林小雨");
        request.setProductType("MEDICAL");
        request.setHospitalLevel("COMMUNITY");
        request.setClaimAmount(500.0);
        request.setInvoiceAmount(500.0);
        request.setDeductible(800.0);
        request.setInsuredDays(200);
        request.setWaitingPeriodDays(30);
        request.setPriorClaimCount(0);
        request.setFraudFlagCount(0);
        request.setAccident(false);
        request.setDocumentComplete(true);

        ClaimDecision decision = service.audit(request);

        Assert.assertEquals("REJECT", decision.getAuditResult());
        Assert.assertEquals(0.0, decision.getPayableAmount(), 0.001);
        Assert.assertTrue(decision.getAuditNotes().contains("扣除免赔额后无可赔付金额，本次拒赔"));
    }
}
