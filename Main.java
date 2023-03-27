package bullscows;

import java.util.Scanner;

public class Main {
    private static Scanner s = new Scanner(System.in);
    private static String secret = "9305";
    public static void main(String[] args) {
//        System.out.println("The secret code is prepared: ****.\n\n" +
//                "Turn 1. Answer:\n1234\n" +
//                "Grade: None.\n\n" +
//                "Turn 2. Answer:\n9876\n" +
//                "Grade: 4 bulls.\nCongrats! The secret code is 9876.");
        int cows = 0;
        int bulls = 0;
        int[] result = new int[2];
        String input = s.next();
        result[0] = calculateCows(input, result[0]);
        result[1] = calculateBulls(input, result[0], result[1]);
        grade(result[0], result[1] );
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
