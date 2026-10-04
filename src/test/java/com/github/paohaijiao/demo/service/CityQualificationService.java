package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.CityQualificationRules;
import com.github.paohaijiao.demo.model.CityQualificationApplication;
import com.github.paohaijiao.demo.model.CityQualificationDecision;

/**
 * 场景 21：落户 / 购房 / 购车资格多政策判定服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：事项路由、失信一票否决、落户通道门槛、限购核验、积分汇总</li>
 *     <li><b>规则侧</b>：五维积分口径、落户分档线、购房购车缴纳月数门槛，全部外置在 XML 中</li>
 * </ul>
 */
public class CityQualificationService {

    private final CityQualificationRules rules;

    public CityQualificationService(CityQualificationRules rules) {
        this.rules = rules;
    }

    public CityQualificationDecision evaluate(CityQualificationApplication app) {
        CityQualificationDecision decision = new CityQualificationDecision();
        decision.setApplicationId(app.getApplicationId());
        decision.setBenefit(app.getBenefit());

        if (app.isBadCreditRecord()) {
            decision.setResult("REJECT");
            decision.addReason("申请人存在严重失信记录，触发一票否决");
            return decision;
        }

        if ("SETTLE".equals(app.getBenefit())) {
            evaluateSettle(app, decision);
        } else if ("HOUSE_PURCHASE".equals(app.getBenefit())) {
            evaluateHouse(app, decision);
        } else {
            evaluateCar(app, decision);
        }
        return decision;
    }

    private void evaluateSettle(CityQualificationApplication app, CityQualificationDecision decision) {
        if ("EMPLOYEE".equals(app.getChannel()) && app.getSocialSecurityMonths() < 6) {
            decision.setResult("REJECT");
            decision.addReason("就业落户人员在本市连续缴纳社保不满 6 个月，不符合通道基本条件");
            return;
        }
        if ("INVESTMENT".equals(app.getChannel())
                && (app.getCompanyYears() < 1 || app.getEnterpriseTaxWan() < 10.0)) {
            decision.setResult("REJECT");
            decision.addReason("投资落户须创办企业满 1 年且年纳税不低于 10 万元");
            return;
        }
        if ("TALENT".equals(app.getChannel()) && "NONE".equals(app.getTalentTitle())) {
            decision.setResult("REJECT");
            decision.addReason("人才引进通道须持有高层次或紧缺人才认定证明");
            return;
        }

        int edu = rules.educationScore(app.getEducation());
        int security = rules.securityScore(app.getSocialSecurityMonths());
        int age = rules.ageSettleScore(app.getAge());
        int title = rules.titleScore(app.getTalentTitle());
        int invest = "INVESTMENT".equals(app.getChannel())
                ? rules.investTaxScore(app.getEnterpriseTaxWan()) : 0;
        int points = edu + security + age + title + invest;

        decision.addDetail("学历积分: " + edu + " / 40");
        decision.addDetail("社保积分: " + security + " / 40");
        decision.addDetail("年龄积分: " + age + " / 15");
        decision.addDetail("人才称号积分: " + title + " / 30");
        decision.addDetail("投资纳税积分: " + invest + " / 20");
        decision.addDetail("落户总积分: " + points);
        decision.setPoints(points);

        String result = rules.settleResult(points);
        decision.setResult(result);
        if ("APPROVE".equals(result)) {
            decision.addReason("积分 " + points + " 分达到核准线（80 分），准予落户");
        } else if ("REVIEW".equals(result)) {
            decision.addReason("积分 " + points + " 分处于复核公示区间（60-79 分），转人工复核");
        } else {
            decision.addReason("积分 " + points + " 分未达落户标准（60 分）");
        }
    }

    private void evaluateHouse(CityQualificationApplication app, CityQualificationDecision decision) {
        if (app.isHasPropertyInCity()) {
            decision.setResult("REJECT");
            decision.addReason("非本市户籍家庭在本市已拥有 1 套住房，受限购政策约束");
            return;
        }
        boolean eligible = rules.houseEligible(app.getSocialSecurityMonths(),
                app.getTaxPaymentMonths(), app.getTalentTitle());
        decision.addDetail("社保连续缴纳: " + app.getSocialSecurityMonths() + " 个月");
        decision.addDetail("个税连续缴纳: " + app.getTaxPaymentMonths() + " 个月");
        if (eligible) {
            decision.setResult("ALLOW");
            decision.addReason("满足非本市户籍家庭购房缴纳年限要求，具备购房资格");
        } else {
            decision.setResult("REJECT");
            decision.addReason("社保或个税连续缴纳不满 36 个月，且不符合人才放宽条件");
        }
    }

    private void evaluateCar(CityQualificationApplication app, CityQualificationDecision decision) {
        if (app.isHasCarInCity()) {
            decision.setResult("REJECT");
            decision.addReason("申请人名下已登记本市小客车，不能重复申领新能源指标");
            return;
        }
        boolean eligible = rules.carEligible(app.getSocialSecurityMonths(),
                app.getTaxPaymentMonths(), true, app.getTalentTitle());
        decision.addDetail("社保连续缴纳: " + app.getSocialSecurityMonths() + " 个月");
        decision.addDetail("个税连续缴纳: " + app.getTaxPaymentMonths() + " 个月");
        if (eligible) {
            decision.setResult("ALLOW");
            decision.addReason("满足新能源小客车指标申请条件，准予核发指标");
        } else {
            decision.setResult("REJECT");
            decision.addReason("不满足新能源指标直接申请条件，需参与普通指标摇号");
        }
    }
}
