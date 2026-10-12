package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 30：预售尾款支付资格与商家履约规则
 */
public interface PresaleFinalPaymentRules {

    /**
     * 商家预售资格：信用分 ≥80、无违约记录、已缴保证金
     */
    boolean merchantEligible(@Param("creditScore") int creditScore,
                             @Param("breaches") int breaches,
                             @Param("bondPaid") boolean bondPaid);

    /**
     * 扣减锁定库存后的可售现货
     */
    int availableStock(@Param("stockOnHand") int stockOnHand,
                       @Param("reservedStock") int reservedStock);

    /**
     * 定金处理方式
     * windowStatus：0 未开启 / 1 进行中 / 2 逾期
     */
    String depositTreatment(@Param("windowStatus") int windowStatus,
                            @Param("identity") boolean identity,
                            @Param("cancelledBefore") boolean cancelledBefore);

    /**
     * 当前是否满足尾款支付条件（窗口进行中且已实名）
     */
    boolean canPayBalance(@Param("windowStatus") int windowStatus,
                          @Param("identity") boolean identity);
}
