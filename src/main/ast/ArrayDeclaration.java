package main.ast;

public class ArrayDeclaration implements Declaration {
    private final String elementType;
    private final ASTNode size;

    public ArrayDeclaration(ASTNode sizeExpression, String elementType) {
        this.size = sizeExpression;
        this.elementType = elementType;
    }

    public String getElementType() {
        return elementType;
    }

    public ASTNode getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "ArrayType{" +
                "elementType=" + elementType +
                ", sizeExpression=" + size +
                '}';
    }
}

