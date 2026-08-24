package com.github.paohaijiao.lsp;

/**
 * LSP 文档模型：维护文档 uri、语言标识、版本号与文本内容。
 *
 * <p>编辑器通过 didOpen / didChange / didClose 通知服务器维护文档缓冲，
 * 服务器在此基础上做语法分析，并为编辑器提供诊断、补全、悬停、定义跳转等能力。</p>
 *
 * <p>使用示例：</p>
 * <pre>
 * JQuickLspDocument doc = new JQuickLspDocument(uri, "jquick", 1, "int a=1;");
 * doc.update(2, "int a=1;\nint b=2;");
 * </pre>
 */
public class JQuickLspDocument {

    /** 文档唯一标识（file URI）。 */
    private final String uri;
    /** 文档语言标识（可随意命名，如 "jquick"）。 */
    private final String languageId;
    /** 文档版本号（由客户端递增）。 */
    private int version;
    /** 文档完整文本。 */
    private String text;
    /** 按行拆分的文本（不含换行符，最后一行可能为空）。 */
    private String[] lines;

    /**
     * 构造文档。
     *
     * @param uri        file URI
     * @param languageId 语言标识
     * @param version    初始版本号
     * @param text       初始文本
     */
    public JQuickLspDocument(String uri, String languageId, int version, String text) {
        this.uri = uri;
        this.languageId = languageId;
        this.version = version;
        setText(text);
    }

    public String getUri() {
        return uri;
    }

    public String getLanguageId() {
        return languageId;
    }

    public int getVersion() {
        return version;
    }

    public String getText() {
        return text;
    }

    /** 返回按行拆分的文本；行号从 0 开始，与 LSP 的 line 一致。 */
    public String[] getLines() {
        return lines;
    }

    /** 全文替换文档内容并递增版本号。 */
    public void update(int newVersion, String newText) {
        this.version = newVersion;
        setText(newText);
    }

    private void setText(String newText) {
        this.text = newText == null ? "" : newText;
        this.lines = this.text.split("\n", -1);
    }

    /**
     * 将 LSP 位置（line/character，均为 0-based）换算为文档字符偏移量。
     * 多字节字符按 UTF-16 码元计数，与 LSP 规范一致。
     *
     * @param line      行号（0-based）
     * @param character 列号（0-based，UTF-16 码元）
     * @return 文档内的字符偏移量；越界时自动收敛到最近边界
     */
    public int offsetOf(int line, int character) {
        if (line < 0) {
            line = 0;
        }
        if (line >= lines.length) {
            line = Math.max(0, lines.length - 1);
        }
        int offset = 0;
        for (int i = 0; i < line; i++) {
            offset += lines[i].length() + 1; // +1 换行符
        }
        int lineLength = lines[line].length();
        int col = Math.min(character, lineLength);
        return offset + col;
    }
}
