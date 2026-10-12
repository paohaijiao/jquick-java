package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 来料批次接收决策
 */
@Data
public class MaterialInspectionDecision {

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 综合质量分（硬拒收时为 0）
     */
    private int totalScore;

    /**
     * 处置动作：ACCEPT 接收入库 / CONDITIONAL 让步接收或挑选 / REJECT 整批拒收
     */
    private String action;

    /**
     * 是否命中零容忍硬规则
     */
    private boolean hardReject;

    /**
     * 评分明细
     */
    private List<String> scoreDetails = new ArrayList<String>();

    /**
     * 处置说明与整改要求
     */
    private List<String> dispositions = new ArrayList<String>();

    public void addDetail(String detail) {
        this.scoreDetails.add(detail);
    }

    public void addDisposition(String disposition) {
        this.dispositions.add(disposition);
    }
}
