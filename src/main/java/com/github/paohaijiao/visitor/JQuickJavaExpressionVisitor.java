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

public class JQuickJavaExpressionVisitor extends JQuickJavaBoolVisitor {

    @Override
    public Object visitExpressionStatement(JQuickJavaParser.ExpressionStatementContext ctx) {
        if (ctx.expression() != null) {
            Object value= visitExpression(ctx.expression());
            return value;
        }
        throw new RuntimeException("unexpected expression statement");
    }
    @Override
    public Object visitExpression(JQuickJavaParser.ExpressionContext ctx) {
        if(ctx.logical()!=null) {
            Object value =visitLogical(ctx.logical());
            return value;
        }
        if(ctx.arithmetic()!=null) {
            Object value = visitArithmetic(ctx.arithmetic());
            return value;
        }
        if(ctx.primary()!=null) {
            Object value = visitPrimary(ctx.primary());
            return value;
        }
        if(ctx.methodInvocation()!=null) {
            Object value = visit(ctx.methodInvocation());
            return value;
        }
        throw new RuntimeException("unexpected expression");
    }


}
