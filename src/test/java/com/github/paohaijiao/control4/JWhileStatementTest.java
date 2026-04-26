package com.github.paohaijiao.control4;


import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;

public class JWhileStatementTest {
    @Test
    public void while1() throws IOException {
        String rule="    while(true){\n" +
                "            continue;\n" +
                "        }";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.WhileStatementContext tree = parser.whileStatement();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        tv.visit(tree);
    }
    @Test
    public void whilebreak() throws IOException {
        String rule="    while(true){\n" +
                "            break;\n" +
                "        }";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.WhileStatementContext tree = parser.whileStatement();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        tv.visit(tree);
    }
    @Test
    public void whileif() throws IOException {
        String rule=" while(true){\n" +
                "            for (int a = 0; a<10; a=a+1){\n" +
                "                if(a==2){\n" +
                "                    continue;\n" +
                "                }else{\n" +
                "                    console.log(\"当前的变量a:\"+a);\n" +
                "                }\n" +
                "            }\n" +
                "            break;\n" +
                "        }";
        System.out.println(rule);
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.WhileStatementContext tree = parser.whileStatement();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        tv.visit(tree);
    }
}
