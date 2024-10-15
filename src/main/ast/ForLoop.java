package main.ast;


public class ForLoop implements Statement {
    private final String iterator;
    private final Range range;
    private final Body body;

    public ForLoop(String iterator, Range range, Body body) {
        this.iterator = iterator;
        this.range = range;
        this.body = body;
    }

    public String getIterator() {
        return iterator;
    }

    public Range getRange() {
        return range;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "ForLoop";
    }

}

