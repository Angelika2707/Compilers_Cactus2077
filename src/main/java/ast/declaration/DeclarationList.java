package ast.declaration;

import ast.base.ASTNode;
import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DeclarationList extends ASTNode {
    private List<Declaration> declarations;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);

        for (Declaration declaration : declarations) {
            declaration.accept(visitor);
        }
    }
}