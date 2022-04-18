package com.banking.myproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MyPage implements Initializable {
    @FXML TextField nameTF;
    @FXML DatePicker dateOfBirth;
    @FXML TextField nationalityTF;
    @FXML TextField genderTF;
    @FXML TextField addressTF;
    @FXML TextField accNoTF;
    @FXML TextField accTypeTF;
    @FXML TextField occupationTF;
    @FXML TextField mobileTF;
    @FXML private PasswordField oldPin;
    @FXML private PasswordField newPin;
    @FXML private PasswordField confirmPin;
    @FXML private TextField todayDate;
    Database db = new Database();
    ResultSet rs;
    PreparedStatement pst;

    public static String accNo;
    public static String accPin;

    public MyPage() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dateOfBirth.setDisable(true);
        this.dateOfBirth.setStyle("-fx-opacity: 1");
        this.dateOfBirth.getEditor().setStyle("-fx-opacity: 1");
        this.todayDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
    }

    @FXML
    private void editProfileButton() {
        this.nameTF.setEditable(true);
        this.dateOfBirth.setDisable(false);
        this.nationalityTF.setEditable(true);
        this.genderTF.setEditable(true);
        this.addressTF.setEditable(true);
        this.accNoTF.setEditable(true);
        this.accTypeTF.setEditable(true);
        this.occupationTF.setEditable(true);
        this.mobileTF.setEditable(true);
    }

    @FXML
    private void viewProfile() {
        String sql = "select * from account where account_no=? and account_pin=?";
        try {
            pst = db.prepare(sql);
            pst.setString(1, MyPage.accNo);
            pst.setString(2, MyPage.accPin);
            System.out.println(MyPage.accNo);
            System.out.println(MyPage.accPin);
            rs = pst.executeQuery();
            if(rs.next()) {
                this.nameTF.setText(rs.getString("name"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(rs.getString("date_of_birth"), formatter);
                this.dateOfBirth.setDisable(true);
                this.dateOfBirth.setValue(localDate);
                this.nationalityTF.setText(rs.getString("nationality"));
                this.genderTF.setText(rs.getString("gender"));
                this.addressTF.setText(rs.getString("address"));
                this.accNoTF.setText(rs.getString("account_no"));
                this.accTypeTF.setText(rs.getString("account_type"));
                this.occupationTF.setText(rs.getString("occupation"));
                this.mobileTF.setText(rs.getString("mobile"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveProfile() {
        String sql="update account set name=?, date_of_birth=?, nationality=?, gender=?, address=?, " +
                "account_no=?, account_type=?, occupation=?, mobile=? where account_no=? and account_pin=?";
        String sql2 = "update bank set account_no=?, name=? where account_no=?";
        try {
            pst = db.prepare(sql);
            pst.setString(1, this.nameTF.getText());
            pst.setString(2, this.dateOfBirth.getValue().toString());
            pst.setString(3, this.nationalityTF.getText());
            pst.setString(4, this.genderTF.getText());
            pst.setString(5, this.addressTF.getText());
            pst.setString(6, this.accNoTF.getText());
            pst.setString(7, this.accTypeTF.getText());
            pst.setString(8, this.occupationTF.getText());
            pst.setString(9, this.mobileTF.getText());
            pst.setString(10, MyPage.accNo);
            pst.setString(11, MyPage.accPin);

            PreparedStatement pst2 = db.prepare(sql2);
            pst2.setString(1, this.accNoTF.getText());
            pst2.setString(2, this.nameTF.getText());
            pst2.setString(3, MyPage.accNo);


            MyPage.accNo = this.accNoTF.getText();
            pst.execute();
            pst2.execute();

            disableField();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void disableField() {
        this.nameTF.setEditable(false);
        this.dateOfBirth.setDisable(true);
        this.nationalityTF.setEditable(false);
        this.genderTF.setEditable(false);
        this.addressTF.setEditable(false);
        this.accNoTF.setEditable(false);
        this.accTypeTF.setEditable(false);
        this.occupationTF.setEditable(false);
        this.mobileTF.setEditable(false);
    }

    @FXML private void changePin() {
        String sql = "select * from account where account_no=? and account_pin=?";
        try {
            pst = db.prepare(sql);
            pst.setString(1, MyPage.accNo);
            pst.setString(2, oldPin.getText());
            rs = pst.executeQuery();
            AccountController accCon = new AccountController();
            if(rs.next()) {
                if(newPin.getText().equals(confirmPin.getText()) && accCon.numericTextFieldCheck(newPin.getText())) {
                    String query = "update account set account_pin=? where account_no=? and account_pin=?";
                    try {
                        PreparedStatement pst1 = db.prepare(query);
                        pst1.setString(1, newPin.getText());
                        pst1.setString(2, MyPage.accNo);
                        pst1.setString(3, oldPin.getText());
                        pst1.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML private void clearPin() {
        oldPin.clear();
        newPin.clear();
        confirmPin.clear();
    }

    @FXML private TextField nameDep;
    @FXML private TextField accNoDep;
    @FXML private TextField balanceDep;
    @FXML private TextField amountDep;
    Bank bank = new Bank();



    @FXML private void depositDisplay() {
        db.readBank(bank);
        this.nameDep.setText(this.nameTF.getText());
        this.accNoDep.setText(this.accNoTF.getText());
        this.balanceDep.setText(Double.toString(this.bank.getBalance()));
    }

    @FXML private void deposit() {
        String amountDepText = amountDep.getText();
        try {
            if(doubleValidation(amountDepText)) {
                bank.depositAmount(Double.parseDouble(amountDepText));
                db.insertBank(this.bank);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        depositDisplay();
    }

    private boolean doubleValidation(String text) {
        if(text.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(text);
            System.out.println("Accepted");
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Not Accepted");
            return false;
        }
    }
}
