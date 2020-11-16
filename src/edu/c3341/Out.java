package edu.c3341;

public class Out {
    private IdList il;

    public Out() {
        this.il = null;
    }

    public void parseOut() {
        Interpreter.t.skipToken(); //skip write
        this.il = new IdList();
        this.il.parseIdList2();
        Interpreter.t.skipToken(); //skip ;

    }

    public void printOut() {
        System.out.print("write ");
        this.il.printIdList();
        System.out.print(";");

    }

    public void execOut() {
        this.il.writeValue();

    }

}
