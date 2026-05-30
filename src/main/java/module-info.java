module com.example.laba2_4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.laba2_4 to javafx.fxml;
    exports com.example.laba2_4;
}