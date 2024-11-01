package ast.base;

import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Program extends ASTNode {
    private List<ProgramUnit> units;

    public void add(ProgramUnit unit) {
        units.add(unit);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
