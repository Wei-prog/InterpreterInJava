package edu.c3341;

public class IdList {

    //<id list> ::= <id> | <id>, <id list>

    private Id id;
    private IdList il;

    public IdList() {
        this.id = null;
        this.il = null;
    }

    public void parseIdList() {
        //do not need an id object here bc only construct an id object when the name not already exist.
        try {
            this.id = Id.parseDSId();
            Interpreter.t.skipToken(); //skip id
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        if (Interpreter.t.getToken() == TokenKind.SEMICOLON) {
            return;
        } else {
            if (Interpreter.t.getToken() != TokenKind.COMMA) {
                assert false : ""
                        + "Violation of: id list sytax error: use comma to seperate ids";
            }
            Interpreter.t.skipToken(); //skip comma
            this.il = new IdList();
            try {
                this.il.parseIdList();
            } catch (AssertionError e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public void parseIdList2() {
        //do not need an id object here bc only construct an id object when the name not already exist.
        try {
            this.id = Id.parseSSId();
            Interpreter.t.skipToken(); //skip id
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        if (Interpreter.t.getToken() == TokenKind.SEMICOLON) {
            return;
        } else {
            if (Interpreter.t.getToken() != TokenKind.COMMA) {
                assert false : ""
                        + "Violation of: id list sytax error: use comma to seperate ids";
            }
            Interpreter.t.skipToken(); //skip comma
            this.il = new IdList();
            try {
                this.il.parseIdList2();
            } catch (AssertionError e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public void printIdList() {
        this.id.printId();
        if (this.il != null) {
            System.out.print(',');
            this.il.printIdList();
        }

    }

    public void writeValue() {
        this.id.printId();
        System.out.println("=" + this.id.getIdVal());

        if (this.il != null) {
            System.out.print(',');
            this.il.writeValue();
        }

    }

    public void readFromFile() {
        if (Interpreter.data.hasNext()) {
            this.id.setIdVal(Integer.parseInt(Interpreter.data.next()));
        }
        if (this.il != null) {
            this.il.readFromFile();
        }

    }

}
