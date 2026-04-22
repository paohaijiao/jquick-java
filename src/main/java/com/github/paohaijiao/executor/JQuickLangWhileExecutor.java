package com.github.paohaijiao.executor;

import com.github.paohaijiao.antlr.impl.JAbstractAntlrExecutor;
import com.github.paohaijiao.exception.JAntlrExecutionException;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.scope.VariableContext;
import com.github.paohaijiao.visitor.JQuickLangCommonVisitor;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.util.Stack;

public class JQuickLangWhileExecutor extends JAbstractAntlrExecutor<String, Object> {

    private  JContext context;

    private JQuickJavaLexer lexer;

    private JQuickJavaParser parser;

    private TokenStream tokenStream;

    private Stack<VariableContext> contextStack;

    public JQuickLangWhileExecutor() {
        this(new JContext(), new Stack<>());
    }

    public JQuickLangWhileExecutor(JContext context, Stack<VariableContext> contextStack) {
        initializeContext(context, contextStack);
    }
    private void initializeContext(JContext context, Stack<VariableContext> contextStack) {
        this.context = context;
        this.contextStack = contextStack;
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
    protected Object parse(Parser parser) throws JAntlrExecutionException {
        JQuickJavaParser actionPaser = (JQuickJavaParser) parser;
        JQuickJavaParser.WhileStatementContext actionContext = actionPaser.whileStatement();
        CommonTokenStream commonTokenStream=(CommonTokenStream)tokenStream;
        JQuickLangCommonVisitor visitor = new JQuickLangCommonVisitor(context,this.contextStack,lexer,commonTokenStream,actionPaser);
        return visitor.visit(actionContext);
    }
    public void intExecuteEnv(JContext context, Stack<VariableContext> contextStack) {
        this.initializeContext(context, contextStack);
    }
    public JContext getContext() {
        return this.context;
    }
}
