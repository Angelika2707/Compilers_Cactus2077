package main.ast;


public class Primary implements ASTNode {
    private final Object value;

    public Primary(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PrimaryNode{" +
                "value=" + value +
                '}';
    }
}
