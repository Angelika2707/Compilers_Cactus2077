package main.ast;


public class RoutineCall implements Statement {
    private final String identifier;
    private final Expression expression;

    public RoutineCall(String identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "RoutineCall";
    }
}
