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
package evalue;

import com.github.paohaijiao.factory.JQuickJavaAnnontationResultProviderFactory;
import org.junit.Test;

import java.io.IOException;

public class JEvalueTest {

    @Test
    public void tesstAction() throws IOException {
        JQuickJavaAnnontationResultProviderFactory router = new JQuickJavaAnnontationResultProviderFactory();
        router.scanAndRegister("com.github.paohaijiao.evalue.impl");
        System.out.println(router.route(String.class, "hello"));
        System.out.println(router.route(Integer.class, 42));
    }
}
