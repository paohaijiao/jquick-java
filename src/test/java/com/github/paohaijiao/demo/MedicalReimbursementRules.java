package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 13：医保报销项目与比例合规校验规则集
 * <p>
 * 规则按「目录范围 → 起付线/比例 → 先行自付 → 封顶线 → 结算结论」拆分：
 * <ul>
 *     <li>政策类：目录判定、起付线、报销比例、乙类先行自付、年度封顶线</li>
 *     <li>决策类：结算结论、封顶截断、人工审核门槛、异地备案校验</li>
 * </ul>
 * 规则脚本见 {@code src/test/resources/demo/medical-reimbursement.xml}。
 */
public interface MedicalReimbursementRules {

    boolean withinCatalog(@Param("medicalCategory") String medicalCategory);

    double deductible(@Param("insuranceType") String insuranceType,
                      @Param("hospitalLevel") String hospitalLevel,
                      @Param("emergency") boolean emergency);

    double reimburseRatio(@Param("insuranceType") String insuranceType,
                          @Param("hospitalLevel") String hospitalLevel,
                          @Param("emergency") boolean emergency);

    double prepaidSelfPay(@Param("medicalCategory") String medicalCategory,
                          @Param("totalExpense") double totalExpense);

    double annualCap(@Param("insuranceType") String insuranceType);

    double applyCap(@Param("payableAmount") double payableAmount,
                    @Param("remainingCap") double remainingCap);

    String complianceResult(@Param("withinCatalog") boolean withinCatalog,
                            @Param("offSite") boolean offSite,
                            @Param("filed") boolean filed);

    boolean needManualReview(@Param("result") String result,
                             @Param("payableAmount") double payableAmount);
}
