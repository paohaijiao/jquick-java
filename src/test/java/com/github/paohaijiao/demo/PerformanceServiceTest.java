package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.BonusDecision;
import com.github.paohaijiao.demo.model.PerformanceRecord;
import com.github.paohaijiao.demo.service.PerformanceService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 6 生产级样板：绩效等级与奖金核算
 * <p>
 * 覆盖 6 组绩效形态：优秀员工、低绩效违规、管理者达标、中等绩效、
 * 不达标待改进、优秀但有违规。
 */
public class PerformanceServiceTest {

    private PerformanceService service;

    @Before
    public void setUp() {
        PerformanceRules rules = JQuickJava.create()
                .createApi(PerformanceRules.class, "demo/performance.xml");
        this.service = new PerformanceService(rules);
    }

    /**
     * 优秀员工：四维均优、销售额达标 → S 级、系数 2.0、奖金翻倍
     */
    @Test
    public void shouldRewardTopPerformer() {
        PerformanceRecord record = new PerformanceRecord();
        record.setEmployeeId("EMP001");
        record.setEmployeeName("张伟");
        record.setDept("销售部");
        record.setKpiScore(95.0);
        record.setProjectScore(90.0);
        record.setTeamScore(85.0);
        record.setAttendanceScore(96.0);
        record.setManager(false);
        record.setHasViolation(false);
        record.setSalesAmount(600000.0);
        record.setBaseSalary(20000.0);

        BonusDecision decision = service.evaluate(record);

        Assert.assertEquals(92.65, decision.getTotalScore(), 0.001);
        Assert.assertEquals("S", decision.getGrade());
        Assert.assertEquals(2.0, decision.getBonusCoefficient(), 0.001);
        Assert.assertEquals(40000.0, decision.getBonusAmount(), 0.001);
        Assert.assertEquals(3, decision.getAttendanceLevel());
        Assert.assertTrue(decision.isPromotionEligible());
        Assert.assertFalse(decision.isNeedPip());
        Assert.assertTrue(decision.getRemarks().contains("绩效等级 S，优先纳入年度评优与晋升池"));
    }

    /**
     * 低绩效违规：总分 51.25（D 级）、违规扣减 0.3 → 系数仅 0.2、进入 PIP
     */
    @Test
    public void shouldPenalizeLowPerformerWithViolation() {
        PerformanceRecord record = new PerformanceRecord();
        record.setEmployeeId("EMP002");
        record.setEmployeeName("李强");
        record.setDept("运营部");
        record.setKpiScore(45.0);
        record.setProjectScore(50.0);
        record.setTeamScore(55.0);
        record.setAttendanceScore(70.0);
        record.setManager(false);
        record.setHasViolation(true);
        record.setSalesAmount(100000.0);
        record.setBaseSalary(10000.0);

        BonusDecision decision = service.evaluate(record);

        Assert.assertEquals(51.25, decision.getTotalScore(), 0.001);
        Assert.assertEquals("D", decision.getGrade());
        Assert.assertEquals(0.2, decision.getBonusCoefficient(), 0.001);
        Assert.assertEquals(2000.0, decision.getBonusAmount(), 0.001);
        Assert.assertEquals(0, decision.getAttendanceLevel());
        Assert.assertFalse(decision.isPromotionEligible());
        Assert.assertTrue(decision.isNeedPip());
    }

    /**
     * 管理者达标：总分 85.0（A 级）、管理加成 0.2、销售激励 0.1 → 系数 1.7
     */
    @Test
    public void shouldApplyManagerAndSalesBonus() {
        PerformanceRecord record = new PerformanceRecord();
        record.setEmployeeId("EMP003");
        record.setEmployeeName("王芳");
        record.setDept("销售部");
        record.setKpiScore(82.0);
        record.setProjectScore(85.0);
        record.setTeamScore(88.0);
        record.setAttendanceScore(92.0);
        record.setManager(true);
        record.setHasViolation(false);
        record.setSalesAmount(300000.0);
        record.setBaseSalary(30000.0);

        BonusDecision decision = service.evaluate(record);

        Assert.assertEquals(85.0, decision.getTotalScore(), 0.001);
        Assert.assertEquals("A", decision.getGrade());
        Assert.assertEquals(1.7, decision.getBonusCoefficient(), 0.001);
        Assert.assertEquals(51000.0, decision.getBonusAmount(), 0.001);
        Assert.assertEquals(2, decision.getAttendanceLevel());
        Assert.assertTrue(decision.isPromotionEligible());
        Assert.assertFalse(decision.isNeedPip());
    }

