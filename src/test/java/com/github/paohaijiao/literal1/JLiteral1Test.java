package com.github.paohaijiao.literal1;
/*
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
public class JLiteral1Test {
    @Test
    public void mapEntry() throws IOException {
        String lexerStr="\"key\":\"value\"";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MapEntryContext tree = parser.mapEntry();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void mapLiteral() throws IOException {
        String lexerStr="{\"user\":\"张三\",\"active\":true}";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MapLiteralContext tree = parser.mapLiteral();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void listLiteral() throws IOException {
        String lexerStr="[1,2,3,4,5,6]";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ListLiteralContext tree = parser.listLiteral();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void literal_string() throws IOException {
        String lexerStr="\"1234567\"";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void literal_number() throws IOException {
        String lexerStr="1";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void literal_date() throws IOException {
        String lexerStr="2026-01-01 12:12:13";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void literal_bool() throws IOException {
        String lexerStr="true";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void literal_null() throws IOException {
        String lexerStr="null";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void literal_identifier() throws IOException {
        String lexerStr="a";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void literal_variables() throws IOException {
        String lexerStr="${type}";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        params.put("type", "string");
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void literal_qualifiedName() throws IOException {
        String lexerStr="com.github.paohaijiao.literal1.JLiteral1Test.class";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.LiteralContext tree = parser.literal();
        JContext params = new JContext();
        params.put("type", "string");
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void literal_listLiteral() throws IOException {
        String lexerStr="[12,34,43]";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ListLiteralContext tree = parser.listLiteral();
        JContext params = new JContext();
        params.put("type", "string");
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void literal_mapLiteral() throws IOException {
        String lexerStr="{\"user\":\"张三\",\"active\":true}";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MapLiteralContext tree = parser.mapLiteral();
        JContext params = new JContext();
        params.put("type", "string");
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }






}
