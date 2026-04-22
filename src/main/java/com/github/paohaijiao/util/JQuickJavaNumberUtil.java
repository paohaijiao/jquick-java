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
package com.github.paohaijiao.util;


import java.math.BigDecimal;

/**
 * packageName com.github.paohaijiao.util
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/7/24
 */
public class JQuickJavaNumberUtil {

    public static <T> T attemptCustomConversion(Class<?> expectedType, Object argValue) {
        if (argValue == null) {
            return null;
        }

        if (expectedType.isAssignableFrom(argValue.getClass())) {
            return (T) argValue;
        }
        String strValue = argValue.toString();
        try {
            if (expectedType == Integer.class || expectedType == int.class) {
                return (T) Integer.valueOf(strValue);
            } else if (expectedType == Long.class || expectedType == long.class) {
                return (T) Long.valueOf(strValue);
            } else if (expectedType == Double.class || expectedType == double.class) {
                return (T) Double.valueOf(strValue);
            } else if (expectedType == Float.class || expectedType == float.class) {
                return (T) Float.valueOf(strValue);
            } else if (expectedType == Short.class || expectedType == short.class) {
                return (T) Short.valueOf(strValue);
            } else if (expectedType == BigDecimal.class) {
                return (T) new BigDecimal(strValue);
            } else {
                throw new IllegalArgumentException("unsupported target type: " + expectedType.getName());
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("cannot convert value '" + argValue + "' to type " + expectedType.getName(), e);
        }
    }

    public static Integer toInteger(Object value) {
        return attemptCustomConversion(Integer.class, value);
    }

    public static Long toLong(Object value) {
        return attemptCustomConversion(Long.class, value);
    }

    public static Double toDouble(Object value) {
        return attemptCustomConversion(Double.class, value);
    }

    public static Float toFloat(Object value) {
        return attemptCustomConversion(Float.class, value);
    }

    public static Short toShort(Object value) {
        return attemptCustomConversion(Short.class, value);
    }

    public static BigDecimal toBigDecimal(Object value) {
        return attemptCustomConversion(BigDecimal.class, value);
    }

}
