package edu.c3341;

public class Op {
    //<op> ::= <no> | <id> | (<exp>)
    private Integer op;
    private Id id;
    private Exp exp;

    public Op() {
        this.op = null;
        this.id = null;
        this.exp = null;
    }

    public void parseOp() {
        if (Interpreter.t.getToken() == TokenKind.INTEGER_CONSTANT) {
            this.op = Interpreter.t.intVal();
            Interpreter.t.skipToken(); //skip integer
        } else if (Interpreter.t.getToken() == TokenKind.IDENTIFIER) {
            this.id = Id.parseSSId(); //skip id
            Interpreter.t.skipToken();
        } else if (Interpreter.t.getToken() == TokenKind.LEFT_PARAN) {
            Interpreter.t.skipToken(); //skip (
            this.exp = new Exp();
            try {
                this.exp.parseExp();
            } catch (AssertionError e) {
                System.out.println(e.getMessage());
            }
            Interpreter.t.skipToken(); //skip )
        } else {
            assert false : "" + "Violation of: not a valid op node";
        }

    }

    public void printOp() {
        if (this.op != null) {
            System.out.print(this.op);
        }
        if (this.id != null) {
            this.id.printId();
        }
        if (this.exp != null) {
            System.out.print("(");
            this.exp.printExp();
            System.out.print(")");
        }

    }

    public int execOp() {
        if (this.op != null) {
            return this.op;
        } else if (this.id != null) {
            return this.id.getIdVal();
        } else if (this.exp != null) {
            return this.exp.execExp();
        }
        return 0;

    }

}
