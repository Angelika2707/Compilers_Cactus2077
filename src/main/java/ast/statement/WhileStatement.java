package ast.statement;

import ast.base.Body;
import ast.expression.Expression;
import ast.visitor.Visitor;
import lombok.Getter;

import java.util.List;

@Getter
public class WhileStatement extends Statement {
    private final Expression condition;
    private final List<Body> body;

    public WhileStatement(Expression condition, List<Body> body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
