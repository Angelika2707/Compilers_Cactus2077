package ast.type;

import ast.visitor.Visitor;

public class BooleanType extends Type {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
