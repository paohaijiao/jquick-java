package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.AdmissionDecision;
import com.github.paohaijiao.demo.model.AlarmDecision;
import com.github.paohaijiao.demo.model.BankTransaction;
import com.github.paohaijiao.demo.model.BonusDecision;
import com.github.paohaijiao.demo.model.ClaimDecision;
import com.github.paohaijiao.demo.model.ClaimRequest;
import com.github.paohaijiao.demo.model.CouponDecision;
import com.github.paohaijiao.demo.model.CouponRequest;
import com.github.paohaijiao.demo.model.CreditApplication;
import com.github.paohaijiao.demo.model.DeviceAlarm;
import com.github.paohaijiao.demo.model.FraudDecision;
import com.github.paohaijiao.demo.model.InspectionBatch;
import com.github.paohaijiao.demo.model.InspectionDecision;
import com.github.paohaijiao.demo.model.InventoryItem;
import com.github.paohaijiao.demo.model.LoanAccount;
import com.github.paohaijiao.demo.model.MedicalClaim;
import com.github.paohaijiao.demo.model.PerformanceRecord;
import com.github.paohaijiao.demo.model.PricingDecision;
import com.github.paohaijiao.demo.model.ReimbursementDecision;
import com.github.paohaijiao.demo.model.Resume;
import com.github.paohaijiao.demo.model.ScreeningDecision;
import com.github.paohaijiao.demo.model.SlaDecision;
import com.github.paohaijiao.demo.model.SubsidyApplication;
import com.github.paohaijiao.demo.model.SubsidyDecision;
import com.github.paohaijiao.demo.model.TaxProfile;
import com.github.paohaijiao.demo.model.TaxRiskDecision;
import com.github.paohaijiao.demo.model.Ticket;
import com.github.paohaijiao.demo.model.WarningDecision;
import com.github.paohaijiao.demo.service.ClaimAuditService;
import com.github.paohaijiao.demo.service.CouponService;
import com.github.paohaijiao.demo.service.DeviceAlarmService;
import com.github.paohaijiao.demo.service.DynamicPricingService;
import com.github.paohaijiao.demo.service.MedicalReimbursementService;
import com.github.paohaijiao.demo.service.PerformanceService;
import com.github.paohaijiao.demo.service.PostLoanWarningService;
import com.github.paohaijiao.demo.service.QualityInspectionService;
import com.github.paohaijiao.demo.service.ResumeScreeningService;
import com.github.paohaijiao.demo.service.RiskAdmissionService;
import com.github.paohaijiao.demo.service.SubsidyService;
import com.github.paohaijiao.demo.service.TaxComplianceService;
import com.github.paohaijiao.demo.service.TicketSlaService;
import com.github.paohaijiao.demo.service.TransactionFraudService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 40：诉求矩阵「直接覆盖」独立入口回归 + 跨场景联合拦截。
 * <p>
 * 前 25 个用例对应 USE-CASES.md 矩阵中由 15 套生产样板直接承接的诉求
 * （诉求 1/2/4/5/7/9/11/12/13/15/17/19/20/22/24/25/28/33/34/35/38/39/42/45/47），
 * 每个诉求一个独立业务入口；最后一个用例演示场景 1 信贷准入与场景 15 交易反欺诈
 * 双引擎串联的联合拦截，构成全部 50 个独立业务场景入口。
 */
public class CoveredScenariosMatrixTest {

    private RiskAdmissionService riskAdmissionService;
    private PostLoanWarningService postLoanWarningService;
    private ClaimAuditService claimAuditService;
    private DynamicPricingService dynamicPricingService;
    private TicketSlaService ticketSlaService;
    private PerformanceService performanceService;
    private TaxComplianceService taxComplianceService;
    private DeviceAlarmService deviceAlarmService;
    private CouponService couponService;
    private SubsidyService subsidyService;
    private QualityInspectionService qualityInspectionService;
    private MedicalReimbursementService medicalReimbursementService;
    private ResumeScreeningService resumeScreeningService;
    private TransactionFraudService transactionFraudService;

