package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.CityQualificationApplication;
import com.github.paohaijiao.demo.model.CityQualificationDecision;
import com.github.paohaijiao.demo.service.CityQualificationService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 21：落户 / 购房 / 购车资格多政策判定
 * <p>
 * 覆盖 6 组政策形态：人才通道高分落户、就业通道积分不足、投资通道临界复核、
 * 通道门槛与失信否决、购房年限与限购、购车普通与人才通道。
 */
public class CityQualificationServiceTest {

    private CityQualificationService service;

    @Before
    public void setUp() {
        CityQualificationRules rules = JQuickJava.create()
                .createApi(CityQualificationRules.class, "demo/city-qualification.xml");
        this.service = new CityQualificationService(rules);
    }

    /**
     * 博士高层次人才走人才引进通道，30 岁社保 12 月 → 95 分核准落户
     */
    @Test
    public void shouldApproveTopTalentSettlement() {
        CityQualificationApplication app = settleApplication("CQ2026001", "TALENT");
        app.setAge(30);
        app.setEducation("DOCTOR");
        app.setSocialSecurityMonths(12);
        app.setTalentTitle("TOP_TALENT");

        CityQualificationDecision decision = service.evaluate(app);

        Assert.assertEquals(95, decision.getPoints());
        Assert.assertEquals("APPROVE", decision.getResult());
        Assert.assertTrue(decision.getDetails().contains("人才称号积分: 30 / 30"));
        Assert.assertTrue(decision.getReasons().contains(
                "积分 95 分达到核准线（80 分），准予落户"));
    }

    /**
     * 本科就业人员 40 岁社保 24 月无称号 → 50 分，不满足落户线
     */
    @Test
    public void shouldRejectLowPointsEmployeeSettlement() {
        CityQualificationApplication app = settleApplication("CQ2026002", "EMPLOYEE");
        app.setAge(40);
        app.setEducation("BACHELOR");
        app.setSocialSecurityMonths(24);
        app.setTalentTitle("NONE");

        CityQualificationDecision decision = service.evaluate(app);

        Assert.assertEquals(50, decision.getPoints());
        Assert.assertEquals("REJECT", decision.getResult());
        Assert.assertTrue(decision.getReasons().contains("积分 50 分未达落户标准（60 分）"));
    }

    /**
     * 硕士企业主 42 岁，企业 3 年纳税 80 万 → 60 分恰入复核区间
     */
    @Test
    public void shouldReviewInvestmentSettlementAtThreshold() {
        CityQualificationApplication app = settleApplication("CQ2026003", "INVESTMENT");
        app.setAge(42);
        app.setEducation("MASTER");
        app.setSocialSecurityMonths(12);
        app.setTalentTitle("NONE");
        app.setCompanyYears(3);
        app.setEnterpriseTaxWan(80.0);

        CityQualificationDecision decision = service.evaluate(app);

        Assert.assertEquals(60, decision.getPoints());
        Assert.assertEquals("REVIEW", decision.getResult());
        Assert.assertTrue(decision.getDetails().contains("投资纳税积分: 10 / 20"));
        Assert.assertTrue(decision.getReasons().contains(
                "积分 60 分处于复核公示区间（60-79 分），转人工复核"));
    }

    /**
     * 通道门槛核验与失信一票否决
     */
    @Test
    public void shouldRejectChannelThresholdAndBadCredit() {
        CityQualificationApplication shortSocialSecurity = settleApplication("CQ2026004", "EMPLOYEE");
        shortSocialSecurity.setAge(30);
        shortSocialSecurity.setEducation("MASTER");
        shortSocialSecurity.setSocialSecurityMonths(3);
        CityQualificationDecision d1 = service.evaluate(shortSocialSecurity);
        Assert.assertEquals("REJECT", d1.getResult());
        Assert.assertTrue(d1.getReasons().contains(
                "就业落户人员在本市连续缴纳社保不满 6 个月，不符合通道基本条件"));

        CityQualificationApplication badCredit = settleApplication("CQ2026005", "TALENT");
        badCredit.setAge(32);
        badCredit.setEducation("DOCTOR");
        badCredit.setSocialSecurityMonths(48);
        badCredit.setTalentTitle("TOP_TALENT");
        badCredit.setBadCreditRecord(true);
        CityQualificationDecision d2 = service.evaluate(badCredit);
        Assert.assertEquals("REJECT", d2.getResult());
        Assert.assertTrue(d2.getReasons().contains("申请人存在严重失信记录，触发一票否决"));

        CityQualificationApplication noTitle = settleApplication("CQ2026006", "TALENT");
        noTitle.setAge(28);
        noTitle.setEducation("DOCTOR");
        noTitle.setSocialSecurityMonths(36);
        noTitle.setTalentTitle("NONE");
        CityQualificationDecision d3 = service.evaluate(noTitle);
        Assert.assertEquals("REJECT", d3.getResult());
        Assert.assertTrue(d3.getReasons().contains("人才引进通道须持有高层次或紧缺人才认定证明"));
    }

