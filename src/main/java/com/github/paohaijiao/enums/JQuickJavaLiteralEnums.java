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
package com.github.paohaijiao.enums;

import com.github.paohaijiao.support.JQuickJavaTypeReference;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Getter
public enum JQuickJavaLiteralEnums {
    String("string", new JQuickJavaTypeReference<String>() {}),
    Date("date", new JQuickJavaTypeReference<Date>() {}),
    Boolean("boolean", new JQuickJavaTypeReference<Boolean>() {}),
    Null("null", new JQuickJavaTypeReference<Object>() {}),
    Variable("variable", new JQuickJavaTypeReference<Object>() {}),
    Identifier("identifier", new JQuickJavaTypeReference<Object>() {}),

    Int("int", new JQuickJavaTypeReference<Integer>() {}),
    Long("long", new JQuickJavaTypeReference<Long>() {}),
    Short("short", new JQuickJavaTypeReference<Short>() {}),
    Byte("byte", new JQuickJavaTypeReference<Byte>() {}),
    Float("float", new JQuickJavaTypeReference<Float>() {}),
    Double("double", new JQuickJavaTypeReference<Double>() {}),
    Char("char", new JQuickJavaTypeReference<Character>() {}),
    Number("number", new JQuickJavaTypeReference<Number>() {}),

    ClassLiteral("class", new JQuickJavaTypeReference<Class<?>>() {}),
    List("list", new JQuickJavaTypeReference<List<?>>() {}),
    Map("map", new JQuickJavaTypeReference<Map<?, ?>>() {}),
    Set("set", new JQuickJavaTypeReference<Set<?>>() {});
    private String code;

    private JQuickJavaTypeReference<?> typeReference;

    private JQuickJavaLiteralEnums(String code, JQuickJavaTypeReference<?> typeReference) {
        this.code = code;
        this.typeReference = typeReference;
    }

    public static JQuickJavaLiteralEnums codeOf(String code) {
        for (JQuickJavaLiteralEnums jiteral : values()) {
            if (code.equals(jiteral.code)) {
                return jiteral;
            }
        }
        return null;
    }

    public static JQuickJavaLiteralEnums typeOf(JQuickJavaTypeReference<?> typeReference) {
        for (JQuickJavaLiteralEnums jiteral : values()) {
            if (typeReference.getType().equals(jiteral.getTypeReference().getType()) ) {
                return jiteral;
            }
        }
        return null;
    }

}
