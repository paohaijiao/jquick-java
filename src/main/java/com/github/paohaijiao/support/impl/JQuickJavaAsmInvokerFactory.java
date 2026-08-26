package com.github.paohaijiao.support.impl;

import com.jquick.asm.util.JQuickBytecodeUtil;
import com.jquick.asm.writer.JQuickClassWriterTool;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

final class JQuickJavaAsmInvokerFactory {

    private static final String PACKAGE_NAME = "com.github.paohaijiao.support.impl.generated";

    private static final ConcurrentMap<Method, JQuickJavaAsmMethodInvoker> METHOD_CACHE = new ConcurrentHashMap<>();

    private static final ConcurrentMap<Constructor<?>, JQuickJavaAsmConstructorInvoker> CONSTRUCTOR_CACHE = new ConcurrentHashMap<>();

    private static final AtomicLong COUNTER = new AtomicLong();

    private JQuickJavaAsmInvokerFactory() {
    }

    static JQuickJavaAsmMethodInvoker getMethodInvoker(Method method) {
        return METHOD_CACHE.computeIfAbsent(method, JQuickJavaAsmInvokerFactory::buildMethodInvoker);
    }

    static JQuickJavaAsmConstructorInvoker getConstructorInvoker(Constructor<?> constructor) {
        return CONSTRUCTOR_CACHE.computeIfAbsent(constructor, JQuickJavaAsmInvokerFactory::buildConstructorInvoker);
    }

    private static JQuickJavaAsmMethodInvoker buildMethodInvoker(Method method) {
        String className = PACKAGE_NAME + ".MethodInvoker" + COUNTER.incrementAndGet();
        String owner = Type.getInternalName(method.getDeclaringClass());
        String descriptor = Type.getMethodDescriptor(method);
        Type[] argumentTypes = Type.getArgumentTypes(method);
        Type returnType = Type.getReturnType(method);
        boolean isStatic = Modifier.isStatic(method.getModifiers());
        byte[] bytes = JQuickClassWriterTool.builder(className)
                .implementInterface(JQuickJavaAsmMethodInvoker.class)
                .addMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", JQuickJavaAsmInvokerFactory::writeConstructor)
                .addMethod(Opcodes.ACC_PUBLIC, "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", mv -> {
                    mv.visitCode();
                    if (!isStatic) {
                        mv.visitVarInsn(Opcodes.ALOAD, 1);
                        mv.visitTypeInsn(Opcodes.CHECKCAST, owner);
                    }
                    for (int i = 0; i < argumentTypes.length; i++) {
                        mv.visitVarInsn(Opcodes.ALOAD, 2);
                        pushInt(mv, i);
                        mv.visitInsn(Opcodes.AALOAD);
                        emitUnboxOrCast(mv, argumentTypes[i]);
                    }
                    mv.visitMethodInsn(isStatic ? Opcodes.INVOKESTATIC : invokeOpcode(method), owner, method.getName(), descriptor, method.getDeclaringClass().isInterface());
                    emitBoxOrNull(mv, returnType);
                    mv.visitInsn(Opcodes.ARETURN);
                    mv.visitMaxs(0, 0);
                    mv.visitEnd();
                })
                .build();
        try {
            Class<?> generatedClass = JQuickBytecodeUtil.defineClass(className, bytes);
            return (JQuickJavaAsmMethodInvoker) generatedClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create ASM method invoker for: " + method, e);
        }
    }

