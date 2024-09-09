package main.lexer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            "\\bvar\\b|\\btype\\b|\\broutine\\b|\\bis\\b|\\brecord\\b|\\barray\\b|\\bwhile\\b|\\bfor\\b|\\bif\\b|\\belse\\b|\\bend\\b|\\btrue\\b|\\bfalse\\b|\\binteger\\b|\\breal\\b|\\bboolean\\b|:=|<=|>=|<|>|=|/=|\\+|\\*|/|%|,|;|\\(|\\)|\\[|\\]|\\.\\.|\\.|:|\\d+(\\.\\d*)?|\\-?\\d+\\.\\d*|\\-?\\d+|[a-zA-Z_][a-zA-Z_0-9]*|-|'|\"|\\n|\\t|\\s{4}|\\s|."
    );

    private TokenType getTokenType(String tokenText) {
        switch (tokenText) {
            case "var": return TokenType.VAR;
            case "type": return TokenType.TYPE;
            case "routine": return TokenType.ROUTINE;
            case "is": return TokenType.IS;
            case "record": return TokenType.RECORD;
            case "array": return TokenType.ARRAY;
            case "while": return TokenType.WHILE;
            case "for": return TokenType.FOR;
            case "if": return TokenType.IF;
            case "else": return TokenType.ELSE;
            case "end": return TokenType.END;
            case "true": return TokenType.TRUE;
            case "false": return TokenType.FALSE;
            case "integer": return TokenType.INTEGER;
            case "real": return TokenType.REAL;
            case "boolean": return TokenType.BOOLEAN;
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
            case "'": return TokenType.SINGLE_QUOTE;
            case "\"": return TokenType.DOUBLE_QUOTE;
            default:
                if (tokenText.matches("\\s{4}")) return TokenType.TAB;
                if (tokenText.matches("\\s")) return TokenType.WHITESPACE;
                if (tokenText.matches("-?\\d+\\.\\d*")) return TokenType.NUMBER;
                if (tokenText.matches("-?\\d+")) return TokenType.NUMBER;
                if (tokenText.matches("[a-zA-Z_][a-zA-Z_0-9]*")) return TokenType.IDENTIFIER;
                return TokenType.UNKNOWN;
        }
    }

    public ArrayList<Token> tokenize(String input) {
        ArrayList<Token> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERN.matcher(input);

        int line = 1;
        int pos = 1;

        while (matcher.find()) {
            String tokenText = matcher.group();
            TokenType tokenType = getTokenType(tokenText);

            tokens.add(new Token(tokenType, tokenText, line, pos));

            if (tokenText.equals("\n")) {
                line++;
                pos = 1;
            } else {
                pos += tokenText.length();
            }
        }

        return tokens;
    }
}
