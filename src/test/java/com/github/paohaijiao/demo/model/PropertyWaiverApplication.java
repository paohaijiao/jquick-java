package com.github.paohaijiao.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业主物业费/停车费减免申请要素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyWaiverApplication {

    /**
     * 户号
     */
    private String householdId;

    private String ownerName;

    /**
     * 家庭类型：SUBSISTENCE 低保特困 / MARTYR 烈属优抚 / DISABLED 重度残疾 /
     * LOW_INCOME 低保边缘 / ELDERLY_ALONE 独居老人 / ORDINARY 普通业主
     */
    private String householdType;

    /**
     * 产权面积（㎡）
     */
    private double propertySize;

    /**
     * 家庭常住人口数
     */
    private int familySize;

    /**
     * 人均建筑面积（㎡）
     */
    private double perCapitaArea;

    /**
     * 物业费单价（元/㎡/月）
     */
    private double monthlyFeePerSqm;

    /**
     * 申请减免月数
     */
    private int monthsApplied;

    /**
     * 登记车位数
     */
    private int parkingSpaceCount;

    /**
     * 单个车位月停车费
     */
    private double monthlyParkingFee;

    /**
     * 房屋是否空置（未入住毛坯/长期空置，普通业主按 70% 缴纳）
     */
    private boolean vacant;

    /**
     * 是否出租（减免仅限自住）
     */
    private boolean rentingOut;

    /**
     * 是否存在违法建设
     */
    private boolean illegalConstruction;

    /**
     * 历史欠费月数
     */
    private int overdueMonths;

    /**
     * 是否存在电动车飞线充电/进梯入户等安全违规
     */
    private boolean parkingSafetyViolation;
}
