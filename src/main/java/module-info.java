module com.example.echohive {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.example.echohive to javafx.fxml;
    exports com.example.echohive;
}