package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.HealthScreeningDecision;
import com.github.paohaijiao.demo.model.HealthScreeningReport;
import com.github.paohaijiao.demo.service.HealthScreeningService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 33：体检指标异常筛查与风险标注
 * <p>
 * 6 组形态：全指标正常、四项临界关注、高血压合并吸烟、糖尿病危急合并家族史、
 * 多器官危急值、心动过缓与临界恢复分叉。
 */
public class HealthScreeningServiceTest {

    private HealthScreeningService service;

    @Before
    public void setUp() {
        HealthScreeningRules rules = JQuickJava.create()
                .createApi(HealthScreeningRules.class, "demo/health-screening.xml");
        this.service = new HealthScreeningService(rules);
    }

    /**
     * 年轻男性全部指标正常 → 年度常规体检
     */
    @Test
    public void shouldReturnNormalForHealthyYoungMan() {
        HealthScreeningReport r = base("RPT001", "M", 28);
        r.setSystolicBp(115);
        r.setDiastolicBp(75);
        r.setFastingGlucose(5.0);
        r.setTotalCholesterol(4.5);
        r.setBmi(22);
        r.setHeartRate(70);
        r.setAlt(25);
        r.setCreatinine(70);

        HealthScreeningDecision d = service.screen(r);

        Assert.assertEquals("NORMAL", d.getHighestLevel());
        Assert.assertEquals(0, d.getAbnormalCount());
        Assert.assertEquals("全科/健康管理科", d.getFollowUpDepartment());
        Assert.assertTrue(d.getAdvice().get(0).contains("各项指标正常"));
    }

    /**
     * 血压、血糖、胆固醇、BMI 四项临界 → ATTENTION，生活方式干预
     */
    @Test
    public void shouldMarkFourBorderlineMetrics() {
        HealthScreeningReport r = base("RPT002", "M", 42);
        r.setSystolicBp(132);
        r.setDiastolicBp(86);
        r.setFastingGlucose(5.8);
        r.setTotalCholesterol(5.5);
        r.setBmi(24.5);
        r.setHeartRate(72);
        r.setAlt(30);
        r.setCreatinine(85);

        HealthScreeningDecision d = service.screen(r);

        Assert.assertEquals("ATTENTION", d.getHighestLevel());
        Assert.assertEquals(4, d.getAbnormalCount());
        Assert.assertEquals(0, d.getHighCount());
        Assert.assertTrue(d.getAdvice().get(0).startsWith("【关注】血压"));
        Assert.assertTrue(d.getAdvice().get(d.getAdvice().size() - 1).contains("生活方式干预"));
    }

    /**
     * 血压 152/96、胆固醇 6.5、吸烟史 → 心血管高风险，2 周内心内科
     */
    @Test
    public void shouldTagCardioRiskForHypertensiveSmoker() {
        HealthScreeningReport r = base("RPT003", "M", 51);
        r.setSystolicBp(152);
        r.setDiastolicBp(96);
        r.setFastingGlucose(5.2);
        r.setTotalCholesterol(6.5);
        r.setBmi(27);
        r.setHeartRate(82);
        r.setAlt(35);
        r.setCreatinine(90);
        r.setSmokingHistory(true);

        HealthScreeningDecision d = service.screen(r);

        Assert.assertEquals("HIGH", d.getHighestLevel());
        Assert.assertEquals(3, d.getAbnormalCount());
        Assert.assertEquals(2, d.getHighCount());
        Assert.assertTrue(d.getRiskTags().contains("心血管高风险（血压异常合并吸烟史）"));
        Assert.assertEquals("心血管内科", d.getFollowUpDepartment());
        Assert.assertTrue(d.getAdvice().get(d.getAdvice().size() - 1).contains("2 周内"));
    }

