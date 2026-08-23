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

## 核心访问器说明

### JQuickMethodInvocationCallVisitor

`JQuickMethodInvocationCallVisitor` 是 JQuick 方法调用分发链中的核心访问器，负责在语法树遍历阶段识别不同调用形式，并将调用路由到对应的执行器或管理器。

它主要处理以下几类调用：

- 静态方法调用
- 构造方法调用
- 实例方法调用
- `this` 上下文方法调用
- 内置方法调用
- 访问静态变量后的方法调用

### visitBuiltinMethodCall

#### API 作用

`visitBuiltinMethodCall(JQuickJavaParser.BuiltinMethodCallContext ctx)` 用于**专门拦截 JQuick 内置方法调用场景**。

当脚本中出现 `Builtin::方法名(...)` 形式的语法节点时，该访问器方法会被触发，并将方法名与参数列表统一交给 `JQuickMethodInvocationManager` 分发执行。

换句话说，这个 API 不是走 Java 反射的普通方法调用链，而是走 **JQuick 内置能力注册与调度链路**。

#### 触发时机

当解析器识别到如下语法时触发：

```java
Builtin::方法名(参数类型1:参数1, 参数类型2:参数2...)
```

访问器执行流程可以概括为：

1. 提取内置方法名
2. 解析参数列表
3. 将参数转换为运行时对象列表
4. 调用 `JQuickMethodInvocationManager.invoke(methodName, args)`

#### 使用场景

适用于以下场景：

- 为 JQuick 提供平台级内置函数
- 提供统一的脚本工具方法入口
- 将常用能力封装为脚本内置 SPI 方法
- 需要将脚本调用与业务对象实例解耦时

典型例子包括：

- 日期处理
- 字符串工具
- 运行时上下文辅助函数
- 脚本层公共函数能力

#### 与普通方法调用的区别

| 对比项 | `visitBuiltinMethodCall` | 普通方法调用（静态 / 实例 / this） |
|--------|---------------------------|------------------------------------|
| 入口语法 | `Builtin::method(...)` | `Class::method(...)` / `obj.method(...)` / `this.method(...)` |
| 调用目标 | JQuick 内置方法管理器 | Java 类、对象实例或脚本函数 |
| 分发方式 | `JQuickMethodInvocationManager` | 反射工厂或函数注册表 |
| 设计目的 | 统一承载内置 SPI 能力 | 调用外部 Java 方法或脚本中自定义函数 |
| 耦合方式 | 与业务对象解耦 | 与具体类、实例或脚本函数绑定 |

可以把它理解为：

- 普通方法调用关注“调用谁”
- 内置方法调用关注“调用 JQuick 平台预定义能力”

#### 简单代码示例

```java
Builtin::today();
Builtin::formatDate(java.lang.String:"yyyy-MM-dd");
Builtin::uuid();
```

#### 实际 SPI 来源

`visitBuiltinMethodCall` 最终调用的是 `JQuickMethodInvocationManager.invoke(methodName, args)`，而内置方法本身来自 `jquick-transform-function` 项目的 SPI 扩展点。

这个扩展点的核心接口是：

- `com.github.paohaijiao.function.core.JQuickMethodFunctionProvider`

SPI 注册文件是：

- `META-INF/services/com.github.paohaijiao.function.core.JQuickMethodFunctionProvider`

也就是说，脚本里的：

```java
Builtin::formatDate(java.lang.String:"yyyy-MM-dd")
```

本质上会被路由到某个 `JQuickMethodFunctionProvider` 实现类的 `invoke(List<Object> args)` 方法。

#### 扩展原理

一个内置方法 Provider 通常需要完成 3 件事：

1. 声明方法名，对应脚本里的 `Builtin::方法名(...)`
2. 实现 `invoke(List<Object> args)`，处理真正的业务逻辑
3. 通过 `META-INF/services` 注册到 SPI，让运行时自动发现

`jquick-transform-function` 中已经提供了一个便于复用的抽象基类：

- `JQuickBaseFunctionFunctionProvider`

这个基类已经封装了：

- `methodName`
- `description`
- `validateArgCount(...)`
- `validateArgCountRange(...)`
- `asString(...)`
- `asInt(...)`
- `asLong(...)`
- `asDouble(...)`
- `asBoolean(...)`

因此扩展新函数时，通常直接继承这个基类即可。

#### 如何扩展一个新的内置 SPI 方法

下面以新增 `maskName` 为例。

##### 1. 定义 Provider 类

