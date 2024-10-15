package main.ast;

public class AssignmentStatement implements Statement {
    private final String identifier;
    private final Expression expression;

    public AssignmentStatement(String identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }

    public String toString() {
        return "AssignmentStatement";
    }
}