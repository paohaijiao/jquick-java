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
package com.github.paohaijiao.factory;

import com.github.paohaijiao.evalue.JQuickJavaResultProvider;
import com.github.paohaijiao.exception.JAssert;

import java.util.HashMap;
import java.util.Map;

public class JQuickJavaResultProviderFactory {

    public final Map<Class<?>, JQuickJavaResultProvider<?>> providers = new HashMap<>();

    public <T> void registerProvider(Class clazz, JQuickJavaResultProvider provider) {
        providers.put(clazz, provider);
    }
    public Object route(Class<?> clazz, Object input) {
        JQuickJavaResultProvider<Object> provider = (JQuickJavaResultProvider<Object>) providers.get(input.getClass());
        if (provider != null) {
            return provider.getResult(input);
        }
        JAssert.throwNewException("No provider found for class: " + clazz.getName());
        return null;
    }
}
