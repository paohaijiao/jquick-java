package com.github.paohaijiao.lsp;

import com.github.paohaijiao.parser.JQuickJavaBaseListener;
import com.github.paohaijiao.parser.JQuickJavaParser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.List;

/**
 * JQuick 语言符号收集器：遍历 ANTLR 语法树，收集函数、变量、参数、import 别名与标识符引用。
 *
 * <p>符号表由 {@link #collect(JQuickJavaParser.ProgramContext)} 生成，
 * 供 LSP 的补全、悬停、定义跳转、文档符号等能力查询。位置统一为 0-based 的
 * line / character（UTF-16 码元），与 LSP 规范一致。</p>
 *
 * <p>使用示例：</p>
 * <pre>
 * JQuickDocumentManager.ParseResult result = JQuickDocumentManager.parseText("int def add(int: a) {...}");
 * JQuickSymbolCollector.SymbolTable table = JQuickSymbolCollector.collect(result.tree);
 * for (JQuickSymbolCollector.Symbol fn : table.functions) {
 *     System.out.println(fn.detail);
 * }
 * </pre>
 */
public class JQuickSymbolCollector {

    /** 符号在文档中的位置（0-based）。 */
    public static class Position {
        /** 起始行（0-based）。 */
        public final int line;
        /** 起始列（0-based，UTF-16 码元）。 */
        public final int startChar;
        /** 结束列（0-based，不含）。 */
        public final int endChar;

        public Position(int line, int startChar, int endChar) {
            this.line = line;
            this.startChar = startChar;
            this.endChar = endChar;
        }

        /** 判断某位置是否落在此区间内。 */
        public boolean contains(int line, int character) {
            if (line != this.line) {
                return false;
            }
            return character >= startChar && character <= endChar;
        }
    }

    /**
     * 符号信息（对应 LSP 的 SymbolInformation / DocumentSymbol 所需字段）。
     */
    public static class Symbol {
        /** 符号种类：function / variable / param / import / reference。 */
        public String kind;
        /** 符号名称。 */
        public String name;
        /** 类型（classsType 文本，可为 null）。 */
        public String type;
        /** 名称 token 在文档中的位置。 */
        public Position position;
        /** 符号描述文本（悬停/补全时展示）。 */
        public String detail;
        /** 符号说明文档（可为 null）。 */
        public String documentation;

        public Symbol(String kind, String name, String type, Position position, String detail) {
            this.kind = kind;
            this.name = name;
            this.type = type;
            this.position = position;
            this.detail = detail;
        }

        public Symbol(String kind, String name, String type, Position position, String detail, String documentation) {
            this(kind, name, type, position, detail);
            this.documentation = documentation;
        }
    }

    /** 文档符号表：分类存放函数/变量/参数/import 声明与标识符引用。 */
    public static class SymbolTable {
        /** 函数定义列表。 */
        public final List<Symbol> functions = new ArrayList<Symbol>();
        /** 变量声明列表。 */
        public final List<Symbol> variables = new ArrayList<Symbol>();
        /** 函数参数列表。 */
        public final List<Symbol> params = new ArrayList<Symbol>();
        /** import 声明列表。 */
        public final List<Symbol> imports = new ArrayList<Symbol>();
        /** 标识符/方法名引用列表（用于定义跳转）。 */
        public final List<Symbol> references = new ArrayList<Symbol>();
    }

    private final SymbolTable table = new SymbolTable();

    /**
     * 收集语法树中的全部符号。
     *
     * @param tree 由 {@link JQuickDocumentManager#parseText} 得到的 program 语法树，可为 null
     * @return 符号表
     */
    public static SymbolTable collect(JQuickJavaParser.ProgramContext tree) {
        JQuickSymbolCollector collector = new JQuickSymbolCollector();
        if (tree != null) {
            ParseTreeWalker.DEFAULT.walk(collector.new SymbolListener(), tree);
        }
        return collector.table;
    }

