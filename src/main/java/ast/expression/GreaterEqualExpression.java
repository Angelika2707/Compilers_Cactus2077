package ast.expression;

import ast.visitor.Visitor;

public class GreaterEqualExpression extends ComparisonExpression {
    public GreaterEqualExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
