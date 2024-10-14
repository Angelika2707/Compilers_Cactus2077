%language "Java"

%define api.package {main.parser}

%code imports {
    import main.token.TokenType;
}

%token <TokenType> VAR TYPE ROUTINE RETURN IS RECORD ARRAY WHILE FOR LOOP IF THEN ELSE END TRUE FALSE INTEGER REAL BOOLEAN
%token <TokenType> ASSIGN LT LE GT GE EQ NE PLUS MINUS MUL DIV MOD AND OR XOR NOT
%token <TokenType> IDENTIFIER INTEGERNUMBER REALNUMBER COMMA SEMICOLON LPAREN RPAREN LBRACKET RBRACKET
%token <TokenType> DOT COLON RANGE REVERSE IN NEWLINE WHITESPACE TAB UNKNOWN // Added IN token

%start Program

%%


Program:
      Declaration SEMICOLON
    | Statement SEMICOLON
    | Function SEMICOLON
    ;

Declaration:
      VAR IDENTIFIER COLON Type IS Expression
        { System.out.println("Variable declaration: " + $2); }
    ;

Type:
      INTEGER
    | BOOLEAN
    | REAL
    | ARRAY LBRACKET INTEGERNUMBER RBRACKET Type
        { System.out.println("Array type"); }
    | RECORD Declaration END
        { System.out.println("Record type"); }
    ;

Statement:
      IDENTIFIER ASSIGN Expression
    | IF Expression THEN Statement ELSE Statement END
        { System.out.println("If-else statement"); }
    | WHILE Expression LOOP Statement END
        { System.out.println("While loop"); }
    | FOR IDENTIFIER IN Expression RANGE Expression LOOP Statement END
        { System.out.println("For loop"); }
    | RETURN Expression
        { System.out.println("Return statement"); }
    ;

Expression:
      Expression PLUS Expression
    | Expression MINUS Expression
    | Expression MUL Expression
    | Expression DIV Expression
    | Expression EQ Expression
    | Expression GE Expression
    | Expression GT Expression
    | Expression LE Expression
    | Expression LT Expression
    | NOT Expression
    | TRUE
    | FALSE
    | INTEGERNUMBER
    | REALNUMBER
    | IDENTIFIER
    | LPAREN Expression RPAREN
    ;

Function:
      ROUTINE IDENTIFIER LPAREN RPAREN IS Declaration SEMICOLON Statement END
        { System.out.println("Function definition: " + $2); }
    ;

%%

// Add any additional helper functions or declarations here
