package com.example.rubank;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.stage.Stage;

/**
 * TransactionManagerController class handles the events triggered on the GUI.
 *
 * @author Pranay Bhatt and Fiona Wang
 */

public class TransactionManagerController {
    private static final String CHECKING = "Checking";
    private static final String COLLEGE_CHECKING = "College Checking";
    private static final String SAVINGS = "Savings";
    private static final String MONEY_MARKET = "Money Market";
    private static final String NB = "NB";
    private static final String NEWARK = "Newark";
    private static final String CAMDEN = "Camden";

    @FXML
    private TextField fName_open, lName_open, depositAmount_open, fName_DW, lName_DW, amount_DW, fName_close, lName_close;
    @FXML
    private DatePicker dob_open, dob_close, dob_DW;
    @FXML
    private RadioButton checking_open, collegeChecking_open, savings_open, moneyMarket_open, checking_DW, collegeChecking_DW, savings_DW, moneyMarket_DW;
    @FXML
    private RadioButton nb_open, newark_open, camden_open;
    @FXML
    private ToggleGroup accountType_open, campus, accountType_DW, accountType_close;
    @FXML
    private CheckBox loyalCustomer;
    @FXML
    private Button openButton, depositButton, withdrawButton, closeButton;
    @FXML
    private TextArea messageArea;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab openTab, closeTab, depositWithdrawTab;
    @FXML
    private RadioButton checking_close, collegeChecking_close, savings_close, moneyMarket_close;

    private AccountDatabase database = new AccountDatabase();

