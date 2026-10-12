package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 23：小微企业税费减免与稳岗补贴规则集
 * <p>
 * 小型微利企业所得税分段实际税负：100 万以下部分 5%、100-300 万部分 10%；
 * 小规模纳税人增值税征收率 1%；稳岗补贴为实缴失业保险费的 50%。
 * 规则脚本见 {@code src/test/resources/demo/tax-relief.xml}。
 */
public interface TaxReliefRules {

    /** 核定企业所得税（万元）：符合小微条件分段优惠，否则按 25% 法定税率 */
    double incomeTaxWan(@Param("incomeWan") double incomeWan,
                        @Param("smallQualified") boolean smallQualified);

    /** 增值税征收率：小规模纳税人减按 1%，一般纳税人不适用该优惠 */
    double vatLevyRate(@Param("taxpayerType") String taxpayerType);

    /** 稳岗补贴资格：社保满 12 月、裁员率不超过 5%、无社保欠费 */
    boolean stabilizationEligible(@Param("ssMonths") int ssMonths,
                                  @Param("layoffRate") int layoffRate,
                                  @Param("noArrears") boolean noArrears);

    /** 稳岗补贴定额：资格成立时为实缴失业保险费的 50% */
    double subsidyAmountWan(@Param("paidUiWan") double paidUiWan,
                            @Param("eligible") boolean eligible);
}
