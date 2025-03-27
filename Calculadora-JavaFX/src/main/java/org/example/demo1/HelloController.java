package org.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.demo1.modelo.suma;

public class HelloController {
    @FXML
    private TextField texto_1;
    @FXML
    private TextField texto_2;
    @FXML
    private TextField texto_resultado;
    @FXML
    private Button suma;


    @FXML
    private void suma(ActionEvent event) {
        try {
            int numero1 = Integer.parseInt(texto_1.getText());
            int numero2 = Integer.parseInt(texto_2.getText());

            suma s= new suma(numero1,numero2);
            this.texto_resultado.setText(s.suma()+"");
        } catch (NumberFormatException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setTitle("Error");
            alerta.setContentText("Formato incorreto");
            alerta.showAndWait();
        }

    }
}