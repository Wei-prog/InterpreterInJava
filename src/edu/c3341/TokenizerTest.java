package edu.c3341;

import static edu.c3341.TokenKind.ERROR;

/**
 * Part 1 of Project 1 for CSE 3341. Test a Tokenizer for Core.
 *
 * @author Wayne D. Heym
 *
 */
public final class TokenizerTest {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TokenizerTest() {
    }

    /**
     * Method to perform the test.
     */
    private static void performTest() {
        Tokenizer t = Tokenizer1.instance();
        while (t.getToken() != TokenKind.EOF && t.getToken() != ERROR) {
            System.out.println(t.getToken().testDriverTokenNumber());
            t.skipToken();
        }
        if (t.getToken() == ERROR) {
            System.out.println("Error: Illegal token encountered.");
        }
    }

}
