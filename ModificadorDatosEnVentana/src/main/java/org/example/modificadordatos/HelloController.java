package org.example.modificadordatos;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.modificadordatos.VentanaController;
import org.example.modificadordatos.modelos.Personas;

import java.io.IOException;
import java.util.Comparator;

public class HelloController {

    @FXML
    private TableView<Personas> TablaPersonas;
    @FXML
    private TableColumn<Personas, String> NombreColum;
    @FXML
    private TableColumn<Personas, String> ApellidosColum;
    @FXML
    private TableColumn<Personas, Integer> EdadColum;
    @FXML
    private TextField filtro;

    private ObservableList<Personas> listaPersonas = FXCollections.observableArrayList();
    private FilteredList<Personas> datosFiltrados;
    private SortedList<Personas> sortedDatos;


    @FXML
    public void initialize() {
        NombreColum.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        ApellidosColum.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellidos()));
        EdadColum.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getEdad()));

        datosFiltrados = new FilteredList<>(listaPersonas, p -> true);
        sortedDatos = new SortedList<>(datosFiltrados);
        sortedDatos.comparatorProperty().bind(TablaPersonas.comparatorProperty());
        TablaPersonas.setItems(sortedDatos);

        // Configurar el filtrado en tiempo real (existente)
        filtro.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarDatos();
        });

    }


    private void filtrarDatos() {
        datosFiltrados.setPredicate(persona -> {
            // Si no hay filtro, mostrar todos
            if (filtro.getText() == null || filtro.getText().isEmpty()) {
                return true;
            }

            // Filtrar por nombre (ignorando mayúsculas/minúsculas)
            String filtrar = filtro.getText().toLowerCase();
            return persona.getNombre().toLowerCase().contains(filtrar);
        });
    }

    public void AgregarNuevo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaCrear.fxml"));
            Parent root = loader.load();

            // Obtiene el controlador de la ventana emergente
            VentanaController ventanaController = loader.getController();
            ventanaController.setListaPersonas(listaPersonas);  // 🔹 Pasamos la lista

            // Configura la escena y la ventana
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ModificarPersona() {
        Personas seleccionada = TablaPersonas.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta("Error", "Por favor seleccione una persona para modificar");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaModificar.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasar los datos
            ModificarController modificarController = loader.getController();
            modificarController.setPersona(seleccionada);
            modificarController.setListaPersonas(listaPersonas);

            // Configurar la ventana
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Modificar Persona");
            stage.showAndWait();

            // Actualizar la tabla
            TablaPersonas.refresh();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana de modificación");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}