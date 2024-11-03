package ast.statement;

import ast.declaration.Declaration;
import ast.expression.Expression;
import ast.visitor.Visitor;
import lombok.Getter;
import java.util.List;

@Getter
public class IfStatement extends Statement {
    private final Expression condition;
    private final List<Declaration> thenDeclarations;
    private final List<Declaration> elseDeclarations;
    private final List<Statement> thenStatements;
    private final List<Statement> elseStatements;

    public IfStatement(Expression condition, List<Declaration> thenDecl, List<Declaration> elseDecl,
                       List<Statement> thenSt, List<Statement> elseSt) {
        this.condition = condition;
        this.thenDeclarations = thenDecl;
        this.elseDeclarations = elseDecl;
        this.thenStatements = thenSt;
        this.elseStatements = elseSt;
    }

    public IfStatement(Expression condition, List<Declaration> thenDecl, List<Statement> thenSt) {
        this.condition = condition;
        this.thenDeclarations = thenDecl;
        this.elseDeclarations = null;
        this.thenStatements = thenSt;
        this.elseStatements = null;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        condition.accept(visitor);
        for (Declaration declaration : thenDeclarations) {
            if (declaration != null) {
                declaration.accept(visitor);
            }
        }
        for (Statement statement : thenStatements) {
            if (statement != null) {
                statement.accept(visitor);
            }
        }
        if (elseDeclarations != null) {
            for (Declaration declaration : elseDeclarations) {
                if (declaration != null) {
                    declaration.accept(visitor);
                }
            }
        }
        if (elseStatements != null) {
            for (Statement statement : elseStatements) {
                if (statement != null) {
                    statement.accept(visitor);
                }
            }
        }
    }
}
