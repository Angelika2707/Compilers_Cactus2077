package analyzer;

import ast.expression.Expression;
import ast.base.Program;
import ast.declaration.TypeDeclaration;
import ast.declaration.VariableDeclaration;
import ast.expression.*;
import ast.function.Function;
import ast.statement.*;
import ast.type.ArrayType;
import ast.type.IdentifierType;
import ast.visitor.ProgramVisitor;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.JexlBuilder;


import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

public class SemanticAnalyzer {

    private final Set<String> usedIdentifiers = new HashSet<>();
    private final Set<String> declaredIdentifiers = new HashSet<>();
    private final Map<Expression, Expression> condsForSimplification = new HashMap<>();
    private final Map<String, Integer> arrHashmap = new HashMap<>();
    private boolean isInsideFunction = false;
    private boolean noVariables = false;

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
                            String identifier = ((IdentifierType) variableDeclaration.type()).identifier();
                            declaredIdentifiers.add(identifier);
                            if (arrHashmap.get(identifier) != null) {
                                arrHashmap.put(variableDeclaration.id(), arrHashmap.get(identifier));
                            }
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
                    if (assignmentStatement.index() != null) {
                        usedIdentifiers.add(assignmentStatement.identifier());
                        switch (assignmentStatement.index()) {
                            case IntegerLiteral integerLiteral -> {
                                if (integerLiteral.value() < 1 || integerLiteral.value() > arrHashmap.get(assignmentStatement.identifier())) {
                                    throw new ArrayIndexOutOfBoundsException("Index " + integerLiteral.value()
                                            + " is out of bounds for array " + assignmentStatement.identifier() + ".");
                                }
                            }
                            case RealLiteral realLiteral -> throw new
                                    IllegalArgumentException("Invalid array index: real number "
                                    + realLiteral.value() + " cannot be used as an array index.");
                            case BooleanLiteral booleanLiteral -> throw new
                                    IllegalArgumentException("Invalid array index: boolean value '"
                                    + booleanLiteral.value() + "' cannot be used as an array index.");
                            default -> {}
                        }
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
                    noVariables = false;
                    if (identifier.nestedAccess() != null) {
                        identifier.nestedAccess().accept(this);
                    }
                }

                @Override
                public void visit(Function functionDeclaration) {
                    isInsideFunction = true;
                    for (var statement : functionDeclaration.stmts()) {
                        statement.accept(this);
                    }
                    isInsideFunction = false;
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
                    noVariables = true;
                    whileStatement.condition().accept(this);
                    if (noVariables) condsForSimplification.put(whileStatement.condition(), whileStatement.condition());
                    noVariables = false;
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
                    noVariables = true;
                    ifStatement.condition().accept(this);
                    if (noVariables) condsForSimplification.put(ifStatement.condition(), ifStatement.condition());
                    noVariables = false;
                    for (var statement: ifStatement.thenStatements()) {
                        statement.accept(this);
                    }
                    for (var statement : ifStatement.elseStatements()) {
                        statement.accept(this);
                    }
                }

                @Override
                public void visit(ReturnStatement returnStatement) {
                    if (!isInsideFunction) {
                        throw new IllegalStateException("Return statement found outside of a routine body.");
                    }
                    returnStatement.returnExpression().accept(this);
                }

