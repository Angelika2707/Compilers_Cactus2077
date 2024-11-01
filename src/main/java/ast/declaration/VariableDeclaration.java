package ast.declaration;

import ast.base.ASTNode;
import ast.base.ProgramUnit;
import ast.expression.Expression;
import ast.type.Type;
import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VariableDeclaration extends Declaration {
    private String id;
    private Type type;
    private Expression expression;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
