package ast.expression;

import ast.visitor.Visitor;

public class XorExpression extends LogicalExpression {
    public XorExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
