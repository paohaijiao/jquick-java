package com.github.paohaijiao.xml;

import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.executor.JQuickJavaActionExecutor;
import com.github.paohaijiao.model.JQuickJavaFunctionDefinitionModel;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.runtime.JQuickJavaRuntimeEnvironment;
import com.github.paohaijiao.transformer.JQuickValueTransformer;
import com.github.paohaijiao.transformer.type.JQuickJavaTypeReference;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import com.github.paohaijiao.xml.invocation.JQuickXmlInvocationHandler;
import com.github.paohaijiao.xml.param.Param;
import com.github.paohaijiao.xml.util.ParamUtil;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class JQuickJavaXmlInvocationHandler extends JQuickXmlInvocationHandler {

    private JConsole console=new JConsole();

    private JContext context=new JContext();

    private Map<String, Object> environment=new HashMap<String, Object>();


    public JQuickJavaXmlInvocationHandler(){

    }
    public JQuickJavaXmlInvocationHandler(JQuickJavaRuntimeEnvironment environment){
        JAssert.notNull(environment,"environment require not be null");
        if(environment.getContext()!=null&&!environment.getContext().isEmpty()){
            this.context.putAll(environment.getContext());
        }
        if(environment.getEnvironment()!=null&&!environment.getEnvironment().isEmpty()){
            this.environment.putAll(environment.getEnvironment());
        }
    }
    public JQuickJavaXmlInvocationHandler(JContext context){
        JAssert.notNull(context,"context require not be null");
        if(!context.isEmpty()){
            this.context.putAll(context);
        }
    }
    public JQuickJavaXmlInvocationHandler(Map<String, Object> env ){
        JAssert.notNull(env,"env require not be null");
        if(!env.isEmpty()){
            this.environment.putAll(env);
        }
    }


    @Override
    protected Object loadResult(String lexerStr, JContext context, Method method, Object[] args) {
        if(!context.isEmpty()){
            context.putAll(context);
        }
        ParamUtil paramUtil=new ParamUtil();
        Map<String,Object> map=paramUtil.bindParams(method, args);
        if(null!=map&&!map.isEmpty()){
            environment.putAll(map);
        }
        JQuickJavaFunctionDefinitionModel define=functionDefinition(lexerStr);
        JQuickJavaRuntimeEnvironment runtimeEnvironment=new JQuickJavaRuntimeEnvironment(context,environment);
        JQuickJavaActionExecutor executor=new JQuickJavaActionExecutor(runtimeEnvironment);
        Object object=executor.execute(define.getAction());
        if(null==object){
            return null;
        }else{
            JQuickJavaTypeReference reference=JQuickJavaTypeReference.of(method.getReturnType());
            return new JQuickValueTransformer().transform(object.toString(),reference);
        }
    }

    public JQuickJavaFunctionDefinitionModel functionDefinition(String lexerStr){
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(lexerStr));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        JQuickJavaParser.FunctionDefinitionContext tree = parser.functionDefinition();
        JQuickJavaCommonVisitor tv = new JQuickJavaCommonVisitor(lexer,tokens,parser);
        Object object = tv.visit(tree);
        JAssert.notNull(object,"the function definition is null");
        JQuickJavaFunctionDefinitionModel functionDefinition=(JQuickJavaFunctionDefinitionModel)object;
        return functionDefinition;

    }

}
