package main.ast;

public class PrimitiveType implements Type, ASTNode {
    private final String typeName;

    public PrimitiveType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }
}
