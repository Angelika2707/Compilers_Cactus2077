package ast.declaration;

import ast.base.ProgramUnit;
import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Declaration extends ProgramUnit {
    private VariableDeclaration variableDeclaration;
    private TypeDeclaration typeDeclaration;

    public Declaration(VariableDeclaration variableDeclaration) {
        this.variableDeclaration = variableDeclaration;
        this.typeDeclaration = null;
    }

    public Declaration(TypeDeclaration typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
        this.variableDeclaration = null;
    }

    @Override
    public void accept(Visitor visitor) {
        if (variableDeclaration != null) {
            visitor.visit(this);
        } else if (typeDeclaration != null) {
            visitor.visit(this);
        }
    }
}
