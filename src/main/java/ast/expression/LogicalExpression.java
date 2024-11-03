package ast.expression;

public abstract class LogicalExpression extends BinaryExpression {
    public LogicalExpression(Expression left, Expression right) {
        super(left, right);
    }
}
