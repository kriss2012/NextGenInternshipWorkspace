package com.nextgen.week1;

/**
 * Week 1: Introduction to Java Basics
 * Next Gen Software Hub Pvt. Ltd. - Internship Task
 *
 * Demonstrates:
 * - Basic Java Program Structure
 * - Main method signature
 * - System.out printing
 * - JVM execution flow
 */
public class HelloJava {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" Welcome to Next Gen Software Hub Java Internship ");
        System.out.println(" Week 1: Introduction to Java Basics");
        System.out.println("==================================================");
        System.out.println("Java Environment initialized successfully!");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Java Vendor : " + System.getProperty("java.vendor"));
        System.out.println("OS Name     : " + System.getProperty("os.name"));
        System.out.println("==================================================");
    }
}
