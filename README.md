# JQuick Java 语法参考手册

简体中文 | [ENGLISH](./README-EN.md)

[![Stars](https://img.shields.io/github/stars/paohaijiao/jquick-java.svg?style=social)](https://github.com/paohaijiao/jquick-java)
[![Forks](https://img.shields.io/github/forks/paohaijiao/jquick-java.svg?style=social)](https://github.com/paohaijiao/jquick-java/forks)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.paohaijiao/jquick-java)](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-java)
[![Awesome Java](https://img.shields.io/badge/Awesome-Java-ff69b4.svg)](https://github.com/akullpp/awesome-java)

> 已被收录至 [Awesome Java](https://github.com/akullpp/awesome-java) 的 **Miscellaneous** 精选章节

---

## 项目简介

**JQuick Java 是一种轻量级类 Java 脚本语言，天生为规则引擎而生。**

它融合了 Java 的强类型安全与 JavaScript 的动态灵活性，支持在**运行时动态加载、解析和执行业务规则**。无论是复杂逻辑判断、数据校验还是流程编排，JQuick Java 都可以让你像配置参数一样调整业务规则，无需重启应用、无需重新部署，真正实现**规则与代码分离**。

适用场景：

- 复杂逻辑判断
- 数据校验
- 流程编排
- 评分卡 / 决策引擎

---

## 核心特性

- 轻量级类 Java 脚本语法
- 运行时动态加载规则
- 支持 XML 配置化定义业务逻辑
- 无缝 Java 互通，可直接调用 Java 方法
- 支持静态方法、构造方法、实例方法、自定义函数、内置 SPI 方法
- 支持泛型、集合、多维数组

---

## 目录

- [项目简介](#项目简介)
- [核心特性](#核心特性)
- [快速开始](#快速开始)
- [语法说明](#语法说明)
  - [数据类型](#数据类型)
  - [包引入](#包引入)
  - [变量声明](#变量声明)
  - [表达式与运算符](#表达式与运算符)
  - [控制结构](#控制结构)
  - [方法定义](#方法定义)
  - [Java 方法调用与内置调用](#java-方法调用与内置调用)
  - [注释](#注释)
  - [保留关键字](#保留关键字)
  - [标识符规则](#标识符规则)
- [JQuickJava 统一入口类](#jquickjava-统一入口类)
- [核心访问器说明](#核心访问器说明)
- [完整示例](#完整示例)
- [XML 配置场景](#xml-配置场景)
- [开源信息](#开源信息)
- [Star / Fork 支持](#star--fork-支持)

---

## 快速开始

### Maven 依赖

```xml
<dependency>
    <groupId>io.github.paohaijiao</groupId>
    <artifactId>jquick-java</artifactId>
    <version>1.4.0</version>
</dependency>
```

### 第一个 JQuick 示例

```java
import List<java.lang.String> as StringList ;
StringList list=["A","B","C"];
```

---

## 语法说明

### 数据类型

#### 基本类型（原生类型）

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

#### 复合类型

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
// 基本类型
int counter = 0;
boolean enabled = true;

// 泛型集合
List<String> names = ["Alice", "Bob"];
Map<String, Integer> scores = {"数学":90, "英语":85};
```

### 包引入

```java
import com.example.pkg as pkgAlias;
import java.lang.String as type1;
import java.util.Date as JDate;
import List<java.lang.String> as StringList ;
```

#### 导入声明

| 组件 | 说明 | 示例 |
|------|------|------|
| qualified.name | 点分路径 | `java.lang.String` |
| as | 别名 | `import java.util.Date as JDate;` |

### 变量声明

| 类型 | 示例 | 说明 |
|------|------|------|
| 基本类型 | `int x = 10;` | 支持类型推导 |
| 引用类型 | `String s = "hi";` | 可显式声明 |

```java
int x = 10;
boolean flag = true;
java.lang.String name = "JQuick";
```

### 表达式与运算符

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

#### 复杂表达式

```java
(a + b) * 2 > 10 && x != y
```

### 控制结构

#### if 语句

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

#### for 循环语句

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

#### while 循环语句

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

### 方法定义

```java
返回类型 def 函数名(参数类型:参数名, ...) {
    return 返回值;
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

### Java 方法调用与内置调用

| Type              | Example |
|-------------------|---------|
| 静态函数调用            | `ClassName::methodName(argType1:arg1, argType2:arg2...)` |
| 构造函数调用            | `new ConstructorName(argType1:arg1, argType2:arg2...)`                                  |
| 实例方法调用            | `objectName.methodName(argType1:arg1, argType2:arg2...)` |
| 调用 jquick 自定义函数   | `this.methodName(argType1:arg1, argType2:arg2...)`       |
| 调用 jquick 内置SPI函数 | `Builtin::methodName(argType1:arg1, argType2:arg2...)`   |

#### 静态方法

使用方法：

`类名::方法名(参数类型1:参数1，参数类型2:参数2...)`

```java
java.lang.Math::max(int:5, int:10); // 10
java.lang.String::format(java.lang.String:"Hello %s", java.lang.String:"JQuick");
java.lang.String::valueOf(int:123);
java.lang.System::currentTimeMillis();
```

#### 构造方法

使用方法：

`new 构造类(参数类型1:参数1，参数类型2:参数2...)`

```java
new java.util.ArrayList();
new com.github.paohaijiao.model.JStudent(int:42);
new com.github.paohaijiao.model.JStudent(java.lang.String:"test string");
```

#### 实例方法

使用方法：

`对象名.方法名(参数类型1:参数1，参数类型2:参数2...)`

```java
testObj.isEven(int:4); // true
str1.toUpperCase();
testObj.methodWithMixedArgs(java.lang.String:"Test", int:42, boolean:true);
```

#### 调用 jquick 自定义函数

使用方法：

`this.函数名(参数类型1:参数1，参数类型2:参数2...)`

```java
int def getSquare(int:a,int:b){
    return a*b;
}
int a=1;
int b=2;
int c=this.getSquare(int:a,int:b);
```

#### 调用 jquick 内置函数（SPI）

使用方法：

`Builtin::方法名(参数类型1:参数1，参数类型2:参数2...)`

```java
Builtin::today();
Builtin::formatDate(java.lang.String:"yyyy-MM-dd");
Builtin::uuid();
```

### 注释

```java
// single-line
```

```java
/*
  multi-line
*/
```

### 保留关键字

| 类别 | 关键字 |
|------|--------|
| 基本类型 | `short`, `int`, `float`, `double`, `long`, `boolean`, `byte` |
| 控制流 | `if`, `else`, `for`, `while`, `return`, `break`, `continue` |
| 声明 | `def`, `import`, `as`, `new`, `var` |
| 字面量 | `true`, `false`, `null`, `this` |
| 内置 | `console`, `Builtin` |

### 标识符规则

```java
1.以英文字符开始
2.可以包含英文数字
3.大小写敏感
```

---

## JQuickJava 统一入口类

`JQuickJava` 是基于 `JQuickJavaXmlParseFactory` 封装的一站式入口类，采用链式 API 提供**包声明、变量初始化、脚本直接执行、XML / 内联规则代理**等便捷能力，适合在规则引擎、评分卡、流程编排等场景中快速接入。

### 链式 API 概览

| API | 说明 |
|-----|------|
| `JQuickJava.create()` | 创建默认入口实例 |
| `importPackage(qualifiedName, alias)` | 声明包引入，生成脚本语法 `import 类型 as 别名;` |
| `importPackage(qualifiedName)` | 声明包引入，自动取最后一段作为别名 |
| `importPackages(...)` | 批量声明包引入（自动生成别名） |
| `constant(name, value)` | 初始化上下文常量 |
| `variable(name, value)` / `variables(map)` | 初始化上下文变量 |
| `env(name, value)` / `envs(map)` | 初始化运行时环境变量 |
| `init(statement...)` | 追加脚本级初始化语句（拼接在脚本头部） |
| `rule(methodName, functionDefinition)` | 注册内联规则脚本（等价于 XML `<java>` 的 CDATA） |
| `execute(scriptBody)` | 直接执行 JQuick 脚本（program 路径） |
| `createApi(apiInterface)` | 生成纯内联规则代理 |
| `createApi(apiInterface, xmlPath)` | 生成 XML + 内联规则合并代理 |

### 直接执行脚本

`execute(...)` 走 program 路径执行脚本，支持包声明、函数定义与调用，上下文变量可直接在脚本内引用：

```java
Object result = JQuickJava.create()
        .importPackage("java.lang.String", "type1")   // import java.lang.String as type1;
        .variable("base", 60)                          // 上下文变量，脚本内可直接引用
        .execute(
                "type1 def a(int:a,int:b) {\n" +
                "   int t = a+b;\n" +
                "   type1 p = java.lang.String::valueOf(int:t);\n" +
                "   return p;\n" +
                "}\n" +
                "int c=1;\n" +
                "int d=2;\n" +
                "this.a(int:c,int:d);"                 // 调用自定义函数 -> "3"
        );
```

### 纯内联规则代理

`rule(...)` 注册内联规则脚本（内容等价于 XML `<java>` 元素的 CDATA），`createApi(Class)` 无需任何 XML 文件即可生成接口代理。参数绑定、返回类型转换、上下文注入与 XML 代理完全一致：

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

### 与 XML 方式结合

`createApi(Class, xmlPath)` 同时加载 XML 规则文件与内联规则：XML 中已有的方法仍走 XML 定义，XML 中不存在的方法由内联规则补齐，同名方法**内联规则优先**。适用于在不改动既有 XML 配置的前提下增量补充规则：

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
        .rule("mul", "int def mul(int:a,int:b){ return a*b; }")   // 内联规则补齐 XML 中没有的方法
        .createApi(UserMapper.class, "jquick-java.xml");          // XML 规则 + 内联规则合并

int sum = userApi.sum(1, 2);    // 来自 XML 规则 -> 3
int mul = userApi.mul(3, 4);    // 来自内联规则 -> 12
```

> 提示：内联规则与 XML 规则共用同一套执行链（参数绑定 → 函数定义解析 → 函数体执行 → 返回类型转换），因此内联规则可视为 XML `<java>` 元素 CDATA 的等价写法。

---

## 核心访问器说明

### JQuickMethodInvocationCallVisitor

`JQuickMethodInvocationCallVisitor` 是 JQuick 方法调用分发链中的核心访问器，负责在语法树遍历阶段识别不同调用形式（静态方法、构造方法、实例方法、`this` 上下文方法、内置方法、访问静态变量后的方法），并将调用路由到对应的执行器或管理器。

### visitBuiltinMethodCall

#### API 作用与触发时机

`visitBuiltinMethodCall(BuiltinMethodCallContext ctx)` 专门拦截 **JQuick 内置方法调用**，当脚本中出现 `Builtin::方法名(...)` 时触发：提取内置方法名 → 解析参数列表 → 交给 `JQuickMethodInvocationManager.invoke(methodName, args)` 分发执行。该 API 不经过 Java 反射链，而是走 **JQuick 内置能力注册与调度链路**。

```java
Builtin::today();
Builtin::formatDate(java.lang.String:"yyyy-MM-dd");
Builtin::uuid();
```

#### 使用场景

- 平台级内置函数（日期处理、字符串工具等）
- 统一脚本工具方法入口
- 将常用能力封装为脚本内置 SPI 方法
- 需要将脚本调用与业务对象实例解耦时

#### 与普通方法调用的区别

| 对比项 | `visitBuiltinMethodCall` | 普通方法调用（静态 / 实例 / this） |
|--------|---------------------------|------------------------------------|
| 入口语法 | `Builtin::method(...)` | `Class::method(...)` / `obj.method(...)` / `this.method(...)` |
| 调用目标 | JQuick 内置方法管理器 | Java 类、对象实例或脚本函数 |
| 分发方式 | `JQuickMethodInvocationManager` | 反射工厂或函数注册表 |
| 设计目的 | 统一承载内置 SPI 能力 | 调用外部 Java 方法或脚本自定义函数 |

#### 内置函数来源（SPI）

`Builtin::方法名(...)` 的实现由独立项目 **jquick-transform-function** 通过 Java SPI 机制提供（`io.github.paohaijiao:jquick-transform-function`）：核心接口 `JQuickMethodFunctionProvider`、注册文件 `META-INF/services/...`、便捷基类 `JQuickBaseFunctionFunctionProvider`（封装参数校验与类型转换）。在独立的 SPI 扩展工程中实现并注册即可扩展，详见 [jquick-transform-function](https://github.com/paohaijiao/jquick-transform-function)。

---

## 完整示例

以下示例按语法能力分层展示，从基础到进阶逐步组合 JQuick 的各类调用形式。

### 基础：函数定义与 `this` 调用

```java
int def getSquare(int:a,int:b){
    return a*b;
}
int a=1;
int b=2;
int c=this.getSquare(int:a,int:b);
```

> 展示自定义函数定义（`int def getSquare(...)`）与 `this.函数名(...)` 调用方式。

### 进阶：构造方法与实例方法调用

```java
java.util.HashMap<java.lang.String,java.lang.String> def a(int:a,float:b) {
    java.lang.String str1 = new java.lang.String(java.lang.String:"Hello");   // new 构造调用
    console.log(str1);
    java.lang.String upperStr = str1.toUpperCase();                           // 实例方法调用
    console.log(upperStr);
    java.lang.String subStr = str1.substring(int:1, int:3);                   // 实例方法调用
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

> 展示构造函数调用（`new java.lang.String(...)`）、实例方法调用（`toUpperCase()`、`substring(...)`、`put(...)`）与 `console.log(...)` 输出。

### 进阶：Java 静态方法调用

```java
java.lang.String def a(int:a,float:b) {
    java.lang.String p=java.lang.String::format(java.lang.String:"Number: %d, String: %s",int: 42, java.lang.String:"test");
    return p;
}
int c=1;
float d=8.1;
this.a(int:c,float:d);
```

> 展示 Java 静态方法调用（`java.lang.String::format(...)`），参数以 `类型:值` 形式传入。

### 进阶：包引入与类型别名

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

> 展示包引入（`import 类型 as 别名;`）与类型别名的使用，别名可直接用于类型声明与静态调用。

---

## XML 配置场景

### 企业信用评分卡

JQuick Java 最典型场景：信用评分、风险决策、规则引擎。

| 一级维度 | 二级维度 | 评分细则 | 得分 |
|----------|----------|----------|------|
| 经营状况 | 经营年限 | ≥10 年 | 3 |
| 经营状况 | 经营年限 | 3-5 年 | 1 |
| 经营状况 | 经营年限 | <3 年 | 0 |
| 财务状况 | 资产负债率 | ≤50% | 4 |
| 财务状况 | 资产负债率 | 50%-70% | 3 |
| 财务状况 | 资产负债率 | 70%-85% | 1 |
| 财务状况 | 资产负债率 | >85% | 0 |
| 财务状况 | 流动比率 | ≥2.0 | 3 |
| 财务状况 | 流动比率 | 1.5-2.0 | 2 |
| 财务状况 | 流动比率 | 1.0-1.5 | 1 |
| 财务状况 | 流动比率 | <1.0 | 0 |
| 履约记录 | 银行信贷 | 无逾期 | 4 |
| 履约记录 | 银行信贷 | 逾期 1-2 次已结清 | 2 |
| 履约记录 | 银行信贷 | 逾期≥3 次 | 0 |
| 企业资质 | 信用评级 | AAA | 2 |
| 企业资质 | 信用评级 | AA | 1.5 |
| 企业资质 | 信用评级 | A | 1 |
| 企业资质 | 信用评级 | BBB 及以下 | 0 |
| 风险管理 | 法律诉讼 | 无诉讼 | 3 |
| 风险管理 | 法律诉讼 | 已结案胜诉 | 2 |
| 风险管理 | 法律诉讼 | 未结诉讼 | 0 |

### 信用分构成

- 经营状况
- 财务状况
- 履约记录
- 企业资质
- 风险管理

```java
@Test
public void testCreditScore() throws IOException {
      JQuickJavaXmlParseFactory handler = new JQuickJavaXmlParseFactory();
      JQuickFactory factory = new JQuickXmlFactory(handler, "credit-score.xml");
      CreditScoreMapper mapper = factory.createApi(CreditScoreMapper.class);
      int scoreOperatingYears = mapper.scoreOperatingYears(12);
      System.out.println("经营年限得分: " + scoreOperatingYears);
      int scoreAnnualRevenue = mapper.scoreAnnualRevenue(8000);
      System.out.println("年营收得分: " + scoreAnnualRevenue);
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

## 开源信息

### 项目进度

- License: Apache 2.0
- Version: 1.4.0
- Java Version: 8+
- Maven Central: `io.github.paohaijiao:jquick-java`

---

## Star / Fork 支持

如果这个项目对你有帮助，建议直接支持仓库：

- 点一个 **Star**
- Fork 一份作为你的工程基线
- 提交 Issue 或 PR 一起完善 JQuick 生态

项目地址：<https://github.com/paohaijiao/jquick-java>
