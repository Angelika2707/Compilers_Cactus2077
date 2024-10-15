%language "Java"

%define api.package {main.parser}

%code imports {
    import main.token.TokenType;
    import main.lexer.Lexer;
    import main.ast.*;
    import java.util.ArrayList;
    import java.util.List;
    import main.token.Token;
}

%token <Token> VAR TYPE ROUTINE RETURN IS RECORD ARRAY WHILE FOR LOOP IF THEN ELSE END TRUE FALSE INTEGER REAL BOOLEAN
%token <Token> ASSIGN LT LE GT GE EQ NE PLUS MINUS MUL DIV MOD AND OR XOR NOT
%token <Token> IDENTIFIER INTEGERNUMBER REALNUMBER COMMA SEMICOLON LPAREN RPAREN LBRACKET RBRACKET
%token <Token> DOT COLON RANGE REVERSE IN NEWLINE WHITESPACE TAB UNKNOWN


%type <Program> Program
%type <Declaration> Declaration
%type <TypeDeclaration> TypeDeclaration
%type <VariableDeclaration> VariableDeclaration
%type <RoutineDeclaration> RoutineDeclaration
%type <Parameters> Parameters
%type <ParameterDeclaration> ParameterDeclaration
%type <Type> Type
%type <PrimitiveType> PrimitiveType
%type <ArrayDeclaration> ArrayDeclaration
%type <RecordDeclaration> RecordDeclaration
%type <Statement> Statement
%type <Body> Body
%type <Assignment> Assignment
%type <RoutineCall> RoutineCall
%type <Expression> Expression
%type <ForLoop> ForLoop
%type <WhileLoop> WhileLoop
%type <Range> Range
%type <IfStatement> IfStatement
%type <Relation> Relation
%type <Simple> Simple
%type <Factor> Factor
%type <Summand> Summand
%type <Primary> Primary
%type <ModifiablePrimary> ModifiablePrimary

%%

Program:
    Declaration
    {
        List<Declaration> declarations = new ArrayList<>();
        declarations.add($1);
        $$ = new Program(declarations);
    }
    | Program Declaration
    {
        List<Declaration> declarations = new ArrayList<>($1.getDeclarations());
        declarations.add($2);
        $$ = new Program(declarations);
    }
    |
    {
        $$ = new Program(new ArrayList<>());
    }
    ;

Declaration:
    VariableDeclaration { $$ = $1; }
    | TypeDeclaration { $$ = $1; }
    ;

VariableDeclaration:
    VAR IDENTIFIER COLON Type IS Expression { $$ = new VariableDeclaration($2.toString(), $4.toString(), $6); }
    | VAR IDENTIFIER IS Expression { $$ = new VariableDeclaration($2.toString(), null, $4); }
    ;

TypeDeclaration:
    TYPE IDENTIFIER IS Type { $$ = new TypeDeclaration($2.toString(), $4.toString()); }
    ;

RoutineDeclaration:
    ROUTINE IDENTIFIER LPAREN Parameters RPAREN IS Body END { $$ = new RoutineDeclaration($2, $4, $6); }
    ;

Parameters:
    ParameterDeclaration { List<ParameterDeclaration> paramList = new ArrayList<>(); paramList.add($1); $$ = paramList; }
    | Parameters COMMA ParameterDeclaration { $1.add($3); $$ = $1; }
    ;

ParameterDeclaration:
    IDENTIFIER COLON IDENTIFIER { $$ = new ParameterDeclaration($1, $3); }
    ;

Type:
    PrimitiveType { $$ = $1; }
    | ArrayDeclaration { $$ = $1; }
    | RecordDeclaration { $$ = $1; }
    | IDENTIFIER { $$ = $1; }
    ;

PrimitiveType:
    INTEGER { $$ = new PrimitiveType("integer"); }
    | REAL { $$ = new PrimitiveType("real"); }
    | BOOLEAN { $$ = new PrimitiveType("boolean"); }
    ;

RecordDeclaration:
    RECORD VariableDeclaration END
        { List<VariableDeclaration> fields = new ArrayList<>(); fields.add($2); $$ = new RecordDeclaration(fields); }
    ;

