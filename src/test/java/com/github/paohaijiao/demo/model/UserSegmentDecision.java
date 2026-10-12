package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户画像分层与营销策略决策
 */
@Data
public class UserSegmentDecision {

    /**
     * 用户号
     */
    private String userId;

    /**
     * 活跃分层：ACTIVE 活跃 / WARM 温客 / COLD 冷客 / DORMANT 沉睡
     */
    private String activitySegment;

    /**
     * 价值分层：HIGH_VALUE 高价值 / MID_VALUE 中价值 / LOW_VALUE 低价值 / NO_VALUE 无价值
     */
    private String valueSegment;

    /**
     * 价值评分（0-75）
     */
    private int valueScore;

    /**
     * 动态营销标签
     */
    private List<String> tags = new ArrayList<String>();

    /**
     * 营销策略
     */
    private String marketingStrategy;

    public void addTag(String tag) {
        this.tags.add(tag);
    }
}
