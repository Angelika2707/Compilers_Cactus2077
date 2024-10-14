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
import java.util.ArrayList;
/* "%code imports" blocks.  */
/* "parser.y":5  */

    import main.token.TokenType;

/* "Parser.java":49  */

/**
 * A Bison parser, automatically generated from <tt>parser.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
class YYParser
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
    S_Type(59),                    /* Type  */
    S_Statement(60),               /* Statement  */
    S_Expression(61),              /* Expression  */
    S_Function(62);                /* Function  */


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
      SymbolKind.S_Type,
      SymbolKind.S_Statement,
      SymbolKind.S_Expression,
      SymbolKind.S_Function
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
  "Declaration", "Type", "Statement", "Expression", "Function", null
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
  private Lexer yylexer;





  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public YYParser(Lexer yylexer)
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
          case 5: /* Declaration: VAR IDENTIFIER COLON Type IS Expression  */
  if (yyn == 5)
    /* "parser.y":27  */
        { System.out.println("Variable declaration: " + ((TokenType)(yystack.valueAt (4)))); };
  break;


  case 9: /* Type: ARRAY LBRACKET INTEGERNUMBER RBRACKET Type  */
  if (yyn == 9)
    /* "parser.y":35  */
        { System.out.println("Array type"); };
  break;


  case 10: /* Type: RECORD Declaration END  */
  if (yyn == 10)
    /* "parser.y":37  */
        { System.out.println("Record type"); };
  break;


  case 12: /* Statement: IF Expression THEN Statement ELSE Statement END  */
  if (yyn == 12)
    /* "parser.y":43  */
        { System.out.println("If-else statement"); };
  break;


  case 13: /* Statement: WHILE Expression LOOP Statement END  */
  if (yyn == 13)
    /* "parser.y":45  */
        { System.out.println("While loop"); };
  break;


  case 14: /* Statement: FOR IDENTIFIER IN Expression RANGE Expression LOOP Statement END  */
  if (yyn == 14)
    /* "parser.y":47  */
        { System.out.println("For loop"); };
  break;


  case 15: /* Statement: RETURN Expression  */
  if (yyn == 15)
    /* "parser.y":49  */
        { System.out.println("Return statement"); };
  break;


  case 32: /* Function: ROUTINE IDENTIFIER LPAREN RPAREN IS Declaration SEMICOLON Statement END  */
  if (yyn == 32)
    /* "parser.y":73  */
        { System.out.println("Function definition: " + ((TokenType)(yystack.valueAt (7)))); };
  break;



