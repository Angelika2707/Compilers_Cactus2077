package ast.type;

import ast.declaration.Declaration;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecordType extends Type {
    private final List<Declaration> fields;
}
