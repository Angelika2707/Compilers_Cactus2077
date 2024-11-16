package ast.expression;

import ast.visitor.Visitor;

public class MulExpression extends BinaryExpression {
    public MulExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
