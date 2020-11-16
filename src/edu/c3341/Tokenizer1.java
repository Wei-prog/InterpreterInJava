/**
 * Shortened package name for Wayne Heym's CSE 3341 course.
 */
package edu.c3341;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Tokenizer for a "subset" of the Core language.
 *
 * @author Wei Zong.73
 * @author Wayne Heym (author of skeleton)
 *
 */
final class Tokenizer1 implements Tokenizer {

    /**
     * According to the singleton pattern, either null or a reference to the
     * single instance of Tokenizer.
     */
    private static Tokenizer1 singleInstance = null;

    /**
     * A Set containing each of the strict single character tokens of a "subset"
     * of the Core language.
     */
    private static final Set<Character> STRICT_SINGLE_CHARACTER_TOKENS;

    static {
        STRICT_SINGLE_CHARACTER_TOKENS = new HashSet<>();
        String source = ";,[]()+-*"; //all single character tokens
        for (int i = 0; i < source.length(); i++) {
            STRICT_SINGLE_CHARACTER_TOKENS.add(source.charAt(i));
        }
    }

    /**
     * A Set containing each of the characters that serve as prefixes of
     * delimiters (a.k.a. special symbols) in a "subset" of the Core language.
     */
    private static final Set<Character> DELIMITER_PREFIX_CHARACTERS;

    static {
        DELIMITER_PREFIX_CHARACTERS = new HashSet<>();
        String source = "=|&!<>";//token prefix
        for (int i = 0; i < source.length(); i++) {
            DELIMITER_PREFIX_CHARACTERS.add(source.charAt(i));
        }
    }

    /**
     * A Set containing each of the characters that serve as delimiters in the
     * Core language.
     */
    private static final Set<Character> DELIMITER_CHARACTERS;

    static {
        DELIMITER_CHARACTERS = new HashSet<>(STRICT_SINGLE_CHARACTER_TOKENS);
        DELIMITER_CHARACTERS.addAll(DELIMITER_PREFIX_CHARACTERS);
    }

    /**
     * States for the finite state automaton simulated by this tokenizer.
     */
    private enum State {

        /**
         * State START is shown in the diagram with "Ready for 1st char. of next
         * token" inside its oval and "(starting state)" written above it.
         */
        START,

        /**
         * State ERROR is shown in the diagram with "Gath. Err." inside its
         * oval.
         */
        ERROR,

        /**
         * State GATHERING_UPPERCASE is shown in the diagram with "Gath. UC"
         * inside its oval.
         */
        GATHERING_UPPERCASE,

        /**
         * State ID_GATHERING_DIGITS is shown in the diagram with "Finish ID"
         * inside its oval.
         */
        ID_GATHERING_DIGITS,

        /**
         * State DIGIT_GATHERING is shown in the diagram with "Gath. digits"
         * inside its oval.
         */
        DIGIT_GATHERING,

        /**
         * State GATHERING_LOWER_CASE is shown in the diagram with "Gath. lc"
         * inside its oval.
         */
        GATHERING_LOWER_CASE,

        /**
         * State EQ is shown in the diagram with "one =" inside its oval.
         */
        EQ,
        /**
         * State VERT_BAR is shown in the diagram with "one |" inside its oval.
         */
        VERT_BAR,
        /**
         * State AMPERSAND is shown in the diagram with "one &" inside its oval.
         */
        AMPERSAND,
        /**
         * State EXC_MRK is shown in the diagram with "one !" inside its oval.
         */
        EXC_MRK,
        /**
         * State LESS_THAN is shown in the diagram with "one <" inside its oval.
         */
        LESS_THAN,
        /**
         * State GREATER_THAN is shown in the diagram with "one >" inside its
         * oval.
         */
        GREATER_THAN;

    }

    /**
     * Head of contents to be tokenized.
     */
    private String head;

    /**
     * Position in head at which tokenizing should continue.
     */
    private int pos;

    /**
     * Tail of contents to be tokenized.
     */
    private Iterator<String> tail;

    /**
     * The current token.
     */
    private StringBuilder token;

    /**
     * The current token kind.
     */
    private TokenKind kind;

    /**
     * According to the singleton pattern, make the default constructor private.
     */
    private Tokenizer1() {
    }

