# JQuick Java

[简体中文](./README.md) | ENGLISH

[![Stars](https://img.shields.io/github/stars/paohaijiao/jquick-java.svg?style=social)](https://github.com/paohaijiao/jquick-java)
[![Forks](https://img.shields.io/github/forks/paohaijiao/jquick-java.svg?style=social)](https://github.com/paohaijiao/jquick-java/forks)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.paohaijiao/jquick-java)](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-java)
[![Awesome Java](https://img.shields.io/badge/Awesome-Java-ff69b4.svg)](https://github.com/akullpp/awesome-java)

> Included in the **Miscellaneous** section of [Awesome Java](https://github.com/akullpp/awesome-java)

---

## Project Overview

**JQuick Java is a lightweight Java-like scripting language designed natively for rule engines, driven by declarative configuration.**

It combines Java's strong type safety with JavaScript's dynamic flexibility and supports **loading, parsing, and executing business rules at runtime**. Business rules are declared as XML or inline scripts, fully decoupled from business code — adjusting indicators, weights, and thresholds is as simple as editing configuration. No restart, no redeployment. A true **low-code rule engine**.

JQuick Java is designed for enterprise legacy environments: compatible with **Java 8+**, adapted to the **domestic (Chinese) database ecosystem**, and can be smoothly embedded into existing systems.

Typical scenarios:

- Enterprise credit rating / risk decisioning
- Complex business rules and data validation
- Process orchestration and scoring
- Declarative low-code platforms

---

## Core Features

- **Java-like scripting engine**: parsed and executed at runtime, strong typing plus dynamic flexibility
- **XML rule proxy**: declare business rules with an interface + XML, dynamic proxy generated automatically
- **Inline rules**: register rules directly in Java code, fully equivalent to XML
- **Seamless Java interoperability**: static methods, constructors, instance methods, `this` custom functions, and built-in SPI (`Builtin::`)
- **ASM high-performance invocation chain**: dual implementation of reflection and ASM
- **Declarative configuration**: indicators, weights, and thresholds maintained via Excel / XML without code changes
- **Lightweight and compatible**: Java 8+, works with legacy Java environments and domestic databases
- **Security sandbox (blacklist/whitelist)**: built-in invocation guard `JQuickJavaInvocationGuard` with class/method-level blacklist and whitelist, blocking dangerous system calls by default
- **Language Server (LSP)**: built-in `JQuickLanguageServer` for IDE symbol hints and completion

---

## Quick Start

### Maven Dependency

```xml
<dependency>
    <groupId>io.github.paohaijiao</groupId>
    <artifactId>jquick-java</artifactId>
    <version>2.5.0</version>
</dependency>
```

### First JQuick Script

```java
import List<java.lang.String> as StringList ;
StringList list=["A","B","C"];
```

### Run Unit Tests

```bash
mvn test -Dtest=TEmEventScoringServiceTest
```

---

## Core Concepts

### Rules Separated from Code

Rule scripts (function definitions) are declared as XML `<java>` elements or inline strings, and exposed to business code through an interface dynamic proxy. Business code only depends on the interface; rule content can be adjusted at any time.

### Scripting Engine

with a layered Visitor execution chain handling literals, expressions, control structures, method invocation, scoping, and function registration.

### XML Rule Proxy

`src/test/resources/scoring-rules.xml` is a typical example:

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

### JQuickJava Unified Entry Class

`JQuickJava` is an all-in-one entry point with a fluent API covering package declarations, variable initialization, direct script execution, and XML / inline rule proxies.

| API | Description |
|-----|-------------|
| `JQuickJava.create()` | Create a default entry instance |
| `importPackage(qualifiedName, alias)` | Declare a package import, generating `import Type as Alias;` |
| `constant(name, value)` / `variable(name, value)` / `env(name, value)` | Initialize constants, context variables, runtime environment variables |
| `init(statement...)` | Append script-level initialization statements |
| `rule(methodName, functionDefinition)` | Register an inline rule script (equivalent to the CDATA of an XML `<java>` element) |
| `execute(scriptBody)` | Execute a JQuick script directly |
| `createApi(apiInterface)` | Create a pure inline-rule proxy |
| `createApi(apiInterface, xmlPath)` | Create a proxy merging XML rules and inline rules |

### Method Invocation Forms

| Form | Syntax | Description |
|------|--------|-------------|
| Static method | `ClassName::methodName(type:arg, ...)` | Java static method |
| Constructor | `new ClassName(type:arg, ...)` | Java constructor |
| Instance method | `obj.methodName(type:arg, ...)` | Java object method |
| Custom function | `this.methodName(type:arg, ...)` | JQuick script function |
| Built-in SPI | `Builtin::methodName(type:arg, ...)` | Platform-level built-in capability |
| Accessed static variable | `classsType@objectName.methodName(...)` | Method on a static-variable object |

### Control Structures

JQuick scripts have built-in, complete flow-control syntax that matches Java:

| Structure | Syntax | Description |
|-----------|--------|-------------|
| Conditional branching | `if (expr) { ... } else if (expr) { ... } else { ... }` | any number of `else if` branches |
| Counter loop | `for (int i = 0; i < 10; i = i + 1) { ... }` | init / condition / step |
| Conditional loop | `while (expr) { ... }` | loop while the condition holds |
| Loop control | `break;` / `continue;` | exit / skip the current loop |
| Function return | `return expr;` | return a value |
| Standard output | `console.log(expr);` | print output, supports string concatenation |

```jquick
// for + if + break (from JForTest)
for (int i = 0; i < 10; i = i + 1) {
    if (i == 2) {
        break;
    } else {
        console.log(i);
    }
}

// while + for + continue (from JWhileStatementTest)
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

// if / else if / else (from rules.xml)
if (eventType == "A") {
    score = 40 + (60 - reportMinutes) * 10 / 60;
} else if (eventType == "B") {
    score = 40;
} else {
    score = 0;
}
```

### Invocation Examples

The following examples are taken from real repository tests ([jquick-java.xml](src/test/resources/jquick-java.xml) / [JStaticMethodInvocationTest.java](src/test/java/com/github/paohaijiao/primary2/JStaticMethodInvocationTest.java)):

```jquick
// Constructor: new ClassName(type:arg, ...)
java.lang.String str1 = new java.lang.String(java.lang.String:"Hello");

// Instance method: obj.method(type:arg, ...)
java.lang.String upperStr = str1.toUpperCase();
java.lang.String subStr   = str1.substring(int:1, int:3);

// Static method: ClassName::method(type:arg, ...)
com.github.paohaijiao.service.exract.JService::sum(int:1, int:2);
java.lang.String::join(java.lang.CharSequence:",",
                       java.lang.CharSequence:"1", java.lang.CharSequence:"22");

// Static-variable object method: Class@staticVar.method(...)
java.lang.System@out.println(java.lang.String:"hahah");

// Built-in SPI function: Builtin::method(type:arg, ...)
Builtin::sum(int:1, int:2, int:3, int:4, int:5, int:6);

// Custom function call: this.method(type:arg, ...)
this.a(int:c, float:d);
```

### Built-in SPI Function Library (Builtin::)

The built-in SPI function library is provided by the standalone repository **[jquick-transform-function](https://github.com/paohaijiao/jquick-transform-function)** and is called directly in scripts as `Builtin::methodName(args)` — no imports required. Built-in functions cover:

| Category | Function Examples |
|----------|-------------------|
| Type checks | `isArray(value)`, `isBoolean(value)` |
| Bitwise operations | `bitAnd(a, b)`, `bitOr(a, b)`, `bitXor(a, b)` |
| Data masking | `bankCardMask(cardNo, keepStart?, keepEnd?)`, `emailMask(email)` |
| ID card recognition | `idCardAge(idCard, referenceDate?)`, `idCardBirthday(idCard, pattern?)`, `idCardGender(idCard, format?)` |
| Financial validation | `bankCardValidate(cardNo)` (Luhn algorithm) |

> For the complete function list and usage, see the [jquick-transform-function](https://github.com/paohaijiao/jquick-transform-function) README.

---

## Performance & Security

### JQuick-ASM Invocation Chain Optimization

Java method invocation in JQuick Java uses a **dual reflection / ASM implementation**: after method resolution, the default invocation chain **generates bytecode invokers at runtime** via `JQuickJavaAsmInvokerFactory` (`JQuickJavaAsmMethodInvoker` / `JQuickJavaAsmConstructorInvoker`), cached and reused in `ConcurrentHashMap`s to avoid regeneration.

| Design Point | Implementation |
|--------------|----------------|
| Runtime bytecode generation | ASM dynamically generates `invoke(...)` / `newInstance(...)` invoker classes |
| Invoker caching | `METHOD_CACHE` / `CONSTRUCTOR_CACHE` concurrent caches — generate once, reuse forever |
| Zero-reflection primitive handling | unboxing/boxing instructions emitted at generation time (`emitUnboxOrCast` / `emitBoxOrNull`) |
| Invocation opcode per call type | static `INVOKESTATIC`, interface `INVOKEINTERFACE`, virtual `INVOKEVIRTUAL`, constructor `INVOKESPECIAL` |
| Method matching optimization | name + parameter-type matching with superclass lookup and automatic varargs expansion |

Complete invocation flow ([JQuickJavaMethodInvoker](src/main/java/com/github/paohaijiao/support/impl/JQuickJavaMethodInvoker.java)):

```text
find method → security check (blacklist/whitelist) → setAccessible → varargs expansion → ASM invoker
```

### Security: Blacklist / Whitelist (JQuickJavaInvocationGuard)

Every Java invocation passes through the **class-level + method-level blacklist/whitelist** check of `JQuickJavaInvocationGuard`; a hit throws `SecurityException`, blocking dangerous operations at the source of the invocation chain.

**Built-in default protection** (`JQuickJavaInvocationGuard.DEFAULT`):

| Type | Default Blocked Items |
|------|-----------------------|
| Method blacklist | `java.lang.System#exit`, `java.lang.Runtime#exit`, `java.lang.Runtime#halt` |
| Class blacklist | `java.lang.ProcessBuilder` |

**Custom protection rules** (configure globally via `JQuickJavaReflectionFactory.setInvocationGuard(...)`):

```java
JQuickJavaReflectionFactory.setInvocationGuard(
        JQuickJavaInvocationGuard.builder()
                .blacklistClasses("java.lang.Runtime")
                .blacklistMethods("java.lang.System#exit")
                .whitelistMethods("java.lang.String#valueOf")
                .build());
```

> Note: once a class/method whitelist is configured, any target not in the whitelist is rejected; blacklist and whitelist can be combined, and the blacklist takes precedence.

### Benchmark

Based on the same APIs as [ReflectionFactoryTest.java](src/test/java/com/github/paohaijiao/extract/factory/ReflectionFactoryTest.java), measured locally by the author (Oracle JDK 1.8.0_191 / Windows 10) over **1,000,000 static method invocations** (after 200,000 warm-up iterations):

| Invocation style | 1M calls | Per call |
|------------------|----------|----------|
| Direct reflection (cached `Method`, best baseline) | ~6.27 ms | ~6 ns |
| JQuick dynamic chain (reflection factory + ASM invoker) | ~222.54 ms | ~223 ns |

Interpretation:

- The gap mainly comes from **dynamic dispatch cost**: every JQuick call includes method resolution, security checks (blacklist/whitelist), varargs normalization, and type boxing — inherent overhead of a dynamic rule engine, independent of the ASM invoker itself;
- The ASM invoker eliminates the `setAccessible` check and `InvocationTargetException` wrapping of the reflection chain, and is reused via the concurrent caches `METHOD_CACHE` / `CONSTRUCTOR_CACHE` — generate once, reuse forever;
- Each call is on the order of **sub-microseconds**, which is more than enough for scoring, validation, and rule computation. To push further, reuse the `createApi(...)` proxy instance and cache parse results; always validate against your target environment.

---

## Business Example: Enterprise Credit Rating Scoring

> The complete runnable code is in the test class [TEmEventScoringServiceTest.java](src/test/java/com/github/paohaijiao/xml/TEmEventScoringServiceTest.java).

### 1. Indicator System: 企业信用评级体系表_带权重.xlsx

The rating system is **driven by an Excel configuration**: [企业信用评级体系表_带权重.xlsx](src/main/resources/企业信用评级体系表_带权重.xlsx) defines all rating indicators, weights, and thresholds. Business analysts can maintain it directly in Excel without touching code. The indicator system (Markdown export) is shown below:

**Rating Dimensions and Weights**

| Dimension | Max Score | Weight |
|-----------|-----------|--------|
| Basic Qualification | 20 | 20% |
| Financial Health | 30 | 30% |
| Performance & Credit | 25 | 25% |
| Management | 15 | 15% |
| Compliance & Risk Control | 10 | 10% |
| **Total** | **100** | **100%** |

**Secondary Indicators and Thresholds (excerpt)**

| Dimension | Indicator | Score | Threshold Rule |
|-----------|-----------|-------|----------------|
| Basic Qualification | Registered vs. paid-in capital | 8 | paid-in/registered ≥100% → 8; ≥50% → 5; ≥20% → 3; else → 0 |
| Basic Qualification | Years in business | 6 | ≥10 yrs → 6; ≥5 yrs → 4; ≥3 yrs → 2; else → 0 |
| Basic Qualification | Certification level | 6 | "甲级/一级/高新" → 6; "乙级/二级" → 3; else → 0 |
| Financial Health | Debt ratio | 12 | ≤50% → 12; ≤70% → 8; ≤90% → 4; else → 0 |
| Financial Health | Profitability | 10 | sustained high growth → 10; sustained low growth → 6; one-year profit → 3; else → 0 |
| Financial Health | Operating cycle | 8 | ≤1.0× industry avg → 8; ≤1.5× → 4; else → 0 |
| Performance & Credit | Contract performance rate | 10 | ≥100% → 10; ≥95% → 6; ≥80% → 3; else → 0 |
| Performance & Credit | Financial credit record | 10 | no overdue → 10; minor overdue → 6; repeated overdue → 3; else → 0 |
| Performance & Credit | Penalties / dishonesty | 5 | none → 5; general admin penalty → 2; else → 0 |
| Management | Revenue growth (CAGR) | 8 | ≥15% → 8; ≥5% → 5; ≥0% → 2; else → 0 |
| Management | Team & management stability | 7 | stable → 7; slightly changed → 4; else → 0 |
| Compliance & Risk | Tax & social insurance compliance | 5 | compliant → 5; minor violation → 2; else → 0 |
| Compliance & Risk | Risk management system | 5 | mature → 5; average → 3; else → 0 |

**Credit Level Mapping**

| Weighted Total Score | Credit Level | Description |
|----------------------|--------------|-------------|
| ≥90 | AAA | Excellent credit, extremely strong performance ability |
| ≥80 | AA | Good credit, strong performance ability |
| ≥70 | A | Fairly good credit, relatively strong performance ability |
| ≥60 | BBB | Average credit, acceptable performance ability |
| ≥50 | BB | Poor credit, weak performance ability |
| <50 | B | Very poor credit, extremely weak performance ability |

### 2. Loading the Rating System via the JQuick Script Engine / XML Rules

Indicators and thresholds from Excel are implemented as XML rules in [scoring-rules.xml](src/test/resources/scoring-rules.xml), parsed and loaded by the script engine at runtime. Excerpts:

```xml
<!-- 1.1 Registered vs. paid-in capital -->
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
<!-- Core: weighted total score -->
<java name="calculateTotalScore" returnClass="int">
    <![CDATA[
        double def calculateTotalScore(int:basicScore,int:financialScore,int:performanceScore,int:managementScore,int:complianceScore) {
            // Dimension max scores: basic 20, financial 30, performance 25, management 15, compliance 10
            return basicScore + financialScore + performanceScore + managementScore + complianceScore;
         }
    ]]>
</java>
```

### 3. Defining the Scoring Interface

The interface [ScoringMapper.java](src/test/java/com/github/paohaijiao/service/ScoringMapper.java) maps one-to-one with the XML rules by `name`; parameters are bound via `@Param`:

```java
public interface ScoringMapper {

    int scoreCapital(@Param("actualCapital") double actualCapital,
                     @Param("registeredCapital") double registeredCapital);

    int scoreEstablishment(@Param("years") int years);

    int scoreDebtRatio(@Param("debtRatio") double debtRatio);          // e.g. 0.65 means 65%

    int scoreCreditRecord(@Param("creditStatus") String creditStatus); // e.g. "无逾期"

    double calculateTotalScore(@Param("basicScore") int basicScore,
                               @Param("financialScore") int financialScore,
                               @Param("performanceScore") int performanceScore,
                               @Param("managementScore") int managementScore,
                               @Param("complianceScore") int complianceScore);
}
```

### 4. Running the Enterprise Credit Rating Scoring

Following [TEmEventScoringServiceTest.java](src/test/java/com/github/paohaijiao/xml/TEmEventScoringServiceTest.java): load once, score across five dimensions, and aggregate:

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
    // Basic qualification: paid-in 5M/registered 5M + 8 years + hi-tech certification
    int basicScore = mapper.scoreCapital(500, 500)            // 8
                   + mapper.scoreEstablishment(8)             // 4
                   + mapper.scoreCertification("甲级/一级/高新"); // 6
    assert basicScore == 18;

    // Financial health: debt ratio 55% + sustained high growth + operating cycle 1.2x
    int financialScore = mapper.scoreDebtRatio(0.55)                // 8
                       + mapper.scoreProfitability("连续盈利高增长")   // 10
                       + mapper.scoreOperationCycle(1.2);           // 4
    assert financialScore == 22;

    // Performance: contract rate 98% + no overdue + no penalties
    int performanceScore = mapper.scoreContractPerformance(0.98)  // 6
                         + mapper.scoreCreditRecord("无逾期")      // 10
                         + mapper.scorePenalty("无");             // 5
    assert performanceScore == 21;

    // Management: growth 12% + stable team
    int managementScore = mapper.scoreRevenueGrowth(0.12)   // 5
                        + mapper.scoreTeamStability("稳定");  // 7
    assert managementScore == 12;

    // Compliance & risk: tax compliant + mature risk management
    int complianceScore = mapper.scoreTaxCompliance("合规")   // 5
                        + mapper.scoreRiskManagement("完善");  // 5
    assert complianceScore == 10;

    // Weighted rating total
    double totalScore = mapper.calculateTotalScore(
            basicScore, financialScore, performanceScore, managementScore, complianceScore);
    assert totalScore >= 70;               // A-level threshold
    String creditLevel = mapToCreditLevel(totalScore);
    assert creditLevel.equals("AA");       // 83 points → AA
}
```

### 5. Interpreting the Result

For the sample enterprise (fully paid-in capital, 8 years in business, high profitability, zero overdue, zero penalties, stable team, tax compliant):

| Dimension | Score | Max |
|-----------|-------|-----|
| Basic Qualification | 18 | 20 |
| Financial Health | 22 | 30 |
| Performance & Credit | 21 | 25 |
| Management | 12 | 15 |
| Compliance & Risk Control | 10 | 10 |
| **Weighted Total** | **83** | **100** |

Final rating: **83 points → AA (good credit, strong performance ability)**.

---

## Modules

JQuick Java is built on the JQuick ecosystem. Core modules:

| Module | Description |
|--------|-------------|
| **jquick-java** (this repo) | Core: scripting engine + unified entry `JQuickJava` + XML rule proxy (based on `jquick-xmlProxy`) + LSP language server |
| **JQuick-ASM** | ASM-based high-performance invocation chain: runtime bytecode generation (`JQuickJavaAsmInvokerFactory` / `JQuickJavaAsmMethodInvoker`, etc.), concurrent invoker caches, automatic primitive boxing/unboxing — dual implementation with the reflection chain |
| **JQuick-Gateway** | Ecosystem module for rule access and gateway capabilities |
| **JQuick-SQL** | Ecosystem module for SQL enhancement and data access |
| **jquick-excel** | Ecosystem module for Excel-driven configuration, powering configurable indicator systems |
| **jquick-pdf** | Ecosystem module for PDF output, powering rating reports |
| **jquick-transform-function** | Built-in SPI function library (`Builtin::`): type checks, bitwise operations, data masking, ID card recognition, financial validation, etc. — [GitHub](https://github.com/paohaijiao/jquick-transform-function) |
| **Scripting Engine** | ANTLR4 parser + Visitor execution chain, parsing and executing Java-like scripts at runtime |
| **XML Rule Proxy** | Interface + XML declarative rules with automatic dynamic proxy generation — rules as configuration |

---

## Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| org.antlr:antlr4-runtime | managed by parent POM | grammar parsing runtime |
| org.antlr:antlr4 | provided | parser generation |
| io.github.paohaijiao:jquick-asm | 1.1.0 | ASM high-performance invocation chain |
| io.github.paohaijiao:jquick-xmlProxy | 1.6.0 | XML rule proxy |
| io.github.paohaijiao:jquick-transform-function | 1.4.0 | built-in SPI function library ([GitHub](https://github.com/paohaijiao/jquick-transform-function)) |
| io.github.paohaijiao:jquick-banner | 1.3.0 | startup banner |
| io.github.paohaijiao:javelin-core | 1.8.8 | infrastructure |
| junit / lombok / gson | managed by parent POM | testing and utilities |

---

## Contributing

You are welcome to contribute to JQuick Java:

- Submit [Issues](https://github.com/paohaijiao/jquick-java/issues) to report problems or suggestions
- Submit PRs to improve the grammar, execution chain, XML proxy, and documentation
- Star / Fork this repository to grow the JQuick ecosystem together

---

## License

[Apache License 2.0](LICENSE)

Project URL: <https://github.com/paohaijiao/jquick-java>
