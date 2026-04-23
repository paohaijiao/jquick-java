// Generated from D:/idea/jthornruleGrammer/QuickJava/JQuickJava.g4 by ANTLR 4.13.2

package com.github.paohaijiao.parser;

import com.github.paohaijiao.console.JConsole;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class JQuickJavaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, DOT=13, GLOBAL=14, TYPESHORT=15, TYPENULL=16, 
		THIS=17, CONTINUE=18, BREAK=19, TYPEINT=20, TYPEFLOAT=21, TYPEDOUBLE=22, 
		TYPELONG=23, TYPEBOOLEAN=24, TYPEBYTE=25, IMPORT=26, NEW=27, VAR=28, AS=29, 
		RETURN=30, DEF=31, WHILE=32, FOR=33, ASSIGN=34, SEMICOLON=35, WITH=36, 
		IF=37, THEN=38, ELSEIF=39, ELSE=40, DOLLAR=41, LPAREN=42, RPAREN=43, LBRACE=44, 
		RBRACE=45, GT=46, GE=47, LT=48, LE=49, EQ=50, NE=51, AND=52, OR=53, ADD=54, 
		MINUS=55, MUL=56, DIV=57, CONTAIN=58, NOTCONTAIN=59, START=60, NOTSTART=61, 
		END=62, NOTEND=63, TRUE=64, FALSE=65, COLON=66, NUMBERIC=67, DATETIME=68, 
		DATE=69, YEAR=70, MONTH=71, DAY=72, TIME_ZONE=73, IDENTIFIER=74, STRING=75, 
		SINGLE_LINE_COMMENT=76, MULTI_LINE_COMMENT=77, WS=78, NEWLINE=79;
	public static final int
		RULE_program = 0, RULE_importDeclaration = 1, RULE_paramType = 2, RULE_genericType = 3, 
		RULE_simpleType = 4, RULE_typeArguments = 5, RULE_arrayType = 6, RULE_listType = 7, 
		RULE_setType = 8, RULE_mapType = 9, RULE_qualifiedName = 10, RULE_statement = 11, 
		RULE_method = 12, RULE_action = 13, RULE_controlStatement = 14, RULE_ifStatement = 15, 
		RULE_elseIfConExpression = 16, RULE_elseIfAction = 17, RULE_elseAction = 18, 
		RULE_forStatement = 19, RULE_initExpression = 20, RULE_conExpression = 21, 
		RULE_stopExpression = 22, RULE_whileStatement = 23, RULE_returnStatement = 24, 
		RULE_breakStatement = 25, RULE_continueStatement = 26, RULE_expressionStatement = 27, 
		RULE_variableDecl = 28, RULE_expression = 29, RULE_sout = 30, RULE_logical = 31, 
		RULE_comparison = 32, RULE_arithmetic = 33, RULE_functionDefinition = 34, 
		RULE_parameterList = 35, RULE_param = 36, RULE_functionVar = 37, RULE_classsType = 38, 
		RULE_primary = 39, RULE_literalItem = 40, RULE_methodInvocation = 41, 
		RULE_this = 42, RULE_instanceName = 43, RULE_methodName = 44, RULE_argumentList = 45, 
		RULE_accessStaticVariable = 46, RULE_accessObjectName = 47, RULE_literal = 48, 
		RULE_listLiteral = 49, RULE_mapLiteral = 50, RULE_mapEntry = 51, RULE_importVar = 52, 
		RULE_identifier = 53, RULE_bool = 54, RULE_string = 55, RULE_date = 56, 
		RULE_variables = 57, RULE_number = 58, RULE_null = 59;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "importDeclaration", "paramType", "genericType", "simpleType", 
			"typeArguments", "arrayType", "listType", "setType", "mapType", "qualifiedName", 
			"statement", "method", "action", "controlStatement", "ifStatement", "elseIfConExpression", 
			"elseIfAction", "elseAction", "forStatement", "initExpression", "conExpression", 
			"stopExpression", "whileStatement", "returnStatement", "breakStatement", 
			"continueStatement", "expressionStatement", "variableDecl", "expression", 
			"sout", "logical", "comparison", "arithmetic", "functionDefinition", 
			"parameterList", "param", "functionVar", "classsType", "primary", "literalItem", 
			"methodInvocation", "this", "instanceName", "methodName", "argumentList", 
			"accessStaticVariable", "accessObjectName", "literal", "listLiteral", 
			"mapLiteral", "mapEntry", "importVar", "identifier", "bool", "string", 
			"date", "variables", "number", "null"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "','", "'['", "']'", "'List'", "'Set'", "'Map'", "'if'", "'else if'", 
			"'else'", "'console.log('", "'@'", "'.class'", "'.'", "'global'", "'short'", 
			"'null'", "'this'", "'continue'", "'break'", "'int'", "'float'", "'double'", 
			"'long'", "'boolean'", "'byte'", "'import'", "'new'", "'var'", "'as'", 
			"'return'", "'def'", "'while'", "'for'", "'='", "';'", "'WITH'", "'IF'", 
			"'THEN'", "'ELSEIF'", "'ELSE'", "'$'", "'('", "')'", "'{'", "'}'", "'>'", 
			"'>='", "'<'", "'<='", "'=='", "'!='", "'&&'", "'||'", "'+'", "'-'", 
			"'*'", "'/'", "'CONTAIN'", "'NOTCONTAIN'", "'START'", "'NOTSTART'", "'END'", 
			"'NOTEND'", "'true'", "'false'", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "DOT", "GLOBAL", "TYPESHORT", "TYPENULL", "THIS", "CONTINUE", "BREAK", 
			"TYPEINT", "TYPEFLOAT", "TYPEDOUBLE", "TYPELONG", "TYPEBOOLEAN", "TYPEBYTE", 
			"IMPORT", "NEW", "VAR", "AS", "RETURN", "DEF", "WHILE", "FOR", "ASSIGN", 
			"SEMICOLON", "WITH", "IF", "THEN", "ELSEIF", "ELSE", "DOLLAR", "LPAREN", 
			"RPAREN", "LBRACE", "RBRACE", "GT", "GE", "LT", "LE", "EQ", "NE", "AND", 
			"OR", "ADD", "MINUS", "MUL", "DIV", "CONTAIN", "NOTCONTAIN", "START", 
			"NOTSTART", "END", "NOTEND", "TRUE", "FALSE", "COLON", "NUMBERIC", "DATETIME", 
			"DATE", "YEAR", "MONTH", "DAY", "TIME_ZONE", "IDENTIFIER", "STRING", 
			"SINGLE_LINE_COMMENT", "MULTI_LINE_COMMENT", "WS", "NEWLINE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "JQuickJava.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    public static boolean banner=true;
	    public static JConsole console=new JConsole();
	    private Stack<Map<String, Object>> scopes = new Stack<>();

	    void start(){
	        enterScope(); // 进入全局作用域
	     }
	      // 进入新作用域
	     void enterScope() {
	        scopes.push(new HashMap<>());
	     }
	     void exitScope() {
	        scopes.pop();
	     }
	     // 声明变量
	    void declareVar(String name, Object value) {
	        Map<String, Object> current = scopes.peek();
	        if (current.containsKey(name)) {
	            console.error("Variable " + name + " already declared");
	        } else {
	            current.put(name, value);
	        }
	    }
	     // 查找变量（从内向外）
	    Object findVar(String name) {
	        for (int i = scopes.size() - 1; i >= 0; i--) {
	            if (scopes.get(i).containsKey(name)) {
	                return scopes.get(i).get(name);
	            }
	        }
	        console.error("Undefined variable: " + name);
	        return null;
	    }

	      // 赋值变量（从内向外找，找不到就在当前作用域创建）
	    void assignVar(String name, Object value) {
	         for (int i = scopes.size() - 1; i >= 0; i--) {
	              if (scopes.get(i).containsKey(name)) {
	                    scopes.get(i).put(name, value);
	                    return;
	               }
	         }
	         // 隐式声明
	         scopes.peek().put(name, value);
	    }


	public JQuickJavaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(JQuickJavaParser.EOF, 0); }
		public List<ImportDeclarationContext> importDeclaration() {
			return getRuleContexts(ImportDeclarationContext.class);
		}
		public ImportDeclarationContext importDeclaration(int i) {
			return getRuleContext(ImportDeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			start();
			setState(124);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(121);
				importDeclaration();
				}
				}
				setState(126);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 24203415766260L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				{
				setState(127);
				statement();
				}
				}
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(133);
			match(EOF);
			exitScope();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ImportDeclarationContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(JQuickJavaParser.IMPORT, 0); }
		public ParamTypeContext paramType() {
			return getRuleContext(ParamTypeContext.class,0);
		}
		public TerminalNode AS() { return getToken(JQuickJavaParser.AS, 0); }
		public ImportVarContext importVar() {
			return getRuleContext(ImportVarContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(JQuickJavaParser.SEMICOLON, 0); }
		public ImportDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterImportDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitImportDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitImportDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportDeclarationContext importDeclaration() throws RecognitionException {
		ImportDeclarationContext _localctx = new ImportDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			match(IMPORT);
			setState(137);
			paramType();
			setState(138);
			match(AS);
			setState(139);
			importVar();
			setState(140);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamTypeContext extends ParserRuleContext {
		public SimpleTypeContext simpleType() {
			return getRuleContext(SimpleTypeContext.class,0);
		}
		public GenericTypeContext genericType() {
			return getRuleContext(GenericTypeContext.class,0);
		}
		public ListTypeContext listType() {
			return getRuleContext(ListTypeContext.class,0);
		}
		public MapTypeContext mapType() {
			return getRuleContext(MapTypeContext.class,0);
		}
		public SetTypeContext setType() {
			return getRuleContext(SetTypeContext.class,0);
		}
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public ParamTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterParamType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitParamType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitParamType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamTypeContext paramType() throws RecognitionException {
		ParamTypeContext _localctx = new ParamTypeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_paramType);
		try {
			setState(148);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(142);
				simpleType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(143);
				genericType();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(144);
				listType();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(145);
				mapType();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(146);
				setType();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(147);
				arrayType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GenericTypeContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public GenericTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_genericType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterGenericType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitGenericType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitGenericType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GenericTypeContext genericType() throws RecognitionException {
		GenericTypeContext _localctx = new GenericTypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_genericType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			qualifiedName();
			setState(152);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(151);
				typeArguments();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SimpleTypeContext extends ParserRuleContext {
		public TerminalNode TYPESHORT() { return getToken(JQuickJavaParser.TYPESHORT, 0); }
		public TerminalNode TYPEINT() { return getToken(JQuickJavaParser.TYPEINT, 0); }
		public TerminalNode TYPEFLOAT() { return getToken(JQuickJavaParser.TYPEFLOAT, 0); }
		public TerminalNode TYPEDOUBLE() { return getToken(JQuickJavaParser.TYPEDOUBLE, 0); }
		public TerminalNode TYPELONG() { return getToken(JQuickJavaParser.TYPELONG, 0); }
		public TerminalNode TYPEBOOLEAN() { return getToken(JQuickJavaParser.TYPEBOOLEAN, 0); }
		public TerminalNode TYPEBYTE() { return getToken(JQuickJavaParser.TYPEBYTE, 0); }
		public SimpleTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterSimpleType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitSimpleType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitSimpleType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleTypeContext simpleType() throws RecognitionException {
		SimpleTypeContext _localctx = new SimpleTypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_simpleType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 66093056L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeArgumentsContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(JQuickJavaParser.LT, 0); }
		public List<ClasssTypeContext> classsType() {
			return getRuleContexts(ClasssTypeContext.class);
		}
		public ClasssTypeContext classsType(int i) {
			return getRuleContext(ClasssTypeContext.class,i);
		}
		public TerminalNode GT() { return getToken(JQuickJavaParser.GT, 0); }
		public TypeArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterTypeArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitTypeArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitTypeArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentsContext typeArguments() throws RecognitionException {
		TypeArgumentsContext _localctx = new TypeArgumentsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_typeArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			match(LT);
			setState(157);
			classsType();
			setState(162);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(158);
				match(T__0);
				setState(159);
				classsType();
				}
				}
				setState(164);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(165);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayTypeContext extends ParserRuleContext {
		public SimpleTypeContext simpleType() {
			return getRuleContext(SimpleTypeContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterArrayType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitArrayType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_arrayType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TYPESHORT:
			case TYPEINT:
			case TYPEFLOAT:
			case TYPEDOUBLE:
			case TYPELONG:
			case TYPEBOOLEAN:
			case TYPEBYTE:
				{
				setState(167);
				simpleType();
				}
				break;
			case IDENTIFIER:
				{
				setState(168);
				qualifiedName();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(173); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(171);
				match(T__1);
				setState(172);
				match(T__2);
				}
				}
				setState(175); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__1 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListTypeContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(JQuickJavaParser.LT, 0); }
		public ClasssTypeContext classsType() {
			return getRuleContext(ClasssTypeContext.class,0);
		}
		public TerminalNode GT() { return getToken(JQuickJavaParser.GT, 0); }
		public ListTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterListType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitListType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitListType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListTypeContext listType() throws RecognitionException {
		ListTypeContext _localctx = new ListTypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_listType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			match(T__3);
			setState(182);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(178);
				match(LT);
				setState(179);
				classsType();
				setState(180);
				match(GT);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SetTypeContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(JQuickJavaParser.LT, 0); }
		public ClasssTypeContext classsType() {
			return getRuleContext(ClasssTypeContext.class,0);
		}
		public TerminalNode GT() { return getToken(JQuickJavaParser.GT, 0); }
		public SetTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterSetType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitSetType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitSetType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetTypeContext setType() throws RecognitionException {
		SetTypeContext _localctx = new SetTypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_setType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			match(T__4);
			setState(189);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(185);
				match(LT);
				setState(186);
				classsType();
				setState(187);
				match(GT);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MapTypeContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(JQuickJavaParser.LT, 0); }
		public List<ClasssTypeContext> classsType() {
			return getRuleContexts(ClasssTypeContext.class);
		}
		public ClasssTypeContext classsType(int i) {
			return getRuleContext(ClasssTypeContext.class,i);
		}
		public TerminalNode GT() { return getToken(JQuickJavaParser.GT, 0); }
		public MapTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterMapType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitMapType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitMapType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapTypeContext mapType() throws RecognitionException {
		MapTypeContext _localctx = new MapTypeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_mapType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			match(T__5);
			setState(198);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(192);
				match(LT);
				setState(193);
				classsType();
				setState(194);
				match(T__0);
				setState(195);
				classsType();
				setState(196);
				match(GT);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QualifiedNameContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(JQuickJavaParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(JQuickJavaParser.IDENTIFIER, i);
		}
		public List<TerminalNode> DOT() { return getTokens(JQuickJavaParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(JQuickJavaParser.DOT, i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterQualifiedName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitQualifiedName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			match(IDENTIFIER);
			setState(205);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(201);
				match(DOT);
				setState(202);
				match(IDENTIFIER);
				}
				}
				setState(207);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(JQuickJavaParser.SEMICOLON, 0); }
		public MethodContext method() {
			return getRuleContext(MethodContext.class,0);
		}
		public ControlStatementContext controlStatement() {
			return getRuleContext(ControlStatementContext.class,0);
		}
		public SoutContext sout() {
			return getRuleContext(SoutContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_statement);
		try {
			setState(217);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(208);
				expression();
				setState(209);
				match(SEMICOLON);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(211);
				method();
				setState(213);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
				case 1:
					{
					setState(212);
					match(SEMICOLON);
					}
					break;
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(215);
				controlStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(216);
				sout();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodContext extends ParserRuleContext {
		public MethodInvocationContext methodInvocation() {
			return getRuleContext(MethodInvocationContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public MethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitMethod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitMethod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodContext method() throws RecognitionException {
		MethodContext _localctx = new MethodContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_method);
		try {
			setState(221);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(219);
				methodInvocation();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(220);
				functionDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ActionContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(JQuickJavaParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(JQuickJavaParser.RBRACE, 0); }
		public List<VariableDeclContext> variableDecl() {
			return getRuleContexts(VariableDeclContext.class);
		}
		public VariableDeclContext variableDecl(int i) {
			return getRuleContext(VariableDeclContext.class,i);
		}
		public List<ControlStatementContext> controlStatement() {
			return getRuleContexts(ControlStatementContext.class);
		}
		public ControlStatementContext controlStatement(int i) {
			return getRuleContext(ControlStatementContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(JQuickJavaParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(JQuickJavaParser.SEMICOLON, i);
		}
		public ActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_action);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			enterScope();
			setState(224);
			match(LBRACE);
			setState(239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 24203415766260L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				setState(237);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
				case 1:
					{
					setState(225);
					variableDecl();
					setState(227);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==SEMICOLON) {
						{
						setState(226);
						match(SEMICOLON);
						}
					}

					}
					break;
				case 2:
					{
					setState(229);
					controlStatement();
					setState(231);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==SEMICOLON) {
						{
						setState(230);
						match(SEMICOLON);
						}
					}

					}
					break;
				case 3:
					{
					setState(233);
					statement();
					setState(235);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==SEMICOLON) {
						{
						setState(234);
						match(SEMICOLON);
						}
					}

					}
					break;
				}
				}
				setState(241);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(242);
			match(RBRACE);
			exitScope();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ControlStatementContext extends ParserRuleContext {
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public ContinueStatementContext continueStatement() {
			return getRuleContext(ContinueStatementContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public ControlStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_controlStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterControlStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitControlStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitControlStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ControlStatementContext controlStatement() throws RecognitionException {
		ControlStatementContext _localctx = new ControlStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_controlStatement);
		try {
			setState(252);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(245);
				ifStatement();
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 2);
				{
				setState(246);
				forStatement();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 3);
				{
				setState(247);
				whileStatement();
				}
				break;
			case RETURN:
				enterOuterAlt(_localctx, 4);
				{
				setState(248);
				returnStatement();
				}
				break;
			case BREAK:
				enterOuterAlt(_localctx, 5);
				{
				setState(249);
				breakStatement();
				}
				break;
			case CONTINUE:
				enterOuterAlt(_localctx, 6);
				{
				setState(250);
				continueStatement();
				}
				break;
			case T__1:
			case T__3:
			case T__4:
			case T__5:
			case GLOBAL:
			case TYPESHORT:
			case TYPENULL:
			case THIS:
			case TYPEINT:
			case TYPEFLOAT:
			case TYPEDOUBLE:
			case TYPELONG:
			case TYPEBOOLEAN:
			case TYPEBYTE:
			case NEW:
			case DOLLAR:
			case LPAREN:
			case LBRACE:
			case TRUE:
			case FALSE:
			case NUMBERIC:
			case DATETIME:
			case DATE:
			case IDENTIFIER:
			case STRING:
				enterOuterAlt(_localctx, 7);
				{
				setState(251);
				expressionStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStatementContext extends ParserRuleContext {
		public List<TerminalNode> LPAREN() { return getTokens(JQuickJavaParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(JQuickJavaParser.LPAREN, i);
		}
		public ConExpressionContext conExpression() {
			return getRuleContext(ConExpressionContext.class,0);
		}
		public List<TerminalNode> RPAREN() { return getTokens(JQuickJavaParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(JQuickJavaParser.RPAREN, i);
		}
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public List<ElseIfConExpressionContext> elseIfConExpression() {
			return getRuleContexts(ElseIfConExpressionContext.class);
		}
		public ElseIfConExpressionContext elseIfConExpression(int i) {
			return getRuleContext(ElseIfConExpressionContext.class,i);
		}
		public List<ElseIfActionContext> elseIfAction() {
			return getRuleContexts(ElseIfActionContext.class);
		}
		public ElseIfActionContext elseIfAction(int i) {
			return getRuleContext(ElseIfActionContext.class,i);
		}
		public ElseActionContext elseAction() {
			return getRuleContext(ElseActionContext.class,0);
		}
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			match(T__6);
			setState(255);
			match(LPAREN);
			setState(256);
			conExpression();
			setState(257);
			match(RPAREN);
			enterScope();
			setState(259);
			action();
			exitScope();
			setState(271);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(261);
				match(T__7);
				setState(262);
				match(LPAREN);
				setState(263);
				elseIfConExpression();
				setState(264);
				match(RPAREN);
				enterScope();
				setState(266);
				elseIfAction();
				exitScope();
				}
				}
				setState(273);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(279);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(274);
				match(T__8);
				enterScope();
				setState(276);
				elseAction();
				exitScope();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElseIfConExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ElseIfConExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseIfConExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterElseIfConExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitElseIfConExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitElseIfConExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseIfConExpressionContext elseIfConExpression() throws RecognitionException {
		ElseIfConExpressionContext _localctx = new ElseIfConExpressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_elseIfConExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(281);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElseIfActionContext extends ParserRuleContext {
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public ElseIfActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseIfAction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterElseIfAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitElseIfAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitElseIfAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseIfActionContext elseIfAction() throws RecognitionException {
		ElseIfActionContext _localctx = new ElseIfActionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_elseIfAction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			action();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElseActionContext extends ParserRuleContext {
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public ElseActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseAction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterElseAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitElseAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitElseAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseActionContext elseAction() throws RecognitionException {
		ElseActionContext _localctx = new ElseActionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_elseAction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			action();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(JQuickJavaParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(JQuickJavaParser.LPAREN, 0); }
		public List<TerminalNode> SEMICOLON() { return getTokens(JQuickJavaParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(JQuickJavaParser.SEMICOLON, i);
		}
		public TerminalNode RPAREN() { return getToken(JQuickJavaParser.RPAREN, 0); }
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public VariableDeclContext variableDecl() {
			return getRuleContext(VariableDeclContext.class,0);
		}
		public InitExpressionContext initExpression() {
			return getRuleContext(InitExpressionContext.class,0);
		}
		public ConExpressionContext conExpression() {
			return getRuleContext(ConExpressionContext.class,0);
		}
		public StopExpressionContext stopExpression() {
			return getRuleContext(StopExpressionContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(287);
			match(FOR);
			setState(288);
			match(LPAREN);
			enterScope();
			setState(292);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(290);
				variableDecl();
				}
				break;
			case 2:
				{
				setState(291);
				initExpression();
				}
				break;
			}
			setState(294);
			match(SEMICOLON);
			setState(296);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 24189456334964L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				setState(295);
				conExpression();
				}
			}

			setState(298);
			match(SEMICOLON);
			setState(300);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 24189456334964L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				setState(299);
				stopExpression();
				}
			}

			setState(302);
			match(RPAREN);
			setState(303);
			action();
			exitScope();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InitExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public InitExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterInitExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitInitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitInitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InitExpressionContext initExpression() throws RecognitionException {
		InitExpressionContext _localctx = new InitExpressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_initExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(306);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterConExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitConExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitConExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConExpressionContext conExpression() throws RecognitionException {
		ConExpressionContext _localctx = new ConExpressionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_conExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(308);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StopExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StopExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stopExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterStopExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitStopExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitStopExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StopExpressionContext stopExpression() throws RecognitionException {
		StopExpressionContext _localctx = new StopExpressionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_stopExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(310);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(JQuickJavaParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(JQuickJavaParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JQuickJavaParser.RPAREN, 0); }
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			match(WHILE);
			setState(313);
			match(LPAREN);
			setState(314);
			expression();
			setState(315);
			match(RPAREN);
			enterScope();
			setState(317);
			action();
			exitScope();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(JQuickJavaParser.RETURN, 0); }
		public TerminalNode SEMICOLON() { return getToken(JQuickJavaParser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			match(RETURN);
			setState(322);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 24189456334964L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				setState(321);
				expression();
				}
			}

			setState(324);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode BREAK() { return getToken(JQuickJavaParser.BREAK, 0); }
		public TerminalNode SEMICOLON() { return getToken(JQuickJavaParser.SEMICOLON, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterBreakStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitBreakStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(326);
			match(BREAK);
			setState(327);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ContinueStatementContext extends ParserRuleContext {
		public TerminalNode CONTINUE() { return getToken(JQuickJavaParser.CONTINUE, 0); }
		public TerminalNode SEMICOLON() { return getToken(JQuickJavaParser.SEMICOLON, 0); }
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterContinueStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitContinueStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(329);
			match(CONTINUE);
			setState(330);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(JQuickJavaParser.SEMICOLON, 0); }
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(332);
			expression();
			setState(333);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableDeclContext extends ParserRuleContext {
		public String varName;
		public Object varValue;
		public Token GLOBAL;
		public ClasssTypeContext ct;
		public Token id;
		public ExpressionContext expr;
		public TerminalNode ASSIGN() { return getToken(JQuickJavaParser.ASSIGN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(JQuickJavaParser.IDENTIFIER, 0); }
		public TerminalNode GLOBAL() { return getToken(JQuickJavaParser.GLOBAL, 0); }
		public ClasssTypeContext classsType() {
			return getRuleContext(ClasssTypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterVariableDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitVariableDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitVariableDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclContext variableDecl() throws RecognitionException {
		VariableDeclContext _localctx = new VariableDeclContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_variableDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==GLOBAL) {
				{
				setState(335);
				((VariableDeclContext)_localctx).GLOBAL = match(GLOBAL);
				}
			}

			setState(339);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(338);
				((VariableDeclContext)_localctx).ct = classsType();
				}
				break;
			}
			setState(341);
			((VariableDeclContext)_localctx).id = match(IDENTIFIER);
			setState(342);
			match(ASSIGN);
			setState(344);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				setState(343);
				((VariableDeclContext)_localctx).expr = expression();
				}
				break;
			}

			        ((VariableDeclContext)_localctx).varName =  (((VariableDeclContext)_localctx).id!=null?((VariableDeclContext)_localctx).id.getText():null);
			        ((VariableDeclContext)_localctx).varValue =  ((VariableDeclContext)_localctx).expr != null ? ((VariableDeclContext)_localctx).expr.value : null;
			        if (((VariableDeclContext)_localctx).ct != null) {
			            String declaredType = (((VariableDeclContext)_localctx).ct!=null?_input.getText(((VariableDeclContext)_localctx).ct.start,((VariableDeclContext)_localctx).ct.stop):null);
			        }
			        declareVar(_localctx.varName, _localctx.varValue);
			        if (((VariableDeclContext)_localctx).GLOBAL != null) {
			        }
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public Object value;
		public ArithmeticContext a;
		public LogicalContext l;
		public MethodInvocationContext m;
		public PrimaryContext p;
		public ArithmeticContext arithmetic() {
			return getRuleContext(ArithmeticContext.class,0);
		}
		public LogicalContext logical() {
			return getRuleContext(LogicalContext.class,0);
		}
		public MethodInvocationContext methodInvocation() {
			return getRuleContext(MethodInvocationContext.class,0);
		}
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_expression);
		try {
			setState(360);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(348);
				((ExpressionContext)_localctx).a = arithmetic();
				 ((ExpressionContext)_localctx).value =  ((ExpressionContext)_localctx).a.value; 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(351);
				((ExpressionContext)_localctx).l = logical();
				 ((ExpressionContext)_localctx).value =  ((ExpressionContext)_localctx).l.value; 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(354);
				((ExpressionContext)_localctx).m = methodInvocation();
				 ((ExpressionContext)_localctx).value =  ((ExpressionContext)_localctx).m.value; 
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(357);
				((ExpressionContext)_localctx).p = primary();
				 ((ExpressionContext)_localctx).value =  ((ExpressionContext)_localctx).p.value; 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SoutContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JQuickJavaParser.RPAREN, 0); }
		public SoutContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sout; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterSout(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitSout(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitSout(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SoutContext sout() throws RecognitionException {
		SoutContext _localctx = new SoutContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_sout);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(362);
			match(T__9);
			setState(363);
			expression();
			setState(364);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LogicalContext extends ParserRuleContext {
		public Object value;
		public Token op;
		public List<ComparisonContext> comparison() {
			return getRuleContexts(ComparisonContext.class);
		}
		public ComparisonContext comparison(int i) {
			return getRuleContext(ComparisonContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(JQuickJavaParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(JQuickJavaParser.OR, i);
		}
		public List<TerminalNode> AND() { return getTokens(JQuickJavaParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(JQuickJavaParser.AND, i);
		}
		public LogicalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterLogical(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitLogical(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitLogical(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalContext logical() throws RecognitionException {
		LogicalContext _localctx = new LogicalContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_logical);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(366);
			comparison();
			setState(371);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(367);
					((LogicalContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==AND || _la==OR) ) {
						((LogicalContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(368);
					comparison();
					}
					} 
				}
				setState(373);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonContext extends ParserRuleContext {
		public Object value;
		public Token op;
		public List<PrimaryContext> primary() {
			return getRuleContexts(PrimaryContext.class);
		}
		public PrimaryContext primary(int i) {
			return getRuleContext(PrimaryContext.class,i);
		}
		public List<TerminalNode> GT() { return getTokens(JQuickJavaParser.GT); }
		public TerminalNode GT(int i) {
			return getToken(JQuickJavaParser.GT, i);
		}
		public List<TerminalNode> GE() { return getTokens(JQuickJavaParser.GE); }
		public TerminalNode GE(int i) {
			return getToken(JQuickJavaParser.GE, i);
		}
		public List<TerminalNode> LT() { return getTokens(JQuickJavaParser.LT); }
		public TerminalNode LT(int i) {
			return getToken(JQuickJavaParser.LT, i);
		}
		public List<TerminalNode> LE() { return getTokens(JQuickJavaParser.LE); }
		public TerminalNode LE(int i) {
			return getToken(JQuickJavaParser.LE, i);
		}
		public List<TerminalNode> EQ() { return getTokens(JQuickJavaParser.EQ); }
		public TerminalNode EQ(int i) {
			return getToken(JQuickJavaParser.EQ, i);
		}
		public List<TerminalNode> NE() { return getTokens(JQuickJavaParser.NE); }
		public TerminalNode NE(int i) {
			return getToken(JQuickJavaParser.NE, i);
		}
		public ComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparison; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitComparison(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonContext comparison() throws RecognitionException {
		ComparisonContext _localctx = new ComparisonContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_comparison);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(374);
			primary();
			setState(379);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(375);
					((ComparisonContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 4433230883192832L) != 0)) ) {
						((ComparisonContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(376);
					primary();
					}
					} 
				}
				setState(381);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArithmeticContext extends ParserRuleContext {
		public Object value;
		public List<PrimaryContext> primary() {
			return getRuleContexts(PrimaryContext.class);
		}
		public PrimaryContext primary(int i) {
			return getRuleContext(PrimaryContext.class,i);
		}
		public List<TerminalNode> MUL() { return getTokens(JQuickJavaParser.MUL); }
		public TerminalNode MUL(int i) {
			return getToken(JQuickJavaParser.MUL, i);
		}
		public List<TerminalNode> DIV() { return getTokens(JQuickJavaParser.DIV); }
		public TerminalNode DIV(int i) {
			return getToken(JQuickJavaParser.DIV, i);
		}
		public List<TerminalNode> ADD() { return getTokens(JQuickJavaParser.ADD); }
		public TerminalNode ADD(int i) {
			return getToken(JQuickJavaParser.ADD, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(JQuickJavaParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(JQuickJavaParser.MINUS, i);
		}
		public ArithmeticContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmetic; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterArithmetic(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitArithmetic(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitArithmetic(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithmeticContext arithmetic() throws RecognitionException {
		ArithmeticContext _localctx = new ArithmeticContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_arithmetic);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
			primary();
			setState(387);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(383);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 270215977642229760L) != 0)) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(384);
					primary();
					}
					} 
				}
				setState(389);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionDefinitionContext extends ParserRuleContext {
		public ClasssTypeContext classsType() {
			return getRuleContext(ClasssTypeContext.class,0);
		}
		public TerminalNode DEF() { return getToken(JQuickJavaParser.DEF, 0); }
		public TerminalNode IDENTIFIER() { return getToken(JQuickJavaParser.IDENTIFIER, 0); }
		public TerminalNode LPAREN() { return getToken(JQuickJavaParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JQuickJavaParser.RPAREN, 0); }
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterFunctionDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitFunctionDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitFunctionDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_functionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
			classsType();
			setState(391);
			match(DEF);
			setState(392);
			match(IDENTIFIER);
			setState(393);
			match(LPAREN);
			setState(395);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66093168L) != 0) || _la==IDENTIFIER) {
				{
				setState(394);
				parameterList();
				}
			}

			setState(397);
			match(RPAREN);
			enterScope();
			setState(399);
			action();
			exitScope();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParameterListContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			param();
			setState(407);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(403);
				match(T__0);
				setState(404);
				param();
				}
				}
				setState(409);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamContext extends ParserRuleContext {
		public ClasssTypeContext classsType() {
			return getRuleContext(ClasssTypeContext.class,0);
		}
		public TerminalNode COLON() { return getToken(JQuickJavaParser.COLON, 0); }
		public FunctionVarContext functionVar() {
			return getRuleContext(FunctionVarContext.class,0);
		}
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(410);
			classsType();
			setState(411);
			match(COLON);
			setState(412);
			functionVar();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionVarContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(JQuickJavaParser.IDENTIFIER, 0); }
		public FunctionVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionVar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterFunctionVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitFunctionVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitFunctionVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionVarContext functionVar() throws RecognitionException {
		FunctionVarContext _localctx = new FunctionVarContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_functionVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(414);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClasssTypeContext extends ParserRuleContext {
		public ImportVarContext importVar() {
			return getRuleContext(ImportVarContext.class,0);
		}
		public ParamTypeContext paramType() {
			return getRuleContext(ParamTypeContext.class,0);
		}
		public ClasssTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classsType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterClasssType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitClasssType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitClasssType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClasssTypeContext classsType() throws RecognitionException {
		ClasssTypeContext _localctx = new ClasssTypeContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_classsType);
		try {
			setState(418);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(416);
				importVar();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(417);
				paramType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryContext extends ParserRuleContext {
		public Object value;
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(JQuickJavaParser.IDENTIFIER, 0); }
		public MethodInvocationContext methodInvocation() {
			return getRuleContext(MethodInvocationContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JQuickJavaParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JQuickJavaParser.RPAREN, 0); }
		public VariableDeclContext variableDecl() {
			return getRuleContext(VariableDeclContext.class,0);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_primary);
		try {
			setState(428);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(420);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(421);
				match(IDENTIFIER);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(422);
				methodInvocation();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(423);
				match(LPAREN);
				setState(424);
				expression();
				setState(425);
				match(RPAREN);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(427);
				variableDecl();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralItemContext extends ParserRuleContext {
		public ClasssTypeContext classsType() {
			return getRuleContext(ClasssTypeContext.class,0);
		}
		public TerminalNode COLON() { return getToken(JQuickJavaParser.COLON, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literalItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterLiteralItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitLiteralItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitLiteralItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralItemContext literalItem() throws RecognitionException {
		LiteralItemContext _localctx = new LiteralItemContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_literalItem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(430);
			classsType();
			setState(431);
			match(COLON);
			setState(432);
			literal();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodInvocationContext extends ParserRuleContext {
		public Object value;
		public MethodInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodInvocation; }
	 
		public MethodInvocationContext() { }
		public void copyFrom(MethodInvocationContext ctx) {
			super.copyFrom(ctx);
			this.value = ctx.value;
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StaticCallContext extends MethodInvocationContext {
		public ClasssTypeContext classsType() {
			return getRuleContext(ClasssTypeContext.class,0);
		}
		public List<TerminalNode> COLON() { return getTokens(JQuickJavaParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(JQuickJavaParser.COLON, i);
		}
		public MethodNameContext methodName() {
			return getRuleContext(MethodNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JQuickJavaParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JQuickJavaParser.RPAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public StaticCallContext(MethodInvocationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterStaticCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitStaticCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitStaticCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ThisMethodCallContext extends MethodInvocationContext {
		public ThisContext this_() {
			return getRuleContext(ThisContext.class,0);
		}
		public TerminalNode DOT() { return getToken(JQuickJavaParser.DOT, 0); }
		public MethodNameContext methodName() {
			return getRuleContext(MethodNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JQuickJavaParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JQuickJavaParser.RPAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public ThisMethodCallContext(MethodInvocationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterThisMethodCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitThisMethodCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitThisMethodCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AccessStaticMethodCallContext extends MethodInvocationContext {
		public AccessStaticVariableContext accessStaticVariable() {
			return getRuleContext(AccessStaticVariableContext.class,0);
		}
		public TerminalNode DOT() { return getToken(JQuickJavaParser.DOT, 0); }
		public MethodNameContext methodName() {
			return getRuleContext(MethodNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JQuickJavaParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JQuickJavaParser.RPAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public AccessStaticMethodCallContext(MethodInvocationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterAccessStaticMethodCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitAccessStaticMethodCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitAccessStaticMethodCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ConstructorCallContext extends MethodInvocationContext {
		public TerminalNode NEW() { return getToken(JQuickJavaParser.NEW, 0); }
		public ClasssTypeContext classsType() {
			return getRuleContext(ClasssTypeContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JQuickJavaParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JQuickJavaParser.RPAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public ConstructorCallContext(MethodInvocationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterConstructorCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitConstructorCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitConstructorCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class InstanceMethodCallContext extends MethodInvocationContext {
		public InstanceNameContext instanceName() {
			return getRuleContext(InstanceNameContext.class,0);
		}
		public TerminalNode DOT() { return getToken(JQuickJavaParser.DOT, 0); }
		public MethodNameContext methodName() {
			return getRuleContext(MethodNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JQuickJavaParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JQuickJavaParser.RPAREN, 0); }
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public InstanceMethodCallContext(MethodInvocationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterInstanceMethodCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitInstanceMethodCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitInstanceMethodCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodInvocationContext methodInvocation() throws RecognitionException {
		MethodInvocationContext _localctx = new MethodInvocationContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_methodInvocation);
		int _la;
		try {
			setState(479);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				_localctx = new StaticCallContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(434);
				classsType();
				setState(435);
				match(COLON);
				setState(436);
				match(COLON);
				setState(437);
				methodName();
				setState(438);
				match(LPAREN);
				setState(440);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66093168L) != 0) || _la==IDENTIFIER) {
					{
					setState(439);
					argumentList();
					}
				}

				setState(442);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new ConstructorCallContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(444);
				match(NEW);
				setState(445);
				classsType();
				setState(446);
				match(LPAREN);
				setState(448);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66093168L) != 0) || _la==IDENTIFIER) {
					{
					setState(447);
					argumentList();
					}
				}

				setState(450);
				match(RPAREN);
				}
				break;
			case 3:
				_localctx = new InstanceMethodCallContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(452);
				instanceName();
				setState(453);
				match(DOT);
				setState(454);
				methodName();
				setState(455);
				match(LPAREN);
				setState(457);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66093168L) != 0) || _la==IDENTIFIER) {
					{
					setState(456);
					argumentList();
					}
				}

				setState(459);
				match(RPAREN);
				}
				break;
			case 4:
				_localctx = new ThisMethodCallContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(461);
				this_();
				setState(462);
				match(DOT);
				setState(463);
				methodName();
				setState(464);
				match(LPAREN);
				setState(466);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66093168L) != 0) || _la==IDENTIFIER) {
					{
					setState(465);
					argumentList();
					}
				}

				setState(468);
				match(RPAREN);
				}
				break;
			case 5:
				_localctx = new AccessStaticMethodCallContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(470);
				accessStaticVariable();
				setState(471);
				match(DOT);
				setState(472);
				methodName();
				setState(473);
				match(LPAREN);
				setState(475);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 66093168L) != 0) || _la==IDENTIFIER) {
					{
					setState(474);
					argumentList();
					}
				}

				setState(477);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ThisContext extends ParserRuleContext {
		public TerminalNode THIS() { return getToken(JQuickJavaParser.THIS, 0); }
		public ThisContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_this; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterThis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitThis(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitThis(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThisContext this_() throws RecognitionException {
		ThisContext _localctx = new ThisContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_this);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(481);
			match(THIS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InstanceNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(JQuickJavaParser.IDENTIFIER, 0); }
		public InstanceNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instanceName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterInstanceName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitInstanceName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitInstanceName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstanceNameContext instanceName() throws RecognitionException {
		InstanceNameContext _localctx = new InstanceNameContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_instanceName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(483);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(JQuickJavaParser.IDENTIFIER, 0); }
		public MethodNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterMethodName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitMethodName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitMethodName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodNameContext methodName() throws RecognitionException {
		MethodNameContext _localctx = new MethodNameContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_methodName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(485);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentListContext extends ParserRuleContext {
		public List<LiteralItemContext> literalItem() {
			return getRuleContexts(LiteralItemContext.class);
		}
		public LiteralItemContext literalItem(int i) {
			return getRuleContext(LiteralItemContext.class,i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitArgumentList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitArgumentList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(487);
			literalItem();
			setState(492);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(488);
				match(T__0);
				setState(489);
				literalItem();
				}
				}
				setState(494);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AccessStaticVariableContext extends ParserRuleContext {
		public ClasssTypeContext classsType() {
			return getRuleContext(ClasssTypeContext.class,0);
		}
		public AccessObjectNameContext accessObjectName() {
			return getRuleContext(AccessObjectNameContext.class,0);
		}
		public AccessStaticVariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_accessStaticVariable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterAccessStaticVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitAccessStaticVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitAccessStaticVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AccessStaticVariableContext accessStaticVariable() throws RecognitionException {
		AccessStaticVariableContext _localctx = new AccessStaticVariableContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_accessStaticVariable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(495);
			classsType();
			setState(496);
			match(T__10);
			setState(497);
			accessObjectName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AccessObjectNameContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public AccessObjectNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_accessObjectName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterAccessObjectName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitAccessObjectName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitAccessObjectName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AccessObjectNameContext accessObjectName() throws RecognitionException {
		AccessObjectNameContext _localctx = new AccessObjectNameContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_accessObjectName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(499);
			identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public Object value;
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public DateContext date() {
			return getRuleContext(DateContext.class,0);
		}
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public NullContext null_() {
			return getRuleContext(NullContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public VariablesContext variables() {
			return getRuleContext(VariablesContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public ListLiteralContext listLiteral() {
			return getRuleContext(ListLiteralContext.class,0);
		}
		public MapLiteralContext mapLiteral() {
			return getRuleContext(MapLiteralContext.class,0);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_literal);
		try {
			setState(513);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(501);
				string();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(502);
				number();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(503);
				date();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(504);
				bool();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(505);
				null_();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(506);
				identifier();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(507);
				variables();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(508);
				qualifiedName();
				setState(509);
				match(T__11);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(511);
				listLiteral();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(512);
				mapLiteral();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListLiteralContext extends ParserRuleContext {
		public List<Object> value;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ListLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterListLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitListLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitListLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListLiteralContext listLiteral() throws RecognitionException {
		ListLiteralContext _localctx = new ListLiteralContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_listLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(515);
			match(T__1);
			setState(524);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 24189456334964L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				setState(516);
				expression();
				setState(521);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(517);
					match(T__0);
					setState(518);
					expression();
					}
					}
					setState(523);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(526);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MapLiteralContext extends ParserRuleContext {
		public Map<Object, Object> value;
		public TerminalNode LBRACE() { return getToken(JQuickJavaParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(JQuickJavaParser.RBRACE, 0); }
		public List<MapEntryContext> mapEntry() {
			return getRuleContexts(MapEntryContext.class);
		}
		public MapEntryContext mapEntry(int i) {
			return getRuleContext(MapEntryContext.class,i);
		}
		public MapLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterMapLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitMapLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitMapLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapLiteralContext mapLiteral() throws RecognitionException {
		MapLiteralContext _localctx = new MapLiteralContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_mapLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(528);
			match(LBRACE);
			setState(537);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 24189456334964L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				setState(529);
				mapEntry();
				setState(534);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(530);
					match(T__0);
					setState(531);
					mapEntry();
					}
					}
					setState(536);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(539);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MapEntryContext extends ParserRuleContext {
		public Object key;
		public Object value;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode COLON() { return getToken(JQuickJavaParser.COLON, 0); }
		public MapEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterMapEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitMapEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitMapEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapEntryContext mapEntry() throws RecognitionException {
		MapEntryContext _localctx = new MapEntryContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_mapEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(541);
			expression();
			setState(542);
			match(COLON);
			setState(543);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ImportVarContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(JQuickJavaParser.IDENTIFIER, 0); }
		public ImportVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importVar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterImportVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitImportVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitImportVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportVarContext importVar() throws RecognitionException {
		ImportVarContext _localctx = new ImportVarContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_importVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(545);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdentifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(JQuickJavaParser.IDENTIFIER, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(547);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BoolContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(JQuickJavaParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(JQuickJavaParser.FALSE, 0); }
		public BoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolContext bool() throws RecognitionException {
		BoolContext _localctx = new BoolContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_bool);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(549);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StringContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(JQuickJavaParser.STRING, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(551);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DateContext extends ParserRuleContext {
		public TerminalNode DATE() { return getToken(JQuickJavaParser.DATE, 0); }
		public TerminalNode DATETIME() { return getToken(JQuickJavaParser.DATETIME, 0); }
		public DateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterDate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitDate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitDate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateContext date() throws RecognitionException {
		DateContext _localctx = new DateContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_date);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(553);
			_la = _input.LA(1);
			if ( !(_la==DATETIME || _la==DATE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariablesContext extends ParserRuleContext {
		public TerminalNode DOLLAR() { return getToken(JQuickJavaParser.DOLLAR, 0); }
		public TerminalNode LBRACE() { return getToken(JQuickJavaParser.LBRACE, 0); }
		public TerminalNode IDENTIFIER() { return getToken(JQuickJavaParser.IDENTIFIER, 0); }
		public TerminalNode RBRACE() { return getToken(JQuickJavaParser.RBRACE, 0); }
		public VariablesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variables; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterVariables(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitVariables(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitVariables(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariablesContext variables() throws RecognitionException {
		VariablesContext _localctx = new VariablesContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_variables);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(555);
			match(DOLLAR);
			setState(556);
			match(LBRACE);
			setState(557);
			match(IDENTIFIER);
			setState(558);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NumberContext extends ParserRuleContext {
		public TerminalNode NUMBERIC() { return getToken(JQuickJavaParser.NUMBERIC, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_number);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(560);
			match(NUMBERIC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NullContext extends ParserRuleContext {
		public TerminalNode TYPENULL() { return getToken(JQuickJavaParser.TYPENULL, 0); }
		public NullContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_null; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterNull(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitNull(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitNull(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NullContext null_() throws RecognitionException {
		NullContext _localctx = new NullContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_null);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(562);
			match(TYPENULL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001O\u0235\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0002"+
		"-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u00021\u00071\u0002"+
		"2\u00072\u00023\u00073\u00024\u00074\u00025\u00075\u00026\u00076\u0002"+
		"7\u00077\u00028\u00078\u00029\u00079\u0002:\u0007:\u0002;\u0007;\u0001"+
		"\u0000\u0001\u0000\u0005\u0000{\b\u0000\n\u0000\f\u0000~\t\u0000\u0001"+
		"\u0000\u0005\u0000\u0081\b\u0000\n\u0000\f\u0000\u0084\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0003\u0002\u0095\b\u0002\u0001\u0003\u0001\u0003"+
		"\u0003\u0003\u0099\b\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0005\u0005\u00a1\b\u0005\n\u0005\f\u0005\u00a4"+
		"\t\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0003\u0006\u00aa"+
		"\b\u0006\u0001\u0006\u0001\u0006\u0004\u0006\u00ae\b\u0006\u000b\u0006"+
		"\f\u0006\u00af\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0003\u0007\u00b7\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003"+
		"\b\u00be\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003"+
		"\t\u00c7\b\t\u0001\n\u0001\n\u0001\n\u0005\n\u00cc\b\n\n\n\f\n\u00cf\t"+
		"\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b"+
		"\u00d6\b\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00da\b\u000b\u0001"+
		"\f\u0001\f\u0003\f\u00de\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u00e4"+
		"\b\r\u0001\r\u0001\r\u0003\r\u00e8\b\r\u0001\r\u0001\r\u0003\r\u00ec\b"+
		"\r\u0005\r\u00ee\b\r\n\r\f\r\u00f1\t\r\u0001\r\u0001\r\u0001\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0003\u000e\u00fd\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f"+
		"\u010e\b\u000f\n\u000f\f\u000f\u0111\t\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u0118\b\u000f\u0001\u0010\u0001"+
		"\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u0125\b\u0013\u0001"+
		"\u0013\u0001\u0013\u0003\u0013\u0129\b\u0013\u0001\u0013\u0001\u0013\u0003"+
		"\u0013\u012d\b\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001"+
		"\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0003\u0018\u0143\b\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001c\u0003"+
		"\u001c\u0151\b\u001c\u0001\u001c\u0003\u001c\u0154\b\u001c\u0001\u001c"+
		"\u0001\u001c\u0001\u001c\u0003\u001c\u0159\b\u001c\u0001\u001c\u0001\u001c"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0003\u001d\u0169\b\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0005\u001f\u0172\b\u001f\n\u001f"+
		"\f\u001f\u0175\t\u001f\u0001 \u0001 \u0001 \u0005 \u017a\b \n \f \u017d"+
		"\t \u0001!\u0001!\u0001!\u0005!\u0182\b!\n!\f!\u0185\t!\u0001\"\u0001"+
		"\"\u0001\"\u0001\"\u0001\"\u0003\"\u018c\b\"\u0001\"\u0001\"\u0001\"\u0001"+
		"\"\u0001\"\u0001#\u0001#\u0001#\u0005#\u0196\b#\n#\f#\u0199\t#\u0001$"+
		"\u0001$\u0001$\u0001$\u0001%\u0001%\u0001&\u0001&\u0003&\u01a3\b&\u0001"+
		"\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0003\'\u01ad"+
		"\b\'\u0001(\u0001(\u0001(\u0001(\u0001)\u0001)\u0001)\u0001)\u0001)\u0001"+
		")\u0003)\u01b9\b)\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0003)\u01c1"+
		"\b)\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0003)\u01ca\b)\u0001"+
		")\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0003)\u01d3\b)\u0001)\u0001"+
		")\u0001)\u0001)\u0001)\u0001)\u0001)\u0003)\u01dc\b)\u0001)\u0001)\u0003"+
		")\u01e0\b)\u0001*\u0001*\u0001+\u0001+\u0001,\u0001,\u0001-\u0001-\u0001"+
		"-\u0005-\u01eb\b-\n-\f-\u01ee\t-\u0001.\u0001.\u0001.\u0001.\u0001/\u0001"+
		"/\u00010\u00010\u00010\u00010\u00010\u00010\u00010\u00010\u00010\u0001"+
		"0\u00010\u00010\u00030\u0202\b0\u00011\u00011\u00011\u00011\u00051\u0208"+
		"\b1\n1\f1\u020b\t1\u00031\u020d\b1\u00011\u00011\u00012\u00012\u00012"+
		"\u00012\u00052\u0215\b2\n2\f2\u0218\t2\u00032\u021a\b2\u00012\u00012\u0001"+
		"3\u00013\u00013\u00013\u00014\u00014\u00015\u00015\u00016\u00016\u0001"+
		"7\u00017\u00018\u00018\u00019\u00019\u00019\u00019\u00019\u0001:\u0001"+
		":\u0001;\u0001;\u0001;\u0000\u0000<\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDF"+
		"HJLNPRTVXZ\\^`bdfhjlnprtv\u0000\u0006\u0002\u0000\u000f\u000f\u0014\u0019"+
		"\u0001\u000045\u0001\u0000.3\u0001\u000069\u0001\u0000@A\u0001\u0000D"+
		"E\u0246\u0000x\u0001\u0000\u0000\u0000\u0002\u0088\u0001\u0000\u0000\u0000"+
		"\u0004\u0094\u0001\u0000\u0000\u0000\u0006\u0096\u0001\u0000\u0000\u0000"+
		"\b\u009a\u0001\u0000\u0000\u0000\n\u009c\u0001\u0000\u0000\u0000\f\u00a9"+
		"\u0001\u0000\u0000\u0000\u000e\u00b1\u0001\u0000\u0000\u0000\u0010\u00b8"+
		"\u0001\u0000\u0000\u0000\u0012\u00bf\u0001\u0000\u0000\u0000\u0014\u00c8"+
		"\u0001\u0000\u0000\u0000\u0016\u00d9\u0001\u0000\u0000\u0000\u0018\u00dd"+
		"\u0001\u0000\u0000\u0000\u001a\u00df\u0001\u0000\u0000\u0000\u001c\u00fc"+
		"\u0001\u0000\u0000\u0000\u001e\u00fe\u0001\u0000\u0000\u0000 \u0119\u0001"+
		"\u0000\u0000\u0000\"\u011b\u0001\u0000\u0000\u0000$\u011d\u0001\u0000"+
		"\u0000\u0000&\u011f\u0001\u0000\u0000\u0000(\u0132\u0001\u0000\u0000\u0000"+
		"*\u0134\u0001\u0000\u0000\u0000,\u0136\u0001\u0000\u0000\u0000.\u0138"+
		"\u0001\u0000\u0000\u00000\u0140\u0001\u0000\u0000\u00002\u0146\u0001\u0000"+
		"\u0000\u00004\u0149\u0001\u0000\u0000\u00006\u014c\u0001\u0000\u0000\u0000"+
		"8\u0150\u0001\u0000\u0000\u0000:\u0168\u0001\u0000\u0000\u0000<\u016a"+
		"\u0001\u0000\u0000\u0000>\u016e\u0001\u0000\u0000\u0000@\u0176\u0001\u0000"+
		"\u0000\u0000B\u017e\u0001\u0000\u0000\u0000D\u0186\u0001\u0000\u0000\u0000"+
		"F\u0192\u0001\u0000\u0000\u0000H\u019a\u0001\u0000\u0000\u0000J\u019e"+
		"\u0001\u0000\u0000\u0000L\u01a2\u0001\u0000\u0000\u0000N\u01ac\u0001\u0000"+
		"\u0000\u0000P\u01ae\u0001\u0000\u0000\u0000R\u01df\u0001\u0000\u0000\u0000"+
		"T\u01e1\u0001\u0000\u0000\u0000V\u01e3\u0001\u0000\u0000\u0000X\u01e5"+
		"\u0001\u0000\u0000\u0000Z\u01e7\u0001\u0000\u0000\u0000\\\u01ef\u0001"+
		"\u0000\u0000\u0000^\u01f3\u0001\u0000\u0000\u0000`\u0201\u0001\u0000\u0000"+
		"\u0000b\u0203\u0001\u0000\u0000\u0000d\u0210\u0001\u0000\u0000\u0000f"+
		"\u021d\u0001\u0000\u0000\u0000h\u0221\u0001\u0000\u0000\u0000j\u0223\u0001"+
		"\u0000\u0000\u0000l\u0225\u0001\u0000\u0000\u0000n\u0227\u0001\u0000\u0000"+
		"\u0000p\u0229\u0001\u0000\u0000\u0000r\u022b\u0001\u0000\u0000\u0000t"+
		"\u0230\u0001\u0000\u0000\u0000v\u0232\u0001\u0000\u0000\u0000x|\u0006"+
		"\u0000\uffff\uffff\u0000y{\u0003\u0002\u0001\u0000zy\u0001\u0000\u0000"+
		"\u0000{~\u0001\u0000\u0000\u0000|z\u0001\u0000\u0000\u0000|}\u0001\u0000"+
		"\u0000\u0000}\u0082\u0001\u0000\u0000\u0000~|\u0001\u0000\u0000\u0000"+
		"\u007f\u0081\u0003\u0016\u000b\u0000\u0080\u007f\u0001\u0000\u0000\u0000"+
		"\u0081\u0084\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000\u0000\u0000"+
		"\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0085\u0001\u0000\u0000\u0000"+
		"\u0084\u0082\u0001\u0000\u0000\u0000\u0085\u0086\u0005\u0000\u0000\u0001"+
		"\u0086\u0087\u0006\u0000\uffff\uffff\u0000\u0087\u0001\u0001\u0000\u0000"+
		"\u0000\u0088\u0089\u0005\u001a\u0000\u0000\u0089\u008a\u0003\u0004\u0002"+
		"\u0000\u008a\u008b\u0005\u001d\u0000\u0000\u008b\u008c\u0003h4\u0000\u008c"+
		"\u008d\u0005#\u0000\u0000\u008d\u0003\u0001\u0000\u0000\u0000\u008e\u0095"+
		"\u0003\b\u0004\u0000\u008f\u0095\u0003\u0006\u0003\u0000\u0090\u0095\u0003"+
		"\u000e\u0007\u0000\u0091\u0095\u0003\u0012\t\u0000\u0092\u0095\u0003\u0010"+
		"\b\u0000\u0093\u0095\u0003\f\u0006\u0000\u0094\u008e\u0001\u0000\u0000"+
		"\u0000\u0094\u008f\u0001\u0000\u0000\u0000\u0094\u0090\u0001\u0000\u0000"+
		"\u0000\u0094\u0091\u0001\u0000\u0000\u0000\u0094\u0092\u0001\u0000\u0000"+
		"\u0000\u0094\u0093\u0001\u0000\u0000\u0000\u0095\u0005\u0001\u0000\u0000"+
		"\u0000\u0096\u0098\u0003\u0014\n\u0000\u0097\u0099\u0003\n\u0005\u0000"+
		"\u0098\u0097\u0001\u0000\u0000\u0000\u0098\u0099\u0001\u0000\u0000\u0000"+
		"\u0099\u0007\u0001\u0000\u0000\u0000\u009a\u009b\u0007\u0000\u0000\u0000"+
		"\u009b\t\u0001\u0000\u0000\u0000\u009c\u009d\u00050\u0000\u0000\u009d"+
		"\u00a2\u0003L&\u0000\u009e\u009f\u0005\u0001\u0000\u0000\u009f\u00a1\u0003"+
		"L&\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a1\u00a4\u0001\u0000\u0000"+
		"\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000\u00a2\u00a3\u0001\u0000\u0000"+
		"\u0000\u00a3\u00a5\u0001\u0000\u0000\u0000\u00a4\u00a2\u0001\u0000\u0000"+
		"\u0000\u00a5\u00a6\u0005.\u0000\u0000\u00a6\u000b\u0001\u0000\u0000\u0000"+
		"\u00a7\u00aa\u0003\b\u0004\u0000\u00a8\u00aa\u0003\u0014\n\u0000\u00a9"+
		"\u00a7\u0001\u0000\u0000\u0000\u00a9\u00a8\u0001\u0000\u0000\u0000\u00aa"+
		"\u00ad\u0001\u0000\u0000\u0000\u00ab\u00ac\u0005\u0002\u0000\u0000\u00ac"+
		"\u00ae\u0005\u0003\u0000\u0000\u00ad\u00ab\u0001\u0000\u0000\u0000\u00ae"+
		"\u00af\u0001\u0000\u0000\u0000\u00af\u00ad\u0001\u0000\u0000\u0000\u00af"+
		"\u00b0\u0001\u0000\u0000\u0000\u00b0\r\u0001\u0000\u0000\u0000\u00b1\u00b6"+
		"\u0005\u0004\u0000\u0000\u00b2\u00b3\u00050\u0000\u0000\u00b3\u00b4\u0003"+
		"L&\u0000\u00b4\u00b5\u0005.\u0000\u0000\u00b5\u00b7\u0001\u0000\u0000"+
		"\u0000\u00b6\u00b2\u0001\u0000\u0000\u0000\u00b6\u00b7\u0001\u0000\u0000"+
		"\u0000\u00b7\u000f\u0001\u0000\u0000\u0000\u00b8\u00bd\u0005\u0005\u0000"+
		"\u0000\u00b9\u00ba\u00050\u0000\u0000\u00ba\u00bb\u0003L&\u0000\u00bb"+
		"\u00bc\u0005.\u0000\u0000\u00bc\u00be\u0001\u0000\u0000\u0000\u00bd\u00b9"+
		"\u0001\u0000\u0000\u0000\u00bd\u00be\u0001\u0000\u0000\u0000\u00be\u0011"+
		"\u0001\u0000\u0000\u0000\u00bf\u00c6\u0005\u0006\u0000\u0000\u00c0\u00c1"+
		"\u00050\u0000\u0000\u00c1\u00c2\u0003L&\u0000\u00c2\u00c3\u0005\u0001"+
		"\u0000\u0000\u00c3\u00c4\u0003L&\u0000\u00c4\u00c5\u0005.\u0000\u0000"+
		"\u00c5\u00c7\u0001\u0000\u0000\u0000\u00c6\u00c0\u0001\u0000\u0000\u0000"+
		"\u00c6\u00c7\u0001\u0000\u0000\u0000\u00c7\u0013\u0001\u0000\u0000\u0000"+
		"\u00c8\u00cd\u0005J\u0000\u0000\u00c9\u00ca\u0005\r\u0000\u0000\u00ca"+
		"\u00cc\u0005J\u0000\u0000\u00cb\u00c9\u0001\u0000\u0000\u0000\u00cc\u00cf"+
		"\u0001\u0000\u0000\u0000\u00cd\u00cb\u0001\u0000\u0000\u0000\u00cd\u00ce"+
		"\u0001\u0000\u0000\u0000\u00ce\u0015\u0001\u0000\u0000\u0000\u00cf\u00cd"+
		"\u0001\u0000\u0000\u0000\u00d0\u00d1\u0003:\u001d\u0000\u00d1\u00d2\u0005"+
		"#\u0000\u0000\u00d2\u00da\u0001\u0000\u0000\u0000\u00d3\u00d5\u0003\u0018"+
		"\f\u0000\u00d4\u00d6\u0005#\u0000\u0000\u00d5\u00d4\u0001\u0000\u0000"+
		"\u0000\u00d5\u00d6\u0001\u0000\u0000\u0000\u00d6\u00da\u0001\u0000\u0000"+
		"\u0000\u00d7\u00da\u0003\u001c\u000e\u0000\u00d8\u00da\u0003<\u001e\u0000"+
		"\u00d9\u00d0\u0001\u0000\u0000\u0000\u00d9\u00d3\u0001\u0000\u0000\u0000"+
		"\u00d9\u00d7\u0001\u0000\u0000\u0000\u00d9\u00d8\u0001\u0000\u0000\u0000"+
		"\u00da\u0017\u0001\u0000\u0000\u0000\u00db\u00de\u0003R)\u0000\u00dc\u00de"+
		"\u0003D\"\u0000\u00dd\u00db\u0001\u0000\u0000\u0000\u00dd\u00dc\u0001"+
		"\u0000\u0000\u0000\u00de\u0019\u0001\u0000\u0000\u0000\u00df\u00e0\u0006"+
		"\r\uffff\uffff\u0000\u00e0\u00ef\u0005,\u0000\u0000\u00e1\u00e3\u0003"+
		"8\u001c\u0000\u00e2\u00e4\u0005#\u0000\u0000\u00e3\u00e2\u0001\u0000\u0000"+
		"\u0000\u00e3\u00e4\u0001\u0000\u0000\u0000\u00e4\u00ee\u0001\u0000\u0000"+
		"\u0000\u00e5\u00e7\u0003\u001c\u000e\u0000\u00e6\u00e8\u0005#\u0000\u0000"+
		"\u00e7\u00e6\u0001\u0000\u0000\u0000\u00e7\u00e8\u0001\u0000\u0000\u0000"+
		"\u00e8\u00ee\u0001\u0000\u0000\u0000\u00e9\u00eb\u0003\u0016\u000b\u0000"+
		"\u00ea\u00ec\u0005#\u0000\u0000\u00eb\u00ea\u0001\u0000\u0000\u0000\u00eb"+
		"\u00ec\u0001\u0000\u0000\u0000\u00ec\u00ee\u0001\u0000\u0000\u0000\u00ed"+
		"\u00e1\u0001\u0000\u0000\u0000\u00ed\u00e5\u0001\u0000\u0000\u0000\u00ed"+
		"\u00e9\u0001\u0000\u0000\u0000\u00ee\u00f1\u0001\u0000\u0000\u0000\u00ef"+
		"\u00ed\u0001\u0000\u0000\u0000\u00ef\u00f0\u0001\u0000\u0000\u0000\u00f0"+
		"\u00f2\u0001\u0000\u0000\u0000\u00f1\u00ef\u0001\u0000\u0000\u0000\u00f2"+
		"\u00f3\u0005-\u0000\u0000\u00f3\u00f4\u0006\r\uffff\uffff\u0000\u00f4"+
		"\u001b\u0001\u0000\u0000\u0000\u00f5\u00fd\u0003\u001e\u000f\u0000\u00f6"+
		"\u00fd\u0003&\u0013\u0000\u00f7\u00fd\u0003.\u0017\u0000\u00f8\u00fd\u0003"+
		"0\u0018\u0000\u00f9\u00fd\u00032\u0019\u0000\u00fa\u00fd\u00034\u001a"+
		"\u0000\u00fb\u00fd\u00036\u001b\u0000\u00fc\u00f5\u0001\u0000\u0000\u0000"+
		"\u00fc\u00f6\u0001\u0000\u0000\u0000\u00fc\u00f7\u0001\u0000\u0000\u0000"+
		"\u00fc\u00f8\u0001\u0000\u0000\u0000\u00fc\u00f9\u0001\u0000\u0000\u0000"+
		"\u00fc\u00fa\u0001\u0000\u0000\u0000\u00fc\u00fb\u0001\u0000\u0000\u0000"+
		"\u00fd\u001d\u0001\u0000\u0000\u0000\u00fe\u00ff\u0005\u0007\u0000\u0000"+
		"\u00ff\u0100\u0005*\u0000\u0000\u0100\u0101\u0003*\u0015\u0000\u0101\u0102"+
		"\u0005+\u0000\u0000\u0102\u0103\u0006\u000f\uffff\uffff\u0000\u0103\u0104"+
		"\u0003\u001a\r\u0000\u0104\u010f\u0006\u000f\uffff\uffff\u0000\u0105\u0106"+
		"\u0005\b\u0000\u0000\u0106\u0107\u0005*\u0000\u0000\u0107\u0108\u0003"+
		" \u0010\u0000\u0108\u0109\u0005+\u0000\u0000\u0109\u010a\u0006\u000f\uffff"+
		"\uffff\u0000\u010a\u010b\u0003\"\u0011\u0000\u010b\u010c\u0006\u000f\uffff"+
		"\uffff\u0000\u010c\u010e\u0001\u0000\u0000\u0000\u010d\u0105\u0001\u0000"+
		"\u0000\u0000\u010e\u0111\u0001\u0000\u0000\u0000\u010f\u010d\u0001\u0000"+
		"\u0000\u0000\u010f\u0110\u0001\u0000\u0000\u0000\u0110\u0117\u0001\u0000"+
		"\u0000\u0000\u0111\u010f\u0001\u0000\u0000\u0000\u0112\u0113\u0005\t\u0000"+
		"\u0000\u0113\u0114\u0006\u000f\uffff\uffff\u0000\u0114\u0115\u0003$\u0012"+
		"\u0000\u0115\u0116\u0006\u000f\uffff\uffff\u0000\u0116\u0118\u0001\u0000"+
		"\u0000\u0000\u0117\u0112\u0001\u0000\u0000\u0000\u0117\u0118\u0001\u0000"+
		"\u0000\u0000\u0118\u001f\u0001\u0000\u0000\u0000\u0119\u011a\u0003:\u001d"+
		"\u0000\u011a!\u0001\u0000\u0000\u0000\u011b\u011c\u0003\u001a\r\u0000"+
		"\u011c#\u0001\u0000\u0000\u0000\u011d\u011e\u0003\u001a\r\u0000\u011e"+
		"%\u0001\u0000\u0000\u0000\u011f\u0120\u0005!\u0000\u0000\u0120\u0121\u0005"+
		"*\u0000\u0000\u0121\u0124\u0006\u0013\uffff\uffff\u0000\u0122\u0125\u0003"+
		"8\u001c\u0000\u0123\u0125\u0003(\u0014\u0000\u0124\u0122\u0001\u0000\u0000"+
		"\u0000\u0124\u0123\u0001\u0000\u0000\u0000\u0124\u0125\u0001\u0000\u0000"+
		"\u0000\u0125\u0126\u0001\u0000\u0000\u0000\u0126\u0128\u0005#\u0000\u0000"+
		"\u0127\u0129\u0003*\u0015\u0000\u0128\u0127\u0001\u0000\u0000\u0000\u0128"+
		"\u0129\u0001\u0000\u0000\u0000\u0129\u012a\u0001\u0000\u0000\u0000\u012a"+
		"\u012c\u0005#\u0000\u0000\u012b\u012d\u0003,\u0016\u0000\u012c\u012b\u0001"+
		"\u0000\u0000\u0000\u012c\u012d\u0001\u0000\u0000\u0000\u012d\u012e\u0001"+
		"\u0000\u0000\u0000\u012e\u012f\u0005+\u0000\u0000\u012f\u0130\u0003\u001a"+
		"\r\u0000\u0130\u0131\u0006\u0013\uffff\uffff\u0000\u0131\'\u0001\u0000"+
		"\u0000\u0000\u0132\u0133\u0003:\u001d\u0000\u0133)\u0001\u0000\u0000\u0000"+
		"\u0134\u0135\u0003:\u001d\u0000\u0135+\u0001\u0000\u0000\u0000\u0136\u0137"+
		"\u0003:\u001d\u0000\u0137-\u0001\u0000\u0000\u0000\u0138\u0139\u0005 "+
		"\u0000\u0000\u0139\u013a\u0005*\u0000\u0000\u013a\u013b\u0003:\u001d\u0000"+
		"\u013b\u013c\u0005+\u0000\u0000\u013c\u013d\u0006\u0017\uffff\uffff\u0000"+
		"\u013d\u013e\u0003\u001a\r\u0000\u013e\u013f\u0006\u0017\uffff\uffff\u0000"+
		"\u013f/\u0001\u0000\u0000\u0000\u0140\u0142\u0005\u001e\u0000\u0000\u0141"+
		"\u0143\u0003:\u001d\u0000\u0142\u0141\u0001\u0000\u0000\u0000\u0142\u0143"+
		"\u0001\u0000\u0000\u0000\u0143\u0144\u0001\u0000\u0000\u0000\u0144\u0145"+
		"\u0005#\u0000\u0000\u01451\u0001\u0000\u0000\u0000\u0146\u0147\u0005\u0013"+
		"\u0000\u0000\u0147\u0148\u0005#\u0000\u0000\u01483\u0001\u0000\u0000\u0000"+
		"\u0149\u014a\u0005\u0012\u0000\u0000\u014a\u014b\u0005#\u0000\u0000\u014b"+
		"5\u0001\u0000\u0000\u0000\u014c\u014d\u0003:\u001d\u0000\u014d\u014e\u0005"+
		"#\u0000\u0000\u014e7\u0001\u0000\u0000\u0000\u014f\u0151\u0005\u000e\u0000"+
		"\u0000\u0150\u014f\u0001\u0000\u0000\u0000\u0150\u0151\u0001\u0000\u0000"+
		"\u0000\u0151\u0153\u0001\u0000\u0000\u0000\u0152\u0154\u0003L&\u0000\u0153"+
		"\u0152\u0001\u0000\u0000\u0000\u0153\u0154\u0001\u0000\u0000\u0000\u0154"+
		"\u0155\u0001\u0000\u0000\u0000\u0155\u0156\u0005J\u0000\u0000\u0156\u0158"+
		"\u0005\"\u0000\u0000\u0157\u0159\u0003:\u001d\u0000\u0158\u0157\u0001"+
		"\u0000\u0000\u0000\u0158\u0159\u0001\u0000\u0000\u0000\u0159\u015a\u0001"+
		"\u0000\u0000\u0000\u015a\u015b\u0006\u001c\uffff\uffff\u0000\u015b9\u0001"+
		"\u0000\u0000\u0000\u015c\u015d\u0003B!\u0000\u015d\u015e\u0006\u001d\uffff"+
		"\uffff\u0000\u015e\u0169\u0001\u0000\u0000\u0000\u015f\u0160\u0003>\u001f"+
		"\u0000\u0160\u0161\u0006\u001d\uffff\uffff\u0000\u0161\u0169\u0001\u0000"+
		"\u0000\u0000\u0162\u0163\u0003R)\u0000\u0163\u0164\u0006\u001d\uffff\uffff"+
		"\u0000\u0164\u0169\u0001\u0000\u0000\u0000\u0165\u0166\u0003N\'\u0000"+
		"\u0166\u0167\u0006\u001d\uffff\uffff\u0000\u0167\u0169\u0001\u0000\u0000"+
		"\u0000\u0168\u015c\u0001\u0000\u0000\u0000\u0168\u015f\u0001\u0000\u0000"+
		"\u0000\u0168\u0162\u0001\u0000\u0000\u0000\u0168\u0165\u0001\u0000\u0000"+
		"\u0000\u0169;\u0001\u0000\u0000\u0000\u016a\u016b\u0005\n\u0000\u0000"+
		"\u016b\u016c\u0003:\u001d\u0000\u016c\u016d\u0005+\u0000\u0000\u016d="+
		"\u0001\u0000\u0000\u0000\u016e\u0173\u0003@ \u0000\u016f\u0170\u0007\u0001"+
		"\u0000\u0000\u0170\u0172\u0003@ \u0000\u0171\u016f\u0001\u0000\u0000\u0000"+
		"\u0172\u0175\u0001\u0000\u0000\u0000\u0173\u0171\u0001\u0000\u0000\u0000"+
		"\u0173\u0174\u0001\u0000\u0000\u0000\u0174?\u0001\u0000\u0000\u0000\u0175"+
		"\u0173\u0001\u0000\u0000\u0000\u0176\u017b\u0003N\'\u0000\u0177\u0178"+
		"\u0007\u0002\u0000\u0000\u0178\u017a\u0003N\'\u0000\u0179\u0177\u0001"+
		"\u0000\u0000\u0000\u017a\u017d\u0001\u0000\u0000\u0000\u017b\u0179\u0001"+
		"\u0000\u0000\u0000\u017b\u017c\u0001\u0000\u0000\u0000\u017cA\u0001\u0000"+
		"\u0000\u0000\u017d\u017b\u0001\u0000\u0000\u0000\u017e\u0183\u0003N\'"+
		"\u0000\u017f\u0180\u0007\u0003\u0000\u0000\u0180\u0182\u0003N\'\u0000"+
		"\u0181\u017f\u0001\u0000\u0000\u0000\u0182\u0185\u0001\u0000\u0000\u0000"+
		"\u0183\u0181\u0001\u0000\u0000\u0000\u0183\u0184\u0001\u0000\u0000\u0000"+
		"\u0184C\u0001\u0000\u0000\u0000\u0185\u0183\u0001\u0000\u0000\u0000\u0186"+
		"\u0187\u0003L&\u0000\u0187\u0188\u0005\u001f\u0000\u0000\u0188\u0189\u0005"+
		"J\u0000\u0000\u0189\u018b\u0005*\u0000\u0000\u018a\u018c\u0003F#\u0000"+
		"\u018b\u018a\u0001\u0000\u0000\u0000\u018b\u018c\u0001\u0000\u0000\u0000"+
		"\u018c\u018d\u0001\u0000\u0000\u0000\u018d\u018e\u0005+\u0000\u0000\u018e"+
		"\u018f\u0006\"\uffff\uffff\u0000\u018f\u0190\u0003\u001a\r\u0000\u0190"+
		"\u0191\u0006\"\uffff\uffff\u0000\u0191E\u0001\u0000\u0000\u0000\u0192"+
		"\u0197\u0003H$\u0000\u0193\u0194\u0005\u0001\u0000\u0000\u0194\u0196\u0003"+
		"H$\u0000\u0195\u0193\u0001\u0000\u0000\u0000\u0196\u0199\u0001\u0000\u0000"+
		"\u0000\u0197\u0195\u0001\u0000\u0000\u0000\u0197\u0198\u0001\u0000\u0000"+
		"\u0000\u0198G\u0001\u0000\u0000\u0000\u0199\u0197\u0001\u0000\u0000\u0000"+
		"\u019a\u019b\u0003L&\u0000\u019b\u019c\u0005B\u0000\u0000\u019c\u019d"+
		"\u0003J%\u0000\u019dI\u0001\u0000\u0000\u0000\u019e\u019f\u0005J\u0000"+
		"\u0000\u019fK\u0001\u0000\u0000\u0000\u01a0\u01a3\u0003h4\u0000\u01a1"+
		"\u01a3\u0003\u0004\u0002\u0000\u01a2\u01a0\u0001\u0000\u0000\u0000\u01a2"+
		"\u01a1\u0001\u0000\u0000\u0000\u01a3M\u0001\u0000\u0000\u0000\u01a4\u01ad"+
		"\u0003`0\u0000\u01a5\u01ad\u0005J\u0000\u0000\u01a6\u01ad\u0003R)\u0000"+
		"\u01a7\u01a8\u0005*\u0000\u0000\u01a8\u01a9\u0003:\u001d\u0000\u01a9\u01aa"+
		"\u0005+\u0000\u0000\u01aa\u01ad\u0001\u0000\u0000\u0000\u01ab\u01ad\u0003"+
		"8\u001c\u0000\u01ac\u01a4\u0001\u0000\u0000\u0000\u01ac\u01a5\u0001\u0000"+
		"\u0000\u0000\u01ac\u01a6\u0001\u0000\u0000\u0000\u01ac\u01a7\u0001\u0000"+
		"\u0000\u0000\u01ac\u01ab\u0001\u0000\u0000\u0000\u01adO\u0001\u0000\u0000"+
		"\u0000\u01ae\u01af\u0003L&\u0000\u01af\u01b0\u0005B\u0000\u0000\u01b0"+
		"\u01b1\u0003`0\u0000\u01b1Q\u0001\u0000\u0000\u0000\u01b2\u01b3\u0003"+
		"L&\u0000\u01b3\u01b4\u0005B\u0000\u0000\u01b4\u01b5\u0005B\u0000\u0000"+
		"\u01b5\u01b6\u0003X,\u0000\u01b6\u01b8\u0005*\u0000\u0000\u01b7\u01b9"+
		"\u0003Z-\u0000\u01b8\u01b7\u0001\u0000\u0000\u0000\u01b8\u01b9\u0001\u0000"+
		"\u0000\u0000\u01b9\u01ba\u0001\u0000\u0000\u0000\u01ba\u01bb\u0005+\u0000"+
		"\u0000\u01bb\u01e0\u0001\u0000\u0000\u0000\u01bc\u01bd\u0005\u001b\u0000"+
		"\u0000\u01bd\u01be\u0003L&\u0000\u01be\u01c0\u0005*\u0000\u0000\u01bf"+
		"\u01c1\u0003Z-\u0000\u01c0\u01bf\u0001\u0000\u0000\u0000\u01c0\u01c1\u0001"+
		"\u0000\u0000\u0000\u01c1\u01c2\u0001\u0000\u0000\u0000\u01c2\u01c3\u0005"+
		"+\u0000\u0000\u01c3\u01e0\u0001\u0000\u0000\u0000\u01c4\u01c5\u0003V+"+
		"\u0000\u01c5\u01c6\u0005\r\u0000\u0000\u01c6\u01c7\u0003X,\u0000\u01c7"+
		"\u01c9\u0005*\u0000\u0000\u01c8\u01ca\u0003Z-\u0000\u01c9\u01c8\u0001"+
		"\u0000\u0000\u0000\u01c9\u01ca\u0001\u0000\u0000\u0000\u01ca\u01cb\u0001"+
		"\u0000\u0000\u0000\u01cb\u01cc\u0005+\u0000\u0000\u01cc\u01e0\u0001\u0000"+
		"\u0000\u0000\u01cd\u01ce\u0003T*\u0000\u01ce\u01cf\u0005\r\u0000\u0000"+
		"\u01cf\u01d0\u0003X,\u0000\u01d0\u01d2\u0005*\u0000\u0000\u01d1\u01d3"+
		"\u0003Z-\u0000\u01d2\u01d1\u0001\u0000\u0000\u0000\u01d2\u01d3\u0001\u0000"+
		"\u0000\u0000\u01d3\u01d4\u0001\u0000\u0000\u0000\u01d4\u01d5\u0005+\u0000"+
		"\u0000\u01d5\u01e0\u0001\u0000\u0000\u0000\u01d6\u01d7\u0003\\.\u0000"+
		"\u01d7\u01d8\u0005\r\u0000\u0000\u01d8\u01d9\u0003X,\u0000\u01d9\u01db"+
		"\u0005*\u0000\u0000\u01da\u01dc\u0003Z-\u0000\u01db\u01da\u0001\u0000"+
		"\u0000\u0000\u01db\u01dc\u0001\u0000\u0000\u0000\u01dc\u01dd\u0001\u0000"+
		"\u0000\u0000\u01dd\u01de\u0005+\u0000\u0000\u01de\u01e0\u0001\u0000\u0000"+
		"\u0000\u01df\u01b2\u0001\u0000\u0000\u0000\u01df\u01bc\u0001\u0000\u0000"+
		"\u0000\u01df\u01c4\u0001\u0000\u0000\u0000\u01df\u01cd\u0001\u0000\u0000"+
		"\u0000\u01df\u01d6\u0001\u0000\u0000\u0000\u01e0S\u0001\u0000\u0000\u0000"+
		"\u01e1\u01e2\u0005\u0011\u0000\u0000\u01e2U\u0001\u0000\u0000\u0000\u01e3"+
		"\u01e4\u0005J\u0000\u0000\u01e4W\u0001\u0000\u0000\u0000\u01e5\u01e6\u0005"+
		"J\u0000\u0000\u01e6Y\u0001\u0000\u0000\u0000\u01e7\u01ec\u0003P(\u0000"+
		"\u01e8\u01e9\u0005\u0001\u0000\u0000\u01e9\u01eb\u0003P(\u0000\u01ea\u01e8"+
		"\u0001\u0000\u0000\u0000\u01eb\u01ee\u0001\u0000\u0000\u0000\u01ec\u01ea"+
		"\u0001\u0000\u0000\u0000\u01ec\u01ed\u0001\u0000\u0000\u0000\u01ed[\u0001"+
		"\u0000\u0000\u0000\u01ee\u01ec\u0001\u0000\u0000\u0000\u01ef\u01f0\u0003"+
		"L&\u0000\u01f0\u01f1\u0005\u000b\u0000\u0000\u01f1\u01f2\u0003^/\u0000"+
		"\u01f2]\u0001\u0000\u0000\u0000\u01f3\u01f4\u0003j5\u0000\u01f4_\u0001"+
		"\u0000\u0000\u0000\u01f5\u0202\u0003n7\u0000\u01f6\u0202\u0003t:\u0000"+
		"\u01f7\u0202\u0003p8\u0000\u01f8\u0202\u0003l6\u0000\u01f9\u0202\u0003"+
		"v;\u0000\u01fa\u0202\u0003j5\u0000\u01fb\u0202\u0003r9\u0000\u01fc\u01fd"+
		"\u0003\u0014\n\u0000\u01fd\u01fe\u0005\f\u0000\u0000\u01fe\u0202\u0001"+
		"\u0000\u0000\u0000\u01ff\u0202\u0003b1\u0000\u0200\u0202\u0003d2\u0000"+
		"\u0201\u01f5\u0001\u0000\u0000\u0000\u0201\u01f6\u0001\u0000\u0000\u0000"+
		"\u0201\u01f7\u0001\u0000\u0000\u0000\u0201\u01f8\u0001\u0000\u0000\u0000"+
		"\u0201\u01f9\u0001\u0000\u0000\u0000\u0201\u01fa\u0001\u0000\u0000\u0000"+
		"\u0201\u01fb\u0001\u0000\u0000\u0000\u0201\u01fc\u0001\u0000\u0000\u0000"+
		"\u0201\u01ff\u0001\u0000\u0000\u0000\u0201\u0200\u0001\u0000\u0000\u0000"+
		"\u0202a\u0001\u0000\u0000\u0000\u0203\u020c\u0005\u0002\u0000\u0000\u0204"+
		"\u0209\u0003:\u001d\u0000\u0205\u0206\u0005\u0001\u0000\u0000\u0206\u0208"+
		"\u0003:\u001d\u0000\u0207\u0205\u0001\u0000\u0000\u0000\u0208\u020b\u0001"+
		"\u0000\u0000\u0000\u0209\u0207\u0001\u0000\u0000\u0000\u0209\u020a\u0001"+
		"\u0000\u0000\u0000\u020a\u020d\u0001\u0000\u0000\u0000\u020b\u0209\u0001"+
		"\u0000\u0000\u0000\u020c\u0204\u0001\u0000\u0000\u0000\u020c\u020d\u0001"+
		"\u0000\u0000\u0000\u020d\u020e\u0001\u0000\u0000\u0000\u020e\u020f\u0005"+
		"\u0003\u0000\u0000\u020fc\u0001\u0000\u0000\u0000\u0210\u0219\u0005,\u0000"+
		"\u0000\u0211\u0216\u0003f3\u0000\u0212\u0213\u0005\u0001\u0000\u0000\u0213"+
		"\u0215\u0003f3\u0000\u0214\u0212\u0001\u0000\u0000\u0000\u0215\u0218\u0001"+
		"\u0000\u0000\u0000\u0216\u0214\u0001\u0000\u0000\u0000\u0216\u0217\u0001"+
		"\u0000\u0000\u0000\u0217\u021a\u0001\u0000\u0000\u0000\u0218\u0216\u0001"+
		"\u0000\u0000\u0000\u0219\u0211\u0001\u0000\u0000\u0000\u0219\u021a\u0001"+
		"\u0000\u0000\u0000\u021a\u021b\u0001\u0000\u0000\u0000\u021b\u021c\u0005"+
		"-\u0000\u0000\u021ce\u0001\u0000\u0000\u0000\u021d\u021e\u0003:\u001d"+
		"\u0000\u021e\u021f\u0005B\u0000\u0000\u021f\u0220\u0003:\u001d\u0000\u0220"+
		"g\u0001\u0000\u0000\u0000\u0221\u0222\u0005J\u0000\u0000\u0222i\u0001"+
		"\u0000\u0000\u0000\u0223\u0224\u0005J\u0000\u0000\u0224k\u0001\u0000\u0000"+
		"\u0000\u0225\u0226\u0007\u0004\u0000\u0000\u0226m\u0001\u0000\u0000\u0000"+
		"\u0227\u0228\u0005K\u0000\u0000\u0228o\u0001\u0000\u0000\u0000\u0229\u022a"+
		"\u0007\u0005\u0000\u0000\u022aq\u0001\u0000\u0000\u0000\u022b\u022c\u0005"+
		")\u0000\u0000\u022c\u022d\u0005,\u0000\u0000\u022d\u022e\u0005J\u0000"+
		"\u0000\u022e\u022f\u0005-\u0000\u0000\u022fs\u0001\u0000\u0000\u0000\u0230"+
		"\u0231\u0005C\u0000\u0000\u0231u\u0001\u0000\u0000\u0000\u0232\u0233\u0005"+
		"\u0010\u0000\u0000\u0233w\u0001\u0000\u0000\u00001|\u0082\u0094\u0098"+
		"\u00a2\u00a9\u00af\u00b6\u00bd\u00c6\u00cd\u00d5\u00d9\u00dd\u00e3\u00e7"+
		"\u00eb\u00ed\u00ef\u00fc\u010f\u0117\u0124\u0128\u012c\u0142\u0150\u0153"+
		"\u0158\u0168\u0173\u017b\u0183\u018b\u0197\u01a2\u01ac\u01b8\u01c0\u01c9"+
		"\u01d2\u01db\u01df\u01ec\u0201\u0209\u020c\u0216\u0219";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}