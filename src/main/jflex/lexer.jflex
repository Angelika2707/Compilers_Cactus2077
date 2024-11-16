package lexer;

import java_cup.runtime.*;
import parser.sym;

%%

%class Lexer
%public
%unicode
%line
%column
%cup

%{
   private Symbol symbol(int type) {
     return new Symbol(type, yyline, yycolumn);
   }
   private Symbol symbol(int type, Object value) {
     return new Symbol(type, yyline, yycolumn, value);
   }
   private Symbol symbol(int type, String text) {
     return new Symbol(type, text);
   }
%}

/* Definitions */
DIGIT       = [0-9]
LETTER      = [a-zA-Z]
IDENTIFIER  = {LETTER}({LETTER}|{DIGIT})*
INTEGERNUM  = "-"?{DIGIT}+
REALNUM     = "-"?{DIGIT}+"."{DIGIT}+
WHITESPACE  = [ \t\n\r]+
COMMENT     = "//" [^\n]*

/* Regex rules */
%%

/* Ignore whitespaces */
{WHITESPACE} {  }
/* Ignore comments */
{COMMENT}    {  }

/* Keywords */
"var"        { System.out.printf("VAR at [%d, %d]%n", yyline, yycolumn); return symbol(sym.VAR); }
"type"       { System.out.printf("TYPE at [%d, %d]%n", yyline, yycolumn); return symbol(sym.TYPE); }
"integer"    { System.out.printf("INTEGER at [%d, %d]%n", yyline, yycolumn); return symbol(sym.INTEGER); }
"real"       { System.out.printf("REAL at [%d, %d]%n", yyline, yycolumn); return symbol(sym.REAL); }
"boolean"    { System.out.printf("BOOLEAN at [%d, %d]%n", yyline, yycolumn); return symbol(sym.BOOLEAN); }
"array"      { System.out.printf("ARRAY at [%d, %d]%n", yyline, yycolumn); return symbol(sym.ARRAY); }
"record"     { System.out.printf("RECORD at [%d, %d]%n", yyline, yycolumn); return symbol(sym.RECORD); }
"routine"    { System.out.printf("ROUTINE at [%d, %d]%n", yyline, yycolumn); return symbol(sym.ROUTINE); }
"is"         { System.out.printf("IS at [%d, %d]%n", yyline, yycolumn); return symbol(sym.IS); }
"if"         { System.out.printf("IF at [%d, %d]%n", yyline, yycolumn); return symbol(sym.IF); }
"then"       { System.out.printf("THEN at [%d, %d]%n", yyline, yycolumn); return symbol(sym.THEN); }
"else"       { System.out.printf("ELSE at [%d, %d]%n", yyline, yycolumn); return symbol(sym.ELSE); }
"end"        { System.out.printf("END at [%d, %d]%n", yyline, yycolumn); return symbol(sym.END); }
"for"        { System.out.printf("FOR at [%d, %d]%n", yyline, yycolumn); return symbol(sym.FOR); }
"in"         { System.out.printf("IN at [%d, %d]%n", yyline, yycolumn); return symbol(sym.IN); }
"reverse"    { System.out.printf("REVERSE at [%d, %d]%n", yyline, yycolumn); return symbol(sym.REVERSE); }
"loop"       { System.out.printf("LOOP at [%d, %d]%n", yyline, yycolumn); return symbol(sym.LOOP); }
"while"      { System.out.printf("WHILE at [%d, %d]%n", yyline, yycolumn); return symbol(sym.WHILE); }
"true"       { System.out.printf("TRUE at [%d, %d]%n", yyline, yycolumn); return symbol(sym.TRUE); }
"false"      { System.out.printf("FALSE at [%d, %d]%n", yyline, yycolumn); return symbol(sym.FALSE); }
"return"     { System.out.printf("RETURN at [%d, %d]%n", yyline, yycolumn); return symbol(sym.RETURN); }

