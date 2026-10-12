package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 银行卡交易风控决策结果
 */
@Data
public class FraudDecision {

    /**
     * 交易流水号
     */
    private String txnNo;

    /**
     * 风险分（0-100）
     */
    private int riskScore;

    /**
     * 风险等级：HIGH / MEDIUM / LOW
     */
    private String riskLevel;

    /**
     * 处置动作：BLOCK-拦截 / STEP_UP-强验证 / PASS-放行
     */
    private String action;

    /**
     * 是否实时预警
     */
    private boolean needAlert;

    /**
     * 命中风险指标
     */
    private List<String> indicators = new ArrayList<String>();

    public void addIndicator(String indicator) {
        this.indicators.add(indicator);
    }
}
