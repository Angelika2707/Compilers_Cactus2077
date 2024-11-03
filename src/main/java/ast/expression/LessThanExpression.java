package ast.expression;

import ast.visitor.Visitor;

public class LessThanExpression extends ComparisonExpression {
    public LessThanExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
