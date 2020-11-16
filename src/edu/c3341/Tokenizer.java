package edu.c3341;

/**
 * Tokenizer for Core Interpreter project. (Note: by package-wide convention,
 * unless stated otherwise, all references are non-null.)
 *
 * @author Wayne D. Heym
 *
 * @mathsubtypes <pre>
 * TOKENIZER_MODEL is (
 *   front: string of character,
 *   remainder: string of character)
 *  exemplar tzr
 *  constraint
 *   [tzr.front cannot be extended by the first character of tzr.remainder
 *    to be the prefix of any legal token kind]  and
 *   (if tzr.front = <> then tzr.remainder = <>)  and
 *   [the set of whitespace characters] intersection entries(tzr.front) = {}  and
 *   [the set of whitespace characters] intersection entries(tzr.remainder) = {' '}  and
 *   (for every w: string of character
 *     where (|w| = 2 and w is substring of tzr.remainder)
 *     (w /= <' ', ' '>))
 * </pre>
 * @mathdefinitions <pre>
 * AGGREGATE(
 *   ss: string of string of character
 *  ): string of character satisfies
 *  if ss = <> then
 *    AGGREGATE(ss) = <>
 *  else
 *    AGGREGATE(ss) = ss[0,1) * <' '> * AGGREGATE(ss[1,|ss|))
 * </pre>
 * @mathmodel type Tokenizer is modeled by TOKENIZER_MODEL
 *
 */
interface Tokenizer {

    /**
     * Java interfaces cannot contain constructors pertaining to the classes
     * that are to implement them; hence, here in this Javadoc comment
     * expectations for constructors are stated. Each class that implements
     * interface Tokenizer is expected to follow the singleton pattern; hence,
     * each such class should have exactly one private constructor. To be a bit
     * more general about it, every constructor for each such class must be
     * private.
     *
     * The two static methods listed below make use of a private static variable
     * of their class, so their bodies must be in their class, not in this
     * interface. Hence, this interface presents the expected static methods
     * here in a Javadoc comment.
     *
     * /** If no instance of Tokenizer yet exists, create one; in any case,
     * (re)set the instance by connecting it to the given Iterator<String> and
     * return a reference to the single instance of the Tokenizer.
     *
     * @param itString
     *            the Iterator<String> from which tokens will be extracted;
     *            Tokenizer expects itString's next() method never to deliver an
     *            empty String or a String containing whitespace.
     * @return the single instance of the Tokenizer
     * @updates itString
     * @ensures <pre>[the reference set returns is the reference to the possibly
     *                newly-created and only instance of the class implementing
     *                the Tokenizer interface]  and
     *          there exists content: string of character
     *            (content = AGGREGATE(~#itString.unseen)  and
     *             set.front * set.remainder = content  and
     *             (content = <>  or
     *              (content /= <> and [no prefix of content is a legal token] and
     *               [set.front is the non-empty text of an ERROR token]) or
     *              ([set.front is the text of a legal token kind] and
     *               [set.front cannot be extended by the first character
     *                of set.remainder to be the prefix of any
     *                legal token kind])))
     *       </pre> public static Tokenizer set(Iterator<String> itString)
     *
     */

    /**
     * /** Return either null or the single instance of the Tokenizer, if it
     * exists.
     *
     * @return either null or the single instance of the Tokenizer, if it exists
     *
     *         public static Tokenizer instance()
     *
     */

    /**
     * Return the kind of the front token. (Restores this.)
     *
     * @return the kind of the front token
     * @ensures getToken = [the kind of token this.front]
     */
    TokenKind getToken();

    /**
     * Skip front token.
     *
     * @updates this
     * @ensures <pre>(if [the token kind of #this.front is good and legal]
     *                  then
     *                    (if #this.remainder = <> then
     *                       [the token kind of this.front is EOF]  and
     *                       this.remainder = <>
     *                     else
     *                       this.front /= <>  and
     *                       [this.front cannot be extended by the first character
     *                        of tzr.remainder to be the prefix of any legal
     *                        token kind]  and
     *                       if this.remainder[0, 1) /= <' '> then
     *                         this.front * this.remainder = #this.remainder
     *                       else
     *                         this.front * this.remainder
     *                           = #this.remainder[1, |#this.remainder|)))  or
     *          ([the token kind of #this.front is EOF] and
     *          this = #this)</pre>
     */
    void skipToken();

    /*
     * For Part 1 of the Core Interpreter project, the following two methods
     * need not be implemented.
     */

    /**
     * Return the integer value of the front INTEGER_CONSTANT token. (Restores
     * this.)
     *
     * @return the integer value of the front INTEGER_CONSTANT token
     * @requires [the kind of this.front is INTEGER_CONSTANT]
     * @ensures intVal = [the integer value of this.front]
     */
    int intVal();

    /**
     * Return the name of the front IDENTIFIER token. (Restores this.)
     *
     * @return the name of the front IDENTIFIER token
     * @requires [the kind of this.front is IDENTIFIER]
     * @ensures intVal = this.front
     */
    String idName();
}
