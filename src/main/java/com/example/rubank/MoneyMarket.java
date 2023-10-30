package com.example.rubank;

/**
 * MoneyMarket class inherits from Savings class and represents a money market account with a profile and balance.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class MoneyMarket extends Savings {
    private int withdrawal;
    private static final double MONTHLY_FEE = 25.0;
    private static final double LOYALTY_INTEREST_RATE = 0.0475;
    private static final double INTEREST_RATE = 0.045;
    private static final double MIN_BALANCE = 2000.0;
    private static final double NO_MONTHLY_FEE = 0.0;
    private static final int MAX_WITHDRAWAL = 3;
    private static final int NUM_OF_MONTHS = 12;

    /**
     * Default MoneyMarket constructor;
     * constructs a MoneyMarket object with the given profile and balance.
     * @param holder the profile
     * @param balance the balance
     */
    public MoneyMarket(Profile holder, double balance) {
        super(holder, balance);
        this.withdrawal = 0;
    }

    /**
     * Constructor used when closing a money market account;
     * constructs a MoneyMarket object with the given profile.
     * @param holder the profile
     */
    public MoneyMarket(Profile holder) {
        super(holder);
    }

    /**
     * Money Market by default has loyal customer status unless account balance drops below $2000
     * @return loyalty status: if balance is greater than $2000
     */
    public boolean isLoyal() {
        return balance >= MIN_BALANCE;
    }

    /**
     * Returns the account type, money market.
     * @return the string "Money Market"
     */
    @Override
    public String getAccountType() {
        return "Money Market";
    }

    /**
     * Subtracts withdrawal from balance and keeps track of total withdrawals made in each account.
     * ($10 fee applies if the number of withdrawals >3 times)
     * @param account the account
     */
    @Override
    public void withdrawMoney(Account account) {
        balance -= account.balance;
        withdrawal++;
    }

    /**
     * Resets the number of withdrawals made in each account to 0.
     */
    public void resetWithdrawals() {
        withdrawal = 0;
    }

    /**
     * Calculates and returns the monthly interest.
     * (Money Market with loyal customer status get 4.75% annual interest rate)
     * @return monthly interest
     */
    @Override
    public double monthlyInterest() {
        if (isLoyal()) return (balance * LOYALTY_INTEREST_RATE) / NUM_OF_MONTHS;
        return (balance * INTEREST_RATE) / NUM_OF_MONTHS;
    }

    /**
     * Calculates and returns the monthly fee.
     * (No monthly fee if account balance >= $2000)
     * @return monthly fee
     */
    @Override
    public double monthlyFee() {
        if (balance >= 2000) {
            if (withdrawal < MAX_WITHDRAWAL)
                return NO_MONTHLY_FEE;
            else return 10.0;
        }
        else
            if (withdrawal < MAX_WITHDRAWAL)
                return MONTHLY_FEE;
            return MONTHLY_FEE + 10.0;
    }

    /**
     * Returns a string representation of money market account.
     * @return a string representation of money market account
     */
    @Override
    public String toString() {
        if (isLoyal()) return "Money Market::Savings::" + holder.toString() + "::Balance $" + String.format("%.2f", balance) + "::is loyal::withdrawal: " + withdrawal;
        return "Money Market::Savings::" + holder.toString() + "::Balance $" + String.format("%.2f", balance) + "::withdrawal: " + withdrawal;
    }
}
