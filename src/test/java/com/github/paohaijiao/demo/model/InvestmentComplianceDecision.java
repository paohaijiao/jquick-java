package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 国资投资项目立项合规决策结果
 */
@Data
public class InvestmentComplianceDecision {

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 合规结论：COMPLIANT-合规通过 / CONDITIONAL-附条件通过 / NON_COMPLIANT-不合规
     */
    private String result;

    /**
     * 按投资额应履行的审批层级：SASAC / GROUP / BOARD / EXECUTIVE / INTERNAL
     */
    private String requiredLevel;

    /**
     * 现有授权对应的审批层级
     */
    private String grantedLevel;

    /**
     * 经负面清单、授权额度截断后的核定出资额（万元）
     */
    private int cappedAmountWan;

    /**
     * 不合规项与整改要求
     */
    private List<String> findings = new ArrayList<String>();

    public void addFinding(String finding) {
        this.findings.add(finding);
    }
}
