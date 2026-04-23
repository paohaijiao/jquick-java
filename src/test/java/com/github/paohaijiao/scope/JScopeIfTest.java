package com.github.paohaijiao.scope;

import com.github.paohaijiao.executor.JQuickJavaExecutor;
import org.junit.Test;

import java.io.IOException;

public class JScopeIfTest {

    @Test
    public void scopefunc() throws IOException {
            String rule=
                    "void function a(int:a,float:b){\n" +
                            "}\n" +
                            "int c=1;\n" +
                            "float d=8.1;" ;
            System.out.println(rule);
            JQuickJavaExecutor executor =JQuickJavaExecutor.getInstance();
            Object result=executor.execute(rule);
            System.out.println(result);
    }

    @Test
    public void scopeFuncIf1() throws IOException {
        String rule=
                "int function a(int:a,float:b){\n" +
                        "    if(a==2){\n" +
                        "      int c=3;\n" +
                        "    }else if(a==3){\n" +
                        "      int f=6;\n" +
                        "    }\n" +
                        "   return 18;"+
                        "}\n" +
                        "int c=1;\n" +
                        "float d=8.1;" +
                        "this.a(int:c,float:d);" ;
        System.out.println(rule);
        JQuickJavaExecutor executor = JQuickJavaExecutor.getInstance();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
    @Test
    public void scopeFuncIf2() throws IOException {
        String rule=
                "int function a(int:a,float:b){\n" +
                        "    console.log(g)\n" +
                        "    if(a==2){\n" +
                        "      int c=3;\n" +
                        "    }else if(a==3){\n" +
                        "      int f=6;\n" +
                        "    }\n" +
                        "   return 18;}\n" +
                        "int c=1;\n" +
                        "float d=8.1;\n" +
                        "java.lang.String g=\"helloworld\";" +
                        "this.a(int:c,float:d);" ;
        System.out.println(rule);
        JQuickJavaExecutor executor =JQuickJavaExecutor.getInstance();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
    @Test
    public void scopeFuncIf3() throws IOException {
        String rule=
                "int function a(int:a,float:b){\n" +
                        "    if(a==2){\n" +
                        "      int c=3;\n" +
                        "    console.log(99)\n" +
                        "    }else if(a==3){\n" +
                        "      int f=6;\n" +
                        "    }\n" +
                        "   return 18;}\n" +
                        "int c=2;\n" +
                        "float d=8.1;\n" +
                        "java.lang.String g=\"helloworld\";" +
                        "this.a(int:c,float:d);" ;
        System.out.println(rule);
        JQuickJavaExecutor executor =JQuickJavaExecutor.getInstance();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
    @Test
    public void scopeFuncIf4() throws IOException {
        String rule=
                "int function a(int:a,float:b){\n" +
                        "    console.log(g)\n" +
                        "    if(a==2){\n" +
                        "      int c=3;\n" +
                        "    }else if(a==3){\n" +
                        "      int f=6;\n" +
                        "    }else{\n" +
                        "\t      int g=6;\n" +
                        "\t\t  console.log(g);\n" +
//                        "\t\t  console.log(f)\n" +
                        "\t}\n" +
                        "   return 18;}\n" +
                        "int c=1;\n" +
                        "float d=8.1;\n" +
                        "float g=32;\n" +
                        "this.a(int:c,float:d);" ;
        System.out.println(rule);
        JQuickJavaExecutor executor =JQuickJavaExecutor.getInstance();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
    @Test
    public void scope4() throws IOException {
        String rule=
                "int function a(int:a,float:b){\n" +
                        "    console.log(g)\n" +
                        "    if(a==2){\n" +
                        "      int c=3;\n" +
                        "    }else if(a==3){\n" +
                        "      int f=6;\n" +
                        "    }else{\n" +
                        "\t      int g=6;\n" +
                        "\t\t  console.log(g);\n" +
//                        "\t\t  console.log(f)\n" +
                        "\t}\n" +
                        "   return 18;}\n" +
                        "int c=1;\n" +
                        "float d=8.1;\n" +
                        "float g=32;\n" +
                        "this.a(int:c,float:d);" ;
        System.out.println(rule);
        JQuickJavaExecutor executor =JQuickJavaExecutor.getInstance();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
}
