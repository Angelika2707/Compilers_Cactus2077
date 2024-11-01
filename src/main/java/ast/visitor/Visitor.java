package ast.visitor;

import ast.base.Program;
import ast.declaration.Declaration;
import ast.declaration.DeclarationList;
import ast.declaration.TypeDeclaration;
import ast.declaration.VariableDeclaration;
import ast.expression.Expression;
import ast.expression.NestedRecordAccess;
import ast.function.Function;
import ast.function.ParamList;
import ast.function.Parameter;
import ast.statement.Statement;
import ast.statement.StatementList;
import ast.type.Type;

public interface Visitor {
    void visit(Program program);
    void visit(Declaration declaration);
    void visit(DeclarationList declarationList);
    void visit(VariableDeclaration variableDeclaration);
    void visit(TypeDeclaration typeDeclaration);
    void visit(Statement statement);
    void visit(StatementList statementList);
    void visit(Expression expression);
    void visit(NestedRecordAccess nestedRecordAccess);
    void visit(Function function);
    void visit(Parameter parameter);
    void visit(ParamList paramList);
    void visit(Type type);
}
