// Generated from D:/my/jthornruleGrammer/QuickJava/JQuickJava.g4 by ANTLR 4.13.2

package com.github.paohaijiao.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JQuickJavaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JQuickJavaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(JQuickJavaParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDeclaration(JQuickJavaParser.ImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#paramType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamType(JQuickJavaParser.ParamTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#genericType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGenericType(JQuickJavaParser.GenericTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#simpleType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleType(JQuickJavaParser.SimpleTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#typeArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArguments(JQuickJavaParser.TypeArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(JQuickJavaParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#listType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListType(JQuickJavaParser.ListTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#setType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetType(JQuickJavaParser.SetTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#mapType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapType(JQuickJavaParser.MapTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(JQuickJavaParser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(JQuickJavaParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod(JQuickJavaParser.MethodContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#action}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAction(JQuickJavaParser.ActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitControlStatement(JQuickJavaParser.ControlStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(JQuickJavaParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#elseIfConExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseIfConExpression(JQuickJavaParser.ElseIfConExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#elseIfAction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseIfAction(JQuickJavaParser.ElseIfActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#elseAction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseAction(JQuickJavaParser.ElseActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(JQuickJavaParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#initExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitExpression(JQuickJavaParser.InitExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#conExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConExpression(JQuickJavaParser.ConExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#stopExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStopExpression(JQuickJavaParser.StopExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(JQuickJavaParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(JQuickJavaParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(JQuickJavaParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(JQuickJavaParser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(JQuickJavaParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#variableDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDecl(JQuickJavaParser.VariableDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(JQuickJavaParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#sout}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSout(JQuickJavaParser.SoutContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#logical}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical(JQuickJavaParser.LogicalContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(JQuickJavaParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#arithmetic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic(JQuickJavaParser.ArithmeticContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(JQuickJavaParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(JQuickJavaParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(JQuickJavaParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#functionVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionVar(JQuickJavaParser.FunctionVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#classsType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClasssType(JQuickJavaParser.ClasssTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(JQuickJavaParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#literalItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralItem(JQuickJavaParser.LiteralItemContext ctx);
	/**
	 * Visit a parse tree produced by the {@code staticCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStaticCall(JQuickJavaParser.StaticCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code constructorCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorCall(JQuickJavaParser.ConstructorCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code instanceMethodCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceMethodCall(JQuickJavaParser.InstanceMethodCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code thisMethodCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisMethodCall(JQuickJavaParser.ThisMethodCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code accessStaticMethodCall}
	 * labeled alternative in {@link JQuickJavaParser#methodInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessStaticMethodCall(JQuickJavaParser.AccessStaticMethodCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#this}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThis(JQuickJavaParser.ThisContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#instanceName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceName(JQuickJavaParser.InstanceNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#methodName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodName(JQuickJavaParser.MethodNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(JQuickJavaParser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#accessStaticVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessStaticVariable(JQuickJavaParser.AccessStaticVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#accessObjectName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessObjectName(JQuickJavaParser.AccessObjectNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(JQuickJavaParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#listLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListLiteral(JQuickJavaParser.ListLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#mapLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapLiteral(JQuickJavaParser.MapLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#mapEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapEntry(JQuickJavaParser.MapEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#importVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportVar(JQuickJavaParser.ImportVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(JQuickJavaParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(JQuickJavaParser.BoolContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(JQuickJavaParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#date}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate(JQuickJavaParser.DateContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#variables}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariables(JQuickJavaParser.VariablesContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(JQuickJavaParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickJavaParser#null}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNull(JQuickJavaParser.NullContext ctx);
}