    /**
     * 购房资格：社保满 36 月无房通过；已有房产被限购；缴纳不足被拒
     */
    @Test
    public void shouldCheckHousePurchaseEligibilityAndLimit() {
        CityQualificationApplication eligible = houseApplication("CQ2026007");
        eligible.setSocialSecurityMonths(40);
        CityQualificationDecision d1 = service.evaluate(eligible);
        Assert.assertEquals("ALLOW", d1.getResult());
        Assert.assertTrue(d1.getReasons().contains("满足非本市户籍家庭购房缴纳年限要求，具备购房资格"));

        CityQualificationApplication ownsProperty = houseApplication("CQ2026008");
        ownsProperty.setSocialSecurityMonths(60);
        ownsProperty.setHasPropertyInCity(true);
        CityQualificationDecision d2 = service.evaluate(ownsProperty);
        Assert.assertEquals("REJECT", d2.getResult());
        Assert.assertTrue(d2.getReasons().contains(
                "非本市户籍家庭在本市已拥有 1 套住房，受限购政策约束"));

        CityQualificationApplication insufficient = houseApplication("CQ2026009");
        insufficient.setSocialSecurityMonths(12);
        CityQualificationDecision d3 = service.evaluate(insufficient);
        Assert.assertEquals("REJECT", d3.getResult());
        Assert.assertTrue(d3.getReasons().contains(
                "社保或个税连续缴纳不满 36 个月，且不符合人才放宽条件"));
    }

    /**
     * 购车指标：普通通道 24 月通过、12 月进入摇号；紧缺人才 8 月走快速通道
     */
    @Test
    public void shouldCheckCarQuotaForNormalAndTalentChannels() {
        CityQualificationApplication normal = carApplication("CQ2026010");
        normal.setSocialSecurityMonths(24);
        Assert.assertEquals("ALLOW", service.evaluate(normal).getResult());

        CityQualificationApplication tooShort = carApplication("CQ2026011");
        tooShort.setSocialSecurityMonths(12);
        CityQualificationDecision d2 = service.evaluate(tooShort);
        Assert.assertEquals("REJECT", d2.getResult());
        Assert.assertTrue(d2.getReasons().contains("不满足新能源指标直接申请条件，需参与普通指标摇号"));

        CityQualificationApplication elite = carApplication("CQ2026012");
        elite.setSocialSecurityMonths(8);
        elite.setTalentTitle("ELITE");
        Assert.assertEquals("ALLOW", service.evaluate(elite).getResult());

        CityQualificationApplication alreadyHasCar = carApplication("CQ2026013");
        alreadyHasCar.setSocialSecurityMonths(36);
        alreadyHasCar.setHasCarInCity(true);
        CityQualificationDecision d4 = service.evaluate(alreadyHasCar);
        Assert.assertEquals("REJECT", d4.getResult());
        Assert.assertTrue(d4.getReasons().contains("申请人名下已登记本市小客车，不能重复申领新能源指标"));
    }

    private CityQualificationApplication settleApplication(String id, String channel) {
        CityQualificationApplication app = new CityQualificationApplication();
        app.setApplicationId(id);
        app.setApplicantName("申请人");
        app.setBenefit("SETTLE");
        app.setChannel(channel);
        app.setAge(30);
        app.setEducation("OTHER");
        app.setSocialSecurityMonths(0);
        app.setTaxPaymentMonths(0);
        app.setHasPropertyInCity(false);
        app.setHasCarInCity(false);
        app.setCompanyYears(0);
        app.setEnterpriseTaxWan(0.0);
        app.setTalentTitle("NONE");
        app.setBadCreditRecord(false);
        return app;
    }

    private CityQualificationApplication houseApplication(String id) {
        CityQualificationApplication app = settleApplication(id, "EMPLOYEE");
        app.setBenefit("HOUSE_PURCHASE");
        return app;
    }

    private CityQualificationApplication carApplication(String id) {
        CityQualificationApplication app = settleApplication(id, "EMPLOYEE");
        app.setBenefit("CAR_PURCHASE");
        return app;
    }
}
