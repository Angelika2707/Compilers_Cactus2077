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

%token <TokenType> VAR TYPE ROUTINE RETURN IS RECORD ARRAY WHILE FOR LOOP IF THEN ELSE END TRUE FALSE INTEGER REAL BOOLEAN
%token <TokenType> ASSIGN LT LE GT GE EQ NE PLUS MINUS MUL DIV MOD AND OR XOR NOT
%token <TokenType> IDENTIFIER INTEGERNUMBER REALNUMBER COMMA SEMICOLON LPAREN RPAREN LBRACKET RBRACKET
%token <TokenType> DOT COLON RANGE REVERSE IN NEWLINE WHITESPACE TAB UNKNOWN

%start Program

%union {
    Token token;
    ASTNode node;
    List<ASTNode> nodes;
}

%%

Program:
    { /* AST Root */ }
    | Program SimpleDeclaration
    | Program RoutineDeclaration
    ;

SimpleDeclaration:
    VariableDeclaration
    | TypeDeclaration
    ;

VariableDeclaration:
    VAR IDENTIFIER COLON Type IS Expression
    | VAR IDENTIFIER IS Expression
    ;

TypeDeclaration:
    TYPE IDENTIFIER IS Type
    ;

RoutineDeclaration:
    ROUTINE IDENTIFIER LPAREN Parameters RPAREN IS Body END
    ;

Parameters:
    ParameterDeclaration
    | Parameters COMMA ParameterDeclaration
    ;

ParameterDeclaration:
    IDENTIFIER COLON IDENTIFIER
    ;

Type:
    PrimitiveType
    | ArrayType
    | RecordType
    | IDENTIFIER
    ;

PrimitiveType:
    INTEGER
    | REAL
    | BOOLEAN
    ;

RecordType:
    RECORD VariableDeclaration END
    ;

ArrayType:
    ARRAY LBRACKET Expression RBRACKET Type
    ;

Body:
    SimpleDeclaration
    | Statement
    | Body SimpleDeclaration
    | Body Statement
    ;

Statement:
    Assignment
    | RoutineCall
    | WhileLoop
    | ForLoop
    | IfStatement
    ;

Assignment:
    ModifiablePrimary ASSIGN Expression
    ;

RoutineCall:
    IDENTIFIER LPAREN Expression RPAREN
    ;

WhileLoop:
    WHILE Expression LOOP Body END
    ;

ForLoop:
    FOR IDENTIFIER Range LOOP Body END
    ;

Range:
    IN Expression RANGE Expression
    | IN REVERSE Expression RANGE Expression
    ;

IfStatement:
    IF Expression THEN Body ELSE Body END
    | IF Expression THEN Body END
    ;

Expression:
    Relation
    | Expression AND Relation
    | Expression XOR Relation
    | Expression OR Relation
    ;

Relation:
    Simple
    | Relation LT Simple
    | Relation LE Simple
    | Relation GT Simple
    | Relation GE Simple
    | Relation EQ Simple
    | Relation NE Simple
    ;

Simple:
    Factor
    | Simple PLUS Factor
    | Simple MINUS Factor
    ;

Factor:
    Summand
    | Factor MUL Summand
    | Factor DIV Summand
    | Factor MOD Summand
    ;

Summand:
    Primary
    | LPAREN Expression RPAREN
    ;

Primary:
    INTEGERNUMBER
    | REALNUMBER
    | TRUE
    | FALSE
    | ModifiablePrimary
    | NOT Primary
    ;

ModifiablePrimary:
    IDENTIFIER
    | ModifiablePrimary DOT IDENTIFIER
    | ModifiablePrimary LBRACKET Expression RBRACKET
    ;

%%

