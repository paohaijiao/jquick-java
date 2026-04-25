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
package com.github.paohaijiao.visitor;

import com.github.paohaijiao.builtins.JQuickJavaBuiltinFunctionManager;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.executor.JQuickJavaActionExecutor;
import com.github.paohaijiao.model.*;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.support.JQuickJavaObjectFactory;
import com.github.paohaijiao.support.JQuickJavaReflectionFactory;
import com.github.paohaijiao.support.JQuickJavaTypeReference;
import com.github.paohaijiao.support.impl.JQuickJavaConstructorFactory;
import com.github.paohaijiao.support.impl.JQuickJavaInstanceMethodFactory;
import com.github.paohaijiao.support.impl.JQuickJavaStaticMethodFactory;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.misc.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class JQuickMethodInvocationCallVisitor extends JQuickJavaPrimaryVisitor {

    private static final Class<?> PKG = JQuickMethodInvocationCallVisitor.class;


    @Override
    public String visitAccessObjectName(JQuickJavaParser.AccessObjectNameContext ctx) {
        if (ctx.identifier()!=null) {
            return visitIdentifier(ctx.identifier());
        }else{
            return null;
        }
    }


    @Override
    public Object  visitFunctionDefinition(JQuickJavaParser.FunctionDefinitionContext ctx) {
        JAssert.notNull(ctx.IDENTIFIER(), "functionName must not be null");
        String functionName = ctx.IDENTIFIER().getText();
        List<JQuickJavaFunctionFieldModel> paramDefine = new ArrayList<>();
        if (ctx.parameterList() != null) {
            paramDefine=visitParameterList(ctx.parameterList())  ;
        }
        if (ctx.parameterList() != null) {
//            List<JQuickJavaVariable> variables = new ArrayList<>();
//            for (JQuickJavaParser.ParamContext paramCtx : ctx.parameterList().param()) {
//                JQuickJavaTypeReference<?> paramType = visitClasssType(paramCtx.classsType());
//                String name = paramCtx.functionVar().getText();
//                JQuickJavaVariable variable=new JQuickJavaVariable(name,null,paramType);
//                variables.add(variable);
//            }
//            currentContext().addScopeVariable(functionName, variables);
        }
        TokenStreamRewriter rewriter = new TokenStreamRewriter(tokenStream);
        if (ctx.action() != null) {
            ctx.action().statement().forEach(stmt -> {
                if (stmt.expression() != null && stmt.expression().methodInvocation() != null && stmt.expression().methodInvocation() instanceof JQuickJavaParser.ConstructorCallContext) {
                    JQuickJavaParser.ConstructorCallContext constructorCtx = (JQuickJavaParser.ConstructorCallContext) stmt.expression().methodInvocation();
                    Token newToken = constructorCtx.NEW().getSymbol();
                    Token nextToken = tokenStream.get(newToken.getTokenIndex() + 1);
                    String whitespace = tokenStream.getText(Interval.of(newToken.getTokenIndex(), nextToken.getTokenIndex() - 1));
                    if (whitespace.trim().isEmpty()) {
                        rewriter.insertAfter(newToken, " ");
                    }
                }
            });
        }
        String modifiedBody = rewriter.getText(Interval.of(ctx.action().start.getTokenIndex(), ctx.action().stop.getTokenIndex()));
        JQuickJavaTypeReference<?> type=null;
        if (null!=ctx.classsType()){
            type= visitClasssType(ctx.classsType());
        }
        JQuickJavaFunctionDefinitionModel jFunctionDefinitionModel =createFunctionDefinition(functionName,paramDefine,modifiedBody,type);
        parser.register(jFunctionDefinitionModel);
        return null;
    }
    @Override
    public List<JQuickJavaFunctionFieldModel> visitParameterList(JQuickJavaParser.ParameterListContext ctx) {
        List<JQuickJavaFunctionFieldModel> list=new ArrayList<>();
        for (int i = 0; i < ctx.param().size(); i++) {
            JQuickJavaFunctionFieldModel model=new JQuickJavaFunctionFieldModel();
            JQuickJavafunctionParamModel param=visitParam(ctx.param().get(i));
            model.setIndex(i);
            model.setFieldName(param.getName());
            model.setType(param.getType());
            list.add(model);
        }
        return list;
    }


    @Override
    public JQuickJavafunctionParamModel visitParam(JQuickJavaParser.ParamContext ctx) {
        JQuickJavafunctionParamModel model=new JQuickJavafunctionParamModel();
        if(ctx.functionVar() != null) {
            model.setName(visitFunctionVar(ctx.functionVar()));
        }
        if(ctx.classsType() != null) {
            model.setType(visitClasssType(ctx.classsType()));
        }
        return model;
    }
    @Override
    public JQuickJavaTypeReferenceAndValue visitLiteralItem(JQuickJavaParser.LiteralItemContext ctx) {
        JAssert.notNull(ctx.classsType(), "classsType must not be null");
        JAssert.notNull(ctx.literal(), "literal require not be null");
        JQuickJavaTypeReferenceAndValue typeReferenceAndValue=new JQuickJavaTypeReferenceAndValue();
        JQuickJavaTypeReference<?> classType=visitClasssType(ctx.classsType());
        Object literal=visitLiteral(ctx.literal());
        String jsonString=null;
        if(null!=literal) {
             jsonString=literal.toString();
            if(jsonString==null) {
                jsonString = gson.toJson(literal);
            }
        }
        typeReferenceAndValue.setTypeArguments(classType);
        Object value=this.mergeDataWithTypeReference(jsonString,classType);
        typeReferenceAndValue.setData(value);
        return typeReferenceAndValue;

    }


    @Override
    public JQuickJavaTypeReferenceAndValueModel visitArgumentList(JQuickJavaParser.ArgumentListContext ctx) {
        JQuickJavaTypeReferenceAndValueModel model=new JQuickJavaTypeReferenceAndValueModel();
        if (null != ctx.literalItem() && !ctx.literalItem().isEmpty()) {
            List<JQuickJavaTypeReferenceAndValue> list = new ArrayList<>();
            for (JQuickJavaParser.LiteralItemContext literalItemContext : ctx.literalItem()) {
                JQuickJavaTypeReferenceAndValue object = visitLiteralItem(literalItemContext);
                list.add(object);
            }
            model.setList(list);
        }
        return model;
    }
    @Override
    public Object visitStaticCall(JQuickJavaParser.StaticCallContext ctx) {
        JAssert.notNull(ctx.classsType(),"the class name is not support");
        JAssert.notNull(ctx.methodName(),"the method name is not support");
        String qualifiedName = ctx.classsType() != null ? ctx.classsType().getText() : null;
        String methodName = visitMethodName(ctx.methodName());
        JQuickJavaTypeReferenceAndValueModel model=new JQuickJavaTypeReferenceAndValueModel();
        if(null!=ctx.argumentList()&&null!=ctx.argumentList().literalItem()&& !ctx.argumentList().literalItem().isEmpty()){
            model=visitArgumentList(ctx.argumentList());
        }
        try {
            JQuickJavaTypeReference<?> typeReference = loadClass(qualifiedName);
            Class<?> clazz=typeReference.getRawType();
            JQuickJavaStaticMethodFactory instance = JQuickJavaReflectionFactory.staticMethod(clazz);
            JQuickJavaTypeReference<?>[] references=model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getTypeArguments).toArray(JQuickJavaTypeReference[]::new);
            Object[] data=model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getData).toArray();
            return instance.invoke(methodName, references,data);
        } catch (Exception e) {
            throw new RuntimeException("please double check static method invocation : " + methodName, e);
        }

    }
    @Override
    public Object visitConstructorCall(JQuickJavaParser.ConstructorCallContext ctx) {
        JAssert.notNull(ctx.classsType(),"the class name is not support");
        JQuickJavaTypeReferenceAndValueModel model=new JQuickJavaTypeReferenceAndValueModel();
        if(null!=ctx.argumentList()&&null!=ctx.argumentList().literalItem()&&ctx.argumentList().literalItem().size()>0){
            model=visitArgumentList(ctx.argumentList());
        }
        String qualifiedName = ctx.classsType() != null ? ctx.classsType().getText() : null;
        try {
            JQuickJavaTypeReference<?> typeReference = loadClass(qualifiedName);
            Class<?> clazz=typeReference.getRawType();
            JQuickJavaConstructorFactory<?> instance = JQuickJavaReflectionFactory.constructor(clazz);
            JQuickJavaTypeReference<?>[] references=model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getTypeArguments).toArray(JQuickJavaTypeReference[]::new);
            Object[] data=model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getData).toArray();
            return instance.newInstance(references,data);
        } catch (Exception e) {
            throw new RuntimeException("please double check constructor method   " , e);
        }
    }
    @Override
    public Object visitInstanceMethodCall(JQuickJavaParser.InstanceMethodCallContext ctx) {
        JAssert.notNull(ctx.instanceName(),"the instanceName  is not support"+ctx.instanceName());
        JAssert.notNull(ctx.methodName(),"the method name is not support: "+ctx.methodName().getText());
        String methodName = visitMethodName(ctx.methodName());
        Object target=null;
        if (ctx.instanceName() != null) {
            target = visitInstanceName(ctx.instanceName());
        }
        JAssert.notNull(target,"the target object is not support:"+ctx.instanceName().getText());
        JQuickJavaTypeReferenceAndValueModel model=new JQuickJavaTypeReferenceAndValueModel();
        if(null!=ctx.argumentList()&&null!=ctx.argumentList().literalItem()&&ctx.argumentList().literalItem().size()>0){
            model=visitArgumentList(ctx.argumentList());
        }
        try {
            JQuickJavaInstanceMethodFactory instance = JQuickJavaReflectionFactory.instanceMethod(target);
            JQuickJavaTypeReference<?>[] references=model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getTypeArguments).toArray(JQuickJavaTypeReference[]::new);
            Object[] data=model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getData).toArray();
            return instance.invoke(methodName,references,data);
        } catch (Exception e) {
            throw new RuntimeException("please double check constructor method   " , e);
        }
    }
    @Override
    public Object visitThisMethodCall(JQuickJavaParser.ThisMethodCallContext ctx) {
        JAssert.notNull(ctx.methodName(),"the method name not support");
        String methodName = visitMethodName(ctx.methodName());
        boolean flag=this.hasFunction(methodName);
        JAssert.isTrue(flag,"the method [ "+methodName+" ] did not define in this context");
        JQuickJavaTypeReferenceAndValueModel model=new JQuickJavaTypeReferenceAndValueModel();
        if(null!=ctx.argumentList()&&null!=ctx.argumentList().literalItem()&&!ctx.argumentList().literalItem().isEmpty()){
            model=visitArgumentList(ctx.argumentList());
        }
        JQuickJavaTypeReference<?>[] references=model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getTypeArguments).toArray(JQuickJavaTypeReference[]::new);
        JQuickJavaFunctionDefinitionModel function = registry.lookupFunction(methodName,references);//find the best match method
        JAssert.notNull(function,"can't find function ["+methodName+"] based the parameter [ "+references+" ] you gived");
        JQuickJavaActionExecutor executor=new JQuickJavaActionExecutor(this.parser.getJContext(),this.parser.copyCurrentScope());
        List<Object> data= model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getData).collect(Collectors.toList());
        for (int i=0;i<function.getFields().size();i++){
            JQuickJavaFunctionFieldModel field=function.getFields().get(i);
            Object value=data.get(i);
            if(field.getType().targetAssignableFrom(value)){
               //this.currentContext().addVariable(field.getFieldName(),value,field.getType());
            }else{
                JAssert.throwNewException("the field [ "+field.getFieldName()+" ] param type mismatch in this context");
            }
        }
        Object object=executor.execute(function.getAction());
        if(null==object){
            return null;
        }else{
            return mergeDataWithTypeReference(object.toString(),function.getReturnType());
        }
    }
    @Override
    public Object visitAccessStaticMethodCall(JQuickJavaParser.AccessStaticMethodCallContext ctx) {
        JAssert.notNull(ctx.accessStaticVariable(),"the accessStaticVariable is not support");
        JAssert.notNull(ctx.methodName(),"the method name is not support");
        String methodName = visitMethodName(ctx.methodName());
        try {
            Object target=visitAccessStaticVariable(ctx.accessStaticVariable());
            JQuickJavaTypeReferenceAndValueModel model=new JQuickJavaTypeReferenceAndValueModel();
            if(null!=ctx.argumentList()&&null!=ctx.argumentList().literalItem()&& !ctx.argumentList().literalItem().isEmpty()){
                model=visitArgumentList(ctx.argumentList());
            }
            List<Object>  args=model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getData).collect(Collectors.toList());
            return  JQuickJavaObjectFactory.createByInstanceMethod(target, methodName, args);
        } catch (Exception e) {
            console.error("please double check static method invocation : " + methodName, e);
            throw new RuntimeException("please double check static method invocation : " + methodName, e);
        }

    }
    @Override
    public Object visitBuiltinMethodCall(JQuickJavaParser.BuiltinMethodCallContext ctx) {
        JQuickJavaBuiltinFunctionManager manager=new JQuickJavaBuiltinFunctionManager();
        JAssert.notNull(ctx.methodName(),"the method name is not support");
        String methodName = visitMethodName(ctx.methodName());
        JAssert.notNull(methodName,"the method name is not support");
        JQuickJavaTypeReferenceAndValueModel model=new JQuickJavaTypeReferenceAndValueModel();
        if(null!=ctx.argumentList()&&null!=ctx.argumentList().literalItem()&& !ctx.argumentList().literalItem().isEmpty()){
            model=visitArgumentList(ctx.argumentList());
        }
        List<Object>  args=model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getData).collect(Collectors.toList());
        JContext context=this.parser.getJContext();
        Stack<Map<String, Object>> stack= this.parser.deepCopyScopeStack();
        return  JQuickJavaBuiltinFunctionManager.invoke(methodName,context,stack,args);
    }

    @Override
    public Object visitInstanceName(JQuickJavaParser.InstanceNameContext ctx) {
        String instanceName=ctx.getText();
        Object instance=parser.findVar(instanceName);
        JAssert.notNull(instance, "the instance not initialize ["+instanceName+"]");
        return instance;
    }
    @Override
    public String visitMethodName(JQuickJavaParser.MethodNameContext ctx) {
        JAssert.notNull(ctx.IDENTIFIER(), "the function name  is not null");
        return ctx.IDENTIFIER().getText();
    }
    @Override
    public String  visitThis(JQuickJavaParser.ThisContext ctx) {
        return ctx.THIS().getText();
    }



    @Override
    public String visitFunctionVar(JQuickJavaParser.FunctionVarContext ctx) {
        return ctx.IDENTIFIER().getText();
    }


}
