package main.lexer;

import main.token.Token;
import main.token.TokenType;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final ArrayList<Token> tokens;
    private Iterator<Token> tokenIterator;
    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            "\\bvar\\b|\\btype\\b|\\broutine\\b|\\breturn\\b|\\bis\\b|\\brecord\\b|\\barray\\b|\\bwhile\\b|\\bfor\\b|\\bloop\\b|\\breverse\\b|\\bif\\b|\\belse\\b|\\bthen\\b|\\bend\\b|\\btrue\\b|\\bfalse\\b|\\band\\b|\\bor\\b|\\bxor\\b|\\bnot\\b|\\binteger\\b|\\breal\\b|\\bboolean\\b|:=|<=|>=|<|>|=|/=|\\+|\\*|/|%|,|;|\\(|\\)|\\[|\\]|\\.\\.|\\.|:|\\d+[a-zA-Z_][a-zA-Z_0-9]*|\\d+(\\.\\d*)?|\\-?\\d+\\.\\d*|\\-?\\d+|[a-zA-Z_][a-zA-Z_0-9]*|-|\\n|\\t|\\s{4}|\\s|."
    );

    public Lexer() {
        this.tokens = new ArrayList<>();
    }

    private TokenType getTokenType(String tokenText) {
        switch (tokenText) {
            case "var": return TokenType.VAR;
            case "type": return TokenType.TYPE;
            case "routine": return TokenType.ROUTINE;
            case "return": return TokenType.RETURN;
            case "is": return TokenType.IS;
            case "record": return TokenType.RECORD;
            case "array": return TokenType.ARRAY;
            case "while": return TokenType.WHILE;
            case "for": return TokenType.FOR;
            case "loop": return TokenType.LOOP;
            case "reverse": return TokenType.REVERSE;
            case "if": return TokenType.IF;
            case "then": return TokenType.THEN;
            case "else": return TokenType.ELSE;
            case "end": return TokenType.END;
            case "true": return TokenType.TRUE;
            case "false": return TokenType.FALSE;
            case "integer": return TokenType.INTEGER;
            case "real": return TokenType.REAL;
            case "boolean": return TokenType.BOOLEAN;
            case "and": return TokenType.AND;
            case "or": return TokenType.OR;
            case "xor": return TokenType.XOR;
            case "not": return TokenType.NOT;
            case ":=": return TokenType.ASSIGN;
            case "<": return TokenType.LT;
            case "<=": return TokenType.LE;
            case ">": return TokenType.GT;
            case ">=": return TokenType.GE;
            case "=": return TokenType.EQ;
            case "/=": return TokenType.NE;
            case "+": return TokenType.PLUS;
            case "-": return TokenType.MINUS;
            case "*": return TokenType.MUL;
            case "/": return TokenType.DIV;
            case "%": return TokenType.MOD;
            case ",": return TokenType.COMMA;
            case ";": return TokenType.SEMICOLON;
            case "(": return TokenType.LPAREN;
            case ")": return TokenType.RPAREN;
            case "[": return TokenType.LBRACKET;
            case "]": return TokenType.RBRACKET;
            case "..": return TokenType.RANGE;
            case ".": return TokenType.DOT;
            case ":": return TokenType.COLON;
            case "\n": return TokenType.NEWLINE;
            case "\t": return TokenType.TAB;
            default:
                if (tokenText.matches("\\s{4}")) return TokenType.TAB;
                if (tokenText.matches("\\s")) return TokenType.WHITESPACE;
                if (tokenText.matches("-?\\d+\\.\\d*")) return TokenType.REALNUMBER;
                if (tokenText.matches("-?\\d+")) return TokenType.INTEGERNUMBER;
                if (tokenText.matches("[a-zA-Z_][a-zA-Z_0-9]*")) return TokenType.IDENTIFIER;
                return TokenType.UNKNOWN;
        }
    }

    public void tokenize(String input) {
        tokens.clear();
        Matcher matcher = TOKEN_PATTERN.matcher(input);

        int line = 1;
        int pos = 1;

        while (matcher.find()) {
            String tokenText = matcher.group();

            if (tokenText.matches("\\s+") || tokenText.equals("\t") || tokenText.equals("\n") || tokenText.equals("\\s{4}")) {
                continue;
            }

            TokenType tokenType = getTokenType(tokenText);

            tokens.add(new Token(tokenType, tokenText, line, pos));

            if (tokenText.equals("\n")) {
                line++;
                pos = 1;
            } else {
                pos += tokenText.length();
            }
        }

        tokenIterator = tokens.iterator();
    }

    public Token yylex() {
        if (tokenIterator != null && tokenIterator.hasNext()) {
            return tokenIterator.next();
        }
        return null;
    }

    public void yyerror(String message) {
        System.err.println("Lexer error: " + message);
    }

    public int getLVal() {
        if (tokenIterator != null && tokenIterator.hasNext()) {
            Token lastToken = tokens.get(tokens.size() - 1);
            if (lastToken.getType() == TokenType.INTEGERNUMBER) {
                return lastToken.getIntegerValue();
            }
        }
        return 0;
    }
}
