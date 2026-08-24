package com.github.paohaijiao.lsp;

import com.github.paohaijiao.parser.JQuickJavaParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * JQuick 语言服务器特性提供器：补全、悬停、定义跳转、文档符号。
 *
 * <p>所有方法输入均为 LSP 位置（0-based line / character，UTF-16 码元），
 * 输出为可直接放入 JSON-RPC result 的 Gson 对象。</p>
 *
 * <p>使用示例：</p>
 * <pre>
 * JQuickLspProvider provider = new JQuickLspProvider(manager);
 * JsonArray items = provider.completions(uri, 0, 5);
 * JsonObject hover = provider.hover(uri, 0, 5);
 * JsonObject location = provider.definition(uri, 0, 5);
 * JsonArray symbols = provider.documentSymbols(uri);
 * </pre>
 */
public class JQuickLspProvider {

    /** 文档管理器：提供文档缓冲与语法解析。 */
    private final JQuickDocumentManager manager;

    /** 语法关键字补全列表。 */
    private static final String[] KEYWORDS = {
            "if", "else if", "else", "for", "while", "return", "def",
            "import", "as", "new", "var", "break", "continue",
            "true", "false", "null", "this", "console.log"
    };

    /** 内置类型补全列表。 */
    private static final String[] TYPES = {
            "int", "float", "double", "long", "boolean", "byte", "short",
            "List", "Set", "Map", "Builtin"
    };

    /**
     * 构造提供器。
     *
     * @param manager 文档管理器
     */
    public JQuickLspProvider(JQuickDocumentManager manager) {
        this.manager = manager;
    }


    /**
     * 生成补全项列表。
     *
     * @param uri       文档 uri
     * @param line      光标行（0-based）
     * @param character 光标列（0-based）
     * @return CompletionItem 数组；文档不存在时返回空数组
     */
    public JsonArray completions(String uri, int line, int character) {
        JsonArray items = new JsonArray();
        JQuickLspDocument doc = manager.get(uri);
        JQuickJavaParser.ProgramContext tree = parseTreeOf(uri);
        if (doc == null) {
            return items;
        }
        JQuickSymbolCollector.SymbolTable table = JQuickSymbolCollector.collect(tree);
        String prefix = wordPrefix(doc, line, character);
        addCompletionItems(items, table, prefix);
        return items;
    }

    /** 将符号表与关键字合并为补全项（带前缀过滤）。 */
    private void addCompletionItems(JsonArray items, JQuickSymbolCollector.SymbolTable table, String prefix) {
        for (JQuickSymbolCollector.Symbol fn : table.functions) {
            if (matches(fn.name, prefix)) {
                items.add(completionItem(fn.name, 3, fn.detail, fn.documentation));
            }
        }
        for (JQuickSymbolCollector.Symbol v : table.variables) {
            if (matches(v.name, prefix)) {
                items.add(completionItem(v.name, 6, v.detail, v.documentation));
            }
        }
        for (JQuickSymbolCollector.Symbol p : table.params) {
            if (matches(p.name, prefix)) {
                items.add(completionItem(p.name, 6, p.detail, p.documentation));
            }
        }
        for (JQuickSymbolCollector.Symbol imp : table.imports) {
            if (matches(imp.name, prefix)) {
                items.add(completionItem(imp.name, 9, imp.detail, imp.documentation));
            }
        }
        for (String type : TYPES) {
            if (matches(type, prefix)) {
                items.add(completionItem(type, 7, "类型 " + type, null));
            }
        }
        for (String kw : KEYWORDS) {
            if (matches(kw, prefix)) {
                items.add(completionItem(kw, 14, "关键字", null));
            }
        }
    }

    /** 构造单个 CompletionItem。 */
    private JsonObject completionItem(String label, int kind, String detail, String documentation) {
        JsonObject item = new JsonObject();
        item.addProperty("label", label);
        item.addProperty("kind", kind);
        if (detail != null) {
            item.addProperty("detail", detail);
        }
        if (documentation != null) {
            item.addProperty("documentation", documentation);
        }
        return item;
    }

