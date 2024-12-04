package analyzer;

import ast.base.Body;
import ast.base.ProgramUnit;
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
    private final Set<Expression> condsForSimplification = new HashSet<>();
    private final Map<String, Integer> arrHashmap = new HashMap<>();
    private final JexlEngine jexl = new JexlBuilder().create();
    private boolean isInsideFunction = false;
    private boolean noIdentifiers = false;

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
                    if (variableDeclaration.type() instanceof ArrayType) {
                        arrHashmap.put(variableDeclaration.id(), ((ArrayType) variableDeclaration.type()).size());
                    } else if (variableDeclaration.expression() == null) {
                        if (variableDeclaration.type() instanceof IdentifierType) {
                            String identifier = ((IdentifierType) variableDeclaration.type()).identifier();
                            if (arrHashmap.get(identifier) != null) {
                                arrHashmap.put(variableDeclaration.id(), arrHashmap.get(identifier));
                            }
                        }
                    }
                }

                @Override
                public void visit(TypeDeclaration typeDeclaration) {
                    if (typeDeclaration.type() instanceof ArrayType) {
                        arrHashmap.put(typeDeclaration.id(), ((ArrayType) typeDeclaration.type()).size());
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
                            case Expression e when e instanceof BinaryExpression || e instanceof ParenthesizedExpression -> {
                                noIdentifiers = true;
                                e.accept(this);
                                if (noIdentifiers) {
                                    String res = simplify(e);
                                    JexlExpression expression = jexl.createExpression(res);
                                    JexlContext context = new MapContext();

                                    Object evalExpr = expression.evaluate(context);
                                    switch (evalExpr) {
                                        case Integer i-> {
                                            if (i < 1 || i > arrHashmap.get(assignmentStatement.identifier())) {
                                                throw new ArrayIndexOutOfBoundsException("Index " + i
                                                        + " is out of bounds for array " + assignmentStatement.identifier() + ".");
                                            }
                                        }
                                        case Double d -> throw new
                                                IllegalArgumentException("Invalid array index: real number "
                                                + d + " cannot be used as an array index.");
                                        case Boolean b -> throw new
                                                IllegalArgumentException("Invalid array index: boolean value '"
                                                + b + "' cannot be used as an array index.");
                                        default -> {}
                                    }
                                    noIdentifiers = false;
                                }
                            }
                            default -> {
                            }
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
                    noIdentifiers = false;
                    if (identifier.nestedAccess() != null) {
                        identifier.nestedAccess().accept(this);
                    }
                }

                @Override
                public void visit(Function functionDeclaration) {
                    isInsideFunction = true;
                    for (var element : functionDeclaration.body()) {
                        if (element instanceof Statement statement) {
                            if (functionDeclaration.returnType() == null &&
                                    statement instanceof ReturnStatement) {
                                throw new IllegalStateException("Unexpected return statement in a void routine.");
                            }
                            statement.accept(this);
                        }
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
                        } else if (param instanceof ArrayAccessExpression)
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
                    noIdentifiers = true;
                    whileStatement.condition().accept(this);
                    if (noIdentifiers) condsForSimplification.add(whileStatement.condition());
                    noIdentifiers = false;
                    for (var element : whileStatement.body()) {
                        if (element instanceof Statement statement) {
                            statement.accept(this);
                        }
                    }
                }

                @Override
                public void visit(ForStatement forStatement) {
                    usedIdentifiers.add(forStatement.loopVariable());
                    for (var element : forStatement.body()) {
                        if (element instanceof Statement statement) {
                            statement.accept(this);
                        }
                    }
                }

                @Override
                public void visit(IfStatement ifStatement) {
                    noIdentifiers = true;
                    ifStatement.condition().accept(this);
                    if (noIdentifiers) condsForSimplification.add(ifStatement.condition());
                    noIdentifiers = false;
                    for (var element : ifStatement.thenBody()) {
                        if (element instanceof Statement statement) {
                            statement.accept(this);
                        }
                    }
                    for (var element : ifStatement.elseBody()) {
                        if (element instanceof Statement statement) {
                            statement.accept(this);
                        }
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
                    noIdentifiers = false;
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
                        case Expression e when e instanceof BinaryExpression || e instanceof ParenthesizedExpression -> {
                            noIdentifiers = true;
                            e.accept(this);
                            if (noIdentifiers) {
                                String res = simplify(e);
                                JexlExpression expression = jexl.createExpression(res);
                                JexlContext context = new MapContext();

                                Object evalExpr = expression.evaluate(context);
                                switch (evalExpr) {
                                    case Integer i-> {
                                        if (i < 1 || i > arrHashmap.get(arrayAccessExpression.identifier())) {
                                            throw new ArrayIndexOutOfBoundsException("Index " + i
                                                    + " is out of bounds for array " + arrayAccessExpression.identifier() + ".");
                                        }
                                    }
                                    case Double d -> throw new
                                            IllegalArgumentException("Invalid array index: real number "
                                            + d + " cannot be used as an array index.");
                                    case Boolean b -> throw new
                                            IllegalArgumentException("Invalid array index: boolean value '"
                                            + b + "' cannot be used as an array index.");
                                    default -> {}
                                }
                                noIdentifiers = false;
                            }
                        }
                        default -> {
                        }
                    }
                }

                @Override
                public void visit(FunctionCallExpression functionCallExpression) {
                    usedIdentifiers.add(functionCallExpression.functionName());
                    noIdentifiers = false;
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
        ListIterator<ProgramUnit> iterator = program.units().listIterator();
        while (iterator.hasNext()) {
            ProgramUnit unit = iterator.next();

            switch (unit) {
                case VariableDeclaration varDecl -> {
                    if (!usedIdentifiers.contains(varDecl.id())) {
                        iterator.remove();
                    }
                }

                case TypeDeclaration typeDecl -> {
                    if (!usedIdentifiers.contains(typeDecl.id())) {
                        iterator.remove();
                    }
                }

                case AssignmentStatement assignStmt -> {
                    if (!usedIdentifiers.contains(assignStmt.identifier())) {
                        iterator.remove();
                    }
                }

                case WhileStatement whileStatement -> {
                    whileStatement.body().removeIf(element -> switch (element) {
                        case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                        case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                        default -> false;
                    });

                    if (condsForSimplification.contains(whileStatement.condition())) {
                        String res = simplify(whileStatement.condition());
                        JexlExpression expression = jexl.createExpression(res);
                        JexlContext context = new MapContext();

                        Object evalExpr = expression.evaluate(context);

                        switch (evalExpr) {
                            case Boolean b when !b -> iterator.remove();
                            case Integer i when i == 0 -> iterator.remove();
                            case Integer i -> throw new IllegalArgumentException("Invalid integer value in condition: "
                                    + i + ". Expected 0 or 1.");
                            case Double ignored ->
                                    throw new IllegalArgumentException("Real values cannot be used in conditions.");
                            default -> filterNestedStatements(whileStatement.body());

                        }
                    } else filterNestedStatements(whileStatement.body());
                }

                case ForStatement forStatement -> {
                    forStatement.body().removeIf(element -> switch (element) {
                        case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                        case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                        default -> false;
                    });
                    filterNestedStatements(forStatement.body());
                }

                case IfStatement ifStatement -> {
                    ifStatement.thenBody().removeIf(element -> switch (element) {
                        case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                        case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                        default -> false;
                    });
                    ifStatement.elseBody().removeIf(element -> switch (element) {
                        case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                        default -> false;
                    });

                    if (condsForSimplification.contains(ifStatement.condition())) {
                        String res = simplify(ifStatement.condition());
                        JexlExpression expression = jexl.createExpression(res);
                        JexlContext context = new MapContext();

                        List<Body> newBody = new ArrayList<>();
                        Object evalExpr = expression.evaluate(context);

                        switch (evalExpr) {
                            case Boolean b when b -> {
                                filterNestedStatements(ifStatement.thenBody());
                                newBody.addAll(ifStatement.thenBody());
                            }
                            case Boolean b when !b -> {
                                filterNestedStatements(ifStatement.elseBody());
                                newBody.addAll(ifStatement.elseBody());
                            }
                            case Integer i when i == 1 -> {
                                filterNestedStatements(ifStatement.thenBody());
                                newBody.addAll(ifStatement.thenBody());
                            }
                            case Integer i when i == 0 -> {
                                filterNestedStatements(ifStatement.elseBody());
                                newBody.addAll(ifStatement.elseBody());
                            }
                            case Integer i -> throw new IllegalArgumentException("Invalid integer value in condition: "
                                    + i + ". Expected 0 or 1.");
                            case Double ignored ->
                                    throw new IllegalArgumentException("Real values cannot be used in conditions.");
                            default -> {
                            }
                        }

                        iterator.remove();
                        for (Body u : newBody) {
                            iterator.add(u);
                        }
                    } else {
                        filterNestedStatements(ifStatement.thenBody());
                        filterNestedStatements(ifStatement.elseBody());
                    }
                }

                case ReturnStatement returnStatement -> {
                    var expression = returnStatement.returnExpression();

                    if (expression instanceof NestedRecordAccess nestedRecordAccess) {
                        if (!usedIdentifiers.contains(nestedRecordAccess.identifier())) {
                            iterator.remove();
                        }
                        if (nestedRecordAccess.nestedAccess() != null) {
                            List<String> path = nestedRecordAccess.getAccessPath();
                            for (var field : path) {
                                if (!usedIdentifiers.contains(field)) iterator.remove();
                            }
                        }
                    }
                }

                case Function functionDecl -> {
                    functionDecl.body().removeIf(element -> switch (element) {
                        case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                        case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                        default -> false;
                    });
                    filterNestedStatements(functionDecl.body());
                    if (!usedIdentifiers.contains(functionDecl.identifier())) {
                        iterator.remove();
                    }
                }

                default -> {
                }
            }
        }
    }

    private void filterNestedStatements(List<Body> body) {
        ListIterator<Body> iterator = body.listIterator();
        while (iterator.hasNext()) {
            Body el = iterator.next();

            if (!(el instanceof Statement statement)) {
                continue;
            }

            switch (statement) {
                case AssignmentStatement assignStmt -> {
                    if (!usedIdentifiers.contains(assignStmt.identifier())) {
                        iterator.remove();
                    }
                }

                case WhileStatement whileStatement -> {
                    whileStatement.body().removeIf(element -> switch (element) {
                        case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                        case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                        default -> false;
                    });

                    if (condsForSimplification.contains(whileStatement.condition())) {
                        String res = simplify(whileStatement.condition());
                        JexlExpression expression = jexl.createExpression(res);
                        JexlContext context = new MapContext();

                        Object evalExpr = expression.evaluate(context);

                        switch (evalExpr) {
                            case Boolean b when !b -> iterator.remove();
                            case Integer i when i == 0 -> iterator.remove();
                            case Integer i -> throw new IllegalArgumentException("Invalid integer value in condition: "
                                    + i + ". Expected 0 or 1.");
                            case Double ignored ->
                                    throw new IllegalArgumentException("Real values cannot be used in conditions.");
                            default -> filterNestedStatements(whileStatement.body());
                        }
                    } else filterNestedStatements(whileStatement.body());
                }

                case ForStatement forStatement -> {
                    forStatement.body().removeIf(element -> switch (element) {
                        case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                        case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                        default -> false;
                    });
                    filterNestedStatements(forStatement.body());
                }

                case IfStatement ifStatement -> {
                    ifStatement.thenBody().removeIf(element -> switch (element) {
                        case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                        case TypeDeclaration typeDecl -> !usedIdentifiers.contains(typeDecl.id());
                        default -> false;
                    });
                    ifStatement.elseBody().removeIf(element -> switch (element) {
                        case VariableDeclaration varDecl -> !usedIdentifiers.contains(varDecl.id());
                        default -> false;
                    });

                    if (condsForSimplification.contains(ifStatement.condition())) {
                        String res = simplify(ifStatement.condition());
                        JexlExpression expression = jexl.createExpression(res);
                        JexlContext context = new MapContext();

                        List<Body> newBody = new ArrayList<>();
                        Object evalExpr = expression.evaluate(context);

                        switch (evalExpr) {
                            case Boolean b when b -> {
                                filterNestedStatements(ifStatement.thenBody());
                                newBody.addAll(ifStatement.thenBody());
                            }
                            case Boolean b when !b -> {
                                filterNestedStatements(ifStatement.elseBody());
                                newBody.addAll(ifStatement.elseBody());
                            }
                            case Integer i when i == 1 -> {
                                filterNestedStatements(ifStatement.thenBody());
                                newBody.addAll(ifStatement.thenBody());
                            }
                            case Integer i when i == 0 -> {
                                filterNestedStatements(ifStatement.elseBody());
                                newBody.addAll(ifStatement.elseBody());
                            }
                            case Integer i -> throw new IllegalArgumentException("Invalid integer value in condition: "
                                    + i + ". Expected 0 or 1.");
                            case Double ignored ->
                                    throw new IllegalArgumentException("Real values cannot be used in conditions.");
                            default -> {
                            }
                        }

                        iterator.remove();
                        for (Body u : newBody) {
                            iterator.add(u);
                        }
                    } else {
                        filterNestedStatements(ifStatement.thenBody());
                        filterNestedStatements(ifStatement.elseBody());
                    }
                }

                case ReturnStatement returnStatement -> {
                    var expression = returnStatement.returnExpression();

                    if (expression instanceof NestedRecordAccess nestedRecordAccess) {
                        if (!usedIdentifiers.contains(nestedRecordAccess.identifier())) {
                            iterator.remove();
                        }
                        if (nestedRecordAccess.nestedAccess() != null) {
                            List<String> path = nestedRecordAccess.getAccessPath();
                            for (var field : path) {
                                if (!usedIdentifiers.contains(field)) iterator.remove();
                            }
                        }
                    }
                }

                default -> {
                }
            }
        }
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
