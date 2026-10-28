package com.nextgen.week3.employee.storage;

import com.nextgen.week3.employee.model.Employee;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * File persistence manager for the Employee Management System.
 * Stores data in CSV format with automatic header handling.
 */
public class EmployeeStorage {
    private static final String CSV_HEADER = "id,name,department,designation,salary,email";
    private final Path storageFile;

    public EmployeeStorage(String filePath) {
        this.storageFile = Paths.get(filePath);
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            Path parent = storageFile.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(storageFile)) {
                Files.createFile(storageFile);
                try (BufferedWriter writer = Files.newBufferedWriter(storageFile)) {
                    writer.write(CSV_HEADER);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize storage file: " + e.getMessage());
        }
    }

    public synchronized List<Employee> loadAll() throws IOException {
        List<Employee> list = new ArrayList<>();
        if (!Files.exists(storageFile)) {
            return list;
        }

        try (BufferedReader reader = Files.newBufferedReader(storageFile)) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                try {
                    list.add(Employee.fromCsvLine(line));
                } catch (Exception e) {
                    System.err.println("Skipping malformed row: " + line);
                }
            }
        }
        return list;
    }

    public synchronized void saveAll(Collection<Employee> employees) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(storageFile)) {
            writer.write(CSV_HEADER);
            writer.newLine();
            for (Employee emp : employees) {
                writer.write(emp.toCsvLine());
                writer.newLine();
            }
        }
    }

    public Path getStoragePath() {
        return storageFile.toAbsolutePath();
    }
}
