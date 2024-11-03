package ast.expression;

import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArrayAccessExpression extends Expression {
    private String identifier;
    private Expression index;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
