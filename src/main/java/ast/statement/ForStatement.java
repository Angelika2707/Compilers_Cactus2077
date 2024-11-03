package ast.statement;

import ast.declaration.Declaration;
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
    private final List<Declaration> declarations;
    private final List<Statement> statements;

    public ForStatement(String loopVariable, Expression startExpr, Expression endExpr,
                        boolean isReverse, List<Declaration> declarations,
                        List<Statement> statements) {
        this.loopVariable = loopVariable;
        this.startExpression = startExpr;
        this.endExpression = endExpr;
        this.isReverse = isReverse;
        this.declarations = declarations;
        this.statements = statements;
    }
    @Override
    public void accept(Visitor visitor) {
        if (startExpression != null) {
            startExpression.accept(visitor);
        }

        if (endExpression != null) {
            endExpression.accept(visitor);
        }

        if (declarations != null) {
            for (Declaration declaration : declarations) {
                if (declaration != null) {
                    declaration.accept(visitor);
                }
            }
        }

        if (statements != null) {
            for (Statement statement : statements) {
                if (statement != null) {
                    statement.accept(visitor);
                }
            }
        }

        visitor.visit(this);
    }
}
