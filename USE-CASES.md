# JQuick Java 使用场景示例集

> 本文档基于 [CreditScoreMapper](src/test/java/com/github/paohaijiao/xml/CreditScoreMapper.java) 的规则代理模式，整理 40 个业务场景入口、49 条业务诉求的完整落地，覆盖金融、政务、制造、医疗、HR、IoT、营销、校园、物业等 9 大行业域。
> 所有规则脚本与调用结果均在本仓库实际运行验证（Oracle JDK 1.8），**共 260 个用例全部通过**。
> 场景 1-15 为**生产级样板**（含 8~14 个规则方法的多维规则集、Service 编排与 6 组真实业务形态测试）；场景 16-39 为按同一六件套骨架（入参模型 + 决策模型 + Rules 接口 + XML 脚本 + Service + 6 用例）落地的 24 套业务样例；场景 40 为 49 条诉求矩阵的独立入口回归（25 个直接覆盖入口 + 1 个跨场景双引擎联合拦截）。
> 文末「[四、49 个业务场景覆盖矩阵](#四49-个业务场景覆盖矩阵)」给出常见业务诉求与样例的对应关系，49 条诉求已全部落地：24 条建独立样例（场景 16-39），25 条由样板直接承接并在场景 40 提供独立入口。

**代码位置**

| 内容 | 路径 |
|------|------|
| 规则接口 | [src/test/java/com/github/paohaijiao/demo/](src/test/java/com/github/paohaijiao/demo) |
| 规则脚本（XML） | [src/test/resources/demo/](src/test/resources/demo) |
| 领域模型 | [demo/model/](src/test/java/com/github/paohaijiao/demo/model) |
| 编排服务 | [demo/service/](src/test/java/com/github/paohaijiao/demo/service) |
| 生产级测试入口 | 见下方「二、场景索引」各场景的测试链接 |

```shell
# 40 个业务场景入口，共 260 个用例（必须逗号分隔；场景 40 含 26 个矩阵入口用例）
mvn test "-Dtest=RiskAdmissionServiceTest,ClaimAuditServiceTest,PostLoanWarningServiceTest,DynamicPricingServiceTest,TicketSlaServiceTest,PerformanceServiceTest,ReplenishmentServiceTest,TaxComplianceServiceTest,DeviceAlarmServiceTest,CouponServiceTest,SubsidyServiceTest,QualityInspectionServiceTest,MedicalReimbursementServiceTest,ResumeScreeningServiceTest,TransactionFraudServiceTest,CorporateLoanServiceTest,UnderwritingServiceTest,SupplierAdmissionServiceTest,InvestorSuitabilityServiceTest,TechProjectReviewServiceTest,CityQualificationServiceTest,SoeInvestmentServiceTest,TaxReliefServiceTest,LicenseAnnualReviewServiceTest,MemberLoyaltyServiceTest,CampaignFraudServiceTest,AfterSalesServiceTest,UserSegmentServiceTest,DistributionCommissionServiceTest,PresaleFinalPaymentServiceTest,IncomingMaterialServiceTest,WorkshopEnergyAlertServiceTest,HealthScreeningServiceTest,TriageServiceTest,SalaryAllowanceServiceTest,ApprovalRoutingServiceTest,BudgetAlertServiceTest,ScholarshipServiceTest,PropertyWaiverServiceTest,CoveredScenariosMatrixTest"
```

---

## 一、通用模式

### 1.1 两种规则落地方式

| 方式 | 说明 | 适用场景 |
|------|------|----------|
| **XML 规则代理** | 规则写在 XML 的 `<java>` CDATA 中，通过 `createApi(接口, "xxx.xml")` 生成动态代理 | 规则需外部化、可热更新、由业务/实施人员维护 |
| **内联规则注册** | 通过 `rule(方法名, 脚本)` 直接注册，再 `createApi(接口)` 生成代理，无需 XML | 规则随代码发布、单元测试、轻量嵌入 |

两种方式共用同一执行链路（参数绑定 → 返回类型转换 → 上下文注入），脚本写法完全一致。

### 1.2 完整骨架（以场景 1 为例）

**第一步：定义规则接口**（方法名 = 脚本函数名，参数名通过 `@Param` 绑定）

```java
package com.github.paohaijiao.demo;

import com.github.paohaijiao.xml.param.Param;

public interface RiskAdmissionRules {

    String admit(@Param("totalScore") int totalScore,
                 @Param("hasJudicialRecord") boolean hasJudicialRecord);

    String riskLevel(@Param("totalScore") int totalScore);
}
```

**第二步：编写 XML 规则**（`namespace` 必须是接口全限定名）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE javas PUBLIC "-//PAOHAIJIAO//DTD API JAVA 1.0//EN"
        "classpath:paohaijiao/dtd/Jquick-java.dtd">
<javas namespace="com.github.paohaijiao.demo.RiskAdmissionRules">

    <java name="admit" returnClass="java.lang.String">
        <![CDATA[
            str def admit(int:totalScore, boolean:hasJudicialRecord) {
                if (hasJudicialRecord) {
                    return "REJECT";
                } else if (totalScore >= 70) {
                    return "PASS";
                } else if (totalScore >= 55) {
                    return "MANUAL_REVIEW";
                } else {
                    return "REJECT";
                }
            }
        ]]>
    </java>

    <java name="riskLevel" returnClass="java.lang.String">
        <![CDATA[
            str def riskLevel(int:totalScore) {
                if (totalScore >= 85) {
                    return "A";
                } else if (totalScore >= 70) {
                    return "B";
                } else if (totalScore >= 55) {
                    return "C";
                } else {
                    return "D";
                }
            }
        ]]>
    </java>

</javas>
```

**第三步：调用**（XML 与内联两种入口）

```java
// 方式一：XML 规则代理
RiskAdmissionRules rules = JQuickJava.create()
        .createApi(RiskAdmissionRules.class, "demo/risk-admission.xml");

// 方式二：内联规则注册（等价，无需 XML 文件）
RiskAdmissionRules rules = JQuickJava.create()
        .rule("admit", "str def admit(int:totalScore, boolean:hasJudicialRecord) { ... }")
        .rule("riskLevel", "str def riskLevel(int:totalScore) { ... }")
        .createApi(RiskAdmissionRules.class);

// 调用规则
rules.admit(85, false);  // PASS
rules.riskLevel(85);     // A
```

### 1.3 关键约定

| 约定 | 说明 |
|------|------|
| 命名空间 | XML 的 `namespace` 必须等于规则接口全限定名，否则代理找不到规则 |
| 方法名 | 脚本函数名必须与接口方法名完全一致 |
| 参数绑定 | 接口参数用 `@Param("脚本参数名")` 标注，按名称注入脚本 |
| 返回类型 | 最终转换以**接口方法返回类型**为准；XML 的 `returnClass` 为元数据 |
| 脚本返回类型 | `int def` / `double def` / `str def` 对应 int / double / String |

### 1.4 生产级样板的统一结构

15 个场景遵循同一套范式，便于横向参照与复制：

| 组成 | 数量/约定 | 职责 |
|------|-----------|------|
| 领域模型（`model/`） | 入参模型 + 决策结果模型，Lombok `@Data` | 承载业务字段与输出明细，规则接口不直接耦合外部 DTO |
| 规则接口（`XxxRules.java`） | 8~14 个方法 | 每个方法只做一件事：评分类各维度独立、决策类只管映射 |
| 规则脚本（`demo/xxx.xml`） | 与接口一一对应 | 阈值、分档、系数、路由全部外置，支持热更新 |
| 编排服务（`service/XxxService.java`） | 1 个 | 前置硬校验/短路、多维分数汇总与封顶、链路串联、明细组装 |
| 测试（`XxxServiceTest.java`） | 6 组真实业务形态 | 覆盖正常通过、临界分档、一票否决、材料/备案异常、极端值封顶等 |

两种被反复验证的决策骨架：

- **评分分级型**（场景 1/2/4/6/8/9/11/12/15）：多维度独立打分 → Service 汇总 clamp 0~100 → 脚本映射等级与处置动作。
- **硬门槛前置两段式**（场景 11/13/14/15）：先做一票否决/硬性条件集合判定，再做评分与路由；硬条件不满足时评分不改变淘汰结论。

---

## 二、场景索引

| # | 场景 | 领域 | 规则方法数 | 示例代码 |
|---|------|------|-----------|----------|
| 1 | 信贷风控准入决策 | 金融风控 | 10 | [规则集](src/test/java/com/github/paohaijiao/demo/RiskAdmissionRules.java) · [XML](src/test/resources/demo/risk-admission.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/RiskAdmissionService.java) · [测试](src/test/java/com/github/paohaijiao/demo/RiskAdmissionServiceTest.java) |
| 2 | 贷后逾期预警与催收路由 | 贷后管理 | 10 | [规则集](src/test/java/com/github/paohaijiao/demo/PostLoanWarningRules.java) · [XML](src/test/resources/demo/post-loan-warning.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/PostLoanWarningService.java) · [测试](src/test/java/com/github/paohaijiao/demo/PostLoanWarningServiceTest.java) |
| 3 | 保险理赔核赔计算 | 保险 | 10 | [规则集](src/test/java/com/github/paohaijiao/demo/ClaimAuditRules.java) · [XML](src/test/resources/demo/claim-audit.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/ClaimAuditService.java) · [测试](src/test/java/com/github/paohaijiao/demo/ClaimAuditServiceTest.java) |
| 4 | 库存滞销动态定价 | 电商零售 | 10 | [规则集](src/test/java/com/github/paohaijiao/demo/DynamicPricingRules.java) · [XML](src/test/resources/demo/dynamic-pricing.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/DynamicPricingService.java) · [测试](src/test/java/com/github/paohaijiao/demo/DynamicPricingServiceTest.java) |
| 5 | 工单 SLA 分级与升级 | 运维客服 | 10 | [规则集](src/test/java/com/github/paohaijiao/demo/TicketSlaRules.java) · [XML](src/test/resources/demo/ticket-sla.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/TicketSlaService.java) · [测试](src/test/java/com/github/paohaijiao/demo/TicketSlaServiceTest.java) |
| 6 | 绩效等级与奖金系数 | 人力资源 | 10 | [规则集](src/test/java/com/github/paohaijiao/demo/PerformanceRules.java) · [XML](src/test/resources/demo/performance.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/PerformanceService.java) · [测试](src/test/java/com/github/paohaijiao/demo/PerformanceServiceTest.java) |
| 7 | 零售库存补货建议 | 供应链 | 10 | [规则集](src/test/java/com/github/paohaijiao/demo/InventoryRules.java) · [XML](src/test/resources/demo/inventory.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/ReplenishmentService.java) · [测试](src/test/java/com/github/paohaijiao/demo/ReplenishmentServiceTest.java) |
| 8 | 税务合规风险打分 | 财税合规 | 10 | [规则集](src/test/java/com/github/paohaijiao/demo/TaxComplianceRules.java) · [XML](src/test/resources/demo/tax-compliance.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/TaxComplianceService.java) · [测试](src/test/java/com/github/paohaijiao/demo/TaxComplianceServiceTest.java) |
| 9 | IoT 设备告警分级 | 物联网 | 13 | [规则集](src/test/java/com/github/paohaijiao/demo/DeviceAlarmRules.java) · [XML](src/test/resources/demo/device-alarm.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/DeviceAlarmService.java) · [测试](src/test/java/com/github/paohaijiao/demo/DeviceAlarmServiceTest.java) |
| 10 | 优惠券发放规则 | 营销 | 11 | [规则集](src/test/java/com/github/paohaijiao/demo/CouponRules.java) · [XML](src/test/resources/demo/coupon.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/CouponService.java) · [测试](src/test/java/com/github/paohaijiao/demo/CouponServiceTest.java) |
| 11 | 政务人才补贴资格审核 | 政务企服 | 10 | [规则集](src/test/java/com/github/paohaijiao/demo/SubsidyRules.java) · [XML](src/test/resources/demo/subsidy-eligibility.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/SubsidyService.java) · [测试](src/test/java/com/github/paohaijiao/demo/SubsidyServiceTest.java) |
| 12 | 产线质检分级与处置 | 制造 MES | 10 | [规则集](src/test/java/com/github/paohaijiao/demo/QualityInspectionRules.java) · [XML](src/test/resources/demo/quality-inspection.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/QualityInspectionService.java) · [测试](src/test/java/com/github/paohaijiao/demo/QualityInspectionServiceTest.java) |
| 13 | 医保报销合规校验 | 医疗医保 | 8 | [规则集](src/test/java/com/github/paohaijiao/demo/MedicalReimbursementRules.java) · [XML](src/test/resources/demo/medical-reimbursement.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/MedicalReimbursementService.java) · [测试](src/test/java/com/github/paohaijiao/demo/MedicalReimbursementServiceTest.java) |
| 14 | 招聘简历智能初筛 | HR SaaS | 14 | [规则集](src/test/java/com/github/paohaijiao/demo/ResumeScreeningRules.java) · [XML](src/test/resources/demo/resume-screening.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/ResumeScreeningService.java) · [测试](src/test/java/com/github/paohaijiao/demo/ResumeScreeningServiceTest.java) |
| 15 | 银行卡交易反欺诈 | 实时风控 | 12 | [规则集](src/test/java/com/github/paohaijiao/demo/TransactionFraudRules.java) · [XML](src/test/resources/demo/transaction-fraud.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/TransactionFraudService.java) · [测试](src/test/java/com/github/paohaijiao/demo/TransactionFraudServiceTest.java) |
| 16 | 企业经营贷授信额度智能测算（诉求 3） | 金融风控 | 10 | [规则集](src/test/java/com/github/paohaijiao/demo/CorporateLoanRules.java) · [XML](src/test/resources/demo/corporate-credit.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/CorporateLoanService.java) · [测试](src/test/java/com/github/paohaijiao/demo/CorporateLoanServiceTest.java) |
| 17 | 保单核保风险判定（诉求 6） | 保险 | 9 | [规则集](src/test/java/com/github/paohaijiao/demo/InsuranceUnderwritingRules.java) · [XML](src/test/resources/demo/insurance-underwriting.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/UnderwritingService.java) · [测试](src/test/java/com/github/paohaijiao/demo/UnderwritingServiceTest.java) |
| 18 | 供应链金融供应商准入评级（诉求 8） | 供应链金融 | 8 | [规则集](src/test/java/com/github/paohaijiao/demo/SupplierAdmissionRules.java) · [XML](src/test/resources/demo/supplier-admission.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/SupplierAdmissionService.java) · [测试](src/test/java/com/github/paohaijiao/demo/SupplierAdmissionServiceTest.java) |
| 19 | 理财客户风险等级匹配测评（诉求 10） | 金融合规 | 7 | [规则集](src/test/java/com/github/paohaijiao/demo/InvestorSuitabilityRules.java) · [XML](src/test/resources/demo/investor-suitability.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/InvestorSuitabilityService.java) · [测试](src/test/java/com/github/paohaijiao/demo/InvestorSuitabilityServiceTest.java) |
| 20 | 科技项目申报材料打分初审（诉求 14） | 政务企服 | 7 | [规则集](src/test/java/com/github/paohaijiao/demo/TechProjectReviewRules.java) · [XML](src/test/resources/demo/tech-project-review.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/TechProjectReviewService.java) · [测试](src/test/java/com/github/paohaijiao/demo/TechProjectReviewServiceTest.java) |
| 21 | 落户/购房/购车资格多政策判定（诉求 16） | 政务企服 | 8 | [规则集](src/test/java/com/github/paohaijiao/demo/CityQualificationRules.java) · [XML](src/test/resources/demo/city-qualification.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/CityQualificationService.java) · [测试](src/test/java/com/github/paohaijiao/demo/CityQualificationServiceTest.java) |
| 22 | 国资项目立项投资额度合规校验（诉求 18） | 国资监管 | 2 | [规则集](src/test/java/com/github/paohaijiao/demo/SoeInvestmentRules.java) · [XML](src/test/resources/demo/soe-investment.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/SoeInvestmentService.java) · [测试](src/test/java/com/github/paohaijiao/demo/SoeInvestmentServiceTest.java) |
| 23 | 小微税费减免/稳岗补贴资格判定（诉求 21） | 财税合规 | 4 | [规则集](src/test/java/com/github/paohaijiao/demo/TaxReliefRules.java) · [XML](src/test/resources/demo/tax-relief.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/TaxReliefService.java) · [测试](src/test/java/com/github/paohaijiao/demo/TaxReliefServiceTest.java) |
| 24 | 行业许可证年审合规校验（诉求 23） | 政务监管 | 9 | [规则集](src/test/java/com/github/paohaijiao/demo/LicenseAnnualReviewRules.java) · [XML](src/test/resources/demo/license-annual-review.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/LicenseAnnualReviewService.java) · [测试](src/test/java/com/github/paohaijiao/demo/LicenseAnnualReviewServiceTest.java) |
| 25 | 会员等级成长值权益升降级（诉求 26） | 电商零售 | 5 | [规则集](src/test/java/com/github/paohaijiao/demo/MemberLoyaltyRules.java) · [XML](src/test/resources/demo/member-loyalty.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/MemberLoyaltyService.java) · [测试](src/test/java/com/github/paohaijiao/demo/MemberLoyaltyServiceTest.java) |
| 26 | 订单防刷单风控校验（诉求 27） | 电商风控 | 6 | [规则集](src/test/java/com/github/paohaijiao/demo/CampaignFraudRules.java) · [XML](src/test/resources/demo/campaign-fraud.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/CampaignFraudService.java) · [测试](src/test/java/com/github/paohaijiao/demo/CampaignFraudServiceTest.java) |
| 27 | 售后退款/退货/换货资格判定（诉求 29） | 电商零售 | 4 | [规则集](src/test/java/com/github/paohaijiao/demo/AfterSalesRules.java) · [XML](src/test/resources/demo/after-sales.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/AfterSalesService.java) · [测试](src/test/java/com/github/paohaijiao/demo/AfterSalesServiceTest.java) |
| 28 | 用户画像标签动态分层（诉求 30） | 电商用户运营 | 5 | [规则集](src/test/java/com/github/paohaijiao/demo/UserSegmentRules.java) · [XML](src/test/resources/demo/user-segment.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/UserSegmentService.java) · [测试](src/test/java/com/github/paohaijiao/demo/UserSegmentServiceTest.java) |
| 29 | 分销返佣/上下级分润（诉求 31） | 电商分销 | 6 | [规则集](src/test/java/com/github/paohaijiao/demo/DistributionCommissionRules.java) · [XML](src/test/resources/demo/distribution-commission.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/DistributionCommissionService.java) · [测试](src/test/java/com/github/paohaijiao/demo/DistributionCommissionServiceTest.java) |
| 30 | 预售尾款资格/履约条件校验（诉求 32） | 电商零售 | 4 | [规则集](src/test/java/com/github/paohaijiao/demo/PresaleFinalPaymentRules.java) · [XML](src/test/resources/demo/presale-final-payment.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/PresaleFinalPaymentService.java) · [测试](src/test/java/com/github/paohaijiao/demo/PresaleFinalPaymentServiceTest.java) |
| 31 | 原材料来料验收标准动态适配（诉求 36） | 制造 MES | 7 | [规则集](src/test/java/com/github/paohaijiao/demo/IncomingMaterialRules.java) · [XML](src/test/resources/demo/incoming-material.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/IncomingMaterialService.java) · [测试](src/test/java/com/github/paohaijiao/demo/IncomingMaterialServiceTest.java) |
| 32 | 车间能耗异常判定与超标预警（诉求 37） | 制造能源 | 5 | [规则集](src/test/java/com/github/paohaijiao/demo/WorkshopEnergyRules.java) · [XML](src/test/resources/demo/workshop-energy.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/WorkshopEnergyAlertService.java) · [测试](src/test/java/com/github/paohaijiao/demo/WorkshopEnergyAlertServiceTest.java) |
| 33 | 体检指标异常筛查与风险标注（诉求 40） | 医疗公卫 | 8 | [规则集](src/test/java/com/github/paohaijiao/demo/HealthScreeningRules.java) · [XML](src/test/resources/demo/health-screening.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/HealthScreeningService.java) · [测试](src/test/java/com/github/paohaijiao/demo/HealthScreeningServiceTest.java) |
| 34 | 门诊分诊/急诊优先级分级（诉求 41） | 医疗急诊 | 4 | [规则集](src/test/java/com/github/paohaijiao/demo/TriageRules.java) · [XML](src/test/resources/demo/triage.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/TriageService.java) · [测试](src/test/java/com/github/paohaijiao/demo/TriageServiceTest.java) |
| 35 | 薪资补贴自动核算（诉求 43） | 人力资源 | 8 | [规则集](src/test/java/com/github/paohaijiao/demo/SalaryAllowanceRules.java) · [XML](src/test/resources/demo/salary-allowance.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/SalaryAllowanceService.java) · [测试](src/test/java/com/github/paohaijiao/demo/SalaryAllowanceServiceTest.java) |
| 36 | 请假/加班/出差审批流转（诉求 44） | OA 协同 | 5 | [规则集](src/test/java/com/github/paohaijiao/demo/ApprovalRoutingRules.java) · [XML](src/test/resources/demo/approval-routing.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/ApprovalRoutingService.java) · [测试](src/test/java/com/github/paohaijiao/demo/ApprovalRoutingServiceTest.java) |
| 37 | 部门预算合规与超支预警（诉求 46） | 财务管理 | 5 | [规则集](src/test/java/com/github/paohaijiao/demo/BudgetAlertRules.java) · [XML](src/test/resources/demo/budget-alert.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/BudgetAlertService.java) · [测试](src/test/java/com/github/paohaijiao/demo/BudgetAlertServiceTest.java) |
| 38 | 校园奖学金/评优/助学金资格评定（诉求 48） | 教育校园 | 6 | [规则集](src/test/java/com/github/paohaijiao/demo/ScholarshipRules.java) · [XML](src/test/resources/demo/scholarship.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/ScholarshipService.java) · [测试](src/test/java/com/github/paohaijiao/demo/ScholarshipServiceTest.java) |
| 39 | 物业费/停车费减免资格判定（诉求 49） | 物业社区 | 4 | [规则集](src/test/java/com/github/paohaijiao/demo/PropertyWaiverRules.java) · [XML](src/test/resources/demo/property-waiver.xml) · [Service](src/test/java/com/github/paohaijiao/demo/service/PropertyWaiverService.java) · [测试](src/test/java/com/github/paohaijiao/demo/PropertyWaiverServiceTest.java) |
| 40 | 49 诉求矩阵独立入口 + 准入/反欺诈跨场景联合拦截（诉求 1/2/4/5/7/9/11/12/13/15/17/19/20/22/24/25/28/33/34/35/38/39/42/45/47） | 跨域回归 | 复用 1-15 | [测试入口](src/test/java/com/github/paohaijiao/demo/CoveredScenariosMatrixTest.java)（25 个诉求各一个独立入口 + 1 个双引擎串联用例，规则与 XML 复用场景 1-15） |

---

## 三、场景详情

### 场景 1：信贷风控准入决策（生产级样板）

**业务背景**：个人消费信贷进件审批。进件要素涵盖单位性质、在职月数、社保缴纳、收入负债、征信逾期与查询、抵押物等，需输出准入结论、风险等级、建议额度、执行利率，并给出评分明细、拒绝原因与风险提示。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | 进件要素、决策结果 | [CreditApplication.java](src/test/java/com/github/paohaijiao/demo/model/CreditApplication.java) · [AdmissionDecision.java](src/test/java/com/github/paohaijiao/demo/model/AdmissionDecision.java) |
| 规则集 | 5 个评分维度 + 5 个决策映射，口径全部外置 | [RiskAdmissionRules.java](src/test/java/com/github/paohaijiao/demo/RiskAdmissionRules.java) · [risk-admission.xml](src/test/resources/demo/risk-admission.xml) |
| 编排层 | 前置校验、维度汇总、额度封顶、原因与提示组装 | [RiskAdmissionService.java](src/test/java/com/github/paohaijiao/demo/service/RiskAdmissionService.java) |
| 测试 | 6 组真实业务形态 | [RiskAdmissionServiceTest.java](src/test/java/com/github/paohaijiao/demo/RiskAdmissionServiceTest.java) |

**规则集拆分**（10 个规则方法，每个只做一件事，便于单维度调参与模型回溯）

| 规则方法 | 口径 | 分值 |
|----------|------|------|
| `scoreDebtRatio` | 月负债还款额 / 月收入 分档 | 0 ~ 25 |
| `scoreOverdue` | 逾期次数 × 最长逾期天数 | 0 ~ 30 |
| `scoreEmployment` | 在职月数 + 单位性质 | 0 ~ 15 |
| `scoreCreditQuery` | 近 3 月征信查询次数（多头借贷信号） | -5 ~ 10 |
| `scoreCollateral` | 抵押物类型 + 价值覆盖倍数 | 0 ~ 20 |
| `riskLevel` | 总分 → A / B / C / D | - |
| `admit` | 总分 + 涉诉一票否决 → 准入结论 | - |
| `suggestLimit` | 月收入 × 风险系数 | - |
| `interestRate` | 风险等级 → 年化执行利率 | - |
| `needManualReview` | 中等分区间或多头查询 → 转人工复核 | - |

**规则脚本（XML 节选）**

```jquick
int def scoreDebtRatio(double:monthlyIncome, double:monthlyDebt) {
    if (monthlyIncome <= 0.0) {
        return 0;
    }
    double ratio = monthlyDebt / monthlyIncome;
    if (ratio <= 0.2) {
        return 25;
    } else if (ratio <= 0.4) {
        return 20;
    } else if (ratio <= 0.5) {
        return 15;
    } else if (ratio <= 0.6) {
        return 8;
    } else {
        return 0;
    }
}

str def admit(int:totalScore, boolean:hasJudicialRecord) {
    if (hasJudicialRecord) {
        return "REJECT";
    } else if (totalScore >= 70) {
        return "PASS";
    } else if (totalScore >= 55) {
        return "MANUAL_REVIEW";
    } else {
        return "REJECT";
    }
}
```

**编排层调用**

```java
RiskAdmissionRules rules = JQuickJava.create()
        .createApi(RiskAdmissionRules.class, "demo/risk-admission.xml");
RiskAdmissionService service = new RiskAdmissionService(rules);

AdmissionDecision decision = service.evaluate(application);
```

**实测：优质国企客户**（月收入 30000、月负债 4500、无逾期、房产抵押估值 60 万）

```text
准入结论: PASS        总分: 100       风险等级: A
建议额度: 300000.0    执行利率: 0.0385
评分明细: 负债收入比 25 | 逾期 30 | 职业稳定性 15 | 征信查询 10 | 抵押物 20
```

**实测：高风险自雇客户**（负债率 75%、6 次逾期且最长 120 天、近 3 月查询 12 次、无抵押）

```text
准入结论: REJECT      总分: 0         风险等级: D
拒绝原因: 负债收入比过高，超出可承受范围
          历史逾期严重，最长逾期天数 120 天
```

其余 4 组形态（边界客户转人工复核、高分多头查询自动降级、涉诉一票否决、年龄不符硬拒绝）见 [RiskAdmissionServiceTest.java](src/test/java/com/github/paohaijiao/demo/RiskAdmissionServiceTest.java)。

---

### 场景 2：贷后逾期预警与催收路由（生产级样板）

**业务背景**：贷款进入逾期后，按逾期天数、欠款金额占比、联系触达、抵押物覆盖率四个维度评分，输出红/橙/黄/蓝预警等级、催收渠道（诉讼/上门/电话/短信）、罚息、最低还款额与风险迁徙分，驱动差异化催收。未逾期账户短路返回。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | 贷款账户快照、预警决策 | [LoanAccount.java](src/test/java/com/github/paohaijiao/demo/model/LoanAccount.java) · [WarningDecision.java](src/test/java/com/github/paohaijiao/demo/model/WarningDecision.java) |
| 规则集 | 4 个评分维度 + 阶段/等级/渠道/罚息/迁徙 | [PostLoanWarningRules.java](src/test/java/com/github/paohaijiao/demo/PostLoanWarningRules.java) · [post-loan-warning.xml](src/test/resources/demo/post-loan-warning.xml) |
| 编排层 | 未逾期短路、四维汇总封顶、催收动作组装 | [PostLoanWarningService.java](src/test/java/com/github/paohaijiao/demo/service/PostLoanWarningService.java) |
| 测试 | 6 组逾期形态 | [PostLoanWarningServiceTest.java](src/test/java/com/github/paohaijiao/demo/PostLoanWarningServiceTest.java) |

**规则集拆分**（10 个规则方法）

| 规则方法 | 口径 | 分值 |
|----------|------|------|
| `overdueStage` | 逾期天数 ≥90/≥60/≥30/≥1 → 阶段 4/3/2/1 | - |
| `scoreOverdueDays` | 天数分档，历史逾期 ≥3 次扣 10、≥1 次扣 5，下限 0 | 0 ~ 35 |
| `scoreAmountRatio` | 逾期金额占剩余本金比例 | 0 ~ 25 |
| `scoreContact` | 是否触达 + 最近联系间隔 | 0 ~ 15 |
| `scoreCollateral` | 房抵覆盖倍数 ≥1.2 得 25、≥0.8 得 18，车抵/无抵押递减 | 0 ~ 25 |
| `warningLevel` | ≥80 RED / ≥60 ORANGE / ≥40 YELLOW / 否则 BLUE | - |
| `collectChannel` | ≥80 或（≥60 且金额 ≥10 万）诉讼；≥60 上门；≥40 电话；否则短信 | - |
| `penaltyInterest` | 剩余本金 × 利率 × 1.5 × 逾期天数 / 365 | - |
| `minRepayAmount` | 逾期金额 ×（1 + 违约金率） | - |
| `riskMigration` | 逾期阶段 ×2 + 历史预警次数，封顶 9 | - |

**规则脚本（XML 节选）**

```jquick
str def warningLevel(int:warningScore) {
    if (warningScore >= 80) {
        return "RED";
    } else if (warningScore >= 60) {
        return "ORANGE";
    } else if (warningScore >= 40) {
        return "YELLOW";
    } else {
        return "BLUE";
    }
}

str def collectChannel(int:warningScore, double:overdueAmount) {
    if (warningScore >= 80 || (warningScore >= 60 && overdueAmount >= 100000.0)) {
        return "LITIGATION";
    } else if (warningScore >= 60) {
        return "HOME_VISIT";
    } else if (warningScore >= 40) {
        return "PHONE";
    } else {
        return "SMS";
    }
}
```

**编排层调用**

```java
PostLoanWarningRules rules = JQuickJava.create()
        .createApi(PostLoanWarningRules.class, "demo/post-loan-warning.xml");
PostLoanWarningService service = new PostLoanWarningService(rules);

WarningDecision decision = service.evaluate(account);
```

**实测：严重逾期拟诉讼**（逾期 95 天、逾期金额 20 万、剩余本金 100 万、房抵覆盖不足）

```text
预警等级: ORANGE   预警分: 60    逾期阶段: 4
催收渠道: LITIGATION              风险迁徙: 8
罚息金额: 14250.0   最低还款额: 210000.0
```

**实测：首次轻微逾期**（逾期 3 天、金额 1 万、联系正常）

```text
预警等级: BLUE     预警分: 27    逾期阶段: 1
催收渠道: SMS                      罚息金额: 450.0
```

其余 4 组形态（中期房抵上门、失联车抵电催、账户正常短路、长期逾期红色迁徙 9）见 [PostLoanWarningServiceTest.java](src/test/java/com/github/paohaijiao/demo/PostLoanWarningServiceTest.java)。

---

### 场景 3：保险理赔核赔计算（生产级样板）

**业务背景**：医疗险 / 重疾险 / 意外险 / 车险理赔核赔。需校验等待期、按产品与医院等级确定赔付比例、扣减免赔额、计算赔付金额、执行产品封顶线、评估理赔风险，并路由到快赔 / 人工审核 / 调查岗通道。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | 报案要素、核赔结果 | [ClaimRequest.java](src/test/java/com/github/paohaijiao/demo/model/ClaimRequest.java) · [ClaimDecision.java](src/test/java/com/github/paohaijiao/demo/model/ClaimDecision.java) |
| 规则集 | 等待期、比例、免赔、封顶、风险、通道路由 | [ClaimAuditRules.java](src/test/java/com/github/paohaijiao/demo/ClaimAuditRules.java) · [claim-audit.xml](src/test/resources/demo/claim-audit.xml) |
| 编排层 | 链路串联、封顶复核、核赔说明组装 | [ClaimAuditService.java](src/test/java/com/github/paohaijiao/demo/service/ClaimAuditService.java) |
| 测试 | 6 组核赔形态 | [ClaimAuditServiceTest.java](src/test/java/com/github/paohaijiao/demo/ClaimAuditServiceTest.java) |

**规则集拆分**（10 个规则方法）

| 规则方法 | 口径 |
|----------|------|
| `withinWaitingPeriod` | 已投保天数 < 等待期天数 |
| `reimburseRatio` | 产品类型 × 医院等级 × 是否意外 |
| `actualDeductible` | 重疾免赔为 0、意外减半、不超过申请金额 |
| `payableAmount` | （min(申请金额, 发票金额) - 免赔额）× 比例 |
| `applyCap` | 医疗 20 万 / 重疾 50 万 / 车险 30 万 / 意外 10 万 |
| `riskScore` | 历史理赔次数 × 10 + 风控标记 × 25 + 大额报案加分 |
| `riskLevel` | LOW / MEDIUM / HIGH |
| `needInvestigation` | 标记 ≥ 2 次，或标记 ≥ 1 次且金额 ≥ 5 万 |
| `auditResult` | 等待期拒赔 → 标记转调查 → 未达免赔拒赔 → 通过 / 转人工 |
| `fastTrack` | 材料齐全 + 赔款 ≤ 5000 元 + 无历史理赔 |

**规则脚本（XML 节选）**

```jquick
double def reimburseRatio(str:productType, str:hospitalLevel, boolean:isAccident) {
    double ratio = 0.6;
    if (productType == "MEDICAL") {
        if (hospitalLevel == "THIRD_A") {
            ratio = 0.85;
        } else if (hospitalLevel == "SECOND_A") {
            ratio = 0.75;
        } else {
            ratio = 0.6;
        }
    } else if (productType == "CRITICAL_ILLNESS") {
        ratio = 1.0;
    } else if (productType == "ACCIDENT") {
        ratio = 0.9;
    } else if (productType == "AUTO") {
        if (isAccident) {
            ratio = 0.9;
        } else {
            ratio = 0.7;
        }
    }
    return ratio;
}

str def auditResult(boolean:withinWaitingPeriod, int:fraudFlagCount, boolean:documentComplete, double:payableAmount) {
    if (withinWaitingPeriod) {
        return "REJECT";
    } else if (fraudFlagCount >= 2) {
        return "MANUAL_AUDIT";
    } else if (payableAmount <= 0.0) {
        return "REJECT";
    } else if (documentComplete) {
        return "APPROVE";
    } else {
        return "MANUAL_AUDIT";
    }
}
```

**编排层调用**

```java
ClaimAuditRules rules = JQuickJava.create()
        .createApi(ClaimAuditRules.class, "demo/claim-audit.xml");
ClaimAuditService service = new ClaimAuditService(rules);

ClaimDecision decision = service.audit(request);
```

**实测：门诊小额医疗**（三甲医院、费用 3200 元、免赔额 300 元、材料齐全）

```text
核赔结论: APPROVE     赔付金额: 2465.0    实际免赔额: 300.0
赔付比例: 0.85        风险分: 0 (LOW)     快赔通道: true
```

**实测：重疾大额**（申请 60 万、发票 58 万、重疾免赔 0、赔付比例 100%）

```text
核赔结论: APPROVE     赔付金额: 500000.0（触达 50 万封顶线）
风险分: 30 (MEDIUM)   快赔通道: false
```

其余 4 组形态（等待期拒赔、风控标记转调查、材料不全转人工、未达免赔额拒赔）见 [ClaimAuditServiceTest.java](src/test/java/com/github/paohaijiao/demo/ClaimAuditServiceTest.java)。

---

### 场景 4：库存滞销动态定价（生产级样板）

**业务背景**：零售清仓场景。按库龄确定基准折扣，再叠加动销、季节、临期、外包仓储四个修正因子，受最低折扣（0.30）与品类毛利保护线双重约束，输出最终折扣、建议售价、清仓等级与促销路由（限时秒杀/组合捆绑/优惠券/会员价）。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | SKU 库存与成本快照、定价决策 | [InventoryItem.java](src/test/java/com/github/paohaijiao/demo/model/InventoryItem.java) · [PricingDecision.java](src/test/java/com/github/paohaijiao/demo/model/PricingDecision.java) |
| 规则集 | 库龄基准 + 4 修正因子 + 地板价 + 清仓/促销路由 | [DynamicPricingRules.java](src/test/java/com/github/paohaijiao/demo/DynamicPricingRules.java) · [dynamic-pricing.xml](src/test/resources/demo/dynamic-pricing.xml) |
| 编排层 | 折扣聚合 clamp 0.30~1.0、毛利保护复核、备注组装 | [DynamicPricingService.java](src/test/java/com/github/paohaijiao/demo/service/DynamicPricingService.java) |
| 测试 | 6 组库存形态 | [DynamicPricingServiceTest.java](src/test/java/com/github/paohaijiao/demo/DynamicPricingServiceTest.java) |

**规则集拆分**（10 个规则方法）

| 规则方法 | 口径 |
|----------|------|
| `ageLevel` | 库龄 ≥180/≥90/≥60/≥30 天分档 |
| `baseDiscount` | 库龄基准折扣 0.5 / 0.65 / 0.8 / 0.9 / 1.0 |
| `slowMovingFactor` | 近 30 天销量与库存量交叉比较，修正 -0.15 / -0.08 / 0 / +0.05 |
| `seasonFactor` | 旺季 / 淡季季节修正 |
| `perishableFactor` | 临期商品加深折扣 |
| `warehouseFactor` | 外包仓储持有成本修正 |
| `applyFloor` | 不低于最低折扣 0.30 |
| `clearanceLevel` | ≤0.55 DEEP / ≤0.75 MEDIUM / <1.0 LIGHT / 否则 NONE |
| `promotionType` | DEEP+临期限时秒杀、DEEP 组合捆绑、MEDIUM 优惠券、LIGHT 会员价 |
| `needClearance` | 折扣 < 1.0 即需清仓 |

> **脚本整数除法陷阱**：`int * 1.0 / int` 在本引擎中不会提升为浮点除法。动销率比较改用整数交叉相乘：

```jquick
double def slowMovingFactor(int:recentSalesQty30d, int:stockQty) {
    if (stockQty <= 0) {
        return 0.0;
    }
    if (recentSalesQty30d * 10 <= stockQty) {
        return -0.15;
    } else if (recentSalesQty30d * 10 <= stockQty * 3) {
        return -0.08;
    } else if (recentSalesQty30d * 10 <= stockQty * 6) {
        return 0.0;
    } else {
        return 0.05;
    }
}

str def promotionType(str:clearanceLevel, boolean:perishable) {
    if (clearanceLevel == "DEEP" && perishable) {
        return "FLASH_SALE";
    } else if (clearanceLevel == "DEEP") {
        return "BUNDLE";
    } else if (clearanceLevel == "MEDIUM") {
        return "COUPON";
    } else if (clearanceLevel == "LIGHT") {
        return "MEMBER_PRICE";
    } else {
        return "NONE";
    }
}
```

**实测：严重滞销品**（库龄 200 天、库存 100、近 30 天仅售 2 件、成本价 100、原价 200）

```text
最终折扣: 0.35      建议售价: 70.0     清仓等级: DEEP
促销路由: BUNDLE    需清仓: true
```

**实测：畅销旺季品**（库龄 10 天、动销健康、旺季因子为正）

```text
最终折扣: 1.0       清仓等级: NONE     需清仓: false（备注为空）
```

其余 4 组形态（临期外包仓 0.54、毛利保护售价 72.0、极限清仓触地板价 0.30、库龄临界 0.8）见 [DynamicPricingServiceTest.java](src/test/java/com/github/paohaijiao/demo/DynamicPricingServiceTest.java)。

---

### 场景 5：工单 SLA 分级与升级（生产级样板）

**业务背景**：客服/运维工单按优先级、客户等级、是否安全类确定首响、解决、升级时限矩阵；结合重开次数、当前处理人负载、历史违约记录计算违约风险分，决定是否立即升级、是否需要经理介入。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | 工单快照、SLA 决策 | [Ticket.java](src/test/java/com/github/paohaijiao/demo/model/Ticket.java) · [SlaDecision.java](src/test/java/com/github/paohaijiao/demo/model/SlaDecision.java) |
| 规则集 | 时限矩阵 + 风险分 + 升级判定 | [TicketSlaRules.java](src/test/java/com/github/paohaijiao/demo/TicketSlaRules.java) · [ticket-sla.xml](src/test/resources/demo/ticket-sla.xml) |
| 编排层 | 时限装配、风险分汇总、升级动作组装 | [TicketSlaService.java](src/test/java/com/github/paohaijiao/demo/service/TicketSlaService.java) |
| 测试 | 6 组工单形态 | [TicketSlaServiceTest.java](src/test/java/com/github/paohaijiao/demo/TicketSlaServiceTest.java) |

**规则集拆分**（10 个规则方法）

| 规则方法 | 口径 |
|----------|------|
| `slaLevel` | P0 或 VIP 客户 → VIP；P1 → GOLD；否则 STANDARD |
| `responseMinutes` | P0 10 / P1 20 / P2 60 / 其余 240 分钟；安全类 P0/P1 收紧到 5 分钟 |
| `resolveMinutes` | VIP/非 VIP × 优先级双维矩阵 |
| `escalateMinutes` | 升级时限双维矩阵 |
| `priorityWeight` | P0 40 / P1 30 / P2 15 / P3 5 |
| `scoreReopen` | 每次重开 12 分，封顶 36 |
| `scoreLoad` | 处理人在手工单 ≥20/≥12/≥6 → 20/12/6 |
| `breachRisk` | 历史违约或风险分 ≥60 → HIGH；≥35 → MEDIUM；否则 LOW |
| `needEscalate` | P0/安全类或高风险 → 立即升级 |
| `needManager` | 安全类工单或高风险需经理介入 |

**规则脚本（XML 节选）**

```jquick
str def slaLevel(str:priority, str:customerLevel, boolean:securityRelated) {
    if (priority == "P0" || customerLevel == "VIP") {
        return "VIP";
    } else if (priority == "P1") {
        return "GOLD";
    } else {
        return "STANDARD";
    }
}

str def breachRisk(int:riskScore, boolean:hasSlaBreachHistory) {
    if (hasSlaBreachHistory || riskScore >= 60) {
        return "HIGH";
    } else if (riskScore >= 35) {
        return "MEDIUM";
    } else {
        return "LOW";
    }
}
```

**实测：P0 安全故障**（VIP 客户、安全类）

```text
服务水平: VIP       首响: 5 分钟      解决: 120 分钟    升级时限: 10 分钟
风险分: 46 (MEDIUM) 需立即升级: true   需经理介入: true
```

**实测：VIP 多次重开**（重开 3 次以上、处理人高负载）

```text
风险分: 66 (HIGH)   需立即升级: true
```

其余 4 组形态（P3 低优不升级、中等负载重开 57 MEDIUM、历史违约 48 HIGH、安全类普通工单需经理）见 [TicketSlaServiceTest.java](src/test/java/com/github/paohaijiao/demo/TicketSlaServiceTest.java)。

---

### 场景 6：绩效等级与奖金系数（生产级样板）

**业务背景**：按 KPI 50%、项目 20%、协作 15%、考勤 15% 加权得到综合分，映射 S/A/B/C/D 等级与基准奖金系数，再叠加管理职责、销售激励、违规扣减，输出奖金金额、晋升资格与 PIP 改进计划。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | 绩效记录、奖金决策 | [PerformanceRecord.java](src/test/java/com/github/paohaijiao/demo/model/PerformanceRecord.java) · [BonusDecision.java](src/test/java/com/github/paohaijiao/demo/model/BonusDecision.java) |
| 规则集 | 加权、等级、系数、三项调节、晋升与 PIP | [PerformanceRules.java](src/test/java/com/github/paohaijiao/demo/PerformanceRules.java) · [performance.xml](src/test/resources/demo/performance.xml) |
| 编排层 | 系数聚合下限保护、晋升线复核、备注组装 | [PerformanceService.java](src/test/java/com/github/paohaijiao/demo/service/PerformanceService.java) |
| 测试 | 6 组员工形态 | [PerformanceServiceTest.java](src/test/java/com/github/paohaijiao/demo/PerformanceServiceTest.java) |

**规则集拆分**（10 个规则方法）

| 规则方法 | 口径 |
|----------|------|
| `weightedScore` | KPI×50% + 项目×20% + 协作×15% + 考勤×15% |
| `grade` | ≥90 S / ≥80 A / ≥70 B / ≥60 C / 否则 D |
| `baseCoefficient` | S 1.8 / A 1.4 / B 1.1 / C 0.8 / D 0.5 |
| `managerBonus` | 管理者 +0.2 |
| `salesBonus` | S/A 且销售额 ≥50 万 +0.2、≥20 万 +0.1 |
| `violationPenalty` | 违规 -0.3 |
| `promotionEligible` | 总分 ≥85 且无违规 |
| `needPip` | D 级强制改进计划 |
| `attendanceLevel` | 考勤 ≥95/≥90/≥80 → 3/2/1 档 |
| `bonusAmount` | 基数薪资 × 最终系数 |

**规则脚本（XML 节选）**（脚本不支持 `!` 取反，布尔条件正向书写后提前 return）

```jquick
boolean def promotionEligible(double:totalScore, boolean:hasViolation) {
    if (hasViolation) {
        return false;
    } else if (totalScore >= 85.0) {
        return true;
    } else {
        return false;
    }
}
```

**实测：优秀销售**（综合 92.65、S 级、销售额达标）

```text
等级: S    综合分: 92.65    奖金系数: 2.0    奖金金额: 40000.0
晋升资格: true               PIP: false
```

**实测：低绩效且违规**（综合 51.25、D 级）

```text
等级: D    综合分: 51.25    奖金系数: 0.2    PIP: true
```

其余 4 组形态（管理者 A 级 1.7 系数、中等 B 级 1.2、不达标 D 级 0.5、优秀但违规取消晋升）见 [PerformanceServiceTest.java](src/test/java/com/github/paohaijiao/demo/PerformanceServiceTest.java)。

---

### 场景 7：零售库存补货建议（生产级样板）

**业务背景**：连锁零售补货。按日均销量与提前期计算提前期需求与安全库存，叠加促销/履约率/旺淡季修正得到目标库存，再依次经过缺口计算、MOQ 最小起订、整箱取整、单次上限封顶，输出下单量与紧急度。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | SKU 补货参数、补货决策 | [ReplenishmentItem.java](src/test/java/com/github/paohaijiao/demo/model/ReplenishmentItem.java) · [ReplenishDecision.java](src/test/java/com/github/paohaijiao/demo/model/ReplenishDecision.java) |
| 规则集 | 需求/安全库存/目标库存 + MOQ/整箱/紧急度 | [InventoryRules.java](src/test/java/com/github/paohaijiao/demo/InventoryRules.java) · [inventory.xml](src/test/resources/demo/inventory.xml) |
| 编排层 | 补货链路串联、封顶拆单提示、备注组装 | [ReplenishmentService.java](src/test/java/com/github/paohaijiao/demo/service/ReplenishmentService.java) |
| 测试 | 6 组补货形态 | [ReplenishmentServiceTest.java](src/test/java/com/github/paohaijiao/demo/ReplenishmentServiceTest.java) |

**规则集拆分**（10 个规则方法）

| 规则方法 | 口径 |
|----------|------|
| `demandDuringLeadTime` | 日均销量 × 提前期天数 |
| `safetyStockLevel` | 基础安全库存 + 促销加日均×5，履约率 <0.9 再加×3 |
| `targetStock` | 提前期需求 + 安全库存，旺季 +日均×7、淡季 -日均×3 |
| `shortage` | 目标库存 - 当前库存，不为负 |
| `adjustToMoq` | 不足最小起订量按 MOQ 下单 |
| `roundToPack` | 按整箱包装向上取整（**while 循环实现**） |
| `stockDays` | 当前库存 / 日均销量（可售天数） |
| `urgency` | 可售天数 ≤提前期 CRITICAL、≤+3 HIGH、≤+7 MEDIUM、否则 LOW |
| `suggestExpress` | 紧急且旺季建议加急物流 |
| `cappedQty` | 单次下单量不超过供应商上限 |

**规则脚本（XML 节选）**

```jquick
int def roundToPack(int:qty, int:packSize) {
    int rounded = 0;
    if (packSize <= 0) {
        return qty;
    }
    while (rounded < qty) {
        rounded = rounded + packSize;
    }
    return rounded;
}

str def urgency(int:stockDays, int:leadTimeDays) {
    if (stockDays <= leadTimeDays) {
        return "CRITICAL";
    } else if (stockDays <= leadTimeDays + 3) {
        return "HIGH";
    } else if (stockDays <= leadTimeDays + 7) {
        return "MEDIUM";
    } else {
        return "LOW";
    }
}
```

**实测：紧急缺货**（当前库存 20、目标库存 290、缺口 270）

```text
目标库存: 290       缺口: 270        紧急度: CRITICAL    建议加急: true
```

**实测：促销旺季大单**（目标 490、缺口 390，受 MOQ 约束）

```text
目标库存: 490       缺口: 390        下单量: 500（按 MOQ 上调）
```

其余 4 组形态（库存充足短路、淡季整箱取整 60、中等紧急 HIGH、单次上限封顶 1000 建议拆单）见 [ReplenishmentServiceTest.java](src/test/java/com/github/paohaijiao/demo/ReplenishmentServiceTest.java)。

---

### 场景 8：税务合规风险打分（生产级样板）

**业务背景**：企业纳税合规评估。从逾期申报、缺票、税负率偏离行业均值、进项占比、异常发票、新设企业六个维度独立打分，输出风险等级与稽查处置（税务稽查/实地走访/自查），并生成命中指标清单。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | 企业税务画像、风险决策 | [TaxProfile.java](src/test/java/com/github/paohaijiao/demo/model/TaxProfile.java) · [TaxRiskDecision.java](src/test/java/com/github/paohaijiao/demo/model/TaxRiskDecision.java) |
| 规则集 | 六维评分 + 等级 + 稽查路由 | [TaxComplianceRules.java](src/test/java/com/github/paohaijiao/demo/TaxComplianceRules.java) · [tax-compliance.xml](src/test/resources/demo/tax-compliance.xml) |
| 编排层 | 六维汇总 clamp 0~100、指标清单与说明组装 | [TaxComplianceService.java](src/test/java/com/github/paohaijiao/demo/service/TaxComplianceService.java) |
| 测试 | 6 组企业形态 | [TaxComplianceServiceTest.java](src/test/java/com/github/paohaijiao/demo/TaxComplianceServiceTest.java) |

**规则集拆分**（10 个规则方法）

| 规则方法 | 口径 | 分值 |
|----------|------|------|
| `scoreLate` | 逾期申报每次 5 分，封顶 25 | 0 ~ 25 |
| `scoreMissingInvoice` | 缺票每次 8 分，封顶 24 | 0 ~ 24 |
| `scoreBurden` | 税负率 <行业均值×0.5 得 20、<×0.8 得 12（交叉相乘比较） | 0 ~ 20 |
| `scoreInputRatio` | 进项占比 >0.95 得 15、>0.85 得 8 | 0 ~ 15 |
| `scoreAbnormal` | 异常发票一票标记 20 分 | 0 ~ 20 |
| `scoreAge` | 成立 <1 年 10 分、<3 年 5 分 | 0 ~ 10 |
| `riskLevel` | ≥70 HIGH / ≥40 MEDIUM / 否则 LOW | - |
| `auditLevel` | 异常发票或 ≥70 → 税务稽查；≥40 → 实地走访；否则自查 | - |
| `needWarning` | MEDIUM 以上预警 | - |
| `needInvestigation` | HIGH 或异常发票立案调查 | - |

**规则脚本（XML 节选）**

```jquick
str def auditLevel(int:riskScore, boolean:hasAbnormalInvoice) {
    if (hasAbnormalInvoice || riskScore >= 70) {
        return "TAX_AUDIT";
    } else if (riskScore >= 40) {
        return "FIELD_VISIT";
    } else {
        return "SELF_CHECK";
    }
}
```

**实测：高风险企业**（多维度全部命中）

```text
风险分: 100 (HIGH)   稽查方式: TAX_AUDIT    立案调查: true
命中指标: 7 条（逾期申报、缺票、税负率显著偏低、进项异常、异常发票、新设企业……）
```

**实测：异常发票但总分不高**（分数 20，LOW）

```text
风险分: 20 (LOW)     稽查方式: TAX_AUDIT（异常发票一票路由，不受等级限制）
```

其余 4 组形态（合规良好 0 分自查、中风险 48 实地走访、进项异常 32、新设企业 72 HIGH）见 [TaxComplianceServiceTest.java](src/test/java/com/github/paohaijiao/demo/TaxComplianceServiceTest.java)。

---

### 场景 9：IoT 设备告警分级（生产级样板）

**业务背景**：对传感器、网关、摄像头、工业机器人等设备做实时告警分级。温度、错误次数、CPU/内存、电量、连接、振动六个维度独立打分，输出告警等级、派单优先级、通知渠道、自动处置（隔离/限流/降频）与升级时限。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | 设备运行快照、告警决策 | [DeviceAlarm.java](src/test/java/com/github/paohaijiao/demo/model/DeviceAlarm.java) · [AlarmDecision.java](src/test/java/com/github/paohaijiao/demo/model/AlarmDecision.java) |
| 规则集 | 六维评分 + 类型归类 + 派单/通知/处置 | [DeviceAlarmRules.java](src/test/java/com/github/paohaijiao/demo/DeviceAlarmRules.java) · [device-alarm.xml](src/test/resources/demo/device-alarm.xml) |
| 编排层 | 六维汇总封顶、指标与处置动作组装 | [DeviceAlarmService.java](src/test/java/com/github/paohaijiao/demo/service/DeviceAlarmService.java) |
| 测试 | 6 组告警形态 | [DeviceAlarmServiceTest.java](src/test/java/com/github/paohaijiao/demo/DeviceAlarmServiceTest.java) |

**规则集拆分**（13 个规则方法）

| 规则方法 | 口径 | 分值 |
|----------|------|------|
| `scoreTemperature` | ≥90℃ 35 / ≥80 25 / ≥70 15 / ≥60 8 | 0 ~ 35 |
| `scoreError` | 错误次数 ≥20/≥10/≥5/≥1 | 0 ~ 25 |
| `scoreResource` | CPU/内存峰值 ≥95/≥85/≥75 | 0 ~ 20 |
| `scoreBattery` | 电量 ≤5% 15 / ≤20% 8 | 0 ~ 15 |
| `scoreConnection` | 断连 25 / 心跳 ≥300s 15 / ≥120s 8 | 0 ~ 25 |
| `scoreVibration` | 振动 ≥8/≥5/≥3 | 0 ~ 20 |
| `alarmLevel` | ≥70 CRITICAL / ≥45 MAJOR / ≥20 MINOR / 否则 NORMAL | - |
| `alarmType` | 网络 > 温度 > 性能 > 电量 > 振动（按优先级归类） | - |
| `needDispatch` | ≥45 派单；20~44 且关键设备派单 | - |
| `dispatchPriority` | P0 / P1 / P2 / P3 | - |
| `notifyChannel` | CRITICAL 电话、MAJOR 短信、MINOR IM、其余邮件 | - |
| `autoRemediation` | ≥70 或网络类隔离、性能类限流、温度/振动类降频 | - |
| `escalateMinutes` | CRITICAL 5 / MAJOR 15 / MINOR 60 / NORMAL 240 分钟 | - |

**规则脚本（XML 节选）**

```jquick
int def scoreResource(int:cpuUsage, int:memoryUsage) {
    int peak = cpuUsage;
    if (memoryUsage > peak) {
        peak = memoryUsage;
    }
    if (peak >= 95) {
        return 20;
    } else if (peak >= 85) {
        return 12;
    } else if (peak >= 75) {
        return 6;
    } else {
        return 0;
    }
}

str def autoRemediation(str:alarmType, int:alarmScore) {
    if (alarmScore >= 70) {
        return "ISOLATE";
    } else if (alarmType == "NETWORK") {
        return "ISOLATE";
    } else if (alarmType == "PERFORMANCE") {
        return "RATE_LIMIT";
    } else if (alarmType == "TEMPERATURE") {
        return "THROTTLE";
    } else {
        return "NONE";
    }
}
```

**实测：炼钢炉温控传感器严重过热**（92℃、22 次错误、CPU 96%）

```text
告警分: 80 (CRITICAL)  类型: TEMPERATURE    派单: P0
通知: PHONE_CALL       自动处置: ISOLATE     升级时限: 5 分钟
```

**实测：园区闸机网关断连**（断连 + 心跳 600s + 10 次错误 + 低电量 15%）

```text
告警分: 51 (MAJOR)     类型: NETWORK        派单: P1
通知: SMS              自动处置: ISOLATE     升级时限: 15 分钟
```

其余 4 组形态（性能 22 MINOR 限流、关键设备低电量 P2 派单、设备正常 0 分、振动 38 降频）见 [DeviceAlarmServiceTest.java](src/test/java/com/github/paohaijiao/demo/DeviceAlarmServiceTest.java)。

---

### 场景 10：优惠券发放规则（生产级样板）

**业务背景**：电商发券。基础满减按会员等级 × 订单金额分档，叠加新客、忠诚、品类三类奖励，确定券类型（按业务优先级归类）、券名、使用门槛、有效期、是否可叠加，并对风控账户与大额券触发人工复核。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | 发券请求、发券决策 | [CouponRequest.java](src/test/java/com/github/paohaijiao/demo/model/CouponRequest.java) · [CouponDecision.java](src/test/java/com/github/paohaijiao/demo/model/CouponDecision.java) |
| 规则集 | 基础满减 + 三类奖励 + 券类型/门槛/风控 | [CouponRules.java](src/test/java/com/github/paohaijiao/demo/CouponRules.java) · [coupon.xml](src/test/resources/demo/coupon.xml) |
| 编排层 | 金额汇总、未命中清零、发放依据组装 | [CouponService.java](src/test/java/com/github/paohaijiao/demo/service/CouponService.java) |
| 测试 | 6 组发券形态 | [CouponServiceTest.java](src/test/java/com/github/paohaijiao/demo/CouponServiceTest.java) |

**规则集拆分**（11 个规则方法）

| 规则方法 | 口径 |
|----------|------|
| `baseDiscount` | 订单 ≥1000/≥500/≥200 元三档 × VIP/GOLD/SILVER/NORMAL 四等级 |
| `newUserBonus` | 注册 7 天内新客 +30 |
| `loyaltyBonus` | 下单 ≥50 且 VIP/GOLD +50；≥20 单 +20 |
| `categoryBonus` | 活动期生鲜 +15、数码 +10 |
| `couponType` | 新客 > VIP 大额 > 大额满减 > 忠诚 > 品类 > 普通满减 > 无 |
| `couponName` | 券类型 → 中文券名 |
| `threshold` | 优惠金额 → 使用门槛（满 2000 减 200……满 100 减 10） |
| `validDays` | 新客券 7 天、VIP 券 30 天、大额券 15 天、其余 10 天 |
| `needManualReview` | 风控标记或优惠 ≥200 元转人工 |
| `stackable` | 已领券或新客券不可叠加 |
| `riskLevel` | 风控标记 HIGH；注册 <3 天且无下单 MEDIUM；否则 LOW |

**规则脚本（XML 节选）**

```jquick
str def couponType(str:memberLevel, double:orderAmount, boolean:newUser, int:joinDays,
                   int:orderCount, str:category, boolean:campaignActive) {
    if (newUser && joinDays <= 7 && orderAmount >= 50.0) {
        return "NEW_USER";
    } else if (memberLevel == "VIP" && orderAmount >= 1000.0) {
        return "VIP_EXCLUSIVE";
    } else if (orderAmount >= 1000.0) {
        return "LARGE_ORDER";
    } else if (orderCount >= 20 && (memberLevel == "VIP" || memberLevel == "GOLD")) {
        return "LOYALTY";
    } else if (campaignActive && category == "FRESH") {
        return "CATEGORY";
    } else if (orderAmount >= 200.0) {
        return "ORDER";
    } else {
        return "NONE";
    }
}
```

**实测：新客首单**（注册 2 天、88 元）

```text
券类型: NEW_USER     券名: 新客专享券   优惠: 30.0   门槛: 200.0
有效期: 7 天         可叠加: false       风控: MEDIUM
```

**实测：GOLD 忠诚会员活动期生鲜单**（600 元、35 单）

```text
券类型: LOYALTY      券名: 忠诚回馈券   优惠: 115.0（80 + 20 + 15）
门槛: 1000.0         有效期: 10 天      可叠加: true
```

其余 4 组形态（VIP 200 元券转人工、SILVER 大额满减 100、风控账户 230 元挂起、未命中策略不发券）见 [CouponServiceTest.java](src/test/java/com/github/paohaijiao/demo/CouponServiceTest.java)。

---

### 场景 11：政务人才补贴资格审核（生产级样板）

**业务背景**：人才安家/创业/租房补贴的线上申报审核。失信、刑事犯罪、重复申领一票否决；材料不齐退回补正；学历、年龄、本地社保、人才认定、创业带动就业五维评分决定通过/人工复核/驳回；通过后按学历、人才类别、带动就业分项定额。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | 补贴申请要素、审核决策 | [SubsidyApplication.java](src/test/java/com/github/paohaijiao/demo/model/SubsidyApplication.java) · [SubsidyDecision.java](src/test/java/com/github/paohaijiao/demo/model/SubsidyDecision.java) |
| 规则集 | 五维评分 + 一票否决 + 分级结论 + 三项定额 | [SubsidyRules.java](src/test/java/com/github/paohaijiao/demo/SubsidyRules.java) · [subsidy-eligibility.xml](src/test/resources/demo/subsidy-eligibility.xml) |
| 编排层 | 评分汇总、否决/补正前置、仅通过时定额、说明组装 | [SubsidyService.java](src/test/java/com/github/paohaijiao/demo/service/SubsidyService.java) |
| 测试 | 6 组申报形态 | [SubsidyServiceTest.java](src/test/java/com/github/paohaijiao/demo/SubsidyServiceTest.java) |

**规则集拆分**（10 个规则方法）

| 规则方法 | 口径 | 分值 |
|----------|------|------|
| `scoreEducation` | 博士 30 / 硕士 22 / 本科 15 / 大专 8 | 0 ~ 30 |
| `scoreAge` | ≤30 岁 15 / ≤35 12 / ≤40 8 / ≤45 4 | 0 ~ 15 |
| `scoreSocialSecurity` | 本地社保 ≥36/≥24/≥12/≥6 月 | 0 ~ 20 |
| `scoreTalent` | A 类 25 / B 类 18 / C 类 10 | 0 ~ 25 |
| `scoreStartup` | 创业带动就业 ≥10/≥5/≥1 人 | 0 ~ 20 |
| `hardReject` | 失信 / 刑事犯罪 / 重复申领任一命中 | - |
| `eligibilityStage` | 否决优先 → 材料补正 → ≥75 通过 / ≥50 复核 / 否则驳回 | - |
| `educationAmount` | 博士 10 万 / 硕士 6 万 / 本科 3 万 / 大专 1 万 | - |
| `talentAmount` | A 类 20 万 / B 类 10 万 / C 类 5 万 | - |
| `startupAmount` | 带动 ≥10 人 10 万 / ≥5 人 5 万 / ≥1 人 2 万 | - |

**规则脚本（XML 节选）**

```jquick
str def eligibilityStage(int:qualificationScore, boolean:hardReject, boolean:documentsComplete) {
    if (hardReject) {
        return "REJECTED";
    } else if (documentsComplete == false) {
        return "MANUAL_REVIEW";
    } else if (qualificationScore >= 75) {
        return "QUALIFIED";
    } else if (qualificationScore >= 50) {
        return "MANUAL_REVIEW";
    } else {
        return "REJECTED";
    }
}
```

**实测：博士 A 类人才创业**（28 岁、社保 48 月、带动就业 12 人）

```text
资格评分: 110        审核结论: QUALIFIED
学历补贴: 100000.0   人才奖励: 200000.0   创业奖励: 100000.0
核定补贴: 400000.0
```

**实测：硕士条件优秀但失信**（评分 ≥70，触发一票否决）

```text
审核结论: REJECTED   核定补贴: 0.0
说明: 存在失信被执行记录，触发一票否决
```

其余 4 组形态（75 分边界通过核定 16 万、本科 C 类 55 分复核、博士材料不齐补正、大专创业 36 分驳回）见 [SubsidyServiceTest.java](src/test/java/com/github/paohaijiao/demo/SubsidyServiceTest.java)。

---

### 场景 12：产线质检分级与处置（生产级样板）

**业务背景**：离散制造批次质检。按致命/严重/轻微缺陷数扣分，叠加外观、关键尺寸、设备状态加扣项，结合不良率（千分比）判定接收/返工/报废/挂起四级结论，决定处置去向、批次放行与 CAPA 纠正预防措施。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | 批次抽检记录、质检决策 | [InspectionBatch.java](src/test/java/com/github/paohaijiao/demo/model/InspectionBatch.java) · [InspectionDecision.java](src/test/java/com/github/paohaijiao/demo/model/InspectionDecision.java) |
| 规则集 | 不良率/公差/扣分 + 等级/处置/CAPA/放行 | [QualityInspectionRules.java](src/test/java/com/github/paohaijiao/demo/QualityInspectionRules.java) · [quality-inspection.xml](src/test/resources/demo/quality-inspection.xml) |
| 编排层 | 不良率与扣分汇总、等级装配、判定说明组装 | [QualityInspectionService.java](src/test/java/com/github/paohaijiao/demo/service/QualityInspectionService.java) |
| 测试 | 6 组批次形态 | [QualityInspectionServiceTest.java](src/test/java/com/github/paohaijiao/demo/QualityInspectionServiceTest.java) |

**规则集拆分**（10 个规则方法）

| 规则方法 | 口径 |
|----------|------|
| `defectRatePermil` | 不良数 ×1000 / 抽检数（整数运算，输出千分比） |
| `dimensionPass` | 实测值对标称值做双向公差判定 |
| `scoreDefects` | 致命 -40 / 严重 -15 / 轻微 -5，下限 0 |
| `scoreAppearance` | 外观 ≥95 不扣、≥85 扣 10、否则扣 20 |
| `scoreDimension` | 尺寸超差扣 30 |
| `scoreEquipment` | 设备告警扣 5 |
| `qualityLevel` | 致命缺陷或不良率 ≥200‰ 报废；<40 分或 ≥100‰ 返工；<60 分或 ≥30‰ 挂起；否则接收 |
| `disposition` | 报废区 / 返工线 / QA 挂起 / 下一工序 |
| `needCapa` | 致命缺陷、严重缺陷 ≥3 件或设备告警 |
| `releaseAllowed` | 接收且关键尺寸合格才放行 |

**规则脚本（XML 节选）**

```jquick
str def qualityLevel(int:qualityScore, int:criticalDefects, int:defectRatePermil) {
    if (criticalDefects >= 1 || defectRatePermil >= 200) {
        return "SCRAP";
    } else if (qualityScore < 40 || defectRatePermil >= 100) {
        return "REWORK";
    } else if (qualityScore < 60 || defectRatePermil >= 30) {
        return "HOLD";
    } else {
        return "ACCEPT";
    }
}
```

**实测：优质批次**（200 抽 1 轻微不良、尺寸合格、外观 97）

```text
质量分: 95   不良率: 5‰    等级: ACCEPT    处置: NEXT_PROCESS    放行: true
```

**实测：致命缺陷批次**（1 件致命缺陷，不看分数）

```text
质量分: 30   等级: SCRAP   处置: SCRAP_BIN  CAPA: true
说明: 发现致命缺陷 1 件，整批判废并隔离
```

其余 4 组形态（尺寸超差 20 分返工、不良率 40‰ 挂起、设备告警 +3 严重缺陷 CAPA、50 抽 16 不良 320‰ 报废）见 [QualityInspectionServiceTest.java](src/test/java/com/github/paohaijiao/demo/QualityInspectionServiceTest.java)。

---

### 场景 13：医保报销合规校验（生产级样板）

**业务背景**：医保即时结算。先判定甲乙丙类目录范围，目录外全自费；目录内按参保类型 × 医院等级确定起付线与报销比例（急诊减半/上浮 5 个点），乙类先行自付 10%，最后受年度统筹封顶线截断；异地未备案转人工审核。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | 结算要素、报销决策 | [MedicalClaim.java](src/test/java/com/github/paohaijiao/demo/model/MedicalClaim.java) · [ReimbursementDecision.java](src/test/java/com/github/paohaijiao/demo/model/ReimbursementDecision.java) |
| 规则集 | 目录/起付线/比例/先行自付/封顶/结论 | [MedicalReimbursementRules.java](src/test/java/com/github/paohaijiao/demo/MedicalReimbursementRules.java) · [medical-reimbursement.xml](src/test/resources/demo/medical-reimbursement.xml) |
| 编排层 | 报销链路串联（目录→自付→起付线→比例→封顶）、说明组装 | [MedicalReimbursementService.java](src/test/java/com/github/paohaijiao/demo/service/MedicalReimbursementService.java) |
| 测试 | 6 组结算形态 | [MedicalReimbursementServiceTest.java](src/test/java/com/github/paohaijiao/demo/MedicalReimbursementServiceTest.java) |

**规则集拆分**（8 个规则方法）

| 规则方法 | 口径 |
|----------|------|
| `withinCatalog` | 丙类（DRUG_C）目录外 |
| `deductible` | 职工 800/500/200、居民 600/300/100；急诊减半 |
| `reimburseRatio` | 职工 0.9/0.85/0.8、居民 0.7/0.65/0.6；急诊 +0.05，上限 1.0 |
| `prepaidSelfPay` | 乙类药先行自付 10% |
| `annualCap` | 职工 30 万 / 居民 20 万 |
| `applyCap` | 支付额超过年度剩余额度时截断 |
| `complianceResult` | 目录外 SELF_PAY；异地未备案 MANUAL_REVIEW；否则 APPROVE |
| `needManualReview` | 异地未备案或单笔统筹支付 ≥5 万 |

报销链路在 Service 中串联：`(总费用 - 乙类先行自付 - 起付线) × 比例`，再做封顶截断。

**规则脚本（XML 节选）**

```jquick
str def complianceResult(boolean:withinCatalog, boolean:offSite, boolean:filed) {
    if (withinCatalog == false) {
        return "SELF_PAY";
    } else if (offSite && filed == false) {
        return "MANUAL_REVIEW";
    } else {
        return "APPROVE";
    }
}
```

**实测：职工三甲甲类药**（费用 5000 元）

```text
起付线: 800.0    报销比例: 0.9    统筹支付: 3780.0    个人自付: 1220.0
结论: APPROVE    人工审核: false
```

**实测：大额费用触封顶**（费用 60 万、年度已报 28 万、剩余额度 2 万）

```text
理论支付: 539280.0   实际支付: 20000.0（按年度剩余额度截断）
个人自付: 580000.0   触达封顶线: true
```

其余 4 组形态（居民社区乙类 480、丙类全自费、职工急诊 2475、异地未备案挂账 4680）见 [MedicalReimbursementServiceTest.java](src/test/java/com/github/paohaijiao/demo/MedicalReimbursementServiceTest.java)。

---

### 场景 14：招聘简历智能初筛（生产级样板）

**业务背景**：招聘场景两段式筛选。第一段用学历、年限、技能覆盖率（80%）、薪资带宽、工作地五个硬门槛 + 跳槽/空窗否决项确定候选集合；第二段对通过者按学历院校、经验、技能、稳定性、资质英语五维评分，路由到面试（STRONG/NORMAL）、人才储备或淘汰。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | 简历要素、初筛决策 | [Resume.java](src/test/java/com/github/paohaijiao/demo/model/Resume.java) · [ScreeningDecision.java](src/test/java/com/github/paohaijiao/demo/model/ScreeningDecision.java) |
| 规则集 | 5 项硬门槛 + 否决项 + 5 维评分 + 结论路由 | [ResumeScreeningRules.java](src/test/java/com/github/paohaijiao/demo/ResumeScreeningRules.java) · [resume-screening.xml](src/test/resources/demo/resume-screening.xml) |
| 编排层 | 门槛逐项判定与否决原因、评分汇总、推荐等级仅对通过者生效 | [ResumeScreeningService.java](src/test/java/com/github/paohaijiao/demo/service/ResumeScreeningService.java) |
| 测试 | 6 组候选人形态 | [ResumeScreeningServiceTest.java](src/test/java/com/github/paohaijiao/demo/ResumeScreeningServiceTest.java) |

**规则集拆分**（14 个规则方法）

| 规则方法 | 口径 |
|----------|------|
| `eduRank` / `educationPass` | 学历档位化（博 4/硕 3/本 2/专 1）后比较 |
| `experiencePass` | 相关年限 ≥ 岗位要求 |
| `skillPass` | 技能覆盖率 ≥80%（交叉相乘，避免除法精度问题） |
| `salaryPass` | 期望 ≤ 带宽上限且 ≥ 下限八成 |
| `locationPass` | 同城或接受异地调动 |
| `hardReject` | 近 5 年跳槽 ≥4 次或空窗 ≥24 月 |
| `scoreEducation` | 超档 20/高 1 档 15/达标 10，另加 985+5、211+3 |
| `scoreExperience` | 超出年限 ≥5/≥2/≥0 → 25/20/12 |
| `scoreSkills` | 覆盖 100%/80%/60% → 20/14/8 |
| `scoreStability` | 跳槽 0/1/2 次 → 15/10/5，空窗 ≤3 月 +5 |
| `scoreBonus` | 资格证 +5，CET6 +5 / CET4 +3 |
| `screeningResult` | 否决/门槛不满足 → REJECT；≥75 面试、≥55 储备 |
| `recommendLevel` | ≥85 STRONG / ≥75 NORMAL / ≥55 RESERVE |

**规则脚本（XML 节选）**

```jquick
boolean def skillPass(int:skillMatchCount, int:requiredSkillCount) {
    if (requiredSkillCount <= 0) {
        return true;
    } else if (skillMatchCount * 100 >= requiredSkillCount * 80) {
        return true;
    } else {
        return false;
    }
}

str def screeningResult(int:totalScore, boolean:hardCheckPass, boolean:hardReject) {
    if (hardReject) {
        return "REJECT";
    } else if (hardCheckPass == false) {
        return "REJECT";
    } else if (totalScore >= 75) {
        return "INTERVIEW";
    } else if (totalScore >= 55) {
        return "RESERVE";
    } else {
        return "REJECT";
    }
}
```

**实测：理想候选人**（硕士 985、8 年、技能全匹配、零跳槽、证 + CET6）

```text
硬门槛: 全部满足     综合分: 90     结论: INTERVIEW    推荐: STRONG
```

**实测：高频跳槽否决**（近 5 年 5 次跳槽，硬门槛全满足也淘汰）

```text
结论: REJECT         推荐: NONE
说明: 近 5 年跳槽 5 次或空窗 1 个月，命中稳定性否决项
```

其余 4 组形态（学历不达标、薪资超带宽、大专 57 分储备、异地愿调动 78 分 NORMAL）见 [ResumeScreeningServiceTest.java](src/test/java/com/github/paohaijiao/demo/ResumeScreeningServiceTest.java)。

---

### 场景 15：银行卡交易反欺诈（生产级样板）

**业务背景**：支付实时风控。金额、短时频次、地理位置、交易时段、设备、商户、无卡交易、鉴权失败、行为偏离/新账户九维风险因子独立计分，汇总后决定拦截/强验证（短信人脸）/放行；受信商户白名单短路放行；中风险境外交易与高风险交易实时预警。

**架构分层**

| 层次 | 职责 | 文件 |
|------|------|------|
| 领域模型 | 交易快照、风控决策 | [BankTransaction.java](src/test/java/com/github/paohaijiao/demo/model/BankTransaction.java) · [FraudDecision.java](src/test/java/com/github/paohaijiao/demo/model/FraudDecision.java) |
| 规则集 | 九维风险因子 + 等级/处置/预警 | [TransactionFraudRules.java](src/test/java/com/github/paohaijiao/demo/TransactionFraudRules.java) · [transaction-fraud.xml](src/test/resources/demo/transaction-fraud.xml) |
| 编排层 | 白名单短路、九维汇总封顶、风险指标明细组装 | [TransactionFraudService.java](src/test/java/com/github/paohaijiao/demo/service/TransactionFraudService.java) |
| 测试 | 6 组交易形态 | [TransactionFraudServiceTest.java](src/test/java/com/github/paohaijiao/demo/TransactionFraudServiceTest.java) |

**规则集拆分**（12 个规则方法）

| 规则方法 | 口径 | 分值 |
|----------|------|------|
| `scoreAmount` | ≥5 万 30 / ≥2 万 20 / ≥1 万 12 / ≥5 千 6 | 0 ~ 30 |
| `scoreVelocity` | 1 小时笔数 ≥10/≥5/≥3 + 短时累计金额，封顶 25 | 0 ~ 25 |
| `scoreGeo` | 境外 25 / 异地未报备 15 / 已报备出行 5 | 0 ~ 25 |
| `scoreTime` | 23 时-5 时 12、22/6 时 6 | 0 ~ 12 |
| `scoreDevice` | 设备变更 15 | 0 ~ 15 |
| `scoreMerchant` | 新商户 8 | 0 ~ 8 |
| `scoreCardNotPresent` | 线上/移动无卡 10 | 0 ~ 10 |
| `scoreFailure` | 24h 鉴权失败 ≥3 次 10 / ≥1 次 5 | 0 ~ 10 |
| `scoreDeviation` | 金额偏离历史 5 倍/3 倍加 15/8；开户 <7 天 +15 | 0 ~ 30 |
| `riskLevel` | ≥75 HIGH / ≥45 MEDIUM / 否则 LOW | - |
| `fraudAction` | BLOCK / STEP_UP / PASS | - |
| `needAlert` | HIGH 必预警；MEDIUM 且境外预警 | - |

**规则脚本（XML 节选）**

```jquick
int def scoreVelocity(int:txnCount1h, double:txnAmount1h) {
    int score = 0;
    if (txnCount1h >= 10) {
        score = 25;
    } else if (txnCount1h >= 5) {
        score = 15;
    } else if (txnCount1h >= 3) {
        score = 8;
    }
    if (txnAmount1h >= 100000.0) {
        score = score + 15;
    } else if (txnAmount1h >= 50000.0) {
        score = score + 8;
    }
    if (score > 25) {
        score = 25;
    }
    return score;
}
```

**实测：深夜境外线上大额**（2 时、3 万、境外、新设备、无卡、1 次失败，9 因子叠加）

```text
风险分: 100 (HIGH)   处置: BLOCK      实时预警: true
指标: 境外交易 | 深夜时段 | 设备变更 | 无卡交易 | 金额偏离历史……
```

**实测：受信商户白名单**（命中多项风险因子但商户受信）

```text
风险分: 0 (LOW)      处置: PASS       指标: 受信商户白名单交易，直接放行
```

其余 4 组形态（日常 0 分放行、报备出行 29 分、夜间新设备 65 分强验证、新账户连续失败 72 分）见 [TransactionFraudServiceTest.java](src/test/java/com/github/paohaijiao/demo/TransactionFraudServiceTest.java)。

---

## 四、49 个业务场景覆盖矩阵

下表把常见业务诉求映射到 15 个生产级样板。标记 **直接覆盖** 的诉求已有完整可运行 demo；标记 **同范式落地** 的诉求与某样板决策骨架一致，替换模型字段与阈值表即可落地。

### 4.1 金融银行 / 信贷风控（12 条）

| # | 业务诉求 | 对应关系 |
|---|----------|----------|
| 1 | 个人信贷申请准入规则校验 | **直接覆盖**：场景 1 |
| 2 | 个人信用评分卡动态加权计算 | **直接覆盖**：场景 1（权重阈值外置）；另见 [CreditScoreMapper](src/test/java/com/github/paohaijiao/xml/CreditScoreMapper.java) |
| 3 | 企业经营贷授信额度智能测算 | 同范式落地：场景 1 的多维评分 + `suggestLimit` 额度测算 |
| 4 | 贷款反欺诈风险识别（设备/地址/黑名单） | **直接覆盖**：场景 15（设备、频次、白名单）+ 场景 1 否决项 |
| 5 | 信用卡进件审批分级（通过/复核/拒绝） | **直接覆盖**：场景 1 的 PASS / MANUAL_REVIEW / REJECT 分流 |
| 6 | 保单核保风险判定 | 同范式落地：场景 3（职业/健康分档替换理赔维度） |
| 7 | 保险理赔责任与赔付比例核算 | **直接覆盖**：场景 3 |
| 8 | 供应链金融供应商准入评级 | 同范式落地：场景 8 评分评级 + 场景 14 硬门槛 |
| 9 | 逾期贷后分层催收策略 | **直接覆盖**：场景 2 |
| 10 | 理财客户风险等级匹配测评 | 同范式落地：场景 14 硬门槛（等级不匹配禁止购买） |
| 11 | 银行卡交易风控拦截 | **直接覆盖**：场景 15 |
| 12 | 白名单/黑名单动态判定 | **直接覆盖**：场景 15 白名单短路 + 场景 11 失信否决（名单均可配置化） |

### 4.2 政务 / 国资 / 企服（12 条）

| # | 业务诉求 | 对应关系 |
|---|----------|----------|
| 13 | 创业/人才补贴资格自动审核 | **直接覆盖**：场景 11 |
| 14 | 科技项目申报材料打分初审 | 同范式落地：场景 11（五维评分 + 通过/复核/驳回） |
| 15 | 政务办事材料完整性前置校验 | **直接覆盖**：场景 11 `documentsComplete` 补正分支 + 场景 3 材料校验 |
| 16 | 落户/购房/购车资格多政策判定 | 同范式落地：场景 11/14（一票否决 + 多条件门槛） |
| 17 | 政府采购供应商准入与失信过滤 | **直接覆盖**：场景 11 失信一票否决 |
| 18 | 国资项目立项投资额度合规校验 | 同范式落地：场景 13 分段核算（额度截断）+ 场景 11 |
| 19 | 企业信用等级自动评级 | **直接覆盖**：场景 8 |
| 20 | 行政审批流程智能路由 | **直接覆盖**：场景 5 时限/升级路由 + 场景 2 渠道路由 |
| 21 | 小微税费减免/稳岗补贴资格判定 | 同范式落地：场景 8（税费口径）+ 场景 11（资格定额） |
| 22 | 应急突发事件等级自动分级 | **直接覆盖**：场景 9（多维阈值 → 等级 → 通知/处置） |
| 23 | 行业许可证年审合规校验 | 同范式落地：场景 8（多条件不合规项计分） |
| 24 | 招投标资格预审 | **直接覆盖**：场景 14 两段式筛选（资质门槛 + 评分排名） |

### 4.3 电商 / 零售 / 本地生活（8 条）

| # | 业务诉求 | 对应关系 |
|---|----------|----------|
| 25 | 营销活动叠加/互斥控制 | **直接覆盖**：场景 10（`stackable` 叠加控制 + 券类型优先级） |
| 26 | 会员等级成长值权益升降级 | 同范式落地：场景 6（评分等级 + 系数）+ 场景 10（会员分档） |
| 27 | 订单防刷单风控校验 | 同范式落地：场景 15（频次、设备、行为偏离九维） |
| 28 | 渠道限价/最低价/价差管控 | **直接覆盖**：场景 4（最低折扣 0.30 + 毛利保护线） |
| 29 | 售后退款/退货/换货资格判定 | 同范式落地：场景 3（责任判定）+ 场景 14（门槛） |
| 30 | 用户画像标签动态分层 | 同范式落地：场景 6/10（分数 → 分层标签） |
| 31 | 分销返佣/上下级分润 | 同范式落地：场景 6（多系数加总，规则结构即 `baseCoefficient + 调节项`） |
| 32 | 预售尾款资格/履约条件校验 | 同范式落地：场景 7（履约/库存条件链路）+ 场景 14 |

### 4.4 制造 / MES / 供应链工业（6 条）

| # | 业务诉求 | 对应关系 |
|---|----------|----------|
| 33 | 产线质检多指标判定（合格/返工/报废） | **直接覆盖**：场景 12 |
| 34 | MES 工单优先级智能排序 | **直接覆盖**：场景 5（优先级权重 + 时限矩阵） |
| 35 | 生产设备健康度评分与故障预警 | **直接覆盖**：场景 9 |
| 36 | 原材料来料验收标准动态适配 | 同范式落地：场景 12（扣分阈值外置，按物料换 XML） |
| 37 | 车间能耗异常判定与超标预警 | 同范式落地：场景 9（阈值告警骨架，维度换成能耗指标） |
| 38 | 成品出入库合规与批次放行 | **直接覆盖**：场景 12 `releaseAllowed` + 场景 7 库存链路 |

### 4.5 医疗 / 医保 / 卫健（3 条）

| # | 业务诉求 | 对应关系 |
|---|----------|----------|
| 39 | 医保报销项目/比例/合规校验 | **直接覆盖**：场景 13 |
| 40 | 体检指标异常筛查与风险标注 | 同范式落地：场景 12 扣分分级 / 场景 9 阈值告警 |
| 41 | 门诊分诊/急诊优先级分级 | 同范式落地：场景 5（优先级 × 病情等级矩阵） |

### 4.6 HR / OA / 企业 SaaS（5 条）

| # | 业务诉求 | 对应关系 |
|---|----------|----------|
| 42 | 员工绩效 KPI 加权计分与等级评定 | **直接覆盖**：场景 6 |
| 43 | 薪资补贴自动核算（工龄/夜班/高温） | 同范式落地：场景 6（`bonusAmount` 分项加总结构） |
| 44 | 请假/加班/出差审批流转 | 同范式落地：场景 5（条件 → 审批人路由/时限） |
| 45 | 招聘简历智能初筛 | **直接覆盖**：场景 14 |
| 46 | 部门预算合规与超支预警 | 同范式落地：场景 9（超支率阈值分级预警） |

### 4.7 IoT / 校园 / 物业 / 通用系统（3 条）

| # | 业务诉求 | 对应关系 |
|---|----------|----------|
| 47 | IoT 传感器数据阈值告警分级 | **直接覆盖**：场景 9 |
| 48 | 校园奖学金/评优/助学金资格评定 | 同范式落地：场景 11（资格 + 评分 + 分级 + 定额） |
| 49 | 物业费/停车费减免资格判定 | 同范式落地：场景 11（硬条件 + 减免分档金额） |

**矩阵小结（49 条诉求已全部落地）**：

- **24 条缺口诉求建独立六件套样例**（场景 16-39，每套 6 个 @Test，共 144 用例）：诉求 3/6/8/10/14/16/18/21/23/26/27/29/30/31/32/36/37/40/41/43/44/46/48/49。
- **25 条由 15 套生产样板直接承接**（诉求 1/2/4/5/7/9/11/12/13/15/17/19/20/22/24/25/28/33/34/35/38/39/42/45/47），并在场景 40 [CoveredScenariosMatrixTest](src/test/java/com/github/paohaijiao/demo/CoveredScenariosMatrixTest.java) 中为每条诉求提供独立业务入口用例（共 25 个），另含 1 个场景 1 信贷准入 × 场景 15 交易反欺诈双引擎串联的跨场景联合拦截用例。
- 全部样例遵循同一套骨架：「评分分级型」（多维分项累加 → 等级 → 动作/定额）或「硬门槛前置两段式」（一票否决/材料门控 → 评分排名），复用时替换领域模型字段与 XML 阈值表即可。

---

## 五、附录 A：脚本语法速查（均经实测）

| 分类 | 支持情况 |
|------|----------|
| 类型关键字 | 已验证 `int` / `double` / `str`；词法层另提供 `float` / `long` / `short` / `byte` / `boolean` |
| 算术运算 | `+` `-` `*` `/`，支持括号与一元 `+` / `-` |
| 比较运算 | `>` `>=` `<` `<=` `==` `!=`（字符串比较使用 `==`） |
| 逻辑运算 | `&&` `||` |
| 控制结构 | `if` / `else if` / `else`、`for`、`while`、`break`、`continue`、`return` |
| 变量声明 | `int quota = 10;`、`double rate = 1.0;` |
| 函数定义 | `int def name(int:a, double:b) { ... }` |
| 实例方法 | `obj.method(int:1, str:"x")` |
| 静态方法 | `java.lang.String::valueOf(int:1)` |
| 构造函数 | `new java.lang.String(java.lang.String:"Hello")` |
| 静态变量对象 | `java.lang.System@out.println(java.lang.String:"Hello")` |
| 自定义函数调用 | `this.method(int:a)` |
| 内置 SPI 函数 | `Builtin::sum(int:1, int:2)` |
| 标准输出 | `console.log(表达式)` |
| 注释 | `// 单行`、`/* 多行 */` |

**当前解析器未提供的语法**（编写规则时请避开）：`!` 取反、`%` 取模、强制类型转换、三元运算符。

**本批 15 个场景实测踩到的三个坑（已在脚本中规避，写新规则时请直接采用规避写法）**：

1. **布尔取反**：不支持 `!flag`，改写为正向条件提前 `return`（见场景 6 `promotionEligible`），或在 Service 侧取反后传入（见场景 15 `!cardPresent`）。
2. **整数除法不提升浮点**：`int * 1.0 / int` 仍是整数除法。比率比较一律改写为整数交叉相乘（见场景 4 `slowMovingFactor`、场景 14 `skillPass`、场景 8 税负率比较）。
3. **百分比率用千分比整数承载**：如场景 12 不良率用 `不良数 × 1000 / 抽检数` 输出千分比整数，避免浮点口径争议。

---

## 六、附录 B：注意事项

1. **命名空间必须精确匹配**：XML 的 `namespace` 需与规则接口全限定名完全一致（含包名）。
2. **方法名必须一致**：脚本函数名与接口方法名不一致时，代理会找不到对应规则。
3. **参数按名绑定**：接口参数需用 `@Param` 标注，未标注时无法与脚本参数正确对应。
4. **返回值转换依据接口声明**：脚本统一以字符串形式产出结果，最终由接口方法返回类型决定转换目标。
5. **规则热更新**：XML 方式下修改规则文件后重建代理即可生效，无需重新编译业务代码。
6. **执行耗时日志**：规则代理每次调用会通过 `JConsole` 输出 `execute time:...s`，生产环境请按需调整日志级别。
7. **性能建议**：复用 `createApi(...)` 生成的代理实例，避免重复加载规则与重建调用链。
8. **Service 侧兜底**：分数汇总统一在 Java 侧 clamp 到合法区间（0~100），金额封顶、白名单短路、硬否决前置也放在 Java 侧，规则脚本只承载单一口径。

---

## 七、相关资源

- 项目主页与核心特性：[README.md](README.md)
- 本文档示例代码：[demo 规则接口](src/test/java/com/github/paohaijiao/demo) + [demo XML 规则](src/test/resources/demo) + [demo 编排服务](src/test/java/com/github/paohaijiao/demo/service)
- 信用评级完整示例：[CreditScoreMapper.java](src/test/java/com/github/paohaijiao/xml/CreditScoreMapper.java) + [credit-score.xml](src/test/resources/credit-score.xml)
- 内联规则与统一入口测试：[JQuickJavaTest.java](src/test/java/com/github/paohaijiao/xml/JQuickJavaTest.java)
- 内置 SPI 函数库：[jquick-transform-function](https://github.com/paohaijiao/jquick-transform-function)
