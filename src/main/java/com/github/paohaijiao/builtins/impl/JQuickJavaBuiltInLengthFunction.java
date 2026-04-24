package com.github.paohaijiao.builtins.impl;
import com.github.paohaijiao.builtins.JQuickBuiltinFunctionProvider;
import com.github.paohaijiao.context.JQuickJavaBuiltInFunctionContext;
import java.util.List;
import java.util.Map;

public class JQuickJavaBuiltInLengthFunction implements JQuickBuiltinFunctionProvider {

    @Override
    public String getName() {
        return "len";
    }

    @Override
    public Object execute(JQuickJavaBuiltInFunctionContext ctx) {
        if (ctx.getArgs() == null || ctx.getArgs().isEmpty()) {
            return 0;
        }
        Object obj = ctx.getArgs().get(0);
        return getLength(obj);
    }

    private int getLength(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof String) {
            return ((String) obj).length();
        }
        if (obj instanceof List) {
            return ((List<?>) obj).size();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size();
        }
        if (obj instanceof Object[]) {
            return ((Object[]) obj).length;
        }
        return obj.toString().length();
    }
}