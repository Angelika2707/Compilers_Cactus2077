package ast.expression;

import ast.base.ASTNode;
import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class NestedRecordAccess extends ASTNode {
    private String identifier;
    private NestedRecordAccess nestedRecord;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

