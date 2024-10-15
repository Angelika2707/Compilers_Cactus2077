package main.ast;


public class Range implements ASTNode{
    private final Expression start;
    private final Expression end;
    private final boolean isReverse;

    public Range(Expression start, Expression end, boolean isReverse) {
        this.start = start;
        this.end = end;
        this.isReverse = isReverse;
    }

    public Expression getStart() {
        return isReverse ? end : start;
    }

    public Expression getEnd() {
        return isReverse ? start : end;
    }

    public boolean isReverse() {
        return isReverse;
    }

    @Override
    public String toString() {
        return "Range{" +
                "start=" + start +
                ", end=" + end +
                ", isReverse=" + isReverse +
                '}';
    }
}

