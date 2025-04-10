package org.example.modificadordatos;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.modificadordatos.modelos.Personas;

import java.awt.event.ActionEvent;

public class ModificarController {
    @FXML
    private TextField NombreMod;
    @FXML
    private TextField ApellidosMod;
    @FXML
    private TextField EdadMod;
    @FXML
    private Button ModificarBoton;
    @FXML
    private Button CerrarBotonMod;

    private Runnable onCloseCallback;
    private Personas personaSeleccionada;
    private ObservableList<Personas> listaPersonas;

    public void setOnCloseCallback(Runnable callback) {
        this.onCloseCallback = callback;
    }


    public void setPersona(Personas persona) {
        this.personaSeleccionada = persona;
        // Precargar los datos en los campos
        NombreMod.setText(persona.getNombre());
        ApellidosMod.setText(persona.getApellidos());
        EdadMod.setText(String.valueOf(persona.getEdad()));
    }

    public void setListaPersonas(ObservableList<Personas> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    @FXML
    private void Modificar(javafx.event.ActionEvent event) {
        // Actualizar el objeto persona con los nuevos valores
        personaSeleccionada.setNombre(NombreMod.getText());
        personaSeleccionada.setApellidos(ApellidosMod.getText());
        personaSeleccionada.setEdad(Integer.parseInt(EdadMod.getText()));

        if (onCloseCallback != null) {
            onCloseCallback.run();
        }
        // Cerrar la ventana
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void CerrarVentanaMod(javafx.event.ActionEvent event) {
        if (onCloseCallback != null) {
            onCloseCallback.run();
        }
        ((Stage) CerrarBotonMod.getScene().getWindow()).close();
    }

}
