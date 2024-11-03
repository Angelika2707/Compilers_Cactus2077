package ast.expression;

import ast.visitor.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BinaryExpression extends Expression {
    protected Expression left;
    protected Expression right;
}