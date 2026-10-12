package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态定价决策结果
 */
@Data
public class PricingDecision {

    /**
     * 商品编码
     */
    private String sku;

    /**
     * 库龄分级：0-4
     */
    private int ageLevel;

    /**
     * 库龄基准折扣
     */
    private double baseDiscount;

    /**
     * 综合折扣（含周转、季节、临期、仓储修正）
     */
    private double finalDiscount;

    /**
     * 建议售价（元）
     */
    private double suggestedPrice;

    /**
     * 清仓等级：DEEP / MEDIUM / LIGHT / NONE
     */
    private String clearanceLevel;

    /**
     * 促销方式：FLASH_SALE / BUNDLE / COUPON / MEMBER_PRICE / NONE
     */
    private String promotionType;

    /**
     * 是否建议清仓
     */
    private boolean needClearance;

    /**
     * 预计毛利率
     */
    private double grossMarginRate;

    /**
     * 定价说明，供运营与商品同学参考
     */
    private List<String> notes = new ArrayList<String>();

    public void addNote(String note) {
        this.notes.add(note);
    }
}
