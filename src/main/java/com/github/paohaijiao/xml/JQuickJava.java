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
package com.github.paohaijiao.xml;

import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.executor.JQuickJavaActionExecutor;
import com.github.paohaijiao.executor.JQuickJavaExecutor;
import com.github.paohaijiao.factory.JQuickJavaTypeReferenceFactory;
import com.github.paohaijiao.model.JQuickJavaFunctionDefinitionModel;
import com.github.paohaijiao.model.JQuickJavaImportContainerModel;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.runtime.JQuickJavaRuntimeEnvironment;
import com.github.paohaijiao.support.JQuickJavaTypeReference;
import com.github.paohaijiao.transformer.JQuickValueTransformer;
import com.github.paohaijiao.xml.builder.JQuickXmlBuilder;
import com.github.paohaijiao.xml.factory.JQuickAbsFactory;
import com.github.paohaijiao.xml.invocation.JQuickEvaluateProcessor;
import com.github.paohaijiao.xml.invocation.JQuickXmlInvocationHandler;
import com.github.paohaijiao.xml.method.JQuickXmlMethod;
import com.github.paohaijiao.xml.namespace.JQuickXmlNamespace;
import com.github.paohaijiao.xml.parser.JQuickXmlParser;
import com.github.paohaijiao.xml.util.ParamUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JQuick unified entry class
 * <p>
 * Provides convenient encapsulation based on {@link JQuickJavaXmlParseFactory}:
 * <ul>
 *   <li>Package declaration: {@link #importPackage(String, String)} / {@link #importPackage(String)}, corresponds to JQuick syntax {@code import Type as Alias;}, takes effect for direct‑script execution path</li>
 *   <li>Variable initialization: constant via {@link #constant(String, Object)}, context variable via {@link #variable(String, Object)},
 *       runtime environment variable via {@link #env(String, Object)}, script‑level initialization statements via {@link #init(String...)}</li>
 *   <li>XML proxy: {@link #createApi(Class, String)}, generates interface dynamic proxy based on {@link JQuickJavaXmlParseFactory} + {@link JQuickXmlApiProxy}</li>
 *   <li>Direct script execution: {@link #execute(String)}, supports package declarations, initialization statements, function definition and invocation</li>
 * </ul>
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/8/23
 */

public class JQuickJava {

    private JConsole console=new JConsole();

    /** Context: holds constants and runtime variables */
    private final JContext context;

    /** Runtime environment: stores environment variables, injected into executor alongside {@link JQuickJavaRuntimeEnvironment} */
    private final Map<String, Object> environment;

    /** Package declaration (import statement), e.g. {@code import java.lang.String as type1;} */
    private final List<String> packageDeclarations;

    /** Script‑level initialization statement, e.g. {@code int x    = 10;} */
    private final List<String> initStatements;

    /** Inline rule script (method name -> function definition content, equivalent to CDATA of XML {@code <java>}), for integration with XML mode */
    private final Map<String, String> inlineRules;

    /** Return‑type metadata for inline rules (method name -> return type), only used for generating XML configuration model */
    private final Map<String, String> inlineRuleReturnClasses;

    public JQuickJava() {
        this(new JContext(), new HashMap<String, Object>());
    }

    public JQuickJava(JContext context) {
        this(context, new HashMap<String, Object>());
    }

    public JQuickJava(Map<String, Object> environment) {
        this(new JContext(), environment);
    }

    public JQuickJava(JContext context, Map<String, Object> environment) {
        JAssert.notNull(context, "context must not be null");
        JAssert.notNull(environment, "environment must not be null");
        this.context = context;
        this.environment = environment;
        this.packageDeclarations = new ArrayList<String>();
        this.initStatements = new ArrayList<String>();
        this.inlineRules = new LinkedHashMap<String, String>();
        this.inlineRuleReturnClasses = new LinkedHashMap<String, String>();
    }

    /**
     * Create default entry instance
     */
    public static JQuickJava create() {
        return new JQuickJava();
    }

    /**
     * 声明包引入（别名）
     * <p>
     * 生成 JQuick 语法：{@code import qualifiedName as alias;}
     * <p>
     * 示例：{@code importPackage("java.lang.String", "type1")} 生成 {@code import java.lang.String as type1;}
     *
     * @param qualifiedName 全限定类型名，支持泛型写法，如 {@code List<java.lang.String>}
     * @param alias         脚本内别名
     */
    public JQuickJava importPackage(String qualifiedName, String alias) {
        JAssert.notNull(qualifiedName, "qualifiedName must not be null");
        JAssert.notNull(alias, "alias must not be null");
        this.packageDeclarations.add("import " + qualifiedName + " as " + alias + ";");
        return this;
    }

    /**
     * Declare package import (auto‑generate alias)
     * <p>
     * The alias is derived from the segment after the last dot in the fully‑qualified name,
     * e.g. {@code java.lang.String} → {@code String}.
     * Alias cannot be auto‑resolved for generic types, please use {@link #importPackage(String, String)}.
     *
     * @param qualifiedName Fully‑qualified type name
     */
    public JQuickJava importPackage(String qualifiedName) {
        JAssert.notNull(qualifiedName, "qualifiedName must not be null");
        if (qualifiedName.indexOf('<') >= 0) {
            throw new IllegalArgumentException("generic import requires explicit alias, use importPackage(qualifiedName, alias)");
        }
        int idx = qualifiedName.lastIndexOf('.');
        String alias = idx >= 0 ? qualifiedName.substring(idx + 1) : qualifiedName;
        return importPackage(qualifiedName, alias);
    }

    /**
     * Declare package imports in batch (auto‑generate alias)
     */
    public JQuickJava importPackages(String... qualifiedNames) {
        JAssert.notNull(qualifiedNames, "qualifiedNames must not be null");
        for (String qualifiedName : qualifiedNames) {
            importPackage(qualifiedName);
        }
        return this;
    }

    /**
     * Get declared package import statements
     */
    public List<String> getPackageDeclarations() {
        return this.packageDeclarations;
    }

    /**
     * Initialize context variable
     */
    public JQuickJava constant(String name, Object value) {
        this.context.addConstant(name, value);
        return this;
    }

    /**
     * Initialize context variable
     */
    public JQuickJava variable(String name, Object value) {
        this.context.put(name, value);
        return this;
    }

    /**
     * Initialize context variable
     */
    public JQuickJava variables(Map<String, Object> variables) {
        if (variables != null && !variables.isEmpty()) {
            this.context.putAll(variables);
        }
        return this;
    }

    /**
     * Initialize environment variable
     */
    public JQuickJava env(String name, Object value) {
        this.environment.put(name, value);
        return this;
    }

    /**
     * Initialize environment variable
     */
    public JQuickJava envs(Map<String, Object> env) {
        if (env != null && !env.isEmpty()) {
            this.environment.putAll(env);
        }
        return this;
    }

    /**
     * Append script‑level initialization statements, e.g. {@code init("int base = 60;")}
     * <p>
     * These statements are concatenated to the head of script and executed after package declarations,
     * used for variable initialization inside scripts.
     */
    public JQuickJava init(String statement) {
        JAssert.notNull(statement, "init statement must not be null");
        this.initStatements.add(statement);
        return this;
    }

    /**
     * Append script‑level initialization statements in batch
     */
    public JQuickJava init(String... statements) {
        JAssert.notNull(statements, "init statements must not be null");
        for (String statement : statements) {
            init(statement);
        }
        return this;
    }

    /**
     * Get script‑level initialization statements
     */
    public List<String> getInitStatements() {
        return this.initStatements;
    }


    /**
     * Register inline rule script to create API in combination with XML mode
     * <p>
     * Content is complete function definition (equivalent to CDATA content of XML {@code <java>} element), for example:
     * {@code rule("sum", "int def sum(int:a,int:b){ return a+b; }")}
     * <p>
     * The function name must match the interface method name; it is recommended to use {@link Param} annotation for naming interface method parameters.
     * Inline rules are executed via XML proxy mechanism (parameter binding, return‑type conversion, context injection),
     * and support entry‑level package declarations and script‑level initialization statements.
     *
     * @param methodName        Interface method name
     * @param functionDefinition Function definition script
     */
    public JQuickJava rule(String methodName, String functionDefinition) {
        JAssert.notNull(methodName, "methodName must not be null");
        JAssert.notNull(functionDefinition, "functionDefinition must not be null");
        this.inlineRules.put(methodName, functionDefinition);
        return this;
    }

    /**
     * Register inline rule script and specify return‑type metadata
     *
     * @param methodName        Interface method name
     * @param returnClass       Return type (metadata only; actual return type is determined by interface method)
     * @param functionDefinition Function definition script
     */
    public JQuickJava rule(String methodName, String returnClass, String functionDefinition) {
        rule(methodName, functionDefinition);
        this.inlineRuleReturnClasses.put(methodName, returnClass);
        return this;
    }

    /**
     * Register inline rule scripts in batch (method name -> function definition content)
     */
    public JQuickJava rules(Map<String, String> functionDefinitions) {
        JAssert.notNull(functionDefinitions, "functionDefinitions must not be null");
        this.inlineRules.putAll(functionDefinitions);
        return this;
    }

    /**
     * Get registered inline rule scripts (method name -> function definition content)
     */
    public Map<String, String> getInlineRules() {
        return this.inlineRules;
    }


    /** Package declaration line parsing template: {@code import fully‑qualified‑type‑name as alias;}
     * (non‑greedy matching for alias to avoid consuming trailing semicolon)
     */
    private static final Pattern IMPORT_PATTERN = Pattern.compile("^import\\s+(.+?)\\s+as\\s+(\\S+?)\\s*;?\\s*$");

    /**
     * Parse alias from package declaration line
     *
     * @param declaration Package declaration line, e.g. {@code import java.lang.String as type1;}
     * @return Alias, returns null if format does not match
     */
    private String extractImportAlias(String declaration) {
        Matcher matcher = IMPORT_PATTERN.matcher(declaration == null ? "" : declaration.trim());
        return matcher.matches() ? matcher.group(2) : null;
    }

    /**
     * Idempotently register package declaration into global import container
     * <p>
     * The parser resolves type aliases (e.g. {@code type1}) within scripts in the form of
     * {@code alias -> type reference}.
     * This container is a global singleton. Direct registration (overwrites on duplicate entries)
     * avoids throwing "already has been imported" exceptions when scripts are executed repeatedly
     * within the same JVM.
     */
    private void registerImports() {
        JQuickJavaImportContainerModel container = JQuickJavaImportContainerModel.getInstance();
        for (String declaration : this.packageDeclarations) {
            Matcher matcher = IMPORT_PATTERN.matcher(declaration.trim());
            if (!matcher.matches()) {
                continue;
            }
            String qualifiedName = matcher.group(1).trim();
            String alias = matcher.group(2);
            JQuickJavaTypeReference<?> typeReference = JQuickJavaTypeReferenceFactory.fromTypeString(qualifiedName);
            if (typeReference == null) {
                typeReference = JQuickJavaTypeReferenceFactory.fromClassName(qualifiedName);
            }
            if (typeReference != null) {
                container.addImport(alias, typeReference);
            }
        }
    }

    /**
     * Backup and clear parser static context, restore after execution completes
     * <p>
     * {@link JQuickJavaParser#context} is static, and parser variable resolution takes precedence over scope stack.
     * Without isolation, variables injected by one script execution will pollute subsequent invocations
     * (e.g. proxy parameter names may be incorrectly resolved by later scripts).
     */
    private static void restoreStaticParserContext(Map<String, Object> backup) {
        JQuickJavaParser.context.clear();
        JQuickJavaParser.context.putAll(backup);
    }


    public JContext getContext() {
        return this.context;
    }

    public Map<String, Object> getEnvironment() {
        return this.environment;
    }

    /**
     * Build runtime environment
     */
    public JQuickJavaRuntimeEnvironment buildRuntimeEnvironment() {
        return new JQuickJavaRuntimeEnvironment(this.context, this.environment);
    }

    /**
     * Build XML parse factory (based on {@link JQuickJavaXmlParseFactory})
     */

    public JQuickJavaXmlParseFactory handler() {
        return new JQuickJavaXmlParseFactory(buildRuntimeEnvironment());
    }


    /**
     * Create XML rule proxy API
     * <p>
     * Reads configuration from xmlPath via {@link JQuickXmlFactory}, and generates proxy according to apiInterface.
     * Registered inline rules ({@link #rule(String, String)}) will be merged into the namespace of this interface.
     * For methods with identical names, inline rules take precedence.
     * Context variables and runtime environment variables initialized in entry point are injected into execution chain
     * through {@link JQuickJavaXmlParseFactory}.
     *
     * @param apiInterface Rule interface, must match {@code namespace} defined in XML
     * @param xmlPath      Path of XML rule file under classpath
     */
    public <T> T createApi(Class<T> apiInterface, String xmlPath) {
        return createApiInternal(apiInterface, xmlPath);
    }

    /**
     * Create pure inline‑rule proxy API (no XML file dependency)
     * <p>
     * Based on inline rules registered via {@link #rule(String, String)}, it adopts the same execution mechanism
     * as XML proxy (parameter binding, return‑type conversion, context injection), and additionally supports
     * package declarations and script‑level initialization statements.
     *
     * @param apiInterface Rule interface
     */

    public <T> T createApi(Class<T> apiInterface) {
        return createApiInternal(apiInterface, null);
    }

    /**
     * Create proxy API combining inline rules and XML
     */
    private <T> T createApiInternal(Class<T> apiInterface, String xmlPath) {
        JQuickJavaXmlParseFactory parseFactory = handler();
        Map<String, JQuickXmlNamespace> namespaceMap;
        if (xmlPath != null && !xmlPath.isEmpty()) {
            namespaceMap = new JQuickXmlParser(parseFactory.createJQuickXmlElement()).parse(xmlPath);
        } else {
            namespaceMap = new HashMap<String, JQuickXmlNamespace>();
        }
        if (!inlineRules.isEmpty()) {
            JQuickXmlNamespace namespace = namespaceMap.get(apiInterface.getName());
            if (namespace == null) {
                namespace = JQuickXmlBuilder.create().namespace(apiInterface.getName()).build();
                namespaceMap.put(apiInterface.getName(), namespace);
            }
            for (Map.Entry<String, String> entry : inlineRules.entrySet()) {
                JQuickXmlMethod method = new JQuickXmlMethod();
                method.setName(entry.getKey());
                method.setReturnClass(inlineRuleReturnClasses.getOrDefault(entry.getKey(), Object.class.getName()));
                method.setContent(entry.getValue());
                method.setTag(INLINE_TAG);
                namespace.addMethod(entry.getKey(), method);
            }
        }
        JQuickInlineMethodInvocationHandler invocationHandler = new JQuickInlineMethodInvocationHandler(buildRuntimeEnvironment());
        JQuickInlineXmlFactory factory = new JQuickInlineXmlFactory(namespaceMap, this.context, invocationHandler);
        return factory.createApi(apiInterface);
    }

    /** Marker for inline‑rule methods, used to distinguish inline methods from XML‑defined methods */
    private static final String INLINE_TAG = "inline";

    /**
     * Assemble complete script: package declarations + script‑level initialization statements + script body
     * <p>
     * Aliases already registered to global import container will be skipped (no import lines generated),
     * so as to avoid exceptions caused by duplicate registration in static import container on repeated execution within same JVM.
     */

    public String buildScript(String scriptBody) {
        JAssert.notNull(scriptBody, "scriptBody must not be null");
        StringBuilder sb = new StringBuilder();
        for (String pkg : this.packageDeclarations) {
            String alias = extractImportAlias(pkg);
            if (alias != null && JQuickJavaImportContainerModel.getInstance().existsIdentify(alias)) {
                continue;
            }
            sb.append(pkg).append("\n");
        }
        for (String stmt : this.initStatements) {
            sb.append(stmt).append("\n");
        }
        sb.append(scriptBody);
        return sb.toString();
    }

    /**
     * Inline rule execution handler
     * <p>
     * Both inline methods and XML rules go through the same execution chain (parse function definition → execute function‑body action).
     * Therefore inline rules can be treated equivalently as CDATA content of XML {@code <java>}, with support for package declarations and context variables.
     */
    private class JQuickInlineMethodInvocationHandler extends JQuickJavaXmlInvocationHandler {
        JQuickInlineMethodInvocationHandler(JQuickJavaRuntimeEnvironment environment) {
            super(environment);
        }

        @Override
        protected Object execute(JQuickXmlMethod xmlMethod, JContext context, Method method, Object[] args) throws IOException {
            if (!INLINE_TAG.equals(xmlMethod.getTag())) {
                return super.execute(xmlMethod, context, method, args);
            }
            String content = xmlMethod.getContent();
            JContext jContext = new JContext();
            jContext.putAll(context);
            String dynamicParsedContent = JQuickEvaluateProcessor.parse(content, jContext);
            String lexer = replaceVariables(dynamicParsedContent, jContext);
            return loadResult(lexer, context, method, args);
        }

        @Override
        protected Object loadResult(String lexerStr, JContext methodContext, Method method, Object[] args) {
            registerImports();
            ParamUtil paramUtil = new ParamUtil();
            Map<String, Object> paramMap = paramUtil.bindParams(method, args);
            Map<String, Object> effectiveEnv = new HashMap<String, Object>(JQuickJava.this.environment);
            if (paramMap != null && !paramMap.isEmpty()) {
                effectiveEnv.putAll(paramMap);
            }
            Set<String> paramNames = paramMap != null ? paramMap.keySet() : Collections.<String>emptySet();
            JContext runtimeContext = new JContext();
            for (Map.Entry<String, Object> entry : JQuickJava.this.context.entrySet()) {
                if (!paramNames.contains(entry.getKey())) {
                    runtimeContext.put(entry.getKey(), entry.getValue());
                }
            }
            Map<String, Object> staticBackup = new HashMap<String, Object>(JQuickJavaParser.context);
            try {
                JQuickJavaParser.context.clear();
                JQuickJavaFunctionDefinitionModel define = functionDefinition(lexerStr);
                JQuickJavaRuntimeEnvironment runtimeEnvironment = new JQuickJavaRuntimeEnvironment(runtimeContext, effectiveEnv);
                JQuickJavaActionExecutor executor = new JQuickJavaActionExecutor(runtimeEnvironment);
                Long start=System.currentTimeMillis();
                Object object = executor.execute(define.getAction());
                double end=(System.currentTimeMillis()-start)/1000.0;
                console.info("execute time:"+end+"s");
                if (object == null) {
                    return null;
                }
                com.github.paohaijiao.transformer.type.JQuickJavaTypeReference reference = com.github.paohaijiao.transformer.type.JQuickJavaTypeReference.of(method.getReturnType());
                return new JQuickValueTransformer().transform(object.toString(), reference);
            } finally {
                restoreStaticParserContext(staticBackup);
            }
        }
    }

    /**
     * Inline rule proxy factory, directly assembles namespace, context and execution handler
     */
    private static class JQuickInlineXmlFactory extends JQuickAbsFactory {

        JQuickInlineXmlFactory(Map<String, JQuickXmlNamespace> namespaceMap, JContext context, JQuickXmlInvocationHandler invocationHandler) {
            this.namespaceMap = namespaceMap;
            this.context = context;
            this.invocationHandler = invocationHandler;
        }
    }

    /**
     * Directly execute JQuick script (program path, package declarations supported)
     * <p>
     * Script body can be: function definitions plus invocations, expressions, control statements, etc.
     * Context variables and runtime environment variables are injected into parser before execution,
     * and can be referenced directly within script.
     *
     * @param scriptBody Script body (excluding package declarations and initialization statements,
     *                   which are uniformly injected by entry point)
     * @return Script execution result
     */
    public Object execute(String scriptBody) {
        registerImports();
        String fullScript = buildScript(scriptBody);
        Map<String, Object> staticBackup = new HashMap<String, Object>(JQuickJavaParser.context);
        try {
            JQuickJavaParser.context.clear();
            JQuickJavaParser.context.putAll(this.context);
            JQuickJavaParser.context.putAll(this.environment);
            JQuickJavaExecutor executor = JQuickJavaExecutor.getInstance(this.context);
            executor.setCurrentContext(this.context);
            return executor.execute(fullScript);
        } finally {
            restoreStaticParserContext(staticBackup);
        }
    }
}
