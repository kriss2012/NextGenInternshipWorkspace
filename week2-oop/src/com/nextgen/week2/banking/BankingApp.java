package com.nextgen.week2.banking;

import com.nextgen.week2.banking.model.Account;
import com.nextgen.week2.banking.model.CurrentAccount;
import com.nextgen.week2.banking.model.SavingsAccount;
import com.nextgen.week2.banking.service.BankService;
import com.nextgen.week2.banking.service.BankServiceImpl;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive Banking System Console Application.
 * Week 2 Mini-Project for Next Gen Software Hub Pvt. Ltd.
 */
public class BankingApp {
    private static final BankService bankService = new BankServiceImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Pre-populate with sample accounts
        seedInitialAccounts();

        System.out.println("==================================================");
        System.out.println("            NEXT GEN DIGITAL BANK SYSTEM          ");
        System.out.println("       Week 2 Mini Project - OOP Architecture     ");
        System.out.println("==================================================");

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Select an option (1-7): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> openNewAccount();
                case "2" -> depositFunds();
                case "3" -> withdrawFunds();
                case "4" -> transferFunds();
                case "5" -> viewStatement();
                case "6" -> listAllAccounts();
                case "7" -> {
                    System.out.println("\nThank you for banking with Next Gen Software Hub Bank!");
                    running = false;
                }
                default -> System.out.println("Invalid selection! Please enter a number between 1 and 7.");
            }
        }
    }

    private static void seedInitialAccounts() {
        bankService.createAccount(new SavingsAccount("SA-1001", "Vikram Patel", 15000.0, 1000.0, 4.5));
        bankService.createAccount(new CurrentAccount("CA-2001", "Neha Sharma", 25000.0, 10000.0));
    }

    private static void printMenu() {
        System.out.println("\n---------------- MAIN MENU ----------------");
        System.out.println(" 1. Open New Account (Savings / Current)");
        System.out.println(" 2. Deposit Funds");
        System.out.println(" 3. Withdraw Funds");
        System.out.println(" 4. Transfer Funds");
        System.out.println(" 5. View Account Statement & History");
        System.out.println(" 6. List All Accounts");
        System.out.println(" 7. Exit System");
        System.out.println("-------------------------------------------");
    }

    private static void openNewAccount() {
        try {
            System.out.println("\n--- Open New Account ---");
            System.out.println("1. Savings Account (Min Balance $1,000, 4.5% interest)");
            System.out.println("2. Current Account ($10,000 Overdraft facility)");
            System.out.print("Choose account type (1 or 2): ");
            String type = scanner.nextLine().trim();

            System.out.print("Enter Unique Account Number (e.g. SA-1002): ");
            String accNum = scanner.nextLine().trim();

            System.out.print("Enter Account Holder Full Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Initial Deposit Amount: $");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            if ("1".equals(type)) {
                Account account = new SavingsAccount(accNum, name, amount, 1000.0, 4.5);
                bankService.createAccount(account);
                System.out.println("Savings Account successfully created for " + name);
            } else if ("2".equals(type)) {
                Account account = new CurrentAccount(accNum, name, amount, 10000.0);
                bankService.createAccount(account);
                System.out.println("Current Account successfully created for " + name);
            } else {
                System.out.println("Invalid account type chosen.");
            }
        } catch (Exception e) {
            System.out.println("Error opening account: " + e.getMessage());
        }
    }

    private static void depositFunds() {
        try {
            System.out.print("\nEnter Account Number: ");
            String accNum = scanner.nextLine().trim();
            System.out.print("Enter Amount to Deposit: $");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            bankService.deposit(accNum, amount);
            System.out.printf("Successfully deposited $%.2f. New Balance: $%.2f%n",
                    amount, bankService.findAccount(accNum).getBalance());
        } catch (Exception e) {
            System.out.println("Deposit Failed: " + e.getMessage());
        }
    }

    private static void withdrawFunds() {
        try {
            System.out.print("\nEnter Account Number: ");
            String accNum = scanner.nextLine().trim();
            System.out.print("Enter Amount to Withdraw: $");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            bankService.withdraw(accNum, amount);
            System.out.printf("Successfully withdrew $%.2f. New Balance: $%.2f%n",
                    amount, bankService.findAccount(accNum).getBalance());
        } catch (Exception e) {
            System.out.println("Withdrawal Failed: " + e.getMessage());
        }
    }

    private static void transferFunds() {
        try {
            System.out.print("\nEnter Sender Account Number: ");
            String fromAcc = scanner.nextLine().trim();
            System.out.print("Enter Beneficiary Account Number: ");
            String toAcc = scanner.nextLine().trim();
            System.out.print("Enter Amount to Transfer: $");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            bankService.transfer(fromAcc, toAcc, amount);
            System.out.printf("Successfully transferred $%.2f from %s to %s%n", amount, fromAcc, toAcc);
        } catch (Exception e) {
            System.out.println("Transfer Failed: " + e.getMessage());
        }
    }

    private static void viewStatement() {
        try {
            System.out.print("\nEnter Account Number: ");
            String accNum = scanner.nextLine().trim();
            bankService.printStatement(accNum);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listAllAccounts() {
        List<Account> list = bankService.getAllAccounts();
        System.out.println("\n---------------- ALL REGISTERED ACCOUNTS ----------------");
        if (list.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for (Account acc : list) {
                System.out.println(acc);
            }
        }
        System.out.println("---------------------------------------------------------");
    }
}
