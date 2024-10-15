package main.ast;


public class VariableDeclaration implements Declaration {
    private final String identifier;
    private final String type;
    private final ASTNode expression;

    public VariableDeclaration(String identifier, String type, ASTNode expression) {
        this.identifier = identifier;
        this.type = type;
        this.expression = expression;
    }

    public VariableDeclaration(String identifier, ASTNode expression) {
        this.identifier = identifier;
        this.type = null;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "VariableDeclaration";
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getType() {
        return type;
    }

    public ASTNode getExpression() {
        return expression;
    }
}