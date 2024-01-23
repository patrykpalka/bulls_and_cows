package bullscows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        int length = 0;
        try {
            length = Integer.valueOf(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: input must be a number form 1 to 36");
        }
        if (length < 1) {
            System.out.println("Error: length cannot be smaller than 1");
            return;
        } else if (length > 36) {
            System.out.println("Error: can't generate a secret code with a length of " + length +
                    " because there aren't enough unique digits and letters.");
            return;
        }
        System.out.println("Input the number of possible symbols in the code:");
        int symbols = Integer.valueOf(scan.nextLine());
        if (symbols < length) {
            System.out.println("Error: number of symbols cannot be less than length of the code");
            return;
        } else if (symbols > 36) {
            System.out.println("Error: can't generate a secret code with a length of " + symbols +
                    " because there aren't enough unique digits and letters.");
            return;
        }
        final String theCode = randomGenerator(length, symbols);
        System.out.println("Okay, let's start a game!");
        int turn = 1;

        while (true) {
            System.out.println("Turn " + turn + ":");
            String userCode = scan.nextLine();
            if (userCode.length() != length) {
                System.out.println("Error: number typed is not of " + length + " length");
            }
            if (checkForUnvalidInput(userCode, symbols)) {
                return;
            }
            int bulls = 0;
            int cows = 0;
            for (int i = 0; i < userCode.length(); i++) {
                if (theCode.contains(String.valueOf(userCode.charAt(i)))) {
                    if (theCode.charAt(i) == userCode.charAt(i)) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }
            if (bulls == length) {
                System.out.println("Grade: " + bulls + " bull(s)"   );
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            } else if (bulls == 0 && cows == 0) {
                System.out.println("Grade: None");
            } else if (bulls > 0 && cows == 0) {
                System.out.println("Grade: " + bulls + " bull(s)");
            } else if (bulls == 0 && cows > 0) {
                System.out.println("Grade: " + cows + " cow(s)");
            } else if (bulls > 0 && cows > 0) {
                System.out.println("Grade: " + bulls + " bull(s) and " + cows + " cow(s)");
            }
            turn++;
        }
    }

    public static String randomGenerator(int length, int symbols) {
        List<Character> randomList = new ArrayList<>();
        StringBuilder stars = new StringBuilder();
        if (symbols < 11) {
            for (int i = 48; i < 48 + symbols; i++) {
                randomList.add((char)i);
            }
            if (symbols == 1) {
                System.out.println("The secret is prepared: * (0).");
            }
            for (int j = 0; j < length; j++) {
                stars.append("*");
            }
            System.out.println("The secret is prepared: " + stars.toString() + " (0-" + (symbols - 1) + ").");
        } else {
            for (int i = 48; i <= 57; i++) {
                randomList.add((char)i);
            }
            for (int j = 97; j < 97 + symbols - 10; j++) {
                randomList.add((char)j);
            }
            for (int j = 0; j < length; j++) {
                stars.append("*");
            }
            if (symbols == 11) {
                System.out.println("The secret is prepared: " + stars.toString() + " (0-9, a).");
            } else {
                int tempSymbols = 97 + symbols - 11;
                System.out.println("The secret is prepared: " + stars.toString() +
                        " (0-9, a-" + String.valueOf((char) tempSymbols) + ").");
            }
        }
        while (randomList.get(0) == '0') {
            Collections.shuffle(randomList);
        }
        StringBuilder result = new StringBuilder();
        for (var ch : randomList.subList(0, symbols)) {
            result.append(ch);
        }
        result.delete(length, result.length());
        return result.toString();
    }

    public static boolean checkForUnvalidInput(String input, int symbols) {
        for (char c : input.toCharArray()) {
            if (symbols < 11) {
                if (c < 48 && c >= 48 + symbols) {
                    System.out.println("Error: user input contains invalid symbol");
                    return true;
                }
            } else {
                if (c < 48 && c >= 48 + symbols && c < 97 && c >= 97 + symbols - 10) {
                    System.out.println("Error: user input contains invalid symbol");
                    return true;
                }
            }
        }
        return false;
    }
}