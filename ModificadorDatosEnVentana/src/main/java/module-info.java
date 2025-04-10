module org.example.modificadordatos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.modificadordatos to javafx.fxml;
    opens org.example.modificadordatos.modelos to javafx.base;
    exports org.example.modificadordatos;
}