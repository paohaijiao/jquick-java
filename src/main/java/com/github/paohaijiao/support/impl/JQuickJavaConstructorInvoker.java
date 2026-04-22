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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static com.github.paohaijiao.support.impl.JQuickJavaMethodInvoker.*;

public class JQuickJavaConstructorInvoker {
    public static <T> T invoke(Class<T> clazz, JQuickJavaTypeReference<?>[] argTypes, Object... args) {
        try {
            Class<?>[] parameterTypes = convertToClassArray(argTypes);
            Constructor<T> constructor = findConstructor(clazz, parameterTypes);
            constructor.setAccessible(true);
            if (constructor.isVarArgs()) {
                args = handleVarArgs(constructor, args);
            }
            return constructor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to invoke constructor for class: " + clazz.getName(), e);
        }
    }
    @SuppressWarnings("unchecked")
    private static <T> Constructor<T> findConstructor(Class<T> clazz, Class<?>[] parameterTypes) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        for (Constructor<?> constructor : constructors) {
            if (isMatch(constructor.getParameterTypes(), parameterTypes, constructor.isVarArgs())) {
                return (Constructor<T>) constructor;
            }
        }
        throw new IllegalArgumentException("No matching constructor found for class: " + clazz.getName() + " with parameter types: " + Arrays.toString(parameterTypes));
    }

}
