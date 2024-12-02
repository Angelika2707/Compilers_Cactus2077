package generator;

import ast.base.Body;
import ast.base.Program;
import ast.base.ProgramUnit;
import ast.declaration.Declaration;
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
    private final Map<String, String> funcSignatures = new HashMap<>();
    private Function currentFunction = null;
    private final LabelGenerator labelGenerator = new LabelGenerator();
    private String recordName = null;
    private final Map<String, Map<String, Type>> records = new HashMap<>();
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
    private void writeRecord(BufferedWriter bufferedWriter, String format, Object... args) {
        for (int i = 0; i < indentLevel; i++) {
            bufferedWriter.write("    ");
        }
        String formattedText = String.format(format, args);
        bufferedWriter.write(formattedText);
        bufferedWriter.newLine();
    }

    @SneakyThrows
    @Override
    public void visit(Program program) {
        List<Function> functions = new ArrayList<>();
        writeIndented(".class public Program");
        writeIndented(".super java/lang/Object");
        writeIndented("");
        for (ProgramUnit unit : program.units()) {
            if (unit instanceof Function) {
                functions.add((Function) unit);
            }
        }
        increaseIndent(() -> {
            for (Function function : functions) {
                function.accept(this);
            }
        });
        writeIndented("");
        writeIndented(".method public static main([Ljava/lang/String;)V");
        increaseIndent(() -> {
            writeIndented(".limit stack 100");
            writeIndented(".limit locals 100");
            for (ProgramUnit unit : program.units()) {
                if (!(unit instanceof Function)) {
                    unit.accept(this);
                }
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
        if (recordName != null) {
            records.get(recordName).put(identifier, type);
        }

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
                default -> {
                }
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
                        default -> {
                        }
                    }
                    writeIndentedFormat("astore %d", index);
                    index += arrayType.size() + 1;
                }
                default -> {
                }
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
                default -> {
                }
            }
        }
    }

    @Override
    public void visit(TypeDeclaration typeDeclaration) {
        Type type = typeDeclaration.type();
        String identifier = typeDeclaration.id();
        switch (type) {
            case RecordType r -> {
                records.put(identifier, new HashMap<>());
                recordName = identifier;
                for (Declaration field : r.fields()) {
                    field.accept(this);
                }
                recordName = null;
                createRecord(identifier);
            }
            default -> {
            }
        }
    }

    @SneakyThrows
    private void createRecord(String name) {
        String fileName = name + ".j";

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        // Записываем заголовок класса
        writer.write(".class public " + name);
        writer.newLine();
        writer.write(".super java/lang/Object");
        writer.newLine();
        writer.newLine();

        Map<String, Type> fields = records.get(name);

        // Генерация полей
        for (String field : fields.keySet()) {
            Type type = fields.get(field);
            switch (type) {
                case IntegerType i -> {
                    decreaseIndent(() -> writeRecord(writer, ".field public %s I", field));
                }
                case RealType r -> {
                    decreaseIndent(() -> writeRecord(writer, ".field public %s F", field));
                }
                case BooleanType b -> {
                    decreaseIndent(() -> writeRecord(writer, ".field public %s Z", field));
                }
                case IdentifierType i -> {
                    decreaseIndent(() -> writeRecord(writer, ".field public %s L%s;", field, i.identifier()));
                }
                case ArrayType a -> {
                    Type elementType = a.elementType();

                    switch (elementType) {
                        case IntegerType i -> {
                            decreaseIndent(() -> writeRecord(writer, ".field public %s [I;", field));
                        }
                        case RealType r -> {
                            decreaseIndent(() -> writeRecord(writer, ".field public %s [F;", field));
                        }
                        case BooleanType b -> {
                            decreaseIndent(() -> writeRecord(writer, ".field public %s [Z;", field));
                        }
                        case IdentifierType i -> {
                            decreaseIndent(() -> writeRecord(writer, ".field public %s [L%s;", field, i.identifier()));
                        }
                        default -> {}
                    }
                }
                default -> {
                }
            }
        }

        writer.newLine();
        writer.close();
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
                    default -> {
                    }
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
                                default -> {
                                }
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
                    default -> {
                    }
                }

                switch (type) {
                    case IntegerType ignored -> writeIndentedFormat("istore %d", idx);
                    case RealType ignored -> writeIndentedFormat("fstore %d", idx);
                    case BooleanType ignored -> writeIndentedFormat("istore %d", idx);
                    case IdentifierType ignored -> {
                    }
                    default -> {
                    }
                }
            }
        } else {

        }
    }

    @Override
    public void visit(CallStatement callStatement) {
        for (Expression e : callStatement.paramList()) {
            e.accept(this);
        }
        writeIndented("invokestatic Program/" + funcSignatures.get(callStatement.identifier()));
    }

    @Override
    public void visit(ForStatement forStatement) {
        String labelStart = labelGenerator.generate("Label_Start", false);
        String labelCondition = labelGenerator.generate("Label_Condition", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        if (forStatement.isReverse()) {
            forStatement.endExpression().accept(this);
        } else {
            forStatement.startExpression().accept(this);
        }
        writeIndentedFormat("istore %d", index);
        variables.put(forStatement.loopVariable(), new Variable(index++, new IntegerType()));
        writeIndentedFormat("%s:", labelCondition);
        writeIndentedFormat("iload %d", variables.get(forStatement.loopVariable()).index());
        if (forStatement.isReverse()) {
            forStatement.startExpression().accept(this);
        } else {
            forStatement.endExpression().accept(this);
        }
        if (forStatement.isReverse()) {
            writeIndentedFormat("if_icmplt %s", labelEnd);
        } else {
            writeIndentedFormat("if_icmpgt %s", labelEnd);
        }
        writeIndentedFormat("%s:", labelStart);
        for (Body el : forStatement.body()) {
            el.accept(this);
        }

        if (forStatement.isReverse()) {
            writeIndentedFormat("iinc %d -1", variables.get(forStatement.loopVariable()).index());
        } else {
            writeIndentedFormat("iinc %d 1", variables.get(forStatement.loopVariable()).index());
        }
        writeIndentedFormat("goto %s", labelCondition);
        writeIndentedFormat("%s:", labelEnd);
        variables.remove(forStatement.loopVariable());
    }

    @Override
    public void visit(IfStatement ifStatement) {
        ifStatement.condition().accept(this);
        String labelElse = labelGenerator.generate("Label_Else", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        if (ifStatement.elseBody().isEmpty()) {
            writeIndentedFormat("ifeq %s", labelEnd); // if else does not exist
        } else {
            writeIndentedFormat("ifeq %s", labelElse); // if 0
        }
        for (Body el : ifStatement.thenBody()) {
            el.accept(this);
        }
        writeIndentedFormat("goto %s", labelEnd);
        writeIndentedFormat("%s:", labelElse);
        for (Body el : ifStatement.elseBody()) {
            el.accept(this);
        }
        writeIndentedFormat("%s:", labelEnd);
    }

    @Override
    public void visit(ReturnStatement returnStatement) {
        returnStatement.returnExpression().accept(this);
        String funcSignature = funcSignatures.get(currentFunction.identifier());
        String returnType = funcSignature.split("\\)")[1];
        if (returnType.length() > 1 && (returnType.startsWith("[") || returnType.startsWith("L"))) {
            writeIndented("areturn");
        }
        switch (returnType) {
            case "I", "Z" -> writeIndented("ireturn");
            case "F" -> writeIndented("freturn");
            default -> {
            }
        }
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        String labelStart = labelGenerator.generate("Label_Start", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeIndentedFormat("%s:", labelStart);
        whileStatement.condition().accept(this);
        writeIndentedFormat("ifeq %s", labelEnd);
        for (Body el : whileStatement.body()) {
            el.accept(this);
        }
        writeIndentedFormat("goto %s", labelStart);
        writeIndentedFormat("%s:", labelEnd);
    }

    @Override
    public void visit(Function function) {
        currentFunction = function;
        int funcVarIndex = index;
        index = 0;

        List<String> varsToDelete = new ArrayList<>();
        decreaseIndent(() -> {
            writeIndentedNotNStart(".method public static " + function.identifier() + "(");
        });

        funcSignatures.put(function.identifier(), function.identifier() + "(");

        for (Parameter parameter : function.params()) {
            parameter.accept(this);
            variables.put(parameter.identifier(), new Variable(index++, parameter.type()));
            varsToDelete.add(parameter.identifier());
        }
        writeIndentedNotN(")");

        String stringType = "";
        if (function.returnType() == null) {
            writeIndentedNotNEnd("V");
            stringType = "V";
        } else {
            switch (function.returnType()) {
                case IntegerType i -> {
                    writeIndentedNotNEnd("I");
                    stringType = "I";
                }
                case RealType r -> {
                    writeIndentedNotNEnd("F");
                    stringType = "F";
                }
                case BooleanType b -> {
                    writeIndentedNotNEnd("Z");
                    stringType = "Z";
                }
                case IdentifierType i -> {
                    writeIndentedNotNEnd("L" + i.identifier() + ";");
                    stringType = "L" + i.identifier() + ";";
                }
                case ArrayType a -> {
                    writeIndentedNotN("[");
                    stringType = "[";
                    switch (a.elementType()) {
                        case IntegerType i -> {
                            writeIndentedNotNEnd("I");
                            stringType += "I";
                        }
                        case RealType r -> {
                            writeIndentedNotNEnd("F");
                            stringType += "F";
                        }
                        case BooleanType b -> {
                            writeIndentedNotNEnd("Z");
                            stringType += "Z";
                        }
                        case IdentifierType i -> {
                            writeIndentedNotNEnd("L" + i.identifier() + ";");
                            stringType += "L" + i.identifier() + ";";
                        }
                        default -> {
                        }
                    }
                }
                default -> {
                }
            }
        }

        funcSignatures.put(function.identifier(), funcSignatures.get(function.identifier()) + ")" + stringType);

        writeIndented(".limit stack 50");
        writeIndented(".limit locals 50");

        for (Body el : function.body()) {
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

        for (String key : varsToDelete) {
            variables.remove(key);
        }

        index = funcVarIndex;
        currentFunction = null;
    }

    @Override
    public void visit(Parameter parameter) {
        String param = "";
        switch (parameter.type()) {
            case IntegerType i -> param = "I";
            case RealType r -> param = "F";
            case BooleanType b -> param = "Z";
            case IdentifierType i -> param = "L" + i.identifier() + ";";
            case ArrayType a -> {
                param += "[";
                switch (a.elementType()) {
                    case IntegerType i -> param += "I";
                    case RealType r -> param += "F";
                    case BooleanType b -> param += "Z";
                    case IdentifierType i -> param += "L" + i.identifier() + ";";
                    default -> {
                    }
                }
            }
            default -> {
            }
        }
        writeIndentedNotN(param);
        funcSignatures.put(currentFunction.identifier(), funcSignatures.get(currentFunction.identifier()) + param);
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
            default -> {
            }
        }
    }

    @Override
    public void visit(FunctionCallExpression functionCallExpression) {
        for (Expression e : functionCallExpression.parameters()) {
            e.accept(this);
        }
        writeIndented("invokestatic Program/" + funcSignatures.get(functionCallExpression.functionName()));
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
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
