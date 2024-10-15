package main.ast;

public class IntegerLiteral implements ASTNode {
    private final int value;

    public IntegerLiteral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "IntegerLiteral(" + value + ")";
    }
}
