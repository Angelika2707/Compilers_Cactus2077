package main.ast;


public class ArrayAccess implements ASTNode {
    private final ASTNode array;
    private final ASTNode index;

    public ArrayAccess(ASTNode array, ASTNode index) {
        this.array = array;
        this.index = index;
    }

    public ASTNode getArray() {
        return array;
    }

    public ASTNode getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "ArrayAccess(" + array + ", " + index + ")";
    }
}

