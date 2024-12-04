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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JasminCodeGenerator implements Visitor {
    // to write the Jasmin code
    private int indentLevel = 0;
    private final BufferedWriter writer;
    // for variables
    private final Map<String, Variable> variables = new HashMap<>();
    private int index = 1;
    private Expression last = null;
    // for functions (routines)
    private final Map<String, String> funcSignatures = new HashMap<>();
    private Function currentFunction = null;
    // for generating unique labels for statements
    private final LabelGenerator labelGenerator = new LabelGenerator();
    // for records
    private String recordName = null;
    private final Map<String, Map<String, Type>> records = new HashMap<>();

    @SneakyThrows
    public JasminCodeGenerator() {
        writer = new BufferedWriter(new FileWriter("program.j"));
    }

    @SneakyThrows
    private void writeIndentedLine(String text) {
        for (int i = 0; i < indentLevel; i++) {
            writer.write("    ");
        }
        writer.write(text);
        writer.newLine();
    }

    @SneakyThrows
    private void writeIndentedWithoutNewline(String text) {
        writer.write(text);
    }

    @SneakyThrows
    private void writeIndentedStartWithoutNewline(String text) {
        for (int i = 0; i < indentLevel; i++) {
            writer.write("    ");
        }
        writer.write(text);
    }

    @SneakyThrows
    private void writeIndentedEndWithoutNewline(String text) {
        writer.write(text);
        writer.newLine();
    }

    @SneakyThrows
    private void writeFormattedIndentedLine(String format, Object... args) {
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
    private void writeRecord(BufferedWriter newWriter, String format, Object... args) {
        for (int i = 0; i < indentLevel; i++) {
            newWriter.write("    ");
        }
        String formattedText = String.format(format, args);
        newWriter.write(formattedText);
        newWriter.newLine();
    }

    @SneakyThrows
    @Override
    public void visit(Program program) {
        List<Function> functions = new ArrayList<>();
        writeIndentedLine(".class public Program");
        writeIndentedLine(".super java/lang/Object");
        writeIndentedLine("");
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
        writeIndentedLine("");
        writeIndentedLine(".method public static main([Ljava/lang/String;)V");
        increaseIndent(() -> {
            writeIndentedLine(".limit stack 100");
            writeIndentedLine(".limit locals 100");
            for (ProgramUnit unit : program.units()) {
                if (!(unit instanceof Function)) {
                    unit.accept(this);
                }
            }
            writeIndentedLine("return");
        });
        writeIndentedLine(".end method");

        writer.close();
    }

    @Override
    public void visit(VariableDeclaration variableDeclaration) {
        Type type = variableDeclaration.type();
        String identifier = variableDeclaration.id();
        Expression expression = variableDeclaration.expression();
        if (recordName != null) {
            records.get(recordName).put(identifier, type);
            return;
        }

        if (type == null) {
            variables.put(identifier, new Variable(index, null));
            switch (last) {
                case IntegerLiteral ignored -> {
                    variables.put(identifier, new Variable(index, new IntegerType()));
                    writeFormattedIndentedLine("istore %d", index);
                }
                case RealLiteral ignored -> {
                    variables.put(identifier, new Variable(index, new RealType()));
                    writeFormattedIndentedLine("fstore %d", index);
                }
                case BooleanLiteral ignored -> {
                    variables.put(identifier, new Variable(index, new BooleanType()));
                    writeFormattedIndentedLine("istore %d", index);
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
                    writeFormattedIndentedLine("ldc %d", arrayType.size());
                    switch (arrayType.elementType()) {
                        case IntegerType ignored -> writeIndentedLine("newarray int");
                        case RealType ignored -> writeIndentedLine("newarray float");
                        case BooleanType ignored -> writeIndentedLine("newarray boolean");
                        case IdentifierType identifierType ->
                            writeFormattedIndentedLine("anewarray %s", identifierType.identifier());
                        default -> {}
                    }
                    writeFormattedIndentedLine("astore %d", index);
                    index++;
                }
                case IdentifierType identifierType -> {
                    String recordName = identifierType.identifier();

                    writeFormattedIndentedLine("new %s", recordName);
                    writeIndentedLine("dup");
                    writeFormattedIndentedLine("invokespecial %s/<init>()V", recordName);

                    variables.put(identifier, new Variable(index, identifierType));
                    writeFormattedIndentedLine("astore %d", index++);
                }
                default -> {}
            }

        } else {
            switch (type) {
                case IntegerType intType -> {
                    variables.put(identifier, new Variable(index, intType));
                    expression.accept(this);
                    writeFormattedIndentedLine("istore %d", index);
                    writeFormattedIndentedLine(".var %d is %s I", index, identifier);
                    index++;
                }
                case RealType realType -> {
                    variables.put(identifier, new Variable(index, realType));
                    expression.accept(this);
                    writeFormattedIndentedLine("fstore %d", index);
                    writeFormattedIndentedLine(".var %d is %s F", index, identifier);
                    index++;
                }
                case BooleanType booleanType -> {
                    variables.put(identifier, new Variable(index, booleanType));
                    expression.accept(this);
                    writeFormattedIndentedLine("istore %d", index);
                    writeFormattedIndentedLine(".var %d is %s Z", index, identifier);
                    index++;
                }
                default -> {}
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
            default -> {}
        }
    }

    @SneakyThrows
    private void createRecord(String name) {
        String fileName = name + ".j";

        BufferedWriter recordWriter = new BufferedWriter(new FileWriter(fileName));
        decreaseIndent(() -> {
            writeRecord(recordWriter,".class public " + name);
            writeRecord(recordWriter,".super java/lang/Object");
            writeRecord(recordWriter,"");
        });

        Map<String, Type> fields = records.get(name);

        for (String field : fields.keySet()) {
            Type type = fields.get(field);
            switch (type) {
                case IntegerType ignored ->
                    decreaseIndent(() -> writeRecord(recordWriter, ".field public %s I", field));
                case RealType ignored ->
                    decreaseIndent(() -> writeRecord(recordWriter, ".field public %s F", field));
                case BooleanType ignored ->
                    decreaseIndent(() -> writeRecord(recordWriter, ".field public %s Z", field));
                case IdentifierType i ->
                    decreaseIndent(() -> writeRecord(recordWriter, ".field public %s L%s;", field, i.identifier()));
                case ArrayType a -> {
                    Type elementType = a.elementType();

                    switch (elementType) {
                        case IntegerType ignored ->
                            decreaseIndent(() -> writeRecord(recordWriter, ".field public %s [I", field));
                        case RealType ignored ->
                            decreaseIndent(() -> writeRecord(recordWriter, ".field public %s [F", field));
                        case BooleanType ignored ->
                            decreaseIndent(() -> writeRecord(recordWriter, ".field public %s [Z", field));
                        case IdentifierType i ->
                            decreaseIndent(() -> writeRecord(recordWriter, ".field public %s [L%s;", field, i.identifier()));
                        default -> {}
                    }
                }
                default -> {}
            }
        }

        decreaseIndent(() -> {
            writeRecord(recordWriter,"");
            writeRecord(recordWriter,".method public <init>()V");
        });

        writeRecord(recordWriter,".limit stack " + (fields.size() + 1));
        writeRecord(recordWriter, ".limit locals " + (fields.size() + 1));
        writeRecord(recordWriter, "aload_0");
        writeRecord(recordWriter, "invokespecial java/lang/Object/<init>()V");
        for (String field : fields.keySet()) {
            if (fields.get(field) instanceof IdentifierType i) {
                writeRecord(recordWriter, "aload_0");
                writeRecord(recordWriter, "new " + i.identifier());
                writeRecord(recordWriter, "dup");
                writeRecord(recordWriter,"invokespecial " + i.identifier() + "/<init>()V");
                writeRecord(recordWriter,"putfield " + name + "/" + field + " L" + i.identifier() + ";");
            }
        }
        writeRecord(recordWriter, "return");

        decreaseIndent(() -> {
            writeRecord(recordWriter, ".end method");
            writeRecord(recordWriter, "");
        });

        recordWriter.close();
    }

    @Override
    public void visit(AssignmentStatement assignmentStatement) {
        String identifier = assignmentStatement.identifier();
        if (assignmentStatement.recordField() == null) {
            if (assignmentStatement.index() != null) {
                int arrLink = variables.get(identifier).index();
                writeFormattedIndentedLine("aload %d", arrLink);
                assignmentStatement.index().accept(this);
                assignmentStatement.expression().accept(this);
                ArrayType arrType = (ArrayType) variables.get(identifier).varInfo();
                switch (arrType.elementType()) {
                    case IntegerType ignored -> writeFormattedIndentedLine("iastore");
                    case RealType ignored -> writeFormattedIndentedLine("fastore");
                    case BooleanType ignored -> writeFormattedIndentedLine("bastore");
                    case IdentifierType ignored -> writeFormattedIndentedLine("aastore");
                    default -> {}
                }
            } else {
                assignmentStatement.expression().accept(this);
                int idx = variables.get(identifier).index();
                Type type = variables.get(identifier).varInfo();

                switch (last) {
                    case IntegerLiteral i -> {
                        // int -> float
                        if (type instanceof RealType) writeIndentedLine("i2f");
                        else if (type instanceof BooleanType && !(i.value() == 1 || i.value() == 0))
                            throw new IllegalArgumentException
                                    ("Invalid assignment: cannot convert value " + i.value() + " to boolean.");
                    }
                    case RealLiteral ignored -> {
                        // float -> int
                        if (type instanceof IntegerType) writeIndentedLine("f2i");
                        else if (type instanceof BooleanType)
                            throw new IllegalArgumentException
                                    ("Invalid assignment: cannot convert value of type real to boolean.");
                    }
                    case BooleanLiteral ignored -> {
                        // int -> float
                        if (type instanceof RealType) writeIndentedLine("i2f");
                    }
                    case NestedRecordAccess n -> {
                        Type typeN = variables.get(n.identifier()).varInfo();

                        if (type instanceof IntegerType && typeN instanceof RealType) {
                            writeIndentedLine("f2i"); // float -> int
                        } else if (type instanceof RealType && typeN instanceof IntegerType) {
                            writeIndentedLine("i2f"); // int -> float
                        } else if (type instanceof RealType && typeN instanceof BooleanType) {
                            writeIndentedLine("i2f"); // int -> float
                        } else if (type instanceof BooleanType && typeN instanceof RealType) {
                            throw new IllegalArgumentException
                                    ("Invalid assignment: cannot convert value of type real to boolean.");
                        }
                    }
                    default -> {}
                }

                switch (type) {
                    case IntegerType ignored -> writeFormattedIndentedLine("istore %d", idx);
                    case RealType ignored -> writeFormattedIndentedLine("fstore %d", idx);
                    case BooleanType ignored -> writeFormattedIndentedLine("istore %d", idx);
                    case IdentifierType ignored -> {
                    }
                    default -> {}
                }
            }
        } else {
            List<String> accessPath = assignmentStatement.recordField().getAccessPath();
            accessPath.add(identifier);
            Variable variable = variables.get(accessPath.getFirst());
            writeFormattedIndentedLine("aload %d", variable.index());
            String name = "";
            if (variable.varInfo() instanceof IdentifierType identifierType) {
                name = identifierType.identifier();
            }
            accessPath.removeFirst();
            while (!accessPath.isEmpty()) {
                Map<String, Type> fields = records.get(name);
                Type type = fields.get(accessPath.getFirst());
                if (type instanceof IdentifierType) {
                    String nextName = "";
                    Type next = records.get(name).get(accessPath.getFirst());
                    if (next instanceof IdentifierType) {
                        nextName = ((IdentifierType) next).identifier();
                    }
                    writeFormattedIndentedLine("getfield %s/%s L%s;", name, accessPath.getFirst(), nextName);
                    name = nextName;
                }
                switch (type) {
                    case IntegerType ignored -> {
                        assignmentStatement.expression().accept(this);
                        writeFormattedIndentedLine("putfield %s/%s %s", name, accessPath.getFirst(), "I");
                    }
                    case RealType ignored -> {
                        assignmentStatement.expression().accept(this);
                        writeFormattedIndentedLine("putfield %s/%s %s", name, accessPath.getFirst(), "F");
                    }
                    case BooleanType ignored -> {
                        assignmentStatement.expression().accept(this);
                        writeFormattedIndentedLine("putfield %s/%s %s", name, accessPath.getFirst(), "Z");
                    }
                    case ArrayType arrayType -> {
                        assignmentStatement.expression().accept(this);
                        String elemType = "";
                        switch (arrayType.elementType()) {
                            case IntegerType ignored -> elemType = "I";
                            case RealType ignored -> elemType = "F";
                            case BooleanType ignored -> elemType = "Z";
                            case IdentifierType i -> elemType = "L" + i.identifier() + ";";
                            default -> {}
                        }
                        writeFormattedIndentedLine("putfield %s/%s [%s", name, accessPath.getFirst(), elemType);
                    }
                    default -> {}
                }
                accessPath.removeFirst();
            }
        }
    }

    @Override
    public void visit(CallStatement callStatement) {
        for (Expression e: callStatement.paramList()) {
            e.accept(this);
        }
        writeIndentedLine("invokestatic Program/" + funcSignatures.get(callStatement.identifier()));
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
        writeFormattedIndentedLine("istore %d", index);
        variables.put(forStatement.loopVariable(), new Variable(index++, new IntegerType()));
        writeFormattedIndentedLine("%s:", labelCondition);
        writeFormattedIndentedLine("iload %d", variables.get(forStatement.loopVariable()).index());
        if (forStatement.isReverse()) {
            forStatement.startExpression().accept(this);
        } else {
            forStatement.endExpression().accept(this);
        }
        if (forStatement.isReverse()) {
            writeFormattedIndentedLine("if_icmplt %s", labelEnd);
        } else {
            writeFormattedIndentedLine("if_icmpgt %s", labelEnd);
        }
        writeFormattedIndentedLine("%s:", labelStart);
        for (Body el : forStatement.body()) {
            el.accept(this);
        }

        if (forStatement.isReverse()) {
            writeFormattedIndentedLine("iinc %d -1", variables.get(forStatement.loopVariable()).index());
        } else {
            writeFormattedIndentedLine("iinc %d 1", variables.get(forStatement.loopVariable()).index());
        }
        writeFormattedIndentedLine("goto %s", labelCondition);
        writeFormattedIndentedLine("%s:", labelEnd);
        variables.remove(forStatement.loopVariable());
    }

    @Override
    public void visit(IfStatement ifStatement) {
        ifStatement.condition().accept(this);
        String labelElse = labelGenerator.generate("Label_Else", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        if (ifStatement.elseBody().isEmpty()) {
            writeFormattedIndentedLine("ifeq %s", labelEnd); // if else does not exist
        } else {
            writeFormattedIndentedLine("ifeq %s", labelElse); // if 0
        }
        for (Body el : ifStatement.thenBody()) {
            el.accept(this);
        }
        writeFormattedIndentedLine("goto %s", labelEnd);
        writeFormattedIndentedLine("%s:", labelElse);
        for (Body el : ifStatement.elseBody()) {
            el.accept(this);
        }
        writeFormattedIndentedLine("%s:", labelEnd);
    }

    @Override
    public void visit(ReturnStatement returnStatement) {
        returnStatement.returnExpression().accept(this);
        String funcSignature = funcSignatures.get(currentFunction.identifier());
        String returnType = funcSignature.split("\\)")[1];
        if (returnType.length() > 1 && (returnType.startsWith("[") || returnType.startsWith("L"))) {
            writeIndentedLine("areturn");
        }
        switch (returnType) {
            case "I", "Z" -> writeIndentedLine("ireturn");
            case "F" -> writeIndentedLine("freturn");
            default -> {}
        }
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        String labelStart = labelGenerator.generate("Label_Start", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeFormattedIndentedLine("%s:", labelStart);
        whileStatement.condition().accept(this);
        writeFormattedIndentedLine("ifeq %s", labelEnd);
        for (Body el : whileStatement.body()) {
            el.accept(this);
        }
        writeFormattedIndentedLine("goto %s", labelStart);
        writeFormattedIndentedLine("%s:", labelEnd);
    }

    @Override
    public void visit(Function function) {
        currentFunction = function;
        int funcVarIndex = index;
        index = 0;

        List<String> varsToDelete = new ArrayList<>();
        decreaseIndent(() -> writeIndentedStartWithoutNewline(".method public static " + function.identifier() + "("));

        funcSignatures.put(function.identifier(), function.identifier() + "(");

        for (Parameter parameter: function.params()) {
            parameter.accept(this);
            variables.put(parameter.identifier(), new Variable(index++, parameter.type()));
            varsToDelete.add(parameter.identifier());
        }
        writeIndentedWithoutNewline(")");

        String stringType = "";
        if (function.returnType() == null) {
            writeIndentedEndWithoutNewline("V");
            stringType = "V";
        } else {
            switch (function.returnType()) {
                case IntegerType ignored -> {
                    writeIndentedEndWithoutNewline("I");
                    stringType = "I";
                }
                case RealType ignored -> {
                    writeIndentedEndWithoutNewline("F");
                    stringType = "F";
                }
                case BooleanType ignored -> {
                    writeIndentedEndWithoutNewline("Z");
                    stringType = "Z";
                }
                case IdentifierType i -> {
                    writeIndentedEndWithoutNewline("L" + i.identifier() + ";");
                    stringType = "L" + i.identifier() + ";";
                }
                case ArrayType a -> {
                    writeIndentedWithoutNewline("[");
                    stringType = "[";
                    switch (a.elementType()) {
                        case IntegerType ignored -> {
                            writeIndentedEndWithoutNewline("I");
                            stringType += "I";
                        }
                        case RealType ignored -> {
                            writeIndentedEndWithoutNewline("F");
                            stringType += "F";
                        }
                        case BooleanType ignored -> {
                            writeIndentedEndWithoutNewline("Z");
                            stringType += "Z";
                        }
                        case IdentifierType i -> {
                            writeIndentedEndWithoutNewline("L" + i.identifier() + ";");
                            stringType += "L" + i.identifier() + ";";
                        }
                        default -> {}
                    }
                }
                default -> {}
            }
        }

        funcSignatures.put(function.identifier(), funcSignatures.get(function.identifier()) + ")" + stringType);

        writeIndentedLine(".limit stack 50");
        writeIndentedLine(".limit locals 50");

        for (Body el: function.body()) {
            if (el instanceof VariableDeclaration) {
                varsToDelete.add(((VariableDeclaration) el).id());
            } else if (el instanceof TypeDeclaration) {
                varsToDelete.add(((TypeDeclaration) el).id());
            }
            el.accept(this);
        }

        if (function.returnType() == null) writeIndentedLine("return");

        decreaseIndent(() -> writeIndentedLine(".end method"));

        for (String key: varsToDelete) {
            variables.remove(key);
            records.remove(key);
        }

        index = funcVarIndex;
        currentFunction = null;
    }

    @Override
    public void visit(Parameter parameter) {
        String param = "";
        switch (parameter.type()) {
            case IntegerType ignored -> param = "I";
            case RealType ignored -> param = "F";
            case BooleanType ignored -> param = "Z";
            case IdentifierType i -> param = "L" + i.identifier() + ";";
            case ArrayType a -> {
                param += "[";
                switch (a.elementType()) {
                    case IntegerType ignored -> param += "I";
                    case RealType ignored -> param += "F";
                    case BooleanType ignored -> param += "Z";
                    case IdentifierType i -> param += "L" + i.identifier() + ";";
                    default -> {}
                }
            }
            default -> {}
        }
        writeIndentedWithoutNewline(param);
        funcSignatures.put(currentFunction.identifier(), funcSignatures.get(currentFunction.identifier()) + param);
    }

    @Override
    public void visit(ArrayAccessExpression arrayAccessExpression) {
        String identifier = arrayAccessExpression.identifier();
        int arrLink = variables.get(identifier).index();
        writeFormattedIndentedLine("aload %d", arrLink);
        arrayAccessExpression.index().accept(this);
        ArrayType arrType = (ArrayType) variables.get(identifier).varInfo();
        switch (arrType.elementType()) {
            case IntegerType ignored -> writeFormattedIndentedLine("iaload");
            case RealType ignored -> writeFormattedIndentedLine("faload");
            case BooleanType ignored -> writeFormattedIndentedLine("baload");
            case IdentifierType ignored -> writeFormattedIndentedLine("aaload");
            default -> {
            }
        }
    }

    @Override
    public void visit(FunctionCallExpression functionCallExpression) {
        for (Expression e: functionCallExpression.parameters()) {
            e.accept(this);
        }
        writeIndentedLine("invokestatic Program/" + funcSignatures.get(functionCallExpression.functionName()));
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
            writeIndentedLine("iadd");
        } else if (last instanceof RealLiteral) {
            writeIndentedLine("fadd");
        } else if (last instanceof NestedRecordAccess n) {
            if (n.nestedAccess() == null) {
                Type type = variables.get(n.identifier()).varInfo();
                switch (type) {
                    case IntegerType ignored -> writeIndentedLine("iadd");
                    case RealType ignored -> writeIndentedLine("fadd");
                    default -> {}
                }
            }
        }
    }

    @Override
    public void visit(MinusExpression minusExpression) {
        minusExpression.left().accept(this);
        minusExpression.right().accept(this);

        if (last instanceof IntegerLiteral) {
            writeIndentedLine("isub");
        } else if (last instanceof RealLiteral) {
            writeIndentedLine("fsub");
        } else if (last instanceof NestedRecordAccess n) {
            if (n.nestedAccess() == null) {
                Type type = variables.get(n.identifier()).varInfo();
                switch (type) {
                    case IntegerType ignored -> writeIndentedLine("isub");
                    case RealType ignored -> writeIndentedLine("fsub");
                    default -> {}
                }
            }
        }
    }

    @Override
    public void visit(MulExpression mulExpression) {
        mulExpression.left().accept(this);
        mulExpression.right().accept(this);

        if (last instanceof IntegerLiteral) {
            writeIndentedLine("imul");
        } else if (last instanceof RealLiteral) {
            writeIndentedLine("fmul");
        } else if (last instanceof NestedRecordAccess n) {
            if (n.nestedAccess() == null) {
                Type type = variables.get(n.identifier()).varInfo();
                switch (type) {
                    case IntegerType ignored -> writeIndentedLine("imul");
                    case RealType ignored -> writeIndentedLine("fmul");
                    default -> {}
                }
            }
        }
    }

    @Override
    public void visit(DivExpression divExpression) {
        divExpression.left().accept(this);
        divExpression.right().accept(this);

        if (last instanceof IntegerLiteral) {
            writeIndentedLine("idiv");
        } else if (last instanceof RealLiteral) {
            writeIndentedLine("fdiv");
        } else if (last instanceof NestedRecordAccess n) {
            if (n.nestedAccess() == null) {
                Type type = variables.get(n.identifier()).varInfo();
                switch (type) {
                    case IntegerType ignored -> writeIndentedLine("idiv");
                    case RealType ignored -> writeIndentedLine("fdiv");
                    default -> {}
                }
            }
        }
    }

    @Override
    public void visit(ModExpression modExpression) {
        modExpression.left().accept(this);
        modExpression.right().accept(this);
        writeIndentedLine("irem");
    }

    @Override
    public void visit(EqualExpression equalExpression) {
        equalExpression.left().accept(this);
        equalExpression.right().accept(this);
        String labelTrue = labelGenerator.generate("Label_True", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeFormattedIndentedLine("if_icmpeq %s", labelTrue);
        writeIndentedLine("iconst_0");
        writeFormattedIndentedLine("goto %s", labelEnd);
        writeFormattedIndentedLine("%s:", labelTrue);
        writeIndentedLine("iconst_1");
        writeFormattedIndentedLine("%s:", labelEnd);
    }

    @Override
    public void visit(NotEqualExpression notEqualExpression) {
        notEqualExpression.left().accept(this);
        notEqualExpression.right().accept(this);
        String labelTrue = labelGenerator.generate("Label_True", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeFormattedIndentedLine("if_icmpne %s", labelTrue);
        writeIndentedLine("iconst_0");
        writeFormattedIndentedLine("goto %s", labelEnd);
        writeFormattedIndentedLine("%s:", labelTrue);
        writeIndentedLine("iconst_1");
        writeFormattedIndentedLine("%s:", labelEnd);
    }

    @Override
    public void visit(GreaterEqualExpression greaterEqualExpression) {
        greaterEqualExpression.left().accept(this);
        greaterEqualExpression.right().accept(this);
        String labelTrue = labelGenerator.generate("Label_True", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeFormattedIndentedLine("if_icmpge %s", labelTrue);
        writeIndentedLine("iconst_0");
        writeFormattedIndentedLine("goto %s", labelEnd);
        writeFormattedIndentedLine("%s:", labelTrue);
        writeIndentedLine("iconst_1");
        writeFormattedIndentedLine("%s:", labelEnd);
    }

    @Override
    public void visit(GreaterThanExpression greaterThanExpression) {
        greaterThanExpression.left().accept(this);
        greaterThanExpression.right().accept(this);
        String labelTrue = labelGenerator.generate("Label_True", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeFormattedIndentedLine("if_icmpgt %s", labelTrue);
        writeIndentedLine("iconst_0");
        writeFormattedIndentedLine("goto %s", labelEnd);
        writeFormattedIndentedLine("%s:", labelTrue);
        writeIndentedLine("iconst_1");
        writeFormattedIndentedLine("%s:", labelEnd);
    }

    @Override
    public void visit(LessEqualExpression lessEqualExpression) {
        lessEqualExpression.left().accept(this);
        lessEqualExpression.right().accept(this);
        String labelTrue = labelGenerator.generate("Label_True", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeFormattedIndentedLine("if_icmple %s", labelTrue);
        writeIndentedLine("iconst_0");
        writeFormattedIndentedLine("goto %s", labelEnd);
        writeFormattedIndentedLine("%s:", labelTrue);
        writeIndentedLine("iconst_1");
        writeFormattedIndentedLine("%s:", labelEnd);
    }

    @Override
    public void visit(LessThanExpression lessThanExpression) {
        lessThanExpression.left().accept(this);
        lessThanExpression.right().accept(this);
        String labelTrue = labelGenerator.generate("Label_True", false);
        String labelEnd = labelGenerator.generate("Label_End", true);
        writeFormattedIndentedLine("if_icmplt %s", labelTrue);
        writeIndentedLine("iconst_0");
        writeFormattedIndentedLine("goto %s", labelEnd);
        writeFormattedIndentedLine("%s:", labelTrue);
        writeIndentedLine("iconst_1");
        writeFormattedIndentedLine("%s:", labelEnd);
    }

    @Override
    public void visit(AndExpression andExpression) {
        andExpression.left().accept(this);
        andExpression.right().accept(this);
        writeIndentedLine("iand");
    }

    @Override
    public void visit(NotExpression notExpression) {
        notExpression.expr().accept(this);
        writeIndentedLine("iconst_1");
        writeIndentedLine("ixor");
    }

    @Override
    public void visit(OrExpression orExpression) {
        orExpression.left().accept(this);
        orExpression.right().accept(this);
        writeIndentedLine("ior");
    }

    @Override
    public void visit(XorExpression xorExpression) {
        xorExpression.left().accept(this);
        xorExpression.right().accept(this);
        writeIndentedLine("ixor");
    }

    @Override
    public void visit(NestedRecordAccess nestedRecordAccess) {
        last = nestedRecordAccess;
        String identifier = nestedRecordAccess.identifier();
        Variable variableInfo = variables.get(identifier);

        if (nestedRecordAccess.nestedAccess() != null) {
            List<String> accessPath = nestedRecordAccess.getAccessPath();
            Variable variable = variables.get(accessPath.getFirst());
            writeFormattedIndentedLine("aload %d", variable.index());
            String name = "";
            if (variable.varInfo() instanceof IdentifierType identifierType) {
                name = identifierType.identifier();
            }
            accessPath.removeFirst();
            while (!accessPath.isEmpty()) {
                Map<String, Type> fields = records.get(name);
                Type typeNext = fields.get(accessPath.getFirst());
                if (typeNext instanceof IdentifierType) {
                    String nextName = "";
                    Type next = records.get(name).get(accessPath.getFirst());
                    if (next instanceof IdentifierType) {
                        nextName = ((IdentifierType) next).identifier();
                    }
                    writeFormattedIndentedLine("getfield %s/%s L%s;", name, accessPath.getFirst(), nextName);
                    name = nextName;
                }
                switch (typeNext) {
                    case IntegerType ignored ->
                        writeFormattedIndentedLine("getfield %s/%s %s", name, accessPath.getFirst(), "I");
                    case RealType ignored ->
                        writeFormattedIndentedLine("getfield %s/%s %s", name, accessPath.getFirst(), "F");
                    case BooleanType ignored ->
                        writeFormattedIndentedLine("getfield %s/%s %s", name, accessPath.getFirst(), "Z");
                    case ArrayType arrayType -> {
                        String elemType = "";
                        switch (arrayType.elementType()) {
                            case IntegerType ignored -> elemType = "I";
                            case RealType ignored -> elemType = "F";
                            case BooleanType ignored -> elemType = "Z";
                            case IdentifierType i -> elemType = "L" + i.identifier() + ";";
                            default -> {}
                        }
                        writeFormattedIndentedLine("getfield %s/%s [%s", name, accessPath.getFirst(), elemType);
                    }
                    default -> {}
                }
                accessPath.removeFirst();
            }
        } else {
            int index = variableInfo.index();
            Type type = variableInfo.varInfo();
            switch (type) {
                case IntegerType ignored -> writeFormattedIndentedLine("iload %d", index);
                case RealType ignored -> writeFormattedIndentedLine("fload %d", index);
                case BooleanType ignored -> writeFormattedIndentedLine("iload %d", index);
                case ArrayType ignored -> writeFormattedIndentedLine("aload %d", index);
                case IdentifierType ignored -> writeFormattedIndentedLine("aload %d", index);
                default -> {}
            }
        }
    }

    @Override
    public void visit(IntegerLiteral integerLiteral) {
        writeIndentedStartWithoutNewline("ldc ");
        last = integerLiteral;
        writeIndentedEndWithoutNewline(String.valueOf(integerLiteral.value()));
    }

    @Override
    public void visit(RealLiteral realLiteral) {
        writeIndentedStartWithoutNewline("ldc ");
        last = realLiteral;
        writeIndentedEndWithoutNewline(String.valueOf(realLiteral.value()));
    }

    @Override
    public void visit(BooleanLiteral booleanLiteral) {
        writeIndentedStartWithoutNewline("iconst_");
        last = booleanLiteral;
        writeIndentedEndWithoutNewline(String.valueOf(booleanLiteral.value() ? 1 : 0));
    }

    @Override
    public void visit(ArrayType arrayType) {}

    @Override
    public void visit(BooleanType booleanType) {}

    @Override
    public void visit(IdentifierType identifierType) {}

    @Override
    public void visit(IntegerType integerType) {}

    @Override
    public void visit(RealType realType) {}

    @Override
    public void visit(RecordType recordType) {}

    private static class LabelGenerator {
        private int counter = 0;

        private String generate(String baseLabel, boolean inc) {
            return baseLabel + "_" + (inc ? counter++ : counter);
        }
    }
}
