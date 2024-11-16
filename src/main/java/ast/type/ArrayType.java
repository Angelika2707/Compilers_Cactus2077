package ast.type;

import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArrayType extends Type {
    private final int size;
    private final Type elementType;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
