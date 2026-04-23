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
//import com.github.paohaijiao.scope.JQuickJavaVariable;

public class JQuickJavaPrimaryVisitor extends JQuickJavaAssignVisitor {

    private static final Class<?> PKG = JQuickJavaPrimaryVisitor.class;

    @Override
    public Object visitPrimary(JQuickJavaParser.PrimaryContext ctx) {
        String t=ctx.getText();
        if(ctx.literal() != null) {
            return visitLiteral(ctx.literal());
        } else if (ctx.IDENTIFIER() != null) {
//            String identifier=ctx.IDENTIFIER().getText();
//            JQuickJavaVariable variable=currentContext().getVariable(identifier);
//            JAssert.isTrue(null!=variable,"variable "+identifier+" not found");;
//            return variable.getValue();
            return null;
        }else if (ctx.expression()!=null) {
            return visitExpression(ctx.expression());
        } else if (ctx.variableDecl()!=null) {
            return visitVariableDecl(ctx.variableDecl());
        } else if (ctx.methodInvocation()!=null) {
            return visit(ctx.methodInvocation());
        }
        return null;
    }


}
