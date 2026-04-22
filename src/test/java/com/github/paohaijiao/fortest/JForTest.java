package com.github.paohaijiao.fortest;

import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.scope.JQuickJavaVariableContext;
import com.github.paohaijiao.support.JQuickJavaTypeReference;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;
import java.util.Stack;

public class JForTest {

    @Test
    public void for1() throws IOException {
        String rule=" " +
                "for (int i = 0; i < 10; i = i + 1) {\n" +
                "            if(i==2){\n" +
                "                break;\n" +
                "            }else{\n" +
                "                console.log(i);\n" +
                "            }\n" +
                "   };";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        Stack<JQuickJavaVariableContext> contextStack = new Stack<JQuickJavaVariableContext>();
        JQuickJavaVariableContext variableContext=new JQuickJavaVariableContext();
        variableContext.addVariable("radius", 5.0, JQuickJavaTypeReference.of(float.class));
        contextStack.push(variableContext);
        JQuickJavaParser.ForStatementContext tree = parser.forStatement();
        JContext params = new JContext();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,contextStack,lexer,tokens,parser);
//        parser.exitScope();
        tv.visit(tree);
    }
    @Test
    public void for2() throws IOException {
        String rule=" " +
                "for (int i = 0; i < 10; i = i + 1) {\n" +
                "            if(i==2){\n" +
                "                continue;\n" +
                "            }else{\n" +
                "                console.log(i);\n" +
                "            }\n" +
                "   };";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ForStatementContext tree = parser.forStatement();
        JContext params = new JContext();
        Stack<JQuickJavaVariableContext> contextStack = new Stack<JQuickJavaVariableContext>();
        JQuickJavaVariableContext variableContext=new JQuickJavaVariableContext();
        variableContext.addVariable("radius", 5.0, JQuickJavaTypeReference.of(float.class));
        contextStack.push(variableContext);
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,contextStack,lexer,tokens,parser);
        tv.visit(tree);
    }
    @Test
    public void for3() throws IOException {
        String rule="        for (int i = 0; i < 10; i = i + 1) {\n" +
                "            for (int j = 0; j < 10; j = j + 1){\n" +
                "                if(j==2){\n" +
                "                    continue;\n" +
                "                }else{\n" +
                "                    console.log(i+\",\"+j);" +
                "                }\n" +
                "            }\n" +
                "        };";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ForStatementContext tree = parser.forStatement();
        JContext params = new JContext();
        Stack<JQuickJavaVariableContext> contextStack = new Stack<JQuickJavaVariableContext>();
        JQuickJavaVariableContext variableContext=new JQuickJavaVariableContext();
        variableContext.addVariable("radius", 5.0, JQuickJavaTypeReference.of(float.class));
        contextStack.push(variableContext);
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(params,contextStack,lexer,tokens,parser);
        tv.visit(tree);
    }
}
