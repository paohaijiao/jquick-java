package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.HealthScreeningRules;
import com.github.paohaijiao.demo.model.HealthScreeningDecision;
import com.github.paohaijiao.demo.model.HealthScreeningReport;

/**
 * 场景 33：体检多指标异常筛查、风险标注与就诊指引
 */
public class HealthScreeningService {

    private final HealthScreeningRules rules;

    public HealthScreeningService(HealthScreeningRules rules) {
        this.rules = rules;
    }

    public HealthScreeningDecision screen(HealthScreeningReport r) {
        HealthScreeningDecision d = new HealthScreeningDecision();
        d.setReportId(r.getReportId());

        String bp = higher(rules.systolicLevel(r.getSystolicBp()),
                rules.diastolicLevel(r.getDiastolicBp()));
        String glucose = rules.glucoseLevel(r.getFastingGlucose());
        String cholesterol = rules.cholesterolLevel(r.getTotalCholesterol());
        String bmi = rules.bmiLevel(r.getBmi());
        String heartRate = rules.heartRateLevel(r.getHeartRate());
        int altRank = rules.altLevelRank(r.getAlt());
        String alt = rankToLevel(altRank);
        String creatinine = rules.creatinineLevel(r.getCreatinine(), r.getGender());

        addItem(d, "血压 " + r.getSystolicBp() + "/" + r.getDiastolicBp() + "mmHg", bp,
                "家庭监测血压，低盐饮食并控制体重", "立即急诊排查高血压急症");
        addItem(d, "空腹血糖 " + r.getFastingGlucose() + "mmol/L", glucose,
                "控制精制糖摄入并增加有氧运动", "尽快到内分泌科明确糖尿病诊断");
        addItem(d, "总胆固醇 " + r.getTotalCholesterol() + "mmol/L", cholesterol,
                "减少饱和脂肪摄入，3 个月后复查血脂", "低脂饮食并到心内科评估调脂治疗");
        addItem(d, "体质指数 " + r.getBmi(), bmi,
                "通过膳食与运动把 BMI 控制在 18.5-24", "到营养/减重门诊干预肥胖");
        addItem(d, "静息心率 " + r.getHeartRate() + " 次/分", heartRate,
                "规律作息，避免浓茶咖啡后复查心电图", "尽快完善动态心电图检查");
        addItem(d, "谷丙转氨酶 " + r.getAlt() + "U/L", alt,
                "避免饮酒与熬夜，复查肝功能", "消化/肝病科排查肝炎、脂肪肝等病因");
        addItem(d, "血肌酐 " + r.getCreatinine() + "μmol/L", creatinine,
                "多饮水避免滥用止痛药，复查肾功能", "肾内科评估肾功能损伤");

        if (rank(bp) >= 2 && r.isSmokingHistory()) {
            d.addRiskTag("心血管高风险（血压异常合并吸烟史）");
        }
        if (rank(glucose) >= 2 && r.isFamilyHistory()) {
            d.addRiskTag("糖尿病高危人群（空腹血糖异常合并家族史）");
        }

        String highest = "NORMAL";
        for (String level : new String[]{bp, glucose, cholesterol, bmi, heartRate, alt, creatinine}) {
            if (rank(level) > rank(highest)) {
                highest = level;
            }
            if (!"NORMAL".equals(level)) {
                d.setAbnormalCount(d.getAbnormalCount() + 1);
            }
            if ("CRITICAL".equals(level)) {
                d.setCriticalCount(d.getCriticalCount() + 1);
            }
            if ("HIGH".equals(level)) {
                d.setHighCount(d.getHighCount() + 1);
            }
        }
        d.setHighestLevel(highest);

        d.setFollowUpDepartment(resolveDepartment(bp, heartRate, glucose, creatinine, alt, highest));
        appendFollowUpAdvice(d);
        return d;
    }

    private void appendFollowUpAdvice(HealthScreeningDecision d) {
        if (d.getCriticalCount() >= 1) {
            d.addAdvice("存在危急值指标，请于 3 日内到 " + d.getFollowUpDepartment() + " 就诊");
        } else if (d.getHighCount() >= 2) {
            d.addAdvice("多项指标明显异常，请于 2 周内到 " + d.getFollowUpDepartment() + " 复查");
        } else if (d.getHighCount() == 1) {
            d.addAdvice("单项指标明显异常，请于 1 个月内到 " + d.getFollowUpDepartment() + " 复查");
        } else if (d.getAbnormalCount() > 0) {
            d.addAdvice("指标处于临界区间，以生活方式干预为主，6 个月后复评");
        } else {
            d.addAdvice("本次体检各项指标正常，请保持良好生活习惯，按年度常规体检即可");
        }
    }

    private String resolveDepartment(String bp, String heartRate, String glucose,
                                     String creatinine, String alt, String highest) {
        if ("NORMAL".equals(highest)) {
            return "全科/健康管理科";
        }
        if (rank(bp) >= 2) {
            return "心血管内科";
        }
        if (rank(heartRate) >= 2) {
            return "心血管内科";
        }
        if (rank(glucose) >= 2) {
            return "内分泌科";
        }
        if ("HIGH".equals(creatinine)) {
            return "肾内科";
        }
        if ("HIGH".equals(alt)) {
            return "消化内科";
        }
        return "全科/健康管理科";
    }

    private void addItem(HealthScreeningDecision d, String label, String level,
                         String attentionAdvice, String highAdvice) {
        if ("NORMAL".equals(level)) {
            return;
        }
        String prefix;
        if ("CRITICAL".equals(level)) {
            prefix = "【危急】";
        } else if ("HIGH".equals(level)) {
            prefix = "【异常】";
        } else {
            prefix = "【关注】";
        }
        String body = "CRITICAL".equals(level) || "HIGH".equals(level) ? highAdvice : attentionAdvice;
        d.addAdvice(prefix + label + "：" + level + "。" + body);
    }

    private String higher(String a, String b) {
        return rank(a) >= rank(b) ? a : b;
    }

    private String rankToLevel(int rank) {
        if (rank >= 2) {
            return "HIGH";
        }
        if (rank == 1) {
            return "ATTENTION";
        }
        return "NORMAL";
    }

    private int rank(String level) {
        if ("CRITICAL".equals(level)) {
            return 3;
        }
        if ("HIGH".equals(level)) {
            return 2;
        }
        if ("ATTENTION".equals(level)) {
            return 1;
        }
        return 0;
    }
}
