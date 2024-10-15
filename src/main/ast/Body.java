package main.ast;

import java.util.ArrayList;
import java.util.List;

public class Body implements ASTNode {
    private final List<ASTNode> bodyElements;

    public Body() {
        this.bodyElements = new ArrayList<>();
    }

    public void addElement(ASTNode element) {
        this.bodyElements.add(element);
    }

    public List<ASTNode> getBodyElements() {
        return bodyElements;
    }

    @Override
    public String toString() {
        return "Body{" + bodyElements + '}';
    }
}

