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
package com.github.paohaijiao.scope;

import com.github.paohaijiao.support.JQuickJavaTypeReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * packageName com.github.paohaijiao.scope
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/8/6
 */
public class JQuickJavaVariableContext {

    private Map<String, JQuickJavaVariable> variables = new HashMap<>();;

    private  Map<String, List<JQuickJavaVariable>> scopeVariables = new HashMap<>();

    private JQuickJavaVariableContext parent;

    public JQuickJavaVariableContext() {
        this(null);
    }

    public JQuickJavaVariableContext(JQuickJavaVariableContext parent) {
        this.variables = new HashMap<>();
        this.scopeVariables = new HashMap<>();
        this.parent = parent;
    }

    public void addVariable(String name, Object value, JQuickJavaTypeReference<?> type) {
        variables.put(name, new JQuickJavaVariable(name, value, type));
    }

    public JQuickJavaVariable getVariable(String name) {
        JQuickJavaVariable variable = variables.get(name);
        if (variable == null && parent != null) {
            return parent.getVariable(name);
        }
        return variable;
    }

    public void addScopeVariable(String name, List<JQuickJavaVariable> variables) {
        if (variables != null) {
            scopeVariables.put(name, variables);
        }
    }
    public void updateVariableWithAllScopes(String name, Object newValue, JQuickJavaTypeReference<?> type) {
        JQuickJavaVariableContext current = this;
        while (current != null) {
             current.addVariable(name,newValue,type);
            current = current.parent;
        }
    }
}
