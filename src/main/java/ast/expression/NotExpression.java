package ast.expression;

import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotExpression extends Expression {
    private Expression expr;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
