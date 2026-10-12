package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.SubsidyRules;
import com.github.paohaijiao.demo.model.SubsidyApplication;
import com.github.paohaijiao.demo.model.SubsidyDecision;

/**
 * 场景 11：政务人才补贴资格审核服务
 * <p>
 * 职责划分：
 * <ul>
 *     <li><b>Java 侧</b>：五维评分汇总、结论装配、分项金额汇总、审核说明组装</li>
 *     <li><b>规则侧</b>：评分口径、一票否决项、分级阈值、补贴定额，全部外置在 XML 中</li>
 * </ul>
 */
public class SubsidyService {

    private final SubsidyRules rules;

    public SubsidyService(SubsidyRules rules) {
        this.rules = rules;
    }

    /**
     * 审核人才补贴申请
     *
     * @param application 申请要素
     * @return 审核决策
     */
    public SubsidyDecision review(SubsidyApplication application) {
        SubsidyDecision decision = new SubsidyDecision();
        decision.setApplyNo(application.getApplyNo());

        int score = rules.scoreEducation(application.getEducation())
                + rules.scoreAge(application.getAge())
                + rules.scoreSocialSecurity(application.getLocalSocialSecurityMonths())
                + rules.scoreTalent(application.getTalentCategory())
                + rules.scoreStartup(application.isStartupRegistered(), application.getStartupEmployees());

        boolean hardReject = rules.hardReject(application.isCreditViolation(),
                application.isCriminalRecord(), application.isReceivedSimilarSubsidy());
        String stage = rules.eligibilityStage(score, hardReject, application.isDocumentsComplete());

        decision.setQualificationScore(score);
        decision.setStage(stage);

        double educationAmount = rules.educationAmount(application.getEducation());
        double talentAmount = rules.talentAmount(application.getTalentCategory());
        double startupAmount = rules.startupAmount(application.isStartupRegistered(),
                application.getStartupEmployees());

        if ("QUALIFIED".equals(stage)) {
            decision.setEducationAmount(educationAmount);
            decision.setTalentAmount(talentAmount);
            decision.setStartupAmount(startupAmount);
            decision.setSubsidyAmount(educationAmount + talentAmount + startupAmount);
        }

        buildReasons(application, decision, hardReject);
        return decision;
    }

    private void buildReasons(SubsidyApplication application, SubsidyDecision decision, boolean hardReject) {
        if (application.isCreditViolation()) {
            decision.addReason("存在失信被执行记录，触发一票否决");
        }
        if (application.isCriminalRecord()) {
            decision.addReason("存在刑事犯罪记录，触发一票否决");
        }
        if (application.isReceivedSimilarSubsidy()) {
            decision.addReason("已享受同类补贴，不得重复申领");
        }
        if (hardReject) {
            return;
        }
        if (!application.isDocumentsComplete()) {
            decision.addReason("申报材料不齐全，退回补正后转人工复核");
            return;
        }
        decision.addReason("资格评分 " + decision.getQualificationScore() + " 分");
        if ("QUALIFIED".equals(decision.getStage())) {
            decision.addReason("审核通过，核定补贴 " + decision.getSubsidyAmount() + " 元");
        } else if ("MANUAL_REVIEW".equals(decision.getStage())) {
            decision.addReason("评分处于复核区间，转人工审核，暂不定额");
        } else {
            decision.addReason("资格评分未达补贴标准，驳回申请");
        }
    }
}
