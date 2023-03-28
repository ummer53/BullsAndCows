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
        determineDifficulty();
        fillSymbolList();
        String difficulty = secretCodeLength();
        generateCode();
        System.out.printf("The secret is prepared: %s%n",difficulty);
    }

    private void generateCode() {
        long lowerRange = (long) Math.pow(10,lengthOfSecretCode);
        long upperRange = (long) Math.pow(10, (lengthOfSecretCode * 2));
        long pseudoRandomNumber = random.nextLong(upperRange);
        while (pseudoRandomNumber < lowerRange) {
            pseudoRandomNumber = random.nextLong(upperRange) + lowerRange;
        }
        String tempSecret = String.valueOf(pseudoRandomNumber);
        secret = generateMixedSecretCode(tempSecret);

    }

    private void determineDifficulty() {
        boolean canPlay = true;
        while (canPlay) {
            System.out.println("Input the length of the secret code:");
            lengthOfSecretCode = s.nextInt();
            System.out.println("Input the number of possible symbols in the code:");
            symbolRange = s.nextInt();
            if (lengthOfSecretCode <= symbolRange)
                canPlay = false;
            else
                System.out.println("Error: can't generate a secret number with a length of" +
                        " 11 because there aren't enough unique digits.");
        }
    }

    private static void fillSymbolList() {
        symbols = new ArrayList<>(symbolRange);
        int range = symbolRange;
        if (range < 11) {
            fillNumericList(range);
        }
        else {
            int  decimalRange = 10;
            fillNumericList(decimalRange);
            range = range - decimalRange;
            fillCharList(range);
        }
    }

    private static void fillCharList(int range) {
        for (int i = 0; i < range; i++) {
            symbols.add((char)((char)i + 'a'));
        }
    }

    private static void fillNumericList(int decimalRange) {
        for (int i = 0; i < decimalRange; i++) {
            symbols.add((char)((char)i + '0'));
        }
    }

    private static String generateMixedSecretCode(String tempSecret) {
        for (int i = 0; i < lengthOfSecretCode; i++) {
            int index = ((String.valueOf(random.nextInt(100)) + tempSecret.charAt(i)).hashCode()) % symbols.size();
            char c = symbols.get(index);
            symbols.remove(index);
            secret = secret + c;
        }
        return secret;
    }

    private static String secretCodeLength() {
        System.out.println(symbols);
        String difficulty = "*".repeat(lengthOfSecretCode);
        if (symbolRange <= 10) {
            System.out.println("this");
            difficulty += "(0-%d).".formatted( symbolRange - 1);
        } else {
            difficulty += "(0-9, a-%c).".formatted(symbols.get(symbols.size() - 1));
        }
        return difficulty;
    }
}
