<p align="center">
  <img src="src/main/resources/static/jquick-logo.svg" width="240" alt="JQuick Java 标志" />
</p>

<h1 align="center">JQuick Java</h1>

<p align="center">
  <a href="https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-java"><img src="https://img.shields.io/maven-central/v/io.github.paohaijiao/jquick-java.svg?style=flat-square&label=Maven%20Central" alt="中央仓库" /></a>
  <a href="https://www.apache.org/licenses/LICENSE-2.0"><img src="https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat-square" alt="开源协议" /></a>
  <a href="https://plugins.jetbrains.com/search?search=JQuick%20Language%20Support"><img src="https://img.shields.io/badge/IDEA%20Plugin-JQuick%20Language%20Support-blue.svg?style=flat-square&logo=intellij-idea" alt="编辑器插件" /></a>
  <a href="https://github.com/akullpp/awesome-java"><img src="https://img.shields.io/badge/Awesome%20Java-Miscellaneous-ff69b4.svg?style=flat-square" alt="Awesome Java 收录" /></a>
</p>

<p align="center">
  <a href="./README.md">English</a> | <b>简体中文</b>
</p>

---

## 项目简介

**JQuick Java 是一款面向规则引擎的轻量级类 Java 脚本语言，以声明式配置驱动开发。**

它融合了 Java 的强类型安全与动态语言的灵活性，支持在**运行时动态加载、解析和执行**业务规则。规则以 XML 文档或内联脚本的形式声明，与业务代码彻底分离——调整一个指标、一个权重或一个阈值，就像修改配置一样简单，无需重启、无需重新部署。

JQuick Java 面向企业级存量环境设计：兼容 **Java 8 及以上版本**，可平稳嵌入既有系统，且尽量不引入重型第三方依赖。

