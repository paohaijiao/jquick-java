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

## Core Visitor Notes

### JQuickMethodInvocationCallVisitor

`JQuickMethodInvocationCallVisitor` is the core visitor in the JQuick method invocation dispatch chain. It identifies different invocation forms during parse tree traversal and routes them to the corresponding executor or manager.

It mainly handles:

- Static method calls
- Constructor calls
- Instance method calls
- `this`-context method calls
- Built-in method calls
- Method calls on accessed static variables

### visitBuiltinMethodCall

#### API Purpose

`visitBuiltinMethodCall(JQuickJavaParser.BuiltinMethodCallContext ctx)` is used to **intercept built-in method invocation scenarios specifically in JQuick**.

When the script contains a syntax node in the form `Builtin::methodName(...)`, this visitor method is triggered and dispatches the method name and arguments to `JQuickMethodInvocationManager`.

In other words, this API does not use the normal Java reflection invocation path. It uses the **JQuick built-in capability registration and dispatch pipeline** instead.

#### Trigger Timing

It is triggered when the parser recognizes syntax like this:

```java
Builtin::methodName(argType1:arg1, argType2:arg2...)
```

The visitor flow is:

1. Extract the built-in method name
2. Parse the argument list
3. Convert arguments into runtime objects
4. Call `JQuickMethodInvocationManager.invoke(methodName, args)`

#### Typical Use Cases

This API is suitable for:

- Providing platform-level built-in functions for JQuick
- Exposing a unified script utility entry
- Encapsulating common capabilities as built-in SPI methods
- Decoupling script calls from concrete business objects

Typical examples include:

- Date utilities
- String utilities
- Runtime context helper functions
- Shared script-level capabilities

#### Difference from Regular Method Calls

| Item | `visitBuiltinMethodCall` | Regular method calls (static / instance / this) |
|------|--------------------------|--------------------------------------------------|
| Entry syntax | `Builtin::method(...)` | `Class::method(...)` / `obj.method(...)` / `this.method(...)` |
| Invocation target | JQuick built-in method manager | Java classes, object instances, or script functions |
| Dispatch mechanism | `JQuickMethodInvocationManager` | Reflection factories or function registry |
| Design purpose | Unified built-in SPI capability entry | Invoke external Java methods or user-defined script functions |
| Coupling model | Decoupled from business objects | Bound to concrete classes, instances, or script functions |

In short:

- Regular method calls focus on who is being called
- Built-in method calls focus on predefined platform capabilities in JQuick

#### Simple Code Example

```java
Builtin::today();
Builtin::formatDate(java.lang.String:"yyyy-MM-dd");
Builtin::uuid();
```

#### Actual SPI Source

`visitBuiltinMethodCall` eventually delegates to `JQuickMethodInvocationManager.invoke(methodName, args)`, and the built-in methods themselves are provided by the SPI extension mechanism in the `jquick-transform-function` project.

The core SPI interface is:

- `com.github.paohaijiao.function.core.JQuickMethodFunctionProvider`

The SPI registration file is:

- `META-INF/services/com.github.paohaijiao.function.core.JQuickMethodFunctionProvider`

That means a script call like:

```java
Builtin::formatDate(java.lang.String:"yyyy-MM-dd")
```

is ultimately routed to the `invoke(List<Object> args)` method of a `JQuickMethodFunctionProvider` implementation.

#### Extension Model

A built-in function provider usually needs to do 3 things:

1. Declare the method name, which matches `Builtin::methodName(...)` in scripts
2. Implement `invoke(List<Object> args)` for the actual logic
3. Register the implementation through `META-INF/services` so it can be discovered at runtime

`jquick-transform-function` already provides a reusable abstract base class:

- `JQuickBaseFunctionFunctionProvider`

This base class already encapsulates:

- `methodName`
- `description`
- `validateArgCount(...)`
- `validateArgCountRange(...)`
- `asString(...)`
- `asInt(...)`
- `asLong(...)`
- `asDouble(...)`
- `asBoolean(...)`

