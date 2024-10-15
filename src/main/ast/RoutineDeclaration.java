package main.ast;


import java.util.List;

public class RoutineDeclaration implements Declaration {
    private final String identifier;
    private final List<ParameterDeclaration> parameters;
    private final ASTNode body;

    public RoutineDeclaration(String identifier, List<ParameterDeclaration> parameters, ASTNode body) {
        this.identifier = identifier;
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public String toString() {
        return "RoutineDeclaration";
    }

    public void addParameter(ParameterDeclaration parameter) {
        parameters.add(parameter);
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<ParameterDeclaration> getParameters() {
        return parameters;
    }

    public ASTNode getBody() {
        return body;
    }
}

