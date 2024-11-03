package ast.expression;

import ast.visitor.Visitor;

public class ModExpression extends BinaryExpression {
    public ModExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
