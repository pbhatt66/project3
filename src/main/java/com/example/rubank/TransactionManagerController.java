package com.example.rubank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class TransactionManagerController {
    private static final String CHECKING = "Checking";
    private static final String COLLEGE_CHECKING = "College Checking";
    private static final String SAVINGS = "Savings";
    private static final String MONEY_MARKET = "Money Market";
    private static final String NB = "NB";
    private static final String NEWARK = "Newark";
    private static final String CAMDEN = "Camden";

    @FXML
    private TextField fName_open, lName_open, depositAmount_open;
    @FXML
    private DatePicker dob_open;
    @FXML
    private RadioButton checking_open, collegeChecking_open, savings_open, moneyMarket_open;
    @FXML
    private RadioButton nb_open, newark_open, camden_open;
    @FXML
    private ToggleGroup accountType, campus;
    @FXML
    private CheckBox loyalCustomer;
    @FXML
    private Button openButton;
    @FXML
    private TextArea messageArea;

    private AccountDatabase database = new AccountDatabase();

    public void initialize() {
        openButton.setDisable(true);
        fName_open.textProperty().addListener((observable, oldValue, newValue) -> openKeyReleasedProperty());
        lName_open.textProperty().addListener((observable, oldValue, newValue) -> openKeyReleasedProperty());
        dob_open.valueProperty().addListener((observable, oldValue, newValue) -> openKeyReleasedProperty());
        depositAmount_open.textProperty().addListener((observable, oldValue, newValue) -> openKeyReleasedProperty());

        closeButton.setDisable(true);
        fName_close.textProperty().addListener((observable, oldValue, newValue) -> closeKeyReleasedProperty());
        lName_close.textProperty().addListener((observable, oldValue, newValue) -> closeKeyReleasedProperty());
        dob_close.valueProperty().addListener((observable, oldValue, newValue) -> closeKeyReleasedProperty());

        depositButton.setDisable(true);
        withdrawButton.setDisable(true);
        fName_DW.textProperty().addListener((observable, oldValue, newValue) -> dwKeyReleasedProperty());
        lName_DW.textProperty().addListener((observable, oldValue, newValue) -> dwKeyReleasedProperty());
        dob_DW.valueProperty().addListener((observable, oldValue, newValue) -> dwKeyReleasedProperty());
        amount_DW.textProperty().addListener((observable, oldValue, newValue) -> dwKeyReleasedProperty());

    }

    @FXML
    private void open() {
        String fName = fName_open.getText();
        fName = fName.substring(0, 1).toUpperCase() + fName.substring(1).toLowerCase();
        String lName = lName_open.getText();
        lName = lName.substring(0, 1).toUpperCase() + lName.substring(1).toLowerCase();
        String dobString = dob_open.getValue().toString();
        // System.out.println(dobString);
        Date dob;
        try { dob = parseDate(dobString);
        } catch (Exception e) {
            messageArea.appendText(e.getMessage() + "\n");
            return; }
        if (!dob.isOver16()) {
            messageArea.appendText("DOB Invalid: " + dobString + " under 16.\n");
            return;
        }
        Profile profile = new Profile(fName, lName, dob);
        double depositAmount;
        try {
            depositAmount = Double.parseDouble(depositAmount_open.getText());
            if (depositAmount <= 0) {
                messageArea.appendText("Deposit amount cannot be 0 or negative.\n");
                return;
            }
        }
        catch (NumberFormatException e) {
            messageArea.appendText("Not a valid amount.\n");
            return;
        }

        String accountTypeString = ((RadioButton) accountType.getSelectedToggle()).getText();
        // System.out.println(accountTypeString);
        // messageArea.appendText("Account opened for " + fName + " " + lName + " born on " + dobString + "\n");
        Account account;
        switch (accountTypeString) {
            case CHECKING ->  {
                account = new Checking(profile, depositAmount);
                if (database.open(account)) {
                    messageArea.appendText(fName + " " + lName + " " + dobString + "(C) opened.\n");
                }
                else messageArea.appendText(fName + " " + lName + " " + dobString + "(C) is already in the database.\n");
            }
            case COLLEGE_CHECKING -> {
                String campusString = ((RadioButton) campus.getSelectedToggle()).getText();
                Campus campus;
                switch (campusString) {
                    case NB -> campus = Campus.NEW_BRUNSWICK;
                    case NEWARK -> campus = Campus.NEWARK;
                    case CAMDEN -> campus = Campus.CAMDEN;
                    default -> {
                        System.out.println("Invalid campus: " + campusString);
                        return;
                    }
                }
                account = new CollegeChecking(profile, depositAmount, campus);
                if (database.open(account)) {
                    messageArea.appendText(fName + " " + lName + " " + dobString + "(CC) opened.\n");
                }
                else messageArea.appendText(fName + " " + lName + " " + dobString + "(CC) is already in the database.\n");

            }
            case SAVINGS -> {
                int loyalCustomerInt = loyalCustomer.isSelected() ? 1 : 0;
                boolean isLoyalCustomer = (loyalCustomerInt == 1);
                account = new Savings(profile, depositAmount, isLoyalCustomer);
                if (database.open(account)) {
                    messageArea.appendText(fName + " " + lName + " " + dobString + "(S) opened.\n");
                }
                else messageArea.appendText(fName + " " + lName + " " + dobString + "(S) is already in the database.\n");
            }
            case MONEY_MARKET -> {
account = new MoneyMarket(profile, depositAmount);
                if (database.open(account)) {
                    messageArea.appendText(fName + " " + lName + " " + dobString + "(M) opened.\n");
                }
                else messageArea.appendText(fName + " " + lName + " " + dobString + "(M) is already in the database.\n");
            }
            default -> {
                System.out.println("Invalid account type: " + accountTypeString);
                return;
            }
        }

    }

    @FXML
    private void openKeyReleasedProperty() {
        boolean isDisabled = fName_open.getText().isEmpty() || lName_open.getText().isEmpty() || dob_open.getValue() == null || depositAmount_open.getText().isEmpty();
        openButton.setDisable(isDisabled);
    }

    private Date parseDate(String dateString) throws Exception {
        String[] dateParts = dateString.split("-");
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        int year = Integer.parseInt(dateParts[0]);

        Date date = new Date(year, month, day);
        if (!date.isValid()) throw new Exception("DOB invalid: " + dateString + " not a valid calendar date!");
        if (date.isFutureDate()) throw new Exception("DOB invalid: " + dateString + " cannot be today or a future day.");
        return date;
    }

    @FXML
    private Button closeButton;
    @FXML
    private TextField fName_close, lName_close;
    @FXML
    private RadioButton checking_close, collegeChecking_close, savings_close, moneyMarket_close;
    @FXML
    private DatePicker dob_close;
    @FXML
    private void close() {
        String fName = fName_close.getText();
        fName = fName.substring(0, 1).toUpperCase() + fName.substring(1).toLowerCase();
        String lName = lName_close.getText();
        lName = lName.substring(0, 1).toUpperCase() + lName.substring(1).toLowerCase();
        String dobString = dob_close.getValue().toString();

        Date dob;
        try {
            dob = parseDate(dobString);
        } catch (Exception e) {
            messageArea.appendText(e.getMessage() + "\n");
            return;
        }
        Profile profile = new Profile(fName, lName, dob);
        Account account;
        String accountTypeString = ((RadioButton) accountType.getSelectedToggle()).getText();
        switch (accountTypeString) {
            case CHECKING -> account = new Checking(profile);
            case COLLEGE_CHECKING -> account = new CollegeChecking(profile);
            case SAVINGS -> account = new Savings(profile);
            case MONEY_MARKET -> account = new MoneyMarket(profile);
            default -> {
                System.out.println("Invalid account type: " + accountTypeString);
                return;
            }
        }
        if (database.close(account)) {
            messageArea.appendText(fName + " " + lName + " " + dobString + "(" + accountTypeString + ") has been closed.\n");
        }
        else messageArea.appendText(fName + " " + lName + " " + dobString + "(" + accountTypeString + ") is not in the database.\n");
    }
    @FXML
    private void closeKeyReleasedProperty() {
        boolean isDisabled = fName_close.getText().isEmpty() || lName_close.getText().isEmpty() || dob_close.getValue() == null;
        closeButton.setDisable(isDisabled);
    }

    @FXML
    private Button depositButton, withdrawButton;
    @FXML
    private TextField fName_DW, lName_DW, amount_DW;
    @FXML
    private DatePicker dob_DW;
    @FXML
    private void deposit() {
        String fName = fName_DW.getText();
        fName = fName.substring(0, 1).toUpperCase() + fName.substring(1).toLowerCase();
        String lName = lName_DW.getText();
        lName = lName.substring(0, 1).toUpperCase() + lName.substring(1).toLowerCase();
        String dobString = dob_DW.getValue().toString();

        Date dob;
        try {
            dob = parseDate(dobString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        Profile profile = new Profile(fName, lName, dob);
        double amount;
        try {
            amount = Double.parseDouble(amount_DW.getText());
            if (amount <= 0) {
                messageArea.appendText("Deposit amount cannot be 0 or negative.\n");
                return;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Deposit amount must be a number.");
            return;
        }
        Account account;
        String accountTypeString = ((RadioButton) accountType.getSelectedToggle()).getText();
        switch (accountTypeString) {
            case CHECKING -> account = new Checking(profile, amount);
            case COLLEGE_CHECKING -> account = new CollegeChecking(profile, amount);
            case SAVINGS -> account = new Savings(profile, amount);
            case MONEY_MARKET -> account = new MoneyMarket(profile, amount);
            default -> {
                System.out.println("Invalid account type: " + accountTypeString);
                return;
            }
        }
        if (database.deposit(account)) {
            messageArea.appendText(fName + " " + lName + " " + dobString + "(" + accountTypeString + ") Deposit - balance updated.\n");
        }
        else messageArea.appendText(fName + " " + lName + " " + dobString + "(" + accountTypeString + ") is not in the database.\n");
    }
    @FXML
    private void withdraw() {
        String fName = fName_DW.getText();
        fName = fName.substring(0, 1).toUpperCase() + fName.substring(1).toLowerCase();
        String lName = lName_DW.getText();
        lName = lName.substring(0, 1).toUpperCase() + lName.substring(1).toLowerCase();
        String dobString = dob_DW.getValue().toString();

        Date dob;
        try {
            dob = parseDate(dobString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        Profile profile = new Profile(fName, lName, dob);
        double amount;
        try {
            amount = Double.parseDouble(amount_DW.getText());
            if (amount <= 0) {
                messageArea.appendText("Withdraw amount cannot be 0 or negative.\n");
                return;
            }
        }
        catch (NumberFormatException e) {
            messageArea.appendText("Not a valid amount.\n");
            return;
        }

        Account account;
        String accountTypeString = ((RadioButton) accountType.getSelectedToggle()).getText();
        switch (accountTypeString) {
            case CHECKING -> account = new Checking(profile, amount);
            case COLLEGE_CHECKING -> account = new CollegeChecking(profile, amount);
            case SAVINGS -> account = new Savings(profile, amount);
            case MONEY_MARKET -> account = new MoneyMarket(profile, amount);
            default -> {
                System.out.println("Invalid account type: " + accountTypeString);
                return;
            }
        }
        if (database.withdraw(account)) {
            messageArea.appendText(fName + " " + lName + " " + dobString + "(" + accountType + ") Withdraw - balance updated.\n");
        }
        else messageArea.appendText(fName + " " + lName + " " + dobString + "(" + accountType + ") is not in the database.\n");

    }

    @FXML
    private void dwKeyReleasedProperty() {
        boolean isDisabled = fName_DW.getText().isEmpty() || lName_DW.getText().isEmpty() || dob_DW.getValue() == null || amount_DW.getText().isEmpty();
        depositButton.setDisable(isDisabled);
        withdrawButton.setDisable(isDisabled);
    }

    @FXML
    private Button printButton, printInterestAndFeesButton, printUpdatedBalancesButton, loadFromFileButton;
    @FXML
    private void print() {
//        if (database.isEmpty()) messageArea.appendText("Account Database is empty.\n");
//        else messageArea.appendText(database.printSorted());
    }

    @FXML
    private void printInterestAndFees() {
//        if (database.isEmpty()) messageArea.appendText("Account Database is empty.\n");
//        else messageArea.appendText(database.printFeesAndInterests());
    }

    @FXML
    private void printUpdatedBalances() {
//        if (database.isEmpty()) messageArea.appendText("Account Database is empty.\n");
//        else messageArea.appendText(database.printUpdatedBalances());
    }

}