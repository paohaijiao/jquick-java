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
package com.github.paohaijiao.executor;

import com.github.paohaijiao.antlr.impl.JAbstractAntlrExecutor;
import com.github.paohaijiao.exception.JAntlrExecutionException;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.scope.JQuickJavaVariableContext;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.util.Stack;

public class JQuickJavaActionExecutor extends JAbstractAntlrExecutor<String, Object> {

    private  JContext context;

    private JQuickJavaLexer lexer;

    private JQuickJavaParser parser;

    private TokenStream tokenStream;

    private  Stack<JQuickJavaVariableContext> stack = new Stack<>();

    public JQuickJavaActionExecutor() {
        this(new JContext(), new Stack<JQuickJavaVariableContext>());
    }
    public JQuickJavaActionExecutor(JContext context) {
        this(context, new Stack<JQuickJavaVariableContext>());
    }
    public JQuickJavaActionExecutor(JContext context, Stack<JQuickJavaVariableContext> contextStack) {
        initializeContext(context, contextStack);
    }
    private void initializeContext(JContext context, Stack<JQuickJavaVariableContext> contextStack) {
        this.context = context;
        this.stack = contextStack;

    }

    @Override
    protected Lexer createLexer(CharStream input) {
        this.lexer= new JQuickJavaLexer(input);
        return lexer;
    }

    @Override
    protected Parser createParser(TokenStream tokens) {
        this.tokenStream=tokens;
        this.parser= new JQuickJavaParser(tokens);
        return parser;
    }
    @Override
    public Object execute(String input) throws JAntlrExecutionException {
        try {
            CharStream charStream =null;
            try{
                charStream = this.createCharStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Lexer lexer = this.createLexer(charStream);
            this.setupLexerErrorHandling(lexer);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            Parser parser = this.createParser(tokens);
            this.setupParserErrorHandling(parser);
            return this.parse(parser);
        } catch (JAntlrExecutionException e) {
            throw e;
        }
    }

    @Override
    protected Object parse(Parser parser) throws JAntlrExecutionException {
        JQuickJavaParser actionPaser = (JQuickJavaParser) parser;
        JQuickJavaParser.ActionContext actionContext = actionPaser.action();
        CommonTokenStream commonTokenStream=(CommonTokenStream)tokenStream;
        JQuickJavaCommonVisitor visitor = new JQuickJavaCommonVisitor(context,this.stack,lexer,commonTokenStream,actionPaser);
        Object object =visitor.visit(actionContext);
        return object;
    }
    public void intExecuteEnv(JContext context, Stack<JQuickJavaVariableContext> contextStack) {
        this.initializeContext(context, contextStack);
    }
    public JContext getContext() {
        return this.context;
    }
}
