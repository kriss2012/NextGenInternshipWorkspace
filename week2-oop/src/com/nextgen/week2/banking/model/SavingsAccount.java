package com.nextgen.week2.banking.model;

/**
 * Savings Account enforcing minimum balance and offering interest accrual.
 */
public class SavingsAccount extends Account {
    private final double minimumBalance;
    private final double annualInterestRate; // e.g. 4.5%

    public SavingsAccount(String accountNumber, String accountHolderName, double initialDeposit,
                          double minimumBalance, double annualInterestRate) {
        super(accountNumber, accountHolderName, initialDeposit);
        if (initialDeposit < minimumBalance) {
            throw new IllegalArgumentException("Initial deposit cannot be less than minimum balance: $" + minimumBalance);
        }
        this.minimumBalance = minimumBalance;
        this.annualInterestRate = annualInterestRate;
    }

    public double getMinimumBalance() { return minimumBalance; }
    public double getAnnualInterestRate() { return annualInterestRate; }

    @Override
    public synchronized void withdraw(double amount) throws IllegalStateException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0.");
        }
        if ((balance - amount) < minimumBalance) {
            throw new IllegalStateException(String.format(
                    "Insufficient funds! Minimum balance of $%.2f must be maintained. Current balance: $%.2f",
                    minimumBalance, balance));
        }
        balance -= amount;
        recordTransaction(Transaction.Type.WITHDRAWAL, amount, "Savings Account Cash Withdrawal");
    }

    public synchronized void applyMonthlyInterest() {
        double monthlyRate = (annualInterestRate / 100.0) / 12.0;
        double interest = balance * monthlyRate;
        balance += interest;
        recordTransaction(Transaction.Type.INTEREST, interest,
                String.format("Monthly Interest credited at %.2f%% p.a.", annualInterestRate));
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }
}
