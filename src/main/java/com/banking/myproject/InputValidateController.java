package com.banking.myproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InputValidateController implements Initializable {
    @FXML
    private Text errorMessage;
    @FXML protected Button closeButton;

        public void create(Stage window) throws IOException {
                    FXMLLoader fxmlLoader = new FXMLLoader(InputValidateController.class.getResource("InputValidation.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    window.setScene(scene);
                    window.setTitle("Error!!");
                    window.show();
        }

        String textLabel() {
            String text = "Error!!";
            if(!AccountController.accCon.numericTextFieldCheck(AccountController.accCon.accNo)) {text += ", Account No.";}
            if(!AccountController.accCon.numericTextFieldCheck(AccountController.accCon.accPin)) {text += ", Account Pin.";}
            if(AccountController.accCon.accType == null) {text += ", Account Type";}
            if(AccountController.accCon.nameInput.isEmpty()) {text += ", Name";}
            if(AccountController.accCon.genderInput.isEmpty()) {text += ", Gender";}
            if(AccountController.accCon.dob.isEmpty()) {text += ", Date Of Birth";}
            if(AccountController.accCon.addressInput.isEmpty()) {text += ", Address";}
            if(AccountController.accCon.nationalityInput.isEmpty()) {text += ", Nationality";}
            if(AccountController.accCon.occupationInput.isEmpty()) {text += ", Occupation";}
            if(!AccountController.accCon.mobileTextFieldCheck(AccountController.accCon.phoneNum)) {text += ", Mobile";}

            return text + " Input May Be Incorrect or Empty!! Try Again!!";
        }

        @FXML
        private void closeStage() {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorMessage.setText(textLabel());
    }
}
