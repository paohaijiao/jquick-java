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

import com.github.paohaijiao.constants.JConstants;
import com.github.paohaijiao.date.JDateUtil;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.i18n.I18nUtils;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.scope.Variable;
import com.github.paohaijiao.scope.VariableContext;
import com.github.paohaijiao.support.JTypeReference;
import com.github.paohaijiao.util.JStringUtils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class JQuickJavaValueVisitor extends JQuickJavaImportVisitor {
    private static final Class<?> PKG = JQuickJavaValueVisitor.class;


    @Override
    public Object visitVariables(JQuickJavaParser.VariablesContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        Object value= this.context.get(identifier);
        return value;
    }

    @Override
    public Date visitDate(JQuickJavaParser.DateContext ctx) {
        if (null != ctx.DATE()) {
            String dateString = ctx.DATE().getText();
            Date date = JDateUtil.parse(JDateUtil.getSimpleDateFormat(JConstants.date), dateString);
            return date;
        } else if (null != ctx.DATETIME()) {
            String dateString = ctx.DATETIME().getText();
            Date date = JDateUtil.parse(JDateUtil.getSimpleDateFormat(JConstants.dateTime), dateString);
            thowEx(getMessageKeyPrefix(PKG,"date"),ctx.getText());
            return date;
        }

        return null;
    }

    @Override
    public String visitString(JQuickJavaParser.StringContext ctx) {
        if (ctx.STRING() != null) {
            String text = ctx.STRING().getText();
            String str = JStringUtils.trim(text);
            return str;
        }
        I18nUtils.getMessage("");
        JAssert.throwNewException("invalid string: " + ctx.getText());
        return null;
    }


    @Override
    public Boolean visitBool(JQuickJavaParser.BoolContext ctx) {
        if (ctx.TRUE() != null) {
            return true;
        } else if (ctx.FALSE() != null) {
            return false;
        }
        JAssert.throwNewException("Unsupported Boolean format: " + ctx.getText());
        return null;
    }
    @Override
    public Object visitLiteral(JQuickJavaParser.LiteralContext ctx) {
        if(null!=ctx.bool()){
            return visitBool(ctx.bool());
        }else  if(null!=ctx.string()){
            return visitString(ctx.string());
        }else  if(null!=ctx.date()){
            return visitDate(ctx.date());
        }else  if(null!=ctx.variables()){
           return  visitVariables(ctx.variables());
         }else  if(null!=ctx.number()){
            try {
                Number number = NumberFormat.getInstance().parse(ctx.number().getText());
                return number;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else  if(null!=ctx.identifier()){
            String identifier=ctx.identifier().getText();
            Variable variable = currentContext().getVariable(identifier);
            Stack<VariableContext> ss= this.contextStack;
            JAssert.notNull(variable,"can't find variable ["+identifier+"]");
            //console.log(JLogLevel.DEBUG,"identifier="+identifier+"value:"+gson.toJson(variable));
            return variable.getValue();
        }else  if(null!=ctx.listLiteral()){
            return ctx.listLiteral().getText();
        } else  if(null!=ctx.mapLiteral()){
            return ctx.mapLiteral().getText();
        } else if(ctx.qualifiedName() != null) {
            String qualifiedName=visitQualifiedName(ctx.qualifiedName());
            JTypeReference<?> typeReference = loadClass(qualifiedName);
            Class<?> clazz=typeReference.getRawType();
            return clazz;
        }else if (null!=ctx.null_()){
            return null;
        }
        JAssert.throwNewException("invalid literalsyntax:"+ctx.getText());
        return null;
    }
    @Override
    public Map<Object,Object> visitMapLiteral(JQuickJavaParser.MapLiteralContext ctx) {
        Map<Object,Object> map=new HashMap<>();
        for (int i = 0; i < ctx.mapEntry().size(); i++) {
            Map<Object,Object> entry=visitMapEntry(ctx.mapEntry(i));
            map.putAll(entry);
        }
        return map;
    }
    @Override
    public Map<Object,Object> visitMapEntry(JQuickJavaParser.MapEntryContext ctx) {
        Map<Object,Object> map=new HashMap<>();
        if(ctx.expression() != null&&ctx.expression().size()==2) {
            Object key=visitExpression(ctx.expression().get(0));
            Object value=visitExpression(ctx.expression().get(1));
            map.put(key,value);
        }
        JAssert.throwNewException("invalidat the map entry");
        return map;
    }

}
