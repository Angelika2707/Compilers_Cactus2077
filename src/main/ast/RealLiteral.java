package main.ast;

public class RealLiteral implements ASTNode {
    private final double value;

    public RealLiteral(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "RealLiteral(" + value + ")";
    }
}
