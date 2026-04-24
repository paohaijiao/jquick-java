package com.github.paohaijiao.context;

public class JQuickJavaBuiltFunctionInfo {

    private final String name;

    private final String description;

    private final int priority;

    public JQuickJavaBuiltFunctionInfo(String name, String description, int priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return String.format("FunctionInfo{name='%s', priority=%d, description='%s'}", name, priority, description);
    }
}
