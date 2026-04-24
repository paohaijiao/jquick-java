package com.github.paohaijiao.methodInvocation2;

import com.github.paohaijiao.executor.JQuickJavaExecutor;
import org.junit.Test;

import java.io.IOException;

public class ThisMethodInvocationTest {
    @Test
    public void methodDefine() throws IOException {
        String rule="    int def getSquare(int:a,int:b){\n" +
                "        return a*b;\n" +
                "    }\n" +
                "    int a=1;\n" +
                "    int b=2;\n" +
                "    int c=this.getSquare(int:a,int:b);";
        System.out.println(rule);
        JQuickJavaExecutor executor = JQuickJavaExecutor.getInstance();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
}
