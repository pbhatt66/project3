package com.example.rubank;

/**
 * Account class represents an account as an object with a profile and balance,
 * and is the general type of the other account types.
 *
 * @author Pranay Bhatt and Fiona Wang
 */

public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;

    /**
     * Default Account constructor;
     * constructs an Account object with the given holder profile and balance.
     * @param holder the holder's profile
     * @param balance the balance
     */
    public Account(Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    /**
     * Constuctor used when closing an account;
     * Constructs an Account object with the given account holder profile.
     * @param holder the holder's profile
     */
    public Account(Profile holder) {
        this.holder = holder;
    }

    /**
     * The following functions are polymorphic within Account-inherited class objects.
     */
    public abstract double monthlyInterest();
    public abstract double monthlyFee();
    public abstract String getAccountType();

    /**
     * Gets the holder profile.
     * @return the profile
     */
    public Profile getHolder() {
        return holder;
    }

    /**
     * A void function that withdraws money from the balance.
     * @param account the account
     */
    public void withdrawMoney(Account account) {
        balance -= account.balance;
    }

    /**
     * Compares this account to another account. Order of significance: AccountType, Profile.
     * @param account the object to be compared.
     * @return a negative integer if this account is before the other account, a positive integer if this account is after the other account, and 0 if the accounts are equal.
     */
    @Override
    public int compareTo(Account account) {
        if (this.getAccountType().compareTo(account.getAccountType()) == 0) {
            return this.holder.compareTo(account.holder);
        }
        return this.getAccountType().compareTo(account.getAccountType());
    }

    /**
     * Returns a string representation of the account.
     * @return a string representation of the account
     * The function is polymorphic within Account-inherited class objects.
     */
    @Override
    public abstract String toString();
}