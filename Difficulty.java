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
    static String number = "1234567890";
    static String allowedGuess = "0123456789abcdefghijklmnopqrstuvwxyz";
    static Random random = new Random();

    public boolean Difficulty() {
        if(determineDifficulty()) {
            fillSymbolList();
            String difficulty = secretCodeLength();
          //  System.out.println("Hi");
            generateCode();
            System.out.printf("The secret is prepared: %s%n", difficulty);
            return true;
        }
        return false;
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

    private boolean determineDifficulty() {
            System.out.println("Input the length of the secret code:");
            String codeLength = s.nextLine();
            if (validNum(codeLength)) {
                lengthOfSecretCode = Integer.valueOf(codeLength);
            }
            else {
                System.out.printf("Error: \"%s\" isn't a valid number.%n",codeLength);
                return false;
            }
            System.out.println("Input the number of possible symbols in the code:");
            String uniqueSymbols = s.nextLine();
            if (validNum(uniqueSymbols)) {
                symbolRange = Integer.valueOf(uniqueSymbols);
                if (symbolRange > 36) {
                    System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                    return false;
                }
            }
            else {
                System.out.printf("Error: \"%s\" isn't a valid number.%n",uniqueSymbols);
                return false;
            }

            if (lengthOfSecretCode == 0 || lengthOfSecretCode > symbolRange){
                System.out.printf("Error: it's not possible to generate a code with a length " +
                        "of %d with %d unique symbols.%n",lengthOfSecretCode,symbolRange);
                return false;
            }
            else {
                return true;
            }


    }

    private boolean validNum(String codeLength) {
        for (int i = 0; i < codeLength.length(); i++) {
            if (!number.contains("" + codeLength.charAt(i))) {
                return false;
            }
        }
        return true;
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
       // System.out.println(symbols);
        String difficulty = "*".repeat(lengthOfSecretCode);
        if (symbolRange <= 10) {
           // System.out.println("this");
            difficulty += "(0-%d).".formatted( symbolRange - 1);
        } else {
            difficulty += "(0-9, a-%c).".formatted(symbols.get(symbols.size() - 1));
        }
        return difficulty;
    }
}