/* "Parser.java":647  */

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

  private static final byte yypact_ninf_ = -47;
  private static final byte yytable_ninf_ = -1;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final byte[] yypact_ = yypact_init();
  private static final byte[] yypact_init()
  {
    return new byte[]
    {
      12,   -35,   -33,    24,    24,   -32,    24,   -18,     7,   -29,
     -28,   -23,   -40,   -17,   -47,   -47,    24,   -47,   -47,   -47,
      24,   123,    75,   -41,    99,    24,   -47,   -47,   -47,   -47,
     124,   -24,   123,    59,    24,    24,    24,    24,    24,    24,
      24,    24,    24,    43,    24,    43,   123,    25,   -16,   -47,
     -47,   -47,    20,    36,   -47,   123,   123,   123,   123,   123,
     123,   123,   123,   123,    28,    46,    30,    35,    13,    24,
      25,   -47,    24,    43,   -47,     0,   123,    15,    85,    39,
     124,    43,    43,   -47,   -47,    42,    44,   -47,   -47
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
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,    26,    27,     0,    30,    28,    29,
       0,    15,     0,     0,     0,     0,     1,     2,     3,     4,
       0,     0,    25,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    11,     0,     0,     6,
       8,     7,     0,     0,    31,    24,    23,    22,    21,    20,
      16,    17,    18,    19,     0,     0,     0,     0,     0,     0,
       0,    13,     0,     0,    10,     0,     5,     0,     0,     0,
       0,     0,     0,    12,     9,     0,     0,    32,    14
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final byte[] yypgoto_ = yypgoto_init();
  private static final byte[] yypgoto_init()
  {
    return new byte[]
    {
     -47,   -47,   -46,   -21,   -34,    -4,   -47
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final byte[] yydefgoto_ = yydefgoto_init();
  private static final byte[] yydefgoto_init()
  {
    return new byte[]
    {
       0,     8,     9,    52,    10,    21,    11
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
      22,    67,    24,    12,    25,    13,    23,    26,    30,    64,
      44,    66,    32,    27,    28,     1,    33,     2,     3,    29,
      53,    46,     4,     5,    77,     6,    31,    69,     1,    68,
      55,    56,    57,    58,    59,    60,    61,    62,    63,    79,
      65,    14,    15,    70,    71,    73,    80,    85,    86,     3,
       7,    74,    75,     4,     5,    83,     6,    81,    87,    84,
      88,    16,    17,    18,    19,    76,     0,    20,    78,    34,
      35,    36,    37,    38,     0,    39,    40,    41,    42,     0,
       0,     7,    34,    35,    36,    37,    38,    43,    39,    40,
      41,    42,     0,     0,     0,    72,     0,    82,    34,    35,
      36,    37,    38,    54,    39,    40,    41,    42,    34,    35,
      36,    37,    38,    45,    39,    40,    41,    42,     0,     0,
       0,     0,    34,    35,    36,    37,    38,     0,    39,    40,
      41,    42,    47,    48,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    49,    50,    51,    34,    35,    36,    37,
      38,     0,    39,    40,    41,    42
    };
  }

private static final byte[] yycheck_ = yycheck_init();
  private static final byte[] yycheck_init()
  {
    return new byte[]
    {
       4,    47,     6,    38,    22,    38,    38,     0,    48,    43,
      51,    45,    16,    42,    42,     3,    20,     5,     6,    42,
      44,    25,    10,    11,    70,    13,    43,     7,     3,    45,
      34,    35,    36,    37,    38,    39,    40,    41,    42,    73,
      44,    17,    18,     7,    16,    15,    46,    81,    82,     6,
      38,    16,    39,    10,    11,    16,    13,    42,    16,    80,
      16,    37,    38,    39,    40,    69,    -1,    43,    72,    23,
      24,    25,    26,    27,    -1,    29,    30,    31,    32,    -1,
      -1,    38,    23,    24,    25,    26,    27,    12,    29,    30,
      31,    32,    -1,    -1,    -1,    49,    -1,    12,    23,    24,
      25,    26,    27,    44,    29,    30,    31,    32,    23,    24,
      25,    26,    27,    14,    29,    30,    31,    32,    -1,    -1,
      -1,    -1,    23,    24,    25,    26,    27,    -1,    29,    30,
      31,    32,     8,     9,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    19,    20,    21,    23,    24,    25,    26,
      27,    -1,    29,    30,    31,    32
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final byte[] yystos_ = yystos_init();
  private static final byte[] yystos_init()
  {
    return new byte[]
    {
       0,     3,     5,     6,    10,    11,    13,    38,    57,    58,
      60,    62,    38,    38,    17,    18,    37,    38,    39,    40,
      43,    61,    61,    38,    61,    22,     0,    42,    42,    42,
      48,    43,    61,    61,    23,    24,    25,    26,    27,    29,
      30,    31,    32,    12,    51,    14,    61,     8,     9,    19,
      20,    21,    59,    44,    44,    61,    61,    61,    61,    61,
      61,    61,    61,    61,    60,    61,    60,    58,    45,     7,
       7,    16,    49,    15,    16,    39,    61,    58,    61,    60,
      46,    42,    12,    16,    59,    60,    60,    16,    16
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final byte[] yyr1_ = yyr1_init();
  private static final byte[] yyr1_init()
  {
    return new byte[]
    {
       0,    56,    57,    57,    57,    58,    59,    59,    59,    59,
      59,    60,    60,    60,    60,    60,    61,    61,    61,    61,
      61,    61,    61,    61,    61,    61,    61,    61,    61,    61,
      61,    61,    62
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     2,     2,     2,     6,     1,     1,     1,     5,
       3,     3,     7,     5,     9,     2,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     2,     1,     1,     1,     1,
       1,     3,     9
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


  private static final int YYLAST_ = 155;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 26;
  private static final int YYNTOKENS_ = 56;


}
/* "parser.y":76  */


// Add any additional helper functions or declarations here
