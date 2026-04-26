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
import com.github.paohaijiao.exception.JQuickJavaBreakException;
import com.github.paohaijiao.exception.JQuickJavaContinueException;
import com.github.paohaijiao.model.JQuickJavaReturnValueModel;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.support.JQuickJavaTypeReference;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JQuickJavaStatementVisitor extends JQuickJavaIfStatementVisitor {

    private static final Class<?> PKG = JQuickJavaStatementVisitor.class;

    @Override
    public Object visitControlStatement(JQuickJavaParser.ControlStatementContext ctx) {
        if (ctx.ifStatement() != null) {
        return  visit(ctx.ifStatement());
        } else if (ctx.forStatement() != null) {
            return visit(ctx.forStatement());
        } else if (ctx.whileStatement() != null) {
            return visit(ctx.whileStatement());
        } else if (ctx.returnStatement() != null) {
            return visit(ctx.returnStatement());
        } else if (ctx.expressionStatement() != null) {
            return visit(ctx.expressionStatement());
        }else if (ctx.breakStatement() != null) {
            return visitBreakStatement(ctx.breakStatement());
        } else if (ctx.continueStatement() != null) {
            return visitContinueStatement(ctx.continueStatement());
        }
        throw new RuntimeException("Unknown control statement");
    }
    @Override
    public Object visitStatement(JQuickJavaParser.StatementContext ctx) {
        String str=ctx.getText();
        if(ctx.expression() != null) {
            return visit(ctx.expression());
        } else if (null!=ctx.method()) {
            return visitMethod(ctx.method());
        } else if (null!=ctx.controlStatement()) {
            return visitControlStatement(ctx.controlStatement());
        }else if (null!=ctx.sout()) {
            return visitSout(ctx.sout());
        }
        throw new RuntimeException("Unknown  statement");
    }
    @Override
    public Object visitAccessStaticVariable(JQuickJavaParser.AccessStaticVariableContext ctx) {
        JAssert.notNull(ctx.classsType(),"can't access  className variable ["+"]");
        JAssert.notNull(ctx.accessObjectName(),"can't access static ObjectName variable ["+"]");
        JQuickJavaTypeReference<?> typeReference=visitClasssType(ctx.classsType());
        JAssert.notNull(typeReference,"can't access className ["+ctx.classsType().getText()+"]");
        String staticField=ctx.accessObjectName().getText();
        try{
            Field filed= typeReference.getRawType().getField(staticField);
            Object obj=filed.get(null);
            return obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        throw new RuntimeException("Unknown  accessStaticVariable");

    }

    @Override
    public Object visitMethod(JQuickJavaParser.MethodContext ctx) {
        if (ctx.functionDefinition() != null) {
            return visit(ctx.functionDefinition());
        } else if (ctx.methodInvocation() != null) {
            return visit(ctx.methodInvocation());
        }
        throw new RuntimeException("Unknown rule type");
    }
    @Override
    public Object visitAction(JQuickJavaParser.ActionContext ctx) {
        Object result = null;
        if(ctx.variableDecl()!=null&&!ctx.variableDecl().isEmpty()){
            for (JQuickJavaParser.VariableDeclContext stmt : ctx.variableDecl()) {
                result = visit(stmt);
            }
        }
        List<JQuickJavaParser.StatementContext> statements = ctx.statement();
        List<JQuickJavaParser.ControlStatementContext> controlStatements = ctx.controlStatement();
        for (JQuickJavaParser.StatementContext stmt : statements) {
            result = visit(stmt);
        }
        for (JQuickJavaParser.ControlStatementContext ctrlStmt : controlStatements) {
            result = visit(ctrlStmt);
            if (result instanceof JQuickJavaReturnValueModel) {
                return result;
            }
        }
        return result;
    }
    @Override
    public Void visitBreakStatement(JQuickJavaParser.BreakStatementContext ctx) {
        throw new JQuickJavaBreakException(new ArrayList<>());
    }
    @Override
    public Void visitContinueStatement(JQuickJavaParser.ContinueStatementContext ctx) {
        throw new JQuickJavaContinueException(new ArrayList<>());
    }
    @Override
    public Object visitSout(JQuickJavaParser.SoutContext ctx) {
        if (ctx.expression() != null) {
            Object object=visitExpression(ctx.expression());
            console.info(null==object?null:object.toString());
        }
        return null;
    }


}
