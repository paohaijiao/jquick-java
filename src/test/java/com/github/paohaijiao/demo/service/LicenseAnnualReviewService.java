package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.LicenseAnnualReviewRules;
import com.github.paohaijiao.demo.model.LicenseReviewApplication;
import com.github.paohaijiao.demo.model.LicenseReviewDecision;

/**
 * 场景 24：行业许可证年审合规校验服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：三条红线前置驳回、八维得分汇总、不合规项逐项告知</li>
 *     <li><b>规则侧</b>：八维计分口径与通过/附条件/不通过阈值，外置在 XML 中</li>
 * </ul>
 */
public class LicenseAnnualReviewService {

    private final LicenseAnnualReviewRules rules;

    public LicenseAnnualReviewService(LicenseAnnualReviewRules rules) {
        this.rules = rules;
    }

    public LicenseReviewDecision review(LicenseReviewApplication app) {
        LicenseReviewDecision decision = new LicenseReviewDecision();
        decision.setLicenseId(app.getLicenseId());

        if (!app.isValidWithinPeriod()) {
            decision.setResult("REJECT");
            decision.addNonConformity("许可证已超过有效期，须重新申领后再经营");
            return decision;
        }
        if (!app.isBusinessLicenseValid()) {
            decision.setResult("REJECT");
            decision.addNonConformity("营业执照失效或被吊销，不予年审");
            return decision;
        }
        if (app.getMajorViolationCount() >= 1) {
            decision.setResult("REJECT");
            decision.addNonConformity("年度内存在 " + app.getMajorViolationCount()
                    + " 次重大食品安全违法违规记录，不予年审");
            return decision;
        }

        int health = app.getHealthCertificateTotal() <= 0
                ? 0
                : rules.scoreHealth(app.getHealthCertificateExpired(), app.getHealthCertificateTotal());
        int training = rules.scoreTraining(app.getTrainingRate());
        int officer = rules.scoreOfficer(app.isFoodSafetyOfficer());
        int defects = rules.scoreDefects(app.getDefectCount());
        int rectification = rules.scoreRectification(app.isRectificationCompleted());
        int complaints = rules.scoreComplaints(app.getComplaintCount());
        int environment = rules.scoreEnvironment(app.getEnvironmentalRating());
        int insurance = rules.scoreInsurance(app.isLiabilityInsurance());
        int total = health + training + officer + defects + rectification
                + complaints + environment + insurance;

        decision.addScoreDetail("健康证管理: " + health + " / 20");
        decision.addScoreDetail("食安培训: " + training + " / 15");
        decision.addScoreDetail("食安员配备: " + officer + " / 10");
        decision.addScoreDetail("检查缺陷项: " + defects + " / 15");
        decision.addScoreDetail("问题整改: " + rectification + " / 10");
        decision.addScoreDetail("投诉处置: " + complaints + " / 10");
        decision.addScoreDetail("环境卫生: " + environment + " / 10");
        decision.addScoreDetail("食安责任险: " + insurance + " / 10");
        decision.addScoreDetail("合规总分: " + total);
        decision.setTotalScore(total);
        decision.setResult(rules.reviewLevel(total));

        if (!"PASS".equals(decision.getResult())) {
            buildNonConformities(app, decision);
        }
        return decision;
    }

    private void buildNonConformities(LicenseReviewApplication app, LicenseReviewDecision decision) {
        if (app.getHealthCertificateTotal() <= 0) {
            decision.addNonConformity("未建立从业人员健康证管理台账，须限期补建");
        } else if (app.getHealthCertificateExpired() > 0) {
            decision.addNonConformity(app.getHealthCertificateExpired() + " 名从业人员健康证已过期，须立即调离接触直接入口食品岗位");
        }
        if (app.getTrainingRate() < 80) {
            decision.addNonConformity("食品安全培训覆盖率 " + app.getTrainingRate() + "% 低于 80%，须补训补考");
        }
        if (!app.isFoodSafetyOfficer()) {
            decision.addNonConformity("未按规定配备食品安全总监或食品安全员");
        }
        if (app.getDefectCount() > 0) {
            decision.addNonConformity("日常监督检查发现 " + app.getDefectCount() + " 项一般不符合项，须逐项整改");
        }
        if (!app.isRectificationCompleted()) {
            decision.addNonConformity("上年度不符合项未完成整改闭环，须提交整改报告");
        }
        if (app.getComplaintCount() > 0) {
            decision.addNonConformity("年内有效消费投诉 " + app.getComplaintCount() + " 件，须优化处置时效");
        }
        if ("C".equals(app.getEnvironmentalRating())) {
            decision.addNonConformity("环境卫生量化等级为 C 级，须停业整顿后复查");
        }
        if (!app.isLiabilityInsurance()) {
            decision.addNonConformity("建议投保食品安全责任保险，完善风险分担机制");
        }
    }
}
