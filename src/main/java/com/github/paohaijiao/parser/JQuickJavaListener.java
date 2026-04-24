// Generated from D:/idea/jthornruleGrammer/QuickJava/JQuickJava.g4 by ANTLR 4.13.2

package com.github.paohaijiao.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JQuickJavaParser}.
 */
public interface JQuickJavaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(JQuickJavaParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(JQuickJavaParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterImportDeclaration(JQuickJavaParser.ImportDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitImportDeclaration(JQuickJavaParser.ImportDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#paramType}.
	 * @param ctx the parse tree
	 */
	void enterParamType(JQuickJavaParser.ParamTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#paramType}.
	 * @param ctx the parse tree
	 */
	void exitParamType(JQuickJavaParser.ParamTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#genericType}.
	 * @param ctx the parse tree
	 */
	void enterGenericType(JQuickJavaParser.GenericTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#genericType}.
	 * @param ctx the parse tree
	 */
	void exitGenericType(JQuickJavaParser.GenericTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#simpleType}.
	 * @param ctx the parse tree
	 */
	void enterSimpleType(JQuickJavaParser.SimpleTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#simpleType}.
	 * @param ctx the parse tree
	 */
	void exitSimpleType(JQuickJavaParser.SimpleTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void enterTypeArguments(JQuickJavaParser.TypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void exitTypeArguments(JQuickJavaParser.TypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#arrayType}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(JQuickJavaParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#arrayType}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(JQuickJavaParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#listType}.
	 * @param ctx the parse tree
	 */
	void enterListType(JQuickJavaParser.ListTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#listType}.
	 * @param ctx the parse tree
	 */
	void exitListType(JQuickJavaParser.ListTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#setType}.
	 * @param ctx the parse tree
	 */
	void enterSetType(JQuickJavaParser.SetTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#setType}.
	 * @param ctx the parse tree
	 */
	void exitSetType(JQuickJavaParser.SetTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#mapType}.
	 * @param ctx the parse tree
	 */
	void enterMapType(JQuickJavaParser.MapTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#mapType}.
	 * @param ctx the parse tree
	 */
	void exitMapType(JQuickJavaParser.MapTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(JQuickJavaParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(JQuickJavaParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(JQuickJavaParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(JQuickJavaParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#method}.
	 * @param ctx the parse tree
	 */
	void enterMethod(JQuickJavaParser.MethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#method}.
	 * @param ctx the parse tree
	 */
	void exitMethod(JQuickJavaParser.MethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#action}.
	 * @param ctx the parse tree
	 */
	void enterAction(JQuickJavaParser.ActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#action}.
	 * @param ctx the parse tree
	 */
	void exitAction(JQuickJavaParser.ActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterControlStatement(JQuickJavaParser.ControlStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitControlStatement(JQuickJavaParser.ControlStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(JQuickJavaParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(JQuickJavaParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#elseIfConExpression}.
	 * @param ctx the parse tree
	 */
	void enterElseIfConExpression(JQuickJavaParser.ElseIfConExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#elseIfConExpression}.
	 * @param ctx the parse tree
	 */
	void exitElseIfConExpression(JQuickJavaParser.ElseIfConExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#elseIfAction}.
	 * @param ctx the parse tree
	 */
	void enterElseIfAction(JQuickJavaParser.ElseIfActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#elseIfAction}.
	 * @param ctx the parse tree
	 */
	void exitElseIfAction(JQuickJavaParser.ElseIfActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#elseAction}.
	 * @param ctx the parse tree
	 */
	void enterElseAction(JQuickJavaParser.ElseActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#elseAction}.
	 * @param ctx the parse tree
	 */
	void exitElseAction(JQuickJavaParser.ElseActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(JQuickJavaParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(JQuickJavaParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#initExpression}.
	 * @param ctx the parse tree
	 */
	void enterInitExpression(JQuickJavaParser.InitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#initExpression}.
	 * @param ctx the parse tree
	 */
	void exitInitExpression(JQuickJavaParser.InitExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#conExpression}.
	 * @param ctx the parse tree
	 */
	void enterConExpression(JQuickJavaParser.ConExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#conExpression}.
	 * @param ctx the parse tree
	 */
	void exitConExpression(JQuickJavaParser.ConExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#stopExpression}.
	 * @param ctx the parse tree
	 */
	void enterStopExpression(JQuickJavaParser.StopExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#stopExpression}.
	 * @param ctx the parse tree
	 */
	void exitStopExpression(JQuickJavaParser.StopExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(JQuickJavaParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(JQuickJavaParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(JQuickJavaParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(JQuickJavaParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(JQuickJavaParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(JQuickJavaParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(JQuickJavaParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(JQuickJavaParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(JQuickJavaParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(JQuickJavaParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#variableDecl}.
	 * @param ctx the parse tree
	 */
	void enterVariableDecl(JQuickJavaParser.VariableDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#variableDecl}.
	 * @param ctx the parse tree
	 */
	void exitVariableDecl(JQuickJavaParser.VariableDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(JQuickJavaParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(JQuickJavaParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#sout}.
	 * @param ctx the parse tree
	 */
	void enterSout(JQuickJavaParser.SoutContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#sout}.
	 * @param ctx the parse tree
	 */
	void exitSout(JQuickJavaParser.SoutContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#logical}.
	 * @param ctx the parse tree
	 */
	void enterLogical(JQuickJavaParser.LogicalContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#logical}.
	 * @param ctx the parse tree
	 */
	void exitLogical(JQuickJavaParser.LogicalContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(JQuickJavaParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(JQuickJavaParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic(JQuickJavaParser.ArithmeticContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic(JQuickJavaParser.ArithmeticContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(JQuickJavaParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(JQuickJavaParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(JQuickJavaParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(JQuickJavaParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(JQuickJavaParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(JQuickJavaParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#functionVar}.
	 * @param ctx the parse tree
	 */
	void enterFunctionVar(JQuickJavaParser.FunctionVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#functionVar}.
	 * @param ctx the parse tree
	 */
	void exitFunctionVar(JQuickJavaParser.FunctionVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#classsType}.
	 * @param ctx the parse tree
	 */
	void enterClasssType(JQuickJavaParser.ClasssTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#classsType}.
	 * @param ctx the parse tree
	 */
	void exitClasssType(JQuickJavaParser.ClasssTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(JQuickJavaParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(JQuickJavaParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#literalItem}.
	 * @param ctx the parse tree
	 */
	void enterLiteralItem(JQuickJavaParser.LiteralItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#literalItem}.
	 * @param ctx the parse tree
	 */
	void exitLiteralItem(JQuickJavaParser.LiteralItemContext ctx);
	/**
	 * Enter a parse tree produced by the {@code staticCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void enterStaticCall(JQuickJavaParser.StaticCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code staticCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void exitStaticCall(JQuickJavaParser.StaticCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constructorCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void enterConstructorCall(JQuickJavaParser.ConstructorCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constructorCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void exitConstructorCall(JQuickJavaParser.ConstructorCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code instanceMethodCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void enterInstanceMethodCall(JQuickJavaParser.InstanceMethodCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code instanceMethodCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void exitInstanceMethodCall(JQuickJavaParser.InstanceMethodCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thisMethodCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void enterThisMethodCall(JQuickJavaParser.ThisMethodCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisMethodCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void exitThisMethodCall(JQuickJavaParser.ThisMethodCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code builtinMethodCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void enterBuiltinMethodCall(JQuickJavaParser.BuiltinMethodCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code builtinMethodCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void exitBuiltinMethodCall(JQuickJavaParser.BuiltinMethodCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code accessStaticMethodCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void enterAccessStaticMethodCall(JQuickJavaParser.AccessStaticMethodCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code accessStaticMethodCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 */
	void exitAccessStaticMethodCall(JQuickJavaParser.AccessStaticMethodCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#this}.
	 * @param ctx the parse tree
	 */
	void enterThis(JQuickJavaParser.ThisContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#this}.
	 * @param ctx the parse tree
	 */
	void exitThis(JQuickJavaParser.ThisContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#instanceName}.
	 * @param ctx the parse tree
	 */
	void enterInstanceName(JQuickJavaParser.InstanceNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#instanceName}.
	 * @param ctx the parse tree
	 */
	void exitInstanceName(JQuickJavaParser.InstanceNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#methodName}.
	 * @param ctx the parse tree
	 */
	void enterMethodName(JQuickJavaParser.MethodNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#methodName}.
	 * @param ctx the parse tree
	 */
	void exitMethodName(JQuickJavaParser.MethodNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(JQuickJavaParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(JQuickJavaParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#accessStaticVariable}.
	 * @param ctx the parse tree
	 */
	void enterAccessStaticVariable(JQuickJavaParser.AccessStaticVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#accessStaticVariable}.
	 * @param ctx the parse tree
	 */
	void exitAccessStaticVariable(JQuickJavaParser.AccessStaticVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#accessObjectName}.
	 * @param ctx the parse tree
	 */
	void enterAccessObjectName(JQuickJavaParser.AccessObjectNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#accessObjectName}.
	 * @param ctx the parse tree
	 */
	void exitAccessObjectName(JQuickJavaParser.AccessObjectNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(JQuickJavaParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(JQuickJavaParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListLiteral(JQuickJavaParser.ListLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListLiteral(JQuickJavaParser.ListLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#mapLiteral}.
	 * @param ctx the parse tree
	 */
	void enterMapLiteral(JQuickJavaParser.MapLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#mapLiteral}.
	 * @param ctx the parse tree
	 */
	void exitMapLiteral(JQuickJavaParser.MapLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#mapEntry}.
	 * @param ctx the parse tree
	 */
	void enterMapEntry(JQuickJavaParser.MapEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#mapEntry}.
	 * @param ctx the parse tree
	 */
	void exitMapEntry(JQuickJavaParser.MapEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#importVar}.
	 * @param ctx the parse tree
	 */
	void enterImportVar(JQuickJavaParser.ImportVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#importVar}.
	 * @param ctx the parse tree
	 */
	void exitImportVar(JQuickJavaParser.ImportVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(JQuickJavaParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(JQuickJavaParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterBool(JQuickJavaParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitBool(JQuickJavaParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(JQuickJavaParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(JQuickJavaParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#date}.
	 * @param ctx the parse tree
	 */
	void enterDate(JQuickJavaParser.DateContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#date}.
	 * @param ctx the parse tree
	 */
	void exitDate(JQuickJavaParser.DateContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#variables}.
	 * @param ctx the parse tree
	 */
	void enterVariables(JQuickJavaParser.VariablesContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#variables}.
	 * @param ctx the parse tree
	 */
	void exitVariables(JQuickJavaParser.VariablesContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(JQuickJavaParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(JQuickJavaParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickJavaParser#null}.
	 * @param ctx the parse tree
	 */
	void enterNull(JQuickJavaParser.NullContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickJavaParser#null}.
	 * @param ctx the parse tree
	 */
	void exitNull(JQuickJavaParser.NullContext ctx);
}