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
- **Invocation guard (blacklist/whitelist)**: built-in invocation guard `JQuickJavaInvocationGuard` with class/method-level blacklist and whitelist, blocking dangerous system calls by default
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

### Minimal Demo: Direct Script Execution

No XML files required — `JQuickJava.create().execute(...)` runs the full pipeline (identical to the official test `JQuickJavaTest#testEntryExecute`):

```java
import com.github.paohaijiao.xml.JQuickJava;

public class QuickStart {
    public static void main(String[] args) {
        Object result = JQuickJava.create()
                .importPackage("java.lang.String", "type1")
                .variable("base", 60)
                .execute(
                        "type1 def a(int:a,int:b) {\n" +
                        "   int t = a + b;\n" +
                        "   type1 p = java.lang.String::valueOf(int:t);\n" +
                        "   return p;\n" +
                        "}\n" +
                        "int c=1;\n" +
                        "int d=2;\n" +
                        "this.a(int:c,int:d);"
                );
        System.out.println("execute result: " + result); // prints 3
    }
}
```

### Minimal Demo: Interface + XML Rule Proxy

The most common production form is "interface + XML": one line of `createApi` generates the rule proxy:

```xml
<!-- src/main/resources/hello-rules.xml -->
<javas namespace="com.example.demo.CalcMapper">
    <java name="sum" returnClass="int">
        <![CDATA[
            int def sum(int:a,int:b) {
                return a + b;
            }
        ]]>
    </java>
</javas>
```

```java
public interface CalcMapper {
    int sum(@Param("a") int a, @Param("b") int b);
}

CalcMapper calc = JQuickJava.create()
        .importPackage("java.lang.String", "str")
        .createApi(CalcMapper.class, "hello-rules.xml");
System.out.println(calc.sum(1, 2)); // prints 3
```

### Run Unit Tests

```bash
mvn test -Dtest=TEmEventScoringServiceTest
```

---

## Core Syntax Reference

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

### Variable Declarations, import, and the Type System

Variable declarations **require an initializer** (bare declarations like `int x;` are not supported):

```jquick
int c = 1;                                   // primitive type
double ratio = actualCapital / registeredCapital;
java.lang.String str = "hello";              // reference type (fully-qualified class name)
List<java.lang.Integer> list = [1, 2, 3];    // generic type + list literal
```

Supported types:

| Type | Example |
|------|---------|
| Primitive | `short`, `int`, `float`, `double`, `long`, `boolean`, `byte` |
| Reference | fully-qualified class name, e.g. `java.lang.String` |
| Generic | `List<T>`, `Set<T>`, `Map<K,V>`, e.g. `List<java.lang.String>` |
| Array | `int[]`, `java.lang.String[]` |
| Class literal | `com.example.Foo.class` |

Before referencing an external type, declare an import alias and use the alias directly in scripts:

```jquick
import java.lang.String as a;
import List<java.lang.String> as c;
import java.util.Date as JDate;
```

### Static Method Invocation in Detail

Static methods follow the unified form `FullyQualifiedClass::methodName(type:arg, ...)`, with parameters prefixed by their `type:` (typedArgument). Real examples of each form (taken from [JStaticMethodInvocationTest.java](src/test/java/com/github/paohaijiao/primary2/JStaticMethodInvocationTest.java)):

| Form | Script example |
|------|----------------|
| No arguments | `java.lang.System::currentTimeMillis();` |
| Multiple args (int) | `java.lang.Math::max(int:5, int:10);` |
| Multiple args (double) | `java.lang.Math::pow(double:2, double:3);` |
| Mixed arguments | `java.lang.String::format(java.lang.String:"Number: %d, String: %s", int:42, java.lang.String:"test");` |
| Varargs | `java.lang.String::join(java.lang.CharSequence:",", java.lang.CharSequence:"a", java.lang.CharSequence:"b");` |
| Generic collection arg | `java.util.Collections::sort(List<java.lang.Integer>:listVar);` |
| null argument | `java.util.Objects::toString(java.lang.String:null);` |
| void return | `java.lang.System::gc();` |
| Custom business class | `com.github.paohaijiao.service.exract.JService::sum(int:1, int:2);` |
| Static-variable object method | `java.lang.System@out.println(java.lang.String:"hahah");` |

Key points:

