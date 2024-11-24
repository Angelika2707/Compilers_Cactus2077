package generator;

import ast.base.Body;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JasminCodeGenerator implements Visitor {
    private int indentLevel = 0;
    private final BufferedWriter writer;
    private final Map<String, Variable> variables = new HashMap<>();
    private int index = 1;
    private Expression last = null;
    private final LabelGenerator labelGenerator = new LabelGenerator();
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
    private void decreaseIndent(Runnable block) {
        indentLevel--;
        block.run();
        indentLevel++;
    }

    @SneakyThrows
    @Override
    public void visit(Program program) {
        List<Function> functions = new ArrayList<>();
        writeIndented(".class public Program");
        writeIndented(".super java/lang/Object");
        writeIndented("");
        writeIndented(".method public static main([Ljava/lang/String;)V");
        increaseIndent(() -> {
            writeIndented(".limit stack 100");
            writeIndented(".limit locals 100");
            for (ProgramUnit unit : program.units()) {
                if (unit instanceof Function) {
                    functions.add((Function) unit);
                } else {
                    unit.accept(this);
                }
            }
            writeIndented("return");
        });
        writeIndented(".end method");
        increaseIndent(() -> {
            for (Function function : functions) {
                function.accept(this);
            }
        });

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
                case IntegerLiteral ignored -> {
                    variables.put(identifier, new Variable(index, new IntegerType()));
                    writeIndentedFormat("istore %d", index);
                }
                case RealLiteral ignored -> {
                    variables.put(identifier, new Variable(index, new RealType()));
                    writeIndentedFormat("fstore %d", index);
                }
                case BooleanLiteral ignored -> {
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
                        case IntegerType ignored -> writeIndented("newarray int");
                        case RealType ignored -> writeIndented("newarray float");
                        case BooleanType ignored -> writeIndented("newarray boolean");
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
                    case IntegerType ignored -> writeIndentedFormat("iastore");
                    case RealType ignored -> writeIndentedFormat("fastore");
                    case BooleanType ignored -> writeIndentedFormat("bastore");
                    case IdentifierType ignored -> writeIndentedFormat("aastore");
                    default -> {}
                }
            } else {
                assignmentStatement.expression().accept(this);
                int idx = variables.get(identifier).index();
                Type type = variables.get(identifier).varInfo();

                switch (last) {
                    case IntegerLiteral i -> {
                        // int -> float
                        if (type instanceof RealType) writeIndented("i2f");
                        else if (type instanceof BooleanType && !(i.value() == 1 || i.value() == 0))
                            throw new IllegalArgumentException
                                    ("Invalid assignment: cannot convert value " + i.value() + " to boolean.");
                    }
                    case RealLiteral r -> {
                        // float -> int
                        if (type instanceof IntegerType) writeIndented("f2i");
                        else if (type instanceof BooleanType)
                            throw new IllegalArgumentException
                                    ("Invalid assignment: cannot convert value of type real to boolean.");
                    }
                    case BooleanLiteral b -> {
                        // int -> float
                        if (type instanceof RealType) writeIndented("i2f");
                    }
                    case NestedRecordAccess n -> {
                        int idxN = variables.get(n.identifier()).index();
                        Type typeN = variables.get(n.identifier()).varInfo();
                        if (n.nestedAccess() == null) {
                            switch (typeN) {
                                case IntegerType ignored -> writeIndentedFormat("iload %d", idxN);
                                case RealType ignored -> writeIndentedFormat("fload %d", idxN);
                                case BooleanType ignored -> writeIndentedFormat("iload %d", idxN);
                                case ArrayType ignored -> writeIndentedFormat("aload %d", idxN);
                                case IdentifierType ignored -> writeIndentedFormat("aload %d", idxN);
                                default -> {}
                            }
                            n.accept(this);
                        } else {

                        }

                        if (type instanceof IntegerType && typeN instanceof RealType) {
                            writeIndented("f2i"); // float -> int
                        } else if (type instanceof RealType && typeN instanceof IntegerType) {
                            writeIndented("i2f"); // int -> float
                        } else if (type instanceof RealType && typeN instanceof BooleanType) {
                            writeIndented("i2f"); // int -> float
                        } else if (type instanceof BooleanType && typeN instanceof RealType) {
                            throw new IllegalArgumentException
                                    ("Invalid assignment: cannot convert value of type real to boolean.");
                        }
                    }
                    default -> {}
                }

                switch (type) {
                    case IntegerType ignored -> writeIndentedFormat("istore %d", idx);
                    case RealType ignored -> writeIndentedFormat("fstore %d", idx);
                    case BooleanType ignored -> writeIndentedFormat("istore %d", idx);
                    case IdentifierType ignored -> {}
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
    public void visit(Function function) {
        int funcVarIndex = index;
        index = 1;

        List<String> varsToDelete = new ArrayList<>();
        decreaseIndent(() -> {
            writeIndentedNotNStart(".method public " + function.identifier() + "(");
        });

        for (Parameter parameter: function.params()) {
            parameter.accept(this);
            variables.put(parameter.identifier(), new Variable(index++, parameter.type()));
            varsToDelete.add(parameter.identifier());
        }
        writeIndentedNotN(")");
        if (function.returnType() == null) {
            writeIndentedNotNEnd("V");
        } else {
            switch (function.returnType()) {
                case IntegerType i -> writeIndentedNotNEnd("I");
                case RealType r -> writeIndentedNotNEnd("F");
                case BooleanType b -> writeIndentedNotNEnd("Z");
                case IdentifierType i -> writeIndentedNotN("L");
                case ArrayType a -> {
                    writeIndentedNotN("[");
                    switch (a.elementType()) {
                        case IntegerType i -> writeIndentedNotNEnd("I");
                        case RealType r -> writeIndentedNotNEnd("F");
                        case BooleanType b -> writeIndentedNotNEnd("Z");
                        case IdentifierType i -> writeIndentedNotNEnd(i.identifier() + ";");
                        default -> {}
                    }
                }
                default -> {}
            }
        }

        writeIndented(".limit stack 50");
        writeIndented(".limit locals 50");

        for (Body el: function.body()) {
            if (el instanceof VariableDeclaration) {
                varsToDelete.add(((VariableDeclaration) el).id());
            } else if (el instanceof TypeDeclaration) {
            }
            el.accept(this);
        }

        if (function.returnType() == null) writeIndented("return");

        decreaseIndent(() -> {
            writeIndented(".end method");
        });

        for (String key: varsToDelete) {
            variables.remove(key);
        }

        index = funcVarIndex;
    }

    @Override
    public void visit(Parameter parameter) {
        switch (parameter.type()) {
            case IntegerType i -> writeIndentedNotN("I");
            case RealType r -> writeIndentedNotN("F");
            case BooleanType b -> writeIndentedNotN("Z");
            case IdentifierType i -> writeIndentedNotN("L");
            case ArrayType a -> {
                writeIndentedNotN("[");
                switch (a.elementType()) {
                    case IntegerType i -> writeIndentedNotN("I");
                    case RealType r -> writeIndentedNotN("F");
                    case BooleanType b -> writeIndentedNotN("Z");
                    case IdentifierType i -> writeIndentedNotN(i.identifier() + ";");
                    default -> {}
                }
            }
            default -> {}
        }
    }

    @Override
    public void visit(ArrayAccessExpression arrayAccessExpression) {
        String identifier = arrayAccessExpression.identifier();
        int arrLink = variables.get(identifier).index();
        writeIndentedFormat("aload %d", arrLink);
        arrayAccessExpression.index().accept(this);
        ArrayType arrType = (ArrayType) variables.get(identifier).varInfo();
        switch (arrType.elementType()) {
            case IntegerType ignored -> writeIndentedFormat("iaload");
            case RealType ignored -> writeIndentedFormat("faload");
            case BooleanType ignored -> writeIndentedFormat("baload");
            case IdentifierType ignored -> writeIndentedFormat("aaload");
            default -> {}
        }
    }

    @Override
    public void visit(FunctionCallExpression functionCallExpression) {

    }

    @Override
    public void visit(BinaryExpression binaryExpression) {}

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
    public void visit(ModExpression modExpression) {
        modExpression.left().accept(this);
        modExpression.right().accept(this);
        writeIndented("irem");
    }

    @Override
    public void visit(EqualExpression equalExpression) {
        equalExpression.left().accept(this);
        equalExpression.right().accept(this);
        String labelTrue = labelGenerator.generate("Label_True", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeIndentedFormat("if_icmpeq %s", labelTrue);
        writeIndented("iconst_0");
        writeIndentedFormat("goto %s", labelEnd);
        writeIndentedFormat("%s:", labelTrue);
        writeIndented("iconst_1");
        writeIndentedFormat("%s:", labelEnd);
    }

    @Override
    public void visit(NotEqualExpression notEqualExpression) {
        notEqualExpression.left().accept(this);
        notEqualExpression.right().accept(this);
        String labelTrue = labelGenerator.generate("Label_True", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeIndentedFormat("if_icmpne %s", labelTrue);
        writeIndented("iconst_0");
        writeIndentedFormat("goto %s", labelEnd);
        writeIndentedFormat("%s:", labelTrue);
        writeIndented("iconst_1");
        writeIndentedFormat("%s:", labelEnd);
    }

    @Override
    public void visit(GreaterEqualExpression greaterEqualExpression) {
        greaterEqualExpression.left().accept(this);
        greaterEqualExpression.right().accept(this);
        String labelTrue = labelGenerator.generate("Label_True", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeIndentedFormat("if_icmpge %s", labelTrue);
        writeIndented("iconst_0");
        writeIndentedFormat("goto %s", labelEnd);
        writeIndentedFormat("%s:", labelTrue);
        writeIndented("iconst_1");
        writeIndentedFormat("%s:", labelEnd);
    }

    @Override
    public void visit(GreaterThanExpression greaterThanExpression) {
        greaterThanExpression.left().accept(this);
        greaterThanExpression.right().accept(this);
        String labelTrue = labelGenerator.generate("Label_True", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeIndentedFormat("if_icmpgt %s", labelTrue);
        writeIndented("iconst_0");
        writeIndentedFormat("goto %s", labelEnd);
        writeIndentedFormat("%s:", labelTrue);
        writeIndented("iconst_1");
        writeIndentedFormat("%s:", labelEnd);
    }

    @Override
    public void visit(LessEqualExpression lessEqualExpression) {
        lessEqualExpression.left().accept(this);
        lessEqualExpression.right().accept(this);
        String labelTrue = labelGenerator.generate("Label_True", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeIndentedFormat("if_icmple %s", labelTrue);
        writeIndented("iconst_0");
        writeIndentedFormat("goto %s", labelEnd);
        writeIndentedFormat("%s:", labelTrue);
        writeIndented("iconst_1");
        writeIndentedFormat("%s:", labelEnd);
    }

    @Override
    public void visit(LessThanExpression lessThanExpression) {
        lessThanExpression.left().accept(this);
        lessThanExpression.right().accept(this);
        String labelTrue = labelGenerator.generate("Label_True", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeIndentedFormat("if_icmplt %s", labelTrue);
        writeIndented("iconst_0");
        writeIndentedFormat("goto %s", labelEnd);
        writeIndentedFormat("%s:", labelTrue);
        writeIndented("iconst_1");
        writeIndentedFormat("%s:", labelEnd);
    }

    @Override
    public void visit(AndExpression andExpression) {
        andExpression.left().accept(this);
        andExpression.right().accept(this);
        writeIndented("iand");
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
    public void visit(XorExpression xorExpression) {
        xorExpression.left().accept(this);
        xorExpression.right().accept(this);
        writeIndented("ixor");
    }

    @Override
    public void visit(NestedRecordAccess nestedRecordAccess) {
        last = nestedRecordAccess;
    }

    @Override
    public void visit(IntegerLiteral integerLiteral) {
        writeIndentedNotNStart("ldc ");
        last = integerLiteral;
        writeIndentedNotNEnd(String.valueOf(integerLiteral.value()));
    }

    @Override
    public void visit(RealLiteral realLiteral) {
        writeIndentedNotNStart("ldc ");
        last = realLiteral;
        writeIndentedNotNEnd(String.valueOf(realLiteral.value()));
    }

    @Override
    public void visit(BooleanLiteral booleanLiteral) {
        writeIndentedNotNStart("iconst_");
        last = booleanLiteral;
        writeIndentedNotNEnd(String.valueOf(booleanLiteral.value() ? 1 : 0));
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

    private static class LabelGenerator {
        private int counter = 0;

        private String generate(String baseLabel, boolean inc) {
            return baseLabel + "_" + (inc ? counter++ : counter);
        }
    }
}
