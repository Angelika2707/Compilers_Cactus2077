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
    private Type returnType;

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
