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

import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.executor.JQuickJavaActionExecutor;
import com.github.paohaijiao.function.manager.JQuickMethodInvocationManager;
import com.github.paohaijiao.model.*;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.runtime.JQuickJavaRuntimeEnvironment;
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
import java.util.stream.Collectors;

public class JQuickMethodInvocationCallVisitor extends JQuickJavaPrimaryVisitor {

    private static final Class<?> PKG = JQuickMethodInvocationCallVisitor.class;

    private JQuickMethodInvocationManager manager=JQuickMethodInvocationManager.getInstance();
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
        TokenStreamRewriter rewriter = new TokenStreamRewriter(tokenStream);    
        if (ctx.action() != null) {
            ctx.action().statement().forEach(stmt -> {
                // PERFORMANCE FIX: postfix 树形下 new 表达式位于 primaryAtom 的 NEW 分支
                JQuickJavaParser.PrimaryAtomContext newAtom = findNewInExpression(stmt.expression());
                if (newAtom != null) {
                    Token newToken = newAtom.NEW().getSymbol();
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
        return jFunctionDefinitionModel;
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
    public JQuickJavaTypeReferenceAndValue visitTypedArgument(JQuickJavaParser.TypedArgumentContext ctx) {
        JAssert.notNull(ctx.classsType(), "classsType must not be null");
        JAssert.notNull(ctx.expression(), "expression require not be null");
        JQuickJavaTypeReferenceAndValue typeReferenceAndValue = new JQuickJavaTypeReferenceAndValue();
        JQuickJavaTypeReference<?> classType = visitClasssType(ctx.classsType());
        Object value = visitExpression(ctx.expression());
        typeReferenceAndValue.setTypeArguments(classType);
        if (value == null) {
            typeReferenceAndValue.setData(null);
            return typeReferenceAndValue;
        }
        String jsonString = value.toString();
        if (jsonString == null) {
            jsonString = gson.toJson(value);
        }
        typeReferenceAndValue.setData(this.mergeDataWithTypeReference(jsonString, classType));
        return typeReferenceAndValue;
    }

    @Override
    public JQuickJavaTypeReferenceAndValue visitArgument(JQuickJavaParser.ArgumentContext ctx) {
        if (ctx.typedArgument() != null) {
            return visitTypedArgument(ctx.typedArgument());
        }
        JAssert.notNull(ctx.expression(), "expression require not be null");
        Object value = visitExpression(ctx.expression());
        JQuickJavaTypeReferenceAndValue typeReferenceAndValue = new JQuickJavaTypeReferenceAndValue();
        if (value == null) {
            typeReferenceAndValue.setTypeArguments(loadClass("java.lang.Object"));
            typeReferenceAndValue.setData(null);
            return typeReferenceAndValue;
        }
        typeReferenceAndValue.setTypeArguments(loadClass(value.getClass().getName()));
        typeReferenceAndValue.setData(value);
        return typeReferenceAndValue;
    }

    @Override
    public JQuickJavaTypeReferenceAndValueModel visitArgumentList(JQuickJavaParser.ArgumentListContext ctx) {
        JQuickJavaTypeReferenceAndValueModel model = new JQuickJavaTypeReferenceAndValueModel();
        if (ctx.argument() != null && !ctx.argument().isEmpty()) {
            List<JQuickJavaTypeReferenceAndValue> list = new ArrayList<>();
            for (JQuickJavaParser.ArgumentContext argumentContext : ctx.argument()) {
                JQuickJavaTypeReferenceAndValue object = visitArgument(argumentContext);
                list.add(object);
            }
            model.setList(list);
        }
        return model;
    }
    // =====================================================================
    // PERFORMANCE FIX: postfix 树形下的方法调用实现。
    // 旧 grammar 的 methodInvocation 6 分支（staticCall/constructorCall/instanceMethodCall/
    // thisMethodCall/accessStaticMethodCall/builtinMethodCall）被扁平化为：
    //   primaryAtom（new / Class::method / Builtin::method） + postfix（.method() / .field）
    // 原 visitXxxMethodCall 的语义被迁移到 visitPrimaryAtom + applyPostfix 两个方法中，
    // 保持既有 Java runtime 反射执行逻辑完全不变。
    // =====================================================================

    @Override
    public Object visitPrimaryAtom(JQuickJavaParser.PrimaryAtomContext ctx) {
        if (ctx.NEW() != null) {
            // PERFORMANCE FIX: 构造调用（原 visitConstructorCall 语义）
            return invokeConstructor(ctx);
        }
        if (ctx.COLON() != null && ctx.classsType() != null) {
            // PERFORMANCE FIX: 静态调用 ClassType::method(args)（原 visitStaticCall 语义）
            return invokeStaticCall(ctx);
        }
        if (ctx.BUILTIN() != null) {
            // PERFORMANCE FIX: Builtin::method(args)（原 visitBuiltinMethodCall 语义）
            return invokeBuiltinCall(ctx);
        }
        // literal / (expression) / this / accessStaticVariable 由父类处理
        return super.visitPrimaryAtom(ctx);
    }

    @Override
    protected Object applyPostfix(JQuickJavaParser.PrimaryAtomContext atom, JQuickJavaParser.PostfixContext postfix, Object receiver) {
        if (postfix.methodName() != null) {
            // .method(args) —— 按接收者来源分派，保持旧 methodInvocation 6 分支的语义
            String methodName = visitMethodName(postfix.methodName());
            JQuickJavaTypeReferenceAndValueModel model = new JQuickJavaTypeReferenceAndValueModel();
            if (postfix.argumentList() != null && postfix.argumentList().argument() != null && !postfix.argumentList().argument().isEmpty()) {
                model = visitArgumentList(postfix.argumentList());
            }
            if (atom.this_() != null && THIS_PLACEHOLDER.equals(receiver)) {
                // PERFORMANCE FIX: this.method() —— DSL 函数执行（原 visitThisMethodCall 语义）
                return invokeThisFunction(methodName, model);
            }
            if (atom.accessStaticVariable() != null) {
                // PERFORMANCE FIX: A@b.method() —— 静态字段实例方法（原 visitAccessStaticMethodCall 语义）
                return invokeAccessStaticMethod(atom, methodName, model);
            }
            // PERFORMANCE FIX: primaryAtom.method() —— 反射实例方法（原 visitInstanceMethodCall 语义）
            return invokeInstanceMethod(receiver, methodName, model);
        }
        // PERFORMANCE FIX: .field 字段访问（DOT IDENTIFIER），反射读取字段值
        JAssert.notNull(receiver, "the field access target is not support: " + postfix.getText());
        String fieldName = postfix.IDENTIFIER().getText();
        try {
            java.lang.reflect.Field field = receiver.getClass().getField(fieldName);
            return field.get(receiver);
        } catch (Exception e) {
            throw new RuntimeException("please double check field access : " + fieldName, e);
        }
    }

    /** this 占位符（与父类 visitPrimaryAtom 中 this 分支返回值一致） */
    private static final String THIS_PLACEHOLDER = "this";

    /**
     * 构造调用：new ClassType(args)（原 visitConstructorCall 语义）
     */
    private Object invokeConstructor(JQuickJavaParser.PrimaryAtomContext ctx) {
        JAssert.notNull(ctx.classsType(), "the class name is not support");
        JQuickJavaTypeReferenceAndValueModel model = new JQuickJavaTypeReferenceAndValueModel();
        if (ctx.argumentList() != null && ctx.argumentList().argument() != null && !ctx.argumentList().argument().isEmpty()) {
            model = visitArgumentList(ctx.argumentList());
        }
        String qualifiedName = ctx.classsType().getText();
        try {
            JQuickJavaTypeReference<?> typeReference = loadClass(qualifiedName);
            JAssert.notNull(typeReference.getRawType(), "the class load " + qualifiedName + " failed");
            Class<?> clazz = typeReference.getRawType();
            JQuickJavaConstructorFactory<?> instance = JQuickJavaReflectionFactory.constructor(clazz);
            JQuickJavaTypeReference<?>[] references = model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getTypeArguments).toArray(JQuickJavaTypeReference[]::new);
            Object[] data = model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getData).toArray();
            return instance.newInstance(references, data);
        } catch (Exception e) {
            throw new RuntimeException("please double check constructor method   ", e);
        }
    }

    /**
     * 静态调用：ClassType::method(args)（原 visitStaticCall 语义）
     */
    private Object invokeStaticCall(JQuickJavaParser.PrimaryAtomContext ctx) {
        JAssert.notNull(ctx.classsType(), "the class name is not support");
        JAssert.notNull(ctx.methodName(), "the method name is not support");
        String qualifiedName = ctx.classsType().getText();
        String methodName = visitMethodName(ctx.methodName());
        JQuickJavaTypeReferenceAndValueModel model = new JQuickJavaTypeReferenceAndValueModel();
        if (ctx.argumentList() != null && ctx.argumentList().argument() != null && !ctx.argumentList().argument().isEmpty()) {
            model = visitArgumentList(ctx.argumentList());
        }
        try {
            JQuickJavaTypeReference<?> typeReference = loadClass(qualifiedName);
            Class<?> clazz = typeReference.getRawType();
            JQuickJavaStaticMethodFactory instance = JQuickJavaReflectionFactory.staticMethod(clazz);
            JQuickJavaTypeReference<?>[] references = model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getTypeArguments).toArray(JQuickJavaTypeReference[]::new);
            Object[] data = model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getData).toArray();
            return instance.invoke(methodName, references, data);
        } catch (Exception e) {
            throw new RuntimeException("please double check static method invocation : " + methodName, e);
        }
    }

