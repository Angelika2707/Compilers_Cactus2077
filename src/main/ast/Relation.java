package main.ast;


import main.token.TokenType;

public class Relation implements Expression {

    private final TokenType type;
    private final Expression left;
    private final Expression right;

    public Relation(TokenType type, Expression left, Expression right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public TokenType getType() {
        return type;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "RelationNode{" +
                "type=" + type +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
