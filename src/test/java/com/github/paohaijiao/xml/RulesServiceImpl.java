package com.github.paohaijiao.xml;



public class RulesServiceImpl  {


    public static void main(String[] args) {
        String eventType = "B";// 事件类型
        int reportMinutes = 20;// 报送时长，30分钟
        int replyMinutes = 3;// 协同回复时长，3分钟
        int closureDays = 1;// 办结时长，2天
        int dispatchScore = 9;// 调度响应分（人工打分）
        int qualityScore = 4;// 调度响应分（人工打分）
        RulesMapper scoringMapper = JQuickJava.create().importPackage("java.lang.String","str").createApi(RulesMapper.class, "rules.xml");

        int reportScore = scoringMapper.scoreReportTime(eventType,reportMinutes);

        double scoreCoordination = scoringMapper.scoreCoordination(eventType,replyMinutes);
        double scoreClosureDays = scoringMapper.scoreClosure(closureDays);
        double scoreTotalScore = scoringMapper.calculateSingleEventScore(eventType,reportMinutes,replyMinutes,closureDays,dispatchScore,qualityScore);
        System.out.println("单事件总得分: " + reportScore);
        System.out.println("单事件协同回复时长总得分: " + scoreCoordination);
        System.out.println("办结时长，2天: " + scoreClosureDays);
        System.out.println("scoreTotalScore: " + scoreTotalScore);
    }
}
