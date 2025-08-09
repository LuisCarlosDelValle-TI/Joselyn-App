package com.example.joytec.models;

public class ProductoRequest {
    // No se necesita para una petición GET, pero aquí está el código
    // si lo necesitas para peticiones POST o PUT.

    private String nombre;
    private double precio;

    public ProductoRequest(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}