    @Before
    public void setUp() {
        this.riskAdmissionService = new RiskAdmissionService(JQuickJava.create()
                .createApi(RiskAdmissionRules.class, "demo/risk-admission.xml"));
        this.postLoanWarningService = new PostLoanWarningService(JQuickJava.create()
                .createApi(PostLoanWarningRules.class, "demo/post-loan-warning.xml"));
        this.claimAuditService = new ClaimAuditService(JQuickJava.create()
                .createApi(ClaimAuditRules.class, "demo/claim-audit.xml"));
        this.dynamicPricingService = new DynamicPricingService(JQuickJava.create()
                .createApi(DynamicPricingRules.class, "demo/dynamic-pricing.xml"));
        this.ticketSlaService = new TicketSlaService(JQuickJava.create()
                .createApi(TicketSlaRules.class, "demo/ticket-sla.xml"));
        this.performanceService = new PerformanceService(JQuickJava.create()
                .createApi(PerformanceRules.class, "demo/performance.xml"));
        this.taxComplianceService = new TaxComplianceService(JQuickJava.create()
                .createApi(TaxComplianceRules.class, "demo/tax-compliance.xml"));
        this.deviceAlarmService = new DeviceAlarmService(JQuickJava.create()
                .createApi(DeviceAlarmRules.class, "demo/device-alarm.xml"));
        this.couponService = new CouponService(JQuickJava.create()
                .createApi(CouponRules.class, "demo/coupon.xml"));
        this.subsidyService = new SubsidyService(JQuickJava.create()
                .createApi(SubsidyRules.class, "demo/subsidy-eligibility.xml"));
        this.qualityInspectionService = new QualityInspectionService(JQuickJava.create()
                .createApi(QualityInspectionRules.class, "demo/quality-inspection.xml"));
        this.medicalReimbursementService = new MedicalReimbursementService(JQuickJava.create()
                .createApi(MedicalReimbursementRules.class, "demo/medical-reimbursement.xml"));
        this.resumeScreeningService = new ResumeScreeningService(JQuickJava.create()
                .createApi(ResumeScreeningRules.class, "demo/resume-screening.xml"));
        this.transactionFraudService = new TransactionFraudService(JQuickJava.create()
                .createApi(TransactionFraudRules.class, "demo/transaction-fraud.xml"));
    }

    /**
     * 诉求 1：个人信贷自动审批分级 —— 优质客户 PASS/A 级/足额授信
     */
    @Test
    public void req01_creditAdmissionPrimePass() {
        CreditApplication a = new CreditApplication();
        a.setApplicationId("M-REQ01");
        a.setApplicantName("张伟");
        a.setAge(35);
        a.setCompanyType("SOE");
        a.setEmployedMonths(84);
        a.setSocialSecurityMonths(60);
        a.setMonthlyIncome(30000.0);
        a.setMonthlyDebt(4500.0);
        a.setOverdueCount(0);
        a.setMaxOverdueDays(0);
        a.setCreditQueryCount3M(2);
        a.setLoanAmount(300000.0);
        a.setLoanTermMonths(36);
        a.setCollateralType("HOUSE");
        a.setCollateralValue(600000.0);
        a.setHasJudicialRecord(false);
        a.setCityTier("TIER_1");

        AdmissionDecision d = riskAdmissionService.evaluate(a);

        Assert.assertEquals("PASS", d.getAdmit());
        Assert.assertEquals("A", d.getRiskLevel());
        Assert.assertEquals(300000.0, d.getSuggestedLimit(), 0.001);
    }

    /**
     * 诉求 2：贷后风险预警 —— 未逾期账户正常监测
     */
    @Test
    public void req02_postLoanNormalWatch() {
        LoanAccount a = new LoanAccount();
        a.setLoanNo("M-REQ02");
        a.setCustomerName("陈静");
        a.setProductType("CONSUMER");
        a.setRemainingPrincipal(50000.0);
        a.setOverdueAmount(0.0);
        a.setOverdueDays(0);
        a.setBaseRate(0.08);
        a.setCollateralType("NONE");
        a.setCollateralValue(0.0);
        a.setContactSuccess(true);
        a.setLastContactDays(0);
        a.setOverdueHistoryCount(0);
        a.setWarningCount(0);

        WarningDecision d = postLoanWarningService.evaluate(a);

        Assert.assertEquals("BLUE", d.getWarningLevel());
        Assert.assertEquals(0, d.getWarningScore());
        Assert.assertTrue(d.getActions().contains("账户状态正常，无需催收"));
    }

    /**
     * 诉求 4：贷款反欺诈风险识别 —— 深夜境外大额交易 100 分拦截
     */
    @Test
    public void req04_antiFraudBlockOverseasNight() {
        BankTransaction t = txn("M-REQ04", 30000.0, 2, "ONLINE", true, true, false,
                6, 60000.0, true, true, false, 1, 400, 8000.0, false);

        FraudDecision d = transactionFraudService.evaluate(t);

        Assert.assertEquals(100, d.getRiskScore());
        Assert.assertEquals("HIGH", d.getRiskLevel());
        Assert.assertEquals("BLOCK", d.getAction());
        Assert.assertTrue(d.isNeedAlert());
    }