    /** 前缀匹配：name 以 prefix 开头（prefix 为空时全部匹配）。 */
    private boolean matches(String name, String prefix) {
        return prefix.isEmpty() || name.startsWith(prefix);
    }

    /** 取光标所在行光标前的单词前缀。 */
    private String wordPrefix(JQuickLspDocument doc, int line, int character) {
        String[] lines = doc.getLines();
        if (line < 0 || line >= lines.length) {
            return "";
        }
        String text = lines[line];
        int end = Math.min(character, text.length());
        int start = end;
        while (start > 0 && isWordChar(text.charAt(start - 1))) {
            start--;
        }
        return text.substring(start, end);
    }

    private boolean isWordChar(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }

    /**
     * 查询位置处的悬停信息。
     *
     * @param uri       文档 uri
     * @param line      位置行（0-based）
     * @param character 位置列（0-based）
     * @return Hover 对象；位置处无符号时返回 null
     */
    public JsonObject hover(String uri, int line, int character) {
        JQuickSymbolCollector.Symbol symbol = symbolAt(uri, line, character);
        if (symbol == null) {
            return null;
        }
        JsonObject hover = new JsonObject();
        JsonObject markdown = new JsonObject();
        markdown.addProperty("kind", "markdown");
        markdown.addProperty("value", markdownText(symbol));
        hover.add("contents", markdown);
        hover.add("range", rangeJson(symbol.position));
        return hover;
    }

    /** 将符号渲染为 markdown 悬停文本。 */
    private String markdownText(JQuickSymbolCollector.Symbol symbol) {
        StringBuilder sb = new StringBuilder();
        sb.append("```jquick\n").append(symbol.detail).append("\n```");
        if (symbol.documentation != null) {
            sb.append("\n\n").append(symbol.documentation);
        }
        return sb.toString();
    }

    /**
     * 查询位置处标识符对应的定义位置。
     *
     * @param uri       文档 uri
     * @param line      位置行（0-based）
     * @param character 位置列（0-based）
     * @return Location 对象；找不到定义时返回 null
     */
    public JsonObject definition(String uri, int line, int character) {
        JQuickSymbolCollector.SymbolTable table = symbolTableOf(uri);
        if (table == null) {
            return null;
        }
        JQuickSymbolCollector.Symbol ref = referenceAt(table, line, character);
        String name = ref != null ? ref.name : null;
        if (name == null) {
            // 直接落在声明符号上时，返回其自身位置
            JQuickSymbolCollector.Symbol decl = declarationAt(table, line, character);
            if (decl == null) {
                return null;
            }
            return location(uri, decl.position);
        }
        JQuickSymbolCollector.Symbol target = findDeclaration(table, name);
        return target == null ? null : location(uri, target.position);
    }

    /** 构造 Location 对象。 */
    private JsonObject location(String uri, JQuickSymbolCollector.Position position) {
        JsonObject loc = new JsonObject();
        loc.addProperty("uri", uri);
        loc.add("range", rangeJson(position));
        return loc;
    }


    /**
     * 生成文档符号大纲。
     *
     * @param uri 文档 uri
     * @return DocumentSymbol 数组；文档不存在时返回空数组
     */
    public JsonArray documentSymbols(String uri) {
        JsonArray symbols = new JsonArray();
        JQuickSymbolCollector.SymbolTable table = symbolTableOf(uri);
        if (table == null) {
            return symbols;
        }
        for (JQuickSymbolCollector.Symbol imp : table.imports) {
            symbols.add(documentSymbol(imp, 2));
        }
        for (JQuickSymbolCollector.Symbol fn : table.functions) {
            symbols.add(documentSymbol(fn, 12));
        }
        for (JQuickSymbolCollector.Symbol v : table.variables) {
            symbols.add(documentSymbol(v, 13));
        }
        for (JQuickSymbolCollector.Symbol p : table.params) {
            symbols.add(documentSymbol(p, 13));
        }
        return symbols;
    }

