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
- **调用防护（黑白名单）**：内置调用防护 `JQuickJavaInvocationGuard`，类/方法级黑白名单，默认拦截危险系统调用
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

### 最小 Demo：直接执行脚本

不依赖 XML 文件，`JQuickJava.create().execute(...)` 即可跑通全链路（与官方测试 `JQuickJavaTest#testEntryExecute` 一致）：

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
        System.out.println("execute result: " + result); // 输出 3
    }
}
```

### 最小 Demo：接口 + XML 规则代理

面向生产最常用的形态是「接口 + XML」，一行 `createApi` 生成规则代理：

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
System.out.println(calc.sum(1, 2)); // 输出 3
```

### 运行单元测试

```bash
mvn test -Dtest=TEmEventScoringServiceTest
```

---

## 核心语法参考

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

### 变量声明、import 与类型系统

变量声明必须**带初始化**（不支持 `int x;` 这类无初始化的裸声明）：

```jquick
int c = 1;                                   // 基本类型
double ratio = actualCapital / registeredCapital;
java.lang.String str = "hello";              // 引用类型（全限定类名）
List<java.lang.Integer> list = [1, 2, 3];    // 泛型 + 列表字面量
```

支持的类型：

| 类型 | 示例 |
|------|------|
| 基本类型 | `short`、`int`、`float`、`double`、`long`、`boolean`、`byte` |
| 引用类型 | 全限定类名，如 `java.lang.String` |
| 泛型 | `List<T>`、`Set<T>`、`Map<K,V>`，如 `List<java.lang.String>` |
| 数组 | `int[]`、`java.lang.String[]` |
| 类字面量 | `com.example.Foo.class` |

引用外部类型前，可用 import 声明别名，脚本内直接使用别名：

```jquick
import java.lang.String as a;
import List<java.lang.String> as c;
import java.util.Date as JDate;
```

### 静态方法调用详解

静态方法统一形式为 `类全限定名::方法名(类型:参数, ...)`，参数以 `类型:` 前缀标注（typedArgument）。各形态的真实用例（取自 [JStaticMethodInvocationTest.java](src/test/java/com/github/paohaijiao/primary2/JStaticMethodInvocationTest.java)）：

| 形态 | 脚本示例 |
|------|---------|
| 无参 | `java.lang.System::currentTimeMillis();` |
| 多参（int） | `java.lang.Math::max(int:5, int:10);` |
| 多参（double） | `java.lang.Math::pow(double:2, double:3);` |
| 混合参数 | `java.lang.String::format(java.lang.String:"Number: %d, String: %s", int:42, java.lang.String:"test");` |
| 可变参数 varargs | `java.lang.String::join(java.lang.CharSequence:",", java.lang.CharSequence:"a", java.lang.CharSequence:"b");` |
| 泛型集合参数 | `java.util.Collections::sort(List<java.lang.Integer>:listVar);` |
| null 参数 | `java.util.Objects::toString(java.lang.String:null);` |
| 返回 void | `java.lang.System::gc();` |
| 自定义业务类 | `com.github.paohaijiao.service.exract.JService::sum(int:1, int:2);` |
| 静态变量对象方法 | `java.lang.System@out.println(java.lang.String:"hahah");` |

要点：

- 参数既可写 `类型:值`（显式声明类型），也可写普通表达式；返回值可用于赋值、也可作为参数继续传递；
- 泛型类型在参数中按 `List<java.lang.String>` 形式声明，引擎自动做类型匹配；
- 调用失败常见报错 `No matching method found` / `please double check static method invocation`：优先核对类全限定名与方法签名（参数个数、类型）是否与 Java 端一致；
- 所有静态调用同样经过 `JQuickJavaInvocationGuard` 黑白名单校验。

### console.log 调试技巧

`console.log(表达式);` 是脚本内置打印语句，执行表达式并输出其 `toString()` 结果：

```jquick
console.log("当前的变量a:" + a);   // 字符串拼接打印
console.log(1);                    // 直接打印字面量
console.log(score);                // 打印变量当前值
```

调试实践：

