package com.github.paohaijiao.lsp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * LSP 功能冒烟测试：覆盖帧协议编解码、文档同步与诊断、符号收集、
 * 补全/悬停/定义/文档符号，以及语言服务器的完整消息分发。
 */
public class JQuickLanguageServerTest {

    /** 构造一条 JQuick 测试脚本。 */
    private String sampleScript() {
        return "import java.lang.String as Str;\n"
                + "int def add(int: a, int: b) {\n"
                + "    int c = a + b;\n"
                + "    return c;\n"
                + "}\n"
                + "int def main() {\n"
                + "    int x = 1;\n"
                + "    int y = 2;\n"
                + "    int z = this.add(int: x, int: y);\n"
                + "    return z;\n"
                + "}\n";
    }

    @Test
    public void testMessageRoundTrip() throws IOException {
        JsonObject request = JQuickLspMessage.request(new JsonPrimitive("1"), "initialize", null);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        JQuickLspMessage.write(buffer, request);
        JsonObject read = JQuickLspMessage.read(new ByteArrayInputStream(buffer.toByteArray()));
        assertNotNull(read);
        assertEquals("initialize", read.get("method").getAsString());
        assertEquals("1", read.get("id").getAsString());

        JsonObject response = JQuickLspMessage.response(new JsonPrimitive("1"), new JsonObject());
        assertEquals("2.0", response.get("jsonrpc").getAsString());
        assertTrue(response.has("result"));

        JsonObject err = JQuickLspMessage.error(new JsonPrimitive("2"), -32601, "not found");
        assertEquals(-32601, err.getAsJsonObject("error").get("code").getAsInt());

        JsonObject notification = JQuickLspMessage.notification("exit", null);
        assertFalse(notification.has("id"));
    }

    @Test
    public void testDocumentManager() {
        JQuickDocumentManager manager = new JQuickDocumentManager();
        String uri = "file:///test.jquick";
        manager.open(uri, "jquick", 1, sampleScript());
        JQuickDocumentManager.ParseResult result = manager.parse(uri);
        assertNotNull(result);
        assertNotNull(result.tree);
        assertTrue("正常脚本不应有诊断: " + result.diagnostics, result.diagnostics.isEmpty());

        // 语法错误应产生诊断
        JQuickDocumentManager.ParseResult bad = JQuickDocumentManager.parseText("int def add( {\n");
        assertFalse("错误脚本应有诊断", bad.diagnostics.isEmpty());
        JsonArray changes = new JsonArray();
        JsonObject change = new JsonObject();
        JsonObject range = new JsonObject();
        JsonObject start = new JsonObject();
        start.addProperty("line", 0);
        start.addProperty("character", 0);
        JsonObject end = new JsonObject();
        end.addProperty("line", 0);
        end.addProperty("character", 0);
        range.add("start", start);
        range.add("end", end);
        change.add("range", range);
        change.addProperty("text", "// 注释\n");
        changes.add(change);
        manager.change(uri, 2, changes);
        JQuickLspDocument doc = manager.get(uri);
        assertNotNull(doc);
        assertEquals(2, doc.getVersion());
        assertTrue(doc.getText().startsWith("// 注释"));
    }


    @Test
    public void testSymbolCollector() {
        JQuickDocumentManager.ParseResult result = JQuickDocumentManager.parseText(sampleScript());
        JQuickSymbolCollector.SymbolTable table = JQuickSymbolCollector.collect(result.tree);
        assertEquals(2, table.functions.size());
        assertEquals("add", table.functions.get(0).name);
        assertEquals("int", table.functions.get(0).type);
        assertTrue(table.functions.get(0).detail.contains("def add(int: a, int: b)"));
        assertEquals(1, table.imports.size());
        assertEquals("Str", table.imports.get(0).name);
        assertFalse("应有变量声明", table.variables.isEmpty());
        assertEquals(2, table.params.size());
    }
    @Test
    public void testProviders() {
        JQuickDocumentManager manager = new JQuickDocumentManager();
        JQuickLspProvider provider = new JQuickLspProvider(manager);
        String uri = "file:///test.jquick";
        manager.open(uri, "jquick", 1, sampleScript());
        JsonArray completions = provider.completions(uri, 8, 26);
        assertTrue("补全应包含函数 add", containsLabel(completions, "add"));
        assertTrue("补全应包含关键字 if", containsLabel(completions, "if"));

        // 悬停：函数定义名 add（第 1 行，列 8-11）
        JsonObject hover = provider.hover(uri, 1, 9);
        assertNotNull(hover);
        assertTrue(hover.getAsJsonObject("contents").get("value").getAsString().contains("int def add"));

        // 定义跳转：第 8 行 this.add 的引用
        JsonObject location = provider.definition(uri, 8, 18);
        assertNotNull(location);
        assertEquals(1, location.getAsJsonObject("range").getAsJsonObject("start").get("line").getAsInt());

        // 定义跳转：落在声明名上
        JsonObject self = provider.definition(uri, 1, 9);
        assertNotNull(self);

        // 文档符号（2 个函数 + 4 个变量 + 2 个参数 + 1 个 import）
        JsonArray symbols = provider.documentSymbols(uri);
        assertEquals(9, symbols.size());
        assertTrue(symbols.toString().contains("add"));
        assertTrue(symbols.toString().contains("Str"));
    }

