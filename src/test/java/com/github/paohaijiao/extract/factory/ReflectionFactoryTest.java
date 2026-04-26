package com.github.paohaijiao.extract.factory;

import com.github.paohaijiao.support.JQuickJavaTypeReference;
import com.github.paohaijiao.support.JQuickJavaReflectionFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ReflectionFactoryTest {
    public static class TestClass {
        private final String name;
        private final int value;
        public TestClass() {
            this("default", 0);
        }

        public TestClass(String name) {
            this(name, 0);
        }

        public TestClass(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        public TestClass(String... names) {
            this(names != null && names.length > 0 ? names[0] : "default", names != null ? names.length : 0);
        }
        public static String staticConcat(String a, String b) {
            return a + b;
        }

        public static String staticVarargs(String prefix, String... values) {
            return prefix + String.join(",", values);
        }
        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public String concat(String suffix) {
            return name + suffix;
        }
        public String varargsMethod(String prefix, String... values) {
            return prefix + name + String.join(",", values);
        }
    }
    @Test
    public void testConstructorFactory() {
        TestClass instance1 = JQuickJavaReflectionFactory.constructor(TestClass.class).newInstance();
        assertEquals("default", instance1.getName());
        assertEquals(0, instance1.getValue());
        TestClass instance2 = JQuickJavaReflectionFactory.constructor(TestClass.class)
                .newInstance(JQuickJavaTypeReference.of(String.class), "test");
        assertEquals("test", instance2.getName());
        assertEquals(1, instance2.getValue());

        TestClass instance3 = JQuickJavaReflectionFactory.constructor(TestClass.class)
                .newInstance(new JQuickJavaTypeReference<?>[]{JQuickJavaTypeReference.of(String.class), JQuickJavaTypeReference.of(int.class)},
                        "test", 42);
        assertEquals("test", instance3.getName());
        assertEquals(42, instance3.getValue());
        JQuickJavaTypeReference<?>[] references=new JQuickJavaTypeReference<?>[]{
                JQuickJavaTypeReference.of(String.class), JQuickJavaTypeReference.of(String.class), JQuickJavaTypeReference.of(String.class),
                JQuickJavaTypeReference.of(String.class)};
        TestClass instance4 = JQuickJavaReflectionFactory.constructor(TestClass.class)
                .newInstance(references, "a", "b", "c");
        assertEquals("a", instance4.getName());
        assertEquals(3, instance4.getValue());
    }
    @Test
    public void testStaticMethodFactory() {
        String result1 = JQuickJavaReflectionFactory.staticMethod(TestClass.class)
                .invoke("staticConcat",
                        new JQuickJavaTypeReference<?>[]{JQuickJavaTypeReference.of(String.class), JQuickJavaTypeReference.of(String.class)},
                        "Hello", "World");
        assertEquals("HelloWorld", result1);
        String result2 = JQuickJavaReflectionFactory.staticMethod(TestClass.class)
                .invoke("staticVarargs",
                        new JQuickJavaTypeReference<?>[]{JQuickJavaTypeReference.of(String.class), JQuickJavaTypeReference.varargsOf(String.class)},
                        "Prefix:", "a", "b", "c");
        assertEquals("Prefix:a,b,c", result2);
        JQuickJavaTypeReference<?>[] references=new JQuickJavaTypeReference<?>[]{
                JQuickJavaTypeReference.of(String.class), JQuickJavaTypeReference.of(String.class)};
        String result3 = JQuickJavaReflectionFactory.staticMethod(TestClass.class)
                .invoke("staticConcat",references, "A", "B");
        assertEquals("AB", result3);
    }

    @Test
    public void testInstanceMethodFactory() {
        TestClass instance = new TestClass("test");
        String name = JQuickJavaReflectionFactory.instanceMethod(instance).invoke("getName");
        assertEquals("test", name);
        String concatResult = JQuickJavaReflectionFactory.instanceMethod(instance)
                .invoke("concat", JQuickJavaTypeReference.of(String.class), "_suffix");
        assertEquals("test_suffix", concatResult);
        String varargsResult = JQuickJavaReflectionFactory.instanceMethod(instance)
                .invoke("varargsMethod",
                        new JQuickJavaTypeReference<?>[]{JQuickJavaTypeReference.of(String.class), JQuickJavaTypeReference.varargsOf(String.class)},
                        "Prefix:", "x", "y", "z");
        assertEquals("Prefix:testx,y,z", varargsResult);
        String[] params = new String[]{"1", "2", "3"};
        String arrayVarargsResult = JQuickJavaReflectionFactory.instanceMethod(instance)
                .invoke("varargsMethod",
                        new JQuickJavaTypeReference<?>[]{JQuickJavaTypeReference.of(String.class), JQuickJavaTypeReference.varargsOf(String.class)},
                        "Prefix:", params);
        assertEquals("Prefix:test1,2,3", arrayVarargsResult);
    }

    @Test
    public void testComplexTypes() {
        List<String> list = JQuickJavaReflectionFactory.constructor(ArrayList.class).newInstance();
        JQuickJavaReflectionFactory.instanceMethod(list).invoke("add", JQuickJavaTypeReference.of(String.class), "item1");
        JQuickJavaReflectionFactory.instanceMethod(list).invoke("add", JQuickJavaTypeReference.of(String.class), "item2");
        int size = JQuickJavaReflectionFactory.instanceMethod(list).invoke("size");
        assertEquals(2, size);
        String[] array = (String[]) JQuickJavaReflectionFactory.staticMethod(Arrays.class)
                .invoke("copyOf",
                        new JQuickJavaTypeReference<?>[]{JQuickJavaTypeReference.of(Object[].class), JQuickJavaTypeReference.of(int.class)},
                        new String[]{"a", "b", "c"}, 2);
        assertArrayEquals(new String[]{"a", "b"}, array);
    }
}