So in practice, new built-in functions are usually implemented by extending this base class.

#### How To Extend a New Built-in SPI Function

The following example adds a new function named `maskName`.

##### 1. Define the Provider Class

```java
package com.github.paohaijiao.function.custom;

import com.github.paohaijiao.function.domain.JQuickBaseFunctionFunctionProvider;
import java.util.List;

public class JQuickMaskNameFunctionProvider extends JQuickBaseFunctionFunctionProvider {

    public JQuickMaskNameFunctionProvider() {
        super("maskName", "Name masking - usage: maskName(name)");
    }

    @Override
    public Object invoke(List<Object> args) {
        validateArgCount(args, 1);
        String name = asString(args.get(0));
        if (name == null || name.isEmpty()) {
            return name;
        }
        if (name.length() == 1) {
            return "*";
        }
        return name.charAt(0) + "*";
    }
}
```

##### 2. Register It in the SPI File

Append the fully qualified class name to:

`META-INF/services/com.github.paohaijiao.function.core.JQuickMethodFunctionProvider`

```java
com.github.paohaijiao.function.custom.JQuickMaskNameFunctionProvider
```

If you have multiple providers, list one implementation class per line.

##### 3. Add the Extension Jar to Your Application

As long as the runtime ClassPath of the `jquick-java` application contains:

- `jquick-java`
- `jquick-transform-function`
- your custom SPI extension jar

then the built-in function can be discovered and used at runtime.

#### Complete SPI Extension Example

Below is a minimal practical example for adding a new built-in function named `maskName`.

##### Project Structure

```java
custom-function-extension/
├─ pom.xml
├─ src/main/java/com/github/paohaijiao/function/custom/JQuickMaskNameFunctionProvider.java
└─ src/main/resources/META-INF/services/com.github.paohaijiao.function.core.JQuickMethodFunctionProvider
```

##### pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.github.paohaijiao</groupId>
    <artifactId>custom-function-extension</artifactId>
    <version>1.0.0</version>

    <dependencies>
        <dependency>
            <groupId>io.github.paohaijiao</groupId>
            <artifactId>jquick-java</artifactId>
            <version>1.4.0</version>
        </dependency>
        <dependency>
            <groupId>io.github.paohaijiao</groupId>
            <artifactId>jquick-transform-function</artifactId>
            <version>1.4.0</version>
        </dependency>
    </dependencies>
</project>
```

##### Provider Implementation

```java
package com.github.paohaijiao.function.custom;

import com.github.paohaijiao.function.domain.JQuickBaseFunctionFunctionProvider;
import java.util.List;

public class JQuickMaskNameFunctionProvider extends JQuickBaseFunctionFunctionProvider {

    public JQuickMaskNameFunctionProvider() {
        super("maskName", "Name masking - usage: maskName(name)");
    }

    @Override
    public Object invoke(List<Object> args) {
        validateArgCount(args, 1);
        String name = asString(args.get(0));
        if (name == null || name.isEmpty()) {
            return name;
        }
        if (name.length() == 1) {
            return "*";
        }
        if (name.length() == 2) {
            return name.charAt(0) + "*";
        }
        return name.charAt(0) + "**" + name.charAt(name.length() - 1);
    }
}
```

##### SPI Registration File

File path:

`src/main/resources/META-INF/services/com.github.paohaijiao.function.core.JQuickMethodFunctionProvider`

File content:

```java
com.github.paohaijiao.function.custom.JQuickMaskNameFunctionProvider
```

##### Add the Extension Jar to the Business Application

When your business application wants to use this extension, just include the extension jar together with the JQuick dependencies:

```xml
<dependencies>
    <dependency>
        <groupId>io.github.paohaijiao</groupId>
        <artifactId>jquick-java</artifactId>
        <version>1.4.0</version>
    </dependency>
    <dependency>
        <groupId>io.github.paohaijiao</groupId>
        <artifactId>jquick-transform-function</artifactId>
        <version>1.4.0</version>
    </dependency>
    <dependency>
        <groupId>com.github.paohaijiao</groupId>
        <artifactId>custom-function-extension</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

