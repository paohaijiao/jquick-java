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
import com.github.paohaijiao.banner.JQuickBanner;
import com.github.paohaijiao.banner.impl.JQuickBannerImpl;
import com.github.paohaijiao.config.JQuickBannerConfig;
import com.github.paohaijiao.exception.JAntlrExecutionException;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.visitor.JQuickJavaCommonVisitor;
import org.antlr.v4.runtime.*;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

public class JQuickJavaExecutor extends JAbstractAntlrExecutor<String, Object> {

    private static volatile JQuickJavaExecutor instance;

    private static final AtomicBoolean bannerLoaded = new AtomicBoolean(false);

    private final ThreadLocal<JContext> threadLocalContext;

    private JQuickJavaExecutor() {
        this.threadLocalContext = ThreadLocal.withInitial(JContext::new);
        loadBannerOnce();
    }
    private JQuickJavaExecutor(JContext context) {
        this.threadLocalContext = ThreadLocal.withInitial(() -> context);
        loadBannerOnce();
    }
    private JQuickJavaLexer lexer;

    private JQuickJavaParser parser;

    private TokenStream tokenStream;

    private JContext context;

    private void loadBannerOnce() {
        if (bannerLoaded.compareAndSet(false, true)) {
            JQuickBannerConfig config=new JQuickBannerConfig();
            JQuickBanner banner=new JQuickBannerImpl(config);
            banner.printBanner();
        }
    }
    /**
     * 获取单例实例（使用默认的 JContext）
     */
    public static JQuickJavaExecutor getInstance() {
        if (instance == null) {
            synchronized (JQuickJavaExecutor.class) {
                if (instance == null) {
                    instance = new JQuickJavaExecutor();
                }
            }
        }
        return instance;
    }  /**
     * 获取单例实例（使用指定的 JContext）
     * 注意：此方法会替换 ThreadLocal 的初始值
     */
    public static JQuickJavaExecutor getInstance(JContext context) {
        if (instance == null) {
            synchronized (JQuickJavaExecutor.class) {
                if (instance == null) {
                    instance = new JQuickJavaExecutor(context);
                }
            }
        } else {
            // 如果实例已存在，更新 ThreadLocal 的初始值
            instance.setDefaultContext(context);
        }
        return instance;
    }

    /**
     * 设置默认上下文（会影响新线程的初始上下文）
     */
    public void setDefaultContext(JContext context) {
        this.threadLocalContext.set(context);
    }

    /**
     * 获取当前线程的上下文
     */
    public JContext getCurrentContext() {
        return threadLocalContext.get();
    }

    /**
     * 设置当前线程的上下文
     */
    public void setCurrentContext(JContext context) {
        threadLocalContext.set(context);
    }

    /**
     * 清除当前线程的上下文
     */
    public void clearCurrentContext() {
        threadLocalContext.remove();
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
        JQuickJavaParser calcParser = (JQuickJavaParser) parser;
        JQuickJavaParser.ProgramContext tree = calcParser.program();
        CommonTokenStream commonTokenStream=(CommonTokenStream)tokenStream;
        JQuickJavaCommonVisitor visitor = new JQuickJavaCommonVisitor(context,lexer,commonTokenStream,calcParser);
        Object object=visitor.visit(tree);
        return object;
    }
}