    /** 构造单个 DocumentSymbol。 */
    private JsonObject documentSymbol(JQuickSymbolCollector.Symbol symbol, int kind) {
        JsonObject ds = new JsonObject();
        ds.addProperty("name", symbol.name);
        if (symbol.detail != null) {
            ds.addProperty("detail", symbol.detail);
        }
        ds.addProperty("kind", kind);
        ds.add("range", rangeJson(symbol.position));
        ds.add("selectionRange", rangeJson(symbol.position));
        return ds;
    }

    /** 解析指定 uri 的文档并返回语法树；文档不存在返回 null。 */
    private JQuickJavaParser.ProgramContext parseTreeOf(String uri) {
        JQuickDocumentManager.ParseResult result = manager.parse(uri);
        return result == null ? null : result.tree;
    }

    /** 收集指定 uri 的符号表；文档不存在返回 null。 */
    private JQuickSymbolCollector.SymbolTable symbolTableOf(String uri) {
        JQuickJavaParser.ProgramContext tree = parseTreeOf(uri);
        return tree == null ? null : JQuickSymbolCollector.collect(tree);
    }

    /** 查找位置处的符号：先查声明，再查引用。 */
    private JQuickSymbolCollector.Symbol symbolAt(String uri, int line, int character) {
        JQuickSymbolCollector.SymbolTable table = symbolTableOf(uri);
        if (table == null) {
            return null;
        }
        JQuickSymbolCollector.Symbol declaration = declarationAt(table, line, character);
        if (declaration != null) {
            return declaration;
        }
        return referenceAt(table, line, character);
    }

    /** 查找位置处的声明符号（函数/变量/参数/import）。 */
    private JQuickSymbolCollector.Symbol declarationAt(JQuickSymbolCollector.SymbolTable table, int line, int character) {
        JQuickSymbolCollector.Symbol s = findIn(table.functions, line, character);
        if (s != null) {
            return s;
        }
        s = findIn(table.variables, line, character);
        if (s != null) {
            return s;
        }
        s = findIn(table.params, line, character);
        if (s != null) {
            return s;
        }
        return findIn(table.imports, line, character);
    }

    /** 查找位置处的引用符号。 */
    private JQuickSymbolCollector.Symbol referenceAt(JQuickSymbolCollector.SymbolTable table, int line, int character) {
        return findIn(table.references, line, character);
    }

    /** 在列表中按位置查找。 */
    private JQuickSymbolCollector.Symbol findIn(List<JQuickSymbolCollector.Symbol> symbols, int line, int character) {
        for (JQuickSymbolCollector.Symbol s : symbols) {
            if (s.position.contains(line, character)) {
                return s;
            }
        }
        return null;
    }

    /** 按名称在声明中查找目标（引用 → 定义映射）。 */
    private JQuickSymbolCollector.Symbol findDeclaration(JQuickSymbolCollector.SymbolTable table, String name) {
        for (JQuickSymbolCollector.Symbol s : table.functions) {
            if (s.name.equals(name)) {
                return s;
            }
        }
        for (JQuickSymbolCollector.Symbol s : table.variables) {
            if (s.name.equals(name)) {
                return s;
            }
        }
        for (JQuickSymbolCollector.Symbol s : table.params) {
            if (s.name.equals(name)) {
                return s;
            }
        }
        for (JQuickSymbolCollector.Symbol s : table.imports) {
            if (s.name.equals(name)) {
                return s;
            }
        }
        return null;
    }

    /** 将内部位置转换为 LSP range JSON。 */
    private JsonObject rangeJson(JQuickSymbolCollector.Position position) {
        JsonObject start = new JsonObject();
        start.addProperty("line", position.line);
        start.addProperty("character", position.startChar);
        JsonObject end = new JsonObject();
        end.addProperty("line", position.line);
        end.addProperty("character", position.endChar);
        JsonObject range = new JsonObject();
        range.add("start", start);
        range.add("end", end);
        return range;
    }
}
