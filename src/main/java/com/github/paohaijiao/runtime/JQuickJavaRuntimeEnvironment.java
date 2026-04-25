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
package com.github.paohaijiao.runtime;

import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.param.JContext;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName com.github.paohaijiao.config
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/4/24
 */
@Data
public class JQuickJavaRuntimeEnvironment {

    private JContext context=new JContext();

    private Map<String, Object> environment=new HashMap<String, Object>();

    public JQuickJavaRuntimeEnvironment(){

    }
    public JQuickJavaRuntimeEnvironment(JContext context){
        JAssert.notNull(context,"context must not be null");
        this.context=context;
    }
    public JQuickJavaRuntimeEnvironment(Map<String, Object> environment){
        JAssert.notNull(environment,"environment must not be null");
        this.environment=environment;
    }
    public JQuickJavaRuntimeEnvironment(JContext context,Map<String, Object> environment){
        JAssert.notNull(context,"context must not be null");
        JAssert.notNull(environment,"environment must not be null");
        this.context=context;
        this.environment=environment;
    }

    /**
     * 设置环境变量
     */
    public JQuickJavaRuntimeEnvironment set(String key, Object value) {
        this.environment.put(key, value);
        return this;
    }
    /**
     * 批量设置环境变量
     */
    public JQuickJavaRuntimeEnvironment setAll(Map<String, Object> map) {
        if (map != null) {
            this.environment.putAll(map);
        }
        return this;
    }
    /**
     * 获取环境变量
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) this.environment.get(key);
    }
    /**
     * 获取环境变量，带默认值
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defaultValue) {
        Object value = this.environment.get(key);
        return value != null ? (T) value : defaultValue;
    }

    /**
     * 获取字符串类型环境变量
     */
    public String getString(String key) {
        Object value = this.environment.get(key);
        return value != null ? value.toString() : null;
    }

    /**
     * 获取字符串类型环境变量，带默认值
     */
    public String getString(String key, String defaultValue) {
        Object value = this.environment.get(key);
        return value != null ? value.toString() : defaultValue;
    }

    /**
     * 获取整数类型环境变量
     */
    public Integer getInt(String key) {
        Object value = this.environment.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 获取整数类型环境变量，带默认值
     */
    public int getInt(String key, int defaultValue) {
        Integer value = getInt(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 获取布尔类型环境变量
     */
    public Boolean getBoolean(String key) {
        Object value = this.environment.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        return null;
    }

    /**
     * 获取布尔类型环境变量，带默认值
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        Boolean value = getBoolean(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 检查环境变量是否存在
     */
    public boolean contains(String key) {
        return this.environment.containsKey(key);
    }

    /**
     * 删除环境变量
     */
    public JQuickJavaRuntimeEnvironment remove(String key) {
        this.environment.remove(key);
        return this;
    }

    /**
     * 清空所有环境变量
     */
    public JQuickJavaRuntimeEnvironment clear() {
        this.environment.clear();
        return this;
    }

    /**
     * 获取环境变量大小
     */
    public int size() {
        return this.environment.size();
    }

    /**
     * 判断环境变量是否为空
     */
    public boolean isEmpty() {
        return this.environment.isEmpty();
    }

    /**
     * 创建新实例
     */
    public static JQuickJavaRuntimeEnvironment create() {
        return new JQuickJavaRuntimeEnvironment();
    }

    /**
     * 根据现有 Context 创建
     */
    public static JQuickJavaRuntimeEnvironment of(JContext context) {
        JQuickJavaRuntimeEnvironment env = new JQuickJavaRuntimeEnvironment();
        if (context != null) {
            env.setContext(context);
        }
        return env;
    }
    /**
     * 根据现有 Map 创建
     */
    public static JQuickJavaRuntimeEnvironment of(Map<String, Object> map) {
        JQuickJavaRuntimeEnvironment env = new JQuickJavaRuntimeEnvironment();
        if (map != null) {
            env.getEnvironment().putAll(map);
        }
        return env;
    }
}
