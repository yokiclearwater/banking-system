module com.banking.myproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.banking.myproject to javafx.fxml;
    exports com.banking.myproject;
}