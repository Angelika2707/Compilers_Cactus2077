package ast.statement;

import ast.expression.Expression;
import ast.expression.NestedRecordAccess;
import ast.visitor.Visitor;
import lombok.Getter;

@Getter
public class AssignmentStatement extends Statement {
    private final String identifier;
    private final Expression expression;
    private final Expression index;
    private final NestedRecordAccess recordField;

    public AssignmentStatement(String identifier, Expression expr) {
        this.identifier = identifier;
        this.expression = expr;
        this.index = null;
        this.recordField = null;
    }

    public AssignmentStatement(String identifier, Expression index, Expression expr) {
        this.identifier = identifier;
        this.index = index;
        this.expression = expr;
        this.recordField = null;
    }

    public AssignmentStatement(NestedRecordAccess recordField, String fieldName, Expression expr) {
        this.recordField = recordField;
        this.identifier = fieldName;
        this.expression = expr;
        this.index = null;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
