package ast.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdentifierType extends Type {
    String identifier;
}
