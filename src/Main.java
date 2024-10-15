import main.lexer.Lexer;
import main.token.Token;
import main.ast.Program;
import main.parser.YYParser;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input = "var x: integer is 10;";

        try {
            Lexer lexer = new Lexer();
            lexer.tokenize(input);
            YYParser parser = new YYParser(lexer);
            if(parser.parse()) {
                System.out.println("---------------------\n");
            }
        } catch (Exception e) {
            System.err.println("Error while parsing: " + e.getMessage());
        }
    }
}
