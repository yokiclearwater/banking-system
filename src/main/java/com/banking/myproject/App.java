package com.banking.myproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {
    static Stage window;
    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        window = stage;
        Database db = new Database();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Authentication.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window = new Stage();
        window.setTitle("Banking Management System");
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}