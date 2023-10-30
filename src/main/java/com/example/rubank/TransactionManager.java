package com.example.rubank;

import java.util.Scanner;

/**
 * TransactionManager class creates an AccountDatabase object and processes user input.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class TransactionManager {
    private AccountDatabase database;
    private boolean quit = false;

    private static final String OPEN = "O";
    private static final String CLOSE = "C";
    private static final String WITHDRAW = "W";
    private static final String DEPOSIT = "D";
    private static final String PRINT = "P";
    private static final String PRINT_INTEREST_FEES = "PI";
    private static final String UPDATE_BALANCE = "UB";
    private static final String CHECKING = "C";
    private static final String COLLEGE_CHECKING = "CC";
    private static final String SAVINGS = "S";
    private static final String MONEY_MARKET = "MM";
    private static final String QUIT = "Q";

    /**
     * Default constructor: constructs a TransactionManager object with an AccountDatabase object.
     */
    public TransactionManager() {
        database = new AccountDatabase();
    }

    /**
     * Parses the date string and returns a Date object.
     * @param dateString the date string in the format MM/DD/YYYY
     * @return the Date object
     * @throws Exception if the date is invalid or a future date.
     */
    private Date parseDate(String dateString) throws Exception {
        String[] dateStrings = dateString.split("/");
        int month = Integer.parseInt(dateStrings[0]);
        int day = Integer.parseInt(dateStrings[1]);
        int year = Integer.parseInt(dateStrings[2]);

        Date date = new Date(year, month, day);
        if(!date.isValid()) throw new Exception("DOB invalid: " + dateString + " not a valid calendar date!");
        if(date.isFutureDate()) throw new Exception("DOB invalid: " + dateString + " cannot be today or a future day.");
        return date;
    }

    /**
     * Processes the open command to direct to open-account-type helper methods.
     * @param inputParts the input parameters for the open command to pass to helper methods
     */
    private void processOpenCommand(String[] inputParts) {
        try {
            switch (inputParts[1]) {
                case CHECKING -> openCheckingCommand(inputParts);
                case COLLEGE_CHECKING -> openCollegeCheckingCommand(inputParts);
                case SAVINGS -> openSavingsCommand(inputParts);
                case MONEY_MARKET -> openMoneyMarketCommand(inputParts);
                default -> System.out.println(inputParts[1] + " is an invalid account!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data for opening an account.");
        }
    }

    /**
     * Processes the open command for checking accounts.
     * @param inputParts the input parameters for the open command to create a Checking object
     */
    private void openCheckingCommand(String[] inputParts) {
        String firstName = inputParts[2];
        //convert firstname into camel case
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        String lastName = inputParts[3];
        //convert lastname into camel case
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        String dateString = inputParts[4];

        Date dob;
        try {
            dob = parseDate(dateString);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        if (!dob.isOver16()) {
            System.out.println("DOB Invalid: " + dateString + " under 16.");
            return;
        }

        Profile profile = new Profile(firstName, lastName, dob);
        double balance;
        try {
            balance = Double.parseDouble(inputParts[5]);
            if (balance <= 0) {
                System.out.println("Initial deposit cannot be 0 or negative.");
                return;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return;
        }
        // if (balance <= 0) {
        //     System.out.println("Initial deposit must be greater than 0!");
        //     return;
        // }
        Account account = new Checking(profile, balance);
        if (database.open(account)) System.out.println(firstName + " " + lastName + " " + dateString + "(C) opened.");
        else System.out.println(firstName + " " + lastName + " " + dateString + "(C) is already in the database.");
    }

    /**
     * Processes the open command for college checking accounts.
     * @param inputParts the input parameters for the open command to create a CollegeChecking object
     */
    private void openCollegeCheckingCommand(String[] inputParts) {
        String firstName = inputParts[2];
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        String lastName = inputParts[3];
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        String dobString = inputParts[4];

        Date dob;
        try {
            dob = parseDate(dobString);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        if (!dob.isOver16()) {
            System.out.println("DOB Invalid: " + dobString + " under 16.");
            return;
        }
        if (!dob.isUnder24()) {
            System.out.println("DOB Invalid: " + dobString + " over 24.");
            return;
        }

        Profile profile = new Profile(firstName, lastName, dob);
        double balance;
        try {
            balance = Double.parseDouble(inputParts[5]);
            if (balance <= 0) {
                System.out.println("Initial deposit cannot be 0 or negative.");
                return;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return;
        }
        // if (balance <= 0) {
        //     System.out.println("Initial deposit must be greater than 0!");
        //     return;
        // }
        Campus campus;
        switch (inputParts[6]) {
            case "0" -> campus = Campus.NEW_BRUNSWICK;
            case "1" -> campus = Campus.NEWARK;
            case "2" -> campus = Campus.CAMDEN;
            default -> {
                System.out.println("Invalid campus code.");
                return;
            }
        }
        Account account = new CollegeChecking(profile, balance, campus);
        if (database.open(account)) System.out.println(firstName + " " + lastName + " " + dobString + "(CC) opened.");
        else System.out.println(firstName + " " + lastName + " " + dobString + "(CC) is already in the database.");
    }

    /**
     * Processes the open command for savings accounts.
     * @param inputParts the input parameters for the open command to create a Savings object
     */
    private void openSavingsCommand(String[] inputParts) {
        String firstName = inputParts[2];
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        String lastName = inputParts[3];
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        String dobString = inputParts[4];

        Date dob;
        try { dob = parseDate(dobString); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        if (!dob.isOver16()) {
            System.out.println("DOB Invalid: " + dobString + " under 16.");
            return;
        }

        Profile profile = new Profile(firstName, lastName, dob);
        double balance;
        try {
            balance = Double.parseDouble(inputParts[5]);
            if (balance <= 0) {
                System.out.println("Initial deposit cannot be 0 or negative.");
                return;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return;
        }
        int loyalty = Integer.parseInt(inputParts[6]);
        boolean isLoyal = (loyalty == 1);
        Account account = new Savings(profile, balance, isLoyal);
        if (database.open(account)) System.out.println(firstName + " " + lastName + " " + dobString + "(S) opened.");
        else System.out.println(firstName + " " + lastName + " " + dobString + "(S) is already in the database."); 
    }

    /**
     * Processes the open command for money market accounts.
     * @param inputParts the input parameters for the open command to create a MoneyMarket object
     */
    private void openMoneyMarketCommand(String[] inputParts) {
        String firstName = inputParts[2];
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        String lastName = inputParts[3];
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        String dobString = inputParts[4];

        Date dob;
        try {
            dob = parseDate(dobString);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        if (!dob.isOver16()) {
            System.out.println("DOB Invalid: " + dobString + " under 16.");
            return;
        }

        Profile profile = new Profile(firstName, lastName, dob);
        double balance;
        try {
            balance = Double.parseDouble(inputParts[5]);
            if (balance <= 0) {
                System.out.println("Initial deposit cannot be 0 or negative.");
                return;
            }
            else if (balance < 2000) {
                System.out.println("Minimum of $2000 to open a Money Market account.");
                return;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return;
        }
        Account account = new MoneyMarket(profile, balance);
        if (database.open(account)) System.out.println(firstName + " " + lastName + " " + dobString + "(MM) opened.");
        else System.out.println(firstName + " " + lastName + " " + dobString + "(MM) is already in the database.");
    }

    /**
     * Processes the close command to direct to close-account-type helper methods.
     * @param inputParts the input parameters for the close command to pass to helper methods
     */
    private void processCloseCommand(String[] inputParts) {
        try {
            String accountType = inputParts[1];
            String firstName = inputParts[2];
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
            String lastName = inputParts[3];
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
            String dobString = inputParts[4];

            Date dob;
            try {
                dob = parseDate(dobString);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
            Profile profile = new Profile(firstName, lastName, dob);
            Account account;
            switch (accountType) {
                case CHECKING -> account = new Checking(profile);
                case COLLEGE_CHECKING -> account = new CollegeChecking(profile);
                case SAVINGS -> account = new Savings(profile);
                case MONEY_MARKET -> account = new MoneyMarket(profile);
                default -> {
                    System.out.println(accountType + " is an invalid account!");
                    return;
                }
            }
            if (database.close(account)) System.out.println(firstName + " " + lastName + " " + dobString + "(" + accountType + ") has been closed.");
            else System.out.println(firstName + " " + lastName + " " + dobString + "(" + accountType + ") is not in the database.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data for closing an account.");
        }
    }

    /**
     * Processes the deposit command.
     * @param inputParts the input parameters for the deposit command
     */
    private void processDepositCommand(String[] inputParts) {
        try {
            String firstName = inputParts[2];
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
            String lastName = inputParts[3];
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
            String dobString = inputParts[4];

            Date dob;
            try { dob = parseDate(dobString); }
            catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
            Profile profile = new Profile(firstName, lastName, dob);
            double amount;
            try {
                amount = Double.parseDouble(inputParts[5]);
                if (amount <= 0) {
                    System.out.println("Deposit - amount cannot be 0 or negative.");
                    return;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Not a valid amount.");
                return;
            }
            
            Account account;
            String accountType = inputParts[1];
            switch (accountType) {
                case CHECKING -> account = new Checking(profile, amount);
                case COLLEGE_CHECKING -> account = new CollegeChecking(profile, amount);
                case SAVINGS -> account = new Savings(profile, amount);
                case MONEY_MARKET -> account = new MoneyMarket(profile, amount);
                default -> {
                    System.out.println(accountType + " is an invalid account!");
                    return;
                }
            }
            if (database.deposit(account)) System.out.println(firstName + " " + lastName + " " + dobString + "(" + accountType + ") Deposit - balance updated.");
            else System.out.println(firstName + " " + lastName + " " + dobString + "(" + accountType + ") is not in the database.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data for depositing into an account.");
        }    
    }

    /**
     * Processes the withdraw command.
     * @param inputParts the input parameters for the withdraw command
     */
    private void processWithdrawCommand(String[] inputParts) {
        try {
            String firstName = inputParts[2];
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
            String lastName = inputParts[3];
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
            String dobString = inputParts[4];

            Date dob;
            try { dob = parseDate(dobString); }
            catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
            Profile profile = new Profile(firstName, lastName, dob);
            double amount;
            try {
                amount = Double.parseDouble(inputParts[5]);
                if (amount <= 0) {
                    System.out.println("Withdraw - amount cannot be 0 or negative.");
                    return;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Not a valid amount.");
                return;
            }
            
            Account account;
            String accountType = inputParts[1];
            switch (accountType) {
                case CHECKING -> account = new Checking(profile, amount);
                case COLLEGE_CHECKING -> account = new CollegeChecking(profile, amount);
                case SAVINGS -> account = new Savings(profile, amount);
                case MONEY_MARKET -> account = new MoneyMarket(profile, amount);
                default -> {
                    System.out.println(accountType + " is an invalid account!");
                    return;
                }
            }
            if (database.withdraw(account)) { System.out.println(firstName + " " + lastName + " " + dobString + "(" + accountType + ") Withdraw - balance updated.");}
            else if (account.balance < amount) System.out.println(firstName + " " + lastName + " " + dobString + "(" + accountType + ") Withdraw - insufficient fund.");
            else System.out.println(firstName + " " + lastName + " " + dobString + "(" + accountType + ") is not in the database.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing data for withdrawing from an account.");
        }
    }


    /**
     * Processes the print command.
     */
    private void processPrintCommand() {
        if (database.isEmpty()) System.out.println("Account Database is empty!");
        else database.printSorted();
    }

    /**
     * Processes the print interest and fees command.
     */
    private void processPrintInterestFeesCommand() {
        if (database.isEmpty()) System.out.println("Account Database is empty!");
        else database.printFeesAndInterests();
    }

    /**
     * Processes the update balance command.
     */
    private void processUpdateBalanceCommand() {
        if (database.isEmpty()) System.out.println("Account Database is empty!");
        else database.printUpdatedBalances();
    }


    /**
     * Runs the TransactionManager.
     */
    public void run() {
        System.out.println("Transaction Manager is running.");
        Scanner scanner = new Scanner(System.in);

        while (!quit && scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String[] inputParts = input.split("\\s+");

            switch (inputParts[0]) {
                case OPEN -> processOpenCommand(inputParts);
                case CLOSE -> processCloseCommand(inputParts);
                case WITHDRAW -> processWithdrawCommand(inputParts);
                case DEPOSIT -> processDepositCommand(inputParts);
                case PRINT -> processPrintCommand();
                case PRINT_INTEREST_FEES -> processPrintInterestFeesCommand();
                case UPDATE_BALANCE -> processUpdateBalanceCommand();
                case QUIT -> quit = true;
                case "" -> {}
                default -> System.out.println("Invalid command!");
            }
        }
        scanner.close();
        System.out.println("Transaction Manager is terminated.");
    }
}
