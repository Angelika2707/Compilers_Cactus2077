package main.ast;

public class BooleanLiteral implements ASTNode {
    private final boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BooleanLiteral(" + value + ")";
    }
}
