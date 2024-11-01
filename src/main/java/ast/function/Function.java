package ast.function;

import ast.base.ProgramUnit;
import ast.declaration.DeclarationList;
import ast.statement.StatementList;
import ast.type.Type;
import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Function extends ProgramUnit {
    private String id;
    private ParamList paramList;
    private Type returnType;
    private DeclarationList declarationList;
    private StatementList statementList;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
