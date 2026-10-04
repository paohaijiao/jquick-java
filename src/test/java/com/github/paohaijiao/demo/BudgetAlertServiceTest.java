package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.BudgetAlertDecision;
import com.github.paohaijiao.demo.model.BudgetStatus;
import com.github.paohaijiao.demo.service.BudgetAlertService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 37：部门预算超支预警
 * <p>
 * 6 组形态：健康预算直接放行、黄色关注轻度超支、橙色超前消耗升级、
 * 红色超支拦截与应急升级、已立项资本支出放行、预算执行严重滞后预警。
 */
public class BudgetAlertServiceTest {

    private BudgetAlertService service;

    @Before
    public void setUp() {
        BudgetAlertRules rules = JQuickJava.create()
                .createApi(BudgetAlertRules.class, "demo/budget-alert.xml");
        this.service = new BudgetAlertService(rules);
    }

    /**
     * 8 月占用 68%、节奏正常、预测不超支 → GREEN 直接放行
     */
    @Test
    public void shouldApproveWhenBudgetHealthy() {
        BudgetStatus s = base("D001", 1000000, 650000, 30000, 8, 950000);
        s.setSingleExpenseAmount(20000);
        s.setExpenseCategory("OPEX");

        BudgetAlertDecision d = service.evaluate(s);

        Assert.assertEquals("GREEN", d.getUtilizationLevel());
        Assert.assertEquals(68, d.getUtilizationPercent());
        Assert.assertEquals("NORMAL", d.getBurnStatus());
        Assert.assertEquals("OK", d.getForecastStatus());
        Assert.assertEquals(0.0, d.getOverrunAmount(), 0.0001);
        Assert.assertEquals("APPROVE", d.getSingleExpenseAction());
        Assert.assertEquals(10, d.getRiskScore());
        Assert.assertEquals(0, d.getAlerts().size());
    }

    /**
     * 占用 75% YELLOW、预测轻度超支 5 万、单笔 3% → 警示放行
     */
    @Test
    public void shouldWarnOnYellowUtilizationAndMildOverrun() {
        BudgetStatus s = base("D002", 1000000, 700000, 50000, 8, 1050000);
        s.setSingleExpenseAmount(30000);
        s.setExpenseCategory("ADMIN");

        BudgetAlertDecision d = service.evaluate(s);

        Assert.assertEquals("YELLOW", d.getUtilizationLevel());
        Assert.assertEquals(75, d.getUtilizationPercent());
        Assert.assertEquals("NORMAL", d.getBurnStatus());
        Assert.assertEquals("MILD", d.getForecastStatus());
        Assert.assertEquals(50000.0, d.getOverrunAmount(), 0.0001);
        Assert.assertEquals("APPROVE_WITH_WARNING", d.getSingleExpenseAction());
        Assert.assertEquals(40, d.getRiskScore());
    }

    /**
     * 8 月占用 93% ORANGE、支出超前、预测超支 8 万、单笔 6% → 升级
     */
    @Test
    public void shouldEscalateOnOrangeOverBurnLargeExpense() {
        BudgetStatus s = base("D003", 1000000, 880000, 50000, 8, 1080000);
        s.setSingleExpenseAmount(60000);
        s.setExpenseCategory("OPEX");

        BudgetAlertDecision d = service.evaluate(s);

        Assert.assertEquals("ORANGE", d.getUtilizationLevel());
        Assert.assertEquals(93, d.getUtilizationPercent());
        Assert.assertEquals("OVER_BURN", d.getBurnStatus());
        Assert.assertEquals("MILD", d.getForecastStatus());
        Assert.assertEquals(80000.0, d.getOverrunAmount(), 0.0001);
        Assert.assertEquals("ESCALATE", d.getSingleExpenseAction());
        Assert.assertEquals(80, d.getRiskScore());
        Assert.assertTrue(join(d).contains("超过 90% 橙色预警线"));
    }

    /**
     * 9 月占用 104% RED、预测严重超支 15%：未立项 CAPEX 拦截，应急 OPEX 升级
     */
    @Test
    public void shouldBlockRedBudgetButEscalateEmergency() {
        BudgetStatus capex = base("D004", 1000000, 980000, 60000, 9, 1150000);
        capex.setSingleExpenseAmount(50000);
        capex.setExpenseCategory("CAPEX");
        capex.setCapexApproved(false);
        BudgetAlertDecision d1 = service.evaluate(capex);
        Assert.assertEquals("RED", d1.getUtilizationLevel());
        Assert.assertEquals(104, d1.getUtilizationPercent());
        Assert.assertEquals("OVER_BURN", d1.getBurnStatus());
        Assert.assertEquals("SEVERE", d1.getForecastStatus());
        Assert.assertEquals(150000.0, d1.getOverrunAmount(), 0.0001);
        Assert.assertEquals("BLOCK", d1.getSingleExpenseAction());
        Assert.assertEquals(120, d1.getRiskScore());
        Assert.assertTrue(join(d1).contains("资本性支出未完成立项，系统拦截"));

        BudgetStatus emergency = base("D005", 1000000, 980000, 60000, 9, 1150000);
        emergency.setSingleExpenseAmount(80000);
        emergency.setExpenseCategory("OPEX");
        emergency.setEmergency(true);
        BudgetAlertDecision d2 = service.evaluate(emergency);
        Assert.assertEquals("ESCALATE", d2.getSingleExpenseAction());
        Assert.assertTrue(join(d2).contains("升级至财务总监/分管领导专项审批"));
    }

    /**
     * 6 月占用 40%、已立项 CAPEX 10 万 → 放行
     */
    @Test
    public void shouldApproveApprovedCapexInHealthyBudget() {
        BudgetStatus s = base("D006", 500000, 200000, 0, 6, 480000);
        s.setSingleExpenseAmount(100000);
        s.setExpenseCategory("CAPEX");
        s.setCapexApproved(true);

        BudgetAlertDecision d = service.evaluate(s);

        Assert.assertEquals("GREEN", d.getUtilizationLevel());
        Assert.assertEquals("NORMAL", d.getBurnStatus());
        Assert.assertEquals("APPROVE", d.getSingleExpenseAction());
        Assert.assertEquals(10, d.getRiskScore());
    }

    /**
     * 8 月仅支出 25% → UNDER_BURN 执行滞后预警
     */
    @Test
    public void shouldAlertUnderBurnExecutionLag() {
        BudgetStatus s = base("D007", 800000, 200000, 0, 8, 600000);
        s.setExpenseCategory("OPEX");

        BudgetAlertDecision d = service.evaluate(s);

        Assert.assertEquals("GREEN", d.getUtilizationLevel());
        Assert.assertEquals("UNDER_BURN", d.getBurnStatus());
        Assert.assertTrue(join(d).contains("预算执行严重滞后"));
    }

    private BudgetStatus base(String dept, double budget, double spent,
                              double committed, int month, double forecast) {
        BudgetStatus s = new BudgetStatus();
        s.setDeptCode(dept);
        s.setDeptName("测试部门");
        s.setAnnualBudget(budget);
        s.setActualSpent(spent);
        s.setCommittedAmount(committed);
        s.setMonth(month);
        s.setForecastYearEnd(forecast);
        s.setSingleExpenseAmount(0);
        s.setExpenseCategory("OPEX");
        s.setEmergency(false);
        s.setCapexApproved(false);
        return s;
    }

    private String join(BudgetAlertDecision d) {
        StringBuilder sb = new StringBuilder();
        for (String a : d.getAlerts()) {
            sb.append(a).append(';');
        }
        return sb.toString();
    }
}