- **打印入参**：规则函数入口处打印各参数，快速确认参数绑定是否正确；
- **打印中间结果**：多分支 / 多层循环逻辑中打印中间变量，配合 `if` / `for` / `while` 定位问题分支；
- **字符串插值 `${varName}`**：从上下文变量（`JContext`）取值替换，适合组装日志与提示信息：

```jquick
console.log("当前企业类型:${type}");
```

- **结合异常类型排查**：`SecurityException` 是黑白名单拦截；`No matching method found` 是方法签名不匹配；`Undefined variable` 是变量未注入（详见 FAQ Q6）。

---

## Java API 使用

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

### 链式调用示例

内联规则注册（与官方测试 `JQuickJavaTest#testEntryInlineCreateApi` 一致）：

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

XML + 内联规则合并（同名方法内联优先，官方测试 `testEntryMergeXmlAndInline`）：

```java
UserMapper userApi = JQuickJava.create()
        .rule("mul", "int def mul(int:a,int:b){ return a*b; }")
        .createApi(UserMapper.class, "jquick-java.xml");
int sum = userApi.sum(1, 2); // 3（来自 XML）
int mul = userApi.mul(3, 4); // 12（来自内联）
```

### 编程式调用 API（JQuickJavaReflectionFactory）

不写脚本，纯 Java 侧直接调用，与脚本共用同一套「安全校验 + ASM 调用器」链路：

```java
// 静态方法：staticMethod(Class).invoke(方法名, 参数类型[], 参数...)
String s = JQuickJavaReflectionFactory.staticMethod(Math.class)
        .invoke("max",
                new JQuickJavaTypeReference<?>[]{JQuickJavaTypeReference.of(int.class),
                        JQuickJavaTypeReference.of(int.class)}, 5, 10); // 10

// 构造方法：constructor(Class).newInstance(...)
TestClass t = JQuickJavaReflectionFactory.constructor(TestClass.class)
        .newInstance(JQuickJavaTypeReference.of(String.class), "test");

// 实例方法：instanceMethod(obj).invoke(方法名, ...)
String name = JQuickJavaReflectionFactory.instanceMethod(instance).invoke("getName");
```

### 运行时环境（JQuickJavaRuntimeEnvironment）

环境变量用于向规则注入运行时参数，支持强类型读取：

```java
JQuickJavaRuntimeEnvironment env = JQuickJavaRuntimeEnvironment.create()
        .set("threshold", 100)
        .setAll(Map.of("maxScore", 50));

env.getInt("threshold", 0);        // 100
env.getString("key", "default");   // 带默认值
env.getBoolean("enabled", false);  // 布尔读取
env.contains("threshold");         // true
```

### 异常处理

JQuick Java 的主要异常类型与处理建议：

| 异常 | 触发场景 | 处理建议 |
|------|---------|---------|
| `JQuickJavaException` | 脚本解析 / 执行过程中的通用错误 | 捕获并记录脚本 ID，定位语法或类型问题 |
| `JQuickJavaBuiltInExecuteException` | 内置 SPI（`Builtin::`）执行异常 | 检查函数名与参数类型是否匹配 |
| `SecurityException` | 命中调用防护黑白名单（如 `System#exit`） | 检查 Guard 配置；不可信调用一律拒绝 |
| `IllegalArgumentException` | 方法匹配失败 / 泛型导入缺别名 | 核对类型声明与 `importPackage(name, alias)` 用法 |
| `JQuickJavaBreakException` / `JQuickJavaContinueException` | `break;` / `continue;` 内部实现 | 属引擎内部机制，业务代码无需捕获 |

> 提示：`JQuickJavaMethodInvoker` 会对反射调用做异常解包（`unwrapInvocationException`），业务侧看到的是目标方法抛出的真实异常，而非 `InvocationTargetException` 包装。

---

## 业务实战案例

### 案例：企业信用评级打分

> 完整可运行代码见测试类 [TEmEventScoringServiceTest.java](src/test/java/com/github/paohaijiao/xml/TEmEventScoringServiceTest.java)。

#### 1. 指标体系：企业信用评级体系表_带权重.xlsx

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

#### 2. 通过 JQuick 脚本引擎 / XML 规则加载评级体系

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

#### 3. 定义评分接口

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

#### 4. 执行企业信用评级打分

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

#### 5. 结果解读

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

