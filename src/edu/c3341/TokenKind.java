package edu.c3341;

/**
 * Token kinds needed for Part 1 of the Core Interpreter project.
 *
 * @author Wayne D. Heym
 *
 */
enum TokenKind {

    /**
     * A helper's kind for all reserved words;
     */
    LOWER_CASE_WORD(0),
    /**
     * Test driver's token number = 1; token is program
     */
    PROGRAM(1),
    /**
     * Test driver's token number = 2; token is begin
     */
    BEGIN(2),
    /**
     * Test driver's token number = 3; token is end
     */
    END(3),
    /**
     * Test driver's token number = 4; token is int
     */
    INT(4),
    /**
     * Test driver's token number = 5; token is if
     */
    IF(5),
    /**
     * Test driver's token number = 6; token is then
     */
    THEN(6),
    /**
     * Test driver's token number = 7; token is else
     */
    ELSE(7),
    /**
     * Test driver's token number = 8; token is while
     */
    WHILE(8),
    /**
     * Test driver's token number = 9; token is loop
     */
    LOOP(9),
    /**
     * Test driver's token number = 10; token is read
     */
    READ(10),
    /**
     * Test driver's token number = 11; token is write
     */
    WRITE(11),

    /**
     * Test driver's token number = 12; token is ;.
     */
    SEMICOLON(12),
    /**
     * Test driver's token number = 13; token is ,.
     */
    COMMA(13),
    /**
     * Test driver's token number = 14; token is =.
     */
    ASSIGNMENT_OPERATOR(14),
    /**
     * Test driver's token number = 15; token is !.
     */
    EXC(15), LEFT_COLON(16), RIGHT_COLON(17), AND_OP(18), LEFT_PARAN(
            20), RIGHT_PARAN(21), ADD(22), SUB(23), MUL(24), NOT_EQ(
                    25), LESS(27), GREATER(28), LESS_EQ(29), GREATER_EQ(30),
    /**
     * Test driver's token number = 19; token is ||.
     */
    OR_OPERATOR(19),

    /**
     * Test driver's token number = 26; token is ==.
     */
    EQUALITY_TEST(26),

    /**
     * Test driver's token number = 31.
     */
    INTEGER_CONSTANT(31),

    /**
     * Test driver's token number = 32.
     */
    IDENTIFIER(32),

    /**
     * Test driver's token number = 33.
     */
    EOF(33),

    /**
     * Test driver's token number = 34.
     */
    ERROR(34);

    /**
     * Test driver's token number.
     */
    private final int testDriverTokenNumber;

    /**
     * Constructor. (As class TokenKind is an enum, the visibility of the
     * explicit constructor is automatically private (i.e., private by default).
     * The default visibility for it is not package visibility.)
     *
     * @param number
     *            the test driver's token number
     */
    TokenKind(int number) {
        this.testDriverTokenNumber = number;
    }

    /**
     * Return test driver's token number.
     *
     * @return test driver's token number
     */
    public int testDriverTokenNumber() {
        return this.testDriverTokenNumber;
    }
}
