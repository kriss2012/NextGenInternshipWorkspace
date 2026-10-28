package com.nextgen.week3.employee.model;

/**
 * Employee entity with CSV serialization/deserialization methods.
 */
public class Employee {
    private String id;
    private String name;
    private String department;
    private String designation;
    private double salary;
    private String email;

    public Employee(String id, String name, String department, String designation, double salary, String email) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.salary = salary;
        this.email = email;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    /**
     * Converts employee record into a CSV row.
     */
    public String toCsvLine() {
        return String.format("%s,%s,%s,%s,%.2f,%s",
                escapeCsv(id), escapeCsv(name), escapeCsv(department),
                escapeCsv(designation), salary, escapeCsv(email));
    }

    /**
     * Reconstructs an Employee instance from a CSV line.
     */
    public static Employee fromCsvLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 6) {
            throw new IllegalArgumentException("Invalid CSV record: " + line);
        }
        return new Employee(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                Double.parseDouble(parts[4].trim()),
                parts[5].trim()
        );
    }

    private static String escapeCsv(String str) {
        return (str == null) ? "" : str.replace(",", " ");
    }

    @Override
    public String toString() {
        return String.format("[%s] %-18s | %-12s | %-15s | $%-10.2f | %s",
                id, name, department, designation, salary, email);
    }
}
