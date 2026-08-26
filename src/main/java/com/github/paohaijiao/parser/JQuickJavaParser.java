// Generated from D:/idea/jthornruleGrammer/QuickJava/JQuickJava.g4 by ANTLR 4.13.2

package com.github.paohaijiao.parser;
import java.util.*;
import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.factory.JQuickJavaFunctionRegistry;
import com.github.paohaijiao.model.JQuickJavaFunctionDefinitionModel;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class JQuickJavaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, DOT=13, TYPESHORT=14, TYPENULL=15, THIS=16, 
		CONTINUE=17, BREAK=18, TYPEINT=19, TYPEFLOAT=20, TYPEDOUBLE=21, TYPELONG=22, 
		TYPEBOOLEAN=23, TYPEBYTE=24, IMPORT=25, NEW=26, VAR=27, AS=28, RETURN=29, 
		DEF=30, WHILE=31, FOR=32, ASSIGN=33, SEMICOLON=34, WITH=35, IF=36, THEN=37, 
		ELSEIF=38, ELSE=39, DOLLAR=40, LPAREN=41, RPAREN=42, LBRACE=43, RBRACE=44, 
		GT=45, GE=46, LT=47, LE=48, EQ=49, NE=50, AND=51, OR=52, ADD=53, MINUS=54, 
		MUL=55, DIV=56, CONTAIN=57, BUILTIN=58, NOTCONTAIN=59, START=60, NOTSTART=61, 
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
		RULE_comparison = 32, RULE_additive = 33, RULE_multiplicative = 34, RULE_functionDefinition = 35, 
		RULE_parameterList = 36, RULE_param = 37, RULE_functionVar = 38, RULE_classsType = 39, 
		RULE_primary = 40, RULE_primaryAtom = 41, RULE_argument = 42, RULE_typedArgument = 43, 
		RULE_methodInvocation = 44, RULE_this = 45, RULE_instanceName = 46, RULE_methodName = 47, 
		RULE_argumentList = 48, RULE_accessStaticVariable = 49, RULE_accessObjectName = 50, 
		RULE_literal = 51, RULE_listLiteral = 52, RULE_mapLiteral = 53, RULE_mapEntry = 54, 
		RULE_importVar = 55, RULE_identifier = 56, RULE_bool = 57, RULE_string = 58, 
		RULE_date = 59, RULE_variables = 60, RULE_number = 61, RULE_null = 62;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "importDeclaration", "paramType", "genericType", "simpleType", 
			"typeArguments", "arrayType", "listType", "setType", "mapType", "qualifiedName", 
			"statement", "method", "action", "controlStatement", "ifStatement", "elseIfConExpression", 
			"elseIfAction", "elseAction", "forStatement", "initExpression", "conExpression", 
			"stopExpression", "whileStatement", "returnStatement", "breakStatement", 
			"continueStatement", "expressionStatement", "variableDecl", "expression", 
			"sout", "logical", "comparison", "additive", "multiplicative", "functionDefinition", 
			"parameterList", "param", "functionVar", "classsType", "primary", "primaryAtom", 
			"argument", "typedArgument", "methodInvocation", "this", "instanceName", 
			"methodName", "argumentList", "accessStaticVariable", "accessObjectName", 
			"literal", "listLiteral", "mapLiteral", "mapEntry", "importVar", "identifier", 
			"bool", "string", "date", "variables", "number", "null"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "','", "'['", "']'", "'List'", "'Set'", "'Map'", "'if'", "'else if'", 
			"'else'", "'console.log('", "'@'", "'.class'", "'.'", "'short'", "'null'", 
			"'this'", "'continue'", "'break'", "'int'", "'float'", "'double'", "'long'", 
			"'boolean'", "'byte'", "'import'", "'new'", "'var'", "'as'", "'return'", 
			"'def'", "'while'", "'for'", "'='", "';'", "'WITH'", "'IF'", "'THEN'", 
			"'ELSEIF'", "'ELSE'", "'$'", "'('", "')'", "'{'", "'}'", "'>'", "'>='", 
			"'<'", "'<='", "'=='", "'!='", "'&&'", "'||'", "'+'", "'-'", "'*'", "'/'", 
			"'CONTAIN'", "'Builtin'", "'NOTCONTAIN'", "'START'", "'NOTSTART'", "'END'", 
			"'NOTEND'", "'true'", "'false'", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "DOT", "TYPESHORT", "TYPENULL", "THIS", "CONTINUE", "BREAK", "TYPEINT", 
			"TYPEFLOAT", "TYPEDOUBLE", "TYPELONG", "TYPEBOOLEAN", "TYPEBYTE", "IMPORT", 
			"NEW", "VAR", "AS", "RETURN", "DEF", "WHILE", "FOR", "ASSIGN", "SEMICOLON", 
			"WITH", "IF", "THEN", "ELSEIF", "ELSE", "DOLLAR", "LPAREN", "RPAREN", 
			"LBRACE", "RBRACE", "GT", "GE", "LT", "LE", "EQ", "NE", "AND", "OR", 
			"ADD", "MINUS", "MUL", "DIV", "CONTAIN", "BUILTIN", "NOTCONTAIN", "START", 
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

	    public static JContext context=new JContext();

	    private Stack<Map<String, Object>> scopes = new Stack<>();

	    public static  JQuickJavaFunctionRegistry registry= JQuickJavaFunctionRegistry.getInstance();


	    public void start(){
	        enterScope(); // 进入全局作用域
	     }
	    public  void init(JContext jContext) {
	    	if(!jContext.isEmpty()){
	    		context.putAll(jContext);
	    	}
	    }
	    public  JContext getJContext() {
	        return this.context;
	    }
	   // 进入新作用域
	   public  void enterScope() {
	        scopes.push(new HashMap<>());
	     }
	   public  void exitScope() {
	        scopes.pop();
	   }
	     // 声明变量
	    public void declareVar(String name, Object value) {
	        if (scopes.isEmpty()) {
	        	enterScope();
	        }
	        Map<String, Object> current = scopes.peek();
	        current.put(name, value);
	    }
	     // 查找变量（从内向外）
	    public Object findVar(String name) {
	    	if(context.containsKey(name)) {
	    		return context.get(name);
	    	}
	        for (int i = scopes.size() - 1; i >= 0; i--) {
	            if (scopes.get(i).containsKey(name)) {
	                return scopes.get(i).get(name);
	            }
	        }
	        console.error("Undefined variable: " + name);
	        return null;
	    }

	      // 赋值变量（从内向外找，找不到就在当前作用域创建）
	    public  void assignVar(String name, Object value) {
	         for (int i = scopes.size() - 1; i >= 0; i--) {
	              if (scopes.get(i).containsKey(name)) {
	                    scopes.get(i).put(name, value);
	                    return;
	               }
	         }
	         // 隐式声明
	         scopes.peek().put(name, value);
	    }
	    public Stack<Map<String, Object>> deepCopyScopeStack() {
	        Stack<Map<String, Object>> newStack = new Stack<>();
	            for (Map<String, Object> scope : scopes) {
	                Map<String, Object> newScope = new HashMap<>();
	                newScope.putAll(scope);
	                newStack.add(newScope);
	            }
	        return newStack;
	    }
	     public Map<String, Object> copyCurrentScope() {
	        Stack<Map<String, Object>> copyStack=deepCopyScopeStack();
	            if (copyStack.isEmpty()) {
	                return new HashMap<>();
	            }
	            return new HashMap<>(copyStack.peek());
	      }
	   public Map<String, Object> copyRuntimeEnvironment() {
	       Stack<Map<String, Object>> copyStack=deepCopyScopeStack();
	            if (copyStack.isEmpty()) {
	                return new HashMap<>();
	            }
	        Map<String, Object> result = new HashMap<>();
	        for (Map<String, Object> scope : copyStack) {
	            result.putAll(scope);
	        }
	        return result;
	   }
	   public void register(JQuickJavaFunctionDefinitionModel define){
	       registry.registerFunction(define);
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
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(127);
				importDeclaration();
				}
				}
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(136);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288242477859587316L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				{
				setState(133);
				statement();
				}
				}
				setState(138);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(139);
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
			setState(142);
			match(IMPORT);
			setState(143);
			paramType();
			setState(144);
			match(AS);
			setState(145);
			importVar();
			setState(146);
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
			setState(154);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(148);
				simpleType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(149);
				genericType();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(150);
				listType();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(151);
				mapType();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(152);
				setType();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(153);
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
			setState(156);
			qualifiedName();
			setState(158);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(157);
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
			setState(160);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 33046528L) != 0)) ) {
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
			setState(162);
			match(LT);
			setState(163);
			classsType();
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(164);
				match(T__0);
				setState(165);
				classsType();
				}
				}
				setState(170);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(171);
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
			setState(175);
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
				setState(173);
				simpleType();
				}
				break;
			case IDENTIFIER:
				{
				setState(174);
				qualifiedName();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(179); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(177);
				match(T__1);
				setState(178);
				match(T__2);
				}
				}
				setState(181); 
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
			setState(183);
			match(T__3);
			setState(188);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(184);
				match(LT);
				setState(185);
				classsType();
				setState(186);
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
			setState(190);
			match(T__4);
			setState(195);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(191);
				match(LT);
				setState(192);
				classsType();
				setState(193);
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
			setState(197);
			match(T__5);
			setState(204);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(198);
				match(LT);
				setState(199);
				classsType();
				setState(200);
				match(T__0);
				setState(201);
				classsType();
				setState(202);
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
			setState(206);
			match(IDENTIFIER);
			setState(211);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(207);
				match(DOT);
				setState(208);
				match(IDENTIFIER);
				}
				}
				setState(213);
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
		public VariableDeclContext variableDecl() {
			return getRuleContext(VariableDeclContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(JQuickJavaParser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
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
			setState(227);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(214);
				variableDecl();
				setState(216);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
				case 1:
					{
					setState(215);
					match(SEMICOLON);
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(218);
				expression();
				setState(219);
				match(SEMICOLON);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(221);
				method();
				setState(223);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
				case 1:
					{
					setState(222);
					match(SEMICOLON);
					}
					break;
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(225);
				controlStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(226);
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
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			functionDefinition();
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
			setState(232);
			match(LBRACE);
			setState(247);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288242477859587316L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				setState(245);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
				case 1:
					{
					setState(233);
					variableDecl();
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
				case 2:
					{
					setState(237);
					controlStatement();
					setState(239);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==SEMICOLON) {
						{
						setState(238);
						match(SEMICOLON);
						}
					}

					}
					break;
				case 3:
					{
					setState(241);
					statement();
					setState(243);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==SEMICOLON) {
						{
						setState(242);
						match(SEMICOLON);
						}
					}

					}
					break;
				}
				}
				setState(249);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(250);
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
			setState(260);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(253);
				ifStatement();
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 2);
				{
				setState(254);
				forStatement();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 3);
				{
				setState(255);
				whileStatement();
				}
				break;
			case RETURN:
				enterOuterAlt(_localctx, 4);
				{
				setState(256);
				returnStatement();
				}
				break;
			case BREAK:
				enterOuterAlt(_localctx, 5);
				{
				setState(257);
				breakStatement();
				}
				break;
			case CONTINUE:
				enterOuterAlt(_localctx, 6);
				{
				setState(258);
				continueStatement();
				}
				break;
			case T__1:
			case T__3:
			case T__4:
			case T__5:
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
			case BUILTIN:
			case TRUE:
			case FALSE:
			case NUMBERIC:
			case DATETIME:
			case DATE:
			case IDENTIFIER:
			case STRING:
				enterOuterAlt(_localctx, 7);
				{
				setState(259);
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
			setState(262);
			match(T__6);
			setState(263);
			match(LPAREN);
			setState(264);
			conExpression();
			setState(265);
			match(RPAREN);
			enterScope();
			setState(267);
			action();
			exitScope();
			setState(279);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(269);
				match(T__7);
				setState(270);
				match(LPAREN);
				setState(271);
				elseIfConExpression();
				setState(272);
				match(RPAREN);
				enterScope();
				setState(274);
				elseIfAction();
				exitScope();
				}
				}
				setState(281);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(287);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(282);
				match(T__8);
				enterScope();
				setState(284);
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
			setState(289);
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
			setState(291);
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
			setState(293);
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
			setState(295);
			match(FOR);
			setState(296);
			match(LPAREN);
			enterScope();
			setState(300);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(298);
				variableDecl();
				}
				break;
			case 2:
				{
				setState(299);
				initExpression();
				}
				break;
			}
			setState(302);
			match(SEMICOLON);
			setState(304);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288242470879871092L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				setState(303);
				conExpression();
				}
			}

			setState(306);
			match(SEMICOLON);
			setState(308);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288242470879871092L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				setState(307);
				stopExpression();
				}
			}

			setState(310);
			match(RPAREN);
			setState(311);
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
			setState(314);
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
			setState(316);
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
			setState(318);
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
			setState(320);
			match(WHILE);
			setState(321);
			match(LPAREN);
			setState(322);
			expression();
			setState(323);
			match(RPAREN);
			enterScope();
			setState(325);
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
			setState(328);
			match(RETURN);
			setState(330);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288242470879871092L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				setState(329);
				expression();
				}
			}

			setState(332);
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
			setState(334);
			match(BREAK);
			setState(335);
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
			setState(337);
			match(CONTINUE);
			setState(338);
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
			setState(340);
			expression();
			setState(341);
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
		public ClasssTypeContext ct;
		public Token id;
		public ExpressionContext expr;
		public TerminalNode ASSIGN() { return getToken(JQuickJavaParser.ASSIGN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(JQuickJavaParser.IDENTIFIER, 0); }
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(344);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				setState(343);
				((VariableDeclContext)_localctx).ct = classsType();
				}
				break;
			}
			setState(346);
			((VariableDeclContext)_localctx).id = match(IDENTIFIER);
			setState(347);
			match(ASSIGN);
			setState(349);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(348);
				((VariableDeclContext)_localctx).expr = expression();
				}
				break;
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
		public LogicalContext l;
		public LogicalContext logical() {
			return getRuleContext(LogicalContext.class,0);
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
			enterOuterAlt(_localctx, 1);
			{
			setState(351);
			((ExpressionContext)_localctx).l = logical();
			 ((ExpressionContext)_localctx).value =  ((ExpressionContext)_localctx).l.value; 
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
		public TerminalNode SEMICOLON() { return getToken(JQuickJavaParser.SEMICOLON, 0); }
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
			setState(354);
			match(T__9);
			setState(355);
			expression();
			setState(356);
			match(RPAREN);
			setState(357);
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
			enterOuterAlt(_localctx, 1);
			{
			setState(359);
			comparison();
			setState(364);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(360);
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
				setState(361);
				comparison();
				}
				}
				setState(366);
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
	public static class ComparisonContext extends ParserRuleContext {
		public Object value;
		public Token op;
		public List<AdditiveContext> additive() {
			return getRuleContexts(AdditiveContext.class);
		}
		public AdditiveContext additive(int i) {
			return getRuleContext(AdditiveContext.class,i);
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
			enterOuterAlt(_localctx, 1);
			{
			setState(367);
			additive();
			setState(372);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615441596416L) != 0)) {
				{
				{
				setState(368);
				((ComparisonContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2216615441596416L) != 0)) ) {
					((ComparisonContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(369);
				additive();
				}
				}
				setState(374);
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
	public static class AdditiveContext extends ParserRuleContext {
		public Object value;
		public List<MultiplicativeContext> multiplicative() {
			return getRuleContexts(MultiplicativeContext.class);
		}
		public MultiplicativeContext multiplicative(int i) {
			return getRuleContext(MultiplicativeContext.class,i);
		}
		public List<TerminalNode> ADD() { return getTokens(JQuickJavaParser.ADD); }
		public TerminalNode ADD(int i) {
			return getToken(JQuickJavaParser.ADD, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(JQuickJavaParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(JQuickJavaParser.MINUS, i);
		}
		public AdditiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterAdditive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitAdditive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitAdditive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveContext additive() throws RecognitionException {
		AdditiveContext _localctx = new AdditiveContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_additive);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(375);
			multiplicative();
			setState(380);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ADD || _la==MINUS) {
				{
				{
				setState(376);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(377);
				multiplicative();
				}
				}
				setState(382);
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
	public static class MultiplicativeContext extends ParserRuleContext {
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
		public MultiplicativeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicative; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterMultiplicative(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitMultiplicative(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitMultiplicative(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeContext multiplicative() throws RecognitionException {
		MultiplicativeContext _localctx = new MultiplicativeContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_multiplicative);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(383);
			primary();
			setState(388);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MUL || _la==DIV) {
				{
				{
				setState(384);
				_la = _input.LA(1);
				if ( !(_la==MUL || _la==DIV) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(385);
				primary();
				}
				}
				setState(390);
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
		enterRule(_localctx, 70, RULE_functionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(391);
			classsType();
			setState(392);
			match(DEF);
			setState(393);
			match(IDENTIFIER);
			setState(394);
			match(LPAREN);
			setState(396);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 33046640L) != 0) || _la==IDENTIFIER) {
				{
				setState(395);
				parameterList();
				}
			}

			setState(398);
			match(RPAREN);
			enterScope();
			setState(400);
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
		enterRule(_localctx, 72, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(403);
			param();
			setState(408);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(404);
				match(T__0);
				setState(405);
				param();
				}
				}
				setState(410);
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
		enterRule(_localctx, 74, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(411);
			classsType();
			setState(412);
			match(COLON);
			setState(413);
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
		enterRule(_localctx, 76, RULE_functionVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(415);
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
		enterRule(_localctx, 78, RULE_classsType);
		try {
			setState(419);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(417);
				importVar();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(418);
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
		public PrimaryAtomContext primaryAtom() {
			return getRuleContext(PrimaryAtomContext.class,0);
		}
		public MethodInvocationContext methodInvocation() {
			return getRuleContext(MethodInvocationContext.class,0);
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
		enterRule(_localctx, 80, RULE_primary);
		try {
			setState(423);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(421);
				primaryAtom();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(422);
				methodInvocation();
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
	public static class PrimaryAtomContext extends ParserRuleContext {
		public Object value;
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(JQuickJavaParser.IDENTIFIER, 0); }
		public TerminalNode LPAREN() { return getToken(JQuickJavaParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JQuickJavaParser.RPAREN, 0); }
		public PrimaryAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryAtom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterPrimaryAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitPrimaryAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitPrimaryAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryAtomContext primaryAtom() throws RecognitionException {
		PrimaryAtomContext _localctx = new PrimaryAtomContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_primaryAtom);
		try {
			setState(431);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(425);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(426);
				match(IDENTIFIER);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(427);
				match(LPAREN);
				setState(428);
				expression();
				setState(429);
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
	public static class ArgumentContext extends ParserRuleContext {
		public TypedArgumentContext typedArgument() {
			return getRuleContext(TypedArgumentContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_argument);
		try {
			setState(435);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(433);
				typedArgument();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(434);
				expression();
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
	public static class TypedArgumentContext extends ParserRuleContext {
		public ClasssTypeContext classsType() {
			return getRuleContext(ClasssTypeContext.class,0);
		}
		public TerminalNode COLON() { return getToken(JQuickJavaParser.COLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TypedArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typedArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterTypedArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitTypedArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitTypedArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypedArgumentContext typedArgument() throws RecognitionException {
		TypedArgumentContext _localctx = new TypedArgumentContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_typedArgument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(437);
			classsType();
			setState(438);
			match(COLON);
			setState(439);
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
	public static class BuiltinMethodCallContext extends MethodInvocationContext {
		public TerminalNode BUILTIN() { return getToken(JQuickJavaParser.BUILTIN, 0); }
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
		public BuiltinMethodCallContext(MethodInvocationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).enterBuiltinMethodCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickJavaListener ) ((JQuickJavaListener)listener).exitBuiltinMethodCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickJavaVisitor ) return ((JQuickJavaVisitor<? extends T>)visitor).visitBuiltinMethodCall(this);
			else return visitor.visitChildren(this);
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
		public PrimaryAtomContext primaryAtom() {
			return getRuleContext(PrimaryAtomContext.class,0);
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
		enterRule(_localctx, 88, RULE_methodInvocation);
		int _la;
		try {
			setState(496);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
			case 1:
				_localctx = new StaticCallContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(441);
				classsType();
				setState(442);
				match(COLON);
				setState(443);
				match(COLON);
				setState(444);
				methodName();
				setState(445);
				match(LPAREN);
				setState(447);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288242470879871092L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
					{
					setState(446);
					argumentList();
					}
				}

				setState(449);
				match(RPAREN);
				}
				break;
			case 2:
				_localctx = new ConstructorCallContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(451);
				match(NEW);
				setState(452);
				classsType();
				setState(453);
				match(LPAREN);
				setState(455);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288242470879871092L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
					{
					setState(454);
					argumentList();
					}
				}

				setState(457);
				match(RPAREN);
				}
				break;
			case 3:
				_localctx = new InstanceMethodCallContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(459);
				primaryAtom();
				setState(460);
				match(DOT);
				setState(461);
				methodName();
				setState(462);
				match(LPAREN);
				setState(464);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288242470879871092L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
					{
					setState(463);
					argumentList();
					}
				}

				setState(466);
				match(RPAREN);
				}
				break;
			case 4:
				_localctx = new ThisMethodCallContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(468);
				this_();
				setState(469);
				match(DOT);
				setState(470);
				methodName();
				setState(471);
				match(LPAREN);
				setState(473);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288242470879871092L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
					{
					setState(472);
					argumentList();
					}
				}

				setState(475);
				match(RPAREN);
				}
				break;
			case 5:
				_localctx = new BuiltinMethodCallContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(477);
				match(BUILTIN);
				setState(478);
				match(COLON);
				setState(479);
				match(COLON);
				setState(480);
				methodName();
				setState(481);
				match(LPAREN);
				setState(483);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288242470879871092L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
					{
					setState(482);
					argumentList();
					}
				}

				setState(485);
				match(RPAREN);
				}
				break;
			case 6:
				_localctx = new AccessStaticMethodCallContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(487);
				accessStaticVariable();
				setState(488);
				match(DOT);
				setState(489);
				methodName();
				setState(490);
				match(LPAREN);
				setState(492);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288242470879871092L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
					{
					setState(491);
					argumentList();
					}
				}

				setState(494);
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
		enterRule(_localctx, 90, RULE_this);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(498);
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
		enterRule(_localctx, 92, RULE_instanceName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(500);
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
		enterRule(_localctx, 94, RULE_methodName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(502);
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
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
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
		enterRule(_localctx, 96, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(504);
			argument();
			setState(509);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(505);
				match(T__0);
				setState(506);
				argument();
				}
				}
				setState(511);
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
		enterRule(_localctx, 98, RULE_accessStaticVariable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(512);
			classsType();
			setState(513);
			match(T__10);
			setState(514);
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
		enterRule(_localctx, 100, RULE_accessObjectName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(516);
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
		enterRule(_localctx, 102, RULE_literal);
		try {
			setState(530);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(518);
				string();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(519);
				number();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(520);
				date();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(521);
				bool();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(522);
				null_();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(523);
				identifier();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(524);
				variables();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(525);
				qualifiedName();
				setState(526);
				match(T__11);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(528);
				listLiteral();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(529);
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
		enterRule(_localctx, 104, RULE_listLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(532);
			match(T__1);
			setState(541);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288242470879871092L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				setState(533);
				expression();
				setState(538);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(534);
					match(T__0);
					setState(535);
					expression();
					}
					}
					setState(540);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(543);
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
		enterRule(_localctx, 106, RULE_mapLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(545);
			match(LBRACE);
			setState(554);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 288242470879871092L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 3131L) != 0)) {
				{
				setState(546);
				mapEntry();
				setState(551);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(547);
					match(T__0);
					setState(548);
					mapEntry();
					}
					}
					setState(553);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(556);
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
		enterRule(_localctx, 108, RULE_mapEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(558);
			expression();
			setState(559);
			match(COLON);
			setState(560);
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
		enterRule(_localctx, 110, RULE_importVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(562);
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
		enterRule(_localctx, 112, RULE_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(564);
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
		enterRule(_localctx, 114, RULE_bool);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(566);
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
		enterRule(_localctx, 116, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(568);
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
		enterRule(_localctx, 118, RULE_date);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(570);
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
		enterRule(_localctx, 120, RULE_variables);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(572);
			match(DOLLAR);
			setState(573);
			match(LBRACE);
			setState(574);
			match(IDENTIFIER);
			setState(575);
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
		enterRule(_localctx, 122, RULE_number);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(577);
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
		enterRule(_localctx, 124, RULE_null);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(579);
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
		"\u0004\u0001O\u0246\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
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
		"7\u00077\u00028\u00078\u00029\u00079\u0002:\u0007:\u0002;\u0007;\u0002"+
		"<\u0007<\u0002=\u0007=\u0002>\u0007>\u0001\u0000\u0001\u0000\u0005\u0000"+
		"\u0081\b\u0000\n\u0000\f\u0000\u0084\t\u0000\u0001\u0000\u0005\u0000\u0087"+
		"\b\u0000\n\u0000\f\u0000\u008a\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002\u009b\b\u0002\u0001\u0003\u0001\u0003\u0003\u0003\u009f\b"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0005\u0005\u00a7\b\u0005\n\u0005\f\u0005\u00aa\t\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0003\u0006\u00b0\b\u0006\u0001\u0006"+
		"\u0001\u0006\u0004\u0006\u00b4\b\u0006\u000b\u0006\f\u0006\u00b5\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u00bd"+
		"\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u00c4\b\b\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u00cd\b\t\u0001"+
		"\n\u0001\n\u0001\n\u0005\n\u00d2\b\n\n\n\f\n\u00d5\t\n\u0001\u000b\u0001"+
		"\u000b\u0003\u000b\u00d9\b\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0003\u000b\u00e0\b\u000b\u0001\u000b\u0001\u000b\u0003"+
		"\u000b\u00e4\b\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0003"+
		"\r\u00ec\b\r\u0001\r\u0001\r\u0003\r\u00f0\b\r\u0001\r\u0001\r\u0003\r"+
		"\u00f4\b\r\u0005\r\u00f6\b\r\n\r\f\r\u00f9\t\r\u0001\r\u0001\r\u0001\r"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0003\u000e\u0105\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0005\u000f\u0116\b\u000f\n\u000f\f\u000f\u0119\t\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u0120\b\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u012d"+
		"\b\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u0131\b\u0013\u0001\u0013"+
		"\u0001\u0013\u0003\u0013\u0135\b\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016"+
		"\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0003\u0018"+
		"\u014b\b\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001c\u0003\u001c\u0159\b\u001c\u0001\u001c\u0001\u001c\u0001\u001c"+
		"\u0003\u001c\u015e\b\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0005\u001f\u016b\b\u001f\n\u001f\f\u001f\u016e\t\u001f\u0001"+
		" \u0001 \u0001 \u0005 \u0173\b \n \f \u0176\t \u0001!\u0001!\u0001!\u0005"+
		"!\u017b\b!\n!\f!\u017e\t!\u0001\"\u0001\"\u0001\"\u0005\"\u0183\b\"\n"+
		"\"\f\"\u0186\t\"\u0001#\u0001#\u0001#\u0001#\u0001#\u0003#\u018d\b#\u0001"+
		"#\u0001#\u0001#\u0001#\u0001#\u0001$\u0001$\u0001$\u0005$\u0197\b$\n$"+
		"\f$\u019a\t$\u0001%\u0001%\u0001%\u0001%\u0001&\u0001&\u0001\'\u0001\'"+
		"\u0003\'\u01a4\b\'\u0001(\u0001(\u0003(\u01a8\b(\u0001)\u0001)\u0001)"+
		"\u0001)\u0001)\u0001)\u0003)\u01b0\b)\u0001*\u0001*\u0003*\u01b4\b*\u0001"+
		"+\u0001+\u0001+\u0001+\u0001,\u0001,\u0001,\u0001,\u0001,\u0001,\u0003"+
		",\u01c0\b,\u0001,\u0001,\u0001,\u0001,\u0001,\u0001,\u0003,\u01c8\b,\u0001"+
		",\u0001,\u0001,\u0001,\u0001,\u0001,\u0001,\u0003,\u01d1\b,\u0001,\u0001"+
		",\u0001,\u0001,\u0001,\u0001,\u0001,\u0003,\u01da\b,\u0001,\u0001,\u0001"+
		",\u0001,\u0001,\u0001,\u0001,\u0001,\u0003,\u01e4\b,\u0001,\u0001,\u0001"+
		",\u0001,\u0001,\u0001,\u0001,\u0003,\u01ed\b,\u0001,\u0001,\u0003,\u01f1"+
		"\b,\u0001-\u0001-\u0001.\u0001.\u0001/\u0001/\u00010\u00010\u00010\u0005"+
		"0\u01fc\b0\n0\f0\u01ff\t0\u00011\u00011\u00011\u00011\u00012\u00012\u0001"+
		"3\u00013\u00013\u00013\u00013\u00013\u00013\u00013\u00013\u00013\u0001"+
		"3\u00013\u00033\u0213\b3\u00014\u00014\u00014\u00014\u00054\u0219\b4\n"+
		"4\f4\u021c\t4\u00034\u021e\b4\u00014\u00014\u00015\u00015\u00015\u0001"+
		"5\u00055\u0226\b5\n5\f5\u0229\t5\u00035\u022b\b5\u00015\u00015\u00016"+
		"\u00016\u00016\u00016\u00017\u00017\u00018\u00018\u00019\u00019\u0001"+
		":\u0001:\u0001;\u0001;\u0001<\u0001<\u0001<\u0001<\u0001<\u0001=\u0001"+
		"=\u0001>\u0001>\u0001>\u0000\u0000?\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDF"+
		"HJLNPRTVXZ\\^`bdfhjlnprtvxz|\u0000\u0007\u0002\u0000\u000e\u000e\u0013"+
		"\u0018\u0001\u000034\u0001\u0000-2\u0001\u000056\u0001\u000078\u0001\u0000"+
		"@A\u0001\u0000DE\u0254\u0000~\u0001\u0000\u0000\u0000\u0002\u008e\u0001"+
		"\u0000\u0000\u0000\u0004\u009a\u0001\u0000\u0000\u0000\u0006\u009c\u0001"+
		"\u0000\u0000\u0000\b\u00a0\u0001\u0000\u0000\u0000\n\u00a2\u0001\u0000"+
		"\u0000\u0000\f\u00af\u0001\u0000\u0000\u0000\u000e\u00b7\u0001\u0000\u0000"+
		"\u0000\u0010\u00be\u0001\u0000\u0000\u0000\u0012\u00c5\u0001\u0000\u0000"+
		"\u0000\u0014\u00ce\u0001\u0000\u0000\u0000\u0016\u00e3\u0001\u0000\u0000"+
		"\u0000\u0018\u00e5\u0001\u0000\u0000\u0000\u001a\u00e7\u0001\u0000\u0000"+
		"\u0000\u001c\u0104\u0001\u0000\u0000\u0000\u001e\u0106\u0001\u0000\u0000"+
		"\u0000 \u0121\u0001\u0000\u0000\u0000\"\u0123\u0001\u0000\u0000\u0000"+
		"$\u0125\u0001\u0000\u0000\u0000&\u0127\u0001\u0000\u0000\u0000(\u013a"+
		"\u0001\u0000\u0000\u0000*\u013c\u0001\u0000\u0000\u0000,\u013e\u0001\u0000"+
		"\u0000\u0000.\u0140\u0001\u0000\u0000\u00000\u0148\u0001\u0000\u0000\u0000"+
		"2\u014e\u0001\u0000\u0000\u00004\u0151\u0001\u0000\u0000\u00006\u0154"+
		"\u0001\u0000\u0000\u00008\u0158\u0001\u0000\u0000\u0000:\u015f\u0001\u0000"+
		"\u0000\u0000<\u0162\u0001\u0000\u0000\u0000>\u0167\u0001\u0000\u0000\u0000"+
		"@\u016f\u0001\u0000\u0000\u0000B\u0177\u0001\u0000\u0000\u0000D\u017f"+
		"\u0001\u0000\u0000\u0000F\u0187\u0001\u0000\u0000\u0000H\u0193\u0001\u0000"+
		"\u0000\u0000J\u019b\u0001\u0000\u0000\u0000L\u019f\u0001\u0000\u0000\u0000"+
		"N\u01a3\u0001\u0000\u0000\u0000P\u01a7\u0001\u0000\u0000\u0000R\u01af"+
		"\u0001\u0000\u0000\u0000T\u01b3\u0001\u0000\u0000\u0000V\u01b5\u0001\u0000"+
		"\u0000\u0000X\u01f0\u0001\u0000\u0000\u0000Z\u01f2\u0001\u0000\u0000\u0000"+
		"\\\u01f4\u0001\u0000\u0000\u0000^\u01f6\u0001\u0000\u0000\u0000`\u01f8"+
		"\u0001\u0000\u0000\u0000b\u0200\u0001\u0000\u0000\u0000d\u0204\u0001\u0000"+
		"\u0000\u0000f\u0212\u0001\u0000\u0000\u0000h\u0214\u0001\u0000\u0000\u0000"+
		"j\u0221\u0001\u0000\u0000\u0000l\u022e\u0001\u0000\u0000\u0000n\u0232"+
		"\u0001\u0000\u0000\u0000p\u0234\u0001\u0000\u0000\u0000r\u0236\u0001\u0000"+
		"\u0000\u0000t\u0238\u0001\u0000\u0000\u0000v\u023a\u0001\u0000\u0000\u0000"+
		"x\u023c\u0001\u0000\u0000\u0000z\u0241\u0001\u0000\u0000\u0000|\u0243"+
		"\u0001\u0000\u0000\u0000~\u0082\u0006\u0000\uffff\uffff\u0000\u007f\u0081"+
		"\u0003\u0002\u0001\u0000\u0080\u007f\u0001\u0000\u0000\u0000\u0081\u0084"+
		"\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000\u0000\u0000\u0082\u0083"+
		"\u0001\u0000\u0000\u0000\u0083\u0088\u0001\u0000\u0000\u0000\u0084\u0082"+
		"\u0001\u0000\u0000\u0000\u0085\u0087\u0003\u0016\u000b\u0000\u0086\u0085"+
		"\u0001\u0000\u0000\u0000\u0087\u008a\u0001\u0000\u0000\u0000\u0088\u0086"+
		"\u0001\u0000\u0000\u0000\u0088\u0089\u0001\u0000\u0000\u0000\u0089\u008b"+
		"\u0001\u0000\u0000\u0000\u008a\u0088\u0001\u0000\u0000\u0000\u008b\u008c"+
		"\u0005\u0000\u0000\u0001\u008c\u008d\u0006\u0000\uffff\uffff\u0000\u008d"+
		"\u0001\u0001\u0000\u0000\u0000\u008e\u008f\u0005\u0019\u0000\u0000\u008f"+
		"\u0090\u0003\u0004\u0002\u0000\u0090\u0091\u0005\u001c\u0000\u0000\u0091"+
		"\u0092\u0003n7\u0000\u0092\u0093\u0005\"\u0000\u0000\u0093\u0003\u0001"+
		"\u0000\u0000\u0000\u0094\u009b\u0003\b\u0004\u0000\u0095\u009b\u0003\u0006"+
		"\u0003\u0000\u0096\u009b\u0003\u000e\u0007\u0000\u0097\u009b\u0003\u0012"+
		"\t\u0000\u0098\u009b\u0003\u0010\b\u0000\u0099\u009b\u0003\f\u0006\u0000"+
		"\u009a\u0094\u0001\u0000\u0000\u0000\u009a\u0095\u0001\u0000\u0000\u0000"+
		"\u009a\u0096\u0001\u0000\u0000\u0000\u009a\u0097\u0001\u0000\u0000\u0000"+
		"\u009a\u0098\u0001\u0000\u0000\u0000\u009a\u0099\u0001\u0000\u0000\u0000"+
		"\u009b\u0005\u0001\u0000\u0000\u0000\u009c\u009e\u0003\u0014\n\u0000\u009d"+
		"\u009f\u0003\n\u0005\u0000\u009e\u009d\u0001\u0000\u0000\u0000\u009e\u009f"+
		"\u0001\u0000\u0000\u0000\u009f\u0007\u0001\u0000\u0000\u0000\u00a0\u00a1"+
		"\u0007\u0000\u0000\u0000\u00a1\t\u0001\u0000\u0000\u0000\u00a2\u00a3\u0005"+
		"/\u0000\u0000\u00a3\u00a8\u0003N\'\u0000\u00a4\u00a5\u0005\u0001\u0000"+
		"\u0000\u00a5\u00a7\u0003N\'\u0000\u00a6\u00a4\u0001\u0000\u0000\u0000"+
		"\u00a7\u00aa\u0001\u0000\u0000\u0000\u00a8\u00a6\u0001\u0000\u0000\u0000"+
		"\u00a8\u00a9\u0001\u0000\u0000\u0000\u00a9\u00ab\u0001\u0000\u0000\u0000"+
		"\u00aa\u00a8\u0001\u0000\u0000\u0000\u00ab\u00ac\u0005-\u0000\u0000\u00ac"+
		"\u000b\u0001\u0000\u0000\u0000\u00ad\u00b0\u0003\b\u0004\u0000\u00ae\u00b0"+
		"\u0003\u0014\n\u0000\u00af\u00ad\u0001\u0000\u0000\u0000\u00af\u00ae\u0001"+
		"\u0000\u0000\u0000\u00b0\u00b3\u0001\u0000\u0000\u0000\u00b1\u00b2\u0005"+
		"\u0002\u0000\u0000\u00b2\u00b4\u0005\u0003\u0000\u0000\u00b3\u00b1\u0001"+
		"\u0000\u0000\u0000\u00b4\u00b5\u0001\u0000\u0000\u0000\u00b5\u00b3\u0001"+
		"\u0000\u0000\u0000\u00b5\u00b6\u0001\u0000\u0000\u0000\u00b6\r\u0001\u0000"+
		"\u0000\u0000\u00b7\u00bc\u0005\u0004\u0000\u0000\u00b8\u00b9\u0005/\u0000"+
		"\u0000\u00b9\u00ba\u0003N\'\u0000\u00ba\u00bb\u0005-\u0000\u0000\u00bb"+
		"\u00bd\u0001\u0000\u0000\u0000\u00bc\u00b8\u0001\u0000\u0000\u0000\u00bc"+
		"\u00bd\u0001\u0000\u0000\u0000\u00bd\u000f\u0001\u0000\u0000\u0000\u00be"+
		"\u00c3\u0005\u0005\u0000\u0000\u00bf\u00c0\u0005/\u0000\u0000\u00c0\u00c1"+
		"\u0003N\'\u0000\u00c1\u00c2\u0005-\u0000\u0000\u00c2\u00c4\u0001\u0000"+
		"\u0000\u0000\u00c3\u00bf\u0001\u0000\u0000\u0000\u00c3\u00c4\u0001\u0000"+
		"\u0000\u0000\u00c4\u0011\u0001\u0000\u0000\u0000\u00c5\u00cc\u0005\u0006"+
		"\u0000\u0000\u00c6\u00c7\u0005/\u0000\u0000\u00c7\u00c8\u0003N\'\u0000"+
		"\u00c8\u00c9\u0005\u0001\u0000\u0000\u00c9\u00ca\u0003N\'\u0000\u00ca"+
		"\u00cb\u0005-\u0000\u0000\u00cb\u00cd\u0001\u0000\u0000\u0000\u00cc\u00c6"+
		"\u0001\u0000\u0000\u0000\u00cc\u00cd\u0001\u0000\u0000\u0000\u00cd\u0013"+
		"\u0001\u0000\u0000\u0000\u00ce\u00d3\u0005J\u0000\u0000\u00cf\u00d0\u0005"+
		"\r\u0000\u0000\u00d0\u00d2\u0005J\u0000\u0000\u00d1\u00cf\u0001\u0000"+
		"\u0000\u0000\u00d2\u00d5\u0001\u0000\u0000\u0000\u00d3\u00d1\u0001\u0000"+
		"\u0000\u0000\u00d3\u00d4\u0001\u0000\u0000\u0000\u00d4\u0015\u0001\u0000"+
		"\u0000\u0000\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d6\u00d8\u00038\u001c"+
		"\u0000\u00d7\u00d9\u0005\"\u0000\u0000\u00d8\u00d7\u0001\u0000\u0000\u0000"+
		"\u00d8\u00d9\u0001\u0000\u0000\u0000\u00d9\u00e4\u0001\u0000\u0000\u0000"+
		"\u00da\u00db\u0003:\u001d\u0000\u00db\u00dc\u0005\"\u0000\u0000\u00dc"+
		"\u00e4\u0001\u0000\u0000\u0000\u00dd\u00df\u0003\u0018\f\u0000\u00de\u00e0"+
		"\u0005\"\u0000\u0000\u00df\u00de\u0001\u0000\u0000\u0000\u00df\u00e0\u0001"+
		"\u0000\u0000\u0000\u00e0\u00e4\u0001\u0000\u0000\u0000\u00e1\u00e4\u0003"+
		"\u001c\u000e\u0000\u00e2\u00e4\u0003<\u001e\u0000\u00e3\u00d6\u0001\u0000"+
		"\u0000\u0000\u00e3\u00da\u0001\u0000\u0000\u0000\u00e3\u00dd\u0001\u0000"+
		"\u0000\u0000\u00e3\u00e1\u0001\u0000\u0000\u0000\u00e3\u00e2\u0001\u0000"+
		"\u0000\u0000\u00e4\u0017\u0001\u0000\u0000\u0000\u00e5\u00e6\u0003F#\u0000"+
		"\u00e6\u0019\u0001\u0000\u0000\u0000\u00e7\u00e8\u0006\r\uffff\uffff\u0000"+
		"\u00e8\u00f7\u0005+\u0000\u0000\u00e9\u00eb\u00038\u001c\u0000\u00ea\u00ec"+
		"\u0005\"\u0000\u0000\u00eb\u00ea\u0001\u0000\u0000\u0000\u00eb\u00ec\u0001"+
		"\u0000\u0000\u0000\u00ec\u00f6\u0001\u0000\u0000\u0000\u00ed\u00ef\u0003"+
		"\u001c\u000e\u0000\u00ee\u00f0\u0005\"\u0000\u0000\u00ef\u00ee\u0001\u0000"+
		"\u0000\u0000\u00ef\u00f0\u0001\u0000\u0000\u0000\u00f0\u00f6\u0001\u0000"+
		"\u0000\u0000\u00f1\u00f3\u0003\u0016\u000b\u0000\u00f2\u00f4\u0005\"\u0000"+
		"\u0000\u00f3\u00f2\u0001\u0000\u0000\u0000\u00f3\u00f4\u0001\u0000\u0000"+
		"\u0000\u00f4\u00f6\u0001\u0000\u0000\u0000\u00f5\u00e9\u0001\u0000\u0000"+
		"\u0000\u00f5\u00ed\u0001\u0000\u0000\u0000\u00f5\u00f1\u0001\u0000\u0000"+
		"\u0000\u00f6\u00f9\u0001\u0000\u0000\u0000\u00f7\u00f5\u0001\u0000\u0000"+
		"\u0000\u00f7\u00f8\u0001\u0000\u0000\u0000\u00f8\u00fa\u0001\u0000\u0000"+
		"\u0000\u00f9\u00f7\u0001\u0000\u0000\u0000\u00fa\u00fb\u0005,\u0000\u0000"+
		"\u00fb\u00fc\u0006\r\uffff\uffff\u0000\u00fc\u001b\u0001\u0000\u0000\u0000"+
		"\u00fd\u0105\u0003\u001e\u000f\u0000\u00fe\u0105\u0003&\u0013\u0000\u00ff"+
		"\u0105\u0003.\u0017\u0000\u0100\u0105\u00030\u0018\u0000\u0101\u0105\u0003"+
		"2\u0019\u0000\u0102\u0105\u00034\u001a\u0000\u0103\u0105\u00036\u001b"+
		"\u0000\u0104\u00fd\u0001\u0000\u0000\u0000\u0104\u00fe\u0001\u0000\u0000"+
		"\u0000\u0104\u00ff\u0001\u0000\u0000\u0000\u0104\u0100\u0001\u0000\u0000"+
		"\u0000\u0104\u0101\u0001\u0000\u0000\u0000\u0104\u0102\u0001\u0000\u0000"+
		"\u0000\u0104\u0103\u0001\u0000\u0000\u0000\u0105\u001d\u0001\u0000\u0000"+
		"\u0000\u0106\u0107\u0005\u0007\u0000\u0000\u0107\u0108\u0005)\u0000\u0000"+
		"\u0108\u0109\u0003*\u0015\u0000\u0109\u010a\u0005*\u0000\u0000\u010a\u010b"+
		"\u0006\u000f\uffff\uffff\u0000\u010b\u010c\u0003\u001a\r\u0000\u010c\u0117"+
		"\u0006\u000f\uffff\uffff\u0000\u010d\u010e\u0005\b\u0000\u0000\u010e\u010f"+
		"\u0005)\u0000\u0000\u010f\u0110\u0003 \u0010\u0000\u0110\u0111\u0005*"+
		"\u0000\u0000\u0111\u0112\u0006\u000f\uffff\uffff\u0000\u0112\u0113\u0003"+
		"\"\u0011\u0000\u0113\u0114\u0006\u000f\uffff\uffff\u0000\u0114\u0116\u0001"+
		"\u0000\u0000\u0000\u0115\u010d\u0001\u0000\u0000\u0000\u0116\u0119\u0001"+
		"\u0000\u0000\u0000\u0117\u0115\u0001\u0000\u0000\u0000\u0117\u0118\u0001"+
		"\u0000\u0000\u0000\u0118\u011f\u0001\u0000\u0000\u0000\u0119\u0117\u0001"+
		"\u0000\u0000\u0000\u011a\u011b\u0005\t\u0000\u0000\u011b\u011c\u0006\u000f"+
		"\uffff\uffff\u0000\u011c\u011d\u0003$\u0012\u0000\u011d\u011e\u0006\u000f"+
		"\uffff\uffff\u0000\u011e\u0120\u0001\u0000\u0000\u0000\u011f\u011a\u0001"+
		"\u0000\u0000\u0000\u011f\u0120\u0001\u0000\u0000\u0000\u0120\u001f\u0001"+
		"\u0000\u0000\u0000\u0121\u0122\u0003:\u001d\u0000\u0122!\u0001\u0000\u0000"+
		"\u0000\u0123\u0124\u0003\u001a\r\u0000\u0124#\u0001\u0000\u0000\u0000"+
		"\u0125\u0126\u0003\u001a\r\u0000\u0126%\u0001\u0000\u0000\u0000\u0127"+
		"\u0128\u0005 \u0000\u0000\u0128\u0129\u0005)\u0000\u0000\u0129\u012c\u0006"+
		"\u0013\uffff\uffff\u0000\u012a\u012d\u00038\u001c\u0000\u012b\u012d\u0003"+
		"(\u0014\u0000\u012c\u012a\u0001\u0000\u0000\u0000\u012c\u012b\u0001\u0000"+
		"\u0000\u0000\u012c\u012d\u0001\u0000\u0000\u0000\u012d\u012e\u0001\u0000"+
		"\u0000\u0000\u012e\u0130\u0005\"\u0000\u0000\u012f\u0131\u0003*\u0015"+
		"\u0000\u0130\u012f\u0001\u0000\u0000\u0000\u0130\u0131\u0001\u0000\u0000"+
		"\u0000\u0131\u0132\u0001\u0000\u0000\u0000\u0132\u0134\u0005\"\u0000\u0000"+
		"\u0133\u0135\u0003,\u0016\u0000\u0134\u0133\u0001\u0000\u0000\u0000\u0134"+
		"\u0135\u0001\u0000\u0000\u0000\u0135\u0136\u0001\u0000\u0000\u0000\u0136"+
		"\u0137\u0005*\u0000\u0000\u0137\u0138\u0003\u001a\r\u0000\u0138\u0139"+
		"\u0006\u0013\uffff\uffff\u0000\u0139\'\u0001\u0000\u0000\u0000\u013a\u013b"+
		"\u0003:\u001d\u0000\u013b)\u0001\u0000\u0000\u0000\u013c\u013d\u0003:"+
		"\u001d\u0000\u013d+\u0001\u0000\u0000\u0000\u013e\u013f\u0003:\u001d\u0000"+
		"\u013f-\u0001\u0000\u0000\u0000\u0140\u0141\u0005\u001f\u0000\u0000\u0141"+
		"\u0142\u0005)\u0000\u0000\u0142\u0143\u0003:\u001d\u0000\u0143\u0144\u0005"+
		"*\u0000\u0000\u0144\u0145\u0006\u0017\uffff\uffff\u0000\u0145\u0146\u0003"+
		"\u001a\r\u0000\u0146\u0147\u0006\u0017\uffff\uffff\u0000\u0147/\u0001"+
		"\u0000\u0000\u0000\u0148\u014a\u0005\u001d\u0000\u0000\u0149\u014b\u0003"+
		":\u001d\u0000\u014a\u0149\u0001\u0000\u0000\u0000\u014a\u014b\u0001\u0000"+
		"\u0000\u0000\u014b\u014c\u0001\u0000\u0000\u0000\u014c\u014d\u0005\"\u0000"+
		"\u0000\u014d1\u0001\u0000\u0000\u0000\u014e\u014f\u0005\u0012\u0000\u0000"+
		"\u014f\u0150\u0005\"\u0000\u0000\u01503\u0001\u0000\u0000\u0000\u0151"+
		"\u0152\u0005\u0011\u0000\u0000\u0152\u0153\u0005\"\u0000\u0000\u01535"+
		"\u0001\u0000\u0000\u0000\u0154\u0155\u0003:\u001d\u0000\u0155\u0156\u0005"+
		"\"\u0000\u0000\u01567\u0001\u0000\u0000\u0000\u0157\u0159\u0003N\'\u0000"+
		"\u0158\u0157\u0001\u0000\u0000\u0000\u0158\u0159\u0001\u0000\u0000\u0000"+
		"\u0159\u015a\u0001\u0000\u0000\u0000\u015a\u015b\u0005J\u0000\u0000\u015b"+
		"\u015d\u0005!\u0000\u0000\u015c\u015e\u0003:\u001d\u0000\u015d\u015c\u0001"+
		"\u0000\u0000\u0000\u015d\u015e\u0001\u0000\u0000\u0000\u015e9\u0001\u0000"+
		"\u0000\u0000\u015f\u0160\u0003>\u001f\u0000\u0160\u0161\u0006\u001d\uffff"+
		"\uffff\u0000\u0161;\u0001\u0000\u0000\u0000\u0162\u0163\u0005\n\u0000"+
		"\u0000\u0163\u0164\u0003:\u001d\u0000\u0164\u0165\u0005*\u0000\u0000\u0165"+
		"\u0166\u0005\"\u0000\u0000\u0166=\u0001\u0000\u0000\u0000\u0167\u016c"+
		"\u0003@ \u0000\u0168\u0169\u0007\u0001\u0000\u0000\u0169\u016b\u0003@"+
		" \u0000\u016a\u0168\u0001\u0000\u0000\u0000\u016b\u016e\u0001\u0000\u0000"+
		"\u0000\u016c\u016a\u0001\u0000\u0000\u0000\u016c\u016d\u0001\u0000\u0000"+
		"\u0000\u016d?\u0001\u0000\u0000\u0000\u016e\u016c\u0001\u0000\u0000\u0000"+
		"\u016f\u0174\u0003B!\u0000\u0170\u0171\u0007\u0002\u0000\u0000\u0171\u0173"+
		"\u0003B!\u0000\u0172\u0170\u0001\u0000\u0000\u0000\u0173\u0176\u0001\u0000"+
		"\u0000\u0000\u0174\u0172\u0001\u0000\u0000\u0000\u0174\u0175\u0001\u0000"+
		"\u0000\u0000\u0175A\u0001\u0000\u0000\u0000\u0176\u0174\u0001\u0000\u0000"+
		"\u0000\u0177\u017c\u0003D\"\u0000\u0178\u0179\u0007\u0003\u0000\u0000"+
		"\u0179\u017b\u0003D\"\u0000\u017a\u0178\u0001\u0000\u0000\u0000\u017b"+
		"\u017e\u0001\u0000\u0000\u0000\u017c\u017a\u0001\u0000\u0000\u0000\u017c"+
		"\u017d\u0001\u0000\u0000\u0000\u017dC\u0001\u0000\u0000\u0000\u017e\u017c"+
		"\u0001\u0000\u0000\u0000\u017f\u0184\u0003P(\u0000\u0180\u0181\u0007\u0004"+
		"\u0000\u0000\u0181\u0183\u0003P(\u0000\u0182\u0180\u0001\u0000\u0000\u0000"+
		"\u0183\u0186\u0001\u0000\u0000\u0000\u0184\u0182\u0001\u0000\u0000\u0000"+
		"\u0184\u0185\u0001\u0000\u0000\u0000\u0185E\u0001\u0000\u0000\u0000\u0186"+
		"\u0184\u0001\u0000\u0000\u0000\u0187\u0188\u0003N\'\u0000\u0188\u0189"+
		"\u0005\u001e\u0000\u0000\u0189\u018a\u0005J\u0000\u0000\u018a\u018c\u0005"+
		")\u0000\u0000\u018b\u018d\u0003H$\u0000\u018c\u018b\u0001\u0000\u0000"+
		"\u0000\u018c\u018d\u0001\u0000\u0000\u0000\u018d\u018e\u0001\u0000\u0000"+
		"\u0000\u018e\u018f\u0005*\u0000\u0000\u018f\u0190\u0006#\uffff\uffff\u0000"+
		"\u0190\u0191\u0003\u001a\r\u0000\u0191\u0192\u0006#\uffff\uffff\u0000"+
		"\u0192G\u0001\u0000\u0000\u0000\u0193\u0198\u0003J%\u0000\u0194\u0195"+
		"\u0005\u0001\u0000\u0000\u0195\u0197\u0003J%\u0000\u0196\u0194\u0001\u0000"+
		"\u0000\u0000\u0197\u019a\u0001\u0000\u0000\u0000\u0198\u0196\u0001\u0000"+
		"\u0000\u0000\u0198\u0199\u0001\u0000\u0000\u0000\u0199I\u0001\u0000\u0000"+
		"\u0000\u019a\u0198\u0001\u0000\u0000\u0000\u019b\u019c\u0003N\'\u0000"+
		"\u019c\u019d\u0005B\u0000\u0000\u019d\u019e\u0003L&\u0000\u019eK\u0001"+
		"\u0000\u0000\u0000\u019f\u01a0\u0005J\u0000\u0000\u01a0M\u0001\u0000\u0000"+
		"\u0000\u01a1\u01a4\u0003n7\u0000\u01a2\u01a4\u0003\u0004\u0002\u0000\u01a3"+
		"\u01a1\u0001\u0000\u0000\u0000\u01a3\u01a2\u0001\u0000\u0000\u0000\u01a4"+
		"O\u0001\u0000\u0000\u0000\u01a5\u01a8\u0003R)\u0000\u01a6\u01a8\u0003"+
		"X,\u0000\u01a7\u01a5\u0001\u0000\u0000\u0000\u01a7\u01a6\u0001\u0000\u0000"+
		"\u0000\u01a8Q\u0001\u0000\u0000\u0000\u01a9\u01b0\u0003f3\u0000\u01aa"+
		"\u01b0\u0005J\u0000\u0000\u01ab\u01ac\u0005)\u0000\u0000\u01ac\u01ad\u0003"+
		":\u001d\u0000\u01ad\u01ae\u0005*\u0000\u0000\u01ae\u01b0\u0001\u0000\u0000"+
		"\u0000\u01af\u01a9\u0001\u0000\u0000\u0000\u01af\u01aa\u0001\u0000\u0000"+
		"\u0000\u01af\u01ab\u0001\u0000\u0000\u0000\u01b0S\u0001\u0000\u0000\u0000"+
		"\u01b1\u01b4\u0003V+\u0000\u01b2\u01b4\u0003:\u001d\u0000\u01b3\u01b1"+
		"\u0001\u0000\u0000\u0000\u01b3\u01b2\u0001\u0000\u0000\u0000\u01b4U\u0001"+
		"\u0000\u0000\u0000\u01b5\u01b6\u0003N\'\u0000\u01b6\u01b7\u0005B\u0000"+
		"\u0000\u01b7\u01b8\u0003:\u001d\u0000\u01b8W\u0001\u0000\u0000\u0000\u01b9"+
		"\u01ba\u0003N\'\u0000\u01ba\u01bb\u0005B\u0000\u0000\u01bb\u01bc\u0005"+
		"B\u0000\u0000\u01bc\u01bd\u0003^/\u0000\u01bd\u01bf\u0005)\u0000\u0000"+
		"\u01be\u01c0\u0003`0\u0000\u01bf\u01be\u0001\u0000\u0000\u0000\u01bf\u01c0"+
		"\u0001\u0000\u0000\u0000\u01c0\u01c1\u0001\u0000\u0000\u0000\u01c1\u01c2"+
		"\u0005*\u0000\u0000\u01c2\u01f1\u0001\u0000\u0000\u0000\u01c3\u01c4\u0005"+
		"\u001a\u0000\u0000\u01c4\u01c5\u0003N\'\u0000\u01c5\u01c7\u0005)\u0000"+
		"\u0000\u01c6\u01c8\u0003`0\u0000\u01c7\u01c6\u0001\u0000\u0000\u0000\u01c7"+
		"\u01c8\u0001\u0000\u0000\u0000\u01c8\u01c9\u0001\u0000\u0000\u0000\u01c9"+
		"\u01ca\u0005*\u0000\u0000\u01ca\u01f1\u0001\u0000\u0000\u0000\u01cb\u01cc"+
		"\u0003R)\u0000\u01cc\u01cd\u0005\r\u0000\u0000\u01cd\u01ce\u0003^/\u0000"+
		"\u01ce\u01d0\u0005)\u0000\u0000\u01cf\u01d1\u0003`0\u0000\u01d0\u01cf"+
		"\u0001\u0000\u0000\u0000\u01d0\u01d1\u0001\u0000\u0000\u0000\u01d1\u01d2"+
		"\u0001\u0000\u0000\u0000\u01d2\u01d3\u0005*\u0000\u0000\u01d3\u01f1\u0001"+
		"\u0000\u0000\u0000\u01d4\u01d5\u0003Z-\u0000\u01d5\u01d6\u0005\r\u0000"+
		"\u0000\u01d6\u01d7\u0003^/\u0000\u01d7\u01d9\u0005)\u0000\u0000\u01d8"+
		"\u01da\u0003`0\u0000\u01d9\u01d8\u0001\u0000\u0000\u0000\u01d9\u01da\u0001"+
		"\u0000\u0000\u0000\u01da\u01db\u0001\u0000\u0000\u0000\u01db\u01dc\u0005"+
		"*\u0000\u0000\u01dc\u01f1\u0001\u0000\u0000\u0000\u01dd\u01de\u0005:\u0000"+
		"\u0000\u01de\u01df\u0005B\u0000\u0000\u01df\u01e0\u0005B\u0000\u0000\u01e0"+
		"\u01e1\u0003^/\u0000\u01e1\u01e3\u0005)\u0000\u0000\u01e2\u01e4\u0003"+
		"`0\u0000\u01e3\u01e2\u0001\u0000\u0000\u0000\u01e3\u01e4\u0001\u0000\u0000"+
		"\u0000\u01e4\u01e5\u0001\u0000\u0000\u0000\u01e5\u01e6\u0005*\u0000\u0000"+
		"\u01e6\u01f1\u0001\u0000\u0000\u0000\u01e7\u01e8\u0003b1\u0000\u01e8\u01e9"+
		"\u0005\r\u0000\u0000\u01e9\u01ea\u0003^/\u0000\u01ea\u01ec\u0005)\u0000"+
		"\u0000\u01eb\u01ed\u0003`0\u0000\u01ec\u01eb\u0001\u0000\u0000\u0000\u01ec"+
		"\u01ed\u0001\u0000\u0000\u0000\u01ed\u01ee\u0001\u0000\u0000\u0000\u01ee"+
		"\u01ef\u0005*\u0000\u0000\u01ef\u01f1\u0001\u0000\u0000\u0000\u01f0\u01b9"+
		"\u0001\u0000\u0000\u0000\u01f0\u01c3\u0001\u0000\u0000\u0000\u01f0\u01cb"+
		"\u0001\u0000\u0000\u0000\u01f0\u01d4\u0001\u0000\u0000\u0000\u01f0\u01dd"+
		"\u0001\u0000\u0000\u0000\u01f0\u01e7\u0001\u0000\u0000\u0000\u01f1Y\u0001"+
		"\u0000\u0000\u0000\u01f2\u01f3\u0005\u0010\u0000\u0000\u01f3[\u0001\u0000"+
		"\u0000\u0000\u01f4\u01f5\u0005J\u0000\u0000\u01f5]\u0001\u0000\u0000\u0000"+
		"\u01f6\u01f7\u0005J\u0000\u0000\u01f7_\u0001\u0000\u0000\u0000\u01f8\u01fd"+
		"\u0003T*\u0000\u01f9\u01fa\u0005\u0001\u0000\u0000\u01fa\u01fc\u0003T"+
		"*\u0000\u01fb\u01f9\u0001\u0000\u0000\u0000\u01fc\u01ff\u0001\u0000\u0000"+
		"\u0000\u01fd\u01fb\u0001\u0000\u0000\u0000\u01fd\u01fe\u0001\u0000\u0000"+
		"\u0000\u01fea\u0001\u0000\u0000\u0000\u01ff\u01fd\u0001\u0000\u0000\u0000"+
		"\u0200\u0201\u0003N\'\u0000\u0201\u0202\u0005\u000b\u0000\u0000\u0202"+
		"\u0203\u0003d2\u0000\u0203c\u0001\u0000\u0000\u0000\u0204\u0205\u0003"+
		"p8\u0000\u0205e\u0001\u0000\u0000\u0000\u0206\u0213\u0003t:\u0000\u0207"+
		"\u0213\u0003z=\u0000\u0208\u0213\u0003v;\u0000\u0209\u0213\u0003r9\u0000"+
		"\u020a\u0213\u0003|>\u0000\u020b\u0213\u0003p8\u0000\u020c\u0213\u0003"+
		"x<\u0000\u020d\u020e\u0003\u0014\n\u0000\u020e\u020f\u0005\f\u0000\u0000"+
		"\u020f\u0213\u0001\u0000\u0000\u0000\u0210\u0213\u0003h4\u0000\u0211\u0213"+
		"\u0003j5\u0000\u0212\u0206\u0001\u0000\u0000\u0000\u0212\u0207\u0001\u0000"+
		"\u0000\u0000\u0212\u0208\u0001\u0000\u0000\u0000\u0212\u0209\u0001\u0000"+
		"\u0000\u0000\u0212\u020a\u0001\u0000\u0000\u0000\u0212\u020b\u0001\u0000"+
		"\u0000\u0000\u0212\u020c\u0001\u0000\u0000\u0000\u0212\u020d\u0001\u0000"+
		"\u0000\u0000\u0212\u0210\u0001\u0000\u0000\u0000\u0212\u0211\u0001\u0000"+
		"\u0000\u0000\u0213g\u0001\u0000\u0000\u0000\u0214\u021d\u0005\u0002\u0000"+
		"\u0000\u0215\u021a\u0003:\u001d\u0000\u0216\u0217\u0005\u0001\u0000\u0000"+
		"\u0217\u0219\u0003:\u001d\u0000\u0218\u0216\u0001\u0000\u0000\u0000\u0219"+
		"\u021c\u0001\u0000\u0000\u0000\u021a\u0218\u0001\u0000\u0000\u0000\u021a"+
		"\u021b\u0001\u0000\u0000\u0000\u021b\u021e\u0001\u0000\u0000\u0000\u021c"+
		"\u021a\u0001\u0000\u0000\u0000\u021d\u0215\u0001\u0000\u0000\u0000\u021d"+
		"\u021e\u0001\u0000\u0000\u0000\u021e\u021f\u0001\u0000\u0000\u0000\u021f"+
		"\u0220\u0005\u0003\u0000\u0000\u0220i\u0001\u0000\u0000\u0000\u0221\u022a"+
		"\u0005+\u0000\u0000\u0222\u0227\u0003l6\u0000\u0223\u0224\u0005\u0001"+
		"\u0000\u0000\u0224\u0226\u0003l6\u0000\u0225\u0223\u0001\u0000\u0000\u0000"+
		"\u0226\u0229\u0001\u0000\u0000\u0000\u0227\u0225\u0001\u0000\u0000\u0000"+
		"\u0227\u0228\u0001\u0000\u0000\u0000\u0228\u022b\u0001\u0000\u0000\u0000"+
		"\u0229\u0227\u0001\u0000\u0000\u0000\u022a\u0222\u0001\u0000\u0000\u0000"+
		"\u022a\u022b\u0001\u0000\u0000\u0000\u022b\u022c\u0001\u0000\u0000\u0000"+
		"\u022c\u022d\u0005,\u0000\u0000\u022dk\u0001\u0000\u0000\u0000\u022e\u022f"+
		"\u0003:\u001d\u0000\u022f\u0230\u0005B\u0000\u0000\u0230\u0231\u0003:"+
		"\u001d\u0000\u0231m\u0001\u0000\u0000\u0000\u0232\u0233\u0005J\u0000\u0000"+
		"\u0233o\u0001\u0000\u0000\u0000\u0234\u0235\u0005J\u0000\u0000\u0235q"+
		"\u0001\u0000\u0000\u0000\u0236\u0237\u0007\u0005\u0000\u0000\u0237s\u0001"+
		"\u0000\u0000\u0000\u0238\u0239\u0005K\u0000\u0000\u0239u\u0001\u0000\u0000"+
		"\u0000\u023a\u023b\u0007\u0006\u0000\u0000\u023bw\u0001\u0000\u0000\u0000"+
		"\u023c\u023d\u0005(\u0000\u0000\u023d\u023e\u0005+\u0000\u0000\u023e\u023f"+
		"\u0005J\u0000\u0000\u023f\u0240\u0005,\u0000\u0000\u0240y\u0001\u0000"+
		"\u0000\u0000\u0241\u0242\u0005C\u0000\u0000\u0242{\u0001\u0000\u0000\u0000"+
		"\u0243\u0244\u0005\u000f\u0000\u0000\u0244}\u0001\u0000\u0000\u00003\u0082"+
		"\u0088\u009a\u009e\u00a8\u00af\u00b5\u00bc\u00c3\u00cc\u00d3\u00d8\u00df"+
		"\u00e3\u00eb\u00ef\u00f3\u00f5\u00f7\u0104\u0117\u011f\u012c\u0130\u0134"+
		"\u014a\u0158\u015d\u016c\u0174\u017c\u0184\u018c\u0198\u01a3\u01a7\u01af"+
		"\u01b3\u01bf\u01c7\u01d0\u01d9\u01e3\u01ec\u01f0\u01fd\u0212\u021a\u021d"+
		"\u0227\u022a";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}