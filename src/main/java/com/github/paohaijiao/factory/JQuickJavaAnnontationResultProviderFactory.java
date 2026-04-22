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
package com.github.paohaijiao.factory;

import com.github.paohaijiao.anno.JQuickJavaHandlesClass;
import com.github.paohaijiao.evalue.JQuickJavaResultProvider;
import com.github.paohaijiao.util.JReflectionUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;


public class JQuickJavaAnnontationResultProviderFactory extends JQuickJavaResultProviderFactory {
    private static Set<Class<?>> findClassesWithAnnotation(File directory, String packageName, Class<? extends Annotation> annotationClass)
            throws ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClassesWithAnnotation(file, packageName + "." + file.getName(), annotationClass));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(annotationClass)) {
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }
    public static Set<Class<?>> findClassesWithAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classes = new HashSet<>();
        String path = packageName.replace('.', '/');

        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("file")) {
                    classes.addAll(findClassesWithAnnotation(new File(resource.getFile()), packageName, annotationClass));
                } else if (resource.getProtocol().equals("jar")) {
                    classes.addAll(findClassesWithAnnotationInJar(resource, packageName, annotationClass));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to scan classes", e);
        }
        return classes;
   }
    private static Set<Class<?>> findClassesWithAnnotationInJar(URL jarUrl, String packageName, Class<? extends Annotation> annotationClass)
            throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        String path = packageName.replace('.', '/');
        JarURLConnection jarConn = (JarURLConnection) jarUrl.openConnection();
        try (JarFile jarFile = jarConn.getJarFile()) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                    String className = entryName.replace('/', '.').substring(0, entryName.length() - 6);
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(annotationClass)) {
                        classes.add(clazz);
                    }
                }
            }
        }
        return classes;
    }
    /**
     * Scan and register classes with HandlesClass annotation under the specified package and its sub packages
     * @param packageName 要扫描的包名
     */
    public void scanAndRegister(String packageName) {
        Set<Class<?>> annotatedClasses = findClassesWithAnnotation(packageName, JQuickJavaHandlesClass.class);
        List<Class<?>> providerClasses = annotatedClasses.stream()
                .filter(clazz -> JReflectionUtils.isImplementInterface(clazz, JQuickJavaResultProvider.class))
                .collect(Collectors.toList());
        for (Class<?> providerClass : providerClasses) {
            try {
                JQuickJavaHandlesClass annotation = providerClass.getAnnotation(JQuickJavaHandlesClass.class);
                Class<?>[] supportedClasses = annotation.value();
                JQuickJavaResultProvider<?> provider = (JQuickJavaResultProvider<?>) JReflectionUtils.newInstance(providerClass);
                for (Class<?> supportedClass : supportedClasses) {
                    registerProvider(supportedClass, provider);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate and register provider: " + providerClass.getName(), e);
            }
        }
    }

    /**
     * Process class conversion
     * @param input  input
     * @return result
     */
    @SuppressWarnings("unchecked")
    public Object route(Object input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        Class<?> inputClass = input.getClass();
        JQuickJavaResultProvider<Object> provider = findMostSpecificProvider(inputClass);
        if (provider != null) {
            return provider.getResult(input);
        }

        throw new RuntimeException("No provider found for class: " + inputClass.getName());
    }

    /**
     * find the most matching provider
     * @param clazz target class
     * @return the best provider
     */
    @SuppressWarnings("unchecked")
    private JQuickJavaResultProvider<Object> findMostSpecificProvider(Class<?> clazz) {
        if (providers.containsKey(clazz)) {
            return (JQuickJavaResultProvider<Object>) providers.get(clazz);
        }

        for (Map.Entry<Class<?>, JQuickJavaResultProvider<?>> entry : providers.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                return (JQuickJavaResultProvider<Object>) entry.getValue();
            }
        }

        return null;
    }
}
