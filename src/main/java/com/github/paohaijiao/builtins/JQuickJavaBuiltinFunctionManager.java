package com.github.paohaijiao.builtins;

import com.github.paohaijiao.context.JQuickJavaBuiltInFunctionContext;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.spi.ServiceLoader;
import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.enums.JLogLevel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内置函数动态调用管理器
 * 使用SPI机制动态发现和执行内置函数
 */
public class BuiltinFunctionManager {

    private static final JConsole console = new JConsole();

    private static final Map<String, JQuickBuiltinFunctionProvider> FUNCTION_REGISTRY = new ConcurrentHashMap<>();

    private static volatile boolean initialized = false;

    /**
     * 初始化函数管理器，加载所有SPI服务
     */
    public static synchronized void initialize() {
        if (initialized) {
            return;
        }
        console.log(JLogLevel.INFO, "正在初始化内置函数管理器...");
        List<JQuickBuiltinFunctionProvider> functions = ServiceLoader.loadServicesByPriority(JQuickBuiltinFunctionProvider.class);
        console.log(JLogLevel.INFO, "发现 " + functions.size() + " 个内置函数实现");
        for (JQuickBuiltinFunctionProvider function : functions) {
            String name = function.getName();
            // 处理函数名冲突：优先级高的覆盖优先级低的
            if (FUNCTION_REGISTRY.containsKey(name)) {
                int existingPriority = getFunctionPriority(FUNCTION_REGISTRY.get(name));
                int newPriority = getFunctionPriority(function);
                if (newPriority < existingPriority) {
                    console.log(JLogLevel.INFO, String.format("函数 '%s' 被更高优先级的实现覆盖 (优先级: %d -> %d)", name, existingPriority, newPriority));
                    FUNCTION_REGISTRY.put(name, function);
                } else {
                    console.log(JLogLevel.WARN, String.format("函数 '%s' 已有优先级 %d 的实现，忽略优先级 %d 的实现", name, existingPriority, newPriority));
                }
            } else {
                FUNCTION_REGISTRY.put(name, function);
                console.log(JLogLevel.DEBUG, String.format("注册内置函数: %s (优先级: %d, 描述: %s)", name, getFunctionPriority(function), function.getDescription()));
            }
        }

        initialized = true;
        console.log(JLogLevel.INFO, "内置函数管理器初始化完成，共注册 " + FUNCTION_REGISTRY.size() + " 个函数");
    }

    /**
     * 调用内置函数
     *
     * @param functionName 函数名称
     * @param context 全局上下文
     * @param scopes 作用域栈
     * @param args 函数参数列表
     * @return 执行结果
     * @throws FunctionNotFoundException 函数未找到异常
     * @throws FunctionExecutionException 函数执行异常
     */
    public static Object invoke(String functionName, JContext context, Stack<Map<String, Object>> scopes, List<Object> args) {
        if (!initialized) {
            initialize();
        }
        // 查找函数
        JQuickBuiltinFunctionProvider function = FUNCTION_REGISTRY.get(functionName);
        if (function == null) {
            String errorMsg = String.format("内置函数 '%s' 未找到", functionName);
            console.log(JLogLevel.ERROR, errorMsg);
            throw new FunctionNotFoundException(errorMsg);
        }
        // 构建执行上下文
        JQuickJavaBuiltInFunctionContext ctx = new JQuickJavaBuiltInFunctionContext();
        ctx.setContext(context);
        ctx.setScopes(scopes);
        ctx.setArgs(args != null ? args : Collections.emptyList());
        try {
            console.log(JLogLevel.DEBUG, String.format("执行内置函数: %s, 参数数量: %d", functionName, ctx.getArgs().size()));
            // 执行函数
            Object result = function.execute(ctx);
            console.log(JLogLevel.DEBUG, String.format("函数 %s 执行完成，返回结果: %s", functionName, result));
            return result;
        } catch (Exception e) {
            String errorMsg = String.format("执行内置函数 '%s' 时发生异常: %s", functionName, e.getMessage());
            console.log(JLogLevel.ERROR, errorMsg);
            throw new FunctionExecutionException(errorMsg, e);
        }
    }

    /**
     * 检查函数是否存在
     */
    public static boolean hasFunction(String functionName) {
        if (!initialized) {
            initialize();
        }
        return FUNCTION_REGISTRY.containsKey(functionName);
    }

    /**
     * 获取所有已注册的函数名
     */
    public static Set<String> getRegisteredFunctions() {
        if (!initialized) {
            initialize();
        }
        return Collections.unmodifiableSet(FUNCTION_REGISTRY.keySet());
    }

    /**
     * 获取函数信息
     */
    public static FunctionInfo getFunctionInfo(String functionName) {
        if (!initialized) {
            initialize();
        }
        JQuickBuiltinFunctionProvider function = FUNCTION_REGISTRY.get(functionName);
        if (function == null) {
            return null;
        }
        return new FunctionInfo(function.getName(), function.getDescription(), getFunctionPriority(function));
    }

    /**
     * 获取函数优先级
     */
    private static int getFunctionPriority(JQuickBuiltinFunctionProvider function) {
        try {
            // 尝试通过反射获取优先级
            Class<?> clazz = function.getClass();
            // 检查是否有getPriority方法
            java.lang.reflect.Method method = clazz.getMethod("getPriority");
            if (method.getReturnType() == int.class || method.getReturnType() == Integer.class) {
                return (int) method.invoke(function);
            }
        } catch (NoSuchMethodException e) {
        } catch (Exception e) {
            console.log(JLogLevel.WARN, "获取函数优先级失败: " + e.getMessage());
        }

        return com.github.paohaijiao.spi.constants.PriorityConstants.DEFAULT;
    }

    /**
     * 重新加载所有函数
     */
    public static synchronized void reload() {
        FUNCTION_REGISTRY.clear();
        initialized = false;
        initialize();
    }

    /**
     * 打印所有已注册函数的信息
     */
    public static void printFunctionInfo() {
        if (!initialized) {
            initialize();
        }
        console.log(JLogLevel.INFO, "========== 已注册内置函数列表 ==========");
        console.log(JLogLevel.INFO, String.format("总计: %d 个函数", FUNCTION_REGISTRY.size()));
        console.log(JLogLevel.INFO, "");
        List<Map.Entry<String, JQuickBuiltinFunctionProvider>> sortedFunctions = new ArrayList<>(FUNCTION_REGISTRY.entrySet());
        for (Map.Entry<String, JQuickBuiltinFunctionProvider> entry : sortedFunctions) {
            JQuickBuiltinFunctionProvider function = entry.getValue();
            console.log(JLogLevel.INFO, String.format("  - %s : %s [优先级: %d]", entry.getKey(), function.getDescription(), getFunctionPriority(function)));
        }
        console.log(JLogLevel.INFO, "=========================================");
    }
}
