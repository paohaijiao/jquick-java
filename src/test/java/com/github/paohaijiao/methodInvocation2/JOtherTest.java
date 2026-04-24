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
package com.github.paohaijiao.methodInvocation2;

import com.github.paohaijiao.model.JQuickJavaImportContainerModel;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class JOtherTest {


    @Test
    public void accessObjectName() {
        String rule = "q";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.AccessObjectNameContext tree = parser.accessObjectName();
        JContext context=new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(context,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void accessStaticVariable() {
        String rule = "com.github.paohaijiao.extract.model.JStudent@hello";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.AccessStaticVariableContext tree = parser.accessStaticVariable();
        JContext context=new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(context,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void methodName() {
        String rule = "methoda";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.MethodNameContext tree = parser.methodName();
        JContext context=new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(context,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void instanceName() {
        String rule = "a";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.InstanceNameContext tree = parser.instanceName();
        JContext context=new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(context,lexer,tokens,parser);
        parser.enterScope();
        parser.declareVar("a",10);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void testConstructorWithNoArguments() {
        String rule = "int:1,float:2";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ArgumentListContext tree = parser.argumentList();
        JContext context=new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(context,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void paramType() throws IOException {
        String rule = "short";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ParamTypeContext tree = parser.paramType();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void int1() throws IOException {
        String rule = "int";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ParamTypeContext tree = parser.paramType();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void  float1() throws IOException {
        String rule = "float";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ParamTypeContext tree = parser.paramType();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void  double1() throws IOException {
        String rule = "double";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ParamTypeContext tree = parser.paramType();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void  long1() throws IOException {
        String rule = "long";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ParamTypeContext tree = parser.paramType();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void  boolean1() throws IOException {
        String rule = "boolean";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ParamTypeContext tree = parser.paramType();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void  qualifiedName() throws IOException {
        String rule = "java.lang.String";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ParamTypeContext tree = parser.paramType();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void  functionVar() throws IOException {
        String rule = "a";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.FunctionVarContext tree = parser.functionVar();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void  param() throws IOException {
        String rule = "int:a";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ParamContext tree = parser.param();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void  parameterList() throws IOException {
        String rule = "int:a,float:b";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ParameterListContext tree = parser.parameterList();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void  functionDefinition() throws IOException {
        String rule = "int def name(int:a,float:b){int a=1;}";
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.FunctionDefinitionContext tree = parser.functionDefinition();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,lexer,tokens,parser);
        Object object = tv.visit(tree);
        System.out.println(object);
    }
    @Test
    public void  join() throws IOException, NoSuchMethodException, IllegalAccessException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle joinHandle = lookup.findStatic(
                String.class,
                "join",
                MethodType.methodType(
                        String.class,
                        CharSequence.class,
                        CharSequence[].class
                )
        );
        System.out.println(joinHandle.toString());
    }

}
