package main.token;

public class Token {
    private final TokenType type;
    private final String text;
    private final int line;
    private final int pos;
    private final Object value;


    public Token(TokenType type, String text, int line, int pos) {
        this.type = type;
        this.text = text
                .replace("\n", "\\n")
                .replace("\t", "\\t");
        this.line = line;
        this.pos = pos;
        if (this.type == TokenType.INTEGERNUMBER) {
            this.value = Integer.parseInt(this.text);
        } else if (this.type == TokenType.REALNUMBER) {
            this.value = Double.parseDouble(this.text);
        } else {
            this.value = null;
        }
    }

    public TokenType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public int getLine() {
        return line;
    }

    public int getPos() {
        return pos;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("[%s \"%s\" (l=%d, p=%d)]\s", type, text, line, pos);
    }
}
