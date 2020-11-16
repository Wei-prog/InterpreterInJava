package edu.c3341;

public class Exp {

    //<exp> ::= <trm>|<trm>+<exp>|<trm>−<exp>

    private Trm trm;
    private Exp exp;
    private int altNo;

    public Exp() {
        this.trm = null;
        this.exp = null;
        this.altNo = 0;
    }

    public void parseExp() {
        this.trm = new Trm();
        try {
            this.trm.parseTrm();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        if (Interpreter.t.getToken() == TokenKind.ADD) {
            this.altNo = 2;
            Interpreter.t.skipToken(); //skip the +
            this.exp = new Exp();
            try {
                this.exp.parseExp();
            } catch (AssertionError e) {
                System.out.println(e.getMessage());
            }
        } else if (Interpreter.t.getToken() == TokenKind.SUB) {
            this.altNo = 3;
            Interpreter.t.skipToken(); //skip the -
            this.exp = new Exp();
            try {
                this.exp.parseExp();
            } catch (AssertionError e) {
                System.out.println(e.getMessage());
            }
        } else {
            this.altNo = 1;
            return;
        }
    }

    public void printExp() {
        this.trm.printTrm();
        if (this.altNo == 2) {
            System.out.print("+");
            this.exp.printExp();
        }
        if (this.altNo == 3) {
            System.out.print("-");
            this.exp.printExp();
        }
    }

    public int execExp() {
        Integer value = null;
        switch (this.altNo) {
            case 1:
                value = this.trm.execTrm();
                break;
            case 2:
                value = this.trm.execTrm() + this.exp.execExp();
                break;
            case 3:
                value = this.trm.execTrm() - this.exp.execExp();
                if (value < 0) {
                    assert false : "Run time: Invalid negative value";
                    System.exit(0);
                }
                break;
        }

        return value;
    }

}
