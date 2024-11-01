package ast.statement;

import ast.base.ProgramUnit;
import ast.expression.Expression;
import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Statement extends ProgramUnit {
    private Expression expression;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}