### 更多案例

仓库内置了更多可直接运行的真实案例（均含接口、XML 规则与测试代码）：

| 案例 | 规则文件 | 测试类 |
|------|---------|--------|
| 多节点时限计分（分级线性评分 + 总分汇总） | [rules.xml](src/test/resources/rules.xml) | [RulesServiceImpl.java](src/test/java/com/github/paohaijiao/xml/RulesServiceImpl.java) |
| 五维信用评分 + 等级 / 风险 / 建议 | [credit-score.xml](src/test/resources/credit-score.xml) | [CreditScoreTest.java](src/test/java/com/github/paohaijiao/xml/CreditScoreTest.java) |
| 字符串 / 集合 / 静态变量调用 | [jquick-java.xml](src/test/resources/jquick-java.xml) | [JQuickJavaTest.java](src/test/java/com/github/paohaijiao/xml/JQuickJavaTest.java) |
| 企业信用评级加权总分 | [scoring-rules.xml](src/test/resources/scoring-rules.xml) | [TEmEventScoringServiceTest.java](src/test/java/com/github/paohaijiao/xml/TEmEventScoringServiceTest.java) |

---

## 性能与生产最佳实践

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

### 生产环境最佳实践

面向生产环境的落地要点：

- **复用代理实例**：`createApi(...)` 含 XML 解析与代理生成成本，规则不变时复用单例代理（懒加载 + 双检锁），把解析开销摊薄到首次；
- **规则热更新**：规则变更时重新 `createApi(...)` 即可加载新规则（内置幂等导入注册与静态上下文隔离，同一 JVM 内可反复重建代理）；配合配置中心 + 版本号实现灰度发布与秒级回滚；
- **预热**：应用启动后预热关键规则路径，避免首个请求承担解析冷启动成本；
- **可观测性**：引擎内置执行耗时输出（`execute time:xxxs`），生产可接入日志采集；为规则版本与维度得分留痕，便于审计与对账；
- **容量预期**：单次调用亚微秒级，对评分、校验类业务足够；吞吐敏感场景叠加规则结果缓存，并以目标环境实测为准。

---

## 安全风险

> ⚠️ **安全警告：JQuick Java 不是完整的安全沙箱，严禁执行不可信来源的外部脚本。**

请区分以下两个概念：

- **面向使用者**：JQuick Java 提供的 `JQuickJavaInvocationGuard` 是**调用防护**（类/方法级黑白名单），用于拦截*已知*危险调用，而非强隔离沙箱；
- **面向生产环境**：脚本本质上是可调用任意 Java 类（反射、IO、网络、文件系统）的代码。**默认配置只拦截黑名单中的少数方法，白名单未配置时其余方法默认放行**。执行来源不可信的脚本，等同于执行任意代码，可能导致严重安全事件。

生产环境必须遵守的底线：

1. **只执行可信规则**：规则脚本必须由本组织可信人员编写与评审，禁止加载外部不可信来源（未经验证的用户输入、公开互联网下载等）的脚本；
2. **严格配置白名单**：通过 `JQuickJavaReflectionFactory.setInvocationGuard(...)` 配置方法/类白名单，把脚本可调用面收敛到最小业务范围；
3. **隔离部署**：规则引擎运行于独立进程 / 容器 / 专用账号，限制网络与文件系统权限，作为纵深防御的最后一道闸门；
4. **审计与灰度**：规则变更走评审、灰度、回滚闭环，并对脚本调用链留痕审计。

---

## FAQ

**Q1：修改规则需要重启服务吗？**
不需要。规则以 XML / 内联脚本承载，重新 `createApi(...)` 即可加载新规则；引擎内置幂等导入注册与静态上下文隔离，支持同一 JVM 内反复重建代理。

**Q2：性能怎么样？**
官方实测单次 Java 方法调用约 223ns（100 万次约 222ms，亚微秒量级），对评分、校验、规则计算场景足够。优化手段：复用代理实例、预热、按需缓存规则结果。

**Q3：脚本能调用我自己的业务类吗？**
可以。脚本支持静态方法（`类名::方法`）、构造方法（`new 类名`）、实例方法（`obj.方法`）三种调用形式，业务对象也可放入 `JContext` 注入后调用。注意：所有调用都受黑白名单约束。

