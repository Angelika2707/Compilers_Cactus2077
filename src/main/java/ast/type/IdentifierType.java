package ast.type;

import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdentifierType extends Type {
    String identifier;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
