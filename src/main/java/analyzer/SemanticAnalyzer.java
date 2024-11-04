package analyzer;

import ast.base.Program;
import ast.declaration.TypeDeclaration;
import ast.declaration.VariableDeclaration;
import ast.expression.NestedRecordAccess;
import ast.statement.AssignmentStatement;
import ast.visitor.ProgramVisitor;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class SemanticAnalyzer {

    private final Set<String> usedIdentifiers = new HashSet<>();
    private final Set<String> declaredIdentifiers = new HashSet<>();

    public Program analyze(Program program) {
        collectDeclaredIdentifiers(program);

        collectUsedIdentifiers(program);

        filterUnusedStatements(program);

        return program;
    }

    private void collectDeclaredIdentifiers(Program program) {
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // ignore
            }
        }));

        try {
            program.accept(new ProgramVisitor() {
                @Override
                public void visit(VariableDeclaration variableDeclaration) {
                    declaredIdentifiers.add(variableDeclaration.id());
                }

                @Override
                public void visit(TypeDeclaration typeDeclaration) {
                    declaredIdentifiers.add(typeDeclaration.id());
                }
            });
        } finally {
            System.setOut(originalOut);
        }
    }

    private void collectUsedIdentifiers(Program program) {
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // ignore
            }
        }));

        try {
            program.accept(new ProgramVisitor() {
                @Override
                public void visit(AssignmentStatement assignmentStatement) {
                    if (assignmentStatement.expression() != null) {
                        assignmentStatement.expression().accept(this);
                    }
                }

                @Override
                public void visit(NestedRecordAccess identifier) {
                    usedIdentifiers.add(identifier.identifier());
                }
            });
        } finally {
            System.setOut(originalOut);
        }
    }

    private void filterUnusedStatements(Program program) {
        program.units().removeIf(unit -> {
            if (unit instanceof VariableDeclaration varDecl) {
                return !usedIdentifiers.contains(varDecl.id());
            } else if (unit instanceof TypeDeclaration typeDecl) {
                return !usedIdentifiers.contains(typeDecl.id());
            } else if (unit instanceof AssignmentStatement assignStmt) {
                return !usedIdentifiers.contains(assignStmt.identifier());
            }
            return false;
        });
    }
}
