package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预售订单尾款支付与履约要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresaleOrder {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 商品编码
     */
    private String sku;

    /**
     * 已付定金（元）
     */
    private double depositAmount;

    /**
     * 应付尾款（元）
     */
    private double balanceAmount;

    /**
     * 尾款窗口状态：0 未开启 / 1 进行中 / 2 已结束（逾期）
     */
    private int windowStatus;

    /**
     * 是否在窗口开启前主动取消
     */
    private boolean cancelledBeforeWindow;

    /**
     * 本单购买件数
     */
    private int demandQty;

    /**
     * 当前现货库存
     */
    private int stockOnHand;

    /**
     * 已为预售锁定的库存
     */
    private int reservedStock;

    /**
     * 买家是否已实名认证
     */
    private boolean identityVerified;

    /**
     * 商家信用分（0-100）
     */
    private int merchantCreditScore;

    /**
     * 商家历史预售违约次数
     */
    private int merchantBreaches;

    /**
     * 商家是否已缴纳履约保证金
     */
    private boolean merchantBondPaid;
}
