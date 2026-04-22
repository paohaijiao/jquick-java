package com.github.paohaijiao.typeReference;

import com.github.paohaijiao.factory.JQuickJavaTypeReferenceFactory;
import com.github.paohaijiao.support.JQuickJavaTypeReference;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JTypeReferenceTest {
    @Test
    public void reference() throws IOException {
        JQuickJavaTypeReference<String> stringType = new JQuickJavaTypeReference<String>() {};
        JQuickJavaTypeReference<Integer> integerType = JQuickJavaTypeReference.of(Integer.class);
        JQuickJavaTypeReference<JQuickJavaTypeReference<String>> stringListType = new JQuickJavaTypeReference<JQuickJavaTypeReference<String>>() {};
        JQuickJavaTypeReference<List<Integer>> integerListType = JQuickJavaTypeReference.listOf(Integer.class);
        JQuickJavaTypeReference<Set<Long>> longSetType = JQuickJavaTypeReference.setOf(Long.class);
        JQuickJavaTypeReference<Map<String, Integer>> mapType = new JQuickJavaTypeReference<Map<String, Integer>>() {};
        JQuickJavaTypeReference<Map<Long, Double>> mapType2 = JQuickJavaTypeReference.mapOf(Long.class, Double.class);
        JQuickJavaTypeReference<String[]> stringArrayType = JQuickJavaTypeReference.varargsOf(String.class);
        JQuickJavaTypeReference<Runnable> runnableType = JQuickJavaTypeReference.interfaceOf(Runnable.class);
        JQuickJavaTypeReference<Map<String, List<Set<Integer>>>> complexType = new JQuickJavaTypeReference<Map<String, List<Set<Integer>>>>() {};

        JQuickJavaTypeReference type= JQuickJavaTypeReference.of(String.class);
        System.out.println(type);
    }
    @Test
    public void factory() throws IOException {
        JQuickJavaTypeReference<?> stringRef = JQuickJavaTypeReferenceFactory.fromClassName("java.lang.String");
        JQuickJavaTypeReference<?> listRef = JQuickJavaTypeReferenceFactory.listFromElementType("java.lang.Integer");
        JQuickJavaTypeReference<?> mapRef = JQuickJavaTypeReferenceFactory.mapFromTypes("java.lang.String", "java.lang.Object");
        JQuickJavaTypeReference<?> arrayRef = JQuickJavaTypeReferenceFactory.arrayFromElementType("java.lang.Integer");
        JQuickJavaTypeReference<?> complexRef = JQuickJavaTypeReferenceFactory.fromTypeString("java.util.Map<java.lang.String, java.util.List<java.lang.Integer>>");
        System.out.println(complexRef);
    }
    @Test
    public void testPrimitiveTypes() {
        JQuickJavaTypeReference<Integer> intRef = JQuickJavaTypeReference.of(int.class);
        assertTrue(intRef.targetAssignableFrom(10));       // int -> int
        assertFalse(intRef.targetAssignableFrom(10L));    // long -> int
        assertTrue(intRef.targetAssignableFrom(Integer.valueOf(10)));  // Integer -> int
        //assertFalse(intRef.isAssignableFrom(null));    // null -> int
        JQuickJavaTypeReference<Boolean> boolRef = JQuickJavaTypeReference.of(boolean.class);
        assertTrue(boolRef.targetAssignableFrom(true));    // boolean -> boolean
        assertTrue(boolRef.targetAssignableFrom(Boolean.TRUE)); // Boolean -> boolean
        assertFalse(boolRef.targetAssignableFrom(1));      // int -> boolean
    }

    @Test
    public void testWrapperTypes() {
        JQuickJavaTypeReference<Integer> integerRef = JQuickJavaTypeReference.of(Integer.class);
        assertTrue(integerRef.targetAssignableFrom(10));       // int -> Integer
        assertTrue(integerRef.targetAssignableFrom(Integer.valueOf(10))); // Integer -> Integer
        assertFalse(integerRef.targetAssignableFrom(10L));     // long -> Integer
        assertTrue(integerRef.targetAssignableFrom(null));     // null -> Integer
        JQuickJavaTypeReference<Double> doubleRef = JQuickJavaTypeReference.of(Double.class);
        assertTrue(doubleRef.targetAssignableFrom(3.14));      // double -> Double
        assertFalse(doubleRef.targetAssignableFrom(3.14f));    // float -> Double
    }

    @Test
    public void testArrays() {
        JQuickJavaTypeReference<String[]> stringArrayRef = JQuickJavaTypeReference.arrayOf(String.class);
        String[] strArray = new String[]{"a", "b"};
        Object[] objArray = new Object[]{"a", "b"};
        Integer[] intArray = new Integer[]{1, 2};
        assertTrue(stringArrayRef.targetAssignableFrom(strArray));  // String[] -> String[]
        assertFalse(stringArrayRef.targetAssignableFrom(objArray)); // Object[] -> String[]
        assertFalse(stringArrayRef.targetAssignableFrom(intArray)); // Integer[] -> String[]
        JQuickJavaTypeReference<int[]> intArrayRef = JQuickJavaTypeReference.of(int[].class);
        int[] intArr = new int[]{1, 2};
        Integer[] integerArr = new Integer[]{1, 2};
        assertTrue(intArrayRef.targetAssignableFrom(intArr));      // int[] -> int[]
        assertFalse(intArrayRef.targetAssignableFrom(integerArr)); // Integer[] -> int[]
    }

    @Test
    public void testCollections() {
        JQuickJavaTypeReference<List<String>> stringListRef = JQuickJavaTypeReference.listOf(String.class);
        List<String> stringList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();
        List<Object> objList = new ArrayList<>();
        assertTrue(stringListRef.targetAssignableFrom(stringList)); // List<String> -> List<String>
        assertTrue(stringListRef.targetAssignableFrom(intList));    // List<Integer> -> List<String>
        assertTrue(stringListRef.targetAssignableFrom(objList));    // List<Object> -> List<String>
        JQuickJavaTypeReference<Set<Double>> doubleSetRef = JQuickJavaTypeReference.setOf(Double.class);
        Set<Double> doubleSet = new HashSet<>();
        Set<Number> numberSet = new HashSet<>();
        assertTrue(doubleSetRef.targetAssignableFrom(doubleSet));   // Set<Double> -> Set<Double>
        assertTrue(doubleSetRef.targetAssignableFrom(numberSet));   // Set<Number> -> Set<Double>
    }

    @Test
    public void testMaps() {
        // Map<String, Integer>
        JQuickJavaTypeReference<Map<String, Integer>> mapRef = JQuickJavaTypeReference.mapOf(String.class, Integer.class);
        Map<String, Integer> stringIntMap = new HashMap<>();
        Map<Object, Number> objNumberMap = new HashMap<>();
        assertTrue(mapRef.targetAssignableFrom(stringIntMap));  // Map<String, Integer> -> Map<String, Integer>
        assertTrue(mapRef.targetAssignableFrom(objNumberMap));  // Map<Object, Number> -> Map<String, Integer>
    }

    @Test
    public void testNullHandling() {
        JQuickJavaTypeReference<String> stringRef = JQuickJavaTypeReference.of(String.class);
        assertTrue(stringRef.targetAssignableFrom(null));  // null -> String (允许)
        JQuickJavaTypeReference<Integer> intRef = JQuickJavaTypeReference.of(int.class);
        assertFalse(intRef.targetAssignableFrom(null));    // null -> int (不允许)
    }

    @Test
    public void testWildcardAndGenericTypes() {
        // List<?> 可以接受任何 List
        JQuickJavaTypeReference<List<?>> wildcardListRef = new JQuickJavaTypeReference<List<?>>() {};
        List<String> stringList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();
        assertTrue(wildcardListRef.targetAssignableFrom(stringList)); // List<String> -> List<?>
        assertTrue(wildcardListRef.targetAssignableFrom(intList));    // List<Integer> -> List<?>
        // List<? extends Number> 可以接受 List<Double>, List<Integer>
        JQuickJavaTypeReference<List<? extends Number>> numberListRef = new JQuickJavaTypeReference<List<? extends Number>>() {};
        List<Double> doubleList = new ArrayList<>();
        List<String> strList = new ArrayList<>();
        assertTrue(numberListRef.targetAssignableFrom(doubleList)); // List<Double> -> List<? extends Number>
    }
}
