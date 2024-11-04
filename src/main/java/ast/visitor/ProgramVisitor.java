package ast.visitor;

import ast.base.Program;
import ast.base.ProgramUnit;
import ast.declaration.Declaration;
import ast.declaration.TypeDeclaration;
import ast.declaration.VariableDeclaration;
import ast.expression.*;
import ast.function.Function;
import ast.function.Parameter;
import ast.statement.*;
import ast.type.*;

import java.util.List;

public class ProgramVisitor implements Visitor {
    private int indentLevel = 0;

    private void printIndented(String text) {
        for (int i = 0; i < indentLevel; i++) {
            System.out.print("  "); // Два пробела на каждый уровень отступа
        }
        System.out.println(text);
    }

    private void increaseIndent(Runnable block) {
        indentLevel++;
        block.run();
        indentLevel--;
    }

    @Override
    public void visit(Program program) {
        printIndented("Program:");
        increaseIndent(() -> {
            for (ProgramUnit unit : program.units()) {
                unit.accept(this);
            }
        });
    }

    @Override
    public void visit(VariableDeclaration variableDeclaration) {
        printIndented("VariableDeclaration: " + variableDeclaration.id());
        increaseIndent(() -> {
            if (variableDeclaration.type() != null) {
                variableDeclaration.type().accept(this);
            }
            if (variableDeclaration.expression() != null) {
                variableDeclaration.expression().accept(this);
            }
        });
    }

    @Override
    public void visit(TypeDeclaration typeDeclaration) {
        printIndented("TypeDeclaration: " + typeDeclaration.id());
        increaseIndent(() -> {
            typeDeclaration.type().accept(this);
        });
    }

    @Override
    public void visit(AssignmentStatement assignmentStatement) {
        printIndented("AssignmentStatement:");
        increaseIndent(() -> {
            assignmentStatement.expression().accept(this);
            if (assignmentStatement.index() != null)
                assignmentStatement.index().accept(this);
            if (assignmentStatement.recordField() != null)
                assignmentStatement.recordField().accept(this);
        });
    }

    @Override
    public void visit(CallStatement callStatement) {
        printIndented("CallStatement: " + callStatement.identifier());
        increaseIndent(() -> {
            for (Expression argument : callStatement.paramList()) {
                argument.accept(this);
            }
        });
    }

    @Override
    public void visit(ForStatement forStatement) {
        printIndented("ForStatement: " + forStatement.loopVariable());
        increaseIndent(() -> {
            forStatement.startExpression().accept(this);
            forStatement.endExpression().accept(this);
            for (Declaration declaration : forStatement.declarations()) {
                declaration.accept(this);
            }
            for (Statement statement : forStatement.statements()) {
                statement.accept(this);
            }
        });
    }

    @Override
    public void visit(IfStatement ifStatement) {
        printIndented("IfStatement:");
        increaseIndent(() -> {
            ifStatement.condition().accept(this);
            for (Statement statement : ifStatement.thenStatements()) {
                statement.accept(this);
            }

            for (Declaration declaration : ifStatement.thenDeclarations()) {
                declaration.accept(this);
            }

            if (!ifStatement.elseStatements().isEmpty() || !ifStatement.elseDeclarations().isEmpty()) {
                printIndented("Else:");
                increaseIndent(() -> {
                    for (Declaration declaration : ifStatement.elseDeclarations()) {
                        declaration.accept(this);
                    }
                    for (Statement statement : ifStatement.elseStatements()) {
                        statement.accept(this);
                    }
                });
            }
        });
    }

    @Override
    public void visit(ReturnStatement returnStatement) {
        printIndented("ReturnStatement:");
        increaseIndent(() -> {
            returnStatement.returnExpression().accept(this);
        });
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        printIndented("WhileStatement:");
        increaseIndent(() -> {
            whileStatement.condition().accept(this);
            for (Statement statement : whileStatement.statements()) {
                statement.accept(this);
            }
            for (Declaration declarations : whileStatement.declarations()) {
                declarations.accept(this);
            }
        });
    }

    @Override
    public void visit(NestedRecordAccess nestedRecordAccess) {
        printIndented("NestedRecordAccess:");
        increaseIndent(() -> {
            printIndented("Access:");
            List<String> path = nestedRecordAccess.getAccessPath();
            for (int i = 0; i < path.size(); i++) {
                if (i == path.size() - 1) printIndented(path.get(i));
                else printIndented(path.get(i) + ".");
            }
        });
    }

    // Expressions
    @Override
    public void visit(AndExpression andExpression) {
        printIndented("AndExpression:");
        increaseIndent(() -> {
            andExpression.left().accept(this);
            andExpression.right().accept(this);
        });
    }

    @Override
    public void visit(ArrayAccessExpression arrayAccessExpression) {
        printIndented("ArrayAccessExpression:");
        increaseIndent(() -> {
            printIndented("Identifier:" + arrayAccessExpression.identifier());
            arrayAccessExpression.index().accept(this);
        });
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        printIndented("BinaryExpression:");
        increaseIndent(() -> {
            binaryExpression.left().accept(this);
            binaryExpression.right().accept(this);
        });
    }

    @Override
    public void visit(BooleanLiteral booleanLiteral) {
        printIndented("BooleanLiteral: " + booleanLiteral.value());
    }

