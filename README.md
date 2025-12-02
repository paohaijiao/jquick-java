# JQuick Java 语法参考手册
简体中文 | [EN](./README-EN.md)
## 项目进度
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Version](https://img.shields.io/badge/version-1.0.0-brightgreen.svg)](CHANGELOG.md)
[![Build Status](https://img.shields.io/github/actions/workflow/status/paohaijiao/jquick-java/build.yml?branch=main)](https://github.com/username/jquick-java/actions)
[![Stars](https://img.shields.io/github/stars/paohaijiao/jquick-java.svg?style=social)](https://github.com/paohaijiao/jquick-java)
[![Issues](https://img.shields.io/github/issues/paohaijiao/jquick-java.svg)](https://github.com/paohaijiao/jquick-java/issues)
## 概述
```jquick  
    JQuick java 是一种轻量级类 Java 脚本语言，专为简洁高效的编程而设计。它结合了 Java 的类型系统
和 JavaScript 的灵活性，支持常用的java方法调用以及逻辑嵌套,非常适合快速原型开发、教学和小型项目。
```
## 目录
- [概述](#概述)
- [核心特性](#核心特性)
    - [1. 简化类型系统](#1-简化类型系统)
- [程序结构](#程序结构)
    - [导入声明](#导入声明)
    - [变量声明](#变量声明)
- [数据类型](#数据类型)
    - [基本类型（simpleType）](#基本类型-simpletype)
    - [复合类型](#复合类型)
- [表达式](#表达式)
    - [运算符分组](#运算符分组)
    - [示例代码](#示例代码)
- [控制结构](#控制结构)
    - [if 语句](#if-语句)
    - [循环语句](#循环语句)
        - [for 循环](#for-循环)
        - [while 循环](#while-循环)
    - [方法定义](#方法定义)
- [调用方式](#调用方式)
    - [静态方法](#静态方法)
    - [构造方法](#构造方法)
    - [实例方法](#实例方法)
- [输出](#输出)
- [注释](#注释)
- [代码示例](#代码示例)
    - [示例 1](#示例-1)
    - [示例 2](#示例-2)
    - [示例 3](#示例-3)
    - [示例 4](#示例-4)
- [附录](#附录)
    - [保留关键字](#保留关键字)
    - [标识符规则](#标识符规则)
- [完整示例](#完整示例)
    1. [示例 1](#示例-1-1)
    2. [示例 2](#示例-2-1)
    3. [示例 3](#示例-3-1)
    4. [示例 4](#示例-4-1)

## 核心特性
### 1. 简化类型系统
- **7 种基本类型**内置支持
- **完整的泛型**支持
- **灵活的集合**（List/Set/Map）
- **多维数组**

```jquick  
// 基本类型  
int counter = 0;  
boolean enabled = true;  

// 泛型集合  
List<String> names = ["Alice", "Bob"];  
Map<String, Integer> scores = {"数学":90, "英语":85};  
```

## 程序结构
```jquick
import com.example.pkg as pkgAlias;
int x = 10;
console.log(x);
```
### 导入声明

| Component      | Description                                 |
|----------------|---------------------------------------------|
| qualified.name | dot-separated path (e.g., java.lang.String) |
| as             | alias name                                  |
### 变量声明

| Keyword    | Example            | Description                     |
|------------|--------------------|---------------------------------|
| simpleType | `int x = 10;`      | Type inferred                   |
| TypeName   | `String s = "hi";` | Explicit type (optional)        |
### 数据类型
### 基本类型（原生类型）
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
### 复合类型
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
import List<java.lang.String> as StringList ;
StringList list=["A","B","C"];
```
###  表达式
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
#### 样例
```jquick
(a + b) * 2 > 10 && x != y
```
### 控制结构
#### if 语句
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
#### 循环 语句
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
#### 方法定义
```jquick
类型 function 方法名(参数类型:参数名或参数值)
int function funtionName(int:a, int:b) {
    return a + b;
}
```
```jquick
import List<java.lang.String> as StringList ;
StringList function funtionName(StringList:a, int:b) {
    return a;
}
```
### 如何调用java 方法
| Type                    | Example                     |
|-------------------------|-----------------------------|
| 静态函数调用                  | `Math::max(1, 2)`           |
| 构造函数调用                  | `new ArrayList()`           |
| 实例方法调用                  | `list.add("item")`          |
| 调用jquick自定义函数           | `this.doSomething()`        |
### 静态方法
1. sample
```jquick
java.lang.Math::max(int:5, int:10);
output:10
```
2. sample
```jquick
java.lang.Math::pow(double:2, double:3);
output:8.0
```
3. sample
```jquick
java.lang.String::valueOf(int:123);
output:123
```
4. sample
```jquick
java.util.Objects::toString(java.lang.String:null);
output:null
```
5. sample
```jquick
java.lang.String::format(java.lang.String:"Number: %d, String: %s",int: 42, java.lang.String:"test");
output:Number: 42, String: test
```
6. sample
```jquick
java.lang.System::currentTimeMillis();
output:1754713207541
```
7. sample
```jquick
java.lang.String::join(java.lang.CharSequence:",", java.lang.CharSequence:"a",java.lang.CharSequence: "b", java.lang.CharSequence:"c");
output:a,b,c
```
8. sample
```jquick
com.github.paohaijiao.service.JService::sum(int:1,int:2);
   output:3
```
### 构造方法
1. sample
```jquick
new com.github.paohaijiao.service.JService();
```
2. sample
```jquick
new com.github.paohaijiao.model.JStudent(int:42, float:3.14, boolean:true);
```
3. sample
```jquick
new com.github.paohaijiao.model.JStudent(java.lang.String:"test string");
```
4. sample
```jquick
new com.github.paohaijiao.model.JStudent(List<java.lang.Integer>:listVar);
```
5. sample
```jquick
new com.github.paohaijiao.model.JStudent(java.lang.String:"test", java.lang.Integer:123, java.lang.Boolean:true, List<java.lang.Integer>:listVar);
```
6. sample
```jquick
new java.util.ArrayList();
```
7. sample
```jquick
new com.github.paohaijiao.model.JStudent(java.lang.String:"a", java.lang.String:"b", java.lang.String:"c");
```
### 实例方法
1. sample
```jquick
testObj.isEven(int:4);
output: true
```
2. sample
```jquick
testObj.noReturn();
```
3. sample
```jquick
testObj.addToList(java.util.ArrayList<java.lang.Integer>:listVar, java.lang.Integer:4);
```
4. sample
```jquick
testObj.methodWithMixedArgs(java.lang.String:"Test", int:42, boolean:true);
```
5. sample
```jquick
testObj.methodWithVarArgs(java.lang.String:"a", java.lang.String:"b", java.lang.String:"c");
```

### 输出
```jquick
console.log("Result: " + result);
```
### 注释
```jquick
// single-line
```
```jquick
/*
  multi-line
*/
```
### 代码示例
```jquick
java.lang.String function a(int:a,float:b) {
    java.lang.String p=java.lang.String::format(java.lang.String:"Number: %d, String: %s",int: 42, java.lang.String:"test"); 
    return p;    
}
    int c=1;
    float d=8.1;
    this.a(int:c,float:d);
```
```jquick
java.util.HashMap<java.lang.String,java.lang.String> function a(int:a,float:b) {
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
### 附录
```string 保留关键字
global, 
short, 
int, 
float, 
double, 
long, 
boolean, 
byte, 
new, 
var, 
return, 
function, 
null, 
this, 
import, 
continue, 
break, 
as, 
if, 
else, 
for, 
while,
true, 
false, 
null
```
### 标识符规则
```string
1.starts with letter/underscore
2.may contain letters, digits, underscores
3.case-sensitive
```
### 完整示例
1. sample 1
```jquick
int function getSquare(int:a,int:b){
    return a*b;
}
int a=1;
int b=2;
int c=this.getSquare(int:a,int:b);
```
2. sample 2
```jquick
java.util.HashMap<java.lang.String,java.lang.String>   function a(int:a,float:b) {
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
3. sample 3
```jquick
java.lang.String function a(int:a,float:b) {
        java.lang.String p=java.lang.String::format(java.lang.String:"Number: %d, String: %s",int: 42, java.lang.String:"test"); 
        return p;   
}
   int c=1;
   float d=8.1;
   this.a(int:c,float:d);
```
4. sample 4
```jquick
import java.lang.String as type1; 
type1 function a(int:a,float:b) {
   type1 p=type1::format(type1:"Number: %d, String: %s",int: 42, type1:"test");
    return p;    
}
   int c=1;
   float d=8.1;
   this.a(int:c,float:d);
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