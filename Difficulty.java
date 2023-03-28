package bullscows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Difficulty {
    static Scanner s = new Scanner(System.in);
    static String secret = "";
    static List<Character> symbols;
    static int lengthOfSecretCode;
    static int symbolRange;
    static Random random = new Random();

    public  Difficulty() {
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
        String difficulty = secretCodeLength(lengthOfSecretCode);
        long lowerRange = (long) Math.pow(10,lengthOfSecretCode);
            long upperRange = (long) Math.pow(10, (lengthOfSecretCode * 3));

            long pseudoRandomNumber = random.nextLong(upperRange);
            while (pseudoRandomNumber < lowerRange) {
                pseudoRandomNumber = random.nextLong(upperRange) + lowerRange;
            }
            String tempSecret = String.valueOf(pseudoRandomNumber);
            while (secret.length() != lengthOfSecretCode) {
                secret = generateMixedSecretCode(tempSecret, lengthOfSecretCode);
                tempSecret = String.valueOf(random.nextLong(upperRange));
            }
            System.out.printf("The secret is prepared: %s%n",difficulty);

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
            char c = symbols.get(index);
            symbols.remove(index);
            secret = secret + c;
            count++;
        }
        if (count == length) {
            return secret;
        }
        return secret;
    }
    private static String secretCodeLength(int lengthOfSecretCode) {
        String secretCodeLength = "";
        int i = 0;
        while (i < lengthOfSecretCode) {
            secretCodeLength += '*';
            i++;
        }

        return secretCodeLength + " (" + (symbolRange < 11 ? ("0-%c".formatted(symbols.get(symbols.size() - 1))) :
                ("0-9, a-%c".formatted(symbols.get(symbols.size() - 1)))) + ")";
    }
}