    /**
     * If no instance of Tokenizer yet exists, create one; in any case, return a
     * reference to the single instance of the Tokenizer.
     *
     * @param itString
     *            the Iterator<String> from which tokens will be extracted;
     *            Tokenizer expects itString never to deliver an empty String or
     *            a String containing whitespace.
     * @return the single instance of the Tokenizer
     *
     */
    public static synchronized Tokenizer set(Iterator<String> itString) {
        if (Tokenizer1.singleInstance == null) {
            Tokenizer1.singleInstance = new Tokenizer1();
            Tokenizer1.singleInstance.token = new StringBuilder();
        } else {
            Tokenizer1.singleInstance.token.setLength(0);
        }
        Tokenizer1.singleInstance.head = "";
        Tokenizer1.singleInstance.pos = 0;
        Tokenizer1.singleInstance.tail = itString;
        Tokenizer1.singleInstance.kind = TokenKind.ERROR;
        Tokenizer1.singleInstance.findToken();
        return Tokenizer1.singleInstance;
    }

    /**
     * Return either null or the single instance of the Tokenizer, if it exists.
     *
     * @return either null or the single instance of the Tokenizer, if it exists
     */
    public static Tokenizer instance() {
        return Tokenizer1.singleInstance;

    }

    /**
     * Given a delimiter prefix character, return the DFA's new state.
     *
     * @param i
     *            a delimiter prefix character
     * @return new state
     */
    private static State newStateForDelimeterPrefixCharacter(int i) {
        State result;
        switch (i) {
            case '=': {
                result = State.EQ;
                break;
            }
            case '|': {
                result = State.VERT_BAR;
                break;
            }
            case '&': {
                result = State.AMPERSAND;
                break;
            }
            case '!': {
                result = State.EXC_MRK;
                break;
            }
            case '<': {
                result = State.LESS_THAN;
                break;
            }
            case '>': {
                result = State.GREATER_THAN;
                break;
            }
            default: {
                /* Should only occur if precondition is violated. */
                assert false : ""
                        + "Violation of: i is a delimeter prefix character";
                result = State.ERROR;
                break;
            }
        }
        return result;
    }

    /**
     * Given a strict single-character token, return its kind.
     *
     * @param i
     *            a strict single-character token
     * @return the kind of i
     */
    private static TokenKind kindOfStrictSingleCharacterToken(int i) {
        TokenKind result;
        switch (i) {
            case ';': {
                result = TokenKind.SEMICOLON;
                break;
            }
            case ',': {
                result = TokenKind.COMMA;
                break;
            }
            case '[': {
                result = TokenKind.LEFT_COLON;
                break;
            }
            case ']': {
                result = TokenKind.RIGHT_COLON;
                break;
            }
            case '(': {
                result = TokenKind.LEFT_PARAN;
                break;
            }
            case ')': {
                result = TokenKind.RIGHT_PARAN;
                break;
            }
            case '+': {
                result = TokenKind.ADD;
                break;
            }
            case '-': {
                result = TokenKind.SUB;
                break;
            }
            case '*': {
                result = TokenKind.MUL;
                break;
            }
            default: {
                /* Should only occur if precondition is violated. */
                assert false : ""
                        + "Violation of: i is a strict single-character token";
                result = TokenKind.ERROR;
                break;
            }
        }
        return result;
    }

    /**
     * Collect the given character into $this.token and move past it in
     * $this.head by incrementing $this.pos.
     *
     * @param c
     *            the character to be collected
     */
    private void collectCharacter(char c) {
        this.token.append(c);
        this.pos++;
    }

    // TODO - Note that the following comment saying this "method is too long"
    // will become true after you complete the provided skeleton for this method.
    // Of course, delete this section of comments before declaring this
    // programming assignment to be complete.

