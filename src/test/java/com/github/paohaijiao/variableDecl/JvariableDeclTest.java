//package com.github.paohaijiao.variableDecl;
//
//import com.github.paohaijiao.param.JContext;
//import com.github.paohaijiao.parser.JQuickJavaLexer;
//import com.github.paohaijiao.parser.JQuickJavaParser;
//import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
//import org.antlr.v4.runtime.CharStreams;
//import org.antlr.v4.runtime.CommonTokenStream;
//import org.junit.Test;
//
//import java.io.IOException;
//
//public class JvariableDeclTest {
//    @Test
//    public void short1() throws IOException {
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("short a=3;"));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.VariableDeclContext tree = parser.variableDecl();
//        JContext params = new JContext();
//        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor( JVariableContextBuilder.mockData(),lexer,tokens,parser);
//        Object object = tv.visit(tree);
//        System.out.println(object);
//    }
//    @Test
//    public void int1() throws IOException {
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("int a=3;"));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.VariableDeclContext tree = parser.variableDecl();
//        JContext params = new JContext();
//        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor( JVariableContextBuilder.mockData(),lexer,tokens,parser);
//        Object object = tv.visit(tree);
//        System.out.println(object);
//    }
//    @Test
//    public void float1() throws IOException {
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("float a=3.8"));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.VariableDeclContext tree = parser.variableDecl();
//        JContext params = new JContext();
//        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor( JVariableContextBuilder.mockData(),lexer,tokens,parser);
//        Object object = tv.visit(tree);
//        System.out.println(object);
//    }
//    @Test
//    public void double1() throws IOException {
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("double a=3.8"));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.VariableDeclContext tree = parser.variableDecl();
//        JContext params = new JContext();
//        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor( JVariableContextBuilder.mockData(),lexer,tokens,parser);
//        Object object = tv.visit(tree);
//        System.out.println(object);
//    }
//    @Test
//    public void long1() throws IOException {
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("long a=3"));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.VariableDeclContext tree = parser.variableDecl();
//        JContext params = new JContext();
//        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor( JVariableContextBuilder.mockData(),lexer,tokens,parser);
//        Object object = tv.visit(tree);
//        System.out.println(object);
//    }
//    @Test
//    public void boolean1() throws IOException {
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("boolean a=true"));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.VariableDeclContext tree = parser.variableDecl();
//        JContext params = new JContext();
//        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor( JVariableContextBuilder.mockData(),lexer,tokens,parser);
//        Object object = tv.visit(tree);
//        System.out.println(object);
//    }
//    @Test
//    public void string1() throws IOException {
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("java.lang.String a=\"paohaijiao\""));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.VariableDeclContext tree = parser.variableDecl();
//        JContext params = new JContext();
//        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor( JVariableContextBuilder.mockData(),lexer,tokens,parser);
//        Object object = tv.visit(tree);
//        System.out.println(object);
//    }
//    @Test
//    public void string1list() throws IOException {
//        String rule="List<java.lang.String> a=[\"paohaijiao\"]";
//        System.out.println(rule);
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(rule));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.VariableDeclContext tree = parser.variableDecl();
//        JContext params = new JContext();
//        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor( JVariableContextBuilder.mockData(),lexer,tokens,parser);
//        Object object = tv.visit(tree);
//        System.out.println(object);
//    }
//    @Test
//    public void string1array() throws IOException {
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("java.lang.String[] a=[\"paohaijiao\"]"));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.VariableDeclContext tree = parser.variableDecl();
//        JContext params = new JContext();
//        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor( JVariableContextBuilder.mockData(),lexer,tokens,parser);
//        Object object = tv.visit(tree);
//        System.out.println(object);
//    }
//    @Test
//    public void map() throws IOException {
//        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString("Map<java.lang.String,java.lang.Object> a={\"topProducts\":[\"ProductA\",\"ProductB\",\"ProductC\"],\"officeLocations\":{\"headquarters\":{\"address\":\"123 Tech Street, Silicon Valley\",\"floors\":5,\"sizeSqft\":25000},\"east_coast_branch\":{\"address\":\"456 Innovation Ave, New York\",\"floors\":3,\"sizeSqft\":15000}},\"companyName\":\"TechCorp Inc.\",\"departments\":{\"dev\":{\"name\":\"Development\",\"employeeCount\":45,\"budget\":1200000.0},\"hr\":{\"name\":\"HR\",\"employeeCount\":5,\"budget\":400000.0},\"sales\":{\"name\":\"Sales\",\"employeeCount\":12,\"budget\":800000.0}},\"revenueByYear\":{\"2020\":5000000,\"2021\":6500000,\"2022\":8200000},\"employees\":{\"user1\":{\"username\":\"john_doe\",\"age\":32,\"hobbies\":[\"Reading\",\"Hiking\",\"Photography\"],\"skillRatings\":{\"Java\":4.5,\"Python\":4.2,\"SQL\":4.0}},\"user2\":{\"username\":\"jane_smith\",\"age\":28,\"hobbies\":[\"Gaming\",\"Cooking\"],\"skillRatings\":{\"JavaScript\":4.7,\"Node.js\":4.3,\"React\":4.5}}},\"foundedYear\":2005}"));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        JQuickJavaParser parser = new JQuickJavaParser(tokens);
//        JQuickJavaParser.VariableDeclContext tree = parser.variableDecl();
//        JContext params = new JContext();
//        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor( JVariableContextBuilder.mockData(),lexer,tokens,parser);
//        Object object = tv.visit(tree);
//        System.out.println(object);
//    }
//}
