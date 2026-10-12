package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷后预警与催收决策结果
 */
@Data
public class WarningDecision {

    /**
     * 借据号
     */
    private String loanNo;

    /**
     * 逾期阶段：0-未逾期 / 1-M1 / 2-M2 / 3-M3 / 4-M3+
     */
    private int overdueStage;

    /**
     * 预警总分（0-100）
     */
    private int warningScore;

    /**
     * 预警等级：RED / ORANGE / YELLOW / BLUE
     */
    private String warningLevel;

    /**
     * 建议催收渠道：LITIGATION-诉讼 / HOME_VISIT-上门 / PHONE-电话 / SMS-短信
     */
    private String collectChannel;

    /**
     * 本次计提罚息（元）
     */
    private double penaltyInterest;

    /**
     * 最低还款额（逾期金额 + 违约金，元）
     */
    private double minRepayAmount;

    /**
     * 风险迁徙度（0-9）
     */
    private int riskMigration;

    /**
     * 是否达到诉讼条件
     */
    private boolean needLitigation;

    /**
     * 催收动作建议，供催收员执行
     */
    private List<String> actions = new ArrayList<String>();

    public void addAction(String action) {
        this.actions.add(action);
    }
}
