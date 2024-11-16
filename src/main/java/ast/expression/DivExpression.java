package ast.expression;

import ast.visitor.Visitor;

public class DivExpression extends BinaryExpression {
    public DivExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
