package com.example.rubank;

/**
 * CollegeChecking class inherits from Checking class and represents a college checking account
 * with a profile, balance, and campus.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class CollegeChecking extends Checking {
    private Campus campus;
    private static final double MONTHLY_FEE = 0.0;
    private static final double INTEREST_RATE = 0.01;
    private static final double NUM_OF_MONTHS = 12.0;

    /**
     * Default CollegeChecking constructor;
     * constructs a CollegeChecking object with the given profile, balance, and campus code.
     * @param holder the profile
     * @param balance the balance
     * @param campus the campus
     */
    public CollegeChecking(Profile holder, double balance, Campus campus) {
        super(holder, balance);
        this.campus = campus;
    }

    /**
     * Constructor used to deposit into college checking account;
     * constructs a CollegeChecking object with the given profile and balance.
     * @param holder the profile
     * @param balance the balance
     */
    public CollegeChecking(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     * Constructor used when closing a college checking account;
     * constructs a CollegeChecking object with the given profile.
     * @param holder the profile
     */
    public CollegeChecking(Profile holder) {
        super(holder);
    }

    /**
     * Returns the account type, college checking.
     * @return the string "College Checking"
     */
    @Override
    public String getAccountType() {
        return "College Checking";
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
     * (No monthly fees for College Checking accounts)
     * @return monthly fee
     */
    @Override
    public double monthlyFee() {
        return MONTHLY_FEE;
    }

    /**
     * Returns a string representation of college checking account.
     * @return a string representation of college checking account
     */
    @Override
    public String toString() {
        return getAccountType() + "::" + holder.toString() + "::Balance $" + String.format("%.2f", balance) + "::" + campus;
    }


}