    /**
     * 空腹血糖 7.2 危急 + 家族史 → 糖尿病高危，3 日内内分泌科
     */
    @Test
    public void shouldFlagCriticalGlucoseWithFamilyHistory() {
        HealthScreeningReport r = base("RPT004", "F", 47);
        r.setSystolicBp(125);
        r.setDiastolicBp(80);
        r.setFastingGlucose(7.2);
        r.setTotalCholesterol(5.0);
        r.setBmi(25.5);
        r.setHeartRate(75);
        r.setAlt(30);
        r.setCreatinine(72);
        r.setFamilyHistory(true);

        HealthScreeningDecision d = service.screen(r);

        Assert.assertEquals("CRITICAL", d.getHighestLevel());
        Assert.assertEquals(1, d.getCriticalCount());
        Assert.assertTrue(d.getRiskTags().contains("糖尿病高危人群（空腹血糖异常合并家族史）"));
        Assert.assertEquals("内分泌科", d.getFollowUpDepartment());
        Assert.assertTrue(d.getAdvice().get(d.getAdvice().size() - 1).contains("3 日内"));
    }

    /**
     * 血压 182/112、血糖 8.1、ALT 130、肌酐 130、心率 105 → 多器官危急，首诊心内科
     */
    @Test
    public void shouldPrioritizeCardiologyForMultipleCriticalValues() {
        HealthScreeningReport r = base("RPT005", "M", 58);
        r.setSystolicBp(182);
        r.setDiastolicBp(112);
        r.setFastingGlucose(8.1);
        r.setTotalCholesterol(4.8);
        r.setBmi(26);
        r.setHeartRate(105);
        r.setAlt(130);
        r.setCreatinine(130);

        HealthScreeningDecision d = service.screen(r);

        Assert.assertEquals("CRITICAL", d.getHighestLevel());
        Assert.assertEquals(2, d.getCriticalCount());
        Assert.assertEquals(3, d.getHighCount());
        Assert.assertEquals("心血管内科", d.getFollowUpDepartment());
        Assert.assertTrue(d.getAdvice().get(0).contains("立即急诊排查高血压急症"));
        Assert.assertTrue(d.getAdvice().contains("【异常】谷丙转氨酶 130U/L：HIGH。消化/肝病科排查肝炎、脂肪肝等病因"));
    }

    /**
     * 心率 46 为 HIGH 首诊心内科；恢复到 54 后整体仅 BMI 消瘦关注
     */
    @Test
    public void shouldHandleBradycardiaAndRecovery() {
        HealthScreeningReport r = base("RPT006", "F", 34);
        r.setSystolicBp(118);
        r.setDiastolicBp(72);
        r.setFastingGlucose(4.9);
        r.setTotalCholesterol(4.6);
        r.setBmi(17.8);
        r.setHeartRate(46);
        r.setAlt(22);
        r.setCreatinine(65);

        HealthScreeningDecision d1 = service.screen(r);
        Assert.assertEquals("HIGH", d1.getHighestLevel());
        Assert.assertEquals(1, d1.getHighCount());
        Assert.assertEquals("心血管内科", d1.getFollowUpDepartment());
        Assert.assertTrue(d1.getAdvice().get(1).contains("尽快完善动态心电图检查"));

        r.setHeartRate(54);
        HealthScreeningDecision d2 = service.screen(r);
        Assert.assertEquals("ATTENTION", d2.getHighestLevel());
        Assert.assertTrue(d2.getAdvice().get(d2.getAdvice().size() - 1).contains("生活方式干预"));
    }

    private HealthScreeningReport base(String id, String gender, int age) {
        HealthScreeningReport r = new HealthScreeningReport();
        r.setReportId(id);
        r.setPersonName("测试受检人");
        r.setGender(gender);
        r.setAge(age);
        r.setSystolicBp(120);
        r.setDiastolicBp(80);
        r.setFastingGlucose(5.0);
        r.setTotalCholesterol(4.5);
        r.setBmi(22);
        r.setHeartRate(72);
        r.setAlt(25);
        r.setCreatinine(gender.equals("M") ? 80 : 65);
        r.setSmokingHistory(false);
        r.setFamilyHistory(false);
        return r;
    }
}
