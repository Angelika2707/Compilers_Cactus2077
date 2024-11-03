package ast.expression;

import ast.visitor.Visitor;

public class AndExpression extends LogicalExpression {
    public AndExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
