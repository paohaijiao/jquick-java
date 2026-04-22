package com.github.paohaijiao.scope;

import com.github.paohaijiao.executor.JQuickJavaExecutor;
import org.junit.Test;

import java.io.IOException;

public class JScopeForTest {

    @Test
    public void scopefunc() throws IOException {
        String rule=
                "void function a(int:a,float:b){" +
                        " for (int i = 0; i < 10; i = i + 1) {\n" +
                               "console.log(i)"+
                        "            if(i==2){\n" +
                        "                break;\n" +
                        "            }else{\n" +
                        "                console.log(i);\n" +
                        "            }\n" +
                        "   };}\n" +
                        "int c=1;\n" +
                        "float d=8.1;" +
                        "this.a(int:c,float:d);" ;
        System.out.println(rule);
        JQuickJavaExecutor executor = new JQuickJavaExecutor();
        Object result=executor.execute(rule);
        System.out.println(result);
    }

    @Test
    public void scopefunc1() throws IOException {
        String rule=
                "void function a(int:a,float:b){" +
                        " for (int i = 0; i < 10; i = i + 1) {\n" +
                            "console.log(d)"+
                        "            if(i==2){\n" +
                        "                break;\n" +
                        "            }else{\n" +
                        "                console.log(i);\n" +
                        "            }\n" +
                        "   };}\n" +
                        "int c=1;\n" +
                        "float d=8.1;" +
                        "this.a(int:c,float:d);" ;
        System.out.println(rule);
        JQuickJavaExecutor executor = new JQuickJavaExecutor();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
    @Test
    public void scopefunc2() throws IOException {
        String rule=
                "void function a(int:a,float:b){" +
                        "console.log(b)"+
                        " for (int i = 0; i < 10; i = i + 1) {\n" +

                        "            if(i==2){\n" +
                        "                break;\n" +
                        "            }else{\n" +
                        "                console.log(i);\n" +
                        "            }\n" +
                        "   };}\n" +
                        "int c=1;\n" +
                        "float d=8.1;" +
                        "this.a(int:c,float:d);" ;
        System.out.println(rule);
        JQuickJavaExecutor executor = new JQuickJavaExecutor();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
    @Test
    public void scopefunc3() throws IOException {
        String rule=
                "void function a(int:a,float:b){" +
                        "int k=1000;"+
                        " for (int i = 0; i < 10; i = i + 1) {\n" +
                        "console.log(k)"+
                        "            if(i==2){\n" +
                        "                break;\n" +
                        "            }else{\n" +
                        "                console.log(i);\n" +
                        "            }\n" +
                        "   };}\n" +
                        "int c=1;\n" +
                        "float d=8.1;" +
                        "this.a(int:c,float:d);" ;
        System.out.println(rule);
        JQuickJavaExecutor executor = new JQuickJavaExecutor();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
    @Test
    public void scopefunc4() throws IOException {
        String rule=
                "void function a(int:a,float:b){" +
                        "int k=1000;"+
                        " for (int i = 0; i < 10; i = i + 1) {\n" +
                        "            if(i==2){\n" +
                                     "int k=1002;"+
                        "                console.log(k);\n" +
                        "                break;\n" +
                        "            }else{\n" +
                        "                console.log(i);\n" +
                        "            }\n" +
                        "   };}\n" +
                        "int c=1;\n" +
                        "float d=8.1;" +
                        "this.a(int:c,float:d);" ;
        System.out.println(rule);
        JQuickJavaExecutor executor = new JQuickJavaExecutor();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
    @Test
    public void scopefunc5() throws IOException {
        String rule=
                "void function a(int:a,float:b){" +
                        "int k=1000;"+
                        " for (int i = 0; i < 10; i = i + 1) {\n" +
                        "            if(i==2){\n" +
                                        "int q=1002;"+
                        "                console.log(q);\n" +
                        "                break;\n" +
                        "            }else{\n" +
                        "                console.log(q);\n" +
                        "            }\n" +
                        "   };}\n" +
                        "int c=1;\n" +
                        "float d=8.1;" +
                        "this.a(int:c,float:d);" ;
        System.out.println(rule);
        JQuickJavaExecutor executor = new JQuickJavaExecutor();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
    @Test
    public void scopefunc6() throws IOException {
        String rule=
                "  void   function a(int:a,float:b) {\n" +
                        "        int k = 1000;\n" +
                        "        for (int i = 0; i < 10; i = i + 1) {\n" +
                        "            for (int j = 0; j < 10; j = j + 1) {\n" +
                        "                if (j == 2) {\n" +
                                            "int q=33;"+
                        "                    continue;\n" +
                        "                } else {\n" +
                     //   "                    console.log(q);\n" +
                        "                    console.log(i + \",\" + j);\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "        ;\n" +
                        "    }\n" +
                        "    int c=1;\n" +
                        "    float d=8.1;\n" +
                        "    this.a(int:c,float:d);" ;
        System.out.println(rule);
        JQuickJavaExecutor executor = new JQuickJavaExecutor();
        Object result=executor.execute(rule);
        System.out.println(result);
    }


}
