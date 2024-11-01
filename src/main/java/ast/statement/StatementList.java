package ast.statement;

import ast.base.ASTNode;
import ast.visitor.Visitor;
import lombok.Getter;

import java.util.List;

@Getter
public class StatementList extends ASTNode {
    private List<Statement> statements;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);

        for (Statement statement : statements) {
            statement.accept(visitor);
        }
    }
}