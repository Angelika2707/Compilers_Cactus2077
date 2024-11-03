package ast.expression;

import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BooleanLiteral extends Expression {
    private final boolean value;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
