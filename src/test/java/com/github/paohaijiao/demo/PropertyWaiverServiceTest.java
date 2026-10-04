package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.PropertyWaiverApplication;
import com.github.paohaijiao.demo.model.PropertyWaiverDecision;
import com.github.paohaijiao.demo.service.PropertyWaiverService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 39：物业费/停车费减免资格判定
 * <p>
 * 6 组形态：低保户物业停车全免、烈属全免无车位、低保边缘比例减免与车位限制、
 * 空置房 70% 缴纳、四类一票否决、独居老人超保障面积封顶。
 */
public class PropertyWaiverServiceTest {

    private PropertyWaiverService service;

    @Before
    public void setUp() {
        PropertyWaiverRules rules = JQuickJava.create()
                .createApi(PropertyWaiverRules.class, "demo/property-waiver.xml");
        this.service = new PropertyWaiverService(rules);
    }

    /**
     * 低保户 80 ㎡ 四口之家、6 个月、1 车位 → 物业停车全免 3000
     */
    @Test
    public void shouldFullyWaiveForSubsistenceHousehold() {
        PropertyWaiverApplication a = base("H001", "SUBSISTENCE");
        a.setPropertySize(80);
        a.setFamilySize(4);
        a.setPerCapitaArea(20);
        a.setMonthlyFeePerSqm(2.5);
        a.setMonthsApplied(6);
        a.setParkingSpaceCount(1);
        a.setMonthlyParkingFee(300);

        PropertyWaiverDecision d = service.review(a);

        Assert.assertTrue(d.isEligible());
        Assert.assertEquals(80.0, d.getQualifiedArea(), 0.0001);
        Assert.assertEquals(100, d.getPropertyWaiverPercent());
        Assert.assertEquals(100, d.getParkingWaiverPercent());
        Assert.assertEquals(1200.0, d.getPropertyWaiverAmount(), 0.0001);
        Assert.assertEquals(1800.0, d.getParkingWaiverAmount(), 0.0001);
        Assert.assertEquals(3000.0, d.getTotalWaiver(), 0.0001);
    }

    /**
     * 烈属 90 ㎡ 人均 30 ㎡ 不超标、全年、无车位 → 全免 2160
     */
    @Test
    public void shouldFullyWaivePropertyForMartyrWithoutParking() {
        PropertyWaiverApplication a = base("H002", "MARTYR");
        a.setPropertySize(90);
        a.setFamilySize(3);
        a.setPerCapitaArea(30);
        a.setMonthlyFeePerSqm(2);
        a.setMonthsApplied(12);
        a.setParkingSpaceCount(0);
        a.setMonthlyParkingFee(0);

        PropertyWaiverDecision d = service.review(a);

        Assert.assertTrue(d.isEligible());
        Assert.assertEquals(90.0, d.getQualifiedArea(), 0.0001);
        Assert.assertEquals(2160.0, d.getPropertyWaiverAmount(), 0.0001);
        Assert.assertEquals(0.0, d.getParkingWaiverAmount(), 0.0001);
        Assert.assertEquals(2160.0, d.getTotalWaiver(), 0.0001);
    }

    /**
     * 低保边缘 70 ㎡ 不超标、2 车位 → 物业 60%、1 个车位停车 50%
     */
    @Test
    public void shouldPartiallyWaiveForLowIncomeAndLimitOneParkingSpace() {
        PropertyWaiverApplication a = base("H003", "LOW_INCOME");
        a.setPropertySize(70);
        a.setFamilySize(4);
        a.setPerCapitaArea(17.5);
        a.setMonthlyFeePerSqm(2);
        a.setMonthsApplied(12);
        a.setParkingSpaceCount(2);
        a.setMonthlyParkingFee(200);

        PropertyWaiverDecision d = service.review(a);

        Assert.assertEquals(60, d.getPropertyWaiverPercent());
        Assert.assertEquals(70.0, d.getQualifiedArea(), 0.0001);
        Assert.assertEquals(1008.0, d.getPropertyWaiverAmount(), 0.0001);
        Assert.assertEquals(1200.0, d.getParkingWaiverAmount(), 0.0001);
        Assert.assertEquals(2208.0, d.getTotalWaiver(), 0.0001);
        Assert.assertTrue(join(d).contains("停车费减免仅限 1 个登记车位"));
    }

