package edu.c3341;

public class Comp {

    //<comp> ::= (<op> <comp op> <op>)
    //<comp op> ::= != | == | < | > | <= | >=
    private Op op1;
    private Op op2;
    private int altNo;

    public void parseComp() {
        Interpreter.t.skipToken();//skip (
        this.op1 = new Op();
        try {
            this.op1.parseOp();
        } catch (AssertionError e) {
            System.out.print(e.getMessage());
        }
        TokenKind k = Interpreter.t.getToken();
        switch (k) {
            case NOT_EQ:
                this.altNo = 1;
                break;
            case EQUALITY_TEST:
                this.altNo = 2;
                break;
            case LESS:
                this.altNo = 3;
                break;
            case GREATER:
                this.altNo = 4;
                break;
            case LESS_EQ:
                this.altNo = 5;
                break;
            case GREATER_EQ:
                this.altNo = 6;
                break;
            default:
                assert false : "Violation of: comp node error, WHAT did you put as compare op";
                break;
        }
        Interpreter.t.skipToken(); //skip comp op
        this.op2 = new Op();
        try {
            this.op2.parseOp();
        } catch (AssertionError e) {
            System.out.print(e.getMessage());
        }
        Interpreter.t.skipToken(); //skip )
    }

    public void printComp() {
        System.out.print("(");
        this.op1.printOp();
        switch (this.altNo) {
            case 1:
                System.out.print("!=");
                break;
            case 2:
                System.out.print("==");
                break;
            case 3:
                System.out.print("<");
                break;
            case 4:
                System.out.print(">");
                break;
            case 5:
                System.out.print("<=");
                break;
            case 6:
                System.out.print(">=");
                break;
            default:
                break;
        }
        this.op2.printOp();
        System.out.print(")");

    }

    public boolean execComp() {
        boolean result = false;
        switch (this.altNo) {
            case 1:
                result = this.op1.execOp() != this.op2.execOp();
                break;
            case 2:
                result = this.op1.execOp() == this.op2.execOp();
                break;
            case 3:
                result = this.op1.execOp() < this.op2.execOp();
                break;
            case 4:
                result = this.op1.execOp() > this.op2.execOp();
                break;
            case 5:
                result = this.op1.execOp() <= this.op2.execOp();
                break;
            case 6:
                result = this.op1.execOp() >= this.op2.execOp();
                break;
            default:
                break;
        }
        return result;

    }

}
