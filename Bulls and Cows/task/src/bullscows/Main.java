package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        generateSecretNumber();
    }

    private static void generateSecretNumber() {
        System.out.println("Input the length of the secret code:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (!input.matches("[1-9][0-9]*")) {
            System.out.println("Error: \"" + input + "\" isn't a valid number.");
            System.exit(0);
        }

        int lengthOfSecretCode = Integer.parseInt(input);

        System.out.println("Input the number of possible symbols in the code:");
        int numberOfPossibleSymbols = scanner.nextInt();

        if (lengthOfSecretCode > numberOfPossibleSymbols) {
            System.out.println("Error: it's not possible to generate a code with a length of " +
                    lengthOfSecretCode + " with " + numberOfPossibleSymbols + " unique symbols.");
            System.exit(0);
        }



        if (numberOfPossibleSymbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }


        String AlphaNumericString = "0123456789" + "abcdefghijklmnopqrstuvxyz";
        if (numberOfPossibleSymbols < 36) {
            AlphaNumericString = AlphaNumericString.substring(0, numberOfPossibleSymbols);
        }
        getStringBuilder(lengthOfSecretCode, AlphaNumericString);
    }

    private static void getStringBuilder(int lengthOfSecretCode, String AlphaNumericString) {

        StringBuilder stringBuilder = new StringBuilder(lengthOfSecretCode);

        for (int i = 0; i < lengthOfSecretCode; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());

            stringBuilder.append(AlphaNumericString.charAt(index));
        }

        if (!checkForUnique(stringBuilder)) {

            getStringBuilder(lengthOfSecretCode, AlphaNumericString);
        } else {
            System.out.print("The secret is prepared: ");
            System.out.print("*".repeat(lengthOfSecretCode) + " (0-9, a-" + AlphaNumericString.charAt(AlphaNumericString.length() - 1) + ")");
            System.out.println();
            System.out.println("Okay, let's start a game!");
            gameMechanics(readInput(), stringBuilder.toString(), lengthOfSecretCode);
        }

    }

    private static boolean checkForUnique(StringBuilder secretNumber) {
        for (int i = 0; i < secretNumber.length(); i++) {
            for (int j = i + 1; j < secretNumber.length(); j++) {
                if (secretNumber.charAt(i) == secretNumber.charAt(j)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static String[] readInput() {
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine().split("");
    }

    private static void gameMechanics(String[] input, String secretCode, int lengthOfSecretCode) {
        int numberOfBulls = 0, numberOfCows = 0;

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < secretCode.length(); j++) {
                if (input[i].equals(Character.toString(secretCode.charAt(j))) && i == j) {
                    numberOfBulls++;
                } else if (input[i].equals(Character.toString(secretCode.charAt(j))) && i != j) {
                    numberOfCows++;
                }
            }
        }

        grader(numberOfBulls, numberOfCows);
        if (numberOfBulls == lengthOfSecretCode) {
            grader(numberOfBulls, numberOfCows);
            System.out.println("Congratulations! You guessed the secret code.");
        } else gameMechanics(readInput(), secretCode, lengthOfSecretCode);

    }


    private static void grader(int numberOfBulls, int numberOfCows) {
        System.out.print("Grade: ");
        if (numberOfBulls == 0 && numberOfCows == 0) {
            System.out.print("None.");
            System.out.println();
        } else if (numberOfBulls == 0 && numberOfCows > 0) {
            System.out.print(numberOfCows + " cow(s)");
            System.out.println();
        } else if (numberOfBulls > 0 && numberOfCows == 0) {
            System.out.print(numberOfBulls + " bull(s)");
            System.out.println();
        } else if (numberOfBulls > 0 && numberOfCows > 0) {
            System.out.print(numberOfBulls + " bull(s) and " + numberOfCows + " cow(s)");
            System.out.println();
        }
    }
}
