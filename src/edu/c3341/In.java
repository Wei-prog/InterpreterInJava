package edu.c3341;

public class In {

    private IdList il;

    public In() {
        this.il = null;
    }

    public void parseIn() {
        if (Interpreter.t.getToken() != TokenKind.READ) {
            assert false : ""
                    + "Violation of: in node sytax error, 'read' expected";
        }
        Interpreter.t.skipToken(); //skip read
        this.il = new IdList();
        this.il.parseIdList2();
        if (Interpreter.t.getToken() != TokenKind.SEMICOLON) {
            assert false : ""
                    + "Violation of: in node sytax error, ';' expected";
        }
        Interpreter.t.skipToken(); //skip ;

    }

    public void printIn() {
        System.out.print("read ");
        this.il.printIdList();
        System.out.print(";");
    }

    public void execIn() {
        this.il.readFromFile();

    }

}
