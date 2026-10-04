package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 银行卡交易要素（实时风控所需的交易快照）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankTransaction {

    /**
     * 交易流水号
     */
    private String txnNo;

    /**
     * 银行卡号（脱敏）
     */
    private String cardNo;

    /**
     * 交易金额
     */
    private double amount;

    /**
     * 交易小时（0-23）
     */
    private int txnHour;

    /**
     * 交易渠道：POS / ATM / ONLINE / MOBILE
     */
    private String channel;

    /**
     * 是否境外交易
     */
    private boolean overseas;

    /**
     * 是否跨城市交易
     */
    private boolean crossCity;

    /**
     * 是否报备出行模式
     */
    private boolean travelMode;

    /**
     * 近 1 小时交易笔数
     */
    private int txnCount1h;

    /**
     * 近 1 小时累计交易金额
     */
    private double txnAmount1h;

    /**
     * 是否新商户
     */
    private boolean newMerchant;

    /**
     * 是否更换设备
     */
    private boolean deviceChanged;

    /**
     * 是否有卡交易（刷卡/插卡）
     */
    private boolean cardPresent;

    /**
     * 近 24 小时鉴权失败次数
     */
    private int failCount24h;

    /**
     * 账户开立天数
     */
    private int accountAgeDays;

    /**
     * 历史单笔最大金额
     */
    private double historicalMaxAmount;

    /**
     * 是否受信商户（白名单）
     */
    private boolean trustedMerchant;
}
