package ast.expression;

import ast.visitor.Visitor;

public class EqualExpression extends ComparisonExpression {
    public EqualExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
