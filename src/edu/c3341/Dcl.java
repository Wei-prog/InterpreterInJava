package edu.c3341;

public class Dcl {

    //decl = int <idlist>

    private IdList il;

    public Dcl() {
        this.il = null;
    }

    public void parseDcl() {
        if (Interpreter.t.getToken() != TokenKind.INT) {
            assert false : ""
                    + "Violation of: decl node syntax error, 'int' expected";

        } else {
            Interpreter.t.skipToken(); //skip int
            this.il = new IdList();
            try {
                this.il.parseIdList();
            } catch (AssertionError e) {
                System.out.println(e.getMessage());
            }
            Interpreter.t.skipToken(); //skip semicolon
        }
    }

    public void printDcl() {
        PrettyPrintHelper.printTabs();
        System.out.print("int ");
        this.il.printIdList();
        System.out.print("; ");
        System.out.println();
    }

    //no run-time behavior for decl

}
