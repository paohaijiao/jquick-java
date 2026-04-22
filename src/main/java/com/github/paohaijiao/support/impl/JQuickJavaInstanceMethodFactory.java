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
package com.github.paohaijiao.support.impl;

import com.github.paohaijiao.support.JQuickJavaTypeReference;

import java.util.Objects;

public class JQuickJavaInstanceMethodFactory {
    private final Object target;
    public JQuickJavaInstanceMethodFactory(Object target) {
        this.target = Objects.requireNonNull(target, "Target object cannot be null");
    }

    public <R> R invoke(String methodName, JQuickJavaTypeReference<?>[] argTypes, Object... args) {
        return JQuickJavaMethodInvoker.invoke(target, target.getClass(), methodName, argTypes, args);
    }

    public <R> R invoke(String methodName) {
        return invoke(methodName, new JQuickJavaTypeReference<?>[0]);
    }

    public <R> R invoke(String methodName, JQuickJavaTypeReference<?> argType, Object arg) {
        return invoke(methodName, new JQuickJavaTypeReference<?>[]{argType}, arg);
    }
}
