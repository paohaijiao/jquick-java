<p align="center">
  <img src="src/main/resources/static/jquick-logo.svg" width="240" alt="JQuick Java logo" />
</p>

<h1 align="center">JQuick Java</h1>

<p align="center">
  <a href="https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-java"><img src="https://img.shields.io/maven-central/v/io.github.paohaijiao/jquick-java.svg?style=flat-square&label=Maven%20Central" alt="Maven Central" /></a>
  <a href="https://www.apache.org/licenses/LICENSE-2.0"><img src="https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat-square" alt="License" /></a>
  <a href="https://plugins.jetbrains.com/search?search=JQuick%20Language%20Support"><img src="https://img.shields.io/badge/IDEA%20Plugin-JQuick%20Language%20Support-blue.svg?style=flat-square&logo=intellij-idea" alt="IDEA Plugin" /></a>
  <a href="https://github.com/akullpp/awesome-java"><img src="https://img.shields.io/badge/Awesome%20Java-Miscellaneous-ff69b4.svg?style=flat-square" alt="Awesome Java" /></a>
</p>

<p align="center">
  <b>English</b> | <a href="./README-CN.md">Chinese</a>
</p>

---



## Introduction

**JQuick Java is a lightweight Java-like scripting language for rule engines, driven by declarative configuration.**

It combines the type safety of Java with the flexibility of dynamic languages, and supports **loading, parsing and executing business rules at runtime**. Rules are declared as XML documents or inline scripts and stay fully decoupled from business code, so adjusting a metric, a weight or a threshold is as simple as editing configuration. No restart, no redeployment.

JQuick Java targets enterprise legacy environments: it runs on **Java 8+** and can be embedded into existing systems without pulling in heavy third-party dependencies.

