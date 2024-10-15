package main.ast;

public class NotExpression implements ASTNode {
    private final ASTNode expression;

    public NotExpression(ASTNode expression) {
        this.expression = expression;
    }

    public ASTNode getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "NotExpression(" + expression + ")";
    }
}
