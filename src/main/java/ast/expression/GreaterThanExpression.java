package ast.expression;

import ast.visitor.Visitor;

public class GreaterThanExpression extends ComparisonExpression {
    public GreaterThanExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
