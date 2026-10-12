package com.github.paohaijiao.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 奖学金评定决策
 */
@Data
public class ScholarshipDecision {

    /**
     * 学号
     */
    private String studentId;

    /**
     * 是否具备参评资格
     */
    private boolean eligible;

    /**
     * 综合测评得分
     */
    private int evaluationScore;

    /**
     * 获奖等级：
     * NATIONAL_SPECIAL 国家奖学金 / NATIONAL_ENDEAVOR 国家励志奖学金 /
     * FIRST/SECOND/THIRD 学业一/二/三等 /
     * FIRST_GRANT/SECOND_GRANT 特困/困难助学金 /
     * INNOVATION 科研创新单项 / COMP_PROVINCIAL 省级竞赛单项 /
     * VOLUNTEER 志愿服务单项 / NONE 未获奖
     */
    private String scholarshipLevel;

    /**
     * 奖励金额（元）
     */
    private int rewardAmount;

    /**
     * 退回/落选原因
     */
    private String rejectReason;

    /**
     * 附加荣誉
     */
    private List<String> honors = new ArrayList<String>();

    public void addHonor(String honor) {
        this.honors.add(honor);
    }
}