    /**
     * 诉求 5：信用卡进件审批分级 —— 边界客户转人工复核
     */
    @Test
    public void req05_cardApplicationManualReview() {
        CreditApplication a = new CreditApplication();
        a.setApplicationId("M-REQ05");
        a.setApplicantName("王芳");
        a.setAge(28);
        a.setCompanyType("PRIVATE");
        a.setEmployedMonths(20);
        a.setSocialSecurityMonths(12);
        a.setMonthlyIncome(15000.0);
        a.setMonthlyDebt(6900.0);
        a.setOverdueCount(1);
        a.setMaxOverdueDays(20);
        a.setCreditQueryCount3M(4);
        a.setLoanAmount(100000.0);
        a.setLoanTermMonths(24);
        a.setCollateralType("VEHICLE");
        a.setCollateralValue(130000.0);
        a.setHasJudicialRecord(false);
        a.setCityTier("TIER_2");

        AdmissionDecision d = riskAdmissionService.evaluate(a);

        Assert.assertEquals("MANUAL_REVIEW", d.getAdmit());
        Assert.assertEquals("C", d.getRiskLevel());
        Assert.assertEquals(57, d.getTotalScore());
    }

    /**
     * 诉求 7：保险理赔责任与赔付比例 —— 门诊小额快赔 85%
     */
    @Test
    public void req07_claimLiabilityAndRatio() {
        ClaimRequest r = new ClaimRequest();
        r.setClaimNo("M-REQ07");
        r.setPolicyNo("POL-M07");
        r.setInsuredName("刘洋");
        r.setProductType("MEDICAL");
        r.setHospitalLevel("THIRD_A");
        r.setClaimAmount(3200.0);
        r.setInvoiceAmount(3200.0);
        r.setDeductible(300.0);
        r.setInsuredDays(400);
        r.setWaitingPeriodDays(30);
        r.setPriorClaimCount(0);
        r.setFraudFlagCount(0);
        r.setAccident(false);
        r.setDocumentComplete(true);

        ClaimDecision d = claimAuditService.audit(r);

        Assert.assertEquals("APPROVE", d.getAuditResult());
        Assert.assertEquals(2465.0, d.getPayableAmount(), 0.001);
        Assert.assertEquals(0.85, d.getReimburseRatio(), 0.0001);
        Assert.assertTrue(d.isFastTrack());
    }

    /**
     * 诉求 9：逾期贷后分层催收 —— 95 天失联大额转诉讼
     */
    @Test
    public void req09_overdueCollectionLitigation() {
        LoanAccount a = new LoanAccount();
        a.setLoanNo("M-REQ09");
        a.setCustomerName("张强");
        a.setProductType("CONSUMER");
        a.setRemainingPrincipal(365000.0);
        a.setOverdueAmount(200000.0);
        a.setOverdueDays(95);
        a.setBaseRate(0.10);
        a.setCollateralType("NONE");
        a.setCollateralValue(0.0);
        a.setContactSuccess(false);
        a.setLastContactDays(30);
        a.setOverdueHistoryCount(0);
        a.setWarningCount(0);

        WarningDecision d = postLoanWarningService.evaluate(a);

        Assert.assertEquals("ORANGE", d.getWarningLevel());
        Assert.assertEquals(4, d.getOverdueStage());
        Assert.assertEquals("LITIGATION", d.getCollectChannel());
        Assert.assertTrue(d.isNeedLitigation());
    }

    /**
     * 诉求 11：银行卡交易风控 —— 日常小额交易放行
     */
    @Test
    public void req11_bankCardTransactionPass() {
        BankTransaction t = txn("M-REQ11", 200.0, 14, "POS", false, false, false,
                1, 200.0, false, false, true, 0, 400, 5000.0, false);

        FraudDecision d = transactionFraudService.evaluate(t);

        Assert.assertEquals(0, d.getRiskScore());
        Assert.assertEquals("LOW", d.getRiskLevel());
        Assert.assertEquals("PASS", d.getAction());
    }

