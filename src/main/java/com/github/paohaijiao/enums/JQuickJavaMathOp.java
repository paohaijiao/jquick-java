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

import lombok.Getter;

@Getter
public enum JQuickJavaMathOp {

    mutiply("*", "mutiply"),
    divide("/", "divide"),
    subtract("-", "subtract"),
    greater(">", "greater"),
    greaterEq(">=", "greaterEq"),
    less("<", "less"),
    lessEq("<=", "lessEq"),
    add("+", "add"),
    mod("mod", "mod"),
    pow("pow", "pow"),
    notEqual("!=", "not equal"),
    equal("==", "equal");

    private String code;

    private String value;

    private JQuickJavaMathOp(String code, String value) {
        this.code = code;
        this.value = value;
    }
    public static JQuickJavaMathOp codeOf(String code) {
        for (JQuickJavaMathOp op : JQuickJavaMathOp.values()) {
            if (op.code.equals(code)) {
                return op;
            }
        }
        return null;
    }
}