    private void activateAutoSubmit(DatePicker datePicker) {
        datePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    datePicker.setValue(datePicker.getConverter().fromString(datePicker.getEditor().getText()));
                } catch (Exception e) {
                    datePicker.getEditor().setText(datePicker.getConverter().toString(datePicker.getValue()));
                }
            }
        });
    }

    /**
     * Parses the date string and returns a Date object.
     * @param dateString the date string in the format MM/DD/YYYY
     * @return the Date object
     * @throws Exception if the date is invalid or a future date.
     */
    private Date parseDate(String dateString) throws Exception {
        String[] dateParts = dateString.split("-");
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        int year = Integer.parseInt(dateParts[0]);

        Date date = new Date(year, month, day);
        if (!date.isValid()) throw new Exception("DOB invalid: " + date.toString() + " not a valid calendar date!");
        if (date.isFutureDate()) throw new Exception("DOB invalid: " + date.toString() + " cannot be today or a future day.");
        return date;
    }

    /**
     * Initializes event handlers and properties in the GUI:
     * clearing each tab when the tab is selected,
     * setting up and configuring all radio buttons and their toggle groups,
     * using listeners to track changes in text fields,
     * and disabling the buttons unless there are valid inputs in each tab.
     */
    public void initialize() {
        openTab.setOnSelectionChanged(event -> {
            if (openTab.isSelected()) {
                clearOpenTab();
            }
        });
        closeTab.setOnSelectionChanged(event -> {
            if (closeTab.isSelected()) {
                clearCloseTab();
            }
        });
        depositWithdrawTab.setOnSelectionChanged(event -> {
            if (depositWithdrawTab.isSelected()) {
                clearDepositWithdrawTab();
            }
        });

        checking_open.setToggleGroup(accountType_open);
        collegeChecking_open.setToggleGroup(accountType_open);
        savings_open.setToggleGroup(accountType_open);
        moneyMarket_open.setToggleGroup(accountType_open);

        checking_close.setToggleGroup(accountType_close);
        collegeChecking_close.setToggleGroup(accountType_close);
        savings_close.setToggleGroup(accountType_close);
        moneyMarket_close.setToggleGroup(accountType_close);

        checking_DW.setToggleGroup(accountType_DW);
        collegeChecking_DW.setToggleGroup(accountType_DW);
        savings_DW.setToggleGroup(accountType_DW);
        moneyMarket_DW.setToggleGroup(accountType_DW);

        nb_open.setToggleGroup(campus);
        newark_open.setToggleGroup(campus);
        camden_open.setToggleGroup(campus);

        nb_open.setDisable(true);
        newark_open.setDisable(true);
        camden_open.setDisable(true);
        loyalCustomer.setDisable(true);

        savings_open.setOnAction(event -> {
            loyalCustomer.setDisable(false);
            nb_open.setDisable(true);
            newark_open.setDisable(true);
            camden_open.setDisable(true);
            nb_open.setSelected(false);
            newark_open.setSelected(false);
            camden_open.setSelected(false);
        });
        checking_open.setOnAction(event -> {
            loyalCustomer.setDisable(true);
            loyalCustomer.setSelected(false);
            nb_open.setDisable(true);
            newark_open.setDisable(true);
            camden_open.setDisable(true);
            nb_open.setSelected(false);
            newark_open.setSelected(false);
            camden_open.setSelected(false);
        });
        collegeChecking_open.setOnAction(event -> {
            loyalCustomer.setDisable(true);
            loyalCustomer.setSelected(false);
            nb_open.setDisable(false);
            newark_open.setDisable(false);
            camden_open.setDisable(false);
        });
        moneyMarket_open.setOnAction(event -> {
            loyalCustomer.setDisable(true);
            loyalCustomer.setSelected(false);
            nb_open.setDisable(true);
            newark_open.setDisable(true);
            camden_open.setDisable(true);
            nb_open.setSelected(false);
            newark_open.setSelected(false);
            camden_open.setSelected(false);
        });

        activateAutoSubmit(dob_open);
        activateAutoSubmit(dob_close);
        activateAutoSubmit(dob_DW);

        openButton.setDisable(true);
        fName_open.textProperty().addListener((observable, oldValue, newValue) -> openKeyReleasedProperty());
        lName_open.textProperty().addListener((observable, oldValue, newValue) -> openKeyReleasedProperty());
        dob_open.valueProperty().addListener((observable, oldValue, newValue) -> openKeyReleasedProperty());
        depositAmount_open.textProperty().addListener((observable, oldValue, newValue) -> openKeyReleasedProperty());
        accountType_open.selectedToggleProperty().addListener((observable, oldValue, newValue) -> openKeyReleasedProperty());

        closeButton.setDisable(true);
        fName_close.textProperty().addListener((observable, oldValue, newValue) -> closeKeyReleasedProperty());
        lName_close.textProperty().addListener((observable, oldValue, newValue) -> closeKeyReleasedProperty());
        dob_close.valueProperty().addListener((observable, oldValue, newValue) -> closeKeyReleasedProperty());
        accountType_close.selectedToggleProperty().addListener((observable, oldValue, newValue) -> closeKeyReleasedProperty());

        depositButton.setDisable(true);
        withdrawButton.setDisable(true);
        fName_DW.textProperty().addListener((observable, oldValue, newValue) -> dwKeyReleasedProperty());
        lName_DW.textProperty().addListener((observable, oldValue, newValue) -> dwKeyReleasedProperty());
        dob_DW.valueProperty().addListener((observable, oldValue, newValue) -> dwKeyReleasedProperty());
        amount_DW.textProperty().addListener((observable, oldValue, newValue) -> dwKeyReleasedProperty());
        accountType_DW.selectedToggleProperty().addListener((observable, oldValue, newValue) -> dwKeyReleasedProperty());
    }

    /**
     * Handles the "open" button to direct to the method that opens an account, and clears the tab.
     */
    @FXML
    private void handleOpenButtonAction() {
        open();
        clearOpenTab();
    }

    /**
     * Opens an account using user inputs from the "open" tab.
     */
    @FXML
    private void open() {
        String fName = fName_open.getText().trim();
        fName = fName.substring(0, 1).toUpperCase() + fName.substring(1).toLowerCase();
        String lName = lName_open.getText().trim();
        lName = lName.substring(0, 1).toUpperCase() + lName.substring(1).toLowerCase();
        String dobString = dob_open.getValue().toString().trim();
        Date dob;
        try { dob = parseDate(dobString);
        } catch (Exception e) {
            messageArea.appendText(e.getMessage() + "\n");
            return; }
        if (!dob.isOver16()) {
            messageArea.appendText("DOB Invalid: " + dob.toString() + " under 16.\n");
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

        String accountTypeString = ((RadioButton) accountType_open.getSelectedToggle()).getText();
        Account account;

        switch (accountTypeString) {
            case CHECKING ->  {
                account = new Checking(profile, depositAmount);
                if (database.open(account)) {
                    messageArea.appendText(fName + " " + lName + " " + dob.toString() + "(C) opened.\n");
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
                        messageArea.appendText("Invalid campus: " + campusString);
                        return;
                    }
                }
                account = new CollegeChecking(profile, depositAmount, campus);
                if (database.open(account)) {
                    messageArea.appendText(fName + " " + lName + " " + dob.toString() + "(CC) opened.\n");
                }
                else messageArea.appendText(fName + " " + lName + " " + dob.toString() + "(CC) is already in the database.\n");

            }
            case SAVINGS -> {
                int loyalCustomerInt = loyalCustomer.isSelected() ? 1 : 0;
                boolean isLoyalCustomer = (loyalCustomerInt == 1);
                account = new Savings(profile, depositAmount, isLoyalCustomer);
                if (database.open(account)) {
                    messageArea.appendText(fName + " " + lName + " " + dob.toString() + "(S) opened.\n");
                }
                else messageArea.appendText(fName + " " + lName + " " + dob.toString() + "(S) is already in the database.\n");
            }
            case MONEY_MARKET -> {
                account = new MoneyMarket(profile, depositAmount);
                if (database.open(account)) {
                    messageArea.appendText(fName + " " + lName + " " + dobString + "(M) opened.\n");
                }
                else messageArea.appendText(fName + " " + lName + " " + dobString + "(M) is already in the database.\n");
            }
            default -> {
                messageArea.appendText("Invalid account type: " + accountTypeString);
                return;
            }
        }

    }

    /**
     * Ensures the "open" button is disabled unless the required fields and selections are properly filled/made.
     */
    @FXML
    private void openKeyReleasedProperty() {
        boolean collegeCheckingSelected = collegeChecking_open.isSelected();
        boolean isDisabled;

        if (collegeCheckingSelected) {
            isDisabled = fName_open.getText().isEmpty() || lName_open.getText().isEmpty() || dob_open.getValue() == null || depositAmount_open.getText().isEmpty() || accountType_open.getSelectedToggle() == null || campus.getSelectedToggle() == null;
        }
        else {
            isDisabled = fName_open.getText().isEmpty() || lName_open.getText().isEmpty() || dob_open.getValue() == null || depositAmount_open.getText().isEmpty() || accountType_open.getSelectedToggle() == null;
        }
        // boolean isDisabled = fName_open.getText().isEmpty() || lName_open.getText().isEmpty() || dob_open.getValue() == null || depositAmount_open.getText().isEmpty() || accountType.getSelectedToggle() == null;
        openButton.setDisable(isDisabled);
    }

    /**
     * Handles the "close" button to direct to the method that closes an account, and clears the tab.
     */
    @FXML
    private void handleCloseButtonAction() {
        close();
        clearCloseTab();
    }

    /**
     * Closes an account using user inputs from the "close" tab.
     */
    @FXML
    private void close() {
        String fName = fName_close.getText().trim();
        fName = fName.substring(0, 1).toUpperCase() + fName.substring(1).toLowerCase();
        String lName = lName_close.getText().trim();
        lName = lName.substring(0, 1).toUpperCase() + lName.substring(1).toLowerCase();
        String dobString = dob_close.getValue().toString().trim();

        Date dob;
        try {
            dob = parseDate(dobString);
        } catch (Exception e) {
            messageArea.appendText(e.getMessage() + "\n");
            return;
        }
        Profile profile = new Profile(fName, lName, dob);
        Account account;
        String accountTypeString = ((RadioButton) accountType_close.getSelectedToggle()).getText();
        switch (accountTypeString) {
            case CHECKING -> account = new Checking(profile);
            case COLLEGE_CHECKING -> account = new CollegeChecking(profile);
            case SAVINGS -> account = new Savings(profile);
            case MONEY_MARKET -> account = new MoneyMarket(profile);
            default -> {
                messageArea.appendText("Invalid account type: " + accountTypeString);
                return;
            }
        }
        String accountTypeShortString = "";
        switch (accountTypeString) {
            case CHECKING -> accountTypeShortString = "C";
            case COLLEGE_CHECKING -> accountTypeShortString = "CC";
            case SAVINGS -> accountTypeShortString = "S";
            case MONEY_MARKET -> accountTypeShortString = "M";
        }

        if (database.close(account)) {
            messageArea.appendText(fName + " " + lName + " " + dob.toString() + "(" + accountTypeShortString + ") has been closed.\n");
        }
        else messageArea.appendText(fName + " " + lName + " " + dob.toString() + "(" + accountTypeShortString + ") is not in the database.\n");
    }

    /**
     * Ensures the "close" button is disabled unless the required fields and selections are properly filled/made.
     */
    @FXML
    private void closeKeyReleasedProperty() {
        boolean isDisabled = fName_close.getText().isEmpty() || lName_close.getText().isEmpty() || dob_close.getValue() == null || accountType_close.getSelectedToggle() == null;
        closeButton.setDisable(isDisabled);
    }

    /**
     * Handles the "deposit" button to direct to the method that deposits an amount to an account, and clears the tab.
     */
    @FXML
    private void handleDepositButtonAction() {
        deposit();
        clearDepositWithdrawTab();
    }

    /**
     * Handles the "withdraw" button to direct to the method that withdraws an amount from an account, and clears the tab.
     */
    @FXML
    private void handleWithdrawButtonAction() {
        withdraw();
        clearDepositWithdrawTab();
    }

    /**
     * Deposits an amount to an account using user inputs from the "deposit/withdraw" tab.
     */
    @FXML
    private void deposit() {
        String fName = fName_DW.getText().trim();
        fName = fName.substring(0, 1).toUpperCase() + fName.substring(1).toLowerCase();
        String lName = lName_DW.getText().trim();
        lName = lName.substring(0, 1).toUpperCase() + lName.substring(1).toLowerCase();
        String dobString = dob_DW.getValue().toString().trim();

        Date dob;
        try {
            dob = parseDate(dobString);
        } catch (Exception e) {
            messageArea.appendText(e.getMessage());
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
            messageArea.appendText("Deposit amount must be a number.");
            return;
        }
        Account account;
        String accountTypeString = ((RadioButton) accountType_DW.getSelectedToggle()).getText();
        switch (accountTypeString) {
            case CHECKING -> account = new Checking(profile, amount);
            case COLLEGE_CHECKING -> account = new CollegeChecking(profile, amount);
            case SAVINGS -> account = new Savings(profile, amount);
            case MONEY_MARKET -> account = new MoneyMarket(profile, amount);
            default -> {
                messageArea.appendText("Invalid account type: " + accountTypeString);
                return;
            }
        }
        String accountTypeShortString = "";
        switch (accountTypeString) {
            case CHECKING -> accountTypeShortString = "C";
            case COLLEGE_CHECKING -> accountTypeShortString = "CC";
            case SAVINGS -> accountTypeShortString = "S";
            case MONEY_MARKET -> accountTypeShortString = "M";
        }

        if (database.deposit(account)) {
            messageArea.appendText(fName + " " + lName + " " + dob.toString() + "(" + accountTypeShortString + ") Deposit - balance updated.\n");
        }
        else messageArea.appendText(fName + " " + lName + " " + dob.toString() + "(" + accountTypeShortString + ") is not in the database.\n");
    }

    /**
     * Withdraws an amount from an account using user inputs from the "deposit/withdraw" tab.
     */
    @FXML
    private void withdraw() {
        String fName = fName_DW.getText().trim();
        fName = fName.substring(0, 1).toUpperCase() + fName.substring(1).toLowerCase();
        String lName = lName_DW.getText().trim();
        lName = lName.substring(0, 1).toUpperCase() + lName.substring(1).toLowerCase();
        String dobString = dob_DW.getValue().toString().trim();

        Date dob;
        try {
            dob = parseDate(dobString);
        } catch (Exception e) {
            messageArea.appendText(e.getMessage());
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
        String accountTypeString = ((RadioButton) accountType_DW.getSelectedToggle()).getText();
        switch (accountTypeString) {
            case CHECKING -> account = new Checking(profile, amount);
            case COLLEGE_CHECKING -> account = new CollegeChecking(profile, amount);
            case SAVINGS -> account = new Savings(profile, amount);
            case MONEY_MARKET -> account = new MoneyMarket(profile, amount);
            default -> {
                messageArea.appendText("Invalid account type: " + accountTypeString);
                return;
            }
        }
        String accountTypeShortString = "";
        switch (accountTypeString) {
            case CHECKING -> accountTypeShortString = "C";
            case COLLEGE_CHECKING -> accountTypeShortString = "CC";
            case SAVINGS -> accountTypeShortString = "S";
            case MONEY_MARKET -> accountTypeShortString = "M";
        }

        if (database.withdraw(account)) {
            messageArea.appendText(fName + " " + lName + " " + dob.toString() + "(" + accountTypeShortString + ") Withdraw - balance updated.\n");
        }
        else if (account.balance < amount) {
            messageArea.appendText(fName + " " + lName + " " + dob.toString() + "(" + accountTypeShortString + ") Withdraw - insufficient funds.\n");
        }
        else messageArea.appendText(fName + " " + lName + " " + dob.toString() + "(" + accountTypeShortString + ") is not in the database.\n");

    }

    /**
     * Ensures the "withdraw" and "deposit" buttons are disabled unless the required fields and selections are properly filled/made.
     */
    @FXML
    private void dwKeyReleasedProperty() {
        boolean isDisabled = fName_DW.getText().isEmpty() || lName_DW.getText().isEmpty() || dob_DW.getValue() == null || amount_DW.getText().isEmpty() || accountType_DW.getSelectedToggle() == null;
        depositButton.setDisable(isDisabled);
        withdrawButton.setDisable(isDisabled);
    }

    @FXML
    private Button printButton, printInterestAndFeesButton, printUpdatedBalancesButton, loadFromFileButton;

    /**
     * Processes the print event.
     */
    @FXML
    private void print() {
        if (database.isEmpty()) messageArea.appendText("Account Database is empty.\n");
        else messageArea.appendText(database.printSorted());
    }

    /**
     * Processes the print interest and fees event.
     */
    @FXML
    private void printInterestAndFees() {
        if (database.isEmpty()) messageArea.appendText("Account Database is empty.\n");
        else messageArea.appendText(database.printFeesAndInterests());
    }

    /**
     * Processes the print updated balances event.
     */
    @FXML
    private void printUpdatedBalances() {
        if (database.isEmpty()) messageArea.appendText("Account Database is empty.\n");
        else messageArea.appendText(database.printUpdatedBalances());
    }

    /**
     * Imports and loads an open source file and reads the contents of that file to open new accounts.
     */
    @FXML
    private void loadFromFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the Import");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("TXT files", "*.TXT"));
        Stage stage = new Stage();
        File targetFile = chooser.showOpenDialog(stage);
        if (targetFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(targetFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String accountType = parts[0];
                    String fName = parts[1];
                    String lName = parts[2];

                    String dobString = parts[3];
                    String[] dateParts = dobString.split("/");
                    int month = Integer.parseInt(dateParts[0]);
                    int day = Integer.parseInt(dateParts[1]);
                    int year = Integer.parseInt(dateParts[2]);
                    Date dob = new Date(year, month, day);

                    Profile profile = new Profile(fName, lName, dob);

                    double depositAmount = Double.parseDouble(parts[4]);
                    switch (accountType) {
                        case "C" -> {
                            Account account = new Checking(profile, depositAmount);
                            database.open(account);
                        }
                        case "CC" -> {
                            Campus campus;
                            switch (parts[5]) {
                                case "0" -> campus = Campus.NEW_BRUNSWICK;
                                case "1" -> campus = Campus.NEWARK;
                                case "2" -> campus = Campus.CAMDEN;
                                default -> {
                                    messageArea.appendText("Invalid campus.\n");
                                    return;
                                }
                            }
                            Account account = new CollegeChecking(profile, depositAmount, campus);
                            database.open(account);
                        }
                        case "S" -> {
                            int loyalCustomerInt = Integer.parseInt(parts[5]);
                            boolean isLoyalCustomer = (loyalCustomerInt == 1);
                            Account account = new Savings(profile, depositAmount, isLoyalCustomer);
                            database.open(account);
                        }
                        case "MM" -> {
                            Account account = new MoneyMarket(profile, depositAmount);
                            database.open(account);
                        }
                    }
                }
                messageArea.appendText("Accounts imported from file successfully.\n");
            }
            catch (IOException e) {
                messageArea.appendText("Error reading file: " + targetFile.getName() + "\n");
            }
        }
    }

    /**
     * Clears the "open" tab and resets to its default state.
     */
    private void clearOpenTab() {
        fName_open.clear();
        lName_open.clear();
        dob_open.setValue(null);
        depositAmount_open.clear();
        accountType_open.selectToggle(null);
        campus.selectToggle(null);
        nb_open.setDisable(true);
        newark_open.setDisable(true);
        camden_open.setDisable(true);
        loyalCustomer.setSelected(false);
    }

    /**
     * Clears the "close" tab and resets to its default state.
     */
    private void clearCloseTab() {
        fName_close.clear();
        lName_close.clear();
        dob_close.setValue(null);
        accountType_close.selectToggle(null);
    }

    /**
     * Clears the "Deposit/Withdraw" tab and resets to its default state.
     */
    private void clearDepositWithdrawTab() {
        fName_DW.clear();
        lName_DW.clear();
        dob_DW.setValue(null);
        amount_DW.clear();
        accountType_DW.selectToggle(null);
    }

}