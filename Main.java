package bullscows;

import java.util.Scanner;

public class Main {
    private static Scanner s = new Scanner(System.in);
    private static String secret = "";
    public static void main(String[] args) {
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
            System.out.println(secret);
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
        if (bulls != 0)
            System.out.printf("Grade:%d bull(s) %d cow(s). The secret code is %s",
                    bulls, cows, secret);
        else if (cows != 0)
            System.out.printf("Grade: %d cow(s). The secret code is %s",
                    cows, secret);
        else
            System.out.printf("Grade: None. The secret code is %s",
                    secret);
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
    private static int calculateBulls(String input, int cows, int bulls) {
        char[] inputArray = input.toCharArray();
        for (int i = 0; i < secret.length(); i++ ) {
            if (inputArray[i] == secret.charAt(i)) {
                cows--;
                bulls++;
            }
        }
        return bulls;
    }
}
