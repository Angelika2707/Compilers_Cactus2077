package ast.visitor;

import ast.base.Program;
import ast.declaration.TypeDeclaration;
import ast.declaration.VariableDeclaration;
import ast.expression.*;
import ast.function.Function;
import ast.function.Parameter;
import ast.statement.*;
import ast.type.*;

public interface Visitor {
    // base
    void visit(Program program);
    // declaration
    void visit(VariableDeclaration variableDeclaration);
    void visit(TypeDeclaration typeDeclaration);
    // statement
    void visit(AssignmentStatement assignmentStatement);
    void visit(CallStatement callStatement);
    void visit(ForStatement forStatement);
    void visit(IfStatement ifStatement);
    void visit(ReturnStatement returnStatement);
    void visit(WhileStatement whileStatement);
    // expression
    void visit(NestedRecordAccess nestedRecordAccess);
    void visit(AndExpression andExpression);
    void visit(ArrayAccessExpression arrayAccessExpression);
    void visit(BinaryExpression binaryExpression);
    void visit(BooleanLiteral booleanLiteral);
    void visit(DivExpression divExpression);
    void visit(EqualExpression equalExpression);
    void visit(FunctionCallExpression functionCallExpression);
    void visit(GreaterEqualExpression greaterEqualExpression);
    void visit(GreaterThanExpression greaterThanExpression);
    void visit(IntegerLiteral integerLiteral);
    void visit(LessEqualExpression lessEqualExpression);
    void visit(LessThanExpression lessThanExpression);
    void visit(MinusExpression minusExpression);
    void visit(ModExpression modExpression);
    void visit(MulExpression mulExpression);
    void visit(NotEqualExpression notEqualExpression);
    void visit(NotExpression notExpression);
    void visit(OrExpression orExpression);
    void visit(ParenthesizedExpression parenthesizedExpression);
    void visit(PlusExpression plusExpression);
    void visit(RealLiteral realLiteral);
    void visit(XorExpression xorExpression);
    // function
    void visit(Function function);
    void visit(Parameter parameter);
    // type
    void visit(ArrayType arrayType);
    void visit(BooleanType booleanType);
    void visit(IdentifierType identifierType);
    void visit(IntegerType integerType);
    void visit(RealType realType);
    void visit(RecordType recordType);
}
