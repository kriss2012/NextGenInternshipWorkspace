package com.nextgen.week3.employee.service;

import java.io.IOException;

/**
 * Background Daemon Thread that periodically flushes unsaved employee records to disk.
 * Demonstrates Java Multithreading & Daemon threads in real-world applications.
 */
public class AutoSaveDaemon implements Runnable {
    private final EmployeeService service;
    private final int intervalSeconds;
    private volatile boolean running = true;

    public AutoSaveDaemon(EmployeeService service, int intervalSeconds) {
        this.service = service;
        this.intervalSeconds = intervalSeconds;
    }

    public void stopDaemon() {
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(intervalSeconds * 1000L);
                if (service.isDirty()) {
                    service.persistNow();
                    System.out.println("\n[AutoSave-Thread] Changes auto-flushed to CSV storage successfully.");
                    System.out.print("Select an option (1-8): ");
                }
            } catch (InterruptedException e) {
                // Thread interrupted on shutdown
                break;
            } catch (IOException e) {
                System.err.println("[AutoSave-Thread Error]: " + e.getMessage());
            }
        }
    }
}
