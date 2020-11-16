package edu.c3341;

//<stmt> ::= <assign>|<if>|<loop>|<in>|<out>
public class Stmt {

    private int altNo;
    private Assign s1;
    private If s2;
    private Loop s3;
    private In s4;
    private Out s5;

    public Stmt() {
        this.altNo = 0;
        this.s1 = null;
        this.s2 = null;
        this.s3 = null;
        this.s4 = null;
        this.s5 = null;
    }

    public void parseStmt() {
        TokenKind tokNo = Interpreter.t.getToken();
        if (tokNo == TokenKind.IDENTIFIER) {
            this.altNo = 1;
            this.s1 = new Assign();
            this.s1.parseAssign();
        }
        if (tokNo == TokenKind.IF) {
            this.altNo = 2;
            this.s2 = new If();
            this.s2.parseIf();
        }
        if (tokNo == TokenKind.WHILE) {
            this.altNo = 3;
            this.s3 = new Loop();
            this.s3.parseLoop();
        }
        if (tokNo == TokenKind.READ) {
            this.altNo = 4;
            this.s4 = new In();
            this.s4.parseIn();
        }
        if (tokNo == TokenKind.WRITE) {
            this.altNo = 5;
            this.s5 = new Out();
            this.s5.parseOut();
        }

    }

    public void printStmt() {
        PrettyPrintHelper.printTabs();
        if (this.altNo == 1) {
            this.s1.printAssign();

        }
        if (this.altNo == 2) {
            this.s2.printIf();

        }
        if (this.altNo == 3) {
            this.s3.printLoop();

        }
        if (this.altNo == 4) {
            this.s4.printIn();

        }
        if (this.altNo == 5) {
            this.s5.printOut();

        }
        System.out.print('\n');
        return;

    }

    public void execStmt() {
        if (this.altNo == 1) {
            this.s1.execAssign();
            return;
        }
        if (this.altNo == 2) {
            this.s2.execIf();
            return;
        }
        if (this.altNo == 3) {
            this.s3.execLoop();
            return;
        }
        if (this.altNo == 4) {
            this.s4.execIn();
            return;
        }
        if (this.altNo == 5) {
            this.s5.execOut();
            return;
        }

    }

}
