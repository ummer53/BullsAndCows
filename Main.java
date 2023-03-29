package bullscows;

import java.util.Scanner;

public class Main extends Difficulty{
    static Scanner s = new Scanner(System.in);
    public static void main(String[] args) {
        if (new Difficulty().Difficulty()) {
            GamePlay game = new GamePlay();
        }
    }
}
