package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 市民资格审核决策结果（落户积分 / 购房购车门槛共用）
 */
@Data
public class CityQualificationDecision {

    /**
     * 申请单号
     */
    private String applicationId;

    /**
     * 申请事项
     */
    private String benefit;

    /**
     * 审核结论：ALLOW-资格通过 / APPROVE-核准落户 / REVIEW-复核公示 / REJECT-不通过
     */
    private String result;

    /**
     * 落户积分（仅落户事项计分，其余事项为 0）
     */
    private int points;

    /**
     * 各项得分与门槛核验明细
     */
    private List<String> details = new ArrayList<String>();

    /**
     * 不通过原因与政策说明
     */
    private List<String> reasons = new ArrayList<String>();

    public void addDetail(String detail) {
        this.details.add(detail);
    }

    public void addReason(String reason) {
        this.reasons.add(reason);
    }
}