    /**
     * 普通业主 100 ㎡ 空置 6 个月 → 按整套减免 30%，停车不免
     */
    @Test
    public void shouldWaiveThirtyPercentForVacantApartment() {
        PropertyWaiverApplication a = base("H004", "ORDINARY");
        a.setPropertySize(100);
        a.setFamilySize(3);
        a.setPerCapitaArea(33.33);
        a.setMonthlyFeePerSqm(2);
        a.setMonthsApplied(6);
        a.setParkingSpaceCount(1);
        a.setMonthlyParkingFee(200);
        a.setVacant(true);

        PropertyWaiverDecision d = service.review(a);

        Assert.assertTrue(d.isEligible());
        Assert.assertEquals(100.0, d.getQualifiedArea(), 0.0001);
        Assert.assertEquals(30, d.getPropertyWaiverPercent());
        Assert.assertEquals(360.0, d.getPropertyWaiverAmount(), 0.0001);
        Assert.assertEquals(0.0, d.getParkingWaiverAmount(), 0.0001);
        Assert.assertTrue(join(d).contains("空置房物业费按应缴标准的 70% 收取"));
    }

    /**
     * 出租、违建、欠费 8 个月、飞线充电 → 四类一票否决
     */
    @Test
    public void shouldHardRejectFourVetoConditions() {
        String[][] cases = {
                {"LOW_INCOME", "RENTING"}, {"SUBSISTENCE", "ILLEGAL"},
                {"MARTYR", "OVERDUE"}, {"DISABLED", "SAFETY"}
        };
        for (String[] c : cases) {
            PropertyWaiverApplication a = base("H005", c[0]);
            a.setPropertySize(60);
            a.setFamilySize(2);
            a.setPerCapitaArea(30);
            a.setMonthlyFeePerSqm(2);
            a.setMonthsApplied(12);
            if ("RENTING".equals(c[1])) {
                a.setRentingOut(true);
            } else if ("ILLEGAL".equals(c[1])) {
                a.setIllegalConstruction(true);
            } else if ("OVERDUE".equals(c[1])) {
                a.setOverdueMonths(8);
            } else {
                a.setParkingSafetyViolation(true);
            }
            PropertyWaiverDecision d = service.review(a);
            Assert.assertFalse(c[0] + " 应被否决", d.isEligible());
            Assert.assertEquals(c[0] + " 否决原因码不符", c[1], d.getRejectReason());
            Assert.assertEquals(0.0, d.getTotalWaiver(), 0.0001);
        }
    }

    /**
     * 独居老人 60 ㎡（人均超 30 ㎡保障线）→ 仅 30 ㎡享 50% 减免
     */
    @Test
    public void shouldCapElderlyWaiverAtSecurityAreaStandard() {
        PropertyWaiverApplication a = base("H006", "ELDERLY_ALONE");
        a.setPropertySize(60);
        a.setFamilySize(1);
        a.setPerCapitaArea(60);
        a.setMonthlyFeePerSqm(2);
        a.setMonthsApplied(12);
        a.setParkingSpaceCount(0);

        PropertyWaiverDecision d = service.review(a);

        Assert.assertEquals(50, d.getPropertyWaiverPercent());
        Assert.assertEquals(30.0, d.getQualifiedArea(), 0.0001);
        Assert.assertEquals(360.0, d.getPropertyWaiverAmount(), 0.0001);
        Assert.assertTrue(join(d).contains("超出的 30.0 ㎡ 部分不予减免"));
    }

    private PropertyWaiverApplication base(String id, String type) {
        PropertyWaiverApplication a = new PropertyWaiverApplication();
        a.setHouseholdId(id);
        a.setOwnerName("测试业主");
        a.setHouseholdType(type);
        a.setPropertySize(0);
        a.setFamilySize(1);
        a.setPerCapitaArea(0);
        a.setMonthlyFeePerSqm(0);
        a.setMonthsApplied(0);
        a.setParkingSpaceCount(0);
        a.setMonthlyParkingFee(0);
        a.setVacant(false);
        a.setRentingOut(false);
        a.setIllegalConstruction(false);
        a.setOverdueMonths(0);
        a.setParkingSafetyViolation(false);
        return a;
    }

    private String join(PropertyWaiverDecision d) {
        StringBuilder sb = new StringBuilder();
        for (String n : d.getNotes()) {
            sb.append(n).append(';');
        }
        return sb.toString();
    }
}
