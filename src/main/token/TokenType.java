package main.token;

public enum TokenType {
    // Keywords
    VAR, TYPE, ROUTINE, RETURN, IS, RECORD, ARRAY, WHILE, FOR, LOOP, IF, THEN, ELSE, END, TRUE, FALSE, INTEGER, REAL, BOOLEAN,
    // Assignment and Operators
    ASSIGN, LT, LE, GT, GE, EQ, NE, PLUS, MINUS, MUL, DIV, MOD, AND, OR, XOR, NOT,
    // Tokens
    IDENTIFIER, INTEGERNUMBER, REALNUMBER, COMMA, SEMICOLON, LPAREN, RPAREN, LBRACKET, RBRACKET,
    DOT, COLON, RANGE, REVERSE, NEWLINE, WHITESPACE, TAB, UNKNOWN;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}