public class TagManager {

    // maintain 2 linked lists
    // a list of people currently untagged (tag ring)
    // a list of those who have already been tagged (the losers)

    // You may only have two fields in your TagManager object:
    // - a reference to the front node of the tag ring
    // - a reference to the front node of the losers (null if empty)

    public static void main(String[] args) {

    }

    /**
     * In this constructor you should initialize a new tag manager over the given
     * list of people. Your constructor should not save the List<String> itself as a
     * field, nor modify the list; but instead it should build your own tag ring of
     * linked nodes that contains these names in the same order. For example, if the
     * list contains ["John", "Sally", "Fred"], the initial tag ring should
     * represent that John is stalking Sally who is stalking Fred who is stalking
     * John (in that order). You may assume that the names are non-empty, non-null
     * strings and that there are no duplicates. You should throw an
     * IllegalArgumentException if the list is null or has a size of 0.
     * 
     * @param names
     */
    public TagManager(List<String> names) {
    }

    /**
     * In this method you should print the names of the people in the tag ring, one
     * per line, indented by two spaces, as " name is stalking name". The behavior
     * is unspecified if the game is over. For the names on the first page, the
     * initial output is:
     * 
     * Joe is stalking Sally Sally is stalking Jim Jim is stalking Carol Carol is
     * stalking Chris Chris is stalking Joe
     * 
     */
    public void printTagRing() {
    }

    /**
     * In this method you should print the names of the people in the ‘losers’, one
     * per line, with each line indented by two spaces, with output of the form
     * "name was tagged by name". It should print the names in the opposite of the
     * order in which they were tagged (most recently tagged first, then next more
     * recently tagged, and so on). It should produce no output if the ‘losers’ is
     * empty. For example, from the previous names, if Jim is tagged, then Chris,
     * then Carol, the output is:
     * 
     * Carol was tagged by Sally Chris was tagged by Carol Jim was tagged by Sally
     * 
     */
    public void printLosers() {
    }

    /**
     * In this method you should return true if the given name is in the current tag
     * ring and false otherwise. It should ignore case in comparing names; for
     * example, if passed "salLY", it should match a node with a name of "Sally".
     */
    public boolean tagRingContains(String name) {
        return false;
    }

    /**
     * In this method you should return true if the given name is in the current
     * ‘losers’ list and false otherwise. It should ignore case in comparing names;
     * for example, if passed "CaRoL", it should match a node with a name of "Carol
     * 
     * @param name
     * @return
     */
    public boolean losersContains(String name) {
        return false;
    }

    /**
     * In this method you should return true if the game is over (i.e., if the tag
     * ring has just one person) and false otherwise.
     * 
     * @return
     */
    public boolean isGameOver() {
        return false;
    }

    /**
     * In this method you should return the name of the winner of the game, or null
     * if the game is not over.
     * 
     * @return
     */
    public String winner() {
        return "hi";
    }

    /**
     * In this method you should record the tagging of the person with the given
     * name, transferring the person from the tag ring to the front of the ‘losers’
     * list. This operation should not change the relative order of the tag ring
     * (i.e., the links of who is tagging whom should stay the same other than the
     * person who is being tagged/removed). Ignore case in comparing names. A node
     * remembers who tagged the person in its tagger field. It is your
     * responsibility to set that field's value
     * 
     * @param name
     */
    public void tag(String name) {

    }

    /**
     * * For Error Handling: You should throw an IllegalStateException if the game
     * is over, or an IllegalArgumentException if the given name is not part of the
     * tag ring (if both of these conditions are true, the IllegalStateException
     * takes precedence).
     */
}
