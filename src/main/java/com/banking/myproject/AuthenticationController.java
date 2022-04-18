package com.banking.myproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationController {
    @FXML protected TextField accNo;
    @FXML protected TextField accPin;
    Database db = new Database();
    PreparedStatement pst;
    ResultSet rs;
    protected static AuthenticationController authCon; // static class for giving access to other classes
    public AuthenticationController() throws SQLException, ClassNotFoundException {
        authCon = this;
    }

    @FXML
    private void authedCheck() throws SQLException {
        String sql = "select * from account where account_no=? and account_pin=?";
        try {
            pst = db.prepare(sql);
            pst.setString(1, accNo.getText());
            pst.setString(2, accPin.getText());
            rs = pst.executeQuery();
            if(rs.next()) {
                MyPage.accNo = rs.getString("account_no");
                MyPage.accPin = rs.getString("account_pin");
                newScene(App.window, "MyPage.fxml");
            }
            else {
                loginFailed();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            pst.close();
            rs.close();
        }
    }

    private void newScene(Stage window, String fileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fileName));
        Scene scene = new Scene(fxmlLoader.load());
        window.setTitle("Banking Management System");
        window.setScene(scene);
        window.show();
    }

    final Stage errorLogin = new Stage();
    private void loginFailed() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("LoginValidation.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        errorLogin.setTitle("Banking Management System");
        errorLogin.setScene(scene);
        errorLogin.show();
    }

    @FXML
    private void register() throws IOException {
        newScene(App.window, "Account.fxml");
    }
    @FXML private Button closeButton;
    @FXML
    private void closeStage() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
