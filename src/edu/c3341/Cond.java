package edu.c3341;

public class Cond {
    //<cond> ::= <comp>|!<cond>
    // | [<cond> && <cond>] | [<cond> or <cond>]

    private Comp comp;
    private Cond cond1;
    private Cond cond2;
    private int altNo;

    public Cond() {
        this.comp = null;
        this.cond1 = null;
        this.cond2 = null;
        this.altNo = 0;
    }

    public void parseCond() {
        if (Interpreter.t.getToken() == TokenKind.LEFT_PARAN) {
            this.altNo = 1;
            this.comp = new Comp();
            try {
                this.comp.parseComp();
            } catch (AssertionError e) {
                System.out.print(e.getMessage());
            }
        }
        if (Interpreter.t.getToken() == TokenKind.EXC) {
            this.altNo = 2;
            this.cond1 = new Cond();
            try {
                this.cond1.parseCond();
            } catch (AssertionError e) {
                System.out.print(e.getMessage());
            }
        }
        if (Interpreter.t.getToken() == TokenKind.LEFT_COLON) {
            Interpreter.t.skipToken(); //skip [
            this.cond1 = new Cond();
            try {
                this.cond1.parseCond();
            } catch (AssertionError e) {
                System.out.print(e.getMessage());
            }
            if (Interpreter.t.getToken() == TokenKind.AND_OP) {
                this.altNo = 3;
                Interpreter.t.skipToken(); //skip &&
                this.cond2 = new Cond();
                try {
                    this.cond2.parseCond();
                } catch (AssertionError e) {
                    System.out.print(e.getMessage());
                }
                Interpreter.t.skipToken(); //skip ]
            } else if (Interpreter.t.getToken() == TokenKind.OR_OPERATOR) {
                this.altNo = 4;
                Interpreter.t.skipToken(); //skip &&
                this.cond2 = new Cond();
                try {
                    this.cond2.parseCond();
                } catch (AssertionError e) {
                    System.out.print(e.getMessage());
                }
                Interpreter.t.skipToken(); //skip ]
            } else {
                assert false : "Violation of: cond node error, check the operator";
            }
        }

    }

    public void printCond() {
        if (this.altNo == 1) {
            this.comp.printComp();
        }
        if (this.altNo == 2) {
            System.out.print("!");
            this.comp.printComp();
        }
        if (this.altNo == 3) {
            System.out.print("[");
            this.cond1.printCond();
            System.out.print("&&");
            this.cond2.printCond();
            System.out.print("]");
        }
        if (this.altNo == 4) {
            System.out.print("[");
            this.cond1.printCond();
            System.out.print("||");
            this.cond2.printCond();
            System.out.print("]");
        }
    }

    public boolean evalCond() {
        boolean result = false;
        if (this.altNo == 1) {
            result = this.comp.execComp();
        }
        if (this.altNo == 2) {
            result = !this.comp.execComp();
        }
        if (this.altNo == 3) {
            result = this.cond1.evalCond() && this.cond2.evalCond();
        }
        if (this.altNo == 4) {
            result = this.cond1.evalCond() || this.cond2.evalCond();
        }
        return result;
    }

}
