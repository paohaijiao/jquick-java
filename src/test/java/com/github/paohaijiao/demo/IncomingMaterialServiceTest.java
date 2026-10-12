package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.MaterialInspection;
import com.github.paohaijiao.demo.model.MaterialInspectionDecision;
import com.github.paohaijiao.demo.service.IncomingMaterialService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 31：原材料来料验收标准动态适配
 * <p>
 * 6 组批次形态：满分接收入库、轻微缺陷让步接收、缺陷超标退货、
 * 致命缺陷零容忍、缺质保书且环保不合规、C 级供应商加严拒收（与 A 级对比）。
 */
public class IncomingMaterialServiceTest {

    private IncomingMaterialService service;

    @Before
    public void setUp() {
        IncomingMaterialRules rules = JQuickJava.create()
                .createApi(IncomingMaterialRules.class, "demo/incoming-material.xml");
        this.service = new IncomingMaterialService(rules);
    }

    /**
     * 50 件全检合格、文件齐全 → 100 分接收
     */
    @Test
    public void shouldAcceptPerfectBatch() {
        MaterialInspection m = base("BAT001", "A");
        m.setSampleSize(50);
        m.setMajorDefects(0);
        m.setMinorDefects(0);

        MaterialInspectionDecision d = service.inspect(m);

        Assert.assertEquals(100, d.getTotalScore());
        Assert.assertEquals("ACCEPT", d.getAction());
        Assert.assertFalse(d.isHardReject());
        Assert.assertTrue(d.getDispositions().contains("抽检符合接收质量限，准予办理入库手续"));
    }

    /**
     * 50 件中 2 个轻微缺陷（4%）→ 87 分让步接收并要求 8D
     */
    @Test
    public void shouldConditionallyAcceptMinorDefects() {
        MaterialInspection m = base("BAT002", "A");
        m.setSampleSize(50);
        m.setMinorDefects(2);

        MaterialInspectionDecision d = service.inspect(m);

        Assert.assertEquals(87, d.getTotalScore());
        Assert.assertEquals("CONDITIONAL", d.getAction());
        Assert.assertTrue(d.getScoreDetails().contains("轻微缺陷率: 7 / 20"));
        Assert.assertTrue(d.getDispositions().contains(
                "轻微缺陷率达到加控线，要求供应商提交 8D 整改报告"));
    }

    /**
     * 3 个严重缺陷 + 4 个轻微缺陷 + 尺寸不合格 → 40 分退货
     */
    @Test
    public void shouldRejectBatchWithExcessiveDefects() {
        MaterialInspection m = base("BAT003", "B");
        m.setSampleSize(50);
        m.setMajorDefects(3);
        m.setMinorDefects(4);
        m.setDimensionPass(false);

        MaterialInspectionDecision d = service.inspect(m);

        Assert.assertEquals(40, d.getTotalScore());
        Assert.assertEquals("REJECT", d.getAction());
        Assert.assertTrue(d.getDispositions().contains("严重缺陷率超过 AQL 加严线，按退货流程处理"));
        Assert.assertTrue(d.getDispositions().contains("关键尺寸检验不合格，批次不得上线，须技术会签"));
    }

    /**
     * 其余全合格但出现 1 个致命缺陷 → 零容忍整批拒收
     */
    @Test
    public void shouldHardRejectCriticalDefect() {
        MaterialInspection m = base("BAT004", "A");
        m.setSampleSize(50);
        m.setCriticalDefects(1);

        MaterialInspectionDecision d = service.inspect(m);

        Assert.assertEquals(0, d.getTotalScore());
        Assert.assertEquals("REJECT", d.getAction());
        Assert.assertTrue(d.isHardReject());
        Assert.assertTrue(d.getDispositions().get(0).contains("致命缺陷，违反零容忍要求"));
    }

    /**
     * 无质保书且环保验证不通过 → 双硬规则拒收
     */
    @Test
    public void shouldHardRejectMissingCertificateAndRoHS() {
        MaterialInspection m = base("BAT005", "B");
        m.setSampleSize(50);
        m.setCertificateProvided(false);
        m.setEnvironmentalCompliant(false);

        MaterialInspectionDecision d = service.inspect(m);

        Assert.assertEquals("REJECT", d.getAction());
        Assert.assertTrue(d.isHardReject());
        Assert.assertTrue(d.getDispositions().contains("缺少质保书/COA 随附文件，批次不予接收"));
        Assert.assertTrue(d.getDispositions().contains(
                "RoHS/环保合规验证不通过，整批拒收并冻结同批次在途库存"));
    }

    /**
     * 74 分批次：A 级供应商让步接收，C 级供应商加严拒收
     */
    @Test
    public void shouldTightenAcceptanceForGradeCSupplier() {
        MaterialInspection gradeA = base("BAT006", "A");
        gradeA.setSampleSize(50);
        gradeA.setMajorDefects(1);
        gradeA.setMinorDefects(1);
        MaterialInspectionDecision d1 = service.inspect(gradeA);
        Assert.assertEquals(74, d1.getTotalScore());
        Assert.assertEquals("CONDITIONAL", d1.getAction());
        Assert.assertFalse(d1.isHardReject());

        MaterialInspection gradeC = base("BAT007", "C");
        gradeC.setSampleSize(50);
        gradeC.setMajorDefects(1);
        gradeC.setMinorDefects(1);
        MaterialInspectionDecision d2 = service.inspect(gradeC);
        Assert.assertEquals(74, d2.getTotalScore());
        Assert.assertEquals("REJECT", d2.getAction());
        Assert.assertTrue(d2.getDispositions().get(0).contains("供应商评级为 C，执行加严验收"));
    }

    private MaterialInspection base(String batchNo, String grade) {
        MaterialInspection m = new MaterialInspection();
        m.setBatchNo(batchNo);
        m.setMaterialCode("MAT-8001");
        m.setMaterialCategory("ELECTRONIC");
        m.setSampleSize(50);
        m.setCriticalDefects(0);
        m.setMajorDefects(0);
        m.setMinorDefects(0);
        m.setCertificateProvided(true);
        m.setAppearancePass(true);
        m.setDimensionPass(true);
        m.setEnvironmentalCompliant(true);
        m.setSupplierGrade(grade);
        return m;
    }
}
