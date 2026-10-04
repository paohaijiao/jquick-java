package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.QualityInspectionRules;
import com.github.paohaijiao.demo.model.InspectionBatch;
import com.github.paohaijiao.demo.model.InspectionDecision;

/**
 * 场景 12：产线批次质检分级服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：不良率与扣分汇总、等级装配、处置说明与 CAPA 提示组装</li>
 *     <li><b>规则侧</b>：公差判定、缺陷扣分口径、等级阈值与报废/返工界限，全部外置在 XML 中</li>
 * </ul>
 */
public class QualityInspectionService {

    private static final int SCORE_FLOOR = 0;
    private static final int SCORE_CEIL = 100;

    private final QualityInspectionRules rules;

    public QualityInspectionService(QualityInspectionRules rules) {
        this.rules = rules;
    }

    /**
     * 执行批次质检判定
     *
     * @param batch 批次抽检记录
     * @return 质检决策
     */
    public InspectionDecision inspect(InspectionBatch batch) {
        InspectionDecision decision = new InspectionDecision();
        decision.setBatchNo(batch.getBatchNo());

        int totalDefects = batch.getCriticalDefects() + batch.getMajorDefects() + batch.getMinorDefects();
        int ratePermil = rules.defectRatePermil(totalDefects, batch.getSampleSize());
        boolean dimensionPass = rules.dimensionPass(batch.getMeasuredSize(),
                batch.getTargetSize(), batch.getTolerance());

        int rawScore = rules.scoreDefects(batch.getCriticalDefects(),
                        batch.getMajorDefects(), batch.getMinorDefects())
                + rules.scoreAppearance(batch.getSurfaceScore())
                + rules.scoreDimension(dimensionPass)
                + rules.scoreEquipment(batch.getEquipmentStatus());
        int qualityScore = clamp(rawScore);

        String level = rules.qualityLevel(qualityScore, batch.getCriticalDefects(), ratePermil);
        boolean equipmentWarning = "WARNING".equals(batch.getEquipmentStatus());

        decision.setQualityScore(qualityScore);
        decision.setDefectRatePermil(ratePermil);
        decision.setDimensionPass(dimensionPass);
        decision.setQualityLevel(level);
        decision.setDisposition(rules.disposition(level));
        decision.setNeedCapa(rules.needCapa(batch.getCriticalDefects(),
                batch.getMajorDefects(), equipmentWarning));
        decision.setReleaseAllowed(rules.releaseAllowed(level, dimensionPass));

        buildNotes(batch, decision, totalDefects);
        return decision;
    }

    private int clamp(int score) {
        if (score < SCORE_FLOOR) {
            return SCORE_FLOOR;
        }
        if (score > SCORE_CEIL) {
            return SCORE_CEIL;
        }
        return score;
    }

    private void buildNotes(InspectionBatch batch, InspectionDecision decision, int totalDefects) {
        if ("ACCEPT".equals(decision.getQualityLevel())) {
            decision.addNote("抽检 " + batch.getSampleSize() + " 件、不良 " + totalDefects
                    + " 件，批次合格，流转下一工序");
        }
        if (batch.getCriticalDefects() >= 1) {
            decision.addNote("发现致命缺陷 " + batch.getCriticalDefects() + " 件，整批判废并隔离");
        }
        if (!decision.isDimensionPass()) {
            decision.addNote("关键尺寸实测 " + batch.getMeasuredSize()
                    + " 超出公差 " + batch.getTolerance() + "，转返工线");
        }
        if ("HOLD".equals(decision.getQualityLevel())) {
            decision.addNote("不良率或质量分处于临界区间，挂起等待加严复检");
        }
        if (batch.isFirstArticle()) {
            decision.addNote("首件检验，需质量工程师确认后再批量投产");
        }
        if ("WARNING".equals(batch.getEquipmentStatus())) {
            decision.addNote("生产设备处于告警状态，建议同步安排点检");
        }
        if (decision.isNeedCapa()) {
            decision.addNote("已触发纠正预防措施（CAPA）流程");
        }
    }
}
