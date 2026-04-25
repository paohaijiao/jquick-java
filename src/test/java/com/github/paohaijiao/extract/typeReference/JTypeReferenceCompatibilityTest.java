package com.github.paohaijiao.extract.typeReference;

import com.github.paohaijiao.support.JQuickJavaTypeReference;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class JTypeReferenceCompatibilityTest {
    @Test
    public void testPrimitiveCompatibility() {
        JQuickJavaTypeReference<Integer> intRef = JQuickJavaTypeReference.of(int.class);
        assertTrue(intRef.isAssignableFrom(JQuickJavaTypeReference.of(Integer.class)));  // int ← Integer
        assertTrue(JQuickJavaTypeReference.of(Integer.class).isAssignableFrom(intRef));  // Integer ← int
        assertFalse(intRef.isAssignableFrom(JQuickJavaTypeReference.of(Long.class)));    // int ← long
        JQuickJavaTypeReference<Boolean> boolRef = JQuickJavaTypeReference.of(boolean.class);
        assertTrue(boolRef.isAssignableFrom(JQuickJavaTypeReference.of(Boolean.class))); // boolean ← Boolean
    }
    @Test
    public void testCollectionCompatibility() {
        JQuickJavaTypeReference<List<Number>> numListRef = JQuickJavaTypeReference.listOf(Number.class);
        JQuickJavaTypeReference<List<Integer>> intListRef = JQuickJavaTypeReference.listOf(Integer.class);
        assertTrue(numListRef.isAssignableFrom(intListRef));  // List<Number> ← List<Integer>
        assertTrue(intListRef.isAssignableFrom(numListRef)); // List<Integer> ← List<Number>
        JQuickJavaTypeReference<Set<CharSequence>> seqSetRef = JQuickJavaTypeReference.setOf(CharSequence.class);
        JQuickJavaTypeReference<Set<String>> strSetRef = JQuickJavaTypeReference.setOf(String.class);
        assertTrue(seqSetRef.isAssignableFrom(strSetRef));    // Set<CharSequence> ← Set<String>
    }
    @Test
    public void testArrayCompatibility() {
        JQuickJavaTypeReference<Number[]> numArrayRef = JQuickJavaTypeReference.arrayOf(Number.class);
        JQuickJavaTypeReference<Integer[]> intArrayRef = JQuickJavaTypeReference.arrayOf(Integer.class);
        assertTrue(numArrayRef.isAssignableFrom(intArrayRef));  // Number[] ← Integer[]
        assertFalse(intArrayRef.isAssignableFrom(numArrayRef));  // Integer[] ← Number[]
        JQuickJavaTypeReference<Integer[][]> int2dArrayRef = JQuickJavaTypeReference.arrayOf(Integer[].class);
        JQuickJavaTypeReference<Number[][]> num2dArrayRef = JQuickJavaTypeReference.arrayOf(Number[].class);
        assertTrue(num2dArrayRef.isAssignableFrom(int2dArrayRef)); // Number[][] ← Integer[][]
    }

    @Test
    public void testMapCompatibility() {
        JQuickJavaTypeReference<Map<CharSequence, Number>> mapRef =
                JQuickJavaTypeReference.mapOf(CharSequence.class, Number.class);
        JQuickJavaTypeReference<Map<String, Integer>> concreteMapRef =
                JQuickJavaTypeReference.mapOf(String.class, Integer.class);
        assertTrue(mapRef.isAssignableFrom(concreteMapRef));  // Map<CharSequence, Number> ← Map<String, Integer>
    }
    @Test
    public void testNullCompatibility() {
        JQuickJavaTypeReference<String> strRef = JQuickJavaTypeReference.of(String.class);
        assertTrue(strRef.isAssignableFrom(JQuickJavaTypeReference.of(Void.class)));  // String ← null
        JQuickJavaTypeReference<Integer> intRef = JQuickJavaTypeReference.of(int.class);
        assertFalse(intRef.isAssignableFrom(JQuickJavaTypeReference.of(Void.class))); // int ← null
    }

    @Test
    public void testEdgeCases() {
        JQuickJavaTypeReference<int[]> primitiveArrayRef = JQuickJavaTypeReference.of(int[].class);
        JQuickJavaTypeReference<Integer[]> wrapperArrayRef = JQuickJavaTypeReference.arrayOf(Integer.class);
        assertFalse(primitiveArrayRef.isAssignableFrom(wrapperArrayRef)); // int[] ≠ Integer[]
        JQuickJavaTypeReference<List<? extends Number>> wildcardRef =
                new JQuickJavaTypeReference<List<? extends Number>>() {};
        JQuickJavaTypeReference<List<Integer>> concreteRef = JQuickJavaTypeReference.listOf(Integer.class);
       // assertTrue(wildcardRef.isAssignableFrom(concreteRef)); // List<? extends Number> ← List<Integer>
    }
}
