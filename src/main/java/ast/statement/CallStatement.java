package ast.statement;

import ast.expression.Expression;
import ast.visitor.Visitor;
import lombok.Getter;

import java.util.List;

@Getter
public class CallStatement extends Statement {
    private final String identifier;
    private final List<Expression> paramList;

    public CallStatement(String identifier, List<Expression> paramList) {
        this.identifier = identifier;
        this.paramList = paramList;
    }

    @Override
    public void accept(Visitor visitor) {
        if (this.paramList != null) {
            for (Expression argument : this.paramList ) {
                if (argument != null) {
                    argument.accept(visitor);
                }
            }
        }

        visitor.visit(this);
    }
}
