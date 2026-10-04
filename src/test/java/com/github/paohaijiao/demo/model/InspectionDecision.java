package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 批次质检决策结果
 */
@Data
public class InspectionDecision {

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 综合质量分（0-100，扣分制）
     */
    private int qualityScore;

    /**
     * 不良率（千分比）
     */
    private int defectRatePermil;

    /**
     * 关键尺寸是否合格
     */
    private boolean dimensionPass;

    /**
     * 质量等级：ACCEPT-接收 / REWORK-返工 / SCRAP-报废 / HOLD-挂起待检
     */
    private String qualityLevel;

    /**
     * 处置去向：NEXT_PROCESS / REWORK_LINE / SCRAP_BIN / QA_HOLD
     */
    private String disposition;

    /**
     * 是否启动纠正预防措施（CAPA）
     */
    private boolean needCapa;

    /**
     * 是否允许批次放行
     */
    private boolean releaseAllowed;

    /**
     * 判定说明
     */
    private List<String> notes = new ArrayList<String>();

    public void addNote(String note) {
        this.notes.add(note);
    }
}
