package bullscows;

import java.util.Scanner;

public class GamePlay {
    static Scanner s = new Scanner(System.in);
    int[] result = new int[2];
    int turn = 1;
    public GamePlay() {
        System.out.println("Okay, let's start a game!");
        while (result[1] != Difficulty.lengthOfSecretCode) {
            result[0] = 0;
            result[1] = 0;
            System.out.printf("Turn %d:%n", turn);
            String guessedInput = s.next();
            result[0] = calculateCows(guessedInput, result[0]);
            result[1] = calculateBulls(guessedInput, result, result[1]);
            if (result[1] == Difficulty.lengthOfSecretCode) {
                System.out.printf("Grade: %d bulls%nCongratulations!" +
                        " You guessed the secret code.",result[1]);
            }
            grade(result[0], result[1]);
            turn++;
        }
    }
    private static void grade(int cows, int bulls) {
        if (bulls > 0 || cows > 0) {
            System.out.println("Grade : " + (bulls <= 1 ? bulls + " bull " : bulls + " bulls ") +
                    (cows <= 1 ? cows + " cow " : cows + " cows "));
        }
        else {
            System.out.printf("Grade: None%n");
        }
    }
    private static int calculateCows(String input, int cows) {
        char[] inputArray = input.toCharArray();
        for (char c : inputArray) {
            String temp = "" + c;
            if (Difficulty.secret.contains(temp)) {
                cows++;
            }
        }
        return cows;
    }
    private static int calculateBulls(String input, int[] result, int bulls) {
        char[] inputArray = input.toCharArray();
        for (int i = 0; i < Difficulty.secret.length(); i++ ) {
            if (inputArray[i] == Difficulty.secret.charAt(i)) {
                result[0]--;
                bulls++;
            }
        }
        return bulls;
    }
}
