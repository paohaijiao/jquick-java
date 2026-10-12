package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 医保报销决策结果
 */
@Data
public class ReimbursementDecision {

    /**
     * 结算单号
     */
    private String claimNo;

    /**
     * 是否在医保目录内
     */
    private boolean withinCatalog;

    /**
     * 起付线金额
     */
    private double deductibleAmount;

    /**
     * 乙类先行自付金额
     */
    private double prepaidSelfPay;

    /**
     * 报销比例
     */
    private double reimburseRatio;

    /**
     * 统筹支付金额
     */
    private double payableAmount;

    /**
     * 个人自付金额
     */
    private double selfPayAmount;

    /**
     * 年度封顶线
     */
    private double annualCap;

    /**
     * 是否触及封顶线
     */
    private boolean capped;

    /**
     * 结算结论：APPROVE-即时结算 / MANUAL_REVIEW-人工审核 / SELF_PAY-全自费
     */
    private String result;

    /**
     * 是否需要人工审核
     */
    private boolean needManualReview;

    /**
     * 结算说明
     */
    private List<String> notes = new ArrayList<String>();

    public void addNote(String note) {
        this.notes.add(note);
    }
}
