package com.github.paohaijiao.function;

import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

public class JArgumentListTest {

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
}
