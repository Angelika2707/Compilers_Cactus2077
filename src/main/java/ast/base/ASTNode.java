package ast.base;

import ast.visitor.Visitor;
import lombok.Data;

@Data
public abstract class ASTNode {
    public abstract void accept(Visitor visitor);
}
