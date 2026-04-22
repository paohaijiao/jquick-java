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

import com.github.paohaijiao.factory.compare.*;

import java.math.BigDecimal;

public class JQuickJavaBigDecimalComparatorFactory {

    private JQuickJavaBigDecimalComparatorFactory() {}

    public static JQuickJavaComparator greaterThan(BigDecimal threshold) {
        return new JQuickJavaGreaterThanComparator(threshold);
    }
    public static JQuickJavaComparator greaterThanOrEqual(BigDecimal threshold) {
        return new JQuickJavaGreaterThanOrEqualComparator(threshold);
    }

    public static JQuickJavaComparator lessThan(BigDecimal threshold) {
        return new JQuickJavaLessThanComparator(threshold);
    }
    public static JQuickJavaComparator lessThanOrEqual(BigDecimal threshold) {
        return new JQuickJavaLessThanOrEqualComparator(threshold);
    }

}
