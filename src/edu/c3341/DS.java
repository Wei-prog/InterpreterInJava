
package edu.c3341;

import java.util.Optional;

public class DS {

    //<decl seq> ::= <decl> | <decl> <decl seq>
    private Dcl dcl;
    private Optional<DS> ds;

    public DS() {
        this.dcl = null;
        this.ds = Optional.empty();
    }

    public void parseDS() {
        //no syntax requirement before the first decl
        this.dcl = new Dcl();
        try {
            this.dcl.parseDcl();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
        if (Interpreter.t.getToken() == TokenKind.BEGIN) {
            return;
        } else {
            DS ds1 = new DS();
            try {
                ds1.parseDS();
            } catch (AssertionError e) {
                System.out.println(e.getMessage());
            }
            this.ds = Optional.of(ds1);
        }
    }

    public void printDS() {
        this.dcl.printDcl();
        if (this.ds.isPresent()) {
            this.ds.get().printDS();
        }
        //empty line print before SS
    }

    //no run time behavior for decl/decl seq

}
