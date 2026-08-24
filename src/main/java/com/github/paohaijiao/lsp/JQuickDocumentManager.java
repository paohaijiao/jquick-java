package com.github.paohaijiao.lsp;

import com.github.paohaijiao.parser.JQuickJavaLexer;
import com.github.paohaijiao.parser.JQuickJavaParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JQuick 语言服务器文档管理器：维护文档缓冲，并基于 ANTLR4 对文档做语法分析、收集诊断。
 *
 * <p>每次 didOpen / didChange 后调用 {@link #parse(String)} 重新解析文档。
 * 解析使用 ANTLR 默认的错误恢复策略（不会因局部语法错误而中断），
 * 通过自定义错误监听器把词法/语法错误转换为 LSP Diagnostic，供编辑器实时展示。</p>
 *
 * <p>使用示例：</p>
 * <pre>
 * JQuickDocumentManager manager = new JQuickDocumentManager();
 * manager.open(uri, "jquick", 1, "int a=1;");
 * JQuickDocumentManager.ParseResult result = manager.parse(uri);
 * for (JQuickDocumentManager.Diagnostic d : result.diagnostics) {
 *     System.out.println(d.message);
 * }
 * </pre>
 */
public class JQuickDocumentManager {

    /** 单条诊断信息（对应 LSP Diagnostic）。 */
    public static class Diagnostic {
        /** 起始行（0-based）。 */
        public int startLine;
        /** 起始列（0-based，UTF-16 码元）。 */
        public int startChar;
        /** 结束行（0-based）。 */
        public int endLine;
        /** 结束列（0-based）。 */
        public int endChar;
        /** 诊断消息。 */
        public String message;
        /** 严重级别：1=Error，2=Warning，3=Information，4=Hint。 */
        public int severity = 1;
        /** 诊断来源。 */
        public String source = "jquick-java";

        public Diagnostic(int startLine, int startChar, int endLine, int endChar, String message) {
            this.startLine = startLine;
            this.startChar = startChar;
            this.endLine = endLine;
            this.endChar = endChar;
            this.message = message;
        }
    }

    /** 文档解析结果：语法树 + 诊断列表。 */
    public static class ParseResult {
        /** 语法树入口（program 规则），可能为 null。 */
        public final JQuickJavaParser.ProgramContext tree;
        /** 词法/语法诊断列表。 */
        public final List<Diagnostic> diagnostics;

        public ParseResult(JQuickJavaParser.ProgramContext tree, List<Diagnostic> diagnostics) {
            this.tree = tree;
            this.diagnostics = diagnostics;
        }
    }

    private final Map<String, JQuickLspDocument> documents = new HashMap<String, JQuickLspDocument>();

    /** 打开（或覆盖）一个文档。 */
    public void open(String uri, String languageId, int version, String text) {
        documents.put(uri, new JQuickLspDocument(uri, languageId, version, text));
    }

    /**
     * 应用文档变更。
     *
     * @param uri            文档 uri
     * @param version        新版本号
     * @param contentChanges LSP TextDocumentContentChangeEvent 数组；
     *                       每项含可选的 range 与必填的 text，
     *                       无 range 时视为全文替换
     */
    public void change(String uri, int version, JsonArray contentChanges) {
        JQuickLspDocument doc = documents.get(uri);
        if (doc == null) {
            return;
        }
        String newText = doc.getText();
        for (JsonElement change : contentChanges) {
            JsonObject event = change.getAsJsonObject();
            String changeText = event.get("text").getAsString();
            JsonElement rangeElement = event.get("range");
            if (rangeElement != null && rangeElement.isJsonObject()) {
                JsonObject range = rangeElement.getAsJsonObject();
                int start = posToOffset(doc, range.getAsJsonObject("start"));
                int end = posToOffset(doc, range.getAsJsonObject("end"));
                newText = newText.substring(0, start) + changeText + newText.substring(end);
            } else {
                newText = changeText; // 全文替换
            }
        }
        doc.update(version, newText);
    }

    /** 关闭（移除）一个文档。 */
    public void close(String uri) {
        documents.remove(uri);
    }

    /** 获取文档；不存在时返回 null。 */
    public JQuickLspDocument get(String uri) {
        return documents.get(uri);
    }

    /** 对指定文档执行语法分析，返回语法树与诊断信息。 */
    public ParseResult parse(String uri) {
        JQuickLspDocument doc = documents.get(uri);
        if (doc == null) {
            return null;
        }
        return parseText(doc.getText());
    }

    /** 对纯文本执行语法分析（不依赖文档缓冲），便于直接调试。 */
    public static ParseResult parseText(String text) {
        List<Diagnostic> errors = new ArrayList<Diagnostic>();
        JQuickJavaLexer lexer = new JQuickJavaLexer(CharStreams.fromString(text == null ? "" : text));
        lexer.removeErrorListeners();
        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                errors.add(new Diagnostic(line - 1, charPositionInLine,
                        line - 1, charPositionInLine + 1, "词法错误: " + msg));
            }
        });
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JQuickJavaParser parser = new JQuickJavaParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                int length = offendingSymbol instanceof Token
                        ? ((Token) offendingSymbol).getText().length()
                        : 1;
                errors.add(new Diagnostic(line - 1, charPositionInLine,
                        line - 1, charPositionInLine + Math.max(1, length), "语法错误: " + msg));
            }
        });
        JQuickJavaParser.ProgramContext tree = parser.program();
        return new ParseResult(tree, errors);
    }

    /** 将 LSP 位置对象（line/character）换算为文档字符偏移量。 */
    private int posToOffset(JQuickLspDocument doc, JsonObject position) {
        int line = position.has("line") ? position.get("line").getAsInt() : 0;
        int character = position.has("character") ? position.get("character").getAsInt() : 0;
        return doc.offsetOf(line, character);
    }
}
