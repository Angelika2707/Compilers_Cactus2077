%language "Java"

%define api.package {parser}


%code imports {
    import token.TokenType;
}

%token <TokenType> VAR TYPE ROUTINE RETURN IS RECORD ARRAY WHILE FOR LOOP IF THEN ELSE END TRUE FALSE INTEGER REAL BOOLEAN
%token <TokenType> ASSIGN LT LE GT GE EQ NE PLUS MINUS MUL DIV MOD AND OR XOR NOT
%token <TokenType> IDENTIFIER INTEGERNUMBER REALNUMBER COMMA SEMICOLON LPAREN RPAREN LBRACKET RBRACKET
%token <TokenType> DOT COLON RANGE REVERSE NEWLINE WHITESPACE TAB UNKNOWN

%start program

%%

%%
