package edu.c3341;

public class Prog {

    // <prog>::=program<decl seq> begin <stmt seq> end
    //instance variables accessed from the program node
    private DS ds;
    private SS ss;

    /**
     * program node constructor
     */
    public Prog() {
        this.ds = null;
        this.ss = null;
    }

    public void parseProg() {
        if (Interpreter.t.getToken() != TokenKind.PROGRAM) {
            assert false : ""
                    + "Violation of: Core program start with keyword PROGRAM";
        }
        Interpreter.t.skipToken(); //skip keyword program
        //parse ds
        this.ds = new DS();
        try {
            this.ds.parseDS();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        if (Interpreter.t.getToken() != TokenKind.BEGIN) {
            assert false : ""
                    + "Violation of: program node syntax error, 'begin' expected";
        }
        Interpreter.t.skipToken(); //skip keyword begin
        //parse ss
        this.ss = new SS();
        try {
            this.ss.parseSS();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }

        if (Interpreter.t.getToken() != TokenKind.END) {
            assert false : ""
                    + "Violation of: program node syntax error, 'end' expected";
        }
        Interpreter.t.skipToken(); //skip keyword end
    }

    public void printProg() {
        PrettyPrintHelper.setToOne();
        System.out.print("program ");
        System.out.print("\n");
        this.ds.printDS();
        System.out.print("begin ");
        System.out.print("\n");
        this.ss.printSS();
        System.out.print("end\n");

    }

    public void ExecProg() {
        this.ss.execSS();
    }

}