ArrayDeclaration:
    ARRAY LBRACKET Expression RBRACKET Type
        { $$ = new ArrayDeclaration($3, $5.toString()); }
    ;

Body:
    Declaration
        { Body body = new Body(); body.addElement($1); $$ = body; }
    | Statement
        { Body body = new Body(); body.addElement($1); $$ = body; }
    | Body Declaration
        { $1.addElement($2); $$ = $1; }
    | Body Statement
        { $1.addElement($2); $$ = $1; }
    ;

Statement:
    Assignment { $$ = $1; }
    | RoutineCall { $$ = $1; }
    | WhileLoop { $$ = $1; }
    | ForLoop { $$ = $1; }
    | IfStatement { $$ = $1; }
    ;

Assignment:
    ModifiablePrimary ASSIGN Expression { $$ = new AssignmentStatement($1, $3); }
    ;

RoutineCall:
    IDENTIFIER LPAREN Expression RPAREN { $$ = new RoutineCall($1, $3); }
    ;

WhileLoop:
    WHILE Expression LOOP Body END { $$ = new WhileLoop($2, $4); }
    ;

ForLoop:
    FOR IDENTIFIER Range LOOP Body END { $$ = new ForLoop($2, $3, $5); }
    ;

Range:
    IN Expression RANGE Expression { $$ = new Range($2, $4, false); }
    | IN REVERSE Expression RANGE Expression { $$ = new Range($4, $2, true); }
    ;

IfStatement:
    IF Expression THEN Body ELSE Body END { $$ = new IfStatement($2, $4, $6); }
    | IF Expression THEN Body END { $$ = new IfStatement($2, $4); }
    ;

Expression:
    Relation { $$ = $1; }
    | Expression AND Relation { $$ = new BinaryExpression(TokenType.AND, $1, $3); }
    | Expression XOR Relation { $$ = new BinaryExpression(TokenType.XOR, $1, $3); }
    | Expression OR Relation { $$ = new BinaryExpression(TokenType.OR, $1, $3); }
    ;

Relation:
    Simple { $$ = $1; }
    | Relation LT Simple { $$ = new Relation(TokenType.LT, $1, $3); }
    | Relation LE Simple { $$ = new Relation(TokenType.LE, $1, $3); }
    | Relation GT Simple { $$ = new Relation(TokenType.GT, $1, $3); }
    | Relation GE Simple { $$ = new Relation(TokenType.GE, $1, $3); }
    | Relation EQ Simple { $$ = new Relation(TokenType.EQ, $1, $3); }
    | Relation NE Simple { $$ = new Relation(TokenType.NE, $1, $3); }
    ;

Simple:
    Factor { $$ = $1; }
    | Simple PLUS Factor { $$ = new BinaryExpression(TokenType.PLUS, $1, $3); }
    | Simple MINUS Factor { $$ = new BinaryExpression(TokenType.MINUS, $1, $3); }
    ;

Factor:
    Summand { $$ = $1; }
    | Factor MUL Summand { $$ = new BinaryExpression(TokenType.MUL, $1, $3); }
    | Factor DIV Summand { $$ = new BinaryExpression(TokenType.DIV, $1, $3); }
    | Factor MOD Summand { $$ = new BinaryExpression(TokenType.MOD, $1, $3); }
    ;

Summand:
    Primary { $$ = $1; }
    | LPAREN Expression RPAREN { $$ = $2; }
    ;

Primary:
    INTEGERNUMBER { $$ = new IntegerLiteral($1.getIntegerValue()); }
    | REALNUMBER { $$ = new RealLiteral($1.getRealValue()); }
    | TRUE { $$ = new BooleanLiteral(true); }
    | FALSE { $$ = new BooleanLiteral(false); }
    | ModifiablePrimary { $$ = $1; }
    | NOT Primary { $$ = new NotExpression($2); }
    ;

ModifiablePrimary:
    IDENTIFIER { $$ = new Identifier($1.getText()); }
    | ModifiablePrimary DOT IDENTIFIER { $$ = new MemberAccess($1, $3.getText()); }
    | ModifiablePrimary LBRACKET Expression RBRACKET { $$ = new ArrayAccess($1, $3); }
    ;

%%

