package ast.expression;

import ast.base.ASTNode;
import ast.visitor.Visitor;
import lombok.Getter;

@Getter
public class Expression extends ASTNode {
    public enum Operator {
        PLUS, MINUS, MUL, DIV, MOD, EQ, NE, GT, LT, GE, LE, AND, OR, XOR, NOT
    }

    private Operator operator;
    private Expression leftOperand;
    private Expression rightOperand;
    private Object value;

    public Expression(Operator operator, Expression leftOperand, Expression rightOperand) {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public Expression(Object value) {
        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
