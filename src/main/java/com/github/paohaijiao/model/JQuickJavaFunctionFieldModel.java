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
package com.github.paohaijiao.model;

import com.github.paohaijiao.support.JQuickJavaTypeReference;
import lombok.Data;

/**
 * packageName com.github.paohaijiao.model
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/7/23
 */
@Data
public class JQuickJavaFunctionFieldModel {

    private Integer index;

    private String fieldName;

    private JQuickJavaTypeReference<?> type;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JQuickJavaFunctionFieldModel that = (JQuickJavaFunctionFieldModel) o;
        if (index != null ? !index.equals(that.index) : that.index != null) return false;
        if (fieldName != null ? !fieldName.equals(that.fieldName) : that.fieldName != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = index != null ? index.hashCode() : 0;
        result = 31 * result + (fieldName != null ? fieldName.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