**Q4：支持哪些 Java 版本？**
Java 8+（编译目标为 8），可在 8 / 11 / 17 等全系 JVM 运行，兼容老旧环境与国产化生态。

**Q5：与硬编码 if-else 相比有什么价值？**
规则与代码分离、配置化维护、热更新免重启、业务人员可自主调整阈值与权重。详见「与同类引擎对比」。

**Q6：报错了怎么排查？**
优先看 `console.log` 输出与异常类型：`SecurityException` 是黑白名单拦截；`No matching method found` 是方法签名不匹配；`Undefined variable` 是变量未注入。仓库测试类（`JQuickJavaTest`、`TEmEventScoringServiceTest` 等）提供了完整正确写法，可对照排查。

**Q7：IDE 有提示吗？**
有。内置 LSP 语言服务 `JQuickLanguageServer`（stdio 模式），支持补全、悬停、定义跳转与文档符号；`maven-shade` 打包的 fat jar 可直接 `java -jar` 启动接入编辑器。

---

## 与同类引擎对比

| 对比维度 | JQuick Java | Drools |
|---------|------------|--------|
| 定位 | 轻量级类 Java 规则脚本语言 | 重量级规则引擎（Rete/Phreak 推理） |
| 规则语言 | 类 Java 语法（XML / 内联脚本） | DRL 专有语言 + DSL |
| 执行模型 | ANTLR 解析 + Visitor 执行链 | Rete 网络模式匹配 + 议程推理 |
| Java 方法调用 | ASM 字节码调用器 + 反射双实现 | MVEL / 反射调用 |
| 运行时依赖 | 少量 JQuick 生态 jar | drools-core / kie-api 等 |
| 环境要求 | Java 8+，纯 Java 可嵌入 | Java 8+，常伴随 KIE 生态 |
| 典型场景 | 评分、校验、脱敏、流程编排 | 复杂关联规则、大规模事实推理、决策表 |

**选型建议**：评分计算、动态校验、数据脱敏、流程编排等确定性规则场景，JQuick Java 更轻、更快、接入成本更低；跨事实强关联、回溯推理、决策表等复杂推理场景，Drools 更合适。

---

## 生态

JQuick Java 生态由官方仓库、核心模块与内置案例组成：

### 官方仓库

