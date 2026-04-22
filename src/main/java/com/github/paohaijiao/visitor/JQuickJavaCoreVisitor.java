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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.enums.JLiteralEnums;
import com.github.paohaijiao.executor.JQuickClassTypeExecutor;
import com.github.paohaijiao.factory.JFunctionRegistry;
import com.github.paohaijiao.model.JImportContainerModel;
import com.github.paohaijiao.model.JLiteralModel;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaBaseVisitor;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.scope.VariableContext;
import com.github.paohaijiao.support.JTypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.antlr.v4.runtime.CommonTokenStream;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Stack;


public class JQuickJavaCoreVisitor extends JQuickJavaBaseVisitor {

    protected JContext context;

    protected CommonTokenStream tokenStream;

    protected JQuickJavaLexer lexer ;

    protected JQuickJavaParser parser;

    protected Stack<VariableContext> contextStack = new Stack<>();

    protected VariableContext globalContext;

    protected Gson gson=new Gson();

    protected static JConsole console=new JConsole();

    protected JImportContainerModel importContainer=JImportContainerModel.getInstance();


    JFunctionRegistry registry= JFunctionRegistry.getInstance();

    protected void enterScope() {
        VariableContext current = contextStack.peek();
        contextStack.push(new VariableContext(current));
    }
    protected VariableContext currentContext() {
        return contextStack.peek();
    }

    protected void exitScope() {
        if (contextStack.size() > 1) {
            contextStack.pop();
        }
    }

    protected boolean toBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        throw new RuntimeException("cannot convert value to boolean: " + value);
    }
    protected JTypeReference<?> loadClass(String className){
        JQuickClassTypeExecutor executor = new JQuickClassTypeExecutor();
        return  executor.execute(className);
    }
    protected Object extract(Object value) {
        if(null==value){
            return null;
        }else if(value instanceof JLiteralModel){
            JLiteralModel literalModel = (JLiteralModel) value;
            return literalModel.getValue();
        }else{
            return value;
        }

    }
    protected JLiteralModel convert(Object value,String literal) {
        if(value==null){
            JLiteralModel model=new JLiteralModel();
            model.setValue(null);
            model.setLiteral("null");
            model.setType(JLiteralEnums.Null);
            return model;
        }else if(value instanceof JLiteralModel){
            return (JLiteralModel)value;
        }else{
            JLiteralModel model=new JLiteralModel();
            model.setValue(value);
            model.setLiteral(literal);
            JTypeReference<?> typeReference=JTypeReference.of(value.getClass());
            JLiteralEnums literalEnums=JLiteralEnums.typeOf(typeReference);
            model.setType(literalEnums);
            return model;
        }

    }
    public static boolean isMapTypeReference(TypeReference<?> typeReference) {
        Type type = typeReference.getType();
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.constructType(typeReference.getType());
        if (javaType != null && javaType.isMapLikeType()) {
            return true;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Type rawType = pType.getRawType();
            if (rawType instanceof Class<?>) {
                return Map.class.isAssignableFrom((Class<?>) rawType);
            }
        } else if (type instanceof Class<?>) {
            return Map.class.isAssignableFrom((Class<?>) type);
        }
        return false;

    }
    protected Object mergeDataWithTypeReference(String data,JTypeReference<?> typeReference){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (data == null || typeReference == null) {
                return null;
            }
            Type type = typeReference.getRawType();
            JTypeReference stringJTypeReference=JTypeReference.of(String.class);
            JTypeReference charSequenceJTypeReference=JTypeReference.of(CharSequence.class);
            boolean isString =type.equals(stringJTypeReference.getRawType());
            boolean isCharSequence =type.equals(charSequenceJTypeReference.getRawType());
            if(data instanceof String&&isString){
                return  data;
            }
            if(data instanceof String&&isCharSequence){
                return  data;
            }
            if (data instanceof String) {//data is tring but result not string
                if(isMapTypeReference(typeReference)){
                    return convertWithGson(data,typeReference);
                }
                return objectMapper.readValue(data, typeReference);
            }else{//data is not tring
                return objectMapper.convertValue(data, typeReference);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public <T> T convertWithGson(String json, TypeReference<T> jacksonTypeRef) {
        Gson gson = new Gson();
        Type gsonType = convertJacksonTypeToGsonType(jacksonTypeRef);
        return gson.fromJson(json, gsonType);
    }

    private Type convertJacksonTypeToGsonType(TypeReference<?> jacksonTypeRef) {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.constructType(jacksonTypeRef.getType());
        if (javaType.isMapLikeType()) {
            JavaType keyType = javaType.getKeyType();
            JavaType valueType = javaType.getContentType();

            return TypeToken.getParameterized(
                    Map.class,
                    convertJacksonTypeToGsonType(keyType),
                    convertJacksonTypeToGsonType(valueType)
            ).getType();
        }

        // 其他类型处理
        return javaType.getRawClass();
    }

    private Type convertJacksonTypeToGsonType(JavaType javaType) {
        if (javaType.isMapLikeType()) {
            return TypeToken.getParameterized(
                    Map.class,
                    convertJacksonTypeToGsonType(javaType.getKeyType()),
                    convertJacksonTypeToGsonType(javaType.getContentType())
            ).getType();
        }

        if (javaType.isCollectionLikeType()) {
            return TypeToken.getParameterized(
                    Collection.class,
                    convertJacksonTypeToGsonType(javaType.getContentType())
            ).getType();
        }

        if (javaType.isArrayType()) {
            return TypeToken.getArray(
                    convertJacksonTypeToGsonType(javaType.getContentType())
            ).getType();
        }

        return javaType.getRawClass();
    }
    public JFunctionRegistry getRegistry() {
        return registry;
    }

    public JImportContainerModel getImportContainer() {
        return importContainer;
    }
    public JContext getContext() {
        return context;
    }
    public void setContext(JContext context) {
        this.context = context;
    }
    public void setImportContainer(JImportContainerModel importContainer) {
        this.importContainer = importContainer;
    }

    public void updateVariableInStack(String varName, Object newValue, JTypeReference<?> type) {
        for (VariableContext context : contextStack) {
             context.updateVariableWithAllScopes(varName, newValue,type);
        }
    }

}
