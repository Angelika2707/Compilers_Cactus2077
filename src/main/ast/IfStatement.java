package main.ast;

public class IfStatement implements Statement {
    private final Expression condition;
    private final Body thenBody;
    private final Body elseBody;

    public IfStatement(Expression condition, Body thenBody, Body elseBody) {
        this.condition = condition;
        this.thenBody = thenBody;
        this.elseBody = elseBody;
    }

    public IfStatement(Expression condition, Body thenBody) {
        this.condition = condition;
        this.thenBody = thenBody;
        this.elseBody = null;
    }

    public Expression getCondition() {
        return condition;
    }

    public Body getThenBody() {
        return thenBody;
    }

    public Body getElseBody() {
        return elseBody;
    }

    public String toString() {
        return "If";
    }
}