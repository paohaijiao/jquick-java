grammar JQuickJava;
options { output=AST;  language=Java; }
@header{
package com.github.paohaijiao.parser;
import java.util.*;
import java.util.Class;
import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.factory.JQuickJavaFunctionRegistry;
import com.github.paohaijiao.model.JQuickJavaFunctionDefinitionModel;
}
@parser::members {

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
}
program :{start();} importDeclaration* statement* EOF {exitScope();};
importDeclaration: IMPORT paramType AS importVar  SEMICOLON;
paramType:
    simpleType
    | genericType
    | listType
    | mapType
    | setType
    | arrayType
    ;
genericType:qualifiedName typeArguments?;
simpleType:
    TYPESHORT
    | TYPEINT
    | TYPEFLOAT
    | TYPEDOUBLE
    | TYPELONG
    | TYPEBOOLEAN
    | TYPEBYTE
    ;
typeArguments:
    '<' classsType (',' classsType)* '>';
arrayType:
    (simpleType | qualifiedName) ('[' ']')+
    ;
listType:
     'List' ('<' classsType '>')?
    ;
setType:
    'Set' ('<' classsType '>')?
        ;
mapType:
    'Map' ('<' classsType ',' classsType '>')?
    ;

qualifiedName:IDENTIFIER  (DOT  IDENTIFIER )*;
statement:
   expression SEMICOLON
    | method SEMICOLON?
    | controlStatement
    | sout
    ;

method:
    methodInvocation|functionDefinition;


action :
    {enterScope();}  // 进入块作用域
    LBRACE
    ( variableDecl SEMICOLON?
    | controlStatement   SEMICOLON?
    | statement     SEMICOLON?
    )*
    RBRACE
    {exitScope();};  // 退出块作用域
controlStatement:
    ifStatement
    | forStatement
    | whileStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | expressionStatement;//4
ifStatement :
         'if' LPAREN  conExpression RPAREN {enterScope();} action {exitScope();} ('else if' LPAREN elseIfConExpression RPAREN {enterScope();} elseIfAction {exitScope();})* ('else' {enterScope();} elseAction {exitScope();})?;
elseIfConExpression:expression;
elseIfAction:action;
elseAction:action;
forStatement :
    FOR LPAREN
    {enterScope();}
    (variableDecl | initExpression)? SEMICOLON conExpression? SEMICOLON stopExpression? RPAREN action  {exitScope();};
initExpression:expression;
conExpression:expression;
stopExpression:expression;
whileStatement :
    WHILE LPAREN expression RPAREN  {enterScope();} action {exitScope();};
returnStatement:
    RETURN expression? SEMICOLON;
breakStatement:
    BREAK SEMICOLON;
continueStatement:
    CONTINUE SEMICOLON;
expressionStatement:
    expression SEMICOLON;
variableDecl returns [String varName, Object varValue]
    :  ct=classsType? id=IDENTIFIER ASSIGN expr=expression?
    ;
expression returns [Object value]
    : m=methodInvocation { $value = $m.value; }
    | a=additive { $value = $a.value; }
    | l=logical { $value = $l.value; }
    | p=primary { $value = $p.value; }
    ;//3
sout:
 'console.log(' expression ')'SEMICOLON;
logical returns [Object value]:
    comparison (op=(OR|AND )comparison)*;

comparison returns [Object value]:
    additive (op=(GT | GE | LT | LE|EQ | NE) additive)*;

additive returns [Object value]:
    multiplicative ((ADD | MINUS) multiplicative)*;

multiplicative returns [Object value]:
    primary ((MUL | DIV) primary)*;


 functionDefinition :
   classsType DEF IDENTIFIER
       LPAREN parameterList? RPAREN
       {enterScope();}
       action
       {exitScope();};
parameterList:
    param (',' param)*;
param:classsType COLON functionVar;
functionVar:IDENTIFIER;
classsType:importVar|paramType;
primary returns [Object value]://2
    primaryAtom
    | methodInvocation
    ;
primaryAtom returns [Object value]
    : literal
    | IDENTIFIER
    | LPAREN expression RPAREN
    | variableDecl
    ;
argument
    : typedArgument
    | expression
    ;
typedArgument
    : classsType COLON expression
    ;
