package ast.type;

import ast.declaration.Declaration;
import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecordType extends Type {
    private final List<Declaration> fields;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
