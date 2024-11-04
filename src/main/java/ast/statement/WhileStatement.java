package ast.statement;

import ast.declaration.Declaration;
import ast.expression.Expression;
import ast.visitor.Visitor;
import lombok.Getter;

import java.util.List;

@Getter
public class WhileStatement extends Statement {
    private final Expression condition;
    private final List<Declaration> declarations;
    private final List<Statement> statements;

    public WhileStatement(Expression condition, List<Declaration> declarations, List<Statement> statements) {
        this.condition = condition;
        this.declarations = declarations;
        this.statements = statements;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
