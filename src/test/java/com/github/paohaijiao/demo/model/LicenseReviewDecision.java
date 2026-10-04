package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 许可证年审合规决策结果
 */
@Data
public class LicenseReviewDecision {

    /**
     * 许可证编号
     */
    private String licenseId;

    /**
     * 年审结论：PASS-通过 / CONDITIONAL-附条件通过 / FAIL-不予通过 / REJECT-红线驳回
     */
    private String result;

    /**
     * 合规评分（0-100）
     */
    private int totalScore;

    /**
     * 各维度得分明细
     */
    private List<String> scoreDetails = new ArrayList<String>();

    /**
     * 不合规项与整改要求
     */
    private List<String> nonConformities = new ArrayList<String>();

    public void addScoreDetail(String detail) {
        this.scoreDetails.add(detail);
    }

    public void addNonConformity(String nonConformity) {
        this.nonConformities.add(nonConformity);
    }
}
