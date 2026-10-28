package com.nextgen.week3.employee.service;

import com.nextgen.week3.employee.model.Employee;
import com.nextgen.week3.employee.storage.EmployeeStorage;
import com.nextgen.week3.exceptions.EmployeeNotFoundException;
import com.nextgen.week3.exceptions.InvalidInputException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Business Service managing employee records in-memory and syncing with file storage.
 */
public class EmployeeService {
    private final Map<String, Employee> employeeMap = new ConcurrentHashMap<>();
    private final EmployeeStorage storage;
    private volatile boolean isDirty = false;

    public EmployeeService(EmployeeStorage storage) {
        this.storage = storage;
        loadFromDisk();
    }

    private void loadFromDisk() {
        try {
            List<Employee> list = storage.loadAll();
            for (Employee emp : list) {
                employeeMap.put(emp.getId(), emp);
            }
        } catch (IOException e) {
            System.err.println("Could not load employees: " + e.getMessage());
        }
    }

    public synchronized void addEmployee(Employee emp) throws InvalidInputException {
        validateEmployee(emp);
        if (employeeMap.containsKey(emp.getId())) {
            throw new InvalidInputException("Employee with ID '" + emp.getId() + "' already exists!");
        }
        employeeMap.put(emp.getId(), emp);
        isDirty = true;
    }

    public synchronized void updateEmployee(Employee emp) throws EmployeeNotFoundException, InvalidInputException {
        if (!employeeMap.containsKey(emp.getId())) {
            throw new EmployeeNotFoundException(emp.getId());
        }
        validateEmployee(emp);
        employeeMap.put(emp.getId(), emp);
        isDirty = true;
    }

    public synchronized void deleteEmployee(String id) throws EmployeeNotFoundException {
        if (!employeeMap.containsKey(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeMap.remove(id);
        isDirty = true;
    }

    public Employee getEmployee(String id) throws EmployeeNotFoundException {
        Employee emp = employeeMap.get(id);
        if (emp == null) {
            throw new EmployeeNotFoundException(id);
        }
        return emp;
    }

    public List<Employee> getAllEmployees() {
        return employeeMap.values().stream()
                .sorted(Comparator.comparing(Employee::getId))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesByDepartment(String dept) {
        return employeeMap.values().stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(dept))
                .collect(Collectors.toList());
    }

    public synchronized void persistNow() throws IOException {
        storage.saveAll(employeeMap.values());
        isDirty = false;
    }

    public boolean isDirty() {
        return isDirty;
    }

    private void validateEmployee(Employee emp) throws InvalidInputException {
        if (emp.getId() == null || emp.getId().trim().isEmpty()) {
            throw new InvalidInputException("Employee ID cannot be empty.");
        }
        if (emp.getName() == null || emp.getName().trim().length() < 2) {
            throw new InvalidInputException("Employee Name must be at least 2 characters.");
        }
        if (emp.getSalary() < 0) {
            throw new InvalidInputException("Salary cannot be negative.");
        }
        if (emp.getEmail() == null || !emp.getEmail().contains("@")) {
            throw new InvalidInputException("Invalid email format.");
        }
    }
}
