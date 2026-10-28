package com.nextgen.week1;

/**
 * Week 1: Control Statements (if-else, switch, loops)
 * Next Gen Software Hub Pvt. Ltd. - Internship Task
 */
public class ControlStatements {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" 1. CONDITIONAL STATEMENTS (if - else if - else)");
        System.out.println("==================================================");

        int score = 85;
        System.out.println("Evaluating student score: " + score);
        if (score >= 90) {
            System.out.println("Result: Grade A - Excellent Performance");
        } else if (score >= 75) {
            System.out.println("Result: Grade B - Very Good");
        } else if (score >= 60) {
            System.out.println("Result: Grade C - Satisfactory");
        } else if (score >= 40) {
            System.out.println("Result: Grade D - Pass");
        } else {
            System.out.println("Result: Grade F - Needs Improvement");
        }

        System.out.println("\n==================================================");
        System.out.println(" 2. SWITCH STATEMENT");
        System.out.println("==================================================");

        int dayOfWeek = 3;
        String dayName = switch (dayOfWeek) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6 -> "Saturday";
            case 7 -> "Sunday";
            default -> "Invalid Day";
        };
        System.out.printf("Day number %d corresponds to: %s%n", dayOfWeek, dayName);

        System.out.println("\n==================================================");
        System.out.println(" 3. LOOPS (for, while, do-while)");
        System.out.println("==================================================");

        System.out.print("Standard for loop (1 to 5): ");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.print("While loop (Countdown 5 to 1): ");
        int count = 5;
        while (count > 0) {
            System.out.print(count + " ");
            count--;
        }
        System.out.println();

        System.out.print("Do-While loop (Runs at least once): ");
        int k = 1;
        do {
            System.out.print(k + " ");
            k++;
        } while (k <= 3);
        System.out.println();

        System.out.println("\n==================================================");
        System.out.println(" 4. JUMP STATEMENTS (break and continue)");
        System.out.println("==================================================");

        System.out.print("Loop with continue (skipping even numbers from 1 to 10): ");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue; // skip even
            }
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.print("Loop with break (stops when reaching 7 in 1 to 10): ");
        for (int i = 1; i <= 10; i++) {
            if (i == 7) {
                break; // terminate loop
            }
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
