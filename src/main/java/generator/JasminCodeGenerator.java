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
    private Expression last = null;
    private boolean isNestedRecordRight = false;
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
            switch (last) {
                case IntegerLiteral i -> {
                    variables.put(identifier, new Variable(index, new IntegerType()));
                    writeIndentedFormat("istore %d", index);
                }
                case RealLiteral r -> {
                    variables.put(identifier, new Variable(index, new RealType()));
                    writeIndentedFormat("fstore %d", index);
                }
                case BooleanLiteral b -> {
                    variables.put(identifier, new Variable(index, new BooleanType()));
                    writeIndentedFormat("istore %d", index);
                }
                default -> {}
            }
            index++;
        } else if (expression == null) {
            switch (type) {
                case IntegerType intType -> {
                    variables.put(identifier, new Variable(index, intType));
                    index++;
                }
                case RealType realType -> {
                    variables.put(identifier, new Variable(index, realType));
                    index++;
                }
                case BooleanType booleanType -> {
                    variables.put(identifier, new Variable(index, booleanType));
                    index++;
                }
                case ArrayType arrayType -> {
                    variables.put(identifier, new Variable(index, arrayType));
                    writeIndentedFormat("ldc %d", arrayType.size());
                    switch (arrayType.elementType()) {
                        case IntegerType intType -> writeIndented("newarray int");
                        case RealType realType -> writeIndented("newarray float");
                        case BooleanType booleanType -> writeIndented("newarray boolean");
                        case IdentifierType identifierType -> {
                            writeIndentedFormat("anewarray %s", identifierType.identifier());
                        }
                        default -> {}
                    }
                    writeIndentedFormat("astore %d", index);
                    index += arrayType.size() + 1;
                }
                default -> {}
            }

        } else {
            switch (type) {
                case IntegerType intType -> {
                    variables.put(identifier, new Variable(index, intType));
                    expression.accept(this);
                    writeIndentedFormat("istore %d", index);
                    writeIndentedFormat(".var %d is %s I", index, identifier);
                    index++;
                }
                case RealType realType -> {
                    variables.put(identifier, new Variable(index, realType));
                    expression.accept(this);
                    writeIndentedFormat("fstore %d", index);
                    writeIndentedFormat(".var %d is %s F", index, identifier);
                    index++;
                }
                case BooleanType booleanType -> {
                    variables.put(identifier, new Variable(index, booleanType));
                    expression.accept(this);
                    writeIndentedFormat("istore %d", index);
                    writeIndentedFormat(".var %d is %s Z", index, identifier);
                    index++;
                }
                default -> {}
            }
        }
    }

    @Override
    public void visit(TypeDeclaration typeDeclaration) {

    }

    @Override
    public void visit(AssignmentStatement assignmentStatement) {
        if (assignmentStatement.recordField() == null) {
            String identifier = assignmentStatement.identifier();
            if (assignmentStatement.index() != null) {
                int arrLink = variables.get(identifier).index();
                writeIndentedFormat("aload %d", arrLink);
                assignmentStatement.index().accept(this);
                assignmentStatement.expression().accept(this);
                ArrayType arrType = (ArrayType) variables.get(identifier).varInfo();
                switch (arrType.elementType()) {
                    case IntegerType intType -> writeIndentedFormat("iastore");
                    case RealType realType -> writeIndentedFormat("fstore");
                    case BooleanType booleanType -> writeIndentedFormat("bastore");
                    case IdentifierType identifierType -> writeIndentedFormat("aastore");
                    default -> {}
                }
            } else {
                assignmentStatement.expression().accept(this);
                int idx = variables.get(identifier).index();
                Type type = variables.get(identifier).varInfo();

                switch (last) {
                    case IntegerLiteral i -> {}
                    case RealLiteral r -> {}
                    case BooleanLiteral b -> {}
                    case NestedRecordAccess n -> {
                        if (n.nestedAccess() == null) {
                            int idxN = variables.get(n.identifier()).index();
                            Type typeN = variables.get(n.identifier()).varInfo();
                            switch (typeN) {
                                case IntegerType intType -> writeIndentedFormat("iload %d", idxN);
                                case RealType realType -> writeIndentedFormat("fload %d", idxN);
                                case BooleanType booleanType -> writeIndentedFormat("iload %d", idxN);
                                case ArrayType arrayType -> writeIndentedFormat("aload %d", idxN);
                                case IdentifierType identifierType -> writeIndentedFormat("aload %d", idxN);
                                default -> {}
                            }
                            n.accept(this);
                        } else {

                        }
                    }
                    default -> {}
                }

                switch (type) {
                    case IntegerType intType -> writeIndentedFormat("istore %d", idx);
                    case RealType realType -> writeIndentedFormat("fstore %d", idx);
                    case BooleanType booleanType -> writeIndentedFormat("istore %d", idx);
                    case IdentifierType identifierType -> {}
                    default -> {}
                }
            }
        } else {

        }
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
        last = nestedRecordAccess;
    }

    @Override
    public void visit(AndExpression andExpression) {
        andExpression.left().accept(this);
        andExpression.right().accept(this);
        writeIndented("iand");
    }

    @Override
    public void visit(ArrayAccessExpression arrayAccessExpression) {

    }

    @Override
    public void visit(BinaryExpression binaryExpression) {

    }

    @Override
    public void visit(BooleanLiteral booleanLiteral) {
        writeIndentedNotNStart("iconst_");
        last = booleanLiteral;
        writeIndentedNotNEnd(String.valueOf(booleanLiteral.value() ? 1 : 0));
    }

    @Override
    public void visit(DivExpression divExpression) {
        divExpression.left().accept(this);
        divExpression.right().accept(this);

        if (last instanceof IntegerLiteral) {
            writeIndented("idiv");
        } else if (last instanceof RealLiteral) {
            writeIndented("fdiv");
        }
    }

    @Override
    public void visit(EqualExpression equalExpression) {
        equalExpression.left().accept(this);
        equalExpression.right().accept(this);
        writeIndented("if_icmpeq Label_True");
        writeIndented("iconst_0");
        writeIndented("goto Label_End");
        writeIndented("Label_True:");
        writeIndented("iconst_1");
        writeIndented("Label_End:");
    }

    @Override
    public void visit(FunctionCallExpression functionCallExpression) {

    }

    @Override
    public void visit(GreaterEqualExpression greaterEqualExpression) {
        greaterEqualExpression.left().accept(this);
        greaterEqualExpression.right().accept(this);
        writeIndented("if_icmpge Label_True");
        writeIndented("iconst_0");
        writeIndented("goto Label_End");
        writeIndented("Label_True:");
        writeIndented("iconst_1");
        writeIndented("Label_End:");
    }

    @Override
    public void visit(GreaterThanExpression greaterThanExpression) {
        greaterThanExpression.left().accept(this);
        greaterThanExpression.right().accept(this);
        writeIndented("if_icmpgt Label_True");
        writeIndented("iconst_0");
        writeIndented("goto Label_End");
        writeIndented("Label_True:");
        writeIndented("iconst_1");
        writeIndented("Label_End:");
    }

    @Override
    public void visit(IntegerLiteral integerLiteral) {
        writeIndentedNotNStart("ldc ");
        last = integerLiteral;
        writeIndentedNotNEnd(String.valueOf(integerLiteral.value()));
    }

    @Override
    public void visit(LessEqualExpression lessEqualExpression) {
        lessEqualExpression.left().accept(this);
        lessEqualExpression.right().accept(this);
        writeIndented("if_icmple Label_True");
        writeIndented("iconst_0");
        writeIndented("goto Label_End");
        writeIndented("Label_True:");
        writeIndented("iconst_1");
        writeIndented("Label_End:");
    }

    @Override
    public void visit(LessThanExpression lessThanExpression) {
        lessThanExpression.left().accept(this);
        lessThanExpression.right().accept(this);
        writeIndented("if_icmplt Label_True");
        writeIndented("iconst_0");
        writeIndented("goto Label_End");
        writeIndented("Label_True:");
        writeIndented("iconst_1");
        writeIndented("Label_End:");
    }

    @Override
    public void visit(MinusExpression minusExpression) {
        minusExpression.left().accept(this);
        minusExpression.right().accept(this);

        if (last instanceof IntegerLiteral) {
            writeIndented("isub");
        } else if (last instanceof RealLiteral) {
            writeIndented("fsub");
        }
    }

    @Override
    public void visit(ModExpression modExpression) {
        modExpression.left().accept(this);
        modExpression.right().accept(this);
        writeIndented("irem");
    }

    @Override
    public void visit(MulExpression mulExpression) {
        mulExpression.left().accept(this);
        mulExpression.right().accept(this);

        if (last instanceof IntegerLiteral) {
            writeIndented("imul");
        } else if (last instanceof RealLiteral) {
            writeIndented("fmul");
        }
    }

    @Override
    public void visit(NotEqualExpression notEqualExpression) {
        notEqualExpression.left().accept(this);
        notEqualExpression.right().accept(this);
        writeIndented("if_icmpne Label_True");
        writeIndented("iconst_0");
        writeIndented("goto Label_End");
        writeIndented("Label_True:");
        writeIndented("iconst_1");
        writeIndented("Label_End:");
    }

    @Override
    public void visit(NotExpression notExpression) {
        notExpression.expr().accept(this);
        writeIndented("iconst_1");
        writeIndented("ixor");
    }

    @Override
    public void visit(OrExpression orExpression) {
        orExpression.left().accept(this);
        orExpression.right().accept(this);
        writeIndented("ior");
    }

    @Override
    public void visit(ParenthesizedExpression parenthesizedExpression) {
        parenthesizedExpression.expr().accept(this);
    }

    @Override
    public void visit(PlusExpression plusExpression) {
        plusExpression.left().accept(this);
        plusExpression.right().accept(this);

        if (last instanceof IntegerLiteral) {
            writeIndented("iadd");
        } else if (last instanceof RealLiteral) {
            writeIndented("fadd");
        }
    }

    @Override
    public void visit(RealLiteral realLiteral) {
        writeIndentedNotNStart("ldc ");
        last = realLiteral;
        writeIndentedNotNEnd(String.valueOf(realLiteral.value()));
    }

    @Override
    public void visit(XorExpression xorExpression) {
        xorExpression.left().accept(this);
        xorExpression.right().accept(this);
        writeIndented("ixor");
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
