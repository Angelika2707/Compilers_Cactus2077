package ast.expression;

import ast.visitor.Visitor;

public class OrExpression extends LogicalExpression {
    public OrExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
