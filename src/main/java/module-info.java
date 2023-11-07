module com.example.rubank {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rubank to javafx.fxml;
    exports com.example.rubank;
}