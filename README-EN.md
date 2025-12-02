# JQuickLang Syntax Reference
## Overview
JQuick Java is a lightweight Java-like scripting language designed for concise and efficient programming. It combines Java's type system with JavaScript's flexibility, making it ideal for rapid prototyping, education, and small projects.
# Table of Contents

- [Overview](#overview)
- [Core Features](#core-features)
    - [1. Simplified Type System](#1-simplified-type-system)
- [Program Structure](#program-structure)
    - [Import Declarations](#import-declarations)
    - [Variable Declarations](#variable-declarations)
- [Data Types](#data-types)
    - [Primitive Types (simpleType)](#primitive-types-simpletype)
    - [Composite Types](#composite-types)
- [Expressions](#expressions)
    - [Operator Groups](#operator-groups)
    - [Sample Code](#sample-code)
- [Control Structures](#control-structures)
    - [if statement](#if-statement)
    - [loop statement](#loop-statement)
        - [for loop](#for-loop)
        - [while loop](#while-loop)
    - [method definition](#method-definition)
- [Invocation Styles](#invocation-styles)
    - [Static Method](#static-method)
    - [Constructor Method](#constructor-method)
    - [Instance Method](#instance-method)
- [Output](#output)
- [Comments](#comments)
- [Code Samples](#code-samples)
    - [Sample 1](#sample-1)
    - [Sample 2](#sample-2)
    - [Sample 3](#sample-3)
    - [Sample 4](#sample-4)
- [Appendix](#appendix)
    - [String Preserve Keywords](#string-preserve-keyword)
    - [Identifier Rules](#identifier-rules)
- [Complete Examples](#complete-example)
    1. [Sample 1](#sample-1-1)
    2. [Sample 2](#sample-2-1)
    3. [Sample 3](#sample-3-1)
    4. [Sample 4](#sample-4-1)
## Core Features
### 1. Simplified Type System
- **7 Primitive types** built-in
- **Full generics** support
- **Flexible collections** (List/Set/Map)
- **dimensional arrays**

```jquick
// Primitive types
int counter = 0;
boolean enabled = true;

// Generic collections
List<String> names = ["Alice", "Bob"];
Map<String, Integer> scores = {"Math":90, "English":85};
```

## Program Structure
```jquick
import com.example.pkg as pkgAlias;
int x = 10;
console.log(x);
```
### Import Declarations

| Component      | Description                                 |
|----------------|---------------------------------------------|
| qualified.name | dot-separated path (e.g., java.lang.String) |
| as             | alias name                                  |
### Variable Declarations

| Keyword    | Example            | Description                     |
|------------|--------------------|---------------------------------|
| simpleType | `int x = 10;`      | Type inferred                   |
| TypeName   | `String s = "hi";` | Explicit type (optional)        |
### Data Types
### Primitive Types (simpleType)
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
### Composite Types
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
###  Expressions
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
| PAREN          | (expression) | (a + b) * 2 > 10 && x != y | boolean\|\| boolean           |
#### sample code
```jquick
(a + b) * 2 > 10 && x != y
```
### Control Structures
#### if statement
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
#### loop statement
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
#### method definition
```jquick
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
### invocation Styles
| Type             | Example                     |
|------------------|-----------------------------|
| Static Method    | `Math::max(1, 2)`           |
| Constructor      | `new ArrayList()`           |
| Instance Method  | `list.add("item")`          |
| This-context     | `this.doSomething()`        |
### Static Method
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
### Constructor Method
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
### Instance Method
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

### output
```jquick
console.log("Result: " + result);
```
### Comments
```jquick
// single-line
```
```jquick
/*
  multi-line
*/
```
### code sample 
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
### appendix
```string preserve keyword
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
### Identifier Rules
```string
1.starts with letter/underscore
2.may contain letters, digits, underscores
3.case-sensitive
```
### complete example
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


# **Generating Power with Love (and Caffeine) ☕**

Thank you for using this open-source project! It is completely free and will be maintained continuously, but the developers do need your support.

---

## **How You Can Help**

1. **Buy Me a Coffee**  
   If this project has saved you time or money, please consider supporting me with a small donation.

2. **Where Your Donation Goes**
  - Server costs to keep the project running.
  - Feature development to add more value.
  - Documentation optimization for a better user experience.

3. **Every Cent Counts**  
   Even a donation of just 1 cent motivates me to debug late into the night!



## **Why Donate?**
✔️ Keep the project **free and ad-free** forever.  
✔️ Support timely responses to issues and community inquiries.  
✔️ Enable planned features for the future.

Thank you for being a partner in making the open-source world better!

--- 

### **Additional Notes**
- The project is maintained with love and caffeine.
- Your support ensures its sustainability and growth.  
---

## **🌟 Support Now**
Feel free to leave a message via [email](mailto:goudingcheng@gmail.com) when sponsoring. Your name will be included in the **"Special Thanks"** list in the project's README file!
![OCBC Pay Now](./src/main/resources/pay/paynow.jpg)
![Touch n Go ](./src/main/resources/pay/tngGo.jpg)
---