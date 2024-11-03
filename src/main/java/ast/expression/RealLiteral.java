package ast.expression;

import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RealLiteral extends Expression {
    private final double value;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}