package edu.c3341;

import static edu.c3341.TokenKind.EOF;
import static edu.c3341.TokenKind.ERROR;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Part 1 of Project 1 for CSE 3341. Test a Tokenizer for Core, including
 * display of the integer constant and identifier name values.
 *
 * @author Wayne Heym
 *
 */
public final class TokenizerTestEnhanced {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TokenizerTestEnhanced() {
    }

    /**
     * Method to perform an enhanced test, showing variable names and integer
     * values.
     */
    private static void performEnhancedTest() {
        Tokenizer tokenizer = Tokenizer1.instance();
        while (tokenizer.getToken() != EOF && tokenizer.getToken() != ERROR) {
            System.out.print(tokenizer.getToken().testDriverTokenNumber());
            if (tokenizer.getToken() == TokenKind.INTEGER_CONSTANT) {
                System.out.println("#" + tokenizer.intVal() + "#");
            } else if (tokenizer.getToken() == TokenKind.IDENTIFIER) {
                System.out.println("$" + tokenizer.idName() + "$");
            } else {
                System.out.println();
            }
            tokenizer.skipToken();
        }
        if (tokenizer.getToken() == TokenKind.ERROR) {
            System.out.println("Error: Illegal token encountered.");
        } else {
            System.out.println(tokenizer.getToken().testDriverTokenNumber());
        }

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(Paths.get(args[0]));
        } catch (IOException e) {
            System.err.println("Error opening file: " + args[0]);
            return;
        }
        Tokenizer1.set(in);
        performEnhancedTest();
        /*
         * Close input stream
         */
        in.close();
    }
}
