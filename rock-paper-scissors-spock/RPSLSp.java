import java.util.*;
import java.io.*;

/**
 * Created on September 3rd 2021. Class: RPSLSp.java Author: Darrow Hartman
 */
public class RPSLSp {
    public static Random random = new Random(5); // * required random initializer
    public static Scanner sc;
    public static String battleFile = "./test-files/battles5.txt"; // or "./test-files/battles3.txt"
    public static int choicesLength;
    public static String[] gameChoices = {}; // * the possible choices for the weapon to the user and computer
    public static int[][] gameOutcomes = {}; // * the results of the games overall
    public static String[][] gameVerbs = {}; // * the verbs that are used for each battle
    public static ArrayList<Integer> gameTally = new ArrayList<Integer>(); // * the results of the games tallied (+1 for
                                                                           // the user, -1 for the computer, and 0 for
                                                                           // tie)
    /**
     * Example gameRules setup gameChoices = [rock,paper,scissors] gameRules = [
     * [0,0,1] rock beats scissors [1,0,0] paper beats rock [0,1,0] scissors beats
     * paper ]
     */
    public static int[][] gameRules = {}; // * the rules of the game that need to be defined using the battle files

    // * current variables
    public static String currentWeaponUser = "";
    public static String currentWeaponComp = "";
    public static String currentVictor = "";

    // * tally variables
    public static int userWinsTally = 0;
    public static int compWinsTally = 0;
    public static int tieTally = 0;
    public static int[] userWeaponsTally = {};
    public static int[] compWeaponsTally = {};

    /**
     * Starting function to initialize game, print introduction, and read battle
     * file
     */
    public static void main(String[] args) throws IOException {
        // * starting prompt
        System.out.println("Welcome to Rock vs Paper vs Scissors vs Spock! By Darrow Hartman for CS-2&3 Review!");
        // * get file input for how to run game
        getFile(battleFile);
        // * start game
        runManualGame();
    }

    /**
     * Runs a full round of the game and prompts the user to continue Note: This is
     * a method to "play an individual battle" and should fulfill the requirement on
     * necessary requirements
     */
    public static void runManualGame() {
        int userSelection = askQuestion() - 1;// * -1 to convert to array entry
        // Random rand = new Random();
        int computerSelection = random.nextInt(gameChoices.length);
        // * adds to user and comp weapon tallies
        userWeaponsTally[userSelection]++;
        compWeaponsTally[computerSelection]++;
        // * selects current weapons
        currentWeaponComp = gameChoices[computerSelection];
        currentWeaponUser = gameChoices[userSelection];

        if (gameRules[userSelection][computerSelection] == 1) {
            // * user has won
            currentVictor = "User";
            gameTally.add(1);
            System.out.println("User (" + currentWeaponUser + ") " + gameVerbs[userSelection][computerSelection]
                    + " Computer (" + currentWeaponComp + ")");
        } else if (gameRules[computerSelection][userSelection] == 1) {
            // * computer has won
            currentVictor = "Computer";
            gameTally.add(-1);

            System.out.println("Computer (" + currentWeaponComp + ") " + gameVerbs[computerSelection][userSelection]
                    + " User (" + currentWeaponUser + ")");

        } else {
            currentVictor = "Tie";
            gameTally.add(0);
            // User (paper) ties Computer (paper)
            System.out.println("User (" + currentWeaponUser + ") ties Computer (" + currentWeaponComp + ")");
        }
        playAgain();
    }

    /**
     * Asks the user to enter 1-5 for their weapon choice
     * 
     * @return int of what they chose
     */
    public static int askQuestion() {

        String optionsPrompt = "";
        for (int i = 0; i < gameChoices.length; i++) {
            optionsPrompt += "\n" + (i + 1) + ". " + gameChoices[i];
        }
        optionsPrompt += "\nChoose your weapon " + "(1-" + gameChoices.length + "): ";

        System.out.print(optionsPrompt);
        sc = new Scanner(System.in);
        String numReturn = sc.nextLine(); // .nextLine();
        try {
            int userVal = Integer.parseInt(numReturn);
            if (0 < userVal && userVal > gameChoices.length) {
                // ! The user has entered a val outside the limits
                throw new NumberFormatException(
                        "Error: your must enter a number 1-5, you entered a number greater than 5 or less than 1");
            }
            return userVal;
        } catch (NumberFormatException error) {
            throw new NumberFormatException("Error: your must enter a number 1-5, you did not enter a number");

        } catch (Exception error) {
            System.out.println(error);
            throw new RuntimeException("Unkown Error");
        }
    }

