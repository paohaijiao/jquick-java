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
import lombok.Data;

/**
 * packageName com.github.paohaijiao.scope
 * @author Martin
 * @version 1.0.0
 * @since 2025/8/6
 */
@Data
public class JQuickJavaVariable {

    private String name;

    private Object value;

    private JQuickJavaTypeReference<?> type;

    public JQuickJavaVariable(String name, Object value, JQuickJavaTypeReference<?> type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }
}
