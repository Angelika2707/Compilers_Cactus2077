package ast.type;

import ast.base.ASTNode;
import ast.visitor.Visitor;
import lombok.Getter;

@Getter
public class Type extends ASTNode {
    private String name;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

