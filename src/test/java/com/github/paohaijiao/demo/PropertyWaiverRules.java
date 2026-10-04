package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 39：物业/停车费减免资格、比例与保障面积规则
 */
public interface PropertyWaiverRules {

    /**
     * 一票否决原因码：出租/违建/欠费超 6 个月/安全违规，其余 PASS
     */
    String hardRejectReason(@Param("renting") boolean renting,
                            @Param("illegal") boolean illegal,
                            @Param("overdueMonths") int overdueMonths,
                            @Param("safetyViolation") boolean safetyViolation);

    /**
     * 物业费减免比例：低保特困/烈属/重残 100%、边缘困难 60%、独居老人 50%、
     * 普通空置房 30%，其余 0
     */
    int propertyWaiverPercent(@Param("householdType") String householdType,
                              @Param("vacant") boolean vacant);

    /**
     * 停车费减免比例：低保特困/烈属/重残 100%（限 1 个车位）、边缘困难 50%，其余 0
     */
    int parkingWaiverPercent(@Param("householdType") String householdType);

    /**
     * 计入减免的面积：低保特困/烈属/重残/独居老人按人均 30 ㎡保障线、
     * 边缘困难按人均 20 ㎡，超出部分按家庭人口封顶；未超标按整套
     */
    double qualifiedArea(@Param("size") double size,
                         @Param("perCapita") double perCapita,
                         @Param("family") int family,
                         @Param("householdType") String householdType);
}