methodInvocation returns [Object value]:
    classsType  COLON COLON methodName LPAREN argumentList? RPAREN    #staticCall
    | NEW classsType   LPAREN argumentList? RPAREN                    #constructorCall
    | primaryAtom '.' methodName LPAREN argumentList? RPAREN          #instanceMethodCall
    | this '.' methodName  LPAREN argumentList? RPAREN                #thisMethodCall
    | BUILTIN COLON COLON methodName  LPAREN argumentList? RPAREN     #builtinMethodCall
    | accessStaticVariable '.' methodName LPAREN argumentList? RPAREN #accessStaticMethodCall
    ;
this:THIS;
instanceName:IDENTIFIER;
methodName:IDENTIFIER;
argumentList: argument (',' argument)*;
accessStaticVariable: classsType '@' accessObjectName;
accessObjectName:identifier;
literal returns [Object value]//1
    : string
    | number
    | date
    | bool
    | null
    | identifier
    | variables
    | qualifiedName  '.class'
    | listLiteral
    | mapLiteral
    ;
listLiteral returns [List<Object> value]
    : '[' (expression (',' expression)*)? ']'
    ;
mapLiteral  returns [Map<Object, Object> value]
    : '{' (mapEntry (',' mapEntry)*)? '}'
    ;
mapEntry returns [Object key, Object value]
    : expression ':' expression
    ;
importVar: IDENTIFIER;
identifier: IDENTIFIER;
bool: TRUE | FALSE;
string: STRING;
date: DATE | DATETIME;
variables: DOLLAR LBRACE IDENTIFIER RBRACE;
number:  NUMBERIC;
null: TYPENULL;
DOT : '.';
TYPESHORT : 'short';
TYPENULL : 'null';
THIS : 'this';
CONTINUE : 'continue';
BREAK : 'break';
TYPEINT : 'int';
TYPEFLOAT : 'float';
TYPEDOUBLE : 'double';
TYPELONG : 'long';
TYPEBOOLEAN : 'boolean';
TYPEBYTE : 'byte';
IMPORT : 'import';
NEW : 'new';
VAR : 'var';
AS : 'as';
RETURN : 'return';
DEF : 'def';
WHILE : 'while';
FOR : 'for';
ASSIGN : '=';
SEMICOLON : ';';
WITH : 'WITH';
IF : 'IF';
THEN : 'THEN';
ELSEIF : 'ELSEIF';
ELSE : 'ELSE';
DOLLAR: '$';
LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
GT : '>';
GE : '>=';
LT : '<';
LE : '<=';
EQ : '==';
NE : '!=';
AND : '&&';
OR : '||';
ADD : '+';
MINUS : '-';
MUL : '*';
DIV : '/';
CONTAIN : 'CONTAIN';
BUILTIN : 'Builtin';
NOTCONTAIN : 'NOTCONTAIN';
START : 'START';
NOTSTART : 'NOTSTART';
END : 'END';
NOTEND : 'NOTEND';
TRUE: 'true';
FALSE : 'false';
COLON : ':';
NUMBERIC: INT|NUMBER;
DATETIME: DATE ' ' TIME_ZONE;
DATE: YEAR '-' MONTH '-' DAY;
YEAR: [0-9][0-9][0-9][0-9];
MONTH: [0-9][0-9];
DAY: [0-9][0-9];
TIME_ZONE: [0-9][0-9]':'[0-9][0-9]':'[0-9][0-9];

fragment INT: [0-9]+;
fragment NUMBER: [0-9]+ '.' [0-9]* | '.' [0-9]+;

IDENTIFIER
    : [a-zA-Z_] [a-zA-Z0-9_]*
    ;
STRING
    : '"' ( ~["\r\n] )* '"'
    | '\'' ( ~['\r\n] )* '\''
    ;
fragment ESC: '\\' (["\\/bfnrt] | UNICODE);
fragment UNICODE: 'u' HEX HEX HEX HEX;
fragment HEX: [0-9a-fA-F];

SINGLE_LINE_COMMENT: '//' ~[\r\n]* -> skip;
MULTI_LINE_COMMENT: '/*' .*? '*/' -> skip;
WS : [ \t\r\n]+ -> channel(HIDDEN);
NEWLINE:'\r'? '\n';