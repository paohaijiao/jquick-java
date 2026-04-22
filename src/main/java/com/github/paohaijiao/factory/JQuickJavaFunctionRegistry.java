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

import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.model.JQuickJavaFunctionDefinitionModel;
import com.github.paohaijiao.model.JQuickJavaFunctionFieldModel;
import com.github.paohaijiao.support.JQuickJavaTypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JQuickJavaFunctionRegistry {
    private static final JQuickJavaFunctionRegistry INSTANCE = new JQuickJavaFunctionRegistry();

    private final Map<String, List<JQuickJavaFunctionDefinitionModel>> functionTable = new HashMap<>();

    public Map<String, List<JQuickJavaFunctionDefinitionModel>> getFunctionTable() {
        return functionTable;
    }

    private JQuickJavaFunctionRegistry() {
    }
    public static JQuickJavaFunctionRegistry getInstance() {
        return INSTANCE;
    }

    public void registerFunction(JQuickJavaFunctionDefinitionModel function) {
        List<String> list=function.getFields().stream().map(JQuickJavaFunctionFieldModel::getFieldName).distinct().collect(Collectors.toList());
        JAssert.isTrue(function.getFields().size() == list.size(),"function params must have the different names");
        functionTable.computeIfAbsent(function.getName(), k -> new ArrayList<>()).add(function);
    }

    public JQuickJavaFunctionDefinitionModel lookupFunction(String name, JQuickJavaTypeReference<?>[]  arguments) {
        List<JQuickJavaFunctionDefinitionModel> candidates = functionTable.get(name);
        if (candidates == null) return null;
        if(arguments==null) return null;
        for(int i=0; i<candidates.size(); i++) {
            JQuickJavaFunctionDefinitionModel functionDefinitionModel= candidates.get(i);
            if(functionDefinitionModel.getFields().size()!=arguments.length) return null;
            for(int j=0; j<functionDefinitionModel.getFields().size(); j++) {
                JQuickJavaFunctionFieldModel define = functionDefinitionModel.getFields().get(j);
                JQuickJavaTypeReference<?> methodReference= define.getType();
                JQuickJavaTypeReference<?> literalModel = arguments[j];
                if(!methodReference.isAssignableFrom(literalModel)) {
                    return null;
                }
            }
            return functionDefinitionModel;

        }
        return null;
    }

    public boolean isFunctionDefined(String name) {
        return functionTable.containsKey(name);
    }
}
