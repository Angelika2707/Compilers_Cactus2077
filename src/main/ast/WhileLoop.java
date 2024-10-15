package main.ast;

public class WhileLoop implements Statement {
    private final Expression condition;
    private final Body body;

    public WhileLoop(Expression condition, Body body) {
        this.condition = condition;
        this.body = body;
    }

    public Expression getCondition() {
        return condition;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "While";
    }
}
