package com.github.paohaijiao.xml;

import com.github.paohaijiao.service.ScoringMapper;
import org.junit.Before;
import org.junit.Test;

public class TEmEventScoringServiceTest {

    private ScoringMapper mapper;

    @Before
    public void setUp() {
        mapper = JQuickJava.create()
                .importPackage("java.lang.String", "str")
                .importPackage("java.util.Date", "JDate")
                .createApi(ScoringMapper.class, "scoring-rules.xml");
    }

    private ScoringMapper createMapper() {
        return JQuickJava.create()
                .importPackage("java.lang.String", "str")
                .importPackage("java.util.Date", "JDate")
                .createApi(ScoringMapper.class, "scoring-rules.xml");
    }
    @Test
    public void quickSubmissionReport() {

        //企业基本资质
        int basicScore = 0;
        int  scoreCapital= mapper.scoreCapital(500, 500);   // 实缴500万，注册500万 -> 8分
        assert scoreCapital == 8 : "实缴500万，注册500万，资本得分应为8分，实际为" + scoreCapital;
        int scoreEstablishment= mapper.scoreEstablishment(8);     // 成立8年 -> 4分
        assert scoreEstablishment == 4 : "成立8年，成立年限得分应为4分，实际为" + scoreEstablishment;
        int scoreCertification= mapper.scoreCertification("甲级/一级/高新"); // 高新 -> 6分
        assert scoreCertification == 6 : "高新，资质得分应为6分，实际为" + scoreCertification;
        basicScore=scoreCapital+scoreEstablishment+scoreCertification;
        //基本资质得分验证
        assert basicScore == 18 : "基本资质得分应为18分，实际为" + basicScore;
        assert basicScore >= 0 && basicScore <= 20 : "基本资质得分应在0-20之间，实际为" + basicScore;
        System.out.println("基本资质得分: " + basicScore + "/20 ✓");

        //财务健康
        int financialScore = 0;
        int scoreDebtRatio= mapper.scoreDebtRatio(0.55);   // 资产负债率55% -> 8分
        assert scoreDebtRatio == 8 : "负债率55%，负债率得分应为8分，实际为" + scoreDebtRatio;
        int   scoreProfitability = mapper.scoreProfitability("连续盈利高增长"); // -> 10分
        assert scoreProfitability == 10 : "连续盈利高增长，盈利状态得分应为10分，实际为" + scoreProfitability;
        int scoreOperationCycle= mapper.scoreOperationCycle(1.2); // 营业周期是行业平均1.2倍 -> 4分
        assert scoreOperationCycle == 4 : "营业周期是行业平均1.2倍，周期得分应为4分，实际为" + scoreOperationCycle;
        financialScore=scoreDebtRatio+scoreProfitability+scoreOperationCycle;
        assert financialScore == 22 : "财务健康得分应为22分，实际为" + financialScore;
        assert financialScore >= 0 && financialScore <= 30 : "财务健康得分应在0-30之间，实际为" + financialScore;
        System.out.println("财务健康得分: " + financialScore + "/30 ✓");

        //履约信用
        int performanceScore = 0;
        performanceScore += mapper.scoreContractPerformance(0.98); // 履约率98% -> 6分
        performanceScore += mapper.scoreCreditRecord("无逾期");     // -> 10分
        performanceScore += mapper.scorePenalty("无");              // -> 5分

        //履约信用得分验证
        assert performanceScore == 21 : "履约信用得分应为21分，实际为" + performanceScore;
        assert performanceScore >= 0 && performanceScore <= 25 : "履约信用得分应在0-25之间，实际为" + performanceScore;
        System.out.println("履约信用得分: " + performanceScore + "/25 ✓");

        //经营管理
        int managementScore = 0;
        managementScore += mapper.scoreRevenueGrowth(0.12);  // 增长率12% -> 5分
        managementScore += mapper.scoreTeamStability("稳定"); // -> 7分

        //经营管理得分验证
        assert managementScore == 12 : "经营管理得分应为12分，实际为" + managementScore;
        assert managementScore >= 0 && managementScore <= 15 : "经营管理得分应在0-15之间，实际为" + managementScore;
        System.out.println("经营管理得分: " + managementScore + "/15 ✓");

        //合规与风控
        int complianceScore = 0;
        complianceScore += mapper.scoreTaxCompliance("合规"); // -> 5分
        complianceScore += mapper.scoreRiskManagement("完善"); // -> 5分

        //合规与风控得分验证
        assert complianceScore == 10 : "合规与风控得分应为10分，实际为" + complianceScore;
        assert complianceScore >= 0 && complianceScore <= 10 : "合规与风控得分应在0-10之间，实际为" + complianceScore;
        System.out.println("合规与风控得分: " + complianceScore + "/10 ✓");
        double totalScore = mapper.calculateTotalScore(basicScore, financialScore, performanceScore, managementScore, complianceScore);
        double expectedTotal =basicScore+financialScore+performanceScore+managementScore+complianceScore;
        int expectedTotalInt = (int) expectedTotal;
        assert totalScore == expectedTotalInt : String.format("加权总分应为%d分，实际为%d分", expectedTotalInt, totalScore);
        assert totalScore >= 70 : "当前企业综合评分应达到A级（≥70分），实际为" + totalScore;

        System.out.println("\n加权评级总分: " + totalScore + "/100 ✓");
        String creditLevel = mapToCreditLevel(totalScore);
        assert creditLevel != null && !creditLevel.isEmpty() : "信用等级不应为空";
        assert creditLevel.equals("AA") : String.format("根据%d分，信用等级应为AA，实际为%s", totalScore, creditLevel);
        assert isValidCreditLevel(creditLevel) : "信用等级应为AAA/AA/A/BBB/BB/B之一，实际为" + creditLevel;
        System.out.println("信用等级: " + creditLevel + " ✓");
        String ratingDescription = getRatingDescription(creditLevel);
        assert ratingDescription != null && !ratingDescription.isEmpty() : "评级说明不应为空";
        assert ratingDescription.contains("信用") : "评级说明应包含'信用'关键词";
        int totalRawScore = basicScore + financialScore + performanceScore + managementScore + complianceScore;
        int maxRawScore = 20 + 30 + 25 + 15 + 10;
        assert totalRawScore <= maxRawScore : String.format("原始得分总和%d不应超过满分%d", totalRawScore, maxRawScore);

        assert basicScore >= 0 : "基本资质得分不应为负数";
        assert financialScore >= 0 : "财务健康得分不应为负数";
        assert performanceScore >= 0 : "履约信用得分不应为负数";
        assert managementScore >= 0 : "经营管理得分不应为负数";
        assert complianceScore >= 0 : "合规与风控得分不应为负数";
        System.out.println("\n✓ 所有数据完整性验证通过");

    }
    /**
     * 将总分映射为信用等级（与Excel中逻辑一致）
     */
    private static String mapToCreditLevel(double score) {
        if (score >= 90) return "AAA";
        if (score >= 80) return "AA";
        if (score >= 70) return "A";
        if (score >= 60) return "BBB";
        if (score >= 50) return "BB";
        return "B";
    }

    /**
     * 获取信用等级对应的评级说明
     */
    private static String getRatingDescription(String level) {
        switch (level) {
            case "AAA": return "信用优秀，履约能力极强";
            case "AA":  return "信用良好，履约能力强";
            case "A":   return "信用较好，履约能力较强";
            case "BBB": return "信用一般，履约能力尚可";
            case "BB":  return "信用较差，履约能力较弱";
            case "B":   return "信用极差，履约能力极弱";
            default:    return "未知等级";
        }
    }

    /**
     * 验证信用等级是否合法
     */
    private static boolean isValidCreditLevel(String level) {
        return level.equals("AAA") || level.equals("AA") || level.equals("A")
                || level.equals("BBB") || level.equals("BB") || level.equals("B");
    }
}
