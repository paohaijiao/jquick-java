package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.PostLoanWarningRules;
import com.github.paohaijiao.demo.model.LoanAccount;
import com.github.paohaijiao.demo.model.WarningDecision;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 场景 2：贷后逾期预警与催收策略服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：正常账户短路、四维评分汇总、诉讼初判、催收动作组装、金额精度处理</li>
 *     <li><b>规则侧</b>：逾期阶段、评分口径、预警等级、渠道阈值、罚息与违约金，全部外置在 XML 中</li>
 * </ul>
 */
public class PostLoanWarningService {

    /**
     * 诉讼金额门槛（元）
     */
    private static final double LITIGATION_AMOUNT_THRESHOLD = 100000.0;

    /**
     * 诉讼预警分门槛
     */
    private static final int LITIGATION_SCORE_THRESHOLD = 80;

    /**
     * 核销初判逾期天数
     */
    private static final int WRITE_OFF_DAYS = 180;

    private final PostLoanWarningRules rules;

    public PostLoanWarningService(PostLoanWarningRules rules) {
        this.rules = rules;
    }

    /**
     * 执行贷后预警评估
     *
     * @param account 账户快照
     * @return 预警与催收决策
     */
    public WarningDecision evaluate(LoanAccount account) {
        WarningDecision decision = new WarningDecision();
        decision.setLoanNo(account.getLoanNo());

        int stage = rules.overdueStage(account.getOverdueDays());
        decision.setOverdueStage(stage);

        if (account.getOverdueDays() <= 0) {
            decision.setWarningScore(0);
            decision.setWarningLevel("BLUE");
            decision.setCollectChannel("SMS");
            decision.setPenaltyInterest(0.0);
            decision.setMinRepayAmount(0.0);
            decision.setRiskMigration(0);
            decision.setNeedLitigation(false);
            decision.addAction("账户状态正常，无需催收");
            return decision;
        }

        int daysScore = rules.scoreOverdueDays(account.getOverdueDays(), account.getOverdueHistoryCount());
        int ratioScore = rules.scoreAmountRatio(account.getOverdueAmount(), account.getRemainingPrincipal());
        int contactScore = rules.scoreContact(account.isContactSuccess(), account.getLastContactDays());
        int collateralScore = rules.scoreCollateral(account.getCollateralType(),
                account.getCollateralValue(), account.getRemainingPrincipal());

        int total = daysScore + ratioScore + contactScore + collateralScore;
        if (total < 0) {
            total = 0;
        } else if (total > 100) {
            total = 100;
        }

        String level = rules.warningLevel(total);
        decision.setWarningScore(total);
        decision.setWarningLevel(level);
        decision.setCollectChannel(rules.collectChannel(total, account.getOverdueAmount()));
        decision.setPenaltyInterest(scale(rules.penaltyInterest(account.getRemainingPrincipal(),
                account.getBaseRate(), account.getOverdueDays())));
        decision.setMinRepayAmount(scale(rules.minRepayAmount(account.getOverdueAmount(), account.getOverdueDays())));
        decision.setNeedLitigation(total >= LITIGATION_SCORE_THRESHOLD
                || (account.getOverdueDays() >= 90 && account.getOverdueAmount() >= LITIGATION_AMOUNT_THRESHOLD));
        decision.setRiskMigration(rules.riskMigration(stage, account.getWarningCount()));

        buildActions(account, decision);
        return decision;
    }

    private void buildActions(LoanAccount account, WarningDecision decision) {
        if (!account.isContactSuccess()) {
            decision.addAction("多次联系未果，建议上门核实并更新联系方式");
        }
        if (account.getOverdueDays() >= 90) {
            decision.addAction("逾期超过 90 天，进入不良资产管理流程");
        }
        if (account.getOverdueDays() >= WRITE_OFF_DAYS) {
            decision.addAction("逾期超过 180 天，符合核销条件初判，提交核销审批");
        }
        if ("RED".equals(decision.getWarningLevel())) {
            decision.addAction("预警等级 RED，启动诉讼材料准备");
        }
        if ("NONE".equals(account.getCollateralType())) {
            decision.addAction("无抵押物，回收保障较弱");
        }
        if (account.getOverdueHistoryCount() >= 3) {
            decision.addAction("近 1 年逾期 " + account.getOverdueHistoryCount() + " 次，建议下调授信或提前收贷");
        }
    }

    private double scale(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