##### JQuick Script Usage Example

```java
Builtin::maskName(java.lang.String:"Zhang San");
Builtin::maskName(java.lang.String:"Ouyang Xiu");
```

##### Compose It Inside a Rule Script

```java
java.lang.String def buildDisplayName(java.lang.String:name, java.lang.String:phone) {
    java.lang.String safeName = Builtin::maskName(java.lang.String:name);
    java.lang.String safePhone = Builtin::phoneMask(java.lang.String:phone);
    return java.lang.String::format(java.lang.String:"%s-%s", java.lang.String:safeName, java.lang.String:safePhone);
}

java.lang.String result = this.buildDisplayName(java.lang.String:"Zhang San", java.lang.String:"13800138000");
console.log(result);
```

##### How To Verify the Extension Is Active

If the following script runs correctly, it means your SPI has been picked up and routed correctly by `visitBuiltinMethodCall`:

```java
java.lang.String masked = Builtin::maskName(java.lang.String:"Zhang Sanfeng");
console.log(masked);
```

#### How To Use the Extended Function in JQuick

After the provider is registered successfully, you can call it directly in scripts:

```java
Builtin::maskName(java.lang.String:"Zhang San");
```

It can also be composed inside rule scripts:

```java
java.lang.String def formatUser(java.lang.String:name, java.lang.String:phone) {
    java.lang.String safeName = Builtin::maskName(java.lang.String:name);
    java.lang.String safePhone = Builtin::phoneMask(java.lang.String:phone);
    return java.lang.String::format(java.lang.String:"%s-%s", java.lang.String:safeName, java.lang.String:safePhone);
}
```

#### Where Existing Built-in Functions Come From

`jquick-transform-function` already ships with many built-in SPI methods, for example:

- Collection functions: `isArray`, `isEmpty`, `join`, `size`
- Condition functions: `if`, `ifElse`, `switch`, `caseWhen`, `coalesce`
- Date functions: `toDate`, `toDateTime`, `addDays`, `year`, `month`
- Business functions: `phoneMask`, `phoneValidate`, `bankCardMask`, `idCardInfo`
- Math functions: `abs`, `avg`, `factorial`, `gcd`

All of them are accessed in the same way:

```java
Builtin::functionName(...)
```

#### Extension Recommendations

If you want to add new script capabilities for a business system, the recommended boundary is:

- Put generic utility functions into SPI providers in the style of `jquick-transform-function`
- For methods tightly coupled to business objects, do not force them into `Builtin::`; prefer regular instance methods or `this` custom functions
- Keep provider naming aligned with method naming for easier maintenance
- Perform argument validation inside the provider so errors fail closer to the call site

#### Troubleshooting

When `Builtin::xxx(...)` fails, check these items first:

1. Whether the method name matches the provider `getMethodName()` or constructor name exactly
2. Whether the implementation class is registered in the SPI file
3. Whether the extension jar is present on the runtime ClassPath
4. Whether the argument count and argument types match the provider implementation

---

## Complete Examples

### Sample 1

```java
int def getSquare(int:a,int:b){
    return a*b;
}
int a=1;
int b=2;
int c=this.getSquare(int:a,int:b);
```

### Sample 2

```java
java.util.HashMap<java.lang.String,java.lang.String> def a(int:a,float:b) {
    java.lang.String str1 = new java.lang.String(java.lang.String:"Hello");
    console.log(str1);
    java.lang.String upperStr = str1.toUpperCase();
    console.log(upperStr);
    java.lang.String subStr = str1.substring(int:1, int:3);
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

### Sample 3

```java
java.lang.String def a(int:a,float:b) {
    java.lang.String p=java.lang.String::format(java.lang.String:"Number: %d, String: %s",int: 42, java.lang.String:"test");
    return p;
}
int c=1;
float d=8.1;
this.a(int:c,float:d);
```

### Sample 4

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
