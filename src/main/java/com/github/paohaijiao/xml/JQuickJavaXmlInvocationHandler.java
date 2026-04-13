package com.github.paohaijiao.xml;

import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickLangLexer;
import com.github.paohaijiao.parser.JQuickLangParser;
import com.github.paohaijiao.visitor.JQuickLangCommonVisitor;
import com.github.paohaijiao.xml.invocation.JQuickXmlInvocationHandler;
import com.google.gson.JsonObject;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.lang.reflect.Method;
import java.util.Stack;

public class JQuickJavaXmlInvocationHandler extends JQuickXmlInvocationHandler {

    private JConsole console=new JConsole();

    @Override
    protected Object loadResult(String lexerStr, JContext context, Method method, Object[] args) {
        JQuickLangLexer lexer = new JQuickLangLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickLangParser parser = new JQuickLangParser(tokens);
        JQuickLangParser.ArithmeticContext tree = parser.arithmetic();
        JContext params = new JContext();
        if(!context.isEmpty()){
            params.putAll(context);
        }
        JQuickLangCommonVisitor tv = new JQuickLangCommonVisitor(params,new Stack<>(),lexer,tokens,parser);
        Object object = tv.visit(tree);
        JsonObject jsonObject = new JsonObject();
        console.info("the result is "+object.toString());
        return object;
    }

}
