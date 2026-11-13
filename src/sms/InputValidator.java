package sms;

import java.util.Scanner;

/**
 * Utility Class: InputValidator
 * ----------------------------------------------------
 * Ensures robust user input validation to prevent runtime crashes
 * from invalid numbers, empty inputs, or type mismatches.
 */
public class InputValidator {

    /**
     * Reads a non-empty string from console
     */
    public static String readString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("  [!] Error: Input cannot be empty. Please try again.");
        }
    }

    /**
     * Reads an integer within [min, max] range safely
     */
    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("  [!] Error: Please enter a number between %d and %d.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Error: Invalid numeric input. Please enter an integer.");
            }
        }
    }

    /**
     * Reads a double within [min, max] range safely
     */
    public static double readDouble(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(line);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("  [!] Error: Value must be between %.2f and %.2f.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Error: Invalid number format. Please enter a valid decimal number.");
            }
        }
    }

    /**
     * Confirms action (yes/no)
     */
    public static boolean readConfirmation(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            }
            if (input.equals("n") || input.equals("no")) {
                return false;
            }
            System.out.println("  [!] Please enter 'y' for Yes or 'n' for No.");
        }
    }

    /**
     * Pauses console until user presses Enter
     */
    public static void pause(Scanner scanner) {
        System.out.print("\nPress [Enter] to continue...");
        scanner.nextLine();
    }
}
