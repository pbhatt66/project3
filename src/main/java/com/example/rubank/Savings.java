package com.example.rubank;

/**
 * Savings class inherits from Account class and represents a saving account
 * with a profile, balance, and loyalty status.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class Savings extends Account {
    protected boolean isLoyal;
    private static final double NO_MONTHLY_FEE = 0.0;
    private static final double MONTHLY_FEE = 25.0;
    private static final double INTEREST_RATE = 0.04;
    private static final double LOYALTY_INTEREST_RATE = 0.0425;
    private static final double MIN_BALANCE = 500.0;
    private static final double NUM_OF_MONTHS = 12.0;

    /**
     * Default Saving constructor;
     * constructs a Savings object with the given profile, balance, and loyalty status.
     * @param holder the profile
     * @param balance the balance
     * @param isLoyal the loyalty status: 1 if loyal customer, 0 if not
     */
    public Savings(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    /**
     * Constructor used to deposit into savings account;
     * constructs a Savings object with the given profile and balance.
     * @param holder the profile
     * @param balance the balance
     */
    public Savings(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     * Constructor used when closing a savings account;
     * constructs a Savings object with the given profile.
     * @param holder the profile
     */
    public Savings(Profile holder) {
        super(holder);
    }

    /**
     * Returns the account type, savings.
     * @return the string "Savings"
     */
    @Override
    public String getAccountType() {
        return "Savings";
    }

    /**
     * Calculates and returns the monthly interest.
     * (Savings with loyal customer status get 4.25% annual interest rate)
     * @return monthly interest
     */
    @Override
    public double monthlyInterest() {
        if(isLoyal) return (balance * LOYALTY_INTEREST_RATE) / NUM_OF_MONTHS;
        return (balance * INTEREST_RATE) / NUM_OF_MONTHS;
    }

    /**
     * Calculates and returns the monthly fee.
     * (No monthly fees for Savings with balance >= $500)
     * @return monthly fee
     */
    @Override
    public double monthlyFee() {
        if (balance >= MIN_BALANCE) return NO_MONTHLY_FEE;
        return MONTHLY_FEE;
    }

    /**
     * Returns a string representation of savings account.
     * @return a string representation of savings account
     */
    @Override
    public String toString() {
        if(isLoyal) return getAccountType() + "::" + holder.toString() + "::Balance $" + String.format("%.2f", balance) + "::is loyal";
        return getAccountType() + "::" + holder.toString() + "::Balance $" + String.format("%.2f", balance);
    }

}
