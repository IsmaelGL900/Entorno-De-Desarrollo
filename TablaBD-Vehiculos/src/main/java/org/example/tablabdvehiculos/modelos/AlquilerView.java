package org.example.tablabdvehiculos.modelos;

public class AlquilerView {
    private String matricula;
    private String marca;
    private double precio;
    private String fechaAlquiler;
    private String fechaEntrega;
    private double total;

    public AlquilerView(String matricula, String marca, double precio, String fechaAlquiler, String fechaEntrega, double total) {
        this.matricula = matricula;
        this.marca = marca;
        this.precio = precio;
        this.fechaAlquiler = fechaAlquiler;
        this.fechaEntrega = fechaEntrega;
        this.total = total;
    }

    public String getMatricula() { return matricula; }
    public String getMarca() { return marca; }
    public double getPrecio() { return precio; }
    public String getFechaAlquiler() { return fechaAlquiler; }
    public String getFechaEntrega() { return fechaEntrega; }
    public double getTotal() { return total; }
}
