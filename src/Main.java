
import java.util.InputMismatchException;
import java.util.Scanner;

/*
Author: Ahmed Essam Taj
Date: 2, November
Project (1)

 */

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String[][] impossibleField = new String[3][3];

    public static void main(String[] args) {
        int gameMode = -1;
        do {

            System.out.println("Choose a game mode:");
            System.out.println("To play a quick game of Tic Tac Toe enter (1) : ");
            System.out.println("To play a best-of-3 series enter (2) : ");
            System.out.println("To play a impossible AI ! enter (3) : ");
            try {
                gameMode = scanner.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("Please choose a valid game mode. ");
                scanner.next();
            }

        } while (gameMode != 1 && gameMode != 2 && gameMode != 3);


        if (gameMode == 1) {

            playQuickGame();

        } else if (gameMode == 2) {

            playBestOf3();

        } else {
            playImpossibleMode();
        }


    }

    //      this is the basic mode (Minimum requirements for project 1)
    public static void playQuickGame() {
        String[][] field = new String[3][3];
        int turn = 1;
        displayField(field);
        while (!(checkUserWin(field) || checkCompWin(field))) {


            field = userPlay(field);
            if (checkUserWin(field)) {
                System.out.println();
                System.out.println("You win!");
                break;
            }

            if (turn == 5) {
                System.out.println();
                System.out.println("Game is a tie! ");
                break;

            }
            field = compPlay(field);
            if (checkCompWin(field)) {
                System.out.println();
                System.out.println("You lose!");
                break;
            }

            turn++;


        }
    }

    // this is the extra cridit requirements for project 1
    public static void playBestOf3() {
        int userWins = 0;
        int compWins = 0;

        while (userWins < 3 && compWins < 3) {
            System.out.println();
            String[][] field = new String[3][3];
            int turn = 1;
            displayField(field);

            while (!(checkUserWin(field) || checkCompWin(field))) {
                field = userPlay(field);
                if (checkUserWin(field)) {
                    System.out.println("You win this round!");
                    userWins++;
                    break;
                }

                if (turn == 5) {
                    System.out.println("This game is a tie!");
                    break;
                }

                field = compPlay(field);
                if (checkCompWin(field)) {
                    System.out.println("Computer wins this round!");
                    compWins++;
                    break;
                }

                turn++;
            }
            System.out.println("====================================");
            System.out.println("Current Score - You: " + userWins + " | Computer: " + compWins);
        }

        if (userWins == 3) {
            System.out.println("Congratulations! You are the ultimate winner!");
        } else if (compWins == 3) {
            System.out.println("The computer is the ultimate winner. Do better next time!");
        }
    }

    // method to display the filed (print)
    public static void displayField(String[][] field) {
        if (field == null) {
            System.out.println("Field is null");
            return;
        }

        int newLine = 1;
        System.out.println();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null) {
                    if (j == 2) {
                        System.out.print("  " + field[i][j]);
                    } else {
                        System.out.print("  " + field[i][j] + "  |");
                    }
                } else {
                    if (j == 2) {
                        System.out.print("  " + (i + j + newLine));
                    } else {
                        System.out.print("  " + (i + j + newLine) + "  |");
                    }

                }


            }
            System.out.println();
            if (i != 2) {
                System.out.println("-----------------");
            }

            newLine++;
            newLine++;

        }
    }

    // method to generate random number between 1-9 and convert it into 2d array index
    public static int generateRandomNum(String[][] field) {

        int randomNumY = (int) Math.floor(Math.random() * (2 - 0 + 1) + 0);
        return randomNumY;

    }

    public static String[][] compPlay(String[][] field) {
        while (true) {

            int compChoiceY = generateRandomNum(field);
            int compChoiceX = generateRandomNum(field);

            if (field[compChoiceY][compChoiceX] == null) {
                System.out.println();
                System.out.println("=========================== Computer Turn ===========================");
                // equation to reverse computer random number (1-9) to y and x for 2d array ..
                int intCompChoice = (3 * compChoiceY) + compChoiceX + 1;
                System.out.println("Computer choose position number " + intCompChoice + " : ");
                field[compChoiceY][compChoiceX] = "o";
                System.out.println("=====================================================================");
                displayField(field);
                break;

            } else {
                compChoiceY = generateRandomNum(field);
                compChoiceX = generateRandomNum(field);
                ;
            }

        }
        return field;
    }

    // method to handle  user turn with handleing input mismatch
    public static String[][] userPlay(String[][] field) {

        while (true) {
            System.out.println();
            System.out.println("============================ Player Turn ============================");
            System.out.println("Please enter the number where you would like to play : ");
            int location;
            try {
                location = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                scanner.next();
                displayField(field);
                continue;
            }

            System.out.println("=====================================================================");
            System.out.println();
            if (location < 1 || location > 9) {
                System.out.println("Please enter a number between 1 and 9");
                displayField(field);
                continue;
            }
            // equation to turn user choice from (1-9) to 2d array
            int y = (int) Math.floor((location - 1) / 3);
            int x = (location - 1) % 3;

            if (field[y][x] != null) {
                System.out.println("Place already occupied, Please choose another one.. ");
                displayField(field);
                continue;
            } else {
                field[y][x] = "x";
                displayField(field);
                break;
            }

        }

        return field;
    }

    // method to check if the user win or not
    public static Boolean checkUserWin(String[][] field) {
        // Checking for horizontal user win (x)
        for (int i = 0; i < field.length; i++) {
            int countHorizontal = 0;
            int countVertical = 0;
            for (int j = 0; j < field[i].length; j++) {
                // i solved it by if statement (could use exception for null but no need)
                if (field[i][j] != null && field[i][j].equals("x")) {
                    countHorizontal++;
                }
                if (field[j][i] != null && field[j][i].equals("x")) {
                    countVertical++;
                }

            }
            if (countHorizontal == 3 || countVertical == 3) {// this means there are three x on the same row
                return true;
            }

        }

        return checkDiagonalWinUser(field);

        //return false;
    }

    public static Boolean checkDiagonalWinUser(String[][] field) {

        int counter = 0;
        for (int i = 0; i < field.length; i++) {
            if (field[i][i] != null && field[i][i].equals("x")) {
                counter++;

            }

        }
        if (counter == 3) {
            return true;
        }

        if (field[0][2] != null && field[1][1] != null && field[2][0] != null) {
            if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0])) {
                return true;
            }
        }
        return false;
    }


    // Method to check if the computer win
    public static Boolean checkCompWin(String[][] field) {
        // Checking for horizontal computer win (o)
        for (int i = 0; i < field.length; i++) {
            int countHorizontal = 0;
            int countVertical = 0;
            for (int j = 0; j < field[i].length; j++) {
                // i solved it by if statement (could use exception for null but no need)
                if (field[i][j] != null && field[i][j].equals("o")) {
                    countHorizontal++;
                }
                if (field[j][i] != null && field[j][i].equals("o")) {
                    countVertical++;
                }

            }
            if (countHorizontal == 3 || countVertical == 3) {// this means there are three x on the same row
                return true;
            }

        }

        return checkDiagonalWinComp(field);
    }

    public static Boolean checkDiagonalWinComp(String[][] field) {

        int counter = 0;
        for (int i = 0; i < field.length; i++) {
            if (field[i][i] != null && field[i][i].equals("o")) {
                counter++;

            }

        }
        if (counter == 3) {
            return true;
        }

        if (field[0][2] != null && field[1][1] != null && field[2][0] != null) {
            if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0])) {
                return true;
            }
        }
        return false;
    }
