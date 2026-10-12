package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.TriageDecision;
import com.github.paohaijiao.demo.model.TriageRequest;
import com.github.paohaijiao.demo.service.TriageService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 34：门诊分诊/急诊优先级分级
 * <p>
 * 6 组形态：胸痛濒危 I 级、卒中绿道时间窗分叉、超高热与婴幼儿高热、
 * 普通感冒候诊、老年/孕妇腹痛升级、外伤出血与普通发热。
 */
public class TriageServiceTest {

    private TriageService service;

    @Before
    public void setUp() {
        TriageRules rules = JQuickJava.create()
                .createApi(TriageRules.class, "demo/triage.xml");
        this.service = new TriageService(rules);
    }

    /**
     * 胸痛合并血压心率危急值 → I 级濒危，立即抢救室
     */
    @Test
    public void shouldTriageChestPainWithVitalCrisisAsLevelOne() {
        TriageRequest req = base("P001", "CHEST_PAIN", 56);
        req.setVitalSignsAbnormal(true);
        req.setOxygenSaturation(96);

        TriageDecision d = service.triage(req);

        Assert.assertEquals(4, d.getLifeThreatScore());
        Assert.assertEquals("I", d.getTriageLevel());
        Assert.assertEquals("RESCUE", d.getZone());
        Assert.assertEquals(0, d.getResponseMinutes());
        Assert.assertTrue(d.getChannels().get(0).contains("立即送抢救室并启动复苏团队"));
    }

    /**
     * 清醒卒中 2 小时 → II 级绿道在窗；6 小时 → 超窗标签
     */
    @Test
    public void shouldOpenStrokeGreenChannelWithWindowBranch() {
        TriageRequest inWindow = base("P002", "STROKE_SIGN", 70);
        inWindow.setOnsetHalfHours(4);
        TriageDecision d1 = service.triage(inWindow);
        Assert.assertEquals("II", d1.getTriageLevel());
        Assert.assertEquals("CRITICAL_BAY", d1.getZone());
        Assert.assertEquals(10, d1.getResponseMinutes());
        Assert.assertTrue(d1.getChannels().get(0).contains("4.5 小时静脉溶栓时间窗内"));

        TriageRequest outWindow = base("P003", "STROKE_SIGN", 70);
        outWindow.setOnsetHalfHours(12);
        TriageDecision d2 = service.triage(outWindow);
        Assert.assertEquals("II", d2.getTriageLevel());
        Assert.assertTrue(d2.getChannels().get(0).contains("已超静脉溶栓时间窗"));
    }

    /**
     * 超高热 40.5℃ → I 级；婴幼儿 39.2℃ → II 级
     */
    @Test
    public void shouldEscalateExtremeAndInfantFever() {
        TriageRequest extreme = base("P004", "FEVER", 30);
        extreme.setTemperature(40.5);
        TriageDecision d1 = service.triage(extreme);
        Assert.assertEquals("I", d1.getTriageLevel());
        Assert.assertEquals("RESCUE", d1.getZone());
        Assert.assertTrue(d1.getChannels().get(0).contains("超高热 40.5℃"));

        TriageRequest infant = base("P005", "FEVER", 2);
        infant.setTemperature(39.2);
        infant.setInfant(true);
        TriageDecision d2 = service.triage(infant);
        Assert.assertEquals("II", d2.getTriageLevel());
        Assert.assertTrue(d2.getChannels().get(0).contains("热性惊厥"));
    }

    /**
     * 普通感冒低热成人 → IV 级候诊
     */
    @Test
    public void shouldSendCommonColdToWaitingArea() {
        TriageRequest req = base("P006", "COLD", 25);
        req.setTemperature(37.8);

        TriageDecision d = service.triage(req);

        Assert.assertEquals("IV", d.getTriageLevel());
        Assert.assertEquals("WAITING", d.getZone());
        Assert.assertEquals(120, d.getResponseMinutes());
        Assert.assertEquals(0, d.getChannels().size());
    }

    /**
     * 72 岁腹痛与孕妇腹痛均升至 II 级
     */
    @Test
    public void shouldEscalateElderlyAndPregnantAbdominalPain() {
        TriageRequest elderly = base("P007", "ABDOMINAL", 72);
        TriageDecision d1 = service.triage(elderly);
        Assert.assertEquals("II", d1.getTriageLevel());
        Assert.assertTrue(d1.getChannels().get(0).contains("65 岁以上老年腹痛"));

        TriageRequest pregnant = base("P008", "ABDOMINAL", 28);
        pregnant.setPregnant(true);
        TriageDecision d2 = service.triage(pregnant);
        Assert.assertEquals("II", d2.getTriageLevel());
        Assert.assertTrue(d2.getChannels().get(0).contains("孕产妇急救绿色通道"));
    }

    /**
     * 外伤活动出血（生命体征尚稳）→ II 级止血；成人 38.8℃ 发热 → III 级留观
     */
    @Test
    public void shouldPrioritizeActiveBleedingAndOrdinaryFever() {
        TriageRequest trauma = base("P009", "TRAUMA", 33);
        trauma.setOxygenSaturation(94);
        trauma.setBleedingActive(true);
        TriageDecision d1 = service.triage(trauma);
        Assert.assertEquals(3, d1.getLifeThreatScore());
        Assert.assertEquals("II", d1.getTriageLevel());
        Assert.assertTrue(d1.getChannels().get(0).contains("活动性出血，立即压迫止血"));

        TriageRequest fever = base("P010", "FEVER", 30);
        fever.setTemperature(38.8);
        TriageDecision d2 = service.triage(fever);
        Assert.assertEquals("III", d2.getTriageLevel());
        Assert.assertEquals("OBS", d2.getZone());
        Assert.assertEquals(30, d2.getResponseMinutes());
    }

    private TriageRequest base(String id, String complaint, int age) {
        TriageRequest r = new TriageRequest();
        r.setPatientId(id);
        r.setPatientName("测试患者");
        r.setAge(age);
        r.setChiefComplaint(complaint);
        r.setVitalSignsAbnormal(false);
        r.setOxygenSaturation(98);
        r.setConsciousnessLevel("ALERT");
        r.setBleedingActive(false);
        r.setBreathingDistress(false);
        r.setTemperature(36.8);
        r.setOnsetHalfHours(2);
        r.setInfant(false);
        r.setPregnant(false);
        return r;
    }
}