The project has been listed in [Awesome Java](https://github.com/akullpp/awesome-java), under the **Miscellaneous** section.

Typical scenarios:

- Enterprise credit rating and risk decision
- Complex business rules and data validation
- Process orchestration and scoring computation
- Declarative low-code platforms

> Note: JQuick Java is **not** a security sandbox. Rules can reach arbitrary Java classes, therefore scripts from untrusted sources must never be executed. The built-in invocation guard blocks only a small set of known dangerous calls by default; configure a white list in production.

---

## Features

- **Java-like script engine**: parse and execute at runtime, static typing with dynamic flexibility
- **XML rule proxy**: declare rules through an interface plus XML, the dynamic proxy is generated automatically
- **Inline rules**: register rules directly from Java code, fully equivalent to XML
- **Seamless Java interop**: static methods, constructors, instance methods, static field objects, `this` custom functions and `Builtin::` built-in functions
- **ASM powered invocation chain**: reflection and runtime bytecode invoker implementations, with concurrent invoker caches
- **Declarative and configuration driven**: metrics, weights and thresholds live in XML configuration, not in code
- **Invocation guard**: class-level and method-level black and white lists, blocking dangerous system calls by default
- **Built-in language server**: LSP over stdio, with incremental document sync, completion, hover, go-to-definition, document symbols and diagnostics
- **Lightweight**: Java 8+, only a handful of small JQuick ecosystem jars, no heavy runtime dependency

---

## IDEA Plugin: JQuick Language Support

JQuick Java comes with a companion IntelliJ IDEA plugin named **JQuick Language Support**. Open `Settings → Plugins → Marketplace`, search for **JQuick Language Support**, click install and restart the IDE. No manual download or local installation is needed. The plugin is also available on the JetBrains Marketplace website.

The plugin provides first-class editing support for `.jquick` rule scripts:

| Capability | Description |
|------------|-------------|
| File type and language | Registers the `JQuick` language and the `.jquick` file extension |
| Syntax highlighting | Keywords, literals, types and operators, with configurable color settings |
| Code completion | Context-aware completion, triggered by `.` and `:` |
| Quick documentation | Type and signature information for the symbol under the caret |
| Go to declaration | Jump from a call site to the corresponding declaration |
| Structure view | Outline of functions and variables in the current script |
| Real-time diagnostics | Parser errors and warnings annotated while you type |
| Language server integration | Connects to the built-in language server of the engine |

---

## Quick Start

### Maven dependency

```xml
<dependency>
    <groupId>io.github.paohaijiao</groupId>
    <artifactId>jquick-java</artifactId>
    <version>2.6.0</version>
</dependency>
```

### Runnable demo

The program below is complete and runnable: it declares a function in JQuick script and calls it through `this`.

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
        System.out.println("execute result: " + result); // "3"
    }
}
```

Compile the project with `mvn compile` and run the class from your IDE or command line.

---

## Syntax Reference

### Reserved words

`if` `else if` `else` `for` `while` `break` `continue` `return` `def` `this` `import` `as` `new` `null` `true` `false` `short` `int` `float` `double` `long` `boolean` `byte` `List` `Set` `Map` `Builtin` `console.log(` `@` `.class`

Two of them are single tokens that must be written exactly as shown: `else if` (with the space inside) and `console.log(` (with the opening parenthesis attached).

### Literals

| Kind | Example | Description |
|------|---------|-------------|
| Integer | `1` `3` | parsed as a number |
| Floating point | `1.5` `3.8` `10.0` | decimal precision is controlled by `JQuickJavaConfig.scale` (default `2`) |
| String | `"helloworld"` `'hello'` | both double quotes and single quotes are accepted |
| Boolean | `true` `false` | |
| Null | `null` | |
| Date | `2025-06-07` | date literal |
| Date time | `2025-06-07 12:00:01` | date time literal |
| List | `[1,2,3,4,5,6]` | list literal |
| Map | `{"user":"mike","active":true}` | map literal, entries are written as `key:value` |
| Class literal | `com.github.paohaijiao.literal1.JLiteral1Test.class` | resolves to a `Class` object |
| Context variable | `${type}` | resolved by name from the execution context |

### Types and variable declaration

| Type | Example |
|------|---------|
| Primitive types | `short` `int` `float` `double` `long` `boolean` `byte` |
| Reference type (fully qualified) | `java.lang.String a = "paohaijiao"` |
| Collection generics | `List<java.lang.String> a = ["paohaijiao"]` |
| Map generics | `Map<java.lang.String,java.lang.Object> a = {"companyName":"TechCorp Inc."}` |
| Generic reference type | `java.util.ArrayList<java.lang.Integer>` |
| Array | `java.lang.String[] a = ["paohaijiao"]` |
| Import alias | `type1 a = "paohaijiao"` |

Declaration syntax is `type name = expression`:

- the leading type is **optional**, but the `=` sign and the initializer are **mandatory** — a bare declaration such as `int a;` is not valid;
- the trailing semicolon of a top-level declaration may be omitted;
- nested literals are supported, for example `Map<java.lang.String,java.lang.Object> a={"topProducts":["ProductA","ProductB"],"companyName":"TechCorp Inc."}`.

### Import declaration

```jquick
import java.lang.String as a ;
import List<java.lang.String> as c ;
```

`import` always requires an `as` alias. The alias can then be used as a function return type, a variable type, a static call class name and an argument type:

```jquick
import java.lang.String as type1;
type1 def a(int:a,float:b) {
    type1 p = type1::format(type1:"Number: %d, String: %s", int:42, type1:"test");
    return p;
}
```

### Operators and expressions

| Category | Operators |
|----------|-----------|
| Arithmetic | `+` `-` `*` `/` |
| String concatenation | `+` |
| Comparison | `>` `>=` `<` `<=` `==` `!=` |
| Logical | `&&` `||` |
| Unary | prefix `+` `-` |
| Grouping | `(` `)` |

Precedence order: `expression → logical → comparison → additive → multiplicative → unary → primary`.

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

Not supported: the `%` operator, the unary `!` operator, and the `and` / `or` keywords. Use `&&` and `||` instead.

### Control structures

| Structure | Syntax |
|-----------|--------|
| Condition | `if (condition) { ... }` |
| Multiple branches | `if (...) { ... } else if (...) { ... } else { ... }` |
| Counting loop | `for (int i = 0; i < 10; i = i + 1) { ... }` |
| Conditional loop | `while (condition) { ... }` |
| Loop control | `break;` / `continue;` |
| Return | `return expression;` |
| Print | `console.log(expression);` |

Every block must be wrapped in braces. The `for` statement only supports the C-style three-part form; a for-each form is not available.

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
            console.log("current a:" + a);
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

### Function definition

```jquick
returnType def functionName(type:paramName, ...) {
    ...
    return expression;
}
```

- the return type is **mandatory**; use `void def name(...)` when nothing is returned;
- the return type may be a primitive type, a fully qualified class name, a collection generic or an import alias;
- a function is invoked as `this.functionName(args)`; a bare name call such as `getSquare(1,2)` is not part of the grammar;
- functions may call each other, for example `this.scoreReportTime(eventType, reportMinutes)`.

```jquick
int def getSquare(int:a,int:b) {
    return a*b;
}
int a=1;
int b=2;
int c=this.getSquare(int:a,int:b);
```

### Method invocation forms

| Form | Syntax | Real example |
|------|--------|--------------|
| Static method | `ClassName::methodName(args)` | `java.lang.Math::max(int:5, int:10);` |
| Constructor | `new ClassName(args)` | `new java.util.ArrayList();` |
| Instance method | `object.methodName(args)` | `testObj.isEven(int:4);` |
| Static field object method | `ClassName@field.method(args)` | `java.lang.System@out.println(java.lang.String:"hello");` |
| Custom function | `this.methodName(args)` | `this.getSquare(int:a,int:b);` |
| Built-in function | `Builtin::methodName(args)` | `Builtin::sum(int:1,int:2,int:3,int:4,int:5,int:6);` |

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

### Argument forms

| Form | Example |
|------|---------|
| Typed argument | `java.lang.Math::max(int:5, int:10);` |
| Untyped expression | `this.scoreReportTime(eventType, reportMinutes);` |

Both forms are valid. A typed argument is written as `type:expression` and lets the engine match the target signature exactly; a plain expression is resolved by its runtime type.

### Output and debugging

`console.log(expression);` evaluates the expression and prints its `toString()` result. String concatenation uses `+`:

```jquick
console.log(1);
console.log(i+","+j);
console.log("current a:"+a);
```

`${name}` is a standalone literal that reads a variable from the execution context by name, and can be used anywhere an expression is expected. The script below evaluates to the value of the context variable `type`:

```jquick
${type}
```

Note that `console.log` itself performs no interpolation; combine it with `+` when a value has to be embedded into a message.

### Built-in functions

Built-in functions are called as `Builtin::methodName(args)` without any import:

```jquick
Builtin::sum(int:1,int:2,int:3,int:4,int:5,int:6);
```

The function library itself is delivered by the [jquick-transform-function](https://github.com/paohaijiao/jquick-transform-function) module, and `Builtin::sum(...)` is the one used by the test suite in this repository; other built-in functions follow that module's documentation.

---

## Java API and XML Rules

### XML rule file

Rules are declared in an XML file that uses the bundled DTD:

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

| Element or attribute | Required | Meaning |
|----------------------|----------|---------|
| `javas` | yes | root element holding all rule methods |
| `javas@namespace` | yes | fully qualified name of the API interface; a mismatch fails fast |
| `java` | yes | one rule method |
| `java@name` | yes | name of the interface method, must match exactly |
| `java@returnClass` | yes | metadata only, the actual return type comes from the interface method signature |
| `java` body | yes | the JQuick function definition, usually wrapped in `CDATA` |

The DTD also allows `if(test)`, `foreach(collection,item,open,close,separator)` and `choose/when(test)/otherwise` elements to be nested inside `java`; the rule files shipped with this repository declare their logic directly inside `CDATA`.

### Interface binding

The interface is bound to the XML file through `namespace` plus the method name, and arguments are bound by `@Param`:

```java
import com.github.paohaijiao.xml.param.Param;

public interface UserMapper {

    HashMap<String,String> all();

    int sum(@Param("a") int a, @Param("b") int b);

    int mul(@Param("a") int a, @Param("b") int b);
}
```

Without `@Param` the binding falls back to the reflective parameter name, so keeping the annotation is recommended.

### Unified entry point

| API | Description |
|-----|-------------|
| `JQuickJava.create()` | creates the default entry point |
| `importPackage(qualifiedName, alias)` | declares an import with an explicit alias |
| `importPackage(qualifiedName)` / `importPackages(qualifiedNames)` | declares imports with an auto-derived alias |
| `constant(name, value)` / `variable(name, value)` / `variables(Map)` | initializes constants and context variables |
| `env(name, value)` / `envs(Map)` | initializes runtime environment entries |
| `init(statement)` / `init(statements)` | appends script level initialization statements |
| `rule(methodName, functionDefinition)` | registers one inline rule, equivalent to a `java` element |
| `rule(methodName, returnClass, functionDefinition)` / `rules(Map)` | inline rule registration with metadata, or in batch |
| `execute(scriptBody)` | executes a JQuick script directly |
| `buildScript(scriptBody)` | assembles imports, initialization statements and the script body |
| `createApi(apiInterface)` | builds a proxy backed by inline rules only |
| `createApi(apiInterface, xmlPath)` | builds a proxy backed by XML rules plus inline rules |
| `getContext()` / `getEnvironment()` / `buildRuntimeEnvironment()` / `handler()` | exposes the context, environment and XML handler |

### Inline rules and XML merge

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

When both an XML file and inline rules are supplied, the inline rule wins for the same method name:

```java
UserMapper userApi = JQuickJava.create()
        .rule("mul", "int def mul(int:a,int:b){ return a*b; }")
        .createApi(UserMapper.class, "jquick-java.xml");
```

---

## Documentation

- `src/test/java` — executable examples for every feature, for example `JQuickJavaTest`, `JStaticMethodInvocationTest`, `JInstanceMethodInvocationTest`, `JConstructorMethodInvocationTest`, `JLiteral1Test`, `JLogicalTest`, `JForTest`, `JImportTest`
- `src/test/resources` — real rule files: `jquick-java.xml`, `rules.xml`, `scoring-rules.xml`, `credit-score.xml`
- `src/main/resources/paohaijiao/dtd/Jquick-java.dtd` — the XML rule grammar
- `docs/visitor-refactor-lsp/README.md` — language server and editor integration notes
- [JQuick ecosystem repositories](https://github.com/paohaijiao) — `jquick-asm`, `jquick-xmlProxy`, `jquick-transform-function` and more
- [Maven Central](https://central.sonatype.com/artifact/io.github.paohaijiao/jquick-java) — released artifacts and version history

---

## Contributing

Contributions of any kind are welcome:

- Open an [issue](https://github.com/paohaijiao/jquick-java/issues) to report a bug or ask a question
- Send a pull request to improve the parser, the execution chain, the XML proxy or the documentation
- Run `mvn test` before submitting, so the existing specs keep passing
- Star or fork the repository, and help other users get started with JQuick Java

---

## License

Released under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

Project home: <https://github.com/paohaijiao/jquick-java>
