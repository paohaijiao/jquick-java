package com.github.paohaijiao.primary;/*
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
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;

/**
 * packageName PACKAGE_NAME
 *
 * @author Martin
 * @version 1.0.0
 * @className Junit
 * @date 2025/6/16
 * @description
 */
public class JPrimarylTest {
    @Test
    public void literal() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("1"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.PrimaryContext tree = parser.primary();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void IDENTIFIER() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("listVar"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.PrimaryContext tree = parser.primary();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params, JVariableContextBuilder.mockData(),lexer,tokens,parser);
        tv.getContext().put("listVar", 18);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void methodInvocation() throws IOException {
        String rule = "new com.github.paohaijiao.model.JStudent(java.lang.String:\"a\", java.lang.String:\"b\", java.lang.String:\"c\");";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.PrimaryContext tree = parser.primary();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void expression() throws IOException {
        String rule = "(3+2)";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.PrimaryContext tree = parser.primary();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void variableDecl() throws IOException {
        String rule = "int a=1;";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.PrimaryContext tree = parser.primary();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,JVariableContextBuilder.mockData(),lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }




}
