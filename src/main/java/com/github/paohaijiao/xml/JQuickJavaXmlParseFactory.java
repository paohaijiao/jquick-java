package com.github.paohaijiao.xml;

import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.runtime.JQuickJavaRuntimeEnvironment;
import com.github.paohaijiao.xml.element.JQuickXmlElement;
import com.github.paohaijiao.xml.handler.JQuickParseHandler;
import com.github.paohaijiao.xml.invocation.JQuickXmlInvocationHandler;

import java.util.HashMap;
import java.util.Map;

public class JQuickJavaXmlParseFactory implements JQuickParseHandler {

    private JContext context=new JContext();

    private Map<String, Object> environment=new HashMap<String, Object>();


    public JQuickJavaXmlParseFactory(){

    }
    public JQuickJavaXmlParseFactory(JQuickJavaRuntimeEnvironment environment){
        JAssert.notNull(environment,"environment require not be null");
        if(environment.getContext()!=null&&!environment.getContext().isEmpty()){
            this.context.putAll(environment.getContext());
        }
        if(environment.getEnvironment()!=null&&!environment.getEnvironment().isEmpty()){
            this.environment.putAll(environment.getEnvironment());
        }
    }
    public JQuickJavaXmlParseFactory(JContext context){
        JAssert.notNull(context,"context require not be null");
        if(!context.isEmpty()){
            this.context.putAll(context);
        }
    }
    public JQuickJavaXmlParseFactory(Map<String, Object> env ){
        JAssert.notNull(env,"env require not be null");
        if(!env.isEmpty()){
            this.environment.putAll(env);
        }
    }

    @Override
    public JQuickXmlElement createJQuickXmlElement() {
        return new JQuickJavaXmlElement();
    }

    @Override
    public JQuickXmlInvocationHandler createlInvocationHandler() {
        JQuickJavaRuntimeEnvironment runtimeEnvironment=new JQuickJavaRuntimeEnvironment(this.context,this.environment);
        return new JQuickJavaXmlInvocationHandler(runtimeEnvironment);
    }
}
