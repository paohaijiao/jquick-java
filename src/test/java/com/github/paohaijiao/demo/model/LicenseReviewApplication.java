package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 食品经营许可证年审申报要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseReviewApplication {

    /**
     * 许可证编号
     */
    private String licenseId;

    /**
     * 经营主体名称
     */
    private String enterpriseName;

    /**
     * 许可证是否在有效期内
     */
    private boolean validWithinPeriod;

    /**
     * 营业执照是否有效
     */
    private boolean businessLicenseValid;

    /**
     * 健康证过期人数
     */
    private int healthCertificateExpired;

    /**
     * 应持健康证总人数
     */
    private int healthCertificateTotal;

    /**
     * 食品安全培训覆盖率（整数百分比）
     */
    private int trainingRate;

    /**
     * 是否按规定配备食品安全员
     */
    private boolean foodSafetyOfficer;

    /**
     * 日常监督检查一般不符合项数
     */
    private int defectCount;

    /**
     * 上年度不符合项是否完成整改
     */
    private boolean rectificationCompleted;

    /**
     * 年内有效消费投诉件数
     */
    private int complaintCount;

    /**
     * 环境卫生量化等级：A / B / C
     */
    private String environmentalRating;

    /**
     * 是否投保食品安全责任险
     */
    private boolean liabilityInsurance;

    /**
     * 年度重大违法违规次数
     */
    private int majorViolationCount;
}
