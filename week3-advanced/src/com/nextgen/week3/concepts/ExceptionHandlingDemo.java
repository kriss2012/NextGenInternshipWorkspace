package com.nextgen.week3.concepts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Week 3 Concept: Exception Handling
 * Next Gen Software Hub Pvt. Ltd. - Internship Task
 *
 * Demonstrates:
 * - Checked vs Unchecked Exceptions
 * - try - catch - finally hierarchy
 * - try-with-resources (AutoCloseable)
 * - Custom Exceptions with error metadata
 */
public class ExceptionHandlingDemo {

    // Custom Checked Exception
    public static class InsufficientInventoryException extends Exception {
        private final String productId;
        private final int requested;
        private final int available;

        public InsufficientInventoryException(String productId, int requested, int available) {
            super(String.format("Product '%s': Requested %d units but only %d in stock", productId, requested, available));
            this.productId = productId;
            this.requested = requested;
            this.available = available;
        }

        public String getProductId() { return productId; }
        public int getRequested() { return requested; }
        public int getAvailable() { return available; }
    }

    public static void processOrder(String productId, int quantity, int inStock) throws InsufficientInventoryException {
        System.out.printf("Attempting order: %s, quantity: %d...%n", productId, quantity);
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero!"); // Unchecked
        }
        if (quantity > inStock) {
            throw new InsufficientInventoryException(productId, quantity, inStock); // Checked
        }
        System.out.println("Order processed successfully!");
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     WEEK 3: EXCEPTION HANDLING DEMONSTRATION     ");
        System.out.println("==================================================");

        // 1. Handling Unchecked Exception (IllegalArgumentException)
        try {
            processOrder("LAPTOP-01", -5, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("[Unchecked Exception Caught]: " + e.getMessage());
        }

        // 2. Handling Checked Custom Exception
        try {
            processOrder("PHONE-99", 50, 12);
        } catch (InsufficientInventoryException e) {
            System.out.printf("[Checked Exception Caught]: %s%n", e.getMessage());
            System.out.printf("  Detail -> Product: %s | Shortage: %d units%n",
                    e.getProductId(), (e.getRequested() - e.getAvailable()));
        }

        // 3. Try-with-resources demonstrating automatic cleanup
        System.out.println("\nTesting try-with-resources with missing file:");
        try (BufferedReader reader = new BufferedReader(new FileReader("non_existent_file.txt"))) {
            System.out.println(reader.readLine());
        } catch (IOException e) {
            System.out.println("[IOException Caught]: Resource safely handled: " + e.getMessage());
        } finally {
            System.out.println("[Finally Block]: Always executes regardless of exceptions.");
        }
    }
}
