package com.github.paohaijiao.value;/*
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
import com.github.paohaijiao.visitor.JQuickLangCommonVisitor;
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
public class JValueTest {
    @Test
    public void string() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("\"helloworld\""));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.StringContext tree = parser.string();
        JContext params = new JContext();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void date1() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("2025-06-07 12:00:01"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.DateContext tree = parser.date();
        JContext params = new JContext();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }

    @Test
    public void date2() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("2025-06-07"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.DateContext tree = parser.date();
        JContext params = new JContext();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }

    @Test
    public void boolean1() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("false"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.BoolContext tree = parser.bool();
        JContext params = new JContext();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void null1() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("null"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }

    @Test
    public void Long() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("1"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void double1() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("1.5"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void float1() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("3.8"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void integer() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("3"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void short1() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("3"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void variables() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("${type}"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.VariablesContext tree = parser.variables();
        JContext params = new JContext();
        params.put("type", "string");
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
}
