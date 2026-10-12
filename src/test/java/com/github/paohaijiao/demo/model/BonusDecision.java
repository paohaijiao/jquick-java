package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 绩效奖金决策结果
 */
@Data
public class BonusDecision {

    /**
     * 工号
     */
    private String employeeId;

    /**
     * 加权总分（0-100）
     */
    private double totalScore;

    /**
     * 绩效等级：S / A / B / C / D
     */
    private String grade;

    /**
     * 最终奖金系数
     */
    private double bonusCoefficient;

    /**
     * 奖金金额（元）
     */
    private double bonusAmount;

    /**
     * 考勤等级：0-3
     */
    private int attendanceLevel;

    /**
     * 是否具备晋升资格
     */
    private boolean promotionEligible;

    /**
     * 是否进入绩效改进计划
     */
    private boolean needPip;

    /**
     * 说明与建议
     */
    private List<String> remarks = new ArrayList<String>();

    public void addRemark(String remark) {
        this.remarks.add(remark);
    }
}