```java
package com.github.paohaijiao.function.custom;

import com.github.paohaijiao.function.domain.JQuickBaseFunctionFunctionProvider;
import java.util.List;

public class JQuickMaskNameFunctionProvider extends JQuickBaseFunctionFunctionProvider {

    public JQuickMaskNameFunctionProvider() {
        super("maskName", "姓名脱敏 - 用法: maskName(name)");
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

##### 2. 注册到 SPI 文件

在资源文件中追加实现类全限定名：

`META-INF/services/com.github.paohaijiao.function.core.JQuickMethodFunctionProvider`

```java
com.github.paohaijiao.function.custom.JQuickMaskNameFunctionProvider
```

如果你有多个 Provider，就一行一个实现类。

##### 3. 让业务工程依赖扩展包

只要运行 `jquick-java` 的应用 ClassPath 中包含：

- `jquick-java`
- `jquick-transform-function`
- 你自定义的 SPI 扩展 Jar

运行时就可以发现并加载该内置方法。

#### 完整 SPI 扩展示例

下面给出一个可以直接参考的最小可用扩展示例，假设你要新增一个 `maskName` 内置函数。

##### 工程目录结构

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

##### Provider 实现类

```java
package com.github.paohaijiao.function.custom;

import com.github.paohaijiao.function.domain.JQuickBaseFunctionFunctionProvider;
import java.util.List;

public class JQuickMaskNameFunctionProvider extends JQuickBaseFunctionFunctionProvider {

    public JQuickMaskNameFunctionProvider() {
        super("maskName", "姓名脱敏 - 用法: maskName(name)");
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

##### SPI 注册文件内容

文件路径：

`src/main/resources/META-INF/services/com.github.paohaijiao.function.core.JQuickMethodFunctionProvider`

文件内容：

```java
com.github.paohaijiao.function.custom.JQuickMaskNameFunctionProvider
```

##### 业务工程依赖扩展包

当你的业务工程需要使用这个扩展时，只需要把扩展 Jar 和 JQuick 相关依赖一起放进工程中：

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

##### JQuick 脚本调用示例

```java
Builtin::maskName(java.lang.String:"张三");
Builtin::maskName(java.lang.String:"欧阳修");
```

##### 与规则脚本组合使用

```java
java.lang.String def buildDisplayName(java.lang.String:name, java.lang.String:phone) {
    java.lang.String safeName = Builtin::maskName(java.lang.String:name);
    java.lang.String safePhone = Builtin::phoneMask(java.lang.String:phone);
    return java.lang.String::format(java.lang.String:"%s-%s", java.lang.String:safeName, java.lang.String:safePhone);
}

java.lang.String result = this.buildDisplayName(java.lang.String:"张三", java.lang.String:"13800138000");
console.log(result);
```

##### 扩展生效判断

如果下面脚本可以正常执行，说明你的 SPI 已经被 `visitBuiltinMethodCall` 正确接管并路由：

```java
java.lang.String masked = Builtin::maskName(java.lang.String:"张三丰");
console.log(masked);
```

#### 如何在 JQuick 中使用扩展函数

Provider 注册成功后，就可以直接在脚本里调用：

```java
Builtin::maskName(java.lang.String:"张三");
```

也可以在规则脚本中组合使用：

```java
java.lang.String def formatUser(java.lang.String:name, java.lang.String:phone) {
    java.lang.String safeName = Builtin::maskName(java.lang.String:name);
    java.lang.String safePhone = Builtin::phoneMask(java.lang.String:phone);
    return java.lang.String::format(java.lang.String:"%s-%s", java.lang.String:safeName, java.lang.String:safePhone);
}
```

#### 已有内置函数的来源说明

`jquick-transform-function` 已经内置了大量可直接使用的 SPI 方法，例如：

- 集合类：`isArray`、`isEmpty`、`join`、`size`
- 条件类：`if`、`ifElse`、`switch`、`caseWhen`、`coalesce`
- 日期类：`toDate`、`toDateTime`、`addDays`、`year`、`month`
- 业务类：`phoneMask`、`phoneValidate`、`bankCardMask`、`idCardInfo`
- 数学类：`abs`、`avg`、`factorial`、`gcd`

这些函数都可以通过同一种调用方式访问：

```java
Builtin::函数名(...)
```

#### 扩展建议

如果你要为业务系统增加新的脚本能力，建议遵循下面的边界：

- 通用工具函数放在 `jquick-transform-function` 风格的 SPI Provider 中
- 与具体业务对象强绑定的方法，不要做成 `Builtin::`，优先走普通实例方法或 `this` 自定义函数
- Provider 命名尽量与方法名一一对应，便于排查和维护
- 参数校验尽量在 Provider 内完成，避免把运行时错误暴露到更深层

#### 排查思路

当 `Builtin::xxx(...)` 调用失败时，优先检查：

1. 方法名是否与 Provider 中的 `getMethodName()` / 构造器传入名称一致
2. SPI 文件中是否注册了实现类全限定名
3. 扩展 Jar 是否已经进入运行时 ClassPath
4. 参数个数与参数类型是否符合 Provider 的实现要求

---

## 完整示例

### 示例 1

```java
int def getSquare(int:a,int:b){
    return a*b;
}
int a=1;
int b=2;
int c=this.getSquare(int:a,int:b);
```

### 示例 2

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

### 示例 3

```java
java.lang.String def a(int:a,float:b) {
    java.lang.String p=java.lang.String::format(java.lang.String:"Number: %d, String: %s",int: 42, java.lang.String:"test");
    return p;
}
int c=1;
float d=8.1;
this.a(int:c,float:d);
```

### 示例 4

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
