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
package com.github.paohaijiao.extract.service;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName com.github.paohaijiao.service
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/7/25
 */
public class JService {

    public String concat(String a, String b) {
        return a + b;
    }

    public static int sum(int a, int b) {
        return a + b;
    }

    public boolean isEven(int num) {
        return num % 2 == 0;
    }

    public List<Integer> addToList(List<Integer> list, Integer value) {
        Integer i=list.get(0);
        list.add(i);
        list.add(value);
        return list;
    }

    public void noReturn() {
        System.out.println("noReturn");
    }

    public String methodWithMixedArgs(String str, int num, boolean flag) {
        return str + num + flag;
    }

    public String methodWithVarArgs(String... strings) {
        return String.join(",", strings);
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        new JService().addToList(list,4);
    }
}
