package com.github.paohaijiao.lsp;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * LSP（Language Server Protocol）消息编解码器。
 *
 * <p>LSP 基于 JSON-RPC 2.0 协议，消息通过 stdio 以「头 + 正文」的帧格式传输：</p>
 * <pre>
 * Content-Length: 123\r\n
 * \r\n
 * { "jsonrpc": "2.0", "method": "...", "params": {...} }
 * </pre>
 *
 * <p>使用示例：</p>
 * <pre>
 * JsonObject request = JQuickLspMessage.read(System.in);
 * JQuickLspMessage.write(System.out, response);
 * </pre>
 */
public final class JQuickLspMessage {

    private static final Gson GSON = new Gson();
    private static final String CONTENT_LENGTH_PREFIX = "Content-Length: ";

    private JQuickLspMessage() {
    }

    /**
     * 从输入流读取一条 LSP 消息。
     *
     * @param in 输入流（通常为 System.in）
     * @return 消息 JSON 对象；读到 EOF 或消息不完整时返回 null
     */
    public static JsonObject read(InputStream in) throws IOException {
        int contentLength = -1;
        String line;
        while ((line = readLine(in)) != null) {
            if (line.isEmpty()) {
                // 空行表示头结束
                break;
            }
            if (line.startsWith(CONTENT_LENGTH_PREFIX)) {
                contentLength = Integer.parseInt(line.substring(CONTENT_LENGTH_PREFIX.length()).trim());
            }
        }
        if (contentLength < 0) {
            return null;
        }
        byte[] body = new byte[contentLength];
        int offset = 0;
        while (offset < contentLength) {
            int read = in.read(body, offset, contentLength - offset);
            if (read < 0) {
                return null;
            }
            offset += read;
        }
        String json = new String(body, StandardCharsets.UTF_8);
        JsonElement element = JsonParser.parseString(json);
        return element != null && element.isJsonObject() ? element.getAsJsonObject() : null;
    }

    /**
     * 将一条 LSP 消息写入输出流。
     *
     * @param out     输出流（通常为 System.out）
     * @param message 消息 JSON 对象
     */
    public static void write(OutputStream out, JsonObject message) throws IOException {
        byte[] body = GSON.toJson(message).getBytes(StandardCharsets.UTF_8);
        String header = CONTENT_LENGTH_PREFIX + body.length + "\r\n\r\n";
        out.write(header.getBytes(StandardCharsets.US_ASCII));
        out.write(body);
        out.flush();
    }

    /** 读取一行文本（不含换行符），读到 EOF 返回 null。 */
    private static String readLine(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = in.read()) != -1) {
            if (ch == '\n') {
                break;
            }
            if (ch != '\r') {
                sb.append((char) ch);
            }
        }
        return sb.length() == 0 && ch == -1 ? null : sb.toString();
    }

    /** 构建 JSON-RPC 请求消息（服务器一般无需发送，仅供客户端模拟调试用）。 */
    public static JsonObject request(JsonElement id, String method, JsonObject params) {
        JsonObject msg = new JsonObject();
        msg.addProperty("jsonrpc", "2.0");
        msg.add("id", id);
        msg.addProperty("method", method);
        if (params != null) {
            msg.add("params", params);
        }
        return msg;
    }

    /** 构建 JSON-RPC 响应消息（id 原样回传，result 允许为 null）。 */
    public static JsonObject response(JsonElement id, JsonElement result) {
        JsonObject msg = new JsonObject();
        msg.addProperty("jsonrpc", "2.0");
        msg.add("id", id);
        msg.add("result", result);
        return msg;
    }

    /** 构建 JSON-RPC 错误响应消息。 */
    public static JsonObject error(JsonElement id, int code, String message) {
        JsonObject msg = new JsonObject();
        msg.addProperty("jsonrpc", "2.0");
        msg.add("id", id);
        JsonObject err = new JsonObject();
        err.addProperty("code", code);
        err.addProperty("message", message);
        msg.add("error", err);
        return msg;
    }

    /** 构建 JSON-RPC 通知消息（无 id）。 */
    public static JsonObject notification(String method, JsonObject params) {
        JsonObject msg = new JsonObject();
        msg.addProperty("jsonrpc", "2.0");
        msg.addProperty("method", method);
        if (params != null) {
            msg.add("params", params);
        }
        return msg;
    }
}
