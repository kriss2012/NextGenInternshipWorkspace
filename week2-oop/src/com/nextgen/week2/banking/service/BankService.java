package com.nextgen.week2.banking.service;

import com.nextgen.week2.banking.model.Account;
import java.util.List;

/**
 * Business Service Interface for the Banking System.
 */
public interface BankService {
    void createAccount(Account account);
    Account findAccount(String accountNumber);
    List<Account> getAllAccounts();
    void deposit(String accountNumber, double amount);
    void withdraw(String accountNumber, double amount);
    void transfer(String fromAccountNumber, String toAccountNumber, double amount);
    void printStatement(String accountNumber);
}
