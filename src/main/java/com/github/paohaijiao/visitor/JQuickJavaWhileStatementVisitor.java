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


import com.github.paohaijiao.exception.JBreakException;
import com.github.paohaijiao.exception.JContinueException;
import com.github.paohaijiao.parser.JQuickJavaParser;


public class JQuickJavaWhileStatementVisitor extends JQuickJavaReturnStatementVisitor {


    @Override
    public Object visitWhileStatement(JQuickJavaParser.WhileStatementContext ctx) {
        Object result = null;
        while (toBoolean(visit(ctx.expression()))) {
            enterScope();
            try {
                result = visitAction(ctx.action());
            } catch (Exception e) {
                if (e instanceof JContinueException) {
                    continue;
                }else if (e instanceof JBreakException) {
                    break;
                }else{
                    e.printStackTrace();
                }
            }finally{
                exitScope();
            }
        }
        return result;
    }

}
