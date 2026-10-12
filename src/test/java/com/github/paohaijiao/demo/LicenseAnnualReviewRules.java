package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 24：行业许可证（食品经营）年审合规评分规则集
 * <p>
 * 八个维度正向计分，满分 100：健康证 20 / 培训 15 / 食安员 10 / 缺陷项 15
 * / 整改 10 / 投诉 10 / 环境等级 10 / 责任险 10；85 通过、70 附条件。
 * 规则脚本见 {@code src/test/resources/demo/license-annual-review.xml}。
 */
public interface LicenseAnnualReviewRules {

    /** 健康证合规得分：零过期满分，过期比例 10%/30% 两段，交叉相乘避免整数除法 */
    int scoreHealth(@Param("expired") int expired,
                    @Param("total") int total);

    /** 食安培训覆盖率得分：100% 满分，80%/60% 两段 */
    int scoreTraining(@Param("rate") int rate);

    /** 是否配备食品安全员 */
    int scoreOfficer(@Param("hasOfficer") boolean hasOfficer);

    /** 日常检查缺陷项得分：0 项满分，2 项/5 项两段 */
    int scoreDefects(@Param("defectCount") int defectCount);

    /** 上年度问题整改完成情况 */
    int scoreRectification(@Param("completed") boolean completed);

    /** 有效投诉得分：0 件满分，3 件/10 件两段 */
    int scoreComplaints(@Param("complaintCount") int complaintCount);

    /** 环境卫生量化等级得分 */
    int scoreEnvironment(@Param("rating") String rating);

    /** 食品安全责任险投保情况（未投保仍得基础分 5） */
    int scoreInsurance(@Param("hasInsurance") boolean hasInsurance);

    /** 年审结论：85 分通过，70 分附条件，其余不予通过 */
    String reviewLevel(@Param("totalScore") int totalScore);
}
