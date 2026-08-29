# JQuick Java

简体中文 | [ENGLISH](./README-EN.md)

[![Stars](https://img.shields.io/github/stars/paohaijiao/jquick-java.svg?style=social)](https://github.com/paohaijiao/jquick-java)
[![Forks](https://img.shields.io/github/forks/paohaijiao/jquick-java.svg?style=social)](https://github.com/paohaijiao/jquick-java/forks)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.paohaijiao/jquick-java)](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-java)
[![Awesome Java](https://img.shields.io/badge/Awesome-Java-ff69b4.svg)](https://github.com/akullpp/awesome-java)

> 已被收录至 [Awesome Java](https://github.com/akullpp/awesome-java) 的 **Miscellaneous** 精选章节

---

## 项目简介

**JQuick Java 是面向规则引擎的轻量级类 Java 脚本语言，以声明式配置驱动开发。**

它融合 Java 的强类型安全与 JavaScript 的动态灵活性，支持在**运行时动态加载、解析和执行业务规则**。业务规则以 XML / 内联脚本形式声明，与业务代码彻底分离——调整指标、权重、阈值就像修改配置一样简单，无需重启、无需重新部署，真正实现**低代码规则引擎**。

JQuick Java 面向企业级老旧环境设计：兼容 **Java 8+**，适配 **国产数据库生态**，可平稳嵌入存量系统。

典型场景：

- 企业信用评级 / 风险决策
- 复杂业务规则与数据校验
- 流程编排、评分计算
- 声明式低代码平台

---

## 核心特性

- **类 Java 脚本引擎**：运行时解析执行，强类型 + 动态灵活性
- **XML 规则代理**：接口 + XML 声明业务规则，自动生成动态代理
- **内联规则**：Java 代码内直接注册规则，与 XML 完全等价
- **无缝 Java 互通**：静态方法、构造方法、实例方法、`this` 自定义函数、内置 SPI（`Builtin::`）
- **ASM 高性能调用链**：反射与 ASM 双实现，按需选择
- **声明式配置驱动**：指标、权重、阈值由 Excel / XML 配置维护，无需改代码
- **轻量兼容**：Java 8+，兼容老旧 Java 环境与国产数据库生态
- **安全沙箱（黑白名单）**：内置调用防护 `JQuickJavaInvocationGuard`，类/方法级黑白名单，默认拦截危险系统调用
- **语言服务（LSP）**：内置 `JQuickLanguageServer`，提供 IDE 符号提示与补全支持

---

## 快速开始

### Maven 依赖

```xml
<dependency>
    <groupId>io.github.paohaijiao</groupId>
    <artifactId>jquick-java</artifactId>
    <version>2.5.0</version>
</dependency>
```

### 第一个 JQuick 脚本

```java
import List<java.lang.String> as StringList ;
StringList list=["A","B","C"];
```

### 运行单元测试

```bash
mvn test -Dtest=TEmEventScoringServiceTest
```

---

## 核心概念

### 规则与代码分离

规则脚本（函数定义）以 XML `<java>` 元素或内联字符串形式声明，通过接口动态代理暴露给业务代码。业务方只依赖接口，规则内容可随时调整。

### 脚本引擎

Visitor 执行链分层处理：字面量、表达式、控制结构、方法调用、作用域与函数注册。

### XML 规则代理

`src/test/resources/scoring-rules.xml` 即典型形态：

```xml
<javas namespace="com.github.paohaijiao.service.ScoringMapper">
    <java name="scoreCapital" returnClass="int">
        <![CDATA[
            int def scoreCapital(double:actualCapital, double:registeredCapital) {
                ...
            }
        ]]>
    </java>
</javas>
```

### JQuickJava 统一入口

`JQuickJava` 是开箱即用的一站式入口，链式 API 覆盖：包声明、变量初始化、脚本直接执行、XML / 内联规则代理。

| API | 说明 |
|-----|------|
| `JQuickJava.create()` | 创建默认入口实例 |
| `importPackage(qualifiedName, alias)` | 声明包引入，生成 `import Type as Alias;` |
| `constant(name, value)` / `variable(name, value)` / `env(name, value)` | 初始化常量、上下文变量、运行时环境变量 |
| `init(statement...)` | 追加脚本级初始化语句 |
| `rule(methodName, functionDefinition)` | 注册内联规则脚本（等价于 XML `<java>` 的 CDATA） |
| `execute(scriptBody)` | 直接执行 JQuick 脚本 |
| `createApi(apiInterface)` | 生成纯内联规则代理 |
| `createApi(apiInterface, xmlPath)` | 生成 XML + 内联规则合并代理 |

### 方法调用形式

| 形式 | 语法 | 说明 |
|------|------|------|
| 静态方法 | `ClassName::methodName(类型:参数, ...)` | Java 静态方法 |
| 构造方法 | `new ClassName(类型:参数, ...)` | Java 构造函数 |
| 实例方法 | `obj.methodName(类型:参数, ...)` | Java 对象方法 |
| 自定义函数 | `this.methodName(类型:参数, ...)` | JQuick 脚本函数 |
| 内置 SPI | `Builtin::methodName(类型:参数, ...)` | 平台级内置能力 |
| 静态变量访问 | `classsType@objectName.methodName(...)` | 静态变量对象方法 |

### 控制结构

JQuick 脚本内置完整的流程控制语法，与 Java 写法一致：

| 结构 | 语法 | 说明 |
|------|------|------|
| 条件分支 | `if (expr) { ... } else if (expr) { ... } else { ... }` | 支持任意层 `else if` |
| 计数循环 | `for (int i = 0; i < 10; i = i + 1) { ... }` | 初始化 / 条件 / 步进三段式 |
| 条件循环 | `while (expr) { ... }` | 条件成立则循环 |
| 循环控制 | `break;` / `continue;` | 跳出 / 跳过当前循环 |
| 函数返回 | `return expr;` | 返回值 |
| 标准输出 | `console.log(expr);` | 打印输出，支持字符串拼接 |

```jquick
// for + if + break（来自 JForTest）
for (int i = 0; i < 10; i = i + 1) {
    if (i == 2) {
        break;
    } else {
        console.log(i);
    }
}

// while + for + continue（来自 JWhileStatementTest）
while (true) {
    for (int a = 0; a < 10; a = a + 1) {
        if (a == 2) {
            continue;
        } else {
            console.log("当前的变量a:" + a);
        }
    }
    break;
}

// if / else if / else（来自 rules.xml）
if (eventType == "A") {
    score = 40 + (60 - reportMinutes) * 10 / 60;
} else if (eventType == "B") {
    score = 40;
} else {
    score = 0;
}
```

### 调用方式用例

以下用例取自仓库真实测试（[jquick-java.xml](src/test/resources/jquick-java.xml) / [JStaticMethodInvocationTest.java](src/test/java/com/github/paohaijiao/primary2/JStaticMethodInvocationTest.java)）：

```jquick
// 构造函数：new 类名(类型:参数, ...)
java.lang.String str1 = new java.lang.String(java.lang.String:"Hello");

// 实例方法：obj.method(类型:参数, ...)
java.lang.String upperStr = str1.toUpperCase();
java.lang.String subStr   = str1.substring(int:1, int:3);

// 静态方法：类名::方法(类型:参数, ...)
com.github.paohaijiao.service.exract.JService::sum(int:1, int:2);
java.lang.String::join(java.lang.CharSequence:",",
                       java.lang.CharSequence:"1", java.lang.CharSequence:"22");

// 静态变量对象方法：类@静态变量.方法(...)
java.lang.System@out.println(java.lang.String:"hahah");

// 内置 SPI 函数：Builtin::方法(类型:参数, ...)
Builtin::sum(int:1, int:2, int:3, int:4, int:5, int:6);

// 自定义函数调用：this.方法(类型:参数, ...)
this.a(int:c, float:d);
```

### 内置 SPI 函数库（Builtin::）

JQuick Java 的内置 SPI 函数库由独立仓库 **[jquick-transform-function](https://github.com/paohaijiao/jquick-transform-function)** 提供，脚本中以 `Builtin::methodName(args)` 直接调用，无需任何 import。内置函数覆盖：

| 分类 | 函数示例 |
|------|----------|
| 类型判断 | `isArray(value)`、`isBoolean(value)` |
| 位运算 | `bitAnd(a, b)`、`bitOr(a, b)`、`bitXor(a, b)` |
| 信息脱敏 | `bankCardMask(cardNo, keepStart?, keepEnd?)`、`emailMask(email)` |
| 身份证识别 | `idCardAge(idCard, referenceDate?)`、`idCardBirthday(idCard, pattern?)`、`idCardGender(idCard, format?)` |
| 金融校验 | `bankCardValidate(cardNo)`（Luhn 算法） |

> 完整函数清单与用法见 [jquick-transform-function](https://github.com/paohaijiao/jquick-transform-function) 的 README。

---

## 性能与安全

### JQuick-ASM 调用链优化

JQuick Java 的 Java 方法调用采用**反射与 ASM 双实现**：默认调用链在方法解析后，通过 `JQuickJavaAsmInvokerFactory` **在运行时生成字节码调用器**（`JQuickJavaAsmMethodInvoker` / `JQuickJavaAsmConstructorInvoker`），并以 `ConcurrentHashMap` 缓存复用，避免重复生成。

| 设计点 | 实现 |
|--------|------|
| 运行时字节码生成 | ASM 动态生成 `invoke(...)` / `newInstance(...)` 调用器类 |
| 调用器缓存 | `METHOD_CACHE` / `CONSTRUCTOR_CACHE` 并发缓存，一次生成、永久复用 |
| 基本类型零反射开销 | 生成期内置拆箱/装箱指令（`emitUnboxOrCast` / `emitBoxOrNull`） |
| 调用指令按需生成 | 静态 `INVOKESTATIC`、接口 `INVOKEINTERFACE`、普通 `INVOKEVIRTUAL`、构造 `INVOKESPECIAL` |
| 方法匹配优化 | 名称 + 参数类型匹配，支持继承链向上查找与 varargs 自动展开 |

调用链完整流程（[JQuickJavaMethodInvoker](src/main/java/com/github/paohaijiao/support/impl/JQuickJavaMethodInvoker.java)）：

```text
查找方法 → 安全检查（黑白名单） → setAccessible → varargs 展开 → ASM 调用器执行
```

### 安全机制：黑白名单（JQuickJavaInvocationGuard）

每次 Java 调用前都会经过 `JQuickJavaInvocationGuard` 的**类级 + 方法级黑白名单**检查，命中即抛出 `SecurityException`，从调用链源头拦截危险操作。

**内置默认防护**（`JQuickJavaInvocationGuard.DEFAULT`）：

| 类型 | 默认拦截项 |
|------|------------|
| 方法黑名单 | `java.lang.System#exit`、`java.lang.Runtime#exit`、`java.lang.Runtime#halt` |
| 类黑名单 | `java.lang.ProcessBuilder` |

**自定义防护规则**（通过 `JQuickJavaReflectionFactory.setInvocationGuard(...)` 全局配置）：

```java
JQuickJavaReflectionFactory.setInvocationGuard(
        JQuickJavaInvocationGuard.builder()
                .blacklistClasses("java.lang.Runtime")
                .blacklistMethods("java.lang.System#exit")
                .whitelistMethods("java.lang.String#valueOf")
                .build());
```

> 提示：类白名单 / 方法白名单一旦配置，未在白名单内的目标一律拒绝；黑白名单可同时配置，黑名单优先。

### 性能基准

基于 [ReflectionFactoryTest.java](src/test/java/com/github/paohaijiao/extract/factory/ReflectionFactoryTest.java) 同款 API，作者在本机（Oracle JDK 1.8.0_191 / Windows 10）实测 **100 万次静态方法调用**（预热 20 万次后计时）：

| 调用方式 | 100 万次耗时 | 单次平均 |
|----------|--------------|----------|
| 直接反射（预缓存 `Method`，最优基线） | ~6.27 ms | ~6 ns |
| JQuick 动态调用链（反射工厂 + ASM 调用器） | ~222.54 ms | ~223 ns |

解读：

- 差距主要来自**动态派发成本**：JQuick 每次调用都包含方法解析、安全校验（黑白名单）、可变参数规整、类型装箱等完整链路，这部分开销是动态规则引擎的固有成本，与 ASM 调用器本身无关；
- ASM 调用器已消除反射调用链的 `setAccessible` 校验、`InvocationTargetException` 包装等开销，并通过 `METHOD_CACHE` / `CONSTRUCTOR_CACHE` 并发缓存复用，一次生成、永久复用；
- 单次调用在**亚微秒量级**，对评分、校验、规则计算等业务场景完全足够；如需进一步压榨，请复用 `createApi(...)` 代理实例并缓存解析结果，最终以目标环境实测为准。

---

## 业务示例：企业信用评级打分

> 完整可运行代码见测试类 [TEmEventScoringServiceTest.java](src/test/java/com/github/paohaijiao/xml/TEmEventScoringServiceTest.java)。

### 1. 指标体系：企业信用评级体系表_带权重.xlsx

评级体系由 **Excel 配置驱动**：[企业信用评级体系表_带权重.xlsx](src/main/resources/企业信用评级体系表_带权重.xlsx) 定义了全部评级指标、权重与阈值，业务人员可直接在 Excel 中维护，无需改动代码。其指标体系（Markdown 导出）如下：

**评级维度与权重**

| 一级维度 | 满分 | 权重 |
|----------|------|------|
| 基本资质 | 20 | 20% |
| 财务健康 | 30 | 30% |
| 履约信用 | 25 | 25% |
| 经营管理 | 15 | 15% |
| 合规与风控 | 10 | 10% |
| **合计** | **100** | **100%** |

**二级指标与阈值（部分节选）**

| 维度 | 二级指标 | 分值 | 阈值规则 |
|------|----------|------|----------|
| 基本资质 | 注册资本与实缴资本 | 8 | 实缴/注册 ≥100% → 8；≥50% → 5；≥20% → 3；其他 → 0 |
| 基本资质 | 企业成立年限 | 6 | ≥10 年 → 6；≥5 年 → 4；≥3 年 → 2；其他 → 0 |
| 基本资质 | 资质认证 | 6 | 甲级/一级/高新 → 6；乙级/二级 → 3；其他 → 0 |
| 财务健康 | 资产负债率 | 12 | ≤50% → 12；≤70% → 8；≤90% → 4；其他 → 0 |
| 财务健康 | 盈利能力 | 10 | 连续盈利高增长 → 10；连续盈利低增长 → 6；一年盈利 → 3；其他 → 0 |
| 财务健康 | 运营能力（营业周期） | 8 | ≤行业平均 1.0 倍 → 8；≤1.5 倍 → 4；其他 → 0 |
| 履约信用 | 合同履约率 | 10 | ≥100% → 10；≥95% → 6；≥80% → 3；其他 → 0 |
| 履约信用 | 金融信用记录 | 10 | 无逾期 → 10；轻微逾期 → 6；多次逾期 → 3；其他 → 0 |
| 履约信用 | 失信与行政处罚 | 5 | 无 → 5；一般行政处罚 → 2；其他 → 0 |
| 经营管理 | 营收增长（复合增长率） | 8 | ≥15% → 8；≥5% → 5；≥0% → 2；其他 → 0 |
| 经营管理 | 团队与管理稳定性 | 7 | 稳定 → 7；略有变动 → 4；其他 → 0 |
| 合规与风控 | 税务与社保合规 | 5 | 合规 → 5；轻微违章 → 2；其他 → 0 |
| 合规与风控 | 风险管理制度 | 5 | 完善 → 5；一般 → 3；其他 → 0 |

**信用等级映射**

| 加权总分 | 信用等级 | 评级说明 |
|----------|----------|----------|
| ≥90 | AAA | 信用优秀，履约能力极强 |
| ≥80 | AA | 信用良好，履约能力强 |
| ≥70 | A | 信用较好，履约能力较强 |
| ≥60 | BBB | 信用一般，履约能力尚可 |
| ≥50 | BB | 信用较差，履约能力较弱 |
| <50 | B | 信用极差，履约能力极弱 |

### 2. 通过 JQuick 脚本引擎 / XML 规则加载评级体系

将 Excel 中的指标与阈值落地为 XML 规则 [scoring-rules.xml](src/test/resources/scoring-rules.xml)，由脚本引擎在运行时解析加载。片段示例：

```xml
<!-- 1.1 注册资本与实缴资本 -->
<java name="scoreCapital" returnClass="int">
    <![CDATA[
        int def scoreCapital(double:actualCapital, double:registeredCapital) {
            if (registeredCapital == 0){
               return 0;
            }
            double ratio = actualCapital / registeredCapital;
            if (ratio >= 1.0) {
                return 8;
            } else if (ratio >= 0.5) {
                return 5;
            } else if (ratio >= 0.2) {
                return 3;
            } else {
                return 0;
            }
        }
    ]]>
</java>
```

```xml
<!-- 核心：计算加权总分 -->
<java name="calculateTotalScore" returnClass="int">
    <![CDATA[
        double def calculateTotalScore(int:basicScore,int:financialScore,int:performanceScore,int:managementScore,int:complianceScore) {
            // 维度满分: 基本资质20, 财务30, 履约25, 管理15, 合规10
            return basicScore + financialScore + performanceScore + managementScore + complianceScore;
         }
    ]]>
</java>
```

### 3. 定义评分接口

接口 [ScoringMapper.java](src/test/java/com/github/paohaijiao/service/ScoringMapper.java) 与 XML 规则按 `name` 一一对应，参数经 `@Param` 绑定：

```java
public interface ScoringMapper {

    int scoreCapital(@Param("actualCapital") double actualCapital,
                     @Param("registeredCapital") double registeredCapital);

    int scoreEstablishment(@Param("years") int years);

    int scoreDebtRatio(@Param("debtRatio") double debtRatio);          // 资产负债率，如 0.65 表示 65%

    int scoreCreditRecord(@Param("creditStatus") String creditStatus); // 如 "无逾期"

    double calculateTotalScore(@Param("basicScore") int basicScore,
                               @Param("financialScore") int financialScore,
                               @Param("performanceScore") int performanceScore,
                               @Param("managementScore") int managementScore,
                               @Param("complianceScore") int complianceScore);
}
```

### 4. 执行企业信用评级打分

参考 [TEmEventScoringServiceTest.java](src/test/java/com/github/paohaijiao/xml/TEmEventScoringServiceTest.java)，一次加载、五维打分、加权汇总：

```java
@Before
public void setUp() {
    mapper = JQuickJava.create()
            .importPackage("java.lang.String", "str")
            .importPackage("java.util.Date", "JDate")
            .createApi(ScoringMapper.class, "scoring-rules.xml");
}

@Test
public void quickSubmissionReport() {
    // 基本资质：实缴500万/注册500万 + 成立8年 + 高新资质
    int basicScore = mapper.scoreCapital(500, 500)      // 8
                   + mapper.scoreEstablishment(8)       // 4
                   + mapper.scoreCertification("甲级/一级/高新"); // 6
    assert basicScore == 18;

    // 财务健康：负债率55% + 连续盈利高增长 + 营业周期1.2倍
    int financialScore = mapper.scoreDebtRatio(0.55)    // 8
                       + mapper.scoreProfitability("连续盈利高增长") // 10
                       + mapper.scoreOperationCycle(1.2);          // 4
    assert financialScore == 22;

    // 履约信用：履约率98% + 无逾期 + 无处罚
    int performanceScore = mapper.scoreContractPerformance(0.98)   // 6
                         + mapper.scoreCreditRecord("无逾期")      // 10
                         + mapper.scorePenalty("无");              // 5
    assert performanceScore == 21;

    // 经营管理：增长率12% + 团队稳定
    int managementScore = mapper.scoreRevenueGrowth(0.12)  // 5
                        + mapper.scoreTeamStability("稳定"); // 7
    assert managementScore == 12;

    // 合规与风控：税务合规 + 风控完善
    int complianceScore = mapper.scoreTaxCompliance("合规")   // 5
                        + mapper.scoreRiskManagement("完善");  // 5
    assert complianceScore == 10;

    // 加权评级总分
    double totalScore = mapper.calculateTotalScore(
            basicScore, financialScore, performanceScore, managementScore, complianceScore);
    assert totalScore >= 70;               // A 级线
    String creditLevel = mapToCreditLevel(totalScore);
    assert creditLevel.equals("AA");       // 83 分 → AA
}
```

### 5. 结果解读

上述企业样本（资本到位、成立 8 年、高盈利、零逾期、零处罚、团队稳定、税务合规）得分明细：

| 维度 | 得分 | 满分 |
|------|------|------|
| 基本资质 | 18 | 20 |
| 财务健康 | 22 | 30 |
| 履约信用 | 21 | 25 |
| 经营管理 | 12 | 15 |
| 合规与风控 | 10 | 10 |
| **加权总分** | **83** | **100** |

最终评级：**83 分 → AA 级（信用良好，履约能力强）**。

---

## 模块说明

JQuick Java 依托 JQuick 生态，核心模块一览：

| 模块 | 说明 |
|------|------|
| **jquick-java**（本项目） | 核心：脚本引擎 + 统一入口 `JQuickJava` + XML 规则代理（基于 `jquick-xmlProxy`）+ LSP 语言服务 |
| **JQuick-ASM** | 基于 ASM 的高性能调用链：运行时字节码生成（`JQuickJavaAsmInvokerFactory` / `JQuickJavaAsmMethodInvoker` 等）、调用器并发缓存、基本类型自动装箱/拆箱，与反射调用链双实现 |
| **JQuick-Gateway** | 生态内规则接入与网关能力模块 |
| **JQuick-SQL** | 生态内 SQL 增强与数据访问能力模块 |
| **jquick-excel** | 生态内 Excel 配置驱动模块，支撑指标体系等配置化场景 |
| **jquick-pdf** | 生态内 PDF 输出模块，支撑评级报告等输出场景 |
| **jquick-transform-function** | 内置 SPI 函数库（`Builtin::`）：类型判断、位运算、信息脱敏、身份证识别、金融校验等，[GitHub](https://github.com/paohaijiao/jquick-transform-function) |
| **脚本引擎** | ANTLR4 解析器 + Visitor 执行链，运行时解析执行类 Java 脚本 |
| **XML 规则代理** | 接口 + XML 声明式规则，动态代理自动生成，规则即配置 |

---

## 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| org.antlr:antlr4-runtime | 由父 POM 管理 | 语法解析运行时 |
| org.antlr:antlr4 | provided | 语法解析器生成 |
| io.github.paohaijiao:jquick-asm | 1.1.0 | ASM 高性能调用链 |
| io.github.paohaijiao:jquick-xmlProxy | 1.6.0 | XML 规则代理 |
| io.github.paohaijiao:jquick-transform-function | 1.4.0 | 内置 SPI 函数库（[GitHub](https://github.com/paohaijiao/jquick-transform-function)） |
| io.github.paohaijiao:jquick-banner | 1.3.0 | 启动横幅 |
| io.github.paohaijiao:javelin-core | 1.8.8 | 基础设施 |
| junit / lombok / gson | 由父 POM 管理 | 测试与工具 |

---

## 贡献

欢迎通过以下方式参与 JQuick Java：

- 提交 [Issue](https://github.com/paohaijiao/jquick-java/issues) 反馈问题或建议
- 提交 PR 完善语法、执行链、XML 代理与文档
- Star / Fork 本仓库，一起完善 JQuick 生态

---

## 许可证

[Apache License 2.0](LICENSE)

项目地址：<https://github.com/paohaijiao/jquick-java>
