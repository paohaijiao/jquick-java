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


import com.github.paohaijiao.enums.JQuickJavaMathOp;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.github.paohaijiao.util.JStringUtils;

import java.math.BigDecimal;

public class JQuickJavaMathVisitor extends JQuickMethodInvocationCallVisitor {

    private static final Class<?> PKG = JQuickJavaMathVisitor.class;


    @Override
    public Object visitArithmetic(JQuickJavaParser.ArithmeticContext ctx) {
        Object result = extract(visitPrimary(ctx.primary(0)));
        java.util.List<Object> values = new java.util.ArrayList<>();
        java.util.List<String> operators = new java.util.ArrayList<>();
        values.add(result);
        for (int i = 1; i < ctx.primary().size(); i++) {
            String operator = ctx.getChild(2 * i - 1).getText();
            JQuickJavaMathOp op = JQuickJavaMathOp.codeOf(operator);
            JAssert.notNull(op, "Unsupported operator: " + operator);
            operators.add(operator);
            values.add(extract(visitPrimary(ctx.primary(i))));
        }

        for (int i = 0; i < operators.size();) {
            String operator = operators.get(i);
            if ("*".equals(operator) || "/".equals(operator)) {
                Object left = values.get(i);
                Object right = values.get(i + 1);
                Object merged = "*".equals(operator) ? multiply(left, right) : divide(left, right);
                values.set(i, merged);
                values.remove(i + 1);
                operators.remove(i);
            } else {
                i++;
            }
        }

        result = values.get(0);
        for (int i = 0; i < operators.size(); i++) {
            String operator = operators.get(i);
            Object right = values.get(i + 1);
            switch (operator) {
                case "+":
                    result = add(result, right);
                    break;
                case "-":
                    result = subtract(result, right);
                    break;
                default:
                    throw new RuntimeException("Unknown operator: " + operator);
            }
        }
        return result;
    }

    private Object multiply(Object left, Object right) {
        if (left instanceof Number && right instanceof Number) {
            BigDecimal leftBigDecimal  = new BigDecimal(left.toString());
            BigDecimal rightBigDecimal  = new BigDecimal(right.toString());
            BigDecimal result= leftBigDecimal.multiply(rightBigDecimal);
            return convertToPrimaryType(result,left.getClass());
        }
        JAssert.throwNewException("multiplication of non-numeric types");
        return null;
    }
    private Object divide(Object left, Object right) {
        if (left instanceof Number && right instanceof Number) {
            BigDecimal leftBigDecimal  = new BigDecimal(left.toString());
            BigDecimal rightBigDecimal  = new BigDecimal(right.toString());
            BigDecimal result= leftBigDecimal.divide(rightBigDecimal,2,BigDecimal.ROUND_HALF_UP);
            return convertToPrimaryType(result,left.getClass());
        }
        JAssert.throwNewException("division of non-numeric types");
        return null;
    }


    private Object add(Object left, Object right) {
        if (left instanceof Number && right instanceof Number) {
            BigDecimal leftBigDecimal  = new BigDecimal(left.toString());
            BigDecimal rightBigDecimal  = new BigDecimal(right.toString());
            BigDecimal result= leftBigDecimal.add(rightBigDecimal);
            return convertToPrimaryType(result,left.getClass());
        }
        else if (left instanceof String || right instanceof String) {
            return JStringUtils.trim(left.toString()) + JStringUtils.trim(right.toString()) ;
        }
        throw new RuntimeException("Addition of incompatible types: "
                + left.getClass() + " and " + right.getClass());
    }

    private Object subtract(Object left, Object right) {
        if (left instanceof Number && right instanceof Number) {
            BigDecimal leftBigDecimal  = new BigDecimal(left.toString());
            BigDecimal rightBigDecimal  = new BigDecimal(right.toString());
            BigDecimal result= leftBigDecimal.subtract(rightBigDecimal);
            return convertToPrimaryType(result,left.getClass());
        }
        throw new RuntimeException("Subtraction of non-numeric types");
    }

    private Object convertToPrimaryType(BigDecimal value,Class<?>  clazz){
        if(clazz==Short.class||clazz==short.class){
            return value.shortValue();
        }
        if(clazz==Integer.class||clazz==int.class){
            return value.intValue();
        }
        if(clazz==Float.class||clazz==float.class){
            return value.floatValue();
        }
        if(clazz==Double.class||clazz==double.class){
            return value.doubleValue();
        }
        if(clazz==Long.class||clazz==long.class){
            return value.longValue();
        }
        return value;
    }
}