- Arguments can be either `type:value` (typedArgument, explicit type) or plain expressions; the return value can be assigned or passed on as a further argument;
- Generic types are declared as `List<java.lang.String>` in arguments — the engine performs type matching automatically;
- Common errors on failure are `No matching method found` / `please double check static method invocation`: first verify the fully-qualified class name and the method signature (argument count and types) against the Java side;
- All static calls also go through the `JQuickJavaInvocationGuard` blacklist/whitelist check.

### Debugging with console.log

`console.log(expression);` is the built-in print statement — it evaluates the expression and prints its `toString()` result:

```jquick
console.log("当前的变量a:" + a);   // print with string concatenation
console.log(1);                    // print a literal directly
console.log(score);                // print the current value of a variable
```

Debugging practices:

- **Print inputs**: print each parameter at the entry of a rule function to quickly confirm parameter binding;
- **Print intermediate results**: print intermediate variables in multi-branch / multi-loop logic, combined with `if` / `for` / `while`, to locate the problematic branch;
- **String interpolation `${varName}`**: reads a value from the context variables (`JContext`) and substitutes it — handy for assembling logs and messages:

```jquick
console.log("当前企业类型:${type}");
```

- **Combine with exception types**: `SecurityException` means a blacklist/whitelist hit; `No matching method found` means a method-signature mismatch; `Undefined variable` means a variable was not injected (see FAQ Q6).

---

## Java API Usage

### JQuickJava Unified Entry

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

### Chained Invocation Example

Inline rule registration (identical to the official test `JQuickJavaTest#testEntryInlineCreateApi`):

```java
UserMapper userApi = JQuickJava.create()
        .importPackage("java.lang.String", "type1")
        .constant("base", 60)
        .rule("sum", "int def sum(int:a,int:b){ return a+b; }")
        .rule("mul",
              "type1 def mul(int:a,int:b){ int t=a*b; type1 p = java.lang.String::valueOf(int:t); return p; }")
        .createApi(UserMapper.class);
int sum = userApi.sum(1, 2); // 3
int mul = userApi.mul(3, 4); // 12
```

Merging XML + inline rules (for methods with the same name, the inline one takes precedence; official test `testEntryMergeXmlAndInline`):

```java
UserMapper userApi = JQuickJava.create()
        .rule("mul", "int def mul(int:a,int:b){ return a*b; }")
        .createApi(UserMapper.class, "jquick-java.xml");
int sum = userApi.sum(1, 2); // 3 (from XML)
int mul = userApi.mul(3, 4); // 12 (from inline)
```

### Programmatic Invocation API (JQuickJavaReflectionFactory)

No scripts — call directly from the Java side, sharing the same "security check + ASM invoker" chain as scripts:

```java
// Static method: staticMethod(Class).invoke(name, paramTypes[], args...)
String s = JQuickJavaReflectionFactory.staticMethod(Math.class)
        .invoke("max",
                new JQuickJavaTypeReference<?>[]{JQuickJavaTypeReference.of(int.class),
                        JQuickJavaTypeReference.of(int.class)}, 5, 10); // 10

// Constructor: constructor(Class).newInstance(...)
TestClass t = JQuickJavaReflectionFactory.constructor(TestClass.class)
        .newInstance(JQuickJavaTypeReference.of(String.class), "test");

// Instance method: instanceMethod(obj).invoke(name, ...)
String name = JQuickJavaReflectionFactory.instanceMethod(instance).invoke("getName");
```

### Runtime Environment (JQuickJavaRuntimeEnvironment)

Environment variables inject runtime parameters into rules, with strongly-typed reads:

```java
JQuickJavaRuntimeEnvironment env = JQuickJavaRuntimeEnvironment.create()
        .set("threshold", 100)
        .setAll(Map.of("maxScore", 50));

env.getInt("threshold", 0);        // 100
env.getString("key", "default");   // with default value
env.getBoolean("enabled", false);  // boolean read
env.contains("threshold");         // true
```

### Exception Handling

Main exception types and handling advice:

