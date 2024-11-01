import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

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

}