package main.ast;


public class Summand implements Expression {
    private final Primary primary;

    public Summand(Primary primary) {
        this.primary = primary;
    }

    public Primary getPrimary() {
        return primary;
    }

    @Override
    public String toString() {
        return "SummandNode{" +
                "primary=" + primary +
                '}';
    }
}