    @Override
    public void visit(DivExpression divExpression) {
        printIndented("DivExpression:");
        increaseIndent(() -> {
            divExpression.left().accept(this);
            divExpression.right().accept(this);
        });
    }

    @Override
    public void visit(EqualExpression equalExpression) {
        printIndented("EqualExpression:");
        increaseIndent(() -> {
            equalExpression.left().accept(this);
            equalExpression.right().accept(this);
        });
    }

    @Override
    public void visit(FunctionCallExpression functionCallExpression) {
        printIndented("FunctionCallExpression: " + functionCallExpression.functionName());
        increaseIndent(() -> {
            for (Expression argument : functionCallExpression.parameters()) {
                argument.accept(this);
            }
        });
    }

    @Override
    public void visit(GreaterEqualExpression greaterEqualExpression) {
        printIndented("GreaterEqualExpression:");
        increaseIndent(() -> {
            greaterEqualExpression.left().accept(this);
            greaterEqualExpression.right().accept(this);
        });
    }

    @Override
    public void visit(GreaterThanExpression greaterThanExpression) {
        printIndented("GreaterThanExpression:");
        increaseIndent(() -> {
            greaterThanExpression.left().accept(this);
            greaterThanExpression.right().accept(this);
        });
    }

    @Override
    public void visit(IntegerLiteral integerLiteral) {
        printIndented("IntegerLiteral: " + integerLiteral.value());
    }

    @Override
    public void visit(LessEqualExpression lessEqualExpression) {
        printIndented("LessEqualExpression:");
        increaseIndent(() -> {
            lessEqualExpression.left().accept(this);
            lessEqualExpression.right().accept(this);
        });
    }

    @Override
    public void visit(LessThanExpression lessThanExpression) {
        printIndented("LessThanExpression:");
        increaseIndent(() -> {
            lessThanExpression.left().accept(this);
            lessThanExpression.right().accept(this);
        });
    }

    @Override
    public void visit(MinusExpression minusExpression) {
        printIndented("MinusExpression:");
        increaseIndent(() -> {
            minusExpression.left().accept(this);
            minusExpression.right().accept(this);
        });
    }

    @Override
    public void visit(ModExpression modExpression) {
        printIndented("ModExpression:");
        increaseIndent(() -> {
            modExpression.left().accept(this);
            modExpression.right().accept(this);
        });
    }

    @Override
    public void visit(MulExpression mulExpression) {
        printIndented("MulExpression:");
        increaseIndent(() -> {
            mulExpression.left().accept(this);
            mulExpression.right().accept(this);
        });
    }

    @Override
    public void visit(NotEqualExpression notEqualExpression) {
        printIndented("NotEqualExpression:");
        increaseIndent(() -> {
            notEqualExpression.left().accept(this);
            notEqualExpression.right().accept(this);
        });
    }

    @Override
    public void visit(NotExpression notExpression) {
        printIndented("NotExpression:");
        increaseIndent(() -> {
            notExpression.expr().accept(this);
        });
    }

    @Override
    public void visit(OrExpression orExpression) {
        printIndented("OrExpression:");
        increaseIndent(() -> {
            orExpression.left().accept(this);
            orExpression.right().accept(this);
        });
    }

    @Override
    public void visit(ParenthesizedExpression parenthesizedExpression) {
        printIndented("ParenthesizedExpression:");
        increaseIndent(() -> {
            parenthesizedExpression.expr().accept(this);
        });
    }

    @Override
    public void visit(PlusExpression plusExpression) {
        printIndented("PlusExpression:");
        increaseIndent(() -> {
            plusExpression.left().accept(this);
            plusExpression.right().accept(this);
        });
    }

    @Override
    public void visit(RealLiteral realLiteral) {
        printIndented("RealLiteral: " + realLiteral.value());
    }

    @Override
    public void visit(XorExpression xorExpression) {
        printIndented("XorExpression:");
        increaseIndent(() -> {
            xorExpression.left().accept(this);
            xorExpression.right().accept(this);
        });
    }

    // Function-related nodes
    @Override
    public void visit(Function function) {
        printIndented("Function: " + function.identifier());
        increaseIndent(() -> {
            for (Parameter parameter : function.params()) {
                parameter.accept(this);
            }
            function.returnType().accept(this);
            for (Statement statement : function.getBody()) {
                statement.accept(this);
            }
        });
    }

    @Override
    public void visit(Parameter parameter) {
        printIndented("Parameter: " + parameter.getName());
        increaseIndent(() -> {
            parameter.getType().accept(this);
        });
    }

    // Type nodes
    @Override
    public void visit(ArrayType arrayType) {
        printIndented("ArrayType:");
        increaseIndent(() -> {
            arrayType.getElementType().accept(this);
        });
    }

    @Override
    public void visit(BooleanType booleanType) {
        printIndented("BooleanType");
    }

    @Override
    public void visit(IdentifierType identifierType) {
        printIndented("IdentifierType: " + identifierType.getName());
    }

    @Override
    public void visit(IntegerType integerType) {
        printIndented("IntegerType");
    }

    @Override
    public void visit(RealType realType) {
        printIndented("RealType");
    }

    @Override
    public void visit(RecordType recordType) {
        printIndented("RecordType");
        increaseIndent(() -> {
            for (Declaration declaration : recordType.getDeclarations()) {
                declaration.accept(this);
            }
        });
    }
}

