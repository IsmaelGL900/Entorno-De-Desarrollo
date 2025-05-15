package org.example.tablabdvehiculos;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.tablabdvehiculos.modelos.AlquilerView;

import javax.print.DocFlavor;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML private TableView<AlquilerView> tablaAlquileres;
    @FXML private TableColumn<AlquilerView, String> colMatricula;
    @FXML private TableColumn<AlquilerView, String> colMarca;
    @FXML private TableColumn<AlquilerView, Double> colPrecio;
    @FXML private TableColumn<AlquilerView, String> colFechaAlquiler;
    @FXML private TableColumn<AlquilerView, String> colFechaEntrega;
    @FXML private TableColumn<AlquilerView, Double> colTotal;
    @FXML private ComboBox<String> clienteBox;
    @FXML private DatePicker FechaInicio;
    @FXML private DatePicker FechaFinal;

    @FXML
    public void initialize() {
        colMatricula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMatricula()));
        colMarca.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMarca()));
        colPrecio.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getPrecio()).asObject());
        colFechaAlquiler.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFechaAlquiler()));
        colFechaEntrega.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFechaEntrega()));
        colTotal.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getTotal()).asObject());

        cargarAlquileres();
        cargarClientes();
        FechaInicio.setOnAction(e -> cargarAlquileresFiltrados());
        FechaFinal.setOnAction(e -> cargarAlquileresFiltrados());
        clienteBox.setOnAction(e -> cargarAlquileresFiltrados());
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

    private void cargarAlquileres() {
        ObservableList<AlquilerView> lista = FXCollections.observableArrayList();

        String sql = """
        SELECT a.matricula, a.marca, a.precio, a.fecha_alquiler, a.fecha_entrega, a.total_servicio
        FROM alquileres a
    """;

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new AlquilerView(
                        rs.getString("matricula"),
                        rs.getString("marca"),
                        rs.getDouble("precio"),
                        rs.getString("fecha_alquiler"),
                        rs.getString("fecha_entrega"),
                        rs.getDouble("total_servicio")
                ));
            }

            tablaAlquileres.setItems(lista);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarAlquileresFiltrados() {
        ObservableList<AlquilerView> lista = FXCollections.observableArrayList();

        String sql = """
        SELECT a.matricula, a.marca, a.precio, a.fecha_alquiler, a.fecha_entrega, a.total_servicio
        FROM alquileres a
        JOIN clientes c ON a.nif = c.NIF
        WHERE 1=1
    """;

        List<Object> parametros = new ArrayList<>();

        // Filtro fecha inicio
        if (FechaInicio.getValue() != null) {
            sql += " AND a.fecha_alquiler = ?";
            parametros.add(Date.valueOf(FechaInicio.getValue()));
        }

        // Filtro fecha final
        if (FechaFinal.getValue() != null) {
            sql += " AND a.fecha_entrega = ?";
            parametros.add(Date.valueOf(FechaFinal.getValue()));
        }

        // Filtro cliente
        String clienteSeleccionado = clienteBox.getValue();
        if (clienteSeleccionado != null && !clienteSeleccionado.isEmpty()) {
            sql += " AND c.NyA = ?";  // Ajusta "cliente_nombre" a la columna correcta
            parametros.add(clienteSeleccionado);
        }

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new AlquilerView(
                        rs.getString("matricula"),
                        rs.getString("marca"),
                        rs.getDouble("precio"),
                        rs.getString("fecha_alquiler"),
                        rs.getString("fecha_entrega"),
                        rs.getDouble("total_servicio")
                ));
            }

            tablaAlquileres.setItems(lista);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}