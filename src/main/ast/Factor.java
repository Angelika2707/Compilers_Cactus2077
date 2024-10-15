package main.ast;


public class Factor implements Expression {
    private final Summand summand;

    public Factor(Summand summand) {
        this.summand = summand;
    }

    public Summand getSummand() {
        return summand;
    }

    @Override
    public String toString() {
        return "FactorNode{" +
                "summand=" + summand +
                '}';
    }
}

