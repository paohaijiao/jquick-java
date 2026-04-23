///*
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// *
// * Copyright (c) [2025-2099] Martin (goudingcheng@gmail.com)
// */
//package com.github.paohaijiao;
//
//import com.github.paohaijiao.executor.JQuickJavaActionExecutor;
//import com.github.paohaijiao.executor.JQuickJavaExecutor;
//import com.github.paohaijiao.param.JContext;
//import com.github.paohaijiao.scope.JQuickJavaVariableContext;
//import com.github.paohaijiao.support.JQuickJavaTypeReference;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.Stack;
//
///**
// * packageName com.github.paohaijiao
// *
// * @author Martin
// * @version 1.0.0
// * @since 2025/7/24
// */
//public class JActionTest {
//    @Test
//    public void mutiple() throws IOException {
//        JContext context = new JContext();
//        context.addConstant("PI", 3.14);
//        Stack<JQuickJavaVariableContext> contextStack = new Stack<JQuickJavaVariableContext>();
//        JQuickJavaVariableContext variableContext=new JQuickJavaVariableContext();
//        variableContext.addVariable("radius", 5.0, JQuickJavaTypeReference.of(float.class));
//        contextStack.add(variableContext);
//        JQuickJavaActionExecutor executor = new JQuickJavaActionExecutor(context,contextStack);
//        String actionCode = "{ var area = PI * radius * radius; return area; }";
//        Object result = executor.execute(actionCode);
//        System.out.println("Area: " + result);
//    }
//    @Test
//    public void sample1() throws IOException {
//        Stack<JQuickJavaVariableContext> contextStack = new Stack<JQuickJavaVariableContext>();
//        JQuickJavaVariableContext variableContext=new JQuickJavaVariableContext();
//        variableContext.addVariable("charArray", new Character[]{'W', 'o', 'r', 'l', 'd'}, JQuickJavaTypeReference.of(Character.class));
//        contextStack.add(variableContext);
//        String rule=
//                " java.util.HashMap<java.lang.String,java.lang.String>   function a(int:a,float:b) {\n" +
//                     "java.lang.String str1 = new java.lang.String(java.lang.String:\"Hello\");\n" +
//                        " console.log(str1); \n" +
//                        " java.lang.String upperStr = str1.toUpperCase(); \n" +
//                        " console.log(upperStr);  \n" +
//                        " java.lang.String subStr = str1.substring(int:1, int:3);\n" +
//                        " console.log(subStr);  \n" +
//                        " java.util.HashMap<java.lang.String,java.lang.String> result = new java.util.HashMap();  " +
//                        "result.put(java.lang.String:\"constructed1\", java.lang.String:str1);" +
//                        "  result.put(java.lang.String:\"constructed2\", java.lang.String:str1); \n" +
//                        " result.put(java.lang.String:\"uppercased\", java.lang.String:upperStr);  " +
//                        "result.put(java.lang.String:\"substring\", java.lang.String:subStr); \n" +
//                        " return result;"+
//                        "    }\n" +
//                        "    int c=1;\n" +
//                        "    float d=8.1;\n" +
//                        "    this.a(int:c,float:d);" ;
//        System.out.println(rule);
//        JQuickJavaExecutor executor = new JQuickJavaExecutor();
//        Object result=executor.execute(rule);
//        System.out.println(result);
//    }
//    @Test
//    public void sample2() throws IOException {
//        Stack<JQuickJavaVariableContext> contextStack = new Stack<JQuickJavaVariableContext>();
//        JQuickJavaVariableContext variableContext=new JQuickJavaVariableContext();
//        variableContext.addVariable("charArray", new Character[]{'W', 'o', 'r', 'l', 'd'}, JQuickJavaTypeReference.of(Character.class));
//        contextStack.add(variableContext);
//        String rule=
//                "   java.lang.String function a(int:a,float:b) {\n" +
//                        "java.lang.String p=java.lang.String::format(java.lang.String:\"Number: %d, String: %s\",int: 42, java.lang.String:\"test\");" +
//                            " return p;"+
//                        "    }\n" +
//                        "    int c=1;\n" +
//                        "    float d=8.1;\n" +
//                        "    this.a(int:c,float:d);" ;
//        System.out.println(rule);
//        JQuickJavaExecutor executor = new JQuickJavaExecutor();
//        Object result=executor.execute(rule);
//        System.out.println(result);
//    }
//    @Test
//    public void sample3() throws IOException {
//        Stack<JQuickJavaVariableContext> contextStack = new Stack<JQuickJavaVariableContext>();
//        JQuickJavaVariableContext variableContext=new JQuickJavaVariableContext();
//        variableContext.addVariable("charArray", new Character[]{'W', 'o', 'r', 'l', 'd'}, JQuickJavaTypeReference.of(Character.class));
//        contextStack.add(variableContext);
//        String rule=
//                "    import java.lang.String as type1;" +
//                "   type1 function a(int:a,float:b) {\n" +
//                        "type1 p=type1::format(type1:\"Number: %d, String: %s\",int: 42, type1:\"test\");" +
//                        " return p;"+
//                        "    }\n" +
//                        "    int c=1;\n" +
//                        "    float d=8.1;\n" +
//                        "    this.a(int:c,float:d);" ;
//        System.out.println(rule);
//        JQuickJavaExecutor executor = new JQuickJavaExecutor();
//        Object result=executor.execute(rule);
//        System.out.println(result);
//    }
//}