/* Operators */
"+"          { System.out.printf("PLUS at [%d, %d]%n", yyline, yycolumn); return symbol(sym.PLUS); }
"-"          { System.out.printf("MINUS at [%d, %d]%n", yyline, yycolumn); return symbol(sym.MINUS); }
"*"          { System.out.printf("MUL at [%d, %d]%n", yyline, yycolumn); return symbol(sym.MUL); }
"/"          { System.out.printf("DIV at [%d, %d]%n", yyline, yycolumn); return symbol(sym.DIV); }
"%"          { System.out.printf("MOD at [%d, %d]%n", yyline, yycolumn); return symbol(sym.MOD); }
":="         { System.out.printf("ASSIGN at [%d, %d]%n", yyline, yycolumn); return symbol(sym.ASSIGN); }
"="          { System.out.printf("EQ at [%d, %d]%n", yyline, yycolumn); return symbol(sym.EQ); }
"/="         { System.out.printf("NE at [%d, %d]%n", yyline, yycolumn); return symbol(sym.NE); }
">"          { System.out.printf("GT at [%d, %d]%n", yyline, yycolumn); return symbol(sym.GT); }
"<"          { System.out.printf("LT at [%d, %d]%n", yyline, yycolumn); return symbol(sym.LT); }
">="         { System.out.printf("GE at [%d, %d]%n", yyline, yycolumn); return symbol(sym.GE); }
"<="         { System.out.printf("LE at [%d, %d]%n", yyline, yycolumn); return symbol(sym.LE); }
"and"        { System.out.printf("AND at [%d, %d]%n", yyline, yycolumn); return symbol(sym.AND); }
"or"         { System.out.printf("OR at [%d, %d]%n", yyline, yycolumn); return symbol(sym.OR); }
"xor"        { System.out.printf("XOR at [%d, %d]%n", yyline, yycolumn); return symbol(sym.XOR); }
"not"        { System.out.printf("NOT at [%d, %d]%n", yyline, yycolumn); return symbol(sym.NOT); }

/* Delimiters */
"("          { System.out.printf("OPEN PAREN at [%d, %d]%n", yyline, yycolumn); return symbol(sym.LPAREN); }
")"          { System.out.printf("CLOSE PAREN at [%d, %d]%n", yyline, yycolumn); return symbol(sym.RPAREN); }
"["          { System.out.printf("OPEN BRACKET at [%d, %d]%n", yyline, yycolumn); return symbol(sym.LBRACKET); }
"]"          { System.out.printf("CLOSE BRACKET at [%d, %d]%n", yyline, yycolumn); return symbol(sym.RBRACKET); }
","          { System.out.printf("COMMA at [%d, %d]%n", yyline, yycolumn); return symbol(sym.COMMA); }
".."         { System.out.printf("RANGE at [%d, %d]%n", yyline, yycolumn); return symbol(sym.RANGE); }
"."          { System.out.printf("DOT at [%d, %d]%n", yyline, yycolumn); return symbol(sym.DOT); }
":"          { System.out.printf("COLON at [%d, %d]%n", yyline, yycolumn); return symbol(sym.COLON); }
";"          { System.out.printf("SEMICOLON at [%d, %d]%n", yyline, yycolumn); return symbol(sym.SEMICOLON); }

/* Identifier */
{IDENTIFIER} { System.out.printf("IDENTIFIER: %s at [%d, %d]%n", yytext(), yyline, yycolumn); return symbol(sym.IDENTIFIER, yytext()); }

/* Numbers */
{INTEGERNUM}    { System.out.printf("INTEGERNUM: %s at [%d, %d]%n", yytext(), yyline, yycolumn); return symbol(sym.INTEGERNUM, Integer.parseInt(yytext())); }
{REALNUM}       { System.out.printf("REALNUM: %s at [%d, %d]%n", yytext(), yyline, yycolumn); return symbol(sym.REALNUM, Double.parseDouble(yytext())); }
