package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 3（生产级样板）：保险理赔核赔规则集
 * <p>
 * 规则按核赔实务拆分为「资格校验 → 比例与免赔额 → 赔付计算 → 封顶 → 风险与通道路由」，
 * 由 {@code ClaimAuditService} 串联执行，规则脚本见
 * {@code src/test/resources/demo/claim-audit.xml}。
 */
public interface ClaimAuditRules {

    /**
     * 是否处于等待期（等待期内不承担赔付责任）
     */
    boolean withinWaitingPeriod(@Param("insuredDays") int insuredDays,
                                @Param("waitingPeriodDays") int waitingPeriodDays);

    /**
     * 赔付比例：产品类型 × 医院等级 × 是否意外
     */
    double reimburseRatio(@Param("productType") String productType,
                          @Param("hospitalLevel") String hospitalLevel,
                          @Param("isAccident") boolean isAccident);

    /**
     * 实际扣减的免赔额（重疾免赔、意外减半、不超过申请金额）
     */
    double actualDeductible(@Param("productType") String productType,
                            @Param("deductible") double deductible,
                            @Param("claimAmount") double claimAmount);

    /**
     * 赔付金额 =（实际费用 - 免赔额）× 赔付比例
     */
    double payableAmount(@Param("claimAmount") double claimAmount,
                         @Param("invoiceAmount") double invoiceAmount,
                         @Param("ratio") double ratio,
                         @Param("deductibleAmount") double deductibleAmount);

    /**
     * 产品封顶线控制
     */
    double applyCap(@Param("payableAmount") double payableAmount,
                    @Param("productType") String productType);

    /**
     * 理赔风险分：历史理赔次数 + 风控标记 + 大额报案
     */
    int riskScore(@Param("priorClaimCount") int priorClaimCount,
                  @Param("fraudFlagCount") int fraudFlagCount,
                  @Param("claimAmount") double claimAmount);

    /**
     * 理赔风险等级：LOW / MEDIUM / HIGH
     */
    String riskLevel(@Param("riskScore") int riskScore);

    /**
     * 是否需要转调查岗
     */
    boolean needInvestigation(@Param("fraudFlagCount") int fraudFlagCount,
                              @Param("claimAmount") double claimAmount);

    /**
     * 核赔结论：APPROVE / MANUAL_AUDIT / REJECT
     */
    String auditResult(@Param("withinWaitingPeriod") boolean withinWaitingPeriod,
                       @Param("fraudFlagCount") int fraudFlagCount,
                       @Param("documentComplete") boolean documentComplete,
                       @Param("payableAmount") double payableAmount);

    /**
     * 是否走小额快赔通道
     */
    boolean fastTrack(@Param("payableAmount") double payableAmount,
                      @Param("documentComplete") boolean documentComplete,
                      @Param("priorClaimCount") int priorClaimCount);
}