    /**
     * 诉求 12：白名单/黑名单动态判定 —— 受信商户白名单短路放行；涉诉黑名单一票否决
     */
    @Test
    public void req12_whiteAndBlackListRouting() {
        BankTransaction white = txn("M-REQ12-W", 200.0, 14, "POS", false, false, false,
                1, 200.0, false, false, true, 0, 400, 5000.0, true);
        FraudDecision wd = transactionFraudService.evaluate(white);
        Assert.assertEquals("PASS", wd.getAction());
        Assert.assertTrue(wd.getIndicators().contains("受信商户白名单交易，直接放行"));

        CreditApplication black = new CreditApplication();
        black.setApplicationId("M-REQ12-B");
        black.setApplicantName("陈刚");
        black.setAge(38);
        black.setCompanyType("PRIVATE");
        black.setEmployedMonths(48);
        black.setSocialSecurityMonths(36);
        black.setMonthlyIncome(20000.0);
        black.setMonthlyDebt(3000.0);
        black.setOverdueCount(0);
        black.setMaxOverdueDays(0);
        black.setCreditQueryCount3M(3);
        black.setLoanAmount(150000.0);
        black.setLoanTermMonths(36);
        black.setCollateralType("HOUSE");
        black.setCollateralValue(300000.0);
        black.setHasJudicialRecord(true);
        black.setCityTier("TIER_2");
        AdmissionDecision bd = riskAdmissionService.evaluate(black);
        Assert.assertEquals("REJECT", bd.getAdmit());
        Assert.assertTrue(bd.getRejectReasons().contains("存在涉诉/被执行记录，触发一票否决"));
    }

    /**
     * 诉求 13：创业/人才补贴资格自动审核 —— 博士 A 类人才核定 40 万
     */
    @Test
    public void req13_talentSubsidyApproval() {
        SubsidyApplication app = subsidyApp("M-REQ13");
        app.setEducation("PHD");
        app.setAge(28);
        app.setLocalSocialSecurityMonths(48);
        app.setTalentCategory("A");
        app.setStartupRegistered(true);
        app.setStartupEmployees(12);

        SubsidyDecision d = subsidyService.review(app);

        Assert.assertEquals("QUALIFIED", d.getStage());
        Assert.assertEquals(110, d.getQualificationScore());
        Assert.assertEquals(400000.0, d.getSubsidyAmount(), 0.001);
    }

    /**
     * 诉求 15：政务办事材料完整性前置校验 —— 材料不齐退回补正
     */
    @Test
    public void req15_documentCompletenessCheck() {
        SubsidyApplication app = subsidyApp("M-REQ15");
        app.setEducation("PHD");
        app.setAge(30);
        app.setLocalSocialSecurityMonths(48);
        app.setTalentCategory("A");
        app.setStartupRegistered(false);
        app.setStartupEmployees(0);
        app.setDocumentsComplete(false);

        SubsidyDecision d = subsidyService.review(app);

        Assert.assertEquals("MANUAL_REVIEW", d.getStage());
        Assert.assertEquals(0.0, d.getSubsidyAmount(), 0.001);
        Assert.assertTrue(d.getReasons().contains("申报材料不齐全，退回补正后转人工复核"));
    }

    /**
     * 诉求 17：政府采购供应商准入与失信过滤 —— 失信记录一票否决
     */
    @Test
    public void req17_procurementCreditVeto() {
        SubsidyApplication app = subsidyApp("M-REQ17");
        app.setEducation("MASTER");
        app.setAge(30);
        app.setLocalSocialSecurityMonths(40);
        app.setTalentCategory("B");
        app.setStartupRegistered(false);
        app.setStartupEmployees(0);
        app.setCreditViolation(true);

        SubsidyDecision d = subsidyService.review(app);

        Assert.assertEquals("REJECTED", d.getStage());
        Assert.assertTrue(d.getReasons().contains("存在失信被执行记录，触发一票否决"));
    }

    /**
     * 诉求 19：企业信用等级自动评级 —— 合规良好企业 LOW/自查
     */
    @Test
    public void req19_enterpriseCreditRating() {
        TaxProfile p = new TaxProfile();
        p.setCompanyId("M-REQ19");
        p.setCompanyName("某科技股份有限公司");
        p.setTaxpayerType("GENERAL");
        p.setTaxPeriod("2026Q2");
        p.setLateCount(0);
        p.setMissingInvoice(0);
        p.setTaxBurdenRate(0.05);
        p.setIndustryAvgBurden(0.04);
        p.setRevenue(20000000.0);
        p.setInputTaxRatio(0.70);
        p.setHasAbnormalInvoice(false);
        p.setYearsSinceRegister(5);
        p.setCreditRating("A");

        TaxRiskDecision d = taxComplianceService.evaluate(p);

        Assert.assertEquals(0, d.getRiskScore());
        Assert.assertEquals("LOW", d.getRiskLevel());
        Assert.assertEquals("SELF_CHECK", d.getAuditLevel());
    }