| Exception | Triggered when | Advice |
|-----------|----------------|--------|
| `JQuickJavaException` | generic errors during script parsing / execution | catch and log the script ID to locate syntax or type issues |
| `JQuickJavaBuiltInExecuteException` | built-in SPI (`Builtin::`) execution errors | check whether the function name and parameter types match |
| `SecurityException` | blacklist/whitelist hit (e.g. `System#exit`) | check the Guard configuration; always reject untrusted calls |
| `IllegalArgumentException` | method matching failed / generic import missing alias | verify type declarations and `importPackage(name, alias)` usage |
| `JQuickJavaBreakException` / `JQuickJavaContinueException` | internal implementation of `break;` / `continue;` | engine-internal mechanism — no need to catch in business code |

> Note: `JQuickJavaMethodInvoker` unwraps reflection exceptions (`unwrapInvocationException`), so business code sees the real exception thrown by the target method rather than an `InvocationTargetException` wrapper.

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

### More Examples

The repository also ships more runnable real-world examples (each includes an interface, XML rules, and tests):

| Example | Rule file | Test class |
|---------|-----------|------------|
| Multi-node time-limit scoring (tiered linear scoring + total aggregation) | [rules.xml](src/test/resources/rules.xml) | [RulesServiceImpl.java](src/test/java/com/github/paohaijiao/xml/RulesServiceImpl.java) |
| Five-dimension credit scoring + level / risk / advice | [credit-score.xml](src/test/resources/credit-score.xml) | [CreditScoreTest.java](src/test/java/com/github/paohaijiao/xml/CreditScoreTest.java) |
| String / collection / static-variable invocation | [jquick-java.xml](src/test/resources/jquick-java.xml) | [JQuickJavaTest.java](src/test/java/com/github/paohaijiao/xml/JQuickJavaTest.java) |
| Enterprise credit rating weighted total | [scoring-rules.xml](src/test/resources/scoring-rules.xml) | [TEmEventScoringServiceTest.java](src/test/java/com/github/paohaijiao/xml/TEmEventScoringServiceTest.java) |

---

## Performance & Production Best Practices

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

### Production Best Practices

Key points for production deployment:

- **Reuse proxy instances**: `createApi(...)` includes XML parsing and proxy generation cost; reuse a singleton proxy when rules are unchanged (lazy init + double-checked locking) to amortize parsing overhead to the first call;
- **Hot rule updates**: calling `createApi(...)` again loads the new rules (built-in idempotent import registration and static-context isolation allow rebuilding proxies repeatedly within the same JVM); pair it with a config center + versioning for gray release and second-level rollback;
- **Warm-up**: warm up critical rule paths after application startup to avoid first-request cold-start parsing costs;
- **Observability**: the engine prints execution time (`execute time:xxxs`) — wire it into log collection in production, and keep a trail of rule versions and dimension scores for audit and reconciliation;
- **Capacity expectations**: per-call latency is sub-microsecond, enough for scoring/validation-type workloads; for throughput-sensitive scenarios, layer rule-result caching and always validate against your target environment.

---

## Security Risks

> ⚠️ **Security warning: JQuick Java is not a full security sandbox. NEVER execute untrusted external scripts.**

Please distinguish between two concepts:

- **For users**: the `JQuickJavaInvocationGuard` provided by JQuick Java is **invocation protection** (class/method-level blacklist/whitelist) that blocks *known* dangerous calls — not a strong isolation sandbox;
- **For production**: a script is essentially code that can call any Java class (reflection, IO, network, file system). **The default configuration only blocks the few blacklisted methods; when no whitelist is configured, all other methods are allowed by default.** Executing a script from an untrusted source is equivalent to executing arbitrary code and can cause serious security incidents.

Production ground rules:

1. **Only execute trusted rules**: rule scripts must be written and reviewed by trusted members of your organization; never load scripts from untrusted external sources (unvalidated user input, public internet downloads, etc.);
2. **Configure strict whitelists**: use `JQuickJavaReflectionFactory.setInvocationGuard(...)` to configure method/class whitelists and shrink the script's callable surface to the minimal business scope;
3. **Isolated deployment**: run the rule engine in a separate process / container / dedicated account with restricted network and file-system permissions, as the last line of defense-in-depth;
4. **Audit and gray release**: rule changes go through a review → gray release → rollback loop, and the script invocation chain is logged for auditing.

---

## FAQ

**Q1: Do I need to restart the service to modify rules?**
No. Rules are carried by XML / inline scripts — calling `createApi(...)` again loads the new rules; the engine has built-in idempotent import registration and static-context isolation, so proxies can be rebuilt repeatedly in the same JVM.

