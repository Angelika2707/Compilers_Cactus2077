package main.ast;

public class TypeDeclaration implements Declaration {
    private final String identifier;
    private final String type;

    public TypeDeclaration(String identifier, String type) {
        this.identifier = identifier;
        this.type = type;
    }

    @Override
    public String toString() {
        return "TypeDeclaration";
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getType() {
        return type;
    }
}
