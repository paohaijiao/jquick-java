package com.github.paohaijiao.xml;

import com.github.paohaijiao.xml.param.Param;
import java.util.HashMap;

public interface CreditScoreMapper {

    // 各维度独立评分接口
    public int scoreOperatingYears(@Param("years") int years);

    public int scoreAnnualRevenue(@Param("revenue") double revenue);

    public int scoreProfitability(@Param("profitStatus") String profitStatus);

    public int scoreDebtRatio(@Param("ratio") double ratio);

    public int scoreCurrentRatio(@Param("ratio") double ratio);

    public int scoreCashFlow(@Param("cashFlowStatus") String cashFlowStatus);

    public int scoreBankCredit(@Param("overdueCount") int overdueCount);

    public int scoreCommercialCompliance(@Param("breachType") String breachType);

    public int scoreIndustryCertification(@Param("certLevel") String certLevel);

    public int scoreIntellectualProperty(@Param("patentCount") int patentCount, @Param("ipType") String ipType);

    public int scoreCreditRating(@Param("rating") String rating);

    public int scoreLegalLitigation(@Param("pendingCount") int pendingCount, @Param("litigationStatus") String litigationStatus);

    public int scorePenalty(@Param("penaltyType") String penaltyType);

    // 总分计算接口
    public int calculateTotalScore(@Param("businessScore") int businessScore,
                                   @Param("financialScore") int financialScore,
                                   @Param("currentRatio") int currentRatio,
                                   @Param("complianceScore") int complianceScore,
                                   @Param("qualificationScore") int qualificationScore,
                                   @Param("riskScore") int riskScore
                                  );

    // 信用等级接口
    public String getCreditRating(@Param("score") int score);

    public String getRiskLevel(@Param("rating") String rating);

    public String getSuggestion(@Param("rating") String rating);
}
