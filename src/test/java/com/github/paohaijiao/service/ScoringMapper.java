package com.github.paohaijiao.service;

import com.github.paohaijiao.xml.param.Param;

import java.util.Date;

public interface ScoringMapper {

    int scoreCapital(@Param("actualCapital") double actualCapital, @Param("registeredCapital") double registeredCapital);

    int scoreEstablishment(@Param("years") int years);

    int scoreCertification(@Param("certLevel") String certLevel); // 入参如："甲级/一级/高新", "乙级/二级", "无"

    int scoreDebtRatio(@Param("debtRatio") double debtRatio); // 资产负债率，如 0.65 表示65%

    int scoreProfitability(@Param("profitStatus") String profitStatus); // 入参如："连续盈利高增长", "连续盈利低增长", "一年盈利", "连续亏损"

    int scoreOperationCycle(@Param("cycleRatio") double cycleRatio); // 营业周期与行业平均的比值

    int scoreContractPerformance(@Param("performanceRate") double performanceRate); // 履约率，如 0.98

    int scoreCreditRecord(@Param("creditStatus") String creditStatus); // 入参如："无逾期", "轻微逾期", "多次逾期", "不良贷款"

    int scorePenalty(@Param("penaltyStatus") String penaltyStatus); // 入参如："无", "一般行政处罚", "重大/失信"

    int scoreRevenueGrowth(@Param("growthRate") double growthRate); // 复合增长率，如 0.12

    int scoreTeamStability(@Param("stabilityStatus") String stabilityStatus); // 入参如："稳定", "略有变动", "频繁变动"

    int scoreTaxCompliance(@Param("taxStatus") String taxStatus); // 入参如："合规", "轻微违章", "重大违章"

    int scoreRiskManagement(@Param("riskStatus") String riskStatus); // 入参如："完善", "一般", "无"

    double calculateTotalScore(@Param("basicScore") int basicScore, @Param("financialScore") int financialScore, @Param("performanceScore") int performanceScore, @Param("managementScore") int managementScore, @Param("complianceScore") int complianceScore);

}
