package com.github.paohaijiao.xml;

import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import com.github.paohaijiao.xml.invocation.JQuickXmlInvocationHandler;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.lang.reflect.Method;
import java.util.Stack;

public class JQuickJavaXmlInvocationHandler extends JQuickXmlInvocationHandler {

    private JConsole console=new JConsole();

    private JContext context=new JContext();

    private Stack statck=new Stack<>();

    public JQuickJavaXmlInvocationHandler(){

    }
    public JQuickJavaXmlInvocationHandler(JContext jcontext){
        context.putAll(jcontext);
    }
    public JQuickJavaXmlInvocationHandler(Stack statck){
        this.statck=statck;
    }
    public JQuickJavaXmlInvocationHandler(JContext jcontext,Stack statck){
        context.putAll(jcontext);
        this.statck=statck;
    }

    @Override
    protected Object loadResult(String lexerStr, JContext context, Method method, Object[] args) {
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.ProgramContext tree = parser.program();
        if(!context.isEmpty()){
            context.putAll(context);
        }
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(context,statck,lexer,tokens,parser);
        Object object = tv.visit(tree);
        console.info("the result is : "+object.toString());
        return object;
    }

}
