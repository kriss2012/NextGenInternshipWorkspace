package com.nextgen.week3.exceptions;

/**
 * Custom Exception thrown when an employee cannot be located by ID.
 */
public class EmployeeNotFoundException extends Exception {
    private final String employeeId;

    public EmployeeNotFoundException(String employeeId) {
        super("Employee not found with ID: " + employeeId);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
