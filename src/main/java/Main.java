import ast.base.Program;
import java_cup.runtime.Symbol;
import lexer.Lexer;
import parser.parser;

import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        try {
            Lexer l = new Lexer(new FileReader("src/main/java/input.txt"));
            parser p = new parser(l);
            Symbol parseResult = p.parse();  // Parse and get the result as a Symbol

            // Extract the Program object from the Symbol's value
            Program result = (Program) parseResult.value;

            if (result.units() != null && !result.units().isEmpty()) {
                System.out.println("Список units успешно заполнен. Количество элементов: " + result.units().size());

                result.units().forEach(unit -> System.out.println(unit));
            } else {
                System.out.println("Список units пуст или не был инициализирован.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
