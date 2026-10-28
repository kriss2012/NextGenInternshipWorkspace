package com.nextgen.week2.banking.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Abstract Base Class for all Bank Accounts.
 * Demonstrates Encapsulation & Abstraction.
 */
public abstract class Account {
    private final String accountNumber;
    private final String accountHolderName;
    protected double balance;
    private final List<Transaction> transactionHistory;

    public Account(String accountNumber, String accountHolderName, double initialDeposit) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty.");
        }
        if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be empty.");
        }
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative.");
        }

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();

        recordTransaction(Transaction.Type.DEPOSIT, initialDeposit, "Initial Account Opening Deposit");
    }

    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolderName() { return accountHolderName; }
    public double getBalance() { return balance; }

    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    protected void recordTransaction(Transaction.Type type, double amount, String description) {
        String txId = "TX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        transactionHistory.add(new Transaction(txId, type, amount, this.balance, description));
    }

    public synchronized void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
        recordTransaction(Transaction.Type.DEPOSIT, amount, "Cash/Check Deposit");
    }

    /**
     * Abstract withdrawal method: Subclasses enforce specific business rules (e.g., minimum balance or overdraft limits).
     */
    public abstract void withdraw(double amount) throws IllegalStateException;

    public abstract String getAccountType();

    @Override
    public String toString() {
        return String.format("[%s] #%s - %s | Balance: $%.2f",
                getAccountType(), accountNumber, accountHolderName, balance);
    }
}
