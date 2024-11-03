package ast.expression;

import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class NestedRecordAccessExpression extends Expression {
    private List<String> identifiers;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
