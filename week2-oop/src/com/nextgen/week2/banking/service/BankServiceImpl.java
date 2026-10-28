package com.nextgen.week2.banking.service;

import com.nextgen.week2.banking.model.Account;
import com.nextgen.week2.banking.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe implementation of BankService.
 */
public class BankServiceImpl implements BankService {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public void createAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        if (accounts.containsKey(account.getAccountNumber())) {
            throw new IllegalArgumentException("Account with number " + account.getAccountNumber() + " already exists!");
        }
        accounts.put(account.getAccountNumber(), account);
    }

    @Override
    public Account findAccount(String accountNumber) {
        Account acc = accounts.get(accountNumber);
        if (acc == null) {
            throw new IllegalArgumentException("Account not found with number: " + accountNumber);
        }
        return acc;
    }

    @Override
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public void deposit(String accountNumber, double amount) {
        Account acc = findAccount(accountNumber);
        acc.deposit(amount);
    }

    @Override
    public void withdraw(String accountNumber, double amount) {
        Account acc = findAccount(accountNumber);
        acc.withdraw(amount);
    }

    @Override
    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Sender and recipient account cannot be the same!");
        }
        Account fromAcc = findAccount(fromAccountNumber);
        Account toAcc = findAccount(toAccountNumber);

        // Atomic lock ordering to avoid deadlocks
        Account firstLock = fromAcc.getAccountNumber().compareTo(toAcc.getAccountNumber()) < 0 ? fromAcc : toAcc;
        Account secondLock = firstLock == fromAcc ? toAcc : fromAcc;

        synchronized (firstLock) {
            synchronized (secondLock) {
                fromAcc.withdraw(amount);
                toAcc.deposit(amount);
            }
        }
    }

    @Override
    public void printStatement(String accountNumber) {
        Account acc = findAccount(accountNumber);
        System.out.println("==================================================");
        System.out.println("            OFFICIAL ACCOUNT STATEMENT            ");
        System.out.println("==================================================");
        System.out.printf("Account No   : %s%n", acc.getAccountNumber());
        System.out.printf("Holder Name  : %s%n", acc.getAccountHolderName());
        System.out.printf("Account Type : %s%n", acc.getAccountType());
        System.out.printf("Current Balance: $%.2f%n", acc.getBalance());
        System.out.println("--------------------------------------------------");
        System.out.println("Transaction History:");
        List<Transaction> txs = acc.getTransactionHistory();
        if (txs.isEmpty()) {
            System.out.println("  (No transactions recorded yet)");
        } else {
            for (Transaction tx : txs) {
                System.out.println("  " + tx);
            }
        }
        System.out.println("==================================================");
    }
}
