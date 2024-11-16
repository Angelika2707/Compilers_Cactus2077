import analyzer.SemanticAnalyzer;
import ast.base.Program;
import ast.visitor.ProgramVisitor;
import java_cup.runtime.Symbol;
import lexer.Lexer;
import parser.parser;

import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        try {
            Lexer l = new Lexer(new FileReader("src/main/java/input.txt"));
            parser p = new parser(l);
            Symbol parseResult = p.parse();

            Program result = (Program) parseResult.value;

            if (result.units() != null && !result.units().isEmpty()) {
                System.out.println("Список units успешно заполнен. Количество элементов: " + result.units().size());

                ProgramVisitor originalVisitor = new ProgramVisitor();
                result.accept(originalVisitor);
                SemanticAnalyzer analyzer = new SemanticAnalyzer();
                result = analyzer.analyze(result);

                ProgramVisitor updatedVisitor = new ProgramVisitor();
                result.accept(updatedVisitor);
            } else {
                System.out.println("Список units пуст или не был инициализирован.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

