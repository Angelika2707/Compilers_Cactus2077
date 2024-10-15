package main.ast;

public class ParameterDeclaration implements ASTNode {
    private final String identifier;
    private final String type;

    public ParameterDeclaration(String identifier, String type) {
        this.identifier = identifier;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ParameterDeclaration";
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getType() {
        return type;
    }
}
