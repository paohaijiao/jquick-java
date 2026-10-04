package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 售后申请要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AfterSalesRequest {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 商品编码
     */
    private String sku;

    /**
     * 商品类目：STANDARD 普通 / FRESH 生鲜 / CUSTOM 定制 / VIRTUAL 虚拟
     */
    private String productCategory;

    /**
     * 签收后天数
     */
    private int daysSinceReceived;

    /**
     * 商品现状：UNUSED 未使用 / USED 已使用 / DAMAGED 到货破损
     */
    private String productCondition;

    /**
     * 申请类型：QUALITY 质量问题 / SEVEN_DAY 七天无理由 / SIZE 尺码不符 / MISMATCH 描述不符 / CHANGE_MIND 多拍不想要
     */
    private String reasonType;

    /**
     * 是否提供质量问题凭证
     */
    private boolean qualityProof;

    /**
     * 是否商家责任（如发错货）
     */
    private boolean shopResponsible;

    /**
     * 商品密封/包装是否完好
     */
    private boolean sealedIntact;

    /**
     * 是否已产生影响二次销售的使用痕迹
     */
    private boolean impactResale;
}
