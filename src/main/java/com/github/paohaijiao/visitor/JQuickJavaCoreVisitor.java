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
import com.github.paohaijiao.enums.JQuickJavaLiteralEnums;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.executor.JQuickJavaClassTypeExecutor;
import com.github.paohaijiao.factory.JQuickJavaFunctionRegistry;
import com.github.paohaijiao.i18n.I18nUtils;
import com.github.paohaijiao.model.JQuickJavaImportContainerModel;
import com.github.paohaijiao.model.JQuickJavaLiteralModel;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickJavaBaseVisitor;
import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.support.JQuickJavaTypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.antlr.v4.runtime.CommonTokenStream;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

@Data
public class JQuickJavaCoreVisitor extends JQuickJavaBaseVisitor {

    private static final Class<?> PKG = JQuickJavaCoreVisitor.class;

    protected JContext context;

    protected Locale local= Locale.ENGLISH;


    protected CommonTokenStream tokenStream;

    protected JQuickJavaLexer lexer ;

    protected JQuickJavaParser parser;


    protected Gson gson=new Gson();

    protected static JConsole console=new JConsole();

    protected JQuickJavaImportContainerModel importContainer= JQuickJavaImportContainerModel.getInstance();


    JQuickJavaFunctionRegistry registry= JQuickJavaFunctionRegistry.getInstance();



    protected boolean toBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        throw new RuntimeException("cannot convert value to boolean: " + value);
    }
    protected JQuickJavaTypeReference<?> loadClass(String className){
        JQuickJavaClassTypeExecutor executor = new JQuickJavaClassTypeExecutor();
        return  executor.execute(className);
    }
    protected Object extract(Object value) {
        if(null==value){
            return null;
        }else if(value instanceof JQuickJavaLiteralModel){
            JQuickJavaLiteralModel literalModel = (JQuickJavaLiteralModel) value;
            return literalModel.getValue();
        }else{
            return value;
        }

    }
    protected JQuickJavaLiteralModel convert(Object value, String literal) {
        if(value==null){
            JQuickJavaLiteralModel model=new JQuickJavaLiteralModel();
            model.setValue(null);
            model.setLiteral("null");
            model.setType(JQuickJavaLiteralEnums.Null);
            return model;
        }else if(value instanceof JQuickJavaLiteralModel){
            return (JQuickJavaLiteralModel)value;
        }else{
            JQuickJavaLiteralModel model=new JQuickJavaLiteralModel();
            model.setValue(value);
            model.setLiteral(literal);
            JQuickJavaTypeReference<?> typeReference= JQuickJavaTypeReference.of(value.getClass());
            JQuickJavaLiteralEnums literalEnums= JQuickJavaLiteralEnums.typeOf(typeReference);
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
    protected Object mergeDataWithTypeReference(String data, JQuickJavaTypeReference<?> typeReference){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (data == null || typeReference == null) {
                return null;
            }
            Type type = typeReference.getRawType();
            JQuickJavaTypeReference stringJTypeReference= JQuickJavaTypeReference.of(String.class);
            JQuickJavaTypeReference charSequenceJTypeReference= JQuickJavaTypeReference.of(CharSequence.class);
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
    public JQuickJavaFunctionRegistry getRegistry() {
        return registry;
    }

    public JQuickJavaImportContainerModel getImportContainer() {
        return importContainer;
    }
    public JContext getContext() {
        return context;
    }
    public void setContext(JContext context) {
        this.context = context;
    }
    public void setImportContainer(JQuickJavaImportContainerModel importContainer) {
        this.importContainer = importContainer;
    }

    private String getCurrentClassFullPath(Class<?> clazz) {
        return clazz.getName();
    }
    protected String getMessageKeyPrefix(Class<?> clazz, String prefix) {
        JAssert.notNull(clazz, "clazz required  not null");
        return getCurrentClassFullPath(clazz) + "." + prefix;
    }

    protected static String getI18N(String key,Object... value) {
        JAssert.notNull(key, "required key not null");
        String result = I18nUtils.getMessage("i18n/messages", key, value);
        return result;
    }
    protected static void thowEx(String key,Object... value) {
        JAssert.notNull(key, "required key not null");
        String msg=getI18N(key,value);
        JAssert.throwNewException(msg);
    }

}
