package main.ast;

public class MemberAccess implements ASTNode {
    private final ASTNode object;
    private final String memberName;

    public MemberAccess(ASTNode object, String memberName) {
        this.object = object;
        this.memberName = memberName;
    }

    public ASTNode getObject() {
        return object;
    }

    public String getMemberName() {
        return memberName;
    }

    @Override
    public String toString() {
        return "MemberAccess(" + object + ", " + memberName + ")";
    }
}
