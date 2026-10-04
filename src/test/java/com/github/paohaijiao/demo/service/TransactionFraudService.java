package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.TransactionFraudRules;
import com.github.paohaijiao.demo.model.BankTransaction;
import com.github.paohaijiao.demo.model.FraudDecision;

/**
 * 场景 15：银行卡交易反欺诈实时风控服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：九维风险分汇总与封顶、白名单短路、风险指标明细组装</li>
 *     <li><b>规则侧</b>：各风险因子计分口径、等级阈值、处置与预警路由，全部外置在 XML 中</li>
 * </ul>
 */
public class TransactionFraudService {

    private static final int SCORE_FLOOR = 0;
    private static final int SCORE_CEIL = 100;

    private final TransactionFraudRules rules;

    public TransactionFraudService(TransactionFraudRules rules) {
        this.rules = rules;
    }

    /**
     * 对单笔交易进行实时风险评估
     *
     * @param txn 交易要素
     * @return 风控决策
     */
    public FraudDecision evaluate(BankTransaction txn) {
        FraudDecision decision = new FraudDecision();
        decision.setTxnNo(txn.getTxnNo());

        // 受信商户白名单直接放行
        if (txn.isTrustedMerchant()) {
            decision.setRiskScore(0);
            decision.setRiskLevel("LOW");
            decision.setAction("PASS");
            decision.setNeedAlert(false);
            decision.addIndicator("受信商户白名单交易，直接放行");
            return decision;
        }

        int rawScore = rules.scoreAmount(txn.getAmount())
                + rules.scoreVelocity(txn.getTxnCount1h(), txn.getTxnAmount1h())
                + rules.scoreGeo(txn.isOverseas(), txn.isCrossCity(), txn.isTravelMode())
                + rules.scoreTime(txn.getTxnHour())
                + rules.scoreDevice(txn.isDeviceChanged())
                + rules.scoreMerchant(txn.isNewMerchant())
                + rules.scoreCardNotPresent(!txn.isCardPresent(), txn.getChannel())
                + rules.scoreFailure(txn.getFailCount24h())
                + rules.scoreDeviation(txn.getAmount(), txn.getHistoricalMaxAmount(),
                        txn.getAccountAgeDays());
        int riskScore = clamp(rawScore);

        String riskLevel = rules.riskLevel(riskScore);
        decision.setRiskScore(riskScore);
        decision.setRiskLevel(riskLevel);
        decision.setAction(rules.fraudAction(riskLevel));
        decision.setNeedAlert(rules.needAlert(riskLevel, txn.isOverseas()));

        buildIndicators(txn, decision);
        return decision;
    }

    private int clamp(int score) {
        if (score < SCORE_FLOOR) {
            return SCORE_FLOOR;
        }
        if (score > SCORE_CEIL) {
            return SCORE_CEIL;
        }
        return score;
    }

    private void buildIndicators(BankTransaction txn, FraudDecision decision) {
        if (txn.getAmount() >= 5000.0) {
            decision.addIndicator("大额交易 " + txn.getAmount() + " 元");
        }
        if (txn.getTxnCount1h() >= 3) {
            decision.addIndicator("近 1 小时交易 " + txn.getTxnCount1h() + " 笔");
        }
        if (txn.getTxnAmount1h() >= 50000.0) {
            decision.addIndicator("近 1 小时累计交易 " + txn.getTxnAmount1h() + " 元");
        }
        if (txn.isOverseas()) {
            decision.addIndicator("境外交易");
        } else if (txn.isCrossCity()) {
            decision.addIndicator(txn.isTravelMode() ? "异地交易（已报备出行）" : "异地未报备交易");
        }
        if (txn.getTxnHour() >= 23 || txn.getTxnHour() < 5) {
            decision.addIndicator("深夜时段交易（" + txn.getTxnHour() + " 时）");
        } else if (txn.getTxnHour() >= 22 || txn.getTxnHour() <= 6) {
            decision.addIndicator("夜间时段交易（" + txn.getTxnHour() + " 时）");
        }
        if (txn.isDeviceChanged()) {
            decision.addIndicator("交易设备变更");
        }
        if (txn.isNewMerchant()) {
            decision.addIndicator("新商户首笔交易");
        }
        if (!txn.isCardPresent() && ("ONLINE".equals(txn.getChannel()) || "MOBILE".equals(txn.getChannel()))) {
            decision.addIndicator(txn.getChannel() + " 渠道无卡交易");
        }
        if (txn.getFailCount24h() >= 1) {
            decision.addIndicator("近 24 小时鉴权失败 " + txn.getFailCount24h() + " 次");
        }
        if (txn.getHistoricalMaxAmount() > 0.0
                && txn.getAmount() * 10 >= txn.getHistoricalMaxAmount() * 30) {
            decision.addIndicator("金额显著偏离历史消费习惯");
        }
        if (txn.getAccountAgeDays() < 7) {
            decision.addIndicator("新开户账户（" + txn.getAccountAgeDays() + " 天）");
        }
    }
}