                @Override
                public void visit(ArrayAccessExpression arrayAccessExpression) {
                    usedIdentifiers.add(arrayAccessExpression.identifier());
                    switch (arrayAccessExpression.index()) {
                        case IntegerLiteral integerLiteral -> {
                            if (integerLiteral.value() < 1 || integerLiteral.value() > arrHashmap.get(arrayAccessExpression.identifier())) {
                                throw new ArrayIndexOutOfBoundsException("Index " + integerLiteral.value()
                                        + " is out of bounds for array " + arrayAccessExpression.identifier() + ".");
                            }
                        }
                        case RealLiteral realLiteral -> throw new
                                IllegalArgumentException("Invalid array index: real number "
                                + realLiteral.value() + " cannot be used as an array index.");
                        case BooleanLiteral booleanLiteral -> throw new
                                IllegalArgumentException("Invalid array index: boolean value '"
                                + booleanLiteral.value() + "' cannot be used as an array index.");
                        default -> {}
                    }
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
                filterNestedStatements(whileStatement.statements());
                yield false;
            }
            case ForStatement forStatement -> {
                forStatement.declarations().removeIf(declaration -> switch (declaration) {
                    case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                    case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                    default -> false;
                });
                filterNestedStatements(forStatement.statements());
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
                    default -> false;
                });
                filterNestedStatements(ifStatement.thenStatements());
                filterNestedStatements(ifStatement.elseStatements());
                String res = simplify(ifStatement.condition());
                System.out.println(res);
                JexlEngine jexl = new JexlBuilder().create();
                JexlExpression expression = jexl.createExpression(res);
                JexlContext context = new MapContext();
                System.out.println(String.valueOf(expression.evaluate(context)));
                yield false;
            }
            case ReturnStatement returnStatement -> {
                var expression = returnStatement.returnExpression();

                if (expression instanceof NestedRecordAccess nestedRecordAccess) {
                    if (!usedIdentifiers.contains(nestedRecordAccess.identifier())) {
                        yield true;
                    }
                    if (nestedRecordAccess.nestedAccess() != null) {
                        List<String> path = nestedRecordAccess.getAccessPath();
                        for (var field : path) {
                            if (!usedIdentifiers.contains(field)) {
                                yield true;
                            }
                        }
                    }
                }

                yield false;
            }
            case Function functionDecl -> {
                functionDecl.decls().removeIf(declaration -> switch (declaration) {
                    case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                    case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                    default -> false;
                });
                filterNestedStatements(functionDecl.stmts());
                yield !usedIdentifiers.contains(functionDecl.identifier());
            }

            default -> false;
        });
    }

    private void filterNestedStatements(List<Statement> statements) {
        statements.removeIf(statement -> switch (statement) {
            case AssignmentStatement assignStmt -> !usedIdentifiers.contains(assignStmt.identifier());

            case ForStatement nestedForStmt -> {
                nestedForStmt.declarations().removeIf(declaration -> switch (declaration) {
                    case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                    case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                    default -> false;
                });
                filterNestedStatements(nestedForStmt.statements());
                yield false;
            }

            case WhileStatement nestedWhileStmt -> {
                nestedWhileStmt.declarations().removeIf(declaration -> switch (declaration) {
                    case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                    case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                    default -> false;
                });
                filterNestedStatements(nestedWhileStmt.statements());
                yield false;
            }

            case IfStatement nestedIfStmt -> {
                nestedIfStmt.thenDeclarations().removeIf(declaration -> switch (declaration) {
                    case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                    case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                    default -> false;
                });
                nestedIfStmt.elseDeclarations().removeIf(declaration -> switch (declaration) {
                    case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                    case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                    default -> false;
                });
                filterNestedStatements(nestedIfStmt.thenStatements());
                filterNestedStatements(nestedIfStmt.elseStatements());
                yield false;
            }

            default -> false;
        });
    }

    private String simplify(Expression expression) {
        PrintStream originalOut = System.out;
        StringBuilder result = new StringBuilder();
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
            }
        }));

        try {
            expression.accept(new ProgramVisitor() {
                @Override
                public void visit(BooleanLiteral booleanLiteral) {
                    result.append(booleanLiteral.value());
                }

                @Override
                public void visit(IntegerLiteral integerLiteral) {
                    result.append(integerLiteral.value());
                }

                @Override
                public void visit(RealLiteral realLiteral) {
                    result.append(realLiteral.value());
                }

                @Override
                public void visit(PlusExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append("+").append(" ").append(right);
                }

                @Override
                public void visit(MinusExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append("-").append(" ").append(right);
                }

                @Override
                public void visit(MulExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append("*").append(" ").append(right);
                }

                @Override
                public void visit(DivExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append("/").append(" ").append(right);
                }

                @Override
                public void visit(ModExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append("%").append(" ").append(right);
                }

                @Override
                public void visit(ParenthesizedExpression expression) {
                    String expr = simplify(expression.expr());
                    result.append("(").append(expr).append(")");
                }

                @Override
                public void visit(AndExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append("&&").append(" ").append(right);
                }

                @Override
                public void visit(OrExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append("||").append(" ").append(right);
                }

                @Override
                public void visit(XorExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append("^").append(" ").append(right);
                }

                @Override
                public void visit(NotExpression expression) {
                    String expr = simplify(expression.expr());
                    result.append("!").append(expr);
                }

                @Override
                public void visit(EqualExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append("==").append(" ").append(right);
                }

                @Override
                public void visit(NotEqualExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append("!=").append(" ").append(right);
                }

                @Override
                public void visit(LessThanExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append("<").append(" ").append(right);
                }

                @Override
                public void visit(GreaterThanExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append(">").append(" ").append(right);
                }

                @Override
                public void visit(LessEqualExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append("<=").append(" ").append(right);
                }

                @Override
                public void visit(GreaterEqualExpression expression) {
                    String left = simplify(expression.left());
                    String right = simplify(expression.right());
                    result.append(left).append(" ").append(">=").append(" ").append(right);
                }
            });
        } finally {
            System.setOut(originalOut);
        }

       return result.toString();
    }
}
