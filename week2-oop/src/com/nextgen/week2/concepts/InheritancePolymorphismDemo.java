package com.nextgen.week2.concepts;

import java.util.ArrayList;
import java.util.List;

/**
 * Week 2 Concept: Inheritance and Polymorphism
 * Next Gen Software Hub Pvt. Ltd. - Internship Task
 *
 * Demonstrates:
 * - Base class & Derived classes (`extends`, `super`)
 * - Compile-time Polymorphism (Method Overloading)
 * - Runtime Polymorphism (Method Overriding & Dynamic Dispatch)
 */
public class InheritancePolymorphismDemo {

    // Base Class
    public static class Employee {
        protected String id;
        protected String name;
        protected double baseSalary;

        public Employee(String id, String name, double baseSalary) {
            this.id = id;
            this.name = name;
            this.baseSalary = baseSalary;
        }

        // Method to be overridden (Runtime Polymorphism)
        public double calculateAnnualCompensation() {
            return baseSalary * 12.0;
        }

        // Method Overloading (Compile-time Polymorphism)
        public void displayDetails() {
            displayDetails(false);
        }

        public void displayDetails(boolean includeTaxBracket) {
            System.out.printf("[%s] %s | Base Salary: $%.2f | Annual: $%.2f",
                    id, name, baseSalary, calculateAnnualCompensation());
            if (includeTaxBracket) {
                String bracket = calculateAnnualCompensation() > 80000 ? "30%" : "20%";
                System.out.printf(" | Tax: %s", bracket);
            }
            System.out.println();
        }
    }

    // Derived Class 1: Manager
    public static class Manager extends Employee {
        private double bonusPercentage;

        public Manager(String id, String name, double baseSalary, double bonusPercentage) {
            super(id, name, baseSalary); // invoke super constructor
            this.bonusPercentage = bonusPercentage;
        }

        @Override
        public double calculateAnnualCompensation() {
            double annualBase = super.calculateAnnualCompensation();
            return annualBase + (annualBase * (bonusPercentage / 100.0));
        }
    }

    // Derived Class 2: Developer
    public static class Developer extends Employee {
        private int completedProjects;

        public Developer(String id, String name, double baseSalary, int completedProjects) {
            super(id, name, baseSalary);
            this.completedProjects = completedProjects;
        }

        @Override
        public double calculateAnnualCompensation() {
            double incentivePerProject = 1500.0;
            return super.calculateAnnualCompensation() + (completedProjects * incentivePerProject);
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  WEEK 2: INHERITANCE & POLYMORPHISM DEMONSTRATION");
        System.out.println("==================================================");

        List<Employee> staff = new ArrayList<>();
        staff.add(new Employee("EMP-001", "Priya Nair", 4000.0));
        staff.add(new Manager("MGR-101", "Rahul Verma", 7500.0, 15.0)); // 15% bonus
        staff.add(new Developer("DEV-201", "Ananya Sen", 5500.0, 4));    // 4 projects

        System.out.println("--- Dynamic Method Dispatch in action ---");
        for (Employee emp : staff) {
            // Polymorphic call: exact calculateAnnualCompensation is chosen at runtime!
            emp.displayDetails(true);
        }

        System.out.println("\n--- Compile-Time Overloading in action ---");
        staff.get(0).displayDetails(); // calls 0-arg overload
        staff.get(0).displayDetails(true); // calls 1-arg overload
    }
}
