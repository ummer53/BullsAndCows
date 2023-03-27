package bullscows;

import java.util.Scanner;

public class Main {
    private static Scanner s = new Scanner(System.in);
    private static String secret = "";
    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");
        int lengthOfSecretCode = s.nextInt();
        if (lengthOfSecretCode > 10) {
            System.out.println("Error: can't generate a secret number with a length of" +
                    " 11 because there aren't enough unique digits.");
        }
        else {
            long pseudoRandomNumber = System.nanoTime();
            String tempSecret = String.valueOf(pseudoRandomNumber);
            secret = generateSecretCode(tempSecret, lengthOfSecretCode);
            while (secret.length() != lengthOfSecretCode) {
                secret = generateSecretCode(String.valueOf(System.nanoTime()), lengthOfSecretCode);
            }
            int[] result = new int[2];
            int turn = 1;
            System.out.println("Okay, let's start a game!");
            while (result[1] != lengthOfSecretCode) {
                result[0] = 0;
                result[1] = 0;
                System.out.printf("Turn %d:%n", turn);
                String guessedInput = s.next();
                result[0] = calculateCows(guessedInput, result[0]);
                result[1] = calculateBulls(guessedInput, result, result[1]);
                if (result[1] == lengthOfSecretCode) {
                    System.out.printf("Grade: %d bulls%nCongratulations!" +
                            " You guessed the secret code.",result[1]);
                }
                grade(result[0], result[1]);
                turn++;
            }

        }
    }

    private static String generateSecretCode(String tempSecret, int length) {
        int count = 0;
        for (int i = 0; i < tempSecret.length(); i++) {
            if (!secret.contains("" + tempSecret.charAt(i))) {
                secret = secret + tempSecret.charAt(i);
                count++;
            }
            if (count == length) {
                return secret;
            }
        }
        return secret;
    }

    private static void grade(int cows, int bulls) {
        if (bulls > 0 && cows > 0) {
            if (bulls == 1 && cows == 1)
                System.out.printf("Grade: %d bull and %d cow%n",bulls,cows);
            System.out.printf("Grade: %d bulls and %d cows%n",bulls,cows);
        } else if (bulls == 0 && cows != 0) {
            if (cows == 1) {
                System.out.printf("Grade: %d cow%n", cows);
            }
            else {
                System.out.printf("Grade: %d cows%n", cows);
            }
        } else if (cows == 0 && bulls != 0) {
            if (bulls == 1){
                System.out.printf("Grade: %d bull%n", bulls);
            }
            else {
                System.out.printf("Grade: %d bulls%n", bulls);
            }
        } else {
            System.out.printf("Grade: None%n");
        }
    }
    private static int calculateCows(String input, int cows) {
        char[] inputArray = input.toCharArray();
        for (char c : inputArray) {
            String temp = "" + c;
            if (secret.contains(temp)) {
                cows++;
            }
        }
        return cows;
    }
    private static int calculateBulls(String input, int[] result, int bulls) {
        char[] inputArray = input.toCharArray();
        for (int i = 0; i < secret.length(); i++ ) {
            if (inputArray[i] == secret.charAt(i)) {
                result[0]--;
                bulls++;
            }
        }
        return bulls;
    }
}
