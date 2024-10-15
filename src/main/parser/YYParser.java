/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java

   Copyright (C) 2007-2015, 2018-2021 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

package main.parser;



import java.text.MessageFormat;
/* "%code imports" blocks.  */
/* "parser.y":5  */

    import main.token.TokenType;
    import main.lexer.Lexer;
    import main.ast.*;
    import java.util.ArrayList;
    import java.util.List;
    import main.token.Token;

/* "YYParser.java":54  */

/**
 * A Bison parser, automatically generated from <tt>parser.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public class YYParser
{
  /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "3.8.2";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.java";






  public enum SymbolKind
  {
    S_YYEOF(0),                    /* "end of file"  */
    S_YYerror(1),                  /* error  */
    S_YYUNDEF(2),                  /* "invalid token"  */
    S_VAR(3),                      /* VAR  */
    S_TYPE(4),                     /* TYPE  */
    S_ROUTINE(5),                  /* ROUTINE  */
    S_RETURN(6),                   /* RETURN  */
    S_IS(7),                       /* IS  */
    S_RECORD(8),                   /* RECORD  */
    S_ARRAY(9),                    /* ARRAY  */
    S_WHILE(10),                   /* WHILE  */
    S_FOR(11),                     /* FOR  */
    S_LOOP(12),                    /* LOOP  */
    S_IF(13),                      /* IF  */
    S_THEN(14),                    /* THEN  */
    S_ELSE(15),                    /* ELSE  */
    S_END(16),                     /* END  */
    S_TRUE(17),                    /* TRUE  */
    S_FALSE(18),                   /* FALSE  */
    S_INTEGER(19),                 /* INTEGER  */
    S_REAL(20),                    /* REAL  */
    S_BOOLEAN(21),                 /* BOOLEAN  */
    S_ASSIGN(22),                  /* ASSIGN  */
    S_LT(23),                      /* LT  */
    S_LE(24),                      /* LE  */
    S_GT(25),                      /* GT  */
    S_GE(26),                      /* GE  */
    S_EQ(27),                      /* EQ  */
    S_NE(28),                      /* NE  */
    S_PLUS(29),                    /* PLUS  */
    S_MINUS(30),                   /* MINUS  */
    S_MUL(31),                     /* MUL  */
    S_DIV(32),                     /* DIV  */
    S_MOD(33),                     /* MOD  */
    S_AND(34),                     /* AND  */
    S_OR(35),                      /* OR  */
    S_XOR(36),                     /* XOR  */
    S_NOT(37),                     /* NOT  */
    S_IDENTIFIER(38),              /* IDENTIFIER  */
    S_INTEGERNUMBER(39),           /* INTEGERNUMBER  */
    S_REALNUMBER(40),              /* REALNUMBER  */
    S_COMMA(41),                   /* COMMA  */
    S_SEMICOLON(42),               /* SEMICOLON  */
    S_LPAREN(43),                  /* LPAREN  */
    S_RPAREN(44),                  /* RPAREN  */
    S_LBRACKET(45),                /* LBRACKET  */
    S_RBRACKET(46),                /* RBRACKET  */
    S_DOT(47),                     /* DOT  */
    S_COLON(48),                   /* COLON  */
    S_RANGE(49),                   /* RANGE  */
    S_REVERSE(50),                 /* REVERSE  */
    S_IN(51),                      /* IN  */
    S_NEWLINE(52),                 /* NEWLINE  */
    S_WHITESPACE(53),              /* WHITESPACE  */
    S_TAB(54),                     /* TAB  */
    S_UNKNOWN(55),                 /* UNKNOWN  */
    S_YYACCEPT(56),                /* $accept  */
    S_Program(57),                 /* Program  */
    S_Declaration(58),             /* Declaration  */
    S_VariableDeclaration(59),     /* VariableDeclaration  */
    S_TypeDeclaration(60),         /* TypeDeclaration  */
    S_Type(61),                    /* Type  */
    S_PrimitiveType(62),           /* PrimitiveType  */
    S_RecordDeclaration(63),       /* RecordDeclaration  */
    S_ArrayDeclaration(64),        /* ArrayDeclaration  */
    S_Expression(65),              /* Expression  */
    S_Relation(66),                /* Relation  */
    S_Simple(67),                  /* Simple  */
    S_Factor(68),                  /* Factor  */
    S_Summand(69),                 /* Summand  */
    S_Primary(70),                 /* Primary  */
    S_ModifiablePrimary(71);       /* ModifiablePrimary  */


    private final int yycode_;

    SymbolKind (int n) {
      this.yycode_ = n;
    }

    private static final SymbolKind[] values_ = {
      SymbolKind.S_YYEOF,
      SymbolKind.S_YYerror,
      SymbolKind.S_YYUNDEF,
      SymbolKind.S_VAR,
      SymbolKind.S_TYPE,
      SymbolKind.S_ROUTINE,
      SymbolKind.S_RETURN,
      SymbolKind.S_IS,
      SymbolKind.S_RECORD,
      SymbolKind.S_ARRAY,
      SymbolKind.S_WHILE,
      SymbolKind.S_FOR,
      SymbolKind.S_LOOP,
      SymbolKind.S_IF,
      SymbolKind.S_THEN,
      SymbolKind.S_ELSE,
      SymbolKind.S_END,
      SymbolKind.S_TRUE,
      SymbolKind.S_FALSE,
      SymbolKind.S_INTEGER,
      SymbolKind.S_REAL,
      SymbolKind.S_BOOLEAN,
      SymbolKind.S_ASSIGN,
      SymbolKind.S_LT,
      SymbolKind.S_LE,
      SymbolKind.S_GT,
      SymbolKind.S_GE,
      SymbolKind.S_EQ,
      SymbolKind.S_NE,
      SymbolKind.S_PLUS,
      SymbolKind.S_MINUS,
      SymbolKind.S_MUL,
      SymbolKind.S_DIV,
      SymbolKind.S_MOD,
      SymbolKind.S_AND,
      SymbolKind.S_OR,
      SymbolKind.S_XOR,
      SymbolKind.S_NOT,
      SymbolKind.S_IDENTIFIER,
      SymbolKind.S_INTEGERNUMBER,
      SymbolKind.S_REALNUMBER,
      SymbolKind.S_COMMA,
      SymbolKind.S_SEMICOLON,
      SymbolKind.S_LPAREN,
      SymbolKind.S_RPAREN,
      SymbolKind.S_LBRACKET,
      SymbolKind.S_RBRACKET,
      SymbolKind.S_DOT,
      SymbolKind.S_COLON,
      SymbolKind.S_RANGE,
      SymbolKind.S_REVERSE,
      SymbolKind.S_IN,
      SymbolKind.S_NEWLINE,
      SymbolKind.S_WHITESPACE,
      SymbolKind.S_TAB,
      SymbolKind.S_UNKNOWN,
      SymbolKind.S_YYACCEPT,
      SymbolKind.S_Program,
      SymbolKind.S_Declaration,
      SymbolKind.S_VariableDeclaration,
      SymbolKind.S_TypeDeclaration,
      SymbolKind.S_Type,
      SymbolKind.S_PrimitiveType,
      SymbolKind.S_RecordDeclaration,
      SymbolKind.S_ArrayDeclaration,
      SymbolKind.S_Expression,
      SymbolKind.S_Relation,
      SymbolKind.S_Simple,
      SymbolKind.S_Factor,
      SymbolKind.S_Summand,
      SymbolKind.S_Primary,
      SymbolKind.S_ModifiablePrimary
    };

    static final SymbolKind get(int code) {
      return values_[code];
    }

    public final int getCode() {
      return this.yycode_;
    }

    /* Return YYSTR after stripping away unnecessary quotes and
       backslashes, so that it's suitable for yyerror.  The heuristic is
       that double-quoting is unnecessary unless the string contains an
       apostrophe, a comma, or backslash (other than backslash-backslash).
       YYSTR is taken from yytname.  */
    private static String yytnamerr_(String yystr)
    {
      if (yystr.charAt (0) == '"')
        {
          StringBuffer yyr = new StringBuffer();
          strip_quotes: for (int i = 1; i < yystr.length(); i++)
            switch (yystr.charAt(i))
              {
              case '\'':
              case ',':
                break strip_quotes;

              case '\\':
                if (yystr.charAt(++i) != '\\')
                  break strip_quotes;
                /* Fall through.  */
              default:
                yyr.append(yystr.charAt(i));
                break;

              case '"':
                return yyr.toString();
              }
        }
      return yystr;
    }

    /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
       First, the terminals, then, starting at \a YYNTOKENS_, nonterminals.  */
    private static final String[] yytname_ = yytname_init();
  private static final String[] yytname_init()
  {
    return new String[]
    {
  "\"end of file\"", "error", "\"invalid token\"", "VAR", "TYPE",
  "ROUTINE", "RETURN", "IS", "RECORD", "ARRAY", "WHILE", "FOR", "LOOP",
  "IF", "THEN", "ELSE", "END", "TRUE", "FALSE", "INTEGER", "REAL",
  "BOOLEAN", "ASSIGN", "LT", "LE", "GT", "GE", "EQ", "NE", "PLUS", "MINUS",
  "MUL", "DIV", "MOD", "AND", "OR", "XOR", "NOT", "IDENTIFIER",
  "INTEGERNUMBER", "REALNUMBER", "COMMA", "SEMICOLON", "LPAREN", "RPAREN",
  "LBRACKET", "RBRACKET", "DOT", "COLON", "RANGE", "REVERSE", "IN",
  "NEWLINE", "WHITESPACE", "TAB", "UNKNOWN", "$accept", "Program",
  "Declaration", "VariableDeclaration", "TypeDeclaration", "Type",
  "PrimitiveType", "RecordDeclaration", "ArrayDeclaration", "Expression",
  "Relation", "Simple", "Factor", "Summand", "Primary",
  "ModifiablePrimary", null
    };
  }

    /* The user-facing name of this symbol.  */
    public final String getName() {
      return yytnamerr_(yytname_[yycode_]);
    }

  };


  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>YYParser</tt>.
   */
  public interface Lexer {
    /* Token kinds.  */
    /** Token "end of file", to be returned by the scanner.  */
    static final int YYEOF = 0;
    /** Token error, to be returned by the scanner.  */
    static final int YYerror = 256;
    /** Token "invalid token", to be returned by the scanner.  */
    static final int YYUNDEF = 257;
    /** Token VAR, to be returned by the scanner.  */
    static final int VAR = 258;
    /** Token TYPE, to be returned by the scanner.  */
    static final int TYPE = 259;
    /** Token ROUTINE, to be returned by the scanner.  */
    static final int ROUTINE = 260;
    /** Token RETURN, to be returned by the scanner.  */
    static final int RETURN = 261;
    /** Token IS, to be returned by the scanner.  */
    static final int IS = 262;
    /** Token RECORD, to be returned by the scanner.  */
    static final int RECORD = 263;
    /** Token ARRAY, to be returned by the scanner.  */
    static final int ARRAY = 264;
    /** Token WHILE, to be returned by the scanner.  */
    static final int WHILE = 265;
    /** Token FOR, to be returned by the scanner.  */
    static final int FOR = 266;
    /** Token LOOP, to be returned by the scanner.  */
    static final int LOOP = 267;
    /** Token IF, to be returned by the scanner.  */
    static final int IF = 268;
    /** Token THEN, to be returned by the scanner.  */
    static final int THEN = 269;
    /** Token ELSE, to be returned by the scanner.  */
    static final int ELSE = 270;
    /** Token END, to be returned by the scanner.  */
    static final int END = 271;
    /** Token TRUE, to be returned by the scanner.  */
    static final int TRUE = 272;
    /** Token FALSE, to be returned by the scanner.  */
    static final int FALSE = 273;
    /** Token INTEGER, to be returned by the scanner.  */
    static final int INTEGER = 274;
    /** Token REAL, to be returned by the scanner.  */
    static final int REAL = 275;
    /** Token BOOLEAN, to be returned by the scanner.  */
    static final int BOOLEAN = 276;
    /** Token ASSIGN, to be returned by the scanner.  */
    static final int ASSIGN = 277;
    /** Token LT, to be returned by the scanner.  */
    static final int LT = 278;
    /** Token LE, to be returned by the scanner.  */
    static final int LE = 279;
    /** Token GT, to be returned by the scanner.  */
    static final int GT = 280;
    /** Token GE, to be returned by the scanner.  */
    static final int GE = 281;
    /** Token EQ, to be returned by the scanner.  */
    static final int EQ = 282;
    /** Token NE, to be returned by the scanner.  */
    static final int NE = 283;
    /** Token PLUS, to be returned by the scanner.  */
    static final int PLUS = 284;
    /** Token MINUS, to be returned by the scanner.  */
    static final int MINUS = 285;
    /** Token MUL, to be returned by the scanner.  */
    static final int MUL = 286;
    /** Token DIV, to be returned by the scanner.  */
    static final int DIV = 287;
    /** Token MOD, to be returned by the scanner.  */
    static final int MOD = 288;
    /** Token AND, to be returned by the scanner.  */
    static final int AND = 289;
    /** Token OR, to be returned by the scanner.  */
    static final int OR = 290;
    /** Token XOR, to be returned by the scanner.  */
    static final int XOR = 291;
    /** Token NOT, to be returned by the scanner.  */
    static final int NOT = 292;
    /** Token IDENTIFIER, to be returned by the scanner.  */
    static final int IDENTIFIER = 293;
    /** Token INTEGERNUMBER, to be returned by the scanner.  */
    static final int INTEGERNUMBER = 294;
    /** Token REALNUMBER, to be returned by the scanner.  */
    static final int REALNUMBER = 295;
    /** Token COMMA, to be returned by the scanner.  */
    static final int COMMA = 296;
    /** Token SEMICOLON, to be returned by the scanner.  */
    static final int SEMICOLON = 297;
    /** Token LPAREN, to be returned by the scanner.  */
    static final int LPAREN = 298;
    /** Token RPAREN, to be returned by the scanner.  */
    static final int RPAREN = 299;
    /** Token LBRACKET, to be returned by the scanner.  */
    static final int LBRACKET = 300;
    /** Token RBRACKET, to be returned by the scanner.  */
    static final int RBRACKET = 301;
    /** Token DOT, to be returned by the scanner.  */
    static final int DOT = 302;
    /** Token COLON, to be returned by the scanner.  */
    static final int COLON = 303;
    /** Token RANGE, to be returned by the scanner.  */
    static final int RANGE = 304;
    /** Token REVERSE, to be returned by the scanner.  */
    static final int REVERSE = 305;
    /** Token IN, to be returned by the scanner.  */
    static final int IN = 306;
    /** Token NEWLINE, to be returned by the scanner.  */
    static final int NEWLINE = 307;
    /** Token WHITESPACE, to be returned by the scanner.  */
    static final int WHITESPACE = 308;
    /** Token TAB, to be returned by the scanner.  */
    static final int TAB = 309;
    /** Token UNKNOWN, to be returned by the scanner.  */
    static final int UNKNOWN = 310;

    /** Deprecated, use YYEOF instead.  */
    public static final int EOF = YYEOF;


    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.
     */
    Object getLVal();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token.
     */
    int yylex() throws java.io.IOException;

    /**
     * Emit an errorin a user-defined way.
     *
     *
     * @param msg The string for the error message.
     */
     void yyerror(String msg);


  }


  /**
   * The object doing lexical analysis for us.
   */
  private main.lexer.Lexer yylexer;





  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public YYParser(main.lexer.Lexer yylexer)
  {

    this.yylexer = yylexer;

  }



  private int yynerrs = 0;

  /**
   * The number of syntax errors so far.
   */
  public final int getNumberOfErrors() { return yynerrs; }

  /**
   * Print an error message via the lexer.
   *
   * @param msg The error message.
   */
  public final void yyerror(String msg) {
      yylexer.yyerror(msg);
  }



  private final class YYStack {
    private int[] stateStack = new int[16];
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;

    public final void push(int state, Object value) {
      height++;
      if (size == height) {
        int[] newStateStack = new int[size * 2];
        System.arraycopy(stateStack, 0, newStateStack, 0, height);
        stateStack = newStateStack;

        Object[] newValueStack = new Object[size * 2];
        System.arraycopy(valueStack, 0, newValueStack, 0, height);
        valueStack = newValueStack;

        size *= 2;
      }

      stateStack[height] = state;
      valueStack[height] = value;
    }

    public final void pop() {
      pop(1);
    }

    public final void pop(int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (0 < num) {
        java.util.Arrays.fill(valueStack, height - num + 1, height + 1, null);
      }
      height -= num;
    }

    public final int stateAt(int i) {
      return stateStack[height - i];
    }

    public final Object valueAt(int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print(java.io.PrintStream out) {
      out.print ("Stack now");

      for (int i = 0; i <= height; i++) {
        out.print(' ');
        out.print(stateStack[i]);
      }
      out.println();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).
   */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).
   */
  public static final int YYABORT = 1;



  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.
   */
  public static final int YYERROR = 2;

  /**
   * Internal return codes that are not supported for user semantic
   * actions.
   */
  private static final int YYERRLAB = 3;
  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;


  private int yyerrstatus_ = 0;


  /**
   * Whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.
   */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  /** Compute post-reduction state.
   * @param yystate   the current state
   * @param yysym     the nonterminal to push on the stack
   */
  private int yyLRGotoState(int yystate, int yysym) {
    int yyr = yypgoto_[yysym - YYNTOKENS_] + yystate;
    if (0 <= yyr && yyr <= YYLAST_ && yycheck_[yyr] == yystate)
      return yytable_[yyr];
    else
      return yydefgoto_[yysym - YYNTOKENS_];
  }

  private int yyaction(int yyn, YYStack yystack, int yylen)
  {
    /* If YYLEN is nonzero, implement the default value of the action:
       '$$ = $1'.  Otherwise, use the top of the stack.

       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    Object yyval = (0 < yylen) ? yystack.valueAt(yylen - 1) : yystack.valueAt(0);

    switch (yyn)
      {
          case 2: /* Program: Declaration  */
  if (yyn == 2)
    /* "parser.y":51  */
    {
        List<Declaration> declarations = new ArrayList<>();
        declarations.add(((Declaration)(yystack.valueAt (0))));
        yyval = new Program(declarations);
    };
  break;


  case 3: /* Program: Program Declaration  */
  if (yyn == 3)
    /* "parser.y":57  */
    {
        List<Declaration> declarations = new ArrayList<>(((Program)(yystack.valueAt (1))).getDeclarations());
        declarations.add(((Declaration)(yystack.valueAt (0))));
        yyval = new Program(declarations);
    };
  break;


  case 4: /* Program: %empty  */
  if (yyn == 4)
    /* "parser.y":63  */
    {
        yyval = new Program(new ArrayList<>());
    };
  break;


  case 5: /* Declaration: VariableDeclaration  */
  if (yyn == 5)
    /* "parser.y":69  */
                        { yyval = ((VariableDeclaration)(yystack.valueAt (0))); };
  break;


  case 6: /* Declaration: TypeDeclaration  */
  if (yyn == 6)
    /* "parser.y":70  */
                      { yyval = ((TypeDeclaration)(yystack.valueAt (0))); };
  break;


  case 7: /* VariableDeclaration: VAR IDENTIFIER COLON Type IS Expression  */
  if (yyn == 7)
    /* "parser.y":74  */
                                            { yyval = new VariableDeclaration(((Token)(yystack.valueAt (4))).toString(), ((Type)(yystack.valueAt (2))).toString(), ((Expression)(yystack.valueAt (0)))); };
  break;


  case 8: /* VariableDeclaration: VAR IDENTIFIER IS Expression  */
  if (yyn == 8)
    /* "parser.y":75  */
                                   { yyval = new VariableDeclaration(((Token)(yystack.valueAt (2))).toString(), null, ((Expression)(yystack.valueAt (0)))); };
  break;


  case 9: /* TypeDeclaration: TYPE IDENTIFIER IS Type  */
  if (yyn == 9)
    /* "parser.y":79  */
                            { yyval = new TypeDeclaration(((Token)(yystack.valueAt (2))).toString(), ((Type)(yystack.valueAt (0))).toString()); };
  break;


  case 10: /* Type: PrimitiveType  */
  if (yyn == 10)
    /* "parser.y":96  */
                  { yyval = ((PrimitiveType)(yystack.valueAt (0))); };
  break;


  case 11: /* Type: ArrayDeclaration  */
  if (yyn == 11)
    /* "parser.y":97  */
                       { yyval = ((ArrayDeclaration)(yystack.valueAt (0))); };
  break;


  case 12: /* Type: RecordDeclaration  */
  if (yyn == 12)
    /* "parser.y":98  */
                        { yyval = ((RecordDeclaration)(yystack.valueAt (0))); };
  break;


  case 13: /* Type: IDENTIFIER  */
  if (yyn == 13)
    /* "parser.y":99  */
                 { yyval = ((Token)(yystack.valueAt (0))); };
  break;


  case 14: /* PrimitiveType: INTEGER  */
  if (yyn == 14)
    /* "parser.y":103  */
            { yyval = new PrimitiveType("integer"); };
  break;


  case 15: /* PrimitiveType: REAL  */
  if (yyn == 15)
    /* "parser.y":104  */
           { yyval = new PrimitiveType("real"); };
  break;


  case 16: /* PrimitiveType: BOOLEAN  */
  if (yyn == 16)
    /* "parser.y":105  */
              { yyval = new PrimitiveType("boolean"); };
  break;


  case 17: /* RecordDeclaration: RECORD VariableDeclaration END  */
  if (yyn == 17)
    /* "parser.y":110  */
        { List<VariableDeclaration> fields = new ArrayList<>(); fields.add(((VariableDeclaration)(yystack.valueAt (1)))); yyval = new RecordDeclaration(fields); };
  break;


  case 18: /* ArrayDeclaration: ARRAY LBRACKET Expression RBRACKET Type  */
  if (yyn == 18)
    /* "parser.y":115  */
        { yyval = new ArrayDeclaration(((Expression)(yystack.valueAt (2))), ((Type)(yystack.valueAt (0))).toString()); };
  break;


  case 19: /* Expression: Relation  */
  if (yyn == 19)
    /* "parser.y":164  */
             { yyval = ((Relation)(yystack.valueAt (0))); };
  break;


  case 20: /* Expression: Expression AND Relation  */
  if (yyn == 20)
    /* "parser.y":165  */
                              { yyval = new BinaryExpression(TokenType.AND, ((Expression)(yystack.valueAt (2))), ((Relation)(yystack.valueAt (0)))); };
  break;


  case 21: /* Expression: Expression XOR Relation  */
  if (yyn == 21)
    /* "parser.y":166  */
                              { yyval = new BinaryExpression(TokenType.XOR, ((Expression)(yystack.valueAt (2))), ((Relation)(yystack.valueAt (0)))); };
  break;


  case 22: /* Expression: Expression OR Relation  */
  if (yyn == 22)
    /* "parser.y":167  */
                             { yyval = new BinaryExpression(TokenType.OR, ((Expression)(yystack.valueAt (2))), ((Relation)(yystack.valueAt (0)))); };
  break;


  case 23: /* Relation: Simple  */
  if (yyn == 23)
    /* "parser.y":171  */
           { yyval = ((Simple)(yystack.valueAt (0))); };
  break;


  case 24: /* Relation: Relation LT Simple  */
  if (yyn == 24)
    /* "parser.y":172  */
                         { yyval = new Relation(TokenType.LT, ((Relation)(yystack.valueAt (2))), ((Simple)(yystack.valueAt (0)))); };
  break;


  case 25: /* Relation: Relation LE Simple  */
  if (yyn == 25)
    /* "parser.y":173  */
                         { yyval = new Relation(TokenType.LE, ((Relation)(yystack.valueAt (2))), ((Simple)(yystack.valueAt (0)))); };
  break;


  case 26: /* Relation: Relation GT Simple  */
  if (yyn == 26)
    /* "parser.y":174  */
                         { yyval = new Relation(TokenType.GT, ((Relation)(yystack.valueAt (2))), ((Simple)(yystack.valueAt (0)))); };
  break;


  case 27: /* Relation: Relation GE Simple  */
  if (yyn == 27)
    /* "parser.y":175  */
                         { yyval = new Relation(TokenType.GE, ((Relation)(yystack.valueAt (2))), ((Simple)(yystack.valueAt (0)))); };
  break;


  case 28: /* Relation: Relation EQ Simple  */
  if (yyn == 28)
    /* "parser.y":176  */
                         { yyval = new Relation(TokenType.EQ, ((Relation)(yystack.valueAt (2))), ((Simple)(yystack.valueAt (0)))); };
  break;


  case 29: /* Relation: Relation NE Simple  */
  if (yyn == 29)
    /* "parser.y":177  */
                         { yyval = new Relation(TokenType.NE, ((Relation)(yystack.valueAt (2))), ((Simple)(yystack.valueAt (0)))); };
  break;


  case 30: /* Simple: Factor  */
  if (yyn == 30)
    /* "parser.y":181  */
           { yyval = ((Factor)(yystack.valueAt (0))); };
  break;


  case 31: /* Simple: Simple PLUS Factor  */
  if (yyn == 31)
    /* "parser.y":182  */
                         { yyval = new BinaryExpression(TokenType.PLUS, ((Simple)(yystack.valueAt (2))), ((Factor)(yystack.valueAt (0)))); };
  break;


  case 32: /* Simple: Simple MINUS Factor  */
  if (yyn == 32)
    /* "parser.y":183  */
                          { yyval = new BinaryExpression(TokenType.MINUS, ((Simple)(yystack.valueAt (2))), ((Factor)(yystack.valueAt (0)))); };
  break;


  case 33: /* Factor: Summand  */
  if (yyn == 33)
    /* "parser.y":187  */
            { yyval = ((Summand)(yystack.valueAt (0))); };
  break;


  case 34: /* Factor: Factor MUL Summand  */
  if (yyn == 34)
    /* "parser.y":188  */
                         { yyval = new BinaryExpression(TokenType.MUL, ((Factor)(yystack.valueAt (2))), ((Summand)(yystack.valueAt (0)))); };
  break;


  case 35: /* Factor: Factor DIV Summand  */
  if (yyn == 35)
    /* "parser.y":189  */
                         { yyval = new BinaryExpression(TokenType.DIV, ((Factor)(yystack.valueAt (2))), ((Summand)(yystack.valueAt (0)))); };
  break;


  case 36: /* Factor: Factor MOD Summand  */
  if (yyn == 36)
    /* "parser.y":190  */
                         { yyval = new BinaryExpression(TokenType.MOD, ((Factor)(yystack.valueAt (2))), ((Summand)(yystack.valueAt (0)))); };
  break;


  case 37: /* Summand: Primary  */
  if (yyn == 37)
    /* "parser.y":194  */
            { yyval = ((Primary)(yystack.valueAt (0))); };
  break;


  case 38: /* Summand: LPAREN Expression RPAREN  */
  if (yyn == 38)
    /* "parser.y":195  */
                               { yyval = ((Expression)(yystack.valueAt (1))); };
  break;


  case 39: /* Primary: INTEGERNUMBER  */
  if (yyn == 39)
    /* "parser.y":199  */
                  { yyval = new IntegerLiteral(((Token)(yystack.valueAt (0))).getIntegerValue()); };
  break;


  case 40: /* Primary: REALNUMBER  */
  if (yyn == 40)
    /* "parser.y":200  */
                 { yyval = new RealLiteral(((Token)(yystack.valueAt (0))).getRealValue()); };
  break;


  case 41: /* Primary: TRUE  */
  if (yyn == 41)
    /* "parser.y":201  */
           { yyval = new BooleanLiteral(true); };
  break;


  case 42: /* Primary: FALSE  */
  if (yyn == 42)
    /* "parser.y":202  */
            { yyval = new BooleanLiteral(false); };
  break;


  case 43: /* Primary: ModifiablePrimary  */
  if (yyn == 43)
    /* "parser.y":203  */
                        { yyval = ((ModifiablePrimary)(yystack.valueAt (0))); };
  break;


  case 44: /* Primary: NOT Primary  */
  if (yyn == 44)
    /* "parser.y":204  */
                  { yyval = new NotExpression(((Primary)(yystack.valueAt (0)))); };
  break;


  case 45: /* ModifiablePrimary: IDENTIFIER  */
  if (yyn == 45)
    /* "parser.y":208  */
               { yyval = new Identifier(((Token)(yystack.valueAt (0))).getText()); };
  break;


  case 46: /* ModifiablePrimary: ModifiablePrimary DOT IDENTIFIER  */
  if (yyn == 46)
    /* "parser.y":209  */
                                       { yyval = new MemberAccess(((ModifiablePrimary)(yystack.valueAt (2))), ((Token)(yystack.valueAt (0))).getText()); };
  break;


  case 47: /* ModifiablePrimary: ModifiablePrimary LBRACKET Expression RBRACKET  */
  if (yyn == 47)
    /* "parser.y":210  */
                                                     { yyval = new ArrayAccess(((ModifiablePrimary)(yystack.valueAt (3))), ((Expression)(yystack.valueAt (1)))); };
  break;



/* "YYParser.java":949  */

        default: break;
      }

    yystack.pop(yylen);
    yylen = 0;
    /* Shift the result of the reduction.  */
    int yystate = yyLRGotoState(yystack.stateAt(0), yyr1_[yyn]);
    yystack.push(yystate, yyval);
    return YYNEWSTATE;
  }




  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse() throws java.io.IOException

  {


    /* Lookahead token kind.  */
    int yychar = YYEMPTY_;
    /* Lookahead symbol kind.  */
    SymbolKind yytoken = null;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;
    YYStack yystack = new YYStack ();
    int label = YYNEWSTATE;



    /* Semantic value of the lookahead.  */
    Object yylval = null;



    yyerrstatus_ = 0;
    yynerrs = 0;

    /* Initialize the stack.  */
    yystack.push (yystate, yylval);



    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
           pushed when we come here.  */
      case YYNEWSTATE:

        /* Accept?  */
        if (yystate == YYFINAL_)
          return true;

        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yyPactValueIsDefault (yyn))
          {
            label = YYDEFAULT;
            break;
          }

        /* Read a lookahead token.  */
        if (yychar == YYEMPTY_)
          {

            yychar = yylexer.yylex ();
            yylval = yylexer.getLVal();

          }

        /* Convert token to internal form.  */
        yytoken = yytranslate_ (yychar);

        if (yytoken == SymbolKind.S_YYerror)
          {
            // The scanner already issued an error message, process directly
            // to error recovery.  But do not keep the error token as
            // lookahead, it is too special and may lead us to an endless
            // loop in error recovery. */
            yychar = Lexer.YYUNDEF;
            yytoken = SymbolKind.S_YYUNDEF;
            label = YYERRLAB1;
          }
        else
          {
            /* If the proper action on seeing token YYTOKEN is to reduce or to
               detect an error, take that action.  */
            yyn += yytoken.getCode();
            if (yyn < 0 || YYLAST_ < yyn || yycheck_[yyn] != yytoken.getCode()) {
              label = YYDEFAULT;
            }

            /* <= 0 means reduce or error.  */
            else if ((yyn = yytable_[yyn]) <= 0)
              {
                if (yyTableValueIsError(yyn)) {
                  label = YYERRLAB;
                } else {
                  yyn = -yyn;
                  label = YYREDUCE;
                }
              }

            else
              {
                /* Shift the lookahead token.  */
                /* Discard the token being shifted.  */
                yychar = YYEMPTY_;

                /* Count tokens shifted since error; after three, turn off error
                   status.  */
                if (yyerrstatus_ > 0)
                  --yyerrstatus_;

                yystate = yyn;
                yystack.push(yystate, yylval);
                label = YYNEWSTATE;
              }
          }
        break;

      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYERRLAB;
        else
          label = YYREDUCE;
        break;

      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction(yyn, yystack, yylen);
        yystate = yystack.stateAt(0);
        break;

      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYERRLAB:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
            ++yynerrs;
            if (yychar == YYEMPTY_)
              yytoken = null;
            yyreportSyntaxError(new Context(this, yystack, yytoken));
          }

        if (yyerrstatus_ == 3)
          {
            /* If just tried and failed to reuse lookahead token after an
               error, discard it.  */

            if (yychar <= Lexer.YYEOF)
              {
                /* Return failure if at end of input.  */
                if (yychar == Lexer.YYEOF)
                  return false;
              }
            else
              yychar = YYEMPTY_;
          }

        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `-------------------------------------------------*/
      case YYERROR:
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt(0);
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;       /* Each real token shifted decrements this.  */

        // Pop stack until we find a state that shifts the error token.
        for (;;)
          {
            yyn = yypact_[yystate];
            if (!yyPactValueIsDefault (yyn))
              {
                yyn += SymbolKind.S_YYerror.getCode();
                if (0 <= yyn && yyn <= YYLAST_
                    && yycheck_[yyn] == SymbolKind.S_YYerror.getCode())
                  {
                    yyn = yytable_[yyn];
                    if (0 < yyn)
                      break;
                  }
              }

            /* Pop the current state because it cannot handle the
             * error token.  */
            if (yystack.height == 0)
              return false;


            yystack.pop ();
            yystate = yystack.stateAt(0);
          }

        if (label == YYABORT)
          /* Leave the switch.  */
          break;



        /* Shift the error token.  */

        yystate = yyn;
        yystack.push (yyn, yylval);
        label = YYNEWSTATE;
        break;

        /* Accept.  */
      case YYACCEPT:
        return true;

        /* Abort.  */
      case YYABORT:
        return false;
      }
}




  /**
   * Information needed to get the list of expected tokens and to forge
   * a syntax error diagnostic.
   */
  public static final class Context {
    Context(YYParser parser, YYStack stack, SymbolKind token) {
      yyparser = parser;
      yystack = stack;
      yytoken = token;
    }

    private YYParser yyparser;
    private YYStack yystack;


    /**
     * The symbol kind of the lookahead token.
     */
    public final SymbolKind getToken() {
      return yytoken;
    }

    private SymbolKind yytoken;
    static final int NTOKENS = YYParser.YYNTOKENS_;

    /**
     * Put in YYARG at most YYARGN of the expected tokens given the
     * current YYCTX, and return the number of tokens stored in YYARG.  If
     * YYARG is null, return the number of expected tokens (guaranteed to
     * be less than YYNTOKENS).
     */
    int getExpectedTokens(SymbolKind yyarg[], int yyargn) {
      return getExpectedTokens (yyarg, 0, yyargn);
    }

    int getExpectedTokens(SymbolKind yyarg[], int yyoffset, int yyargn) {
      int yycount = yyoffset;
      int yyn = yypact_[this.yystack.stateAt(0)];
      if (!yyPactValueIsDefault(yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative
             indexes in YYCHECK.  In other words, skip the first
             -YYN actions for this state because they are default
             actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST_ - yyn + 1;
          int yyxend = yychecklim < NTOKENS ? yychecklim : NTOKENS;
          for (int yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck_[yyx + yyn] == yyx && yyx != SymbolKind.S_YYerror.getCode()
                && !yyTableValueIsError(yytable_[yyx + yyn]))
              {
                if (yyarg == null)
                  yycount += 1;
                else if (yycount == yyargn)
                  return 0; // FIXME: this is incorrect.
                else
                  yyarg[yycount++] = SymbolKind.get(yyx);
              }
        }
      if (yyarg != null && yycount == yyoffset && yyoffset < yyargn)
        yyarg[yycount] = null;
      return yycount - yyoffset;
    }
  }





  /**
   * Build and emit a "syntax error" message in a user-defined way.
   *
   * @param yyctx  The context of the error.
   */
  private void yyreportSyntaxError(Context yyctx) {
      yyerror("syntax error");
  }

  /**
   * Whether the given <code>yypact_</code> value indicates a defaulted state.
   * @param yyvalue   the value to check
   */
  private static boolean yyPactValueIsDefault(int yyvalue) {
    return yyvalue == yypact_ninf_;
  }

  /**
   * Whether the given <code>yytable_</code>
   * value indicates a syntax error.
   * @param yyvalue the value to check
   */
  private static boolean yyTableValueIsError(int yyvalue) {
    return yyvalue == yytable_ninf_;
  }

  private static final byte yypact_ninf_ = -28;
  private static final byte yytable_ninf_ = -1;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final byte[] yypact_ = yypact_init();
  private static final byte[] yypact_init()
  {
    return new byte[]
    {
      35,   -25,   -20,    17,   -28,   -28,   -28,    -6,    22,   -28,
     -28,   -12,    -5,    -5,   -28,   -28,     6,   -28,   -28,   -28,
     -12,     0,    27,    46,    33,   -28,   -28,   -15,    53,    32,
     -28,   -28,   -28,   -28,    60,   -28,   -28,   -28,   -28,   -28,
      13,   -12,   -12,   -12,   -12,   -12,   -12,   -12,   -12,   -12,
     -12,   -12,   -12,   -12,   -12,   -12,    40,    65,   -12,   -12,
     -28,    27,    27,    27,    46,    46,    46,    46,    46,    46,
      33,    33,   -28,   -28,   -28,   -27,   -28,   -28,   -24,     0,
     -28,    -5,   -28
    };
  }

/* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE does not specify something else to do.  Zero
   means the default is an error.  */
  private static final byte[] yydefact_ = yydefact_init();
  private static final byte[] yydefact_init()
  {
    return new byte[]
    {
       4,     0,     0,     0,     2,     5,     6,     0,     0,     1,
       3,     0,     0,     0,    41,    42,     0,    45,    39,    40,
       0,     8,    19,    23,    30,    33,    37,    43,     0,     0,
      14,    15,    16,    13,     0,    10,    12,    11,     9,    44,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
      38,    20,    22,    21,    24,    25,    26,    27,    28,    29,
      31,    32,    34,    35,    36,     0,    46,    17,     0,     7,
      47,     0,    18
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final byte[] yypgoto_ = yypgoto_init();
  private static final byte[] yypgoto_init()
  {
    return new byte[]
    {
     -28,   -28,    79,    55,   -28,   -13,   -28,   -28,   -28,   -18,
      28,    14,    29,    20,    68,   -28
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final byte[] yydefgoto_ = yydefgoto_init();
  private static final byte[] yydefgoto_init()
  {
    return new byte[]
    {
       0,     3,     4,     5,     6,    34,    35,    36,    37,    21,
      22,    23,    24,    25,    26,    27
    };
  }

/* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule whose
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
  private static final byte[] yytable_ = yytable_init();
  private static final byte[] yytable_init()
  {
    return new byte[]
    {
      38,    11,    40,    28,    29,    14,    15,    41,    42,    43,
      41,    42,    43,     7,    30,    31,    32,     9,     8,    80,
       1,     2,    81,    14,    15,    16,    17,    18,    19,    13,
      55,    20,    56,    33,    41,    42,    43,    75,     1,     2,
      78,    79,    12,    16,    17,    18,    19,    41,    42,    43,
      44,    45,    46,    47,    48,    49,     1,    60,    64,    65,
      66,    67,    68,    69,    52,    53,    54,    59,    82,    61,
      62,    63,    72,    73,    74,    50,    51,    58,    76,    70,
      71,    77,    10,    57,    39
    };
  }

private static final byte[] yycheck_ = yycheck_init();
  private static final byte[] yycheck_init()
  {
    return new byte[]
    {
      13,     7,    20,     8,     9,    17,    18,    34,    35,    36,
      34,    35,    36,    38,    19,    20,    21,     0,    38,    46,
       3,     4,    46,    17,    18,    37,    38,    39,    40,     7,
      45,    43,    47,    38,    34,    35,    36,    55,     3,     4,
      58,    59,    48,    37,    38,    39,    40,    34,    35,    36,
      23,    24,    25,    26,    27,    28,     3,    44,    44,    45,
      46,    47,    48,    49,    31,    32,    33,     7,    81,    41,
      42,    43,    52,    53,    54,    29,    30,    45,    38,    50,
      51,    16,     3,    28,    16
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final byte[] yystos_ = yystos_init();
  private static final byte[] yystos_init()
  {
    return new byte[]
    {
       0,     3,     4,    57,    58,    59,    60,    38,    38,     0,
      58,     7,    48,     7,    17,    18,    37,    38,    39,    40,
      43,    65,    66,    67,    68,    69,    70,    71,     8,     9,
      19,    20,    21,    38,    61,    62,    63,    64,    61,    70,
      65,    34,    35,    36,    23,    24,    25,    26,    27,    28,
      29,    30,    31,    32,    33,    45,    47,    59,    45,     7,
      44,    66,    66,    66,    67,    67,    67,    67,    67,    67,
      68,    68,    69,    69,    69,    65,    38,    16,    65,    65,
      46,    46,    61
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final byte[] yyr1_ = yyr1_init();
  private static final byte[] yyr1_init()
  {
    return new byte[]
    {
       0,    56,    57,    57,    57,    58,    58,    59,    59,    60,
      61,    61,    61,    61,    62,    62,    62,    63,    64,    65,
      65,    65,    65,    66,    66,    66,    66,    66,    66,    66,
      67,    67,    67,    68,    68,    68,    68,    69,    69,    70,
      70,    70,    70,    70,    70,    71,    71,    71
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     1,     2,     0,     1,     1,     6,     4,     4,
       1,     1,     1,     1,     1,     1,     1,     3,     5,     1,
       3,     3,     3,     1,     3,     3,     3,     3,     3,     3,
       1,     3,     3,     1,     3,     3,     3,     1,     3,     1,
       1,     1,     1,     1,     2,     1,     3,     4
    };
  }




  /* YYTRANSLATE_(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
     as returned by yylex, with out-of-bounds checking.  */
  private static final SymbolKind yytranslate_(int t)
  {
    // Last valid token kind.
    int code_max = 310;
    if (t <= 0)
      return SymbolKind.S_YYEOF;
    else if (t <= code_max)
      return SymbolKind.get(yytranslate_table_[t]);
    else
      return SymbolKind.S_YYUNDEF;
  }
  private static final byte[] yytranslate_table_ = yytranslate_table_init();
  private static final byte[] yytranslate_table_init()
  {
    return new byte[]
    {
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49,    50,    51,    52,    53,    54,
      55
    };
  }


  private static final int YYLAST_ = 84;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 9;
  private static final int YYNTOKENS_ = 56;


}
/* "parser.y":213  */


