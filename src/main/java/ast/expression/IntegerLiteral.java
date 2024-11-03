package ast.expression;

import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IntegerLiteral extends Expression {
    private final int value;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
