package com.github.paohaijiao.whileStatement;

import com.github.paohaijiao.executor.JQuickJavaWhileExecutor;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.scope.JQuickJavaVariableContext;
import com.github.paohaijiao.support.JQuickJavaTypeReference;
import org.junit.Test;

import java.io.IOException;
import java.util.Stack;

public class JWhileStatementTest {
    @Test
    public void while1() throws IOException {
        String rule="    while(true){\n" +
                "            continue;\n" +
                "        }";
        System.out.println(rule);
        Stack<JQuickJavaVariableContext> contextStack = new Stack<JQuickJavaVariableContext>();
        JQuickJavaVariableContext variableContext=new JQuickJavaVariableContext();
        variableContext.addVariable("radius", 5.0, JQuickJavaTypeReference.of(float.class));
        contextStack.push(variableContext);
        JContext jContext=new JContext();
        JQuickJavaWhileExecutor executor = new JQuickJavaWhileExecutor(jContext,contextStack);
        Object result=executor.execute(rule);
    }
    @Test
    public void whilebreak() throws IOException {
        String rule="    while(true){\n" +
                "            break;\n" +
                "        }";
        System.out.println(rule);
        Stack<JQuickJavaVariableContext> contextStack = new Stack<JQuickJavaVariableContext>();
        JQuickJavaVariableContext variableContext=new JQuickJavaVariableContext();
        variableContext.addVariable("radius", 5.0, JQuickJavaTypeReference.of(float.class));
        contextStack.push(variableContext);
        JContext jContext=new JContext();
        JQuickJavaWhileExecutor executor = new JQuickJavaWhileExecutor(jContext,contextStack);
        Object result=executor.execute(rule);
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
        Stack<JQuickJavaVariableContext> contextStack = new Stack<JQuickJavaVariableContext>();
        JQuickJavaVariableContext variableContext=new JQuickJavaVariableContext();
        variableContext.addVariable("radius", 5.0, JQuickJavaTypeReference.of(float.class));
        contextStack.push(variableContext);
        JContext jContext=new JContext();
        JQuickJavaWhileExecutor executor = new JQuickJavaWhileExecutor(jContext,contextStack);
        Object result=executor.execute(rule);
    }
}
