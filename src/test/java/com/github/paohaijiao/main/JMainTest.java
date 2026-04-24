/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (c) [2025-2099] Martin (goudingcheng@gmail.com)
 */
package com.github.paohaijiao.main;

import com.github.paohaijiao.executor.JQuickJavaExecutor;
import org.junit.Test;

import java.io.IOException;

public class JMainTest {

    @Test
    public void methodDefine1() throws IOException {
        String rule="    import java.lang.String as string;\n" +
                "     import java.lang.System as system;\n" +
                "   string function getSquare(java.lang.String:a,int:b){\n" +
                "        system@out.println(string:a);\n" +
                "        return a+b;\n" +
                "    }\n" +
                "    string a=\"hello world\";\n" +
                "    int b=2;\n" +
                "    string c=this.getSquare(string:a,int:b);";
        System.out.println(rule);
        JQuickJavaExecutor executor =JQuickJavaExecutor.getInstance();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
    @Test
    public void methodDefine2() throws IOException {
        String rule="   import java.lang.String as string;\n" +
                "     import java.lang.System as system;\n" +
                "    string function getSquare(java.lang.String:a,int:b){\n" +
                "        system@out.println(string:a);\n" +
                "        string d= a+b;\n" +
                "        return d;\n" +
                "    }\n" +
                "    string a=\"hello world\";\n" +
                "    int b=2;\n" +
                "    string c=this.getSquare(string:a,int:b);";
        System.out.println(rule);
        JQuickJavaExecutor executor =JQuickJavaExecutor.getInstance();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
    @Test
    public void methodDefine3() throws IOException {
        String rule="import java.lang.String as string;\n" +
                "import java.lang.System as system;\n" +
                "java.lang.Object function getSquare(int:a,int:b){\n" +
                "    system@out.println(int:a);\n" +
                "\tif(a.equals(int:b)){\n" +
                "\t\tfloat f=3; \n" +
                "\t\treturn f;\n" +
                "\t}        \n" +
                "\tint d= a+b;\n" +
                "      return d;\n" +
                "    }\n" +
                "int a=2;\n" +
                "int b=2;\n" +
                "java.lang.Object c=this.getSquare(int:a,int:b);";
        System.out.println(rule);
        JQuickJavaExecutor executor = JQuickJavaExecutor.getInstance();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
    @Test
    public void methodDefine4() throws IOException {
        String rule="import java.lang.String as string;\n" +
                "import java.lang.System as system;\n" +
                "int function getSquare(int:a,int:b){\n" +
                "    system@out.println(int:a);\n" +
                "\tfor(i=0;i<10;i=i+1){\n" +
                "\t   if(i==2){\n" +
                "\t     continue;\n" +
                "\t   }else{\n" +
                "\t       int k=i+3;\n" +
                "\t       system@out.println(int:k);\n" +
                "\t   }\n" +
                "    }    \n" +
                "\tint d= a+b;\n" +
                "      return d;\n" +
                "    }\n" +
                "int a=2;\n" +
                "int b=2;\n" +
                "int c=this.getSquare(int:a,int:b);";
        System.out.println(rule);
        JQuickJavaExecutor executor =JQuickJavaExecutor.getInstance();
        Object result=executor.execute(rule);
        System.out.println(result);
    }
    @Test
    public void while1() throws IOException {
        String rule="import java.lang.String as string;\n" +
                "import java.lang.System as system;\n" +
                "int function getSquare(int:a,int:b){\n" +
                "\t       console.log(1);\n" +
                "\t       boolean s=true;\n" +
                "\t       console.log(2);\n" +
                "\twhile(s){\n" +
                "\t   if(a>5){\n" +
                "\t       console.log(3);\n" +
                "\t       s=false;\n" +
                "\t       console.log(a);\n" +
                "\t       system@out.println(string:'hehe');\n" +
                "\t     return a;\n" +
                "\t   }else{\n" +
                "\t       a=a+1;\n" +
                "\t       system@out.println(int:a);\n" +
                "\t   }\n" +
                "    }    \n" +
                "    }\n" +
                "int a=1;\n" +
                "int b=2;\n" +
                "int c=this.getSquare(int:a,int:b);";
        System.out.println(rule);
        JQuickJavaExecutor executor = JQuickJavaExecutor.getInstance();
        Object result=executor.execute(rule);
        System.out.println(result);
    }


}