**Q2: What about performance?**
Official benchmarks measure ~223 ns per Java method call (~222 ms per 1M calls, sub-microsecond), which is enough for scoring, validation, and rule computation. Optimization: reuse proxy instances, warm up, and cache rule results on demand.

**Q3: Can scripts call my own business classes?**
Yes. Scripts support three invocation forms — static methods (`ClassName::method`), constructors (`new ClassName`), and instance methods (`obj.method`); business objects can also be injected via `JContext`. Note: all calls are subject to blacklist/whitelist constraints.

**Q4: Which Java versions are supported?**
Java 8+ (compiled target 8), runs on 8 / 11 / 17 and other JVMs; compatible with legacy environments and the domestic (Chinese) ecosystem.

**Q5: What value does it add over hardcoded if-else?**
Rules are separated from code, maintained as configuration, hot-reloadable without restart, and business analysts can adjust thresholds and weights themselves. See "Comparison with Similar Engines".

**Q6: How do I troubleshoot errors?**
Check `console.log` output and the exception type first: `SecurityException` means a blacklist/whitelist hit; `No matching method found` means the method signature doesn't match; `Undefined variable` means a variable was not injected. The repository test classes (`JQuickJavaTest`, `TEmEventScoringServiceTest`, etc.) provide complete correct usage for reference.

**Q7: Is there IDE support?**
Yes. A built-in LSP language server `JQuickLanguageServer` (stdio mode) provides completion, hover, go-to-definition, and document symbols; the `maven-shade` fat jar can be started with `java -jar` and connected to editors.

---

## Comparison with Similar Engines

| Dimension | JQuick Java | Drools |
|-----------|-------------|--------|
| Positioning | Lightweight Java-like rule scripting language | Heavyweight rule engine (Rete/Phreak inference) |
| Rule language | Java-like syntax (XML / inline scripts) | Proprietary DRL language + DSL |
| Execution model | ANTLR parsing + Visitor execution chain | Rete network pattern matching + agenda inference |
| Java method invocation | ASM bytecode invokers + reflection dual implementation | MVEL / reflection invocation |
| Runtime dependencies | a few JQuick ecosystem jars | drools-core / kie-api, etc. |
| Environment | Java 8+, pure Java, embeddable | Java 8+, usually accompanied by the KIE ecosystem |
| Typical scenarios | scoring, validation, masking, orchestration | complex related rules, large-scale fact inference, decision tables |

**Suggestion**: for deterministic rule scenarios like scoring computation, dynamic validation, data masking, and orchestration, JQuick Java is lighter, faster, and cheaper to adopt; for complex cross-fact inference, backward chaining, and decision tables, Drools fits better.

---

## Ecosystem

the JQuick Java ecosystem consists of official repositories, core modules, and built-in examples:

### Official Repositories

