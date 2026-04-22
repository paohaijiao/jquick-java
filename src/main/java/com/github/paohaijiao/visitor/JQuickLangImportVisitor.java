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

import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.factory.JTypeReferenceFactory;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.support.JTypeReference;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class JQuickLangImportVisitor extends JQuickLangRegistryVisitor {
    @Override
    public Void visitImportDeclaration(JQuickJavaParser.ImportDeclarationContext ctx) {
        JAssert.notNull(ctx.paramType(),"missing paramType ");
        JAssert.notNull(ctx.importVar(),"missing qualified import variable");
        JTypeReference<?> typeReference=null;
        if(ctx.paramType() != null) {
            typeReference=visitParamType(ctx.paramType());
        }
        JAssert.notNull(typeReference,"typeReference not null");
        String var=null;
        if(ctx.importVar() != null) {
            var=ctx.importVar().getText();
        }
        JAssert.notNull(var,"var not null");
        JAssert.isTrue(!this.importContainer.existsIdentify(var),"var ["+var+"] akready has been  imported, can't be assign again");
        this.importContainer.addImport(var,typeReference);
        return null;
    }
    @Override
    public JTypeReference<?>  visitClasssType(JQuickJavaParser.ClasssTypeContext ctx) {
        if(ctx.importVar()!=null){
            return importContainer.get(ctx.importVar().getText());
        }else if(ctx.paramType()!=null){
            return visitParamType(ctx.paramType());
        }
        JAssert.throwNewException("visitFunctionType not implemented");
        return null;
    }

    @Override
    public JTypeReference<?> visitParamType(JQuickJavaParser.ParamTypeContext ctx) {
        if(ctx.simpleType()!=null){
            return   visitSimpleType(ctx.simpleType());
        }else if(ctx.genericType()!=null){
            String type=ctx.getText();
            JTypeReference<?> stringRef = JTypeReferenceFactory.fromTypeString(type);
            return stringRef;
        }else if(ctx.listType()!=null){
            JTypeReference<?> classType=visitClasssType(ctx.listType().classsType());
            JTypeReference<?> listRef = JTypeReferenceFactory.listFromElementType(classType.getRawType().getTypeName());
            return listRef;
        }else if(ctx.setType()!=null){
            JTypeReference<?> classType=visitClasssType(ctx.setType().classsType());
            JTypeReference<?> listRef = JTypeReferenceFactory.setFromElementType(classType.getRawType().getTypeName());
            return listRef;
        }else if(ctx.mapType()!=null){
            JAssert.isTrue( ctx.mapType().classsType().size()==2,"map type  have two parameters one for key , and one for value");
            JTypeReference<?> key=visitClasssType(ctx.mapType().classsType(0));
            JTypeReference<?> value=visitClasssType(ctx.mapType().classsType(1));
            String keyType=key.getRawType().getTypeName();
            String valueType=value.getRawType().getTypeName();
            JTypeReference<?> mapRef = JTypeReferenceFactory.mapFromTypes(keyType,valueType);
            return mapRef;
        }else if(ctx.arrayType()!=null){
            String type="";
            if(null!=ctx.arrayType().simpleType()){
                type=ctx.arrayType().simpleType().getText();
            }else{
                type=ctx.arrayType().qualifiedName().getText();
            }
            JTypeReference<?> arrayRef = JTypeReferenceFactory.arrayFromElementType(type);
            return arrayRef;
        }
        JAssert.throwNewException("unsupported type argument type");
        return null;
    }

    @Override
    public String visitQualifiedName(JQuickJavaParser.QualifiedNameContext ctx) {
        List<String> list=new ArrayList<>();
        if(ctx.IDENTIFIER()!=null&&!ctx.IDENTIFIER().isEmpty()){
            for (TerminalNode terminalNode:ctx.IDENTIFIER()){
                list.add(terminalNode.getText());
            }
        }
        return StringUtils.join(list, ".");
        }

    @Override
    public  JTypeReference<?>[] visitTypeArguments(JQuickJavaParser.TypeArgumentsContext ctx) {
        JAssert.notNull(ctx.classsType(),"typeArgument not null");
        JTypeReference<?>[] typeReference=new JTypeReference<?>[ctx.classsType().size()];
        if(ctx.classsType()!=null&&!ctx.classsType().isEmpty()){
           for (int i = 0; i < ctx.classsType().size(); i++) {
            typeReference[i]=visitClasssType(ctx.classsType().get(i));
           }
       }
        return typeReference;
    }
    @Override
    public JTypeReference visitSimpleType(JQuickJavaParser.SimpleTypeContext ctx) {
        if(ctx.TYPESHORT()!=null){
            return JTypeReference.of(short.class);
        }else if(ctx.TYPEINT()!=null){
            return JTypeReference.of(int.class);
        } else if (ctx.TYPEFLOAT()!=null) {
            return JTypeReference.of(float.class);
        }else if (ctx.TYPEDOUBLE()!=null){
            return JTypeReference.of(double.class);
        }else if (ctx.TYPELONG()!=null){
            return JTypeReference.of(long.class);
        }else if (ctx.TYPEBOOLEAN()!=null){
            return JTypeReference.of(boolean.class);
        }else if (ctx.TYPEBYTE()!=null){
            return JTypeReference.of(byte.class);
        }
        JAssert.throwNewException("unexpected type data type");
        return null;
    }


    protected JTypeReference<?> getComplexReferenceRef(String varType){
        JTypeReference<?> stringRef = JTypeReferenceFactory.fromClassName(varType);
        JTypeReference<?> listRef = JTypeReferenceFactory.listFromElementType(varType);
        JTypeReference<?> mapRef = JTypeReferenceFactory.mapFromTypes(varType, varType);
        JTypeReference<?> arrayRef = JTypeReferenceFactory.arrayFromElementType(varType);
        JTypeReference<?> complexRef = JTypeReferenceFactory.fromTypeString("java.util.Map<java.lang.String, java.util.List<java.lang.Integer>>");
        if(null!=listRef){
            return listRef;
        }else if(null!=mapRef){
            return mapRef;
        }else if(null!=arrayRef){
            return arrayRef;
        }else if(null!=complexRef){
            return complexRef;
        }else if(null!=stringRef){
            return stringRef;
        }
        JAssert.throwNewException("unsupported type argument type");
        return null;
    }




}
