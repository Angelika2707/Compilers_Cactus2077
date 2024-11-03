package ast.expression;

import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FunctionCallExpression extends Expression {
    private String functionName;
    private List<Expression> parameters;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
