package com.github.paohaijiao.executor;

import com.github.paohaijiao.antlr.impl.JAbstractAntlrExecutor;
import com.github.paohaijiao.exception.JAntlrExecutionException;
import com.github.paohaijiao.model.JQuickJavaVariableContainerModel;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import org.antlr.v4.runtime.*;

import java.io.IOException;

public class JQuickJavaForExecutor extends JAbstractAntlrExecutor<String, Object> {

    private  JContext context;

    private JQuickJavaLexer lexer;

    private JQuickJavaParser parser;

    private TokenStream tokenStream;

    public JQuickJavaForExecutor() {
        this(new JContext(), new JQuickJavaVariableContainerModel());
    }

    public JQuickJavaForExecutor(JContext context, JQuickJavaVariableContainerModel variableContainer) {
        initializeContext(context, variableContainer);
    }
    private void initializeContext(JContext context, JQuickJavaVariableContainerModel variableContainerModel) {
        this.context = context;
        if (context != null) {
            for (String key : context.keySet()) {
                this.context.put(key, context.get(key));
            }
        }
        if (variableContainerModel != null) {
            for (String key : variableContainerModel.keySet()) {
                this.context.put(key, variableContainerModel.get(key));
            }
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
        JQuickJavaParser.ForStatementContext actionContext = actionPaser.forStatement();
        CommonTokenStream commonTokenStream=(CommonTokenStream)tokenStream;
        JQuickJavaCommonVisitor visitor = new JQuickJavaCommonVisitor(lexer,commonTokenStream,actionPaser);
        return visitor.visit(actionContext);
    }
    public void intExecuteEnv(JContext context, JQuickJavaVariableContainerModel variableContainerModel) {
        this.initializeContext(context, variableContainerModel);
    }
    public JContext getContext() {
        return this.context;
    }
}
