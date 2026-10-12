package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.WorkshopEnergyAlert;
import com.github.paohaijiao.demo.model.WorkshopEnergyRecord;
import com.github.paohaijiao.demo.service.WorkshopEnergyAlertService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 32：车间能耗异常判定与超标预警
 * <p>
 * 6 组班次形态：全要素正常、用电 18% 预警叠加温度燃气异常、用电 37%+单耗浪费+需量紧急、
 * 停线空转紧急、用水泄漏紧急、计量离线紧急与需量 8% 预警。
 */
public class WorkshopEnergyAlertServiceTest {

    private WorkshopEnergyAlertService service;

    @Before
    public void setUp() {
        WorkshopEnergyRules rules = JQuickJava.create()
                .createApi(WorkshopEnergyRules.class, "demo/workshop-energy.xml");
        this.service = new WorkshopEnergyAlertService(rules);
    }

    /**
     * 用电、单耗、需量、用水全部贴合基准 → NORMAL
     */
    @Test
    public void shouldStayNormalWhenAllMetricsOnBaseline() {
        WorkshopEnergyRecord r = base("WS01");
        r.setTotalKwh(800);
        r.setBaselineKwh(800);
        r.setOutputQty(100);
        r.setWaterUsageTon(30);
        r.setWaterBaselineTon(30);

        WorkshopEnergyAlert a = service.evaluate(r);

        Assert.assertEquals("NORMAL", a.getAlertLevel());
        Assert.assertEquals(0, a.getPowerDeviationPercent());
        Assert.assertEquals("NORMAL", a.getEfficiencyLevel());
        Assert.assertEquals(0, a.getSignals().size());
    }

    /**
     * 用电 +18%、恒温 28℃、燃气压力异常 → WARNING，三信号齐备
     */
    @Test
    public void shouldWarnOnPowerDeviationAndUtilityIssues() {
        WorkshopEnergyRecord r = base("WS02");
        r.setTotalKwh(950);
        r.setBaselineKwh(800);
        r.setOutputQty(120);
        r.setTemperature(28);
        r.setGasPressureOk(false);

        WorkshopEnergyAlert a = service.evaluate(r);

        Assert.assertEquals("WARNING", a.getAlertLevel());
        Assert.assertEquals(18, a.getPowerDeviationPercent());
        Assert.assertTrue(a.getSignals().contains("燃气压力异常，可能影响加热炉工艺稳定性"));
        Assert.assertTrue(a.getSignals().get(0).contains("用电同比偏高 18%"));
        Assert.assertTrue(a.getSignals().contains(
                "恒温车间实测 28℃，超出 18-26℃ 工艺区间，检查空调系统"));
    }

    /**
     * 用电 +37%、单耗浪费、需量超 20% → CRITICAL
     */
    @Test
    public void shouldRaiseCriticalForWasteAndDemandOverrun() {
        WorkshopEnergyRecord r = base("WS03");
        r.setTotalKwh(1100);
        r.setBaselineKwh(800);
        r.setOutputQty(100);
        r.setMaxDemandKw(600);
        r.setDemandContractKw(500);

        WorkshopEnergyAlert a = service.evaluate(r);

        Assert.assertEquals("CRITICAL", a.getAlertLevel());
        Assert.assertEquals(37, a.getPowerDeviationPercent());
        Assert.assertEquals(20, a.getDemandExcessPercent());
        Assert.assertEquals("WASTE", a.getEfficiencyLevel());
        Assert.assertTrue(a.getSignals().contains("单位产品电耗超标准 20%，能效评级 WASTE"));
        Assert.assertTrue(a.getSignals().get(2).contains("最大需量超合同 20%"));
    }

    /**
     * 停线仍耗电 200kWh（超基准 10%）→ 空转/偷漏电路径紧急告警
     */
    @Test
    public void shouldRaiseCriticalForStoppedLineEnergyUse() {
        WorkshopEnergyRecord r = base("WS04");
        r.setTotalKwh(200);
        r.setBaselineKwh(800);
        r.setProductionLineStatus("STOPPED");
        r.setOutputQty(0);

        WorkshopEnergyAlert a = service.evaluate(r);

        Assert.assertEquals("CRITICAL", a.getAlertLevel());
        Assert.assertEquals("NORMAL", a.getEfficiencyLevel());
        Assert.assertTrue(a.getSignals().get(0).contains("产线已停线但仍有 200kWh 用电"));
    }

    /**
     * 用水从 30 吨飙到 80 吨（+166%）→ 管网泄漏紧急告警
     */
    @Test
    public void shouldRaiseCriticalForWaterLeak() {
        WorkshopEnergyRecord r = base("WS05");
        r.setTotalKwh(800);
        r.setBaselineKwh(800);
        r.setOutputQty(100);
        r.setWaterUsageTon(80);
        r.setWaterBaselineTon(30);

        WorkshopEnergyAlert a = service.evaluate(r);

        Assert.assertEquals("CRITICAL", a.getAlertLevel());
        Assert.assertEquals(166, a.getWaterDeviationPercent());
        Assert.assertTrue(a.getSignals().get(0).contains("用水同比偏高 166%，疑似供水管网泄漏"));
    }

    /**
     * 计量表离线直接 CRITICAL；最大需量超合同 8% 但未到 10% 紧急线 → WARNING
     */
    @Test
    public void shouldHandleMeterOfflineAndNearDemandLimit() {
        WorkshopEnergyRecord offline = base("WS06");
        offline.setMeterOnline(false);
        WorkshopEnergyAlert a1 = service.evaluate(offline);
        Assert.assertEquals("CRITICAL", a1.getAlertLevel());
        Assert.assertTrue(a1.getSignals().get(0).contains("能耗计量表离线，数据采集中断"));

        WorkshopEnergyRecord nearLimit = base("WS07");
        nearLimit.setTotalKwh(800);
        nearLimit.setBaselineKwh(800);
        nearLimit.setOutputQty(100);
        nearLimit.setMaxDemandKw(540);
        nearLimit.setDemandContractKw(500);
        WorkshopEnergyAlert a2 = service.evaluate(nearLimit);
        Assert.assertEquals("WARNING", a2.getAlertLevel());
        Assert.assertEquals(8, a2.getDemandExcessPercent());
        Assert.assertTrue(a2.getSignals().contains("最大需量超合同 8%，接近加价线"));
    }

    private WorkshopEnergyRecord base(String id) {
        WorkshopEnergyRecord r = new WorkshopEnergyRecord();
        r.setWorkshopId(id);
        r.setWorkshopName("一号车间");
        r.setShift("DAY");
        r.setTotalKwh(800);
        r.setBaselineKwh(800);
        r.setOutputQty(100);
        r.setStandardKwhPerUnit(8);
        r.setWaterUsageTon(30);
        r.setWaterBaselineTon(30);
        r.setMaxDemandKw(400);
        r.setDemandContractKw(500);
        r.setTempControlled(true);
        r.setTemperature(22);
        r.setGasPressureOk(true);
        r.setMeterOnline(true);
        r.setProductionLineStatus("RUNNING");
        return r;
    }
}
