package com.nextgen.week1.projects;

import java.util.Random;
import java.util.Scanner;

/**
 * Week 1 Console Project: Number Guessing Game
 * Next Gen Software Hub Pvt. Ltd. - Internship Task
 *
 * Features:
 * - Configurable difficulty levels (Easy, Medium, Hard)
 * - Maximum attempts constraint based on difficulty
 * - Hot/Cold hint guidance
 * - Score tracking and replay capability
 */
public class NumberGuessingGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;
        int totalGames = 0;
        int gamesWon = 0;

        System.out.println("==================================================");
        System.out.println("         WELCOME TO NUMBER GUESSING GAME          ");
        System.out.println("     Next Gen Software Hub - Week 1 Project       ");
        System.out.println("==================================================");

        while (playAgain) {
            totalGames++;
            System.out.println("\nSelect Difficulty Level:");
            System.out.println("1. Easy   (Range: 1 to 50,  Max Attempts: 10)");
            System.out.println("2. Medium (Range: 1 to 100, Max Attempts: 7)");
            System.out.println("3. Hard   (Range: 1 to 200, Max Attempts: 5)");
            System.out.print("Choose option (1-3): ");

            int choice = 2;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.next();
            }

            int maxRange;
            int maxAttempts;

            switch (choice) {
                case 1 -> {
                    maxRange = 50;
                    maxAttempts = 10;
                }
                case 3 -> {
                    maxRange = 200;
                    maxAttempts = 5;
                }
                default -> {
                    maxRange = 100;
                    maxAttempts = 7;
                }
            }

            int targetNumber = random.nextInt(maxRange) + 1;
            int attemptsUsed = 0;
            boolean won = false;

            System.out.printf("%nI have chosen a secret number between 1 and %d.%n", maxRange);
            System.out.printf("You have %d attempts. Best of luck!%n%n", maxAttempts);

            while (attemptsUsed < maxAttempts) {
                attemptsUsed++;
                System.out.printf("[Attempt %d/%d] Enter your guess: ", attemptsUsed, maxAttempts);

                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    scanner.next();
                    attemptsUsed--; // don't penalize format error
                    continue;
                }

                int guess = scanner.nextInt();

                if (guess == targetNumber) {
                    System.out.printf("🎉 BINGO! You guessed the number %d in %d attempts!%n",
                            targetNumber, attemptsUsed);
                    int score = ((maxAttempts - attemptsUsed + 1) * 100) / maxAttempts;
                    System.out.printf("Round Score: %d / 100 points!%n", score);
                    won = true;
                    gamesWon++;
                    break;
                } else if (guess < targetNumber) {
                    int diff = targetNumber - guess;
                    if (diff <= 5) {
                        System.out.println("Too low, but boiling hot! You are very close.");
                    } else if (diff <= 15) {
                        System.out.println("A bit low, warm!");
                    } else {
                        System.out.println("Too low! Cold.");
                    }
                } else {
                    int diff = guess - targetNumber;
                    if (diff <= 5) {
                        System.out.println("Too high, but boiling hot! You are very close.");
                    } else if (diff <= 15) {
                        System.out.println("A bit high, warm!");
                    } else {
                        System.out.println("Too high! Cold.");
                    }
                }
            }

            if (!won) {
                System.out.printf("%n💀 Game Over! You've used all %d attempts.%n", maxAttempts);
                System.out.printf("The secret number was: %d%n", targetNumber);
            }

            System.out.printf("%nTotal Games Played: %d | Games Won: %d%n", totalGames, gamesWon);
            System.out.print("Do you want to play another round? (y/n): ");
            String reply = scanner.next().trim().toLowerCase();
            playAgain = reply.startsWith("y");
        }

        System.out.println("\nThanks for playing! Keep coding with Next Gen Software Hub!");
        scanner.close();
    }
}
