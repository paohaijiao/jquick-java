package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 税务合规风险决策结果
 */
@Data
public class TaxRiskDecision {

    /**
     * 企业税号
     */
    private String companyId;

    /**
     * 风险总分（0-100）
     */
    private int riskScore;

    /**
     * 风险等级：HIGH / MEDIUM / LOW
     */
    private String riskLevel;

    /**
     * 处置方式：SELF_CHECK-自查 / FIELD_VISIT-实地核查 / TAX_AUDIT-税务稽查
     */
    private String auditLevel;

    /**
     * 是否推送风险提醒
     */
    private boolean needWarning;

    /**
     * 是否移交稽查
     */
    private boolean needInvestigation;

    /**
     * 风险指标明细
     */
    private List<String> indicators = new ArrayList<String>();

    /**
     * 处置建议
     */
    private List<String> notes = new ArrayList<String>();

    public void addIndicator(String indicator) {
        this.indicators.add(indicator);
    }

    public void addNote(String note) {
        this.notes.add(note);
    }
}