/* ===================================================================================================
                            these methods are for the immpossible mode
                            which is not a requirement for project 1
                            put I did it to practice my logic and to analyise
                            the game, and for FUN ! :)
======================================================================================================
 */

    // simple method to handle the first input from the user and decide which algorithm to use
    public static void playImpossibleMode() {

        displayField(impossibleField);
        impossibleField = userPlay(impossibleField);

        if (userChoseCenter(impossibleField)) {
            manageUserCenter();

        } else if (userChoseCorner()) {
            manageUserCorner();
        } else if (userChoseEdge()) {
            manageUserEdge();
        }

    }


    // ================== this method handles the case of user started at positon [5] =============
    public static boolean userChoseCenter(String[][] field) {

        try {
            if (field[1][1].contains("x")) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public static void manageUserCenter() {

        // comp auto place in position 1
        System.out.println("=========================== Computer Turn ===========================");
        System.out.println("Computer choose position number 1" + " : ");
        System.out.println("=====================================================================");
        impossibleField[0][0] = "o";
        displayField(impossibleField);

        impossibleField = userPlay(impossibleField);

        if (impossibleField[2][2] != null) {
            if (impossibleField[2][2].equals("x")) {
                System.out.println("=========================== Computer Turn ===========================");
                System.out.println("Computer choose position number 3" + " : ");
                System.out.println("=====================================================================");
                impossibleField[0][2] = "o";
                displayField(impossibleField);
            }
        } else {
            blockUserLine();
        }


        while (true) {

            impossibleField = userPlay(impossibleField);

            if (checkIfTheGameIsTie(impossibleField)) {
                System.out.println("game is tie");
                break;
            }

            if (!completeCompSequence() && !blockUserLine()) {

                impossibleField = compPlay(impossibleField);

            }

            //completeCompSequence();
            if (!checkCompWin(impossibleField)) {
                blockUserLine();

            } else {
                System.out.println("You Lose! comp win :)");
                break;
            }


        }

    }


    // ================== this method handles the case of user started at a corner positon  =============
    public static boolean userChoseCorner() {


        if ("x".equals(impossibleField[0][0]) ||
                "x".equals(impossibleField[0][2]) || "x".equals(impossibleField[2][0]) ||
                "x".equals(impossibleField[2][2])) {
            return true;
        }
        return false;
    }

    public static void manageUserCorner() {
        System.out.println("=========================== Computer Turn ===========================");
        System.out.println("Computer chooses the center (position 5):");
        impossibleField[1][1] = "o";
        displayField(impossibleField);

        //1st move
        impossibleField = userPlay(impossibleField);

        boolean computerMadeMove = false;


        if ("x".equals(impossibleField[0][0])) {

            if ("x".equals(impossibleField[2][1])) {

                System.out.println("Computer choose position 9 :");
                impossibleField[2][2] = "o";
                computerMadeMove = true;
            } else if ("x".equals(impossibleField[1][2])) {

                System.out.println("Computer choose position 3 :");
                impossibleField[0][2] = "o";
                computerMadeMove = true;
            }
        } else if ("x".equals(impossibleField[0][2])) {

            if ("x".equals(impossibleField[2][1])) {

                System.out.println("Computer chose position 7 :");
                impossibleField[2][0] = "o";
                computerMadeMove = true;
            } else if ("x".equals(impossibleField[1][0])) {
                // User places on left edge after top-right corner
                System.out.println("Computer choose position 1 :");
                impossibleField[0][0] = "o";
                computerMadeMove = true;
            }
        } else if ("x".equals(impossibleField[2][0])) {

            if ("x".equals(impossibleField[0][1])) {

                System.out.println("Computer choose position 3 :");
                impossibleField[0][2] = "o";
                computerMadeMove = true;
            } else if ("x".equals(impossibleField[1][2])) {

                System.out.println("Computer choose position 9 : ");
                impossibleField[2][2] = "o";
                computerMadeMove = true;
            }
        } else if ("x".equals(impossibleField[2][2])) {

            if ("x".equals(impossibleField[0][1])) {

                System.out.println("Computer choose position 1 ");
                impossibleField[0][0] = "o";
                computerMadeMove = true;
            } else if ("x".equals(impossibleField[1][0])) {

                System.out.println("Computer choose position 7 :");
                impossibleField[2][0] = "o";
                computerMadeMove = true;
            }
        }


        if (!computerMadeMove) {
            if (!blockUserLine()) {
                impossibleField = compPlay(impossibleField);
            }
        }
        displayField(impossibleField);


        while (true) {
            impossibleField = userPlay(impossibleField);

            // Check if user has won (if the algorithm is running correct should not activate)
            if (checkUserWin(impossibleField)) {
                System.out.println("Congratulations! You win! for some reason !");
                break;
            }

            // Check if it's a tie
            if (checkIfTheGameIsTie(impossibleField)) {
                System.out.println("The game is a tie!");
                break;
            }

            // no wining move for the comp
            if (!completeCompSequence()) {
                // nothing to block
                if (!blockUserLine()) {

                    impossibleField = compPlay(impossibleField);
                }
            }

            if (checkCompWin(impossibleField)) {
                System.out.println("You Lose! Computer wins :)");
                break;
            }


            if (checkIfTheGameIsTie(impossibleField)) {
                System.out.println("The game is a tie!");
                break;
            }
        }
    }


    // ================== this method handles the case of user started at a edge positon  =============
    public static boolean userChoseEdge() {

        if ("x".equals(impossibleField[0][1]) ||
                "x".equals(impossibleField[1][0]) ||
                "x".equals(impossibleField[1][2]) ||
                "x".equals(impossibleField[2][1])) {
            return true;
        }
        return false;
    }

    public static void manageUserEdge() {

        System.out.println("=========================== Computer Turn ===========================");
        System.out.println("Computer chooses the center (position 5):");
        impossibleField[1][1] = "o";
        displayField(impossibleField);

        // 2nd move
        impossibleField = userPlay(impossibleField);


        if ("x".equals(impossibleField[0][1])) {

            System.out.println("Computer choose position 1 :");
            impossibleField[0][0] = "o";
        } else if ("x".equals(impossibleField[2][1])) {

            System.out.println("Computer chjoose position 9 :");
            impossibleField[2][2] = "o";
        } else if ("x".equals(impossibleField[1][0])) {

            System.out.println("Computer choose position 1):");
            impossibleField[0][0] = "o";
        } else if ("x".equals(impossibleField[1][2])) {

            System.out.println("Computer choose position 3 :");
            impossibleField[0][2] = "o";
        } else if ("x".equals(impossibleField[0][0]) || "x".equals(impossibleField[0][2]) ||
                "x".equals(impossibleField[2][0]) || "x".equals(impossibleField[2][2])) {

            if ("x".equals(impossibleField[0][0])) {
                System.out.println("Computer choose position 9 :");
                impossibleField[2][2] = "o";
            } else if ("x".equals(impossibleField[0][2])) {
                System.out.println("Computer choose position 7 :");
                impossibleField[2][0] = "o";
            } else if ("x".equals(impossibleField[2][0])) {
                System.out.println("Computer choose position 3 :");
                impossibleField[0][2] = "o";
            } else if ("x".equals(impossibleField[2][2])) {
                System.out.println("Computer choose position 1 :");
                impossibleField[0][0] = "o";
            }
        }
        displayField(impossibleField);


        while (true) {
            impossibleField = userPlay(impossibleField);


            // Check if user has won (if the algorithm is running correct should not activate)
            if (checkUserWin(impossibleField)) {
                System.out.println("Congratulations! You win! for some reason !");
                break;
            }

            if (checkIfTheGameIsTie(impossibleField)) {
                System.out.println("The game is a tie!");
                break;
            }


            if (!completeCompSequence()) {

                if (!blockUserLine()) {

                    impossibleField = compPlay(impossibleField);
                }
            }


            if (checkCompWin(impossibleField)) {
                System.out.println("You Lose! Computer wins :)");
                break;
            }


            if (checkIfTheGameIsTie(impossibleField)) {
                System.out.println("The game is a tie!");
                break;
            }
        }
    }


    // this method is used to complete the sequence for the computer to win
    public static boolean completeCompSequence() {
        // Check rows for a winning move
        for (int i = 0; i < 3; i++) {
            if (impossibleField[i][0] == "o" && impossibleField[i][1] == "o" && impossibleField[i][2] == null) {
                impossibleField[i][2] = "o";
                displayField(impossibleField);
                return true;
            }
            if (impossibleField[i][0] == "o" && impossibleField[i][2] == "o" && impossibleField[i][1] == null) {
                impossibleField[i][1] = "o";
                displayField(impossibleField);
                return true;
            }
            if (impossibleField[i][1] == "o" && impossibleField[i][2] == "o" && impossibleField[i][0] == null) {
                impossibleField[i][0] = "o";
                displayField(impossibleField);
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (impossibleField[0][j] == "o" && impossibleField[1][j] == "o" && impossibleField[2][j] == null) {
                impossibleField[2][j] = "o";
                displayField(impossibleField);
                return true;
            }
            if (impossibleField[0][j] == "o" && impossibleField[2][j] == "o" && impossibleField[1][j] == null) {
                impossibleField[1][j] = "o";
                displayField(impossibleField);
                return true;
            }
            if (impossibleField[1][j] == "o" && impossibleField[2][j] == "o" && impossibleField[0][j] == null) {
                impossibleField[0][j] = "o";
                displayField(impossibleField);
                return true;
            }
        }

        // Check diagonals
        if (impossibleField[0][0] == "o" && impossibleField[1][1] == "o" && impossibleField[2][2] == null) {
            impossibleField[2][2] = "o";
            displayField(impossibleField);
            return true;
        }
        if (impossibleField[0][0] == "o" && impossibleField[2][2] == "o" && impossibleField[1][1] == null) {
            impossibleField[1][1] = "o";
            displayField(impossibleField);
            return true;
        }
        if (impossibleField[1][1] == "o" && impossibleField[2][2] == "o" && impossibleField[0][0] == null) {
            impossibleField[0][0] = "o";
            displayField(impossibleField);
            return true;
        }

        // Anti-diagonal
        if (impossibleField[0][2] == "o" && impossibleField[1][1] == "o" && impossibleField[2][0] == null) {
            impossibleField[2][0] = "o";
            displayField(impossibleField);
            return true;
        }
        if (impossibleField[0][2] == "o" && impossibleField[2][0] == "o" && impossibleField[1][1] == null) {
            impossibleField[1][1] = "o";
            displayField(impossibleField);
            return true;
        }
        if (impossibleField[1][1] == "o" && impossibleField[2][0] == "o" && impossibleField[0][2] == null) {
            impossibleField[0][2] = "o";
            displayField(impossibleField);
            return true;
        }

        return false;
    }


    // this method checks if there is any line for the user to win and block them
    public static boolean blockUserLine() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if ("x".equals(impossibleField[i][0]) && "x".equals(impossibleField[i][1]) && impossibleField[i][2] == null) {
                impossibleField[i][2] = "o";
                displayField(impossibleField);
                return true;
            }
            if ("x".equals(impossibleField[i][0]) && "x".equals(impossibleField[i][2]) && impossibleField[i][1] == null) {
                impossibleField[i][1] = "o";
                displayField(impossibleField);
                return true;
            }
            if ("x".equals(impossibleField[i][1]) && "x".equals(impossibleField[i][2]) && impossibleField[i][0] == null) {
                impossibleField[i][0] = "o";
                displayField(impossibleField);
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if ("x".equals(impossibleField[0][j]) && "x".equals(impossibleField[1][j]) && impossibleField[2][j] == null) {
                impossibleField[2][j] = "o";
                displayField(impossibleField);
                return true;
            }
            if ("x".equals(impossibleField[0][j]) && "x".equals(impossibleField[2][j]) && impossibleField[1][j] == null) {
                impossibleField[1][j] = "o";
                displayField(impossibleField);
                return true;
            }
            if ("x".equals(impossibleField[1][j]) && "x".equals(impossibleField[2][j]) && impossibleField[0][j] == null) {
                impossibleField[0][j] = "o";
                displayField(impossibleField);
                return true;
            }
        }

        // Check diagonal
        if ("x".equals(impossibleField[0][0]) && "x".equals(impossibleField[1][1]) && impossibleField[2][2] == null) {
            impossibleField[2][2] = "o";
            displayField(impossibleField);
            return true;
        }
        if ("x".equals(impossibleField[0][0]) && "x".equals(impossibleField[2][2]) && impossibleField[1][1] == null) {
            impossibleField[1][1] = "o";
            displayField(impossibleField);
            return true;
        }
        if ("x".equals(impossibleField[1][1]) && "x".equals(impossibleField[2][2]) && impossibleField[0][0] == null) {
            impossibleField[0][0] = "o";
            displayField(impossibleField);
            return true;
        }

        // Check anti-diagonal
        if ("x".equals(impossibleField[0][2]) && "x".equals(impossibleField[1][1]) && impossibleField[2][0] == null) {
            impossibleField[2][0] = "o";
            displayField(impossibleField);
            return true;
        }
        if ("x".equals(impossibleField[0][2]) && "x".equals(impossibleField[2][0]) && impossibleField[1][1] == null) {
            impossibleField[1][1] = "o";
            displayField(impossibleField);
            return true;
        }
        if ("x".equals(impossibleField[1][1]) && "x".equals(impossibleField[2][0]) && impossibleField[0][2] == null) {
            impossibleField[0][2] = "o";
            displayField(impossibleField);
            return true;
        }


        return false;
    }


    // this method check if the game is a tie retun ture
    public static boolean checkIfTheGameIsTie(String[][] field) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == null) {
                    return false;  // there is an empty position
                }
            }
        }

       // displayField(field);
       // System.out.println("This game is a tie!");
        return true;
    }
}