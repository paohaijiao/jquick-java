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
package com.github.paohaijiao.support;
import com.github.paohaijiao.console.JConsole;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.*;
import java.util.*;

public class JQuickJavaObjectFactory {
    public static JConsole console=new JConsole();

    public static Object createByConstructor(String className, List<Object> args) throws Exception {
        Class<?> clazz = Class.forName(className);
        Class<?>[] parameterTypes = getParameterTypes(args);
        Constructor<?> constructor = clazz.getConstructor(parameterTypes);
        Object[] methodArgs = prepareConstructorArguments(constructor, args);
        return constructor.newInstance(methodArgs);
    }
    private static Object[] prepareConstructorArguments(Constructor<?> constructor, List<Object> args) {
        Type[] genericParameterTypes = constructor.getGenericParameterTypes();
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] arguments = new Object[args.size()];
        boolean isVarArgs = constructor.isVarArgs();
        int fixedParamCount = isVarArgs ? parameterTypes.length - 1 : parameterTypes.length;
        for (int i = 0; i < args.size(); i++) {
            Object arg = args.get(i);
            if (arg == null) {
                arguments[i] = null;
                continue;
            }
            if (isVarArgs && i >= fixedParamCount) {
                Class<?> varArgComponentType = parameterTypes[fixedParamCount].getComponentType();
                if (arg instanceof Collection && varArgComponentType.isAssignableFrom(
                        ((Collection<?>) arg).iterator().next().getClass())) {
                    Collection<?> collection = (Collection<?>) arg;
                    Object array = Array.newInstance(varArgComponentType, collection.size());
                    int index = 0;
                    for (Object item : collection) {
                        Array.set(array, index++, item);
                    }
                    arguments[i] = array;
                    continue;
                }
                if (varArgComponentType.isAssignableFrom(arg.getClass())) {
                    arguments[i] = arg;
                    continue;
                }
            }
            if (i < genericParameterTypes.length) {
                Type paramType = genericParameterTypes[i];
                if (paramType instanceof ParameterizedType && arg instanceof Collection) {
                    ParameterizedType pType = (ParameterizedType) paramType;
                    Type rawType = pType.getRawType();
                    if (rawType == List.class || rawType == Collection.class) {
                        arguments[i] = new ArrayList<>((Collection<?>) arg);
                        continue;
                    } else if (rawType == Set.class) {
                        arguments[i] = new HashSet<>((Collection<?>) arg);
                        continue;
                    }
                }
                if (paramType instanceof ParameterizedType && arg instanceof Map) {
                    ParameterizedType pType = (ParameterizedType) paramType;
                    Type rawType = pType.getRawType();
                    if (rawType == Map.class) {
                        arguments[i] = new HashMap<>((Map<?, ?>) arg);
                        continue;
                    }
                }
            }
            arguments[i] = arg;
        }
        if (isVarArgs && args.size() > fixedParamCount) {
            int varArgCount = args.size() - fixedParamCount;
            Class<?> varArgComponentType = parameterTypes[fixedParamCount].getComponentType();
            Object varArgsArray = Array.newInstance(varArgComponentType, varArgCount);
            for (int i = 0; i < varArgCount; i++) {
                Object arg = arguments[fixedParamCount + i];
                Array.set(varArgsArray, i, arg);
            }
            Object[] newArguments = new Object[fixedParamCount + 1];
            System.arraycopy(arguments, 0, newArguments, 0, fixedParamCount);
            newArguments[fixedParamCount] = varArgsArray;
            return newArguments;
        }
        return arguments;
    }

    public static Object  createByStaticMethod(String className, String methodName, List<Object> args) throws Exception {
        Class<?> clazz = Class.forName(className);
        Class<?>[] paramTypes = args.stream()
                .map(arg -> arg != null ? arg.getClass() : Object.class)
                .toArray(Class<?>[]::new);
        Method method = MethodUtils.getMatchingAccessibleMethod(clazz, methodName, paramTypes);
        if (method == null) {
            method=findMethod(clazz,methodName,paramTypes);
            if(method==null){
                throw new NoSuchMethodException(
                        String.format("No accessible method '%s' found in class %s with parameters %s",
                                methodName, className, Arrays.toString(paramTypes))
                );
            }
        }
        Object[] methodArgs = prepareMethodArguments(method, args);
        return MethodUtils.invokeStaticMethod(clazz, methodName, methodArgs);
    }
    private static Method  findMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes){
        Method method = MethodUtils.getMatchingMethod(
                clazz,
                methodName,
                paramTypes
        );
        return method;
    }

    public static Object createByInstanceMethod(Object target, String methodName, List<Object> args) throws Exception {
        Class<?> clazz = target.getClass();
        Class<?>[] paramTypes = args.stream()
                .map(arg -> arg != null ? arg.getClass() : Object.class)
                .toArray(Class<?>[]::new);
        Method method = MethodUtils.getMatchingAccessibleMethod(clazz, methodName, paramTypes);
        if (method == null) {
            throw new NoSuchMethodException(
                    String.format("Method %s(%s) not found in %s", methodName, Arrays.toString(paramTypes), clazz.getName()));
        }
        Object[] methodArgs = prepareMethodArguments(method, args);
        return MethodUtils.invokeMethod(target, methodName, methodArgs);
    }
    private static Object[] prepareMethodArguments(Method method, List<Object> args) {
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] arguments = new Object[args.size()];
        boolean isVarArgs = method.isVarArgs();
        int fixedParamCount = isVarArgs ? parameterTypes.length - 1 : parameterTypes.length;//every method have no more than 1 var argue , and it occur in last index of method param
        for (int i = 0; i < args.size(); i++) {
            Object arg = args.get(i);
            if (arg == null) {
                arguments[i] = null;
                continue;
            }
            if (isVarArgs && i >= fixedParamCount) {//process var argue
                Class<?> varArgComponentType = parameterTypes[fixedParamCount].getComponentType();
                if (arg instanceof Collection && varArgComponentType.isAssignableFrom(
                        ((Collection<?>) arg).iterator().next().getClass())) {
                    Collection<?> collection = (Collection<?>) arg;
                    Object array = Array.newInstance(varArgComponentType, collection.size());
                    int index = 0;
                    for (Object item : collection) {
                        Array.set(array, index++, item);
                    }
                    arguments[i] = array;
                    continue;
                }
                // single element need add to argument array
                if (varArgComponentType.isAssignableFrom(arg.getClass())) {
                    arguments[i] = arg;
                    continue;
                }
            }
            // handle ordinary parameters (including non-array parts of fixed and variable parameters)
            if (i < genericParameterTypes.length) {
                Type paramType = genericParameterTypes[i];
                // handling generic collections
                if (paramType instanceof ParameterizedType && arg instanceof Collection) {
                    ParameterizedType pType = (ParameterizedType) paramType;
                    Type rawType = pType.getRawType();
                    if (rawType == List.class || rawType == Collection.class) {
                        arguments[i] = new ArrayList<>((Collection<?>) arg);
                        continue;
                    } else if (rawType == Set.class) {
                        arguments[i] = new HashSet<>((Collection<?>) arg);
                        continue;
                    }
                }
                // handling generic map
                if (paramType instanceof ParameterizedType && arg instanceof Map) {
                    ParameterizedType pType = (ParameterizedType) paramType;
                    Type rawType = pType.getRawType();
                    if (rawType == Map.class) {
                        arguments[i] = new HashMap<>((Map<?, ?>) arg);
                        continue;
                    }
                }
            }
            arguments[i] = arg;
        }

        //if it is a variable parameter method and the number of parameters exceeds the fixed number,
        // it is necessary to package the variable parameters
        if (isVarArgs && args.size() > fixedParamCount) {
            int varArgCount = args.size() - fixedParamCount;
            Class<?> varArgComponentType = parameterTypes[fixedParamCount].getComponentType();
            //create a variable parameter array
            Object varArgsArray = Array.newInstance(varArgComponentType, varArgCount);
            for (int i = 0; i < varArgCount; i++) {
                Object arg = arguments[fixedParamCount + i];
                Array.set(varArgsArray, i, arg);
            }
            //create a new parameter array, merge fixed parameter and variable parameter arrays
            Object[] newArguments = new Object[fixedParamCount + 1];
            System.arraycopy(arguments, 0, newArguments, 0, fixedParamCount);
            newArguments[fixedParamCount] = varArgsArray;
            return newArguments;
        }
        return arguments;
    }
    private static void buildArgs(List<Object> args){
        for (int i = 0; i < args.size(); i++) {
            Object arg = args.get(i);
            if (arg instanceof List && !(arg instanceof ArrayList)) {
                args.set(i, new ArrayList<>((List<?>) arg));
            }
            if (arg instanceof Map && !(arg instanceof HashMap)) {
                Map m = (Map) arg;
                HashMap map= new HashMap<>();
                map.putAll(m);
                args.set(i, map);
            }
        }
    }

    private static Class<?>[] getParameterTypes(List<Object> args) {
        if (args == null || args.isEmpty()) {
            return new Class<?>[0];
        }
        Class<?>[] parameterTypes = new Class<?>[args.size()];
        for (int i = 0; i < args.size(); i++) {
            Object arg = args.get(i);
            if (arg == null) {
                parameterTypes[i] = Object.class;
                continue;
            }
            parameterTypes[i] = handlePrimitiveTypes(arg.getClass());
            if (arg instanceof Collection) {
                if(arg instanceof List) {
                    parameterTypes[i] = List.class;
                }else{
                    parameterTypes[i] = Collection.class;
                }

            } else if (arg instanceof Map) {
                parameterTypes[i] =Map.class;
            }
        }
        return parameterTypes;
    }
    private static Class<?> handlePrimitiveTypes(Class<?> clazz) {
        if (clazz == Integer.class) return int.class;
        if (clazz == Double.class) return double.class;
        if (clazz == Boolean.class) return boolean.class;
        if (clazz == Long.class) return long.class;
        if (clazz == Float.class) return float.class;
        if (clazz == Short.class) return short.class;
        if (clazz == Byte.class) return byte.class;
        if (clazz == Character.class) return char.class;
        if (Collection.class.isAssignableFrom(clazz)) {
            return Collection.class;
        }
        return clazz;
    }
    public static Class<?> getCollectionType(Collection<?> collection, int paramIndex) {
        try {
            if (collection == null) {
                return Collection.class;
            }
            Class<?> collectionClass = collection.getClass();
            if (collectionClass.isInterface()) {
                if (List.class.isAssignableFrom(collectionClass)) {
                    collectionClass = ArrayList.class;
                } else if (Set.class.isAssignableFrom(collectionClass)) {
                    collectionClass = HashSet.class;
                } else {
                    collectionClass = Collection.class;
                }
            }
            return collectionClass;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error determining collection type for parameter at index " + paramIndex, e);
        }
    }
    public static Class<?> getMapType(Map<?, ?> map, int paramIndex) {
        try {
            if (map == null) {
                return Map.class;
            }
            Class<?> mapClass = map.getClass();
            if (mapClass.isInterface()) {
                mapClass = HashMap.class;
            }
            if (!map.isEmpty()) {
                Map.Entry<?, ?> firstEntry = map.entrySet().iterator().next();
                Class<?> keyType = firstEntry.getKey() != null ? firstEntry.getKey().getClass() : Object.class;
                Class<?> valueType = firstEntry.getValue() != null ? firstEntry.getValue().getClass() : Object.class;
                return mapClass;
            }
            return mapClass;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error determining map type for parameter at index " + paramIndex, e);
        }
    }
    public static Type createParameterizedCollectionType(Class<?> collectionClass, Class<?> elementType) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{elementType};
            }

            @Override
            public Type getRawType() {
                return collectionClass;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
    public static Type createParameterizedMapType(Class<?> mapClass, Class<?> keyType, Class<?> valueType) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{keyType, valueType};
            }

            @Override
            public Type getRawType() {
                return mapClass;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    public static Object create(String className, String methodName, List<Object> args, Boolean isStatic, Object target) throws Exception {
        if (methodName == null) {
            return createByConstructor(className, args);
        } else if (isStatic != null && isStatic) {
            return createByStaticMethod(className, methodName, args);
        } else if (target != null) {
            return createByInstanceMethod(target, methodName, args);
        } else {
            throw new IllegalArgumentException("Invalid combination of parameters for object creation");
        }
    }
}
