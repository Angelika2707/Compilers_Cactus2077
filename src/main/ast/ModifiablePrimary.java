package main.ast;


import java.util.List;

public class ModifiablePrimary implements ASTNode {
    private final String identifier;
    private final List<ASTNode> index;

    public ModifiablePrimary(String identifier, List<ASTNode> index) {
        this.identifier = identifier;
        this.index = index;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<ASTNode> getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "ModifiablePrimaryNode{" +
                "identifier='" + identifier + '\'' +
                ", index=" + index +
                '}';
    }
}

