# JQuick Java 语法参考手册
简体中文 | [ENGLISH](./README-EN.md)
## 项目进度
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Version](https://img.shields.io/badge/version-1.4.0-brightgreen.svg)](CHANGELOG.md)
[![Java Version](https://img.shields.io/badge/Java-8%2B-orange.svg)]()
[![Stars](https://img.shields.io/github/stars/paohaijiao/jquick-java.svg?style=social)](https://github.com/paohaijiao/jquick-java)
[![Issues](https://img.shields.io/github/issues/paohaijiao/jquick-java.svg)](https://github.com/paohaijiao/jquick-java/issues)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.paohaijiao/jquick-java)](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-java)

<div>
✨ 轻量级类 Java 脚本语言｜专为规则引擎设计｜动态规则编排 ✨
</div>

## 📘 项目概述

 <b>JQuick Java 是一种轻量级类 Java 脚本语言，天生为规则引擎而生</b>。它完美融合了 Java 的强类型安全
与 JavaScript 的动态灵活性，支持在<b>运行时动态加载、解析和执行业务规则</b>。无论是复杂的逻辑判断、
数据校验还是流程编排，JQuick Java 都能让您<b>像配置参数一样调整业务规则</b>，无需重启应用，无需重新部署，
让您的系统真正实现<b>规则与代码分离</b>，适用于：
- 复杂逻辑判断
- 数据校验
- 流程编排
- 评分卡 / 决策引擎

## 📑 目录
- [📘 项目概述](#-项目概述)
- [✨ 核心特性](#-核心特性)
  - [1. 🚀 简化类型系统](#1--简化类型系统)
  - [1.2 🔗 动态规则引擎](#12--动态规则引擎)
- [2. 🏗️ 程序结构](#2-程序结构)
  - [2.1 📦 导入声明](#21-导入声明)
  - [2.2 ⚙️ 变量声明](#22-变量声明)
  - [⚙️ 2.3 表达式与运算符](#️-23表达式与运算符)
    - [2.3.1 复杂表达式](#231-复杂表达式)
    - [2.3.2 控制结构](#232-控制结构)
      - [2.3.2.1 if 语句](#2321-if-语句)
      - [2.3.2.2 循环语句](#2322-循环-语句)
      - [2.3.2.3 while 循环语句](#2323-while循环-语句)
      - [2.3.2.4 方法定义语句](#2324-方法定义语句)
    - [☕ 2.3.3 Java 方法调用](#-233-java-方法调用)
      - [2.3.3.1 静态方法](#2331-静态方法)
      - [2.3.3.2 构造方法](#2332-构造方法)
      - [2.3.3.3 实例方法](#2333-实例方法)
      - [2.3.3.4 内置方法(预留)](#2334-内置方法预留)
      - [2.3.3.5 注释](#2335-注释)
      - [2.3.3.6 代码示例](#2336-代码示例)
      - [2.3.3.7 保留关键字](#2337-保留关键字)
  - [标识符规则](#标识符规则)
- [完整示例](#完整示例)
  - [示例 1](#示例-1)
  - [示例 2](#示例-2)
  - [示例 3](#示例-3)
  - [示例 4](#示例-4)
- [3. XML 配置场景](#3-xml-配置场景)
  - [🏦 企业信用评分卡](#-企业信用评分卡)
- [捐献 ☕](#捐献-)

## ✨ 核心特性

### 1.  🚀 简化类型系统

- 内置 7 种基本类型
- 完整支持泛型
- 原生支持 List/Set/Map 集合
- 支持多维数组
#### 1.1 🧩数据类型
##### 1.1.1 基本类型（原生类型）
| Type Keyword | Data Type        | Example                     |
|--------------|------------------|-----------------------------|
| short        | Short integer    | `short s = 100;`            |
| int          | Integer          | `int x = 42;`               |
| float        | Floating point   | `float pi = 3.14;`          |
| double       | Double           | `double d = 9.99;`          |
| long         | Long integer     | `long big = 100;`           |
| boolean      | Boolean          | `boolean flag = true;`      |
| byte         | Byte             | `byte b = 0x1F;`            |
| Null         | null             | `null`                      |
| Date         | Date             | `2025-06-07`                |
| Date         | Date             | `2025-06-07 12:00:01`       |
##### 1.1.2复合类型
| Type Format               | Example                          |
|--------------------------|----------------------------------|
| Generic (Type<T>)         | `List<String> names;`            |
| Generic Multi (Type<K,V>) | `Map<String, Integer> scores;`   |
| List                      | `List<Double> prices;`           |
| Set                       | `Set<Employee> staff;`           |
| Array                     | `int[] numbers = {1,2,3};`       |
| Custom Class              | `MyClass obj = new MyClass();`   |
| Import Alias              | `import java.util.Date as JDate;`|

```jquick
// 基本类型
int counter = 0;
boolean enabled = true;

// 泛型集合
List<String> names = ["Alice", "Bob"];
Map<String, Integer> scores = {"数学":90, "英语":85};
```
### 1.2  🔗 动态规则引擎
- 运行时动态加载规则
- XML 配置化定义业务逻辑
- 规则热更新，无需重启应用
- 无缝 Java 互通(直接调用 Java 方法、兼容 Java 生态)
## 2.🏗️ 程序结构
```jquick
import com.example.pkg as pkgAlias;
int x = 10;
console.log(x);
```
### 2.1 📦 导入声明
| 组件 | 说明 | 示例 |
|------|------|------|
| qualified.name | 点分路径 | `java.lang.String` |
| as | 别名 | `import java.util.Date as JDate;` |
### 2.2 ⚙️ 变量声明
| 类型 | 示例 | 说明 |
|------|------|------|
| 基本类型 | `int x = 10;` | 支持类型推导 |
| 引用类型 | `String s = "hi";` | 可显式声明 |


```jquick
import List<java.lang.String> as StringList ;
StringList list=["A","B","C"];
```
###  ⚙️ 2.3表达式与运算符
| Operator Group | Operators    | Example                  | Desc                          |
|----------------|--------------|--------------------------|-------------------------------|
| Mul            | `*`          | 1*1                      | number * number               | 
| Div            | `/`          | 1/1                      | number / number               |
| Add            | `+`          | 1+1                      | number + number string+string |
| Sub            | `-`          | 1-1                      | number - number               |
| GT             | `>`          | 1>1                      | number > number               |
| GE             | `>=`         | 1>=1                     | number * number               |
| LT             | `<`          | 1<1                      | number < number               |
| LE             | `<=`         | 1<=1                     | number <= number              |
| NE             | `!=`         | 1 !=1                    | number != number              |
| EQ             | `==`         | 1==1                     | number == number              |
| AND            | `&&`         | true&& true              | boolean  && boolean           |
| OR             | \|\|      | true \|\| false              | boolean\|\| boolean           |
| PAREN          | (expression) | (a + b) * 2 > 10 && x != y | (expression)                  |
### 2.3.1 复杂表达式
```jquick
(a + b) * 2 > 10 && x != y
```
### 2.3.2 控制结构
#### 2.3.2.1 if 语句
```jquick
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
####  2.3.2.2 循环 语句
```jquick
        for (int i = 0; i < 10; i = i + 1) {
            for (int j = 0; j < 10; j = j + 1){
                if(j==2){
                    continue;
                }else{
                    console.log(i+","+j);                }
            }
        };
```
####  2.3.2.3 while循环 语句
```jquick
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
####  2.3.2.4 方法定义语句
```jquick
返回类型 def 函数名(参数类型:参数名, ...) {
    // 函数体
    return 返回值;
}
int def funtionName(int:a, int:b) {
    return a + b;
}
```
```jquick
import List<java.lang.String> as StringList ;
StringList def funtionName(StringList:a, int:b) {
    return a;
}
```
### ☕2.3.3 Java 方法调用
| Type           | Example                                 |
|----------------|-----------------------------------------|
| 静态函数调用         | `Math::max(1, 2)`                       |
| 构造函数调用         | `new ArrayList()`                       |
| 实例方法调用         | `list.add("item")`                      |
| 调用jquick自定义函数  | `this.doSomething()`                    |
| 调用jquick内置函数（） | `Builtin::方法名(参数类型1:参数1, 参数类型2:参数2...)` |
### 2.3.3.1 静态方法
使用方法:
  类名:: 方法名(参数类型1:参数1，参数类型2:参数2...)
```jquick
java.lang.Math::max(int:5, int:10); // 10
java.lang.String::format("Hello %s", "JQuick");
```
### 2.3.3.2 构造方法
使用方法:
  new 构造类(参数类型1:参数1，参数类型2:参数2...)
```jquick
new java.util.ArrayList();
new com.github.paohaijiao.model.JStudent(int:42);
```  
### 2.3.3.3实例方法
使用方法:
  对象名:: 方法名(参数类型1:参数1，参数类型2:参数2...)
  
```jquick
testObj.isEven(int:4); // true
str1.toUpperCase();
```
### 2.3.3.4内置方法(预留)
使用方法:
Builtin:: 方法名(参数类型1:参数1，参数类型2:参数2...)
```jquick
testObj.isEven(int:4); // true
str1.toUpperCase();
```

### 2.3.3.5 注释
```jquick
// single-line
```
```jquick
/*
  multi-line
*/
```
### 2.3.3.6 代码示例
```jquick
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
### 2.3.3.7保留关键字

| 类别 | 关键字 |
|------|--------|
| 基本类型 | `short`, `int`, `float`, `double`, `long`, `boolean`, `byte` |
| 控制流 | `if`, `else`, `for`, `while`, `return`, `break`, `continue` |
| 声明 | `def`, `import`, `as`, `new`, `var` |
| 字面量 | `true`, `false`, `null`, `this` |
| 内置 | `console`, `Builtin` |
### 标识符规则
```string
1.以英文字符开始
2.可以包含英文数字
3.大小写敏感
```
### 完整示例
1. 示例 1
```jquick
int def getSquare(int:a,int:b){
    return a*b;
}
int a=1;
int b=2;
int c=this.getSquare(int:a,int:b);
```
2. 示例 2
```jquick
java.util.HashMap<java.lang.String,java.lang.String>   def a(int:a,float:b) {
    java.lang.String str1 = new java.lang.String(java.lang.String:"Hello");
    console.log(str1);
    java.lang.String upperStr = str1.toUpperCase();
    console.log(upperStr);  
    java.lang.String subStr = str1.substring(int:1, int:3);
    console.log(subStr);  
    java.util.HashMap<java.lang.String,java.lang.String> result = new java.util.HashMap();  result.put(java.lang.String:"constructed1", java.lang.String:str1);  result.put(java.lang.String:"constructed2", java.lang.String:str1);
    result.put(java.lang.String:"uppercased", java.lang.String:upperStr);  result.put(java.lang.String:"substring", java.lang.String:subStr);
    return result;    
}
int c=1;
float d=8.1;
this.a(int:c,float:d);
```
3. 示例 3
```jquick
java.lang.String def a(int:a,float:b) {
        java.lang.String p=java.lang.String::format(java.lang.String:"Number: %d, String: %s",int: 42, java.lang.String:"test"); 
        return p;   
}
   int c=1;
   float d=8.1;
   this.a(int:c,float:d);
```
4. 示例 4
```jquick
import java.lang.String as type1; 
type1 def a(int:a,float:b) {
   type1 p=type1::format(type1:"Number: %d, String: %s",int: 42, type1:"test");
    return p;    
}
   int c=1;
   float d=8.1;
   this.a(int:c,float:d);
```

### 3.XML 配置场景
🏦 企业信用评分卡
JQuick Java 最典型场景：信用评分、风险决策、规则引擎。

| 一级维度 | 二级维度 | 评分细则         | 得分 |
|----------|----------|------------------|------|
| 经营状况 | 经营年限 | ≥10 年           | 3    |
| 经营状况 | 经营年限 | 3-5 年           | 1    |
| 经营状况 | 经营年限 | <3 年            | 0    |
| 财务状况 | 资产负债率 | ≤50%           | 4    |
| 财务状况 | 资产负债率 | 50%-70%        | 3    |
| 财务状况 | 资产负债率 | 70%-85%        | 1    |
| 财务状况 | 资产负债率 | >85%           | 0    |
| 财务状况 | 流动比率 | ≥2.0            | 3    |
| 财务状况 | 流动比率 | 1.5-2.0         | 2    |
| 财务状况 | 流动比率 | 1.0-1.5         | 1    |
| 财务状况 | 流动比率 | <1.0            | 0    |
| 履约记录 | 银行信贷 | 无逾期           | 4    |
| 履约记录 | 银行信贷 | 逾期 1-2 次已结清 | 2    |
| 履约记录 | 银行信贷 | 逾期≥3 次        | 0    ||
| 企业资质 | 信用评级 | AAA              | 2    |
| 企业资质 | 信用评级 | AA               | 1.5  |
| 企业资质 | 信用评级 | A                | 1    |
| 企业资质 | 信用评级 | BBB 及以下       | 0    |
| 风险管理 | 法律诉讼 | 无诉讼           | 3    |
| 风险管理 | 法律诉讼 | 已结案胜诉       | 2    |
| 风险管理 | 法律诉讼 | 未结诉讼         | 0    |

## 信用分构成
 - 经营状况
 - 财务状况
 - 履约记录
 - 企业资质
 - 风险管理
###  企业信用评分卡
```java
    @Test
    public void testCreditScore() throws IOException {
          JQuickJavaXmlParseFactory handler = new JQuickJavaXmlParseFactory();
          JQuickFactory factory = new JQuickXmlFactory(handler, "credit-score.xml");
          CreditScoreMapper mapper = factory.createApi(CreditScoreMapper.class);
          // 1. 各维度评分测试
          int scoreOperatingYears = mapper.scoreOperatingYears(12);
          System.out.println("经营年限得分: " + scoreOperatingYears);
          int scoreAnnualRevenue = mapper.scoreAnnualRevenue(8000);
          System.out.println("年营收得分: " + scoreAnnualRevenue);
          //...其他各维度小分以此类推
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

  // 各维度独立评分接口
  public int scoreOperatingYears(@Param("years") int years);

  public int scoreAnnualRevenue(@Param("revenue") double revenue);

  //其他子计算省略...
  // 总分计算公式
  public int calculateTotalScore(@Param("businessScore") int businessScore,
                                 @Param("financialScore") int financialScore,
                                 @Param("currentRatio") int currentRatio,
                                 @Param("complianceScore") int complianceScore,
                                 @Param("qualificationScore") int qualificationScore,
                                 @Param("riskScore") int riskScore
  );
}

```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE javas PUBLIC "-//PAOHAIJIAO//DTD API JAVA 1.0//EN"
        "classpath:paohaijiao/dtd/Jquick-java.dtd">
<javas namespace="com.github.paohaijiao.xml.CreditScoreMapper">
  <!-- 1. 经营状况评分（满分10分） -->
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
            // 权重配置（百分比）
            // 经营状况: 25%, 财务状况: 25%, 履约记录: 20%, 企业资质: 15%, 风险管理: 15%
            // 各维度满分: 经营状况10分, 财务状况10分, 履约记录8分, 企业资质6分, 风险管理6分
            int baseScore = 60;
            // 应用权重计算各维度实际得分
            // 经营状况权重 25% → 满分10分 × 25% = 2.5分，实际得分按比例计算
            int weightedBusinessScore = (businessScore * 25) / 10;
            // 财务状况权重 25% → 满分10分 × 25% = 2.5分
            int weightedFinancialScore = (financialScore * 25) / 10;
            // 履约记录权重 20% → 满分8分 × 20% = 1.6分
            int weightedComplianceScore = (complianceScore * 20) / 8;
            // 企业资质权重 15% → 满分6分 × 15% = 0.9分
            int weightedQualificationScore = (qualificationScore * 15) / 6;
            // 风险管理权重 15% → 满分6分 × 15% = 0.9分
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

# **捐献 ☕**

感谢您使用这个开源项目！它完全免费并将持续维护，但开发者确实需要您的支持。

---

## **如何支持我们**

1. **请我喝杯咖啡**  
   果这个项目为您节省了时间或金钱，请考虑通过小额捐赠支持我。

2. **您的捐赠用途**
- 维持项目运行的服务器成本.
- 开发新功能以提供更多价值.
- 优化文档以提升用户体验.

3. **每一分都很重要**  
   即使是1分钱的捐赠也能激励我熬夜调试！


## **为什么捐赠?**
✔️ 保持项目永远免费且无广告.  
✔️ 支持及时响应问题和社区咨询.  
✔️ 实现计划中的未来功能.

感谢您成为让开源世界更美好的伙伴！

--- 

### **补充说明**
- 本项目和产品维护.
- 您的支持确保其可持续性和成长 .  
---

## **🌟 立即支持**
赞助时欢迎通过 [email](mailto:goudingcheng@gmail.com) 留言。您的名字将被列入项目README文件的 **"特别感谢"** 名单中！
![Ali Pay](./src/main/resources/pay/alipay.jpg)
![Wechat Pay](./src/main/resources/pay/wechat.jpg)

---