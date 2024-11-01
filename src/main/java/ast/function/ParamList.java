package ast.function;

import ast.base.ASTNode;
import ast.visitor.Visitor;
import lombok.Getter;

import java.util.List;

@Getter
public class ParamList extends ASTNode {
    private List<Parameter> parameters;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);

        for (Parameter parameter : parameters) {
            parameter.accept(visitor);
        }
    }
}
