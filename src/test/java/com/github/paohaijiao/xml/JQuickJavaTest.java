package com.github.paohaijiao.xml;

import com.github.paohaijiao.xml.factory.JQuickFactory;
import com.github.paohaijiao.xml.factory.JQuickXmlFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class JQuickJavaTest {
    @Test
    public void tesstAction() throws IOException {
        JQuickJavaXmlParseFactory handler=new JQuickJavaXmlParseFactory();
        JQuickFactory factory = new JQuickXmlFactory(handler,"jquick-java.xml");
        UserMapper userApi = factory.createApi(UserMapper.class);
        int sum= userApi.sum(1,2);
        System.out.println(sum);
    }

    @Test
    public void testEntryCreateApi() {
        UserMapper userApi = JQuickJava.create().createApi(UserMapper.class, "jquick-java.xml");
        int sum = userApi.sum(1, 2);
        org.junit.Assert.assertEquals(3, sum);
    }

    @Test
    public void testEntryExecute() {
        Object result = JQuickJava.create()
                .importPackage("java.lang.String", "type1")
                .variable("base", 60)
                .execute(
                        "type1 def a(int:a,int:b) {\n" +
                                "   int t = a+b;\n" +
                                "   type1 p = java.lang.String::valueOf(int:t);\n" +
                                "   return p;\n" +
                                "}\n" +
                                "int c=1;\n" +
                                "int d=2;\n" +
                                "this.a(int:c,int:d);"
                );
        System.out.println("execute result: " + result);
        org.junit.Assert.assertEquals("3", result);
    }

    @Test
    public void testEntryInlineCreateApi() {
        UserMapper userApi = JQuickJava.create()
                .importPackage("java.lang.String", "type1")
                .constant("base", 60)
                .rule("sum", "int def sum(int:a,int:b){ return a+b; }")
                .rule("mul", "type1 def mul(int:a,int:b){ int t=a*b; type1 p = java.lang.String::valueOf(int:t); return p; }")
                .createApi(UserMapper.class);
        int sum = userApi.sum(1, 2);
        int mul = userApi.mul(3, 4);
        System.out.println("inline createApi sum: " + sum + ", mul: " + mul);
        org.junit.Assert.assertEquals(3, sum);
        org.junit.Assert.assertEquals(12, mul);
    }

    @Test
    public void testEntryMergeXmlAndInline() {
        UserMapper userApi = JQuickJava.create()
                .rule("mul", "int def mul(int:a,int:b){ return a*b; }")
                .createApi(UserMapper.class, "jquick-java.xml");
        int sum = userApi.sum(1, 2);
        int mul = userApi.mul(3, 4);
        System.out.println("merge createApi sum: " + sum + ", mul: " + mul);
        org.junit.Assert.assertEquals(3, sum);
        org.junit.Assert.assertEquals(12, mul);
    }

}
