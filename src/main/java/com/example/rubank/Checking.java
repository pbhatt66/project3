package com.example.rubank;

/**
 * Checking class inherits from Account class and represents a checking account with a profile and balance.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class Checking extends Account {
    private static final double MONTHLY_FEE = 12.0;
    private static final double INTEREST_RATE = 0.01;
    private static final double MIN_BALANCE = 1000.0;
    private static final double NO_MONTHLY_FEE = 0.0;
    private static final double NUM_OF_MONTHS = 12.0;

    /**
     * Default Checking constructor;
     * constructs a Checking object with the given profile and balance.
     * @param holder the profile
     * @param balance the balance
     */
    public Checking(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     * Constructor used when closing a checking account;
     * constructs a Checking object with the given profile.
     * @param holder the profile
     */
    public Checking(Profile holder) {
        super(holder);
    }

    /**
     * Returns the account type, checking.
     * @return the string "Checking"
     */
    @Override
    public String getAccountType() {
        return "Checking";
    }

    /**
     * Calculates and returns the monthly interest.
     * @return monthly interest
     */
    @Override
    public double monthlyInterest() {
        return (balance * INTEREST_RATE) / NUM_OF_MONTHS;
    }

    /**
     * Calculates and returns the monthly fee.
     * (Monthly fee is waived if account balance >= $1000)
     * @return monthly fee
     */
    @Override
    public double monthlyFee() {
        if (balance >= MIN_BALANCE){
            return NO_MONTHLY_FEE;
        }
        return MONTHLY_FEE;
    }

    /**
     * Returns a string representation of checking account.
     * @return a string representation of checking account
     */
    @Override
    public String toString() {
        return getAccountType() + "::" + holder.toString() + "::Balance $" + String.format("%.2f", balance);
    }

}
