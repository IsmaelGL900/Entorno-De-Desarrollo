module org.example.tablabdvehiculos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.example.tablabdvehiculos to javafx.fxml;
    exports org.example.tablabdvehiculos;
}