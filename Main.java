package bullscows;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Scanner s = new Scanner(System.in);
    private static String secret = "";
    static int lengthOfSecretCode;
    static int symbolRange;
    private static Random random = new Random();
    private static List<Character> symbols;
    public static void main(String[] args) {
        System.out.println("Input the length of the secret code:");
        lengthOfSecretCode = s.nextInt();
        System.out.println("Input the number of possible symbols in the code:");
        symbolRange = s.nextInt();   // to store the number of distinct symbols user wants to use in code
        while (lengthOfSecretCode > symbolRange) {
            System.out.println("Error: can't generate a secret number with a length of" +
                    " 11 because there aren't enough unique digits.");
            System.out.println("Input the length of the secret code:");
            lengthOfSecretCode = s.nextInt();
            System.out.println("Input the number of possible symbols in the code:");
            symbolRange = s.nextInt();
        }
        fillSymbolList(symbolRange);
        //System.out.println(symbols);
        long lowerRange = (long) Math.pow(10,lengthOfSecretCode);
        long upperRange = (long) Math.pow(10, (lengthOfSecretCode * 3));

//        if (lengthOfSecretCode > ) {
//            System.out.println("Error: can't generate a secret number with a length of" +
//                    " 11 because there aren't enough unique digits.");
//        }
      //  else
        {
            long pseudoRandomNumber = random.nextLong(upperRange);
            while (pseudoRandomNumber < lowerRange) {
                pseudoRandomNumber = random.nextLong(upperRange) + lowerRange;
            }
            String tempSecret = String.valueOf(pseudoRandomNumber);
            while (secret.length() != lengthOfSecretCode) {
                             //   System.out.println(secret);
                             //  System.out.println( "temp " + tempSecret);
                if (symbolRange < 11) {
                    secret = generateMixedSecretCode(tempSecret, lengthOfSecretCode);
                }else {
                    secret = generateMixedSecretCode(tempSecret, lengthOfSecretCode);
                }
                tempSecret = String.valueOf(random.nextLong(upperRange));
            }
            System.out.println(secret);
            System.out.printf("The secret is prepared: %s%n",secretCodeLength(lengthOfSecretCode));
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

    private static Object secretCodeLength(int lengthOfSecretCode) {
        String secretCodeLength = "";
        int i = 0;
        while (i < lengthOfSecretCode) {
            secretCodeLength += '*';
            i++;
        }

        return secretCodeLength + " (" + (symbolRange < 11 ? ("0-%c".formatted(symbols.get(symbols.size() - 1))) :
                ("0-9, a-%c".formatted(symbols.get(symbols.size() - 1)))) + ")";
    }

    private static void fillSymbolList(int symbolRange) {
        symbols = new ArrayList<>(symbolRange);
        int  decimalRange = 10;
        if (symbolRange < decimalRange) {
            for (int i = 0; i < symbolRange; i++) {
                symbols.add((char)((char)i + '0'));
            }
        }
        else {
            for (int i = 0; i < decimalRange; i++) {
                symbols.add((char)((char)i + '0'));
            }
            symbolRange = symbolRange - decimalRange;
            for (int i = 0; i < symbolRange; i++) {
                symbols.add((char)((char)i + 'a'));
            }

        }
    }
    private static String generateMixedSecretCode(String tempSecret, int length) {
        int count = 0;
        secret = "";
        for (int i = 0; i < length; i++) {
            if (symbols.size() == 1) {
                return secret + symbols.get(0);
            }
            int index = ((String.valueOf(random.nextInt(100)) + tempSecret.charAt(i)).hashCode()) % symbols.size();
            //System.out.println(index);
            char c = symbols.get(index);
            symbols.remove(index);
            secret = secret + c;
           // System.out.println(symbols);
            count++;
            }
            if (count == length) {
                return secret;
            }

        return secret;
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
