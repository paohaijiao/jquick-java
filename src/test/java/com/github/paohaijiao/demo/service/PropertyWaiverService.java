package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.PropertyWaiverRules;
import com.github.paohaijiao.demo.model.PropertyWaiverApplication;
import com.github.paohaijiao.demo.model.PropertyWaiverDecision;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 场景 39：物业费/停车费减免资格判定
 */
public class PropertyWaiverService {

    private final PropertyWaiverRules rules;

    public PropertyWaiverService(PropertyWaiverRules rules) {
        this.rules = rules;
    }

    public PropertyWaiverDecision review(PropertyWaiverApplication a) {
        PropertyWaiverDecision d = new PropertyWaiverDecision();
        d.setHouseholdId(a.getHouseholdId());

        String reject = rules.hardRejectReason(a.isRentingOut(), a.isIllegalConstruction(),
                a.getOverdueMonths(), a.isParkingSafetyViolation());
        if (!"PASS".equals(reject)) {
            d.setEligible(false);
            d.setRejectReason(reject);
            d.setQualifiedArea(0.0);
            return d;
        }
        d.setEligible(true);

        int propertyPercent = rules.propertyWaiverPercent(a.getHouseholdType(), a.isVacant());
        int parkingPercent = rules.parkingWaiverPercent(a.getHouseholdType());
        d.setPropertyWaiverPercent(propertyPercent);
        d.setParkingWaiverPercent(parkingPercent);

        double area;
        if ("ORDINARY".equals(a.getHouseholdType()) && a.isVacant()) {
            area = a.getPropertySize();
        } else {
            area = rules.qualifiedArea(a.getPropertySize(), a.getPerCapitaArea(),
                    a.getFamilySize(), a.getHouseholdType());
        }
        d.setQualifiedArea(money(area));
        if (area < a.getPropertySize()) {
            d.addNote("人均建筑面积超出保障标准，超出的 "
                    + money(a.getPropertySize() - area) + " ㎡ 部分不予减免");
        }

        double propertyAmount = money(area * a.getMonthlyFeePerSqm()
                * a.getMonthsApplied() * propertyPercent / 100.0);
        d.setPropertyWaiverAmount(propertyAmount);

        double parkingAmount = 0.0;
        if (a.getParkingSpaceCount() >= 1 && parkingPercent > 0) {
            parkingAmount = money(a.getMonthlyParkingFee() * a.getMonthsApplied()
                    * parkingPercent / 100.0);
            if (a.getParkingSpaceCount() > 1) {
                d.addNote("停车费减免仅限 1 个登记车位，其余 "
                        + (a.getParkingSpaceCount() - 1) + " 个车位正常缴纳");
            }
        }
        d.setParkingWaiverAmount(parkingAmount);
        d.setTotalWaiver(money(propertyAmount + parkingAmount));

        if ("ORDINARY".equals(a.getHouseholdType()) && a.isVacant()) {
            d.addNote("空置房物业费按应缴标准的 70% 收取（减免 30%），停车费不免");
        }
        return d;
    }

    private double money(double v) {
        return BigDecimal.valueOf(v).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
