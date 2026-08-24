# JQuick Java Syntax Reference

[中文](./README.md) | ENGLISH

[![Stars](https://img.shields.io/github/stars/paohaijiao/jquick-java.svg?style=social)](https://github.com/paohaijiao/jquick-java)
[![Forks](https://img.shields.io/github/forks/paohaijiao/jquick-java.svg?style=social)](https://github.com/paohaijiao/jquick-java/forks)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.paohaijiao/jquick-java)](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-java)
[![Awesome Java](https://img.shields.io/badge/Awesome-Java-ff69b4.svg)](https://github.com/akullpp/awesome-java)

> Included in the **Miscellaneous** section of [Awesome Java](https://github.com/akullpp/awesome-java)

---

## Project Overview

**JQuick Java is a lightweight Java-like scripting language designed natively for rule engines.**

It combines Java's strong type safety with JavaScript's dynamic flexibility and supports **loading, parsing, and executing business rules at runtime**. Whether you are handling complex decision logic, data validation, or process orchestration, JQuick Java lets you adjust business rules like configuration parameters, without restarting or redeploying the application. This enables true **separation of rules and code**.

Typical scenarios:

- Complex logical decisions
- Data validation
- Process orchestration
- Scorecards / decision engines

---

## Core Features

- Lightweight Java-like scripting syntax
- Dynamic rule loading at runtime
- XML-based business logic configuration
- Seamless Java interoperability with direct Java method calls
- Support for static methods, constructors, instance methods, custom functions, and built-in SPI methods
- Support for generics, collections, and multidimensional arrays

---

## Table of Contents

- [Project Overview](#project-overview)
- [Core Features](#core-features)
- [Quick Start](#quick-start)
- [Syntax Guide](#syntax-guide)
  - [Data Types](#data-types)
  - [Imports](#imports)
  - [Variable Declarations](#variable-declarations)
  - [Expressions and Operators](#expressions-and-operators)
  - [Control Structures](#control-structures)
  - [Method Definitions](#method-definitions)
  - [Java Method Calls and Built-in Calls](#java-method-calls-and-built-in-calls)
  - [Comments](#comments)
  - [Reserved Keywords](#reserved-keywords)
  - [Identifier Rules](#identifier-rules)
- [JQuickJava Unified Entry Class](#jquickjava-unified-entry-class)
- [Core Visitor Notes](#core-visitor-notes)
- [Complete Examples](#complete-examples)
- [XML Configuration Scenario](#xml-configuration-scenario)
- [Open Source](#open-source)
- [Star / Fork Support](#star--fork-support)

---

## Quick Start

### Maven Dependency

```xml
<dependency>
    <groupId>io.github.paohaijiao</groupId>
    <artifactId>jquick-java</artifactId>
    <version>1.4.0</version>
</dependency>
```

### First JQuick Example

```java
import List<java.lang.String> as StringList ;
StringList list=["A","B","C"];
```

---

## Syntax Guide

### Data Types

#### Primitive Types

| Type Keyword | Data Type | Example |
|--------------|-----------|---------|
| short | Short integer | `short s = 100;` |
| int | Integer | `int x = 42;` |
| float | Floating point | `float pi = 3.14;` |
| double | Double | `double d = 9.99;` |
| long | Long integer | `long big = 100;` |
| boolean | Boolean | `boolean flag = true;` |
| byte | Byte | `byte b = 0x1F;` |
| Null | null | `null` |
| Date | Date | `2025-06-07` |
| Date | Date | `2025-06-07 12:00:01` |

#### Composite Types

| Type Format | Example |
|-------------|---------|
| Generic (Type<T>) | `List<String> names;` |
| Generic Multi (Type<K,V>) | `Map<String, Integer> scores;` |
| List | `List<Double> prices;` |
| Set | `Set<Employee> staff;` |
| Array | `int[] numbers = {1,2,3};` |
| Custom Class | `MyClass obj = new MyClass();` |
| Import Alias | `import java.util.Date as JDate;` |

```java
// Primitive types
int counter = 0;
boolean enabled = true;

// Generic collections
List<String> names = ["Alice", "Bob"];
Map<String, Integer> scores = {"Math":90, "English":85};
```

### Imports

```java
import com.example.pkg as pkgAlias;
import java.lang.String as type1;
import java.util.Date as JDate;
import List<java.lang.String> as StringList ;
```

#### Import Declaration

| Component | Description | Example |
|-----------|-------------|---------|
| qualified.name | Dot-separated path | `java.lang.String` |
| as | Alias | `import java.util.Date as JDate;` |

### Variable Declarations

| Type | Example | Description |
|------|---------|-------------|
| Primitive type | `int x = 10;` | Type inference supported |
| Reference type | `String s = "hi";` | Explicit declaration supported |

```java
int x = 10;
boolean flag = true;
java.lang.String name = "JQuick";
```

### Expressions and Operators

| Operator Group | Operators | Example | Desc |
|----------------|-----------|---------|------|
| Mul | `*` | 1*1 | number * number |
| Div | `/` | 1/1 | number / number |
| Add | `+` | 1+1 | number + number string+string |
| Sub | `-` | 1-1 | number - number |
| GT | `>` | 1>1 | number > number |
| GE | `>=` | 1>=1 | number >= number |
| LT | `<` | 1<1 | number < number |
| LE | `<=` | 1<=1 | number <= number |
| NE | `!=` | 1 !=1 | number != number |
| EQ | `==` | 1==1 | number == number |
| AND | `&&` | true&&true | boolean && boolean |
| OR | `||` | true \|\| false | boolean \|\| boolean |
| PAREN | (expression) | (a + b) * 2 > 10 && x != y | (expression) |

#### Complex Expression

```java
(a + b) * 2 > 10 && x != y
```

### Control Structures

#### if Statement

```java
if(false){
    console.log(1);
}else if(true){
    console.log(2);
}else if(false){
    console.log(3);
}else{
    console.log(4);
}
```

#### for Loop

```java
for (int i = 0; i < 10; i = i + 1) {
    for (int j = 0; j < 10; j = j + 1){
        if(j==2){
            continue;
        }else{
            console.log(i+","+j);
        }
    }
};
```

#### while Loop

```java
while(true){
    for (int a = 0; a<10; a=a+1){
        if(a==2){
            continue;
        }else{
            console.log("当前的变量a:"+a);
        }
    }
    break;
}
```

### Method Definitions

```java
returnType def functionName(type:paramName, ...) {
    return value;
}

int def funtionName(int:a, int:b) {
    return a + b;
}
```

```java
import List<java.lang.String> as StringList ;
StringList def funtionName(StringList:a, int:b) {
    return a;
}
```

### Java Method Calls and Built-in Calls

| Type                              | Example                                                  |
|-----------------------------------|----------------------------------------------------------|
| Static method call                | `ClassName::methodName(argType1:arg1, argType2:arg2...)` |
| Constructor call                  | `new ConstructorName(argType1:arg1, argType2:arg2...)`                                  |
| Instance method call              | `objectName.methodName(argType1:arg1, argType2:arg2...)` |
| JQuick custom function call       | `this.methodName(argType1:arg1, argType2:arg2...)`       |
| JQuick built-in SPI function call | `Builtin::methodName(argType1:arg1, argType2:arg2...)`   |

#### Static Method

Usage:

`ClassName::methodName(argType1:arg1, argType2:arg2...)`

```java
java.lang.Math::max(int:5, int:10); // 10
java.lang.String::format(java.lang.String:"Hello %s", java.lang.String:"JQuick");
java.lang.String::valueOf(int:123);
java.lang.System::currentTimeMillis();
```

#### Constructor Method

Usage:

`new ClassName(argType1:arg1, argType2:arg2...)`

```java
new java.util.ArrayList();
new com.github.paohaijiao.model.JStudent(int:42);
new com.github.paohaijiao.model.JStudent(java.lang.String:"test string");
```

#### Instance Method

Usage:

`objectName.methodName(argType1:arg1, argType2:arg2...)`

```java
testObj.isEven(int:4); // true
str1.toUpperCase();
testObj.methodWithMixedArgs(java.lang.String:"Test", int:42, boolean:true);
```

#### JQuick Custom Function Call

Usage:

`this.functionName(argType1:arg1, argType2:arg2...)`

```java
int def getSquare(int:a,int:b){
    return a*b;
}
int a=1;
int b=2;
int c=this.getSquare(int:a,int:b);
```

#### JQuick Built-in Function Call (SPI)

Usage:

`Builtin::methodName(argType1:arg1, argType2:arg2...)`

```java
Builtin::today();
Builtin::formatDate(java.lang.String:"yyyy-MM-dd");
Builtin::uuid();
```

### Comments

```java
// single-line
```

```java
/*
  multi-line
*/
```

### Reserved Keywords

| Category | Keywords |
|----------|----------|
| Primitive types | `short`, `int`, `float`, `double`, `long`, `boolean`, `byte` |
| Control flow | `if`, `else`, `for`, `while`, `return`, `break`, `continue` |
| Declarations | `def`, `import`, `as`, `new`, `var` |
| Literals | `true`, `false`, `null`, `this` |
| Built-ins | `console`, `Builtin` |

### Identifier Rules

```java
1.starts with letter/underscore
2.may contain letters, digits, underscores
3.case-sensitive
```

---

## JQuickJava Unified Entry Class

`JQuickJava` is an all-in-one entry class built on top of `JQuickJavaXmlParseFactory`. It provides a fluent API for **package declarations, variable initialization, direct script execution, and XML / inline rule proxies**, making it easy to integrate rule engines, scorecards, and process orchestration.

### Fluent API Overview

| API | Description |
|-----|-------------|
| `JQuickJava.create()` | Create a default entry instance |
| `importPackage(qualifiedName, alias)` | Declare a package import, generating `import Type as Alias;` |
| `importPackage(qualifiedName)` | Declare a package import, deriving the alias from the last segment automatically |
| `importPackages(...)` | Batch package imports (auto-generated aliases) |
| `constant(name, value)` | Initialize a context constant |
| `variable(name, value)` / `variables(map)` | Initialize context variables |
| `env(name, value)` / `envs(map)` | Initialize runtime environment variables |
| `init(statement...)` | Append script-level initialization statements (prepended to the script) |
| `rule(methodName, functionDefinition)` | Register an inline rule script (equivalent to the CDATA of an XML `<java>` element) |
| `execute(scriptBody)` | Execute a JQuick script directly (program path) |
| `createApi(apiInterface)` | Create a pure inline-rule proxy |
| `createApi(apiInterface, xmlPath)` | Create a proxy that merges XML rules and inline rules |

### Direct Script Execution

`execute(...)` runs a script through the program path, supporting package declarations, function definitions, and invocations. Context variables can be referenced directly inside the script:

```java
Object result = JQuickJava.create()
        .importPackage("java.lang.String", "type1")   // import java.lang.String as type1;
        .variable("base", 60)                          // context variable, directly usable in script
        .execute(
                "type1 def a(int:a,int:b) {\n" +
                "   int t = a+b;\n" +
                "   type1 p = java.lang.String::valueOf(int:t);\n" +
                "   return p;\n" +
                "}\n" +
                "int c=1;\n" +
                "int d=2;\n" +
                "this.a(int:c,int:d);"                 // invoke custom function -> "3"
        );
```

### Pure Inline-Rule Proxy

`rule(...)` registers an inline rule script (equivalent to the CDATA of an XML `<java>` element). `createApi(Class)` generates an interface proxy without any XML file, with parameter binding, return-type conversion, and context injection identical to the XML proxy:

```java
public interface UserMapper {
    public int sum(@Param("a") int a, @Param("b") int b);
    public int mul(@Param("a") int a, @Param("b") int b);
}
```

```java
UserMapper userApi = JQuickJava.create()
        .importPackage("java.lang.String", "type1")
        .constant("base", 60)
        .rule("sum", "int def sum(int:a,int:b){ return a+b; }")
        .rule("mul", "type1 def mul(int:a,int:b){ int t=a*b; type1 p = java.lang.String::valueOf(int:t); return p; }")
        .createApi(UserMapper.class);

int sum = userApi.sum(1, 2);    // 3
int mul = userApi.mul(3, 4);    // 12
```

### Combining with XML Mode

`createApi(Class, xmlPath)` loads both the XML rule file and inline rules: methods already defined in XML still use the XML definitions, methods missing from XML are filled by inline rules, and for same-name methods **inline rules take precedence**. This is ideal for incrementally adding rules without changing existing XML configurations:

```xml
<!-- jquick-java.xml -->
<javas namespace="com.github.paohaijiao.xml.UserMapper">
    <java name="sum" returnClass="java.util.List">
        <![CDATA[
           int def sum(int:a,int:b) {
              return a+b;
            }
        ]]>
    </java>
</javas>
```

```java
UserMapper userApi = JQuickJava.create()
        .rule("mul", "int def mul(int:a,int:b){ return a*b; }")   // inline rule fills the method missing from XML
        .createApi(UserMapper.class, "jquick-java.xml");          // XML rules + inline rules merged

int sum = userApi.sum(1, 2);    // from XML rule -> 3
int mul = userApi.mul(3, 4);    // from inline rule -> 12
```

> Tip: Inline rules and XML rules share the same execution chain (parameter binding → function-definition parsing → function-body execution → return-type conversion), so an inline rule is an equivalent way of writing the CDATA of an XML `<java>` element.

---

## Core Visitor Notes

### JQuickMethodInvocationCallVisitor

`JQuickMethodInvocationCallVisitor` is the core visitor in the JQuick method invocation dispatch chain. It identifies different invocation forms during parse tree traversal (static method calls, constructor calls, instance method calls, `this`-context method calls, built-in method calls, and method calls on accessed static variables) and routes them to the corresponding executor or manager.

### visitBuiltinMethodCall

#### API Purpose & Trigger Timing

`visitBuiltinMethodCall(BuiltinMethodCallContext ctx)` specifically intercepts **JQuick built-in method invocation**. It is triggered when the script contains `Builtin::methodName(...)`: it extracts the built-in method name, parses the argument list, and dispatches to `JQuickMethodInvocationManager.invoke(methodName, args)`. This API does not go through the Java reflection chain; it uses the **JQuick built-in capability registration and dispatch pipeline** instead.

```java
Builtin::today();
Builtin::formatDate(java.lang.String:"yyyy-MM-dd");
Builtin::uuid();
```

#### Typical Use Cases

- Providing platform-level built-in functions (date utilities, string utilities, etc.)
- Exposing a unified script utility entry
- Encapsulating common capabilities as built-in SPI methods
- Decoupling script calls from concrete business objects

#### Difference from Regular Method Calls

| Item | `visitBuiltinMethodCall` | Regular method calls (static / instance / this) |
|------|--------------------------|--------------------------------------------------|
| Entry syntax | `Builtin::method(...)` | `Class::method(...)` / `obj.method(...)` / `this.method(...)` |
| Invocation target | JQuick built-in method manager | Java classes, object instances, or script functions |
| Dispatch mechanism | `JQuickMethodInvocationManager` | Reflection factories or function registry |
| Design purpose | Unified built-in SPI capability entry | Invoke external Java methods or user-defined script functions |

#### Built-in Function Source (SPI)

The implementations behind `Builtin::methodName(...)` are provided by the standalone project **jquick-transform-function** through the Java SPI mechanism (`io.github.paohaijiao:jquick-transform-function`): core interface `JQuickMethodFunctionProvider`, registration file `META-INF/services/...`, and convenient base class `JQuickBaseFunctionFunctionProvider` (encapsulates argument validation and type conversion). To extend, implement and register it in a standalone SPI extension project. See [jquick-transform-function](https://github.com/paohaijiao/jquick-transform-function) for details.

---

## Complete Examples

The following examples are layered by syntax capability, progressively combining the various JQuick invocation forms from basic to advanced.

### Basic: Function Definition & `this` Invocation

```java
int def getSquare(int:a,int:b){
    return a*b;
}
int a=1;
int b=2;
int c=this.getSquare(int:a,int:b);
```

> Demonstrates custom function definition (`int def getSquare(...)`) and invocation via `this.functionName(...)`.

### Advanced: Constructor & Instance Method Invocation

```java
java.util.HashMap<java.lang.String,java.lang.String> def a(int:a,float:b) {
    java.lang.String str1 = new java.lang.String(java.lang.String:"Hello");   // constructor call
    console.log(str1);
    java.lang.String upperStr = str1.toUpperCase();                           // instance method call
    console.log(upperStr);
    java.lang.String subStr = str1.substring(int:1, int:3);                   // instance method call
    console.log(subStr);
    java.util.HashMap<java.lang.String,java.lang.String> result = new java.util.HashMap();
    result.put(java.lang.String:"constructed1", java.lang.String:str1);
    result.put(java.lang.String:"constructed2", java.lang.String:str1);
    result.put(java.lang.String:"uppercased", java.lang.String:upperStr);
    result.put(java.lang.String:"substring", java.lang.String:subStr);
    return result;
}
int c=1;
float d=8.1;
this.a(int:c,float:d);
```

> Demonstrates constructor calls (`new java.lang.String(...)`), instance method calls (`toUpperCase()`, `substring(...)`, `put(...)`), and `console.log(...)` output.

### Advanced: Java Static Method Invocation

```java
java.lang.String def a(int:a,float:b) {
    java.lang.String p=java.lang.String::format(java.lang.String:"Number: %d, String: %s",int: 42, java.lang.String:"test");
    return p;
}
int c=1;
float d=8.1;
this.a(int:c,float:d);
```

> Demonstrates Java static method invocation (`java.lang.String::format(...)`) with arguments passed in the `type:value` form.

### Advanced: Package Import & Type Alias

```java
import java.lang.String as type1;
type1 def a(int:a,float:b) {
   type1 p=type1::format(type1:"Number: %d, String: %s",int: 42, type1:"test");
   return p;
}
int c=1;
float d=8.1;
this.a(int:c,float:d);
```

> Demonstrates package imports (`import Type as Alias;`) and type aliases, which can be used directly for type declarations and static calls.

---

## XML Configuration Scenario

### Enterprise Credit Scorecard

One of the most typical JQuick Java scenarios is credit scoring, risk decisioning, and rule engines.

| Level 1 Dimension | Level 2 Dimension | Rule | Score |
|-------------------|------------------|------|-------|
| Business Operation | Operating Years | ≥10 years | 3 |
| Business Operation | Operating Years | 3-5 years | 1 |
| Business Operation | Operating Years | <3 years | 0 |
| Financial Status | Debt Ratio | ≤50% | 4 |
| Financial Status | Debt Ratio | 50%-70% | 3 |
| Financial Status | Debt Ratio | 70%-85% | 1 |
| Financial Status | Debt Ratio | >85% | 0 |
| Financial Status | Current Ratio | ≥2.0 | 3 |
| Financial Status | Current Ratio | 1.5-2.0 | 2 |
| Financial Status | Current Ratio | 1.0-1.5 | 1 |
| Financial Status | Current Ratio | <1.0 | 0 |
| Performance Record | Bank Credit | No overdue records | 4 |
| Performance Record | Bank Credit | 1-2 settled overdue records | 2 |
| Performance Record | Bank Credit | ≥3 overdue records | 0 |
| Enterprise Qualification | Credit Rating | AAA | 2 |
| Enterprise Qualification | Credit Rating | AA | 1.5 |
| Enterprise Qualification | Credit Rating | A | 1 |
| Enterprise Qualification | Credit Rating | BBB and below | 0 |
| Risk Management | Legal Litigation | No litigation | 3 |
| Risk Management | Legal Litigation | Closed and won | 2 |
| Risk Management | Legal Litigation | Ongoing litigation | 0 |

### Score Composition

- Business Operation
- Financial Status
- Performance Record
- Enterprise Qualification
- Risk Management

```java
@Test
public void testCreditScore() throws IOException {
      JQuickJavaXmlParseFactory handler = new JQuickJavaXmlParseFactory();
      JQuickFactory factory = new JQuickXmlFactory(handler, "credit-score.xml");
      CreditScoreMapper mapper = factory.createApi(CreditScoreMapper.class);
      int scoreOperatingYears = mapper.scoreOperatingYears(12);
      System.out.println("Operating years score: " + scoreOperatingYears);
      int scoreAnnualRevenue = mapper.scoreAnnualRevenue(8000);
      System.out.println("Annual revenue score: " + scoreAnnualRevenue);
      int businessScore = scoreOperatingYears + scoreAnnualRevenue + scoreProfitability;
      int financialScore = scoreDebtRatio + scoreCurrentRatio + scoreCashFlow;
      int complianceScore = scoreBankCredit + scoreCommercialCompliance;
      int qualificationScore = scoreIndustryCertification + scoreIntellectualProperty + scoreCreditRating;
      int riskScore = scoreLegalLitigation + scorePenalty;
      System.out.println("\n========== Subtotals ==========");
      System.out.println("Business subtotal: " + businessScore + "/10");
      System.out.println("Financial subtotal: " + financialScore + "/10");
      System.out.println("Compliance subtotal: " + complianceScore + "/8");
      System.out.println("Qualification subtotal: " + qualificationScore + "/6");
      System.out.println("Risk subtotal: " + riskScore + "/6");
      int baseScore = 60;
      int totalScore = mapper.calculateTotalScore(
              baseScore,
              businessScore,
              financialScore,
              complianceScore,
              qualificationScore,
              riskScore
      );
      System.out.println("\n========== Final Result ==========");
      System.out.println("Base score: " + baseScore);
      System.out.println("Dynamic adjustment: " + (totalScore - baseScore) + "/40");
      System.out.println("Total score: " + totalScore + "/100");
}
```

```java
public interface CreditScoreMapper {

  public int scoreOperatingYears(@Param("years") int years);

  public int scoreAnnualRevenue(@Param("revenue") double revenue);

  public int calculateTotalScore(@Param("businessScore") int businessScore,
                                 @Param("financialScore") int financialScore,
                                 @Param("currentRatio") int currentRatio,
                                 @Param("complianceScore") int complianceScore,
                                 @Param("qualificationScore") int qualificationScore,
                                 @Param("riskScore") int riskScore
  );
}
```

```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE javas PUBLIC "-//PAOHAIJIAO//DTD API JAVA 1.0//EN"
        "classpath:paohaijiao/dtd/Jquick-java.dtd">
<javas namespace="com.github.paohaijiao.xml.CreditScoreMapper">
  <java name="scoreOperatingYears" returnClass="int">
    <![CDATA[
            int def scoreOperatingYears(int:years) {
                if (years >= 10) {
                    return 3;
                } else if (years >= 5) {
                    return 2;
                } else if (years >= 3) {
                    return 1;
                } else {
                    return 0;
                }
            }
        ]]>
  </java>
  <java name="scoreAnnualRevenue" returnClass="int">
    <![CDATA[
            int def scoreAnnualRevenue(double:revenue) {
                if (revenue >= 5000) {
                    return 4;
                } else if (revenue >= 1000) {
                    return 3;
                } else if (revenue >= 500) {
                    return 2;
                } else {
                    return 1;
                }
            }
        ]]>
  </java>
  <java name="scoreProfitability" returnClass="int">
    <![CDATA[
            int def scoreProfitability(str:profitStatus) {
                if (profitStatus == "3years") {
                    return 3;
                } else if (profitStatus == "2years") {
                    return 2;
                } else if (profitStatus == "current") {
                    return 1;
                } else {
                    return 0;
                }
            }
        ]]>
  </java>
  <java name="calculateTotalScore" returnClass="int">
    <![CDATA[
        int def calculateTotalScore(
            int:businessScore,
            int:financialScore,
            int:complianceScore,
            int:qualificationScore,
            int:riskScore
        ) {
            int baseScore = 60;
            int weightedBusinessScore = (businessScore * 25) / 10;
            int weightedFinancialScore = (financialScore * 25) / 10;
            int weightedComplianceScore = (complianceScore * 20) / 8;
            int weightedQualificationScore = (qualificationScore * 15) / 6;
            int weightedRiskScore = (riskScore * 15) / 6;
            int totalScore = baseScore
                + weightedBusinessScore
                + weightedFinancialScore
                + weightedComplianceScore
                + weightedQualificationScore
                + weightedRiskScore;
            return totalScore;
        }
    ]]>
  </java>
</javas>
```

---

## Open Source

### Project Status

- License: Apache 2.0
- Version: 1.4.0
- Java Version: 8+
- Maven Central: `io.github.paohaijiao:jquick-java`

---

## Star / Fork Support

If this project is useful to you, support the repository directly:

- Give it a **Star**
- Fork it as your own engineering baseline
- Submit Issues or PRs to improve the JQuick ecosystem together

Project URL: <https://github.com/paohaijiao/jquick-java>
