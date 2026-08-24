package com.github.paohaijiao.lsp;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * JQuick 语言服务器主入口：通过 stdio 与编辑器通信，实现 LSP（Language Server Protocol）。
 *
 * <p>服务器按 JSON-RPC 2.0 帧协议读取请求/通知，分发给文档同步与特性处理，
 * 并基于 ANTLR 语法树实时发布诊断。支持的能力：文档同步（增量）、补全、悬停、定义跳转、文档符号。</p>
 *
 * <p>启动方式（在编辑器的 Language Server 配置中指定）：</p>
 * <pre>
 * java -cp jquick-java-2.2.0.jar com.github.paohaijiao.lsp.JQuickLanguageServer
 * </pre>
 */
public class JQuickLanguageServer {

    /** JSON-RPC 错误码。 */
    private static final int PARSE_ERROR = -32700;
    private static final int METHOD_NOT_FOUND = -32601;
    private static final int INVALID_PARAMS = -32602;
    private static final int INTERNAL_ERROR = -32603;

    /** 文档管理器。 */
    private final JQuickDocumentManager manager = new JQuickDocumentManager();
    /** 特性提供器。 */
    private final JQuickLspProvider provider = new JQuickLspProvider(manager);
    /** 输入流（默认 System.in，便于测试时替换）。 */
    private final InputStream in;
    /** 输出流（默认 System.out，便于测试时替换）。 */
    private final PrintStream out;

    /** 是否收到 shutdown 请求。 */
    private boolean shutdownRequested = false;
    /** 是否继续运行消息循环。 */
    private boolean running = true;

    /**
     * 构造语言服务器（stdio 模式）。
     *
     * @param out 输出流
     */
    public JQuickLanguageServer(PrintStream out) {
        this(System.in, out);
    }

    /**
     * 构造语言服务器。
     *
     * @param in  输入流
     * @param out 输出流
     */
    public JQuickLanguageServer(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out instanceof PrintStream ? (PrintStream) out : newPrintStream(out);
    }

