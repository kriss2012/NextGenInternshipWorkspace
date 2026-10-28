package com.nextgen.week2.concepts;

import java.util.ArrayList;
import java.util.List;

/**
 * Week 2 Concept: Abstraction and Interfaces
 * Next Gen Software Hub Pvt. Ltd. - Internship Task
 *
 * Demonstrates:
 * - Abstract classes (`abstract` methods vs concrete methods)
 * - Interfaces and contract fulfillment
 * - Multiple interface implementation
 * - Default interface methods
 */
public class AbstractionInterfaceDemo {

    // Interface 1
    public interface Payable {
        double getPaymentAmount();

        // Default method (Java 8+)
        default void logPayment() {
            System.out.printf("[Audit] Processing payment of $%.2f%n", getPaymentAmount());
        }
    }

    // Interface 2
    public interface Trackable {
        String getTrackingId();
    }

    // Abstract Class
    public static abstract class Vehicle {
        protected String registrationNumber;
        protected String model;

        public Vehicle(String registrationNumber, String model) {
            this.registrationNumber = registrationNumber;
            this.model = model;
        }

        // Concrete method shared by all subclasses
        public void displayInfo() {
            System.out.printf("Vehicle Model: %s | Reg: %s%n", model, registrationNumber);
        }

        // Abstract method: each vehicle must define how it calculates fuel efficiency
        public abstract double calculateFuelEfficiency(); // km per liter
    }

    // Concrete implementation implementing abstract class and multiple interfaces
    public static class DeliveryVan extends Vehicle implements Payable, Trackable {
        private double fuelLitersUsed;
        private double distanceCoveredKm;
        private double driverHourlyRate;
        private double hoursWorked;

        public DeliveryVan(String reg, String model, double fuelLitersUsed, double distanceCoveredKm,
                           double driverHourlyRate, double hoursWorked) {
            super(reg, model);
            this.fuelLitersUsed = fuelLitersUsed;
            this.distanceCoveredKm = distanceCoveredKm;
            this.driverHourlyRate = driverHourlyRate;
            this.hoursWorked = hoursWorked;
        }

        @Override
        public double calculateFuelEfficiency() {
            return distanceCoveredKm / (fuelLitersUsed == 0 ? 1 : fuelLitersUsed);
        }

        @Override
        public double getPaymentAmount() {
            return driverHourlyRate * hoursWorked;
        }

        @Override
        public String getTrackingId() {
            return "TRACK-VAN-" + registrationNumber;
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   WEEK 2: ABSTRACTION & INTERFACES DEMONSTRATION ");
        System.out.println("==================================================");

        DeliveryVan van = new DeliveryVan("MH-12-AB-1234", "Ford Transit", 45.0, 495.0, 25.0, 8.0);

        // Abstraction through Vehicle
        van.displayInfo();
        System.out.printf("Fuel Efficiency: %.2f km/l%n", van.calculateFuelEfficiency());

        // Polymorphism through Trackable interface
        Trackable trackable = van;
        System.out.println("Tracking ID: " + trackable.getTrackingId());

        // Polymorphism through Payable interface
        Payable payable = van;
        payable.logPayment();
    }
}
