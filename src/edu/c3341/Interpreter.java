package edu.c3341;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;

public final class Interpreter {

    private Interpreter() {

    }

    public static Iterator<String> data;
    public static Tokenizer t;

    /**
     * Checking illegal tokens
     *
     */
    /*
     * private static Tokenizer getTokens(Iterator<String> in) {
     * Tokenizer1.set(in); t = Tokenizer1.instance(); while (t.getToken() !=
     * TokenKind.EOF && t.getToken() != ERROR) { try { t.skipToken(); } catch
     * (AssertionError e) { System.out.println(e.getMessage()); }
     *
     * } if (t.getToken() == ERROR) {
     * System.out.println("Error: Illegal token encountered."); } return t; }
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        Scanner in;
        Scanner input;
        Boolean inPrint = true;
        //question on how to handle correct core program, but insufficient input stream
        //can you put it into two try catch phase, what are the differences
        try {
            in = new Scanner(Paths.get(args[0]));
            input = new Scanner(Paths.get(args[1]));
        } catch (IOException e) {
            System.err.println("Error opening file: " + args[0]);
            System.err.println("Error opening file: " + args[1]);
            return;
        }
        if (args.length > 2 && args[2].equals("doNotPrint")) {
            inPrint = false;
        }
        //get input stream
        data = input;
        //Tokenizing
        Tokenizer1.set(in);
        t = Tokenizer1.instance();
        //parsing
        Prog p = new Prog();
        try {
            p.parseProg();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        if (inPrint) {
            p.printProg();
        }
        p.ExecProg();
        /*
         * Close input stream
         */
        in.close();
        input.close();
    }
}
