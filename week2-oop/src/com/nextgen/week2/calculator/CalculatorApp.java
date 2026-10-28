package com.nextgen.week2.calculator;

import java.util.Scanner;

/**
 * Interactive OOP Calculator Application.
 */
public class CalculatorApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("==================================================");
        System.out.println("           OOP CALCULATOR SYSTEM                  ");
        System.out.println("     Next Gen Software Hub - Week 2 Project       ");
        System.out.println("==================================================");
        System.out.println("Supported operators: " + String.join("  ", calculator.getSupportedOperations()));

        boolean keepRunning = true;
        while (keepRunning) {
            try {
                System.out.print("\nEnter first number: ");
                if (!scanner.hasNextDouble()) {
                    System.out.println("Invalid numeric input!");
                    scanner.next();
                    continue;
                }
                double num1 = scanner.nextDouble();

                System.out.print("Enter operator (+, -, *, /, %, ^): ");
                String op = scanner.next().trim();

                System.out.print("Enter second number: ");
                if (!scanner.hasNextDouble()) {
                    System.out.println("Invalid numeric input!");
                    scanner.next();
                    continue;
                }
                double num2 = scanner.nextDouble();

                double result = calculator.calculate(op, num1, num2);
                System.out.printf("=> Result: %.4f %s %.4f = %.4f%n", num1, op, num2, result);

            } catch (ArithmeticException | UnsupportedOperationException e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("\nCalculate another expression? (y/n): ");
            String reply = scanner.next().trim().toLowerCase();
            keepRunning = reply.startsWith("y");
        }

        System.out.println("Exiting OOP Calculator. Goodbye!");
        scanner.close();
    }
}
