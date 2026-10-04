package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 15：银行卡交易反欺诈规则集
 * <p>
 * 九维风险因子独立计分，汇总后决定拦截、强验证或放行：
 * 金额、频次、地理位置、时段、设备、商户、无卡交易、鉴权失败、行为偏离与新账户。
 * 规则脚本见 {@code src/test/resources/demo/transaction-fraud.xml}。
 */
public interface TransactionFraudRules {

    int scoreAmount(@Param("amount") double amount);

    int scoreVelocity(@Param("txnCount1h") int txnCount1h,
                      @Param("txnAmount1h") double txnAmount1h);

    int scoreGeo(@Param("overseas") boolean overseas,
                 @Param("crossCity") boolean crossCity,
                 @Param("travelMode") boolean travelMode);

    int scoreTime(@Param("txnHour") int txnHour);

    int scoreDevice(@Param("deviceChanged") boolean deviceChanged);

    int scoreMerchant(@Param("newMerchant") boolean newMerchant);

    int scoreCardNotPresent(@Param("cardNotPresent") boolean cardNotPresent,
                            @Param("channel") String channel);

    int scoreFailure(@Param("failCount24h") int failCount24h);

    int scoreDeviation(@Param("amount") double amount,
                       @Param("historicalMaxAmount") double historicalMaxAmount,
                       @Param("accountAgeDays") int accountAgeDays);

    String riskLevel(@Param("riskScore") int riskScore);

    String fraudAction(@Param("riskLevel") String riskLevel);

    boolean needAlert(@Param("riskLevel") String riskLevel,
                      @Param("overseas") boolean overseas);
}