    /**
     * Builtin::method(args)（原 visitBuiltinMethodCall 语义）
     */
    private Object invokeBuiltinCall(JQuickJavaParser.PrimaryAtomContext ctx) {
        JAssert.notNull(ctx.methodName(), "the method name is not support");
        String methodName = visitMethodName(ctx.methodName());
        JAssert.notNull(methodName, "the method name is not support");
        JQuickJavaTypeReferenceAndValueModel model = new JQuickJavaTypeReferenceAndValueModel();
        if (ctx.argumentList() != null && ctx.argumentList().argument() != null && !ctx.argumentList().argument().isEmpty()) {
            model = visitArgumentList(ctx.argumentList());
        }
        List<Object> args = model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getData).collect(Collectors.toList());
        return manager.invoke(methodName, args);
    }

    /**
     * this.method() —— DSL 函数执行（原 visitThisMethodCall 语义）
     */
    private Object invokeThisFunction(String methodName, JQuickJavaTypeReferenceAndValueModel model) {
        boolean flag = this.hasFunction(methodName);
        JAssert.isTrue(flag, "the method [ " + methodName + " ] did not define in this context");
        JQuickJavaTypeReference<?>[] references = model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getTypeArguments).toArray(JQuickJavaTypeReference[]::new);
        JQuickJavaFunctionDefinitionModel function = registry.lookupFunction(methodName, references);//find the best match method
        JAssert.notNull(function, "can't find function [" + methodName + "] based the parameter [ " + references + " ] you gived");
        List<Object> data = model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getData).collect(Collectors.toList());
        for (int i = 0; i < function.getFields().size(); i++) {
            JQuickJavaFunctionFieldModel field = function.getFields().get(i);
            Object value = data.get(i);
            if (field.getType().targetAssignableFrom(value)) {
                this.parser.declareVar(field.getFieldName(), value);
            } else {
                JAssert.throwNewException("the field [ " + field.getFieldName() + " ] param type mismatch in this context");
            }
        }
        JQuickJavaRuntimeEnvironment environment = new JQuickJavaRuntimeEnvironment(this.parser.getJContext(), this.parser.copyRuntimeEnvironment());
        JQuickJavaActionExecutor executor = new JQuickJavaActionExecutor(environment);
        Object object = executor.execute(function.getAction());
        if (null == object) {
            return null;
        } else {
            return mergeDataWithTypeReference(object.toString(), function.getReturnType());
        }
    }

    /**
     * A@b.method() —— 静态字段实例方法（原 visitAccessStaticMethodCall 语义）
     */
    private Object invokeAccessStaticMethod(JQuickJavaParser.PrimaryAtomContext atom, String methodName, JQuickJavaTypeReferenceAndValueModel model) {
        JAssert.notNull(atom.accessStaticVariable(), "the accessStaticVariable is not support");
        try {
            Object target = visitAccessStaticVariable(atom.accessStaticVariable());
            List<Object> args = model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getData).collect(Collectors.toList());
            return JQuickJavaObjectFactory.createByInstanceMethod(target, methodName, args);
        } catch (Exception e) {
            console.error("please double check static method invocation : " + methodName, e);
            throw new RuntimeException("please double check static method invocation : " + methodName, e);
        }
    }

    /**
     * primaryAtom.method() —— 反射实例方法（原 visitInstanceMethodCall 语义）
     */
    private Object invokeInstanceMethod(Object target, String methodName, JQuickJavaTypeReferenceAndValueModel model) {
        JAssert.notNull(target, "the instance target is not support");
        JQuickJavaTypeReference<?>[] references = model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getTypeArguments).toArray(JQuickJavaTypeReference[]::new);
        Object[] data = model.getList().stream().map(JQuickJavaTypeReferenceAndValue::getData).toArray();
        try {
            JQuickJavaInstanceMethodFactory instance = JQuickJavaReflectionFactory.instanceMethod(target);
            return instance.invoke(methodName, references, data);
        } catch (Exception e) {
            throw new RuntimeException("please double check constructor method   ", e);
        }
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

    /**
     * expression -> logical -> comparison -> additive -> multiplicative -> unary -> primary -> primaryAtom
     * PERFORMANCE FIX: 查找表达式树中是否包含 new 构造调用（primaryAtom 的 NEW 分支）。
     * 旧实现遍历 primary.methodInvocation()，postfix 树形下 new 位于 primaryAtom。
     */
    private JQuickJavaParser.PrimaryAtomContext findNewInExpression(JQuickJavaParser.ExpressionContext expr) {
        if (expr == null || expr.logical() == null) {
            return null;
        }
        for (JQuickJavaParser.ComparisonContext comparison : expr.logical().comparison()) {
            for (JQuickJavaParser.AdditiveContext additive : comparison.additive()) {
                for (JQuickJavaParser.MultiplicativeContext multiplicative : additive.multiplicative()) {
                    for (JQuickJavaParser.UnaryContext unary : multiplicative.unary()) {
                        JQuickJavaParser.PrimaryContext primary = unary.primary();
                        if (primary != null && primary.primaryAtom() != null && primary.primaryAtom().NEW() != null) {
                            return primary.primaryAtom();
                        }
                    }
                }
            }
        }
        return null;
    }


}
