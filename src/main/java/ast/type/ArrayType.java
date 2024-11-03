package ast.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArrayType extends Type {
    private final int size;
    private final Type elementType;
}
