package ast.function;

import ast.base.ASTNode;
import ast.expression.Expression;
import ast.type.Type;
import ast.visitor.Visitor;
import lombok.Getter;

@Getter
public class Parameter extends ASTNode {
    private final String identifier;
    private final Type type;
    private final Expression expression;

    public Parameter(String identifier, Type type) {
        this.identifier = identifier;
        this.type = type;
        this.expression = null;
    }

    public Parameter(Expression expression) {
        this.identifier = null;
        this.type = null;
        this.expression = expression;
    }

    public Parameter(String identifier) {
        this.identifier = identifier;
        this.type = null;
        this.expression = null;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
