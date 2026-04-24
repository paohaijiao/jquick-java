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
import com.github.paohaijiao.model.JQuickJavaLiteralModel;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.support.JQuickJavaTypeReference;

public class JQuickJavaAssignVisitor extends JQuickJavaLiteralVisitor {

    private static final Class<?> PKG = JQuickJavaAssignVisitor.class;

    @Override
    public Object visitVariableDecl(JQuickJavaParser.VariableDeclContext ctx) {
        JAssert.notNull(ctx.IDENTIFIER(),"identifier required not null");
        JAssert.notNull(ctx.expression(),"expression required not null");
        String varName = ctx.IDENTIFIER().getText();
        if(ctx.classsType() != null){// define
            JQuickJavaTypeReference<?> typeRef=visitClasssType(ctx.classsType());
            Object express=visitExpression(ctx.expression());
            String string=null==express?null:express.toString();
            Object value=mergeDataWithTypeReference(string,typeRef);
            this.parser.declareVar(varName,value);
            return value;
        }else{//update
            Object value = ctx.expression() != null ? visitExpression(ctx.expression()) : null;
            this.parser.declareVar(varName,value);
            return value;
        }
    }

}
