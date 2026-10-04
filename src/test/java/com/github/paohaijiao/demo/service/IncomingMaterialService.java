package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.IncomingMaterialRules;
import com.github.paohaijiao.demo.model.MaterialInspection;
import com.github.paohaijiao.demo.model.MaterialInspectionDecision;

/**
 * 场景 31：来料检验批次接收判定
 * <p>
 * 致命缺陷、缺质保书、环保不合规零容忍直接拒收；其余按六维质量分入库/让步/退货，
 * C 级供应商执行加严验收。
 */
public class IncomingMaterialService {

    private final IncomingMaterialRules rules;

    public IncomingMaterialService(IncomingMaterialRules rules) {
        this.rules = rules;
    }

    public MaterialInspectionDecision inspect(MaterialInspection m) {
        MaterialInspectionDecision d = new MaterialInspectionDecision();
        d.setBatchNo(m.getBatchNo());

        if (m.getCriticalDefects() > 0 || !m.isCertificateProvided()
                || !m.isEnvironmentalCompliant()) {
            d.setTotalScore(0);
            d.setAction("REJECT");
            d.setHardReject(true);
            if (m.getCriticalDefects() > 0) {
                d.addDisposition("发现 " + m.getCriticalDefects()
                        + " 个致命缺陷，违反零容忍要求，整批拒收并启动供应商问责");
            }
            if (!m.isCertificateProvided()) {
                d.addDisposition("缺少质保书/COA 随附文件，批次不予接收");
            }
            if (!m.isEnvironmentalCompliant()) {
                d.addDisposition("RoHS/环保合规验证不通过，整批拒收并冻结同批次在途库存");
            }
            return d;
        }

        int majorScore = rules.scoreMajor(m.getMajorDefects(), m.getSampleSize());
        int minorScore = rules.scoreMinor(m.getMinorDefects(), m.getSampleSize());
        int certScore = rules.scoreCertificate(m.isCertificateProvided());
        int appearanceScore = rules.scoreAppearance(m.isAppearancePass());
        int dimensionScore = rules.scoreDimension(m.isDimensionPass());
        int envScore = rules.scoreEnvironmental(m.isEnvironmentalCompliant());
        int total = majorScore + minorScore + certScore + appearanceScore + dimensionScore + envScore;

        d.addDetail("严重缺陷率: " + majorScore + " / 30");
        d.addDetail("轻微缺陷率: " + minorScore + " / 20");
        d.addDetail("质保文件: " + certScore + " / 15");
        d.addDetail("外观: " + appearanceScore + " / 15");
        d.addDetail("尺寸: " + dimensionScore + " / 10");
        d.addDetail("环保合规: " + envScore + " / 10");
        d.setTotalScore(total);

        String level = rules.inspectionLevel(total);
        if ("C".equals(m.getSupplierGrade()) && !"ACCEPT".equals(level)) {
            d.setAction("REJECT");
            d.addDisposition("供应商评级为 C，执行加严验收，质量分 " + total
                    + " 未达 90 分接收线，整批退货");
            collectDefectItems(d, m, majorScore, minorScore);
            return d;
        }

        d.setAction(level);
        if ("ACCEPT".equals(level)) {
            d.addDisposition("抽检符合接收质量限，准予办理入库手续");
        } else if ("CONDITIONAL".equals(level)) {
            d.addDisposition("质量分 " + total + "，作让步接收处理，须挑选/复检后上线");
            collectDefectItems(d, m, majorScore, minorScore);
        } else {
            d.addDisposition("质量分 " + total + "，低于 70 分接收底线，整批退货");
            collectDefectItems(d, m, majorScore, minorScore);
        }
        return d;
    }

    private void collectDefectItems(MaterialInspectionDecision d, MaterialInspection m,
                                    int majorScore, int minorScore) {
        if (majorScore == 0) {
            d.addDisposition("严重缺陷率超过 AQL 加严线，按退货流程处理");
        } else if (m.getMajorDefects() > 0) {
            d.addDisposition("存在 " + m.getMajorDefects() + " 个严重缺陷，上线前须全检挑选");
        }
        if (minorScore == 0) {
            d.addDisposition("轻微缺陷率过高，不予让步接收");
        } else if (minorScore <= 7 && m.getMinorDefects() > 0) {
            d.addDisposition("轻微缺陷率达到加控线，要求供应商提交 8D 整改报告");
        }
        if (!m.isDimensionPass()) {
            d.addDisposition("关键尺寸检验不合格，批次不得上线，须技术会签");
        }
        if (!m.isAppearancePass()) {
            d.addDisposition("外观检验不合格，转外观挑选后复检");
        }
    }
}