    /**
     * Gives the user the option to start another battle
     */
    public static void playAgain() {
        System.out.print("Battle again (yes/no)? ");
        String willPlayAgain = sc.nextLine(); // will be y or n
        if (willPlayAgain.equals("y") || willPlayAgain.equals("yes") || willPlayAgain.equals("Y")) {
            runManualGame();
        } else if (willPlayAgain.equals("n") || willPlayAgain.equals("no") || willPlayAgain.equals("N")) {
            // * print out stats
            printTallyOutput();
        } else {
            throw new RuntimeException("Error: your response was not either y/n or yes/no");
        }
    }

    /**
     * Use `getFiles(file)` to get the data from a specific file filename - name of
     * file ex: "file.txt" will prob use for the battles files
     */
    public static void getFile(String filename) {
        // * referenced: https://www.w3schools.com/java/java_files_read.asp
        File myObj = new File(filename);
        try {
            sc = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int lineCount = 0;
        while (sc.hasNextLine()) {
            lineCount++;
            String data = sc.nextLine();

            if (lineCount == 1) {
                // * get val for choicesLength
                try {
                    choicesLength = Integer.parseInt(data); // * first line is always a num
                    gameChoices = new String[choicesLength];
                    userWeaponsTally = new int[choicesLength];
                    compWeaponsTally = new int[choicesLength];
                } catch (Exception e) {
                    throw new NumberFormatException("Error: Should be a number -- problem with file");
                }
            }
            if (lineCount == 2) {
                // * get values for gameChoices
                String[] keywords = data.split(" ");
                for (int i = 0; i < keywords.length; i++) {
                    // * this for loop is sort of redundant because I could just set gameChoices to
                    // data.split() but this project encourages use of for loops
                    gameChoices[i] = keywords[i];
                }
                int choicesSq = choicesLength ^ 2; // * the number of possible outcomes is square the number of choices
                                                   // ex: rock, paper, scissors has 9
                gameRules = new int[choicesLength][choicesLength];
                gameVerbs = new String[choicesLength][choicesLength];
            }
            if (lineCount != 1 && lineCount != 2) {

                int lineShouldBe = choicesLength * choicesLength + 2; // * length the file should be
                if (lineCount > lineShouldBe) {
                    // ! error because of filesize
                    throw new Error("Error: There is something wrong with the file -- too long/short");
                }

                // * rest of lines define rules to game
                String[] ruleBase = data.split(":"); // * splits string into init val and battle vals
                String ruleInit = ruleBase[0];
                String[] ruleBattles = ruleBase[1].split(" ");

                // * converts names to ints for array positioning
                int ruleInitVal = convertNametoNum(ruleInit);

                // * set 1's and 0's for rule val
                if (ruleBattles.length == 3) {
                    int ruleCorrespValA = convertNametoNum(ruleBattles[0]); // * A is first one -- competitor
                    int ruleCorrespValB = convertNametoNum(ruleBattles[1]); // * B is second one -- winner

                    gameVerbs[ruleInitVal][ruleCorrespValA] = ruleBattles[2]; // * verb for every existing battle
                    // * there is a winner + verb
                    if (ruleCorrespValB == ruleInitVal) {
                        // * init won
                        if (gameRules[ruleInitVal][ruleCorrespValA] == 1) {
                            // ! error it has already gone through this one
                            throw new Error("Error: There is a duplicate set of wins/losses in the file.");
                        }

                        gameRules[ruleInitVal][ruleCorrespValA] = 1; // * init wins
                    } else {
                        // ! possible issue: it will not detect extra copies of ties in file
                        gameRules[ruleInitVal][ruleCorrespValA] = 0; // * init loses
                    }
                } else if (ruleBattles.length == 2) {
                    // * this is a tie
                    int ruleCorrespVal = convertNametoNum(ruleBattles[0]); // * ruleBattles[1] is tie
                    gameVerbs[ruleInitVal][ruleCorrespVal] = "n/a"; // * verbs don't exist for ties
                    gameRules[ruleInitVal][ruleCorrespVal] = 0;

                } else {
                    System.err.println("some kind of error that doesn't make sense in getFile()");
                }

            }

        }
        sc.close();
    }

    /**
     * Converts the name of a weapon into a number corresponding to it
     */
    public static int convertNametoNum(String name) {
        name = name.replaceAll("\\s", "");
        int retval = -1;
        for (int i = 0; i < gameChoices.length; i++) {
            String gc = gameChoices[i].replaceAll("\\s", "");
            // * was having issues with if statement name equal gc, thought there was
            // something wrong with my compiler until I found this:
            // https://stackoverflow.com/questions/14696054/while-loop-in-java-string-comparison-does-not-work
            if (new String(name).equals(gc)) {
                retval = i;
            }

        }

        return retval;
    }

    /**
     * Uses special format for tally given in assignement pdf Note: This "prints the
     * statistics" and should fulfill the requirement on necessary requirements
     */
    public static void printTallyOutput() {
        // ! I was trying to get the template code for the prints to work but I kept
        // having problems so I did it myself
        // System.out.printf(" %8s %8s %8s %8s %8s\n","rock"/*, "rock”, "paper", …*/);
        // System.out.println(" -------- -------- -------- -------- --------");
        // System.out.printf("%8s %8d %8d %8d %8d %8d\n","Computer"/*, “Computer”,
        // ...*/);
        // System.out.printf("%8s %8d %8d %8d %8d %8d\n", "User"/*, “User”, ...*/);
        String gamesAsString = "         ";
        int totalChrs = 8; // * total num of chars required
        for (int i = 0; i < gameChoices.length; i++) {
            int remainingChrs = totalChrs - gameChoices[i].length();
            for (int j = 0; j < remainingChrs; j++) {
                gamesAsString += " ";
            }
            gamesAsString += gameChoices[i] + " ";
        }

        // System.out.println("\n");
        System.out.println("");
        System.out.println(gamesAsString);
        String compString = "Computer ";
        String userString = "    User ";
        String divider = "         ";

        for (int i = 0; i < choicesLength; i++) {
            divider += "-------- ";
            // !if over 9 then there will be issues with formatting
            compString += "       " + compWeaponsTally[i] + " ";
            userString += "       " + userWeaponsTally[i] + " ";

        }

        System.out.println(divider);

        System.out.println(compString);
        System.out.println(userString);

        countTally();
        System.out.println("");

        System.out.println("The computer won " + compWinsTally + timesVSTime(compWinsTally) + ",");
        System.out.println("the user won " + userWinsTally + timesVSTime(userWinsTally) + ",");
        System.out.println("and they tied " + tieTally + timesVSTime(tieTally) + ".");

    }

    /**
     * Determine if it should be time or times based on tally
     * 
     * @return time or times as strings
     */
    public static String timesVSTime(int tally) {
        String timeOrTimes = "";
        if (tally > 1) {
            timeOrTimes = " times";
        } else {
            timeOrTimes = " time";
        }

        return timeOrTimes;
    }

    /**
     * prints out 2-d arrays
     */
    public static void print2d(int[][] arr) {
        System.out.println("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]) + ",");
        }
        System.out.println("]");

    }

    /**
     * count up tally from gameTally into number of wins for each group (user, comp,
     * and tie)
     */
    public static void countTally() {
        for (int i = 0; i < gameTally.size(); i++) {
            if (gameTally.get(i) == 1) {
                // * user won
                userWinsTally++;
            } else if (gameTally.get(i) == -1) {
                // * comp won
                compWinsTally++;
            } else if (gameTally.get(i) == 0) {
                // * it was a tie
                tieTally++;
            }
        }
    }

    // /**
    // * Runs code 12 times so I don't have to
    // */
    // public static void automateTest(){
    // for (int i = 0; i < 12; i++) {
    // // * starting prompt
    // System.out.println("Welcome to Rock vs Paper vs Scissors vs Spock! By Darrow
    // Hartman for CS-2&3 Review!");
    // // * get file input for how to run game
    // getFile(battleFile);
    // // * start game
    // runManualGame();
    // }
    // }

}