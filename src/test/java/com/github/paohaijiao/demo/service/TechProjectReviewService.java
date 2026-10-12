package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.TechProjectReviewRules;
import com.github.paohaijiao.demo.model.ProjectReviewDecision;
import com.github.paohaijiao.demo.model.TechProjectApplication;

/**
 * 场景 20：科技项目申报材料打分初审服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：五维评分汇总、结论装配、资助定额挂接、评审说明组装</li>
 *     <li><b>规则侧</b>：五维评分口径、结论阈值、分类资助定额，全部外置在 XML 中</li>
 * </ul>
 */
public class TechProjectReviewService {

    private final TechProjectReviewRules rules;

    public TechProjectReviewService(TechProjectReviewRules rules) {
        this.rules = rules;
    }

    public ProjectReviewDecision review(TechProjectApplication app) {
        ProjectReviewDecision decision = new ProjectReviewDecision();
        decision.setProjectId(app.getProjectId());

        int tech = rules.scoreTech(app.getTechLevel(), app.getInventionPatents());
        int industrial = rules.scoreIndustrialization(app.getPilotStage(), app.getAnnualRevenueWan());
        int team = rules.scoreTeam(app.getLeaderTitle(), app.getPhdCount());
        int budget = rules.scoreBudget(app.getRdRatio(), app.getSelfFundedRatio());
        int social = rules.scoreSocial(app.getEmploymentIncrease(), app.getEnergySavingTon());
        int total = tech + industrial + team + budget + social;

        decision.addScoreDetail("技术先进性: " + tech + " / 35");
        decision.addScoreDetail("产业化成熟度: " + industrial + " / 25");
        decision.addScoreDetail("团队实力: " + team + " / 15");
        decision.addScoreDetail("经费预算合理性: " + budget + " / 15");
        decision.addScoreDetail("社会效益: " + social + " / 10");
        decision.addScoreDetail("汇总评分: " + total);
        decision.setTotalScore(total);

        String result = rules.reviewResult(total, app.isMajorViolation(), app.isMaterialsComplete());
        decision.setResult(result);

        if ("APPROVE".equals(result)) {
            decision.setGrantAmountWan(rules.grantAmountWan(app.getProjectCategory(), total));
        }
        buildReasons(app, decision);
        return decision;
    }

    private void buildReasons(TechProjectApplication app, ProjectReviewDecision decision) {
        if (app.isMajorViolation()) {
            decision.addReason("申报主体近三年存在重大违法违规记录，不予立项");
            return;
        }
        if (!app.isMaterialsComplete()) {
            decision.addReason("申报材料不齐全，须补正后重新提交初审");
            return;
        }
        if ("APPROVE".equals(decision.getResult())) {
            decision.addReason("综合评分 " + decision.getTotalScore()
                    + " 分达到立项线，拟立项支持，核定资助额度 "
                    + decision.getGrantAmountWan() + " 万元");
        } else if ("REVIEW".equals(decision.getResult())) {
            decision.addReason("综合评分处于答辩复评区间（65-79 分），须组织专家答辩");
        } else {
            decision.addReason("综合评分未达立项标准（65 分），不予立项");
        }
    }
}
