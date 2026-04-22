package com.github.paohaijiao.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.paohaijiao.exception.JAssert;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
public class JQuickJavaTypeReference<T> extends TypeReference<T> {

    protected final Type type;

    protected final Class<? super T> rawType;

    protected JQuickJavaTypeReference() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class<?>) {
            throw new IllegalArgumentException("typeReference requires type parameters");
        }
        this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        this.rawType = (Class<? super T>) getRawType(this.type);
    }

    public JQuickJavaTypeReference(Type type) {
        this.type = type;
        this.rawType = (Class<? super T>) getRawType(type);
    }
    public static class AccessibleJTypeReference<T> extends JQuickJavaTypeReference<T> {
        public AccessibleJTypeReference(Type type) {
            super(type);
        }
    }
    public Class<? super T> getRawType() {
        return rawType;
    }

    public Type getType() {
        return type;
    }

    public Type[] getActualTypeArguments() {
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments();
        }
        return new Type[0];
    }

    public boolean isArray() {
        return type instanceof GenericArrayType ||
                (type instanceof Class<?> && ((Class<?>) type).isArray());
    }

    public Type getArrayComponentType() {
        if (type instanceof GenericArrayType) {
            return ((GenericArrayType) type).getGenericComponentType();
        } else if (type instanceof Class<?> && ((Class<?>) type).isArray()) {
            return ((Class<?>) type).getComponentType();
        }
        throw new IllegalStateException("Not an array type: " + type);
    }

    public static <E> JQuickJavaTypeReference<List<E>> listOf(Class<E> elementType) {
        return new JQuickJavaTypeReference<List<E>>() {
            @Override
            public Type getType() {
                return new ParameterizedType() {
                    public Type[] getActualTypeArguments() { return new Type[]{elementType}; }
                    public Type getRawType() { return List.class; }
                    public Type getOwnerType() { return null; }
                };
            }
        };
    }

    public static <E> JQuickJavaTypeReference<Set<E>> setOf(Class<E> elementType) {
        return new JQuickJavaTypeReference<Set<E>>() {
            @Override
            public Type getType() {
                return new ParameterizedType() {
                    @Override
                    public Type[] getActualTypeArguments() {
                        return new Type[]{elementType}; //  E
                    }
                    @Override
                    public Type getRawType() {
                        return Set.class; //  Set
                    }
                    @Override
                    public Type getOwnerType() {
                        return null;
                    }
                };
            }
        };
    }

    public static <K, V> JQuickJavaTypeReference<Map<K, V>> mapOf(Class<K> keyType, Class<V> valueType) {
        return new JQuickJavaTypeReference<Map<K, V>>() {};
    }

    public static <E> JQuickJavaTypeReference<E[]> arrayOf(Class<E> elementType) {
        Class<?> arrayClass = Array.newInstance(elementType, 0).getClass();
        return new JQuickJavaTypeReference<E[]>(arrayClass) {};
    }

    public static <E> JQuickJavaTypeReference<E[]> varargsOf(Class<E> elementType) {
        return arrayOf(elementType);
    }
    private static Class<?>[] convertToClassArray(JQuickJavaTypeReference<?>[] argTypes) {
        if (argTypes == null) return new Class<?>[0];
        Class<?>[] result = new Class<?>[argTypes.length];
        for (int i = 0; i < argTypes.length; i++) {
            result[i] = argTypes[i].getRawType();
        }
        return result;
    }
    public static <I> JQuickJavaTypeReference<I> interfaceOf(Class<I> interfaceType) {
        return new JQuickJavaTypeReference<I>(interfaceType) ;
    }

    public static <T> JQuickJavaTypeReference<T> of(Class<T> type) {
        return new JQuickJavaTypeReference<T>(type);
    }

    public static <T> JQuickJavaTypeReference<T> of(Type type) {
        return new JQuickJavaTypeReference<T>(type) ;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JQuickJavaTypeReference<?> && type.equals(((JQuickJavaTypeReference<?>) obj).type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return type.toString();
    }


    private static Class<?> getRawType(Type type) {
        if (type instanceof com.fasterxml.jackson.databind.JavaType) {// special Type
            return ((com.fasterxml.jackson.databind.JavaType) type).getRawClass();
        }
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return getRawType(((ParameterizedType) type).getRawType());
        } else if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        } else if (type instanceof GenericArrayType) {
            Class<?> componentType = getRawType(((GenericArrayType) type).getGenericComponentType());
            return Array.newInstance(componentType, 0).getClass();
        } else if (type instanceof TypeVariable<?>) {
            TypeVariable<?> tv = (TypeVariable<?>) type;
            Type[] bounds = tv.getBounds();
            if (bounds.length > 0) {
                return getRawType(bounds[0]);
            }
            return Object.class;
        } else {
            JAssert.throwNewException("unsupported type: " + type + " of " + type.getClass());
            return null;
        }
    }
    public boolean isAssignableFrom(JQuickJavaTypeReference<?> targetType) {
        Objects.requireNonNull(targetType, "Target type cannot be null");
        if (targetType.getRawType() == Void.class) {
            return !this.rawType.isPrimitive();
        }
        if (!isRawTypeCompatible(targetType)) {
            return false;
        }
        if (this.isArray() && targetType.isArray()) {
            return isArrayComponentTypeCompatible(targetType);
        }
        if (this.isParameterizedType() && targetType.isParameterizedType()) {
            return areGenericArgsCompatible(targetType);
        }
        return true;
    }
    public boolean isParameterizedType() {
        return type instanceof ParameterizedType;
    }
    private boolean isRawTypeCompatible(JQuickJavaTypeReference<?> targetType) {
        Class<?> targetRawType = targetType.getRawType();
        if (this.rawType.isPrimitive()) {
            Class<?> boxedType = primitiveToWrapper(this.rawType);
            return boxedType.isAssignableFrom(targetRawType)||targetRawType.isAssignableFrom(boxedType)||targetRawType==rawType;
        } else if (targetRawType.isPrimitive()) {
            Class<?> unboxedType = wrapperToPrimitive(targetRawType);
            return this.rawType.isAssignableFrom(unboxedType)|| unboxedType.isAssignableFrom(targetType.rawType);
        }
        return this.rawType.isAssignableFrom(targetRawType);
    }
    private boolean isArrayComponentTypeCompatible(JQuickJavaTypeReference<?> targetType) {
        Type thisComponentType = this.getArrayComponentType();
        Type targetComponentType = targetType.getArrayComponentType();
        Class<?> thisComponentClass = getRawType(thisComponentType);
        Class<?> targetComponentClass = getRawType(targetComponentType);
        return thisComponentClass.isAssignableFrom(targetComponentClass);
    }
    private boolean areGenericArgsCompatible(JQuickJavaTypeReference<?> targetType) {
        Type thisRawType = ((ParameterizedType) this.type).getRawType();
        Type targetRawType = ((ParameterizedType) targetType.type).getRawType();
        if (!thisRawType.equals(targetRawType)) {
            return false;
        }
        Type[] thisArgs = this.getActualTypeArguments();
        Type[] targetArgs = targetType.getActualTypeArguments();
        if (thisArgs.length != targetArgs.length) {
            return false;
        }
        for (int i = 0; i < thisArgs.length; i++) {
            Class<?> thisArgClass = getRawType(thisArgs[i]);
            Class<?> targetArgClass = getRawType(targetArgs[i]);
            if (!thisArgClass.isAssignableFrom(targetArgClass)) {
                return false;
            }
        }
        return true;
    }
    public boolean targetAssignableFrom(Object target) {
        if (target == null) {
            return !rawType.isPrimitive();
        }
        Class<?> targetClass = target.getClass();
        if (rawType.isPrimitive()) {
            Class<?> boxedType = primitiveToWrapper(rawType);
            return boxedType.isAssignableFrom(targetClass);
        } else if (isPrimitiveWrapper(rawType)) {
            Class<?> unboxedType = wrapperToPrimitive(rawType);
            return unboxedType.isAssignableFrom(targetClass) || rawType.isAssignableFrom(targetClass);
        }
        if (isTargetArray()) {
            if (!targetClass.isArray()) return false;
            Type componentType = getArrayComponentType();
            Class<?> componentRawType = getRawType(componentType);
            Class<?> targetComponentType = targetClass.getComponentType();
            return componentRawType.isAssignableFrom(targetComponentType);
        }
        return rawType.isAssignableFrom(targetClass);
    }
    public boolean isTargetArray() {
        if (type instanceof GenericArrayType) {
            return true;
        }
        if (type instanceof Class<?>) {
            return ((Class<?>) type).isArray();
        }
        return false;
    }
    public static Class<?> primitiveToWrapper(Class<?> primitiveType) {
        if (primitiveType == int.class) return Integer.class;
        if (primitiveType == long.class) return Long.class;
        if (primitiveType == double.class) return Double.class;
        if (primitiveType == float.class) return Float.class;
        if (primitiveType == boolean.class) return Boolean.class;
        if (primitiveType == char.class) return Character.class;
        if (primitiveType == byte.class) return Byte.class;
        if (primitiveType == short.class) return Short.class;
        if (primitiveType == void.class) return Void.class;
        return primitiveType;
    }
    public static Class<?> wrapperToPrimitive(Class<?> wrapperType) {
        if (wrapperType == Integer.class) return int.class;
        if (wrapperType == Long.class) return long.class;
        if (wrapperType == Double.class) return double.class;
        if (wrapperType == Float.class) return float.class;
        if (wrapperType == Boolean.class) return boolean.class;
        if (wrapperType == Character.class) return char.class;
        if (wrapperType == Byte.class) return byte.class;
        if (wrapperType == Short.class) return short.class;
        if (wrapperType == Void.class) return void.class;
        return wrapperType;
    }
    public static boolean isPrimitiveWrapper(Class<?> type) {
        return type == Integer.class || type == Long.class || type == Double.class
                || type == Float.class || type == Boolean.class || type == Character.class
                || type == Byte.class || type == Short.class || type == Void.class;
    }
}
