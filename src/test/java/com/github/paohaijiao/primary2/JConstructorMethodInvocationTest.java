package com.github.paohaijiao.primary2;/*
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
import com.github.paohaijiao.extract.service.JService;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

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
public class JConstructorMethodInvocationTest {

    public JContext setUp() {
        JService testInstance = new JService();
        JContext jContext = new JContext();
        jContext.put("testObj", testInstance);
        List<Integer> listVar = new ArrayList<Integer>() {
        };
        listVar.addAll(Arrays.asList(1, 2, 3));
        jContext.put("listVar", Arrays.asList(1, 2, 3));
//        jContext.put("listVar", listVar);
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        return jContext;
    }

    @Test
    public void testSimpleConstructorCall() {
        String rule = "new com.github.paohaijiao.service.JService();";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }

    @Test
    public void testConstructorWithPrimitiveArguments() {
        String rule = "new com.github.paohaijiao.model.JStudent(int:42, float:3.14, boolean:true);";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }

    @Test
    public void testConstructorWithStringArgument() {
        String rule = "new com.github.paohaijiao.model.JStudent(java.lang.String:\"teststring\");";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }

    @Test
    public void testConstructorWithCollectionArgument() {
//        String rule = "new com.github.paohaijiao.model.JStudent(List<java.lang.Integer>:listVar);";
//        System.out.println(rule);
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
//        JContext params = setUp();
//        List<Integer> listVar = new ArrayList<Integer>() {
//        };
//        listVar.addAll(Arrays.asList(1, 2, 3));
//
//        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor( JVariableContextBuilder.mockData(),lexer,tokens,parser);
//        Object object = tv.visit(tree);
//        System.out.println(object);
    }




    @Test
    public void testConstructorWithMixedArguments() {
//        String rule = "new com.github.paohaijiao.model.JStudent(java.lang.String:\"test\", java.lang.Integer:123, java.lang.Boolean:true, List<java.lang.Integer>:listVar);";
//        System.out.println(rule);
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
//        JContext params = setUp();
//        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(JVariableContextBuilder.mockData(),lexer,tokens,parser);
//        Object object = tv.visit(tree);
//        System.out.println(object);
    }

    @Test
    public void testConstructorWithNoArguments() {
        String rule = "new java.util.ArrayList();";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
        assert (object instanceof ArrayList);
    }

    @Test
    public void testConstructorWithVarArgs() {
        String rule = "new com.github.paohaijiao.model.JStudent(java.lang.String:\"a\", java.lang.String:\"b\", java.lang.String:\"c\");";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodInvocationContext tree = parser.methodInvocation();
        JContext params = setUp();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }


}
