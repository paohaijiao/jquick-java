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
package com.github.paohaijiao.evalue.impl;

import com.github.paohaijiao.anno.JQuickJavaHandlesClass;
import com.github.paohaijiao.evalue.JQuickJavaResultProvider;

@JQuickJavaHandlesClass(String.class)
public class JQuickJavaStringResultProvider implements JQuickJavaResultProvider<String> {
    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    public Object getResult(String  input) {
        if (!(input instanceof String)) {
            throw new IllegalArgumentException("Input must be String");
        }
        String str = (String) input;
        return "Processed String: " + str.toUpperCase();
    }
}
