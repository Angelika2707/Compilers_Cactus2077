package astm;

import java.util.List;

// Base class for all AST nodes
abstract class ASTNode {
    abstract void accept(Visitor visitor);
}

// Top-level Program node
class Program extends ASTNode {
    List<ProgramUnit> units;

    public Program(List<ProgramUnit> units) {
        this.units = units;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// Abstract class for program units (declarations, statements, functions)
abstract class ProgramUnit extends ASTNode {}

// Declaration node
class Declaration extends ProgramUnit {
    VariableDeclaration variableDeclaration;
    TypeDeclaration typeDeclaration;

    public Declaration(VariableDeclaration varDecl) {
        this.variableDeclaration = varDecl;
    }

    public Declaration(TypeDeclaration typeDecl) {
        this.typeDeclaration = typeDecl;
    }

    @Override
    void accept(Visitor visitor) {
        if (variableDeclaration != null) {
            variableDeclaration.accept(visitor);
        } else if (typeDeclaration != null) {
            typeDeclaration.accept(visitor);
        }
    }
}

// Statement node
abstract class Statement extends ProgramUnit {}

// Variable declaration node
class VariableDeclaration extends ASTNode {
    String identifier;
    Type type;
    Expression expression;

    public VariableDeclaration(String id, Type type, Expression expr) {
        this.identifier = id;
        this.type = type;
        this.expression = expr;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// Type declaration node
class TypeDeclaration extends ASTNode {
    String identifier;
    Type type;

    public TypeDeclaration(String id, Type type) {
        this.identifier = id;
        this.type = type;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// Type nodes
abstract class Type extends ASTNode {}

class IntegerType extends Type {
    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class BooleanType extends Type {
    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class RealType extends Type {
    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class IdentifierType extends Type {
    String identifier;

    public IdentifierType(String id) {
        this.identifier = id;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class ArrayType extends Type {
    int size;
    Type elementType;

    public ArrayType(int size, Type elementType) {
        this.size = size;
        this.elementType = elementType;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class RecordType extends Type {
    List<Declaration> fields;

    public RecordType(List<Declaration> fields) {
        this.fields = fields;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// Expression nodes
abstract class Expression extends ASTNode {}

class BinaryExpression extends Expression {
    String operator;
    Expression left;
    Expression right;

    public BinaryExpression(String operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class UnaryExpression extends Expression {
    String operator;
    Expression operand;

    public UnaryExpression(String operator, Expression operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class LiteralExpression extends Expression {
    Object value;

    public LiteralExpression(Object value) {
        this.value = value;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class IdentifierExpression extends Expression {
    String identifier;

    public IdentifierExpression(String id) {
        this.identifier = id;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class ArrayAccessExpression extends Expression {
    String identifier;
    Expression index;

    public ArrayAccessExpression(String id, Expression index) {
        this.identifier = id;
        this.index = index;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class FieldAccessExpression extends Expression {
    String identifier;
    String field;

    public FieldAccessExpression(String id, String field) {
        this.identifier = id;
        this.field = field;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// Function node
class Function extends ASTNode {
    String identifier;
    List<Param> params;
    Type returnType;
    FunctionBody body;

    public Function(String id, List<Param> params, Type returnType, FunctionBody body) {
        this.identifier = id;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// Parameter node
class Param extends ASTNode {
    String identifier;
    Type type;

    public Param(String id, Type type) {
        this.identifier = id;
        this.type = type;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// Function body node
class FunctionBody extends ASTNode {
    List<Declaration> declarations;
    Statement statement;

    public FunctionBody(List<Declaration> declarations, Statement statement) {
        this.declarations = declarations;
        this.statement = statement;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// Specific statement types
class AssignmentStatement extends Statement {
    String identifier;
    Expression expression;

    public AssignmentStatement(String id, Expression expr) {
        this.identifier = id;
        this.expression = expr;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class IfStatement extends Statement {
    Expression condition;
    Statement thenStatement;
    Statement elseStatement;

    public IfStatement(Expression condition, Statement thenStatement, Statement elseStatement) {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class WhileStatement extends Statement {
    Expression condition;
    Statement body;

    public WhileStatement(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class ForStatement extends Statement {
    String identifier;
    Expression start;
    Expression end;
    Statement body;

    public ForStatement(String id, Expression start, Expression end, Statement body) {
        this.identifier = id;
        this.start = start;
        this.end = end;
        this.body = body;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class ReturnStatement extends Statement {
    Expression expression;

    public ReturnStatement(Expression expr) {
        this.expression = expr;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// Visitor interface
interface Visitor {
    void visit(Program program);
    void visit(Function function);
    void visit(FunctionBody functionBody);
    void visit(VariableDeclaration varDecl);
    void visit(TypeDeclaration typeDecl);
    void visit(Param param);
    void visit(AssignmentStatement assignStmt);
    void visit(IfStatement ifStmt);
    void visit(WhileStatement whileStmt);
    void visit(ForStatement forStmt);
    void visit(ReturnStatement returnStmt);
    void visit(BinaryExpression binExpr);
    void visit(UnaryExpression unExpr);
    void visit(LiteralExpression litExpr);
    void visit(IdentifierExpression idExpr);
    void visit(ArrayAccessExpression arrayAccessExpr);
    void visit(FieldAccessExpression fieldAccessExpr);
    void visit(IntegerType integerType);
    void visit(BooleanType booleanType);
    void visit(RealType realType);
    void visit(IdentifierType identifierType);
    void visit(ArrayType arrayType);
    void visit(RecordType recordType);
}

// DFSVisitor implementation
class DFSVisitor implements Visitor {
    @Override public void visit(Program program) { for (ProgramUnit unit : program.units) { unit.accept(this); } }
    @Override public void visit(Function function) { for (Param param : function.params) { param.accept(this); } if (function.returnType != null) { function.returnType.accept(this); } function.body.accept(this); }
    @Override public void visit(FunctionBody functionBody) { for (Declaration decl : functionBody.declarations) { decl.accept(this); } if (functionBody.statement != null) { functionBody.statement.accept(this); } }
    @Override public void visit(VariableDeclaration varDecl) { if (varDecl.type != null) { varDecl.type.accept(this); } if (varDecl.expression != null) { varDecl.expression.accept(this); } }
    @Override public void visit(TypeDeclaration typeDecl) { if (typeDecl.type != null) { typeDecl.type.accept(this); } }
    @Override public void visit(Param param) { param.type.accept(this); }
    @Override public void visit(AssignmentStatement assignStmt) { assignStmt.expression.accept(this); }
    @Override
    public void visit(IfStatement ifStmt) {
        ifStmt.condition.accept(this);
        ifStmt.thenStatement.accept(this);
        if (ifStmt.elseStatement != null) {
            ifStmt.elseStatement.accept(this);
        }
    }

    @Override
    public void visit(WhileStatement whileStmt) {
        whileStmt.condition.accept(this);
        whileStmt.body.accept(this);
    }

    @Override
    public void visit(ForStatement forStmt) {
        forStmt.start.accept(this);
        forStmt.end.accept(this);
        forStmt.body.accept(this);
    }

    @Override
    public void visit(ReturnStatement returnStmt) {
        if (returnStmt.expression != null) {
            returnStmt.expression.accept(this);
        }
    }

    @Override
    public void visit(BinaryExpression binExpr) {
        binExpr.left.accept(this);
        binExpr.right.accept(this);
    }

    @Override
    public void visit(UnaryExpression unExpr) {
        unExpr.operand.accept(this);
    }

    @Override
    public void visit(LiteralExpression litExpr) {
        // No further traversal needed for literals
    }

    @Override
    public void visit(IdentifierExpression idExpr) {
        // No further traversal needed for identifiers
    }

    @Override
    public void visit(ArrayAccessExpression arrayAccessExpr) {
        arrayAccessExpr.index.accept(this);
    }

    @Override
    public void visit(FieldAccessExpression fieldAccessExpr) {
        // No further traversal needed for field access
    }

    @Override
    public void visit(IntegerType integerType) {
        // No further traversal needed for integer type
    }

    @Override
    public void visit(BooleanType booleanType) {
        // No further traversal needed for boolean type
    }

    @Override
    public void visit(RealType realType) {
        // No further traversal needed for real type
    }

    @Override
    public void visit(IdentifierType identifierType) {
        // No further traversal needed for identifier type
    }

    @Override
    public void visit(ArrayType arrayType) {
        arrayType.elementType.accept(this);
    }

    @Override
    public void visit(RecordType recordType) {
        for (Declaration field : recordType.fields) {
            field.accept(this);
        }
    }
}

