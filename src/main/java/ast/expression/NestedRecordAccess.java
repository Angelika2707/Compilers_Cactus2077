package ast.expression;

import ast.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class NestedRecordAccess extends Expression {
    private final String identifier;
    @Setter private NestedRecordAccess nestedAccess;

    public NestedRecordAccess(String identifier) {
        this.identifier = identifier;
        this.nestedAccess = null;
    }

    public NestedRecordAccess(NestedRecordAccess nestedAccess, String identifier) {
        this.identifier = identifier;
        this.nestedAccess = nestedAccess;
    }

    public List<String> getAccessPath() {
        List<String> path = new ArrayList<>();
        NestedRecordAccess current = this;
        while (current != null) {
            path.addFirst(current.identifier);
            current = current.nestedAccess;
        }
        return path;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

