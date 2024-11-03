package ast.expression;

import ast.base.ASTNode;
import ast.visitor.Visitor;

public abstract class Expression extends ASTNode {
    public abstract void accept(Visitor visitor);
}