| Repository | Description |
|------------|-------------|
| [paohaijiao/jquick-java](https://github.com/paohaijiao/jquick-java) | Core engine: scripting engine + unified entry `JQuickJava` + XML rule proxy + LSP language server (this project) |
| [paohaijiao/jquick-asm](https://github.com/paohaijiao/jquick-asm) | ASM high-performance invocation chain: runtime bytecode generation and invoker caching |
| [paohaijiao/jquick-xmlProxy](https://github.com/paohaijiao/jquick-xmlProxy) | XML rule proxy: interface + XML declarative rules |
| [paohaijiao/jquick-json](https://github.com/paohaijiao/jquick-json) | Lightweight JSON processing library: parse / generate / manipulate JSON, POJO ↔ JSON conversion |
| [paohaijiao/jquick-path](https://github.com/paohaijiao/jquick-path) | JSONPath query language: path expressions to extract data from JSON documents |
| [paohaijiao/jquick-curl](https://github.com/paohaijiao/jquick-curl) | Curl-based Java HTTP client framework: cURL commands directly turned into HTTP requests |
| [paohaijiao/jquick-sql](https://github.com/paohaijiao/jquick-sql) | SQL enhancement and data access module |
| [paohaijiao/jquick-excel](https://github.com/paohaijiao/jquick-excel) | Excel-driven configuration module, powering configurable indicator systems |
| [paohaijiao/jquick-pdf](https://github.com/paohaijiao/jquick-pdf) | PDF output module, powering rating reports |
| [paohaijiao/jquick-transform-function](https://github.com/paohaijiao/jquick-transform-function) | Built-in SPI function library (`Builtin::`): type checks, bitwise operations, data masking, ID card recognition, financial validation |
| [paohaijiao/jquick-connector](https://github.com/paohaijiao/jquick-connector) | Ecosystem connector module |
| [paohaijiao/jquick-mybatis](https://github.com/paohaijiao/jquick-mybatis) | MyBatis integration module |
| [paohaijiao/jquick-idea-plugin](https://github.com/paohaijiao/jquick-idea-plugin) | IDEA plugin: in-IDE rule script editing support |
| [paohaijiao/javelin](https://github.com/paohaijiao/javelin) | Ecosystem parent POM and infrastructure |

### Core Modules (Maven Central)

| Module | Description |
|--------|-------------|
| [jquick-java](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-java) | Core engine (this project) |
| [jquick-asm](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-asm) | ASM high-performance invocation chain |
| [jquick-xmlProxy](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-xmlProxy) | XML rule proxy |
| [jquick-json](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-json) | Lightweight JSON processing library |
| [jquick-path](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-path) | JSONPath query language |
| [jquick-curl](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-curl) | Curl-based HTTP client |
| [jquick-sql](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-sql) | SQL enhancement and data access |
| [jquick-excel](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-excel) | Excel-driven configuration |
| [jquick-pdf](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-pdf) | PDF output |
| [jquick-transform-function](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-transform-function) | Built-in SPI function library |
| [jquick-banner](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-banner) | Startup banner |
| [javelin-core](https://central.sonatype.com/artifact/io.github.paohaijiao/javelin-core) | Infrastructure |

### Built-in Integration Examples

| Example | Entry |
|---------|-------|
| Enterprise credit rating scoring (Excel-driven config + XML rules) | [TEmEventScoringServiceTest.java](src/test/java/com/github/paohaijiao/xml/TEmEventScoringServiceTest.java) |
| Multi-node time-limit scoring (tiered linear scoring + total aggregation) | [rules.xml](src/test/resources/rules.xml) |
| Five-dimension credit scoring + level / risk / advice | [credit-score.xml](src/test/resources/credit-score.xml) |
| String / collection / static-variable invocation examples | [jquick-java.xml](src/test/resources/jquick-java.xml) |

> PRs are welcome to add JQuick Java-based landing cases / secondary-packaged projects to this list to grow the JQuick ecosystem together.

---

## Modules

JQuick Java is built on the JQuick ecosystem. Core modules:

| Module | Description |
|--------|-------------|
| **jquick-java** (this repo) | Core: scripting engine + unified entry `JQuickJava` + XML rule proxy (based on `jquick-xmlProxy`) + LSP language server |
| **jquick-asm** | ASM-based high-performance invocation chain: runtime bytecode generation (`JQuickJavaAsmInvokerFactory` / `JQuickJavaAsmMethodInvoker`, etc.), concurrent invoker caches, automatic primitive boxing/unboxing — dual implementation with the reflection chain |
| **jquick-gateway** | Ecosystem module for rule access and gateway capabilities |
| **jquick-sql** | Ecosystem module for SQL enhancement and data access |
| **jquick-json** | Lightweight JSON processing library in the ecosystem: parse / generate / manipulate JSON, POJO ↔ JSON conversion, variable merging — based on jquick-asm bytecode bean accessors, [GitHub](https://github.com/paohaijiao/jquick-json) |
| **jquick-path** | JSONPath query language in the ecosystem: path expressions to extract data from JSON documents (like XPath for JSON), [GitHub](https://github.com/paohaijiao/jquick-path) |
| **jquick-curl** | Curl-based Java HTTP client framework in the ecosystem: cURL commands directly turned into executable HTTP requests, with annotation / XML configuration and dynamic proxies, [GitHub](https://github.com/paohaijiao/jquick-curl) |
| **jquick-excel** | Ecosystem module for Excel-driven configuration, powering configurable indicator systems |
| **jquick-pdf** | Ecosystem module for PDF output, powering rating reports |
| **jquick-banner** | Ecosystem module for the startup banner |
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
