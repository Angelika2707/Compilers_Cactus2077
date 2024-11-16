package ast.statement;
import ast.expression.Expression;
import ast.visitor.Visitor;
import lombok.Getter;

@Getter
public class ReturnStatement extends Statement {
    private final Expression returnExpression;

    public ReturnStatement(Expression returnExpression) {
        this.returnExpression = returnExpression;
    }

    public ReturnStatement() {
        this.returnExpression = null;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
