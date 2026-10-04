package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员账户与本期消费行为要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfile {

    /**
     * 会员号
     */
    private String memberId;

    /**
     * 会员姓名
     */
    private String memberName;

    /**
     * 当前等级：S1 注册 / S2 银卡 / S3 金卡 / S4 白金 / S5 黑金
     */
    private String currentLevel;

    /**
     * 期初累计成长值
     */
    private int growthValue;

    /**
     * 本期消费金额（元）
     */
    private int periodSpendYuan;

    /**
     * 本期成交订单数
     */
    private int periodOrders;

    /**
     * 本期退款率（整数百分比）
     */
    private int refundRate;

    /**
     * 等级有效期剩余天数
     */
    private int daysToExpire;

    /**
     * 违规套取权益次数
     */
    private int violationCount;
}
