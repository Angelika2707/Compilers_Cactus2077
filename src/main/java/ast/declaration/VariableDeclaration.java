package ast.declaration;

import ast.expression.Expression;
import ast.type.Type;
import ast.visitor.Visitor;
import lombok.Getter;

@Getter
public class VariableDeclaration extends Declaration {
    private final String id;
    private final Type type;
    private final Expression expression;

    public VariableDeclaration(String id, Type type, Expression expression) {
        this.id = id;
        this.type = type;
        this.expression = expression;
    }

    public VariableDeclaration(String id, Type type) {
        this.id = id;
        this.type = type;
        this.expression = null;
    }

    public VariableDeclaration(String id, Expression expression) {
        this.id = id;
        this.type = null;
        this.expression = expression;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
