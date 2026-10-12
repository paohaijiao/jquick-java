package com.github.paohaijiao.demo;

import com.github.paohaijiao.demo.model.SupplierAdmissionDecision;
import com.github.paohaijiao.demo.model.SupplierProfile;
import com.github.paohaijiao.demo.service.SupplierAdmissionService;
import com.github.paohaijiao.xml.JQuickJava;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 场景 18 生产级样板：供应链金融上游供应商准入评级
 * <p>
 * 覆盖 6 组供应商形态：S 级战略（额度触顶 1000 万）、A/B 级合格、
 * C 级附条件（担保+监管专户）、失信、重大违约、资质不全新供应商。
 */
public class SupplierAdmissionServiceTest {

    private SupplierAdmissionService service;

    @Before
    public void setUp() {
        SupplierAdmissionRules rules = JQuickJava.create()
                .createApi(SupplierAdmissionRules.class, "demo/supplier-admission.xml");
        this.service = new SupplierAdmissionService(rules);
    }

    /**
     * S 级战略供应商：100 分 A 级，理论保理 1600 万触单户上限核 1000 万、账期 90 天
     */
    @Test
    public void shouldAdmitStrategicSupplierWithCeiling() {
        SupplierProfile supplier = new SupplierProfile(
                "SUP001", "中盛新材料股份有限公司", 8, 3000, "S",
                80000000.0, 99.0, 99.2, 99.5, 60, "HEALTHY", false, false);

        SupplierAdmissionDecision decision = service.evaluate(supplier);

        Assert.assertEquals(100, decision.getTotalScore());
        Assert.assertEquals("A", decision.getGrade());
        Assert.assertEquals("APPROVE", decision.getDecision());
        Assert.assertEquals(10000000.0, decision.getFactoringLimit(), 0.001);
        Assert.assertFalse(decision.isRequireGuarantee());
        Assert.assertEquals(90, decision.getSettlementDays());
        Assert.assertTrue(decision.getScoreDetails().contains("履约记录评分: 30 / 30"));
    }

    /**
     * 合格供应商：78 分 B 级准入，保理 360 万、账期 60 天
     */
    @Test
    public void shouldAdmitQualifiedSupplier() {
        SupplierProfile supplier = new SupplierProfile(
                "SUP002", "广联五金制品有限公司", 5, 800, "A",
                30000000.0, 97.0, 98.0, 96.0, 24, "STABLE", false, false);

        SupplierAdmissionDecision decision = service.evaluate(supplier);

        Assert.assertEquals(78, decision.getTotalScore());
        Assert.assertEquals("B", decision.getGrade());
        Assert.assertEquals("APPROVE", decision.getDecision());
        Assert.assertEquals(3600000.0, decision.getFactoringLimit(), 0.001);
        Assert.assertEquals(60, decision.getSettlementDays());
        Assert.assertTrue(decision.getConditions().isEmpty());
    }

    /**
     * 限制类供应商：62 分 C 级附条件准入，需保证金、监管专户、账期缩至 30 天
     */
    @Test
    public void shouldAdmitConditionalSupplierWithGuarantee() {
        SupplierProfile supplier = new SupplierProfile(
                "SUP003", "鑫泰包装材料厂", 5, 800, "B",
                20000000.0, 96.0, 96.0, 94.0, 24, "WEAK", false, false);

        SupplierAdmissionDecision decision = service.evaluate(supplier);

        Assert.assertEquals(62, decision.getTotalScore());
        Assert.assertEquals("C", decision.getGrade());
        Assert.assertEquals("CONDITIONAL", decision.getDecision());
        Assert.assertEquals(1000000.0, decision.getFactoringLimit(), 0.001);
        Assert.assertTrue(decision.isRequireGuarantee());
        Assert.assertEquals(30, decision.getSettlementDays());
        Assert.assertTrue(decision.getConditions().contains(
                "附条件准入：需缴纳 10% 履约保证金或引入第三方担保"));
        Assert.assertTrue(decision.getConditions().contains(
                "财务状况偏弱，保理回款须进入核心企业监管专户封闭运行"));
    }

    /**
     * 失信被执行人：一票否决
     */
    @Test
    public void shouldRejectDishonestSupplier() {
        SupplierProfile supplier = new SupplierProfile(
                "SUP004", "失信供应商", 10, 5000, "A",
                60000000.0, 98.0, 98.0, 98.0, 48, "HEALTHY", true, false);

        SupplierAdmissionDecision decision = service.evaluate(supplier);

        Assert.assertEquals("REJECT", decision.getDecision());
        Assert.assertEquals("D", decision.getGrade());
        Assert.assertEquals(0.0, decision.getFactoringLimit(), 0.001);
        Assert.assertTrue(decision.getRejectReasons().contains(
                "供应商为失信被执行人，禁止准入并终止合作洽谈"));
    }

    /**
     * 近 2 年重大违约：暂停准入 24 个月
     */
    @Test
    public void shouldRejectMajorBreachSupplier() {
        SupplierProfile supplier = new SupplierProfile(
                "SUP005", "曾断供供应商", 7, 600, "B",
                15000000.0, 92.0, 90.0, 88.0, 30, "STABLE", false, true);

        SupplierAdmissionDecision decision = service.evaluate(supplier);

        Assert.assertEquals("REJECT", decision.getDecision());
        Assert.assertTrue(decision.getRejectReasons().contains(
                "近 2 年发生重大合同违约（断供/质量事故），暂停准入 24 个月"));
    }

    /**
     * 资质不全的新设供应商：20 分 D 级淘汰
     */
    @Test
    public void shouldRejectUnqualifiedNewSupplier() {
        SupplierProfile supplier = new SupplierProfile(
                "SUP006", "某个体户加工厂", 1, 50, "NONE",
                2000000.0, 88.0, 86.0, 85.0, 4, "RISKY", false, false);

        SupplierAdmissionDecision decision = service.evaluate(supplier);

        Assert.assertEquals(18, decision.getTotalScore());
        Assert.assertEquals("D", decision.getGrade());
        Assert.assertEquals("REJECT", decision.getDecision());
        Assert.assertTrue(decision.getRejectReasons().contains("综合评分 18 低于准入线 60，评为 D 级供应商"));
        Assert.assertEquals(0, decision.getSettlementDays());
    }
}