    /** 由 token 构造位置。 */
    private static Position positionOf(Token token) {
        return new Position(token.getLine() - 1, token.getCharPositionInLine(),
                token.getCharPositionInLine() + token.getText().length());
    }

    /**
     * 语法树监听器：在遍历过程中收集各类符号。
     */
    private class SymbolListener extends JQuickJavaBaseListener {

        @Override
        public void enterFunctionDefinition(JQuickJavaParser.FunctionDefinitionContext ctx) {
            Token nameToken = ctx.IDENTIFIER().getSymbol();
            String name = nameToken.getText();
            String type = ctx.classsType().getText();
            StringBuilder params = new StringBuilder();
            if (ctx.parameterList() != null) {
                List<JQuickJavaParser.ParamContext> paramContexts = ctx.parameterList().param();
                for (int i = 0; i < paramContexts.size(); i++) {
                    if (i > 0) {
                        params.append(", ");
                    }
                    JQuickJavaParser.ParamContext p = paramContexts.get(i);
                    params.append(p.classsType().getText()).append(": ").append(p.functionVar().IDENTIFIER().getText());
                }
            }
            Symbol symbol = new Symbol("function", name, type, positionOf(nameToken),
                    type + " def " + name + "(" + params + ")",
                    "函数定义：返回类型 " + type + "，参数 " + (params.length() == 0 ? "无" : params));
            table.functions.add(symbol);
        }

        @Override
        public void enterVariableDecl(JQuickJavaParser.VariableDeclContext ctx) {
            Token nameToken = ctx.id;
            if (nameToken == null) {
                return;
            }
            String type = ctx.ct == null ? null : ctx.ct.getText();
            Symbol symbol = new Symbol("variable", nameToken.getText(), type, positionOf(nameToken),
                    type == null ? nameToken.getText() : type + " " + nameToken.getText(),
                    "变量声明" + (type == null ? "" : "，类型 " + type));
            table.variables.add(symbol);
        }

        @Override
        public void enterParam(JQuickJavaParser.ParamContext ctx) {
            Token nameToken = ctx.functionVar().IDENTIFIER().getSymbol();
            Symbol symbol = new Symbol("param", nameToken.getText(), ctx.classsType().getText(),
                    positionOf(nameToken), ctx.classsType().getText() + ": " + nameToken.getText(),
                    "函数参数，类型 " + ctx.classsType().getText());
            table.params.add(symbol);
        }

        @Override
        public void enterImportDeclaration(JQuickJavaParser.ImportDeclarationContext ctx) {
            Token aliasToken = ctx.importVar().IDENTIFIER().getSymbol();
            String type = ctx.paramType().getText();
            Symbol symbol = new Symbol("import", aliasToken.getText(), type, positionOf(aliasToken),
                    "import " + type + " as " + aliasToken.getText(),
                    "类型导入别名，" + type + " 可通过 " + aliasToken.getText() + " 引用");
            table.imports.add(symbol);
        }

        @Override
        public void enterMethodName(JQuickJavaParser.MethodNameContext ctx) {
            table.references.add(new Symbol("reference", ctx.IDENTIFIER().getText(), null,
                    positionOf(ctx.IDENTIFIER().getSymbol()), ctx.IDENTIFIER().getText()));
        }

        /**
         * 新语法下，表达式中的标识符（变量引用、实例名等）均通过
         * primaryAtom -> literal -> identifier 进入语法树，
         * 由 enterIdentifier 统一收集（见下），primaryAtom 不再有直接 IDENTIFIER 分支。
         * <pre>
         * int x = a + b;      // a、b 走 primaryAtom -> literal -> identifier
         * str1.toUpperCase(); // 实例名 str1 同样走 identifier
         * </pre>
         */

        @Override
        public void enterIdentifier(JQuickJavaParser.IdentifierContext ctx) {
            table.references.add(new Symbol("reference", ctx.IDENTIFIER().getText(), null,
                    positionOf(ctx.IDENTIFIER().getSymbol()), ctx.IDENTIFIER().getText()));
        }
    }
}
