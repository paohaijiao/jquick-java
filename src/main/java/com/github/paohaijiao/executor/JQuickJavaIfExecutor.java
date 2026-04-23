package com.github.paohaijiao.executor;

import com.github.paohaijiao.antlr.impl.JAbstractAntlrExecutor;
import com.github.paohaijiao.exception.JAntlrExecutionException;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.util.Stack;

public class JQuickJavaIfExecutor extends JAbstractAntlrExecutor<String, Object> {

    private  JContext context=new JContext();

    private JQuickJavaLexer lexer;

    private JQuickJavaParser parser;

    private TokenStream tokenStream;


    public JQuickJavaIfExecutor() {
    }

    public JQuickJavaIfExecutor(JContext jContext ) {
        initializeContext(jContext);
    }
    private void initializeContext(JContext jContext) {
        this.context.putAll(jContext);
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
        JQuickJavaParser.IfStatementContext actionContext = actionPaser.ifStatement();
        CommonTokenStream commonTokenStream=(CommonTokenStream)tokenStream;
        JQuickJavaCommonVisitor visitor = new JQuickJavaCommonVisitor(context,lexer,commonTokenStream,actionPaser);
        return visitor.visit(actionContext);
    }
    public void intExecuteEnv(JContext context) {
        this.initializeContext(context);
    }
    public JContext getContext() {
        return this.context;
    }
}