    /**
     * 诉求 20：行政审批流程智能路由 —— 安全类 P0 工单 5 分钟首响、经理介入
     */
    @Test
    public void req20_approvalRoutingBySla() {
        Ticket t = new Ticket();
        t.setTicketNo("M-REQ20");
        t.setTitle("支付网关不可用");
        t.setPriority("P0");
        t.setCategory("INCIDENT");
        t.setCustomerLevel("VIP");
        t.setCreateChannel("PHONE");
        t.setSecurityRelated(true);
        t.setReopenedTimes(0);
        t.setAssigneeLoad(8);
        t.setHasSlaBreachHistory(false);
        t.setElapsedMinutes(20);

        SlaDecision d = ticketSlaService.evaluate(t);

        Assert.assertEquals("VIP", d.getSlaLevel());
        Assert.assertEquals(5, d.getResponseMinutes());
        Assert.assertEquals(10, d.getEscalateMinutes());
        Assert.assertTrue(d.isNeedManager());
    }

    /**
     * 诉求 22：应急突发事件等级自动分级 —— 92℃ 过热 CRITICAL/P0
     */
    @Test
    public void req22_emergencyLevelClassification() {
        DeviceAlarm a = new DeviceAlarm();
        a.setDeviceId("M-REQ22");
        a.setDeviceName("炼钢炉温控传感器");
        a.setDeviceType("SENSOR");
        a.setCriticality("HIGH");
        a.setTemperature(92.0);
        a.setCpuUsage(96);
        a.setMemoryUsage(70);
        a.setErrorCount(22);
        a.setConnectionLost(false);
        a.setLastHeartbeatSeconds(60);
        a.setBatteryLevel(80);
        a.setVibrationLevel(1.0);

        AlarmDecision d = deviceAlarmService.evaluate(a);

        Assert.assertEquals("CRITICAL", d.getAlarmLevel());
        Assert.assertEquals("P0", d.getDispatchPriority());
        Assert.assertEquals("ISOLATE", d.getAutoRemediation());
    }

    /**
     * 诉求 24：招投标资格预审两段式 —— 资质硬门槛淘汰 + 达标者评分入围
     */
    @Test
    public void req24_bidPrequalificationTwoStage() {
        Resume unqualified = resume("M-REQ24-U", "BACHELOR", "MASTER", "211", 5, 3, 4, 5);
        unqualified.setExpectedSalary(16000.0);
        unqualified.setSalaryMin(15000.0);
        unqualified.setSalaryMax(25000.0);
        unqualified.setCurrentCity("上海");
        unqualified.setJobCity("上海");
        unqualified.setWillingToRelocate(false);
        unqualified.setJobHoppingCount(1);
        unqualified.setGapMonths(5);
        unqualified.setCertified(true);
        unqualified.setEnglishLevel("CET6");
        ScreeningDecision d1 = resumeScreeningService.screen(unqualified);
        Assert.assertFalse(d1.isHardCheckPass());
        Assert.assertEquals("REJECT", d1.getResult());

        Resume qualified = resume("M-REQ24-Q", "MASTER", "MASTER", "985", 8, 3, 5, 5);
        qualified.setExpectedSalary(18000.0);
        qualified.setSalaryMin(15000.0);
        qualified.setSalaryMax(25000.0);
        qualified.setCurrentCity("上海");
        qualified.setJobCity("上海");
        qualified.setWillingToRelocate(false);
        qualified.setJobHoppingCount(0);
        qualified.setGapMonths(2);
        qualified.setCertified(true);
        qualified.setEnglishLevel("CET6");
        ScreeningDecision d2 = resumeScreeningService.screen(qualified);
        Assert.assertTrue(d2.isHardCheckPass());
        Assert.assertEquals("INTERVIEW", d2.getResult());
        Assert.assertEquals(90, d2.getTotalScore());
    }

    /**
     * 诉求 25：营销活动叠加/互斥控制 —— 新客券互斥、VIP 券可叠加
     */
    @Test
    public void req25_couponStackingControl() {
        CouponRequest n = couponReq("M-REQ25-N", "NORMAL", 88.0, 0, "APPAREL", 2, true);
        CouponDecision nd = couponService.issue(n);
        Assert.assertEquals("NEW_USER", nd.getCouponType());
        Assert.assertFalse(nd.isStackable());

        CouponRequest v = couponReq("M-REQ25-V", "VIP", 2500.0, 15, "DIGITAL", 900, false);
        v.setLastOrderDays(10);
        v.setAvgOrderAmount(800.0);
        CouponDecision vd = couponService.issue(v);
        Assert.assertEquals("VIP_EXCLUSIVE", vd.getCouponType());
        Assert.assertTrue(vd.isStackable());
    }

