package edu.c3341;

import java.util.Optional;

public class If {
    //<if> ::= if <cond> then <stmt seq> end; (8)
    //|if <cond> then <stmt seq> else <stmt seq> end;

    private Cond c;
    private SS s1;
    Optional<SS> s2;

    public If() {
        this.c = null;
        this.s1 = null;
        this.s2 = Optional.empty();
    }

    public void parseIf() {
        Interpreter.t.skipToken(); //skip if
        this.c = new Cond();
        try {
            this.c.parseCond();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }

        if (Interpreter.t.getToken() != TokenKind.THEN) {
            assert false : "Violation of: if node syntax error, 'then' expected";
        }
        Interpreter.t.skipToken();
        this.s1 = new SS();
        try {
            this.s1.parseSS();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        if (Interpreter.t.getToken() == TokenKind.END) {
            Interpreter.t.skipToken(); //skip end
            Interpreter.t.skipToken(); //skip ;
            return;
        }
        if (Interpreter.t.getToken() == TokenKind.ELSE) {
            Interpreter.t.skipToken(); //skip else
            SS ss = new SS();
            try {
                ss.parseSS();
            } catch (AssertionError e) {
                System.out.println(e.getMessage());
            }
            this.s2 = Optional.of(ss);
            Interpreter.t.skipToken(); //skip END
            Interpreter.t.skipToken(); //skip ;
            return;
        }

    }

    public void printIf() {
        System.out.print("if ");
        this.c.printCond();
        System.out.println(" then");
        PrettyPrintHelper.increment();
        this.s1.printSS();
        PrettyPrintHelper.decrement();
        if (this.s2.isPresent()) {
            PrettyPrintHelper.printTabs();
            System.out.println("else");
            PrettyPrintHelper.increment();
            this.s2.get().printSS();
            PrettyPrintHelper.decrement();
        }
        PrettyPrintHelper.printTabs();
        System.out.print("end");
        System.out.print(";");

    }

    public void execIf() {
        if (this.c.evalCond()) {
            this.s1.execSS();
            return;
        }
        if (this.s2.isPresent()) {
            this.s2.get().execSS();
        }

    }

}
