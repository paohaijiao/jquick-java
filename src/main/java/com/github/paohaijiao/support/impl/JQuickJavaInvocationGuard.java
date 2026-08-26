package com.github.paohaijiao.support.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class JQuickJavaInvocationGuard {

    public static final JQuickJavaInvocationGuard DEFAULT = builder()
            .blacklistMethods("java.lang.System#exit", "java.lang.Runtime#exit", "java.lang.Runtime#halt")
            .blacklistClasses("java.lang.ProcessBuilder")
            .build();

    private final Set<String> classBlacklist;
    private final Set<String> classWhitelist;
    private final Set<String> methodBlacklist;
    private final Set<String> methodWhitelist;

    private JQuickJavaInvocationGuard(Set<String> classBlacklist,
                                      Set<String> classWhitelist,
                                      Set<String> methodBlacklist,
                                      Set<String> methodWhitelist) {
        this.classBlacklist = classBlacklist;
        this.classWhitelist = classWhitelist;
        this.methodBlacklist = methodBlacklist;
        this.methodWhitelist = methodWhitelist;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void check(Method method) {
        if (method == null) {
            throw new SecurityException("Method cannot be null");
        }
        String className = method.getDeclaringClass().getName();
        String methodKey = className + "#" + method.getName();
        checkClassName(className);
        if (methodBlacklist != null && methodBlacklist.contains(methodKey)) {
            throw new SecurityException("Method is in blacklist: " + methodKey);
        }
        if (methodWhitelist != null && !methodWhitelist.isEmpty() && !methodWhitelist.contains(methodKey)) {
            throw new SecurityException("Method is not in whitelist: " + methodKey);
        }
    }

    public void check(Constructor<?> constructor) {
        if (constructor == null) {
            throw new SecurityException("Constructor cannot be null");
        }
        String className = constructor.getDeclaringClass().getName();
        checkClassName(className);
        String methodKey = className + "#<init>";
        if (methodBlacklist != null && methodBlacklist.contains(methodKey)) {
            throw new SecurityException("Constructor is in blacklist: " + methodKey);
        }
        if (methodWhitelist != null && !methodWhitelist.isEmpty() && !methodWhitelist.contains(methodKey)) {
            throw new SecurityException("Constructor is not in whitelist: " + methodKey);
        }
    }

    private void checkClassName(String className) {
        if (className == null) {
            throw new SecurityException("Class name cannot be null");
        }
        if (classBlacklist != null && classBlacklist.contains(className)) {
            throw new SecurityException("Class is in blacklist: " + className);
        }
        if (classWhitelist != null && !classWhitelist.isEmpty() && !classWhitelist.contains(className)) {
            throw new SecurityException("Class is not in whitelist: " + className);
        }
    }

    public static final class Builder {
        private final Set<String> classBlacklist = new HashSet<>();
        private final Set<String> classWhitelist = new HashSet<>();
        private final Set<String> methodBlacklist = new HashSet<>();
        private final Set<String> methodWhitelist = new HashSet<>();

        public Builder blacklistClasses(String... classNames) {
            if (classNames != null) {
                classBlacklist.addAll(Arrays.asList(classNames));
            }
            return this;
        }

        public Builder whitelistClasses(String... classNames) {
            if (classNames != null) {
                classWhitelist.addAll(Arrays.asList(classNames));
            }
            return this;
        }

        public Builder blacklistMethods(String... methodKeys) {
            if (methodKeys != null) {
                methodBlacklist.addAll(Arrays.asList(methodKeys));
            }
            return this;
        }

        public Builder whitelistMethods(String... methodKeys) {
            if (methodKeys != null) {
                methodWhitelist.addAll(Arrays.asList(methodKeys));
            }
            return this;
        }

        public JQuickJavaInvocationGuard build() {
            return new JQuickJavaInvocationGuard(
                    classBlacklist.isEmpty() ? null : Collections.unmodifiableSet(new HashSet<>(classBlacklist)),
                    classWhitelist.isEmpty() ? null : Collections.unmodifiableSet(new HashSet<>(classWhitelist)),
                    methodBlacklist.isEmpty() ? null : Collections.unmodifiableSet(new HashSet<>(methodBlacklist)),
                    methodWhitelist.isEmpty() ? null : Collections.unmodifiableSet(new HashSet<>(methodWhitelist))
            );
        }
    }
}