    /**
     * 诉求 28：渠道限价/最低价/价差管控 —— 折扣击穿毛利线时按保护价执行
     */
    @Test
    public void req28_priceFloorAndMarginProtection() {
        InventoryItem item = new InventoryItem();
        item.setSku("M-REQ28");
        item.setSkuName("滞销小家电");
        item.setCategory("家电");
        item.setStockAgeDays(120);
        item.setStockQty(100);
        item.setCostPrice(60.0);
        item.setCurrentPrice(100.0);
        item.setRecentSalesQty30d(10);
        item.setSeasonFactor("NORMAL");
        item.setPerishable(false);
        item.setWarehouseType("MAIN");
        item.setMarginFloor(0.20);

        PricingDecision d = dynamicPricingService.price(item);

        Assert.assertEquals(0.50, d.getFinalDiscount(), 0.001);
        Assert.assertEquals(72.0, d.getSuggestedPrice(), 0.001);
        Assert.assertTrue(d.getNotes().contains("清仓折扣触及最低毛利保护线，按保护价执行"));
    }

    /**
     * 诉求 33：产线质检多指标判定 —— 优质批次合格放行
     */
    @Test
    public void req33_productionQualityRelease() {
        InspectionBatch b = inspectionBatch("M-REQ33", 200, 0, 0, 1, 10.01, 10.0, 0.05, 97);

        InspectionDecision d = qualityInspectionService.inspect(b);

        Assert.assertEquals("ACCEPT", d.getQualityLevel());
        Assert.assertTrue(d.isReleaseAllowed());
        Assert.assertEquals("NEXT_PROCESS", d.getDisposition());
    }

    /**
     * 诉求 34：MES 工单优先级智能排序 —— P3 低优请求标准 SLA
     */
    @Test
    public void req34_workOrderPriorityMatrix() {
        Ticket t = new Ticket();
        t.setTicketNo("M-REQ34");
        t.setTitle("修改头像失败");
        t.setPriority("P3");
        t.setCategory("REQUEST");
        t.setCustomerLevel("NORMAL");
        t.setCreateChannel("APP");
        t.setSecurityRelated(false);
        t.setReopenedTimes(0);
        t.setAssigneeLoad(3);
        t.setHasSlaBreachHistory(false);
        t.setElapsedMinutes(100);

        SlaDecision d = ticketSlaService.evaluate(t);

        Assert.assertEquals("STANDARD", d.getSlaLevel());
        Assert.assertEquals(240, d.getResponseMinutes());
        Assert.assertEquals(7200, d.getResolveMinutes());
        Assert.assertFalse(d.isNeedEscalate());
    }

    /**
     * 诉求 35：生产设备健康度评分与故障预警 —— 资源紧张 22 分 MINOR，预警不派单
     */
    @Test
    public void req35_equipmentHealthScoring() {
        DeviceAlarm a = new DeviceAlarm();
        a.setDeviceId("M-REQ35");
        a.setDeviceName("图像识别节点");
        a.setDeviceType("CAMERA");
        a.setCriticality("MEDIUM");
        a.setTemperature(45.0);
        a.setCpuUsage(88);
        a.setMemoryUsage(92);
        a.setErrorCount(6);
        a.setConnectionLost(false);
        a.setLastHeartbeatSeconds(30);
        a.setBatteryLevel(60);
        a.setVibrationLevel(1.0);

        AlarmDecision d = deviceAlarmService.evaluate(a);

        Assert.assertEquals(22, d.getAlarmScore());
        Assert.assertEquals("MINOR", d.getAlarmLevel());
        Assert.assertFalse(d.isNeedDispatch());
        Assert.assertEquals("RATE_LIMIT", d.getAutoRemediation());
    }

    /**
     * 诉求 38：成品出入库合规与批次放行 —— 致命缺陷整批冻结不予放行
     */
    @Test
    public void req38_batchReleaseCompliance() {
        InspectionBatch b = inspectionBatch("M-REQ38", 100, 1, 0, 2, 10.02, 10.0, 0.05, 80);

        InspectionDecision d = qualityInspectionService.inspect(b);

        Assert.assertEquals("SCRAP", d.getQualityLevel());
        Assert.assertFalse(d.isReleaseAllowed());
        Assert.assertEquals("SCRAP_BIN", d.getDisposition());
    }

    /**
     * 诉求 39：医保报销项目/比例/合规校验 —— 职工三甲甲类报销 3780
     */
    @Test
    public void req39_medicalInsuranceReimbursement() {
        MedicalClaim c = new MedicalClaim();
        c.setClaimNo("M-REQ39");
        c.setPatientName("患者");
        c.setInsuranceType("EMPLOYEE");
        c.setHospitalLevel("THIRD_A");
        c.setMedicalCategory("DRUG_A");
        c.setTotalExpense(5000.0);
        c.setEmergency(false);
        c.setOffSite(false);
        c.setFiled(false);
        c.setAnnualAccumulated(0.0);

        ReimbursementDecision d = medicalReimbursementService.settle(c);

        Assert.assertEquals("APPROVE", d.getResult());
        Assert.assertEquals(0.9, d.getReimburseRatio(), 0.0001);
        Assert.assertEquals(3780.0, d.getPayableAmount(), 0.001);
    }

