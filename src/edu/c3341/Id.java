package edu.c3341;

import java.util.HashMap;
import java.util.Map;

public class Id {

    //<id> ::= <let> | <let><id> | <let><no>
    private static Map<String, Id> eIds = new HashMap<String, Id>();
    private String name;
    private Integer val;
    private boolean declared;
    private boolean initialized;

    private Id(String n) {
        this.name = n;
        this.val = null;
        this.declared = false;
        this.initialized = false;

    }

    //why public static here
    public static Id parseDSId() {
        if (Interpreter.t.getToken() != TokenKind.IDENTIFIER) {
            assert false : ""
                    + "Violation of: id name convention error, check identifier name";
            return null;
        }
        String idName = Interpreter.t.idName();
        if (eIds.containsKey(idName)) {
            assert false : ""
                    + "Violation of: each identifier is declared only once";
        } else {
            Id id = new Id(idName);
            eIds.put(idName, id);
            eIds.get(idName).declared = true;
            return id;
        }
        return null;
    }

    public static Id parseSSId() {
        if (Interpreter.t.getToken() != TokenKind.IDENTIFIER) {
            assert false : ""
                    + "Violation of: id name convention error, check identifier name";
            return null;
        }
        String idName = Interpreter.t.idName();
        if (eIds.containsKey(idName)) {
            return eIds.get(idName);
        } else {
            assert false : ""
                    + "Compile error: occurance of undeclared variable in statement";
            return null;
        }
    }

    public void printId() {
        System.out.print(this.name);
    }

    public void setIdVal(int i) {
        this.val = i;
        this.initialized = true;
    }

    public int getIdVal() {
        if (this.val == null) {
            assert false : "Run-time error: uninitilized variable" + this.name;
        }
        return this.val;
    }
}
