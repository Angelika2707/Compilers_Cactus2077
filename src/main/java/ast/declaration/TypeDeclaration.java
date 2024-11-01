package ast.declaration;

import ast.base.ASTNode;
import ast.base.ProgramUnit;
import ast.type.Type;
import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TypeDeclaration extends ProgramUnit {
    private String id;
    private Type type;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
