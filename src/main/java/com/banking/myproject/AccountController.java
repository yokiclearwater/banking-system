package com.banking.myproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccountController implements Initializable {
    // FXML ELEMENT (BUTTON, TEXTFIELD, RADIOBUTTON...)
    @FXML protected TextField accountNo;
    @FXML protected ComboBox<String> accountType;
    @FXML protected TextField accountPin;
    @FXML protected TextField name;
    @FXML protected TextField address;
    @FXML protected TextField nationality;
    @FXML protected TextField occupation;
    @FXML protected TextField mobile;
    @FXML protected RadioButton male;
    @FXML protected RadioButton female;
    @FXML protected DatePicker dateOfBirth;
    ArrayList<Account> accList = new ArrayList<Account>();
    private boolean flag;
    private final ToggleGroup genderGroup = new ToggleGroup();
    private final Stage window = new Stage();
    private final Database db = new Database();

    // String for getting data from GUI
    protected String accNo, accType, accPin, nameInput,
            genderInput, nationalityInput, occupationInput,
            phoneNum, dob, addressInput;

    protected static AccountController accCon; // static class for giving access to other classes
    public AccountController() throws SQLException, ClassNotFoundException {
        accCon = this;
    }


    // initialize on running stage
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list= FXCollections.observableArrayList("Saving","Current");
        accCon.accountType.setItems(list);
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
    }

    // Function for Create Button (Get Input, and Validate Them)
    @FXML
    private void createButton() throws IOException {
        accCon.accPin = accCon.accountPin.getText();
        accCon.accNo = accCon.accountNo.getText();
        accCon.accType = accCon.accountType.getValue();
        accCon.nameInput = accCon.name.getText();
        RadioButton selectedToggle = (RadioButton) genderGroup.getSelectedToggle();
        accCon.genderInput = (selectedToggle == null) ? "" : selectedToggle.getText();
        accCon.phoneNum = mobile.getText();
        accCon.dob = (dateOfBirth.getValue() != null) ? dateOfBirth.getValue().toString() : "";
        accCon.addressInput = address.getText();
        accCon.nationalityInput = nationality.getText();
        accCon.occupationInput = occupation.getText();
        validateAll();
        if(flag) {
            Account acc = new Account(accCon.accNo, accCon.accType, accCon.genderInput,
                    accCon.addressInput, accCon.nameInput, accCon.nationalityInput,
                    accCon.occupationInput, accCon.phoneNum, accCon.accPin, accCon.dateOfBirth.getValue());
            accList.add(acc);
            for(Account a : accList) {
                try {
                    db.insertData(a);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println(a);
            }
        }
    }

    // reset button
    @FXML
    private void clearButton() {
        accCon.accountNo.clear();
        accCon.accountPin.clear();
        accCon.name.clear();
        accCon.accountType.getSelectionModel().clearSelection();
        accCon.mobile.clear();
        accCon.nationality.clear();
        accCon.occupation.clear();
        accCon.dateOfBirth.getEditor().clear();
        accCon.address.clear();
    }

    // Function to validate all input
    private void validateAll() throws IOException {
        flag = numericTextFieldCheck(accCon.accNo) &&
                numericTextFieldCheck(accCon.accPin) &&
                mobileTextFieldCheck(accCon.phoneNum) && !(accCon.addressInput).isEmpty()
                && !(accCon.genderInput).isEmpty() && (accCon.accType != null)
                && !(accCon.nationalityInput.isEmpty()) && !(accCon.nameInput.isEmpty())
                && !(accCon.occupationInput.isEmpty()) && !(accCon.dob).isEmpty();
        validateInputScene(flag);
    }

    // function to validate whether an input is an integer or not
    boolean numericTextFieldCheck(String text) {
        if(text.isEmpty()) {
            return false;
        }
        try {
            Long.parseLong(text);
            System.out.println("Accepted");
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Not Accepted");
            return false;
        }
    }

    // function to validate whether an input is a mobile number
    // condition is it should start with + or 0
    boolean mobileTextFieldCheck(String mobile) {
        if(mobile.isEmpty() || (mobile.charAt(0) != '+' && mobile.charAt(0) != '0')) {
            return false;
        }
        else {
            String text = mobile.substring(1);
            return numericTextFieldCheck(text);
        }
    }

    // validate number input scene
    // creating a new scene showing error message
    private void validateInputScene(boolean validateBool) throws IOException {
        if(!validateBool) {
            InputValidateController ivc = new InputValidateController();
            ivc.create(window);
        }
    }

    Stage app = App.window;
    @FXML
    private void backButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Authentication.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        app.setTitle("Banking Management System");
        app.setScene(scene);
        app.show();
    }
}