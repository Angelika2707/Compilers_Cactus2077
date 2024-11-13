package ast.statement;

import ast.base.Body;
import ast.expression.Expression;
import ast.visitor.Visitor;
import lombok.Getter;

import java.util.List;

@Getter
public class ForStatement extends Statement {
    private final String loopVariable;
    private final Expression startExpression;
    private final Expression endExpression;
    private final boolean isReverse;
    private final List<Body> body;

    public ForStatement(String loopVariable, Expression startExpr, Expression endExpr,
                        boolean isReverse, List<Body> body) {
        this.loopVariable = loopVariable;
        this.startExpression = startExpr;
        this.endExpression = endExpr;
        this.isReverse = isReverse;
        this.body = body;
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
