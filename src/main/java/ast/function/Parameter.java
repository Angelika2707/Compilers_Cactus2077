package ast.function;

import ast.base.ASTNode;
import ast.type.Type;
import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Parameter extends ASTNode {
    private String identifier;
    private Type type;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