    /**
     * 诉求 42：员工绩效 KPI 加权计分与等级评定 —— S 级系数 2.0
     */
    @Test
    public void req42_kpiWeightedGrading() {
        PerformanceRecord r = new PerformanceRecord();
        r.setEmployeeId("M-REQ42");
        r.setEmployeeName("张伟");
        r.setDept("销售部");
        r.setKpiScore(95.0);
        r.setProjectScore(90.0);
        r.setTeamScore(85.0);
        r.setAttendanceScore(96.0);
        r.setManager(false);
        r.setHasViolation(false);
        r.setSalesAmount(600000.0);
        r.setBaseSalary(20000.0);

        BonusDecision d = performanceService.evaluate(r);

        Assert.assertEquals(92.65, d.getTotalScore(), 0.001);
        Assert.assertEquals("S", d.getGrade());
        Assert.assertEquals(2.0, d.getBonusCoefficient(), 0.001);
        Assert.assertEquals(40000.0, d.getBonusAmount(), 0.001);
    }

    /**
     * 诉求 45：招聘简历智能初筛 —— 硕士 985 全匹配强烈推荐
     */
    @Test
    public void req45_resumeScreeningStrong() {
        Resume r = resume("M-REQ45", "MASTER", "MASTER", "985", 8, 3, 5, 5);
        r.setExpectedSalary(18000.0);
        r.setSalaryMin(15000.0);
        r.setSalaryMax(25000.0);
        r.setCurrentCity("上海");
        r.setJobCity("上海");
        r.setWillingToRelocate(false);
        r.setJobHoppingCount(0);
        r.setGapMonths(2);
        r.setCertified(true);
        r.setEnglishLevel("CET6");

        ScreeningDecision d = resumeScreeningService.screen(r);

        Assert.assertTrue(d.isHardCheckPass());
        Assert.assertEquals("STRONG", d.getRecommendLevel());
        Assert.assertEquals("INTERVIEW", d.getResult());
    }

    /**
     * 诉求 47：IoT 传感器数据阈值告警分级 —— 网关掉连 51 分 MAJOR/P1
     */
    @Test
    public void req47_iotSensorThresholdAlarm() {
        DeviceAlarm a = new DeviceAlarm();
        a.setDeviceId("M-REQ47");
        a.setDeviceName("园区闸机网关");
        a.setDeviceType("GATEWAY");
        a.setCriticality("HIGH");
        a.setTemperature(40.0);
        a.setCpuUsage(50);
        a.setMemoryUsage(50);
        a.setErrorCount(10);
        a.setConnectionLost(true);
        a.setLastHeartbeatSeconds(600);
        a.setBatteryLevel(15);
        a.setVibrationLevel(0.5);

        AlarmDecision d = deviceAlarmService.evaluate(a);

        Assert.assertEquals(51, d.getAlarmScore());
        Assert.assertEquals("MAJOR", d.getAlarmLevel());
        Assert.assertEquals("NETWORK", d.getAlarmType());
        Assert.assertEquals("P1", d.getDispatchPriority());
    }

    /**
     * 跨场景组合：场景 15 交易反欺诈 + 场景 1 信贷准入双引擎串联联合拦截。
     * 同一客户深夜境外大额交易被反欺诈拦截、信贷画像命中高风险 → 联合决策拒绝放款
     */
    @Test
    public void req50_crossScenarioAdmissionAndFraudJointBlock() {
        BankTransaction txn = txn("M-REQ50-T", 30000.0, 2, "ONLINE", true, true, false,
                6, 60000.0, true, true, false, 1, 400, 8000.0, false);
        FraudDecision fraud = transactionFraudService.evaluate(txn);

        CreditApplication app = new CreditApplication();
        app.setApplicationId("M-REQ50-C");
        app.setApplicantName("李强");
        app.setAge(31);
        app.setCompanyType("SELF_EMPLOYED");
        app.setEmployedMonths(8);
        app.setSocialSecurityMonths(0);
        app.setMonthlyIncome(12000.0);
        app.setMonthlyDebt(9000.0);
        app.setOverdueCount(6);
        app.setMaxOverdueDays(120);
        app.setCreditQueryCount3M(12);
        app.setLoanAmount(200000.0);
        app.setLoanTermMonths(24);
        app.setCollateralType("NONE");
        app.setCollateralValue(0.0);
        app.setHasJudicialRecord(false);
        app.setCityTier("TIER_3");
        AdmissionDecision admission = riskAdmissionService.evaluate(app);

        boolean fraudBlocked = "BLOCK".equals(fraud.getAction());
        boolean creditRejected = "REJECT".equals(admission.getAdmit());
        Assert.assertTrue(fraudBlocked);
        Assert.assertTrue(creditRejected);
        Assert.assertEquals(100, fraud.getRiskScore());
        Assert.assertEquals("D", admission.getRiskLevel());
        Assert.assertTrue(fraudBlocked || creditRejected);
        Assert.assertEquals("JOINT_REJECT", fraudBlocked && creditRejected
                ? "JOINT_REJECT" : "SINGLE_REJECT");
    }

