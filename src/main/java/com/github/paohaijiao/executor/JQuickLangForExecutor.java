package com.github.paohaijiao.executor;

import com.github.paohaijiao.antlr.impl.JAbstractAntlrExecutor;
import com.github.paohaijiao.exception.JAntlrExecutionException;
import com.github.paohaijiao.model.JVariableContainerModel;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.visitor.JQuickLangCommonVisitor;
import org.antlr.v4.runtime.*;

import java.io.IOException;

public class JQuickLangForExecutor extends JAbstractAntlrExecutor<String, Object> {

    private  JContext context;

    private JQuickJavaLexer lexer;

    private JQuickJavaParser parser;

    private TokenStream tokenStream;

    public JQuickLangForExecutor() {
        this(new JContext(), new JVariableContainerModel());
    }

    public JQuickLangForExecutor(JContext context, JVariableContainerModel variableContainer) {
        initializeContext(context, variableContainer);
    }
    private void initializeContext(JContext context, JVariableContainerModel variableContainerModel) {
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
        JQuickLangCommonVisitor visitor = new JQuickLangCommonVisitor(context,lexer,commonTokenStream,actionPaser);
        return visitor.visit(actionContext);
    }
    public void intExecuteEnv(JContext context, JVariableContainerModel variableContainerModel) {
        this.initializeContext(context, variableContainerModel);
    }
    public JContext getContext() {
        return this.context;
    }
}
