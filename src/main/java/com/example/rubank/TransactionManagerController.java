package com.example.rubank;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TransactionManagerController {

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
    }

    @FXML
    private void open(ActionEvent event) {
        String fName = fName_open.getText();
        String lName = lName_open.getText();
        String dobString = dob_open.getValue().toString();
        System.out.println(dobString);
        Date dob;
        try {
            dob = parseDate(dobString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        if (!dob.isOver16()) {
            System.out.println("DOB Invalid: " + dobString + " under 16.");
            return;
        }

        String accountTypeString = ((RadioButton) accountType.getSelectedToggle()).getText();
        System.out.println(accountTypeString);
        messageArea.appendText("Account opened for " + fName + " " + lName + " born on " + dobString + "\n");
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
    private void close() {

    }

}