| 仓库 | 说明 |
|------|------|
| [paohaijiao/jquick-java](https://github.com/paohaijiao/jquick-java) | 核心引擎：脚本引擎 + 统一入口 `JQuickJava` + XML 规则代理 + LSP 语言服务（本项目） |
| [paohaijiao/jquick-asm](https://github.com/paohaijiao/jquick-asm) | ASM 高性能调用链：运行时字节码生成与调用器缓存 |
| [paohaijiao/jquick-xmlProxy](https://github.com/paohaijiao/jquick-xmlProxy) | XML 规则代理：接口 + XML 声明式规则 |
| [paohaijiao/jquick-json](https://github.com/paohaijiao/jquick-json) | 轻量 JSON 处理库：解析 / 生成 / 操作 JSON、POJO ↔ JSON 互转 |
| [paohaijiao/jquick-path](https://github.com/paohaijiao/jquick-path) | JSONPath 查询语言：路径表达式从 JSON 文档提取数据 |
| [paohaijiao/jquick-curl](https://github.com/paohaijiao/jquick-curl) | 基于 Curl 的 Java HTTP 客户端框架：cURL 命令直接转化为 HTTP 请求 |
| [paohaijiao/jquick-sql](https://github.com/paohaijiao/jquick-sql) | SQL 增强与数据访问模块 |
| [paohaijiao/jquick-excel](https://github.com/paohaijiao/jquick-excel) | Excel 配置驱动模块，支撑指标体系等配置化场景 |
| [paohaijiao/jquick-pdf](https://github.com/paohaijiao/jquick-pdf) | PDF 输出模块，支撑评级报告等输出场景 |
| [paohaijiao/jquick-transform-function](https://github.com/paohaijiao/jquick-transform-function) | 内置 SPI 函数库（`Builtin::`）：类型判断、位运算、信息脱敏、身份证识别、金融校验 |
| [paohaijiao/jquick-connector](https://github.com/paohaijiao/jquick-connector) | 生态连接器模块 |
| [paohaijiao/jquick-mybatis](https://github.com/paohaijiao/jquick-mybatis) | MyBatis 集成模块 |
| [paohaijiao/jquick-idea-plugin](https://github.com/paohaijiao/jquick-idea-plugin) | IDEA 插件：IDE 内规则脚本编辑支持 |
| [paohaijiao/javelin](https://github.com/paohaijiao/javelin) | 生态父 POM 与基础设施 |

### 核心模块（Maven Central）

| 模块 | 说明 |
|------|------|
| [jquick-java](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-java) | 核心引擎（本项目） |
| [jquick-asm](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-asm) | ASM 高性能调用链 |
| [jquick-xmlProxy](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-xmlProxy) | XML 规则代理 |
| [jquick-json](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-json) | 轻量 JSON 处理库 |
| [jquick-path](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-path) | JSONPath 查询语言 |
| [jquick-curl](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-curl) | 基于 Curl 的 HTTP 客户端 |
| [jquick-sql](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-sql) | SQL 增强与数据访问 |
| [jquick-excel](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-excel) | Excel 配置驱动 |
| [jquick-pdf](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-pdf) | PDF 输出 |
| [jquick-transform-function](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-transform-function) | 内置 SPI 函数库 |
| [jquick-banner](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-banner) | 启动横幅 |
| [javelin-core](https://central.sonatype.com/artifact/io.github.paohaijiao/javelin-core) | 基础设施 |

### 内置集成案例

| 案例 | 入口 |
|------|------|
| 企业信用评级打分（Excel 配置驱动 + XML 规则） | [TEmEventScoringServiceTest.java](src/test/java/com/github/paohaijiao/xml/TEmEventScoringServiceTest.java) |
| 多节点时限计分（分级线性评分 + 总分汇总） | [rules.xml](src/test/resources/rules.xml) |
| 五维信用评分 + 等级 / 风险 / 建议 | [credit-score.xml](src/test/resources/credit-score.xml) |
| 字符串 / 集合 / 静态变量调用示例 | [jquick-java.xml](src/test/resources/jquick-java.xml) |

> 欢迎将基于 JQuick Java 的落地案例 / 二次封装项目通过 PR 补充到本清单，一起完善 JQuick 生态。

---

## 模块说明

JQuick Java 依托 JQuick 生态，核心模块一览：

| 模块 | 说明 |
|------|------|
| **jquick-java**（本项目） | 核心：脚本引擎 + 统一入口 `JQuickJava` + XML 规则代理（基于 `jquick-xmlProxy`）+ LSP 语言服务 |
| **jquick-asm** | 基于 ASM 的高性能调用链：运行时字节码生成（`JQuickJavaAsmInvokerFactory` / `JQuickJavaAsmMethodInvoker` 等）、调用器并发缓存、基本类型自动装箱/拆箱，与反射调用链双实现 |
| **jquick-gateway** | 生态内规则接入与网关能力模块 |
| **jquick-sql** | 生态内 SQL 增强与数据访问能力模块 |
| **jquick-json** | 生态内轻量 JSON 处理库：解析 / 生成 / 操作 JSON、POJO ↔ JSON 互转、变量合并，基于 jquick-asm 字节码 Bean 访问器，[GitHub](https://github.com/paohaijiao/jquick-json) |
| **jquick-path** | 生态内 JSONPath 查询语言：通过路径表达式从 JSON 文档提取数据（类似 XPath for JSON），[GitHub](https://github.com/paohaijiao/jquick-path) |
| **jquick-curl** | 生态内基于 Curl 的 Java HTTP 客户端框架：cURL 命令直接转化为可执行 HTTP 请求，支持注解 / XML 配置与动态代理，[GitHub](https://github.com/paohaijiao/jquick-curl) |
| **jquick-excel** | 生态内 Excel 配置驱动模块，支撑指标体系等配置化场景 |
| **jquick-pdf** | 生态内 PDF 输出模块，支撑评级报告等输出场景 |
| **jquick-banner** | 生态内启动横幅模块 |
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
