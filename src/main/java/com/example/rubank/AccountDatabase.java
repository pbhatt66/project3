package com.example.rubank;

/**
 * AccountDatabase class stores Account objects in an array,
 * and provides methods to open, close, withdraw/deposit from/to an account, and sort then print accounts.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class AccountDatabase {
    private Account[] accounts;
    private int numAcct;
    private static final int NOT_FOUND = -1;
    private static final int ARRAY_GROWTH_FACTOR = 4;

    /**
     * Default constructor: constructs an AccountDatabase object with an array of size 4.
     */
    public AccountDatabase() {
        this.accounts = new Account[4];
        this.numAcct = 0;
    }

    /**
     * Finds the index of the given account in the array.
     * @param account the account to find
     * @return the index of the account in the array, or NOT_FOUND if not found
     */
    private int find(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].getAccountType().equals(account.getAccountType()) && accounts[i].getHolder().equals(account.getHolder())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Grows the array by adding four spots.
     */
    private void grow() {
        Account[] newAccounts = new Account[accounts.length + ARRAY_GROWTH_FACTOR];
        for (int i = 0; i < accounts.length; i++) {
            newAccounts[i] = accounts[i];
        }
        accounts = newAccounts;
    }

    /**
     * Checks if the given account is in the array.
     * @param account the account to check
     * @return true if the account is in the array, false otherwise.
     */
    public boolean contains(Account account) {
        return find(account) != NOT_FOUND;
    }

    /**
     * Opens the given account and adds it to the array.
     * @param account the account to add
     * @return true if the account was opened, false if the account already exists in the array,
     * or if someone opens both a checking and college checking account
     */
    public boolean open(Account account) {
        if (contains(account)) {
            return false;
        }
        else if (account.getAccountType().equals("Checking")) {
            CollegeChecking collegeChecking = new CollegeChecking(account.holder, account.balance);
            if (contains(collegeChecking)) {
                return false;
            }
        }
        else if (account.getAccountType().equals("College Checking")) {
            Checking checking = new Checking(account.holder, account.balance);
            if (contains(checking)) {
                return false;
            }
        }
        if (numAcct == accounts.length) {
            grow();
        }
        accounts[numAcct++] = account;
        return true;
    }

    /**
     * Closes the given account and removes it from the array.
     * @param account the account to close
     * @return true if the account was closed, false if the account was not in the array
     */
    public boolean close(Account account) {
        int index = find(account);
        if (index == NOT_FOUND) {
            return false;
        }
        accounts[index] = accounts[--numAcct];
        accounts[numAcct] = null;
        return true;
    }

    /**
     * Withdraws money from the account.
     * @param account the account to withdraw from
     * @return true if the withdrawal was valid, false if the account was not in the array
     * or if the withdrawal is greater than the account balance
     */
    public boolean withdraw(Account account) {
        int index = find(account);
        if (index == NOT_FOUND) return false;
        if (accounts[index].balance < account.balance)
        {
            account.balance = accounts[index].balance;
            return false;
        }

        accounts[index].withdrawMoney(account);
        return true;
    }

    /**
     * Deposits money to the account.
     * @param account the account to deposit to
     * @return true if the deposit was valid, false if the account was not in the array
     */
    public boolean deposit(Account account) {
        int index = find(account);
        if (index == NOT_FOUND) {
            return false;
        }
        accounts[index].balance += account.balance;
        return true;
    }

    /**
     * Checks if the array is empty.
     * @return true if there are no accounts in the array, false otherwise.
     */
    public boolean isEmpty() {
        return numAcct == 0;
    }

    /**
     * Returns string output of all accounts in the database.
     * @return the number of accounts in the array
     */
    public String printSorted() {
        bubbleSort();
        String str = "";
        str += "\n*Accounts sorted by account type and profile.\n";
        for (int i = 0; i < numAcct; i++) {
            str += accounts[i].toString() + "\n";
        }
        str += "*end of list.\n";
        return str;
    }

    /**
     * Helper method for printSorted() that sorts the array by account type and profile using bubbleSort.
     */
    private void bubbleSort() {
        int n = numAcct;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (accounts[j].compareTo(accounts[j + 1]) > 0) {
                    Account temp = accounts[j];
                    accounts[j] = accounts[j + 1];
                    accounts[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    /**
     * Returns string output of all accounts in the database with interest and fees.
     * @return the number of accounts in the array
     */
    public String printFeesAndInterests() {
        bubbleSort();
        String str = "";
        str += "\n*list of accounts with fee and monthly interest\n";
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].monthlyFee() > 0 || accounts[i].monthlyInterest() > 0) {
                str += accounts[i].toString() + "::fee $" + String.format("%.2f", accounts[i].monthlyFee())
                        + "::monthly interest $" + String.format("%.2f", accounts[i].monthlyInterest()) + "\n";
            }
        }
        str += "*end of list.\n";
        return str;
    }

    /**
     * Returns string output of all accounts in the database with updated balances.
     * @return the number of accounts in the array
     */
    public String printUpdatedBalances() {
        bubbleSort();
        String str = "";
        str += "\n*list of accounts with fees and interests applied.\n";
        for (int i = 0; i < numAcct; i++) {
            accounts[i].balance += accounts[i].monthlyInterest() - accounts[i].monthlyFee();
            if (accounts[i].getAccountType().equals("Money Market")) {
                MoneyMarket moneyMarket = (MoneyMarket) accounts[i];
                moneyMarket.resetWithdrawals();
            }
            str += accounts[i].toString() + "\n";
        }
        str += "*end of list.\n";
        return str;
    }
}
