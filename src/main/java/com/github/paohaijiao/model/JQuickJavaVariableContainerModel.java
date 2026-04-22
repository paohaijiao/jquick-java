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

import java.util.*;
import java.util.function.Function;

/**
 * packageName com.github.paohaijiao.model
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/7/22
 */
public class JQuickJavaVariableContainerModel extends HashMap<String, Object> {

    public void set(String name, Object value) {
        put(name, value);
    }

    public Object get(String name) {
        return super.get(name);
    }

    public Object getOrDefault(String name, Object defaultValue) {
        return containsKey(name) ? get(name) : defaultValue;
    }

    public <T> Optional<T> getAs(String name, Class<T> type) {
        Object value = get(name);
        if (value != null && type.isInstance(value)) {
            return Optional.of(type.cast(value));
        }
        return Optional.empty();
    }

    public boolean exists(String name) {
        return containsKey(name);
    }

    public void remove(String name) {
        super.remove(name);
    }

    public void clearAll() {
        clear();
    }

    public boolean setIfAbsent(String name, Object value) {
        if (!containsKey(name)) {
            put(name, value);
            return true;
        }
        return false;
    }

    public boolean compareAndSet(String name, Object expect, Object update) {
        if (Objects.equals(get(name), expect)) {
            put(name, update);
            return true;
        }
        return false;
    }

    public String getAsString(String name) {
        Object val = get(name);
        return val != null ? val.toString() : null;
    }

    public String getAsString(String name, String defaultValue) {
        String val = getAsString(name);
        return val != null ? val : defaultValue;
    }

    public int getAsInt(String name) {
        Object val = get(name);
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }
        throw new ClassCastException("Variable '" + name + "' is not a number");
    }

    public int getAsInt(String name, int defaultValue) {
        try {
            return getAsInt(name);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public boolean getAsBoolean(String name) {
        Object val = get(name);
        if (val instanceof Boolean) {
            return (Boolean) val;
        }
        throw new ClassCastException("Variable '" + name + "' is not a boolean");
    }

    public void setAll(Map<String, ?> variables) {
        variables.forEach(this::put);
    }

    public JQuickJavaVariableContainerModel getAll(Collection<String> names) {
        JQuickJavaVariableContainerModel result = new JQuickJavaVariableContainerModel();
        names.forEach(name -> {
            if (containsKey(name)) {
                result.put(name, get(name));
            }
        });
        return result;
    }

    public void increment(String name) {
        computeIfPresent(name, (k, v) -> {
            if (v instanceof Integer) return (Integer) v + 1;
            if (v instanceof Long) return (Long) v + 1;
            if (v instanceof Double) return (Double) v + 1;
            throw new UnsupportedOperationException("Cannot increment non-number value");
        });
    }

    public void decrement(String name) {
        computeIfPresent(name, (k, v) -> {
            if (v instanceof Integer) return (Integer) v - 1;
            if (v instanceof Long) return (Long) v - 1;
            if (v instanceof Double) return (Double) v - 1;
            throw new UnsupportedOperationException("Cannot decrement non-number value");
        });
    }

    public void setIf(String name, Object value, boolean condition) {
        if (condition) {
            put(name, value);
        }
    }

    public void compute(String name, Function<Object, Object> remappingFunction) {
        compute(name, (k, v) -> remappingFunction.apply(v));
    }
    public boolean isType(String name, Class<?> type) {
        Object val = get(name);
        return val != null && type.isInstance(val);
    }

    public boolean containsAll(Collection<String> names) {
        return names.stream().allMatch(this::containsKey);
    }
    public Map<String, Object> toUnmodifiableMap() {
        return Collections.unmodifiableMap(this);
    }
    public Properties toProperties() {
        Properties props = new Properties();
        forEach((k, v) -> props.put(k, v != null ? v.toString() : ""));
        return props;
    }

}
