package ast.expression;

import ast.visitor.Visitor;

public class NotEqualExpression extends ComparisonExpression {
    public NotEqualExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
