import java.awt.*;
import java.util.*;
import java.util.List;

public class TagManager {

    // maintain 2 linked lists
    // a list of people currently untagged (tag ring)
    public static LinkedList<TagNode> tagRing = new LinkedList<TagNode>();
    // a list of those who have already been tagged (the losers)
    public static LinkedList<TagNode> theLosers = new LinkedList<TagNode>();

    // You may only have two fields in your TagManager object:
    // - a reference to the front node of the tag ring
    // - a reference to the front node of the losers (null if empty)

    public static void main(String[] args) {
        // addTest();

        // printTagRing();

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
    public TagManager(List<String> names) throws IllegalArgumentException {

        // * check for irregularities
        if (names.size() == 0 || names == null || names.isEmpty() == true) {
            throw new IllegalArgumentException("Size of names list in TagManager is 0 or null");
        }

        // * build tagRing
        for (int i = 0; i < names.size(); i++) {
            // System.out.println(i);
            if (i == 0) {
                TagNode firstNode = new TagNode(names.get(0));
                tagRing.add(firstNode);
            } else if (i != names.size() - 1) {
                TagNode nextNode = new TagNode(names.get(i));
                tagRing.get(i - 1).next = nextNode; // set prev one
                tagRing.add(nextNode);

            } else if (i == names.size() - 1) {
                TagNode finalNode = new TagNode(names.get(i));
                tagRing.get(i - 1).next = finalNode; // set prev one
                finalNode.next = tagRing.get(0);
                tagRing.add(finalNode);

            }
        }
        // System.out.println(tagRingContains("Ruth Martin"));

        // printTagRing();
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
    public static void printTagRing() {
        // System.out.println("Current tag ring:");

        for (int i = 0; i < tagRing.size(); i++) {
            System.out.printf("  %s is stalking %s \n", tagRing.get(i).name, tagRing.get(i).next.name);
        }

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
    public static void printLosers() {
        // System.out.println("Current losers:");

        for (int i = 0; i < theLosers.size(); i++) {
            System.out.printf("  %s was tagged by %s \n", theLosers.get(i).name, theLosers.get(i).tagger);
        }
    }

    // ! just a note on tagRingContains() and losersContains()
    // ! could easily be combined into only a contains()
    // ! function taking the linkedList as a parameter.

    /**
     * In this method you should return true if the given name is in the current tag
     * ring and false otherwise. It should ignore case in comparing names; for
     * example, if passed "salLY", it should match a node with a name of "Sally".
     */
    public static boolean tagRingContains(String name) {
        String[] formattedNameArr = name.toString().split(" ");

        String formattedName = formattedNameArr[0].substring(0, 1).toUpperCase()
                + formattedNameArr[0].toLowerCase().substring(1) + " "
                + formattedNameArr[1].substring(0, 1).toUpperCase() + formattedNameArr[1].toLowerCase().substring(1);

        Iterator iter = tagRing.iterator();
        while (iter.hasNext()) {
            TagNode current = (TagNode) iter.next();
            if (current.name.toString().contains(formattedName)) {
                return true;
            }
        }
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
    public static boolean losersContains(String name) {
        String[] formattedNameArr = name.toString().split(" ");

        String formattedName = formattedNameArr[0].substring(0, 1).toUpperCase()
                + formattedNameArr[0].toLowerCase().substring(1) + " "
                + formattedNameArr[1].substring(0, 1).toUpperCase() + formattedNameArr[1].toLowerCase().substring(1);

        Iterator iter = theLosers.iterator();
        while (iter.hasNext()) {
            TagNode current = (TagNode) iter.next();
            if (current.name.toString().contains(formattedName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * In this method you should return true if the game is over (i.e., if the tag
     * ring has just one person) and false otherwise.
     * 
     * @return if game is over or not
     */
    public boolean isGameOver() {
        if (tagRing.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * In this method you should return the name of the winner of the game, or null
     * if the game is not over.
     * 
     * @return name of winner or null
     */
    public String winner() {
        if (isGameOver()) {
            return tagRing.getFirst().name.toString();
        } else {
            return null;
        }
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
        String[] formattedNameArr = name.toString().split(" ");

        String formattedName = formattedNameArr[0].substring(0, 1).toUpperCase()
                + formattedNameArr[0].toLowerCase().substring(1) + " "
                + formattedNameArr[1].substring(0, 1).toUpperCase() + formattedNameArr[1].toLowerCase().substring(1);

        if (tagRingContains(name) && !losersContains(name)) {

            for (int i = 0; i < tagRing.size(); i++) {
                // * converting name
                if (tagRing.get(i).name.contains(formattedName)) {
                    TagNode taggedNode = tagRing.get(i);
                    // * tie up endings with next and tagger
                    if (i == 0) {
                        tagRing.get(tagRing.size() - 1).next = tagRing.get(1);
                        taggedNode.tagger = tagRing.get(tagRing.size() - 1).name;
                    } else if (i == tagRing.size() - 1) {
                        tagRing.get(tagRing.size() - 2).next = tagRing.get(0);
                        taggedNode.tagger = tagRing.get(tagRing.size() - 2).name;
                    } else {
                        tagRing.get(i - 1).next = tagRing.get(i + 1);
                        taggedNode.tagger = tagRing.get(i - 1).name;
                    }

                    // * remove
                    tagRing.remove(i);

                    // * add to losers
                    theLosers.addFirst(taggedNode);

                }
            }
        } else {
            System.out.println("error with tag() that should not occur -- likely problem with tagmain");
        }

    }

    /**
     * For adding test users to tag ring
     */
    public static void addTest() {

        // * we love those j-names
        TagNode jack = new TagNode("Jack");
        TagNode jeremy = new TagNode("Jeremy");
        TagNode jenny = new TagNode("Jenny");
        TagNode jocelyn = new TagNode("Jocelyn");
        TagNode john = new TagNode("John");

        jack.next = jeremy;
        jeremy.next = jenny;
        jenny.next = jocelyn;
        jocelyn.next = john;
        john.next = jack;

        tagRing.add(jack);
        tagRing.add(jeremy);
        tagRing.add(jocelyn);
        tagRing.add(jenny);
        tagRing.add(john);
    }

    /**
     * * For Error Handling: You should throw an IllegalStateException if the game
     * is over, or an IllegalArgumentException if the given name is not part of the
     * tag ring (if both of these conditions are true, the IllegalStateException
     * takes precedence).
     */
}