    /**
     * 中等绩效：总分 72.35（B 级）、销售激励 0.1 → 系数 1.2，无晋升无 PIP
     */
    @Test
    public void shouldSettleAveragePerformer() {
        PerformanceRecord record = new PerformanceRecord();
        record.setEmployeeId("EMP004");
        record.setEmployeeName("赵敏");
        record.setDept("市场部");
        record.setKpiScore(70.0);
        record.setProjectScore(72.0);
        record.setTeamScore(68.0);
        record.setAttendanceScore(85.0);
        record.setManager(false);
        record.setHasViolation(false);
        record.setSalesAmount(250000.0);
        record.setBaseSalary(15000.0);

        BonusDecision decision = service.evaluate(record);

        Assert.assertEquals(72.35, decision.getTotalScore(), 0.001);
        Assert.assertEquals("B", decision.getGrade());
        Assert.assertEquals(1.2, decision.getBonusCoefficient(), 0.001);
        Assert.assertEquals(18000.0, decision.getBonusAmount(), 0.001);
        Assert.assertEquals(1, decision.getAttendanceLevel());
        Assert.assertFalse(decision.isPromotionEligible());
        Assert.assertFalse(decision.isNeedPip());
    }

    /**
     * 不达标待改进：总分 56.95（D 级）→ 系数 0.5、进入 PIP、考勤预警
     */
    @Test
    public void shouldRouteUnderperformerToPip() {
        PerformanceRecord record = new PerformanceRecord();
        record.setEmployeeId("EMP005");
        record.setEmployeeName("陈静");
        record.setDept("客服部");
        record.setKpiScore(52.0);
        record.setProjectScore(55.0);
        record.setTeamScore(58.0);
        record.setAttendanceScore(75.0);
        record.setManager(false);
        record.setHasViolation(false);
        record.setSalesAmount(60000.0);
        record.setBaseSalary(12000.0);

        BonusDecision decision = service.evaluate(record);

        Assert.assertEquals(56.95, decision.getTotalScore(), 0.001);
        Assert.assertEquals("D", decision.getGrade());
        Assert.assertEquals(0.5, decision.getBonusCoefficient(), 0.001);
        Assert.assertEquals(6000.0, decision.getBonusAmount(), 0.001);
        Assert.assertEquals(0, decision.getAttendanceLevel());
        Assert.assertTrue(decision.isNeedPip());
        Assert.assertTrue(decision.getRemarks().contains("考勤得分低于 80，请关注出勤情况"));
    }

    /**
     * 优秀但有违规：总分 87.3（A 级）但违规 → 无晋升资格、系数被扣减
     */
    @Test
    public void shouldBlockPromotionForViolation() {
        PerformanceRecord record = new PerformanceRecord();
        record.setEmployeeId("EMP006");
        record.setEmployeeName("周涛");
        record.setDept("技术部");
        record.setKpiScore(88.0);
        record.setProjectScore(86.0);
        record.setTeamScore(84.0);
        record.setAttendanceScore(90.0);
        record.setManager(false);
        record.setHasViolation(true);
        record.setSalesAmount(520000.0);
        record.setBaseSalary(25000.0);

        BonusDecision decision = service.evaluate(record);

        Assert.assertEquals(87.3, decision.getTotalScore(), 0.001);
        Assert.assertEquals("A", decision.getGrade());
        Assert.assertEquals(1.3, decision.getBonusCoefficient(), 0.001);
        Assert.assertEquals(32500.0, decision.getBonusAmount(), 0.001);
        Assert.assertFalse(decision.isPromotionEligible());
        Assert.assertTrue(decision.getRemarks().contains("存在违规记录，奖金系数已扣减 0.3"));
    }
}
