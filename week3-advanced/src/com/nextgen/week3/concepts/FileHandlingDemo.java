package com.nextgen.week3.concepts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Week 3 Concept: File Handling
 * Next Gen Software Hub Pvt. Ltd. - Internship Task
 *
 * Demonstrates:
 * - Traditional File, FileReader, FileWriter, BufferedReader, BufferedWriter
 * - Modern Java NIO (Files, Path)
 * - Creating directories, writing strings, reading lines, checking existence
 */
public class FileHandlingDemo {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("       WEEK 3: FILE HANDLING DEMONSTRATION        ");
        System.out.println("==================================================");

        Path testDir = Paths.get("week3-advanced", "data");
        Path testFile = testDir.resolve("demo_log.txt");

        try {
            // 1. Create directory if not existing
            if (!Files.exists(testDir)) {
                Files.createDirectories(testDir);
                System.out.println("Created directory: " + testDir.toAbsolutePath());
            }

            // 2. Writing using BufferedWriter
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile.toFile()))) {
                writer.write("Entry 1: Next Gen Software Hub Internship Started\n");
                writer.write("Entry 2: Java Advanced Concepts Session - File I/O\n");
                writer.write("Entry 3: Persistent storage demonstration successful\n");
            }
            System.out.println("Successfully written 3 records to: " + testFile.getFileName());

            // 3. Reading using BufferedReader
            System.out.println("\n--- Reading file using BufferedReader ---");
            try (BufferedReader reader = new BufferedReader(new FileReader(testFile.toFile()))) {
                String line;
                int lineNum = 1;
                while ((line = reader.readLine()) != null) {
                    System.out.printf("[%d] %s%n", lineNum++, line);
                }
            }

            // 4. Reading all lines at once using modern Java NIO (Files.readAllLines)
            System.out.println("\n--- Reading file using modern Java NIO ---");
            List<String> allLines = Files.readAllLines(testFile);
            System.out.println("Total lines read via NIO: " + allLines.size());
            System.out.println("File size: " + Files.size(testFile) + " bytes");

        } catch (IOException e) {
            System.err.println("File I/O error occurred: " + e.getMessage());
        }
    }
}
