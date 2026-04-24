package com.github.paohaijiao.error;

/**
 * JQuickJava 语言顶层异常
 * 用于统一处理：语法错误、解析错误、运行时错误、编译错误
 */
public class JQuickJavaException extends RuntimeException {

    /**
     * 仅带错误信息的异常
     * @param message 错误描述
     */
    public JQuickJavaException(String message) {
        super(message);
    }

    /**
     * 带错误信息 + 原始异常的异常（用于包装底层异常）
     * @param message 错误描述
     * @param cause 原始异常
     */
    public JQuickJavaException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 带行号的语法/解析异常（语言开发最常用）
     * @param line 行号
     * @param message 错误信息
     */
    public JQuickJavaException(int line, String message) {
        super(String.format("行号 [%d] 错误: %s", line, message));
    }

    /**
     * 带行号 + 列号的精准错误
     * @param line 行号
     * @param column 列号
     * @param message 错误信息
     */
    public JQuickJavaException(int line, int column, String message) {
        super(String.format("行号 [%d], 列号 [%d] 错误: %s", line, column, message));
    }
}
