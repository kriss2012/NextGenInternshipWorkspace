package com.nextgen.week3.concepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Week 3 Concept: Multithreading Basics
 * Next Gen Software Hub Pvt. Ltd. - Internship Task
 *
 * Demonstrates:
 * - Creating threads via Thread subclass and Runnable interface
 * - Race conditions and synchronization
 * - Atomic variables (AtomicInteger)
 * - Thread pool execution with ExecutorService
 */
public class MultithreadingDemo {

    // Shared counter to demonstrate synchronization
    public static class Counter {
        private int count = 0;
        private final AtomicInteger atomicCount = new AtomicInteger(0);

        // Synchronized method prevents race conditions
        public synchronized void incrementSynchronized() {
            count++;
        }

        public void incrementAtomic() {
            atomicCount.incrementAndGet();
        }

        public int getCount() { return count; }
        public int getAtomicCount() { return atomicCount.get(); }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("==================================================");
        System.out.println("     WEEK 3: MULTITHREADING DEMONSTRATION         ");
        System.out.println("==================================================");

        // 1. Thread creation via Runnable lambda
        Thread workerThread = new Thread(() -> {
            System.out.printf("[%s] Background worker thread started...%n", Thread.currentThread().getName());
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.printf("[%s] Background worker thread finished work!%n", Thread.currentThread().getName());
        }, "CustomWorker-1");

        workerThread.start();
        workerThread.join(); // wait for completion

        // 2. Concurrency & Synchronization test
        System.out.println("\nTesting concurrency with 4 threads updating shared counter...");
        Counter counter = new Counter();
        ExecutorService pool = Executors.newFixedThreadPool(4);

        int tasksCount = 1000;
        for (int i = 0; i < tasksCount; i++) {
            pool.submit(() -> {
                counter.incrementSynchronized();
                counter.incrementAtomic();
            });
        }

        pool.shutdown();
        boolean finished = pool.awaitTermination(3, TimeUnit.SECONDS);

        System.out.printf("All tasks finished: %b%n", finished);
        System.out.printf("Expected Count     : %d%n", tasksCount);
        System.out.printf("Synchronized Count : %d (Thread-safe)%n", counter.getCount());
        System.out.printf("AtomicInteger Count: %d (Lock-free thread-safe)%n", counter.getAtomicCount());
    }
}
