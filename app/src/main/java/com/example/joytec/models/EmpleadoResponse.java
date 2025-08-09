package com.example.joytec.models;

public class EmpleadoResponse {
    private int id_empleado;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String puesto;
    private double salario;

    // Constructor vacío
    public EmpleadoResponse() {}

    // Getters y Setters
    public int getId_empleado() { return id_empleado; }
    public void setId_empleado(int id_empleado) { this.id_empleado = id_empleado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    // Método auxiliar
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}