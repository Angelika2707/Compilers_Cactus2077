package ast.function;

import ast.base.Body;
import ast.base.ProgramUnit;
import ast.type.Type;
import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Function extends ProgramUnit {
    private String identifier;
    private List<Parameter> params;
    private Type returnType;
    private List<Body> body;

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
