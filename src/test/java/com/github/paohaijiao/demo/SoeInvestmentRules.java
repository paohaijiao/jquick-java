package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

/**
 * 场景 22：国资项目立项投资额度合规规则集
 * <p>
 * 按投资额度划分审批层级（300/1000/5000/10000 万四级阈值），
 * 并按负面清单与授权额度对出资额分段截断。
 * 规则脚本见 {@code src/test/resources/demo/soe-investment.xml}。
 */
public interface SoeInvestmentRules {

    /** 投资额度对应的审批层级：国资委 / 集团 / 公司董事会 / 经营层 / 内部 */
    String approvalLevel(@Param("amountWan") int amountWan);

    /** 核定出资额：授权额度与申报额取小，非主业 3000 万封顶，金融地产清零 */
    int cappedApprovedWan(@Param("requestedWan") int requestedWan,
                         @Param("grantedWan") int grantedWan,
                         @Param("projectType") String projectType);
}
