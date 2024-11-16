package ast.declaration;

import ast.type.Type;
import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TypeDeclaration extends Declaration {
    private final String id;
    private final Type type;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
