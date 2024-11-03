package ast.expression;

import ast.visitor.Visitor;

public class MinusExpression extends BinaryExpression {
    public MinusExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