本项目已被 [Awesome Java](https://github.com/akullpp/awesome-java) 收录于 **Miscellaneous** 精选章节。

典型使用场景：

- 企业信用评级与风险决策
- 复杂业务规则与数据校验
- 流程编排与评分计算
- 声明式低代码平台

> 说明：JQuick Java **不是安全沙箱**。规则可以触达任意 Java 类，因此严禁执行来源不可信的脚本。内置的调用防护默认只拦截少数已知危险调用，生产环境请务必配置白名单。

---

## 特性列表

- **类 Java 脚本引擎**：运行时解析与执行，兼顾强类型与动态灵活性
- **XML 规则代理**：以接口搭配 XML 声明规则，动态代理自动生成
- **内联规则**：可直接在 Java 代码中注册规则，与 XML 方式完全等价
- **无缝互通**：静态方法、构造方法、实例方法、静态变量对象、`this` 自定义函数与 `Builtin::` 内置函数均可调用
- **高性能调用链**：反射与运行时字节码调用器双实现，调用器并发缓存、一次生成永久复用
- **声明式配置驱动**：指标、权重与阈值由 XML 配置维护，不改动业务代码
- **调用防护**：类级与方法级黑白名单，默认拦截危险系统调用
- **内置语言服务**：基于标准输入输出的语言服务协议，支持增量文档同步、代码补全、悬停提示、定义跳转、文档符号与实时诊断
- **轻量兼容**：Java 8 及以上版本，仅依赖少量体积小巧的生态组件，无重型运行期依赖

---

## IDEA 插件：JQuick Language Support

JQuick Java 提供配套的编辑器插件 **JQuick Language Support**。在编辑器中打开「设置 → 插件 → 插件市场」，搜索名称 **JQuick Language Support**，点击安装并重启编辑器即可，无需手动下载或本地安装；该插件在 JetBrains 插件市场网站上同样可以搜索到。

插件为 `.jquick` 规则脚本提供完整的编辑支持：

| 能力 | 说明 |
|------|------|
| 文件类型与语言 | 注册 `JQuick` 语言以及 `.jquick` 文件扩展名 |
| 语法高亮 | 关键字、字面量、类型与运算符高亮，配色方案可自定义 |
| 代码补全 | 结合上下文的补全，输入 `.` 与 `:` 时自动触发 |
| 快速文档 | 光标所在符号的类型与签名信息 |
| 定义跳转 | 从调用处直接跳转到对应声明 |
| 结构视图 | 展示当前脚本中的函数与变量大纲 |
| 实时诊断 | 编辑过程中即时标注解析错误与警告 |
| 语言服务集成 | 对接引擎内置的语言服务 |

---

## 快速上手

### Maven 依赖

```xml
<dependency>
    <groupId>io.github.paohaijiao</groupId>
    <artifactId>jquick-java</artifactId>
    <version>2.6.0</version>
</dependency>
```

### 可运行示例

下面的程序完整可运行：它在脚本中定义一个函数，并通过 `this` 完成调用。

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
        System.out.println("execute result: " + result); // 输出 "3"
    }
}
```

执行 `mvn compile` 编译工程后，在编辑器中或命令行直接运行该类的入口方法即可。

---

## 语法参考

### 保留字

`if` `else if` `else` `for` `while` `break` `continue` `return` `def` `this` `import` `as` `new` `null` `true` `false` `short` `int` `float` `double` `long` `boolean` `byte` `List` `Set` `Map` `Builtin` `console.log(` `@` `.class`

其中两个是单一词法单元，必须按原样书写：`else if`（空格属于该单元）与 `console.log(`（左括号紧跟在后面）。

### 字面量

| 种类 | 示例 | 说明 |
|------|------|------|
| 整数 | `1` `3` | 按数值解析 |
| 浮点数 | `1.5` `3.8` `10.0` | 小数位精度由 `JQuickJavaConfig.scale` 控制，默认值为 `2` |
| 字符串 | `"helloworld"` `'hello'` | 双引号与单引号均可 |
| 布尔值 | `true` `false` | |
| 空值 | `null` | |
| 日期 | `2025-06-07` | 日期字面量 |
| 日期时间 | `2025-06-07 12:00:01` | 日期时间字面量 |
| 列表 | `[1,2,3,4,5,6]` | 列表字面量 |
| 映射 | `{"user":"mike","active":true}` | 映射字面量，条目写作 `key:value` |
| 类字面量 | `com.github.paohaijiao.literal1.JLiteral1Test.class` | 解析为 `Class` 对象 |
| 上下文变量 | `${type}` | 按名称从执行上下文取值 |

### 类型与变量声明

| 类型 | 示例 |
|------|------|
| 基本类型 | `short` `int` `float` `double` `long` `boolean` `byte` |
| 引用类型（全限定类名） | `java.lang.String a = "paohaijiao"` |
| 集合泛型 | `List<java.lang.String> a = ["paohaijiao"]` |
| 映射泛型 | `Map<java.lang.String,java.lang.Object> a = {"companyName":"TechCorp Inc."}` |
| 通用泛型引用类型 | `java.util.ArrayList<java.lang.Integer>` |
| 数组 | `java.lang.String[] a = ["paohaijiao"]` |
| 引入别名 | `type1 a = "paohaijiao"` |

声明语法为 `类型 变量名 = 表达式`：

- 前置类型**可以省略**，但 `=` 与初始化表达式**不可省略**，因此 `int a;` 这类裸声明不合法；
- 顶层声明的结尾分号可以省略；
- 支持嵌套字面量，例如 `Map<java.lang.String,java.lang.Object> a={"topProducts":["ProductA","ProductB"],"companyName":"TechCorp Inc."}`。

### 引入声明

```jquick
import java.lang.String as a ;
import List<java.lang.String> as c ;
```

`import` 必须带 `as` 别名。别名随后可以充当函数返回类型、变量类型、静态调用的类名与参数类型：

```jquick
import java.lang.String as type1;
type1 def a(int:a,float:b) {
    type1 p = type1::format(type1:"Number: %d, String: %s", int:42, type1:"test");
    return p;
}
```

### 运算符与表达式

| 分类 | 运算符 |
|------|--------|
| 算术 | `+` `-` `*` `/` |
| 字符串拼接 | `+` |
| 比较 | `>` `>=` `<` `<=` `==` `!=` |
| 逻辑 | `&&` `||` |
| 一元 | 前缀 `+` `-` |
| 分组 | `(` `)` |

优先级顺序为：`expression → logical → comparison → additive → multiplicative → unary → primary`。

```jquick
1+1
6-1
6*2
10.0/3
"hello"+"world"
2>1
true&&false
true||false
1-(8*(6-2))
```

不支持：取模运算符 `%`、一元运算符 `!` 以及 `and` / `or` 关键字；逻辑运算请使用 `&&` 与 `||`。

### 控制结构

| 结构 | 语法 |
|------|------|
| 条件分支 | `if (条件) { ... }` |
| 多分支 | `if (...) { ... } else if (...) { ... } else { ... }` |
| 计数循环 | `for (int i = 0; i < 10; i = i + 1) { ... }` |
| 条件循环 | `while (条件) { ... }` |
| 循环控制 | `break;` / `continue;` |
| 返回值 | `return 表达式;` |
| 打印输出 | `console.log(表达式);` |

所有语句块都必须用花括号包裹。`for` 仅支持三段式写法，不提供遍历形式。

```jquick
for (int i = 0; i < 10; i = i + 1) {
    if (i == 2) {
        break;
    } else {
        console.log(i);
    }
}

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

if (eventType == "A") {
    score = 40;
} else if (eventType == "B") {
    score = 30;
} else {
    score = 0;
}
```

### 函数定义

```jquick
返回类型 def 函数名(类型:参数名, ...) {
    ...
    return 表达式;
}
```

- 返回类型**必须书写**；无返回值时写作 `void def 函数名(...)`；
- 返回类型可以是基本类型、全限定类名、集合泛型或引入别名；
- 函数通过 `this.函数名(参数...)` 调用，语法中不存在 `getSquare(1,2)` 这样的裸函数名调用；
- 函数之间可以互相调用，例如 `this.scoreReportTime(eventType, reportMinutes)`。

```jquick
int def getSquare(int:a,int:b) {
    return a*b;
}
int a=1;
int b=2;
int c=this.getSquare(int:a,int:b);
```

### 方法调用形式

| 形式 | 语法 | 真实示例 |
|------|------|----------|
| 静态方法 | `类名::方法名(参数...)` | `java.lang.Math::max(int:5, int:10);` |
| 构造方法 | `new 类名(参数...)` | `new java.util.ArrayList();` |
| 实例方法 | `对象.方法名(参数...)` | `testObj.isEven(int:4);` |
| 静态变量对象方法 | `类名@字段.方法(参数...)` | `java.lang.System@out.println(java.lang.String:"hello");` |
| 自定义函数 | `this.方法名(参数...)` | `this.getSquare(int:a,int:b);` |
| 内置函数 | `Builtin::方法名(参数...)` | `Builtin::sum(int:1,int:2,int:3,int:4,int:5,int:6);` |

```jquick
java.lang.Math::pow(double:2, double:3);
java.lang.String::format(java.lang.String:"Number: %d, String: %s", int:42, java.lang.String:"test");
java.lang.String::join(java.lang.CharSequence:",", java.lang.CharSequence:"a", java.lang.CharSequence:"b");
java.util.Collections::sort(List<java.lang.Integer>:listVar);
java.util.Objects::toString(java.lang.String:null);
java.lang.System::gc();

new com.github.paohaijiao.extract.model.JStudent(int:42, float:3.14, boolean:true);
new com.github.paohaijiao.extract.model.JStudent(List<java.lang.Integer>:listVar);

testObj.noReturn();
testObj.methodWithMixedArgs(java.lang.String:"Test", int:42, boolean:true);
```

### 参数写法

| 形式 | 示例 |
|------|------|
| 带类型参数 | `java.lang.Math::max(int:5, int:10);` |
| 普通表达式参数 | `this.scoreReportTime(eventType, reportMinutes);` |

两种写法都合法。带类型参数写作 `类型:表达式`，便于引擎精确匹配目标方法签名；普通表达式则按运行时类型进行推断。

### 输出与调试

`console.log(表达式);` 会先计算表达式，再打印其 `toString()` 结果，字符串拼接使用 `+`：

```jquick
console.log(1);
console.log(i+","+j);
console.log("当前的变量a:"+a);
```

`${名称}` 是一个独立字面量，按名称从执行上下文读取变量，可出现在任何需要表达式的位置。下面这段脚本的求值结果就是上下文变量 `type` 的值：

```jquick
${type}
```

注意：`console.log` 本身不做插值，需要把取值嵌进提示信息时请配合 `+` 使用。

### 内置函数

内置函数以 `Builtin::方法名(参数...)` 的形式调用，无需任何引入：

```jquick
Builtin::sum(int:1,int:2,int:3,int:4,int:5,int:6);
```

内置函数库由 [jquick-transform-function](https://github.com/paohaijiao/jquick-transform-function) 模块提供，本仓库测试中确认使用的是 `Builtin::sum(...)`，其余内置函数以该模块的说明为准。

---

## Java 接口与 XML 规则

### XML 规则文件

规则声明在 XML 文件中，并引用随包提供的文档类型定义：

```xml
<!DOCTYPE javas PUBLIC "-//PAOHAIJIAO//DTD API JAVA 1.0//EN"
        "classpath:paohaijiao/dtd/Jquick-java.dtd">
<javas namespace="com.example.demo.UserMapper">
    <java name="sum" returnClass="int">
        <![CDATA[
            int def sum(int:a,int:b) {
                return a+b;
            }
        ]]>
    </java>
</javas>
```

| 元素或属性 | 是否必需 | 含义 |
|------------|----------|------|
| `javas` | 必需 | 根元素，容纳全部规则方法 |
| `javas@namespace` | 必需 | 接口的全限定类名，不匹配会立即失败 |
| `java` | 必需 | 一条规则方法 |
| `java@name` | 必需 | 接口方法名，必须完全一致 |
| `java@returnClass` | 必需 | 仅作元数据，实际返回类型以接口方法签名为准 |
| `java` 主体 | 必需 | 函数定义脚本，通常包裹在 `CDATA` 中 |

文档类型定义还允许在 `java` 内嵌套 `if(test)`、`foreach(collection,item,open,close,separator)` 与 `choose/when(test)/otherwise` 元素；本仓库自带的规则文件均把逻辑直接写在 `CDATA` 中。

### 接口绑定

接口通过 `namespace` 与方法名同 XML 文件绑定，参数则通过 `@Param` 绑定：

```java
import com.github.paohaijiao.xml.param.Param;

public interface UserMapper {

    HashMap<String,String> all();

    int sum(@Param("a") int a, @Param("b") int b);

    int mul(@Param("a") int a, @Param("b") int b);
}
```

不使用 `@Param` 时会退回按反射参数名绑定，因此建议始终保留该注解。

### 统一入口

| 接口方法 | 说明 |
|----------|------|
| `JQuickJava.create()` | 创建默认入口实例 |
| `importPackage(qualifiedName, alias)` | 以显式别名声明引入 |
| `importPackage(qualifiedName)` / `importPackages(qualifiedNames)` | 以自动推导的别名声明引入 |
| `constant(name, value)` / `variable(name, value)` / `variables(Map)` | 初始化常量与上下文变量 |
| `env(name, value)` / `envs(Map)` | 初始化运行期环境变量 |
| `init(statement)` / `init(statements)` | 追加脚本级初始化语句 |
| `rule(methodName, functionDefinition)` | 注册一条内联规则，等价于一个 `java` 元素 |
| `rule(methodName, returnClass, functionDefinition)` / `rules(Map)` | 带元数据注册内联规则，或批量注册 |
| `execute(scriptBody)` | 直接执行一段脚本 |
| `buildScript(scriptBody)` | 拼接引入声明、初始化语句与脚本主体 |
| `createApi(apiInterface)` | 生成仅由内联规则支撑的代理 |
| `createApi(apiInterface, xmlPath)` | 生成由 XML 规则叠加内联规则支撑的代理 |
| `getContext()` / `getEnvironment()` / `buildRuntimeEnvironment()` / `handler()` | 暴露上下文、环境变量与 XML 处理器 |

### 内联规则与 XML 合并

```java
UserMapper userApi = JQuickJava.create()
        .importPackage("java.lang.String", "type1")
        .constant("base", 60)
        .rule("sum", "int def sum(int:a,int:b){ return a+b; }")
        .rule("mul", "type1 def mul(int:a,int:b){ int t=a*b; type1 p = java.lang.String::valueOf(int:t); return p; }")
        .createApi(UserMapper.class);

int sum = userApi.sum(1, 2); // 3
int mul = userApi.mul(3, 4); // 12
```

当同时提供 XML 文件与内联规则时，同名方法以内联规则为准：

```java
UserMapper userApi = JQuickJava.create()
        .rule("mul", "int def mul(int:a,int:b){ return a*b; }")
        .createApi(UserMapper.class, "jquick-java.xml");
```

---

## 文档说明

- `src/test/java` 目录——覆盖全部能力的可执行示例，例如 `JQuickJavaTest`、`JStaticMethodInvocationTest`、`JInstanceMethodInvocationTest`、`JConstructorMethodInvocationTest`、`JLiteral1Test`、`JLogicalTest`、`JForTest`、`JImportTest`
- `src/test/resources` 目录——真实规则文件：`jquick-java.xml`、`rules.xml`、`scoring-rules.xml`、`credit-score.xml`
- `src/main/resources/paohaijiao/dtd/Jquick-java.dtd`——XML 规则的文档类型定义
- `docs/visitor-refactor-lsp/README.md`——语言服务与编辑器接入说明
- [生态仓库](https://github.com/paohaijiao)——`jquick-asm`、`jquick-xmlProxy`、`jquick-transform-function` 等
- [中央仓库](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-java)——已发布构件与版本历史

---

## 贡献指南

欢迎以任意方式参与 JQuick Java：

- 提交 [议题](https://github.com/paohaijiao/jquick-java/issues)，反馈缺陷或提出建议
- 提交合并请求，完善语法解析、执行链、XML 代理或文档
- 提交前执行 `mvn test`，保证既有用例全部通过
- 点亮星标或复刻仓库，帮助更多使用者上手 JQuick Java

---

## 开源协议

本项目基于 [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0) 开源。

项目地址：<https://github.com/paohaijiao/jquick-java>
