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
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.model.JQuickJavaTypeReferenceAndValue;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.runtime.JQuickJavaRuntimeEnvironment;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JQuickJavaActionExecutor extends JAbstractAntlrExecutor<String, Object> {

    private  JContext context=new JContext();

    private  Map<String,Object> env=new HashMap<>();

    private JQuickJavaLexer lexer;

    private JQuickJavaParser parser;

    private TokenStream tokenStream;

    public  JQuickJavaActionExecutor(JQuickJavaRuntimeEnvironment environment) {
        JAssert.notNull(environment,"environment must not be null");
        if(environment.getContext()!=null&& !environment.getContext().isEmpty()){
            context.putAll(environment.getContext());
        }
        if(null!=environment.getEnvironment()&&!environment.getEnvironment().isEmpty()){
            env.putAll(environment.getEnvironment());
        }
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
        for (String key: env.keySet()) {
            actionPaser.declareVar(key, env.get(key));
        }
        actionPaser.init(context);
        JQuickJavaParser.ActionContext actionContext = actionPaser.action();
        CommonTokenStream commonTokenStream=(CommonTokenStream)tokenStream;
        JQuickJavaCommonVisitor visitor = new JQuickJavaCommonVisitor(lexer,commonTokenStream,actionPaser);
        Object object =visitor.visit(actionContext);
        return object;
    }
    public JContext getContext() {
        return this.context;
    }
}
