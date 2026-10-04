package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.BudgetAlertRules;
import com.github.paohaijiao.demo.model.BudgetAlertDecision;
import com.github.paohaijiao.demo.model.BudgetStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 场景 37：部门预算超支预警与单笔支出处置
 */
public class BudgetAlertService {

    private final BudgetAlertRules rules;

    public BudgetAlertService(BudgetAlertRules rules) {
        this.rules = rules;
    }

    public BudgetAlertDecision evaluate(BudgetStatus s) {
        BudgetAlertDecision d = new BudgetAlertDecision();
        d.setDeptCode(s.getDeptCode());

        String level = rules.utilizationLevel(
                s.getActualSpent(), s.getCommittedAmount(), s.getAnnualBudget());
        d.setUtilizationLevel(level);
        int percent = (int) Math.round((s.getActualSpent() + s.getCommittedAmount())
                * 100.0 / s.getAnnualBudget());
        d.setUtilizationPercent(percent);

        String burn = rules.burnStatus(s.getActualSpent(), s.getAnnualBudget(), s.getMonth());
        d.setBurnStatus(burn);

        String forecast = rules.forecastStatus(s.getForecastYearEnd(), s.getAnnualBudget());
        d.setForecastStatus(forecast);
        double overrun = s.getForecastYearEnd() > s.getAnnualBudget()
                ? s.getForecastYearEnd() - s.getAnnualBudget() : 0.0;
        d.setOverrunAmount(BigDecimal.valueOf(overrun).setScale(2, RoundingMode.HALF_UP).doubleValue());

        boolean overFivePercent = s.getSingleExpenseAmount() * 100 > s.getAnnualBudget() * 5;
        String action = rules.singleExpenseAction(level, s.getExpenseCategory(),
                s.isEmergency(), s.isCapexApproved(), overFivePercent);
        d.setSingleExpenseAction(action);
        d.setRiskScore(rules.riskScore(level, burn, forecast));

        attachAlerts(s, d, level, percent, burn, forecast, overrun, action);
        return d;
    }

    private void attachAlerts(BudgetStatus s, BudgetAlertDecision d,
                              String level, int percent, String burn,
                              String forecast, double overrun, String action) {
        if ("RED".equals(level)) {
            d.addAlert("预算占用率已达 " + percent + "%，进入超支状态，冻结非应急支出");
        } else if ("ORANGE".equals(level)) {
            d.addAlert("预算占用率 " + percent + "%，超过 90% 橙色预警线");
        } else if ("YELLOW".equals(level)) {
            d.addAlert("预算占用率 " + percent + "%，超过 70% 关注线，请控制支出节奏");
        }
        if ("OVER_BURN".equals(burn)) {
            d.addAlert("支出进度显著快于时间进度（偏差超 15%），存在年末超支风险");
        } else if ("UNDER_BURN".equals(burn)) {
            d.addAlert("预算执行严重滞后（偏差超 15%），请排查项目停滞或采购积压");
        }
        if ("SEVERE".equals(forecast)) {
            d.addAlert("年末预测严重超支，超预算金额 " + overrun + " 元（≥10%），须上报预算调整");
        } else if ("MILD".equals(forecast)) {
            d.addAlert("年末预测轻度超支，超预算金额 " + overrun + " 元");
        }
        if ("BLOCK".equals(action)) {
            if ("CAPEX".equals(s.getExpenseCategory())) {
                d.addAlert("单笔 " + s.getSingleExpenseAmount()
                        + " 元资本性支出未完成立项，系统拦截");
            } else {
                d.addAlert("预算已超支，单笔 " + s.getSingleExpenseAmount()
                        + " 元非应急支出系统拦截");
            }
        } else if ("ESCALATE".equals(action)) {
            d.addAlert("单笔 " + s.getSingleExpenseAmount()
                    + " 元支出升级至财务总监/分管领导专项审批");
        }
    }
}
