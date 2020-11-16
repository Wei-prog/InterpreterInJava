package edu.c3341;

public class Loop {
    //<loop> ::= while <cond> loop <stmt seq> end;
    private Cond c;
    private SS s;

    public Loop() {
        this.c = null;
        this.s = null;
    }

    public void parseLoop() {
        Interpreter.t.skipToken(); //skip while
        this.c = new Cond();
        try {
            this.c.parseCond();
        } catch (AssertionError e) {
            System.out.print(e.getMessage());
        }
        Interpreter.t.skipToken(); //skip loop
        this.s = new SS();
        try {
            this.s.parseSS();
        } catch (AssertionError e) {
            System.out.print(e.getMessage());
        }
        Interpreter.t.skipToken(); //skip end
        Interpreter.t.skipToken(); //skip ;
    }

    public void printLoop() {
        System.out.print("while ");
        this.c.printCond();
        System.out.println(" loop");
        PrettyPrintHelper.increment();
        this.s.printSS();
        PrettyPrintHelper.decrement();
        PrettyPrintHelper.printTabs();
        System.out.print("end");
        System.out.print(";");
    }

    public void execLoop() {
        while (this.c.evalCond()) {
            this.s.execSS();
        }
    }

}