    /**
     * Update this to find the next Core language token. Do so by simulating the
     * behavior of a particular deterministic finite state automaton (DFA or
     * FSA) beginning in its Start state. This method is too long. Checkstyle
     * reports that its length exceeds 150 lines. Because, as a simple way of
     * describing the implementation of a finite state machine, I recommend the
     * use of a switch statement to our students, I find this approach
     * acceptable, despite the length of this method. --Wayne Heym
     */
    private void findToken() {
        if (this.kind != TokenKind.EOF) {
            //this.head is the first string not yet read got by this.tail.next(a string iterator)
            if (this.head.length() <= this.pos && this.tail.hasNext()) {
                this.pos = 0;
                this.head = this.tail.next();
            }
            if (this.pos < this.head.length()) {
                boolean seeking = true;
                State state = State.START;
                this.token.setLength(0);
                while (seeking) {
                    switch (state) {
                        case START: {
                            char current = this.head.charAt(this.pos);
                            this.collectCharacter(current);
                            if (STRICT_SINGLE_CHARACTER_TOKENS
                                    .contains(current)) {
                                this.kind = kindOfStrictSingleCharacterToken(
                                        current);
                                seeking = false;
                                //exit loop and print out the current one char token
                            } else if (DELIMITER_PREFIX_CHARACTERS
                                    .contains(current)) {
                                state = newStateForDelimeterPrefixCharacter(
                                        current);
                                //checking for next char to determine which token it is,
                                //there can be no white space between symbols and still correctly tokenize
                            } else if ('a' <= current && current <= 'z') {
                                state = State.GATHERING_LOWER_CASE;
                            } else if ('A' <= current && current <= 'Z') {
                                state = State.GATHERING_UPPERCASE;
                            } else if ('0' <= current && current <= '9') {
                                state = State.DIGIT_GATHERING;
                            } else {
                                state = State.ERROR;
                            }
                            break;
                        }
                        case GATHERING_LOWER_CASE: {
                            if (this.head.length() <= this.pos) {
                                this.kind = TokenKind.LOWER_CASE_WORD;
                                seeking = false;
                            } else {
                                char current = this.head.charAt(this.pos);
                                if ('a' <= current && current <= 'z') {
                                    this.collectCharacter(current);
                                } else if ('A' <= current && current <= 'Z'
                                        || '0' <= current && current <= '9') {
                                    this.collectCharacter(current);
                                    state = State.ERROR;
                                } else {
                                    this.kind = TokenKind.LOWER_CASE_WORD;
                                    seeking = false;
                                }
                            }
                            break;
                        }
                        case DIGIT_GATHERING: {
                            if (this.head.length() <= this.pos) {
                                this.kind = TokenKind.INTEGER_CONSTANT;
                                seeking = false;
                            } else {
                                char current = this.head.charAt(this.pos);
                                if ('0' <= current && current <= '9') {
                                    this.collectCharacter(current);
                                } else if ('A' <= current && current <= 'Z'
                                        || 'a' <= current && current <= 'z') {
                                    this.collectCharacter(current);
                                    state = State.ERROR;
                                } else {
                                    //two tokens have no whitespace to separate,
                                    //so head.length will still be larger than pos(the cursor)
                                    //but need to exit loop for taking in current token
                                    this.kind = TokenKind.INTEGER_CONSTANT;
                                    seeking = false;
                                }
                            }

                            break;
                        }
                        case GATHERING_UPPERCASE: {
                            if (this.head.length() <= this.pos) {
                                //if there is whitespace separating, token ends
                                this.kind = TokenKind.IDENTIFIER;
                                seeking = false;
                            } else {
                                //1. collect uppercase until number, same token
                                //2. collect uppercase until lowercase, error
                                //3. collect uppercase until else, next token
                                char current = this.head.charAt(this.pos);
                                if ('A' <= current && current <= 'Z') {
                                    this.collectCharacter(current);
                                } else if ('a' <= current && current <= 'z') {
                                    this.collectCharacter(current);
                                    state = State.ERROR;
                                } else if ('0' <= current && current <= '9') {
                                    state = State.ID_GATHERING_DIGITS;
                                    //no need to change seeking to false because it's the same token
                                } else {
                                    this.kind = TokenKind.IDENTIFIER;
                                    seeking = false;
                                }
                            }
                            break;

                        }
                        case ID_GATHERING_DIGITS: {
                            if (this.head.length() <= this.pos) {
                                this.kind = TokenKind.IDENTIFIER;
                                seeking = false;
                            } else {
                                char current = this.head.charAt(this.pos);
                                if ('0' <= current && current <= '9') {
                                    this.collectCharacter(current);
                                } else if ('a' <= current && current <= 'z'
                                        || 'A' <= current && current <= 'Z') {
                                    this.collectCharacter(current);
                                    state = State.ERROR;
                                } else {
                                    this.kind = TokenKind.IDENTIFIER;
                                    seeking = false;
                                }
                            }

                            break;
                        }
                        case EQ: {
                            //only take in one char so no need for checking whitespace to end token for ==
                            //, directly end token, if the next char is empty space after the first = , need to handle
                            if (this.head.length() <= this.pos) {
                                this.kind = TokenKind.ASSIGNMENT_OPERATOR;
                            } else {
                                char current = this.head.charAt(this.pos);
                                if (current == '=') {
                                    this.collectCharacter(current);
                                    this.kind = TokenKind.EQUALITY_TEST;
                                } else {
                                    this.kind = TokenKind.ASSIGNMENT_OPERATOR;
                                }
                            }
                            seeking = false;
                            break;
                        }
                        case VERT_BAR: {
                            if (this.head.length() <= this.pos) {
                                this.kind = TokenKind.ERROR;
                            } else {
                                char current = this.head.charAt(this.pos);
                                if (current == '|') {
                                    this.collectCharacter(current);
                                    this.kind = TokenKind.OR_OPERATOR;
                                } else {
                                    this.collectCharacter(current);
                                    this.kind = TokenKind.ERROR;
                                }
                            }
                            seeking = false;
                            break;
                        }
                        case AMPERSAND: {
                            if (this.head.length() <= this.pos) {
                                this.kind = TokenKind.ERROR;
                            } else {
                                char current = this.head.charAt(this.pos);
                                if (current == '&') {
                                    this.collectCharacter(current);
                                    this.kind = TokenKind.AND_OP;
                                } else {
                                    this.collectCharacter(current);
                                    this.kind = TokenKind.ERROR;
                                }
                            }
                            seeking = false;
                            break;
                        }

                        case EXC_MRK: {
                            if (this.head.length() <= this.pos) {
                                this.kind = TokenKind.EXC;
                            } else {
                                char current = this.head.charAt(this.pos);
                                if (current == '=') {
                                    this.collectCharacter(current);
                                    this.kind = TokenKind.NOT_EQ;
                                } else {
                                    this.kind = TokenKind.EXC;
                                }
                            }
                            seeking = false;
                            break;
                        }
                        case LESS_THAN: {
                            if (this.head.length() <= this.pos) {
                                this.kind = TokenKind.LESS;
                            } else {
                                char current = this.head.charAt(this.pos);
                                if (current == '=') {
                                    this.collectCharacter(current);
                                    this.kind = TokenKind.LESS_EQ;
                                } else {
                                    this.kind = TokenKind.LESS;
                                }
                            }
                            seeking = false;
                            break;
                        }
                        case GREATER_THAN: {
                            if (this.head.length() <= this.pos) {
                                this.kind = TokenKind.GREATER;
                            } else {
                                char current = this.head.charAt(this.pos);
                                if (current == '=') {
                                    this.collectCharacter(current);
                                    this.kind = TokenKind.GREATER_EQ;
                                } else {
                                    this.kind = TokenKind.GREATER;
                                }
                            }
                            seeking = false;
                            break;
                        }
                        case ERROR: {
                            if (this.head.length() <= this.pos) {
                                this.kind = TokenKind.ERROR;
                                seeking = false;
                            } else {
                                char current = this.head.charAt(this.pos);
                                if (DELIMITER_CHARACTERS.contains(current)) {
                                    this.kind = TokenKind.ERROR;
                                    seeking = false;
                                } else {
                                    this.collectCharacter(current);
                                }
                            }
                            break;
                        }
                        default: {
                            /*
                             * It's a programming error if control reaches here:
                             */
                            assert false : "Programming error: "
                                    + "unhandled state in simulation of FSA";
                            state = State.ERROR;
                            break;
                        }
                    }
                }
                if (this.kind == TokenKind.LOWER_CASE_WORD) {
                    switch (this.token.toString()) {
                        case "program":
                            this.kind = TokenKind.PROGRAM;
                            break;
                        case "begin":
                            this.kind = TokenKind.BEGIN;
                            break;
                        case "end":
                            this.kind = TokenKind.END;
                            break;
                        case "int":
                            this.kind = TokenKind.INT;
                            break;
                        case "if":
                            this.kind = TokenKind.IF;
                            break;
                        case "then":
                            this.kind = TokenKind.THEN;
                            break;
                        case "else":
                            this.kind = TokenKind.ELSE;
                            break;
                        case "while":
                            this.kind = TokenKind.WHILE;
                            break;
                        case "loop":
                            this.kind = TokenKind.LOOP;
                            break;
                        case "read":
                            this.kind = TokenKind.READ;
                            break;
                        case "write":
                            this.kind = TokenKind.WRITE;
                            break;
                        default: {
                            /*
                             * Error of unrecognized reserved word
                             */
                            assert false : "Programming error: unknown key word, not a reserved word";
                            this.kind = TokenKind.ERROR;
                            break;
                        }
                    }
                }
            } else {
                this.kind = TokenKind.EOF;
                this.token.setLength(0);
            }

        }
    }

    /**
     * Return the kind of the current token.
     *
     * @return the kind of the current token
     */
    @Override
    public TokenKind getToken() {
        return this.kind;
    }

    /**
     * Skip current token.
     */
    @Override
    public void skipToken() {
        this.findToken();
    }

    /**
     * Return the integer value of the current INTEGER_CONSTANT token.
     *
     * @return the integer value of the current INTEGER_CONSTANT token
     */
    @Override
    public int intVal() {
        return Integer.parseInt(this.token.toString());
    }

    /**
     * Return the name of the current IDENTIFIER token.
     *
     * @return the name of the current IDENTIFIER token
     */
    @Override
    public String idName() {
        return this.token.toString();
    }
}
