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


import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.factory.JFunctionRegistry;
import com.github.paohaijiao.model.*;
import com.github.paohaijiao.support.JTypeReference;

import java.util.ArrayList;
import java.util.List;

public class JQuickJavaRegistryVisitor extends JQuickJavaCoreVisitor {

    JFunctionRegistry registry= JFunctionRegistry.getInstance();

    public void registerFunction(JFunctionDefinitionModel function) {
        if (function == null || function.getName() == null) {
            throw new IllegalArgumentException("Function definition cannot be null");
        }
        registry.registerFunction( function);
    }
    public  boolean hasFunction(String functionName) {
        return registry.isFunctionDefined(functionName);
    }

    public  JVariableContainerModel invoke(String functionName, JTypeReferenceAndValueModel model) {
        if (!hasFunction(functionName)) {
            throw new IllegalArgumentException("Function '" + functionName + "' is not defined");
        }
        JTypeReference<?>[] references=model.getList().stream().map(JTypeReferenceAndValue::getTypeArguments).toArray(JTypeReference[]::new);
        Object[] data=model.getList().stream().map(JTypeReferenceAndValue::getData).toArray();
        JFunctionDefinitionModel function = registry.lookupFunction(functionName,references);
        validateArguments(function, references);
        JVariableContainerModel localVariables = new JVariableContainerModel();
        bindParameters(function, data, localVariables);
        return localVariables;
    }

    private void validateArguments(JFunctionDefinitionModel function,  JTypeReference<?>[] typeReferences) {
        JAssert.notNull(function, "Function cannot be null");
        int expectedCount = function.getParameterCount();
        int actualCount = typeReferences != null ? typeReferences.length : 0;
        if (expectedCount != actualCount) {
            throw new IllegalArgumentException(String.format(
                    "the number of parameters does not match. Function '%s' need %d parameter，but actually %d ",
                    function.getName(), expectedCount, actualCount
            ));
        }
        if (expectedCount == 0) {
            return;
        }

        List<JFunctionFieldModel> fields = function.getFields();
        for (int i = 0; i < expectedCount; i++) {
            JFunctionFieldModel expectedField = fields.get(i);
            JTypeReference<?> actualValue = typeReferences[i];
            if (actualValue.getType() == null && !isNullableType(expectedField.getType())) {
                throw new IllegalArgumentException(String.format(
                        "parameter '%s'(index:%d) can not be null，need type: %s",
                        actualValue.getClass().getSimpleName(), i + 1, expectedField.getType().getRawType().getSimpleName()
                ));
            }
        }
    }
    private boolean isNullableType(JTypeReference<?> type) {
        switch (type.getRawType().getSimpleName()) {
            case "int":
            case "long":
            case "double":
            case "float":
            case "boolean":
                return false;
            default:
                return true;
        }
    }

    private void bindParameters(JFunctionDefinitionModel function,
                                Object[] arguments,
                                JVariableContainerModel localVariables) {
        List<String> paramNames = function.getParameterNames();
        List<JFunctionFieldModel> paramTypes = function.getFields();
        for (int i = 0; i < paramNames.size(); i++) {
            String paramName = paramNames.get(i);
            Object argValue = arguments[i];
            try {
                localVariables.set(paramName, argValue);
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format(
                        "bindParameters failed - %s(index:%d): %s",
                        paramName, i + 1, e.getMessage()
                ), e);
            }
        }
    }




    public static JFunctionDefinitionModel createFunctionDefinition(String name, List<JFunctionFieldModel> paramDefine, String action,JTypeReference<?> type) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Function name cannot be null or empty");
        }
        return new JFunctionDefinitionModel(name,paramDefine, action,type);
    }

    public List<String> getRegisteredFunctionNames() {
        return new ArrayList<>(registry.getFunctionTable().keySet());
    }


    public List<JFunctionDefinitionModel> getFunctionDefinition(String functionName) {
        return registry.getFunctionTable().get(functionName);
    }



}
