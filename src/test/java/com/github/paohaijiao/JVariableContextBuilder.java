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
package com.github.paohaijiao;

import com.github.paohaijiao.scope.JQuickJavaVariableContext;
import com.github.paohaijiao.service.JService;
import com.github.paohaijiao.support.JQuickJavaTypeReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * packageName com.github.paohaijiao
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/8/9
 */
public class JVariableContextBuilder {
    public static Stack<JQuickJavaVariableContext> mockData(){
        JQuickJavaVariableContext variableContext=new JQuickJavaVariableContext();
        List<Integer> listVar = new ArrayList<Integer>() {
        };
        listVar.addAll(Arrays.asList(1, 2, 3));
        Stack<JQuickJavaVariableContext> contextStack = new Stack<JQuickJavaVariableContext>();
        variableContext.addVariable("testObj", new JService(), JQuickJavaTypeReference.of(JService.class));
        variableContext.addVariable("listVar", listVar, JQuickJavaTypeReference.listOf(Integer.class));
        contextStack.add(variableContext);
        return contextStack;
    }
}
