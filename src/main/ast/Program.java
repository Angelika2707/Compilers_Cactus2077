package main.ast;

import java.util.List;

public class Program implements ASTNode {
    private List<Declaration> declarations;

    public Program(List<Declaration> declarations) {
        this.declarations = declarations;
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }

    @Override
    public String toString() {
        return "Program{" + declarations + '}';
    }
}
