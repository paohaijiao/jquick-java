package com.github.paohaijiao.error;
/**
 * 函数未找到异常
 */
public class JQuickJavaBuiltInFunctionNotFoundException extends RuntimeException {

    public JQuickJavaBuiltInFunctionNotFoundException(String message) {
        super(message);
    }

    public JQuickJavaBuiltInFunctionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
