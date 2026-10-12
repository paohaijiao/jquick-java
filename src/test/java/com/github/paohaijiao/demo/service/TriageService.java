package com.github.paohaijiao.demo.service;

import com.github.paohaijiao.demo.TriageRules;
import com.github.paohaijiao.demo.model.TriageDecision;
import com.github.paohaijiao.demo.model.TriageRequest;

/**
 * 场景 34：门诊/急诊预检四级分诊
 */
public class TriageService {

    private final TriageRules rules;

    public TriageService(TriageRules rules) {
        this.rules = rules;
    }

    public TriageDecision triage(TriageRequest req) {
        TriageDecision d = new TriageDecision();
        d.setPatientId(req.getPatientId());

        int consciousCode = "UNRESPONSIVE".equals(req.getConsciousnessLevel()) ? 2
                : "DROWSY".equals(req.getConsciousnessLevel()) ? 1 : 0;
        int score = rules.lifeThreatScore(req.isVitalSignsAbnormal() ? 1 : 0,
                req.getOxygenSaturation(), consciousCode,
                req.isBleedingActive() ? 1 : 0,
                req.isBreathingDistress() ? 1 : 0);
        d.setLifeThreatScore(score);

        String level;
        if (score >= 4) {
            level = "I";
            d.addChannel("生命体征濒危，立即送抢救室并启动复苏团队");
        } else {
            level = classifyByComplaint(req, d);
        }

        applyZoneAndResponse(d, level);
        return d;
    }

    private String classifyByComplaint(TriageRequest req, TriageDecision d) {
        String complaint = req.getChiefComplaint();

        if ("CHEST_PAIN".equals(complaint)) {
            d.addChannel("胸痛中心绿色通道，10 分钟内完成首份心电图与肌钙蛋白采血");
            return "II";
        }

        if ("STROKE_SIGN".equals(complaint)) {
            if (rules.withinStrokeWindow(req.getOnsetHalfHours())) {
                d.addChannel("卒中绿色通道，发病在 4.5 小时静脉溶栓时间窗内，急查 CT 与凝血");
            } else {
                d.addChannel("卒中绿色通道，已超静脉溶栓时间窗，神经科即刻评估取栓指征");
            }
            return "II";
        }

        if ("FEVER".equals(complaint)) {
            String feverLevel = rules.feverLevel(req.getTemperature(), req.isInfant());
            if ("I".equals(feverLevel)) {
                d.addChannel("超高热 " + req.getTemperature() + "℃，立即抢救降温并排查感染性休克");
                return "I";
            }
            if ("II".equals(feverLevel)) {
                d.addChannel("3 岁以下婴幼儿 39℃ 以上高热，优先就诊并警惕热性惊厥");
                return "II";
            }
            return feverLevel;
        }

        if ("TRAUMA".equals(complaint)) {
            if (req.isBleedingActive()) {
                d.addChannel("外伤伴活动性出血，立即压迫止血并评估缝合/输血");
                return "II";
            }
            return "III";
        }

        if ("ABDOMINAL".equals(complaint)) {
            if (req.isPregnant()) {
                d.addChannel("孕产妇腹痛，启动孕产妇急救绿色通道并通知产科急会诊");
                return "II";
            }
            if (req.isBleedingActive()) {
                d.addChannel("腹痛伴活动性出血，立即评估消化道出血/宫外孕");
                return "II";
            }
            if (req.getAge() >= 65) {
                d.addChannel("65 岁以上老年腹痛，按高危腹痛优先排查血管性与外科急腹症");
                return "II";
            }
            return "III";
        }

        if (req.isPregnant() && req.isBleedingActive()) {
            d.addChannel("孕产妇出血，启动孕产妇急救绿色通道并通知产科");
            return "II";
        }

        return rules.complaintBaseLevel(complaint);
    }

    private void applyZoneAndResponse(TriageDecision d, String level) {
        d.setTriageLevel(level);
        if ("I".equals(level)) {
            d.setZone("RESCUE");
            d.setResponseMinutes(0);
        } else if ("II".equals(level)) {
            d.setZone("CRITICAL_BAY");
            d.setResponseMinutes(10);
        } else if ("III".equals(level)) {
            d.setZone("OBS");
            d.setResponseMinutes(30);
        } else {
            d.setZone("WAITING");
            d.setResponseMinutes(120);
        }
    }
}
