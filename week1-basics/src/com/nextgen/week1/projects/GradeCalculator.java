package com.nextgen.week1.projects;

import java.util.Scanner;

/**
 * Week 1 Console Project: Student Grade Calculator
 * Next Gen Software Hub Pvt. Ltd. - Internship Task
 *
 * Features:
 * - Accepts student details and marks across multiple subjects
 * - Input validation for marks (0 to 100)
 * - Calculates total marks, percentage, GPA (10-point scale), letter grade
 * - Determines passing status and honors/distinction
 */
public class GradeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================================");
        System.out.println("          STUDENT GRADE CALCULATOR                ");
        System.out.println("     Next Gen Software Hub - Week 1 Project       ");
        System.out.println("==================================================");

        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine().trim();
        if (studentName.isEmpty()) {
            studentName = "Sample Student";
        }

        System.out.print("Enter Roll Number / Student ID: ");
        String rollNumber = scanner.nextLine().trim();
        if (rollNumber.isEmpty()) {
            rollNumber = "NGS-101";
        }

        int numSubjects = 0;
        while (numSubjects <= 0) {
            System.out.print("Enter number of subjects (e.g., 5): ");
            if (scanner.hasNextInt()) {
                numSubjects = scanner.nextInt();
                if (numSubjects <= 0) {
                    System.out.println("Number of subjects must be greater than 0.");
                }
            } else {
                System.out.println("Invalid input! Please enter a positive integer.");
                scanner.next();
            }
        }

        String[] subjectNames = new String[numSubjects];
        double[] marks = new double[numSubjects];
        double totalMarks = 0;
        boolean hasFailedAny = false;

        scanner.nextLine(); // consume leftover newline

        for (int i = 0; i < numSubjects; i++) {
            System.out.printf("Enter name for Subject %d: ", (i + 1));
            String name = scanner.nextLine().trim();
            subjectNames[i] = name.isEmpty() ? ("Subject " + (i + 1)) : name;

            double mark = -1;
            while (mark < 0 || mark > 100) {
                System.out.printf("  Enter marks for %s (0 - 100): ", subjectNames[i]);
                if (scanner.hasNextDouble()) {
                    mark = scanner.nextDouble();
                    if (mark < 0 || mark > 100) {
                        System.out.println("  Marks must be between 0 and 100!");
                    }
                } else {
                    System.out.println("  Invalid input! Please enter numeric marks.");
                    scanner.next();
                }
            }
            scanner.nextLine(); // consume newline
            marks[i] = mark;
            totalMarks += mark;
            if (mark < 40) {
                hasFailedAny = true;
            }
        }

        double maxPossibleMarks = numSubjects * 100.0;
        double percentage = (totalMarks / maxPossibleMarks) * 100.0;
        double gpa = (percentage / 10.0);

        String letterGrade;
        String performance;
        if (hasFailedAny || percentage < 40.0) {
            letterGrade = "F";
            performance = "Failed";
        } else if (percentage >= 90.0) {
            letterGrade = "A+";
            performance = "Outstanding / Distinction";
        } else if (percentage >= 80.0) {
            letterGrade = "A";
            performance = "Excellent";
        } else if (percentage >= 70.0) {
            letterGrade = "B";
            performance = "Very Good";
        } else if (percentage >= 60.0) {
            letterGrade = "C";
            performance = "Good";
        } else if (percentage >= 50.0) {
            letterGrade = "D";
            performance = "Average";
        } else {
            letterGrade = "E";
            performance = "Pass";
        }

        // Print Report Card
        System.out.println("\n==================================================");
        System.out.println("                OFFICIAL REPORT CARD              ");
        System.out.println("==================================================");
        System.out.printf(" Student Name : %s%n", studentName);
        System.out.printf(" Roll Number  : %s%n", rollNumber);
        System.out.println("--------------------------------------------------");
        System.out.printf("%-20s %-10s %-10s%n", "Subject", "Marks", "Status");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < numSubjects; i++) {
            System.out.printf("%-20s %-10.2f %-10s%n",
                    subjectNames[i], marks[i], (marks[i] >= 40 ? "PASS" : "FAIL"));
        }
        System.out.println("--------------------------------------------------");
        System.out.printf("Total Marks   : %.2f / %.2f%n", totalMarks, maxPossibleMarks);
        System.out.printf("Percentage    : %.2f%%%n", percentage);
        System.out.printf("GPA (10 Scale): %.2f%n", gpa);
        System.out.printf("Final Grade   : %s (%s)%n", letterGrade, performance);
        System.out.println("==================================================");

        scanner.close();
    }
}
