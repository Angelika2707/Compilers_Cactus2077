package ast.function;

import ast.base.ProgramUnit;
import ast.type.Type;
import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Function extends ProgramUnit {
    private String identifier;
    private ParamList paramList;
    private Type returnType;
    private DeclarationList declarationList;
    private StatementList statementList;

    public void accept(Visitor visitor) {
        this.paramList.accept(visitor);
        visitor.visit(this);
    }
}
