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
import org.example.modificadordatos.modelos.Personas;

import java.io.IOException;
import java.sql.*;
import java.util.Comparator;

public class HelloController {
    @FXML
    private ComboBox<String> vehiculoBox;
    @FXML
    private ComboBox<String> clienteBox;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtKilometros;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtNIF;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtPoblacion;
    @FXML
    private TextField txtTotalServicio;
    @FXML
    private DatePicker FechaAlquiler;
    @FXML
    private DatePicker FechaEntrega;
    @FXML
    private Button btnGrabar;

    @FXML
    public void initialize() {
        cargarVehiculos();
        cargarClientes();

        vehiculoBox.setOnAction(event -> cargarDatosVehiculo());
        clienteBox.setOnAction(event -> cargarDatosCliente());

        FechaAlquiler.valueProperty().addListener((obs, oldVal, newVal) -> calcularTotalServicio());
        FechaEntrega.valueProperty().addListener((obs, oldVal, newVal) -> calcularTotalServicio());
    }

    private void cargarVehiculos() {
        String sql = "SELECT matricula FROM vehiculos";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String item = rs.getString("matricula");
                vehiculoBox.getItems().add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarClientes() {
        String sql = "SELECT NyA FROM clientes";

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String item = rs.getString("NyA");
                clienteBox.getItems().add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarDatosVehiculo() {
        String seleccion = vehiculoBox.getValue();
        if (seleccion == null) return;

        String matricula = seleccion.split(" - ")[0];

        String sql = "SELECT descripcion, marca, kilometros, precio FROM vehiculos WHERE matricula = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, matricula);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtDescripcion.setText(rs.getString("descripcion"));
                txtMarca.setText(rs.getString("marca"));
                txtKilometros.setText(rs.getString("kilometros"));
                txtPrecio.setText(rs.getString("precio"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarDatosCliente() {
        String seleccion = clienteBox.getValue();
        if (seleccion == null) return;

        String nif = seleccion.split(" - ")[0];

        String sql = "SELECT NIF, Direccion, Poblacion FROM clientes WHERE NyA = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nif);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtNIF.setText(rs.getString("NIF"));
                txtDireccion.setText(rs.getString("Direccion"));
                txtPoblacion.setText(rs.getString("Poblacion"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void calcularTotalServicio() {
        if (FechaAlquiler.getValue() == null || FechaEntrega.getValue() == null) return;

        long dias = java.time.temporal.ChronoUnit.DAYS.between(FechaAlquiler.getValue(), FechaEntrega.getValue());
        if (dias <= 0) dias = 1; // mínimo 1 día

        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            double total = dias * precio;
            txtTotalServicio.setText(String.format("%.2f", total));
        } catch (NumberFormatException e) {
            txtTotalServicio.setText("0.00");
        }
    }

    @FXML
    private void grabarAlquiler(ActionEvent event) {
        if (vehiculoBox.getValue() == null || clienteBox.getValue() == null ||
                FechaAlquiler.getValue() == null || FechaEntrega.getValue() == null) {
            mostrarAlerta("Faltan datos por completar.");
            return;
        }

        String matricula = vehiculoBox.getValue();
        String nif = txtNIF.getText();

        String sql = "INSERT INTO alquileres (fecha_alquiler, fecha_entrega, matricula, nif, descripcion, marca, kilometros, precio, total_servicio) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(FechaAlquiler.getValue()));
            ps.setDate(2, Date.valueOf(FechaEntrega.getValue()));
            ps.setString(3, matricula);
            ps.setString(4, nif);
            ps.setString(5, txtDescripcion.getText());
            ps.setString(6, txtMarca.getText());
            ps.setInt(7, Integer.parseInt(txtKilometros.getText()));
            ps.setDouble(8, Double.parseDouble(txtPrecio.getText().replace(",", ".")));
            ps.setDouble(9, Double.parseDouble(txtTotalServicio.getText().replace(",", ".")));

            ps.executeUpdate();

            mostrarAlerta("Alquiler guardado correctamente.");
            limpiarCampos();

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al guardar en la base de datos.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        vehiculoBox.setValue(null);
        clienteBox.setValue(null);
        txtDescripcion.clear();
        txtMarca.clear();
        txtKilometros.clear();
        txtPrecio.clear();
        txtTotalServicio.clear();
        txtNIF.clear();
        txtDireccion.clear();
        txtPoblacion.clear();
        FechaAlquiler.setValue(null);
        FechaEntrega.setValue(null);
    }
}