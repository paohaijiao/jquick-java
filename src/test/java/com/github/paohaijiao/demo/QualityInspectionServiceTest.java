package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.InspectionBatch;
import com.github.paohaijiao.demo.model.InspectionDecision;
import com.github.paohaijiao.demo.service.QualityInspectionService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 12 生产级样板：产线质检多指标判定
 * <p>
 * 覆盖 6 组批次形态：合格放行、致命缺陷报废、尺寸超差返工、
 * 不良率临界挂起、设备告警成片缺陷、大批量报废。
 */
public class QualityInspectionServiceTest {

    private QualityInspectionService service;

    @Before
    public void setUp() {
        QualityInspectionRules rules = JQuickJava.create()
                .createApi(QualityInspectionRules.class, "demo/quality-inspection.xml");
        this.service = new QualityInspectionService(rules);
    }

    /**
     * 优质批次：200 抽 1 轻微不良、尺寸合格、外观 97 → 95 分接收放行
     */
    @Test
    public void shouldReleaseConformingBatch() {
        InspectionBatch batch = batch("BAT-1201", 200, 0, 0, 1, 10.01, 10.0, 0.05, 97, "NORMAL", false);

        InspectionDecision decision = service.inspect(batch);

        Assert.assertEquals(95, decision.getQualityScore());
        Assert.assertEquals(5, decision.getDefectRatePermil());
        Assert.assertTrue(decision.isDimensionPass());
        Assert.assertEquals("ACCEPT", decision.getQualityLevel());
        Assert.assertEquals("NEXT_PROCESS", decision.getDisposition());
        Assert.assertTrue(decision.isReleaseAllowed());
        Assert.assertFalse(decision.isNeedCapa());
        Assert.assertTrue(decision.getNotes().contains("抽检 200 件、不良 1 件，批次合格，流转下一工序"));
    }

    /**
     * 致命缺陷：1 件致命缺陷 → 不看分数直接整批报废
     */
    @Test
    public void shouldScrapBatchWithCriticalDefect() {
        InspectionBatch batch = batch("BAT-1202", 100, 1, 0, 2, 10.02, 10.0, 0.05, 80, "NORMAL", false);

        InspectionDecision decision = service.inspect(batch);

        Assert.assertEquals(30, decision.getQualityScore());
        Assert.assertEquals("SCRAP", decision.getQualityLevel());
        Assert.assertEquals("SCRAP_BIN", decision.getDisposition());
        Assert.assertFalse(decision.isReleaseAllowed());
        Assert.assertTrue(decision.isNeedCapa());
        Assert.assertTrue(decision.getNotes().contains("发现致命缺陷 1 件，整批判废并隔离"));
    }

    /**
     * 尺寸超差：2 严重 + 2 轻微、关键尺寸超差 → 20 分返工
     */
    @Test
    public void shouldReworkDimensionOutOfToleranceBatch() {
        InspectionBatch batch = batch("BAT-1203", 100, 0, 2, 2, 10.12, 10.0, 0.05, 90, "NORMAL", false);

        InspectionDecision decision = service.inspect(batch);

        Assert.assertEquals(20, decision.getQualityScore());
        Assert.assertEquals(40, decision.getDefectRatePermil());
        Assert.assertFalse(decision.isDimensionPass());
        Assert.assertEquals("REWORK", decision.getQualityLevel());
        Assert.assertEquals("REWORK_LINE", decision.getDisposition());
        Assert.assertFalse(decision.isReleaseAllowed());
        Assert.assertFalse(decision.isNeedCapa());
        Assert.assertTrue(decision.getNotes().contains("关键尺寸实测 10.12 超出公差 0.05，转返工线"));
    }

    /**
     * 不良率临界：4 件轻微不良、千分之 40 → 挂起待加严复检
     */
    @Test
    public void shouldHoldBorderlineDefectRateBatch() {
        InspectionBatch batch = batch("BAT-1204", 100, 0, 0, 4, 10.00, 10.0, 0.05, 97, "NORMAL", false);

        InspectionDecision decision = service.inspect(batch);

        Assert.assertEquals(80, decision.getQualityScore());
        Assert.assertEquals(40, decision.getDefectRatePermil());
        Assert.assertEquals("HOLD", decision.getQualityLevel());
        Assert.assertEquals("QA_HOLD", decision.getDisposition());
        Assert.assertFalse(decision.isReleaseAllowed());
        Assert.assertFalse(decision.isNeedCapa());
        Assert.assertTrue(decision.getNotes().contains("不良率或质量分处于临界区间，挂起等待加严复检"));
    }

    /**
     * 设备告警 + 3 件严重缺陷 → 40 分挂起，触发 CAPA
     */
    @Test
    public void shouldTriggerCapaForEquipmentWarningAndMajorDefects() {
        InspectionBatch batch = batch("BAT-1205", 200, 0, 3, 0, 10.01, 10.0, 0.05, 90, "WARNING", true);

        InspectionDecision decision = service.inspect(batch);

        Assert.assertEquals(40, decision.getQualityScore());
        Assert.assertEquals(15, decision.getDefectRatePermil());
        Assert.assertEquals("HOLD", decision.getQualityLevel());
        Assert.assertTrue(decision.isNeedCapa());
        Assert.assertTrue(decision.getNotes().contains("生产设备处于告警状态，建议同步安排点检"));
        Assert.assertTrue(decision.getNotes().contains("首件检验，需质量工程师确认后再批量投产"));
        Assert.assertTrue(decision.getNotes().contains("已触发纠正预防措施（CAPA）流程"));
    }

    /**
     * 大批量报废：50 抽 16 件不良含 2 件致命、不良率千分之 320
     */
    @Test
    public void shouldScrapHighDefectRateBatch() {
        InspectionBatch batch = batch("BAT-1206", 50, 2, 4, 10, 10.20, 10.0, 0.05, 70, "WARNING", false);

        InspectionDecision decision = service.inspect(batch);

        Assert.assertEquals(0, decision.getQualityScore());
        Assert.assertEquals(320, decision.getDefectRatePermil());
        Assert.assertEquals("SCRAP", decision.getQualityLevel());
        Assert.assertEquals("SCRAP_BIN", decision.getDisposition());
        Assert.assertFalse(decision.isReleaseAllowed());
        Assert.assertTrue(decision.isNeedCapa());
    }

    private InspectionBatch batch(String batchNo, int sampleSize, int critical, int major, int minor,
                                  double measured, double target, double tolerance,
                                  int surfaceScore, String equipmentStatus, boolean firstArticle) {
        InspectionBatch b = new InspectionBatch();
        b.setBatchNo(batchNo);
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
        b.setSurfaceScore(surfaceScore);
        b.setEquipmentStatus(equipmentStatus);
        b.setFirstArticle(firstArticle);
        return b;
    }
}
