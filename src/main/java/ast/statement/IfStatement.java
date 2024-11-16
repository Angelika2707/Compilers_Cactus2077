package ast.statement;

import ast.base.Body;
import ast.expression.Expression;
import ast.visitor.Visitor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class IfStatement extends Statement {
    private final Expression condition;
    private final List<Body> thenBody;
    private final List<Body> elseBody;

    public IfStatement(Expression condition, List<Body> thenBody, List<Body> elseBody) {
        this.condition = condition;
        this.thenBody = thenBody;
        this.elseBody = elseBody;
    }

    public IfStatement(Expression condition, List<Body> thenBody) {
        this.condition = condition;
        this.thenBody = thenBody;
        this.elseBody = new ArrayList<>();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