    private BankTransaction txn(String txnNo, double amount, int hour, String channel,
                                boolean overseas, boolean crossCity, boolean travelMode,
                                int count1h, double amount1h, boolean newMerchant,
                                boolean deviceChanged, boolean cardPresent, int failCount24h,
                                int accountAgeDays, double historicalMax, boolean trustedMerchant) {
        BankTransaction t = new BankTransaction();
        t.setTxnNo(txnNo);
        t.setCardNo("6222 **** **** 0001");
        t.setAmount(amount);
        t.setTxnHour(hour);
        t.setChannel(channel);
        t.setOverseas(overseas);
        t.setCrossCity(crossCity);
        t.setTravelMode(travelMode);
        t.setTxnCount1h(count1h);
        t.setTxnAmount1h(amount1h);
        t.setNewMerchant(newMerchant);
        t.setDeviceChanged(deviceChanged);
        t.setCardPresent(cardPresent);
        t.setFailCount24h(failCount24h);
        t.setAccountAgeDays(accountAgeDays);
        t.setHistoricalMaxAmount(historicalMax);
        t.setTrustedMerchant(trustedMerchant);
        return t;
    }

    private SubsidyApplication subsidyApp(String applyNo) {
        SubsidyApplication app = new SubsidyApplication();
        app.setApplyNo(applyNo);
        app.setApplicantName("申请人");
        app.setSubsidyType("SETTLEMENT");
        app.setCreditViolation(false);
        app.setCriminalRecord(false);
        app.setReceivedSimilarSubsidy(false);
        app.setDocumentsComplete(true);
        return app;
    }

    private CouponRequest couponReq(String userId, String level, double orderAmount,
                                    int orderCount, String category, int joinDays, boolean newUser) {
        CouponRequest r = new CouponRequest();
        r.setUserId(userId);
        r.setMemberLevel(level);
        r.setOrderAmount(orderAmount);
        r.setOrderCount(orderCount);
        r.setCategory(category);
        r.setJoinDays(joinDays);
        r.setLastOrderDays(0);
        r.setAvgOrderAmount(0.0);
        r.setNewUser(newUser);
        r.setHasUsedCoupon(false);
        r.setCampaignActive(false);
        r.setStockLeft(1000);
        r.setChannel("APP");
        r.setRiskFlag(false);
        return r;
    }

    private InspectionBatch inspectionBatch(String no, int sampleSize, int critical,
                                            int major, int minor, double measured,
                                            double target, double tolerance, int surface) {
        InspectionBatch b = new InspectionBatch();
        b.setBatchNo(no);
        b.setProductCode("P-001");
        b.setProductName("精密轴套");
        b.setWorkshop("一号车间");
        b.setSampleSize(sampleSize);
        b.setCriticalDefects(critical);
        b.setMajorDefects(major);
        b.setMinorDefects(minor);
        b.setMeasuredSize(measured);
        b.setTargetSize(target);
        b.setTolerance(tolerance);
        b.setSurfaceScore(surface);
        b.setEquipmentStatus("NORMAL");
        b.setFirstArticle(false);
        return b;
    }

    private Resume resume(String name, String education, String requiredEducation, String schoolTier,
                          int years, int requiredYears, int matched, int required) {
        Resume r = new Resume();
        r.setCandidateName(name);
        r.setPositionName("Java 开发工程师");
        r.setEducationLevel(education);
        r.setRequiredEducation(requiredEducation);
        r.setSchoolTier(schoolTier);
        r.setYearsExperience(years);
        r.setRequiredYears(requiredYears);
        r.setSkillMatchCount(matched);
        r.setRequiredSkillCount(required);
        r.setChannel("ONLINE");
        return r;
    }
}
