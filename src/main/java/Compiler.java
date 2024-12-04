import analyzer.SemanticAnalyzer;
import ast.base.Program;
import ast.visitor.ProgramVisitor;
import generator.JasminCodeGenerator;
import java_cup.runtime.Symbol;
import lexer.Lexer;
import lombok.SneakyThrows;
import parser.parser;

import java.io.FileReader;

public class Compiler {
    @SneakyThrows
    public void compile(String filename) {
        Lexer l = new Lexer(new FileReader(filename));
        parser p = new parser(l);
        Symbol parseResult = p.parse();

        Program result = (Program) parseResult.value;

        System.out.println("AST before applying optimizations:");
        ProgramVisitor originalVisitor = new ProgramVisitor();
        result.accept(originalVisitor);

        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        result = analyzer.analyze(result);

        System.out.println("AST after applying optimizations:");
        ProgramVisitor updatedVisitor = new ProgramVisitor();
        result.accept(updatedVisitor);

        JasminCodeGenerator javaCodeGenerator = new JasminCodeGenerator();
        javaCodeGenerator.visit(result);
    }
}
