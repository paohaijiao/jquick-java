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

import com.github.paohaijiao.parser.JQuickJavaParser;

public class JQuickJavaIfStatementVisitor extends JQuickJavaForStatementVisitor {

    private static final Class<?> PKG = JQuickJavaIfStatementVisitor.class;

    @Override
    public Object visitIfStatement(JQuickJavaParser.IfStatementContext ctx) {
        if (toBoolean(visitConExpression(ctx.conExpression()))) {
            Object result= visitAction(ctx.action());
            return result;
        }
        for (int i = 0; i < ctx.elseIfConExpression().size(); i++) {
            if (toBoolean(visitElseIfConExpression(ctx.elseIfConExpression(i)))) {
                Object result =  visitElseIfAction(ctx.elseIfAction(i));
                return result;
            }
        }
        if (null!=ctx.elseAction()) {
            return visitElseAction(ctx.elseAction());
        }
        return null;
    }
    @Override
    public Object visitElseIfAction(JQuickJavaParser.ElseIfActionContext ctx) {
         if(ctx.action()!=null) {
             Object result=visitAction(ctx.action());
             return result;
         }
         return null;
    }

    @Override
    public Object visitElseIfConExpression(JQuickJavaParser.ElseIfConExpressionContext ctx) {
        if(ctx.expression()!=null) {
            return visitExpression(ctx.expression());
        }
        return null;
    }

    @Override
    public Object visitElseAction(JQuickJavaParser.ElseActionContext ctx) {
        Object result=null;
        if(null!=ctx.action()) {
            result=visitAction(ctx.action());
        }
        return result;
    }
}
