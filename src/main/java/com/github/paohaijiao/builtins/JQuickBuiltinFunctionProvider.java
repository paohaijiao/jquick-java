package com.github.paohaijiao.builtins;

import com.github.paohaijiao.runtime.JQuickJavaRuntimeEnvironment;

import java.util.List;

/**
 * JQuick 内置函数接口
 * 所有内置函数都需要实现此接口，通过 ServiceLoader 动态加载
 */
public interface JQuickBuiltinFunctionProvider {
    /**
     * 执行函数
     * @param ctx 执行上下文（包含 args、context、scopes 等所有信息）
     * @return 执行结果
     */
    Object execute(JQuickJavaRuntimeEnvironment ctx, List<Object> args);

    /**
     * 获取函数名称
     */
    String getName();

    /**
     * 获取函数描述
     */
    default String getDescription() {
        return "JQuick builtin function: " + getName();
    }
}
