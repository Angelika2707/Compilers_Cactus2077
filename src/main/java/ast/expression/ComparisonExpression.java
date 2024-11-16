package ast.expression;

public abstract class ComparisonExpression extends BinaryExpression {
    public ComparisonExpression(Expression left, Expression right) {
        super(left, right);
    }
}
