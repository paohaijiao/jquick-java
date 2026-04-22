package com.github.paohaijiao.function;/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (c) [2025-2099] Martin (goudingcheng@gmail.com)
 */

import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.service.JService;
import com.github.paohaijiao.visitor.JQuickLangCommonVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * packageName PACKAGE_NAME
 *
 * @author Martin
 * @version 1.0.0
 * @className Junit
 * @date 2025/6/16
 * @description
 */
public class JStaticMethodInvocationTest {

    public JContext setUp() {
        JService testInstance = new JService();
        JContext jContext = new JContext();
        jContext.put("testObj", testInstance);
        List<Integer> listVar = new ArrayList<Integer>() {
        };
        listVar.addAll(Arrays.asList(1, 8, 3));
        jContext.put("listVar", Arrays.asList(1, 8, 3));
//        jContext.put("listVar", listVar);
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        return jContext;
    }

    @Test
    public void testSimpleStaticMethodCall() {
        String rule = "java.lang.Math::max(int:5, int:10);";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object result = tv.visit(tree);
        System.out.println(result);
    }

    @Test
    public void testStaticMethodWithPrimitiveArguments() {
        String rule = "java.lang.Math::pow(double:2, double:3);";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object result = tv.visit(tree);
        System.out.println(result);
    }

    @Test
    public void testStaticMethodWithStringArgument() {
        String rule = "java.lang.String::valueOf(int:123);";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object result = tv.visit(tree);
        System.out.println(result);
    }

    @Test
    public void testStaticMethodWithCollectionArgument() {
//        java.util.List collections=null;
//        //String rule = "java.util.Collections::sort<java.util.List<java.lang.Integer>>(listVar);";
//        String rule = "java.util.Collections::sort(List<java.lang.Integer>:listVar);";
//        System.out.println(rule);
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
//        JContext params = setUp();
//        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
//        List<Integer> listVar = new ArrayList<Integer>() ;
//        listVar.add(5);
//        listVar.add(2);
//        listVar.add(3);
//        VariableContext variableTree=new VariableContext("root");
//        JTypeReference<List<Integer>> typeRef = JTypeReference.listOf(Integer.class);
//        variableTree.addVariable("listVar",listVar,typeRef);
//        tv.setCurrent(variableTree);
//        Object result = tv.visit(tree);
//        System.out.println(result);
    }

    @Test
    public void testStaticMethodWithNullArgument() {
        String rule = "java.util.Objects::toString(java.lang.String:null);";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object result = tv.visit(tree);
        System.out.println(result);
    }

    @Test
    public void testStaticMethodWithMixedArguments() {
        String tt=java.lang.String.format("Number: %d, String: %s",42,"Hello World");
        String rule = "java.lang.String::format(java.lang.String:\"Number: %d, String: %s\",int: 42, java.lang.String:\"test\");";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object result = tv.visit(tree);
        System.out.println(result);
        assertEquals("Number: 42, String: test", result);
    }

    @Test
    public void testStaticMethodWithNoArguments() {
        String rule = "java.lang.System::currentTimeMillis();";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object result = tv.visit(tree);
        System.out.println(result);
    }

    @Test
    public void testStaticMethodWithVarArgs() {
        String rule = "java.lang.String::join(java.lang.CharSequence:\",\", java.lang.CharSequence:\"a\",java.lang.CharSequence: \"b\", java.lang.CharSequence:\"c\");";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object result = tv.visit(tree);
        System.out.println(result);
    }

    @Test
    public void testStaticMethodReturningVoid() {
        String rule = "java.lang.System::gc();";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object result = tv.visit(tree);
        System.out.println(result);
    }

    @Test
    public void testStaticMethodFromCustomClass() {
        String rule = "com.github.paohaijiao.service.JService::sum(int:1,int:2);";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object result = tv.visit(tree);
        System.out.println(result);
    }
    @Test
    public void join() {
        String rule = "java.lang.String::join(java.lang.CharSequence:\",\",java.lang.CharSequence:\"1\",java.lang.CharSequence:\"22\",java.lang.CharSequence:\"33\")";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object result = tv.visit(tree);
        System.out.println(result);
    }
    @Test
    public void print() {
        String rule = "java.lang.System@out.println(java.lang.String:\"hahah\")";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object result = tv.visit(tree);
        System.out.println(result);
    }

}
