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
import com.github.paohaijiao.parser.JQuickJavaParser;

public class JQuickJavaPrimaryVisitor extends JQuickJavaAssignVisitor {

    private static final Class<?> PKG = JQuickJavaPrimaryVisitor.class;


    @Override
    public Object visitPrimary(JQuickJavaParser.PrimaryContext ctx) {
        Object value = visitPrimaryAtom(ctx.primaryAtom());
        for (int i = 0; i < ctx.postfix().size(); i++) {
            value = applyPostfix(ctx.primaryAtom(), ctx.postfix(i), value);
        }
        return value;
    }

    @Override
    public Object visitPrimaryAtom(JQuickJavaParser.PrimaryAtomContext ctx) {
        if (ctx.literal() != null) {
            return visitLiteral(ctx.literal());
        } else if (ctx.expression() != null) {
            return visitExpression(ctx.expression());
        } else if (ctx.this_() != null) {
            return ctx.this_().THIS().getText();
        } else if (ctx.accessStaticVariable() != null) {
            return visitAccessStaticVariable(ctx.accessStaticVariable());
        }
        return super.visitPrimaryAtom(ctx);
    }

    /**
     * postfix 钩子：.method(args) / .field。默认不支持，由子类（JQuickMethodInvocationCallVisitor）提供反射实现。
     */
    protected Object applyPostfix(JQuickJavaParser.PrimaryAtomContext atom, JQuickJavaParser.PostfixContext postfix, Object receiver) {
        throw new RuntimeException("postfix is not supported in this visitor: " + postfix.getText());
    }


}
