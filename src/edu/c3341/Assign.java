package edu.c3341;

public class Assign {

    private Id id;
    private Exp exp;

    public Assign() {
        this.id = null;
        this.exp = null;
    }

    public void parseAssign() {
        try {
            this.id = Id.parseSSId();
        } catch (AssertionError e) {
            System.out.print(e.getMessage());
        }
        Interpreter.t.skipToken(); //skip Identifier

        if (Interpreter.t.getToken() != TokenKind.ASSIGNMENT_OPERATOR) {
            assert false : ""
                    + "Violation of: Assign node syntax error: '=' expected";
        }
        Interpreter.t.skipToken(); //skip =
        this.exp = new Exp();
        try {
            this.exp.parseExp();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        if (Interpreter.t.getToken() != TokenKind.SEMICOLON) {
            assert false : ""
                    + "Violation of: Assign node syntax error: ';' expected";
        }
        Interpreter.t.skipToken(); //skip ;
    }

    public void printAssign() {
        this.id.printId();
        System.out.print("=");
        this.exp.printExp();
        System.out.print(";");
    }

    public void execAssign() {
        try {
            this.id.setIdVal(this.exp.execExp());
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

}
