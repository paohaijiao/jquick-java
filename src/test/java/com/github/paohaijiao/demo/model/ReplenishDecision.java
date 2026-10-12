package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 补货决策结果
 */
@Data
public class ReplenishDecision {

    /**
     * 商品编码
     */
    private String sku;

    /**
     * 补货提前期需求
     */
    private int demandDuringLeadTime;

    /**
     * 动态安全库存
     */
    private int safetyStockLevel;

    /**
     * 目标库存
     */
    private int targetStock;

    /**
     * 库存缺口
     */
    private int shortage;

    /**
     * 最终建议下单量（已考虑 MOQ、包装与上限）
     */
    private int orderQty;

    /**
     * 可售天数
     */
    private int stockDays;

    /**
     * 紧急度：CRITICAL / HIGH / MEDIUM / LOW
     */
    private String urgency;

    /**
     * 是否建议加急补货
     */
    private boolean suggestExpress;

    /**
     * 说明与建议
     */
    private List<String> notes = new ArrayList<String>();

    public void addNote(String note) {
        this.notes.add(note);
    }
}
