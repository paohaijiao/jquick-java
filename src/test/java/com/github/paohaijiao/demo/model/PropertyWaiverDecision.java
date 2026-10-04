package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 物业/停车费减免决策
 */
@Data
public class PropertyWaiverDecision {

    /**
     * 户号
     */
    private String householdId;

    /**
     * 是否通过资格审核
     */
    private boolean eligible;

    /**
     * 否决原因码：RENTING 出租 / ILLEGAL 违建 / OVERDUE 长期欠费 / SAFETY 安全违规
     */
    private String rejectReason;

    /**
     * 计入减免的建筑面积（㎡，超出保障面积标准部分不免）
     */
    private double qualifiedArea;

    /**
     * 物业费减免比例（百分比）
     */
    private int propertyWaiverPercent;

    /**
     * 停车费减免比例（百分比）
     */
    private int parkingWaiverPercent;

    /**
     * 物业费减免金额
     */
    private double propertyWaiverAmount;

    /**
     * 停车费减免金额
     */
    private double parkingWaiverAmount;

    /**
     * 减免合计
     */
    private double totalWaiver;

    /**
     * 审核说明
     */
    private List<String> notes = new ArrayList<String>();

    public void addNote(String note) {
        this.notes.add(note);
    }
}
