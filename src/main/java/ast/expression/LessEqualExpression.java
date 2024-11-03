package ast.expression;

import ast.visitor.Visitor;

public class LessEqualExpression extends ComparisonExpression {
    public LessEqualExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
