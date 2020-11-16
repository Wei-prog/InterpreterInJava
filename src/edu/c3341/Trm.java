package edu.c3341;

public class Trm {

    //<trm> ::= <op> | <op> * <trm>
    private Op op;
    private Trm trm;
    private int altNo;

    public Trm() {
        this.op = null;
        this.trm = null;
        this.altNo = 0;
    }

    public void parseTrm() {
        this.op = new Op();
        try {
            this.op.parseOp();
        } catch (AssertionError e) {
            System.out.print(e.getMessage());
        }
        if (Interpreter.t.getToken() == TokenKind.MUL) {
            this.altNo = 2;
            Interpreter.t.skipToken(); //skip *
            this.trm = new Trm();
            this.trm.parseTrm();
        }
    }

    public void printTrm() {
        this.op.printOp();
        if (this.altNo == 2) {
            System.out.print("*");
            this.trm.printTrm();
        }
    }

    public int execTrm() {
        int value = this.op.execOp();
        if (this.altNo == 2) {
            value = value * this.trm.execTrm();
        }
        return value;
    }

}
