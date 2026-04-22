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

import com.github.paohaijiao.JVariableContextBuilder;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.scope.VariableContext;
import com.github.paohaijiao.service.JService;
import com.github.paohaijiao.support.JTypeReference;
import com.github.paohaijiao.visitor.JQuickLangCommonVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * packageName PACKAGE_NAME
 *
 * @author Martin
 * @version 1.0.0
 * @className Junit
 * @date 2025/6/16
 * @description
 */
public class JInstanceMethodInvocationTest {

    public JContext setUp() {
        JService testInstance = new JService();
        JContext jContext=new JContext();
        jContext.put("testObj", testInstance);
        ArrayList<Integer> listVar = new ArrayList<Integer>() {}; // 匿名内部类保留类型信息
        listVar.addAll(Arrays.asList(1, 2, 3));
        jContext.put("listVar", listVar);
//        jContext.put("listVar", listVar);
        Map<String,String> map=new HashMap<>();
        map.put("key", "value");
        return jContext;
    }
    public Stack<VariableContext> mockData(){
        VariableContext variableContext=new VariableContext();
        List<Integer> listVar = new ArrayList<Integer>() {
        };
        listVar.addAll(Arrays.asList(1, 2, 3));
        Stack<VariableContext> contextStack = new Stack<VariableContext>();
        variableContext.addVariable("testObj", new JService(), JTypeReference.of(JService.class));
        variableContext.addVariable("listVar", listVar, JTypeReference.listOf(Integer.class));
        contextStack.add(variableContext);
        return contextStack;
    }
    @Test
    public void testSimpleInstanceMethodCall() {
        String rule = "com.github.paohaijiao.service.JService::sum(int:5, int:3);";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params =setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void testBooleanReturn() {
        String rule = "testObj.isEven(int:4);";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        VariableContext variableContext=new VariableContext();
        List<Integer> listVar = new ArrayList<Integer>() {
        };
        listVar.addAll(Arrays.asList(1, 2, 3));
        Stack<VariableContext> contextStack = new Stack<VariableContext>();
        variableContext.addVariable("testObj", new JService(), JTypeReference.of(JService.class));
        contextStack.add(variableContext);
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(new JContext(),contextStack,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void testVoidReturn() {
        String rule = "testObj.noReturn();";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params =setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,mockData(),lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void testCollectionArgument() {
        String rule = "testObj.addToList(java.util.ArrayList<java.lang.Integer>:listVar, java.lang.Integer:4);";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params =setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,mockData(),lexer,tokens,parser);
        System.out.println(tv.visit(tree));
    }
    @Test
    public  void testMixedArgumentTypes() {
        String code = "testObj.methodWithMixedArgs(java.lang.String:\"Test\", int:42, boolean:true);";
        System.out.println(code);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params =setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,mockData(),lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void testVarArgsMethod() {
        String code = "testObj.methodWithVarArgs(java.lang.String:\"a\", java.lang.String:\"b\", java.lang.String:\"c\");";
        System.out.println(code);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params =setUp();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,mockData(),lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }







    @Test
    public void varArgumentList() throws IOException {
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("a,b"));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.VarArgumentListContext tree = parser.varArgumentList();
//        JContext params = new JContext();
//        params.put("a","1");
//        params.put("b",Arrays.asList("a","b"));
//        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params);
//        Object object = tv.visit(tree);
//        System.out.println(object);
    }
    @Test
    public void argumentList() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("int:1,int:2"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ArgumentListContext tree = parser.argumentList();
        JContext params = new JContext();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void instanceName() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("listVar"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.InstanceNameContext tree = parser.instanceName();
        JContext params = new JContext();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params, JVariableContextBuilder.mockData(),lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }


}
