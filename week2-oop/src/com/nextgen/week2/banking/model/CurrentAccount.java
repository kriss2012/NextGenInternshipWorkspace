package com.nextgen.week2.banking.model;

/**
 * Current/Checking Account supporting overdraft limits.
 */
public class CurrentAccount extends Account {
    private final double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountHolderName, double initialDeposit, double overdraftLimit) {
        super(accountNumber, accountHolderName, initialDeposit);
        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative.");
        }
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public synchronized void withdraw(double amount) throws IllegalStateException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0.");
        }
        // Total available funds = current balance + overdraft limit
        if (amount > (balance + overdraftLimit)) {
            throw new IllegalStateException(String.format(
                    "Transaction declined! Amount $%.2f exceeds available limit ($%.2f balance + $%.2f overdraft).",
                    amount, balance, overdraftLimit));
        }
        balance -= amount;
        recordTransaction(Transaction.Type.WITHDRAWAL, amount, "Current Account Withdrawal / Overdraft");
    }

    @Override
    public String getAccountType() {
        return "Current Account";
    }
}
