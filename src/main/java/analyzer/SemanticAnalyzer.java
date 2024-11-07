package analyzer;

import ast.base.Program;
import ast.declaration.TypeDeclaration;
import ast.declaration.VariableDeclaration;
import ast.expression.ArrayAccessExpression;
import ast.expression.FunctionCallExpression;
import ast.expression.NestedRecordAccess;
import ast.function.Function;
import ast.statement.*;
import ast.type.ArrayType;
import ast.type.IdentifierType;
import ast.visitor.ProgramVisitor;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

public class SemanticAnalyzer {

    private final Set<String> usedIdentifiers = new HashSet<>();
    private final Set<String> declaredIdentifiers = new HashSet<>();
    private final Map<String, Integer> arrHashmap = new HashMap<>();

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
                    if (variableDeclaration.type() instanceof ArrayType) {
                        arrHashmap.put(variableDeclaration.id(), ((ArrayType) variableDeclaration.type()).size());
                    } else if (variableDeclaration.expression() == null) {
                        if (variableDeclaration.type() instanceof IdentifierType) {
                            declaredIdentifiers.add(((IdentifierType) variableDeclaration.type()).identifier());
                        }
                    }
                }

                @Override
                public void visit(TypeDeclaration typeDeclaration) {
                    declaredIdentifiers.add(typeDeclaration.id());
                    if (typeDeclaration.type() instanceof ArrayType) {
                        arrHashmap.put(typeDeclaration.id(), ((ArrayType) typeDeclaration.type()).size());
                    }
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
                    if (assignmentStatement.recordField() != null) {
                        usedIdentifiers.add(assignmentStatement.identifier());
                        usedIdentifiers.add(assignmentStatement.recordField().getAccessPath().getLast());
                    }
                }

                @Override
                public void visit(VariableDeclaration variableDeclaration) {
                    if (variableDeclaration.type() instanceof IdentifierType) {
                        usedIdentifiers.add(((IdentifierType) variableDeclaration.type()).identifier());
                    }
                }

                @Override
                public void visit(NestedRecordAccess identifier) {
                    usedIdentifiers.add(identifier.identifier());
                    if (identifier.nestedAccess() != null) {
                        identifier.nestedAccess().accept(this);
                    }
                }

                @Override
                public void visit(Function functionDeclaration) {
                    for (var statement : functionDeclaration.stmts()) {
                        statement.accept(this);
                    }
                }

                @Override
                public void visit(CallStatement callStatement) {
                    usedIdentifiers.add(callStatement.identifier());
                    for (var param : callStatement.paramList()) {
                        if (param instanceof NestedRecordAccess) {
                            usedIdentifiers.add(((NestedRecordAccess) param).identifier());
                            if (((NestedRecordAccess) param).nestedAccess() != null) {
                                ((NestedRecordAccess) param).nestedAccess().accept(this);
                            }
                        }
                        else if (param instanceof ArrayAccessExpression)
                            usedIdentifiers.add(((ArrayAccessExpression) param).identifier());
                        else if (param instanceof FunctionCallExpression) {
                            usedIdentifiers.add(((FunctionCallExpression) param).functionName());
                            for (var p : ((FunctionCallExpression) param).parameters()) {
                                p.accept(this);
                            }
                        }

                    }
                }

                @Override
                public void visit(WhileStatement whileStatement) {
                    whileStatement.condition().accept(this);
                    for (var statement : whileStatement.statements()) {
                        statement.accept(this);
                    }
                }

                @Override
                public void visit(ForStatement forStatement) {
                    usedIdentifiers.add(forStatement.loopVariable());
                    for (var statement : forStatement.statements()) {
                        statement.accept(this);
                    }
                }

                @Override
                public void visit(IfStatement ifStatement) {
                    ifStatement.condition().accept(this);
                    for (var statement: ifStatement.thenStatements()) {
                        statement.accept(this);
                    }
                    for (var statement : ifStatement.elseStatements()) {
                        statement.accept(this);
                    }
                }

                @Override
                public void visit(ReturnStatement returnStatement) {
                    returnStatement.returnExpression().accept(this);
                }

                @Override
                public void visit(ArrayAccessExpression arrayAccessExpression) {
                    usedIdentifiers.add(arrayAccessExpression.identifier());
                }

                @Override
                public void visit(FunctionCallExpression functionCallExpression) {
                    usedIdentifiers.add(functionCallExpression.functionName());
                    for (var param : functionCallExpression.parameters()) {
                        param.accept(this);
                    }
                }
            });
        } finally {
            System.setOut(originalOut);
        }
    }

    private void filterUnusedStatements(Program program) {
        program.units().removeIf(unit -> switch (unit) {
            case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
            case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
            case AssignmentStatement assignStmt -> !usedIdentifiers.contains(assignStmt.identifier());
            case WhileStatement whileStatement -> {
                whileStatement.declarations().removeIf(declaration -> switch (declaration) {
                    case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                    case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                    default -> false;
                });
                whileStatement.statements().removeIf(statement -> switch (statement) {
                    case AssignmentStatement assignStmt -> !usedIdentifiers.contains(assignStmt.identifier());
                    default -> false;
                });
                yield false;
            }
            case ForStatement forStatement -> {
                forStatement.declarations().removeIf(declaration -> switch (declaration) {
                    case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                    case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                    default -> false;
                });
                forStatement.statements().removeIf(statement -> switch (statement) {
                    case AssignmentStatement assignStmt -> !usedIdentifiers.contains(assignStmt.identifier());
                    default -> false;
                });
                yield false;
            }
            case IfStatement ifStatement -> {
                ifStatement.thenDeclarations().removeIf(declaration -> switch (declaration) {
                    case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                    case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                    default -> false;
                });
                ifStatement.elseDeclarations().removeIf(declaration -> switch (declaration) {
                    case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                    case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                    default -> false;
                });
                ifStatement.thenStatements().removeIf(statement -> switch (statement) {
                    case AssignmentStatement assignStmt -> !usedIdentifiers.contains(assignStmt.identifier());
                    default -> false;
                });
                ifStatement.elseStatements().removeIf(statement -> switch (statement) {
                    case AssignmentStatement assignStmt -> !usedIdentifiers.contains(assignStmt.identifier());
                    default -> false;
                });
                yield false;
            }
            case ReturnStatement returnStatement -> {
                switch (returnStatement.returnExpression()) {
                    case NestedRecordAccess nestedRecordAccess: {
                        if (!usedIdentifiers.contains(nestedRecordAccess.identifier())) {
                            yield true;
                        }
                        if (nestedRecordAccess.nestedAccess() != null) {
                            List<String> path = nestedRecordAccess.getAccessPath();
                            for (var field: path) {
                                if (!usedIdentifiers.contains(field)) yield true;
                            }
                        }
                    }
                    default: yield false;
                }
            }
            case Function functionDecl -> {
                functionDecl.decls().removeIf(declaration -> switch (declaration) {
                    case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                    case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                    default -> false;
                });
                functionDecl.stmts().removeIf(statement -> switch (statement) {
                    case AssignmentStatement assignStmt -> !usedIdentifiers.contains(assignStmt.identifier());
                    default -> false;
                });
                yield !usedIdentifiers.contains(functionDecl.identifier());
            }
            default -> false;
        });
    }
}
