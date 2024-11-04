package analyzer;

import ast.base.Program;
import ast.declaration.TypeDeclaration;
import ast.declaration.VariableDeclaration;
import ast.expression.NestedRecordAccess;
import ast.statement.AssignmentStatement;
import ast.function.Function;
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

                @Override
                public void visit(Function functionDeclaration) {
                    for (var declaration : functionDeclaration.decls()) {
                        if (declaration instanceof VariableDeclaration varDecl) {
                            declaredIdentifiers.add(varDecl.id());
                        }
                    }
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

                @Override
                public void visit(Function functionDeclaration) {
                    for (var statement : functionDeclaration.stmts()) {
                        statement.accept(this);
                    }
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
            } else if (unit instanceof Function functionDecl) {
                functionDecl.decls().removeIf(declaration -> {
                    if (declaration instanceof VariableDeclaration varDecl) {
                        return !usedIdentifiers.contains(varDecl.id());
                    }
                    return false;
                });
                functionDecl.stmts().removeIf(statement -> {
                    return false;
                });
            }
            return false;
        });
    }
}
