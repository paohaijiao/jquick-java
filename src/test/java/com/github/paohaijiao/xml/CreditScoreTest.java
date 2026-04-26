/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (c) [2025-2099] Martin (goudingcheng@gmail.com)
 */
package com.github.paohaijiao.xml;
import com.github.paohaijiao.xml.factory.JQuickFactory;
import com.github.paohaijiao.xml.factory.JQuickXmlFactory;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;

public class CreditScoreTest {

    @Test
    public void testCreditScore() throws IOException {
        JQuickJavaXmlParseFactory handler = new JQuickJavaXmlParseFactory();
        JQuickFactory factory = new JQuickXmlFactory(handler, "credit-score.xml");
        CreditScoreMapper mapper = factory.createApi(CreditScoreMapper.class);

        // 1. 各维度评分测试
        int scoreOperatingYears = mapper.scoreOperatingYears(12);
        System.out.println("经营年限得分: " + scoreOperatingYears);

        int scoreAnnualRevenue = mapper.scoreAnnualRevenue(8000);
        System.out.println("年营收得分: " + scoreAnnualRevenue);

        int scoreProfitability = mapper.scoreProfitability("3years");
        System.out.println("盈利能力得分: " + scoreProfitability);

        int scoreDebtRatio = mapper.scoreDebtRatio(45);
        System.out.println("资产负债率得分: " + scoreDebtRatio);

        int scoreCurrentRatio = mapper.scoreCurrentRatio(2.5);
        System.out.println("流动比率得分: " + scoreCurrentRatio);

        int scoreCashFlow = mapper.scoreCashFlow("excellent");
        System.out.println("现金流得分: " + scoreCashFlow);

        int scoreBankCredit = mapper.scoreBankCredit(0);
        System.out.println("银行信贷得分: " + scoreBankCredit);

        int scoreCommercialCompliance = mapper.scoreCommercialCompliance("none");
        System.out.println("商业履约得分: " + scoreCommercialCompliance);

        int scoreIndustryCertification = mapper.scoreIndustryCertification("national");
        System.out.println("行业资质得分: " + scoreIndustryCertification);

        int scoreIntellectualProperty = mapper.scoreIntellectualProperty(5, "invention");
        System.out.println("知识产权得分: " + scoreIntellectualProperty);

        int scoreCreditRating = mapper.scoreCreditRating("AAA");
        System.out.println("信用评级得分: " + scoreCreditRating);

        int scoreLegalLitigation = mapper.scoreLegalLitigation(0, "none");
        System.out.println("法律诉讼得分: " + scoreLegalLitigation);

        int scorePenalty = mapper.scorePenalty("none");
        System.out.println("行政处罚得分: " + scorePenalty);

        // 2. 计算各维度小分
        int businessScore = scoreOperatingYears + scoreAnnualRevenue + scoreProfitability;
        int financialScore = scoreDebtRatio + scoreCurrentRatio + scoreCashFlow;
        int complianceScore = scoreBankCredit + scoreCommercialCompliance;
        int qualificationScore = scoreIndustryCertification + scoreIntellectualProperty + scoreCreditRating;
        int riskScore = scoreLegalLitigation + scorePenalty;

        System.out.println("\n========== 各维度小计 ==========");
        System.out.println("经营状况小计: " + businessScore + "/10");
        System.out.println("财务状况小计: " + financialScore + "/10");
        System.out.println("履约记录小计: " + complianceScore + "/8");
        System.out.println("企业资质小计: " + qualificationScore + "/6");
        System.out.println("风险管理小计: " + riskScore + "/6");

        int baseScore = 60;
        int totalScore = mapper.calculateTotalScore(
                baseScore,
                businessScore,
                financialScore,
                complianceScore,
                qualificationScore,
                riskScore
        );

        System.out.println("\n========== 最终结果 ==========");
        System.out.println("基础分: " + baseScore);
        System.out.println("动态调整分: " + (totalScore - baseScore) + "/40");
        System.out.println("总分: " + totalScore + "/100");
        // 4. 获取信用等级
        String rating = mapper.getCreditRating(totalScore);
        String riskLevel = mapper.getRiskLevel(rating);
        String suggestion = mapper.getSuggestion(rating);
        System.out.println("\n信用等级: " + rating);
        System.out.println("风险程度: " + riskLevel);
        System.out.println("建议措施: " + suggestion);
    }
}
