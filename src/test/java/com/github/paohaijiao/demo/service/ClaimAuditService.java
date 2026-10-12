package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.ClaimAuditRules;
import com.github.paohaijiao.demo.model.ClaimDecision;
import com.github.paohaijiao.demo.model.ClaimRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 场景 3：保险理赔核赔服务
 * <p>
 * 核赔链路：等待期校验 → 赔付比例 → 免赔额扣减 → 赔付计算 → 封顶 → 风险评分与通道路由。
 * 比例、免赔、封顶、路由阈值全部由 XML 规则外置管理，理算逻辑变化时无需发版。
 */
public class ClaimAuditService {

    /**
     * 小额快赔通道默认额度上限（元），仅用于说明展示
     */
    private static final double FAST_TRACK_CAP = 5000.0;

    private final ClaimAuditRules rules;

    public ClaimAuditService(ClaimAuditRules rules) {
        this.rules = rules;
    }

    /**
     * 执行理赔核赔
     *
     * @param request 报案要素
     * @return 核赔结果
     */
    public ClaimDecision audit(ClaimRequest request) {
        ClaimDecision decision = new ClaimDecision();
        decision.setClaimNo(request.getClaimNo());

        boolean withinWaiting = rules.withinWaitingPeriod(request.getInsuredDays(), request.getWaitingPeriodDays());
        double ratio = rules.reimburseRatio(request.getProductType(), request.getHospitalLevel(), request.isAccident());
        double deductibleAmount = rules.actualDeductible(request.getProductType(),
                request.getDeductible(), request.getClaimAmount());
        double payable = rules.payableAmount(request.getClaimAmount(), request.getInvoiceAmount(),
                ratio, deductibleAmount);
        double capped = rules.applyCap(payable, request.getProductType());
        if (capped > request.getClaimAmount()) {
            capped = request.getClaimAmount();
        }

        int score = rules.riskScore(request.getPriorClaimCount(), request.getFraudFlagCount(), request.getClaimAmount());
        boolean investigation = rules.needInvestigation(request.getFraudFlagCount(), request.getClaimAmount());
        String result = rules.auditResult(withinWaiting, request.getFraudFlagCount(),
                request.isDocumentComplete(), capped);

        decision.setReimburseRatio(ratio);
        decision.setDeductibleAmount(scale(deductibleAmount));
        decision.setRiskScore(score);
        decision.setRiskLevel(rules.riskLevel(score));
        decision.setNeedInvestigation(investigation);

        if ("REJECT".equals(result)) {
            decision.setAuditResult(result);
            decision.setPayableAmount(0.0);
            decision.setFastTrack(false);
            if (withinWaiting) {
                decision.addAuditNote("保单处于等待期（已投保 " + request.getInsuredDays()
                        + " 天 < 等待期 " + request.getWaitingPeriodDays() + " 天），本次不承担赔付责任");
            } else {
                decision.addAuditNote("扣除免赔额后无可赔付金额，本次拒赔");
            }
            return decision;
        }

        if (investigation && "APPROVE".equals(result)) {
            result = "MANUAL_AUDIT";
            decision.addAuditNote("存在风控标记，转调查岗复核");
        }
        decision.setAuditResult(result);
        decision.setPayableAmount(scale(capped));
        decision.setFastTrack(rules.fastTrack(capped, request.isDocumentComplete(), request.getPriorClaimCount()));

        if (payable > capped) {
            decision.addAuditNote("赔付金额触达产品封顶线，按封顶金额赔付");
        }
        if ("MANUAL_AUDIT".equals(result) && !request.isDocumentComplete()) {
            decision.addAuditNote("理赔材料不齐全，转人工审核");
        }
        if (decision.isFastTrack()) {
            decision.addAuditNote("赔付金额不超过 " + (int) FAST_TRACK_CAP + " 元且材料齐全，进入小额快赔通道");
        }
        if (request.getPriorClaimCount() >= 3) {
            decision.addAuditNote("近 1 年理赔 " + request.getPriorClaimCount() + " 次，理赔频次偏高");
        }
        if ("MEDICAL".equals(request.getProductType()) && "COMMUNITY".equals(request.getHospitalLevel())) {
            decision.addAuditNote("社区医院就诊，赔付比例按 60% 执行");
        }
        return decision;
    }

    private double scale(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