    private static JQuickJavaAsmConstructorInvoker buildConstructorInvoker(Constructor<?> constructor) {
        String className = PACKAGE_NAME + ".ConstructorInvoker" + COUNTER.incrementAndGet();
        String owner = Type.getInternalName(constructor.getDeclaringClass());
        String descriptor = Type.getConstructorDescriptor(constructor);
        Type[] argumentTypes = Type.getArgumentTypes(descriptor);
        byte[] bytes = JQuickClassWriterTool.builder(className)
                .implementInterface(JQuickJavaAsmConstructorInvoker.class)
                .addMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", JQuickJavaAsmInvokerFactory::writeConstructor)
                .addMethod(Opcodes.ACC_PUBLIC, "newInstance", "([Ljava/lang/Object;)Ljava/lang/Object;", mv -> {
                    mv.visitCode();
                    mv.visitTypeInsn(Opcodes.NEW, owner);
                    mv.visitInsn(Opcodes.DUP);
                    for (int i = 0; i < argumentTypes.length; i++) {
                        mv.visitVarInsn(Opcodes.ALOAD, 1);
                        pushInt(mv, i);
                        mv.visitInsn(Opcodes.AALOAD);
                        emitUnboxOrCast(mv, argumentTypes[i]);
                    }
                    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, owner, "<init>", descriptor, false);
                    mv.visitInsn(Opcodes.ARETURN);
                    mv.visitMaxs(0, 0);
                    mv.visitEnd();
                })
                .build();
        try {
            Class<?> generatedClass = JQuickBytecodeUtil.defineClass(className, bytes);
            return (JQuickJavaAsmConstructorInvoker) generatedClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create ASM constructor invoker for: " + constructor, e);
        }
    }

    private static void writeConstructor(MethodVisitor mv) {
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private static int invokeOpcode(Method method) {
        if (method.getDeclaringClass().isInterface()) {
            return Opcodes.INVOKEINTERFACE;
        }
        return Opcodes.INVOKEVIRTUAL;
    }

    private static void emitUnboxOrCast(MethodVisitor mv, Type type) {
        switch (type.getSort()) {
            case Type.BOOLEAN:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Boolean");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
                return;
            case Type.BYTE:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Byte");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B", false);
                return;
            case Type.CHAR:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Character");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C", false);
                return;
            case Type.SHORT:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Short");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S", false);
                return;
            case Type.INT:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Integer");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
                return;
            case Type.FLOAT:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Float");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F", false);
                return;
            case Type.LONG:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Long");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
                return;
            case Type.DOUBLE:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Double");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
                return;
            case Type.ARRAY:
            case Type.OBJECT:
                mv.visitTypeInsn(Opcodes.CHECKCAST, type.getInternalName());
                return;
            default:
                throw new IllegalArgumentException("Unsupported argument type: " + type);
        }
    }

    private static void emitBoxOrNull(MethodVisitor mv, Type type) {
        switch (type.getSort()) {
            case Type.VOID:
                mv.visitInsn(Opcodes.ACONST_NULL);
                return;
            case Type.BOOLEAN:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
                return;
            case Type.BYTE:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
                return;
            case Type.CHAR:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
                return;
            case Type.SHORT:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
                return;
            case Type.INT:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                return;
            case Type.FLOAT:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
                return;
            case Type.LONG:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                return;
            case Type.DOUBLE:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                return;
            case Type.ARRAY:
            case Type.OBJECT:
                return;
            default:
                throw new IllegalArgumentException("Unsupported return type: " + type);
        }
    }

    private static void pushInt(MethodVisitor mv, int value) {
        switch (value) {
            case 0:
                mv.visitInsn(Opcodes.ICONST_0);
                return;
            case 1:
                mv.visitInsn(Opcodes.ICONST_1);
                return;
            case 2:
                mv.visitInsn(Opcodes.ICONST_2);
                return;
            case 3:
                mv.visitInsn(Opcodes.ICONST_3);
                return;
            case 4:
                mv.visitInsn(Opcodes.ICONST_4);
                return;
            case 5:
                mv.visitInsn(Opcodes.ICONST_5);
                return;
            default:
                if (value <= Byte.MAX_VALUE) {
                    mv.visitIntInsn(Opcodes.BIPUSH, value);
                } else if (value <= Short.MAX_VALUE) {
                    mv.visitIntInsn(Opcodes.SIPUSH, value);
                } else {
                    mv.visitLdcInsn(value);
                }
        }
    }
}
