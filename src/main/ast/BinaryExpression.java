package main.ast;


import main.token.TokenType;

public class BinaryExpression implements Expression {
    private final TokenType operator;
    private final Expression left;
    private final Expression right;

    public BinaryExpression(TokenType operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public TokenType getOperator() {
        return operator;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "(" + left + " " + operator + " " + right + ")";
    }
}