    @Test
    public void testHoverOnUnknownPosition() {
        JQuickDocumentManager manager = new JQuickDocumentManager();
        JQuickLspProvider provider = new JQuickLspProvider(manager);
        String uri = "file:///test.jquick";
        manager.open(uri, "jquick", 1, "int a = 1;");
        assertNull(provider.hover(uri, 5, 5));
    }

    /** 新语法（primary/primaryAtom/argument/typedArgument/methodInvocation）下的引用收集与定义跳转。 */
    @Test
    public void testNewSyntaxInstanceMethodReference() {
        String script = "import java.lang.String as Str;\n"
                + "int def main() {\n"
                + "    Str str1 = \"hello\";\n"
                + "    int len = str1.toUpperCase();\n"
                + "    return len;\n"
                + "}\n";
        JQuickDocumentManager.ParseResult result = JQuickDocumentManager.parseText(script);
        assertTrue("新语法脚本不应有诊断: " + result.diagnostics, result.diagnostics.isEmpty());

        JQuickSymbolCollector.SymbolTable table = JQuickSymbolCollector.collect(result.tree);
        // 实例名 str1（第 3 行）与返回值 len（第 4 行）应作为引用被收集（走 primaryAtom）
        boolean foundStr1 = false;
        boolean foundLen = false;
        for (JQuickSymbolCollector.Symbol ref : table.references) {
            if ("str1".equals(ref.name) && ref.position.line == 3) {
                foundStr1 = true;
            }
            if ("len".equals(ref.name) && ref.position.line == 4) {
                foundLen = true;
            }
        }
        assertTrue("实例名 str1 应被收集为引用", foundStr1);
        assertTrue("变量 len 应被收集为引用", foundLen);

        // 实例名 str1 引用（第 3 行，列 14-18）应跳转到变量声明（第 2 行，列 8-12）
        JQuickDocumentManager manager = new JQuickDocumentManager();
        JQuickLspProvider provider = new JQuickLspProvider(manager);
        String uri = "file:///test.jquick";
        manager.open(uri, "jquick", 1, script);
        JsonObject location = provider.definition(uri, 3, 16);
        assertNotNull(location);
        assertEquals(2, location.getAsJsonObject("range").getAsJsonObject("start").get("line").getAsInt());
        // 方法名 toUpperCase（第 3 行，列 19）命中引用但无对应声明
        JsonObject noTarget = provider.definition(uri, 3, 22);
        assertNull(noTarget);
    }

    @Test
    public void testLanguageServerEndToEnd() throws IOException {
        StringBuilder input = new StringBuilder();
        input.append(frame(JQuickLspMessage.request(new JsonPrimitive("1"), "initialize",
                new JsonObject())));
        // didOpen 通知
        JsonObject openParams = new JsonObject();
        JsonObject textDocument = new JsonObject();
        textDocument.addProperty("uri", "file:///test.jquick");
        textDocument.addProperty("languageId", "jquick");
        textDocument.addProperty("version", 1);
        textDocument.addProperty("text", sampleScript());
        openParams.add("textDocument", textDocument);
        input.append(frame(JQuickLspMessage.notification("textDocument/didOpen", openParams)));
        // completion 请求
        JsonObject completionParams = new JsonObject();
        JsonObject cd = new JsonObject();
        cd.addProperty("uri", "file:///test.jquick");
        completionParams.add("textDocument", cd);
        JsonObject position = new JsonObject();
        position.addProperty("line", 8);
        position.addProperty("character", 26);
        completionParams.add("position", position);
        input.append(frame(JQuickLspMessage.request(new JsonPrimitive("2"), "textDocument/completion",
                completionParams)));
        // hover 请求
        JsonObject hoverParams = new JsonObject();
        hoverParams.add("textDocument", cd);
        JsonObject hpos = new JsonObject();
        hpos.addProperty("line", 1);
        hpos.addProperty("character", 9);
        hoverParams.add("position", hpos);
        input.append(frame(JQuickLspMessage.request(new JsonPrimitive("3"), "textDocument/hover",
                hoverParams)));
        // shutdown + exit
        input.append(frame(JQuickLspMessage.request(new JsonPrimitive("4"), "shutdown", null)));
        input.append(frame(JQuickLspMessage.notification("exit", null)));

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        JQuickLanguageServer server = new JQuickLanguageServer(
                new ByteArrayInputStream(input.toString().getBytes(StandardCharsets.UTF_8)), buffer);
        server.start();

        String output = buffer.toString(StandardCharsets.UTF_8.name());
        // 应有 4 条响应 + 1 条诊断通知
        assertTrue("初始化响应应存在", output.contains("\"id\":\"1\""));
        assertTrue("补全响应应存在", output.contains("\"id\":\"2\""));
        assertTrue("补全应包含函数 add", output.contains("add"));
        assertTrue("悬停响应应存在", output.contains("\"id\":\"3\""));
        assertTrue("诊断通知应存在", output.contains("textDocument/publishDiagnostics"));
        assertTrue("shutdown 响应应存在", output.contains("\"id\":\"4\""));
    }

    /** 将 JSON 消息编码为 LSP 帧（Content-Length 头 + 正文）。 */
    private String frame(JsonObject message) {
        byte[] body = message.toString().getBytes(StandardCharsets.UTF_8);
        return "Content-Length: " + body.length + "\r\n\r\n" + message;
    }

    /** 判断补全列表是否包含指定 label。 */
    private boolean containsLabel(JsonArray items, String label) {
        for (com.google.gson.JsonElement item : items) {
            if (label.equals(item.getAsJsonObject().get("label").getAsString())) {
                return true;
            }
        }
        return false;
    }
}
