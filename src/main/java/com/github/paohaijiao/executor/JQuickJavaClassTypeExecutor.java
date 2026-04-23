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
import com.github.paohaijiao.support.JQuickJavaTypeReference;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import org.antlr.v4.runtime.*;

import java.util.Stack;

/**
 * packageName com.github.paohaijiao.executor
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/8/6
 */
public class JQuickJavaClassTypeExecutor extends JAbstractAntlrExecutor<String, JQuickJavaTypeReference<?>> {

    private JQuickJavaLexer lexer;

    private JQuickJavaParser parser;

    private TokenStream tokenStream;

    private JContext context;

    public JQuickJavaClassTypeExecutor(){
        context=new JContext();
    }
    public JQuickJavaClassTypeExecutor(JContext context){
        context=context;
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
    protected JQuickJavaTypeReference<?> parse(Parser parser) throws JAntlrExecutionException {
        JQuickJavaParser calcParser = (JQuickJavaParser) parser;
        JQuickJavaParser.ClasssTypeContext tree = calcParser.classsType();
        CommonTokenStream commonTokenStream=(CommonTokenStream)tokenStream;
        JQuickJavaCommonVisitor visitor = new JQuickJavaCommonVisitor(context,lexer,commonTokenStream,calcParser);
        JQuickJavaTypeReference<?> object=(JQuickJavaTypeReference<?>)visitor.visit(tree);
        return object;
    }
}
