package main.ast;


public class Simple implements Expression {
    private final Factor factor;

    public Simple(Factor factor) {
        this.factor = factor;
    }

    public Factor getFactor() {
        return factor;
    }

    @Override
    public String toString() {
        return "SimpleNode{" +
                "factor=" + factor +
                '}';
    }
}

