package org.example.modificadordatos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.modificadordatos.modelos.Personas;

public class VentanaController {
    private ObservableList<Personas> listaPersonas = FXCollections.observableArrayList(); // 🔹 Inicialización

    @FXML
    private TextField NombreText;
    @FXML
    private TextField ApellidosText;
    @FXML
    private TextField EdadText;
    @FXML
    private Button guardarBoton;
    @FXML
    private Button cerrarBoton;

    public void setListaPersonas(ObservableList<Personas> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    @FXML
    public void Guardar(ActionEvent event) {
        try {
            Personas nuevaPersona =new  Personas(NombreText.getText(), ApellidosText.getText(), Integer.parseInt(EdadText.getText()));

            listaPersonas.add(nuevaPersona);

            // Cerrar la ventana después de guardar
            Stage stage = (Stage) guardarBoton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}