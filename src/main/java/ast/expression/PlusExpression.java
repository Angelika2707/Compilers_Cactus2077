package ast.expression;

import ast.visitor.Visitor;

public class PlusExpression extends BinaryExpression {
    public PlusExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