    /** 构造 UTF-8 编码的打印流（兼容 Java 8 的 Charset 构造器缺失问题）。 */
    private static PrintStream newPrintStream(OutputStream out) {
        try {
            return new PrintStream(out, true, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 不受支持", e);
        }
    }

    /**
     * 启动消息循环：持续读取请求并分发，直到 EOF 或 exit 通知。
     *
     * @throws IOException 流读取失败
     */
    public void start() throws IOException {
        while (running) {
            JsonObject message = JQuickLspMessage.read(in);
            if (message == null) {
                break; // 客户端关闭了 stdin
            }
            dispatch(message);
        }
    }

    /** 分发单条消息：请求（带 id）返回结果，通知（无 id）仅处理。 */
    private void dispatch(JsonObject message) {
        String method = message.has("method") ? message.get("method").getAsString() : null;
        boolean hasId = message.has("id") && !message.get("id").isJsonNull();
        JsonElement id = hasId ? message.get("id") : null;
        JsonObject params = message.has("params") && message.get("params").isJsonObject()
                ? message.get("params").getAsJsonObject() : null;
        try {
            if (method == null) {
                if (hasId) {
                    respondError(id, INVALID_PARAMS, "消息缺少 method");
                }
                return;
            }
            if ("initialize".equals(method)) {
                respond(id, initializeResult());
            } else if ("initialized".equals(method)) {
                // 初始化完成通知，无需响应
            } else if ("shutdown".equals(method)) {
                shutdownRequested = true;
                respond(id, null);
            } else if ("exit".equals(method)) {
                running = false;
            } else if ("$/cancelRequest".equals(method)) {
                // 请求取消通知，忽略
            } else if ("textDocument/didOpen".equals(method)) {
                onDidOpen(params);
            } else if ("textDocument/didChange".equals(method)) {
                onDidChange(params);
            } else if ("textDocument/didClose".equals(method)) {
                onDidClose(params);
            } else if ("textDocument/completion".equals(method)) {
                respond(id, completion(params));
            } else if ("textDocument/hover".equals(method)) {
                respond(id, hover(params));
            } else if ("textDocument/definition".equals(method)) {
                respond(id, definition(params));
            } else if ("textDocument/documentSymbol".equals(method)) {
                respond(id, documentSymbol(params));
            } else {
                respondError(id, METHOD_NOT_FOUND, "未支持的方法: " + method);
            }
        } catch (Exception e) {
            respondError(id, INTERNAL_ERROR, "处理 " + method + " 失败: " + e.getMessage());
        }
    }


    /** 构造 initialize 响应结果（服务器能力声明）。 */
    private JsonObject initializeResult() {
        JsonObject result = new JsonObject();
        JsonObject capabilities = new JsonObject();
        JsonObject sync = new JsonObject();
        sync.addProperty("openClose", true);
        sync.addProperty("change", 2); // Incremental
        capabilities.add("textDocumentSync", sync);
        JsonObject completion = new JsonObject();
        JsonArray trigger = new JsonArray();
        trigger.add(".");
        trigger.add(":");
        completion.add("triggerCharacters", trigger);
        completion.addProperty("resolveProvider", false);
        capabilities.add("completionProvider", completion);
        capabilities.addProperty("hoverProvider", true);
        capabilities.addProperty("definitionProvider", true);
        capabilities.addProperty("documentSymbolProvider", true);
        result.add("capabilities", capabilities);
        JsonObject serverInfo = new JsonObject();
        serverInfo.addProperty("name", "jquick-java");
        serverInfo.addProperty("version", "2.3.0");
        result.add("serverInfo", serverInfo);
        return result;
    }

    /** 处理 textDocument/didOpen。 */
    private void onDidOpen(JsonObject params) {
        JsonObject textDocument = params.getAsJsonObject("textDocument");
        String uri = textDocument.get("uri").getAsString();
        String languageId = textDocument.has("languageId") ? textDocument.get("languageId").getAsString() : "jquick";
        int version = textDocument.has("version") ? textDocument.get("version").getAsInt() : 0;
        String text = textDocument.has("text") ? textDocument.get("text").getAsString() : "";
        manager.open(uri, languageId, version, text);
        publishDiagnostics(uri);
    }

    /** 处理 textDocument/didChange。 */
    private void onDidChange(JsonObject params) {
        JsonObject textDocument = params.getAsJsonObject("textDocument");
        String uri = textDocument.get("uri").getAsString();
        int version = textDocument.has("version") ? textDocument.get("version").getAsInt() : 0;
        JsonArray contentChanges = params.getAsJsonArray("contentChanges");
        manager.change(uri, version, contentChanges);
        publishDiagnostics(uri);
    }

    /** 处理 textDocument/didClose。 */
    private void onDidClose(JsonObject params) {
        JsonObject textDocument = params.getAsJsonObject("textDocument");
        manager.close(textDocument.get("uri").getAsString());
    }

    /** 处理 textDocument/completion。 */
    private JsonArray completion(JsonObject params) {
        JsonObject position = positionOf(params);
        return provider.completions(uriOf(params), position.get("line").getAsInt(),
                position.get("character").getAsInt());
    }

    /** 处理 textDocument/hover。 */
    private JsonObject hover(JsonObject params) {
        JsonObject position = positionOf(params);
        return provider.hover(uriOf(params), position.get("line").getAsInt(),
                position.get("character").getAsInt());
    }

    /** 处理 textDocument/definition。 */
    private JsonObject definition(JsonObject params) {
        JsonObject position = positionOf(params);
        return provider.definition(uriOf(params), position.get("line").getAsInt(),
                position.get("character").getAsInt());
    }

    /** 处理 textDocument/documentSymbol。 */
    private JsonArray documentSymbol(JsonObject params) {
        return provider.documentSymbols(uriOf(params));
    }


    /** 解析文档并发布诊断通知（textDocument/publishDiagnostics）。 */
    private void publishDiagnostics(String uri) {
        JQuickDocumentManager.ParseResult result = manager.parse(uri);
        JsonArray diagnostics = new JsonArray();
        if (result != null) {
            for (JQuickDocumentManager.Diagnostic d : result.diagnostics) {
                JsonObject diagnostic = new JsonObject();
                diagnostic.add("range", diagnosticRange(d));
                diagnostic.addProperty("severity", d.severity);
                diagnostic.addProperty("source", d.source);
                diagnostic.addProperty("message", d.message);
                diagnostics.add(diagnostic);
            }
        }
        JsonObject notification = JQuickLspMessage.notification("textDocument/publishDiagnostics", null);
        JsonObject params = new JsonObject();
        params.addProperty("uri", uri);
        params.add("diagnostics", diagnostics);
        notification.add("params", params);
        write(notification);
    }

    /** 将内部诊断位置转换为 LSP range。 */
    private JsonObject diagnosticRange(JQuickDocumentManager.Diagnostic d) {
        JsonObject start = new JsonObject();
        start.addProperty("line", d.startLine);
        start.addProperty("character", d.startChar);
        JsonObject end = new JsonObject();
        end.addProperty("line", d.endLine);
        end.addProperty("character", d.endChar);
        JsonObject range = new JsonObject();
        range.add("start", start);
        range.add("end", end);
        return range;
    }

    /** 成功响应。 */
    private void respond(JsonElement id, JsonElement result) {
        if (id == null) {
            return; // 通知不需要响应
        }
        write(JQuickLspMessage.response(id, result));
    }

    /** 错误响应。 */
    private void respondError(JsonElement id, int code, String message) {
        if (id == null) {
            return;
        }
        write(JQuickLspMessage.error(id, code, message));
    }

    /** 写出消息并捕获 IO 异常（写入失败时终止循环）。 */
    private void write(JsonObject message) {
        try {
            JQuickLspMessage.write(out, message);
        } catch (IOException e) {
            running = false;
        }
    }

    /** 取 params.textDocument.uri。 */
    private String uriOf(JsonObject params) {
        return params.getAsJsonObject("textDocument").get("uri").getAsString();
    }

    /** 取 params.position。 */
    private JsonObject positionOf(JsonObject params) {
        return params.getAsJsonObject("position");
    }

    /**
     * 启动语言服务器（stdio 模式）。
     *
     * <p>LSP 协议帧通过 stdout 传输，但 JConsole 在找不到配置文件时会直接向
     * System.out 打印提示，且该打印发生在 JQuickJavaParser 类加载时（静态字段
     * 初始化 new JConsole()）。因此必须先临时将 stdout 重定向到 stderr，
     * 触发类加载并全局禁用 JConsole，再恢复 stdout，防止日志污染协议通道。</p>
     *
     * @param args 无参数
     */
    public static void main(String[] args) throws IOException {
        PrintStream realOut = System.out;
        System.setOut(System.err);
        try {
            // 触发 JQuickJavaParser 类加载（期间 JConsole 可能打印到 stdout），
            // 加载完成后全局禁用 JConsole 输出。
            com.github.paohaijiao.parser.JQuickJavaParser.console.globalDisable();
        } finally {
            System.setOut(realOut);
        }
        new JQuickLanguageServer(System.out).start();
    }
}
