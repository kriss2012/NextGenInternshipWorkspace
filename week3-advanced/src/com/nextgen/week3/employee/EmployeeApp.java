package com.nextgen.week3.employee;

import com.nextgen.week3.employee.model.Employee;
import com.nextgen.week3.employee.service.AutoSaveDaemon;
import com.nextgen.week3.employee.service.EmployeeService;
import com.nextgen.week3.employee.storage.EmployeeStorage;
import com.nextgen.week3.exceptions.EmployeeNotFoundException;
import com.nextgen.week3.exceptions.InvalidInputException;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive Console Application: Employee Management System
 * Week 3 Project for Next Gen Software Hub Pvt. Ltd.
 */
public class EmployeeApp {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String dataFilePath = "week3-advanced/data/employees.csv";
        EmployeeStorage storage = new EmployeeStorage(dataFilePath);
        EmployeeService service = new EmployeeService(storage);

        // Seed sample employees if empty
        if (service.getAllEmployees().isEmpty()) {
            try {
                service.addEmployee(new Employee("EMP-101", "Aarav Sharma", "Engineering", "Software Engineer", 65000.0, "aarav@nextgenhub.in"));
                service.addEmployee(new Employee("EMP-102", "Pooja Mehta", "Human Resources", "HR Manager", 72000.0, "pooja@nextgenhub.in"));
                service.addEmployee(new Employee("EMP-103", "Rohan Iyer", "Engineering", "Tech Lead", 95000.0, "rohan@nextgenhub.in"));
                service.persistNow();
            } catch (Exception ignored) {}
        }

        // Start background AutoSave Daemon Thread
        AutoSaveDaemon autoSaver = new AutoSaveDaemon(service, 5);
        Thread daemonThread = new Thread(autoSaver, "AutoSave-Daemon");
        daemonThread.setDaemon(true);
        daemonThread.start();

        System.out.println("==================================================");
        System.out.println("          EMPLOYEE MANAGEMENT SYSTEM              ");
        System.out.println("   Week 3: Exceptions, File I/O & Multithreading   ");
        System.out.println("==================================================");
        System.out.println("Data Storage: " + storage.getStoragePath());
        System.out.println("AutoSave Daemon active every 5 seconds.");

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Select an option (1-8): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> listAllEmployees(service);
                case "2" -> searchEmployee(service);
                case "3" -> addEmployee(service);
                case "4" -> updateEmployee(service);
                case "5" -> deleteEmployee(service);
                case "6" -> filterByDepartment(service);
                case "7" -> {
                    try {
                        service.persistNow();
                        System.out.println("Data saved to CSV file successfully.");
                    } catch (Exception e) {
                        System.out.println("Save failed: " + e.getMessage());
                    }
                }
                case "8" -> {
                    try {
                        service.persistNow();
                    } catch (Exception ignored) {}
                    autoSaver.stopDaemon();
                    System.out.println("Saved and exiting. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid selection! Please enter 1-8.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n---------------- MAIN MENU ----------------");
        System.out.println(" 1. View All Employees");
        System.out.println(" 2. Search Employee by ID");
        System.out.println(" 3. Add New Employee");
        System.out.println(" 4. Update Employee");
        System.out.println(" 5. Delete Employee");
        System.out.println(" 6. Filter by Department");
        System.out.println(" 7. Force Save to CSV File");
        System.out.println(" 8. Exit System");
        System.out.println("-------------------------------------------");
    }

    private static void listAllEmployees(EmployeeService service) {
        List<Employee> list = service.getAllEmployees();
        System.out.println("\n---------------- ALL EMPLOYEES ----------------");
        if (list.isEmpty()) {
            System.out.println("No employees currently registered.");
        } else {
            System.out.printf("%-10s %-18s %-15s %-18s %-12s %s%n",
                    "ID", "Name", "Department", "Designation", "Salary", "Email");
            System.out.println("----------------------------------------------------------------------------------");
            for (Employee e : list) {
                System.out.printf("%-10s %-18s %-15s %-18s $%-11.2f %s%n",
                        e.getId(), e.getName(), e.getDepartment(), e.getDesignation(), e.getSalary(), e.getEmail());
            }
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

    private static void searchEmployee(EmployeeService service) {
        System.out.print("\nEnter Employee ID: ");
        String id = scanner.nextLine().trim();
        try {
            Employee emp = service.getEmployee(id);
            System.out.println("Found Employee:\n" + emp);
        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addEmployee(EmployeeService service) {
        try {
            System.out.println("\n--- Add New Employee ---");
            System.out.print("Enter ID (e.g. EMP-104): ");
            String id = scanner.nextLine().trim();

            System.out.print("Enter Full Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Department (e.g. Engineering, Sales, HR): ");
            String dept = scanner.nextLine().trim();

            System.out.print("Enter Designation: ");
            String desig = scanner.nextLine().trim();

            System.out.print("Enter Annual Salary: $");
            double salary = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter Email Address: ");
            String email = scanner.nextLine().trim();

            Employee emp = new Employee(id, name, dept, desig, salary, email);
            service.addEmployee(emp);
            System.out.println("Employee " + name + " added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Input error: Salary must be a valid number.");
        } catch (InvalidInputException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    private static void updateEmployee(EmployeeService service) {
        try {
            System.out.print("\nEnter ID of employee to update: ");
            String id = scanner.nextLine().trim();
            Employee existing = service.getEmployee(id);

            System.out.printf("Current Name [%s]: ", existing.getName());
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) existing.setName(name);

            System.out.printf("Current Department [%s]: ", existing.getDepartment());
            String dept = scanner.nextLine().trim();
            if (!dept.isEmpty()) existing.setDepartment(dept);

            System.out.printf("Current Designation [%s]: ", existing.getDesignation());
            String desig = scanner.nextLine().trim();
            if (!desig.isEmpty()) existing.setDesignation(desig);

            System.out.printf("Current Salary [$%.2f]: ", existing.getSalary());
            String salStr = scanner.nextLine().trim();
            if (!salStr.isEmpty()) existing.setSalary(Double.parseDouble(salStr));

            System.out.printf("Current Email [%s]: ", existing.getEmail());
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) existing.setEmail(email);

            service.updateEmployee(existing);
            System.out.println("Employee updated successfully!");
        } catch (EmployeeNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteEmployee(EmployeeService service) {
        System.out.print("\nEnter Employee ID to delete: ");
        String id = scanner.nextLine().trim();
        try {
            service.deleteEmployee(id);
            System.out.println("Employee " + id + " deleted successfully!");
        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void filterByDepartment(EmployeeService service) {
        System.out.print("\nEnter Department Name to filter: ");
        String dept = scanner.nextLine().trim();
        List<Employee> list = service.getEmployeesByDepartment(dept);
        System.out.printf("\nEmployees in '%s' (%d found):%n", dept, list.size());
        for (Employee e : list) {
            System.out.println("  " + e);
        }
    }
}
