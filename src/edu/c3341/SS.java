package edu.c3341;

import java.util.Optional;

public class SS {

    //<stmt seq> ::=<stmt> | <stmt> <stmt seq>

    private Stmt stmt;
    private Optional<SS> ss;

    public SS() {
        this.stmt = null;
        this.ss = Optional.empty();
    }

    public void parseSS() {
        this.stmt = new Stmt();
        try {

            this.stmt.parseStmt();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        if (Interpreter.t.getToken() == TokenKind.END
                || Interpreter.t.getToken() == TokenKind.ELSE
                || Interpreter.t.getToken() == TokenKind.EOF) {
            return;
        }
        if (Interpreter.t.getToken() != TokenKind.IDENTIFIER
                && Interpreter.t.getToken() != TokenKind.IF
                && Interpreter.t.getToken() != TokenKind.WHILE
                && Interpreter.t.getToken() != TokenKind.READ
                && Interpreter.t.getToken() != TokenKind.WRITE) {
            assert false : ""
                    + "statement sequence node error, the end of all statements is not found.";
        } else {
            SS ss1 = new SS();
            try {
                //error handling of no end is impossible except for EOF
                ss1.parseSS();
            } catch (AssertionError e) {
                System.out.println(e.getMessage());
            }
            this.ss = Optional.of(ss1);
        }
    }

    public void printSS() {

        this.stmt.printStmt();
        if (this.ss.isPresent()) {
            this.ss.get().printSS();
        }
    }

    public void execSS() {
        this.stmt.execStmt();
        if (this.ss.isPresent()) {
            this.ss.get().execSS();
        }
    }

}
