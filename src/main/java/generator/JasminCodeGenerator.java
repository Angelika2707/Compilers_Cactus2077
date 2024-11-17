package generator;

import ast.base.Program;
import ast.base.ProgramUnit;
import ast.declaration.TypeDeclaration;
import ast.declaration.VariableDeclaration;
import ast.expression.*;
import ast.function.Function;
import ast.function.Parameter;
import ast.statement.*;
import ast.type.*;
import ast.visitor.Visitor;
import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JasminCodeGenerator implements Visitor {
    private int indentLevel = 0;
    private final BufferedWriter writer;
    private final Map<String, Variable> variables = new HashMap<>();
    private int index = 1;
 //   private final Path outputDir = Paths.get("src", "main", "resources", "generator");

    @SneakyThrows
    public JasminCodeGenerator() {
        /*
        Path outputPath = outputDir.resolve("program.j");
        Files.createDirectories(outputPath.getParent());
        writer = new BufferedWriter(new FileWriter(outputPath.toFile()));

         */
        writer = new BufferedWriter(new FileWriter("program.j"));
    }

    @SneakyThrows
    private void writeIndented(String text) {
        for (int i = 0; i < indentLevel; i++) {
            writer.write("    ");
        }
        writer.write(text);
        writer.newLine();
    }

    @SneakyThrows
    private void writeIndentedNotN(String text) {
        writer.write(text);
    }

    @SneakyThrows
    private void writeIndentedNotNStart(String text) {
        for (int i = 0; i < indentLevel; i++) {
            writer.write("    ");
        }
        writer.write(text);
    }

    @SneakyThrows
    private void writeIndentedNotNEnd(String text) {
        writer.write(text);
        writer.newLine();
    }

    @SneakyThrows
    private void writeIndentedFormat(String format, Object... args) {
        for (int i = 0; i < indentLevel; i++) {
            writer.write("    ");
        }
        String formattedText = String.format(format, args);
        writer.write(formattedText);
        writer.newLine();
    }

    @SneakyThrows
    private void increaseIndent(Runnable block) {
        indentLevel++;
        block.run();
        indentLevel--;
    }

    @SneakyThrows
    @Override
    public void visit(Program program) {
        writeIndented(".class public Program");
        writeIndented(".super java/lang/Object");
        writeIndented("");
        writeIndented(".method public static main([Ljava/lang/String;)V");
        increaseIndent(() -> {
            writeIndented(".limit stack 100");
            writeIndented(".limit locals 100");
            for (ProgramUnit unit : program.units()) {
                unit.accept(this);
            }
            writeIndented("return");
        });
        writeIndented(".end method");
        writer.close();
        compileJasminToJava();
    }

    @SneakyThrows
    private void compileJasminToJava() {
        String jasminPath = "\"C:\\Program Files\\jasmin-2.4\\jasmin.jar\"";
     //   Path programFilePath = outputDir.resolve("program.j");
        Path programFilePath = Paths.get("program.j");
        String command = String.format("java -jar %s %s", jasminPath, programFilePath);
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }

    @Override
    public void visit(VariableDeclaration variableDeclaration) {
        Type type = variableDeclaration.type();
        String identifier = variableDeclaration.id();
        Expression expression = variableDeclaration.expression();

        if (type == null) {
            variables.put(identifier, new Variable(index, null));
            writeIndentedNotNStart("ldc ");
            expression.accept(this);
            writeIndentedFormat("istore %d", index);
            writeIndentedFormat(".var %d is %s", index, identifier);
            index++;
        } else if (expression == null) {
            switch (type) {
                case IntegerType intType -> variables.put(identifier, new Variable(index, intType));
                case RealType realType -> variables.put(identifier, new Variable(index, realType));
                case BooleanType booleanType -> variables.put(identifier, new Variable(index, booleanType));
                default -> {}
            }
            writeIndentedFormat(".var %d is %s", index, identifier);
            index++;
        } else {
            switch (type) {
                case IntegerType intType -> {
                    variables.put(identifier, new Variable(index, intType));
                    writeIndentedNotNStart("ldc ");
                    expression.accept(this);
                    writeIndentedFormat("istore %d", index);
                    writeIndentedFormat(".var %d is %s I", index, identifier);
                }
                case RealType realType -> {
                    variables.put(identifier, new Variable(index, realType));
                    writeIndentedNotNStart("ldc ");
                    expression.accept(this);
                    writeIndentedFormat("fstore %d", index);
                    writeIndentedFormat(".var %d is %s F", index, identifier);
                }
                case BooleanType booleanType -> {
                    variables.put(identifier, new Variable(index, booleanType));
                    writeIndentedNotNStart("ldc ");
                    expression.accept(this);
                    writeIndentedFormat("istore %d", index);
                    writeIndentedFormat(".var %d is %s I", index, identifier);
                }
                default -> {}
            }
            index++;
        }
    }

    @Override
    public void visit(TypeDeclaration typeDeclaration) {

    }

    @Override
    public void visit(AssignmentStatement assignmentStatement) {

    }

    @Override
    public void visit(CallStatement callStatement) {

    }

    @Override
    public void visit(ForStatement forStatement) {

    }

    @Override
    public void visit(IfStatement ifStatement) {

    }

    @Override
    public void visit(ReturnStatement returnStatement) {

    }

    @Override
    public void visit(WhileStatement whileStatement) {

    }

    @Override
    public void visit(NestedRecordAccess nestedRecordAccess) {

    }

    @Override
    public void visit(AndExpression andExpression) {

    }

    @Override
    public void visit(ArrayAccessExpression arrayAccessExpression) {

    }

    @Override
    public void visit(BinaryExpression binaryExpression) {

    }

    @Override
    public void visit(BooleanLiteral booleanLiteral) {
        writeIndentedNotNEnd(String.valueOf(booleanLiteral.value() ? 1 : 0));
    }

    @Override
    public void visit(DivExpression divExpression) {

    }

    @Override
    public void visit(EqualExpression equalExpression) {

    }

    @Override
    public void visit(FunctionCallExpression functionCallExpression) {

    }

    @Override
    public void visit(GreaterEqualExpression greaterEqualExpression) {

    }

    @Override
    public void visit(GreaterThanExpression greaterThanExpression) {

    }

    @Override
    public void visit(IntegerLiteral integerLiteral) {
        writeIndentedNotNEnd(String.valueOf(integerLiteral.value()));
    }

    @Override
    public void visit(LessEqualExpression lessEqualExpression) {

    }

    @Override
    public void visit(LessThanExpression lessThanExpression) {

    }

    @Override
    public void visit(MinusExpression minusExpression) {

    }

    @Override
    public void visit(ModExpression modExpression) {

    }

    @Override
    public void visit(MulExpression mulExpression) {

    }

    @Override
    public void visit(NotEqualExpression notEqualExpression) {

    }

    @Override
    public void visit(NotExpression notExpression) {

    }

    @Override
    public void visit(OrExpression orExpression) {

    }

    @Override
    public void visit(ParenthesizedExpression parenthesizedExpression) {

    }

    @Override
    public void visit(PlusExpression plusExpression) {

    }

    @Override
    public void visit(RealLiteral realLiteral) {
        writeIndentedNotNEnd(String.valueOf(realLiteral.value()));
    }

    @Override
    public void visit(XorExpression xorExpression) {

    }

    @Override
    public void visit(Function function) {

    }

    @Override
    public void visit(Parameter parameter) {

    }

    @Override
    public void visit(ArrayType arrayType) {

    }

    @Override
    public void visit(BooleanType booleanType) {

    }

    @Override
    public void visit(IdentifierType identifierType) {

    }

    @Override
    public void visit(IntegerType integerType) {
    }

    @Override
    public void visit(RealType realType) {

    }

    @Override
    public void visit(RecordType recordType) {

    }
}
