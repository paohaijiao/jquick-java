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
package com.github.paohaijiao.control4;

import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;

/**
 * packageName com.github.paohaijiao.control
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/4/25
 */
public class JOtherTest {
    @Test
    public void controlStatement_expressionStatement() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("1-(8*(6-2));"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ExpressionStatementContext tree = parser.expressionStatement();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void controlStatement_continueStatement() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("continue;"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ContinueStatementContext tree = parser.continueStatement();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void controlStatement_breakStatement() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("break;"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.BreakStatementContext tree = parser.breakStatement();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void controlStatement_returnStatement() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("return 2;"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ReturnStatementContext tree = parser.returnStatement();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void controlStatement_ifStatement() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("if(a==2){" +
                "return 2;}"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ControlStatementContext tree = parser.controlStatement();
        parser.declareVar("a",2);
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void controlStatement_elseIfConExpression() throws IOException {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("if (a==2){\n" +
                    "return 2;\n" +
                "}else if (a==3){\n" +
                    "return 1;\n" +
                "}else {\n" +
                    "return 4;\n" +
                "}"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ControlStatementContext tree = parser.controlStatement();
        parser.declareVar("a",3);
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void controlStatement_elseAction() throws IOException {
        String lexerStr="if (a==2){\n" +
                "return 2;\n" +
                "}else if (a==3){\n" +
                "return 1;\n" +
                "}else {\n" +
                "return 4;\n" +
                "}";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        System.out.println(lexerStr);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ControlStatementContext tree = parser.controlStatement();
        parser.declareVar("a",-3);
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
}
